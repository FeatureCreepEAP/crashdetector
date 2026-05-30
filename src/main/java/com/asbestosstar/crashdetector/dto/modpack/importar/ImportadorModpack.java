package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.asbestosstar.crashdetector.gui.tipos.importador.DialogoConflictoImportacionGUI;
import com.asbestosstar.crashdetector.gui.tipos.importador.DialogoConflictoImportacionYumeiriReyu;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public interface ImportadorModpack {

	public String obtenerNombreImportador();

	public String obtenerExtensionModpack();

	public boolean puedeImportar(Path archivoModpack);

	public ResultadoImportacion importarModpack(Path archivoModpack, Path carpetaDestino, PoliticaImportacion politica)
			throws IOException;

	default ResultadoImportacion instalarEntradaConConflictos(byte[] bytesEntrada, InfoEntradaImportacion info,
			Path destino, PoliticaImportacion politica) throws IOException {

		synchronized (ImportadorParalelo.BLOQUEO_CONFLICTOS) {
			ResultadoImportacion resultado = new ResultadoImportacion();

			if (politica == null) {
				politica = new PoliticaImportacion();
			}

			if (bytesEntrada == null) {
				resultado.errores++;
				resultado.mensajes.add("Entrada sin bytes: " + destino);
				return resultado;
			}

			if (destino == null) {
				resultado.errores++;
				resultado.mensajes.add("Destino nulo.");
				return resultado;
			}

			if (destino.getParent() != null) {
				Files.createDirectories(destino.getParent());
			}

			if (!Files.exists(destino)) {
				Files.write(destino, bytesEntrada, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
				resultado.copiados++;
				return resultado;
			}

			ConflictoImportacion conflicto = crearConflictoDesdeEntrada(bytesEntrada, info, destino);

			ConflictoImportacion.Decision decision = resolverConflictoConGUI(conflicto, politica);

			if (decision == ConflictoImportacion.Decision.REEMPLAZAR) {
				Files.write(destino, bytesEntrada, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
				resultado.reemplazados++;
				return resultado;
			}

			if (decision == ConflictoImportacion.Decision.FUSIONAR) {
				if (!FusionadorFTBQuestsSnbt.puedeFusionar(info, destino)) {
					resultado.saltados++;
					resultado.mensajes.add("No se puede fusionar este archivo: " + destino);
					return resultado;
				}

				try {
					byte[] fusionado = FusionadorFTBQuestsSnbt.fusionar(bytesEntrada, destino);
					Files.write(destino, fusionado, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

					resultado.fusionados++;
					resultado.mensajes.add("Archivo FTB Quests fusionado: " + destino);
					return resultado;
				} catch (Throwable t) {
					resultado.errores++;
					resultado.mensajes.add("No se pudo fusionar FTB Quests " + destino + ": " + t.getMessage());
					return resultado;
				}
			}

			if (decision == ConflictoImportacion.Decision.RENOMBRAR) {
				Path renombrado = crearRutaRenombrada(destino);
				Files.write(renombrado, bytesEntrada, StandardOpenOption.CREATE_NEW);
				resultado.renombrados++;
				return resultado;
			}

			resultado.saltados++;
			return resultado;
		}
	}

	default ConflictoImportacion crearConflictoDesdeEntrada(byte[] bytesEntrada, InfoEntradaImportacion info,
			Path destino) throws IOException {

		ConflictoImportacion conflicto = new ConflictoImportacion();

		conflicto.tipo = info != null && info.esMod ? ConflictoImportacion.Tipo.MOD : ConflictoImportacion.Tipo.ARCHIVO;

		conflicto.archivoExistente = destino;
		conflicto.archivoNuevo = null;
		conflicto.rutaRelativa = info != null ? info.rutaRelativa : destino.getFileName().toString();

		conflicto.tamanoExistente = Files.size(destino);
		conflicto.tamanoNuevo = bytesEntrada.length;

		conflicto.fechaExistente = Files.getLastModifiedTime(destino).toMillis();
		conflicto.fechaNueva = info != null ? info.fechaModificacion : 0L;

		if (info != null) {
			conflicto.curseForgeIdNuevo = info.curseForgeProjectId;
			conflicto.modrinthProjectIdNuevo = info.modrinthProjectId;
			conflicto.modrinthVersionIdNuevo = info.modrinthVersionId;
		}

		conflicto.importadoPareceMasNuevo = conflicto.fechaNueva > 0 && conflicto.fechaNueva > conflicto.fechaExistente;
		conflicto.importadoPareceMasViejo = conflicto.fechaNueva > 0 && conflicto.fechaNueva < conflicto.fechaExistente;

		return conflicto;
	}

	default ConflictoImportacion.Decision resolverConflictoConGUI(ConflictoImportacion conflicto,
			PoliticaImportacion politica) {

		if (politica.reemplazarTodo) {
			return ConflictoImportacion.Decision.REEMPLAZAR;
		}

		if (politica.saltarTodo) {
			return ConflictoImportacion.Decision.SALTAR;
		}

		if (politica.renombrarTodo) {
			return ConflictoImportacion.Decision.RENOMBRAR;
		}

		if (politica.noPreguntarSiImportadoEsMasNuevo && conflicto.importadoPareceMasNuevo) {
			return ConflictoImportacion.Decision.REEMPLAZAR;
		}

		DialogoConflictoImportacionGUI gui = TipoGUI.IMPORTADOR_CONFLICTO.obtenerGUIPredeterminado(
				DialogoConflictoImportacionYumeiriReyu.ID, DialogoConflictoImportacionYumeiriReyu::new);

		gui.establecerConflicto(conflicto);
		return gui.mostrarYObtenerDecision();
	}

	public static void mezclar(ResultadoImportacion total, ResultadoImportacion r) {
		if (r == null) {
			return;
		}

		total.copiados += r.copiados;
		total.reemplazados += r.reemplazados;
		total.saltados += r.saltados;
		total.renombrados += r.renombrados;
		total.fusionados += r.fusionados;
		total.errores += r.errores;
		total.mensajes.addAll(r.mensajes);
	}

	default Path crearRutaRenombrada(Path destino) {
		String nombre = destino.getFileName().toString();
		String base = nombre;
		String ext = "";

		int punto = nombre.lastIndexOf('.');
		if (punto > 0) {
			base = nombre.substring(0, punto);
			ext = nombre.substring(punto);
		}

		for (int i = 1; i < 10000; i++) {
			Path candidato = destino.getParent().resolve(base + "_importado_" + i + ext);
			if (!Files.exists(candidato)) {
				return candidato;
			}
		}

		return destino.getParent().resolve(base + "_importado_" + System.currentTimeMillis() + ext);
	}
}