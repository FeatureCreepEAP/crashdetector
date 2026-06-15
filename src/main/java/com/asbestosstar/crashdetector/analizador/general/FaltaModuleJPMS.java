package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class FaltaModuleJPMS implements VerificacionRapida {

	private boolean activado = false;
	private final Set<String> errores = new HashSet<>();
	private final List<String> enlaces = new ArrayList<>();

	/**
	 * Bandera ligera para indicar si el log contiene, en general, el patrón de
	 * módulos JPMS faltantes. Esto permite que el verificador por línea se salte
	 * todo el trabajo si no hay ninguna coincidencia global.
	 */
	private boolean posibleFaltaModulo = false;

	private static final String FIND_EXCEPTION_MODULE = "java.lang.module.FindException: Module ";
	private static final String NOT_FOUND_REQUIRED_BY = " not found, required by ";
	private static final String MODULE = "Module ";
	private static final String NOT_FOUND = " not found";
	private static final String REQUIRED_BY = "required by ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { FIND_EXCEPTION_MODULE, NOT_FOUND_REQUIRED_BY };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneFaltaModulo(evento.linea)) {
			posibleFaltaModulo = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			posibleFaltaModulo = false;
			return;
		}

		String contenidoConsola = consola.contenido_verificar;

		// Verificación global mínima: solo comprobamos si aparecen los fragmentos
		// clave del error JPMS. El análisis real se hace en el método por línea.
		posibleFaltaModulo = contenidoConsola.contains(FIND_EXCEPTION_MODULE)
				&& contenidoConsola.contains(NOT_FOUND_REQUIRED_BY);
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return posibleFaltaModulo;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		// Si el log no parece contener el error, evitamos trabajo extra por cada línea.
		if (!posibleFaltaModulo || linea == null) {
			return;
		}

		if (lineaContieneFaltaModulo(linea)) {

			try {
				String modNecesitado = extraerEntre(linea, MODULE, NOT_FOUND);
				String modRequeridor = extraerDespues(linea, REQUIRED_BY);

				if (modNecesitado.length() == 0 || modRequeridor.length() == 0) {
					return;
				}

				String mensaje = MonitorDePID.idioma.jpms_modules_faltas(modNecesitado,
						modRequeridor + ModulesDuplicadosJavaModulePlatform.procesarModulo(modRequeridor));

				// Solo agregar si es un error nuevo
				if (errores.add(mensaje)) {
					String enlace = consola.agregarErrorALectador(numero_de_linea, this);
					enlaces.add(enlace);
					activado = true;
				}
			} catch (Exception e) {
				// Ignora errores de parseo para evitar fallos críticos,
				// pero aún así registra la línea como problema.
				consola.agregarErrorALectador(numero_de_linea, this);
			}
		}
	}

	private boolean lineaContieneFaltaModulo(String linea) {
		return linea.contains(FIND_EXCEPTION_MODULE) && linea.contains(NOT_FOUND_REQUIRED_BY);
	}

	private String extraerEntre(String texto, String inicioTexto, String finTexto) {
		if (texto == null || inicioTexto == null || finTexto == null)
			return "";

		int inicio = texto.indexOf(inicioTexto);
		if (inicio < 0)
			return "";

		inicio += inicioTexto.length();

		int fin = texto.indexOf(finTexto, inicio);
		if (fin < 0 || fin <= inicio)
			return "";

		return sinEspaciosLaterales(texto.substring(inicio, fin));
	}

	private String extraerDespues(String texto, String marcador) {
		if (texto == null || marcador == null)
			return "";

		int inicio = texto.indexOf(marcador);
		if (inicio < 0)
			return "";

		inicio += marcador.length();

		if (inicio >= texto.length())
			return "";

		return sinEspaciosLaterales(texto.substring(inicio));
	}

	private String sinEspaciosLaterales(String texto) {
		if (texto == null)
			return "";

		int inicio = 0;
		int fin = texto.length();

		while (inicio < fin && Character.isWhitespace(texto.charAt(inicio))) {
			inicio++;
		}

		while (fin > inicio && Character.isWhitespace(texto.charAt(fin - 1))) {
			fin--;
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public Verificaciones nueva() {
		return new FaltaModuleJPMS();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1000f; // Prioridad alta para errores de módulos JPMS [[7]]
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		Iterator<String> erroresIter = errores.iterator();
		Iterator<String> enlacesIter = enlaces.iterator();

		while (erroresIter.hasNext() && enlacesIter.hasNext()) {
			String error = erroresIter.next();
			String enlace = enlacesIter.next();
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		// TODO Auto-generated method stub
		return MonitorDePID.idioma.nombre_de_falta_module_jmps();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionFaltasClases()).construir();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "falta_module_jpms";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		// Para ser conservadores, solo marcamos el trazo como perteneciente a este
		// verificador si:
		// - Ya se ha activado (es decir, se detectó al menos un error JPMS en el log),
		// y
		// - El texto del trazo contiene claramente el patrón de FindException de
		// módulos faltantes.
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		return t.contains(FIND_EXCEPTION_MODULE) && t.contains(NOT_FOUND_REQUIRED_BY);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}