package com.asbestosstar.crashdetector.discord;

import java.time.OffsetDateTime;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.google.gson.JsonObject;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

/**
 * Clase para configurar y gestionar el Discord Rich Presence para
 * CrashDetector. Utiliza la biblioteca Discord IPC para establecer la conexión
 * y enviar la presencia enriquecida.
 */
public class DiscordRichPresenceManager {

	public static void init() {

		CrashDetectorLogger.log("registrando ipc cliente");
		IPCClient client = new IPCClient(1444964488406110208L);

		client.setListener(new IPCListener() {
			@Override
			public void onReady(IPCClient client) {

				RichPresence.Builder builder = new RichPresence.Builder();
				builder.setState("CrashDetector")
						.setDetails("https://www.curseforge.com/minecraft/mc-mods/crashdetector")
						.setActivityType(ActivityType.Playing);

				RichPresence rich = builder.build();

				client.sendRichPresence(rich);

			}

			@Override
			public void onPacketSent(IPCClient client, Packet packet) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPacketReceived(IPCClient client, Packet packet) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onActivityJoin(IPCClient client, String secret) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onActivitySpectate(IPCClient client, String secret) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onActivityJoinRequest(IPCClient client, String secret, User user) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onClose(IPCClient client, JsonObject json) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDisconnect(IPCClient client, Throwable t) {
				// TODO Auto-generated method stub

			}
		});
		try {
			client.connect();
		} catch (NoDiscordClientException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

}