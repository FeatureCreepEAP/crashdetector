# Documentación del Mapa de Dependencias

## Descripción General

El Mapa de Dependencias es una herramienta avanzada de análisis y visualización incluida con CrashDetector.

Su propósito es descubrir, visualizar e investigar relaciones entre mods basándose en referencias reales de bytecode, en lugar de depender únicamente de metadatos declarados. Mientras que la mayoría de los visores de dependencias se basan solo en información encontrada en archivos como `mods.toml`, `fabric.mod.json` o `plugin.yml`, el Mapa de Dependencias examina el código compilado en sí para determinar qué mods dependen realmente unos de otros.

Esto permite que la herramienta revele dependencias ocultas, relaciones no documentadas, uso de APIs, dependencias circulares y desajustes de versiones que de otro modo permanecerían invisibles.

El Mapa de Dependencias es particularmente útil para:

* Diagnóstico de dependencias faltantes
* Investigación de compatibilidad
* Mantenimiento de modpacks
* Análisis de uso de APIs
* Ingeniería inversa
* Inspección de bibliotecas embebidas
* Investigación de mixins
* Análisis de módulos FeatureCreep

---

# Arquitectura

El Mapa de Dependencias está construido sobre varios subsistemas de CrashDetector.

```text
ModsTree
    │
    ▼
Análisis de Bytecode ASM
    │
    ▼
Motor de Análisis de Dependencias
    │
    ▼
Grafo de Dependencias
    │
    ▼
Integración CFR
```

A diferencia de las herramientas tradicionales de dependencias, las relaciones se derivan de referencias reales de bytecode descubiertas durante el análisis.

---

# Cómo se Descubren las Dependencias

El Mapa de Dependencias no se basa en metadatos de dependencia declarados.

En su lugar, se analiza cada clase dentro de cada mod.

Por ejemplo:

```java
SomeOtherModClass.doSomething();
```

o

```java
SomeOtherModClass.someField
```

Cuando una clase perteneciente al Mod A referencia una clase perteneciente al Mod B, el sistema registra una dependencia.

```text
Mod A → Mod B
```

Esto indica que Mod A referencia directamente código proporcionado por Mod B.

---

# Fuentes de Dependencias

El analizador inspecciona:

## Referencias de Métodos

```java
SomeClass.someMethod()
```

## Referencias de Campos

```java
SomeClass.someField
```

## Referencias Entre Mods

Solo se cuentan las referencias que cruzan límites entre mods.

Las referencias contenidas completamente dentro de un solo mod se ignoran.

```text
Mod A → Mod A
```

Tales referencias internas no se consideran dependencias.

---

# Descubrimiento Recursivo de Mods

El análisis de dependencias incluye:

* Mods estándar
* Mods anidados
* Bibliotecas jar-in-jar
* Dependencias embebidas
* Módulos internos

El sistema recorre recursivamente todos los contenedores descubiertos por ModsTree.

```java
mods_en_mods()
```

Esto permite que dependencias ocultas dentro de archivos anidados se analicen junto con mods ordinarios.

---

# Análisis Paralelo

Los modpacks grandes pueden contener:

* Cientos de mods
* Cientos de miles de clases
* Millones de referencias

Para mejorar el rendimiento, el análisis de dependencias se realiza en paralelo.

El proceso:

1. Construye un índice global de propiedad de clases.
2. Crea un pool de trabajadores.
3. Analiza múltiples mods simultáneamente.
4. Fusiona los resultados en el mapa de dependencias final.

Esto reduce significativamente el tiempo de análisis en sistemas modernos con múltiples núcleos.

---

# Grafo de Dependencias

La visualización principal es un grafo.

Cada nodo representa un mod.

Cada conexión representa una dependencia descubierta.

```text
Mod A ─────► Mod B
```

significa:

```text
Mod A referencia clases pertenecientes al Mod B
```

---

# Tamaño de los Nodos

El tamaño de los nodos refleja su importancia dentro del ecosistema de dependencias.

Los nodos más grandes generalmente indican:

* Más mods dependientes
* Más dependencias
* Mayor influencia dentro del modpack

Frameworks y bibliotecas suelen aparecer como los nodos más grandes.

Ejemplos pueden incluir:

```text
Minecraft
Forge
NeoForge
Fabric API
Architectury
FeatureCreep Core
```

---

# Etiquetas de Nodos

Los nodos muestran:

```text
Nombre del Mod (Dependientes)
```

Ejemplo:

```text
Architectury (37)
```

indica que treinta y siete mods dependen de Architectury.

---

# Navegación

El grafo soporta varias funciones de navegación.

## Zoom

Usa la rueda del ratón para acercar y alejar.

## Desplazamiento

Haz clic y arrastra el espacio vacío para moverte por el grafo.

## Selección

Hacer clic en un nodo lo selecciona y muestra información adicional.

## Doble Clic

Hacer doble clic en un mod abre automáticamente sus referencias de dependencia.

---

# Vista de Árbol de Dependencias

La vista de árbol proporciona una representación estructurada de las relaciones.

Cada mod muestra:

```text
Dependencias
Dependientes
```

Ejemplo:

```text
Create
 ├─ Flywheel
 ├─ Forge

Dependientes
 ├─ Steam Rails
 ├─ Create Additions
 └─ Additional Create Content
```

---

# Clasificación de Dependencias

Los mods se ordenan automáticamente según:

1. Número de dependientes.
2. Número de dependencias.
3. Orden alfabético.

Esto coloca naturalmente bibliotecas y frameworks importantes cerca de la parte superior.

---

# Inspección de Referencias

El Mapa de Dependencias puede explicar con precisión por qué existe una dependencia.

Ejemplo:

```text
com.example.MyClass.method()
    →
net.example.OtherClass.someMethod()
```

Para cada referencia, el sistema registra:

* Mod de origen
* Mod de destino
* Clase de origen
* Método de origen
* Clase de destino
* Método de destino
* Descriptor JVM

Esto permite investigar directamente las relaciones de dependencia.

---

# Navegador de Referencias Entre Mods

Cualquier enlace de dependencia puede expandirse para revelar todas las referencias responsables de crear la relación.

```text
Mod A → Mod B
```

El navegador puede responder preguntas como:

```text
¿Por qué este mod depende de ese mod?
```

o

```text
¿Se puede eliminar esta dependencia de forma segura?
```

---

# Integración CFR

El Mapa de Dependencias se integra directamente con el navegador CFR.

Seleccionar una referencia permite descompilar inmediatamente la clase de origen.

Flujo:

```text
Referencia de Dependencia
        ↓
Clase de Origen
        ↓
Navegador CFR
        ↓
Código Fuente Java
```

Las clases se abren mediante el protocolo personalizado:

```text
cfr://fully.qualified.ClassName
```

lo que permite una investigación rápida sin salir de CrashDetector.

---

# Menús Contextuales

Hacer clic derecho en las entradas proporciona funcionalidad adicional.

## Ver Referencias

Muestra todas las referencias entre dos mods.

## Descompilar

Abre la clase seleccionada en el navegador CFR.

---

# Detalles de Dependencia

Seleccionar una dependencia muestra:

```text
Mod de Origen
Mod de Destino
Número de Referencias
```

proporcionando un resumen conciso de la relación.

---

# Vista de Dependientes

La vista de Dependientes responde una pregunta importante:

```text
¿Qué mods dependen de este mod?
```

Ejemplo:

```text
Curios
 ├─ Mod A
 ├─ Mod B
 ├─ Mod C
 └─ Mod D
```

Esto es particularmente útil al considerar eliminar o reemplazar una biblioteca.

---

# Dependencias Circulares

Las dependencias mutuas se detectan automáticamente.

Ejemplo:

```text
Mod A → Mod B
Mod B → Mod A
```

El grafo las renderiza como enlaces bidireccionales.

Las dependencias circulares frecuentemente revelan:

* Acoplamiento fuerte
* Problemas arquitectónicos
* Decisiones de diseño heredadas
* Límites de API inadecuados

---

# Detección de Dependencias No Alineadas

Una de las funciones más potentes del Mapa de Dependencias es la Detección de Dependencias No Alineadas.

Este sistema busca referencias hacia paquetes que ya no existen dentro de una dependencia.

Ejemplo:

Mod base:

```text
com.example.api
```

Mod dependiente:

```java
com.example.api.SomeClass
```

Si `SomeClass` ya no existe dentro del mod base, la dependencia se considera no alineada.

---

# Qué Detectan las Dependencias No Alineadas

Esta función puede identificar:

* APIs eliminadas
* Versiones incorrectas
* Ports incompletos
* Clases faltantes
* Bibliotecas sombreadas rotas
* Migraciones parciales

Estos problemas frecuentemente causan fallos, `ClassNotFoundException`s y problemas de compatibilidad.

---

# Flujo de Análisis No Alineado

Selecciona:

1. Un mod base.
2. Un paquete.

Luego pulsa:

```text
Comprobar Dependencias No Alineadas
```

El analizador inspeccionará cada dependiente y reportará referencias inválidas.

---

# Resultados No Alineados

Cada resultado incluye:

```text
Mod Dependiente
Clase Faltante
Método de Origen
Paquete Referenciado
```

Esto facilita mucho el diagnóstico de desajustes de versión.

---

# Funciones de Privacidad

El Mapa de Dependencias utiliza:

```java
ubicacion_para_publicar()
```

en lugar de rutas reales del sistema de archivos.

Esto permite compartir capturas de pantalla, reportes y solicitudes de soporte públicamente sin exponer estructuras personales de directorios.

---

# Características de Rendimiento

El Mapa de Dependencias depende en gran medida de:

```java
Buscador.cargarYPrecargarClasesEnCache()
```

que precarga metadatos de clases en memoria.

Beneficios:

* Generación de grafos más rápida
* Navegación de referencias más rápida
* Análisis de dependencias más rápido
* Navegación CFR más rápida
* Menor acceso a archivos comprimidos

---

# Usos Típicos

## Investigación de Dependencias Faltantes

Determinar exactamente qué mod requiere una dependencia faltante.

## Planificación de Eliminación de Mods

Identificar todos los dependientes antes de eliminar un mod.

## Análisis de APIs

Descubrir qué mods consumen una API concreta.

## Investigación de Compatibilidad

Investigar interacciones inesperadas entre mods.

## Investigación de Mixins

Rastrear referencias relacionadas con objetivos de mixins.

## Ingeniería Inversa

Navegar grandes bases de código sin salir de CrashDetector.

## Desarrollo de FeatureCreep

Analizar relaciones entre módulos en despliegues complejos de FeatureCreep.

---

# Resumen

El Mapa de Dependencias es el sistema de análisis de dependencias a nivel de bytecode de CrashDetector.

En lugar de depender de metadatos declarados, descubre relaciones a partir de referencias reales en el código, produciendo una representación muy precisa de las interacciones entre mods.

Combinado con ModsTree, análisis ASM e integración CFR, el Mapa de Dependencias proporciona:

* Grafos interactivos de dependencias
* Árboles de dependencias
* Inspección de referencias
* Detección de dependencias circulares
* Descubrimiento de dependencias no alineadas
* Descompilación directa de código fuente

lo que lo convierte en una de las herramientas de diagnóstico e investigación más potentes disponibles dentro del ecosistema de CrashDetector.

