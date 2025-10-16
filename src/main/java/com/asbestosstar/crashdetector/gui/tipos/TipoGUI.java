package com.asbestosstar.crashdetector.gui.tipos;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import com.asbestosstar.crashdetector.MonitorDePID;

public abstract class TipoGUI {

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
}
