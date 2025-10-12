package com.asbestosstar.crashdetector.analizador.firmas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

public class FiltrodeCodice {

	public static Map<String, FiltrodeCodice> filtros = new HashMap<String, FiltrodeCodice>();

	public static FiltrodeCodice REGEX_TODOS = new FiltrodeCodice("regex_todos", TipoDeFiltrodeCodice.DE_TODOS,
			(contenido, regex) -> {
				if (regex == null || regex.isEmpty())
					return false;
				try {
					Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
					return patron.matcher(contenido).find();
				} catch (Exception e) {
					return false; // si el regex es inválido
				}
			});

	public static FiltrodeCodice REGEX_LINEA = new FiltrodeCodice("regex_linea", TipoDeFiltrodeCodice.DE_LINEA,
			(linea, regex) -> {
				if (regex == null || regex.isEmpty())
					return false;
				try {
					Pattern patron = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
					return patron.matcher(linea).find();
				} catch (Exception e) {
					return false;
				}
			});

	public static FiltrodeCodice CONTAINE_TODOS = new FiltrodeCodice("containe_todos", TipoDeFiltrodeCodice.DE_TODOS,
			(contenido, texto) -> {
				if (texto == null || texto.isEmpty())
					return false;
				return contenido.toLowerCase().contains(texto.toLowerCase());
			});

	public static FiltrodeCodice CONTAINE_LINEA = new FiltrodeCodice("containe_linea", TipoDeFiltrodeCodice.DE_LINEA,
			(linea, texto) -> {
				if (texto == null || texto.isEmpty())
					return false;
				return linea.toLowerCase().contains(texto.toLowerCase());
			});

	static {
		filtros.put(REGEX_TODOS.id, REGEX_TODOS);
		filtros.put(REGEX_LINEA.id, REGEX_LINEA);
		filtros.put(CONTAINE_TODOS.id, CONTAINE_TODOS);
		filtros.put(CONTAINE_LINEA.id, CONTAINE_LINEA);

	}

	public static FiltrodeCodice obtener(String id_de_filtro) {
		return filtros.getOrDefault(id_de_filtro, CONTAINE_TODOS);
	}

	public String id;
	public TipoDeFiltrodeCodice tipo;
	public BiFunction<String, String, Boolean> predicado;

	public FiltrodeCodice(String id, TipoDeFiltrodeCodice tipo, BiFunction<String, String, Boolean> predicado) {
		this.id = id;
		this.tipo = tipo;
		this.predicado = predicado;
	}

	public boolean activar(String para_verificar, String para_buscar) {
		return predicado.apply(para_verificar, para_buscar);
	}

}
