package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscador;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores IllegalAccessError donde un mod intenta acceder a un método o
 * campo privado/protegido.
 *
 * Ejemplo: java.lang.IllegalAccessError: class X tried to access private method
 * Y
 */
public class AccesoIlegalMod implements VerificacionRapida {

	private boolean activado = false;
	private boolean analizarLineas = false;
	private String enlace = "";

	private String claseOrigen = "";
	private String miembroAccedido = "";
	private String claseMiembroAccedido = "";

	private final List<ArchivoDeMod> modsClaseOrigen = new ArrayList<>();
	private final List<ArchivoDeMod> modsClaseMiembroAccedido = new ArrayList<>();

	private static final String ILLEGAL_ACCESS_ERROR = "java.lang.IllegalAccessError";
	private static final String TRIED_TO_ACCESS = " tried to access ";
	private static final String CLASS_PREFIX = "class ";
	private static final String INTERFACE_PREFIX = "interface ";

	@Override
	public String[] patronesRapidos() {
		return new String[] { ILLEGAL_ACCESS_ERROR, TRIED_TO_ACCESS };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (lineaContieneAccesoIlegal(evento.linea)) {
			analizarLineas = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {

		if (consola == null || consola.contenido_verificar == null)
			return;

		String log = consola.contenido_verificar;

		if (log.contains(ILLEGAL_ACCESS_ERROR) && log.contains(TRIED_TO_ACCESS)) {
			analizarLineas = true;
		}
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return analizarLineas && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!analizarLineas || linea == null || activado)
			return;

		if (lineaContieneAccesoIlegal(linea)) {

			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);

			int inicioClase = linea.indexOf(CLASS_PREFIX);
			int finClase = linea.indexOf(" tried to access");

			if (inicioClase > -1 && finClase > inicioClase) {
				claseOrigen = limpiarClase(linea.substring(inicioClase + CLASS_PREFIX.length(), finClase));
			}

			int idxTried = linea.indexOf("tried to access");
			if (idxTried > -1) {
				int inicioMiembro = idxTried + "tried to access".length();
				if (inicioMiembro < linea.length()) {
					miembroAccedido = linea.substring(inicioMiembro).trim();
					claseMiembroAccedido = extraerClaseDesdeMiembro(miembroAccedido);
				}
			}

			buscarModsRelacionados();

			activado = true;
		}
	}

	private boolean lineaContieneAccesoIlegal(String linea) {
		return linea.contains(ILLEGAL_ACCESS_ERROR) && linea.contains(TRIED_TO_ACCESS);
	}

	private void buscarModsRelacionados() {
		try {
			Buscador.cargar();

			modsClaseOrigen.clear();
			modsClaseMiembroAccedido.clear();

			if (claseOrigen != null && !claseOrigen.isEmpty()) {
				modsClaseOrigen.addAll(buscarModsConClase(claseOrigen));
			}

			if (claseMiembroAccedido != null && !claseMiembroAccedido.isEmpty()
					&& !claseMiembroAccedido.equals(claseOrigen)) {
				modsClaseMiembroAccedido.addAll(buscarModsConClase(claseMiembroAccedido));
			}
		} catch (Throwable ignorado) {
		}
	}

	private List<ArchivoDeMod> buscarModsConClase(String clase) {
		List<ArchivoDeMod> encontrados = new ArrayList<>();

		try {
			String clasePunto = limpiarClase(clase);
			String claseInterna = clasePunto.replace('.', '/');

			List<ArchivoDeMod> resultadoInterno = Buscador.buscarModsConTermino(claseInterna);
			if (resultadoInterno != null) {
				encontrados.addAll(resultadoInterno);
			}

			if (!claseInterna.equals(clasePunto)) {
				List<ArchivoDeMod> resultadoPunto = Buscador.buscarModsConTermino(clasePunto);
				if (resultadoPunto != null) {
					encontrados.addAll(resultadoPunto);
				}
			}
		} catch (Throwable ignorado) {
		}

		return encontrados.stream().distinct().collect(Collectors.toList());
	}

	private String limpiarClase(String clase) {
		if (clase == null)
			return "";

		String limpia = clase.trim();

		int modulo = limpia.indexOf(" in ");
		if (modulo > -1) {
			limpia = limpia.substring(0, modulo).trim();
		}

		if (limpia.startsWith(CLASS_PREFIX)) {
			limpia = limpia.substring(CLASS_PREFIX.length()).trim();
		}

		if (limpia.startsWith(INTERFACE_PREFIX)) {
			limpia = limpia.substring(INTERFACE_PREFIX.length()).trim();
		}

		return limpia.replace('/', '.').replace("'", "").replace("\"", "").trim();
	}

	private String extraerClaseDesdeMiembro(String miembro) {
		if (miembro == null)
			return "";

		String texto = miembro.replace("'", "").replace("\"", "").trim();

		int parentesis = texto.indexOf('(');
		String antesParentesis = parentesis > -1 ? texto.substring(0, parentesis) : texto;

		int fin = antesParentesis.length();

		while (fin > 0) {
			int inicio = antesParentesis.lastIndexOf(' ', fin - 1);
			String posible = antesParentesis.substring(inicio + 1, fin).trim();

			int ultimoPunto = posible.lastIndexOf('.');
			if (ultimoPunto > 0) {
				String clase = posible.substring(0, ultimoPunto);
				if (clase.contains(".")) {
					return limpiarClase(clase);
				}
			}

			if (inicio < 0) {
				break;
			}

			fin = inicio;
		}

		return "";
	}

	private String formatearMods(List<ArchivoDeMod> mods) {
		if (mods == null || mods.isEmpty()) {
			return "";
		}

		return mods.stream().map(mod -> "<b>" + Buscador.rutaParaPublicar(mod.ubicacion_para_publicar()) + "</b>")
				.distinct().collect(Collectors.joining(", "));
	}

	@Override
	public Verificaciones nueva() {
		return new AccesoIlegalMod();
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

		StringBuilder sb = new StringBuilder();
		sb.append(MonitorDePID.idioma.mensajeAccesoIlegalMod(claseOrigen, miembroAccedido));

		String modsOrigen = formatearMods(modsClaseOrigen);
		String modsDestino = formatearMods(modsClaseMiembroAccedido);

		List<String> valores = new ArrayList<>();

		if (!modsOrigen.isEmpty()) {
			valores.add(modsOrigen);
		}

		if (!modsDestino.isEmpty()) {
			valores.add(modsDestino);
		}

		if (!valores.isEmpty()) {
			sb.append("<p>(").append(String.join(", ", valores)).append(")</p>");
		}

		sb.append(this.enlace);
		return sb.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreAccesoIlegalMod();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "acceso_ilegal_mod";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}