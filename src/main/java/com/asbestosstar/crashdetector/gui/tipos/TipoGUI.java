package com.asbestosstar.crashdetector.gui.tipos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.Icon;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

public abstract class TipoGUI<T extends CrashDetectorGUI> {

	/**
	 * Para todos GUIS por favor registrar un tipo
	 */
	public static List<TipoGUI> TIPOS_DE_GUI = new ArrayList<>();
	
	
	
	

	
	
	
	
	/**
	 * La GUI Principal
	 */
	public static TipoGUI PRINCIPAL = new TipoGUI() {
		@Override
		public String etiquetaDelBoton() {
			return "GUI";
		}
		@Override
		public String id() {
			return "principal";
		}
	};

	/**
	 * La GUI de GrepR
	 */
	public static TipoGUI GREPR = new TipoGUI() {
		@Override
		public String id() {
			return "grepr";
		}
		@Override
		public String etiquetaDelBoton() {
			return "grepr/fgrepr";
		}
	};
	
	/**
	 * Para Árbol de Mods
	 */
	public static TipoGUI ARBOL_DE_MODS = new TipoGUI() {
		@Override
		public String id() {
			return "arbol_de_mods";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.arbolDeMods();
		}
	};
	
	/**
	 * Diálogo Compartir
	 */
	public static TipoGUI DIALOGO_COMPARTIR = new TipoGUI() {
		@Override
		public String id() {
			return "dialogo_compartir";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.botonDeCompartirInforme();
		}
	};

	/**
	 * Editor de firmas de razones personalizadas
	 */
	public static TipoGUI EDITOR_FIRMAS = new TipoGUI() {
		@Override
		public String id() {
			return "editor_firmas";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.editorCodiceBoton();
		}
	};

	/**
	 * GUI con TODOS los QuickFix
	 */
	public static TipoGUI TODOS_QUICKFIXES = new TipoGUI() {
		@Override
		public String id() {
			return "quickfix_todos";
		}
		@Override
		public String etiquetaDelBoton() {
			return "QuickFix";
		}
	};

	/**
	 * GUI con QuickFix individuales
	 */
	public static TipoGUI QUICKFIX = new TipoGUI() {
		@Override
		public String id() {
			return "quickfix";
		}
		@Override
		public String etiquetaDelBoton() {
			return "QuickFix";
		}
	};
	
	/**
	 * Escáner de MCreator
	 */
	public static TipoGUI ESCANER_MCREATOR = new TipoGUI() {
		@Override
		public String id() {
			return "escaner_mcreator";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.escanearDeMCreator();
		}
	};
	
	/**
	 * Historia de Mods
	 */
	public static TipoGUI HISTORIA_DE_MODS = new TipoGUI() {
		@Override
		public String id() {
			return "historia_de_mods";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.historialDeMods();
		}
	};
	
	/**
	 * Lectador de Consolas
	 */
	public static TipoGUI LECTADOR_DE_CONSOLAS = new TipoGUI() {
		@Override
		public String id() {
			return "lectador_de_consolas";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.obtenerEtiquetaBotonLectador();
		}
	};
	
	/**
	 * No registro de Launcher
	 */
	public static TipoGUI NO_REGISTRO_LANZER = new TipoGUI() {
		@Override
		public String id() {
			return "no_registro_launcher";
		}
		@Override
		public String etiquetaDelBoton() {
			return MonitorDePID.idioma.noRegistroLauncherTitulo();
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
