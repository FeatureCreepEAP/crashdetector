#!/bin/sh
set -eu

RAIZ=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
SALIDA="$RAIZ/mass_verificaciones"
JAR=${CRASHDETECTOR_JAR:-"$RAIZ/target/crashdetector-2.4.1.jar"}
JAVA_BIN=${JAVA:-java}

rm -rf -- "$SALIDA"
mkdir -p -- "$SALIDA/archivos" "$SALIDA/enlaces"
TAB=$(printf '\t')
printf 'tipo%snombre%sruta del informe%scódigo de salida%sduración en segundos\n' "$TAB" "$TAB" "$TAB" "$TAB" > "$SALIDA/resumen.tsv"
: > "$SALIDA/consola_masiva.log"

if [ ! -f "$JAR" ]; then
  printf '%s\n' "No existe el JAR: $JAR" | tee -a "$SALIDA/consola_masiva.log" >&2
  exit 1
fi

ESCAPAR=$(command -v python3 || true)
registrar() {
  tipo=$1
  nombre=$2
  informe=$3
  codigo=$4
  duracion=$5
  if [ -n "$ESCAPAR" ]; then
    nombre=$($ESCAPAR -c 'import sys; print(sys.argv[1].replace("\t", " ").replace("\n", " "))' "$nombre")
    informe=$($ESCAPAR -c 'import sys; print(sys.argv[1].replace("\t", " ").replace("\n", " "))' "$informe")
  fi
  printf '%s\t%s\t%s\t%s\t%s\n' "$tipo" "$nombre" "$informe" "$codigo" "$duracion" >> "$SALIDA/resumen.tsv"
}

validar_informe() {
  archivo=$1
  [ -f "$archivo" ] || return 1
  [ -s "$archivo" ] || return 1
  grep -qi '<html\|<body\|<!doctype' "$archivo"
}

fallo=0
for entrada in "$RAIZ"/mass_archivos/*; do
  [ -f "$entrada" ] || continue
  nombre=$(basename "$entrada")
  caso="$SALIDA/archivos/$nombre"
  mkdir -p -- "$caso"
  inicio=$(date +%s)
  set +e
  "$JAVA_BIN" -Djava.awt.headless=true -jar "$JAR" analizar "$entrada" >"$caso/salida_estandar.txt" 2>"$caso/salida_de_error.txt"
  codigo=$?
  set -e
  fin=$(date +%s)
  duracion=$((fin - inicio))
  cp "$caso/salida_de_error.txt" "$caso/consola_monitor.log"
  printf '%s\n' "$codigo" > "$caso/codigo_de_salida.txt"
  informe=$(awk 'NF {line=$0} END {print line}' "$caso/salida_estandar.txt")
  if [ "$codigo" -eq 0 ] && validar_informe "$informe"; then
    cp "$informe" "$caso/informe.html"
    informe="$caso/informe.html"
  else
    informe=
    fallo=1
  fi
  registrar archivos "$nombre" "$informe" "$codigo" "$duracion"
done

# Los separadores son Markdown completo, no un parser de URLs. Cada bloque se
# ejecuta en una JVM independiente para aislar el estado estático de EnlanceMD.
ENLACES="$SALIDA/.enlaces_divididos"
rm -rf -- "$ENLACES"
mkdir -p -- "$ENLACES"
awk -v directorio="$ENLACES" '
  BEGIN {
    numero = 1
    archivo = sprintf("%s/%06d.md", directorio, numero)
    printf "" > archivo
  }
  /^[[:space:]]*#####[#]*[[:space:]]*$/ {
    close(archivo)
    numero++
    archivo = sprintf("%s/%06d.md", directorio, numero)
    printf "" > archivo
    next
  }
  { print > archivo }
  END { close(archivo) }
' "$RAIZ/MASS_ENLACES.MD"

for entrada in "$ENLACES"/*.md; do
  [ -f "$entrada" ] || continue
  id=$(basename "$entrada" .md)
  caso="$SALIDA/enlaces/$id"
  mkdir -p -- "$caso"
  cp "$entrada" "$caso/entrada.md"
  inicio=$(date +%s)
  set +e
  "$JAVA_BIN" -Djava.awt.headless=true -Dcrashdetector.markdown.sin_red=true -jar "$JAR" analizar-markdown "$caso/entrada.md" >"$caso/salida_estandar.txt" 2>"$caso/salida_de_error.txt"
  codigo=$?
  set -e
  fin=$(date +%s)
  duracion=$((fin - inicio))
  cp "$caso/salida_de_error.txt" "$caso/consola_monitor.log"
  printf '%s\n' "$codigo" > "$caso/codigo_de_salida.txt"
  informe=$(awk 'NF {line=$0} END {print line}' "$caso/salida_estandar.txt")
  informe_copiado=
  if validar_informe "$informe"; then
    cp "$informe" "$caso/informe.html"
    informe_copiado="$caso/informe.html"
  fi
  case "$codigo" in
    0|2|3)
      ;;
    *)
      fallo=1
      ;;
  esac
  registrar enlaces "$id" "$informe_copiado" "$codigo" "$duracion"
done

rm -rf -- "$ENLACES"
printf '%s\n' "Los fixtures de archivos usan formato normal de Minecraft; los bloques Markdown se ejecutan con EnlanceMD en modo determinista sin red." >> "$SALIDA/consola_masiva.log"
printf '%s\n' "Los códigos 2/3 de Markdown representan ausencia de enlaces compatibles o fallos de descarga; no prueban disponibilidad de mclo.gs." >> "$SALIDA/consola_masiva.log"

exit "$fallo"
