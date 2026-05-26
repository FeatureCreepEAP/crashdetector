package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.nio.file.Path;

public class ConflictoImportacion {

	public enum Tipo {
		ARCHIVO, MOD
	}

	public enum Decision {
		REEMPLAZAR, SALTAR, RENOMBRAR, FUSIONAR, CANCELAR
	}

	public Tipo tipo;

	public Path archivoExistente;
	public Path archivoNuevo;

	public String rutaRelativa;

	public long tamanoExistente;
	public long tamanoNuevo;

	public long fechaExistente;
	public long fechaNueva;

	public String curseForgeIdExistente;
	public String curseForgeIdNuevo;

	public String modrinthProjectIdExistente;
	public String modrinthProjectIdNuevo;

	public String modrinthVersionIdExistente;
	public String modrinthVersionIdNuevo;

	public boolean mismoMod;
	public boolean importadoPareceMasNuevo;
	public boolean importadoPareceMasViejo;
}