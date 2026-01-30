package com.asbestosstar.crashdetector;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.canario.CanarioDeOrdenJudicial;
import com.asbestosstar.crashdetector.canario.pordefecto.CDInformesAsbestosstarEgoismJPCanario;
import com.asbestosstar.crashdetector.canario.pordefecto.CDPasteAsbestosstarEgoismJPCanario;
import com.asbestosstar.crashdetector.detectorlanzer.DetectorLanzer;
import com.asbestosstar.crashdetector.grepr.BusquedaArchivos;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.antimanipulacion.AntiManipulacionGUIPanko;
import com.asbestosstar.crashdetector.gui.tipos.aplic.ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn;
import com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUIHamu;
import com.asbestosstar.crashdetector.gui.tipos.canario.CanarioDeOrdenJudicialGUI1984;
import com.asbestosstar.crashdetector.gui.tipos.cfr.BuscarParaCFR;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrSakuraRiddle;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirLegacy;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanelEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.consola.ConsolaDesarrolladorGUI;
import com.asbestosstar.crashdetector.gui.tipos.consola.ConsolaDesarrolladorGUITL;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoSAO;
import com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones.DeshabilitarVerificacionesAmaneKanata;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorCodiceGUIIronMouse;
import com.asbestosstar.crashdetector.gui.tipos.editor_plantilla.EditorPlantillaModioNoche;
import com.asbestosstar.crashdetector.gui.tipos.editor_plantilla.EditorPlantillaPredeterminado;
import com.asbestosstar.crashdetector.gui.tipos.editorgui.CDSkinCape;
import com.asbestosstar.crashdetector.gui.tipos.grepr.BusquedaGUISaliorMoon;
import com.asbestosstar.crashdetector.gui.tipos.historia.ClioOfficeGUI;
import com.asbestosstar.crashdetector.gui.tipos.historia.HistoriaModsGUILegacy;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos.LanzerBuenoGUIMaidMint;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos.LanzerMaloGUISylentBell;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;
import com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUIRosemiLoveLock;
import com.asbestosstar.crashdetector.gui.tipos.miranda.DerechosPiratasGUIOnePiece;
import com.asbestosstar.crashdetector.gui.tipos.modapi.CDModsEstiloTL;
import com.asbestosstar.crashdetector.gui.tipos.modsbuenas.ModsBuenasGUIReiBubbles;
import com.asbestosstar.crashdetector.gui.tipos.modsmalas.GUIModsMalasRimaEvenstar;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroDeLauncherVShojo;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroLanzadorGUI;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUIEstiloLanzer;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.ElementoQuickFixDemonSlayers;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.PanelQuickFixDemonSlayers;

public class MonitorDePID {

	public static final String mensaje_de_registro_lanzer_completo = "Puedes ignorar esta linea, solo es para CrashDetector, este mensaje es siempre en espanol";
	public static File ArchivoDeCodigoError0 = Statics.carpeta.resolve("ArchivoDeCodigoError0").toFile();
	public static Path ultimo_mods = Statics.carpeta.resolve("ultima_mods");
	// public static Path viajo_ultima_mods = carpeta.resolve("viajo_ultima_mods");
	public static List<Consola> consolas = new ArrayList<Consola>();

	public static String nl = System.lineSeparator();
	public static Idioma idioma = Idioma.detectar();
	public static String local;
	public static String enlace;
	public static long pid;
	public static boolean resultados = false;
	public static boolean consola_de_launcher_inyectado = false;
	public static StringBuilder contenidoInforme;
	public static Analizador analizador = new Analizador();
	public static Object consola_des = null;

	/**
	 * Es diferente en el process diferente
	 */
	public static Instant utc = Instant.now();

	public static void main(String[] args) {
		registrarGUISPredeterminado();
		DetectorLanzer.registrarLanzeresDefectos();
		if (args.length > 0 && args[0].equals("--monitor")) {
			long pid = Long.parseLong(args[1]);
			monitor_proceso(pid);
			return;
		}

		if (args.length > 0 && (args[0].equals("grepr") || args[0].equals("fgrepr"))) {
			boolean useRegex = args[0].equals("grepr");
			boolean caseInsensitive = false;
			String searchString = "";
			String directory = System.getProperty("user.dir");

			int index = 1;
			while (index < args.length) {
				String arg = args[index];
				if (arg.equals("-i")) {
					caseInsensitive = true;
					index++;
				} else if (arg.equals("--help")) {
					mostrarAyudaCLI();
					return;
				} else {
					break;
				}
			}

			if (index >= args.length) {
				System.out.println("Error: Falta cadena de búsqueda");
				mostrarAyudaCLI();
				return;
			}

			searchString = args[index++];
			if (index < args.length) {
				directory = args[index];
			}

			List<String> resultados = BusquedaArchivos.buscar(directory, searchString, useRegex, caseInsensitive);
			for (String res : resultados) {
				System.out.println(res);
			}
			return;
		}

		ArchivoDeCodigoError0.delete();

		String mods = "";
		if (ultimo_mods.toFile().exists()) {
			try {
				mods = leer_archivo(ultimo_mods);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		File html = Statics.carpeta.resolve("pantilla.htm").toFile();
		copiarACarpetaDesdeJar("/pantilla.htm", html);

		copiarACarpetaDesdeJar("/imagenes/gura.png", Statics.carpeta.resolve("imagenes/gura.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/clio.png", Statics.carpeta.resolve("imagenes/clio.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/hamu.png", Statics.carpeta.resolve("imagenes/hamu.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/nanashi_mumei.png",
				Statics.carpeta.resolve("imagenes/nanashi_mumei.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/shion.png", Statics.carpeta.resolve("imagenes/shion.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/vshojo.png", Statics.carpeta.resolve("imagenes/vshojo.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/rosemi.png", Statics.carpeta.resolve("imagenes/rosemi.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/kiara_ame.png", Statics.carpeta.resolve("imagenes/kiara_ame.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/ironmouse.png", Statics.carpeta.resolve("imagenes/ironmouse.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/padoru.gif", Statics.carpeta.resolve("imagenes/padoru.gif").toFile());
		copiarACarpetaDesdeJar("/imagenes/demonslayers.png",
				Statics.carpeta.resolve("imagenes/demonslayers.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/boton_agregar.png",
				Statics.carpeta.resolve("imagenes/boton_agregar.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_compartir.png",
				Statics.carpeta.resolve("imagenes/boton_compartir.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_compartir_icon.png",
				Statics.carpeta.resolve("imagenes/boton_compartir_icon.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_actualizar.png",
				Statics.carpeta.resolve("imagenes/boton_actualizar.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_archivos.png",
				Statics.carpeta.resolve("imagenes/boton_archivos.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_config.png",
				Statics.carpeta.resolve("imagenes/boton_config.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/boton_cdmods.png",
				Statics.carpeta.resolve("imagenes/boton_cdmods.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/cd_chars.png", Statics.carpeta.resolve("imagenes/cd_chars.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/cd_logo.png", Statics.carpeta.resolve("imagenes/cd_logo.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/profeco.jpg", Statics.carpeta.resolve("imagenes/profeco.jpg").toFile());
		copiarACarpetaDesdeJar("/imagenes/edps.png", Statics.carpeta.resolve("imagenes/edps.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/ppc_jp.png", Statics.carpeta.resolve("imagenes/ppc_jp.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/dprk.png", Statics.carpeta.resolve("imagenes/dprk.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/kimjongun.png", Statics.carpeta.resolve("imagenes/kimjongun.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/saliormoongrep.png",
				Statics.carpeta.resolve("imagenes/saliormoongrep.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/bandera_mexico.png",
				Statics.carpeta.resolve("imagenes/bandera_mexico.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_inglaterra.png",
				Statics.carpeta.resolve("imagenes/bandera_inglaterra.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_arabia.png",
				Statics.carpeta.resolve("imagenes/bandera_arabia.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_brasil.png",
				Statics.carpeta.resolve("imagenes/bandera_brasil.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_iran.png",
				Statics.carpeta.resolve("imagenes/bandera_iran.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_rusia.png",
				Statics.carpeta.resolve("imagenes/bandera_rusia.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_china.png",
				Statics.carpeta.resolve("imagenes/bandera_china.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_esperanto.png",
				Statics.carpeta.resolve("imagenes/bandera_esperanto.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_japon.png",
				Statics.carpeta.resolve("imagenes/bandera_japon.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/bandera_corea.png",
				Statics.carpeta.resolve("imagenes/bandera_corea.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/mod.png", Statics.carpeta.resolve("imagenes/mod.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/clase.png", Statics.carpeta.resolve("imagenes/clase.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/metodo.png", Statics.carpeta.resolve("imagenes/metodo.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/campo.png", Statics.carpeta.resolve("imagenes/campo.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/paquete.png", Statics.carpeta.resolve("imagenes/paquete.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/referencia_metodo.png",
				Statics.carpeta.resolve("imagenes/referencia_metodo.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/referencia_campo.png",
				Statics.carpeta.resolve("imagenes/referencia_campo.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/sabalifecruel.png",
				Statics.carpeta.resolve("imagenes/sabalifecruel.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/sakura_riddle.png",
				Statics.carpeta.resolve("imagenes/sakura_riddle.png").toFile());

		copiarACarpetaDesdeJar("/imagenes/sao.png", Statics.carpeta.resolve("imagenes/sao.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/amane_kanata.png",
				Statics.carpeta.resolve("imagenes/amane_kanata.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/rima_evenstar.png",
				Statics.carpeta.resolve("imagenes/rima_evenstar.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/maid_mint.png", Statics.carpeta.resolve("imagenes/maid_mint.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/sylent_bell.png",
				Statics.carpeta.resolve("imagenes/sylent_bell.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/onepeice.png", Statics.carpeta.resolve("imagenes/onepeice.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/rei_bubbles.png",
				Statics.carpeta.resolve("imagenes/rei_bubbles.png").toFile());
		copiarACarpetaDesdeJar("/imagenes/panko.png", Statics.carpeta.resolve("imagenes/panko.png").toFile());

//		new File(viajo_ultima_mods.toString()).delete();
//		try {
//			new File(viajo_ultima_mods.toString()).createNewFile();
//			FileWriter escribidor = new FileWriter(viajo_ultima_mods.toFile());
//			escribidor.write(mods);
//			escribidor.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		StringBuilder actuales = new StringBuilder();
		boolean primera = true;

		for (Path ub : Statics.carpetas_de_mods) {
			File dir = ub.toFile();

			if (!dir.exists() || !dir.isDirectory()) {
				CrashDetectorLogger.log(dir.getAbsolutePath() + idioma.carpeta_de_mods_no_valido());
				continue;
			}

			File[] archivosMods = dir.listFiles();
			if (archivosMods == null) {
				CrashDetectorLogger.log(dir.getAbsolutePath() + idioma.carpeta_de_mods_no_valido());
				continue;
			}

			for (File archivoMod : archivosMods) {
				if (archivoMod.isFile()) {
					if (primera) {
						primera = false;
					} else {
						actuales.append(nl);
					}

					String pathCompleta = archivoMod.getAbsolutePath();
					actuales.append(pathCompleta);
				}
			}
		}

		File um_archivo = new File(ultimo_mods.toString());
		um_archivo.delete();
		try {
			um_archivo.createNewFile();
			FileWriter escribidor = new FileWriter(ultimo_mods.toFile());
			escribidor.write(actuales.toString());
			escribidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// cargardor de extenciones aqui
		CargadorExtensiones.cargarExtensionesProcesoApp(um_archivo);
		Entregar.comenzarEntregar();
		// Consola.escribirMapa(Instant.now());

		long pid = obtenerPID();
		System.out.println("PID: " + pid);
		String jar;

		try {
			URI uriJar = MonitorDePID.class.getProtectionDomain().getCodeSource().getLocation().toURI();
			String uriJarString = uriJar.toString();

			if (uriJarString.startsWith("union:")) {// Para Modlauncher
				uriJarString = uriJarString.replace("union:", "file://");
			}

			if (uriJarString.startsWith("jar:")) {
				uriJarString = uriJarString.substring(4); // elimnar "jar:"
			}

			CrashDetectorLogger.log(uriJarString);
			URI cd_uri = new URI(uriJarString);
			String cd_uri_path = cd_uri.getPath();
			CrashDetectorLogger.log(cd_uri_path);
			jar = new File(cd_uri_path).getAbsolutePath().split(".jar")[0] + ".jar";
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(idioma.no_se_donde_esta_jar());
			return;
		}

		// Get the Java binary path
		String javaBinary = jvm();
		if (javaBinary == null || javaBinary.isEmpty()) {
			System.err.println("CD NO PUEDE OBTENER JVM");
			return;
		} else {
			System.out.println("JVM " + javaBinary);
		}

		// Launch the child monitor process
		try {
			String cp = obtenerClassPath(jar);
			// System.out.println("******************" + cp);

			ProcessBuilder pb = new ProcessBuilder(javaBinary, obtenerXMXPorMitadDeRAM(),
					// "-XX:MaxJavaStackTraceDepth=1000000",
					"-cp", cp, "com.asbestosstar.crashdetector.MonitorDePID", "--monitor", String.valueOf(pid))
			// .inheritIO()
			;

			// System.out.println("classpath"+cp);
			// pb.redirectError(new File(CrashDetectorLogger.LOG_ERR_FILE_PATH));
			// pb.redirectOutput(new File(CrashDetectorLogger.LOG_FILE_PATH));

			pb.start();
		} catch (Exception e) {
			System.out.println("error con comenzando el proceso CD");
			e.printStackTrace();
		}
		System.out.println("completa con comenzando el proceso CD");

	}

	public static String obtenerClassPath(String jar) {
		// ClassPath base de la JVM actual
		StringBuilder classPath = new StringBuilder(System.getProperty("java.class.path"));

		// Añadir el JAR proporcionado si no es null/vacío
		if (jar != null && !jar.isEmpty()) {
			classPath.append(File.pathSeparator).append(jar);
		}

		// Intentar añadir CFR si está instalado
		try {
			File cfrJar = BuscarParaCFR.encontrarCfr(); // Usando BuscarParaCFR para obtener el JAR de CFR
			if (cfrJar != null && cfrJar.exists()) {
				String rutaCfr = cfrJar.getAbsolutePath();
				// Evitar duplicarlo en el classpath
				if (!classPath.toString().contains(rutaCfr)) {
					classPath.append(File.pathSeparator).append(rutaCfr);
				}
			}
		} catch (Throwable t) {
			// En caso de error con la búsqueda del JAR de CFR, se ignora
		}

		return classPath.toString();
	}

	public static void registrarGUISPredeterminado() {
		// TODO Auto-generated method stub

		TipoGUI.PRINCIPAL.registrarGUI(PrincipalGUIEstiloLanzer.ID, () -> new PrincipalGUIEstiloLanzer());
		TipoGUI.CONFIG_PANEL.registrarGUI(ConfigPanelEstiloTL.ID, () -> new ConfigPanelEstiloTL());
		TipoGUI.TODOS_QUICKFIXES.registrarGUI(PanelQuickFixDemonSlayers.ID, () -> new PanelQuickFixDemonSlayers());
		TipoGUI.NO_REGISTRO_LANZER.registrarGUI(NoRegistroDeLauncherVShojo.ID, () -> new NoRegistroDeLauncherVShojo());
		TipoGUI.ESCANER_MCREATOR.registrarGUI(EscanerMCreatorGUIRosemiLoveLock.ID,
				() -> new EscanerMCreatorGUIRosemiLoveLock());
		TipoGUI.LECTADOR_DE_CONSOLAS.registrarGUI(LectadorDeConsolasHoloTalk.ID,
				() -> new LectadorDeConsolasHoloTalk());
		TipoGUI.HISTORIA_DE_MODS.registrarGUI(HistoriaModsGUILegacy.ID, () -> new HistoriaModsGUILegacy());
		TipoGUI.HISTORIA_DE_MODS.registrarGUI(ClioOfficeGUI.ID, () -> new ClioOfficeGUI());
		TipoGUI.GREPR.registrarGUI(BusquedaGUISaliorMoon.ID, () -> new BusquedaGUISaliorMoon());
		TipoGUI.EDITOR_FIRMAS.registrarGUI(EditorCodiceGUIIronMouse.ID, () -> new EditorCodiceGUIIronMouse());
		// TipoGUI.PRINCIPAL.registrarGUI(DialogoCompartir.ID, ()->new
		// DialogoCompartir());
		TipoGUI.ARBOL_DE_MODS.registrarGUI(ArbolDeModsGUIHamu.ID, () -> new ArbolDeModsGUIHamu());
		TipoGUI.EDITOR_PLANTILLA.registrarGUI(EditorPlantillaPredeterminado.ID,
				() -> new EditorPlantillaPredeterminado());
		TipoGUI.EDITOR_PLANTILLA.registrarGUI(EditorPlantillaModioNoche.ID, () -> new EditorPlantillaModioNoche());
		TipoGUI.DIALOGO_COMPARTIR.registrarGUI(DialogoCompartirLegacy.ID, () -> new DialogoCompartirLegacy());
		TipoGUI.EDITOR_GUI.registrarGUI(CDSkinCape.ID, () -> new CDSkinCape());
		TipoGUI.MOD_API_PANEL.registrarGUI(CDModsEstiloTL.ID, () -> new CDModsEstiloTL());
		TipoGUI.CFR.registrarGUI(CfrSakuraRiddle.ID, () -> new CfrSakuraRiddle());
		TipoGUI.CORPO.registrarGUI(CorpoSAO.ID, () -> new CorpoSAO());
		TipoGUI.DESHABLICAR_VERIFICACIONES.registrarGUI(DeshabilitarVerificacionesAmaneKanata.ID,
				() -> new DeshabilitarVerificacionesAmaneKanata());
		TipoGUI.LANZER_MALO.registrarGUI(LanzerMaloGUISylentBell.ID, () -> new LanzerMaloGUISylentBell());
		TipoGUI.LANZER_BUENO.registrarGUI(LanzerBuenoGUIMaidMint.ID, () -> new LanzerBuenoGUIMaidMint());
		TipoGUI.MODS_MALAS.registrarGUI(GUIModsMalasRimaEvenstar.ID, () -> new GUIModsMalasRimaEvenstar());
		TipoGUI.MODS_BUENAS.registrarGUI(ModsBuenasGUIReiBubbles.ID, () -> new ModsBuenasGUIReiBubbles());
		TipoGUI.MIRANDA.registrarGUI(DerechosPiratasGUIOnePiece.ID, () -> new DerechosPiratasGUIOnePiece());
		TipoGUI.ANTI_MANIPULACION.registrarGUI(AntiManipulacionGUIPanko.ID, () -> new AntiManipulacionGUIPanko());
		TipoGUI.LFPDPPP.registrarGUI(LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
				() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos());
		TipoGUI.APLIC.registrarGUI(ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn.ID,
				() -> new ActaDeProteccionDelIdiomaCulturalDePyongyangGUIKimJongUn());
		TipoGUI.CANARIO.registrarGUI(CanarioDeOrdenJudicialGUI1984.ID, () -> new CanarioDeOrdenJudicialGUI1984());
		TipoGUI.CONSOLA_DESARROLLADOR.registrarGUI(ConsolaDesarrolladorGUITL.ID, () -> new ConsolaDesarrolladorGUITL());
		TipoGUI.QUICKFIX.registrarGUI(ElementoQuickFixDemonSlayers.ID, () -> new ElementoQuickFixDemonSlayers());

	}

	private static void monitor_proceso(long pid) {
		// List<Consola> consolas_sin_processando = Consola.obtenerConsolas();
		abrirConsola();
		ProxySysOutSysErrCDProceso.init();
		MonitorDePID.pid = pid;
		try {
			Transformaciones.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ultimo_mods.toFile().exists()) {
			CargadorExtensiones.cargarExtensionesProcesoMonitor(ultimo_mods.toFile());
		}

		System.out.println(idioma.buscando_para_pid(pid));
		Entregar.recibir();

		CountDownLatch latch = new CountDownLatch(1); // Necesito por que sin esta preceso esta muerte

		while (true) {

			if (!viva(pid)) {

				// System.out.println( escribes el codio de error aqui );

				System.out.println(idioma.pid_esta_muerto(pid));
				System.out.println(mensaje_de_registro_lanzer_completo);
				CrashDetectorLogger.log(idioma.pid_esta_muerto(pid));

				CrashDetectorLogger.log("Finalizando Contento de Consolas");

				// if (duration.getSeconds() >= 25) {// Para las consolas completa
				// } else {

				List<Consola> consolas_sin_processando = Consola.obtenerConsolas();

				if (!ArchivoDeCodigoError0.exists() && !Consola.tiene_registro_de_launcher(consolas_sin_processando)) {
					try {// Cuando tiene una informe de crash esta codio 0 y tiene tiempo para esperar
						Thread.sleep(500);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}

				// }

				for (Consola consola : consolas_sin_processando) {
					consola.finalizarContenido(utc, false);
					if (consola.nueva) {
						consolas.add(consola);
					}
				}

				if (activar() && !GraphicsEnvironment.isHeadless()) {
					if (!Consola.tiene_registro_de_launcher(consolas)) {
						obtenerConsolaDeLauncher(utc);
					}
				}

				historia_mods();

				Instant luego = Instant.now();
				recargar(true, luego);
				System.gc();

				if (activar()) {
					CrashDetectorLogger.log("activar ");

					if (GraphicsEnvironment.isHeadless()) {
						CrashDetectorLogger.log("headless ");

						System.out.println(idioma.local_headless(enlace));
						fin(latch);

					} else {
						registrarCanariosPorDefecto();

						CrashDetectorLogger.log("no headless ");

						final PrincipalGUI gui = TipoGUI.PRINCIPAL.obtenerGUIPredeterminado(PrincipalGUIEstiloLanzer.ID,
								() -> {
									return new PrincipalGUIEstiloLanzer();
								});
						CrashDetectorLogger.log("tiene principal gui");

						SwingUtilities.invokeLater(() ->

						gui.constructir(utc, latch)

						);
					}

				} else {
					fin(latch);
				}

//				try {
//					latch.await(); // Muerte cunado el popup se cerrada
//				} catch (InterruptedException e) {
//					Thread.currentThread().interrupt();
//				}

				break;

			}

//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				break;
//			}
		}
	}

//	private static void showPopupMessage(String message, CountDownLatch latch) {
//		SwingUtilities.invokeLater(() -> {
//			JOptionPane pane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
//			JDialog dialog = pane.createDialog("Proceso Crash Detector");
//			dialog.setModal(true);
//			dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//				@Override
//				public void windowClosed(java.awt.event.WindowEvent e) {
//					latch.countDown(); // Release the latch when the dialog is closed
//				}
//			});
//			dialog.setVisible(true);
//		});
//	}

	public static void abrirConsola() {
		// Check if the environment is not headless
		if (!GraphicsEnvironment.isHeadless() && consola_des == null) {
			if (ConfigMundial.obtenerInstancia().obtenerConsolaDesarrollo()) {
				ConsolaDesarrolladorGUI cons = TipoGUI.CONSOLA_DESARROLLADOR
						.obtenerGUIPredeterminado(ConsolaDesarrolladorGUITL.ID, () -> new ConsolaDesarrolladorGUITL());

				consola_des = cons;
				cons.init();
			}
		} else {
			System.out.println("No se puede abrir la consola: se ha detectado un entorno sin interfaz gráfica.");
		}
	}

	private static void registrarCanariosPorDefecto() {
		// TODO Auto-generated method stub
		CanarioDeOrdenJudicial.canarios.add(new CDPasteAsbestosstarEgoismJPCanario());
		CanarioDeOrdenJudicial.canarios.add(new CDInformesAsbestosstarEgoismJPCanario());
	}

	public static void fin(CountDownLatch latch) {
		// TODO Auto-generated method stub

		ArchivoDeCodigoError0.delete();
		// viajo_ultima_mods.toFile().delete();
		latch.countDown();
		System.exit(0);
	}

	public static void historia_mods() {

		try {
			Path directorioHistorial = Statics.carpeta.resolve("historia_mods");
			Files.createDirectories(directorioHistorial);

			File[] archivosHistorial = directorioHistorial.toFile().listFiles();
			int siguienteNumero = 0;
			if (archivosHistorial != null) {
				for (File archivo : archivosHistorial) {
					String nombre = archivo.getName();
					if (nombre.matches("\\d{6}\\.falla") || nombre.matches("\\d{6}\\.exito")) {
						int num = Integer.parseInt(nombre.substring(0, 6));
						if (num >= siguienteNumero)
							siguienteNumero = num + 1;
					}
				}
			}

			String extension = activar() ? "falla" : "exito";
			String nombreArchivo = String.format("%06d.%s", siguienteNumero, extension);
			Path archivoHistorial = directorioHistorial.resolve(nombreArchivo);

			if (ultimo_mods.toFile().exists()) {
				String contenido = new String(Files.readAllBytes(ultimo_mods), StandardCharsets.UTF_8);
				Files.write(archivoHistorial, contenido.getBytes(StandardCharsets.UTF_8));
			}
		} catch (IOException e) {
			CrashDetectorLogger.log("Error al crear historial de modlist: " + e.getMessage());
		}
	}

	/**
	 * asegura los registros son completa para 20 segundos
	 * 
	 * @param luego el tiempo ACTUAL AHORA
	 */
	private static void finalizarConsolasLentas(Instant luego) {
		// TODO Auto-generated method stub

		if (Consola.tiene_registro_de_launcher(consolas) && !consola_de_launcher_inyectado) {
			Duration duration = Duration.between(luego, Instant.now());

			// while (!tiene_mensaje_de_registro_launcher_completa && duration.getSeconds()
			// < 20) {// TODO Config para
			// tiempo
			CrashDetectorLogger.log("reincinar finalizacion " + duration.getSeconds());
			// duration = Duration.between(luego, Instant.now());
			for (Consola consola : consolas) {
				consola.finalizarContenido(utc, false);
			}

			// }

		}

	}

	public static String analizar(List<Consola> consolas) {
		analizador = new Analizador();
		analizador.analizar(consolas);
		return analizador.toString();
	}

	// Compatible approach (Java 7+)
	public static String leer_archivo(Path path) throws IOException {
		return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
	}

	public static boolean activar() {
		for (Consola cons : consolas) {
			if (cons.archivo.toString().contains("crash-reports")) {
				return true;
			}
			for (Verificaciones ver : analizador.verificaciones) {
				if (ver.activado() && ver.anularNormal()) {
					return true;
				}

			}

		}
		if (ArchivoDeCodigoError0.exists()) {
			return false;
		}
		return true;
	}

	public static void obtenerConsolaDeLauncher(Instant utc) {
		// para detener el programa
		JFrame frame_blanco = new JFrame();
		frame_blanco.setUndecorated(true);
		frame_blanco.setSize(0, 0);
		frame_blanco.setLocationRelativeTo(null);
		frame_blanco.setVisible(true);

		CountDownLatch templach = new CountDownLatch(1);
		final NoRegistroLanzadorGUI noreg = TipoGUI.NO_REGISTRO_LANZER
				.obtenerGUIPredeterminado(NoRegistroDeLauncherVShojo.ID, () -> {
					return new NoRegistroDeLauncherVShojo();
				});

		SwingUtilities.invokeLater(() -> {
			noreg.preparar(frame_blanco, utc);
			noreg.init();
			noreg.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosed(java.awt.event.WindowEvent e) {
					templach.countDown();
				}
			});

		});

		try {
			templach.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		frame_blanco.dispose();
	}

	private static void mostrarAyudaCLI() {
		System.out.println("Uso: java -jar CrashDetectorMC.jar [grepr|fgrepr] [-i] <cadena> [directorio]");
		System.out.println("  grepr: Busca usando regex (por defecto)");
		System.out.println("  fgrepr: Busca sin regex");
		System.out.println("  -i: Ignorar mayúsculas/minúsculas");
	}

	/**
	 * Obtiene el PID actual usando RuntimeMXBean (Java 8 compatible)
	 */
	public static long obtenerPID() {
		String nombre = ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(nombre.split("@")[0]);
	}

	public static String jvm() {
		// Step 1: Try using ProcessHandle via reflection (Java 9+)
		try {
			Class<?> processHandleClass = Class.forName("java.lang.ProcessHandle");
			java.lang.reflect.Method ofMethod = processHandleClass.getMethod("of", long.class);
			Object optionalHandle = ofMethod.invoke(null, obtenerPID());

			if (optionalHandle != null) {
				java.lang.reflect.Method isPresentMethod = optionalHandle.getClass().getMethod("isPresent");
				boolean isPresent = (boolean) isPresentMethod.invoke(optionalHandle);

				if (isPresent) {
					java.lang.reflect.Method getMethod = optionalHandle.getClass().getMethod("get");
					Object processHandle = getMethod.invoke(optionalHandle);

					java.lang.reflect.Method infoMethod = processHandleClass.getMethod("info");
					Object processInfo = infoMethod.invoke(processHandle);

					Class<?> infoClass = Class.forName("java.lang.ProcessHandle$Info");
					java.lang.reflect.Method commandMethod = infoClass.getMethod("command");
					Object commandOpt = commandMethod.invoke(processInfo);

					if (commandOpt != null) {
						java.lang.reflect.Method isPresentOpt = commandOpt.getClass().getMethod("isPresent");
						boolean commandPresent = (boolean) isPresentOpt.invoke(commandOpt);

						if (commandPresent) {
							java.lang.reflect.Method getOpt = commandOpt.getClass().getMethod("get");
							return (String) getOpt.invoke(commandOpt);
						}
					}
				}
			}
		} catch (Exception ignored) {
			// Java viaja
		}

		return obtenerRutaEjecutable8();

	}

	/**
	 * Obtiene la ruta completa del ejecutable del proceso actual
	 * 
	 * @return Ruta completa del ejecutable o null si no se encuentra
	 */
	private static String obtenerRutaEjecutable8() {
		try {
			String sistemaOperativo = System.getProperty("os.name").toLowerCase();

			if (sistemaOperativo.contains("win")) {
				// Usar PowerShell primero (más confiable que wmic)
				ProcessBuilder pb = new ProcessBuilder("powershell",
						"Get-WmiObject -Class Win32_Process -Filter \"ProcessId = " + obtenerPID()
								+ "\" | Select-Object -ExpandProperty ExecutablePath");

				Process proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {

					String linea;
					while ((linea = lector.readLine()) != null) {
						String limpiado = linea.trim();
						if (!limpiado.isEmpty()) {
							// Devolver la ruta completa del ejecutable
							return limpiado;
						}
					}
				}

				// Si PowerShell falla, usar tasklist como último recurso
				pb = new ProcessBuilder("tasklist", "/FI", "PID eq " + obtenerPID(), "/FO", "CSV", "/NH");
				proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
					String linea;
					while ((linea = lector.readLine()) != null) {
						if (!linea.trim().isEmpty()) {
							String[] valores = new CSVParser().parsear(linea);
							if (valores.length > 0 && !valores[0].trim().isEmpty()) {
								return valores[0].trim(); // Nombre del ejecutable
							}
						}
					}
				}
			} else {
				// Unix/Linux/macOS: Usar ps para obtener el comando
				ProcessBuilder pb = new ProcessBuilder("ps", "-p", String.valueOf(obtenerPID()), "-o", "comm=");
				Process proceso = pb.start();
				try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
					String linea;
					while ((linea = lector.readLine()) != null) {
						String limpiado = linea.trim();
						if (!limpiado.isEmpty()) {
							return limpiado;
						}
					}
				}
			}

			// No se encontró información del proceso
			return null;

		} catch (Exception e) {
			// Registrar errores para diagnóstico
			System.err.println("Error al obtener ruta ejecutable: " + e.getMessage());
			return null;
		}
	}

	public static boolean viva(long pid) {
		try {
			// Intentar usar ProcessHandle (Java 9+)
			Class<?> processHandleClase = Class.forName("java.lang.ProcessHandle");
			java.lang.reflect.Method meth = processHandleClase.getMethod("of", long.class);
			Object optionalHandle = meth.invoke(null, pid);

			if (optionalHandle != null) {
				java.lang.reflect.Method isPresentMethod = optionalHandle.getClass().getMethod("isPresent");
				boolean existe = (boolean) isPresentMethod.invoke(optionalHandle);

				if (existe) {
					java.lang.reflect.Method getMethod = optionalHandle.getClass().getMethod("get");
					Object processHandle = getMethod.invoke(optionalHandle);

					java.lang.reflect.Method estaViviaMeth = processHandle.getClass().getMethod("isAlive");
					return (boolean) estaViviaMeth.invoke(processHandle);
				}
			}
			return false;
		} catch (Exception e) {
			// Si falla (Java 8 o ProcessHandle no disponible), usa el método de comandos
			// del sistema
			return viva8(pid);
		}
	}

	public static boolean viva8(long pid) {
		try {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				// Intentar con PowerShell primero
				ProcessBuilder pb = new ProcessBuilder("powershell",
						"try { Get-Process -Id " + pid + " -ErrorAction Stop; exit 0 } catch { exit 1 }");

				Process proceso = pb.start();
				int codigoSalida = proceso.waitFor();

				if (codigoSalida == 0) {
					return true; // El proceso existe
				}

				// Si PowerShell falla, usar tasklist como respaldo
				// return verificarConTasklist(pid);
			} else {
				// En Unix/Linux/macOS, usar ps
				ProcessBuilder pb = new ProcessBuilder("ps", "-p", String.valueOf(pid));
				Process proceso = pb.start();
				int codigoSalida = proceso.waitFor();
				return codigoSalida == 0;
			}
		} catch (Exception e) {
			// Error al ejecutar el comando, asumir que el proceso no existe
			return false;
		}
		return false;

	}

	/**
	 * Verifica si un proceso está activo usando tasklist en Windows
	 * 
	 * @param pid El ID del proceso a verificar
	 * @return true si el proceso existe y está activo
	 * @throws IOException Si hay un error al ejecutar el comando
	 */
//	private static boolean verificarConTasklist(long pid) throws IOException, InterruptedException {
//	    ProcessBuilder pb = new ProcessBuilder("tasklist", "/FI", "PID eq " + pid, "/FO", "CSV", "/NH");
//	    Process proceso = pb.start();
//	    try (BufferedReader reader = new BufferedReader(
//	            new InputStreamReader(proceso.getInputStream()))) {
//	        
//	        String linea;
//	        while ((linea = reader.readLine()) != null) {
//	            String limpiado = linea.trim();
//	            // Si la salida contiene información del proceso, está activo
//	            if (!limpiado.isEmpty() && !limpiado.startsWith("INFO:") && !limpiado.contains("No tasks")) {
//	                return true;
//	            }
//	        }
//	    }
//	    
//	    // Si no se encontró el proceso
//	    return false;
//	}

	/**
	 * Copia un recurso desde el JAR a una ubicación en el disco, preservando
	 * archivos existentes válidos
	 * 
	 * @param ubicacionEnJar Ruta del recurso dentro del JAR
	 * @param resultado      Archivo de destino en el sistema de archivos
	 */
	public static void copiarACarpetaDesdeJar(String ubicacionEnJar, File resultado) {
		// Verificar si el archivo ya existe y es válido (con contenido)
		if (resultado.exists()) {
			// Si el archivo es válido (tamaño mayor a cero), no hacer nada
			if (resultado.length() > 0) {
				return;
			}

			// Si el archivo existe pero está vacío (0 bytes), registrar y eliminar para
			// recrearlo
			if (resultado.length() == 0) {
				if (!resultado.delete()) {
					CrashDetectorLogger
							.log("No se pudo eliminar el archivo vacío para recrearlo: " + resultado.getAbsolutePath());
					return;
				}
			}
		} else {
			// Si el archivo no existe, crear directorios necesarios
			resultado.getParentFile().mkdirs();
		}

		File archivoTemporal = null;
		try {
			CrashDetectorLogger.log("Cargando imagen " + resultado.getAbsolutePath());
			// Preparar archivo temporal
			archivoTemporal = new File(resultado.getParentFile(), resultado.getName() + ".tmp");

			try (InputStream inputStream = MonitorDePID.class.getResourceAsStream(ubicacionEnJar);
					FileOutputStream outputStream = new FileOutputStream(archivoTemporal)) {

				if (inputStream == null) {
					throw new RuntimeException("Recurso no encontrado en el JAR: " + ubicacionEnJar);
				}

				byte[] buffer = new byte[8192]; // Buffer más grande para mayor eficiencia
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}

			// Validar que el archivo temporal se escribió correctamente
			if (archivoTemporal.length() == 0) {
				throw new IOException("El recurso se escribió como archivo vacío: " + ubicacionEnJar);
			}

			// Mover el archivo temporal al destino final (reemplaza solo si es necesario)
			if (resultado.exists() && !resultado.delete()) {
				CrashDetectorLogger
						.log("No se pudo eliminar archivo previo para reemplazar: " + resultado.getAbsolutePath());
				return;
			}

			if (!archivoTemporal.renameTo(resultado)) {
				Files.copy(archivoTemporal.toPath(), resultado.toPath(), StandardCopyOption.REPLACE_EXISTING);
				Files.delete(archivoTemporal.toPath());
			}
		} catch (Exception e) {
			CrashDetectorLogger.log("Error al extraer recurso " + ubicacionEnJar + " al disco: " + e.getMessage());
			if (archivoTemporal != null && archivoTemporal.exists()) {
				try {
					Files.delete(archivoTemporal.toPath());
				} catch (IOException ex) {
					CrashDetectorLogger
							.log("No se pudo limpiar el archivo temporal: " + archivoTemporal.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 
	 * @param finalizar_contento si quires finalizar_contento
	 * @param luego              el tiempo ACTUAL AHORA, Solo necesitas si quieres
	 *                           finalizar_contento
	 */
	public static void recargar(boolean finalizar_contento, Instant luego) {
		StringBuilder constructor = new StringBuilder();
		Buscardor.mods.clear();
		Buscardor.cargado = false;
		Buscardor.cargadotodos = false;
		if (finalizar_contento) {
			finalizarConsolasLentas(luego);
		}
		for (Consola consola : consolas) {
			consola.verificacion_de_stacktrace = new VerificacionDeStackTrace(consola);
			consola.enlaces.clear();
		}
		CrashDetectorLogger.log("Analyzador Consolas");

		String res = analizar(consolas);

		if (res.replace(" ", "").equals("")) {
			constructor.append(idioma.noResultados());
		} else {
			constructor.append(res);
			resultados = true;
		}

		// CrashDetectorLogger.log("resultdos " + res);
		contenidoInforme = constructor;
		local = GeneradorDeInformacion.generarLocal(consolas, utc).getAbsolutePath();
	}

	/**
	 * Obtiene una cadena de argumento -Xmx basada en la mitad de la memoria RAM
	 * total del sistema.
	 * 
	 * @return cadena en formato "-XmxNNNNm" donde NNNN es la mitad de la RAM en
	 *         megabytes, redondeada hacia abajo. Si no se puede detectar la RAM,
	 *         devuelve "-Xmx4096m" (4 GB).
	 */
	public static String obtenerXMXPorMitadDeRAM() {
		try {
			// https://stackoverflow.com/questions/950754/how-do-i-find-the-physical-memory-size-in-java
			com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory
					.getOperatingSystemMXBean();
			long memoriaTotalBytes = os.getTotalPhysicalMemorySize();

			// Si la memoria no está disponible (valor -1 o <= 0), usar valor predeterminado
			if (memoriaTotalBytes <= 0) {
				System.out.println("0 ram");
				return "-Xmx4096m"; // 4 GB por defecto
			}

			// Calcular la mitad de la memoria total
			long mitadMemoriaBytes = memoriaTotalBytes / 2;

			// Convertir a megabytes (1 MB = 1024 * 1024 bytes)
			long mitadMemoriaMB = mitadMemoriaBytes / (1024 * 1024);

			// Asegurar un mínimo razonable (ej. 512 MB)
			if (mitadMemoriaMB < 512) {
				mitadMemoriaMB = 512;
			}

			// Formatear como argumento de JVM
			System.out.println("ram " + mitadMemoriaMB);
			return "-Xmx" + mitadMemoriaMB + "m";

		} catch (Exception e) {
			// En caso de error (ej. clase no disponible en alguna JVM), usar valor seguro
			System.out.println("fgb ram");
			return "-Xmx4096m";
		}
	}

}