package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.asbestosstar.crashdetector.buscar.ModCarpeta;
import com.asbestosstar.crashdetector.buscar.ModPKZip;

/** Comprueba que ModCarpeta y ModPKZip reconocen module.xml FC12 sin crear sub-mods falsos. */
class PruebaDeteccionModuleXmlFC12 {

	@TempDir
	Path carpeta;

	@Test
	void moduleXmlEnRaizDefineElModSinSubMods() throws IOException {
		Files.write(carpeta.resolve("module.xml"), fixture());

		ModCarpeta mod = new ModCarpeta("raiz", null, carpeta);

		assertTrue(mod.mods_en_mods().isEmpty());
		assertTrue(mod.nombre().contains("ejemplo_modfc12"));
		assertTrue(mod.nombre().contains("ejemplo.modfc12"));
		assertEquals("1.2.3", mod.version());
	}

	@Test
	void moduleXmlEnModulesNoCreaSubMod() throws IOException {
		Path modules = Files.createDirectories(carpeta.resolve("modules"));
		Files.write(modules.resolve("module.xml"), fixture());

		ModCarpeta mod = new ModCarpeta("raiz", null, carpeta);

		assertTrue(mod.mods_en_mods().isEmpty());
		assertTrue(mod.nombre().contains("ejemplo_modfc12"));
	}

	@Test
	void moduleXmlEnSubcarpetaDefineUnSubMod() throws IOException {
		Path lib = Files.createDirectories(carpeta.resolve("lib"));
		Files.write(lib.resolve("module.xml"), fixture());

		ModCarpeta mod = new ModCarpeta("raiz", null, carpeta);

		assertEquals(1, mod.mods_en_mods().size());
		assertTrue(mod.mods_en_mods().get(0).nombre().contains("ejemplo_modfc12"));
	}

	@Test
	void jarConModulesXmlSeLeeSinSubMods() throws IOException {
		ModPKZip mod = new ModPKZip("contenedor.jar", null, jarConModulesXml());

		assertTrue(mod.mods_en_mods().isEmpty());
		assertTrue(mod.nombre().contains("ejemplo_modfc12"));
		assertTrue(mod.nombre().contains("ejemplo.modfc12"));
		assertEquals("1.2.3", mod.version());
	}

	private static byte[] jarConModulesXml() throws IOException {
		Manifest manifiesto = new Manifest();
		manifiesto.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		ByteArrayOutputStream salida = new ByteArrayOutputStream();
		try (JarOutputStream jar = new JarOutputStream(salida, manifiesto)) {
			jar.putNextEntry(new JarEntry("modules/module.xml"));
			jar.write(fixture());
			jar.closeEntry();
		}
		return salida.toByteArray();
	}

	private static byte[] fixture() throws IOException {
		try (InputStream in = PruebaDeteccionModuleXmlFC12.class.getClassLoader()
				.getResourceAsStream("modules/module.xml")) {
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
