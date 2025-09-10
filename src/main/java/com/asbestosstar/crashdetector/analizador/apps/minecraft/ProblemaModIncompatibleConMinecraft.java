package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

/**
 * Clase que detecta mods incompatibles con la versión de Minecraft en
 * Forge.Gracias a Aternos por que esta es una implementacion de su codex
 * https://github.com/aternosorg/codex-minecraft
 */
public class ProblemaModIncompatibleConMinecraft implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private final List<String> nombresMods = new ArrayList<>();
	private final List<String> versionesMinecraft = new ArrayList<>();

	/**
	 * Verifica si el log contiene mods incompatibles con la versión de Minecraft.
	 */
	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;

		// Patrón para detectar mods que no son compatibles con la versión actual de
		// Minecraft
		Pattern patron = Pattern.compile(
				"The mod (\\S+) does not wish to run in Minecraft version Minecraft ([0-9\\.]+)\\. You will have to remove it to play\\.",
				Pattern.DOTALL);
		Matcher coincidencia = patron.matcher(contenido);

		while (coincidencia.find()) {
			String nombreMod = coincidencia.group(1).trim();
			String versionMC = coincidencia.group(2).trim();
			if (!nombreMod.isEmpty() && !versionMC.isEmpty()) {
				nombresMods.add(nombreMod);
				versionesMinecraft.add(versionMC);
			}
		}

		if (!nombresMods.isEmpty()) {
			if (nombresMods.size() > 1) {
				this.mensaje = MonitorDePID.idioma.mensajeModIncompatibleConMinecraftPlural(nombresMods,
						versionesMinecraft);
			} else {
				this.mensaje = MonitorDePID.idioma.mensajeModIncompatibleConMinecraftSingular(nombresMods.get(0),
						versionesMinecraft.get(0));
			}
			activado = true;
		}
	}

	/**
	 * Crea una nueva instancia del verificador.
	 */
	@Override
	public Verificaciones nueva() {
		return new ProblemaModIncompatibleConMinecraft();
	}

	/**
	 * Indica si el problema fue detectado.
	 */
	@Override
	public boolean activado() {
		return activado;
	}

	/**
	 * Prioridad del problema (alta).
	 */
	@Override
	public float prioridad() {
		return 900.0f;
	}

	/**
	 * Devuelve el mensaje de error almacenado.
	 */
	@Override
	public String mensaje() {
		return mensaje;
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaModIncompatibleConMinecraft();
	}

	/**
	 * Devuelve las soluciones posibles para este problema.
	 */
	@Override
	public QuickFix solucion() {
		Builder builder = new Builder(nombre());

		// Agregar solución para cada mod afectado
		for (int i = 0; i < nombresMods.size(); i++) {
			String nombreMod = nombresMods.get(i);
			String versionMC = versionesMinecraft.get(i);
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarMod(nombreMod));
			builder.agregarEtiqueta(MonitorDePID.idioma.solucionInstalarVersionForge(versionMC));
		}

		return builder.construir();
	}
}