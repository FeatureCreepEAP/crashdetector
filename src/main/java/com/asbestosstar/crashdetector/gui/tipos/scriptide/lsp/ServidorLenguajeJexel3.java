package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Servidor de lenguaje interno para Jexel3.
 *
 * No usa LSP4J. Da completado basico, diagnosticos simples y se puede ampliar
 * despues cuando la sintaxis real de Jexel3 este mas definida.
 */
public class ServidorLenguajeJexel3 implements ScriptIntellisense {

	public final List<ProblemaScript> problemas = new ArrayList<ProblemaScript>();

	@Override
	public boolean disponible() {
		return true;
	}

	@Override
	public String nombre() {
		return "Jexel3 interno";
	}

	@Override
	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) {
	}

	@Override
	public void cerrar() {
		problemas.clear();
	}

	@Override
	public void abrirDocumento(File archivo, String texto) {
		validar(archivo, texto);
	}

	@Override
	public void cambiarDocumento(File archivo, String texto) {
		validar(archivo, texto);
	}

	@Override
	public void cerrarDocumento(File archivo) {
		problemas.clear();
	}

	@Override
	public List<SugerenciaScript> completar(File archivo, String texto, int posicionCaret) {
		String prefijo = prefijo(texto, posicionCaret).toLowerCase();
		List<SugerenciaScript> ret = new ArrayList<SugerenciaScript>();

		agregar(ret, prefijo, "true", "true", "Booleano verdadero");
		agregar(ret, prefijo, "false", "false", "Booleano falso");
		agregar(ret, prefijo, "null", "null", "Valor nulo");
		agregar(ret, prefijo, "if", "if ", "Condicional");
		agregar(ret, prefijo, "else", "else ", "Condicional alterno");
		agregar(ret, prefijo, "return", "return ", "Retorno");
		agregar(ret, prefijo, "import", "import ", "Importacion");
		agregar(ret, prefijo, "var", "var ", "Variable");
		agregar(ret, prefijo, "function", "function ", "Funcion");

		Collections.sort(ret);
		return ret;
	}

	@Override
	public List<ProblemaScript> diagnosticos() {
		return new ArrayList<ProblemaScript>(problemas);
	}

	public void validar(File archivo, String texto) {
		problemas.clear();

		if (texto == null) {
			return;
		}

		int balanceLlaves = 0;
		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);
			if (c == '{') {
				balanceLlaves++;
			} else if (c == '}') {
				balanceLlaves--;
			}

			if (balanceLlaves < 0) {
				problemas.add(new ProblemaScript(uri(archivo), 1, i + 1, "Llave de cierre sin apertura", 1));
				balanceLlaves = 0;
			}
		}

		if (balanceLlaves > 0) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Falta cerrar una o mas llaves", 1));
		}
	}

	public void agregar(List<SugerenciaScript> ret, String prefijo, String etiqueta, String insercion, String detalle) {
		if (prefijo.isEmpty() || etiqueta.toLowerCase().startsWith(prefijo)) {
			ret.add(new SugerenciaScript(etiqueta, insercion, detalle));
		}
	}

	public String prefijo(String texto, int posicionCaret) {
		if (texto == null) {
			return "";
		}

		int pos = Math.max(0, Math.min(posicionCaret, texto.length()));
		int ini = pos;

		while (ini > 0) {
			char c = texto.charAt(ini - 1);
			if (!Character.isLetterOrDigit(c) && c != '_' && c != '$') {
				break;
			}
			ini--;
		}

		return texto.substring(ini, pos);
	}

	public String uri(File archivo) {
		return archivo == null ? "jexel3://documento" : archivo.toURI().toString();
	}
}