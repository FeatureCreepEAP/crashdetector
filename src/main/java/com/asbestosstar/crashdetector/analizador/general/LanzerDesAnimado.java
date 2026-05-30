package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.Json.Nodo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el usuario está usando un launcher explícitamente
 * desaconsejado. Este verificador ignora completamente el archivo de launchers
 * recomendados, a menos que exista y sea no vacío (en cuyo caso muestra la
 * lista al final).
 */
public class LanzerDesAnimado implements Verificaciones {

	public static final Path ARCHIVO_DESANIMADOS = Statics.carpeta.resolve("lanzeres_desanimados.json");
	public static final Path ARCHIVO_ANIMADOS = Statics.carpeta.resolve("lanzeres_animados.json");

	private boolean activado = false;
	private String mensaje = "";
	boolean completa = false;

	@Override
	public void verificar(Consola consola) {
		if (completa) {
			return;
		}
		this.completa = true;

		String LANZADOR_ACTUAL = Statics.lanzer_del_app;

		if (LANZADOR_ACTUAL == null) {
			return;
		}

		LANZADOR_ACTUAL = LANZADOR_ACTUAL.trim();
		if (LANZADOR_ACTUAL.isEmpty()) {
			return;
		}

		// Si el archivo de desanimados no existe, no hacer nada.
		if (!ARCHIVO_DESANIMADOS.toFile().exists()) {
			return;
		}

		String contenido;
		try {
			contenido = new String(Files.readAllBytes(ARCHIVO_DESANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
		} catch (IOException e) {
			return;
		}

		if (contenido == null || contenido.trim().isEmpty()) {
			return;
		}

		// Parsear launchers desaconsejados
		Nodo raiz;
		try {
			raiz = Json.leer(contenido);
		} catch (Exception e) {
			return;
		}

		if (raiz == null || !raiz.esObjeto()) {
			return;
		}

		// Verificación estricta de existencia real de la clave.
		// Esto evita depender del comportamiento de Json.obtener(...)
		// cuando devuelve nodos "fantasma" o placeholders para claves inexistentes.
		boolean existeLauncher = false;
		for (String clave : raiz.claves()) {
			if (LANZADOR_ACTUAL.equals(clave)) {
				existeLauncher = true;
				break;
			}
		}

		if (!existeLauncher) {
			return;
		}

		// Solo obtener la entrada después de confirmar que la clave sí existe.
		Nodo entrada = raiz.obtener(LANZADOR_ACTUAL);
		if (entrada == null || entrada.esArreglo()) {
			return;
		}

		// Extraer motivo en el idioma correcto.
		// También aquí se comprueba primero si la clave de idioma existe realmente,
		// para no depender del comportamiento no estricto de obtener(...).
		String motivo = "";
		if (entrada.esObjeto()) {
			String langActual = MonitorDePID.idioma.codigo();
			String langRespaldo = Idioma.idioma_respaldo.obtener();
			String[] orden = { langActual, langRespaldo, "es" };

			for (String lang : orden) {
				if (lang == null) {
					continue;
				}

				lang = lang.trim();
				if (lang.isEmpty()) {
					continue;
				}

				boolean existeIdioma = false;
				for (String clave : entrada.claves()) {
					if (lang.equals(clave)) {
						existeIdioma = true;
						break;
					}
				}

				if (!existeIdioma) {
					continue;
				}

				Nodo nodoMotivo = entrada.obtener(lang);
				if (nodoMotivo != null && !nodoMotivo.esObjeto() && !nodoMotivo.esArreglo()) {
					String txt = nodoMotivo.comoCadena();
					if (txt != null && !txt.trim().isEmpty()) {
						motivo = txt;
						break;
					}
				}
			}
		}

		// Construir mensaje
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.lanzer_desanimado_titulo(LANZADOR_ACTUAL));

		if (!motivo.isEmpty()) {
			sb.append(nl_html).append(motivo);
		}

		sb.append(nl_html).append(MonitorDePID.idioma.lanzer_desanimado_problemas_comunes());

		// Solo mencionar launchers animados si el archivo existe y tiene contenido
		if (ARCHIVO_ANIMADOS.toFile().exists()) {
			try {
				String contenidoAnim = new String(Files.readAllBytes(ARCHIVO_ANIMADOS),
						java.nio.charset.StandardCharsets.UTF_8);

				if (contenidoAnim != null && !contenidoAnim.trim().isEmpty()) {
					Nodo raizAnim = Json.leer(contenidoAnim);

					if (raizAnim != null && raizAnim.esArreglo() && raizAnim.tamano() > 0) {
						sb.append(nl_html).append(MonitorDePID.idioma.lanzer_desanimado_usar_animados());
						sb.append(" ");

						boolean primero = true;
						for (int i = 0; i < raizAnim.tamano(); i++) {
							Nodo item = raizAnim.en(i);
							if (item != null && !item.esObjeto() && !item.esArreglo()) {
								String id = item.comoCadena();
								if (id != null) {
									id = id.trim();
									if (!id.isEmpty()) {
										if (!primero) {
											sb.append(", ");
										}
										sb.append("<code>").append(id).append("</code>");
										primero = false;
									}
								}
							}
						}
					}
				}
			} catch (Exception ignored) {
				// Ignorar errores al leer el archivo de launchers animados
			}
		}

		this.mensaje = sb.toString();
		this.activado = true;
	}

	@Override
	public Verificaciones nueva() {
		return new LanzerDesAnimado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400.0f;
	}

	@Override
	public String mensaje() {
		CrashDetectorLogger.log("LanzerDesAnimado: mensaje generado");
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_lanzer_desanimado();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.lanzer_desanimado_cambiar_lanzer());
		return builder.construir();
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "lanzer_desanimado";
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}