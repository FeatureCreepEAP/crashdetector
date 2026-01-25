package com.asbestosstar.crashdetector.canario.pordefecto;

import com.asbestosstar.crashdetector.canario.Canario404;

/**
 * Canario por defecto para el servidor de informes de CrashDetector. Se
 * considera seguro mientras la URL no devuelva 404.
 */
public class CDInformesAsbestosstarEgoismJPCanario implements Canario404 {

	public static String url_canario = "https://asbestosstar.egoism.jp/crash_detector/canario_informe.htm";

	@Override
	public String nombre_de_servicio() {
		return "https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb";
	}

	@Override
	public String urlDeArchivoBuscar() {
		// TODO Auto-generated method stub
		return url_canario;
	}
}
