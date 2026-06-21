package com.asbestosstar.crashdetector.analizador.rapido;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class AutomataDePatrones {

	private static final class Nodo {
		private final Map<Byte, Nodo> hijos = new HashMap<>();
		private Nodo fallo;
		private final List<PatronBytes> patronesTerminados = new ArrayList<>();
	}

	public static final class PatronBytes {
		public final String texto;
		public final byte[] bytes;

		private PatronBytes(String texto) {
			this.texto = texto;
			this.bytes = texto.getBytes(StandardCharsets.UTF_8);
		}
	}

	public static final class Coincidencia {
		public final String patron;
		public final int inicio;
		public final int fin;

		public Coincidencia(String patron, int inicio, int fin) {
			this.patron = patron;
			this.inicio = inicio;
			this.fin = fin;
		}
	}

	private final Nodo raiz = new Nodo();

	public AutomataDePatrones(String[] patrones) {
		if (patrones != null) {
			for (String patron : patrones) {
				if (patron != null && !patron.isEmpty()) {
					insertar(new PatronBytes(patron));
				}
			}
		}

		construirFallos();
	}

	private void insertar(PatronBytes patron) {
		Nodo actual = raiz;

		for (byte b : patron.bytes) {
			actual = actual.hijos.computeIfAbsent(b, k -> new Nodo());
		}

		actual.patronesTerminados.add(patron);
	}

	private void construirFallos() {
		Queue<Nodo> cola = new LinkedList<>();

		raiz.fallo = raiz;

		for (Nodo hijo : raiz.hijos.values()) {
			hijo.fallo = raiz;
			cola.add(hijo);
		}

		while (!cola.isEmpty()) {
			Nodo actual = cola.poll();

			for (Map.Entry<Byte, Nodo> entrada : actual.hijos.entrySet()) {
				byte b = entrada.getKey();
				Nodo hijo = entrada.getValue();

				Nodo f = actual.fallo;

				while (f != raiz && !f.hijos.containsKey(b)) {
					f = f.fallo;
				}

				Nodo destino = f.hijos.get(b);
				hijo.fallo = destino != null ? destino : raiz;
				hijo.patronesTerminados.addAll(hijo.fallo.patronesTerminados);

				cola.add(hijo);
			}
		}
	}

	public List<Coincidencia> buscar(byte[] datos, int inicio, int fin) {
		List<Coincidencia> resultados = null;
		Nodo actual = raiz;

		for (int i = inicio; i < fin; i++) {
			byte b = datos[i];

			while (actual != raiz && !actual.hijos.containsKey(b)) {
				actual = actual.fallo;
			}

			Nodo siguiente = actual.hijos.get(b);
			actual = siguiente != null ? siguiente : raiz;

			if (!actual.patronesTerminados.isEmpty()) {
				if (resultados == null) {
					resultados = new ArrayList<>();
				}

				for (PatronBytes patron : actual.patronesTerminados) {
					int inicioCoincidencia = i - patron.bytes.length + 1;
					resultados.add(new Coincidencia(patron.texto, inicioCoincidencia, i + 1));
				}
			}
		}

		return resultados;
	}
}