package com.asbestosstar.crashdetector.dto.modpack.importar;

public interface ResolutorConflictosImportacion {

	public ConflictoImportacion.Decision resolverConflicto(ConflictoImportacion conflicto,
			PoliticaImportacion politica);
}