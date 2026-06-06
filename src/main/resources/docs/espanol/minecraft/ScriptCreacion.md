# Documentación de Script IDE

## Descripción General

Script IDE es un entorno de scripting integrado dentro de CrashDetector.

Su objetivo es proporcionar un editor ligero pero potente para modding, automatización, datapacks, resource packs, formatos de configuración, lenguajes de scripting y contenido Datafied de FeatureCreep.

El IDE soporta:

* Resaltado de sintaxis
* Gramáticas TextMate
* Reglas de comentarios y corchetes específicas de cada lenguaje
* IntelliSense
* Servidores de lenguaje locales
* Servidores LSP externos
* Diagnósticos
* Autocompletado
* Descarga automática de dependencias
* Múltiples tipos de proyecto

La implementación es intencionalmente modular:

* `ScriptIDEGUI` proporciona la funcionalidad común del editor.
* `ScriptIntellisense` proporciona inteligencia de lenguaje.
* `ScriptIntellisenseLocal` proporciona autocompletado de respaldo.
* `ScriptIntellisenseLsp4j` proporciona soporte para clientes LSP.
* Los servidores de lenguaje internos implementan `ScriptIntellisense`.
* Las gramáticas TextMate proporcionan coloreado de sintaxis independientemente de IntelliSense.

---

# Dependencias Requeridas

El IDE descarga automáticamente:

```text
org.eclipse.lsp4j:org.eclipse.lsp4j
org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc
org.jboss:jboss-dmr
```

Estas dependencias se descargan en:

```text
~/crash_detector/deps
```

LSP4J proporciona la implementación del cliente.

JBoss DMR es requerido por los proyectos FeatureCreep Datafied Content.

---

# Soporte para TextMate

El IDE soporta gramáticas TextMate.

Las gramáticas descargadas se almacenan en:

```text
~/crash_detector/scriptide/textmate/
```

Cada tipo de proyecto puede proporcionar:

```text
syntax.tmLanguage.json
language-configuration.json
```

El IDE analiza:

* comentarios
* corchetes
* pares de cierre automático
* pares envolventes
* ámbitos de palabras clave
* ámbitos de cadenas
* ámbitos de comentarios
* ámbitos de funciones
* ámbitos numéricos

Este sistema funciona independientemente de IntelliSense.

Incluso sin un servidor LSP, el editor sigue proporcionando:

* coloreado de sintaxis
* manejo de comentarios
* emparejamiento de corchetes
* inserción automática de corchetes

---

# FeatureCreep Datafied Content

## Lenguaje

FeatureCreep Datafied Content utiliza:

```text
JBoss DMR
JSON
```

Extensiones:

```text
.dmr
.json
```

## IntelliSense

FeatureCreep utiliza un servidor de lenguaje interno.

No se requiere ningún servidor externo.

El servidor interno comprende:

* estructuras DMR
* estructuras JSON
* diseños de objetos FeatureCreep
* mapas de dependencias
* registros
* definiciones de contenido

Debido a que JBoss DMR se espera en el classpath, el IDE puede analizar directamente contenido DMR.

No se requiere instalación adicional.

---

# KubeJS

## Lenguaje

KubeJS utiliza JavaScript.

Extensiones:

```text
.js
.json
```

## Servidor de Lenguaje

Requerido:

```bash
npm install -g typescript
npm install -g typescript-language-server
```

El IDE utiliza:

```text
typescript-language-server
```

a través de LSP4J.

## Beneficios

Proporciona:

* autocompletado
* información contextual (hover)
* diagnósticos
* búsqueda de símbolos
* navegación

El IDE podrá generar archivos de definiciones TypeScript en el futuro para mejorar el soporte de APIs de mods.

---

# Mineflayer

## Lenguaje

Mineflayer utiliza JavaScript.

Aunque controla bots de Minecraft, fundamentalmente pertenece al ecosistema Node.js.

Extensiones:

```text
.js
.json
```

## Instalación Requerida

```bash
npm install -g typescript
npm install -g typescript-language-server
```

Dentro del proyecto:

```bash
npm install mineflayer
```

Opcional:

```bash
npm install mineflayer-pathfinder
npm install prismarine-viewer
npm install vec3
```

## IntelliSense

Utiliza:

```text
typescript-language-server
```

El servidor TypeScript entiende automáticamente los paquetes npm instalados.

Cuando Mineflayer está instalado mediante npm, el IDE recibe:

* autocompletado
* diagnósticos
* información de tipos

directamente desde las definiciones de los paquetes.

---

# ZenScript

## Lenguaje

ZenScript de CraftTweaker.

Extensiones:

```text
.zs
.zenscript
```

## TextMate

El IDE descarga:

```text
ZenScript-VSCodeLanguage
```

automáticamente.

Proporciona:

* resaltado de sintaxis
* comentarios
* reglas de corchetes

## IntelliSense

Actualmente local.

En el futuro podría utilizar:

```text
ZenScript Language Server
```

si aparece una implementación estable.

---

# GroovyScript

## Lenguaje

Groovy.

Extensiones:

```text
.groovy
```

## Servidor de Lenguaje

Recomendado:

```text
groovy-language-server
```

Almacenado como:

```text
~/crash_detector/groovy-language-server-all.jar
```

Comando configurado:

```bash
java -jar groovy-language-server-all.jar
```

## Notas

El servidor únicamente entiende APIs externas si los JAR correspondientes están presentes en su classpath.

Para modding de Minecraft esto significa que:

* JARs de Minecraft
* JARs de Forge
* JARs de NeoForge
* JARs de Fabric
* JARs de mods

deben ser visibles para el servidor de lenguaje.

---

# ComputerCraft / CC:Tweaked

## Lenguaje

Lua.

Extensiones:

```text
.lua
```

## Servidor de Lenguaje

Recomendado:

```text
lua-language-server
```

## Instalación

```bash
lua-language-server
```

## Configuración

El IDE genera automáticamente:

```text
.luarc.json
```

La configuración generada registra:

```text
colors
colours
disk
fs
gps
http
keys
multishell
os
paintutils
parallel
peripheral
pocket
rednet
redstone
rs
settings
shell
term
textutils
turtle
vector
window
```

como variables globales de ComputerCraft.

## Opcional

La mejor experiencia se obtiene con:

```text
lua-ls-cc-tweaked
```

que proporciona tipados para la API de CC:Tweaked.

---

# WorldEdit CraftScripts

## Lenguaje

Los CraftScripts utilizan JavaScript Rhino.

Extensiones:

```text
.js
```

## Servidor de Lenguaje

Utiliza:

```text
typescript-language-server
```

## Archivos Generados

El IDE genera:

```text
jsconfig.json
```

y

```text
.crashdetector/types/worldedit-craftscript/index.d.ts
```

Estos proporcionan definiciones para:

```text
context
player
argv
Packages
importPackage()
importClass()
```

y APIs comunes de WorldEdit.

## Beneficios

Proporciona:

* autocompletado
* diagnósticos
* información contextual

manteniendo soporte para variables globales específicas de Rhino.

---

# Datapacks y Resource Packs

## Extensiones

```text
.mcfunction
.json
.mcmeta
```

## Servidor de Lenguaje

Recomendado:

```text
Spyglass
```

## Funcionalidades

Proporciona:

* autocompletado de comandos
* diagnósticos de datapacks
* referencias a recursos
* validación de namespaces
* soporte para mcfunction

El IDE asigna:

```text
.mcfunction -> mcfunction
.json -> json
.mcmeta -> json
```

para la identificación del lenguaje.

---

# JBoss FEEL

## Lenguaje

DMN FEEL.

Extensiones:

```text
.feel
.dmn
```

## IntelliSense

Utiliza un servidor de lenguaje interno.

No requiere instalación externa.

## Funcionalidades

Proporciona:

* autocompletado de palabras clave FEEL
* autocompletado de funciones FEEL
* diagnósticos básicos

Si las bibliotecas Drools FEEL existen en el classpath, el IDE puede validar expresiones mediante reflexión.

Las completaciones soportadas incluyen:

```text
date
time
date and time
duration
substring
contains
count
min
max
sum
mean
median
stddev
if
then
else
for
some
every
satisfies
```

---

# Jexel3

## Lenguaje

Scripting de Jexel3.

Extensiones:

```text
.jexel
.txt
```

## IntelliSense

Servidor de lenguaje interno.

No requiere dependencias externas.

Proporciona:

* autocompletado
* diagnósticos
* validación de corchetes

Esta implementación existe porque actualmente no hay un LSP ampliamente adoptado para Jexel.

---

# Juegos de Paradox (CWTools)

## Servidor de Lenguaje

CWTools.

Soporta:

* Stellaris
* Hearts of Iron IV
* Europa Universalis IV
* Crusader Kings II
* Crusader Kings III
* Victoria II
* Victoria III
* Imperator Rome

## Almacenamiento

```text
~/crash_detector/cwtools_lsp
```

Directorios de configuración:

```text
vic2-config
vic3-config
ck2-config
ck3-config
eu4-config
hoi4-config
stellaris-config
ir-config
```

## Funcionalidades

CWTools proporciona:

* diagnósticos
* autocompletado
* navegación
* búsqueda de símbolos
* soporte para localización
* validación de scripts

para scripting del motor Clausewitz.

---

# Autocompletado

El IDE proporciona dos sistemas de autocompletado.

## Autocompletado LSP

Proporcionado por:

```text
ScriptIntellisenseLsp4j
```

Utiliza servidores de lenguaje reales.

Proporciona:

* autocompletado
* diagnósticos
* información contextual
* navegación

## Autocompletado Local

Proporcionado por:

```text
ScriptIntellisenseLocal
```

Se utiliza cuando:

* no existe un LSP
* no hay un servidor instalado
* falla el inicio del servidor

Esto garantiza que el editor siga siendo utilizable.

---

# Arquitectura

```text
ScriptIDEGUI
│
├── Gramática TextMate
│
├── ConfigLenguajeTextMate
│
├── ScriptIntellisense
│   │
│   ├── ScriptIntellisenseLocal
│   ├── ScriptIntellisenseLsp4j
│   ├── ServidorLenguajeDatafiedFC
│   ├── ServidorLenguajeFeel
│   └── ServidorLenguajeJexel3
│
└── Servidores LSP
    │
    ├── TypeScript Language Server
    ├── Lua Language Server
    ├── Groovy Language Server
    ├── Spyglass
    └── CWTools
```

El diseño general separa intencionalmente:

* resaltado de sintaxis
* configuración del lenguaje
* autocompletado
* diagnósticos
* soporte para tipos de proyecto

de forma que se puedan añadir nuevos lenguajes sin modificar el núcleo del editor.

---

# Resumen

Script IDE es el entorno integrado de scripting y desarrollo de CrashDetector.

Al combinar gramáticas TextMate, IntelliSense local, integración LSP, descarga automática de dependencias y soporte para múltiples ecosistemas de modding, proporciona una plataforma flexible para desarrollar, editar y analizar contenido relacionado con Minecraft, FeatureCreep y otros entornos de scripting.

Las características principales incluyen:

* Resaltado de sintaxis mediante TextMate
* IntelliSense local y LSP
* Diagnósticos en tiempo real
* Soporte para múltiples lenguajes
* Descarga automática de dependencias
* Integración con servidores de lenguaje externos
* Servidores de lenguaje internos
* Configuración automática de proyectos
* Arquitectura modular extensible
* Integración con el ecosistema CrashDetector

convirtiéndolo en una herramienta de desarrollo potente y extensible dentro del ecosistema de CrashDetector.

