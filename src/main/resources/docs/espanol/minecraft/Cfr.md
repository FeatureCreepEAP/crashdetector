# Documentación de la Integración CFR

## Descripción General

CrashDetector incluye un subsistema de descompilación CFR (Class File Reader) completamente integrado.

A diferencia de los frontends tradicionales de descompilación que ejecutan CFR contra un archivo JAR almacenado en disco, CrashDetector integra CFR directamente dentro de la infraestructura de ModsTree. Esto permite descompilar clases directamente desde mods cargados, JARs anidados, bibliotecas embebidas y otros contenedores sin necesidad de extraer archivos al disco.

El subsistema CFR consta de cuatro componentes principales:

* Descubrimiento de CFR
* Framework de Interfaz CFR
* Interfaz de Usuario CFR
* Motor de Descompilación en Memoria

---

# ¿Qué es CFR?

CFR (Class File Reader) es un descompilador de Java.

Convierte bytecode Java:

```text
com/example/MyClass.class
```

de vuelta a código fuente Java legible.

CrashDetector utiliza CFR para:

* Inspeccionar el interior de los mods
* Investigar fallos
* Analizar mixins
* Explorar clases desconocidas
* Examinar dependencias embebidas
* Ayudar en la depuración de conflictos de dependencias

---

# Arquitectura

La implementación de CFR está dividida en varias clases:

```text
BuscarParaCFR
        │
        ▼
     CfrBase
        │
        ▼
 CfrSakuraRiddle
        │
        ▼
  DescompilarCFR
        │
        ▼
     ModsTree
        │
        ▼
      API CFR
```

---

# Descubrimiento de la Instalación de CFR

CrashDetector busca automáticamente CFR.

El orden de búsqueda es:

## Directorio Universal de Dependencias

```text
~/crash_detector/deps
```

---

## Directorio CFR Heredado

```text
~/crash_detector/cfr
```

Si se encuentra, CrashDetector intenta migrar automáticamente CFR al directorio moderno de dependencias.

---

## Directorio de Dependencias de la Instancia

```text
<instancia>/deps
```

---

## Ubicaciones Comunes en Linux

Ejemplos:

```text
/usr/share/java/CFR/cfr.jar
/usr/share/java/cfr.jar
/opt/cfr/cfr.jar
/usr/local/lib/cfr.jar
```

---

# Instalación de CFR

Si no se encuentra CFR, CrashDetector muestra una interfaz gráfica explicando que CFR es necesario.

El diálogo de instalación proporciona:

## Descargar CFR

Descarga:

```text
https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar
```

---

## Abrir Directorio de Instalación

Abre:

```text
~/crash_detector/deps
```

permitiendo a los usuarios colocar CFR en la ubicación correcta.

---

# El Protocolo cfr://

CrashDetector introduce un protocolo personalizado:

```text
cfr://com.example.MyClass
```

Seleccionar uno de estos enlaces lanza el navegador CFR.

Ejemplo:

```text
cfr://net.minecraft.world.entity.Entity
```

El navegador automáticamente:

1. Abre la interfaz CFR.
2. Solicita la clase.
3. La descompila.
4. Muestra el código fuente Java resultante.

---

# El Navegador Sakura Riddle

La implementación predeterminada es:

```java
CfrSakuraRiddle
```

Se trata de un navegador gráfico de descompilación.

Características:

* Resaltado de sintaxis
* Personalización de temas
* Navegación entre clases
* Vista de código desplazable
* Panel de retrato de Sakura

---

# Personalización del Tema

El navegador CFR utiliza ConfigColor en lugar de colores codificados directamente.

Las categorías disponibles incluyen:

## Interfaz General

```text
tema.sakura.cfr.fondo
tema.sakura.cfr.texto
tema.sakura.cfr.borde
tema.sakura.cfr.fondo.retrato
```

---

## Resaltado de Sintaxis

```text
tema.sakura.cfr.codigo.palabra.clave
tema.sakura.cfr.codigo.cadena
tema.sakura.cfr.codigo.comentario
tema.sakura.cfr.codigo.numero
tema.sakura.cfr.codigo.tipo
tema.sakura.cfr.codigo.metodo
```

Estos colores se aplican mediante LectadorDeCodigo.

---

# Descompilación en Memoria

La característica más importante de la integración CFR de CrashDetector es que nunca requiere que las clases existan como archivos independientes.

En su lugar obtiene directamente los bytes de la clase desde ModsTree.

Ejemplo:

```java
byte[] bytes =
    Buscador.obtenerBytesDeClase(
        "net.minecraft.world.entity.Entity"
    );
```

La clase puede provenir de:

* Un mod Forge
* Un mod Fabric
* Un JAR anidado
* Una dependencia embebida
* Un plugin
* Un módulo de FeatureCreep

El usuario no necesita saber dónde existe físicamente la clase.

---

# Normalización de Nombres de Clase

Se aceptan varios formatos.

Formato Java:

```text
com.example.MyClass
```

---

Formato ASM:

```text
com/example/MyClass
```

---

Ruta de archivo .class:

```text
com/example/MyClass.class
```

---

Descriptor JVM:

```text
Lcom/example/MyClass;
```

Todos los formatos se normalizan automáticamente.

---

# Integración con ModsTree

CFR nunca escanea directamente archivos JAR.

En su lugar solicita los bytes de clase a ModsTree.

```java
Buscador.obtenerBytesDeClase(...)
```

Esto proporciona varias ventajas.

---

## Soporte para JARs Embebidos

Los descompiladores tradicionales frecuentemente tienen problemas con estructuras como:

```text
mod.jar
 └── libs/
     └── dependency.jar
```

ModsTree ya indexa archivos anidados.

CFR obtiene automáticamente soporte para ellos.

---

## Almacenamiento Anónimo

Las clases pueden provenir de:

```text
mod.jar!/library.jar!/another.jar!/Class.class
```

y CFR sigue recibiéndolas como arreglos normales de bytes.

---

## Sin Archivos Temporales

Nada se extrae al disco.

Esto reduce:

* E/S de disco
* Uso de almacenamiento temporal
* Requisitos de limpieza

---

# Resolución de Dependencias

Cuando CFR encuentra referencias a clases adicionales:

```java
SomeOtherClass.doSomething();
```

solicita dichas clases a su implementación personalizada de ClassFileSource.

CrashDetector las resuelve mediante ModsTree.

```java
Buscador.obtenerBytesDeClase(
    "SomeOtherClass"
);
```

Esto convierte efectivamente a ModsTree en un classpath virtual.

---

# Classpath Virtual

Uno de los aspectos más singulares de esta implementación es que ModsTree se comporta como un classpath virtual completo de Java.

Para CFR, todas las clases parecen disponibles aunque físicamente puedan existir en:

* Diferentes mods
* Diferentes archivos
* Diferentes archivos anidados
* Diferentes cargadores

Esto mejora significativamente la precisión de la descompilación.

---

# Opciones de CFR

Las opciones actuales incluyen:

```text
hideutf=true
comments=false
```

Estas configuraciones reducen el ruido de salida y mejoran la legibilidad.

Se pueden añadir fácilmente opciones adicionales mediante:

```java
Map<String,String> opciones
```

dentro de DescompilarCFR.

---

# Relación con el Análisis de Bytecode

CrashDetector contiene dos sistemas de análisis independientes.

## Análisis ASM

Proporcionado por:

```java
AnalizadorBytecodeASM
```

Utilizado para:

* Mixins
* Referencias de métodos
* Referencias de campos
* Constantes
* Grafos de dependencias

---

## Análisis CFR

Proporcionado por:

```java
DescompilarCFR
```

Utilizado para:

* Visualización de código fuente legible
* Investigación manual
* Ingeniería inversa
* Diagnóstico para desarrolladores

---

# Flujo de Trabajo Típico

Un flujo de diagnóstico típico es:

1. CrashDetector identifica una clase problemática.
2. ModsTree localiza el mod propietario.
3. ASM extrae referencias y metadatos.
4. El usuario pulsa un hipervínculo cfr://.
5. CFR solicita los bytes de la clase a ModsTree.
6. Se reconstruye el código fuente.
7. El usuario inspecciona la implementación.

Esto permite investigar problemas rápidamente sin abrir herramientas externas.

---

# Posibilidades Futuras

Debido a que CFR ya recibe las clases directamente desde ModsTree, futuras integraciones podrían incluir:

* Navegación clicable entre clases
* Visualización de grafos de llamadas de métodos
* Navegación hacia objetivos de mixins
* Árboles de dependencias descompiladas
* Explicaciones automáticas de conflictos
* Vistas lado a lado de bytecode y código fuente
* Búsqueda de referencias cruzadas
* Indexación de símbolos
* Búsqueda integrada de código fuente

La arquitectura actual ya proporciona gran parte de la infraestructura necesaria para estas características avanzadas.

---

# Resumen

El subsistema CFR de CrashDetector es mucho más que un simple lanzador de descompilador.

Al combinar CFR con ModsTree, el sistema crea efectivamente un classpath virtual de todo el modpack capaz de:

* Descompilar clases directamente desde memoria
* Recorrer archivos anidados
* Resolver dependencias automáticamente
* Navegar por clases mediante enlaces cfr://
* Integrarse con análisis de mixins y bytecode

Esto convierte a CFR en una potente herramienta de desarrollo y depuración estrechamente integrada con el ecosistema de CrashDetector en lugar de una utilidad externa independiente.

