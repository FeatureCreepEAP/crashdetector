# Documentación del Escáner de Mods MCreator

## Descripción General

El Escáner de Mods MCreator es una utilidad especializada de CrashDetector diseñada para identificar mods creados usando MCreator.

Muchos modpacks contienen una mezcla de mods escritos manualmente, mods de biblioteca y mods generados mediante MCreator. Aunque MCreator en sí mismo es una herramienta legítima de desarrollo, los mods generados suelen presentar patrones distintivos que pueden ser útiles al diagnosticar fallos, investigar problemas de compatibilidad o analizar la composición de un modpack.

El Escáner de Mods MCreator proporciona una interfaz simple para descubrir y listar todos los mods generados por MCreator detectados dentro del modpack cargado actualmente.

El escáner es particularmente útil para:

* Investigación de fallos
* Análisis de compatibilidad
* Auditoría de modpacks
* Solicitudes de soporte
* Control de calidad
* Investigación de desarrollo
* Mantenimiento de modpacks

---

# Arquitectura

El escáner está construido sobre varios subsistemas de CrashDetector.

```text
ModsTree
    │
    ▼
Escáner MCreator
    │
    ▼
Procesamiento de Resultados
    │
    ▼
Interfaz Gráfica
```

La interfaz gráfica está separada de la lógica de escaneo, permitiendo que múltiples temas visuales compartan la misma funcionalidad subyacente.

---

# Framework de Interfaz de Usuario

La implementación central es:

```java
EscanerMCreatorGUI
```

Esta clase abstracta contiene:

* Lógica de escaneo
* Manejo de resultados
* Soporte de localización
* Integración de temas
* Soporte de configuración

Las implementaciones concretas proporcionan la apariencia visual mientras reutilizan la misma funcionalidad de escaneo.

---

# Tema Rosemi LoveLock

La implementación predeterminada es:

```java
EscanerMCreatorGUIRosemiLoveLock
```

Este tema proporciona:

* Arte de Rosemi LoveLock
* Colores configurables
* Paneles redondeados
* Vistas de resultados estilizadas
* Controles conscientes del tema

Todas las configuraciones de apariencia están controladas mediante `ConfigColor`.

No hay colores codificados de forma fija en la experiencia del usuario.

---

# Propósito del Escáner

El escáner intenta identificar mods que parecen haber sido generados usando MCreator.

Esta información puede ser útil porque:

* Los mods generados suelen compartir estructuras comunes.
* Ciertos problemas de compatibilidad se observan con más frecuencia en mods generados.
* Las solicitudes de soporte pueden beneficiarse de saber si un mod se originó en MCreator.
* Los modpacks grandes pueden contener cantidades significativas de mods generados que de otro modo serían difíciles de identificar.

El escáner es puramente informativo.

No clasifica automáticamente los mods MCreator como problemáticos.

---

# Flujo de Escaneo

El flujo de trabajo es intencionalmente simple.

```text
Pulsar Escanear
      ↓
Analizar Mods Cargados
      ↓
Identificar Mods MCreator
      ↓
Generar Resultados
      ↓
Mostrar Reporte
```

El proceso se ejecuta completamente en segundo plano.

---

# Procesamiento en Segundo Plano

El escaneo se realiza usando:

```java
SwingWorker
```

Esto garantiza que la interfaz permanezca responsiva durante el análisis.

Mientras se escanea:

* El área de resultados muestra información de progreso.
* El botón de escaneo se deshabilita.
* Los mensajes de estado se actualizan.

Una vez que el escaneo termina:

* Se muestran los resultados.
* Los controles se vuelven a habilitar.
* Se muestra el estado de finalización.

---

# Generación de Resultados

El escáner obtiene resultados mediante:

```java
EscanerMCreator.obtainerMCreatorMods()
```

La información devuelta se limpia y se formatea antes de su presentación.

El escáner elimina automáticamente encabezados duplicados y formato innecesario antes de mostrar los resultados al usuario.

---

# Visualización de Resultados

Los resultados se muestran dentro de un área dedicada de reporte.

El reporte normalmente contiene:

```text
Resultados del Escaneo MCreator

Mod Detectado A
Mod Detectado B
Mod Detectado C
```

seguido de un mensaje de finalización.

Si no se detectan mods MCreator, el escáner muestra un mensaje apropiado en su lugar.

---

# Manejo de Errores

Si ocurre un error durante el escaneo, el escáner muestra:

```text
Error Durante el Escaneo
```

junto con el mensaje de la excepción subyacente.

La interfaz se recupera automáticamente y vuelve a habilitar el botón de escaneo.

Esto permite a los usuarios intentar otro escaneo sin reiniciar CrashDetector.

---

# Indicadores de Estado

El escáner soporta tres estados principales.

## Cargando

```text
Cargando...
```

Se muestra mientras el análisis está en ejecución.

---

## Completado

```text
Completado
```

Se muestra cuando el escaneo termina correctamente.

---

## Error

```text
Error
```

Se muestra si ocurre una excepción.

---

# Soporte de Localización

Todo el texto visible para el usuario se obtiene mediante:

```java
MonitorDePID.idioma
```

Esto significa:

* No hay cadenas visibles para el usuario codificadas directamente.
* Los paquetes de idioma pueden sobrescribir texto.
* Se soportan traducciones comunitarias.

El escáner se adapta automáticamente al idioma activo.

---

# Configuración del Tema

La implementación Rosemi LoveLock expone numerosas configuraciones de apariencia.

Ejemplos incluyen:

## Colores de Ventana

```text
Fondo de Ventana
Texto Principal
Fondo de Resultados
Color de Estado
```

## Colores de Botones

```text
Fondo del Botón
Texto del Botón
```

## Paneles de Interfaz

```text
Tarjeta de Descripción
Tarjeta de Resultados
Colores de Borde
```

Todas las configuraciones pueden modificarse mediante el sistema de configuración de CrashDetector.

---

# Arte Decorativo

El tema Rosemi LoveLock soporta arte decorativo.

La interfaz intenta cargar:

```text
imagenes/rosemi.png
```

La imagen es:

* Cargada de forma asíncrona
* Escalada automáticamente
* Mostrada dentro del panel de descripción

Si la imagen no puede encontrarse, la interfaz continúa funcionando normalmente.

---

# Separación de Apariencia

El escáner sigue el patrón arquitectónico estándar de CrashDetector.

La clase abstracta contiene:

```text
Lógica
Escaneo
Manejo de Resultados
Tareas en Segundo Plano
```

La implementación concreta contiene:

```text
Colores
Fuentes
Diseño
Arte
Estilo Visual
```

Esta separación permite crear nuevos temas sin modificar el comportamiento de escaneo.

---

# Características de Rendimiento

El escáner es ligero comparado con herramientas como:

* ModsTree
* Mapa de Dependencias
* SpongeMixin Explorer

El escáner no realiza por sí mismo análisis profundo de bytecode.

En su lugar, depende del subsistema subyacente de detección de MCreator para generar resultados de forma eficiente.

Esto hace que los tiempos de escaneo sean relativamente cortos incluso en modpacks grandes.

---

# Usos Típicos

## Solicitudes de Soporte

Determinar si hay mods generados por MCreator presentes en un pack.

---

## Auditoría de Modpacks

Identificar todos los mods generados dentro de una distribución.

---

## Investigación de Fallos

Recopilar información adicional al analizar fallos.

---

## Investigación de Compatibilidad

Investigar si mods generados pueden estar involucrados en conflictos.

---

## Investigación de Desarrollo

Estudiar la prevalencia de mods generados dentro de una colección.

---

# Relación con Otras Herramientas de CrashDetector

El Escáner de Mods MCreator complementa varias otras utilidades de CrashDetector.

## ModsTree

Proporciona descubrimiento de mods e indexación de metadatos.

## Mapa de Dependencias

Proporciona análisis de relaciones de dependencia.

## SpongeMixin Explorer

Proporciona herramientas de investigación de mixins.

## Navegador CFR

Proporciona descompilación de código fuente.

El escáner proporciona información de identificación de alto nivel que luego puede investigarse más a fondo usando estas herramientas avanzadas.

---

# Resumen

El Escáner de Mods MCreator es una utilidad ligera de diagnóstico diseñada para identificar mods generados por MCreator dentro de un modpack.

Al combinar análisis en segundo plano, soporte de localización, temas configurables e integración con el ecosistema más amplio de CrashDetector, proporciona una forma simple y efectiva de auditar modpacks y recopilar información diagnóstica útil.

Las características principales incluyen:

* Detección de mods MCreator
* Escaneo en segundo plano
* Texto de interfaz localizado
* Personalización de temas
* Tema visual Rosemi LoveLock
* Operación ligera
* Integración con el ecosistema de análisis de CrashDetector

lo que la convierte en una herramienta valiosa tanto para personal de soporte, mantenedores de modpacks y desarrolladores.

