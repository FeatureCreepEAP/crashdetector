package com.asbestosstar.crashdetector.analizador.general;


import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Detecta errores de Fabric al cargar entrypoints (ej: ModMenu).
 * Estos errores suelen indicar incompatibilidad del mod.
 */
public class ErrorEntrypointFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String modNombre = "";

	@Override
	public void verificar(Consola consola) {
		// No se usa; el sistema llama a verificar(Consola, String, int)
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		if (activado || linea == null) {
			return;
		}

		// Buscar el patrón base sin regex
		if (linea.contains("net.fabricmc.loader.api.EntrypointException")
				&& linea.contains("Exception while loading entries for entrypoint")
				&& linea.contains("provided by '")) {

			// Extraer el nombre del mod entre comillas simples después de "provided by '"
			int inicio = linea.indexOf("provided by '");
			if (inicio != -1) {
				inicio += "provided by '".length();
				int fin = linea.indexOf('\'', inicio);
				if (fin != -1) {
					this.modNombre = linea.substring(inicio, fin).trim();
				} else {
					this.modNombre = "mod desconocido";
				}
			} else {
				this.modNombre = "mod desconocido";
			}

			this.activado = true;
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.error_entrypoint_fabric_html(modNombre) + (enlace.isEmpty() ? "" : " " + enlace);
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorEntrypointFabric();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1300.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_error_entrypoint_fabric();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_eliminar_mod(modNombre));
		builder.agregarEtiqueta(MonitorDePID.idioma.solucion_actualizar_mod(modNombre));
		return builder.construir();
	}

	@Override
	public String id() {
		return "error_entrypoint_fabric";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
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
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"+this.getClass().getSimpleName()+".java";
	}
	
	
	
	
	
	
}