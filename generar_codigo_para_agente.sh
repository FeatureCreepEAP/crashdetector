#!/usr/bin/env sh
set -eu

mkdir -p ./ia

{
  find ./src/main/java/com/asbestosstar/crashdetector/analizador/ -type f -name "*.java" | sort | while IFS= read -r archivo; do
    printf '\n===== %s =====\n' "$archivo"
    cat "$archivo"
    printf '\n'
  done

  archivo_chino="./src/main/java/com/asbestosstar/crashdetector/idioma/Chino.java"
  if [ -f "$archivo_chino" ]; then
    printf '\n===== %s =====\n' "$archivo_chino"
    cat "$archivo_chino"
    printf '\n'
  fi
} > ./ia/codigo_para_agente.txt
