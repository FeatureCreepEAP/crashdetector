package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de Fabric al cargar entrypoints (ej: ModMenu). Estos errores
 * suelen indicar incompatibilidad del mod.
 */
public class ErrorEntrypointFabric implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String modNombre = "";

	private static final String ENTRYPOINT_EXCEPTION = "net.fabricmc.loader.api.EntrypointException";
	private static final String EXCEPTION_WHILE_LOADING = "Exception while loading entries for entrypoint";
	private static final String PROVIDED_BY = "provided by '";
	private static final String MOD_DESCONOCIDO = "mod desconocido";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ENTRYPOINT_EXCEPTION, EXCEPTION_WHILE_LOADING, PROVIDED_BY };
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

		// Buscar el patrón base sin regex
		if (lineaContieneErrorEntrypoint(linea)) {

			// Extraer el nombre del mod entre comillas simples después de "provided by '"
			int inicio = linea.indexOf(PROVIDED_BY);
			if (inicio != -1) {
				inicio += PROVIDED_BY.length();
				int fin = linea.indexOf('\'', inicio);
				if (fin != -1) {
					this.modNombre = linea.substring(inicio, fin).trim();
				} else {
					this.modNombre = MOD_DESCONOCIDO;
				}
			} else {
				this.modNombre = MOD_DESCONOCIDO;
			}

			this.activado = true;
			String enlace = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.error_entrypoint_fabric_html(modNombre)
					+ (enlace.isEmpty() ? "" : " " + enlace);
		}
	}

	private boolean lineaContieneErrorEntrypoint(String linea) {
		return linea.contains(ENTRYPOINT_EXCEPTION) && linea.contains(EXCEPTION_WHILE_LOADING)
				&& linea.contains(PROVIDED_BY);
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
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}