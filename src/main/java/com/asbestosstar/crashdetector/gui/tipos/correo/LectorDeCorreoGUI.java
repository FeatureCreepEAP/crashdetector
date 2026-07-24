package com.asbestosstar.crashdetector.gui.tipos.correo;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

import javax.crypto.AEADBadTagException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

/**
 * Funcionalidad común del lector de correo.
 *
 * Las implementaciones concretas se ocupan únicamente de construir y aplicar la
 * apariencia. Esta clase controla la bóveda cifrada, las cuentas, la caché, las
 * operaciones IMAP y el trabajo en segundo plano.
 */
@SuppressWarnings("serial")
public abstract class LectorDeCorreoGUI extends JFrame implements BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;
	protected static final int PUERTO_IMAPS_PREDETERMINADO = 993;
	private static final int MENSAJES_POR_ACTUALIZACION = 100;
	private static final int MENSAJES_CACHE_MAXIMOS = 500;
	private static final int CUERPO_MAXIMO_CARACTERES = 100_000;
	private static final int CUERPO_BUSQUEDA_MAXIMO_CARACTERES = 20_000;

	public static final Map<String, Supplier<LectorDeCorreoGUI>> GUIS = new HashMap<String, Supplier<LectorDeCorreoGUI>>();

	static {
		GUIS.put(LectorDeCorreoNetscape.ID, LectorDeCorreoNetscape::new);
	}

	private final BovedaCorreoSegura boveda = new BovedaCorreoSegura();
	private final ExecutorService ejecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(Runnable runnable) {
			Thread hilo = new Thread(runnable, "crashdetector-mail-reader");
			hilo.setDaemon(true);
			return hilo;
		}
	});

	private BovedaCorreoSegura.DatosBoveda datos;
	private char[] claveBoveda;
	private volatile boolean cerrando;

	@Override
	public final TipoGUI<LectorDeCorreoGUI> tipo() {
		return TipoGUI.LECTOR_CORREO;
	}

	@Override
	public final void init() {
		SwingUtilities.invokeLater(() -> {
			try {
				if (!desbloquearBovedaInteractiva()) {
					dispose();
					return;
				}
				construirApariencia();
				actualizarEstadoDependenciasCorreo();
				publicarDatos();
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
				mostrarErrorCorreo(MonitorDePID.idioma.correoErrorBoveda(), t);
				dispose();
			}
		});
	}

	/** Construye componentes, layout y listeners de la implementación concreta. */
	protected abstract void construirApariencia();

	/** Solicita la clave de una bóveda nueva o existente. */
	protected abstract char[] pedirClaveBoveda(boolean nueva);

	/** Solicita los datos de una cuenta. */
	protected abstract CuentaCorreo pedirCuenta(CuentaCorreo existente);

	/** Confirma la eliminación de una cuenta. */
	protected abstract boolean confirmarEliminarCuenta(CuentaCorreo cuenta);

	/** Actualiza la representación visual de las cuentas. */
	protected abstract void mostrarCuentas(List<CuentaCorreo> cuentas);

	/** Actualiza la representación visual de los mensajes. */
	protected abstract void mostrarMensajes(List<MensajeCorreo> mensajes);

	/** Muestra un estado no modal. */
	protected abstract void mostrarEstadoCorreo(String estado);

	/** Muestra un error modal. */
	protected abstract void mostrarErrorCorreo(String mensaje, Throwable error);

	/** Muestra información modal usando la apariencia concreta. */
	protected abstract void mostrarInfoCorreo(String mensaje);

	/** Refleja en la interfaz si las dependencias opcionales están disponibles. */
	protected abstract void mostrarEstadoDependenciasCorreo(boolean disponibles, String diagnostico);

	/** Activa o desactiva los controles visuales durante una descarga. */
	protected abstract void establecerDescargaDependenciasCorreo(boolean descargando);

	protected final boolean dependenciasCorreoDisponibles() {
		return CorreoImapOpcional.dependenciasDisponibles();
	}

	protected final String diagnosticoDependenciasCorreo() {
		return CorreoImapOpcional.diagnosticoDependencias();
	}

	protected final void actualizarEstadoDependenciasCorreo() {
		mostrarEstadoDependenciasCorreo(dependenciasCorreoDisponibles(), diagnosticoDependenciasCorreo());
	}

	protected final void descargarDependenciasCorreoAsync() {
		establecerDescargaDependenciasCorreo(true);
		Thread hilo = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();
					coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven("org.eclipse.angus",
							"jakarta.mail", "2.0.4"));
					DescargadorDependenciasMaven.descargarDependencias(coordenadas);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							mostrarInfoCorreo(MonitorDePID.idioma.correoDependenciasDescargadasReiniciar());
							actualizarEstadoDependenciasCorreo();
						}
					});
				} catch (final Throwable t) {
					CrashDetectorLogger.logException(t);
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							mostrarErrorCorreo(MonitorDePID.idioma
									.correoErrorDescargandoDependencias(t.getMessage()), t);
						}
					});
				} finally {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							establecerDescargaDependenciasCorreo(false);
							actualizarEstadoDependenciasCorreo();
						}
					});
				}
			}
		}, "Correo-Descargar-Dependencias");
		hilo.setDaemon(true);
		hilo.start();
	}

	private boolean desbloquearBovedaInteractiva() {
		boolean nueva = !boveda.existe();
		char[] solicitada = pedirClaveBoveda(nueva);
		if (solicitada == null || solicitada.length == 0) {
			borrar(solicitada);
			return false;
		}

		try {
			if (nueva) {
				datos = boveda.crear(solicitada);
			} else {
				datos = boveda.abrir(solicitada);
			}
			claveBoveda = Arrays.copyOf(solicitada, solicitada.length);
			return true;
		} catch (AEADBadTagException e) {
			mostrarErrorCorreo(MonitorDePID.idioma.correoClaveBovedaIncorrecta(), e);
			return false;
		} catch (IOException | GeneralSecurityException e) {
			mostrarErrorCorreo(MonitorDePID.idioma.correoErrorBoveda(), e);
			return false;
		} finally {
			borrar(solicitada);
		}
	}

	protected final List<CuentaCorreo> obtenerCuentas() {
		if (datos == null) {
			return Collections.emptyList();
		}
		List<CuentaCorreo> copia = new ArrayList<CuentaCorreo>();
		for (CuentaCorreo cuenta : datos.cuentas()) {
			copia.add(cuenta.copiar());
		}
		return copia;
	}

	protected final List<MensajeCorreo> obtenerMensajesCuenta(String cuentaId) {
		if (datos == null || cuentaId == null) {
			return Collections.emptyList();
		}
		List<MensajeCorreo> resultado = new ArrayList<MensajeCorreo>();
		for (MensajeCorreo mensaje : datos.mensajes()) {
			if (cuentaId.equals(mensaje.cuentaId())) {
				resultado.add(mensaje);
			}
		}
		ordenarMensajes(resultado);
		return resultado;
	}

	protected final List<MensajeCorreo> filtrarMensajes(String cuentaId, String consulta) {
		List<MensajeCorreo> mensajes = obtenerMensajesCuenta(cuentaId);
		String filtro = consulta == null ? "" : consulta.trim().toLowerCase(Locale.ROOT);
		if (filtro.isEmpty()) {
			return mensajes;
		}

		List<MensajeCorreo> resultado = new ArrayList<MensajeCorreo>();
		for (MensajeCorreo mensaje : mensajes) {
			String cuerpo = mensaje.cuerpo();
			if (cuerpo.length() > CUERPO_BUSQUEDA_MAXIMO_CARACTERES) {
				cuerpo = cuerpo.substring(0, CUERPO_BUSQUEDA_MAXIMO_CARACTERES);
			}
			if (contiene(mensaje.remitente(), filtro) || contiene(mensaje.destinatarios(), filtro)
					|| contiene(mensaje.asunto(), filtro) || contiene(cuerpo, filtro)) {
				resultado.add(mensaje);
			}
		}
		return resultado;
	}

	private boolean contiene(String valor, String filtro) {
		return valor != null && valor.toLowerCase(Locale.ROOT).contains(filtro);
	}

	protected final CuentaCorreo buscarCuenta(String cuentaId) {
		if (datos == null || cuentaId == null) {
			return null;
		}
		for (CuentaCorreo cuenta : datos.cuentas()) {
			if (cuentaId.equals(cuenta.id())) {
				return cuenta.copiar();
			}
		}
		return null;
	}

	protected final void agregarCuentaInteractiva() {
		CuentaCorreo nueva = pedirCuenta(null);
		if (nueva == null) {
			return;
		}
		if (!nueva.esValida()) {
			nueva.borrarSecreto();
			mostrarErrorCorreo(MonitorDePID.idioma.correoCuentaInvalida(), null);
			return;
		}

		datos.cuentas().add(nueva);
		guardarYPublicar();
	}

	protected final void editarCuentaInteractiva(String cuentaId) {
		CuentaCorreo existente = buscarCuentaInterna(cuentaId);
		if (existente == null) {
			return;
		}

		CuentaCorreo editada = pedirCuenta(existente.copiar());
		if (editada == null) {
			return;
		}
		if (!editada.esValida()) {
			editada.borrarSecreto();
			mostrarErrorCorreo(MonitorDePID.idioma.correoCuentaInvalida(), null);
			return;
		}

		for (int i = 0; i < datos.cuentas().size(); i++) {
			CuentaCorreo actual = datos.cuentas().get(i);
			if (cuentaId.equals(actual.id())) {
				actual.borrarSecreto();
				datos.cuentas().set(i, editada);
				guardarYPublicar();
				return;
			}
		}
	}

	protected final void eliminarCuentaInteractiva(String cuentaId) {
		CuentaCorreo cuenta = buscarCuentaInterna(cuentaId);
		if (cuenta == null || !confirmarEliminarCuenta(cuenta.copiar())) {
			return;
		}

		datos.cuentas().removeIf(actual -> {
			if (cuentaId.equals(actual.id())) {
				actual.borrarSecreto();
				return true;
			}
			return false;
		});
		datos.mensajes().removeIf(mensaje -> cuentaId.equals(mensaje.cuentaId()));
		guardarYPublicar();
	}

	protected final void actualizarCuentaAsync(String cuentaId) {
		if (!dependenciasCorreoDisponibles()) {
			actualizarEstadoDependenciasCorreo();
			mostrarErrorCorreo(MonitorDePID.idioma.correoEstadoDependenciasNoCargadas(diagnosticoDependenciasCorreo()), null);
			return;
		}
		CuentaCorreo interna = buscarCuentaInterna(cuentaId);
		if (interna == null || cerrando) {
			return;
		}
		CuentaCorreo cuenta = interna.copiar();

		mostrarEstadoCorreo(MonitorDePID.idioma.correoConectando(cuenta.nombre()));
		try {
			ejecutor.submit(() -> {
				try {
					List<MensajeCorreo> descargados = descargarMensajes(cuenta);
					if (cerrando) {
						return;
					}
					SwingUtilities.invokeLater(() -> {
						if (cerrando) {
							return;
						}
						try {
							fusionarMensajes(cuenta.id(), descargados);
							guardarBoveda();
							mostrarMensajes(obtenerMensajesCuenta(cuenta.id()));
							mostrarEstadoCorreo(MonitorDePID.idioma.correoMensajesActualizados(descargados.size()));
						} catch (Throwable t) {
							CrashDetectorLogger.logException(t);
							mostrarErrorCorreo(MonitorDePID.idioma.correoErrorBoveda(), t);
						}
					});
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					if (!cerrando) {
						SwingUtilities.invokeLater(() -> mostrarErrorCorreo(MonitorDePID.idioma.correoErrorConexion(), t));
					}
				} finally {
					cuenta.borrarSecreto();
				}
			});
		} catch (RuntimeException e) {
			cuenta.borrarSecreto();
			throw e;
		}
	}

	protected final void actualizarTodasAsync() {
		if (!dependenciasCorreoDisponibles()) {
			actualizarEstadoDependenciasCorreo();
			mostrarErrorCorreo(MonitorDePID.idioma.correoEstadoDependenciasNoCargadas(diagnosticoDependenciasCorreo()), null);
			return;
		}
		List<CuentaCorreo> cuentas = obtenerCuentas();
		for (CuentaCorreo cuenta : cuentas) {
			actualizarCuentaAsync(cuenta.id());
			cuenta.borrarSecreto();
		}
	}

	private CuentaCorreo buscarCuentaInterna(String cuentaId) {
		if (datos == null || cuentaId == null) {
			return null;
		}
		for (CuentaCorreo cuenta : datos.cuentas()) {
			if (cuentaId.equals(cuenta.id())) {
				return cuenta;
			}
		}
		return null;
	}

	private void guardarYPublicar() {
		try {
			guardarBoveda();
			publicarDatos();
			mostrarEstadoCorreo(MonitorDePID.idioma.correoCuentaGuardada());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			mostrarErrorCorreo(MonitorDePID.idioma.correoErrorBoveda(), t);
		}
	}

	private void publicarDatos() {
		List<CuentaCorreo> cuentas = obtenerCuentas();
		mostrarCuentas(cuentas);
		if (cuentas.isEmpty()) {
			mostrarMensajes(Collections.emptyList());
			mostrarEstadoCorreo(MonitorDePID.idioma.correoSinCuentas());
		} else {
			mostrarMensajes(obtenerMensajesCuenta(cuentas.get(0).id()));
			mostrarEstadoCorreo(MonitorDePID.idioma.correoEstadoListo());
		}
		for (CuentaCorreo cuenta : cuentas) {
			cuenta.borrarSecreto();
		}
	}

	private void guardarBoveda() throws IOException, GeneralSecurityException {
		if (datos == null || claveBoveda == null) {
			throw new IllegalStateException("mail vault is locked");
		}
		boveda.guardar(datos, claveBoveda);
	}

	private List<MensajeCorreo> descargarMensajes(CuentaCorreo cuenta) throws Exception {
		List<MensajeCorreo> resultado = CorreoImapOpcional.descargarMensajes(cuenta, MENSAJES_POR_ACTUALIZACION,
				CUERPO_MAXIMO_CARACTERES);
		ordenarMensajes(resultado);
		return resultado;
	}

	private void fusionarMensajes(String cuentaId, List<MensajeCorreo> nuevos) {
		Map<String, MensajeCorreo> fusionados = new LinkedHashMap<String, MensajeCorreo>();
		for (MensajeCorreo existente : datos.mensajes()) {
			if (!cuentaId.equals(existente.cuentaId())) {
				fusionados.put(existente.claveUnica(), existente);
			}
		}
		for (MensajeCorreo nuevo : nuevos) {
			fusionados.put(nuevo.claveUnica(), nuevo);
		}

		List<MensajeCorreo> lista = new ArrayList<MensajeCorreo>(fusionados.values());
		ordenarMensajes(lista);
		if (lista.size() > MENSAJES_CACHE_MAXIMOS) {
			lista = new ArrayList<MensajeCorreo>(lista.subList(0, MENSAJES_CACHE_MAXIMOS));
		}
		datos.mensajes().clear();
		datos.mensajes().addAll(lista);
	}

	private void ordenarMensajes(List<MensajeCorreo> mensajes) {
		mensajes.sort(Comparator.comparing(MensajeCorreo::fecha).reversed()
				.thenComparing(MensajeCorreo::uid, Comparator.reverseOrder()));
	}

	@Override
	public void dispose() {
		cerrando = true;
		ejecutor.shutdownNow();
		if (datos != null) {
			datos.borrarSecretos();
		}
		borrar(claveBoveda);
		claveBoveda = null;
		super.dispose();
	}

	private static void borrar(char[] chars) {
		if (chars != null) {
			Arrays.fill(chars, '\0');
		}
	}
}
