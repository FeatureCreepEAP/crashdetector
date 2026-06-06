# Recomendaciones de Launchers de Minecraft

## Launchers Recomendados

### TLauncher

* **Descripción**: TLauncher es un launcher versátil y fácil de usar enfocado en desarrolladores que soporta una amplia gama de versiones de Minecraft y modpacks con una interfaz similar al antiguo launcher vanilla como lo recuerdas.

* **Pros**:

  * Soporta el formato de carpeta (.)minecraft del launcher vanilla, lo cual es bueno para el desarrollo de modloaders y el uso de modloaders menos comunes, versiones de desarrollo o clientes.
  * Repositorios Maven con jars vanilla y jars de Minecraft Forge.
  * Consola de Desarrollador de Alta Calidad con excelente registro.
  * Rápido y Ligero.
  * Fácil de arreglar problemas en JSONs o en el propio launcher.
  * Fácil de descompilar.
  * Fácil de hacer mods para el propio launcher.
  * El formato jar hace fácil ejecutarlo en una amplia variedad de computadoras y si existe algún problema con algo como JavaFX (como en MacOS Mojave) no es demasiado difícil evitarlo.
  * Escribe el contenido de la consola en un archivo (útil para algunos analizadores de crashes antiguos).
  * Comunidad multilingüe.
  * TLMods está en múltiples idiomas permitiéndote leer descripciones de CurseForge en múltiples idiomas y buscar mods en tu idioma.
  * TLMods no necesita una API Key de CurseForge.
  * TLMods permite crear modpacks en formato TL e instalar modpacks de CurseForge (aunque para creación recomendamos el launcher de nombre similar ATLauncher ya que puede crear packs de CurseForge que eventualmente llegan a TLMods; TLMods tampoco es generalmente el mejor launcher para modpacks prefabricados comparado con Prism Launcher o ATLauncher).
  * Capacidad de cambiar temporalmente el nombre de usuario para probar cosas específicas del nombre de usuario o en máquinas virtuales para mods sospechosos sin necesidad de cambiar la cuenta Microsoft.
  * Capacidad de compartir rápidamente logs con SecureLogger, una API para compartir logs gzip crudos. Por defecto permite 10MB de logs comprimidos sin límites de líneas.
  * Capacidad de cambiar los endpoints de TLMods, Secure Logger y otros servicios en la configuración.
  * Mod de capa personalizada y skins de alta resolución.
  * Capacidad de desactivar estadísticas anónimas.
  * Funciones avanzadas de seguridad y advertencias.
  * Análisis de crashes integrado.
  * TLGuard para advertir sobre servidores CIS problemáticos (algo que Mojang frecuentemente no hace).
  * Mirrors y proxies en Rusia para ayudarte a acceder a archivos cuando las fuentes oficiales no son accesibles.
  * Instalación con un clic de muchos modloaders y OptiFine, pero también puedes instalar fácilmente versiones oficiales de modloaders, a diferencia de muchos launchers.
  * Fácil de cambiar la JVM por una personalizada.
  * Argumentos personalizados avanzados.
  * Changelog de Minecraft en la pantalla principal.
  * Los canales de soporte te dan muchas libertades que no se encuentran en otros Discords.
  * El desarrollador de CrashDetector tiene el rol de guía comunitario en el servidor de Discord (aunque no está conectado con la empresa de ninguna manera), por lo que puede ayudarte con crashes si lo mencionas. Muchas funciones de CrashDetector como Análisis de Crashes, Compartición Rápida de Logs, Enlace a canales de soporte, Consola de Desarrollo, JSONs personalizados de razones de crash, botón de refrescar, botón de abrir carpeta, configuración gráfica, interfaz de usuario, buscador de mods, sistema de skins, selector de GPU y relanzador fueron influenciadas por TLauncher, ya que vieron las implementaciones amigables para el usuario de funciones avanzadas de nivel desarrollador y aprendieron de su sabiduría y errores.

* **Contras**:

  * No uses las versiones exe o deb. Usa la versión jar de TLauncher porque son conocidas por ser problemáticas.
  * A veces, especialmente con versiones antiguas del launcher, algunas versiones nuevas del juego o modloaders no funcionan sin una ligera modificación.
  * En algunas versiones, especialmente aquellas con el logo gris de TL, TL añade cosas a los JSONs. Muchos launchers, como el launcher vanilla, no soportan estos cambios y probablemente necesitarás borrar esa versión de la carpeta versions y reinstalarla para usarla con otro launcher.
  * Soporte más débil para modpacks prefabricados comparado con muchos launchers; a veces los modpacks de CurseForge terminan descargándose con problemas.
  * Muchas personas que no son desarrolladores no saben que la mayoría de los Kits de Desarrollo de Mods (excepto LiteLoader) no obligan a usar una cuenta Microsoft, por lo que llaman a TLauncher un launcher "pirata", aunque en realidad es mucho más fácil configurar TLauncher con una cuenta Microsoft que la mayoría de los Kits de Desarrollo de Mods. Muchos canales de soporte te rechazarán por usarlo.
  * Sus competidores, a quienes no les gusta que tengan el launcher de Minecraft más popular, trabajaron con periodistas para crear una serie de videos falsos sobre malware que convencieron a mucha gente de que era malware...

---


### Prism Launcher

**Descripción:**
Prism Launcher es ideal para usuarios que prefieren modpacks prefabricados o que poseen configuraciones de hardware problemáticas. 

#### Ventajas

* Relativamente ligero.
* Instalación sencilla de modpacks desde múltiples proveedores.
* Comunidad activa y actualizaciones frecuentes.
* Bootstrap personalizado que reduce problemas relacionados con controladores, Apple Silicon o configuraciones complejas.
* Amplias herramientas para desarrolladores, incluyendo soporte para VisualVM.
* Formato de instancia avanzado.
* Soporte avanzado para argumentos y agentes Java.
* Gestión sencilla de múltiples JVM personalizadas.
* Buenas herramientas de búsqueda de mods.
* Basado en PolyMC, que a su vez deriva de MultiMC.
* Código abierto.
* Incluye mascota felina en la interfaz.
* Tema oscuro.
* Posicionamiento público favorable a la comunidad LGBTQIA+.
* Permite utilizar múltiples nombres de usuario sin modificar la cuenta de Microsoft.
* Buena consola de desarrollo. 

#### Desventajas

* Menor flexibilidad para configuraciones personalizadas porque no utiliza directamente la estructura tradicional `.minecraft`.
* Algunos mods o modloaders pueden comportarse de forma diferente debido al bootstrap personalizado.
* Algunos problemas que aparecerían en otros launchers pueden quedar ocultos durante las pruebas.
* La interfaz puede resultar menos intuitiva para ciertos usuarios. 

---

## Launchers Intermedios

### Launcher Oficial de Minecraft

**Descripción:**
Launcher oficial proporcionado por Mojang. 

#### Ventajas

* Compatible con prácticamente todas las versiones de Minecraft.
* Considerado el launcher de referencia para muchos modloaders y mods.
* Actualizaciones oficiales directas de Mojang.

#### Desventajas

* Soporte limitado para modding sin herramientas adicionales.
* Menos funciones avanzadas que muchos launchers de terceros.
* Problemas ocasionales con las cuentas Microsoft.
* Versiones recientes consideradas lentas y propensas a errores.
* La consola moderna puede ocultar parte de la salida estándar.
* Basado en Chromium.
* Interfaz criticada por algunos usuarios.
* Muchos usuarios consideran que las versiones antiguas del launcher tenían una mejor experiencia de uso. 

---

### ATLauncher

**Descripción:**
Launcher popular conocido por su amplia colección de modpacks y herramientas para creadores de modpacks. 

#### Ventajas

* Instalación fiable de modpacks.
* Muy utilizado por estudios de modpacks.
* Exportación e importación de modpacks en diversos formatos.
* Bastante configurable.
* Disponible en formato JAR.
* Fácil de descompilar.
* Fácil de modificar.

#### Desventajas

* La interfaz puede resultar compleja para principiantes.
* Se han observado algunos problemas de estabilidad, especialmente en Linux.
* El formato de instancias es menos flexible que el de Prism Launcher.
* No utiliza la estructura tradicional `.minecraft`. 

---

### TLLegacy / Legacy Launcher

**Descripción:**
Versión más ligera de TLauncher. 

#### Ventajas

* Soporte para versiones antiguas de Minecraft.
* Ligero y sencillo.
* Útil para proyectos de modding retro o por nostalgia.
* Código abierto.
* Incluye muchas funciones heredadas de TLauncher.

#### Desventajas

* Carece de numerosas funciones presentes en TLauncher.
* Interfaz considerada inferior por los autores del documento.
* Comparte varios de los inconvenientes de TLauncher. 

---

### CurseForge App (con Launcher de Mojang)

**Descripción:**
Aplicación oficial de CurseForge para instalación y creación de modpacks. 

#### Ventajas

* Integración oficial con CurseForge.
* No requiere habilitar descargas de terceros para muchos mods.
* Los creadores reciben ingresos cuando los usuarios instalan mods mediante la aplicación.
* Muchos creadores de modpacks recomiendan utilizar CurseForge o la aplicación de Modrinth.

#### Desventajas

* No se recomienda utilizar la versión basada en Overwolf.
* Más telemetría que otros launchers.
* Actualizaciones automáticas incluso en sistemas que no soportan nuevas versiones.
* Menos flexible que muchos competidores.
* No utiliza el formato tradicional `.minecraft`.
* Rendimiento lento, especialmente con recursos gráficos limitados.
* Los modpacks muy grandes pueden causar problemas en la interfaz.
* Se han observado instalaciones corruptas con cierta frecuencia.
* Consume bastantes recursos y permanece activo en segundo plano.
* Tiene dos consolas distintas: una en CurseForge y otra en el launcher de Mojang.
* Por defecto utiliza la opción "Skip Launcher" en versiones recientes. 

---

**Nota:** La mayoría de los demás launchers no mencionados específicamente suelen situarse en esta categoría intermedia según sus características generales y la experiencia de los usuarios. 













# Launchers Condenados

## Modrinth App (Theseus)

* **Descripción**: Aunque Modrinth es un excelente repositorio de mods, su aplicación tiene problemas de estabilidad y soporte.

* **Pros**:

  * Acceso a una amplia gama de mods a través del repositorio Modrinth.
  * Da dinero a los creadores, a diferencia de la mayoría de los launchers de terceros, cuando instalas mods a través de él. Esto significa que muchos creadores de modpacks te pedirán que uses este o la aplicación de CurseForge.
  * Un fork llamado Astralrinth corrige algunos de los problemas de seguridad por defecto.
  * La interfaz tiene una apariencia moderna.
  * Código abierto.
  * Puedes desactivar gran parte de la telemetría.

* **Contras**:

  * La interfaz tiene varios errores conocidos.
  * Soporte limitado para MCForge en el pasado.
  * Opiniones mixtas de usuarios y soporte comunitario inconsistente.
  * Tuvimos problemas para hacerlo funcionar en RHEL9.
  * Mala política de privacidad: [https://modrinth.com/legal/privacy](https://modrinth.com/legal/privacy)
  * Tiene un bootstrap personalizado, con efectos desconocidos.
  * Sin soporte para modpacks de CurseForge.

---

## CurseForge App con Skip Launcher

* **Descripción**: La aplicación de CurseForge es conocida por su repositorio de mods, pero la opción Skip Launcher es problemática.

* **Pros**:

  * La mayoría de las ventajas de la aplicación CurseForge, pero sin tener que lidiar con el horrible launcher vanilla.

* **Contras**:

  * Los mismos problemas que con el Launcher de Mojang.
  * Ya no existe la consola del launcher vanilla, únicamente la integrada en CurseForge, que como se mencionó anteriormente no muestra SysOut ni SysErr, necesarios para muchos crashes.
  * Tampoco guarda esta información en un archivo, por lo que esos datos básicamente se pierden.
  * Para recuperar esa información necesitarás usar una herramienta como ProxySysOutSysErr de CrashDetector o alguna alternativa.
  * En algunos mods el juego se bloquea por razones desconocidas o carga indefinidamente (especialmente con el modpack Dawn of Berk).
  * Esto parece ser inconsistente o exclusivo de Windows, ya que no pudimos reproducirlo nosotros mismos, aunque sabemos que es un problema común. 

---

## SKLauncher

* **Descripción**: Un launcher común con muchos problemas.

* **Pros**:

  * Es considerado relativamente ligero.
  * Fuertes capacidades de modding.
  * Soporte para la carpeta tradicional `.minecraft`.
  * Tiene versión JAR.
  * Fácil de descompilar.
  * Fácil de modificar el propio launcher.

* **Contras**:

  * Añade un agente que posiblemente pueda entrar en conflicto con otros agentes.
  * Frecuentemente tuve problemas para abrirlo, especialmente en RHEL9.
  * He visto muchos problemas relacionados con instalaciones incorrectas de mods, modpacks o incluso del propio juego.
  * No soporta modpacks de CurseForge.
  * Al ser un launcher crackeado, muchos canales de soporte te rechazarán por utilizarlo. 

---

## Clientes PvP (Lunar Client, Badlion, WaterClient, Feather Client, LabyMod, NoRisk, etc.)

* **Descripción**: Estos clientes suelen estar diseñados como modpacks prefabricados con versiones personalizadas de los mods.

* **Pros**:

  * Optimizados para PvP con diversas mejoras.
  * En algunos casos tienen una mejor integración que utilizar los mods individualmente.

* **Contras**:

  * Riesgos de seguridad y posibles expulsiones en servidores que no los permiten.
  * Problemas de rendimiento y errores específicos de estos clientes.
  * Soporte limitado para juego no PvP y modding.
  * Frecuentemente son similares a modpacks prefabricados pero con versiones personalizadas de muchos mods.
  * Muchas de estas versiones son antiguas y reemplazarlas o deshabilitarlas provoca problemas.
  * Muchos mods entran en conflicto con estos clientes.
  * Existen numerosos crashes asociados a clientes como FeatherClient y Lunar Client.
  * Muchos están ofuscados y utilizan bootstraps personalizados que modifican gran cantidad de comportamiento. 

---

## FreshCraft

* **Descripción**: Un launcher relativamente nuevo que no ha recibido demasiada retroalimentación positiva. Falla incluso en tareas básicas como configurar correctamente la carpeta assets.

* **Pros**:

  * Ninguna ventaja significativa frente a launchers establecidos.

* **Contras**:

  * Inestable y propenso a crashes.
  * Funciones limitadas y poco soporte para modpacks.
  * Soporte comunitario pobre y falta de actualizaciones.
  * Solo Windows.
  * Formato EXE.
  * Código cerrado.
  * Difícil de descompilar. 

---

## Versión Flatpak de TLLegacy / Legacy Launcher

* **Descripción**: La versión Flatpak de TLLegacy tiene problemas adicionales en comparación con la versión independiente.

* **Pros**:

  * Acceso a las funciones de TLLegacy desde la plataforma Flatpak.

* **Contras**:

  * Degradación de rendimiento debido a la sobrecarga de Flatpak.
  * La consola de desarrollo y muchas versiones no funcionan.
  * Menos actualizaciones y soporte que la versión independiente.
  * Muchos cambios y múltiples características que no funcionan correctamente. 

---

## LauncherFenix

* **Descripción**: Un launcher que ha recibido críticas por sus características anticuadas y bajo rendimiento.

* **Pros**:

  * Ninguna ventaja significativa frente a launchers modernos.

* **Contras**:

  * Interfaz y diseño anticuados.
  * Soporte limitado para modpacks y funcionalidades.
  * Usuarios reportan crashes y errores frecuentes. 

---

## Crystal Launcher

* **Descripción**: Un launcher que no ha conseguido demasiada adopción debido a diversos problemas.

* **Pros**:

  * Ninguna ventaja significativa frente a otras opciones disponibles.

* **Contras**:

  * Problemas de rendimiento y estabilidad.
  * Funciones limitadas y soporte pobre para modpacks.
  * Falta de soporte comunitario y actualizaciones regulares. 

---

## Battly Launcher

* **Descripción**: Un launcher que ha recibido fuertes críticas por su rendimiento y seguridad.

* **Pros**:

  * Ninguna ventaja significativa frente a launchers reputados.

* **Contras**:

  * Vulnerabilidades de seguridad frecuentes y posible presencia de malware.
  * Mal rendimiento en la mayoría de sistemas y configuraciones.
  * Falta de actualizaciones regulares y soporte comunitario. 

---

## Titan Launcher

* **Descripción**: Un launcher conocido por su asociación con contenido pirateado y riesgos de seguridad.

* **Pros**:

  * Ninguna. Generalmente no se recomienda debido a preocupaciones serias.

* **Contras**:

  * Alto riesgo de malware y virus.
  * Posibles baneos en servidores oficiales y comunidades.
  * Problemas de rendimiento y estabilidad. 

---

**Nota:** Las imágenes proporcionadas en el documento original son marcadores de posición y deberían reemplazarse por URLs reales que apunten a imágenes relevantes de cada launcher. 

