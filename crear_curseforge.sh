#!/usr/bin/env bash
#
# crear_curseforge.sh
#
# Crea versiones para CurseForge de los JAR generados por Maven.
#
# Para cada archivo .jar situado directamente en ./target:
#   - ignora los JAR de fuentes (*-sources.jar)
#   - ignora copias ya creadas (*_curseforge.jar)
#   - crea una copia con "_curseforge" antes de ".jar"
#   - elimina de ESA COPIA únicamente:
#         com/asbestosstar/crashdetector/nube/**
#
# El JAR original no se modifica.
#
# Ejemplo:
#   target/crashdetector-1.2.3.jar
#       ->
#   target/crashdetector-1.2.3_curseforge.jar
#
# Uso:
#   chmod +x crear_curseforge.sh
#   ./crear_curseforge.sh
#
# Normalmente se ejecuta desde la raíz del proyecto Maven, después de:
#   mvn package
#

set -euo pipefail

DIRECTORIO_TARGET="target"
PAQUETE_NUBE="com/asbestosstar/crashdetector/nube/*"

# Comprobar que estamos en un proyecto que ya tiene el directorio target.
if [[ ! -d "$DIRECTORIO_TARGET" ]]; then
    echo "Error: no existe el directorio '$DIRECTORIO_TARGET'."
    echo "Ejecute este script desde la raíz del proyecto Maven después de 'mvn package'."
    exit 1
fi

# 'zip -d' permite eliminar entradas del JAR sin extraer y reconstruir
# manualmente todo el archivo.
if ! command -v zip >/dev/null 2>&1; then
    echo "Error: no se encontró el comando 'zip'."
    echo "Instale la utilidad zip y vuelva a ejecutar este script."
    exit 1
fi

if ! command -v unzip >/dev/null 2>&1; then
    echo "Error: no se encontró el comando 'unzip'."
    echo "Instale la utilidad unzip y vuelva a ejecutar este script."
    exit 1
fi

encontrados=0
creados=0

# Usamos find con -print0 para soportar correctamente nombres con espacios.
while IFS= read -r -d '' jar_original; do
    encontrados=$((encontrados + 1))

    nombre="${jar_original%.jar}"
    jar_curseforge="${nombre}_curseforge.jar"

    echo "Procesando:"
    echo "  Original:   $jar_original"
    echo "  CurseForge: $jar_curseforge"

    # Crear primero una copia exacta del JAR normal.
    cp -f -- "$jar_original" "$jar_curseforge"

    # Comprobar si el paquete .nube está realmente presente.
    # Si está presente, eliminarlo únicamente de la copia CurseForge.
    if unzip -Z1 "$jar_curseforge" 2>/dev/null \
        | grep -q '^com/asbestosstar/crashdetector/nube/'; then

        # zip devuelve código distinto de cero cuando el patrón no encuentra
        # entradas. Como ya comprobamos que existen, aquí cualquier error real
        # debe detener el script.
        zip -dq "$jar_curseforge" "$PAQUETE_NUBE"

        echo "  Eliminado: com/asbestosstar/crashdetector/nube/**"
    else
        echo "  El paquete .nube no estaba presente; la copia queda sin cambios."
    fi

    # Verificación final: una versión CurseForge nunca debe contener .nube.
    if unzip -Z1 "$jar_curseforge" 2>/dev/null \
        | grep -q '^com/asbestosstar/crashdetector/nube/'; then
        echo "Error: todavía existen archivos del paquete .nube en:"
        echo "  $jar_curseforge"
        rm -f -- "$jar_curseforge"
        exit 1
    fi

    creados=$((creados + 1))
    echo "  OK"
    echo

done < <(
    find "$DIRECTORIO_TARGET" \
        -maxdepth 1 \
        -type f \
        -name '*.jar' \
        ! -name '*-sources.jar' \
        ! -name '*_curseforge.jar' \
        -print0
)

if [[ "$encontrados" -eq 0 ]]; then
    echo "No se encontraron JAR normales en '$DIRECTORIO_TARGET'."
    echo "Se ignoran los archivos '*-sources.jar' y '*_curseforge.jar'."
    exit 0
fi

echo "Terminado: se crearon $creados JAR para CurseForge."
