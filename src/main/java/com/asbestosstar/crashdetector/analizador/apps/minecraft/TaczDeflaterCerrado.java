package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

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

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		String[] lineas = contenido.split(Verificaciones.nl);

		boolean tieneDeflaterCerrado = contenido
				.contains("Caused by: java.lang.NullPointerException: Deflater has been closed");

		int lineaStack = -1;
		for (int i = 0; i < lineas.length; i++) {
			if (lineas[i].contains("at com.tacz.guns.util.GetJarResources.lambda$backupFiles$2")) {
				lineaStack = i;
				break;
			}
		}

		if (tieneDeflaterCerrado && lineaStack >= 0) {
			// Buscar el JAR que contiene la clase implicada
			String classPath = "com/tacz/guns/util/GetJarResources.class";
			for (ArchivoDeMod mod : Buscardor.buscarModsConTermino(classPath)) {
				modsUbicacion.add(mod.ubicacion_para_publicar());
			}

			// Mensaje y enlace
			mensaje = MonitorDePID.idioma.errorTaczDeflaterCerrado(modsUbicacion) + Verificaciones.nl_html;
			enlaceHtml = consola.agregarErrorALectador(lineaStack, this);
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

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}

}
