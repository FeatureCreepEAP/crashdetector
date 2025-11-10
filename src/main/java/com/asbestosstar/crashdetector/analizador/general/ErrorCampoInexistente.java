package com.asbestosstar.crashdetector.analizador.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores NoSuchFieldError que ocurren cuando un mod intenta acceder a
 * un campo que ya no existe en la versión actual del juego u otro mod. Soporta
 * formatos: - "java.lang.NoSuchFieldError: DEAD_PLANKS" -
 * "java.lang.NoSuchFieldError: Class X does not have member field
 * 'paquete.Clase$Tipo nombreCampo'"
 */
public class ErrorCampoInexistente implements Verificaciones {

	// Patrón simple tradicional: toma el token inmediatamente después de
	// "NoSuchFieldError:"
	private static final Pattern PATRON_ERROR_SIMPLE = Pattern.compile("java\\.lang\\.NoSuchFieldError:\\s+(\\w+)");

	// Patrón extendido (Forge/Fabric más verboso): extrae el nombre de campo dentro
	// de comillas simples.
	// Ejemplo:
	// java.lang.NoSuchFieldError: Class
	// traben.entity_model_features.config.EMFConfig
	// does not have member field
	// 'traben.entity_model_features.config.EMFConfig$PhysicsModCompatChoice
	// attemptPhysicsModPatch_2'
	private static final Pattern PATRON_ERROR_MIEMBRO = Pattern.compile(
			"java\\.lang\\.NoSuchFieldError:\\s*Class\\s+[^\\s]+\\s+does\\s+not\\s+have\\s+member\\s+field\\s+'([^']+)'");

	/**
	 * Bandera para indicar si ya se detectó este problema en el log.
	 */
	private boolean activado = false;

	/**
	 * Bandera global ligera: solo indica si el log contiene alguna referencia a
	 * {@code java.lang.NoSuchFieldError}. Sirve como filtro rápido para el análisis
	 * línea a línea.
	 */
	private boolean posibleNoSuchField = false;

	/**
	 * Bandera interna para indicar que ya se encontró la línea del error y se está
	 * esperando la primera línea de stack que comience por {@code "at "}.
	 */
	private boolean esperandoLineaStack = false;

	private String mensaje = "";
	private String enlaceHtml = "";

	// Datos que se extraen durante el análisis y se usan al construir el mensaje
	private String nombreCampoDetectado = "";
	private String lineaError = "";
	private String lineaStack = "";

	@Override
	public void verificar(Consola consola) {
		// Análisis global muy ligero: solo comprobamos si aparece el texto base
		// de NoSuchFieldError en algún punto del log.
		// Si no aparece, la verificación por línea saldrá inmediatamente y evitamos
		// trabajo innecesario.
		String contenido = consola.contenido_verificar;
		this.posibleNoSuchField = contenido != null && contenido.contains("java.lang.NoSuchFieldError:");
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya estamos activados, o ni siquiera hay rastro de NoSuchFieldError en
		// todo el log, no hacemos más trabajo.
		if (activado && !esperandoLineaStack) {
			return;
		}
		if (!posibleNoSuchField || linea == null) {
			return;
		}

		String l = linea;

		// Si todavía no se ha detectado el error principal, buscarlo en esta línea
		if (!activado && l.contains("java.lang.NoSuchFieldError:")) {
			// Intentar patrón extendido primero
			String nombreCampo = null;
			Matcher mExt = PATRON_ERROR_MIEMBRO.matcher(l);
			if (mExt.find()) {
				// Dentro de la comilla simple puede venir "paquete.Clase$Tipo nombreCampo"
				// Nos quedamos con el último token después del espacio, si existe.
				String crudo = mExt.group(1).trim();
				int idxEspacio = crudo.lastIndexOf(' ');
				nombreCampo = (idxEspacio >= 0 && idxEspacio < crudo.length() - 1) ? crudo.substring(idxEspacio + 1)
						: crudo;
			} else {
				// Fallback: patrón simple clásico
				Matcher mSimple = PATRON_ERROR_SIMPLE.matcher(l);
				if (mSimple.find()) {
					nombreCampo = mSimple.group(1);
				}
			}

			if (nombreCampo != null && !nombreCampo.isEmpty()) {
				this.nombreCampoDetectado = nombreCampo;
				this.lineaError = l.trim();
				this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
				this.activado = true;
				this.esperandoLineaStack = true;
				// No construimos el mensaje todavía; se hará en mensaje() para poder incluir
				// la primera línea "at ..." que encontremos después.
				return;
			}
		}

		// Si ya se detectó la línea del error y estamos esperando la primera línea
		// de stack que comience por "at ", la capturamos aquí.
		if (activado && esperandoLineaStack) {
			String trim = l.trim();
			if (trim.startsWith("at ")) {
				this.lineaStack = trim;
				this.esperandoLineaStack = false;
			}
		}
	}

	// Utilidad para escapar HTML básico
	private String escapeHtml(String s) {
		if (s == null)
			return "";
		return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCampoInexistente();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 850.0f;
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		// Si ya construimos el mensaje previamente, devolverlo tal cual
		if (mensaje != null && !mensaje.isEmpty()) {
			return mensaje;
		}

		// Construir mensaje: primero el mensaje localizado, luego un bloque
		// monoespaciado con contexto y enlace
		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.errorCampoInexistente(nombreCampoDetectado, lineaError));
		sb.append(Verificaciones.nl_html);
		sb.append("<span style='color:#888888; font-family:monospace;'>");
		// Mostrar la línea "at ..." si existe
		if (lineaStack != null && !lineaStack.isEmpty()) {
			sb.append(escapeHtml(lineaStack));
		}
		sb.append("</span>");
		sb.append(Verificaciones.nl_html);
		sb.append(enlaceHtml);

		this.mensaje = sb.toString();
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_campo_inexistente();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.paso1_campo_inexistente())
				.agregarEtiqueta(MonitorDePID.idioma.paso2_campo_inexistente()).construir();
	}

	@Override
	public String id() {
		return "error_campo_inexistente";
	}

	/**
	 * Marca trazos que pertenezcan a este problema.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se ha activado, y</li>
	 * <li>El trazo contiene claramente "NoSuchFieldError".</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a asociar un
	 * trazo que no corresponda realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		return trazo.trace.contains("NoSuchFieldError");
	}
}
