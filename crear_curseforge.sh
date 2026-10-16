#!/usr/bin/env bash

set -euo pipefail

DIRECTORIO_TARGET="target"

PREFIJO_NUBE="com/asbestosstar/crashdetector/nube/"
PREFIJO_APP="com/asbestosstar/crashdetector/app/"

if [[ ! -d "$DIRECTORIO_TARGET" ]]; then
    echo "Error: no existe '$DIRECTORIO_TARGET'."
    exit 1
fi

command -v zip >/dev/null 2>&1 || {
    echo "Error: falta 'zip'."
    exit 1
}

command -v unzip >/dev/null 2>&1 || {
    echo "Error: falta 'unzip'."
    exit 1
}

encontrados=0
creados=0

while IFS= read -r -d '' jar_original; do
    encontrados=$((encontrados + 1))

    nombre="${jar_original%.jar}"
    jar_curseforge="${nombre}_curseforge.jar"

    echo "Procesando:"
    echo "  Original:   $jar_original"
    echo "  CurseForge: $jar_curseforge"

    cp -f -- "$jar_original" "$jar_curseforge"

    temporal="$(mktemp)"

    #
    # Encontrar todo lo que pertenezca a:
    #
    #   com/asbestosstar/crashdetector/nube/**
    #   com/asbestosstar/crashdetector/app/**
    #
    unzip -Z1 "$jar_curseforge" \
        | grep -E "^(${PREFIJO_NUBE}|${PREFIJO_APP})" \
        > "$temporal" || true

    if [[ -s "$temporal" ]]; then
        cantidad="$(wc -l < "$temporal" | tr -d ' ')"

        echo "  Encontradas $cantidad entradas para eliminar."

        while IFS= read -r entrada; do
            [[ -z "$entrada" ]] && continue
            zip -dq "$jar_curseforge" "$entrada"
        done < "$temporal"

        echo "  Eliminado completamente:"
        echo "    ${PREFIJO_NUBE}**"
        echo "    ${PREFIJO_APP}**"
    else
        echo "  Los paquetes excluidos no estaban presentes."
    fi

    rm -f "$temporal"

    #
    # Verificación final.
    #
    restantes="$(
        unzip -Z1 "$jar_curseforge" \
            | grep -E "^(${PREFIJO_NUBE}|${PREFIJO_APP})" || true
    )"

    if [[ -n "$restantes" ]]; then
        echo
        echo "ERROR: todavía existen entradas que deberían haberse eliminado:"
        printf '%s\n' "$restantes"

        rm -f -- "$jar_curseforge"
        exit 1
    fi

    creados=$((creados + 1))

    echo "  Verificación: paquetes excluidos ausentes."
    echo "  OK"
    echo

done < <(
    find "$DIRECTORIO_TARGET" \
        -maxdepth 1 \
        -type f \
        -name '*.jar' \
        ! -name '*-sources.jar' \
        ! -name '*-javadoc.jar' \
        ! -name '*_curseforge.jar' \
        -print0
)

if [[ "$encontrados" -eq 0 ]]; then
    echo "No se encontraron JAR normales en '$DIRECTORIO_TARGET'."
    exit 0
fi

echo "Terminado: se crearon $creados JAR para CurseForge."