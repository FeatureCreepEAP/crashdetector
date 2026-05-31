package com.asbestosstar.crashdetector.gui.tipos.corpo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.PirataMC;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.config.ConfigString;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.antimanipulacion.AntiManipulacionGUIPanko;
import com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones.DeshabilitarVerificacionesAmaneKanata;
import com.asbestosstar.crashdetector.gui.tipos.editor.EditorCodiceGUIIronMouse;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresbuenos.LanzerBuenoGUIMaidMint;
import com.asbestosstar.crashdetector.gui.tipos.lanzeresmalos.LanzerMaloGUISylentBell;
import com.asbestosstar.crashdetector.gui.tipos.miranda.DerechosPiratasGUIOnePiece;
import com.asbestosstar.crashdetector.gui.tipos.modsbuenas.ModsBuenasGUIReiBubbles;
import com.asbestosstar.crashdetector.gui.tipos.modsmalas.GUIModsMalasRimaEvenstar;

public abstract class CorpoBase extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	public static Map<String, Supplier<CorpoBase>> GUIS = new HashMap<String, Supplier<CorpoBase>>();

	public static void escribirIdiomaRespaldo(String codigo) {
		Idioma.idioma_respaldo.escribir(codigo);
	}

	public static String obtenerIdiomaRespaldo() {
		return Idioma.idioma_respaldo.obtener();
	}

	public static void escribirBuscardorHablicar(boolean valor) {
		Buscador.hablicar.escribir(valor);
	}

	public static boolean obtenerBuscardorHablicar() {
		return Buscador.hablicar.obtener();
	}

	public static void escribirNombreCD(String valor) {
		Statics.nombre_cd.escribir(valor);
	}

	public static String obtenerNombreCD() {
		return Statics.nombre_cd.obtener();
	}

	public static void escribirCondenarPirata(boolean valor) {
		PirataMC.config.escribir(valor);
	}

	public static boolean obtenerCondenarPirata() {
		return PirataMC.config.obtener();
	}

	public static void abrirEditorCodice() {
		TipoGUI.EDITOR_FIRMAS
				.obtenerGUIPredeterminado(EditorCodiceGUIIronMouse.ID, () -> new EditorCodiceGUIIronMouse()).init();
	}

	public static void abrirVerificaciones() {
		TipoGUI.DESHABLICAR_VERIFICACIONES.obtenerGUIPredeterminado(DeshabilitarVerificacionesAmaneKanata.ID,
				() -> new DeshabilitarVerificacionesAmaneKanata()).init();
	}

	public static void abrirLanzeresMalos() {
		TipoGUI.LANZER_MALO.obtenerGUIPredeterminado(LanzerMaloGUISylentBell.ID, () -> new LanzerMaloGUISylentBell())
				.init();
	}

	public static void abrirLanzeresBuenos() {
		TipoGUI.LANZER_BUENO.obtenerGUIPredeterminado(LanzerBuenoGUIMaidMint.ID, () -> new LanzerBuenoGUIMaidMint())
				.init();
	}

	public static void abrirModsMalas() {
		TipoGUI.MODS_MALAS.obtenerGUIPredeterminado(GUIModsMalasRimaEvenstar.ID, () -> new GUIModsMalasRimaEvenstar())
				.init();
	}

	public static void abrirModsBuenas() {
		TipoGUI.MODS_BUENAS.obtenerGUIPredeterminado(ModsBuenasGUIReiBubbles.ID, () -> new ModsBuenasGUIReiBubbles())
				.init();
	}

	public static void abrirMiranda() {
		TipoGUI.MIRANDA.obtenerGUIPredeterminado(DerechosPiratasGUIOnePiece.ID, () -> new DerechosPiratasGUIOnePiece())
				.init();
	}

	public static void abrirAntiManipulacion() {
		TipoGUI.ANTI_MANIPULACION
				.obtenerGUIPredeterminado(AntiManipulacionGUIPanko.ID, () -> new AntiManipulacionGUIPanko()).init();
	}

	public static void escribirSuprimirConsolaCD(boolean valor) {
		ConfigBoolean.de("suprimir_consola_cd", false).escribir(valor);
	}

	public static boolean obtenerSuprimirConsolaCD() {
		return ConfigBoolean.de("suprimir_consola_cd", false).obtener();
	}

	public static void escribirSuprimirVerificacionDeStacktrazos(boolean valor) {
		ConfigBoolean.de("suprimir_verificacion_de_stacktrazos", false).escribir(valor);
	}

	public static boolean obtenerSuprimirVerificacionDeStacktrazos() {
		return ConfigBoolean.de("suprimir_verificacion_de_stacktrazos", false).obtener();
	}

	public static void escribirMostrarSelectorAplicacionPrincipal(boolean valor) {
		ConfigBoolean.de("principal.mostrar_selector_aplicacion", true).escribir(valor);
	}

	public static boolean obtenerMostrarSelectorAplicacionPrincipal() {
		return ConfigBoolean.de("principal.mostrar_selector_aplicacion", true).obtener();
	}

	public static void escribirMostrarBotonCDLauncherPrincipal(boolean valor) {
		ConfigBoolean.de("principal.mostrar_boton_cdlauncher", true).escribir(valor);
	}

	public static boolean obtenerMostrarBotonCDLauncherPrincipal() {
		return ConfigBoolean.de("principal.mostrar_boton_cdlauncher", true).obtener();
	}

	public static void escribirMostrarBotonCDModsPrincipal(boolean valor) {
		ConfigBoolean.de("principal.mostrar_boton_cdmods", true).escribir(valor);
	}

	public static boolean obtenerMostrarBotonCDModsPrincipal() {
		return ConfigBoolean.de("principal.mostrar_boton_cdmods", true).obtener();
	}

	public static void escribirMostrarBotonIAPrincipal(boolean valor) {
		ConfigBoolean.de("principal.mostrar_boton_ia", true).escribir(valor);
	}

	public static boolean obtenerMostrarBotonIAPrincipal() {
		return ConfigBoolean.de("principal.mostrar_boton_ia", true).obtener();
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.CORPO;
	}

	@Override
	public void recargarApariencia() {
		aplicarApariencia();
	}

	/**
	 * Aplica la apariencia a todos los componentes de la GUI
	 */
	public abstract void aplicarApariencia();

}