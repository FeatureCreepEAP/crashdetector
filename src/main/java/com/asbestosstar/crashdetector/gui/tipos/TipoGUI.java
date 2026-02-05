package com.asbestosstar.crashdetector.gui.tipos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.Icon;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.antimanipulacion.AntiManipulacionGUI;
import com.asbestosstar.crashdetector.gui.tipos.aplic.ActaDeProteccionDelIdiomaCulturalDePyongyangGUI;
import com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUI;
import com.asbestosstar.crashdetector.gui.tipos.canario.CanarioDeOrdenJudicialGUI;
import com.asbestosstar.crashdetector.gui.tipos.cdlauncher.CDLauncherGUI;
import com.asbestosstar.crashdetector.gui.tipos.cfr.CfrBase;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartir;
import com.asbestosstar.crashdetector.gui.tipos.config.ConfigPanel;
import com.asbestosstar.crashdetector.gui.tipos.consola.ConsolaDesarrolladorGUI;
import com.asbestosstar.crashdetector.gui.tipos.corpo.CorpoBase;
import com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones.DeshablicarVerificaciones;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorFirmasGUI;
import com.asbestosstar.crashdetector.gui.tipos.editor_plantilla.EditorPlantilla;
import com.asbestosstar.crashdetector.gui.tipos.editorgui.EditorGUI;
import com.asbestosstar.crashdetector.gui.tipos.grepr.GrepRGUI;
import com.asbestosstar.crashdetector.gui.tipos.historia.HistoriaDeModsGUI;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos.LanzerBuenoGUI;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos.LanzerMaloGUI;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI;
import com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUI;
import com.asbestosstar.crashdetector.gui.tipos.miranda.DerechosPiratasGUI;
import com.asbestosstar.crashdetector.gui.tipos.modapi.PanelAPIBase;
import com.asbestosstar.crashdetector.gui.tipos.modsbuenas.ModsBuenasGUI;
import com.asbestosstar.crashdetector.gui.tipos.modsmalas.GUIModsMalas;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroLanzadorGUI;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.QuickFixGUI;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.TodosQuickFixesGUI;

public abstract class TipoGUI<T extends CrashDetectorGUI> {

	/**
	 * Para todos GUIS por favor registrar un tipo
	 */
	public static List<TipoGUI> TIPOS_DE_GUI = new ArrayList<>();

	/**
	 * La GUI Principal
	 */
	public static TipoGUI<PrincipalGUI> PRINCIPAL = new TipoGUI<PrincipalGUI>() {
		@Override
		public String etiquetaDelBoton() {
			return "GUI";
		}

		@Override
		public String id() {
			return "principal";
		}

		@Override
		public void registrarGUI(String id, Supplier<PrincipalGUI> gui) {
			PrincipalGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<PrincipalGUI>> obtenerGUIs() {
			return PrincipalGUI.GUIS;
		}
	};

	/**
	 * La GUI de GrepR
	 */
	public static TipoGUI<GrepRGUI> GREPR = new TipoGUI<GrepRGUI>() {
		@Override
		public String id() {
			return "grepr";
		}

		@Override
		public String etiquetaDelBoton() {
			return "grepr/fgrepr";
		}

		@Override
		public void registrarGUI(String id, Supplier<GrepRGUI> gui) {
			GrepRGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<GrepRGUI>> obtenerGUIs() {
			return GrepRGUI.GUIS;
		}
	};

	/**
	 * Para Árbol de Mods
	 */
	public static TipoGUI<ArbolDeModsGUI> ARBOL_DE_MODS = new TipoGUI<ArbolDeModsGUI>() {
		@Override
		public String id() {
			return "arbol_de_mods";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.arbolDeMods();
		}

		@Override
		public void registrarGUI(String id, Supplier<ArbolDeModsGUI> gui) {
			ArbolDeModsGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<ArbolDeModsGUI>> obtenerGUIs() {
			return ArbolDeModsGUI.GUIS;
		}
	};

	/**
	 * Diálogo Compartir
	 */
	public static TipoGUI<DialogoCompartir> DIALOGO_COMPARTIR = new TipoGUI<DialogoCompartir>() {
		@Override
		public String id() {
			return "dialogo_compartir";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.botonDeCompartirInforme();
		}

		@Override
		public void registrarGUI(String id, Supplier<DialogoCompartir> gui) {
			DialogoCompartir.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<DialogoCompartir>> obtenerGUIs() {
			return DialogoCompartir.GUIS;
		}
	};

	/**
	 * Editor de firmas de razones personalizadas
	 */
	public static TipoGUI<EditorFirmasGUI> EDITOR_FIRMAS = new TipoGUI<EditorFirmasGUI>() {
		@Override
		public String id() {
			return "editor_firmas";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.editorCodiceBoton();
		}

		@Override
		public void registrarGUI(String id, Supplier<EditorFirmasGUI> gui) {
			EditorFirmasGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<EditorFirmasGUI>> obtenerGUIs() {
			return EditorFirmasGUI.GUIS;
		}
	};

	/**
	 * GUI con TODOS los QuickFix
	 */
	public static TipoGUI<TodosQuickFixesGUI> TODOS_QUICKFIXES = new TipoGUI<TodosQuickFixesGUI>() {
		@Override
		public String id() {
			return "quickfix_todos";
		}

		@Override
		public String etiquetaDelBoton() {
			return "QuickFix";
		}

		@Override
		public void registrarGUI(String id, Supplier<TodosQuickFixesGUI> gui) {
			TodosQuickFixesGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<TodosQuickFixesGUI>> obtenerGUIs() {
			return TodosQuickFixesGUI.GUIS;
		}
	};

	/**
	 * GUI con QuickFix individuales
	 */
	public static TipoGUI<QuickFixGUI> QUICKFIX = new TipoGUI<QuickFixGUI>() {
		@Override
		public String id() {
			return "quickfix";
		}

		@Override
		public String etiquetaDelBoton() {
			return "QuickFix";
		}

		@Override
		public void registrarGUI(String id, Supplier<QuickFixGUI> gui) {
			QuickFixGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<QuickFixGUI>> obtenerGUIs() {
			return QuickFixGUI.GUIS;
		}
	};

	/**
	 * Escáner de MCreator
	 */
	public static TipoGUI<EscanerMCreatorGUI> ESCANER_MCREATOR = new TipoGUI<EscanerMCreatorGUI>() {
		@Override
		public String id() {
			return "escaner_mcreator";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.escanearDeMCreator();
		}

		@Override
		public void registrarGUI(String id, Supplier<EscanerMCreatorGUI> gui) {
			EscanerMCreatorGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<EscanerMCreatorGUI>> obtenerGUIs() {
			return EscanerMCreatorGUI.GUIS;
		}
	};

	/**
	 * Historia de Mods
	 */
	public static TipoGUI<HistoriaDeModsGUI> HISTORIA_DE_MODS = new TipoGUI<HistoriaDeModsGUI>() {
		@Override
		public String id() {
			return "historia_de_mods";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.historialDeMods();
		}

		@Override
		public void registrarGUI(String id, Supplier<HistoriaDeModsGUI> gui) {
			HistoriaDeModsGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<HistoriaDeModsGUI>> obtenerGUIs() {
			return HistoriaDeModsGUI.GUIS;
		}
	};

	/**
	 * Lectador de Consolas
	 */
	public static TipoGUI<LectadorDeConsolasGUI> LECTADOR_DE_CONSOLAS = new TipoGUI<LectadorDeConsolasGUI>() {
		@Override
		public String id() {
			return "lectador_de_consolas";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.obtenerEtiquetaBotonLectador();
		}

		@Override
		public void registrarGUI(String id, Supplier<LectadorDeConsolasGUI> gui) {
			LectadorDeConsolasGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<LectadorDeConsolasGUI>> obtenerGUIs() {
			return LectadorDeConsolasGUI.GUIS;
		}
	};

	@Deprecated
	/**
	 * No registro de Launcher
	 */
	public static TipoGUI<NoRegistroLanzadorGUI> NO_REGISTRO_LANZER = new TipoGUI<NoRegistroLanzadorGUI>() {
		@Override
		public String id() {
			return "no_registro_launcher";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.noRegistroLauncherTitulo();
		}

		@Override
		public void registrarGUI(String id, Supplier<NoRegistroLanzadorGUI> gui) {
			NoRegistroLanzadorGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<NoRegistroLanzadorGUI>> obtenerGUIs() {
			return NoRegistroLanzadorGUI.GUIS;
		}
	};

	public abstract String id();

	/**
	 * La etiqueta del botón en la barra lateral.
	 */
	public abstract String etiquetaDelBoton();

	/**
	 * Icono del botón.
	 */
	public Icon icon() {
		return null;
	}

	/**
	 * Register una GUI
	 * 
	 * @param gui
	 */
	public abstract void registrarGUI(String id, Supplier<T> gui);

	/**
	 * IDs de GUIs con Suppliers para las GUIs
	 * 
	 * @return
	 */
	public abstract Map<String, Supplier<T>> obtenerGUIs();

	public T obtenerGUIPredeterminado(String id_de_por_defecto, Supplier<T> por_defecto) {
		ConfigString str = ConfigString.de("guitipo_" + this.id(), id_de_por_defecto);
		CrashDetectorLogger.log(str.obtener());
		return obtenerGUIs().getOrDefault(str.obtener(), por_defecto).get();
	}

	/**
	 * Panal Para Config
	 */
	public static TipoGUI<ConfigPanel> CONFIG_PANEL = new TipoGUI<ConfigPanel>() {
		@Override
		public String id() {
			return "config_panel";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.config();
		}

		@Override
		public void registrarGUI(String id, Supplier<ConfigPanel> gui) {
			ConfigPanel.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<ConfigPanel>> obtenerGUIs() {
			return ConfigPanel.GUIS;
		}
	};

	/**
	 * Panal Para Editor Plantilla
	 */
	public static TipoGUI<EditorPlantilla> EDITOR_PLANTILLA = new TipoGUI<EditorPlantilla>() {
		@Override
		public String id() {
			return "editor_plantilla";
		}

		@Override
		public String etiquetaDelBoton() {
			return "Plantilla HTML";
		}

		@Override
		public void registrarGUI(String id, Supplier<EditorPlantilla> gui) {
			EditorPlantilla.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<EditorPlantilla>> obtenerGUIs() {
			return EditorPlantilla.GUIS;
		}
	};

	/**
	 * Editor de GUIs
	 */
	public static TipoGUI<EditorGUI> EDITOR_GUI = new TipoGUI<EditorGUI>() {
		@Override
		public String id() {
			return "editor_gui";
		}

		@Override
		public String etiquetaDelBoton() {
			return "Plantilla GUI";
		}

		@Override
		public void registrarGUI(String id, Supplier<EditorGUI> gui) {
			EditorGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<EditorGUI>> obtenerGUIs() {
			return EditorGUI.GUIS;
		}
	};

	/**
	 * Editor de GUIs
	 */
	public static TipoGUI<PanelAPIBase> MOD_API_PANEL = new TipoGUI<PanelAPIBase>() {
		@Override
		public String id() {
			return "cdmods";
		}

		@Override
		public String etiquetaDelBoton() {
			return "CDMods";
		}

		@Override
		public void registrarGUI(String id, Supplier<PanelAPIBase> gui) {
			PanelAPIBase.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<PanelAPIBase>> obtenerGUIs() {
			return PanelAPIBase.GUIS;
		}
	};

	/**
	 * Editor de GUIs
	 */
	public static TipoGUI<CfrBase> CFR = new TipoGUI<CfrBase>() {
		@Override
		public String id() {
			return "cfr";
		}

		@Override
		public String etiquetaDelBoton() {
			return "Cfr";
		}

		@Override
		public void registrarGUI(String id, Supplier<CfrBase> gui) {
			CfrBase.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<CfrBase>> obtenerGUIs() {
			return CfrBase.GUIS;
		}
	};

	/**
	 * Editor de CORPO CONFIG
	 */
	public static TipoGUI<CorpoBase> CORPO = new TipoGUI<CorpoBase>() {
		@Override
		public String id() {
			return "corpo";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.configuracionCorporativa();
		}

		@Override
		public void registrarGUI(String id, Supplier<CorpoBase> gui) {
			CorpoBase.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<CorpoBase>> obtenerGUIs() {
			return CorpoBase.GUIS;
		}
	};

	/**
	 * Editor de Verificaciones
	 */
	public static TipoGUI<DeshablicarVerificaciones> DESHABLICAR_VERIFICACIONES = new TipoGUI<DeshablicarVerificaciones>() {
		@Override
		public String id() {
			return "deshablicar_verificaciones";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.gestionVerificaciones();
		}

		@Override
		public void registrarGUI(String id, Supplier<DeshablicarVerificaciones> gui) {
			DeshablicarVerificaciones.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<DeshablicarVerificaciones>> obtenerGUIs() {
			return DeshablicarVerificaciones.GUIS;
		}
	};

	public static TipoGUI<LanzerMaloGUI> LANZER_MALO = new TipoGUI<LanzerMaloGUI>() {
		@Override
		public String id() {
			return "lanzer_malo";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.lanzadoresDesaconsejados();
		}

		@Override
		public void registrarGUI(String id, Supplier<LanzerMaloGUI> gui) {
			LanzerMaloGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<LanzerMaloGUI>> obtenerGUIs() {
			return LanzerMaloGUI.GUIS;
		}
	};

	public static TipoGUI<LanzerBuenoGUI> LANZER_BUENO = new TipoGUI<LanzerBuenoGUI>() {
		@Override
		public String id() {
			return "lanzer_bueno";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.lanzadoresRecomendados();
		}

		@Override
		public void registrarGUI(String id, Supplier<LanzerBuenoGUI> gui) {
			LanzerBuenoGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<LanzerBuenoGUI>> obtenerGUIs() {
			return LanzerBuenoGUI.GUIS;
		}
	};

	public static TipoGUI<GUIModsMalas> MODS_MALAS = new TipoGUI<GUIModsMalas>() {
		@Override
		public String id() {
			return "mods_malas";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.modsNoRecomendados();
		}

		@Override
		public void registrarGUI(String id, Supplier<GUIModsMalas> gui) {
			GUIModsMalas.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<GUIModsMalas>> obtenerGUIs() {
			return GUIModsMalas.GUIS;
		}
	};

	public static TipoGUI<ModsBuenasGUI> MODS_BUENAS = new TipoGUI<ModsBuenasGUI>() {
		@Override
		public String id() {
			return "mods_buenas";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.modsRecomendados();
		}

		@Override
		public void registrarGUI(String id, Supplier<ModsBuenasGUI> gui) {
			ModsBuenasGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<ModsBuenasGUI>> obtenerGUIs() {
			return ModsBuenasGUI.GUIS;
		}
	};

	public static TipoGUI<DerechosPiratasGUI> MIRANDA = new TipoGUI<DerechosPiratasGUI>() {
		@Override
		public String id() {
			return "miranda";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.derechosMiranda();
		}

		@Override
		public void registrarGUI(String id, Supplier<DerechosPiratasGUI> gui) {
			DerechosPiratasGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<DerechosPiratasGUI>> obtenerGUIs() {
			return DerechosPiratasGUI.GUIS;
		}
	};

	public static TipoGUI<AntiManipulacionGUI> ANTI_MANIPULACION = new TipoGUI<AntiManipulacionGUI>() {
		@Override
		public String id() {
			return "antimanipulacion";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.antiTamper();
		}

		@Override
		public void registrarGUI(String id, Supplier<AntiManipulacionGUI> gui) {
			AntiManipulacionGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<AntiManipulacionGUI>> obtenerGUIs() {
			return AntiManipulacionGUI.GUIS;
		}
	};

	public static TipoGUI<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI> LFPDPPP = new TipoGUI<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI>() {
		@Override
		public String id() {
			return "lfpdppp";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.tituloLFPDPPP();
		}

		@Override
		public void registrarGUI(String id,
				Supplier<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI> gui) {
			LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI>> obtenerGUIs() {
			return LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUI.GUIS;
		}
	};

	public static TipoGUI<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI> APLIC = new TipoGUI<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI>() {
		@Override
		public String id() {
			return "aplic";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.actaProteccionIdiomaCultural();
		}

		@Override
		public void registrarGUI(String id, Supplier<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI> gui) {
			ActaDeProteccionDelIdiomaCulturalDePyongyangGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<ActaDeProteccionDelIdiomaCulturalDePyongyangGUI>> obtenerGUIs() {
			return ActaDeProteccionDelIdiomaCulturalDePyongyangGUI.GUIS;
		}
	};

	public static TipoGUI<CanarioDeOrdenJudicialGUI> CANARIO = new TipoGUI<CanarioDeOrdenJudicialGUI>() {
		@Override
		public String id() {
			return "canario";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.canarioTitulo();
		}

		@Override
		public void registrarGUI(String id, Supplier<CanarioDeOrdenJudicialGUI> gui) {
			CanarioDeOrdenJudicialGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<CanarioDeOrdenJudicialGUI>> obtenerGUIs() {
			return CanarioDeOrdenJudicialGUI.GUIS;
		}
	};

	public static TipoGUI<ConsolaDesarrolladorGUI> CONSOLA_DESARROLLADOR = new TipoGUI<ConsolaDesarrolladorGUI>() {
		@Override
		public String id() {
			return "consola";
		}

		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.consolaDesarrollo();
		}

		@Override
		public void registrarGUI(String id, Supplier<ConsolaDesarrolladorGUI> gui) {
			ConsolaDesarrolladorGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<ConsolaDesarrolladorGUI>> obtenerGUIs() {
			return ConsolaDesarrolladorGUI.GUIS;
		}
	};
	
	
	
	public static TipoGUI<CDLauncherGUI> CDLAUNCHER = new TipoGUI<CDLauncherGUI>() {
		@Override
		public String id() {
			return "relanzer";
		}

		@Override
		public String etiquetaDelBoton() {
			return "CDLauncher";
		}

		@Override
		public void registrarGUI(String id, Supplier<CDLauncherGUI> gui) {
			CDLauncherGUI.GUIS.put(id, gui);
		}

		@Override
		public Map<String, Supplier<CDLauncherGUI>> obtenerGUIs() {
			return CDLauncherGUI.GUIS;
		}
	};
	
	
	

	/**
	 * Registro estático de todos los tipos de GUI en la lista global.
	 */
	static {
		TIPOS_DE_GUI.add(PRINCIPAL);
		TIPOS_DE_GUI.add(GREPR);
		TIPOS_DE_GUI.add(ARBOL_DE_MODS);
		TIPOS_DE_GUI.add(DIALOGO_COMPARTIR);
		TIPOS_DE_GUI.add(EDITOR_FIRMAS);
		TIPOS_DE_GUI.add(TODOS_QUICKFIXES);
		TIPOS_DE_GUI.add(QUICKFIX);
		TIPOS_DE_GUI.add(ESCANER_MCREATOR);
		TIPOS_DE_GUI.add(HISTORIA_DE_MODS);
		TIPOS_DE_GUI.add(LECTADOR_DE_CONSOLAS);
		TIPOS_DE_GUI.add(NO_REGISTRO_LANZER);
		TIPOS_DE_GUI.add(CONFIG_PANEL);
		TIPOS_DE_GUI.add(EDITOR_PLANTILLA);
		TIPOS_DE_GUI.add(EDITOR_GUI);
		TIPOS_DE_GUI.add(MOD_API_PANEL);
		TIPOS_DE_GUI.add(CFR);
		TIPOS_DE_GUI.add(CORPO);
		TIPOS_DE_GUI.add(DESHABLICAR_VERIFICACIONES);
		TIPOS_DE_GUI.add(LANZER_MALO);
		TIPOS_DE_GUI.add(LANZER_BUENO);
		TIPOS_DE_GUI.add(MODS_MALAS);
		TIPOS_DE_GUI.add(MODS_BUENAS);
		TIPOS_DE_GUI.add(MIRANDA);
		TIPOS_DE_GUI.add(ANTI_MANIPULACION);
		TIPOS_DE_GUI.add(LFPDPPP);
		TIPOS_DE_GUI.add(APLIC);
		TIPOS_DE_GUI.add(CANARIO);
		TIPOS_DE_GUI.add(CONSOLA_DESARROLLADOR);
		TIPOS_DE_GUI.add(CDLAUNCHER);

	}
}
