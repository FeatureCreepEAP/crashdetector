package com.asbestosstar.crashdetector.canario.pordefecto;

import com.asbestosstar.crashdetector.canario.Canario404;

public class CDPasteAsbestosstarEgoismJPCanario implements Canario404 {

	public static String url_canario = "https://asbestosstar.egoism.jp/crash_detector/canario_paste.htm";

	@Override
	public String nombre_de_servicio() {
		return "https://asbestosstar.egoism.jp/crash_detector/paste/endpoint.php";
	}

	@Override
	public String urlDeArchivoBuscar() {
		// TODO Auto-generated method stub
		return url_canario;
	}

}
