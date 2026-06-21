package com.asbestosstar.crashdetector.gui.tipos.deshablicarverificaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JDialog;

import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.config.ConfigStringArray;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public abstract class DeshablicarVerificaciones extends JDialog
		implements CrashDetectorGUI, BotonDeBarraLateralDerecha {
	public static Map<String, Supplier<DeshablicarVerificaciones>> GUIS = new HashMap<String, Supplier<DeshablicarVerificaciones>>();

	public static HashSet<VerificacionesLegacy> verificaciones() {
		return Analizador.verificaciones;
	}

	public static ConfigStringArray config() {
		return Analizador.CONFIG_LISTA_DENEGACION;
	}

	public static List<String> configValor() {
		return Analizador.CONFIG_LISTA_DENEGACION.obtener();
	}

	public static void escribirConfig(ArrayList<String> valor) {
		Analizador.CONFIG_LISTA_DENEGACION.escribir(valor);
	}

	/**
	 * Si es recinebdado para corperaciones/vtuber fan proyectos/modpacks
	 * 
	 * @param ver
	 * @return
	 */
	public static boolean recomendadoParaCorperata(VerificacionesLegacy ver) {
		return ver.recomendadoParaCorperata();
	}

	/**
	 * nombre visible
	 * 
	 * @param ver
	 * @return
	 */
	public static String nombre(VerificacionesLegacy ver) {
		return ver.nombre();
	}

	/**
	 * nombre visible
	 * 
	 * @param ver
	 * @return
	 */
	public static String enlaceACodigo(VerificacionesLegacy ver) {
		return ver.enlaceACodigo();
	}

	/**
	 * id para lista
	 * 
	 * @param ver
	 * @return
	 */
	public static String id(VerificacionesLegacy ver) {
		return ver.id();
	}

	/**
	 * id para lista
	 * 
	 * @param ver
	 * @return
	 */
	public static String enlaceDocs(VerificacionesLegacy ver) {
		Documento doc = ver.docs();
		if (doc == null || doc.equals(Documento.NINGUN)) {
			return null;
		}
		return doc.enlace();
	}

	@Override
	public TipoGUI tipo() {
		// TODO Auto-generated method stub
		return TipoGUI.DESHABLICAR_VERIFICACIONES;
	}

}