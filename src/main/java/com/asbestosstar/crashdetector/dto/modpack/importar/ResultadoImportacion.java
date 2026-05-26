package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.util.ArrayList;
import java.util.List;

public class ResultadoImportacion {

	public int copiados;
	public int reemplazados;
	public int saltados;
	public int renombrados;
	public int errores;
	public int fusionados;

	public List<String> mensajes = new ArrayList<String>();
}