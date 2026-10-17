package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Analizador;
import com.asbestosstar.crashdetector.analizador.Criticalidad;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.apps.minecraft.ConflictoChunkyDistantHorizons;
import com.asbestosstar.crashdetector.idioma.Espanol;

@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.analizador.apps.minecraft.ConflictoChunkyDistantHorizons" },
		nota = "Cubre la firma completa del trazo, descartes, identidad y registro")
class PruebaConflictoChunkyDistantHorizons {

	private static final String EXCEPCION = "IllegalStateException: Chunky is not loaded.";
	private static final String PROVIDER = "org.popcraft.chunky.ChunkyProvider.get";
	private static final String ACCESSOR =
			"com.seibel.distanthorizons.forge.wrappers.modAccessor.ChunkyAccessor.bindOnGenerationProgressEvent";
	private static final String ABSTRACT =
			"com.seibel.distanthorizons.core.wrapperInterfaces.modAccessor.AbstractChunkyAccessor.tryRunFirstTimeSetup";

	private ConflictoChunkyDistantHorizons verificacion;

	@BeforeEach
	void preparar() {
		MonitorDePID.idioma = new Espanol();
		verificacion = new ConflictoChunkyDistantHorizons();
	}

	@Test
	void detectaLaFirmaCompletaEnUnaMismaTraza() throws Exception {
		verificacion.verificarPorLinea(null, EXCEPCION, 1);
		verificacion.verificarPorLinea(null, " at " + PROVIDER, 2);
		verificacion.verificarPorLinea(null, " at " + ACCESSOR, 3);
		verificacion.verificarPorLinea(null, " at " + ABSTRACT, 4);

		assertTrue(verificacion.activado());
		assertTrue(verificacion.mensaje().contains("Chunky"));
		assertTrue(verificacion.nombre().contains("Chunky"));
	}

	@Test
	void noSeActivaConFirmaIncompleta() throws Exception {
		verificacion.verificarPorLinea(null, EXCEPCION, 1);
		verificacion.verificarPorLinea(null, " at " + PROVIDER, 2);
		verificacion.verificarPorLinea(null, " at " + ACCESSOR, 3);

		assertFalse(verificacion.activado());
		assertEquals("", verificacion.mensaje());
	}

	@Test
	void noSeActivaConTextoAislado() throws Exception {
		verificacion.verificarPorLinea(null, "Chunky is installed and ready", 1);
		verificacion.verificarPorLinea(null, "Distant Horizons is enabled", 2);

		assertFalse(verificacion.activado());
		assertEquals("", verificacion.mensaje());
	}

	@Test
	void identidadPatronesYRegistro() {
		assertEquals("conflicto_chunky_distant_horizons", verificacion.id());
		assertEquals(1300.0f, verificacion.prioridad());
		assertEquals(Criticalidad.ERROR, verificacion.nivel_de_criticalidad());
		assertFalse(verificacion.anularNormal());
		assertNotSame(verificacion, verificacion.nueva());
		assertEquals(4, verificacion.patronesRapidos().length);
		assertTrue(Arrays.asList(verificacion.patronesRapidos()).contains(EXCEPCION));
		assertTrue(Arrays.asList(verificacion.patronesRapidos()).contains(PROVIDER));
		assertTrue(Arrays.asList(verificacion.patronesRapidos()).contains(ACCESSOR));
		assertTrue(Arrays.asList(verificacion.patronesRapidos()).contains(ABSTRACT));
		assertEquals(4, verificacion.ocupaTrazo().length);
		assertTrue(Arrays.asList(verificacion.ocupaTrazo()).contains(ACCESSOR));
		assertTrue(Analizador.verificaciones.stream()
				.anyMatch(v -> v instanceof ConflictoChunkyDistantHorizons));
	}

	private Consola consolaConTraza(String trazo) throws Exception {
		Path archivo = Files.createTempFile("chunky-dh-test", ".log");
		Consola consola = new Consola(archivo);
		consola.verificacion_de_stacktrace = new VerificacionDeStackTrace(consola);
		Constructor<VerificacionDeStackTrace.TraceInfo> constructor = VerificacionDeStackTrace.TraceInfo.class
				.getDeclaredConstructor(String.class, int.class, int.class, boolean.class);
		constructor.setAccessible(true);
		VerificacionDeStackTrace.TraceInfo info = constructor.newInstance(trazo, 1, 1, true);
		consola.verificacion_de_stacktrace.trazos_completos.add(info);
		Files.deleteIfExists(archivo);
		return consola;
	}
}
