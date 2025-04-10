package com.asbestosstar.crashdetectormc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class GeneradorDeInformacion {

	public static File generarLocal(StringBuilder constructor,Instant instant) {
    	try {
    		String pantilla = MonitorDePID.leer_archivo(new File("crash_detector/pantilla.htm").toPath());
    		File ret = 	new File("crash_detector/"+instant.toString()+".htm");
			FileWriter escribidor = new FileWriter(ret);
			escribidor.write(pantilla.replace("{constructor}", constructor.toString()));
			escribidor.close();
			return ret;
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
	
	
	
	
	
	
}
