#!/bin/bash

# Nombre del archivo de salida donde se combinarán todos los archivos .java
ARCHIVO_SALIDA="todos_codigo.txt"

# Verificar si existe la carpeta 'src'
if [ ! -d "src" ]; then
    echo "❌ Error: No se encontró la carpeta 'src'. Asegúrate de ejecutar este script desde la raíz del proyecto Maven."
    exit 1
fi

# Vaciar o crear el archivo de salida
> "$ARCHIVO_SALIDA"

# Recorrer recursivamente todos los archivos .java en la carpeta 'src'
echo "🔍 Buscando archivos .java en la carpeta 'src'..."
while IFS= read -r -d '' archivo; do
    # Añadir un encabezado para identificar cada archivo
    echo "// ==============================================" >> "$ARCHIVO_SALIDA"
    echo "// Archivo: $archivo" >> "$ARCHIVO_SALIDA"
    echo "// ==============================================" >> "$ARCHIVO_SALIDA"
    echo "" >> "$ARCHIVO_SALIDA"
    
    # Añadir el contenido del archivo .java
    cat "$archivo" >> "$ARCHIVO_SALIDA"
    
    # Añadir una línea en blanco al final para separar archivos
    echo -e "\n" >> "$ARCHIVO_SALIDA"
done < <(find src -type f -name "*.java" -print0)

# Confirmar la operación
echo "✅ Todos los archivos .java han sido combinados en '$ARCHIVO_SALIDA'."