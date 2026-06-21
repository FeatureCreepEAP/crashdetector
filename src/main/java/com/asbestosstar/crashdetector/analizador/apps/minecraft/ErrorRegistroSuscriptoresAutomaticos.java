package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Analiza errores cuando falla el registro de suscriptores automáticos. Detecta
 * específicamente el error:
 *
 * "Failed to register automatic subscribers. ModID: [modid], class [classname]"
 *
 * Incluye tanto el modid como la clase en el mensaje y usa Buscardor para
 * encontrar los JARs relacionados.
 */
public class ErrorRegistroSuscriptoresAutomaticos implements Verificaciones {

	private boolean activado = false;

	private String mensaje = "";
	private String modId = "";
	private String nombreClase = "";

	private List<String> modsUbicacion = new ArrayList<>();

	private String enlaceHtml = "";

	private static final String TEXTO_ERROR = "Failed to register automatic subscribers. ModID: ";
	private static final String TEXTO_CLASE = ", class ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Verificación por línea del registro.
	 * <p>
	 * Busca el patrón:
	 *
	 * "Failed to register automatic subscribers. ModID: <modid>, class <clase>"
	 *
	 * en la línea actual, extrae modId y nombre de clase, localiza los mods
	 * implicados y registra la línea en el lector.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (linea == null) {
			return;
		}

		int inicio = linea.indexOf(TEXTO_ERROR);

		if (inicio < 0) {
			return;
		}

		// Mover al inicio real del modId.
		inicio += TEXTO_ERROR.length();

		if (inicio >= linea.length()) {
			return;
		}

		// Buscar el separador entre modId y clase.
		int separadorClase = linea.indexOf(TEXTO_CLASE, inicio);

		if (separadorClase < 0 || separadorClase <= inicio) {
			return;
		}

		// Extraer modId.
		modId = linea.substring(inicio, separadorClase).trim();

		if (modId.isEmpty()) {
			return;
		}

		// Inicio real de la clase.
		int inicioClase = separadorClase + TEXTO_CLASE.length();

		if (inicioClase >= linea.length()) {
			return;
		}

		// Buscar final de la clase.
		// Normalmente termina en espacio o final de línea.
		int finClase = linea.indexOf(' ', inicioClase);

		if (finClase < 0) {
			finClase = linea.length();
		}

		// Extraer clase.
		nombreClase = linea.substring(inicioClase, finClase).trim();

		if (nombreClase.isEmpty()) {
			return;
		}

		// Convierte el nombre de clase a formato de ruta para buscar en JARs.
		String classPath = nombreClase.replace('.', '/') + ".class";

		// Busca mods que contienen esta clase.
		List<ArchivoDeMod> modsPotenciales = Buscador.buscarModsConTermino(classPath);

		// Extrae las ubicaciones para publicar de cada mod encontrado.
		for (ArchivoDeMod mod : modsPotenciales) {
			modsUbicacion.add(mod.ubicacion_para_publicar());
		}

		mensaje = MonitorDePID.idioma.errorRegistroSuscriptoresAutomaticos(modId, nombreClase, modsUbicacion)
				+ VerificacionesLegacy.nl_html;

		enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		activado = true;
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new ErrorRegistroSuscriptoresAutomaticos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Prioridad media-alta
	}

	@Override
	public String mensaje() {
		if (!activado) {
			return "";
		}

		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_registro_suscriptores_automaticos();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_registro_suscriptores_automaticos(modId, nombreClase))
				.agregarEtiqueta(
						MonitorDePID.idioma.paso2_registro_suscriptores_automaticos(modId, nombreClase, modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_registro_suscriptores_automaticos(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso4_registro_suscriptores_automaticos()).construir();
	}

	@Override
	public String id() {
		// Sin acentos, en minúsculas, con guiones bajos.
		return "registro_subscriptores_automaticos";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve true cuando:
	 * <ul>
	 * <li>El verificador ya se activó.</li>
	 * <li>El trazo contiene el texto completo del error con los datos conocidos
	 * (modId y clase), o bien el patrón base.</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {

		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if (!modId.isEmpty() && !nombreClase.isEmpty()) {

			String esperado = TEXTO_ERROR + modId + TEXTO_CLASE + nombreClase;

			return t.contains(esperado);
		}

		// Fallback muy estricto.
		return t.contains(TEXTO_ERROR);
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}