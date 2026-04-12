package com.asbestosstar.crashdetector.gui.tipos.docs;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;

public class Documento {

	public static final Documento NINGUN = new Documento();

	/**
	 * Mapa de código de idioma -> nombre de archivo del documento. Ejemplo: es ->
	 * guia.md en -> guide.md
	 */
	public final Map<String, String> DOCS = new HashMap<>();

	public Documento() {
	}

	private Documento(Map<String, String> docs) {
		if (docs != null) {
			this.DOCS.putAll(docs);
		}
	}

	/**
	 * Devuelve la ruta física del documento según el idioma actual.
	 *
	 * Estructura esperada: Statics.carpeta/docs/{idioma-en-espanol-ascii}/{archivo}
	 *
	 * Si no existe documento para el idioma actual, devuelve null.
	 */
	public Path ubicacion() {
		String codigoIdioma = MonitorDePID.idioma.codigo();

		if (DOCS.containsKey(codigoIdioma)) {
			return Statics.carpeta.resolve("docs")
					.resolve(MonitorDePID.idioma.nombre_del_idioma_espanol_minusculas_ascii())
					.resolve(DOCS.get(codigoIdioma));
		}

		return null;
	}

	/**
	 * Enlace del documento con protocolo docs://
	 *
	 * Formato: docs://{codigoIdioma}/{nombreArchivo}
	 *
	 * Ejemplo: docs://es/manual.md
	 *
	 * Si no hay documento para el idioma actual, devuelve cadena vacía.
	 *
	 * @return enlace del documento usando protocolo docs://
	 */
	public String enlace() {
		String codigoIdioma = MonitorDePID.idioma.codigo();
		String archivo = DOCS.get(codigoIdioma);

		if (archivo == null || archivo.isEmpty()) {
			return "";
		}

		return "docs://" + codigoIdioma + "/" + archivo;
	}

	/**
	 * Crea un nuevo builder para construir instancias de Documento.
	 *
	 * @return builder vacío
	 */
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private final Map<String, String> docs = new HashMap<>();

		/**
		 * Añade o reemplaza un documento para un código de idioma.
		 *
		 * Ejemplo: .doc("es", "manual.md") .doc("en", "manual_en.md")
		 *
		 * @param codigoIdioma código del idioma, por ejemplo "es", "en", "ru"
		 * @param archivo      nombre del archivo del documento
		 * @return este builder
		 */
		public Builder doc(String codigoIdioma, String archivo) {
			if (codigoIdioma != null && !codigoIdioma.isEmpty() && archivo != null && !archivo.isEmpty()) {
				docs.put(codigoIdioma, archivo);
			}
			return this;
		}

		/**
		 * Añade varios documentos de una sola vez.
		 *
		 * @param documentos mapa de código de idioma -> nombre de archivo
		 * @return este builder
		 */
		public Builder docs(Map<String, String> documentos) {
			if (documentos != null) {
				for (Map.Entry<String, String> entry : documentos.entrySet()) {
					doc(entry.getKey(), entry.getValue());
				}
			}
			return this;
		}

		/**
		 * Construye la instancia final de Documento.
		 *
		 * @return documento construido
		 */
		public Documento build() {
			return new Documento(Collections.unmodifiableMap(new HashMap<>(docs)));
		}
	}
}