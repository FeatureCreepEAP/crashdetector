# CrashDetectorMC

2,034 / 5,000
Este es un lector de seguimiento de pila y analizador de registros. Permite leer los seguimientos de pila. Busca:

Configuraciones rotas de SpongeMixin
Servicios de ModLauncher + SpongeMixins en el seguimiento de pila
Archivos jar en líneas

Si no hay ningún archivo mod en las líneas, busca modids (esto es común en entornos de desarrollo y lanzadores orientados al desarrollo).

Si no existe, busca paquetes en esa línea.

Distingue entre seguimientos fatales y no fatales, indicando que se priorizan los fatales.

Tiene un sistema de nivel y número de línea: los de nivel superior deben priorizarse sobre los de nivel inferior; en el mismo nivel, los de nivel inferior deben priorizarse según lo especificado.

Tiene listas de denegación de prefijos para paquetes para evitar que los paquetes del cargador, los modids y los archivos jar se incluyan en la salida. Crea URLs pegadas usando securelogger
Muestra la ubicación de tus registros
Se integra con MixerLogger
Funciona con ModLauncher (MCForge y derivados) 1.17+, FabricMC y FeatureCreep
Detecta errores fatales de clase no encontrada
Funciona con muchos formatos de registro diferentes de diferentes lanzadores
Facilita la búsqueda de la causa del fallo
Comprueba otros problemas comunes, como dependencias faltantes, versiones antiguas de ASM y versiones de Java no compatibles.
Gratuito y de código abierto
Compatible con español, inglés, árabe, portugués, ruso, chino, persa, coreano, japonés y más.

Aún necesita algunas pruebas, más uso en el mundo real y mejoras, pero con el tiempo debería ser posible tener un lector de seguimiento de pila como este.

Notas:

La versión de Modlauncher debería funcionar con NeoForge y PillowLoader, ya que escribimos nuestro archivo de clase para que sea compatible con su formato; sin embargo, estos no han sido probados exhaustivamente. MCForge y su nuevo NovaLauncher son el principal objeto de prueba.

El archivo JAR es el mismo en todos los cargadores de mods (ModLauncher, FeatureCreep y FabricMC).

Esto no se ha probado en quilt-loader ni en la biblioteca de registro de QSL.

Esto no funciona en los servidores Theseus ni modrinth.gg del lanzador; sin embargo, se puede cambiar fácilmente con coremods.
Enviar comentarios




## Repositorio trasladado a GitHub

Debido al cierre de Pagure.io, tal como se ha discutido aquĆ­: https://discussion.fedoraproject.org/t/decommissioning-of-pagure-io-anticipated-by-flock-2026/181997, este proyecto ha sido trasladado de Pagure a GitHub:

- Antiguo: https://pagure.io/CrashDetectorMC
- Nuevo: https://github.com/FeatureCreepEAP/crashdetector
