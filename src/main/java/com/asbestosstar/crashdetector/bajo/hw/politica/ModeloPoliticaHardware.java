package com.asbestosstar.crashdetector.bajo.hw.politica;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Modelo común de la política corporativa de sistemas operativos y hardware.
 *
 * No depende de bibliotecas nativas. Los nombres técnicos de plataformas no se
 * traducen porque son identificadores de producto; los textos de la interfaz se
 * obtienen siempre desde Idioma.
 */
public final class ModeloPoliticaHardware {

	private ModeloPoliticaHardware() {
	}

	public enum TipoEntrada {
		SISTEMA_OPERATIVO("os"), ARQUITECTURA("arch"), CPU("cpu");

		private final String clave;

		TipoEntrada(String clave) {
			this.clave = clave;
		}

		public String clave() {
			return clave;
		}
	}

	public enum Estado {
		SIN_REGLA, RECOMENDADO, NEUTRAL, DESACONSEJADO;

		public static Estado desdeTexto(String texto) {
			if (texto == null) {
				return SIN_REGLA;
			}
			try {
				return valueOf(texto.trim().toUpperCase(Locale.ROOT));
			} catch (IllegalArgumentException ex) {
				return SIN_REGLA;
			}
		}
	}

	public static final class Entrada {
		private final String id;
		private final TipoEntrada tipo;
		private final String familia;
		private final String nombre;
		private final Estado sugerencia;
		private final List<String> aliasNormalizados;

		public Entrada(String id, TipoEntrada tipo, String familia, String nombre, Estado sugerencia, String... alias) {
			this.id = validarId(id);
			this.tipo = tipo;
			this.familia = familia == null ? "" : familia;
			this.nombre = nombre == null ? id : nombre;
			this.sugerencia = sugerencia == null ? Estado.NEUTRAL : sugerencia;

			List<String> normalizados = new ArrayList<String>();
			agregarAlias(normalizados, nombre);
			agregarAlias(normalizados, id.replace('_', ' '));
			if (alias != null) {
				for (String valor : alias) {
					agregarAlias(normalizados, valor);
				}
			}
			this.aliasNormalizados = Collections.unmodifiableList(normalizados);
		}

		public String id() {
			return id;
		}

		public TipoEntrada tipo() {
			return tipo;
		}

		public String familia() {
			return familia;
		}

		public String nombre() {
			return nombre;
		}

		public Estado sugerencia() {
			return sugerencia;
		}

		public boolean esGenerica() {
			return id.endsWith("_generic");
		}

		public List<String> aliasNormalizados() {
			return aliasNormalizados;
		}

		/**
		 * Devuelve una puntuación de coincidencia. Se usa el alias más largo para que
		 * una entrada específica, como "SPARC M8", gane a una entrada genérica como
		 * "SPARC".
		 */
		public int puntuacion(String textoNormalizado) {
			if (textoNormalizado == null || textoNormalizado.isEmpty()) {
				return -1;
			}
			int mejor = -1;
			for (String alias : aliasNormalizados) {
				if (alias.length() < 2) {
					continue;
				}
				if (contieneToken(textoNormalizado, alias)) {
					mejor = Math.max(mejor, alias.length());
				}
			}
			return mejor;
		}

		private static boolean contieneToken(String texto, String alias) {
			int desde = 0;
			while (true) {
				int indice = texto.indexOf(alias, desde);
				if (indice < 0) {
					return false;
				}
				int fin = indice + alias.length();
				boolean izquierda = indice == 0 || !Character.isLetterOrDigit(texto.charAt(indice - 1));
				boolean derecha = fin == texto.length() || !Character.isLetterOrDigit(texto.charAt(fin));
				// Los modelos Intel usan el número de generación como prefijo del número
				// completo: "i9-14" debe coincidir con "i9-14900K".
				if (!derecha && fin < texto.length() && Character.isDigit(alias.charAt(alias.length() - 1))
						&& Character.isDigit(texto.charAt(fin))) {
					derecha = true;
				}
				if (izquierda && derecha) {
					return true;
				}
				desde = indice + 1;
			}
		}

		private static void agregarAlias(List<String> lista, String valor) {
			String normalizado = normalizar(valor);
			if (!normalizado.isEmpty() && !lista.contains(normalizado)) {
				lista.add(normalizado);
			}
		}

		private static String validarId(String id) {
			if (id == null || id.trim().isEmpty()) {
				throw new IllegalArgumentException("ID de plataforma vacío");
			}
			String limpio = id.trim().toLowerCase(Locale.ROOT);
			for (int i = 0; i < limpio.length(); i++) {
				char c = limpio.charAt(i);
				if (!(Character.isLetterOrDigit(c) || c == '_' || c == '-' || c == '.')) {
					throw new IllegalArgumentException("ID de plataforma no válido: " + id);
				}
			}
			return limpio;
		}
	}

	public static Entrada buscarMejorCoincidencia(List<Entrada> entradas, String texto) {
		String normalizado = normalizar(texto);
		Entrada mejorEspecifica = null;
		Entrada mejorGenerica = null;
		int puntuacionEspecifica = -1;
		int puntuacionGenerica = -1;
		if (entradas == null || normalizado.isEmpty()) {
			return null;
		}
		for (Entrada entrada : entradas) {
			int puntuacion = entrada.puntuacion(normalizado);
			if (puntuacion < 0) {
				continue;
			}
			if (entrada.esGenerica()) {
				if (puntuacion > puntuacionGenerica) {
					puntuacionGenerica = puntuacion;
					mejorGenerica = entrada;
				}
			} else if (puntuacion > puntuacionEspecifica) {
				puntuacionEspecifica = puntuacion;
				mejorEspecifica = entrada;
			}
		}
		return mejorEspecifica != null ? mejorEspecifica : mejorGenerica;
	}

	public static String normalizar(String texto) {
		if (texto == null) {
			return "";
		}
		String n = Normalizer.normalize(texto, Normalizer.Form.NFD);
		StringBuilder sb = new StringBuilder(n.length());
		for (int i = 0; i < n.length(); i++) {
			char c = n.charAt(i);
			if (Character.getType(c) == Character.NON_SPACING_MARK) {
				continue;
			}
			if (Character.isLetterOrDigit(c)) {
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(' ');
			}
		}
		return sb.toString().trim().replaceAll("\\s+", " ");
	}
}
