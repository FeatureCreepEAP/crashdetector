# Documentación de SpongeMixin Explorer

## Descripción General

SpongeMixin Explorer es la herramienta dedicada de visualización e investigación de sistemas SpongePowered Mixin de CrashDetector.

Su propósito es descubrir, organizar, inspeccionar y analizar mixins presentes en todo un modpack, incluyendo mixins ubicados dentro de mods anidados y bibliotecas embebidas.

A diferencia de las herramientas tradicionales de depuración de mixins, SpongeMixin Explorer está estrechamente integrado con ModsTree, CFR y el framework de análisis de bytecode de CrashDetector, permitiendo a los usuarios moverse sin problemas entre metadatos de mixins, análisis de conflictos y descompilación de código fuente.

La herramienta es particularmente útil para:

* Investigación de conflictos de mixins
* Investigación de compatibilidad de mods
* Ingeniería inversa
* Depuración de modpacks
* Análisis de inyecciones
* Descubrimiento de objetivos
* Auditoría de SpongeMixin
* Desarrollo y mantenimiento

---

# Arquitectura

SpongeMixin Explorer combina varios subsistemas de CrashDetector.

```text
ModsTree
    │
    ▼
Descubrimiento de Mixins
    │
    ▼
Mixin Explorer
    │
    ▼
Análisis de Conflictos
    │
    ▼
Integración CFR
```

Los mixins se descubren directamente mediante análisis de bytecode, no solo a partir de archivos de configuración.

---

# Interfaz de Usuario

La implementación predeterminada es:

```java
MixinsGUIChiarru
```

La interfaz se divide en varias áreas:

## Selección de Mods

Muestra todos los mods que contienen mixins.

Los usuarios pueden:

* Ver todos los mods
* Filtrar por un mod específico
* Actualizar la base de datos de mixins

---

## Árbol de Mixins

La vista de árbol muestra:

```text
Mod
 └─ Mixin
      ├─ Objetivos
      ├─ Métodos
      └─ Campos
```

Esto proporciona una vista estructurada de cada mixin descubierto.

---

## Panel de Detalles

Seleccionar cualquier elemento muestra información detallada.

Según el nodo seleccionado, la información puede incluir:

* Ubicación del mod
* Objetivos del mixin
* Métodos inyectados
* Campos shadow
* Conflictos potenciales
* Código fuente descompilado

---

## Panel del Personaje

El tema predeterminado Chiarru incluye:

* Arte de Chiarru
* Arte de GoMix
* Colores conscientes del tema
* Configuraciones de apariencia modificables

Todos los colores se controlan mediante `ConfigColor` en lugar de valores codificados directamente.

---

# Proceso de Carga

Cuando se abre el explorador, CrashDetector realiza una precarga completa.

```java
Buscador.cargarYPrecargarClasesEnCache()
```

Esto permite que el explorador funcione eficientemente incluso en modpacks grandes.

Durante la carga:

1. Se descubren los mods.
2. Se indexan las clases.
3. Se identifican los mixins.
4. Se analizan los objetivos.
5. Se construye la estructura del árbol.

Se muestra una superposición de carga mientras este proceso está en ejecución.

---

# Descubrimiento de Mixins

El explorador escanea cada clase descubierta.

Para cada clase:

```java
mod.esClaseMixin(...)
```

se utiliza para determinar si la clase representa un SpongeMixin.

Solo las clases mixin válidas se añaden a la interfaz.

---

# Descubrimiento Recursivo

El descubrimiento de mixins es recursivo.

El explorador busca en:

* Mods estándar
* Mods anidados
* Bibliotecas jar-in-jar
* Dependencias embebidas

utilizando:

```java
mods_en_mods()
```

Esto garantiza que los mixins ocultos no sean pasados por alto.

---

# Vista de Mods

El nivel más alto del árbol muestra mods que contienen al menos un mixin.

Seleccionar un mod muestra:

```text
Ubicación
Cantidad de Mixins
```

Esto proporciona una visión rápida del uso de mixins dentro de un mod específico.

---

# Vista de Mixin

Seleccionar un mixin muestra:

```text
Nombre de Clase
Mod Propietario
Objetivos
Métodos
Campos
```

El explorador obtiene información de la estructura `MixinInfo` almacenada.

Ejemplo:

```text
Clase Mixin:
com.example.ExampleMixin

Objetivos:
net.minecraft.world.entity.Entity

Métodos:
injectMovement(...)
overwriteTick(...)

Campos:
shadowHealth
shadowPosition
```

---

# Análisis de Objetivos

Cada objetivo de mixin se muestra debajo del nodo del mixin.

Ejemplo:

```text
Objetivos
 ├─ net.minecraft.world.entity.Entity
 ├─ net.minecraft.world.entity.Player
 └─ net.minecraft.server.level.ServerPlayer
```

Seleccionar un objetivo muestra:

* Nombre del objetivo
* Número de conflictos
* Mixins relacionados

---

# Análisis de Métodos

Los métodos de mixin se muestran por separado.

Cada método incluye:

```text
Nombre del Método
Descriptor
Métodos Objetivo
```

Ejemplo:

```text
injectTick(Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;)V
```

Seleccionar un método muestra todos los objetivos asociados.

---

# Análisis de Campos

Los campos de mixin se agrupan debajo de la categoría Campos.

La información incluye:

```text
Nombre del Campo
Descriptor
```

Ejemplo:

```text
shadowHealth I
```

Esto es particularmente útil al investigar campos shadow y mixins accessor.

---

# Análisis de Conflictos

Una de las características más importantes de SpongeMixin Explorer es la detección de conflictos.

El explorador puede identificar situaciones donde múltiples mixins afectan el mismo objetivo.

Ejemplo:

```text
Mixin A
        ┐
        ├─► Entity.tick()
        │
Mixin B
        ┘
```

Tales situaciones frecuentemente contribuyen a problemas de compatibilidad.

---

# Conflictos de Objetivo

El análisis de conflictos de objetivo busca todos los mixins que apuntan a la misma clase.

Ejemplo:

```text
Objetivo:
net.minecraft.world.entity.Entity
```

Resultados:

```text
Mod A :: ExampleMixin
Mod B :: AnotherMixin
Mod C :: EntityMixin
```

Esto ayuda a identificar modificaciones superpuestas.

---

# Conflictos de Método

El explorador también puede buscar mixins que apuntan al mismo método.

Ejemplo:

```text
Entity.tick()
```

Los resultados incluyen:

* Mod
* Clase mixin
* Nombre del método
* Descriptor

Esto permite investigar detalladamente colisiones de inyección.

---

# Conflictos de Campo

El análisis de campos busca campos shadow coincidentes.

Ejemplo:

```text
health I
```

Los resultados muestran todos los mixins que interactúan con el mismo campo.

Aunque no todas las coincidencias representan conflictos reales, suelen señalar áreas que requieren investigación.

---

# Navegador de Conflictos

El menú contextual proporciona:

```text
Buscar Conflictos
```

Seleccionar esta opción genera un árbol dedicado de conflictos.

Los resultados se agrupan por:

* Objetivo
* Método
* Campo

Esto facilita investigar grandes colecciones de mixins superpuestos.

---

# Menú Contextual

Hacer clic derecho en una entrada proporciona varias acciones.

## Buscar Conflictos

Analiza el elemento seleccionado en busca de posibles conflictos de mixins.

## Descompilar Selección

Abre la clase correspondiente dentro del navegador CFR.

---

# Integración CFR

SpongeMixin Explorer se integra directamente con el subsistema CFR de CrashDetector.

Seleccionar:

```text
Descompilar Selección
```

hace que la clase se pase a:

```java
CfrBase.descompilarClase(...)
```

El código fuente resultante se muestra inmediatamente.

Esto permite a los desarrolladores inspeccionar:

* Lógica de inyección
* Métodos objetivo
* Accessors
* Overwrites
* Campos shadow

sin salir de CrashDetector.

---

# Iconos

El explorador utiliza iconos dedicados para diferentes tipos de nodos.

| Tipo de Nodo | Significado                |
| ------------ | -------------------------- |
| Mod          | Contenedor de mod          |
| Mixin        | Clase mixin                |
| Objetivo     | Clase objetivo             |
| Método       | Método de inyección        |
| Campo        | Campo shadow o relacionado |
| Conflicto    | Conflicto potencial        |

Esto mejora la navegación dentro de árboles grandes.

---

# Sistema de Temas

La implementación Chiarru expone amplia personalización.

Las categorías configurables incluyen:

## Interfaz General

```text
Fondo
Paneles
Texto
Vista de Árbol
Colores de Selección
```

## Visualización de Código

```text
Palabras Clave
Cadenas
Comentarios
Números
Tipos
Métodos
```

Todos los colores se controlan mediante el sistema de configuración y pueden modificarse sin recompilar CrashDetector.

---

# Rendimiento

El explorador está diseñado para modpacks grandes.

Las mejoras de rendimiento incluyen:

* Caché de clases
* Indexación recursiva
* Generación perezosa de detalles
* Carga en segundo plano
* Procesamiento basado en SwingWorker

Esto mantiene la interfaz responsiva mientras analiza miles de clases.

---

# Usos Típicos

## Investigación de Conflictos de Mixins

Determinar qué mods modifican el mismo código del juego.

## Investigación de Compatibilidad

Identificar incompatibilidades potenciales antes de que causen fallos.

## Ingeniería Inversa

Inspeccionar el comportamiento de mixins sin abrir un IDE externo.

## Desarrollo de Mods

Revisar inyecciones y objetivos durante el desarrollo.

## Mantenimiento de Modpacks

Auditar los mixins presentes en todo un pack.

## Análisis de Fallos

Determinar si un fallo puede estar relacionado con mixins en conflicto.

---

# Relación con Otras Herramientas de CrashDetector

SpongeMixin Explorer trabaja estrechamente con:

## ModsTree

Proporciona descubrimiento de clases e información de propiedad.

## Mapa de Dependencias

Proporciona análisis de dependencias entre mods.

## Navegador CFR

Proporciona descompilación de código fuente.

## Análisis ASM

Proporciona la inspección de bytecode subyacente usada para identificar mixins y sus objetivos.

En conjunto, estas herramientas forman un ecosistema completo para investigar interacciones entre mods.

---

# Resumen

SpongeMixin Explorer es el entorno dedicado de análisis de SpongePowered Mixin de CrashDetector.

Al combinar descubrimiento recursivo de mods, análisis de bytecode, detección de conflictos e integración con CFR, proporciona una interfaz potente para comprender cómo interactúan los mixins en todo un modpack.

Las características principales incluyen:

* Descubrimiento recursivo de mixins
* Inspección de objetivos
* Análisis de métodos
* Análisis de campos
* Detección de conflictos
* Descompilación de código fuente
* Personalización de temas
* Integración con ModsTree y CFR

lo que lo convierte en una de las herramientas de investigación de mixins más completas disponibles dentro del ecosistema de CrashDetector.

