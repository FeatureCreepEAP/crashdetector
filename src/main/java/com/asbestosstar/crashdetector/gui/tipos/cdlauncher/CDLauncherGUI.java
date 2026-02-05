package com.asbestosstar.crashdetector.gui.tipos.cdlauncher;

import java.awt.Window;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.ConfigMundial;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.principal.PrincipalGUI;

/**
 * GUI base de CDLauncher.
 *
 * Esta clase:
 * - Es directamente un JDialog.
 * - No define colores.
 * - No define layout.
 * - Solo contiene lógica común.
 *
 * La apariencia completa se implementa en las subclases.
 */
public abstract class CDLauncherGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String, Supplier<CDLauncherGUI>> GUIS = new HashMap<>();

	protected PrincipalGUI principal;

	protected CDLauncherGUI() {
		super();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CDLAUNCHER;
	}

	/**
	 * No usar init(). CDLauncher se crea mediante construir(...).
	 */
	@Override
	public final void init() {
		// NO USAR
	}

	/**
	 * Inicializa este diálogo usando la GUI principal como owner.
	 */
	protected void inicializarDialogo(PrincipalGUI principal) {
		this.principal = principal;

		Window owner = SwingUtilities.getWindowAncestor(principal.getRootPane());
	//	setModal(true);
		setTitle("CDLauncher");
		setLocationRelativeTo(owner);
	}

	/**
	 * Construye y muestra el diálogo de CDLauncher.
	 */
	public abstract void construir(PrincipalGUI principal);

	/**
	 * Cambia la GUI principal al modo Lanzer / Launcher.
	 */
	public void cambiarAparienciaDeGUIPrincipal(PrincipalGUI principal) {
		principal.cambiarAModoLanzer();
	}

	/**
	 * Habilita la consola de desarrollo y la abre.
	 */
	public static void habilitarConsola() {
		ConfigMundial.obtenerInstancia().guardarConsolaDesarrollo(true);
		MonitorDePID.abrirConsola();
	}

	@Override
	public abstract void recargarApariencia();

	@Override
	public abstract List<ElementoConfig> obtenerElementosConfigs();
}
