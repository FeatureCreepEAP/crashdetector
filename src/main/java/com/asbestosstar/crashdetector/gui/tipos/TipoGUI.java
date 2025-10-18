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

import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;
import com.asbestosstar.crashdetector.gui.tipos.grepr.GrepRGUI;
import com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirGUI;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorFirmasGUI;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.TodosQuickFixesGUI;
import com.asbestosstar.crashdetector.gui.tipos.quickfix.QuickFixGUI;
import com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUI;
import com.asbestosstar.crashdetector.gui.tipos.historia.HistoriaDeModsGUI;
import com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasGUI;
import com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador.NoRegistroLanzadorGUI;

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
	public static TipoGUI<DialogoCompartirGUI> DIALOGO_COMPARTIR = new TipoGUI<DialogoCompartirGUI>() {
		@Override
		public String id() {
			return "dialogo_compartir";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.botonDeCompartirInforme();
		}
		@Override
		public void registrarGUI(String id, Supplier<DialogoCompartirGUI> gui) {
			DialogoCompartirGUI.GUIS.put(id, gui);
		}
		@Override
		public Map<String, Supplier<DialogoCompartirGUI>> obtenerGUIs() {
			return DialogoCompartirGUI.GUIS;
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
	 * @param gui
	 */
	public abstract void registrarGUI(String id,Supplier<T> gui);
	
	/**
	 * IDs de GUIs con Suppliers para las GUIs
	 * @return
	 */
	public abstract Map<String,Supplier<T>> obtenerGUIs();
	
	public T obtenerGUIPredeterminado(String id_de_por_defecto,Supplier<T> por_defecto) {
		ConfigString str =ConfigString.de("guitipo_"+this.id(), id_de_por_defecto);
		CrashDetectorLogger.log(str.obtener());
		return obtenerGUIs().getOrDefault(str.obtener(), por_defecto).get();
	}
	
	
	
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
	}
}
