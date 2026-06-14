package com.asbestosstar.crashdetector.analizador.rapido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Implementación de un autómata tipo Aho-Corasick para búsqueda múltiple de
 * patrones. Optimiza la detección de disparadores rápidos en una sola pasada
 * por línea.
 */
public final class AutomataDePatrones {

	private static class Nodo {
		Map<Character, Nodo> hijos = new HashMap<>();
		Nodo fallo;
		List<String> patronesTerminados = new ArrayList<>();
	}

	private final Nodo raiz;

	public AutomataDePatrones(String[] patrones) {
		this.raiz = new Nodo();
		for (String p : patrones) {
			insertar(p);
		}
		construirEnlacesDeFallo();
	}

	private void insertar(String patron) {
		Nodo actual = raiz;
		for (char c : patron.toCharArray()) {
			actual = actual.hijos.computeIfAbsent(c, k -> new Nodo());
		}
		actual.patronesTerminados.add(patron);
	}

	private void construirEnlacesDeFallo() {
		Queue<Nodo> cola = new LinkedList<>();
		raiz.fallo = null;

		for (Nodo hijo : raiz.hijos.values()) {
			hijo.fallo = raiz;
			cola.add(hijo);
		}

		while (!cola.isEmpty()) {
			Nodo actual = cola.poll();

			for (Map.Entry<Character, Nodo> entrada : actual.hijos.entrySet()) {
				char c = entrada.getKey();
				Nodo hijo = entrada.getValue();
				Nodo f = actual.fallo;

				while (f != null && !f.hijos.containsKey(c)) {
					f = f.fallo;
				}

				if (f == null) {
					hijo.fallo = raiz;
				} else {
					hijo.fallo = f.hijos.get(c);
					hijo.patronesTerminados.addAll(hijo.fallo.patronesTerminados);
				}
				cola.add(hijo);
			}
		}
	}

	/**
	 * Busca todos los patrones en la línea y devuelve las coincidencias
	 * encontradas.
	 */
	public List<Coincidencia> buscar(String linea) {
		List<Coincidencia> resultados = new ArrayList<>();
		Nodo actual = raiz;

		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);

			while (actual != raiz && !actual.hijos.containsKey(c)) {
				actual = actual.fallo;
			}

			actual = actual.hijos.getOrDefault(c, raiz);

			if (!actual.patronesTerminados.isEmpty()) {
				for (String patron : actual.patronesTerminados) {
					resultados.add(new Coincidencia(patron, i - patron.length() + 1));
				}
			}
		}

		return resultados;
	}

	public static final class Coincidencia {
		public final String patron;
		public final int posicion;

		public Coincidencia(String patron, int posicion) {
			this.patron = patron;
			this.posicion = posicion;
		}
	}
}
