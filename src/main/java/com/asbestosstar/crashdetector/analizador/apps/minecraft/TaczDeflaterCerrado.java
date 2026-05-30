package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
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

	// Flag global para saber si en algún lugar del log aparece la NPE concreta
	private boolean tieneDeflaterCerradoEnLog = false;

	/**
	 * Verificación global sobre el contenido completo de la consola.
	 * <p>
	 * Aquí solo se comprueba si aparece la NPE "Caused by:
	 * java.lang.NullPointerException: Deflater has been closed" en el texto
	 * completo del log. La detección de la línea concreta del stack de
	 * {@code GetJarResources.backupFiles} se hace en
	 * {@link #verificarPorLinea(Consola, String, int)}.
	 * </p>
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		tieneDeflaterCerradoEnLog = contenido
				.contains("Caused by: java.lang.NullPointerException: Deflater has been closed");
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		// Si globalmente no hemos visto la NPE de Deflater, no seguimos
		if (!tieneDeflaterCerradoEnLog) {
			return;
		}

		// Buscar el frame específico del stack de TACZ
		if (linea.contains("at com.tacz.guns.util.GetJarResources.lambda$backupFiles$2")) {

			// Buscar el JAR que contiene la clase implicada
			String classPath = "com/tacz/guns/util/GetJarResources.class";
			for (ArchivoDeMod mod : Buscador.buscarModsConTermino(classPath)) {
				modsUbicacion.add(mod.ubicacion_para_publicar());
			}

			// Mensaje y enlace
			mensaje = MonitorDePID.idioma.errorTaczDeflaterCerrado(modsUbicacion) + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
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
		if (!t.contains("NullPointerException: Deflater has been closed")) {
			return false;
		}

		// Afinar con la clase/método implicados
		if (t.contains("com.tacz.guns.util.GetJarResources")
				&& (t.contains("backupFiles") || t.contains("lambda$backupFiles$2"))) {
			return true;
		}

		return false;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
