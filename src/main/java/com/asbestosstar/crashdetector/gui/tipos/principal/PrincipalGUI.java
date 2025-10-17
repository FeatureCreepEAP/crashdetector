package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Font;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Base técnica: - Crea y configura el visor (JEditorPane/JScrollPane) y
 * listeners. - NO asume layout; delega toda la construcción visual a
 * buildLayout(...). - NO contiene helpers/estilo; la subclase controla
 * apariencia en applyAppearance(). - NO reimplementa métodos del interface (usa
 * los defaults de CrashDetectorGUI).
 */
public abstract class PrincipalGUI extends JFrame implements CrashDetectorGUI {

	public static Map<String,Supplier<PrincipalGUI>> GUIS = new HashMap<>();
	
	
	protected Instant tiempoFallo;
	protected CountDownLatch cerrojo;

	/** Botones de barra lateral registrados (solo referencias técnicas) */
	protected static final List<Supplier<BotonDeBarraLateralDerecha>> BOTONES_REGISTRADOS = new ArrayList<>();
	/** Mapa lógico->swing para refrescar etiquetas desde la subclase */
	protected final Map<BotonDeBarraLateralDerecha, javax.swing.JButton> botonesSidebarInicializados = new HashMap<>();

	static {
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.grepr.BusquedaGUISaliorMoon());
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.mcreator.EscanerMCreatorGUIRosemiLoveLock());
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.historia.HistoriaModsGUILegacy());
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.arbol.ArbolDeModsGUIHamu());
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.lectador.LectadorDeConsolasHoloTalk());
		BOTONES_REGISTRADOS.add(() -> new com.asbestosstar.crashdetector.gui.tipos.editor.EditorCodiceGUIIronMouse());
	}

	public static void registrarBotonSidebar(Supplier<BotonDeBarraLateralDerecha> sup) {
		BOTONES_REGISTRADOS.add(sup);
	}
	
	/**
	 * MUY IMPORTANTE. Necesitas tener una instancia. Necesitas un super a aqui en tu versiones
	 * @param tiempoFallo
	 * @param cerrojo
	 */
	public void constructir(Instant tiempoFallo, CountDownLatch cerrojo) {
		this.tiempoFallo = tiempoFallo;
		this.cerrojo = cerrojo;

		// Delegar TODO el layout/estética a la subclase:
		constuctirFormato(BOTONES_REGISTRADOS, botonesSidebarInicializados);

		// Aplicar apariencia inicial (colores/labels) totalmente en la subclase
		recargarApariencia();
		setVisible(true);
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.PRINCIPAL;
	}

	/** El interface exige este método; aquí solo delegamos a la subclase. */
	@Override
	public final void recargarApariencia() {
		aplicarApariancia(botonesSidebarInicializados);
		repaint();
	}

	@Override
	public void dispose() {
		super.dispose();
		MonitorDePID.fin(cerrojo);
	}

	/*
	 * ================= Hooks que la subclase DEBE implementar (solo
	 * apariencia/layout) =================
	 */

	/**
	 * Construcción COMPLETA del layout y componentes visuales. Aquí se añaden
	 * paneles, barra lateral, botones, tamaños de ventana, etc.
	 */
	protected abstract void constuctirFormato(List<Supplier<BotonDeBarraLateralDerecha>> registrados,
			Map<BotonDeBarraLateralDerecha, javax.swing.JButton> salidaBotonesSidebar);

	/**
	 * Aplicar apariencia/tema: colores, labels, títulos, tooltips, etc. No debe
	 * crear ni añadir componentes, solo estilizar/actualizar los existentes.
	 */
	protected abstract void aplicarApariancia(Map<BotonDeBarraLateralDerecha, javax.swing.JButton> botonesSidebar);

	public JEditorPane pantalla() {
		JEditorPane pantalla = new JEditorPane();
		// Configuración técnica del visor (sin estilos ni colores)
		pantalla.setContentType("text/html");
		pantalla.setEditable(false);
		pantalla.setFont(new Font("Consolas", Font.PLAIN, 14));
		pantalla.addHyperlinkListener(this::enlanceEvento); // método default del interface

		// Carga inicial del informe (técnico)
		try {
			pantalla.setText(new String(Files.readAllBytes(Paths.get(MonitorDePID.local))));
			pantalla.setCaretPosition(0);
		} catch (IOException e) {
			pantalla.setText("<html><body>Problema con el Informe: " + e.getMessage() + "</body></html>");
		}
		return pantalla;
	}

}
