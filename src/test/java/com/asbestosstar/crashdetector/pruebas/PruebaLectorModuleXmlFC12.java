package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.cargador.CargadorFeatureCreep;
import com.asbestosstar.crashdetector.cargador.LectorModuleXmlFC12;
import com.asbestosstar.crashdetector.cargador.LectorModuleXmlFC12.Descriptor;

/** Comprueba la lectura autónoma de module.xml de FeatureCreep 12. */
class PruebaLectorModuleXmlFC12 {

	private static final String CABECERA = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static final String NS = "xmlns=\"urn:jboss:module:1.9\"";

	@Test
	void leeCamposDelFixture() throws IOException {
		Descriptor d = leer(fixture()).orElseThrow(() -> new AssertionError("El fixture debería ser válido"));

		assertEquals("ejemplo.modfc12", d.getNombre());
		assertEquals("1.2.3", d.getVersion());
		assertEquals("ejemplo_modfc12", d.getIdMod());
		assertEquals("ejemplo.modfc12.Principal", d.getMainClass());
		assertEquals("ejemplo.mixins.json", d.getPropiedades().get("MixinConfigs"));
	}

	@Test
	void entrypointsDeclaradosEnOrdenDeDocumento() throws IOException {
		Descriptor d = leer(fixture()).orElseThrow(() -> new AssertionError("El fixture debería ser válido"));

		assertEquals(2, d.getEntrypoints().size());
		assertEquals("preLaunch", d.getEntrypoints().get(0).getTipo());
		assertEquals("ejemplo.modfc12.Temprano", d.getEntrypoints().get(0).getClase());
		assertEquals("java", d.getEntrypoints().get(0).getAdaptador());
		assertEquals("client", d.getEntrypoints().get(1).getTipo());
	}

	@Test
	void entrypointsDentroDeEntrypointsTambienSeLeen() {
		String xml = CABECERA + "<module " + NS + " name=\"x.mod\">"
				+ "<entrypoints><entrypoint type=\"server\" class=\"x.Servidor\" adapter=\"kotlin\"/></entrypoints>"
				+ "</module>";

		Descriptor d = leer(xml).orElseThrow(() -> new AssertionError("XML válido"));

		assertEquals(1, d.getEntrypoints().size());
		assertEquals("server", d.getEntrypoints().get(0).getTipo());
		assertEquals("kotlin", d.getEntrypoints().get(0).getAdaptador());
	}

	@Test
	void versionDeAtributoRaizTienePrioridadSobrePropiedad() {
		String xml = CABECERA + "<module " + NS + " name=\"x.mod\" version=\"2.0.0\">"
				+ "<properties><property name=\"Module-Version\" value=\"1.0\"/></properties></module>";

		assertEquals("2.0.0", leer(xml).orElseThrow(() -> new AssertionError("XML válido")).getVersion());
	}

	@Test
	void sinPropiedadesUsaElNombreComoIdentificador() {
		String xml = CABECERA + "<module " + NS + " name=\"solo.nombre\"/>";

		Descriptor d = leer(xml).orElseThrow(() -> new AssertionError("XML válido"));

		assertEquals("solo.nombre", d.getIdMod());
		assertEquals("", d.getVersion());
		assertEquals("", d.getMainClass());
	}

	@Test
	void mainClassDeEntrypointDeTipoMainCuandoNoHayElementoMainClass() {
		String xml = CABECERA + "<module " + NS + " name=\"x.mod\">"
				+ "<entrypoint type=\"main\" class=\"x.Main\" adapter=\"kotlin\"/></module>";

		Descriptor d = leer(xml).orElseThrow(() -> new AssertionError("XML válido"));

		assertEquals("x.Main", d.getMainClass());
		assertEquals("kotlin", d.getEntrypoints().get(0).getAdaptador());
	}

	@Test
	void elementoMainClassTienePrioridadSobreEntrypointMain() {
		String xml = CABECERA + "<module " + NS + " name=\"x.mod\">"
				+ "<main-class name=\"x.Principal\"/>"
				+ "<entrypoint type=\"main\" class=\"x.Otro\"/></module>";

		assertEquals("x.Principal", leer(xml).orElseThrow(() -> new AssertionError("XML válido")).getMainClass());
	}

	@Test
	void rechazaDoctypeYEntidadesExternas() {
		String xml = CABECERA + "<!DOCTYPE module [<!ENTITY x SYSTEM \"file:///etc/passwd\">]>"
				+ "<module " + NS + " name=\"&x;\"/>";

		assertFalse(leer(xml).isPresent());
	}

	@Test
	void xmlMalformadoDevuelveVacio() {
		assertFalse(leer(CABECERA + "<module " + NS + " name=").isPresent());
	}

	@Test
	void sinNombreDevuelveVacio() {
		assertFalse(leer(CABECERA + "<module " + NS + "/>").isPresent());
	}

	@Test
	void raizDistintaDeModuleDevuelveVacio() {
		assertFalse(leer(CABECERA + "<otra name=\"x\"/>").isPresent());
	}

	@Test
	void reconoceSoloRutasDescriptorValidas() {
		assertTrue(LectorModuleXmlFC12.esRutaDescriptor("module.xml"));
		assertTrue(LectorModuleXmlFC12.esRutaDescriptor("/module.xml"));
		assertTrue(LectorModuleXmlFC12.esRutaDescriptor("modules/module.xml"));
		assertTrue(LectorModuleXmlFC12.esRutaDescriptor("modules\\module.xml"));

		assertFalse(LectorModuleXmlFC12.esRutaDescriptor("modules.xml"));
		assertFalse(LectorModuleXmlFC12.esRutaDescriptor("lib/module.xml"));
		assertFalse(LectorModuleXmlFC12.esRutaDescriptor("x/module.xml"));
		assertFalse(LectorModuleXmlFC12.esRutaDescriptor("module.xml.bak"));
		assertFalse(LectorModuleXmlFC12.esRutaDescriptor(null));
	}

	@Test
	void conDescriptorLaMainClassDelManifiestoSeIgnora() {
		Optional<Descriptor> conSinMain = leer(CABECERA + "<module " + NS + " name=\"x.mod\"/>");

		assertEquals("", LectorModuleXmlFC12.resolverMainClass(conSinMain, "manifiesto.Main"));
	}

	@Test
	void conDescriptorGanaSuMainClass() throws IOException {
		Optional<Descriptor> conMain = leer(fixture());

		assertEquals("ejemplo.modfc12.Principal", LectorModuleXmlFC12.resolverMainClass(conMain, "manifiesto.Main"));
	}

	@Test
	void sinDescriptorUsaLaMainClassDelManifiesto() {
		assertEquals("manifiesto.Main", LectorModuleXmlFC12.resolverMainClass(Optional.empty(), " manifiesto.Main "));
		assertEquals("", LectorModuleXmlFC12.resolverMainClass(Optional.empty(), null));
	}

	@Test
	void nombresYVersionDelDescriptorSeUsanEnCargador() throws IOException {
		byte[] contenido = fixture();

		assertEquals(Arrays.asList("ejemplo_modfc12", "ejemplo.modfc12"),
				CargadorFeatureCreep.parsearNombresDescriptorFC12(contenido));
		assertEquals("1.2.3", CargadorFeatureCreep.parsearVersionDescriptorFC12(contenido));
	}

	@Test
	void sinIdModNoRepiteElNombre() throws IOException {
		byte[] contenido = (CABECERA + "<module " + NS + " name=\"solo.nombre\"/>")
				.getBytes(StandardCharsets.UTF_8);

		List<String> nombres = CargadorFeatureCreep.parsearNombresDescriptorFC12(contenido);

		assertEquals(Collections.singletonList("solo.nombre"), nombres);
	}

	@Test
	void modulesXmlLegadoSigueSiendoLeido() throws IOException {
		byte[] legado = "<module xmlns=\"urn:jboss:module:1.1\" name=\"legacy.mod\" slot=\"2.0\"/>"
				.getBytes(StandardCharsets.UTF_8);

		assertEquals(Collections.singletonList("legacy.mod"), CargadorFeatureCreep.parsearNombreModuloJBoss(legado));
		assertEquals("2.0", CargadorFeatureCreep.parsearVersionModuloJBoss(legado));
	}

	@Test
	void descriptorMalformadoCaeAlParserHeredado() throws IOException {
		byte[] roto = "<module name=\"roto.mod\" <<<".getBytes(StandardCharsets.UTF_8);

		assertEquals(Collections.singletonList("roto.mod"), CargadorFeatureCreep.parsearNombresDescriptorFC12(roto));
	}

	private static Optional<Descriptor> leer(String xml) {
		return LectorModuleXmlFC12.leer(xml.getBytes(StandardCharsets.UTF_8));
	}

	private static Optional<Descriptor> leer(byte[] contenido) {
		return LectorModuleXmlFC12.leer(contenido);
	}

	private static byte[] fixture() throws IOException {
		try (InputStream in = PruebaLectorModuleXmlFC12.class.getClassLoader()
				.getResourceAsStream("modules/module.xml")) {
			assertNotNull(in, "Falta el fixture modules/module.xml en src/test/resources");
			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int leidos;
			while ((leidos = in.read(buffer)) != -1) {
				salida.write(buffer, 0, leidos);
			}
			return salida.toByteArray();
		}
	}
}
