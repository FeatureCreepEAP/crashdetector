package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.app.CrashDetectorCli;

/** Comprueba que el estado CLI pertenece al núcleo y no a la fachada app. */
class PruebaEstadoSalidaCLI {

	@AfterEach
	void restableceEstado() {
		MonitorDePID.restablecerCodigoSalidaCLI();
	}

	@Test
	void conservaCodigosPublicosYNormalizaCodigosDesconocidos() {
		MonitorDePID.restablecerCodigoSalidaCLI();
		assertEquals(MonitorDePID.CODIGO_EXITO, MonitorDePID.obtenerCodigoSalidaCLI());

		MonitorDePID.marcarFalloMonitor(MonitorDePID.CODIGO_ENTRADA_INVALIDA);
		assertEquals(2, MonitorDePID.obtenerCodigoSalidaCLI());
		MonitorDePID.marcarFalloMonitor(MonitorDePID.CODIGO_FALLO_MONITOR);
		assertEquals(3, MonitorDePID.obtenerCodigoSalidaCLI());
		MonitorDePID.marcarFalloMonitor(MonitorDePID.CODIGO_INFORME_INVALIDO);
		assertEquals(4, MonitorDePID.obtenerCodigoSalidaCLI());

		MonitorDePID.marcarFalloMonitor(0);
		assertEquals(3, MonitorDePID.obtenerCodigoSalidaCLI());
		MonitorDePID.marcarFalloMonitor(97);
		assertEquals(3, MonitorDePID.obtenerCodigoSalidaCLI());
	}

	@Test
	void laFachadaCLIDelegaElEstadoAlMonitor() {
		MonitorDePID.restablecerCodigoSalidaCLI();
		assertEquals(MonitorDePID.CODIGO_EXITO, CrashDetectorCli.obtenerCodigoSalida());

		CrashDetectorCli.marcarFalloMonitor(CrashDetectorCli.CODIGO_INFORME_INVALIDO);
		assertEquals(4, MonitorDePID.obtenerCodigoSalidaCLI());
		assertEquals(4, CrashDetectorCli.obtenerCodigoSalida());

		CrashDetectorCli.marcarFalloMonitor();
		assertEquals(3, MonitorDePID.obtenerCodigoSalidaCLI());
	}

	@Test
	void unaNuevaEjecucionCLIRestableceUnFalloAnterior() {
		MonitorDePID.marcarFalloMonitor(MonitorDePID.CODIGO_FALLO_MONITOR);
		CrashDetectorCli.procesarArgumentos(new String[] { "--help" });
		assertEquals(MonitorDePID.CODIGO_EXITO, MonitorDePID.obtenerCodigoSalidaCLI());
	}

	@Test
	void monitorNoContieneReferenciaBinariaAClaseCLI() throws Exception {
		InputStream entrada = MonitorDePID.class.getResourceAsStream("MonitorDePID.class");
		if (entrada == null) {
			entrada = MonitorDePID.class.getResourceAsStream("/com/asbestosstar/crashdetector/MonitorDePID.class");
		}
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int leidos;
		try (InputStream flujo = entrada) {
			while ((leidos = flujo.read(buffer)) != -1) {
				bytes.write(buffer, 0, leidos);
			}
		}
		String constante = new String(bytes.toByteArray(), StandardCharsets.ISO_8859_1);
		assertFalse(constante.contains("com/asbestosstar/crashdetector/app/CrashDetectorCli"));
	}
}
