# Documentación de Historial de Mods

## Descripción General

Historial de Mods es el sistema integrado de seguimiento de cambios de modpacks de CrashDetector.

Su propósito es registrar instantáneas de la colección de mods de un usuario a lo largo del tiempo y permitir la comparación directa entre diferentes estados de un modpack.

En lugar de intentar recordar manualmente qué mods fueron añadidos o eliminados entre fallos, actualizaciones o ejecuciones exitosas, Historial de Mods mantiene registros históricos que pueden compararse instantáneamente.

El sistema es particularmente útil para:

* Investigación de fallos
* Mantenimiento de modpacks
* Análisis de regresiones
* Seguimiento de actualizaciones
* Solicitudes de soporte
* Investigación de compatibilidad
* Auditoría histórica

Al comparar estados anteriores de un modpack, los usuarios pueden determinar rápidamente qué modificaciones pueden haber introducido un problema.

---

# Arquitectura

Historial de Mods está construido alrededor de un modelo simple de instantáneas históricas.

```text
Modpack Actual
       │
       ▼
Instantánea Histórica
       │
       ▼
Archivo Histórico
       │
       ▼
Motor de Comparación
       │
       ▼
Reporte de Diferencias
```

El sistema almacena listas de mods instalados y permite comparaciones entre cualquier par de estados registrados.

---

# Interfaces Disponibles

Actualmente Historial de Mods incluye dos implementaciones gráficas.

## Oficina de Clío

```java
ClioOfficeGUI
```

Una interfaz altamente tematizada inspirada en una oficina de archivos históricos.

Características:

* Arte personalizado
* Diseño proporcional
* Temática de archivo histórico
* Listas de comparación desplazables
* Gestión de instantáneas

---

## Interfaz Heredada

```java
HistoriaModsGUILegacy
```

La implementación original.

Características:

* Diseño tradicional de escritorio
* Diseño ligero
* Presentación simplificada
* Uso mínimo de recursos

Ambas interfaces comparten el mismo motor de comparación subyacente.

---

# Archivos Históricos

Los registros históricos se almacenan en:

```text
history_mods/
```

Los archivos se clasifican en tres tipos.

---

## Registros de Éxito

```text
000451.success
```

Representan un estado de ejecución exitosa.

---

## Registros de Fallo

```text
000452.failure
```

Representan un fallo o una ejecución fallida.

---

## Instantáneas

```text
000453.snapshot
```

Representan una instantánea creada manualmente.

---

# Propósito del Análisis Histórico

El objetivo principal es responder preguntas como:

```text
¿Qué cambió antes del fallo?
```

```text
¿Qué mods fueron añadidos?
```

```text
¿Qué mods fueron eliminados?
```

```text
¿Cuándo apareció el problema por primera vez?
```

Sin Historial de Mods, estas investigaciones pueden resultar extremadamente difíciles en modpacks grandes.

---

# Descubrimiento de Archivos Históricos

Cuando la interfaz se abre, CrashDetector escanea el directorio de historial.

Los registros se descubren y ordenan automáticamente.

Los archivos se ordenan según su identificador numérico.

Ejemplo:

```text
000450.success
000451.success
000452.failure
000453.snapshot
```

Las entradas más recientes aparecen primero.

---

# Sistema de Selección de Doble Columna

Historial de Mods utiliza dos columnas de selección independientes.

```text
Registro Izquierdo
        │
        ▼
Motor de Comparación
        ▲
        │
Registro Derecho
```

Esto permite comparar cualquier par de estados históricos.

---

# Tipos de Estado Histórico

Cada registro muestra su estado.

---

## Éxito

```text
(Éxito)
```

Indica una ejecución completada correctamente.

---

## Fallo

```text
(Fallo)
```

Indica una ejecución que terminó sin éxito.

---

## Instantánea

```text
(Instantánea)
```

Indica un estado preservado manualmente.

---

# Creación de Instantáneas

Una de las características más útiles es la creación manual de instantáneas.

El usuario selecciona un registro y pulsa:

```text
Instantánea
```

El sistema crea una copia preservada.

Ejemplo:

```text
000452.failure
        │
        ▼
000452.snapshot
```

Si ya existe una instantánea, se añade un sufijo incremental.

Ejemplo:

```text
000452_01.snapshot
000452_02.snapshot
```

Esto evita sobrescrituras accidentales.

---

# Por Qué Son Útiles las Instantáneas

Las instantáneas permiten preservar estados importantes.

Ejemplos:

* Modpacks estables
* Configuraciones conocidas como funcionales
* Copias de seguridad previas a actualizaciones
* Puntos de investigación
* Entornos de prueba

Proporcionan una forma conveniente de marcar momentos significativos en la historia de un modpack.

---

# Proceso de Comparación

El flujo de comparación es sencillo.

```text
Seleccionar Registro A
         │
Seleccionar Registro B
         │
         ▼
Comparar
         │
         ▼
Reporte de Diferencias
```

El reporte muestra todos los cambios detectados.

---

# Normalización de Mods

Antes de la comparación, los nombres de los mods se normalizan.

El sistema:

1. Extrae el nombre del archivo.
2. Elimina extensiones.
3. Convierte el nombre a minúsculas.
4. Compara identificadores normalizados.

Ejemplo:

```text
ExampleMod-1.0.jar
```

se convierte en:

```text
examplemod-1.0
```

Esto mejora la precisión de las comparaciones.

---

# Manejo de Rutas Relativas

Historial de Mods intenta almacenar y mostrar rutas relativas cuando es posible.

Esto proporciona varias ventajas:

* Reportes más limpios
* Mayor facilidad para compartir
* Menor exposición de información específica de la máquina
* Mejor legibilidad

Si no puede generarse una ruta relativa, se utiliza el nombre del archivo.

---

# Detección de Diferencias

El motor de comparación identifica dos categorías principales.

---

## Mods Añadidos

Mods presentes en el registro más reciente pero ausentes en el más antiguo.

Ejemplo:

```text
+ mods/new_mod.jar
```

---

## Mods Eliminados

Mods presentes en el registro más antiguo pero ausentes en el más reciente.

Ejemplo:

```text
- mods/old_mod.jar
```

---

# Reporte de Diferencias

Los resultados se muestran en un reporte basado en HTML.

Ejemplo:

```text
Comparando:
000451.success
con
000452.failure

+ mods/newlibrary.jar
+ mods/newfeature.jar

- mods/removedmod.jar
```

Esto proporciona una visión inmediata de los cambios.

---

# Codificación por Colores

Los reportes de diferencias utilizan colores para facilitar la lectura.

---

## Mods Añadidos

Se muestran utilizando el color configurado para adiciones.

Normalmente verde.

```text
+ ExampleMod.jar
```

---

## Mods Eliminados

Se muestran utilizando el color configurado para eliminaciones.

Normalmente rojo.

```text
- OldMod.jar
```

---

# Sin Cambios Detectados

Si dos registros contienen conjuntos idénticos de mods, el reporte muestra:

```text
No se detectaron cambios.
```

Esto puede resultar especialmente valioso durante investigaciones de fallos.

A veces ocurre un fallo incluso cuando la lista de mods no ha cambiado, indicando que otro factor puede ser responsable.

---

# Tema Oficina de Clío

La implementación Oficina de Clío presenta Historial de Mods como un archivo histórico.

La interfaz incluye:

* Arte temático de archivo
* Estilo decorativo de pergamino
* Listas de registros históricos
* Texto descriptivo dorado
* Diseño escalable

El diseño está construido utilizando coordenadas proporcionales, permitiendo que la interfaz se adapte al tamaño de la ventana sin perder su apariencia.

---

# Sistema de Diseño Adaptable

Oficina de Clío utiliza rectángulos normalizados.

```text
x
y
ancho
alto
```

expresados como proporciones en lugar de coordenadas fijas.

Esto permite que los elementos de la interfaz mantengan sus posiciones previstas independientemente del tamaño de la ventana.

---

# Presentación del Archivo Histórico

Los registros se muestran mediante etiquetas simplificadas.

Ejemplo:

```text
451 (Éxito)
452 (Fallo)
453 (Instantánea)
```

Los ceros iniciales y las extensiones de archivo se eliminan para mejorar la legibilidad.

---

# Configuración de Temas

Ambas interfaces soportan personalización visual mediante ConfigColor.

Ejemplos incluyen:

## Colores de Estado

```text
Éxito
Fallo
Instantánea
```

---

## Colores de Resultados

```text
Mods Añadidos
Mods Eliminados
```

---

## Colores de la Interfaz

```text
Paneles
Listas
Botones
Bordes
Texto
```

No existen colores visibles para el usuario codificados directamente.

---

# Manejo de Errores

Historial de Mods maneja problemas comunes de forma elegante.

Ejemplos:

* Archivos faltantes
* Registros corruptos
* Rutas inválidas
* Fallos de comparación
* Fallos de creación de instantáneas

Siempre que sea posible, se muestra información útil al usuario.

---

# Usos Típicos

## Investigación de Fallos

Determinar qué mods cambiaron antes de que ocurriera un fallo.

---

## Análisis de Actualizaciones

Identificar modificaciones introducidas durante actualizaciones.

---

## Seguimiento de Regresiones

Encontrar cuándo un modpack previamente funcional se volvió inestable.

---

## Solicitudes de Soporte

Proporcionar evidencia histórica al solicitar ayuda.

---

## Mantenimiento de Modpacks

Monitorizar cambios durante el desarrollo a largo plazo de un modpack.

---

## Pruebas

Crear instantáneas antes de experimentar con nuevos mods.

---

# Relación con Otras Herramientas de CrashDetector

Historial de Mods trabaja junto a varios sistemas de CrashDetector.

## Mapa de Dependencias

Investigar nuevas dependencias descubiertas durante las comparaciones.

## SpongeMixin Explorer

Analizar mixins introducidos por mods recientemente añadidos.

## Guard

Inspeccionar nuevos mods en busca de problemas de seguridad.

## Navegador CFR

Descompilar clases recién introducidas para investigaciones adicionales.

En conjunto, estas herramientas permiten investigar con mucho más detalle los cambios descubiertos por Historial de Mods.

---

# Resumen

Historial de Mods es el sistema de seguimiento y comparación histórica de modpacks de CrashDetector.

Al mantener registros de estados anteriores de mods, permitir la creación manual de instantáneas y proporcionar reportes detallados de diferencias, permite a los usuarios identificar con precisión cómo ha cambiado un modpack a lo largo del tiempo.

Las características principales incluyen:

* Registros históricos de mods
* Creación de instantáneas
* Detección de mods añadidos
* Detección de mods eliminados
* Comparación mediante rutas relativas
* Reportes HTML de diferencias
* Interfaz de archivo Oficina de Clío
* Interfaz heredada
* Personalización de temas

lo que lo convierte en una herramienta de diagnóstico invaluable para mantenedores de modpacks, desarrolladores, equipos de soporte y usuarios avanzados.

