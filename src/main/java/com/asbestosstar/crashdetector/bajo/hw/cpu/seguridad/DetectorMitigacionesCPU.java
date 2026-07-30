package com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU.EstadoCPU;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU.EstadoComponente;
import com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad.ResultadoVulnerabilidadCPU.EstadoMitigacion;

/**
 * Detector portátil de las mitigaciones originales de Meltdown y Spectre.
 *
 * Alcance:
 *
 * - Meltdown: CVE-2017-5754. - Spectre V1: CVE-2017-5753. - Spectre V2:
 * CVE-2017-5715.
 *
 * Principio de diseño:
 *
 * El estado que informa el kernel o el sistema operativo tiene prioridad sobre
 * una lista de nombres de CPU. Esto es importante porque:
 *
 * - Un CPU afectado puede estar correctamente mitigado. - Un CPU nuevo puede
 * tener protección directa en hardware. - La revisión mínima de microcódigo
 * cambia según familia, modelo y stepping. - En una máquina virtual, el
 * invitado puede no ver el firmware real del host.
 *
 * Cuando el sistema operativo no proporciona una interfaz fiable, la clase
 * devuelve DESCONOCIDO o PARCIAL en vez de inventar un resultado seguro.
 */
public final class DetectorMitigacionesCPU {

	private static final int TIEMPO_COMANDO_SEGUNDOS = 7;
	private static final int LIMITE_SALIDA = 64 * 1024;

	private static final String URL_LINUX = "https://www.kernel.org/doc/html/latest/admin-guide/hw-vuln/spectre.html";
	private static final String URL_WINDOWS = "https://support.microsoft.com/help/4074629";
	private static final String URL_APPLE = "https://support.apple.com/101886";
	private static final String URL_FREEBSD = "https://www.freebsd.org/security/advisories/FreeBSD-SA-18:03.speculative_execution.asc";
	private static final String URL_NETBSD = "https://wiki.netbsd.org/security/meltdown_spectre/";
	private static final String URL_OPENBSD = "https://www.openbsd.org/64.html";
	private static final String URL_SOLARIS = "https://docs.oracle.com/en/operating-systems/solaris/oracle-solaris/11.4/secure-sys-dev/protecting-against-malware-with-security-extensions.html";
	private static final String URL_AIX = "https://www.ibm.com/support/pages/checking-aix-protection-against-spectre-and-meltdown-settings";
	private static final String URL_XINUOS = "https://www.xinuos.com/support/";
	private static final String URL_GENERICO = "https://nvd.nist.gov/vuln/detail/CVE-2017-5715";

	private DetectorMitigacionesCPU() {
	}

	public static ResultadoVulnerabilidadCPU evaluarMeltdown(String cpuDelRegistro) {
		return evaluar(TipoVulnerabilidad.MELTDOWN, cpuDelRegistro);
	}

	public static ResultadoVulnerabilidadCPU evaluarSpectre(String cpuDelRegistro) {
		return evaluar(TipoVulnerabilidad.SPECTRE, cpuDelRegistro);
	}

	/**
	 * Reporte pensado para diagnóstico manual y pruebas fuera del analizador.
	 */
	public static String obtenerReporteLocal() {
		InstantaneaCPU local = capturarInstantaneaLocal();
		ResultadoVulnerabilidadCPU meltdown = evaluar(TipoVulnerabilidad.MELTDOWN, local.nombreCPU);
		ResultadoVulnerabilidadCPU spectre = evaluar(TipoVulnerabilidad.SPECTRE, local.nombreCPU);
		return meltdown.construirResumenTexto() + System.lineSeparator() + spectre.construirResumenTexto();
	}

	public static void main(String[] args) {
		System.out.println(obtenerReporteLocal());
	}

	private static ResultadoVulnerabilidadCPU evaluar(TipoVulnerabilidad tipo, String cpuDelRegistro) {
		InstantaneaCPU local = capturarInstantaneaLocal();
		String cpuObjetivo = textoNoVacio(cpuDelRegistro, local.nombreCPU, local.arquitectura);
		boolean correspondeLocal = cpuDelRegistro == null || cpuDelRegistro.trim().isEmpty()
				|| pareceMismoCPU(cpuDelRegistro, local.nombreCPU);

		// Cuando se analiza un registro de otra computadora, no se debe mezclar el
		// fabricante ni la arquitectura del equipo local con el nombre remoto.
		String fabricanteObjetivo = correspondeLocal ? local.fabricante : inferirFabricanteDesdeNombre(cpuObjetivo);
		String arquitecturaObjetivo = correspondeLocal ? local.arquitectura
				: inferirArquitecturaDesdeNombre(cpuObjetivo);

		EstadoCPU clasificacionBase = tipo == TipoVulnerabilidad.MELTDOWN
				? clasificarCPUParaMeltdown(cpuObjetivo, fabricanteObjetivo, arquitecturaObjetivo)
				: clasificarCPUParaSpectre(cpuObjetivo, fabricanteObjetivo, arquitecturaObjetivo);

		EvaluacionOS evaluacion;
		if (correspondeLocal) {
			evaluacion = evaluarSistemaLocal(tipo, local, clasificacionBase);
		} else {
			evaluacion = EvaluacionOS.desconocida(clasificacionBase,
					texto(ClavesTextoSeguridadCPU.REGISTRO_CPU_DISTINTO_EVIDENCIA),
					texto(ClavesTextoSeguridadCPU.REGISTRO_CPU_DISTINTO_ACCION), enlaceParaSO(local.familiaSO));
		}

		EstadoCPU estadoCPU = evaluacion.estadoCPU == EstadoCPU.DESCONOCIDO ? clasificacionBase : evaluacion.estadoCPU;

		String sistemaObjetivo = correspondeLocal ? local.nombreSOCompleto()
				: texto(ClavesTextoSeguridadCPU.SO_NO_DETERMINADO_REGISTRO);
		String revisionObjetivo = correspondeLocal ? local.microcodigoFirmware : "";

		return new ResultadoVulnerabilidadCPU(tipo.nombreLocalizado(), cpuObjetivo, fabricanteObjetivo,
				arquitecturaObjetivo, sistemaObjetivo, estadoCPU, evaluacion.estadoMitigacion, evaluacion.parcheSO,
				evaluacion.parcheFirmware, revisionObjetivo, evaluacion.evidencia, evaluacion.accionRecomendada,
				evaluacion.enlaceOficial, correspondeLocal);
	}

	private static EvaluacionOS evaluarSistemaLocal(TipoVulnerabilidad tipo, InstantaneaCPU local,
			EstadoCPU clasificacionBase) {
		switch (local.familiaSO) {
		case LINUX:
			return evaluarLinux(tipo, clasificacionBase);
		case WINDOWS:
			return evaluarWindows(tipo, clasificacionBase);
		case MACOS:
			return evaluarMacOS(tipo, local, clasificacionBase);
		case FREEBSD:
			return evaluarFreeBSD(tipo, clasificacionBase);
		case NETBSD:
			return evaluarNetBSD(tipo, clasificacionBase);
		case OPENBSD:
			return evaluarOpenBSD(tipo, local, clasificacionBase);
		case SOLARIS:
			return evaluarSolaris(tipo, clasificacionBase);
		case AIX:
			return evaluarAIX(tipo, clasificacionBase, local.microcodigoFirmware);
		case UNIXWARE:
		case OPENSERVER:
			return evaluarXinuos(tipo, local, clasificacionBase);
		case DRAGONFLY:
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.DRAGONFLY_SIN_INTERFAZ),
					texto(ClavesTextoSeguridadCPU.DRAGONFLY_ACCION), URL_GENERICO);
		case ZOS:
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.ZOS_SIN_INTERFAZ),
					texto(ClavesTextoSeguridadCPU.ZOS_ACCION), "https://www.ibm.com/support/pages/node/1114069");
		case IBMI:
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.IBMI_SIN_INTERFAZ),
					texto(ClavesTextoSeguridadCPU.IBMI_ACCION), URL_AIX);
		case HPUX:
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.HPUX_SIN_INTERFAZ),
					texto(ClavesTextoSeguridadCPU.HPUX_ACCION), URL_GENERICO);
		default:
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.SO_SIN_INTERFAZ),
					texto(ClavesTextoSeguridadCPU.SO_SIN_INTERFAZ_ACCION), URL_GENERICO);
		}
	}

	// =====================================================================
	// LINUX Y SISTEMAS QUE EXPONEN EL ABI DE VULNERABILIDADES DE LINUX
	// =====================================================================

	private static EvaluacionOS evaluarLinux(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase) {
		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			String estado = leerArchivoSeguro("/sys/devices/system/cpu/vulnerabilities/meltdown");
			if (estado.isEmpty()) {
				return EvaluacionOS.desconocida(clasificacionBase,
						texto(ClavesTextoSeguridadCPU.LINUX_MELTDOWN_SIN_SYSFS),
						texto(ClavesTextoSeguridadCPU.LINUX_MELTDOWN_SIN_SYSFS_ACCION), URL_LINUX);
			}
			return interpretarEstadoLinuxSimple(estado, clasificacionBase, "Meltdown", URL_LINUX);
		}

		String v1 = leerArchivoSeguro("/sys/devices/system/cpu/vulnerabilities/spectre_v1");
		String v2 = leerArchivoSeguro("/sys/devices/system/cpu/vulnerabilities/spectre_v2");

		if (v1.isEmpty() && v2.isEmpty()) {
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.LINUX_SPECTRE_SIN_SYSFS),
					texto(ClavesTextoSeguridadCPU.LINUX_SPECTRE_SIN_SYSFS_ACCION), URL_LINUX);
		}

		EstadoLinux e1 = interpretarTextoLinux(v1);
		EstadoLinux e2 = interpretarTextoLinux(v2);
		EstadoCPU cpu = combinarEstadoCPU(clasificacionBase, e1, e2);
		EstadoMitigacion general = combinarMitigaciones(e1, e2);
		EstadoComponente parcheSO = estadoComponenteSO(general);
		EstadoComponente firmware = detectarFirmwareEnTextoSpectre(v2);

		String evidencia = "spectre_v1=" + textoNoVacio(v1, texto(ClavesTextoSeguridadCPU.NO_DISPONIBLE))
				+ "; spectre_v2=" + textoNoVacio(v2, texto(ClavesTextoSeguridadCPU.NO_DISPONIBLE));
		String accion = general == EstadoMitigacion.MITIGADO || general == EstadoMitigacion.NO_APLICA
				? texto(ClavesTextoSeguridadCPU.LINUX_SPECTRE_MITIGADO_ACCION)
				: texto(ClavesTextoSeguridadCPU.LINUX_SPECTRE_ACTUALIZAR_ACCION);

		return new EvaluacionOS(cpu, general, parcheSO, firmware, evidencia, accion, URL_LINUX);
	}

	private static EvaluacionOS interpretarEstadoLinuxSimple(String texto, EstadoCPU base, String nombre,
			String enlace) {
		EstadoLinux estado = interpretarTextoLinux(texto);
		if (estado == EstadoLinux.NO_AFECTADO) {
			return new EvaluacionOS(EstadoCPU.NO_AFECTADO, EstadoMitigacion.NO_APLICA, EstadoComponente.NO_NECESARIO,
					EstadoComponente.NO_NECESARIO, nombre + "=" + texto,
					texto(ClavesTextoSeguridadCPU.LINUX_CPU_NO_AFECTADO), enlace);
		}
		if (estado == EstadoLinux.MITIGADO) {
			return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
					EstadoComponente.NO_NECESARIO, nombre + "=" + texto,
					texto(ClavesTextoSeguridadCPU.LINUX_MITIGACION_ACTIVA), enlace);
		}
		if (estado == EstadoLinux.VULNERABLE) {
			return new EvaluacionOS(base, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					EstadoComponente.DESCONOCIDO, nombre + "=" + texto,
					texto(ClavesTextoSeguridadCPU.LINUX_KERNEL_VULNERABLE_ACCION), enlace);
		}
		return EvaluacionOS.desconocida(base, nombre + "=" + texto,
				texto(ClavesTextoSeguridadCPU.LINUX_REVISAR_SYSFS_ACCION), enlace);
	}

	private static EstadoLinux interpretarTextoLinux(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return EstadoLinux.DESCONOCIDO;
		}
		String lower = texto.trim().toLowerCase(Locale.ROOT);
		if (lower.contains("not affected") || lower.contains("no afectado")) {
			return EstadoLinux.NO_AFECTADO;
		}
		if (lower.contains("vulnerable") || lower.contains("mitigation: none")
				|| lower.contains("mitigation: disabled")) {
			return EstadoLinux.VULNERABLE;
		}
		if (lower.contains("mitigation:") || lower.contains("mitigated") || lower.contains("retpoline")
				|| lower.contains("ibrs") || lower.contains("ibpb") || lower.contains("kpti")) {
			return EstadoLinux.MITIGADO;
		}
		return EstadoLinux.DESCONOCIDO;
	}

	private static EstadoCPU combinarEstadoCPU(EstadoCPU base, EstadoLinux... estados) {
		boolean hayAfectado = false;
		boolean todosNoAfectados = true;
		boolean hayDato = false;
		for (EstadoLinux estado : estados) {
			if (estado == null || estado == EstadoLinux.DESCONOCIDO) {
				todosNoAfectados = false;
				continue;
			}
			hayDato = true;
			if (estado == EstadoLinux.MITIGADO || estado == EstadoLinux.VULNERABLE) {
				hayAfectado = true;
				todosNoAfectados = false;
			} else if (estado != EstadoLinux.NO_AFECTADO) {
				todosNoAfectados = false;
			}
		}
		if (hayAfectado) {
			return EstadoCPU.AFECTADO;
		}
		if (hayDato && todosNoAfectados) {
			return EstadoCPU.NO_AFECTADO;
		}
		return base;
	}

	private static EstadoMitigacion combinarMitigaciones(EstadoLinux... estados) {
		boolean hayVulnerable = false;
		boolean hayDesconocido = false;
		boolean hayMitigadoOAjenos = false;
		for (EstadoLinux estado : estados) {
			if (estado == EstadoLinux.VULNERABLE) {
				hayVulnerable = true;
			} else if (estado == EstadoLinux.DESCONOCIDO) {
				hayDesconocido = true;
			} else {
				hayMitigadoOAjenos = true;
			}
		}
		if (hayVulnerable) {
			return EstadoMitigacion.VULNERABLE;
		}
		if (hayDesconocido && hayMitigadoOAjenos) {
			return EstadoMitigacion.PARCIAL;
		}
		if (hayDesconocido) {
			return EstadoMitigacion.DESCONOCIDO;
		}
		return EstadoMitigacion.MITIGADO;
	}

	private static EstadoComponente detectarFirmwareEnTextoSpectre(String texto) {
		if (texto == null || texto.trim().isEmpty()) {
			return EstadoComponente.DESCONOCIDO;
		}
		String lower = texto.toLowerCase(Locale.ROOT);
		if (lower.contains("no microcode") || lower.contains("microcode missing")
				|| lower.contains("no hardware support")) {
			return EstadoComponente.AUSENTE;
		}
		if (lower.contains("ibrs") || lower.contains("ibpb") || lower.contains("stibp")
				|| lower.contains("enhanced ibrs") || lower.contains("eibrs") || lower.contains("firmware")) {
			return EstadoComponente.PRESENTE;
		}
		if (lower.contains("retpoline") || lower.contains("lfence")) {
			return EstadoComponente.NO_NECESARIO;
		}
		return EstadoComponente.DESCONOCIDO;
	}

	// =====================================================================
	// WINDOWS
	// =====================================================================

	private static EvaluacionOS evaluarWindows(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase) {
		Map<String, String> valores = consultarSpeculationControlWindows();
		if (valores.isEmpty() || "true".equalsIgnoreCase(valores.get("ModuleMissing"))) {
			return EvaluacionOS.desconocida(clasificacionBase,
					texto(ClavesTextoSeguridadCPU.WINDOWS_SPECULATIONCONTROL_AUSENTE),
					texto(ClavesTextoSeguridadCPU.WINDOWS_SPECULATIONCONTROL_ACCION), URL_WINDOWS);
		}

		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			Boolean rdclProtegido = valorBooleano(valores, "RdclHardwareProtected");
			Boolean requiereKva = valorBooleano(valores, "KVAShadowRequired");
			Boolean soporte = valorBooleano(valores, "KVAShadowWindowsSupportPresent");
			Boolean habilitado = valorBooleano(valores, "KVAShadowWindowsSupportEnabled");

			if (Boolean.TRUE.equals(rdclProtegido) || Boolean.FALSE.equals(requiereKva)) {
				return new EvaluacionOS(EstadoCPU.NO_AFECTADO, EstadoMitigacion.NO_APLICA,
						EstadoComponente.NO_NECESARIO, EstadoComponente.PRESENTE,
						resumenMapa(valores, "RdclHardwareProtected", "KVAShadowRequired"),
						texto(ClavesTextoSeguridadCPU.WINDOWS_MELTDOWN_HARDWARE), URL_WINDOWS);
			}
			if (Boolean.TRUE.equals(requiereKva)) {
				if (Boolean.TRUE.equals(soporte) && Boolean.TRUE.equals(habilitado)) {
					return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
							EstadoComponente.NO_NECESARIO,
							resumenMapa(valores, "KVAShadowRequired", "KVAShadowWindowsSupportPresent",
									"KVAShadowWindowsSupportEnabled"),
							texto(ClavesTextoSeguridadCPU.WINDOWS_KVA_ACTIVO), URL_WINDOWS);
				}
				return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.VULNERABLE,
						Boolean.FALSE.equals(soporte) || Boolean.FALSE.equals(habilitado) ? EstadoComponente.AUSENTE
								: EstadoComponente.DESCONOCIDO,
						EstadoComponente.NO_NECESARIO,
						resumenMapa(valores, "KVAShadowRequired", "KVAShadowWindowsSupportPresent",
								"KVAShadowWindowsSupportEnabled"),
						texto(ClavesTextoSeguridadCPU.WINDOWS_KVA_ACTUALIZAR), URL_WINDOWS);
			}
			return EvaluacionOS.desconocida(clasificacionBase, resumenMapa(valores),
					texto(ClavesTextoSeguridadCPU.WINDOWS_REVISAR_MELTDOWN), URL_WINDOWS);
		}

		Boolean hw = valorBooleano(valores, "BTIHardwarePresent");
		Boolean soporte = valorBooleano(valores, "BTIWindowsSupportPresent");
		Boolean habilitado = valorBooleano(valores, "BTIWindowsSupportEnabled");
		Boolean politica = valorBooleano(valores, "BTIDisabledBySystemPolicy");
		Boolean sinHardware = valorBooleano(valores, "BTIDisabledByNoHardwareSupport");
		Boolean retpoline = valorBooleano(valores, "BTIKernelRetpolineEnabled");

		boolean soporteFirmwareOS = Boolean.TRUE.equals(hw) || Boolean.TRUE.equals(retpoline);
		boolean proteccionActiva = Boolean.TRUE.equals(soporte) && Boolean.TRUE.equals(habilitado)
				&& !Boolean.TRUE.equals(politica) && !Boolean.TRUE.equals(sinHardware) && soporteFirmwareOS;

		if (proteccionActiva) {
			return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
					Boolean.TRUE.equals(hw) ? EstadoComponente.PRESENTE : EstadoComponente.NO_NECESARIO,
					resumenMapa(valores, "BTIHardwarePresent", "BTIWindowsSupportPresent", "BTIWindowsSupportEnabled",
							"BTIKernelRetpolineEnabled", "BTIDisabledBySystemPolicy", "BTIDisabledByNoHardwareSupport"),
					texto(ClavesTextoSeguridadCPU.WINDOWS_BTI_ACTIVO), URL_WINDOWS);
		}

		if (Boolean.FALSE.equals(soporte) || Boolean.FALSE.equals(habilitado) || Boolean.TRUE.equals(politica)
				|| Boolean.TRUE.equals(sinHardware)) {
			EstadoComponente firmware = Boolean.TRUE.equals(sinHardware) || Boolean.FALSE.equals(hw)
					? EstadoComponente.AUSENTE
					: EstadoComponente.DESCONOCIDO;
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE,
					Boolean.FALSE.equals(soporte) || Boolean.FALSE.equals(habilitado) ? EstadoComponente.AUSENTE
							: EstadoComponente.PRESENTE,
					firmware, resumenMapa(valores), texto(ClavesTextoSeguridadCPU.WINDOWS_BTI_ACTUALIZAR), URL_WINDOWS);
		}

		return EvaluacionOS.desconocida(clasificacionBase, resumenMapa(valores),
				texto(ClavesTextoSeguridadCPU.WINDOWS_REVISAR_SPECTRE), URL_WINDOWS);
	}

	private static Map<String, String> consultarSpeculationControlWindows() {
		String script = "$ErrorActionPreference='SilentlyContinue';"
				+ "$m=Get-Module -ListAvailable SpeculationControl | Select-Object -First 1;"
				+ "if(-not $m){'ModuleMissing=true';exit};" + "Import-Module SpeculationControl -ErrorAction Stop;"
				+ "$s=Get-SpeculationControlSettings;"
				+ "$n=@('BTIHardwarePresent','BTIWindowsSupportPresent','BTIWindowsSupportEnabled',"
				+ "'BTIDisabledBySystemPolicy','BTIDisabledByNoHardwareSupport','BTIKernelRetpolineEnabled',"
				+ "'RdclHardwareProtected','KVAShadowRequired','KVAShadowWindowsSupportPresent',"
				+ "'KVAShadowWindowsSupportEnabled');" + "foreach($x in $n){$v=$s.$x;if($null-ne $v){$x+'='+$v}}";
		String salida = ejecutarComando("powershell.exe", "-NoLogo", "-NoProfile", "-NonInteractive",
				"-ExecutionPolicy", "Bypass", "-Command", script);
		return parsearClaves(salida);
	}

	// =====================================================================
	// macOS
	// =====================================================================

	private static EvaluacionOS evaluarMacOS(TipoVulnerabilidad tipo, InstantaneaCPU local,
			EstadoCPU clasificacionBase) {
		Version version = Version.parse(local.versionSO);
		if (version == null) {
			return EvaluacionOS.desconocida(clasificacionBase,
					texto(ClavesTextoSeguridadCPU.MACOS_VERSION_NO_INTERPRETADA),
					texto(ClavesTextoSeguridadCPU.MACOS_ACTUALIZAR), URL_APPLE);
		}

		// macOS 11 y posteriores contienen las mitigaciones originales dentro del
		// sistema mantenido; no se intenta inferir revisiones privadas de firmware.
		if (version.mayor >= 11) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
					EstadoComponente.DESCONOCIDO, "macOS " + version,
					texto(ClavesTextoSeguridadCPU.MACOS_POSTERIOR_PARCHES_ORIGINALES), URL_APPLE);
		}

		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			if (version.alMenos(10, 13, 2)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.DESCONOCIDO, "macOS " + version,
						texto(ClavesTextoSeguridadCPU.MACOS_MELTDOWN_10132), URL_APPLE);
			}
			if ((version.es(10, 12, 6) || version.es(10, 11, 6)) && historialAppleContiene("2018-001")) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.DESCONOCIDO, texto(ClavesTextoSeguridadCPU.MACOS_SECURITY_UPDATE_EVIDENCIA),
						texto(ClavesTextoSeguridadCPU.MACOS_SECURITY_UPDATE_INSTALADO), URL_APPLE);
			}
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					EstadoComponente.DESCONOCIDO, "macOS " + version,
					texto(ClavesTextoSeguridadCPU.MACOS_MELTDOWN_ANTIGUO_ACCION), URL_APPLE);
		}

		if (version.alMenos(10, 13, 3)) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
					EstadoComponente.DESCONOCIDO, "macOS " + version,
					texto(ClavesTextoSeguridadCPU.MACOS_SPECTRE_SUPLEMENTAL), URL_APPLE);
		}
		if (version.es(10, 13, 2)) {
			String safari = ejecutarComando("/usr/bin/defaults", "read", "/Applications/Safari.app/Contents/Info",
					"CFBundleShortVersionString");
			Version safariVersion = Version.parse(safari);
			if (safariVersion != null && safariVersion.alMenos(11, 0, 2)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
						EstadoComponente.DESCONOCIDO, "macOS 10.13.2; Safari " + safariVersion,
						texto(ClavesTextoSeguridadCPU.MACOS_SPECTRE_SAFARI_PARCIAL), URL_APPLE);
			}
		}
		if ((version.es(10, 12, 6) || version.es(10, 11, 6))) {
			String safari = ejecutarComando("/usr/bin/defaults", "read", "/Applications/Safari.app/Contents/Info",
					"CFBundleShortVersionString");
			Version safariVersion = Version.parse(safari);
			if (safariVersion != null && safariVersion.alMenos(11, 0, 2)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
						EstadoComponente.DESCONOCIDO, "Safari " + safariVersion,
						texto(ClavesTextoSeguridadCPU.MACOS_SPECTRE_SAFARI_1102), URL_APPLE);
			}
		}
		return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
				EstadoComponente.DESCONOCIDO, "macOS " + version,
				texto(ClavesTextoSeguridadCPU.MACOS_SPECTRE_ACTUALIZAR), URL_APPLE);
	}

	private static boolean historialAppleContiene(String texto) {
		String salida = ejecutarComando("/usr/sbin/softwareupdate", "--history");
		return salida.toLowerCase(Locale.ROOT).contains(texto.toLowerCase(Locale.ROOT));
	}

	// =====================================================================
	// FREEBSD, NETBSD Y OPENBSD
	// =====================================================================

	private static EvaluacionOS evaluarFreeBSD(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase) {
		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			String pti = sysctl("vm.pmap.pti");
			if (esUnoOVerdadero(pti)) {
				return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.NO_NECESARIO, "vm.pmap.pti=" + pti, "Page Table Isolation está activo.",
						URL_FREEBSD);
			}
			if (esCeroOFalso(pti)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
						EstadoComponente.NO_NECESARIO, "vm.pmap.pti=" + pti,
						texto(ClavesTextoSeguridadCPU.FREEBSD_PTI_HABILITAR), URL_FREEBSD);
			}
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.FREEBSD_PTI_NO_LEIDO),
					texto(ClavesTextoSeguridadCPU.FREEBSD_PTI_REVISAR), URL_FREEBSD);
		}

		String ibrsActivo = primerValorNoVacio(sysctl("hw.ibrs_active"), sysctl("machdep.mitigations.ibrs.enabled"));
		String ibrsDeshabilitado = sysctl("hw.ibrs_disable");
		boolean v2Activa = esUnoOVerdadero(ibrsActivo)
				|| (!ibrsActivo.isEmpty() && ibrsActivo.toLowerCase(Locale.ROOT).contains("enabled"));
		boolean deshabilitada = esUnoOVerdadero(ibrsDeshabilitado);

		if (v2Activa && !deshabilitada) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
					EstadoComponente.PRESENTE,
					"hw.ibrs_active=" + ibrsActivo + "; hw.ibrs_disable=" + ibrsDeshabilitado,
					texto(ClavesTextoSeguridadCPU.FREEBSD_SPECTRE_MITIGADO), URL_FREEBSD);
		}
		if (deshabilitada) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					EstadoComponente.DESCONOCIDO,
					"hw.ibrs_active=" + ibrsActivo + "; hw.ibrs_disable=" + ibrsDeshabilitado,
					texto(ClavesTextoSeguridadCPU.FREEBSD_IBRS_DESHABILITADO), URL_FREEBSD);
		}
		if (esCeroOFalso(ibrsActivo)) {
			return EvaluacionOS.desconocida(clasificacionBase,
					"hw.ibrs_active=" + ibrsActivo + "; hw.ibrs_disable=" + ibrsDeshabilitado,
					texto(ClavesTextoSeguridadCPU.FREEBSD_IBRS_INACTIVO), URL_FREEBSD);
		}
		return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.FREEBSD_IBRS_NO_ENCONTRADO),
				texto(ClavesTextoSeguridadCPU.FREEBSD_IBRS_REVISAR), URL_FREEBSD);
	}

	private static EvaluacionOS evaluarNetBSD(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase) {
		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			String svs = sysctl("machdep.svs.enabled");
			if (esUnoOVerdadero(svs)) {
				return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.NO_NECESARIO, "machdep.svs.enabled=" + svs, "SVS está activo.", URL_NETBSD);
			}
			if (esCeroOFalso(svs)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
						EstadoComponente.NO_NECESARIO, "machdep.svs.enabled=" + svs,
						texto(ClavesTextoSeguridadCPU.NETBSD_SVS_HABILITAR), URL_NETBSD);
			}
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.NETBSD_SVS_NO_LEIDO),
					texto(ClavesTextoSeguridadCPU.NETBSD_SVS_REVISAR), URL_NETBSD);
		}

		String hw = sysctl("machdep.spectre_v2.hwmitigated");
		String sw = sysctl("machdep.spectre_v2.swmitigated");
		String metodo = sysctl("machdep.spectre_v2.method");
		boolean v2 = esUnoOVerdadero(hw) || esUnoOVerdadero(sw);
		if (v2) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
					esUnoOVerdadero(hw) ? EstadoComponente.PRESENTE : EstadoComponente.NO_NECESARIO,
					"hwmitigated=" + hw + "; swmitigated=" + sw + "; method=" + metodo,
					texto(ClavesTextoSeguridadCPU.NETBSD_SPECTRE_MITIGADO), URL_NETBSD);
		}
		if (esCeroOFalso(hw) && esCeroOFalso(sw)) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					EstadoComponente.AUSENTE, "hwmitigated=" + hw + "; swmitigated=" + sw + "; method=" + metodo,
					texto(ClavesTextoSeguridadCPU.NETBSD_SPECTRE_HABILITAR), URL_NETBSD);
		}
		return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.NETBSD_SPECTRE_NO_LEIDO),
				texto(ClavesTextoSeguridadCPU.NETBSD_SPECTRE_REVISAR), URL_NETBSD);
	}

	private static EvaluacionOS evaluarOpenBSD(TipoVulnerabilidad tipo, InstantaneaCPU local,
			EstadoCPU clasificacionBase) {
		Version version = Version.parse(local.versionSO);
		if (version == null) {
			String uname = ejecutarComando("uname", "-r");
			version = Version.parse(uname);
		}
		if (version == null) {
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.OPENBSD_VERSION_NO_LEIDA),
					texto(ClavesTextoSeguridadCPU.OPENBSD_ACTUALIZAR), URL_OPENBSD);
		}

		if (version.alMenos(6, 4, 0)) {
			if (tipo == TipoVulnerabilidad.MELTDOWN) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.NO_NECESARIO, "OpenBSD " + version,
						texto(ClavesTextoSeguridadCPU.OPENBSD_MELTDOWN_INCLUIDO), URL_OPENBSD);
			}
			String smt = sysctl("hw.smt");
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
					EstadoComponente.DESCONOCIDO, "OpenBSD " + version + "; hw.smt=" + smt,
					texto(ClavesTextoSeguridadCPU.OPENBSD_SPECTRE_PARCIAL), URL_OPENBSD);
		}

		return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
				EstadoComponente.DESCONOCIDO, "OpenBSD " + version,
				texto(ClavesTextoSeguridadCPU.OPENBSD_ANTIGUO_ACCION), URL_OPENBSD);
	}

	// =====================================================================
	// SOLARIS / ILLUMOS
	// =====================================================================

	private static EvaluacionOS evaluarSolaris(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase) {
		String salida = ejecutarComando("sxadm", "status", "-po", "extension,status,configuration");
		if (salida.isEmpty()) {
			salida = ejecutarComando("sxadm", "status");
		}
		if (salida.isEmpty()) {
			return EvaluacionOS.desconocida(clasificacionBase, texto(ClavesTextoSeguridadCPU.SOLARIS_SXADM_AUSENTE),
					texto(ClavesTextoSeguridadCPU.SOLARIS_SXADM_ACCION), URL_SOLARIS);
		}

		Map<String, String> extensiones = parsearSxadm(salida);
		if (tipo == TipoVulnerabilidad.MELTDOWN) {
			String rdclNo = extensiones.get("rdcl_no");
			String kpti = extensiones.get("kpti");
			if (estaHabilitado(rdclNo)) {
				return new EvaluacionOS(EstadoCPU.NO_AFECTADO, EstadoMitigacion.NO_APLICA,
						EstadoComponente.NO_NECESARIO, EstadoComponente.PRESENTE,
						"rdcl_no=" + rdclNo + "; kpti=" + kpti, texto(ClavesTextoSeguridadCPU.SOLARIS_RDCL_HARDWARE),
						URL_SOLARIS);
			}
			if (estaHabilitado(kpti)) {
				return new EvaluacionOS(EstadoCPU.AFECTADO, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
						EstadoComponente.NO_NECESARIO, "rdcl_no=" + rdclNo + "; kpti=" + kpti,
						texto(ClavesTextoSeguridadCPU.SOLARIS_KPTI_ACTIVO), URL_SOLARIS);
			}
			if (estaDeshabilitado(kpti)) {
				return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
						EstadoComponente.DESCONOCIDO, "rdcl_no=" + rdclNo + "; kpti=" + kpti,
						texto(ClavesTextoSeguridadCPU.SOLARIS_KPTI_ACTIVAR), URL_SOLARIS);
			}
			return EvaluacionOS.desconocida(clasificacionBase, salida,
					texto(ClavesTextoSeguridadCPU.SOLARIS_MELTDOWN_REVISAR), URL_SOLARIS);
		}

		String hwBti = extensiones.get("hw_bti");
		String ibrs = extensiones.get("ibrs");
		String ibpb = extensiones.get("ibpb");
		String rsbs = extensiones.get("rsbs");
		boolean activa = estaHabilitado(hwBti) || estaHabilitado(ibrs) || estaHabilitado(ibpb);
		if (activa) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.MITIGADO, EstadoComponente.PRESENTE,
					EstadoComponente.PRESENTE,
					"hw_bti=" + hwBti + "; ibrs=" + ibrs + "; ibpb=" + ibpb + "; rsbs=" + rsbs,
					texto(ClavesTextoSeguridadCPU.SOLARIS_V2_ACTIVO), URL_SOLARIS);
		}
		if (estaDeshabilitado(hwBti) || estaDeshabilitado(ibrs) || estaDeshabilitado(ibpb)) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					EstadoComponente.PRESENTE, "hw_bti=" + hwBti + "; ibrs=" + ibrs + "; ibpb=" + ibpb,
					texto(ClavesTextoSeguridadCPU.SOLARIS_EXTENSION_ACTIVAR), URL_SOLARIS);
		}
		return EvaluacionOS.desconocida(clasificacionBase, salida, texto(ClavesTextoSeguridadCPU.SOLARIS_V2_REVISAR),
				URL_SOLARIS);
	}

	private static Map<String, String> parsearSxadm(String salida) {
		Map<String, String> mapa = new LinkedHashMap<String, String>();
		if (salida == null) {
			return mapa;
		}
		String[] lineas = salida.split("\\R");
		for (String linea : lineas) {
			String limpia = linea.trim().toLowerCase(Locale.ROOT);
			if (limpia.isEmpty() || limpia.startsWith("extension")) {
				continue;
			}
			String nombre;
			String valor;
			int dosPuntos = limpia.indexOf(':');
			if (dosPuntos > 0) {
				nombre = limpia.substring(0, dosPuntos).trim();
				valor = limpia.substring(dosPuntos + 1).trim();
			} else {
				String[] partes = limpia.split("\\s+", 2);
				if (partes.length < 2) {
					continue;
				}
				nombre = partes[0];
				valor = partes[1];
			}
			mapa.put(nombre, valor);
		}
		return mapa;
	}

	// =====================================================================
	// AIX / POWER
	// =====================================================================

	private static EvaluacionOS evaluarAIX(TipoVulnerabilidad tipo, EstadoCPU clasificacionBase, String firmware) {
		String oslevel = ejecutarComando("oslevel", "-s");
		boolean nivelModerno = esAixDe2019OMasReciente(oslevel);
		boolean firmwareVisible = esRevisionFirmwareVisible(firmware);

		if (!nivelModerno) {
			return new EvaluacionOS(clasificacionBase, EstadoMitigacion.VULNERABLE, EstadoComponente.AUSENTE,
					firmwareVisible ? EstadoComponente.DESCONOCIDO : EstadoComponente.AUSENTE,
					"oslevel=" + oslevel + "; firmware=" + firmware, texto(ClavesTextoSeguridadCPU.AIX_ACTUALIZAR),
					URL_AIX);
		}

		return new EvaluacionOS(clasificacionBase, EstadoMitigacion.PARCIAL, EstadoComponente.PRESENTE,
				firmwareVisible ? EstadoComponente.DESCONOCIDO : EstadoComponente.AUSENTE,
				"oslevel=" + oslevel + "; lsmcode=" + firmware,
				texto(ClavesTextoSeguridadCPU.AIX_CONTROL_ESPECULATIVO_REVISAR), URL_AIX);
	}

	private static boolean esAixDe2019OMasReciente(String oslevel) {
		if (oslevel == null || oslevel.trim().isEmpty()) {
			return false;
		}
		String[] partes = oslevel.trim().split("-");
		if (partes.length < 4) {
			return false;
		}
		String fecha = partes[partes.length - 1];
		if (fecha.length() < 4) {
			return false;
		}
		try {
			int anio = Integer.parseInt(fecha.substring(0, 2));
			return anio >= 19;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// =====================================================================
	// XINUOS: UNIXWARE / OPENSERVER
	// =====================================================================

	private static EvaluacionOS evaluarXinuos(TipoVulnerabilidad tipo, InstantaneaCPU local,
			EstadoCPU clasificacionBase) {
		String unameX = ejecutarComando("uname", "-X");
		String patchck = ejecutarComando("pkginfo", "-l", "patchck");
		String sysinfoPaquete = ejecutarComando("pkginfo", "-l", "sysinfo");
		String sysinfo = ejecutarComando("sysinfo");
		String dmesg = filtrarLineasSeguridadCPU(ejecutarComando("dmesg"));

		StringBuilder evidencia = new StringBuilder();
		agregarEvidencia(evidencia, "estado", texto(ClavesTextoSeguridadCPU.XINUOS_SIN_ESTADO_RUNTIME));
		agregarEvidencia(evidencia, "uname -X", resumirSalida(unameX));
		agregarEvidencia(evidencia, "pkginfo patchck", resumirSalida(patchck));
		agregarEvidencia(evidencia, "pkginfo sysinfo", resumirSalida(sysinfoPaquete));
		agregarEvidencia(evidencia, "sysinfo", resumirSalida(sysinfo));
		agregarEvidencia(evidencia, "dmesg", resumirSalida(dmesg));

		if (clasificacionBase == EstadoCPU.NO_AFECTADO) {
			return new EvaluacionOS(EstadoCPU.NO_AFECTADO, EstadoMitigacion.NO_APLICA, EstadoComponente.NO_NECESARIO,
					EstadoComponente.NO_NECESARIO, evidencia.toString(),
					texto(ClavesTextoSeguridadCPU.XINUOS_CPU_NO_AFECTADO), URL_XINUOS);
		}

		return new EvaluacionOS(clasificacionBase, EstadoMitigacion.DESCONOCIDO, EstadoComponente.DESCONOCIDO,
				EstadoComponente.DESCONOCIDO, evidencia.toString(),
				texto(ClavesTextoSeguridadCPU.XINUOS_ACCION_PATCHCK), URL_XINUOS);
	}

	private static String filtrarLineasSeguridadCPU(String salida) {
		if (salida == null || salida.trim().isEmpty()) {
			return "";
		}
		StringBuilder filtrada = new StringBuilder();
		String[] lineas = salida.split("\\R");
		for (String linea : lineas) {
			String lower = linea.toLowerCase(Locale.ROOT);
			if (lower.contains("microcode") || lower.contains("firmware") || lower.contains("bios")
					|| lower.contains("spectre") || lower.contains("meltdown") || lower.contains("ibrs")
					|| lower.contains("ibpb") || lower.contains("retpoline") || lower.contains("kpti")
					|| lower.contains("pti")) {
				if (filtrada.length() > 0) {
					filtrada.append(System.lineSeparator());
				}
				filtrada.append(linea.trim());
			}
		}
		return filtrada.toString();
	}

	private static void agregarEvidencia(StringBuilder destino, String nombre, String valor) {
		if (valor == null || valor.trim().isEmpty()) {
			return;
		}
		if (destino.length() > 0) {
			destino.append("; ");
		}
		destino.append(nombre).append("=").append(valor);
	}

	private static String resumirSalida(String salida) {
		if (salida == null) {
			return "";
		}
		String limpia = salida.trim().replace('\r', ' ').replace('\n', ' ');
		while (limpia.contains("  ")) {
			limpia = limpia.replace("  ", " ");
		}
		int limite = 900;
		return limpia.length() <= limite ? limpia : limpia.substring(0, limite) + "...";
	}

	// =====================================================================
	// CLASIFICACIÓN CONSERVADORA DEL CPU CUANDO EL SO NO INFORMA EL ESTADO
	// =====================================================================

	private static EstadoCPU clasificarCPUParaMeltdown(String cpu, String fabricante, String arquitectura) {
		String t = normalizar(cpu + " " + fabricante + " " + arquitectura);

		if (contieneAlguno(t, "amd", "authenticamd", "hygon")) {
			return EstadoCPU.NO_AFECTADO;
		}
		if (contieneAlguno(t, "itanium", "ia64", "ia 64")) {
			return EstadoCPU.NO_AFECTADO;
		}
		if (contieneAlguno(t, "s390", "ibm z", "zseries", "linuxone")) {
			return EstadoCPU.NO_AFECTADO;
		}
		if (contieneAlguno(t, "cortex a75")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "cortex a5", "cortex a7", "cortex a32", "cortex a35", "cortex a53", "cortex a55",
				"cortex m", "cortex r")) {
			return EstadoCPU.NO_AFECTADO;
		}
		if (contieneAlguno(t, "power7", "power 7", "power8", "power 8", "power9", "power 9")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "apple a", "apple m")) {
			return EstadoCPU.POSIBLEMENTE_AFECTADO;
		}
		if (contieneAlguno(t, "intel", "genuineintel", "pentium pro", "core ", "xeon", "celeron", "pentium ", "atom")) {
			return EstadoCPU.POSIBLEMENTE_AFECTADO;
		}
		if (contieneAlguno(t, "sparc", "mips", "riscv", "loongarch", "arm", "aarch64")) {
			return EstadoCPU.DESCONOCIDO;
		}
		return EstadoCPU.DESCONOCIDO;
	}

	private static EstadoCPU clasificarCPUParaSpectre(String cpu, String fabricante, String arquitectura) {
		String t = normalizar(cpu + " " + fabricante + " " + arquitectura);

		if (contieneAlguno(t, "intel", "genuineintel", "amd", "authenticamd", "hygon", "via", "centaur", "zhaoxin",
				"xeon", "core ", "ryzen", "epyc", "threadripper", "athlon", "opteron")) {
			return EstadoCPU.POSIBLEMENTE_AFECTADO;
		}
		if (contieneAlguno(t, "cortex a8", "cortex a9", "cortex a12", "cortex a15", "cortex a17", "cortex a57",
				"cortex a72", "cortex a73", "cortex a75", "cortex a76", "cortex r7", "cortex r8", "krait", "kryo",
				"mongoose", "denver", "carmel", "apple a", "apple m")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "cortex a5", "cortex a7", "cortex a32", "cortex a35", "cortex a53", "cortex a55",
				"cortex m")) {
			return EstadoCPU.NO_AFECTADO;
		}
		if (contieneAlguno(t, "power7", "power 7", "power8", "power 8", "power9", "power 9", "s390", "ibm z",
				"linuxone")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "sparc t4", "sparc t5", "sparc t7", "sparc t8", "sparc m5", "sparc m6", "sparc m7",
				"sparc m8", "sparc s7")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "p5600", "p6600")) {
			return EstadoCPU.AFECTADO;
		}
		if (contieneAlguno(t, "sparc", "mips", "riscv", "loongarch", "arm", "aarch64", "power")) {
			return EstadoCPU.POSIBLEMENTE_AFECTADO;
		}
		return EstadoCPU.DESCONOCIDO;
	}

	// =====================================================================
	// INVENTARIO LOCAL Y MICROCÓDIGO/FIRMWARE
	// =====================================================================

	private static InstantaneaCPU capturarInstantaneaLocal() {
		String nombreSO = System.getProperty("os.name", texto(ClavesTextoSeguridadCPU.DESCONOCIDO)).trim();
		String versionSO = System.getProperty("os.version", "").trim();
		String arquitectura = System.getProperty("os.arch", texto(ClavesTextoSeguridadCPU.DESCONOCIDA)).trim();
		FamiliaSO familia = detectarFamiliaSO(nombreSO);
		String cpu = obtenerNombreCPULocal(familia, arquitectura);
		String fabricante = detectarFabricanteCPU(cpu, familia);
		String microcodigo = obtenerMicrocodigoFirmware(familia, arquitectura, fabricante);
		return new InstantaneaCPU(nombreSO, versionSO, arquitectura, cpu, fabricante, microcodigo, familia);
	}

	private static String obtenerNombreCPULocal(FamiliaSO familia, String arquitectura) {
		String valor = "";
		switch (familia) {
		case WINDOWS:
			valor = ejecutarComando("powershell.exe", "-NoLogo", "-NoProfile", "-NonInteractive", "-Command",
					"(Get-CimInstance Win32_Processor | Select-Object -First 1 -ExpandProperty Name)");
			if (valor.isEmpty()) {
				valor = ejecutarComando("wmic", "cpu", "get", "Name", "/value").replace("Name=", "").trim();
			}
			break;
		case LINUX:
			valor = primerCampoCpuinfo("model name", "processor", "cpu model", "machine", "hardware");
			break;
		case MACOS:
			valor = ejecutarComando("sysctl", "-n", "machdep.cpu.brand_string");
			if (valor.isEmpty()) {
				valor = ejecutarComando("sysctl", "-n", "hw.model");
			}
			break;
		case FREEBSD:
		case NETBSD:
		case OPENBSD:
		case DRAGONFLY:
			valor = sysctl("hw.model");
			break;
		case SOLARIS:
			valor = ejecutarComando("psrinfo", "-pv");
			if (valor.isEmpty()) {
				valor = ejecutarComando("uname", "-p");
			}
			break;
		case AIX:
			valor = extraerLineaCon(ejecutarComando("prtconf"), "processor type");
			if (valor.isEmpty()) {
				valor = ejecutarComando("uname", "-M");
			}
			break;
		case UNIXWARE:
		case OPENSERVER:
			valor = obtenerNombreCpuXinuos();
			break;
		case ZOS:
		case IBMI:
		case HPUX:
		default:
			valor = ejecutarComando("uname", "-m");
			break;
		}
		return textoNoVacio(limpiarSalidaCPU(valor), arquitectura);
	}

	private static String inferirFabricanteDesdeNombre(String cpu) {
		String t = normalizar(cpu);
		if (contieneAlguno(t, "intel", "xeon", "celeron", "pentium", "core i")) {
			return "Intel";
		}
		if (contieneAlguno(t, "amd", "ryzen", "epyc", "athlon", "opteron", "threadripper")) {
			return "AMD";
		}
		if (contieneAlguno(t, "apple")) {
			return "Apple";
		}
		if (contieneAlguno(t, "power", "s390", "ibm z", "linuxone")) {
			return "IBM";
		}
		if (contieneAlguno(t, "sparc")) {
			return "Oracle/Sun";
		}
		if (contieneAlguno(t, "arm", "cortex", "kryo", "krait", "aarch64")) {
			return texto(ClavesTextoSeguridadCPU.FABRICANTE_ARM_COMPATIBLE);
		}
		if (contieneAlguno(t, "mips")) {
			return texto(ClavesTextoSeguridadCPU.FABRICANTE_MIPS_COMPATIBLE);
		}
		return texto(ClavesTextoSeguridadCPU.DESCONOCIDO);
	}

	private static String inferirArquitecturaDesdeNombre(String cpu) {
		String t = normalizar(cpu);
		if (contieneAlguno(t, "itanium", "ia64", "ia 64")) {
			return "IA-64";
		}
		if (contieneAlguno(t, "intel", "xeon", "celeron", "pentium", "core i", "amd", "ryzen", "epyc", "athlon",
				"opteron", "threadripper", "hygon", "zhaoxin")) {
			return texto(ClavesTextoSeguridadCPU.ARQUITECTURA_X86_REGISTRO);
		}
		if (contieneAlguno(t, "arm", "cortex", "kryo", "krait", "apple a", "apple m", "aarch64")) {
			return "Arm/AArch64";
		}
		if (contieneAlguno(t, "power")) {
			return "IBM Power";
		}
		if (contieneAlguno(t, "s390", "ibm z", "linuxone")) {
			return "IBM Z/s390x";
		}
		if (contieneAlguno(t, "sparc")) {
			return "SPARC";
		}
		if (contieneAlguno(t, "mips")) {
			return "MIPS";
		}
		if (contieneAlguno(t, "riscv", "risc v")) {
			return "RISC-V";
		}
		return texto(ClavesTextoSeguridadCPU.DESCONOCIDA);
	}

	private static String detectarFabricanteCPU(String cpu, FamiliaSO familia) {
		String valor = "";
		if (familia == FamiliaSO.LINUX) {
			valor = primerCampoCpuinfo("vendor_id", "cpu implementer", "vendor", "machine");
		} else if (familia == FamiliaSO.MACOS) {
			valor = ejecutarComando("sysctl", "-n", "machdep.cpu.vendor");
		} else if (familia == FamiliaSO.WINDOWS) {
			valor = ejecutarComando("powershell.exe", "-NoLogo", "-NoProfile", "-NonInteractive", "-Command",
					"(Get-CimInstance Win32_Processor | Select-Object -First 1 -ExpandProperty Manufacturer)");
		}
		if (!valor.isEmpty()) {
			return limpiarSalidaCPU(valor);
		}
		String t = normalizar(cpu);
		if (contieneAlguno(t, "intel", "xeon", "celeron", "pentium")) {
			return "Intel";
		}
		if (contieneAlguno(t, "amd", "ryzen", "epyc", "athlon", "opteron")) {
			return "AMD";
		}
		if (contieneAlguno(t, "apple")) {
			return "Apple";
		}
		if (contieneAlguno(t, "power", "s390", "ibm")) {
			return "IBM";
		}
		if (contieneAlguno(t, "sparc")) {
			return "Oracle/Sun";
		}
		if (contieneAlguno(t, "arm", "cortex", "aarch64")) {
			return texto(ClavesTextoSeguridadCPU.FABRICANTE_ARM_COMPATIBLE);
		}
		return texto(ClavesTextoSeguridadCPU.DESCONOCIDO);
	}

	private static String obtenerMicrocodigoFirmware(FamiliaSO familia, String arquitectura, String fabricante) {
		switch (familia) {
		case WINDOWS:
			return obtenerMicrocodigoWindows();
		case LINUX:
			String mc = primerCampoCpuinfo("microcode");
			if (!mc.isEmpty()) {
				return mc;
			}
			if (esPower(arquitectura, fabricante)) {
				return textoNoVacio(ejecutarComando("lsmcode", "-c"), ejecutarComando("lsmcode", "-r"));
			}
			return texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_PROC_CPUINFO);
		case FREEBSD:
			return textoNoVacio(sysctl("dev.cpu.0.microcode_version"), texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_SO));
		case NETBSD:
		case OPENBSD:
			return textoNoVacio(extraerLineaCon(ejecutarComando("dmesg"), "microcode"),
					texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_SO));
		case MACOS:
			return textoNoVacio(ejecutarComando("sysctl", "-n", "machdep.cpu.microcode_version"),
					texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_APPLE));
		case SOLARIS:
			if (normalizar(arquitectura).contains("sparc")) {
				return textoNoVacio(ejecutarComando("prtconf", "-V"), texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_SO));
			}
			String kstat = ejecutarComando("kstat", "-p", "cpu_info:0:cpu_info0:microcode_version");
			return textoNoVacio(kstat, ejecutarComando("prtconf", "-V"), texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_SO));
		case AIX:
			return textoNoVacio(ejecutarComando("lsmcode", "-c"), ejecutarComando("lsmcode", "-r"),
					texto(ClavesTextoSeguridadCPU.NO_DISPONIBLE_AIX));
		case UNIXWARE:
		case OPENSERVER:
			return obtenerFirmwareXinuos();
		default:
			return texto(ClavesTextoSeguridadCPU.SIN_INTERFAZ_PORTATIL);
		}
	}

	private static String obtenerNombreCpuXinuos() {
		String unameX = ejecutarComando("uname", "-X");
		String lineaCPU = extraerPrimeraLineaConAlguno(unameX, "cpu", "processor", "machine");
		String hw = ejecutarComando("hw", "-r", "cpu");
		return textoNoVacio(limpiarSalidaCPU(lineaCPU), limpiarSalidaCPU(hw), ejecutarComando("uname", "-m"));
	}

	private static String obtenerFirmwareXinuos() {
		String dmesg = ejecutarComando("dmesg");
		String linea = extraerPrimeraLineaConAlguno(dmesg, "microcode", "firmware", "bios");
		return textoNoVacio(linea, texto(ClavesTextoSeguridadCPU.SIN_INTERFAZ_PORTATIL));
	}

	private static String extraerPrimeraLineaConAlguno(String texto, String... fragmentos) {
		if (texto == null || texto.isEmpty() || fragmentos == null) {
			return "";
		}
		String[] lineas = texto.split("\\R");
		for (String linea : lineas) {
			String lower = linea.toLowerCase(Locale.ROOT);
			for (String fragmento : fragmentos) {
				if (fragmento != null && lower.contains(fragmento.toLowerCase(Locale.ROOT))) {
					return linea.trim();
				}
			}
		}
		return "";
	}

	private static String obtenerMicrocodigoWindows() {
		String salida = ejecutarComando("reg", "query", "HKLM\\HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0",
				"/v", "Update Revision");
		if (salida.isEmpty()) {
			return texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_REGISTRO);
		}
		String[] lineas = salida.split("\\R");
		for (String linea : lineas) {
			String limpia = linea.trim();
			if (!limpia.toLowerCase(Locale.ROOT).startsWith("update revision")) {
				continue;
			}
			String[] partes = limpia.split("\\s+");
			if (partes.length > 0) {
				String binario = partes[partes.length - 1].replaceAll("[^0-9A-Fa-f]", "");
				String convertido = convertirLittleEndian(binario);
				return textoNoVacio(convertido, binario);
			}
		}
		return texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_REGISTRO);
	}

	private static String convertirLittleEndian(String hex) {
		if (hex == null || hex.isEmpty() || (hex.length() & 1) != 0) {
			return "";
		}
		StringBuilder invertido = new StringBuilder(hex.length());
		for (int i = hex.length(); i > 0; i -= 2) {
			invertido.append(hex, i - 2, i);
		}
		try {
			return "0x" + Long.toHexString(Long.parseUnsignedLong(invertido.toString(), 16)).toUpperCase(Locale.ROOT);
		} catch (NumberFormatException e) {
			return "";
		}
	}

	// =====================================================================
	// UTILIDADES
	// =====================================================================

	private static FamiliaSO detectarFamiliaSO(String osName) {
		String so = normalizar(osName);
		if (so.contains("windows")) {
			return FamiliaSO.WINDOWS;
		}
		if (so.contains("mac os") || so.contains("macos") || so.contains("darwin")) {
			return FamiliaSO.MACOS;
		}
		if (so.contains("freebsd")) {
			return FamiliaSO.FREEBSD;
		}
		if (so.contains("netbsd")) {
			return FamiliaSO.NETBSD;
		}
		if (so.contains("openbsd")) {
			return FamiliaSO.OPENBSD;
		}
		if (so.contains("dragonfly")) {
			return FamiliaSO.DRAGONFLY;
		}
		if (so.contains("unixware") || so.contains("openunix") || so.contains("open unix")) {
			return FamiliaSO.UNIXWARE;
		}
		if (so.contains("openserver") || so.contains("sco sv") || so.contains("sco_sv")) {
			return FamiliaSO.OPENSERVER;
		}
		if (so.contains("sunos") || so.contains("solaris") || so.contains("illumos")) {
			return FamiliaSO.SOLARIS;
		}
		if (so.equals("aix") || so.startsWith("aix ")) {
			return FamiliaSO.AIX;
		}
		if (so.contains("z os") || so.contains("z/os")) {
			return FamiliaSO.ZOS;
		}
		if (so.contains("os 400") || so.contains("os/400") || so.contains("ibm i")) {
			return FamiliaSO.IBMI;
		}
		if (so.contains("hp ux") || so.contains("hp-ux")) {
			return FamiliaSO.HPUX;
		}
		if (so.contains("linux") || so.contains("android")) {
			return FamiliaSO.LINUX;
		}
		return FamiliaSO.OTRO;
	}

	private static String enlaceParaSO(FamiliaSO familia) {
		switch (familia) {
		case WINDOWS:
			return URL_WINDOWS;
		case LINUX:
			return URL_LINUX;
		case MACOS:
			return URL_APPLE;
		case FREEBSD:
			return URL_FREEBSD;
		case NETBSD:
			return URL_NETBSD;
		case OPENBSD:
			return URL_OPENBSD;
		case SOLARIS:
			return URL_SOLARIS;
		case AIX:
			return URL_AIX;
		case UNIXWARE:
		case OPENSERVER:
			return URL_XINUOS;
		default:
			return URL_GENERICO;
		}
	}

	private static boolean pareceMismoCPU(String uno, String dos) {
		if (uno == null || uno.trim().isEmpty() || dos == null || dos.trim().isEmpty()) {
			return false;
		}
		String a = normalizarModeloCPU(uno);
		String b = normalizarModeloCPU(dos);
		if (a.equals(b) || a.contains(b) || b.contains(a)) {
			return true;
		}
		List<String> tokensA = tokensSignificativos(a);
		List<String> tokensB = tokensSignificativos(b);
		int comunes = 0;
		for (String token : tokensA) {
			if (tokensB.contains(token)) {
				comunes++;
			}
		}
		return comunes >= 2 && comunes >= Math.min(tokensA.size(), tokensB.size()) / 2;
	}

	private static String normalizarModeloCPU(String texto) {
		String n = normalizar(texto).replace("processor", " ").replace("cpu", " ").replace("genuine", " ")
				.replace("authentic", " ").replace("tm", " ").replace("r ", " ");
		n = n.replaceAll("[0-9]+(?:\\.[0-9]+)?\\s*(?:ghz|mhz)", " ");
		return n.replaceAll("\\s+", " ").trim();
	}

	private static List<String> tokensSignificativos(String texto) {
		List<String> lista = new ArrayList<String>();
		for (String token : texto.split("\\s+")) {
			if (token.length() >= 3 && !lista.contains(token)) {
				lista.add(token);
			}
		}
		return lista;
	}

	private static String normalizar(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.toLowerCase(Locale.ROOT).replace('(', ' ').replace(')', ' ').replace('-', ' ').replace('_', ' ')
				.replace('/', ' ').replaceAll("\\s+", " ").trim();
	}

	private static boolean contieneAlguno(String texto, String... opciones) {
		for (String opcion : opciones) {
			if (texto.contains(normalizar(opcion))) {
				return true;
			}
		}
		return false;
	}

	private static String leerArchivoSeguro(String ruta) {
		try {
			Path path = Paths.get(ruta);
			if (!Files.isRegularFile(path)) {
				return "";
			}
			byte[] datos = Files.readAllBytes(path);
			if (datos.length > LIMITE_SALIDA) {
				return new String(datos, 0, LIMITE_SALIDA, StandardCharsets.UTF_8).trim();
			}
			return new String(datos, StandardCharsets.UTF_8).trim();
		} catch (Exception e) {
			CrashDetectorLogger.log(texto(ClavesTextoSeguridadCPU.ERROR_LEER_RUTA, ruta) + ": " + e.getMessage());
			return "";
		}
	}

	private static String primerCampoCpuinfo(String... claves) {
		File archivo = new File("/proc/cpuinfo");
		if (!archivo.isFile()) {
			return "";
		}

		// Se conservan todos los primeros valores y después se respeta el orden de
		// preferencia recibido. Así "model name" gana sobre el campo numérico
		// "processor", aunque este último aparezca primero en /proc/cpuinfo.
		Map<String, String> valores = new LinkedHashMap<String, String>();
		try (InputStream in = new FileInputStream(archivo);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			String linea;
			while ((linea = reader.readLine()) != null) {
				int pos = linea.indexOf(':');
				if (pos <= 0) {
					continue;
				}
				String clave = linea.substring(0, pos).trim().toLowerCase(Locale.ROOT);
				if (!valores.containsKey(clave)) {
					valores.put(clave, linea.substring(pos + 1).trim());
				}
			}
		} catch (Exception e) {
			CrashDetectorLogger
					.log(texto(ClavesTextoSeguridadCPU.ERROR_LEER_RUTA, "/proc/cpuinfo") + ": " + e.getMessage());
			return "";
		}

		for (String buscada : claves) {
			String valor = valores.get(buscada.toLowerCase(Locale.ROOT));
			if (valor != null && !valor.trim().isEmpty()) {
				return valor.trim();
			}
		}
		return "";
	}

	private static String sysctl(String clave) {
		return ejecutarComando("sysctl", "-n", clave);
	}

	private static String ejecutarComando(String... comando) {
		ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
			Thread t = new Thread(r, "lector-comando-cpu");
			t.setDaemon(true);
			return t;
		});
		Process proceso = null;
		try {
			ProcessBuilder pb = new ProcessBuilder(comando);
			pb.redirectErrorStream(true);
			proceso = pb.start();
			final InputStream salidaProceso = proceso.getInputStream();
			Future<String> lectura = executor.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					StringBuilder sb = new StringBuilder();
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(salidaProceso, StandardCharsets.UTF_8))) {
						String linea;
						while ((linea = reader.readLine()) != null && sb.length() < LIMITE_SALIDA) {
							if (sb.length() > 0) {
								sb.append('\n');
							}
							sb.append(linea);
						}
					}
					return sb.toString().trim();
				}
			});

			if (!proceso.waitFor(TIEMPO_COMANDO_SEGUNDOS, TimeUnit.SECONDS)) {
				proceso.destroyForcibly();
				lectura.cancel(true);
				return "";
			}
			return lectura.get(1, TimeUnit.SECONDS).trim();
		} catch (Throwable t) {
			return "";
		} finally {
			if (proceso != null && proceso.isAlive()) {
				proceso.destroyForcibly();
			}
			executor.shutdownNow();
		}
	}

	private static Map<String, String> parsearClaves(String salida) {
		Map<String, String> mapa = new LinkedHashMap<String, String>();
		if (salida == null) {
			return mapa;
		}
		for (String linea : salida.split("\\R")) {
			int pos = linea.indexOf('=');
			if (pos <= 0) {
				continue;
			}
			mapa.put(linea.substring(0, pos).trim(), linea.substring(pos + 1).trim());
		}
		return mapa;
	}

	private static Boolean valorBooleano(Map<String, String> mapa, String clave) {
		String valor = mapa.get(clave);
		if (valor == null) {
			return null;
		}
		if ("true".equalsIgnoreCase(valor) || "1".equals(valor)) {
			return Boolean.TRUE;
		}
		if ("false".equalsIgnoreCase(valor) || "0".equals(valor)) {
			return Boolean.FALSE;
		}
		return null;
	}

	private static String resumenMapa(Map<String, String> mapa, String... claves) {
		if (mapa == null || mapa.isEmpty()) {
			return "sin datos";
		}
		StringBuilder sb = new StringBuilder();
		if (claves == null || claves.length == 0) {
			for (Map.Entry<String, String> entrada : mapa.entrySet()) {
				agregarPar(sb, entrada.getKey(), entrada.getValue());
			}
		} else {
			for (String clave : claves) {
				if (mapa.containsKey(clave)) {
					agregarPar(sb, clave, mapa.get(clave));
				}
			}
		}
		return sb.toString();
	}

	private static void agregarPar(StringBuilder sb, String clave, String valor) {
		if (sb.length() > 0) {
			sb.append("; ");
		}
		sb.append(clave).append('=').append(valor);
	}

	private static String primerValorNoVacio(String... valores) {
		return textoNoVacio(valores);
	}

	private static String textoNoVacio(String... valores) {
		if (valores != null) {
			for (String valor : valores) {
				if (valor != null && !valor.trim().isEmpty()) {
					return valor.trim();
				}
			}
		}
		return "";
	}

	private static String limpiarSalidaCPU(String salida) {
		if (salida == null) {
			return "";
		}
		String limpia = salida.replace('\r', ' ').replace('\n', ' ').replaceAll("\\s+", " ").trim();
		int max = 300;
		return limpia.length() > max ? limpia.substring(0, max) : limpia;
	}

	private static String extraerLineaCon(String salida, String texto) {
		if (salida == null || texto == null) {
			return "";
		}
		for (String linea : salida.split("\\R")) {
			if (linea.toLowerCase(Locale.ROOT).contains(texto.toLowerCase(Locale.ROOT))) {
				int pos = linea.indexOf(':');
				return pos >= 0 ? linea.substring(pos + 1).trim() : linea.trim();
			}
		}
		return "";
	}

	private static boolean esRevisionFirmwareVisible(String firmware) {
		if (firmware == null || firmware.trim().isEmpty()) {
			return false;
		}
		String limpio = firmware.trim();
		return !limpio.equals(texto(ClavesTextoSeguridadCPU.DESCONOCIDO))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_PROC_CPUINFO))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_SO))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_APPLE))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.NO_DISPONIBLE_AIX))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.SIN_INTERFAZ_PORTATIL))
				&& !limpio.equals(texto(ClavesTextoSeguridadCPU.NO_EXPUESTO_REGISTRO));
	}

	private static String texto(String clave, String... argumentos) {
		return MonitorDePID.idioma.mensajeSeguridadCPU(clave, argumentos);
	}

	private static boolean esUnoOVerdadero(String valor) {
		if (valor == null) {
			return false;
		}
		String v = valor.trim().toLowerCase(Locale.ROOT);
		return "1".equals(v) || "true".equals(v) || "yes".equals(v) || "enabled".equals(v) || "active".equals(v)
				|| v.startsWith("1 ");
	}

	private static boolean esCeroOFalso(String valor) {
		if (valor == null) {
			return false;
		}
		String v = valor.trim().toLowerCase(Locale.ROOT);
		return "0".equals(v) || "false".equals(v) || "no".equals(v) || "disabled".equals(v) || "inactive".equals(v)
				|| v.startsWith("0 ");
	}

	private static boolean estaHabilitado(String valor) {
		return valor != null && valor.toLowerCase(Locale.ROOT).contains("enabled");
	}

	private static boolean estaDeshabilitado(String valor) {
		return valor != null && valor.toLowerCase(Locale.ROOT).contains("disabled");
	}

	private static boolean esPower(String arquitectura, String fabricante) {
		String t = normalizar(arquitectura + " " + fabricante);
		return t.contains("ppc") || t.contains("power");
	}

	private static EstadoComponente estadoComponenteSO(EstadoMitigacion estado) {
		if (estado == EstadoMitigacion.MITIGADO) {
			return EstadoComponente.PRESENTE;
		}
		if (estado == EstadoMitigacion.VULNERABLE) {
			return EstadoComponente.AUSENTE;
		}
		if (estado == EstadoMitigacion.NO_APLICA) {
			return EstadoComponente.NO_NECESARIO;
		}
		return EstadoComponente.DESCONOCIDO;
	}

	private enum TipoVulnerabilidad {
		MELTDOWN, SPECTRE;

		String nombreLocalizado() {
			return this == MELTDOWN ? MonitorDePID.idioma.nombreVulnerabilidadMeltdown()
					: MonitorDePID.idioma.nombreVulnerabilidadSpectre();
		}
	}

	private enum FamiliaSO {
		WINDOWS, LINUX, MACOS, FREEBSD, NETBSD, OPENBSD, DRAGONFLY, SOLARIS, AIX, UNIXWARE, OPENSERVER, ZOS, IBMI, HPUX,
		OTRO
	}

	private enum EstadoLinux {
		NO_AFECTADO, MITIGADO, VULNERABLE, DESCONOCIDO
	}

	private static final class InstantaneaCPU {
		final String nombreSO;
		final String versionSO;
		final String arquitectura;
		final String nombreCPU;
		final String fabricante;
		final String microcodigoFirmware;
		final FamiliaSO familiaSO;

		InstantaneaCPU(String nombreSO, String versionSO, String arquitectura, String nombreCPU, String fabricante,
				String microcodigoFirmware, FamiliaSO familiaSO) {
			this.nombreSO = nombreSO;
			this.versionSO = versionSO;
			this.arquitectura = arquitectura;
			this.nombreCPU = nombreCPU;
			this.fabricante = fabricante;
			this.microcodigoFirmware = microcodigoFirmware;
			this.familiaSO = familiaSO;
		}

		String nombreSOCompleto() {
			return versionSO.isEmpty() ? nombreSO : nombreSO + " " + versionSO;
		}
	}

	private static final class EvaluacionOS {
		final EstadoCPU estadoCPU;
		final EstadoMitigacion estadoMitigacion;
		final EstadoComponente parcheSO;
		final EstadoComponente parcheFirmware;
		final String evidencia;
		final String accionRecomendada;
		final String enlaceOficial;

		EvaluacionOS(EstadoCPU estadoCPU, EstadoMitigacion estadoMitigacion, EstadoComponente parcheSO,
				EstadoComponente parcheFirmware, String evidencia, String accionRecomendada, String enlaceOficial) {
			this.estadoCPU = estadoCPU;
			this.estadoMitigacion = estadoMitigacion;
			this.parcheSO = parcheSO;
			this.parcheFirmware = parcheFirmware;
			this.evidencia = evidencia;
			this.accionRecomendada = accionRecomendada;
			this.enlaceOficial = enlaceOficial;
		}

		static EvaluacionOS desconocida(EstadoCPU cpu, String evidencia, String accion, String enlace) {
			return new EvaluacionOS(cpu, EstadoMitigacion.DESCONOCIDO, EstadoComponente.DESCONOCIDO,
					EstadoComponente.DESCONOCIDO, evidencia, accion, enlace);
		}
	}

	private static final class Version {
		final int mayor;
		final int menor;
		final int parche;

		Version(int mayor, int menor, int parche) {
			this.mayor = mayor;
			this.menor = menor;
			this.parche = parche;
		}

		static Version parse(String texto) {
			if (texto == null) {
				return null;
			}
			String limpio = texto.trim();
			int inicio = -1;
			for (int i = 0; i < limpio.length(); i++) {
				if (Character.isDigit(limpio.charAt(i))) {
					inicio = i;
					break;
				}
			}
			if (inicio < 0) {
				return null;
			}
			StringBuilder numero = new StringBuilder();
			for (int i = inicio; i < limpio.length(); i++) {
				char c = limpio.charAt(i);
				if (Character.isDigit(c) || c == '.') {
					numero.append(c);
				} else {
					break;
				}
			}
			String[] partes = numero.toString().split("\\.");
			try {
				int mayor = partes.length > 0 && !partes[0].isEmpty() ? Integer.parseInt(partes[0]) : 0;
				int menor = partes.length > 1 && !partes[1].isEmpty() ? Integer.parseInt(partes[1]) : 0;
				int parche = partes.length > 2 && !partes[2].isEmpty() ? Integer.parseInt(partes[2]) : 0;
				return new Version(mayor, menor, parche);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		boolean alMenos(int a, int b, int c) {
			if (mayor != a) {
				return mayor > a;
			}
			if (menor != b) {
				return menor > b;
			}
			return parche >= c;
		}

		boolean es(int a, int b, int c) {
			return mayor == a && menor == b && parche == c;
		}

		@Override
		public String toString() {
			return mayor + "." + menor + "." + parche;
		}
	}
}
