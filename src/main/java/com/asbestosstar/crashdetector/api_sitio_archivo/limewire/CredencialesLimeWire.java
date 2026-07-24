package com.asbestosstar.crashdetector.api_sitio_archivo.limewire;

import java.util.Arrays;

import com.asbestosstar.crashdetector.api_sitio_archivo.SitioDeArchivoAPI.ServicioNoSoportado;

/**
 * Credenciales efímeras para la sesión web de LimeWire.
 *
 * No se guardan en ConfigString ni se escriben al disco. Se leen de propiedades
 * de la JVM o variables de entorno y se conservan como char[] para poder
 * limpiarlas al cerrar la sesión.
 */
public final class CredencialesLimeWire implements AutoCloseable {

	public static final String PROP_TOKEN = "crashdetector.limewire.accessToken";
	public static final String PROP_CSRF = "crashdetector.limewire.csrfToken";
	public static final String PROP_CLIENTE = "crashdetector.limewire.clientDeviceId";
	public static final String PROP_COOKIE_ADICIONAL = "crashdetector.limewire.cookieAdicional";

	public static final String ENV_TOKEN = "CRASHDETECTOR_LIMEWIRE_ACCESS_TOKEN";
	public static final String ENV_CSRF = "CRASHDETECTOR_LIMEWIRE_CSRF_TOKEN";
	public static final String ENV_CLIENTE = "CRASHDETECTOR_LIMEWIRE_CLIENT_ID";
	public static final String ENV_COOKIE_ADICIONAL = "CRASHDETECTOR_LIMEWIRE_COOKIE_ADICIONAL";

	private final char[] tokenAcceso;
	private final char[] tokenCsrf;
	private final char[] idDispositivoCliente;
	private final char[] cookieAdicional;

	private CredencialesLimeWire(char[] tokenAcceso, char[] tokenCsrf, char[] idDispositivoCliente,
			char[] cookieAdicional) {
		this.tokenAcceso = tokenAcceso;
		this.tokenCsrf = tokenCsrf;
		this.idDispositivoCliente = idDispositivoCliente;
		this.cookieAdicional = cookieAdicional;
	}

	public static CredencialesLimeWire desdeEntorno() throws ServicioNoSoportado {
		String token = obtener(PROP_TOKEN, ENV_TOKEN);
		String csrf = obtener(PROP_CSRF, ENV_CSRF);
		String cliente = obtener(PROP_CLIENTE, ENV_CLIENTE);
		String cookie = obtenerOpcional(PROP_COOKIE_ADICIONAL, ENV_COOKIE_ADICIONAL);

		if (vacio(token) || vacio(csrf) || vacio(cliente)) {
			throw new ServicioNoSoportado(
					"Faltan credenciales de LimeWire. Define " + ENV_TOKEN + ", " + ENV_CSRF + " y " + ENV_CLIENTE
							+ " o sus propiedades equivalentes de la JVM.");
		}

		return new CredencialesLimeWire(token.toCharArray(), csrf.toCharArray(), cliente.toCharArray(),
				cookie == null ? new char[0] : cookie.toCharArray());
	}

	private static String obtener(String propiedad, String entorno) {
		String valor = System.getProperty(propiedad);
		if (vacio(valor)) {
			valor = System.getenv(entorno);
		}
		return valor == null ? null : valor.trim();
	}

	private static String obtenerOpcional(String propiedad, String entorno) {
		String valor = System.getProperty(propiedad);
		if (vacio(valor)) {
			valor = System.getenv(entorno);
		}
		return valor == null ? null : valor.trim();
	}

	private static boolean vacio(String valor) {
		return valor == null || valor.trim().isEmpty();
	}

	public String tokenAcceso() {
		return new String(tokenAcceso);
	}

	public String tokenCsrf() {
		return new String(tokenCsrf);
	}

	public String idDispositivoCliente() {
		return new String(idDispositivoCliente);
	}

	public String cookie() {
		StringBuilder sb = new StringBuilder();
		sb.append("production_access_token=").append(tokenAcceso());
		sb.append("; lmwr_client_id_apilimewirecom=").append(idDispositivoCliente());
		if (cookieAdicional.length > 0) {
			sb.append("; ").append(new String(cookieAdicional));
		}
		return sb.toString();
	}

	@Override
	public void close() {
		Arrays.fill(tokenAcceso, '\0');
		Arrays.fill(tokenCsrf, '\0');
		Arrays.fill(idDispositivoCliente, '\0');
		Arrays.fill(cookieAdicional, '\0');
	}
}
