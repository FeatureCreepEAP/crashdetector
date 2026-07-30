package com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad;

/**
 * Claves estables para los textos localizados utilizados por las comprobaciones
 * de Meltdown y Spectre.
 *
 * Las claves no son textos visibles. Cada implementación de Idioma debe
 * resolverlas mediante Idioma.mensajeSeguridadCPU(...).
 */
public final class ClavesTextoSeguridadCPU {

	private ClavesTextoSeguridadCPU() {
	}

	public static final String REGISTRO_CPU_DISTINTO_EVIDENCIA = "registro_cpu_distinto_evidencia";
	public static final String REGISTRO_CPU_DISTINTO_ACCION = "registro_cpu_distinto_accion";
	public static final String SO_NO_DETERMINADO_REGISTRO = "so_no_determinado_registro";

	public static final String DRAGONFLY_SIN_INTERFAZ = "dragonfly_sin_interfaz";
	public static final String DRAGONFLY_ACCION = "dragonfly_accion";
	public static final String ZOS_SIN_INTERFAZ = "zos_sin_interfaz";
	public static final String ZOS_ACCION = "zos_accion";
	public static final String IBMI_SIN_INTERFAZ = "ibmi_sin_interfaz";
	public static final String IBMI_ACCION = "ibmi_accion";
	public static final String HPUX_SIN_INTERFAZ = "hpux_sin_interfaz";
	public static final String HPUX_ACCION = "hpux_accion";
	public static final String SO_SIN_INTERFAZ = "so_sin_interfaz";
	public static final String SO_SIN_INTERFAZ_ACCION = "so_sin_interfaz_accion";

	public static final String LINUX_MELTDOWN_SIN_SYSFS = "linux_meltdown_sin_sysfs";
	public static final String LINUX_MELTDOWN_SIN_SYSFS_ACCION = "linux_meltdown_sin_sysfs_accion";
	public static final String LINUX_SPECTRE_SIN_SYSFS = "linux_spectre_sin_sysfs";
	public static final String LINUX_SPECTRE_SIN_SYSFS_ACCION = "linux_spectre_sin_sysfs_accion";
	public static final String LINUX_SPECTRE_MITIGADO_ACCION = "linux_spectre_mitigado_accion";
	public static final String LINUX_SPECTRE_ACTUALIZAR_ACCION = "linux_spectre_actualizar_accion";
	public static final String LINUX_CPU_NO_AFECTADO = "linux_cpu_no_afectado";
	public static final String LINUX_MITIGACION_ACTIVA = "linux_mitigacion_activa";
	public static final String LINUX_KERNEL_VULNERABLE_ACCION = "linux_kernel_vulnerable_accion";
	public static final String LINUX_REVISAR_SYSFS_ACCION = "linux_revisar_sysfs_accion";
	public static final String NO_DISPONIBLE = "no_disponible";

	public static final String WINDOWS_SPECULATIONCONTROL_AUSENTE = "windows_speculationcontrol_ausente";
	public static final String WINDOWS_SPECULATIONCONTROL_ACCION = "windows_speculationcontrol_accion";
	public static final String WINDOWS_MELTDOWN_HARDWARE = "windows_meltdown_hardware";
	public static final String WINDOWS_KVA_ACTIVO = "windows_kva_activo";
	public static final String WINDOWS_KVA_ACTUALIZAR = "windows_kva_actualizar";
	public static final String WINDOWS_REVISAR_MELTDOWN = "windows_revisar_meltdown";
	public static final String WINDOWS_BTI_ACTIVO = "windows_bti_activo";
	public static final String WINDOWS_BTI_ACTUALIZAR = "windows_bti_actualizar";
	public static final String WINDOWS_REVISAR_SPECTRE = "windows_revisar_spectre";

	public static final String MACOS_VERSION_NO_INTERPRETADA = "macos_version_no_interpretada";
	public static final String MACOS_ACTUALIZAR = "macos_actualizar";
	public static final String MACOS_POSTERIOR_PARCHES_ORIGINALES = "macos_posterior_parches_originales";
	public static final String MACOS_MELTDOWN_10132 = "macos_meltdown_10132";
	public static final String MACOS_SECURITY_UPDATE_EVIDENCIA = "macos_security_update_evidencia";
	public static final String MACOS_SECURITY_UPDATE_INSTALADO = "macos_security_update_instalado";
	public static final String MACOS_MELTDOWN_ANTIGUO_ACCION = "macos_meltdown_antiguo_accion";
	public static final String MACOS_SPECTRE_SUPLEMENTAL = "macos_spectre_suplemental";
	public static final String MACOS_SPECTRE_SAFARI_PARCIAL = "macos_spectre_safari_parcial";
	public static final String MACOS_SPECTRE_SAFARI_1102 = "macos_spectre_safari_1102";
	public static final String MACOS_SPECTRE_ACTUALIZAR = "macos_spectre_actualizar";

	public static final String FREEBSD_PTI_HABILITAR = "freebsd_pti_habilitar";
	public static final String FREEBSD_PTI_NO_LEIDO = "freebsd_pti_no_leido";
	public static final String FREEBSD_PTI_REVISAR = "freebsd_pti_revisar";
	public static final String FREEBSD_SPECTRE_MITIGADO = "freebsd_spectre_mitigado";
	public static final String FREEBSD_IBRS_DESHABILITADO = "freebsd_ibrs_deshabilitado";
	public static final String FREEBSD_IBRS_INACTIVO = "freebsd_ibrs_inactivo";
	public static final String FREEBSD_IBRS_NO_ENCONTRADO = "freebsd_ibrs_no_encontrado";
	public static final String FREEBSD_IBRS_REVISAR = "freebsd_ibrs_revisar";

	public static final String NETBSD_SVS_HABILITAR = "netbsd_svs_habilitar";
	public static final String NETBSD_SVS_NO_LEIDO = "netbsd_svs_no_leido";
	public static final String NETBSD_SVS_REVISAR = "netbsd_svs_revisar";
	public static final String NETBSD_SPECTRE_MITIGADO = "netbsd_spectre_mitigado";
	public static final String NETBSD_SPECTRE_HABILITAR = "netbsd_spectre_habilitar";
	public static final String NETBSD_SPECTRE_NO_LEIDO = "netbsd_spectre_no_leido";
	public static final String NETBSD_SPECTRE_REVISAR = "netbsd_spectre_revisar";

	public static final String OPENBSD_VERSION_NO_LEIDA = "openbsd_version_no_leida";
	public static final String OPENBSD_ACTUALIZAR = "openbsd_actualizar";
	public static final String OPENBSD_MELTDOWN_INCLUIDO = "openbsd_meltdown_incluido";
	public static final String OPENBSD_SPECTRE_PARCIAL = "openbsd_spectre_parcial";
	public static final String OPENBSD_ANTIGUO_ACCION = "openbsd_antiguo_accion";

	public static final String SOLARIS_SXADM_AUSENTE = "solaris_sxadm_ausente";
	public static final String SOLARIS_SXADM_ACCION = "solaris_sxadm_accion";
	public static final String SOLARIS_RDCL_HARDWARE = "solaris_rdcl_hardware";
	public static final String SOLARIS_KPTI_ACTIVO = "solaris_kpti_activo";
	public static final String SOLARIS_KPTI_ACTIVAR = "solaris_kpti_activar";
	public static final String SOLARIS_MELTDOWN_REVISAR = "solaris_meltdown_revisar";
	public static final String SOLARIS_V2_ACTIVO = "solaris_v2_activo";
	public static final String SOLARIS_EXTENSION_ACTIVAR = "solaris_extension_activar";
	public static final String SOLARIS_V2_REVISAR = "solaris_v2_revisar";

	public static final String AIX_ACTUALIZAR = "aix_actualizar";
	public static final String AIX_CONTROL_ESPECULATIVO_REVISAR = "aix_control_especulativo_revisar";

	public static final String XINUOS_SIN_ESTADO_RUNTIME = "xinuos_sin_estado_runtime";
	public static final String XINUOS_ACCION_PATCHCK = "xinuos_accion_patchck";
	public static final String XINUOS_CPU_NO_AFECTADO = "xinuos_cpu_no_afectado";

	public static final String DESCONOCIDO = "desconocido";
	public static final String DESCONOCIDA = "desconocida";
	public static final String FABRICANTE_ARM_COMPATIBLE = "fabricante_arm_compatible";
	public static final String FABRICANTE_MIPS_COMPATIBLE = "fabricante_mips_compatible";
	public static final String ARQUITECTURA_X86_REGISTRO = "arquitectura_x86_registro";
	public static final String NO_EXPUESTO_PROC_CPUINFO = "no_expuesto_proc_cpuinfo";
	public static final String NO_EXPUESTO_SO = "no_expuesto_so";
	public static final String NO_EXPUESTO_APPLE = "no_expuesto_apple";
	public static final String NO_DISPONIBLE_AIX = "no_disponible_aix";
	public static final String SIN_INTERFAZ_PORTATIL = "sin_interfaz_portatil";
	public static final String NO_EXPUESTO_REGISTRO = "no_expuesto_registro";
	public static final String NO_EXPUESTO_REGISTRO_REMOTO = "no_expuesto_registro_remoto";
	public static final String ERROR_LEER_RUTA = "error_leer_ruta";
	public static final String TEXTO_DESCONOCIDO = "texto_desconocido";
}
