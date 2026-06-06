package com.asbestosstar.crashdetector.gui.tipos.scriptide.lsp;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.asbestosstar.crashdetector.gui.tipos.scriptide.TipoProyectoScript;

/**
 * Servidor de lenguaje interno para FEEL.
 *
 * FEEL normalmente aparece en DMN/Drools/KIE. Esta clase no depende
 * directamente de Drools para que el IDE pueda compilar aunque kie-dmn-feel no
 * exista en el classpath.
 *
 * Si las clases FEEL de Drools existen, se intenta validar por reflexion. Si no
 * existen, se usan diagnosticos basicos de parentesis, comillas y palabras
 * comunes.
 */
public class ServidorLenguajeFeel implements ScriptIntellisense {

	public File carpetaProyecto;
	public final List<ProblemaScript> problemas = new ArrayList<ProblemaScript>();

	@Override
	public boolean disponible() {
		return true;
	}

	@Override
	public String nombre() {
		return "FEEL interno";
	}

	@Override
	public void iniciar(TipoProyectoScript tipo, File carpetaProyecto) {
		this.carpetaProyecto = carpetaProyecto;
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

		agregar(ret, prefijo, "if", "if ", "Condicional FEEL");
		agregar(ret, prefijo, "then", "then ", "Rama verdadera");
		agregar(ret, prefijo, "else", "else ", "Rama falsa");
		agregar(ret, prefijo, "for", "for ", "Iteracion");
		agregar(ret, prefijo, "in", "in ", "Pertenencia o iteracion");
		agregar(ret, prefijo, "return", "return ", "Resultado de iteracion");
		agregar(ret, prefijo, "some", "some ", "Cuantificador existencial");
		agregar(ret, prefijo, "every", "every ", "Cuantificador universal");
		agregar(ret, prefijo, "satisfies", "satisfies ", "Condicion de cuantificador");
		agregar(ret, prefijo, "and", "and ", "Operador logico");
		agregar(ret, prefijo, "or", "or ", "Operador logico");
		agregar(ret, prefijo, "not", "not(", "Negacion");
		agregar(ret, prefijo, "true", "true", "Booleano verdadero");
		agregar(ret, prefijo, "false", "false", "Booleano falso");
		agregar(ret, prefijo, "null", "null", "Valor nulo");

		agregar(ret, prefijo, "date", "date(\"\")", "Funcion fecha");
		agregar(ret, prefijo, "time", "time(\"\")", "Funcion hora");
		agregar(ret, prefijo, "date and time", "date and time(\"\")", "Fecha y hora");
		agregar(ret, prefijo, "duration", "duration(\"\")", "Duracion");
		agregar(ret, prefijo, "years and months duration", "years and months duration(\"\")", "Duracion por meses");

		agregar(ret, prefijo, "string", "string()", "Conversion a texto");
		agregar(ret, prefijo, "number", "number()", "Conversion a numero");
		agregar(ret, prefijo, "substring", "substring(, )", "Subcadena");
		agregar(ret, prefijo, "string length", "string length()", "Longitud de texto");
		agregar(ret, prefijo, "upper case", "upper case()", "Mayusculas");
		agregar(ret, prefijo, "lower case", "lower case()", "Minusculas");
		agregar(ret, prefijo, "contains", "contains(, )", "Texto contiene");
		agregar(ret, prefijo, "starts with", "starts with(, )", "Texto empieza con");
		agregar(ret, prefijo, "ends with", "ends with(, )", "Texto termina con");
		agregar(ret, prefijo, "matches", "matches(, )", "Regex");

		agregar(ret, prefijo, "list contains", "list contains(, )", "Lista contiene");
		agregar(ret, prefijo, "count", "count()", "Conteo");
		agregar(ret, prefijo, "min", "min()", "Minimo");
		agregar(ret, prefijo, "max", "max()", "Maximo");
		agregar(ret, prefijo, "sum", "sum()", "Suma");
		agregar(ret, prefijo, "mean", "mean()", "Promedio");
		agregar(ret, prefijo, "median", "median()", "Mediana");
		agregar(ret, prefijo, "mode", "mode()", "Moda");
		agregar(ret, prefijo, "stddev", "stddev()", "Desviacion estandar");
		agregar(ret, prefijo, "all", "all()", "Todos true");
		agregar(ret, prefijo, "any", "any()", "Algun true");
		agregar(ret, prefijo, "append", "append(, )", "Agregar a lista");
		agregar(ret, prefijo, "concatenate", "concatenate(, )", "Concatenar listas");
		agregar(ret, prefijo, "insert before", "insert before(, , )", "Insertar en lista");
		agregar(ret, prefijo, "remove", "remove(, )", "Eliminar de lista");
		agregar(ret, prefijo, "reverse", "reverse()", "Invertir lista");
		agregar(ret, prefijo, "index of", "index of(, )", "Indices de valor");
		agregar(ret, prefijo, "union", "union(, )", "Union de listas");
		agregar(ret, prefijo, "distinct values", "distinct values()", "Valores unicos");
		agregar(ret, prefijo, "flatten", "flatten()", "Aplanar lista");

		agregar(ret, prefijo, "floor", "floor()", "Piso");
		agregar(ret, prefijo, "ceiling", "ceiling()", "Techo");
		agregar(ret, prefijo, "decimal", "decimal(, )", "Redondeo decimal");
		agregar(ret, prefijo, "abs", "abs()", "Valor absoluto");
		agregar(ret, prefijo, "modulo", "modulo(, )", "Modulo");
		agregar(ret, prefijo, "sqrt", "sqrt()", "Raiz cuadrada");
		agregar(ret, prefijo, "log", "log()", "Logaritmo");
		agregar(ret, prefijo, "exp", "exp()", "Exponencial");
		agregar(ret, prefijo, "odd", "odd()", "Numero impar");
		agregar(ret, prefijo, "even", "even()", "Numero par");

		Collections.sort(ret);
		return ret;
	}

	@Override
	public List<ProblemaScript> diagnosticos() {
		return new ArrayList<ProblemaScript>(problemas);
	}

	public void validar(File archivo, String texto) {
		problemas.clear();

		if (texto == null || texto.trim().isEmpty()) {
			return;
		}

		if (validarConDroolsPorReflexion(archivo, texto)) {
			return;
		}

		validarBasico(archivo, texto);
	}

	public boolean validarConDroolsPorReflexion(File archivo, String texto) {
		try {
			/*
			 * Evitamos import directo para no forzar kie-dmn-feel como dependencia dura.
			 *
			 * Esta ruta puede variar entre versiones de Drools/KIE. Si falla, simplemente
			 * se usa el validador basico.
			 */
			Class<?> claseFeelBuilder = Class.forName("org.kie.dmn.feel.FEELBuilder");
			Object builder = claseFeelBuilder.getMethod("builder").invoke(null);
			Object feel = builder.getClass().getMethod("build").invoke(builder);

			try {
				feel.getClass().getMethod("evaluate", String.class).invoke(feel, texto);
				return true;
			} catch (Throwable t) {
				String mensaje = t.getCause() == null ? t.getMessage() : t.getCause().getMessage();
				problemas.add(new ProblemaScript(uri(archivo), 1, 1,
						"FEEL invalido segun Drools: " + (mensaje == null ? "error desconocido" : mensaje), 1));
				return true;
			}
		} catch (Throwable t) {
			return false;
		}
	}

	public void validarBasico(File archivo, String texto) {
		int parentesis = 0;
		int corchetes = 0;
		int llaves = 0;
		boolean comilla = false;

		for (int i = 0; i < texto.length(); i++) {
			char c = texto.charAt(i);

			if (c == '"' && !estaEscapado(texto, i)) {
				comilla = !comilla;
				continue;
			}

			if (comilla) {
				continue;
			}

			if (c == '(') {
				parentesis++;
			} else if (c == ')') {
				parentesis--;
			} else if (c == '[') {
				corchetes++;
			} else if (c == ']') {
				corchetes--;
			} else if (c == '{') {
				llaves++;
			} else if (c == '}') {
				llaves--;
			}

			if (parentesis < 0) {
				problemas.add(new ProblemaScript(uri(archivo), linea(texto, i), columna(texto, i),
						"Parentesis de cierre sin apertura", 1));
				parentesis = 0;
			}
			if (corchetes < 0) {
				problemas.add(new ProblemaScript(uri(archivo), linea(texto, i), columna(texto, i),
						"Corchete de cierre sin apertura", 1));
				corchetes = 0;
			}
			if (llaves < 0) {
				problemas.add(new ProblemaScript(uri(archivo), linea(texto, i), columna(texto, i),
						"Llave de cierre sin apertura", 1));
				llaves = 0;
			}
		}

		if (comilla) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Comilla sin cerrar", 1));
		}
		if (parentesis > 0) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Falta cerrar parentesis", 1));
		}
		if (corchetes > 0) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Falta cerrar corchete", 1));
		}
		if (llaves > 0) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Falta cerrar llave", 1));
		}

		validarPalabrasComunes(archivo, texto);
	}

	public void validarPalabrasComunes(File archivo, String texto) {
		String t = texto.toLowerCase();

		if (t.contains(" if ") && !t.contains(" then ")) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Un if de FEEL normalmente necesita then", 2));
		}

		if (t.contains(" then ") && !t.contains(" else ")) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1, "Un if de FEEL normalmente necesita else", 2));
		}

		if ((t.contains(" some ") || t.contains(" every ")) && !t.contains(" satisfies ")) {
			problemas.add(new ProblemaScript(uri(archivo), 1, 1,
					"Un cuantificador some/every normalmente necesita satisfies", 2));
		}
	}

	public boolean estaEscapado(String texto, int indice) {
		int barras = 0;
		for (int i = indice - 1; i >= 0; i--) {
			if (texto.charAt(i) == '\\') {
				barras++;
			} else {
				break;
			}
		}
		return barras % 2 == 1;
	}

	public int linea(String texto, int indice) {
		int linea = 1;
		for (int i = 0; i < indice && i < texto.length(); i++) {
			if (texto.charAt(i) == '\n') {
				linea++;
			}
		}
		return linea;
	}

	public int columna(String texto, int indice) {
		int col = 1;
		for (int i = indice - 1; i >= 0 && i < texto.length(); i--) {
			if (texto.charAt(i) == '\n') {
				break;
			}
			col++;
		}
		return col;
	}

	public void agregar(List<SugerenciaScript> ret, String prefijo, String etiqueta, String insercion, String detalle) {
		if (prefijo == null || prefijo.isEmpty() || etiqueta.toLowerCase().startsWith(prefijo)) {
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

			if (!Character.isLetterOrDigit(c) && c != '_' && c != ' ') {
				break;
			}

			ini--;
		}

		return texto.substring(ini, pos).trim();
	}

	public String uri(File archivo) {
		return archivo == null ? "feel://documento" : archivo.toURI().toString();
	}
}