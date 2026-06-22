package com.asbestosstar.crashdetector.gui.tipos.no_registro_lanzador;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public final class GeneradorLogGalimatias {

	private GeneradorLogGalimatias() {
	}

	public static void generar(File destino, long objetivoBytes) throws Exception {
		if (destino == null) {
			throw new IllegalArgumentException("destino == null");
		}

		if (objetivoBytes <= 0) {
			throw new IllegalArgumentException("objetivoBytes debe ser mayor que 0");
		}

		try (FileOutputStream fos = new FileOutputStream(destino)) {
			long escritos = 0;
			int linea = 0;

			while (escritos < objetivoBytes) {
				String s = generarLinea(linea);
				byte[] b = s.getBytes(StandardCharsets.UTF_8);

				int escribir = (int) Math.min(b.length, objetivoBytes - escritos);
				fos.write(b, 0, escribir);

				escritos += escribir;
				linea++;
			}
		}
	}

	private static String generarLinea(int linea) {
		int minuto = (linea / 80) % 60;
		int segundo = linea % 60;
		int hilo = linea % 12;
		int mod = linea % 240;

		switch (linea % 18) {
		case 0:
			return "[12:" + dos(minuto) + ":" + dos(segundo)
					+ "] [main/INFO]: Loading Minecraft 1.20.1 with Forge 47.2." + (linea % 20) + "\n";
		case 1:
			return "[12:" + dos(minuto) + ":" + dos(segundo)
					+ "] [main/INFO]: ModLauncher running: args [--username, Player" + (linea % 999)
					+ ", --gameDir, .minecraft, --version, forge]\n";
		case 2:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [main/INFO]: Found mod file fake_mod_" + mod + "-1."
					+ (linea % 9) + "." + (linea % 30) + ".jar of type MOD with provider locator\n";
		case 3:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [main/WARN]: Mod file " + "library_" + (mod % 90)
					+ ".jar is missing mods.toml file\n";
		case 4:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Worker-Main-" + hilo
					+ "/INFO]: Registering item galimatias:item_" + mod + "\n";
		case 5:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Worker-Main-" + hilo
					+ "/INFO]: Registering block galimatias:block_" + (mod * 3) + "\n";
		case 6:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Render thread/INFO]: OpenGL version "
					+ (linea % 2 == 0 ? "4.6.0 NVIDIA 551.86" : "4.6.0 Mesa 24.1.0") + "\n";
		case 7:
			return "[12:" + dos(minuto) + ":" + dos(segundo)
					+ "] [Render thread/INFO]: Created texture atlas minecraft:textures/atlas/blocks.png-atlas\n";
		case 8:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Server thread/INFO]: Preparing spawn area: "
					+ (linea % 100) + "%\n";
		case 9:
			return "[12:" + dos(minuto) + ":" + dos(segundo)
					+ "] [Server thread/WARN]: Can't keep up! Is the server overloaded? Running "
					+ (1000 + (linea % 9000)) + "ms or " + (linea % 180) + " ticks behind\n";
		case 10:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Render thread/WARN]: Shader warning: uniform sampler"
					+ (linea % 7) + " was not found\n";
		case 11:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Netty Client IO #" + (hilo % 4)
					+ "/INFO]: Connected to local server\n";
		case 12:
			return "[12:" + dos(minuto) + ":" + dos(segundo)
					+ "] [Server thread/ERROR]: Failed to load datapack entry galimatias:recipe_" + mod + "\n";
		case 13:
			return "java.lang.IllegalStateException: Failed to bake model galimatias:block/generated_" + mod + "\n";
		case 14:
			return "\tat net.minecraft.client.resources.model.ModelBakery.bake(ModelBakery.java:"
					+ (100 + (linea % 500)) + ")\n";
		case 15:
			return "\tat com.example.galimatias.client.ModelLoader.lambda$load$" + (linea % 8) + "(ModelLoader.java:"
					+ (20 + (linea % 220)) + ")\n";
		case 16:
			return "Caused by: java.io.FileNotFoundException: galimatias/models/block/generated_" + mod + ".json\n";
		default:
			return "[12:" + dos(minuto) + ":" + dos(segundo) + "] [Render thread/INFO]: Loaded chunk " + (linea % 64)
					+ ", " + ((linea / 64) % 64) + " in dimension minecraft:overworld\n";
		}
	}

	private static String dos(int n) {
		return n < 10 ? "0" + n : String.valueOf(n);
	}
}