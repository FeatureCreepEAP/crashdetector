package com.asbestosstar.crashdetector.dto.modpack.modrinth;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.dto.modpack.bbsmc.BBSModDesdeJar;

public class MRModDesdeJar extends BBSModDesdeJar {

	public static final String ENDPOINT_MODRINTH = "https://api.modrinth.com/v2";

	public static ResultadoBBS analizarInstancia(Path carpetaInstancia) throws IOException {
		return analizarInstanciaConEndpoint(carpetaInstancia, ENDPOINT_MODRINTH);
	}

	public static ResultadoBBS analizarArchivos(Path carpetaInstancia, List<ArchivoLocalBBS> archivos)
			throws IOException {
		return analizarArchivosConEndpoint(carpetaInstancia, archivos, ENDPOINT_MODRINTH);
	}

	public static Json.Nodo solicitarVersionPorSha1Modrinth(String sha1) throws IOException {
		return solicitarVersionPorSha1(ENDPOINT_MODRINTH, sha1);
	}

	public static Json.Nodo solicitarProyectoModrinth(String projectId) throws IOException {
		return solicitarProyecto(ENDPOINT_MODRINTH, projectId);
	}
}