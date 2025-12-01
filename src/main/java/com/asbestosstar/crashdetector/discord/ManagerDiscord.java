package com.asbestosstar.crashdetector.discord;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.cargador.Cargador;

public class ManagerDiscord {

	public static void comenzar() {
		
		if(Cargador.claseExiste("com.jagrosh.discordipc.entities.ActivityType")) {
		CrashDetectorLogger.log("tenemos IPC");
			DiscordRichPresenceManager.init();
		}
	}
	
	
	
}
