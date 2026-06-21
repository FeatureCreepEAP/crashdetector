package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class LenguajeProveedorCheck implements Verificaciones {

	private final Set<String> errores = new HashSet<>();
	public boolean activado = false;
	private final Map<String, String> enlacesPorError = new HashMap<>();

	private static final String MOD_FILE = "Mod File";
	private static final String NEEDS_LANGUAGE_PROVIDER = "needs language provider";
	private static final String LANGUAGE_PROVIDER = "language provider ";
	private static final String WE_HAVE_FOUND = "We have found ";
	private static final String FAILED_TO_LOAD_LANGUAGE_PROVIDER = "Failed to load language provider";
	private static final String LOADING_LANGUAGE_PROVIDER = "Loading language provider";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MOD_FILE, NEEDS_LANGUAGE_PROVIDER, FAILED_TO_LOAD_LANGUAGE_PROVIDER,
				LOADING_LANGUAGE_PROVIDER, WE_HAVE_FOUND };
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
	 * Se ejecuta para cada línea de la consola. Cuando encuentra una línea con el
	 * patrón "Mod File ... needs language provider ...", extrae los datos
	 * relevantes y busca más información en las líneas siguientes (como "We have
	 * found ...") usando el array cacheado de líneas.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		if (!linea.contains(MOD_FILE) || !linea.contains(NEEDS_LANGUAGE_PROVIDER)) {
			return;
		}

		try {
			// Extraer detalles del error
			String archivoJar = extraerEntre(linea, "Mod File ", " needs", 0);
			String proveedor = extraerDespuesHastaEspacio(linea, LANGUAGE_PROVIDER);

			if (archivoJar.isEmpty() || proveedor.isEmpty()) {
				return;
			}

			int separadorProveedor = proveedor.indexOf(':');
			if (separadorProveedor <= 0 || separadorProveedor + 1 >= proveedor.length()) {
				return;
			}

			String proveedorNombre = proveedor.substring(0, separadorProveedor);
			String req = proveedor.substring(separadorProveedor + 1);
			String encontrado = "";

			// Buscar versión disponible en líneas posteriores
			String[] lineasConsola = consola.lineas_verificar;
			if (lineasConsola != null) {
				for (int j = numero_de_linea; j < lineasConsola.length; j++) {
					String lineaPosterior = lineasConsola[j];
					if (lineaPosterior == null) {
						continue;
					}

					int idxEncontrado = lineaPosterior.indexOf(WE_HAVE_FOUND);
					if (idxEncontrado >= 0) {
						int inicio = idxEncontrado + WE_HAVE_FOUND.length();
						int fin = lineaPosterior.indexOf('§', inicio);
						if (fin < 0) {
							fin = lineaPosterior.length();
						}
						encontrado = limpiarEspacios(lineaPosterior, inicio, fin);
						break;
					}
				}
			}

			// Construir mensaje base
			String mensaje = MonitorDePID.idioma.errorProveedorVersion(archivoJar, proveedorNombre, req, encontrado);

			// Agregar mensaje especial para JavaFML/MCForge
			if (contieneJavaFML(proveedor)) {
				mensaje += Verificaciones.nl_html + MonitorDePID.idioma.errorJavaFML_MCForge();
			}

			// Solo registrar si es un error nuevo
			if (errores.add(mensaje)) {
				String enlace = consola.agregarErrorALectador(numero_de_linea, this);
				enlacesPorError.put(mensaje, enlace);
			}
			activado = true;

		} catch (Exception e) {
			CrashDetectorLogger.logException(e);
			// Registrar la línea incluso si falla el parseo
			consola.agregarErrorALectador(numero_de_linea, this);
		}
	}

	private boolean lineaContieneProblemaProveedor(String linea) {
		return linea.contains(NEEDS_LANGUAGE_PROVIDER) || linea.contains(FAILED_TO_LOAD_LANGUAGE_PROVIDER)
				|| linea.contains(LOADING_LANGUAGE_PROVIDER) || linea.contains(WE_HAVE_FOUND);
	}

	private String extraerEntre(String texto, String inicioTexto, String finTexto, int desde) {
		int inicio = texto.indexOf(inicioTexto, desde);
		if (inicio < 0) {
			return "";
		}

		inicio += inicioTexto.length();

		int fin = texto.indexOf(finTexto, inicio);
		if (fin < 0 || fin <= inicio) {
			return "";
		}

		return limpiarEspacios(texto, inicio, fin);
	}

	private String extraerDespuesHastaEspacio(String texto, String marcador) {
		int inicio = texto.indexOf(marcador);
		if (inicio < 0) {
			return "";
		}

		inicio += marcador.length();

		int fin = texto.indexOf(' ', inicio);
		if (fin < 0) {
			fin = texto.length();
		}

		return limpiarEspacios(texto, inicio, fin);
	}

	private boolean contieneJavaFML(String texto) {
		for (int i = 0; i + 7 <= texto.length(); i++) {
			char c0 = texto.charAt(i);
			if ((c0 == 'j' || c0 == 'J') && equalsIgnoreCaseAscii(texto, i, "javafml")) {
				return true;
			}
		}

		return false;
	}

	private boolean equalsIgnoreCaseAscii(String texto, int offset, String buscado) {
		if (offset + buscado.length() > texto.length()) {
			return false;
		}

		for (int i = 0; i < buscado.length(); i++) {
			char a = texto.charAt(offset + i);
			char b = buscado.charAt(i);

			if (a >= 'A' && a <= 'Z') {
				a = (char) (a + 32);
			}

			if (b >= 'A' && b <= 'Z') {
				b = (char) (b + 32);
			}

			if (a != b) {
				return false;
			}
		}

		return true;
	}

	private String limpiarEspacios(String texto, int inicio, int fin) {
		while (inicio < fin && texto.charAt(inicio) <= ' ') {
			inicio++;
		}

		while (fin > inicio && texto.charAt(fin - 1) <= ' ') {
			fin--;
		}

		if (inicio >= fin) {
			return "";
		}

		return texto.substring(inicio, fin);
	}

	@Override
	public Verificaciones nueva() {
		return new LenguajeProveedorCheck();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1450.0f;
	}

	@Override
	public String mensaje() {
		if (errores.isEmpty())
			return "";

		StringBuilder html = new StringBuilder("<ul>");
		for (String error : errores) {
			String enlace = enlacesPorError.getOrDefault(error, "");
			html.append("<li>").append(error).append(" ").append(enlace).append("</li>");
		}
		html.append("</ul>");
		return html.toString();
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_lenguaje_proveedor_check();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;// TODO
	}

	@Override
	public String id() {
		return "lenguaje_proveedor_check";
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>Ya se ha activado esta verificación, y</li>
	 * <li>El trazo contiene claramente el patrón de error de proveedor de lenguaje
	 * de ModLauncher: una línea con "Mod File" y "needs language provider".</li>
	 * </ul>
	 * Es deliberadamente conservador: se prefiere un falso negativo antes que
	 * asociar un trazo que no corresponda realmente a este problema.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains(MOD_FILE) && t.contains(NEEDS_LANGUAGE_PROVIDER);
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

}