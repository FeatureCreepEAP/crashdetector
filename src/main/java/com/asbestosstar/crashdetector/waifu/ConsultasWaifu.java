package com.asbestosstar.crashdetector.waifu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Catálogo de consultas GraphQL de NeoForge Waifu.
 *
 * Esta clase contiene: - Los dos campos raíz de Query. - Todos los campos
 * consultables de GameVersion. - Las consultas especializadas utilizadas por la
 * interfaz web oficial. - Una entrada "rawGraphQL" para consultas arbitrarias.
 *
 * El ejecutor genérico vive en WaifuAPI, por lo que este catálogo no limita la
 * superficie del esquema: cualquier consulta GraphQL válida puede enviarse
 * aunque no tenga un preset dedicado.
 */
public final class ConsultasWaifu {

	public static final String ID_GAME_VERSIONS = "gameVersions";
	public static final String ID_GAME_VERSION = "gameVersion";
	public static final String ID_MODS = "mods";
	public static final String ID_MODS_BY_ID = "modsById";
	public static final String ID_MOD_INFORMATION = "_modInformation";
	public static final String ID_CLASSES = "classes";
	public static final String ID_CLASS = "class";
	public static final String ID_CLASS_DEFINITIONS = "classDefinitions";
	public static final String ID_RECIPES = "recipes";
	public static final String ID_DATA_MAPS = "dataMaps";
	public static final String ID_TAG_ENTRIES = "tagEntries";
	public static final String ID_DATA_FILES = "dataFiles";
	public static final String ID_ENUM_EXTENSIONS = "enumExtensions";

	public static final String ID_METHOD_REFERENCES = "methodReferences";
	public static final String ID_FIELD_REFERENCES = "fieldReferences";
	public static final String ID_IMPLEMENTATIONS = "implementations";
	public static final String ID_ANNOTATED_CLASSES = "annotatedClasses";
	public static final String ID_MIXINS_BY_PACKAGE = "mixinsByPackage";
	public static final String ID_RECIPES_BY_TYPE = "recipesByType";
	public static final String ID_TAGS_CONTAINING_ELEMENT = "tagsContainingElement";
	public static final String ID_RAW = "rawGraphQL";

	private static final LinkedHashMap<String, ConsultaPredefinida> CONSULTAS = new LinkedHashMap<String, ConsultaPredefinida>();

	static {
		registrar(new ConsultaPredefinida(
				ID_GAME_VERSIONS, "GameVersions", "query GameVersions($loader: Loader) {\n"
						+ "  gameVersions(loader: $loader) {\n" + "    version\n" + "    loader\n" + "  }\n" + "}",
				"{\"loader\":null}"));

		registrar(new ConsultaPredefinida(ID_GAME_VERSION, "GameVersion",
				"query GameVersion($version: String!, $loader: Loader!) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n" + "    version\n" + "    loader\n"
						+ "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\"}"));

		registrar(new ConsultaPredefinida(ID_MODS, "Mods",
				"query Mods($version: String!, $loader: Loader!, $predicate: ModPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    mods(where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          id name version authors license modIds\n"
						+ "          curseforgeProjectId modrinthProjectId mavenCoordinates indexedOn\n" + "        }\n"
						+ "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_MODS_BY_ID, "ModsById",
				"query ModsById($version: String!, $loader: Loader!, $ids: [Int!]!, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    modsById(ids: $ids, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          id name version authors license modIds\n"
						+ "          curseforgeProjectId modrinthProjectId mavenCoordinates indexedOn\n" + "        }\n"
						+ "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"ids\":[],\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_MOD_INFORMATION, "GetModInformation",
				"fragment PlatformInformation on IntPlatformInformation {\n"
						+ "  title downloads description projectUrl issuesUrl iconUrl sourceUrl\n" + "}\n"
						+ "query GetModInformation($version: String!, $loader: Loader!, $id: Int!) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n" + "    _modInformation(id: $id) {\n"
						+ "      id name authors modIds license version metadata mavenCoordinates\n"
						+ "      curseforge { ...PlatformInformation fileDownloadUrl(version: $version, loader: $loader) }\n"
						+ "      modrinth { ...PlatformInformation fileDownloadUrl(version: $version, loader: $loader) }\n"
						+ "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"id\":0}"));

		registrar(new ConsultaPredefinida(ID_CLASSES, "Classes",
				"query Classes($version: String!, $loader: Loader!, $predicate: ClassPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    classes(where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          id name\n" + "          definitions { name mod { id name } }\n" + "        }\n"
						+ "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_CLASS, "ClassByName",
				"query ClassByName($version: String!, $loader: Loader!, $name: String!) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n" + "    class(name: $name) {\n"
						+ "      id name\n" + "      definitions { name parents mod { id name } }\n"
						+ "      methods { id name descriptor }\n" + "      fields { id name type }\n"
						+ "      inheritors { id name depth }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"name\":\"\"}"));

		registrar(new ConsultaPredefinida(ID_CLASS_DEFINITIONS, "ClassDefinitions",
				"query ClassDefinitions($version: String!, $loader: Loader!, $predicate: ClassDefinitionPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    classDefinitions(where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          name parents\n" + "          mod { id name }\n"
						+ "          annotations { type value }\n" + "        }\n" + "      }\n" + "    }\n" + "  }\n"
						+ "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_RECIPES, "GetRecipes",
				"query GetRecipes($version: String!, $loader: Loader!, $predicate: RecipeFilePredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    recipes(where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { name type recipe mod { id name } } }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_DATA_MAPS, "DataMaps",
				"query DataMaps($version: String!, $loader: Loader!, $registry: String!, $predicate: DataMapPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    dataMaps(registry: $registry, where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          name\n" + "          entries { key values { value mod { id name } } }\n"
						+ "        }\n" + "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"registry\":\"\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_TAG_ENTRIES, "GetTagEntries",
				"query GetTagEntries($version: String!, $loader: Loader!, $registry: String!, $predicate: TagEntryPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    tagEntries(registry: $registry, where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { tag entry mod { id name } } }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"registry\":\"\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_DATA_FILES, "DataFiles",
				"query DataFiles($version: String!, $loader: Loader!, $location: String!, $predicate: DataFilePredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    dataFiles(location: $location, where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { name value mod { id name } } }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"location\":\"\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_ENUM_EXTENSIONS, "GetEnumExtensions",
				"query GetEnumExtensions($version: String!, $loader: Loader!, $predicate: EnumExtensionPredicate, $cursor: ID) {\n"
						+ "  gameVersion(version: $version, loader: $loader) {\n"
						+ "    enumExtensions(where: $predicate, after: $cursor) {\n" + "      count\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { enum name constructor parameters mod { id name } } }\n" + "    }\n"
						+ "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":null,\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_METHOD_REFERENCES, "GetMethodReferences",
				"query GetMethodReferences($version: String!, $loader: Loader!, $class: String!, $methodFilter: MethodPredicate!, $filter: ReferencePredicate) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n" + "    class(name: $class) {\n"
						+ "      methods(where: $methodFilter) {\n" + "        name descriptor\n"
						+ "        references(where: $filter) {\n" + "          referenceCount\n"
						+ "          owner { mod { id name } name }\n" + "        }\n" + "      }\n" + "    }\n"
						+ "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"class\":\"\",\"methodFilter\":{\"name\":{\"matches\":\".*\"}},\"filter\":null}"));

		registrar(new ConsultaPredefinida(ID_FIELD_REFERENCES, "GetFieldReferences",
				"query GetFieldReferences($version: String!, $loader: Loader!, $class: String!, $fieldFilter: FieldPredicate!, $filter: ReferencePredicate) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n" + "    class(name: $class) {\n"
						+ "      fields(where: $fieldFilter) {\n" + "        name type\n"
						+ "        references(where: $filter) {\n" + "          referenceCount\n"
						+ "          owner { mod { id name } name }\n" + "        }\n" + "      }\n" + "    }\n"
						+ "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"class\":\"\",\"fieldFilter\":{\"name\":{\"matches\":\".*\"}},\"filter\":null}"));

		registrar(new ConsultaPredefinida(ID_IMPLEMENTATIONS, "GetImplementations",
				"query GetImplementations($version: String!, $loader: Loader!, $class: String!) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n" + "    class(name: $class) {\n"
						+ "      inheritors {\n" + "        id name depth\n"
						+ "        definitions { mod { id name } }\n" + "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"class\":\"\"}"));

		registrar(new ConsultaPredefinida(ID_ANNOTATED_CLASSES, "GetClassesAnnotated",
				"query GetClassesAnnotated($version: String!, $loader: Loader!, $predicate: ClassDefinitionPredicate!, $annotationPredicate: AnnotationPredicate!, $cursor: ID) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n"
						+ "    classDefinitions(where: $predicate, after: $cursor) {\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          name\n" + "          mod { id name }\n"
						+ "          annotations(where: $annotationPredicate) { type value }\n" + "        }\n"
						+ "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":{\"anyAnnotation\":{\"type\":{\"matches\":\".*\"}}},\"annotationPredicate\":{\"type\":{\"matches\":\".*\"}},\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_MIXINS_BY_PACKAGE, "MixinsByPackage",
				"query MixinsByPackage($version: String!, $loader: Loader!, $predicate: ClassDefinitionPredicate!, $cursor: ID) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n"
						+ "    classDefinitions(where: $predicate, after: $cursor) {\n"
						+ "      pageInfo { hasNextPage endCursor }\n" + "      edges {\n" + "        node {\n"
						+ "          name mod { id name }\n"
						+ "          annotations(where: {type: {equals: \"org/spongepowered/asm/mixin/Mixin\"}}) { value }\n"
						+ "        }\n" + "      }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"predicate\":{\"anyAnnotation\":{\"type\":{\"equals\":\"org/spongepowered/asm/mixin/Mixin\"}}},\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_RECIPES_BY_TYPE, "RecipesByType",
				"query RecipesByType($version: String!, $loader: Loader!, $type: String!, $cursor: ID) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n"
						+ "    recipes(where: {type: {equals: $type}}, after: $cursor) {\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { name type recipe mod { id name } } }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"type\":\"\",\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_TAGS_CONTAINING_ELEMENT, "TagsContainingElement",
				"query TagsContainingElement($version: String!, $loader: Loader!, $registry: String!, $entry: String!, $cursor: ID) {\n"
						+ "  gameVersion(loader: $loader, version: $version) {\n"
						+ "    tagEntries(registry: $registry, where: {entry: {equals: $entry}}, after: $cursor) {\n"
						+ "      pageInfo { hasNextPage endCursor }\n"
						+ "      edges { node { tag entry mod { id name } } }\n" + "    }\n" + "  }\n" + "}",
				"{\"version\":\"\",\"loader\":\"NeoForge\",\"registry\":\"\",\"entry\":\"\",\"cursor\":null}"));

		registrar(new ConsultaPredefinida(ID_RAW, "", "", "{}"));
	}

	private ConsultasWaifu() {
	}

	private static void registrar(ConsultaPredefinida consulta) {
		CONSULTAS.put(consulta.id, consulta);
	}

	public static List<String> ids() {
		return Collections.unmodifiableList(new ArrayList<String>(CONSULTAS.keySet()));
	}

	public static ConsultaPredefinida obtener(String id) {
		return CONSULTAS.get(id);
	}

	public static Map<String, ConsultaPredefinida> todas() {
		return Collections.unmodifiableMap(CONSULTAS);
	}

	/**
	 * Ejecuta un preset por su ID. La cadena de variables debe ser un objeto JSON.
	 */
	public static String ejecutar(String id, String variablesJson) throws java.io.IOException {
		ConsultaPredefinida consulta = obtener(id);
		if (consulta == null) {
			throw new java.io.IOException("Consulta Waifu desconocida: " + id);
		}
		if (ID_RAW.equals(id)) {
			throw new java.io.IOException("rawGraphQL requiere una consulta proporcionada por el usuario.");
		}
		return WaifuAPI.ejecutarConsultaGraphQL(consulta.graphql, consulta.nombreOperacion, variablesJson);
	}

	public static final class ConsultaPredefinida {
		public final String id;
		public final String nombreOperacion;
		public final String graphql;
		public final String variablesEjemplo;

		ConsultaPredefinida(String id, String nombreOperacion, String graphql, String variablesEjemplo) {
			this.id = id;
			this.nombreOperacion = nombreOperacion;
			this.graphql = graphql;
			this.variablesEjemplo = variablesEjemplo;
		}
	}
}
