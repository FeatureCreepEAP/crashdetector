package com.asbestosstar.crashdetector.detectorlanzer;

import com.asbestosstar.crashdetector.App;

public class DetectorMinecraftServer implements DetectorLanzer {
	@Override
	public String id() {
		return "minecraft_server";
	}

	@Override
	public boolean animado() {
		return true;
	} // asumo recomendado

	@Override
	public boolean desanimado() {
		return false;
	}

	@Override
	public boolean detectar(App app, String cmd) {
		if (!app.equals(App.MINECRAFT))
			return false;

		boolean tieneServer;
		boolean tieneClient = false;

		try {
			Class.forName("net.minecraft.server.MinecraftServer", false, this.getClass().getClassLoader());
			tieneServer = true;
		} catch (ClassNotFoundException e) {
			tieneServer = false;
		}

		try {
			Class.forName("net.minecraft.client.main.Main", false, this.getClass().getClassLoader());
			tieneClient = true;
		} catch (ClassNotFoundException ignored) {
		}

		return tieneServer && !tieneClient;
	}
}