#!/usr/bin/env bash

set -euo pipefail

DIRECTORIO_TARGET="target"
PREFIJO_NUBE="com/asbestosstar/crashdetector/nube/"

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

    unzip -Z1 "$jar_curseforge" \
        | grep "^${PREFIJO_NUBE}" \
        > "$temporal" || true

    if [[ -s "$temporal" ]]; then
        cantidad="$(wc -l < "$temporal" | tr -d ' ')"

        echo "  Encontradas $cantidad entradas de .nube."

        #
        # Eliminar cada entrada utilizando exactamente el nombre
        # que existe dentro del JAR.
        #
        while IFS= read -r entrada; do
            [[ -z "$entrada" ]] && continue
            zip -dq "$jar_curseforge" "$entrada"
        done < "$temporal"

        echo "  Eliminado completamente: ${PREFIJO_NUBE}**"
    else
        echo "  El paquete .nube no estaba presente."
    fi

    rm -f "$temporal"

    #
    # Verificación final.
    #
    restantes="$(
        unzip -Z1 "$jar_curseforge" \
            | grep "^${PREFIJO_NUBE}" || true
    )"

    if [[ -n "$restantes" ]]; then
        echo
        echo "ERROR: todavía existen entradas de .nube:"
        printf '%s\n' "$restantes"

        rm -f -- "$jar_curseforge"
        exit 1
    fi

    creados=$((creados + 1))

    echo "  Verificación: .nube no existe en el JAR."
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