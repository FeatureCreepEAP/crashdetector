# AGENT.MD — Crash Detector

Guía obligatoria para cualquier agente, copiloto o colaborador que escriba código
en este repositorio. Lee este archivo completo antes de modificar cualquier archivo.

---

## 1. Idioma del código y de los mensajes

- **TODO el código, identificadores, comentarios, JavaDoc y mensajes al usuario deben
  estar en ESPAÑOL.** Esto incluye:
  - Nombres de clases, métodos, variables y campos (ej. `MonitorDePID`,
    `registrarGUI`, `etiquetaDelBoton`, `obtenerGUIPredeterminado`).
  - Comentarios de línea y de bloque.
  - JavaDoc / documentación de APIs.
  - Cadenas visibles para el usuario.
- Los identificadores técnicos internos (códigos de idioma, claves de config,
  IDs de GUI) usan minúsculas y guion bajo (ej. `guitipo_principal`, `es`, `en`).
- No introducir texto en inglés en mensajes de usuario ni comentarios. La
  documentación del proyecto vive en `src/main/resources/docs/espanol/` (primaria)
  y `src/main/resources/docs/ingles/` (traducción).

## 2. Internacionalización (i18n)

- La interfaz central es `com.asbestosstar.crashdetector.Idioma`.
- Los idiomas se registran dinámicamente en `Idioma.IDIOMAS_REGISTRADOS`
  (un `LinkedHashMap&lt;String, Idioma&gt;` cuya clave es el código del idioma:
  `es`, `en`, `pt`, `ar`, `fa`, `ru`, `zh`, `eo`, `ja`, `ko`, `uk`, `vi`,
  `id`, `ms`, `km`, `th`, `lo`, `fr`, `sw`).
- **Español (`es`) es el idioma predeterminado** (`idioma_respaldo`, valor `"es"`).
- Para añadir un idioma nuevo:
  1. Crear la clase en `com.asbestosstar.crashdetector.idioma/` (ej. `Aleman.java`)
     implementando `Idioma`.
  2. Registrarla en `Idioma.registrarIdiomasPredeterminados()` con
     `registrarIdioma(new Aleman())`.
  3. Añadir su bandera/imagen en `src/main/resources/imagenes/` (ej. `bandera_alemania.png`).
- Registrar el mismo código dos veces sobrescribe la traducción anterior; las
  extensiones/modpacks pueden usar esto para parcializar traducciones.
- La interfaz `Idioma` incluye las etiquetas semánticas de severidad usadas por el
  analizador: `error()`, `advertencia()` y `fatal()`. **Todo idioma nuevo debe
  implementarlas** (son las que se muestran en el lector de consolas junto al
  color correspondiente de la verificación).

## 3. Localización HTML para verificaciones (analizador)

**Los mensajes de las verificaciones NO son texto plano: son HTML**, igual que las
verificaciones existentes. Nunca concatenar texto sin traducir directamente en la
verificación: todo string visible pasa por `MonitorDePID.idioma`.

### 3.1 Ejemplo completo: verificación + localización HTML

**Verificación** (`analizador/general/MiVerificacionNueva.java`):

```java
public class MiVerificacionNueva implements Verificaciones {

    private boolean activado = false;
    private String mensaje = "";

    // Disparadores rápidos (sin regex)
    @Override
    public String[] patronesRapidos() {
        return new String[] { "El patrón que buscamos" };
    }

    // La lógica de detección
    @Override
    public void verificarCoincidencia(EventoDeCoincidencia evento) {
        if (evento == null || evento.linea == null || activado) {
            return;
        }
        verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
    }

    @Override
    public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
        if (!linea.contains("El patrón que buscamos")) {
            return;
        }

        if (consola != null) {
            consola.agregarErrorALectador(numero_de_linea, this);
        }

        // El mensaje se construye con HTML, usando los métodos del idioma
        this.mensaje = construirMensaje();
        this.activado = true;
    }

    private String construirMensaje() {
        StringBuilder sb = new StringBuilder();
        sb.append("&lt;b&gt;").append(MonitorDePID.idioma.miVerificacionTitulo()).append("&lt;/b&gt;&lt;br&gt;&lt;br&gt;");
        sb.append(MonitorDePID.idioma.miVerificacionDetalle());
        return sb.toString();
    }

    @Override
    public Verificaciones nueva() {
        return new MiVerificacionNueva();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return -1500; // más negativo = más prioritario
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombreVerificacionMiVerificacion();
    }

    // La id SIN símbolos, acentos ni espacios
    @Override
    public String id() {
        return "mi_verificacion_nueva";
    }

    @Override
    public Documento docs() {
        return Documento.NINGUN;
    }
}
```

**Localización** (`idioma/Espanol.java` — y el método equivalente en **todas** las
demás clases de idioma):

```java
@Override
public String miVerificacionTitulo() {
    return "Título de la verificación";
}

@Override
public String miVerificacionDetalle() {
    return "&lt;b style='color:#" + config.obtenerColorAdvertencia() + "'&gt;"
            + "Texto destacado con el color de advertencia del tema." + "&lt;/b&gt; "
            + "Texto normal del mensaje, con párrafos separados por &lt;br&gt;&lt;br&gt;"
            + "El salto de línea en HTML es &lt;br&gt; (constante nl_html), no \\n. "
            + "&lt;i&gt;Notas al pie en cursiva con &lt;i&gt;...&lt;/i&gt;.&lt;/i&gt;";
}
```

### 3.2 Reglas del formato HTML

- Los saltos de línea se escriben como `&lt;br&gt;` o `&lt;br&gt;&lt;br&gt;` (la interfaz expone
  `Verificaciones.nl_html = "&lt;br&gt;"`), **nunca** `\n` ni `System.lineSeparator()`
  dentro del mensaje HTML.
- Párrafos, listas y énfasis con etiquetas HTML estándar (`&lt;b&gt;`, `&lt;i&gt;`, `&lt;ul&gt;&lt;li&gt;`, `&lt;a&gt;`).
- **Los colores NO se escriben como literales fijos**: se obtienen de la config del
  tema (ej. `config.obtenerColorAdvertencia()`, `config.obtenerColorError()`,
  `config.obtenerColorDeTitulosDeConsolas()`, `config.obtenerColorEnlace()`),
  igual que en el ejemplo de `Raptor Lake`.
- El nombre (`nombre()`) y el mensaje (`mensaje()`) van siempre mediante
  `MonitorDePID.idioma.&lt;metodo&gt;()`.
- La documentación de la verificación se enlaza con `docs()` y el `QuickFix` con
  `solucion()`; ambos se renderizan automáticamente como enlaces en el mensaje final
  (ver `Verificaciones.comoString()`).

### 3.3 Error vs Advertencia (`Criticalidad`)

Cada verificación tiene un nivel de severidad definido en
`analizador/Criticalidad.java` con tres niveles:

| Nivel | Color | Significado |
|---|---|---|
| `Criticalidad.FATAL` | `Color.RED` | Error grave del propio programa |
| `Criticalidad.ERROR` | `config.obtenerColorError()` | **Problema que SÍ provoca (o provocó) el fallo/crash** |
| `Criticalidad.ADVERTENCIA` | `config.obtenerColorAdvertencia()` | **Problema importante que NO provoca el fallo por sí solo — aviso preventivo** |

- `Verificaciones.nivel_de_criticalidad()` **devuelve `Criticalidad.ERROR` por
  defecto**. Solo se sobrescribe cuando la verificación no es la causa directa del
  crash.
- Usa **`ADVERTENCIA`** para: avisos de salud de hardware (ej. la inestabilidad de
  Raptor Lake, que "casi con seguridad NO es la causa del crash actual"), opciones
  de JVM desaconsejadas, configuraciones que conviene evitar, software problemático
  que aún no ha fallado, etc.
- Usa **`ERROR`** (o el default) para: excepciones que terminan el juego, mods
  corruptos, dependencias faltantes, versiones incompatibles que impiden arrancar.
- Usa **`FATAL`** solo para fallos internos graves del propio Crash Detector.
- El nivel se muestra en el lector de consolas con el **nombre traducido del idioma**
  (`MonitorDePID.idioma.error()` / `advertencia()` / `fatal()`) junto al color del
  tema — por eso toda clase de idioma debe implementar esos tres métodos.

```java
// Verificación que advierte sin ser la causa del crash
@Override
public Criticalidad nivel_de_criticalidad() {
    return Criticalidad.ADVERTENCIA;
}
```

## 4. Sistema de GUIs y tipos de GUI

### 4.1 Cadenas y colores de la GUI

- **Ningún string visible ni color de la GUI está escrito en firme ("hardcodeado").**
- Los textos se obtienen del idioma activo: `MonitorDePID.idioma.&lt;metodo&gt;()`.
- Los colores del tema se obtienen mediante `ConfigColor`
  (`com.asbestosstar.crashdetector.config.ConfigColor`, junto a `ConfigString`,
  `ConfigBoolean`, `ConfigDouble`, etc.). Ejemplos de uso:
  - `ConfigColor.de("color_fondo_panel", "1e1e1e")` para definir un color configurable.
  - `Config.obtenerInstancia().obtenerColorAdvertencia()` para colores semánticos ya
    existentes (error, advertencia, info, títulos de consolas, enlaces, …).
- Si un color o texto nuevo se necesita: añadir la clave `ConfigColor`/`ConfigString`
  con su valor predeterminado y exponer el método en **todas** las clases de idioma.

### 4.2 Formato / contrato de un TipoGUI

Toda familia de GUIs se representa con un `TipoGUI&lt;T extends CrashDetectorGUI&gt;`
en `com.asbestosstar.crashdetector.gui.tipos.TipoGUI`, que define:

- `String id()` — identificador único del tipo (ej. `"principal"`, `"grepr"`).
- `String etiquetaDelBoton()` — texto del botón en la barra lateral (usar
  `MonitorDePID.idioma.&lt;metodo&gt;()` para textos traducibles).
- `Icon icon()` — icono opcional del botón.
- `void registrarGUI(String id, Supplier&lt;T&gt; gui)` — registra una variante de GUI.
- `Map&lt;String, Supplier&lt;T&gt;&gt; obtenerGUIs()` — mapa de variantes registradas.
- `boolean requireBuscador()` — si la GUI necesita el buscador (por defecto `false`).
- `T obtenerGUIPredeterminado(String id_de_por_defecto, Supplier&lt;T&gt; por_defecto)` —
  resuelve la variante activa leyendo la config `guitipo_&lt;id&gt;`.

### 4.3 Registro de GUIs (OBLIGATORIO)

- Cada clase base de GUI tiene un mapa estático `GUIS`
  (ej. `PrincipalGUI.GUIS`, `GrepRGUI.GUIS`), de tipo
  `Map&lt;String, Supplier&lt;T&gt;&gt;`.
- **Para TODAS las GUIs nuevas se debe registrar un tipo**:
  1. Declarar el `public static TipoGUI&lt;XxxGUI&gt; XXX = new TipoGUI&lt;&gt;() {...}` en
     `TipoGUI.java`, implementando `registrarGUI`/`obtenerGUIs` contra
     `XxxGUI.GUIS`.
  2. Añadir el nuevo tipo al bloque `static { TIPOS_DE_GUI.add(XXX); ... }` de
     `TipoGUI.java`. Si no se añade ahí, la GUI no aparecerá en la interfaz.

### 4.4 Valores predeterminados y configuración

- El usuario elige su variante de GUI por tipo mediante la clave de config
  `guitipo_&lt;id_del_tipo&gt;` (ej. `guitipo_principal`), creada con
  `ConfigString.de("guitipo_" + this.id(), id_de_por_defecto)`.
- `obtenerGUIPredeterminado(...)` devuelve la variante configurada o cae en el
  `Supplier` por defecto si el ID no existe.
- Toda clave de configuración nueva se define con `ConfigString.de(clave, valorPredeterminado)`;
  los colores con `ConfigColor.de(clave, valorPredeterminadoHexSinNumero)`.

## 5. Variantes de GUI (skins) e imágenes

- Cada familia de GUI tiene una base (ej. `GrepRGUI`) y variantes con temática
  ("waifu", legacy, corporativa, regional…, ej. `BusquedaGUISaliorMoon`,
  `GuardiaSketchyVT`, `ConfigPanelEstiloTL`), cada una registrada en el mapa
  `GUIS` de su familia bajo un ID distinto.
- **Las imágenes de las GUIs viven en `src/main/resources/imagenes/`** (PNG/GIF/JPG).
  Al crear una variante nueva se debe añadir su imagen correspondiente en esa
  carpeta y referenciarla desde la clase de la GUI. Ejemplos de nombres válidos:
  `boton_config.png`, `boton_actualizar.png`, `cd_logo.png`, `bandera_mexico.png`.
- Las imágenes de bandera (`bandera_*.png`) se usan para el selector de idiomas
  (`ComboIdiomasConIcono`).
- Nomenclatura de imágenes: minúsculas con guion bajo.

## 6. MonitorDePID

- `com.asbestosstar.crashdetector.MonitorDePID` es la clase principal de la
  aplicación (entry point y bucle de monitoreo del proceso Java/Minecraft).
- Expone acceso global al idioma activo: `MonitorDePID.idioma` (usado en todo el
  código de GUIs y verificaciones, ej. `MonitorDePID.idioma.arbolDeMods()`), y
  trabaja junto con `Statics` (carpetas globales, ej. `Statics.carpeta_mundial_como_archivo`).
- Las imágenes/temas visuales asociados al monitoreo (waifus, banners, botones)
  se cargan desde `src/main/resources/imagenes/` vía `MonitorDePID` y el
  paquete `com.asbestosstar.crashdetector.waifu`.
- Al añadir elementos visuales al monitor: registrar la imagen en `imagenes/`,
  exponer la etiqueta traducible en la interfaz `Idioma` (todas las clases de
  idioma) y usar los colores vía `ConfigColor`/`Config`.

## 7. Analizador — añadir verificaciones

Para crear MÁS verificaciones (checks) del analizador:

1. **Registrar la verificación en
   `src/main/java/com/asbestosstar/crashdetector/analizador/Analizador.java`.**
2. Si la verificación es **general** (aplica a cualquier registro/crash), crearla
   en el paquete `com.asbestosstar.crashdetector.analizador.general`.
3. Si la verificación es **específica de una aplicación**, crearla en el paquete
   correspondiente bajo `com.asbestosstar.crashdetector.analizador.apps`.
4. Escribir los mensajes de la verificación en **las clases de idioma** siguiendo
   el **formato HTML** descrito en la sección 3, usando `MonitorDePID.idioma` y
   los colores de config (nunca literales).
5. Respetar las clases de soporte del paquete: `Verificaciones`,
   `VerificacionDeStackTrace`, `Criticalidad`, `ListaDenegadosTrace`, `QuickFix`,
   y subpaquetes `firmas` / `rapido`.

### 7.1 Error vs Advertencia al crear verificaciones

- **ERROR (default)**: la verificación detecta la causa directa del crash.
  No hace falta sobrescribir `nivel_de_criticalidad()`.
- **ADVERTENCIA**: la verificación detecta algo importante que **no** causó el
  fallo pero conviene corregir/evitar. Además de sobrescribir
  `nivel_de_criticalidad()` (ver 3.3), el mensaje debe dejar claro que es un aviso
  (el detalle de Raptor Lake es el patrón a seguir:
  *"Esto casi con seguridad NO es la causa de tu crash actual, es solo un aviso"*).
- Las etiquetas visibles (`error()`, `advertencia()`, `fatal()`) salen del idioma
  activo; no se hardcodean en la verificación.

### 7.2 `anularNormal()` — advertencias importantes

- `anularNormal()` (por defecto `false`) se reserva para **verificaciones
  importantes**: cuando se activan, **fuerzan la apertura de CrashDetector** aunque
  el proceso analizado no se haya cerrado de forma anormal. Sirve como
  **advertencia preventiva** —a modo de aviso— para casos en los que conviene
  evitar el uso de algo aunque no haya provocado un fallo todavía.
- **Úsalo solo cuando sea realmente necesario para la verificación específica**;
  la mayoría de verificaciones deben devolver `false`. No es un mecanismo general
  para que toda verificación abra la GUI.
- Ejemplo real (`analizador/general/AntiManipulacion.java`):

```java
@Override
public boolean anularNormal() {
    // Si hay manipulación, forzar apertura de CrashDetector.
    return activado;
}
```

## 8. Convenciones generales de código

- Proyecto **Java + Maven** (`pom.xml`, workflow en `.github/workflows/maven.yml`);
  multi-loader de Minecraft (Forge, NeoForge, Fabric, Rift, LiteLoader, Bukkit,
  Sponge, Canary, NilLoader, Flint, MangoLoader, FancyModLoader, ModLauncher, …).
- Logging centralizado con `CrashDetectorLogger.log(...)` / `.logException(...)`.
- Todos los identificadores y comentarios en español (ver sección 1). Los JavaDoc
  describen propósito en español.
- Sigue el estilo existente: llaves en la misma línea, tabulación con tabs,
  nombres descriptivos en español (`CargadorExtensiones`, `EscanerMCreator`,
  `VisorHeapDumpGUI`, `GeneradorDeInformacion`).
- Preferir búsqueda de subcadenas (`indexOf`/`contains`) sobre regex en el camino
  caliente del análisis (ver `RaptorLakeInestable`).
- No romper los puntos de registro: `TIPOS_DE_GUI`, `IDIOMAS_REGISTRADOS`,
  `GUIS` de cada familia, y el registro de verificaciones en `Analizador.java`.
- Documentar funcionalidades nuevas en `src/main/resources/docs/espanol/minecraft/`
  y su traducción en `docs/ingles/minecraft/`.