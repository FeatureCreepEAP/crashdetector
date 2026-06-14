package com.asbestosstar.crashdetector.discord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.Statics;
import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

public class DiscordRichPresenceManager {

	private static final AtomicBoolean INICIADO = new AtomicBoolean(false);

	private static final ExecutorService EJECUTOR = Executors.newSingleThreadExecutor(r -> {
		Thread t = new Thread(r, "Discord-RichPresence");
		t.setDaemon(true);
		return t;
	});

	private static volatile IPCClient client;

	public static void init() {
		if (!INICIADO.compareAndSet(false, true)) {
			return;
		}

		EJECUTOR.execute(() -> {
			try {
				initInterno();
			} catch (Throwable t) {
				CrashDetectorLogger.log("Discord Rich Presence fallo: " + t.getMessage());
			}
		});
	}

	private static void initInterno() throws NoDiscordClientException {
		CrashDetectorLogger.log("registrando ipc cliente");

		IPCClient nuevoCliente = new IPCClient(Statics.discordRichPresenceID);
		client = nuevoCliente;

		nuevoCliente.setListener(new IPCListener() {
			@Override
			public void onReady(IPCClient client) {
				RichPresence rich = new RichPresence.Builder().setState(Statics.nombre_cd.obtener())
						.setDetails(Statics.detallesDiscordRichPresence).setActivityType(ActivityType.Playing).build();

				client.sendRichPresence(rich);
			}

			@Override
			public void onPacketSent(IPCClient client, Packet packet) {
			}

			@Override
			public void onPacketReceived(IPCClient client, Packet packet) {
			}

			@Override
			public void onActivityJoin(IPCClient client, String secret) {
			}

			@Override
			public void onActivitySpectate(IPCClient client, String secret) {
			}

			@Override
			public void onActivityJoinRequest(IPCClient client, String secret, User user) {
			}

			@Override
			public void onClose(IPCClient client, JsonObject json) {
				DiscordRichPresenceManager.client = null;
			}

			@Override
			public void onDisconnect(IPCClient client, Throwable t) {
				DiscordRichPresenceManager.client = null;
			}
		});

		nuevoCliente.connect();
	}

	public static void cerrar() {
		IPCClient c = client;
		if (c != null) {
			try {
				c.close();
			} catch (Throwable ignored) {
			}
		}
		client = null;
	}
}