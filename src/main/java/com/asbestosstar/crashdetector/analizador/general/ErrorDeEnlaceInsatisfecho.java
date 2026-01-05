package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ErrorDeEnlaceInsatisfecho implements Verificaciones {

	private boolean activado = false;
	private String nombreBiblioteca = "";
	private final StringBuilder mensaje = new StringBuilder();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Patrón para detectar errores de enlace insatisfecho sin hardcodear binarios
		// específicos
		Pattern pattern = Pattern.compile("java\\.lang\\.UnsatisfiedLinkError: Failed to locate library: (?<name>.*)");
		Matcher matcher = pattern.matcher(contenidoConsola);

		if (matcher.find()) {
			nombreBiblioteca = matcher.group("name");
			activado = true;

			// Construir mensaje en el formato solicitado
			mensaje.append(MonitorDePID.idioma.error_enlace_insatisfecho(nombreBiblioteca));
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorDeEnlaceInsatisfecho();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 700.0f;
	}

	@Override
	public String mensaje() {
		return mensaje.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_enlace_insatisfecho();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucion_para_error_enlace_insatisfecho()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "enlace_insatisfecho";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// TODO Auto-generated method stub
		return false;// TODO
	}
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	
	

}