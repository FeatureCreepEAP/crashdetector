package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta conflictos de IDs de Minecraft (colisión de ID o rango máximo) sin
 * regex. Modernized with global flag and per-line detection for speed.
 */
public class ConflictoDeIDsMinecraft implements Verificaciones {

	private boolean activado = false;
	private boolean posibleConflicto = false;

	private String tipoConflicto = "";
	private String idConflictivo = "";
	private String modOrigen = "";
	private String modDestino = "";
	private String enlace = "";

	private static final String MAX_RANGO = "maximum id range exceeded";
	private static final String SLOT_OCCUPIED = "java.lang.IllegalArgumentException: Slot ";
	private static final String ID_OCCUPIED_MID = " is already occupied by ";
	private static final String ID_OCCUPIED_END = " when adding ";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null || consola.contenido_verificar.isEmpty()) {
			return;
		}

		String contenido = consola.contenido_verificar;

		if (contenido.contains(MAX_RANGO) || contenido.contains(SLOT_OCCUPIED)) {
			posibleConflicto = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!posibleConflicto || activado || linea == null || linea.isEmpty()) {
			return;
		}

		// Conflicto de rango máximo
		if (linea.contains(MAX_RANGO)) {
			tipoConflicto = "maximo_rango";
			enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
			return;
		}

		// Colisión de ID
		int idxSlot = linea.indexOf(SLOT_OCCUPIED);
		if (idxSlot >= 0) {
			int idxIsAlready = linea.indexOf(ID_OCCUPIED_MID, idxSlot);
			int idxWhenAdding = linea.indexOf(ID_OCCUPIED_END, idxIsAlready);
			if (idxIsAlready > idxSlot && idxWhenAdding > idxIsAlready) {
				idConflictivo = linea.substring(idxSlot + SLOT_OCCUPIED.length(), idxIsAlready).trim();
				modOrigen = linea.substring(idxIsAlready + ID_OCCUPIED_MID.length(), idxWhenAdding).trim();
				modDestino = linea.substring(idxWhenAdding + ID_OCCUPIED_END.length()).trim();
				if (!idConflictivo.isEmpty() && !modOrigen.isEmpty() && !modDestino.isEmpty()) {
					tipoConflicto = "colision_id";
					enlace = consola.agregarErrorALectador(numero_de_linea, this);
					activado = true;
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ConflictoDeIDsMinecraft();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f;
	}

	@Override
	public String mensaje() {
		if ("colision_id".equals(tipoConflicto)) {
			return MonitorDePID.idioma.conflicto_id_colision_especifico(idConflictivo, modOrigen, modDestino) + " "
					+ enlace;
		} else if ("maximo_rango".equals(tipoConflicto)) {
			return MonitorDePID.idioma.conflicto_id_maximo() + " " + enlace;
		}
		return "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_conflicto_ids();
	}

	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		if ("maximo_rango".equals(tipoConflicto)) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucion_maximo_rango())
					.agregarBoton(MonitorDePID.idioma.instalar_justenoughids(), (bool) -> {
						Verificaciones.abrirEnNavegador("https://www.curseforge.com/minecraft/mc-mods/justenoughids");
					}).agregarBoton(MonitorDePID.idioma.instalar_endlessids(), (bool) -> {
						Verificaciones.abrirEnNavegador("https://www.curseforge.com/minecraft/mc-mods/endless-ids");
					});
		} else if ("colision_id".equals(tipoConflicto)) {
			builder.agregarEtiqueta(MonitorDePID.idioma.solucion_colision_id())
					.agregarBoton(MonitorDePID.idioma.usar_idfix_minus(), (bool) -> {
						Verificaciones.abrirEnNavegador(
								"https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id");
					}).agregarBoton(MonitorDePID.idioma.usar_minecraft_id_resolver(), (bool) -> {
						Verificaciones.abrirEnNavegador("https://github.com/SS111/Minecraft-ID-Resolver");
					}).agregarBoton(MonitorDePID.idioma.ver_documentacion_jp(), (bool) -> {
						Verificaciones.abrirEnNavegador(
								"https://minecraftjapan.miraheze.org/wiki/MOD%E8%A7%A3%E8%AA%AC/ID%E7%AB%B6%E5%90%88%E9%98%B2%E6%AD%A2%E6%94%AF%E6%8F%B4MOD");
					});
		}

		return builder.construir();
	}

	@Override
	public String id() {
		return "conflicto_de_ids_minecraft";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		if ("maximo_rango".equals(tipoConflicto)) {
			return t.contains(MAX_RANGO);
		} else if ("colision_id".equals(tipoConflicto)) {
			if (!idConflictivo.isEmpty() && !modOrigen.isEmpty() && !modDestino.isEmpty()) {
				String esperado = SLOT_OCCUPIED + idConflictivo + ID_OCCUPIED_MID + modOrigen + ID_OCCUPIED_END
						+ modDestino;
				return t.contains(esperado);
			}
		}
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}