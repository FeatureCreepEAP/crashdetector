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
 * regex.
 */
public class ConflictoDeIDsMinecraft implements Verificaciones {

	private boolean activado = false;
	private String tipoConflicto = "";
	private String idConflictivo = "";
	private String modOrigen = "";
	private String modDestino = "";

	private static final String MAX_RANGO = "maximum id range exceeded";
	private static final String SLOT_OCCUPIED = "java.lang.IllegalArgumentException: Slot ";

	@Override
	public void verificar(Consola consola) {
		if (consola == null || consola.contenido_verificar == null)
			return;

		String contenido = consola.contenido_verificar;

		// Detectar conflicto de rango máximo
		if (contenido.contains(MAX_RANGO)) {
			tipoConflicto = "maximo_rango";
			activado = true;
		}

		// Detectar colisión de ID
		int idxSlot = contenido.indexOf(SLOT_OCCUPIED);
		if (idxSlot >= 0) {
			int idxIsAlready = contenido.indexOf(" is already occupied by ", idxSlot);
			int idxWhenAdding = contenido.indexOf(" when adding ", idxIsAlready);
			if (idxSlot >= 0 && idxIsAlready > idxSlot && idxWhenAdding > idxIsAlready) {
				idConflictivo = contenido.substring(idxSlot + SLOT_OCCUPIED.length(), idxIsAlready).trim();
				modOrigen = contenido.substring(idxIsAlready + " is already occupied by ".length(), idxWhenAdding)
						.trim();
				modDestino = contenido.substring(idxWhenAdding + " when adding ".length(),
						contenido.indexOf("\n", idxWhenAdding) > 0 ? contenido.indexOf("\n", idxWhenAdding)
								: contenido.length())
						.trim();
				if (!idConflictivo.isEmpty() && !modOrigen.isEmpty() && !modDestino.isEmpty()) {
					tipoConflicto = "colision_id";
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
			return MonitorDePID.idioma.conflicto_id_colision_especifico(idConflictivo, modOrigen, modDestino);
		} else if ("maximo_rango".equals(tipoConflicto)) {
			return MonitorDePID.idioma.conflicto_id_maximo();
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
		if (!activado || trazo == null || trazo.trace == null)
			return false;

		String t = trazo.trace;

		if ("maximo_rango".equals(tipoConflicto)) {
			return t.contains(MAX_RANGO);
		} else if ("colision_id".equals(tipoConflicto)) {
			if (!idConflictivo.isEmpty() && !modOrigen.isEmpty() && !modDestino.isEmpty()) {
				String esperado = "java.lang.IllegalArgumentException: Slot " + idConflictivo
						+ " is already occupied by " + modOrigen + " when adding " + modDestino;
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