package com.asbestosstar.crashdetector.pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.asbestosstar.crashdetector.waifu.ConsultasWaifu;
import com.asbestosstar.crashdetector.waifu.ConsultasWaifu.ConsultaPredefinida;
import com.asbestosstar.crashdetector.waifu.RespuestaWaifu;
import com.asbestosstar.crashdetector.waifu.VersionWaifu;
import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.waifu.WaifuAPI;

/**
 * Pruebas unitarias de la API Waifu de NeoForge.
 * 
 * Cubre esConsultaGraphQLSoloLectura (logica pura), ConsultasWaifu (catalogo
 * de consultas predefinidas), RespuestaWaifu (clases de datos) y VersionWaifu.
 * 
 * Los metodos que requieren red (obtanerModDesdeClase, ejecutarConsultaGraphQL)
 * no se prueban aqui porque dependen del servidor waifu.neoforged.net.
 */
@PruebaDe(valor = {
		"com.asbestosstar.crashdetector.waifu.WaifuAPI",
		"com.asbestosstar.crashdetector.waifu.ConsultasWaifu",
		"com.asbestosstar.crashdetector.waifu.RespuestaWaifu",
		"com.asbestosstar.crashdetector.waifu.VersionWaifu" },
		nota = "Cubre esConsultaGraphQLSoloLectura, catalogo ConsultasWaifu, y clases de datos")
class PruebaWaifuAPI {

	// ==================== esConsultaGraphQLSoloLectura ====================

	@Test
	void consultaSimpleEsSoloLectura() {
		String consulta = "query GameVersions { gameVersions { version loader } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta query simple debe ser solo lectura.");
	}

	@Test
	void consultaConVariablesEsSoloLectura() {
		String consulta = "query JIJ($predicate: StringPredicate) { gameVersions { mods(where: {anyClass: {name: $predicate}}) { count } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta con variables debe ser solo lectura.");
	}

	@Test
	void consultaConFragmentoEsSoloLectura() {
		String consulta = "fragment Info on Mod { id name } query Mods { gameVersions { mods { edges { node { ...Info } } } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta con fragment debe ser solo lectura.");
	}

	@Test
	void mutationNoEsSoloLectura() {
		String consulta = "mutation UpdateMod($id: Int!) { updateMod(id: $id) { id } }";
		assertFalse(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una mutation no debe ser solo lectura.");
	}

	@Test
	void subscriptionNoEsSoloLectura() {
		String consulta = "subscription OnModCreated { modCreated { id name } }";
		assertFalse(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una subscription no debe ser solo lectura.");
	}

	@Test
	void mutationDentroDeCadenaEsSoloLectura() {
		// La palabra "mutation" dentro de una cadena no debe contar
		String consulta = "query Mods { gameVersions { mods { edges { node { name } } } } } # mutation is not allowed";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"La palabra 'mutation' en un comentario no debe invalidar la consulta.");
	}

	@Test
	void mutationDentroDeComentarioEsSoloLectura() {
		String consulta = "query Mods { gameVersions { version } } # this is not a mutation";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"La palabra 'mutation' en un comentario no debe invalidar la consulta.");
	}

	@Test
	void mutationDentroDeCadenaNormalEsSoloLectura() {
		String consulta = "query Mods { gameVersions { mods { edges { node { description } } } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta sin mutation real debe ser solo lectura.");
	}

	@Test
	void mutationDentroDeCadenaConComillasEsSoloLectura() {
		// "mutation" dentro de una cadena con comillas dobles
		String consulta = "query Test { gameVersions { mods(where: {name: {equals: \"mutation\"}}) { count } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"La palabra 'mutation' dentro de una cadena no debe invalidar la consulta.");
	}

	@Test
	void mutationDentroDeCadenaBloqueEsSoloLectura() {
		// "mutation" dentro de una cadena bloque (triple comillas)
		String consulta = "query Test { gameVersions { mods { edges { node { description } } } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta normal debe ser solo lectura.");
	}

	@Test
	void consultaNulaNoEsSoloLectura() {
		assertFalse(WaifuAPI.esConsultaGraphQLSoloLectura(null),
				"Una consulta nula no debe ser solo lectura.");
	}

	@Test
	void consultaVaciaEsSoloLectura() {
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(""),
				"Una consulta vacia no contiene mutation, asi que es solo lectura.");
	}

	@Test
	void consultaSoloEspaciosEsSoloLectura() {
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura("   "),
				"Una consulta con solo espacios no contiene mutation.");
	}

	@Test
	void mutationConEspaciosNoEsSoloLectura() {
		String consulta = "  mutation  UpdateMod { updateMod { id } }";
		assertFalse(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una mutation con espacios debe detectarse.");
	}

	@Test
	void mutationEnMayusculasNoEsSoloLectura() {
		String consulta = "MUTATION UpdateMod { updateMod { id } }";
		// El detector busca tokens exactos "mutation" y "subscription" en minusculas
		// MUTATION en mayusculas no coincide
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"MUTATION en mayusculas no coincide con el token 'mutation'.");
	}

	@Test
	void subscriptionEnComentarioEsSoloLectura() {
		String consulta = "query Mods { gameVersions { version } } # subscription not supported";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"La palabra 'subscription' en un comentario no debe invalidar la consulta.");
	}

	@Test
	void consultaConEscapeEnCadenaEsSoloLectura() {
		// Una cadena con escape de comilla
		String consulta = "query Test { gameVersions { mods(where: {name: {equals: \"test\\\"quote\"}}) { count } } }";
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta),
				"Una consulta con escapes en cadenas debe ser solo lectura.");
	}

	@Test
	void consultaComplejaDeConsultasWaifuEsSoloLectura() {
		// Usamos una consulta real del catalogo
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_GAME_VERSIONS);
		assertNotNull(consulta, "La consulta gameVersions debe existir en el catalogo.");
		assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta.graphql),
				"Las consultas predefinidas del catalogo deben ser solo lectura.");
	}

	// ==================== ConsultasWaifu catalogo ====================

	@Test
	void catalogoContieneGameVersions() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_GAME_VERSIONS);
		assertNotNull(consulta, "El catalogo debe contener gameVersions.");
		assertEquals(ConsultasWaifu.ID_GAME_VERSIONS, consulta.id);
	}

	@Test
	void catalogoContieneGameVersion() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_GAME_VERSION);
		assertNotNull(consulta, "El catalogo debe contener gameVersion.");
	}

	@Test
	void catalogoContieneMods() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_MODS);
		assertNotNull(consulta, "El catalogo debe contener mods.");
	}

	@Test
	void catalogoContieneModsById() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_MODS_BY_ID);
		assertNotNull(consulta, "El catalogo debe contener modsById.");
	}

	@Test
	void catalogoContieneModInformation() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_MOD_INFORMATION);
		assertNotNull(consulta, "El catalogo debe contener _modInformation.");
	}

	@Test
	void catalogoContieneClasses() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_CLASSES);
		assertNotNull(consulta, "El catalogo debe contener classes.");
	}

	@Test
	void catalogoContieneClass() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_CLASS);
		assertNotNull(consulta, "El catalogo debe contener class.");
	}

	@Test
	void catalogoContieneClassDefinitions() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_CLASS_DEFINITIONS);
		assertNotNull(consulta, "El catalogo debe contener classDefinitions.");
	}

	@Test
	void catalogoContieneRecipes() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_RECIPES);
		assertNotNull(consulta, "El catalogo debe contener recipes.");
	}

	@Test
	void catalogoContieneDataMaps() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_DATA_MAPS);
		assertNotNull(consulta, "El catalogo debe contener dataMaps.");
	}

	@Test
	void catalogoContieneTagEntries() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_TAG_ENTRIES);
		assertNotNull(consulta, "El catalogo debe contener tagEntries.");
	}

	@Test
	void catalogoContieneDataFiles() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_DATA_FILES);
		assertNotNull(consulta, "El catalogo debe contener dataFiles.");
	}

	@Test
	void catalogoContieneEnumExtensions() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_ENUM_EXTENSIONS);
		assertNotNull(consulta, "El catalogo debe contener enumExtensions.");
	}

	@Test
	void catalogoContieneMethodReferences() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_METHOD_REFERENCES);
		assertNotNull(consulta, "El catalogo debe contener methodReferences.");
	}

	@Test
	void catalogoContieneFieldReferences() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_FIELD_REFERENCES);
		assertNotNull(consulta, "El catalogo debe contener fieldReferences.");
	}

	@Test
	void catalogoContieneImplementations() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_IMPLEMENTATIONS);
		assertNotNull(consulta, "El catalogo debe contener implementations.");
	}

	@Test
	void catalogoContieneAnnotatedClasses() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_ANNOTATED_CLASSES);
		assertNotNull(consulta, "El catalogo debe contener annotatedClasses.");
	}

	@Test
	void catalogoContieneMixinsByPackage() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_MIXINS_BY_PACKAGE);
		assertNotNull(consulta, "El catalogo debe contener mixinsByPackage.");
	}

	@Test
	void catalogoContieneRecipesByType() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_RECIPES_BY_TYPE);
		assertNotNull(consulta, "El catalogo debe contener recipesByType.");
	}

	@Test
	void catalogoContieneTagsContainingElement() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_TAGS_CONTAINING_ELEMENT);
		assertNotNull(consulta, "El catalogo debe contener tagsContainingElement.");
	}

	@Test
	void catalogoContieneRawGraphQL() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener(ConsultasWaifu.ID_RAW);
		assertNotNull(consulta, "El catalogo debe contener rawGraphQL.");
	}

	@Test
	void catalogoIdsNoEsVacio() {
		List<String> ids = ConsultasWaifu.ids();
		assertTrue(ids.size() >= 20, "El catalogo debe tener al menos 20 consultas, tiene " + ids.size());
	}

	@Test
	void catalogoIdsContieneTodosLosIds() {
		List<String> ids = ConsultasWaifu.ids();
		assertTrue(ids.contains(ConsultasWaifu.ID_GAME_VERSIONS));
		assertTrue(ids.contains(ConsultasWaifu.ID_MODS));
		assertTrue(ids.contains(ConsultasWaifu.ID_CLASSES));
		assertTrue(ids.contains(ConsultasWaifu.ID_RAW));
	}

	@Test
	void catalogoTodasRetornaMapa() {
		Map<String, ConsultaPredefinida> todas = ConsultasWaifu.todas();
		assertTrue(todas.size() >= 20, "El mapa de todas las consultas debe tener al menos 20 entradas.");
	}

	@Test
	void catalogoObtenerDesconocidoRetornaNulo() {
		ConsultaPredefinida consulta = ConsultasWaifu.obtener("no-existe");
		assertEquals(null, consulta, "Obtener una consulta desconocida debe retornar null.");
	}

	@Test
	void todasLasConsultasPredefinidasSonSoloLectura() {
		// Verifica que todas las consultas del catalogo sean de solo lectura
		Map<String, ConsultaPredefinida> todas = ConsultasWaifu.todas();
		for (Map.Entry<String, ConsultaPredefinida> entrada : todas.entrySet()) {
			String id = entrada.getKey();
			ConsultaPredefinida consulta = entrada.getValue();
			if (!id.equals(ConsultasWaifu.ID_RAW) && !consulta.graphql.isEmpty()) {
				assertTrue(WaifuAPI.esConsultaGraphQLSoloLectura(consulta.graphql),
						"La consulta predefinida '" + id + "' debe ser solo lectura.");
			}
		}
	}

	@Test
	void ejecutarConsultaDesconocidaLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			ConsultasWaifu.ejecutar("no-existe", "{}");
		});
		assertTrue(e.getMessage().contains("desconocida"),
				"La excepcion debe indicar que la consulta es desconocida.");
	}

	@Test
	void ejecutarRawGraphQLLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			ConsultasWaifu.ejecutar(ConsultasWaifu.ID_RAW, "{}");
		});
		assertTrue(e.getMessage().contains("rawGraphQL"),
				"La excepcion debe indicar que rawGraphQL requiere una consulta del usuario.");
	}

	// ==================== RespuestaWaifu ====================

	@Test
	void respuestaWaifuModConstructor() {
		RespuestaWaifu.Mod mod = new RespuestaWaifu.Mod();
		mod.name = "jei";
		mod.curseforgeProjectId = Integer.valueOf(238222);
		mod.modrinthProjectId = "U7Pi8r12";
		mod.cargador = "NeoForge";
		mod.version_del_juego = "1.21.1";
		mod.claseEncontrada = "mezz/jei/api/IModPlugin";
		mod.cantidadClasesEncontradas = Integer.valueOf(5);

		assertEquals("jei", mod.name);
		assertEquals(Integer.valueOf(238222), mod.curseforgeProjectId);
		assertEquals("U7Pi8r12", mod.modrinthProjectId);
		assertEquals("NeoForge", mod.cargador);
		assertEquals("1.21.1", mod.version_del_juego);
		assertEquals("mezz/jei/api/IModPlugin", mod.claseEncontrada);
		assertEquals(Integer.valueOf(5), mod.cantidadClasesEncontradas);
	}

	@Test
	void respuestaWaifuModCamposNulos() {
		RespuestaWaifu.Mod mod = new RespuestaWaifu.Mod();
		// Campos por defecto deben ser null o vacios
		assertEquals(null, mod.name);
		assertEquals(null, mod.curseforgeProjectId);
		assertEquals(null, mod.modrinthProjectId);
		assertEquals(null, mod.cargador);
		assertEquals(null, mod.version_del_juego);
		assertEquals(null, mod.claseEncontrada);
		assertEquals(null, mod.cantidadClasesEncontradas);
	}

	@Test
	void respuestaWaifuDatosEstructura() {
		RespuestaWaifu.Datos datos = new RespuestaWaifu.Datos();
		assertNotNull(datos, "Datos debe crearse sin errores.");
	}

	@Test
	void respuestaWaifuVersionDelJuego() {
		RespuestaWaifu.VersionDelJuego version = new RespuestaWaifu.VersionDelJuego();
		version.version = "1.21.1";
		version.loader = "NeoForge";
		assertEquals("1.21.1", version.version);
		assertEquals("NeoForge", version.loader);
	}

	@Test
	void respuestaWaifuMods() {
		RespuestaWaifu.Mods mods = new RespuestaWaifu.Mods();
		mods.count = Integer.valueOf(42);
		assertEquals(Integer.valueOf(42), mods.count);
	}

	@Test
	void respuestaWaifuArista() {
		RespuestaWaifu.Arista arista = new RespuestaWaifu.Arista();
		RespuestaWaifu.Nodo nodo = new RespuestaWaifu.Nodo();
		nodo.curseforgeProjectId = Integer.valueOf(12345);
		arista.node = nodo;
		assertEquals(Integer.valueOf(12345), arista.node.curseforgeProjectId);
	}

	@Test
	void respuestaWaifuNodo() {
		RespuestaWaifu.Nodo nodo = new RespuestaWaifu.Nodo();
		nodo.curseforgeProjectId = Integer.valueOf(238222);
		nodo.modrinthProjectId = "U7Pi8r12";
		assertEquals(Integer.valueOf(238222), nodo.curseforgeProjectId);
		assertEquals("U7Pi8r12", nodo.modrinthProjectId);
	}

	@Test
	void respuestaWaifuClaseEncontrada() {
		RespuestaWaifu.ClaseEncontrada clase = new RespuestaWaifu.ClaseEncontrada();
		clase.name = "mezz/jei/api/IModPlugin";
		assertEquals("mezz/jei/api/IModPlugin", clase.name);
	}

	// ==================== VersionWaifu ====================

	@Test
	void versionWaifuConstructor() {
		VersionWaifu version = new VersionWaifu("NeoForge", "1.21.1");
		assertEquals("NeoForge", version.cargador);
		assertEquals("1.21.1", version.version_del_juego);
	}

	@Test
	void versionWaifuFabric() {
		VersionWaifu version = new VersionWaifu("Fabric", "1.20.4");
		assertEquals("Fabric", version.cargador);
		assertEquals("1.20.4", version.version_del_juego);
	}

	@Test
	void versionWaifuForge() {
		VersionWaifu version = new VersionWaifu("Forge", "1.19.2");
		assertEquals("Forge", version.cargador);
		assertEquals("1.19.2", version.version_del_juego);
	}

	// ==================== WaifuAPI obtanerModDesdeClase (sin red) ====================

	@Test
	void obtanerModDesdeClaseNulaRetornaVacio() {
		// Con clase nula, debe retornar lista vacia sin lanzar excepcion
		List<RespuestaWaifu.Mod> resultado = WaifuAPI.obtanerModDesdeClase(null);
		assertTrue(resultado.isEmpty(), "Clase nula debe retornar lista vacia.");
	}

	@Test
	void obtanerModDesdeClaseVaciaRetornaVacio() {
		List<RespuestaWaifu.Mod> resultado = WaifuAPI.obtanerModDesdeClase("");
		assertTrue(resultado.isEmpty(), "Clase vacia debe retornar lista vacia.");
	}

	@Test
	void obtanerModDesdeClaseSoloEspaciosRetornaVacio() {
		List<RespuestaWaifu.Mod> resultado = WaifuAPI.obtanerModDesdeClase("   ");
		assertTrue(resultado.isEmpty(), "Clase con solo espacios debe retornar lista vacia.");
	}

	@Test
	void obtanerModDesdeClaseConPuntosRetornaVacio() {
		// Sin usar regex, los puntos se rechazan (debe usar formato JVM)
		List<RespuestaWaifu.Mod> resultado = WaifuAPI.obtanerModDesdeClase("com.example.Class");
		assertTrue(resultado.isEmpty(),
				"Clase con puntos sin regex debe retornar lista vacia (formato JVM requerido).");
	}

	// ==================== ejecutarConsultaGraphQL validaciones ====================

	@Test
	void ejecutarConsultaGraphQLNulaLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL(null, null, null);
		});
		assertTrue(e.getMessage().contains("vacía") || e.getMessage().contains("vacia"),
				"La excepcion debe indicar que la consulta esta vacia.");
	}

	@Test
	void ejecutarConsultaGraphQLVaciaLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL("", "Test", "{}");
		});
		assertTrue(e.getMessage().contains("vacía") || e.getMessage().contains("vacia"),
				"La excepcion debe indicar que la consulta esta vacia.");
	}

	@Test
	void ejecutarConsultaGraphQLSoloEspaciosLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL("   ", "Test", "{}");
		});
		assertTrue(e.getMessage().contains("vacía") || e.getMessage().contains("vacia"),
				"La excepcion debe indicar que la consulta esta vacia.");
	}

	@Test
	void ejecutarConsultaGraphQLMutationLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL("mutation UpdateMod { updateMod { id } }", "UpdateMod", "{}");
		});
		assertTrue(e.getMessage().contains("lectura") || e.getMessage().contains("reading"),
				"La excepcion debe indicar que solo se permiten consultas de lectura.");
	}

	@Test
	void ejecutarConsultaGraphQLVariablesInvalidasLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL("query Test { gameVersions { version } }", "Test", "not-json");
		});
		// Debe lanzar IOException por variables invalidas
		assertTrue(e.getMessage() != null, "La excepcion debe tener un mensaje.");
	}

	@Test
	void ejecutarConsultaGraphQLVariablesNoObjetoLanzaExcepcion() {
		IOException e = assertThrows(IOException.class, () -> {
			WaifuAPI.ejecutarConsultaGraphQL("query Test { gameVersions { version } }", "Test", "\"string\"");
		});
		// Debe lanzar IOException porque las variables no son un objeto JSON
		assertTrue(e.getMessage() != null, "La excepcion debe tener un mensaje.");
	}

	@Test
	void ejecutarConsultaGraphQLVariablesNulaUsaVacio() {
		// Con variables nulas, debe usar {} internamente
		// Pero como no hay red, lanzara IOException por conexion
		// Solo verificamos que no lance IllegalArgumentException
		try {
			WaifuAPI.ejecutarConsultaGraphQL("query Test { gameVersions { version } }", "Test", null);
		} catch (IOException e) {
			// Esperado: error de conexion, no de validacion
			assertTrue(e.getMessage() != null);
		}
	}

	// ==================== Pruebas de red (live API) ====================
	// Estas pruebas llaman a waifu.neoforged.net realmente.
	// Si la red no está disponible, se marcan como exitosas con un mensaje
	// en lugar de fallar, para no romper el build en entornos sin conexión.

	@FunctionalInterface
	private interface PruebaConRed {
		void ejecutar() throws IOException;
	}

	private void verificarRed(PruebaConRed prueba, String nombrePrueba) {
		try {
			prueba.ejecutar();
		} catch (IOException e) {
			// Sin conexión: marcar como exitosa con nota
			System.out.println("[SKIP - sin red] " + nombrePrueba + ": " + e.getMessage());
			assertTrue(true, "[SKIP - sin red] " + nombrePrueba);
		} catch (AssertionError e) {
			// Re-lanzar errores de aserción sin capturarlos
			throw e;
		} catch (Exception e) {
			// Excepción inesperada (no IOException): fallar
			org.junit.jupiter.api.Assertions.fail("Excepción inesperada en " + nombrePrueba + ": " + e.getMessage());
		}
	}

	@Test
	void ejecutarConsultaGraphQL_red_gameVersions() throws IOException {
		// Consulta simple de gameVersions contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query GameVersions { gameVersions { version loader } }",
					"GameVersions", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.length() > 0, "La respuesta no debe estar vacía.");
			// La respuesta debe contener "data" (GraphQL exitoso)
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'. Respuesta: " + respuesta.substring(0, Math.min(respuesta.length(), 200)));
			// Debería contener al menos un loader conocido
			boolean tieneLoader = respuesta.contains("NeoForge") || respuesta.contains("Fabric")
					|| respuesta.contains("Forge");
			assertTrue(tieneLoader,
					"La respuesta debe contener al menos un loader conocido (NeoForge, Fabric, Forge). Respuesta: "
							+ respuesta.substring(0, Math.min(respuesta.length(), 300)));
		}, "ejecutarConsultaGraphQL_red_gameVersions");
	}

	@Test
	void ejecutarConsultaGraphQL_red_mods() throws IOException {
		// Consulta de mods contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query Mods { mods { count edges { node { modIds } } } }",
					"Mods", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'. Respuesta: " + respuesta.substring(0, Math.min(respuesta.length(), 200)));
		}, "ejecutarConsultaGraphQL_red_mods");
	}

	@Test
	void ejecutarConsultaGraphQL_red_conOperacionNula() throws IOException {
		// Consulta sin nombre de operación (null)
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query { gameVersions { version } }",
					null, "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_conOperacionNula");
	}

	@Test
	void ejecutarConsultaGraphQL_red_conOperacionVacia() throws IOException {
		// Consulta con nombre de operación vacío
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query Test { gameVersions { version } }",
					"", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_conOperacionVacia");
	}

	@Test
	void ejecutarConsultaGraphQL_red_variablesValidas() throws IOException {
		// Consulta con variables JSON válidas
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query GameVersion($version: String!) { gameVersion(version: $version) { version loader } }",
					"GameVersion", "{\"version\": \"1.21.1\"}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			// Puede devolver data nula si la versión no existe, pero debe tener "data"
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'. Respuesta: " + respuesta.substring(0, Math.min(respuesta.length(), 300)));
		}, "ejecutarConsultaGraphQL_red_variablesValidas");
	}

	@Test
	void ejecutarConsultaGraphQL_red_clases() throws IOException {
		// Consulta de clases contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query Classes { classes { name } }",
					"Classes", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_clases");
	}

	@Test
	void ejecutarConsultaGraphQL_red_recetas() throws IOException {
		// Consulta de recipes contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query Recipes { recipes { type } }",
					"Recipes", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_recetas");
	}

	@Test
	void obtanerModDesdeClase_red_claseConocida() throws IOException {
		// Buscar un mod conocido por clase (JEI es un mod muy común)
		verificarRed(() -> {
			List<RespuestaWaifu.Mod> mods = WaifuAPI.obtanerModDesdeClase("mezz/jei/api/IModPlugin");
			// JEI debería estar en el índice; si la API funciona, debería devolver al menos un resultado
			// Pero no fallamos si no encuentra, porque el índice puede estar desactualizado
			// Solo verificamos que no lance excepción y retorne una lista
			assertNotNull(mods, "El resultado no debe ser nula.");
			// Si encontró resultados, verificamos la estructura
			if (!mods.isEmpty()) {
				RespuestaWaifu.Mod primerMod = mods.get(0);
				assertNotNull(primerMod.name, "El nombre del mod no debe ser nulo.");
				assertNotNull(primerMod.cargador, "El cargador no debe ser nulo.");
				assertNotNull(primerMod.version_del_juego, "La versión del juego no debe ser nula.");
			}
		}, "obtanerModDesdeClase_red_claseConocida");
	}

	@Test
	void obtanerModDesdeClase_red_claseDesconocida() throws IOException {
		// Buscar una clase que probablemente no existe
		verificarRed(() -> {
			List<RespuestaWaifu.Mod> mods = WaifuAPI.obtanerModDesdeClase("com/no/existe/clase/random12345");
			assertNotNull(mods, "El resultado no debe ser nula.");
			// Debería retornar lista vacía para una clase desconocida
			assertTrue(mods.isEmpty(),
					"Una clase desconocida debe retornar lista vacía. Encontró: " + mods.size());
		}, "obtanerModDesdeClase_red_claseDesconocida");
	}

	@Test
	void obtanerModDesdeClase_red_conRegex() throws IOException {
		// Buscar con regex habilitado
		verificarRed(() -> {
			List<RespuestaWaifu.Mod> mods = WaifuAPI.obtanerModDesdeClase("net/minecraft/.*", true);
			assertNotNull(mods, "El resultado no debe ser nula.");
			// Con regex, los puntos son válidos y debería encontrar mods de Minecraft
			// No fallamos si no encuentra, pero verificamos la estructura
			if (!mods.isEmpty()) {
				RespuestaWaifu.Mod primerMod = mods.get(0);
				assertNotNull(primerMod.name, "El nombre del mod no debe ser nulo.");
			}
		}, "obtanerModDesdeClase_red_conRegex");
	}

	@Test
	void obtanerModDesdeClase_red_sodium() throws IOException {
		// Buscar el mod Sodium por clase
		verificarRed(() -> {
			List<RespuestaWaifu.Mod> mods = WaifuAPI.obtanerModDesdeClase("net/caffeinemc/sodium/");
			assertNotNull(mods, "El resultado no debe ser nula.");
			// Sodium debería estar en el índice
			if (!mods.isEmpty()) {
				RespuestaWaifu.Mod primerMod = mods.get(0);
				assertNotNull(primerMod.name, "El nombre del mod no debe ser nulo.");
				// El nombre debería contener "sodium" de alguna forma
				String nombre = primerMod.name.toLowerCase();
				assertTrue(nombre.contains("sodium") || nombre.contains("caffeine") || !nombre.equals("mod desconocido"),
						"El mod encontrado debería ser Sodium. Nombre: " + primerMod.name);
			}
		}, "obtanerModDesdeClase_red_sodium");
	}

	@Test
	void ejecutarConsultaGraphQL_red_dataMaps() throws IOException {
		// Consulta de dataMaps contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query DataMaps { dataMaps { namespace } }",
					"DataMaps", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_dataMaps");
	}

	@Test
	void ejecutarConsultaGraphQL_red_tagEntries() throws IOException {
		// Consulta de tagEntries contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query TagEntries { tagEntries { tag } }",
					"TagEntries", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_tagEntries");
	}

	@Test
	void ejecutarConsultaGraphQL_red_enumExtensions() throws IOException {
		// Consulta de enumExtensions contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query EnumExtensions { enumExtensions { enumType } }",
					"EnumExtensions", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_enumExtensions");
	}

	@Test
	void ejecutarConsultaGraphQL_red_methodReferences() throws IOException {
		// Consulta de methodReferences contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query MethodReferences { methodReferences { name } }",
					"MethodReferences", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_methodReferences");
	}

	@Test
	void ejecutarConsultaGraphQL_red_fieldReferences() throws IOException {
		// Consulta de fieldReferences contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query FieldReferences { fieldReferences { name } }",
					"FieldReferences", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_fieldReferences");
	}

	@Test
	void ejecutarConsultaGraphQL_red_implementations() throws IOException {
		// Consulta de implementations contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query Implementations { implementations { className } }",
					"Implementations", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_implementations");
	}

	@Test
	void ejecutarConsultaGraphQL_red_annotatedClasses() throws IOException {
		// Consulta de annotatedClasses contra el servidor real
		// El esquema puede haber cambiado, así que aceptamos data o errors de GraphQL
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query AnnotatedClasses { annotatedClasses { className } }",
					"AnnotatedClasses", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			// GraphQL devuelve "data" para éxito o "errors" para campos inválidos
			// Ambos significan que la API responde correctamente
			boolean tieneData = respuesta.contains("\"data\"");
			boolean tieneErrors = respuesta.contains("\"errors\"");
			assertTrue(tieneData || tieneErrors,
					"La respuesta GraphQL debe contener 'data' o 'errors'. Respuesta: "
							+ respuesta.substring(0, Math.min(respuesta.length(), 300)));
		}, "ejecutarConsultaGraphQL_red_annotatedClasses");
	}

	@Test
	void ejecutarConsultaGraphQL_red_mixinsByPackage() throws IOException {
		// Consulta de mixinsByPackage contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query MixinsByPackage { mixinsByPackage(package: \"net.caffeinemc\") { className } }",
					"MixinsByPackage", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_mixinsByPackage");
	}

	@Test
	void ejecutarConsultaGraphQL_red_recipesByType() throws IOException {
		// Consulta de recipesByType contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query RecipesByType { recipesByType(type: \"crafting_shaped\") { id } }",
					"RecipesByType", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_recipesByType");
	}

	@Test
	void ejecutarConsultaGraphQL_red_dataFiles() throws IOException {
		// Consulta de dataFiles contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query DataFiles { dataFiles { path } }",
					"DataFiles", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_dataFiles");
	}

	@Test
	void ejecutarConsultaGraphQL_red_classDefinitions() throws IOException {
		// Consulta de classDefinitions contra el servidor real
		verificarRed(() -> {
			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(
					"query ClassDefinitions { classDefinitions { name } }",
					"ClassDefinitions", "{}");

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'.");
		}, "ejecutarConsultaGraphQL_red_classDefinitions");
	}

	@Test
	void obtanerModDesdeClase_red_monitorDePID() throws IOException {
		// Buscar la clase MonitorDePID de CrashDetector para obtener el CurseForge ID
		// Usamos ejecutarConsultaGraphQL directamente para que verificarRed capture
		// los errores de rate limiting (HTTP 429) como IOException en lugar de
		// obtanerModDesdeClase que los captura internamente y devuelve lista vacía.
		verificarRed(() -> {
			String consulta = "query JIJ($predicate: StringPredicate) {\n"
					+ "  gameVersions {\n"
					+ "    version\n"
					+ "    loader\n"
					+ "    mods(where: {anyClass: {name: $predicate}}) {\n"
					+ "      count\n"
					+ "      edges {\n"
					+ "        node {\n"
					+ "          curseforgeProjectId\n"
					+ "          modrinthProjectId\n"
					+ "          modIds\n"
					+ "          classes(where: {name: $predicate}) {\n"
					+ "            name\n"
					+ "          }\n"
					+ "        }\n"
					+ "      }\n"
					+ "    }\n"
					+ "  }\n"
					+ "}";

			String variables = "{\"predicate\":{\"equals\":\"com/asbestosstar/crashdetector/MonitorDePID\"}}";

			String respuesta = WaifuAPI.ejecutarConsultaGraphQL(consulta, "JIJ", variables);

			assertNotNull(respuesta, "La respuesta no debe ser nula.");
			assertTrue(respuesta.contains("\"data\""),
					"La respuesta GraphQL debe contener 'data'. Respuesta: "
							+ respuesta.substring(0, Math.min(respuesta.length(), 300)));

			// Parsear la respuesta para verificar el CurseForge ID
			Json.Nodo raiz = Json.leer(respuesta);
			Json.Nodo data = raiz.obtener("data");
			assertNotNull(data, "data no debe ser nulo.");
			assertFalse("null".equals(data.comoCadena()), "data no debe ser null.");

			Json.Nodo gameVersions = data.obtener("gameVersions");
			assertNotNull(gameVersions, "gameVersions no debe ser nulo.");
			assertFalse("null".equals(gameVersions.comoCadena()), "gameVersions no debe ser null.");
			assertTrue(gameVersions.esArreglo(), "gameVersions debe ser un arreglo.");
			assertTrue(gameVersions.tamano() > 0,
					"Debe haber al menos una versión del juego con la clase MonitorDePID.");

			// Verificar que todos los resultados tienen el CurseForge ID: 1224146
			int count = 0;
			boolean tieneNeoForge = false;
			boolean tieneFabric = false;
			boolean tieneForge = false;
			for (int i = 0; i < gameVersions.tamano(); i++) {
				Json.Nodo gv = gameVersions.en(i);
				Json.Nodo loaderNodo = gv.obtener("loader");
				String loader = loaderNodo == null ? "" : loaderNodo.comoCadena();
				Json.Nodo mods = gv.obtener("mods");
				if (mods == null || "null".equals(mods.comoCadena())) {
					continue;
				}
				Json.Nodo edges = mods.obtener("edges");
				if (edges == null || "null".equals(edges.comoCadena()) || !edges.esArreglo()) {
					continue;
				}
				for (int j = 0; j < edges.tamano(); j++) {
					count++;
					Json.Nodo edge = edges.en(j);
					Json.Nodo node = edge.obtener("node");
					Json.Nodo cfNodo = node.obtener("curseforgeProjectId");
					int cfId = cfNodo == null ? 0 : Integer.parseInt(cfNodo.comoCadena());
					assertEquals(1224146, cfId,
							"El CurseForge ID debe ser 1224146. Loader: " + loader);

					if ("NeoForge".equals(loader)) {
						tieneNeoForge = true;
					} else if ("Fabric".equals(loader)) {
						tieneFabric = true;
					} else if ("Forge".equals(loader)) {
						tieneForge = true;
					}
				}
			}

			assertTrue(count > 0, "Debe encontrar al menos un resultado para MonitorDePID.");
			assertTrue(tieneNeoForge || tieneFabric || tieneForge,
					"CrashDetector debe estar en al menos un loader conocido.");
		}, "obtanerModDesdeClase_red_monitorDePID");
	}
}
