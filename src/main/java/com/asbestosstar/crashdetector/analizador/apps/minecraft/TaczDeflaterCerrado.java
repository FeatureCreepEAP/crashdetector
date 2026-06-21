package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta el fallo de TACZ al comprimir (Deflater cerrado) cuando aparece el
 * stack en GetJarResources.backupFiles y la NPE "Deflater has been closed".
 * Muestra el/los JAR(es) que contienen la clase afectada y enlaza al log.
 */
public class TaczDeflaterCerrado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private final List<String> modsUbicacion = new ArrayList<>();

	private static final String TEXTO_DEFLATER_CERRADO = "Caused by: java.lang.NullPointerException: Deflater has been closed";
	private static final String TEXTO_DEFLATER_CERRADO_CORTO = "NullPointerException: Deflater has been closed";
	private static final String TEXTO_STACK_TACZ = "at com.tacz.guns.util.GetJarResources.lambda$backupFiles$2";
	private static final String CLASE_TACZ = "com.tacz.guns.util.GetJarResources";
	private static final String METODO_BACKUP_FILES = "backupFiles";
	private static final String METODO_BACKUP_FILES_LAMBDA = "lambda$backupFiles$2";
	private static final String CLASS_PATH = "com/tacz/guns/util/GetJarResources.class";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_DEFLATER_CERRADO, TEXTO_DEFLATER_CERRADO_CORTO, TEXTO_STACK_TACZ, CLASE_TACZ,
				METODO_BACKUP_FILES_LAMBDA };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscar el frame específico del stack de TACZ
		if (linea.contains(TEXTO_STACK_TACZ)) {

			// Buscar el JAR que contiene la clase implicada
			for (ArchivoDeMod mod : Buscador.buscarModsConTermino(CLASS_PATH)) {
				modsUbicacion.add(mod.ubicacion_para_publicar());
			}

			// Mensaje y enlace
			mensaje = MonitorDePID.idioma.errorTaczDeflaterCerrado(modsUbicacion) + VerificacionesLegacy.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public VerificacionesLegacy nueva() {
		return new TaczDeflaterCerrado();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f;
	}

	@Override
	public String mensaje() {
		if (!activado)
			return "";
		return mensaje + enlaceHtml;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeTaczDeflaterCerrado();
	}

	@Override
	public QuickFix solucion() {
		// Un único paso: sugerir activar el modo de depuración del pack de TACZ.
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoTaczDeflaterCerrado())
				.construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "tacz_deflater_cerrado";
	}

	/**
	 * Indica si este verificador debe "ocupar" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, el trazo se considera perteneciente a este
	 * problema solo cuando:
	 * <ul>
	 * <li>El verificador ya se activó.</li>
	 * <li>El trazo contiene la NPE "Deflater has been closed".</li>
	 * <li>Y además hace referencia a la clase
	 * {@code com.tacz.guns.util.GetJarResources} (idealmente al método
	 * {@code backupFiles}).</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo antes que
	 * marcar un trazo que no pertenezca realmente a este error.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		// La NPE característica del problema
		if (!t.contains(TEXTO_DEFLATER_CERRADO_CORTO)) {
			return false;
		}

		// Afinar con la clase/método implicados
		if (t.contains(CLASE_TACZ) && (t.contains(METODO_BACKUP_FILES) || t.contains(METODO_BACKUP_FILES_LAMBDA))) {
			return true;
		}

		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}