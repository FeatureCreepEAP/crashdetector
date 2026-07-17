#!/usr/bin/env bash
#
# build-portable-rpm.sh
#
# Run this from the root of the CrashDetector Maven project on RHEL 9.
# It builds the Maven JAR first, then creates a conservative RPM intended for:
#   - RHEL 9 and 10
#   - Fedora
#   - SUSE Linux Enterprise 15 and 16
#
# The RPM deliberately:
#   - has no %{?dist} suffix;
#   - uses a gzip payload instead of a newer distro-specific default;
#   - avoids distro-specific RPM macros;
#   - disables automatic dependency generation;
#   - is noarch unless native ELF files are explicitly allowed.
#
# Common usage:
#   ./build-portable-rpm.sh
#   JAVA_DEP=devel ./build-portable-rpm.sh
#   JAVA_DEP=headless ./build-portable-rpm.sh
#   JAVA_DEP=none ./build-portable-rpm.sh
#   AUTO_INSTALL_BUILD_DEPS=1 ./build-portable-rpm.sh
#
# Supported environment variables:
#   JAVA_DEP=runtime|devel|headless|generic|none
#       runtime  -> Requires: java-21-openjdk             (default; supports Swing)
#       devel    -> Requires: java-21-openjdk-devel
#       headless -> Requires: java-21-openjdk-headless
#       generic  -> Requires: /usr/bin/java, with a Java 21 runtime check at launch
#       none     -> no RPM Java dependency, with a Java 21 runtime check at launch
#
#   AUTO_INSTALL_BUILD_DEPS=1
#       Uses sudo dnf to install rpm-build, Maven and Java 21 development tools.
#
#   SKIP_TESTS=1|0                 Default: 1
#   MAVEN_ARGS="..."               Additional Maven arguments
#   PACKAGE_NAME="..."             Override the RPM package name
#   RPM_VERSION="..."              Override the RPM Version
#   RPM_RELEASE="..."              Override the RPM Release
#   RPM_LICENSE="..."              Default: Public Domain
#   RPM_SUMMARY="..."              Default: CrashDetector JVM crash analysis application
#   RPM_URL="..."                  Override project URL
#   MAIN_CLASS="..."               Default: com.asbestosstar.crashdetector.app.CrashDetectorApp
#   COMMAND_NAME="..."             Default: RPM package name
#   INCLUDE_PROJECT_DEPS=1|0       Include the root deps/ directory; default: 1
#   ALLOW_NATIVE_DEPS=1|0          Default: 0. Enabling this reduces portability.
#   OUTPUT_DIR="..."               Default: target/rpm
#   KEEP_RPMBUILD_TREE=1|0         Default: 0
#   SIGN_RPM=1|0                   Default: 0; requires configured rpmsign
#
set -Eeuo pipefail
IFS=$'\n\t'

readonly SCRIPT_NAME="${0##*/}"
readonly PROJECT_ROOT="$(pwd -P)"
readonly POM_FILE="${PROJECT_ROOT}/pom.xml"

die() {
    printf 'ERROR: %s\n' "$*" >&2
    exit 1
}

warn() {
    printf 'WARNING: %s\n' "$*" >&2
}

info() {
    printf '==> %s\n' "$*"
}

command_exists() {
    command -v "$1" >/dev/null 2>&1
}

usage() {
    sed -n '2,/^set -Eeuo pipefail$/p' "$0" | sed '$d; s/^# \{0,1\}//'
}

on_error() {
    local exit_code=$?
    printf 'ERROR: %s failed at line %s with exit code %s.\n' \
        "$SCRIPT_NAME" "${BASH_LINENO[0]:-unknown}" "$exit_code" >&2
    exit "$exit_code"
}
trap on_error ERR

if [[ "${1:-}" == "--help" || "${1:-}" == "-h" ]]; then
    usage
    exit 0
fi

[[ $# -eq 0 ]] || die "Unknown positional argument: $1. Use --help for usage."
[[ -f "$POM_FILE" ]] || die "pom.xml was not found. Run this script from the Maven project root."

AUTO_INSTALL_BUILD_DEPS="${AUTO_INSTALL_BUILD_DEPS:-0}"
SKIP_TESTS="${SKIP_TESTS:-1}"
JAVA_DEP="${JAVA_DEP:-runtime}"
INCLUDE_PROJECT_DEPS="${INCLUDE_PROJECT_DEPS:-1}"
ALLOW_NATIVE_DEPS="${ALLOW_NATIVE_DEPS:-0}"
KEEP_RPMBUILD_TREE="${KEEP_RPMBUILD_TREE:-0}"
SIGN_RPM="${SIGN_RPM:-0}"
MAIN_CLASS="${MAIN_CLASS:-com.asbestosstar.crashdetector.app.CrashDetectorApp}"
RPM_LICENSE="${RPM_LICENSE:-Public Domain}"
RPM_SUMMARY="${RPM_SUMMARY:-CrashDetector JVM crash analysis application}"
OUTPUT_DIR="${OUTPUT_DIR:-target/rpm}"

for boolean_name in \
    AUTO_INSTALL_BUILD_DEPS SKIP_TESTS INCLUDE_PROJECT_DEPS \
    ALLOW_NATIVE_DEPS KEEP_RPMBUILD_TREE SIGN_RPM
do
    boolean_value="${!boolean_name}"
    [[ "$boolean_value" == "0" || "$boolean_value" == "1" ]] \
        || die "$boolean_name must be 0 or 1, not: $boolean_value"
done

case "$JAVA_DEP" in
    runtime|devel|headless|generic|none) ;;
    *) die "JAVA_DEP must be runtime, devel, headless, generic, or none." ;;
esac

install_build_dependencies() {
    [[ "$AUTO_INSTALL_BUILD_DEPS" == "1" ]] || return 0

    command_exists dnf || die "AUTO_INSTALL_BUILD_DEPS=1 requires dnf."
    command_exists sudo || die "AUTO_INSTALL_BUILD_DEPS=1 requires sudo."

    info "Installing RHEL 9 build dependencies"
    sudo dnf install -y \
        rpm-build \
        maven \
        java-21-openjdk-devel \
        tar \
        gzip \
        findutils \
        coreutils \
        file
}

install_build_dependencies

for required_command in rpmbuild rpm tar gzip sed awk find install cp realpath readlink sort cut head tr grep date; do
    command_exists "$required_command" || die "Required command not found: $required_command"
done

if [[ -x "${PROJECT_ROOT}/mvnw" ]]; then
    MVN=("${PROJECT_ROOT}/mvnw")
elif command_exists mvn; then
    MVN=("$(command -v mvn)")
else
    die "Maven was not found. Install maven, provide ./mvnw, or use AUTO_INSTALL_BUILD_DEPS=1."
fi

find_java_21() {
    local candidate
    local -a candidates=()

    if [[ -n "${JAVA_HOME:-}" ]]; then
        candidates+=("${JAVA_HOME}/bin/java")
    fi

    candidates+=(
        "/usr/lib/jvm/java-21-openjdk/bin/java"
        "/usr/lib64/jvm/java-21-openjdk/bin/java"
    )

    if command_exists java; then
        candidates+=("$(command -v java)")
    fi

    for candidate in "${candidates[@]}"; do
        [[ -x "$candidate" ]] || continue

        local version_line version major
        version_line="$("$candidate" -version 2>&1 | head -n 1 || true)"
        version="$(printf '%s\n' "$version_line" | sed -n 's/.*version "\([^"]*\)".*/\1/p')"

        if [[ -z "$version" ]]; then
            version="$(printf '%s\n' "$version_line" | awk '{print $2}' | tr -d '"')"
        fi

        major="$(printf '%s\n' "$version" | awk -F'[._+-]' '{ if ($1 == "1") print $2; else print $1 }')"

        if [[ "$major" == "21" ]]; then
            printf '%s\n' "$candidate"
            return 0
        fi
    done

    return 1
}

BUILD_JAVA="$(find_java_21)" \
    || die "Java 21 was not found. Install java-21-openjdk-devel or use AUTO_INSTALL_BUILD_DEPS=1."

export JAVA_HOME
JAVA_HOME="$(cd "$(dirname "$BUILD_JAVA")/.." && pwd -P)"
export PATH="${JAVA_HOME}/bin:${PATH}"

info "Using build Java: $("$BUILD_JAVA" -version 2>&1 | head -n 1)"

maven_eval() {
    local expression="$1"
    local value

    value="$("${MVN[@]}" -q -DforceStdout help:evaluate \
        "-Dexpression=${expression}" 2>/dev/null | tail -n 1)" || return 1

    [[ -n "$value" && "$value" != "null object or invalid expression" ]] || return 1
    printf '%s\n' "$value"
}

MAVEN_ARTIFACT_ID="$(maven_eval project.artifactId)" \
    || die "Could not read project.artifactId from pom.xml."
MAVEN_VERSION="$(maven_eval project.version)" \
    || die "Could not read project.version from pom.xml."
MAVEN_PACKAGING="$(maven_eval project.packaging || printf 'jar')"
MAVEN_FINAL_NAME="$(maven_eval project.build.finalName || printf '%s-%s' "$MAVEN_ARTIFACT_ID" "$MAVEN_VERSION")"
MAVEN_PROJECT_URL="$(maven_eval project.url || true)"

[[ "$MAIN_CLASS" =~ ^[A-Za-z_$][A-Za-z0-9_$]*(\.[A-Za-z_$][A-Za-z0-9_$]*)+$ ]] \
    || die "MAIN_CLASS is not a valid fully qualified Java class name: $MAIN_CLASS"

[[ "$MAVEN_PACKAGING" == "jar" ]] \
    || die "This script packages Maven JAR projects; project.packaging is '$MAVEN_PACKAGING'."

normalise_package_name() {
    printf '%s' "$1" \
        | tr '[:upper:]' '[:lower:]' \
        | sed \
            -e 's/[^a-z0-9+._-]/-/g' \
            -e 's/--*/-/g' \
            -e 's/^[._+-]*//' \
            -e 's/[._+-]*$//'
}

normalise_rpm_component() {
    printf '%s' "$1" \
        | sed \
            -e 's/[^A-Za-z0-9._+]/./g' \
            -e 's/\.\.*/./g' \
            -e 's/^[._+]*//' \
            -e 's/[._+]*$//'
}

DEFAULT_PACKAGE_NAME="$(normalise_package_name "$MAVEN_ARTIFACT_ID")"
PACKAGE_NAME="${PACKAGE_NAME:-$DEFAULT_PACKAGE_NAME}"
PACKAGE_NAME="$(normalise_package_name "$PACKAGE_NAME")"
[[ -n "$PACKAGE_NAME" ]] || die "The RPM package name became empty after normalisation."

if [[ -z "${RPM_VERSION:-}" ]]; then
    RPM_VERSION_SOURCE="${MAVEN_VERSION%-SNAPSHOT}"
    RPM_VERSION_SOURCE="${RPM_VERSION_SOURCE%-snapshot}"
    RPM_VERSION="$(normalise_rpm_component "$RPM_VERSION_SOURCE")"
fi
[[ -n "$RPM_VERSION" ]] || die "The RPM version became empty after normalisation."

if [[ -z "${RPM_RELEASE:-}" ]]; then
    if [[ "$MAVEN_VERSION" == *-SNAPSHOT || "$MAVEN_VERSION" == *-snapshot ]]; then
        RPM_RELEASE="0.1.snapshot"
    else
        RPM_RELEASE="1"
    fi
fi
RPM_RELEASE="$(normalise_rpm_component "$RPM_RELEASE")"
[[ -n "$RPM_RELEASE" ]] || die "The RPM release became empty after normalisation."

RPM_URL="${RPM_URL:-${MAVEN_PROJECT_URL:-https://pagure.io/CrashDetectorMC}}"

validate_single_line_spec_value() {
    local field_name="$1"
    local field_value="$2"

    [[ "$field_value" != *$'\n'* && "$field_value" != *$'\r'* ]] \
        || die "$field_name must be a single line."
}

validate_single_line_spec_value RPM_SUMMARY "$RPM_SUMMARY"
validate_single_line_spec_value RPM_LICENSE "$RPM_LICENSE"
validate_single_line_spec_value RPM_URL "$RPM_URL"

# Escape literal percent characters so rpmbuild does not interpret them as macros.
RPM_SUMMARY_SPEC="${RPM_SUMMARY//%/%%}"
RPM_LICENSE_SPEC="${RPM_LICENSE//%/%%}"
RPM_URL_SPEC="${RPM_URL//%/%%}"

COMMAND_NAME="${COMMAND_NAME:-$PACKAGE_NAME}"
COMMAND_NAME="$(normalise_package_name "$COMMAND_NAME")"
[[ -n "$COMMAND_NAME" ]] || die "COMMAND_NAME became empty after normalisation."

if [[ "$SKIP_TESTS" == "1" ]]; then
    MAVEN_TEST_ARGS=(-DskipTests)
else
    MAVEN_TEST_ARGS=()
fi

EXTRA_MAVEN_ARGS=()
if [[ -n "${MAVEN_ARGS:-}" ]]; then
    # Intended for ordinary unquoted Maven switches such as:
    # MAVEN_ARGS="-Pdev -Dsome.property=value"
    read -r -a EXTRA_MAVEN_ARGS <<< "$MAVEN_ARGS"
fi

info "Building Maven project"
"${MVN[@]}" clean package "${MAVEN_TEST_ARGS[@]}" "${EXTRA_MAVEN_ARGS[@]}"

MAIN_JAR="${PROJECT_ROOT}/target/${MAVEN_FINAL_NAME}.jar"

if [[ ! -f "$MAIN_JAR" ]]; then
    mapfile -t jar_candidates < <(
        find "${PROJECT_ROOT}/target" -maxdepth 1 -type f -name '*.jar' \
            ! -name '*-sources.jar' \
            ! -name '*-javadoc.jar' \
            ! -name 'original-*.jar' \
            -printf '%s\t%p\n' \
            | sort -nr \
            | cut -f2-
    )

    [[ ${#jar_candidates[@]} -gt 0 ]] \
        || die "Maven completed, but no packageable JAR was found in target/."

    MAIN_JAR="${jar_candidates[0]}"
    warn "Expected target/${MAVEN_FINAL_NAME}.jar was absent; using ${MAIN_JAR#"$PROJECT_ROOT"/}."
fi

[[ -s "$MAIN_JAR" ]] || die "The selected JAR is empty: $MAIN_JAR"

OUTPUT_DIR_ABS="${OUTPUT_DIR}"
if [[ "$OUTPUT_DIR_ABS" != /* ]]; then
    OUTPUT_DIR_ABS="${PROJECT_ROOT}/${OUTPUT_DIR_ABS}"
fi
mkdir -p "$OUTPUT_DIR_ABS"

RPMBUILD_ROOT="${PROJECT_ROOT}/target/rpmbuild-${PACKAGE_NAME}"
rm -rf "$RPMBUILD_ROOT"
mkdir -p \
    "$RPMBUILD_ROOT/BUILD" \
    "$RPMBUILD_ROOT/BUILDROOT" \
    "$RPMBUILD_ROOT/RPMS" \
    "$RPMBUILD_ROOT/SOURCES" \
    "$RPMBUILD_ROOT/SPECS" \
    "$RPMBUILD_ROOT/SRPMS"

cleanup() {
    if [[ "$KEEP_RPMBUILD_TREE" != "1" ]]; then
        rm -rf "$RPMBUILD_ROOT"
    else
        info "Keeping RPM build tree: $RPMBUILD_ROOT"
    fi
}
trap cleanup EXIT

SOURCE_JAR="${RPMBUILD_ROOT}/SOURCES/${PACKAGE_NAME}.jar"
SOURCE_LAUNCHER="${RPMBUILD_ROOT}/SOURCES/${COMMAND_NAME}"
SOURCE_DEPS="${RPMBUILD_ROOT}/SOURCES/${PACKAGE_NAME}-deps.tar.gz"
SPEC_FILE="${RPMBUILD_ROOT}/SPECS/${PACKAGE_NAME}.spec"

install -m 0644 "$MAIN_JAR" "$SOURCE_JAR"

DEPS_STAGE="${RPMBUILD_ROOT}/deps-stage"
mkdir -p "${DEPS_STAGE}/deps"

validate_project_deps() {
    local deps_dir="${PROJECT_ROOT}/deps"
    [[ "$INCLUDE_PROJECT_DEPS" == "1" && -d "$deps_dir" ]] || return 0

    info "Validating project deps/ directory"

    while IFS= read -r -d '' symlink; do
        local target resolved
        target="$(readlink "$symlink")"

        [[ "$target" != /* ]] \
            || die "Absolute symlink is not allowed in deps/: ${symlink#"$PROJECT_ROOT"/} -> $target"

        resolved="$(realpath -m "$(dirname "$symlink")/$target")"

        case "$resolved" in
            "$deps_dir"|"$deps_dir"/*) ;;
            *) die "Symlink escapes deps/: ${symlink#"$PROJECT_ROOT"/} -> $target" ;;
        esac
    done < <(find "$deps_dir" -type l -print0)

    if command_exists file; then
        local native_found=0
        while IFS= read -r -d '' dependency_file; do
            if file -b "$dependency_file" | grep -q 'ELF '; then
                warn "Native ELF dependency found: ${dependency_file#"$PROJECT_ROOT"/}"
                native_found=1
            fi
        done < <(find "$deps_dir" -type f -print0)

        if [[ "$native_found" == "1" && "$ALLOW_NATIVE_DEPS" != "1" ]]; then
            die "Native ELF files reduce cross-distribution portability. Remove them or set ALLOW_NATIVE_DEPS=1."
        fi
    else
        if find "$deps_dir" -type f \( -name '*.so' -o -name '*.so.*' \) -print -quit | grep -q .; then
            [[ "$ALLOW_NATIVE_DEPS" == "1" ]] \
                || die "Possible native libraries found in deps/. Install file(1) for exact detection or set ALLOW_NATIVE_DEPS=1."
        fi
    fi

    cp -a "${deps_dir}/." "${DEPS_STAGE}/deps/"
}

validate_project_deps

tar \
    --sort=name \
    --owner=0 \
    --group=0 \
    --numeric-owner \
    --mtime="@${SOURCE_DATE_EPOCH:-0}" \
    -C "$DEPS_STAGE" \
    -czf "$SOURCE_DEPS" \
    deps

if [[ "$ALLOW_NATIVE_DEPS" == "1" ]]; then
    RPM_BUILD_ARCH="$(rpm --eval '%{_arch}')"
    warn "Building architecture-specific RPM '$RPM_BUILD_ARCH'; portability depends on the bundled native files."
else
    RPM_BUILD_ARCH="noarch"
fi

cat > "$SOURCE_LAUNCHER" <<LAUNCHER
#!/bin/sh
set -eu

APP_HOME="/usr/lib/${PACKAGE_NAME}"
MAIN_CLASS="${MAIN_CLASS}"

if [ -n "\${JAVA_HOME:-}" ] && [ -x "\${JAVA_HOME}/bin/java" ]; then
    JAVA_BIN="\${JAVA_HOME}/bin/java"
else
    JAVA_BIN="\$(command -v java 2>/dev/null || true)"
fi

if [ -z "\${JAVA_BIN}" ] || [ ! -x "\${JAVA_BIN}" ]; then
    echo "${PACKAGE_NAME}: Java was not found. Install Java 21 or set JAVA_HOME." >&2
    exit 69
fi

VERSION_LINE="\$("\${JAVA_BIN}" -version 2>&1 | head -n 1 || true)"
JAVA_VERSION="\$(printf '%s\n' "\${VERSION_LINE}" | sed -n 's/.*version "\([^"]*\)".*/\1/p')"

if [ -z "\${JAVA_VERSION}" ]; then
    JAVA_VERSION="\$(printf '%s\n' "\${VERSION_LINE}" | awk '{print \$2}' | tr -d '"')"
fi

JAVA_MAJOR="\$(printf '%s\n' "\${JAVA_VERSION}" | awk -F'[._+-]' '{ if (\$1 == "1") print \$2; else print \$1 }')"

case "\${JAVA_MAJOR}" in
    ''|*[!0-9]*)
        echo "${PACKAGE_NAME}: could not determine the Java version from: \${VERSION_LINE}" >&2
        exit 69
        ;;
esac

if [ "\${JAVA_MAJOR}" -lt 21 ]; then
    echo "${PACKAGE_NAME}: Java 21 or newer is required; detected Java \${JAVA_VERSION}." >&2
    exit 69
fi

CLASSPATH="\${APP_HOME}/${PACKAGE_NAME}.jar:\${APP_HOME}/deps/*"

# JAVA_TOOL_OPTIONS and JDK_JAVA_OPTIONS remain available for JVM switches.
exec "\${JAVA_BIN}" -cp "\${CLASSPATH}" "\${MAIN_CLASS}" "\$@"
LAUNCHER
chmod 0755 "$SOURCE_LAUNCHER"

case "$JAVA_DEP" in
    runtime)
        RPM_REQUIRES='Requires: java-21-openjdk'
        ;;
    devel)
        RPM_REQUIRES='Requires: java-21-openjdk-devel'
        ;;
    headless)
        RPM_REQUIRES='Requires: java-21-openjdk-headless'
        ;;
    generic)
        RPM_REQUIRES='Requires: /usr/bin/java'
        ;;
    none)
        RPM_REQUIRES=''
        ;;
esac

cat > "$SPEC_FILE" <<SPEC
# Generated by ${SCRIPT_NAME}. Do not add a distro tag to Release.
%global debug_package %{nil}
%define _binary_payload w9.gzdio
%define _source_payload w9.gzdio

Name:           ${PACKAGE_NAME}
Version:        ${RPM_VERSION}
Release:        ${RPM_RELEASE}
Summary:        ${RPM_SUMMARY_SPEC}
License:        ${RPM_LICENSE_SPEC}
URL:            ${RPM_URL_SPEC}
BuildArch:      ${RPM_BUILD_ARCH}
AutoReqProv:    no
${RPM_REQUIRES}

Source0:        ${PACKAGE_NAME}.jar
Source1:        ${COMMAND_NAME}
Source2:        ${PACKAGE_NAME}-deps.tar.gz

%description
${RPM_SUMMARY_SPEC}.
This package contains the Maven-built application JAR, an executable launcher,
and optional project dependencies from the deps directory.

%prep
# The Maven project is built before rpmbuild; no source preparation is required.

%build
# The Maven project is built before rpmbuild; no compilation is performed here.

%install
rm -rf "%{buildroot}"
install -d "%{buildroot}%{_prefix}/lib/%{name}/deps"
install -m 0644 "%{SOURCE0}" "%{buildroot}%{_prefix}/lib/%{name}/%{name}.jar"
install -D -m 0755 "%{SOURCE1}" "%{buildroot}%{_bindir}/${COMMAND_NAME}"
tar -xzf "%{SOURCE2}" -C "%{buildroot}%{_prefix}/lib/%{name}"

%files
%{_bindir}/${COMMAND_NAME}
%dir %{_prefix}/lib/%{name}
%{_prefix}/lib/%{name}/%{name}.jar
%{_prefix}/lib/%{name}/deps

%changelog
* $(LC_ALL=C date '+%a %b %d %Y') ${USER:-RPM Builder} - ${RPM_VERSION}-${RPM_RELEASE}
- Build portable Java application RPM.
SPEC

info "Building RPM ${PACKAGE_NAME}-${RPM_VERSION}-${RPM_RELEASE}.${RPM_BUILD_ARCH}"
rpmbuild \
    -bb \
    --clean \
    --define "_topdir ${RPMBUILD_ROOT}" \
    "$SPEC_FILE"

mapfile -t built_rpms < <(
    find "${RPMBUILD_ROOT}/RPMS" -type f -name '*.rpm' -print | sort
)

[[ ${#built_rpms[@]} -gt 0 ]] || die "rpmbuild completed, but no binary RPM was produced."

for built_rpm in "${built_rpms[@]}"; do
    destination="${OUTPUT_DIR_ABS}/$(basename "$built_rpm")"
    install -m 0644 "$built_rpm" "$destination"

    info "Validating $(basename "$destination")"
    rpm -K "$destination"
    rpm -qpl "$destination" >/dev/null
    rpm -qp --requires "$destination" >/dev/null

    if [[ "$SIGN_RPM" == "1" ]]; then
        command_exists rpmsign || die "SIGN_RPM=1 requires rpmsign."
        rpmsign --addsign "$destination"
        rpm -K "$destination"
    fi

    printf '\nBuilt RPM:\n  %s\n\n' "$destination"
    printf 'Package metadata:\n'
    rpm -qpi "$destination"
    printf '\nRuntime requirements:\n'
    rpm -qp --requires "$destination" || true
done
