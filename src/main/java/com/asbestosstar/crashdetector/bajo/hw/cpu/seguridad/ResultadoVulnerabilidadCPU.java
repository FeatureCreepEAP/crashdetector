package com.asbestosstar.crashdetector.bajo.hw.cpu.seguridad;

import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;

/**
 * Resultado inmutable de una comprobación de Meltdown o Spectre.
 *
 * La clase separa tres preguntas distintas:
 *
 * 1. ¿El CPU parece afectado? 2. ¿El sistema operativo tiene activa una
 * mitigación? 3. ¿El microcódigo, firmware o soporte de hardware requerido está
 * presente?
 *
 * Ningún texto visible se almacena aquí de manera fija. Las etiquetas, estados
 * y explicaciones se obtienen de MonitorDePID.idioma al construir la salida.
 */
public final class ResultadoVulnerabilidadCPU {

	public enum EstadoCPU {
		AFECTADO, POSIBLEMENTE_AFECTADO, NO_AFECTADO, DESCONOCIDO
	}

	public enum EstadoMitigacion {
		MITIGADO, PARCIAL, VULNERABLE, NO_APLICA, DESCONOCIDO
	}

	public enum EstadoComponente {
		PRESENTE, AUSENTE, NO_NECESARIO, DESCONOCIDO
	}

	private final String vulnerabilidad;
	private final String cpu;
	private final String fabricante;
	private final String arquitectura;
	private final String sistemaOperativo;
	private final EstadoCPU estadoCPU;
	private final EstadoMitigacion estadoMitigacion;
	private final EstadoComponente parcheSistemaOperativo;
	private final EstadoComponente parcheMicrocodigoFirmware;
	private final String revisionMicrocodigoFirmware;
	private final String evidencia;
	private final String accionRecomendada;
	private final String enlaceOficial;
	private final boolean correspondeAlEquipoLocal;

	public ResultadoVulnerabilidadCPU(String vulnerabilidad, String cpu, String fabricante, String arquitectura,
			String sistemaOperativo, EstadoCPU estadoCPU, EstadoMitigacion estadoMitigacion,
			EstadoComponente parcheSistemaOperativo, EstadoComponente parcheMicrocodigoFirmware,
			String revisionMicrocodigoFirmware, String evidencia, String accionRecomendada, String enlaceOficial,
			boolean correspondeAlEquipoLocal) {
		this.vulnerabilidad = seguro(vulnerabilidad);
		this.cpu = seguro(cpu);
		this.fabricante = seguro(fabricante);
		this.arquitectura = seguro(arquitectura);
		this.sistemaOperativo = seguro(sistemaOperativo);
		this.estadoCPU = estadoCPU == null ? EstadoCPU.DESCONOCIDO : estadoCPU;
		this.estadoMitigacion = estadoMitigacion == null ? EstadoMitigacion.DESCONOCIDO : estadoMitigacion;
		this.parcheSistemaOperativo = parcheSistemaOperativo == null ? EstadoComponente.DESCONOCIDO
				: parcheSistemaOperativo;
		this.parcheMicrocodigoFirmware = parcheMicrocodigoFirmware == null ? EstadoComponente.DESCONOCIDO
				: parcheMicrocodigoFirmware;
		this.revisionMicrocodigoFirmware = seguro(revisionMicrocodigoFirmware);
		this.evidencia = seguro(evidencia);
		this.accionRecomendada = seguro(accionRecomendada);
		this.enlaceOficial = seguro(enlaceOficial);
		this.correspondeAlEquipoLocal = correspondeAlEquipoLocal;
	}

	private static String seguro(String texto) {
		return texto == null ? "" : texto.trim();
	}

	private static Idioma idioma() {
		return MonitorDePID.idioma;
	}

	public String getVulnerabilidad() {
		return vulnerabilidad;
	}

	public String getCpu() {
		return cpu;
	}

	public String getFabricante() {
		return fabricante;
	}

	public String getArquitectura() {
		return arquitectura;
	}

	public String getSistemaOperativo() {
		return sistemaOperativo;
	}

	public EstadoCPU getEstadoCPU() {
		return estadoCPU;
	}

	public EstadoMitigacion getEstadoMitigacion() {
		return estadoMitigacion;
	}

	public EstadoComponente getParcheSistemaOperativo() {
		return parcheSistemaOperativo;
	}

	public EstadoComponente getParcheMicrocodigoFirmware() {
		return parcheMicrocodigoFirmware;
	}

	public String getRevisionMicrocodigoFirmware() {
		return revisionMicrocodigoFirmware;
	}

	public String getEvidencia() {
		return evidencia;
	}

	public String getAccionRecomendada() {
		return accionRecomendada;
	}

	public String getEnlaceOficial() {
		return enlaceOficial;
	}

	public boolean correspondeAlEquipoLocal() {
		return correspondeAlEquipoLocal;
	}

	/**
	 * Se muestra una advertencia cuando el CPU está afectado o posiblemente
	 * afectado y no se ha podido confirmar una mitigación completa.
	 */
	public boolean requiereAdvertencia() {
		boolean cpuRelevante = estadoCPU == EstadoCPU.AFECTADO || estadoCPU == EstadoCPU.POSIBLEMENTE_AFECTADO;
		boolean seguro = estadoMitigacion == EstadoMitigacion.MITIGADO
				|| estadoMitigacion == EstadoMitigacion.NO_APLICA;
		return cpuRelevante && !seguro;
	}

	public String construirMensajeHtml() {
		Idioma i = idioma();
		StringBuilder sb = new StringBuilder();
		sb.append("<b>").append(escaparHtml(i.tituloProteccionProcesador(vulnerabilidad))).append("</b><br><br>");
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuCPU(), cpu);
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuFabricante(), fabricante);
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuArquitectura(), arquitectura);
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuSistemaOperativo(), sistemaOperativo);
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuAfectado(), i.estadoAfectacionSeguridadCPU(estadoCPU.name()));
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuEstadoGeneral(),
				i.estadoMitigacionSeguridadCPU(estadoMitigacion.name()));
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuMitigacionSO(),
				i.estadoComponenteSeguridadCPU(parcheSistemaOperativo.name()));
		agregarLineaHtml(sb, i.etiquetaSeguridadCpuMicrocodigoFirmware(),
				i.estadoComponenteSeguridadCPU(parcheMicrocodigoFirmware.name()));

		if (!revisionMicrocodigoFirmware.isEmpty()) {
			agregarLineaHtml(sb, i.etiquetaSeguridadCpuRevisionDetectada(), revisionMicrocodigoFirmware);
		}
		if (!correspondeAlEquipoLocal) {
			sb.append("<br><b>").append(escaparHtml(i.etiquetaSeguridadCpuNota())).append(":</b> ")
					.append(escaparHtml(i.notaSeguridadCpuRegistroRemoto())).append("<br>");
		}
		if (!evidencia.isEmpty()) {
			sb.append("<br><b>").append(escaparHtml(i.etiquetaSeguridadCpuEvidencia())).append(":</b> ")
					.append(escaparHtml(evidencia)).append("<br>");
		}
		if (!accionRecomendada.isEmpty()) {
			sb.append("<br><b>").append(escaparHtml(i.etiquetaSeguridadCpuAccionRecomendada())).append(":</b> ")
					.append(escaparHtml(accionRecomendada));
		}
		return sb.toString();
	}

	public String construirResumenTexto() {
		Idioma i = idioma();
		StringBuilder sb = new StringBuilder();
		sb.append(vulnerabilidad).append(" | ").append(i.etiquetaSeguridadCpuCPU()).append("=").append(cpu);
		sb.append(" | ").append(i.etiquetaSeguridadCpuAfectado()).append("=")
				.append(i.estadoAfectacionSeguridadCPU(estadoCPU.name()));
		sb.append(" | ").append(i.etiquetaSeguridadCpuEstadoGeneral()).append("=")
				.append(i.estadoMitigacionSeguridadCPU(estadoMitigacion.name()));
		sb.append(" | ").append(i.etiquetaSeguridadCpuMitigacionSO()).append("=")
				.append(i.estadoComponenteSeguridadCPU(parcheSistemaOperativo.name()));
		sb.append(" | ").append(i.etiquetaSeguridadCpuMicrocodigoFirmware()).append("=")
				.append(i.estadoComponenteSeguridadCPU(parcheMicrocodigoFirmware.name()));
		if (!revisionMicrocodigoFirmware.isEmpty()) {
			sb.append(" | ").append(i.etiquetaSeguridadCpuRevisionDetectada()).append("=")
					.append(revisionMicrocodigoFirmware);
		}
		if (!evidencia.isEmpty()) {
			sb.append(" | ").append(i.etiquetaSeguridadCpuEvidencia()).append("=").append(evidencia.replace('\n', ' '));
		}
		return sb.toString();
	}

	private static void agregarLineaHtml(StringBuilder sb, String etiqueta, String valor) {
		sb.append(escaparHtml(etiqueta)).append(": ").append(escaparHtml(valor)).append("<br>");
	}

	private static String escaparHtml(String texto) {
		if (texto == null || texto.isEmpty()) {
			return "";
		}
		return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
				.replace("'", "&#39;");
	}
}
