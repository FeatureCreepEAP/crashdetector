package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Espanol implements Idioma {

	@Override
	public String carpeta_de_mods_no_valido() {
		// TODO Auto-generated method stub
		return "no es una carpeta de mods valida";
	}

	@Override
	public String no_se_donde_esta_jar() {
		// TODO Auto-generated method stub
		return "No se donde esta el archivo JAR de CrashDetector";
	}

	@Override
	public String buscando_para_pid(long pid) {
		// TODO Auto-generated method stub
		 return "Buscando para PID: " + String.valueOf(pid);
	}

	@Override
	public String pid_esta_muerto(long pid) {
		// TODO Auto-generated method stub
		return "(PID: " + String.valueOf(pid) + ") esta muerto!";
	}

	@Override
	public String no_tenemos_jvm() {
		// TODO Auto-generated method stub
		return "No tenemos JVM";
	}

	
	@Override
	public String probelma_con_graficas_ati() {
	    return "Actualizar tus controladores podría ayudarte. Ten en cuenta que buscar actualizaciones de la manera habitual no encontrará ninguna cuando los controladores estén en un estado dañado, por lo que es importante que sigas la guía vinculada. Importante: Si tienes gráficos Nvidia, asegúrate de configurar cualquier cosa relacionada con Minecraft (como Java y lanzadores) para priorizar el rendimiento alto tanto en la configuración de Windows como en el panel de control de Nvidia. Lee esta guía para solucionarlo: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
	}

	@Override
	public String probelma_con_graficas_nouveau() {
	    return "Algunas versiones antiguas a veces tienen algunos problemas con algunos gráficos Nouveau en la pantalla de carga temprana.";
	}

	@Override
	public String probelma_con_graficas_general() {
	    return "Tienes un problema con tus controladores gráficos. Si tienes una GPU o APU AMD/ATI, actualiza tus controladores gráficos AMD. Si tienes una tarjeta gráfica NVIDIA, asegúrate de marcar el juego y todas las instancias de javaw.exe para usar la tarjeta gráfica dedicada. Lee esta guía: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
	}

	@Override
	public String fmlEarlyWindow() {
	    return "Tu ventana FML Early está fallando. "
	            + "Para cambiar esto, ve a (.)minecraft/config/fml.toml "
	            + "Edita earlyWindowProvider para que sea earlyWindowProvider=\"none\" "
	            + "Si estás en una Mac M1, también asegúrate de estar usando una versión ARM de Java, no una versión Intel x64. "
	            + "Este también es un problema común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona. https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
	}

	@Override
	public String no_tienes_las_dependencias_necesitas() {
	    return "No tienes todas las dependencias necesarias:";
	}
	
	    @Override
	    public String linea_de_dependencia(String linea) {
	    	return linea.replace("Requested by", "Solicitado por").replace("Expected range", "rango esperado");
	    }

		@Override
		public String local_headless(String archivo) {
			// TODO Auto-generated method stub
			return "Tu Informe de CrashDetector esta aqui "+ archivo;
		}

		@Override
		public String texto_de_gui() {
			// TODO Auto-generated method stub
			return "Esta es el GUI de CrashDetector. Si el juego cierra sin problemas, ignoralo.";
		}

		@Override
		public String texto_de_buton_local_enlance() {
			// TODO Auto-generated method stub
			return "Ver Informe";
		}

		@Override
		public String texto_debajo_de_buton_local_enlance() {
			// TODO Auto-generated method stub
			return "Ver Un Informe Local en tu navigator.";
		}

		@Override
		public String texto_de_buton_compartir_enlance() {
			// TODO Auto-generated method stub
			return "Compartir Informe";
		}

		@Override
		public String texto_debajo_de_buton_compartir_enlance() {
			// TODO Auto-generated method stub
			return "Compartir Informe,Sus registros se cargarán en securelogger.net y  tu informe se cargaran a un otro sito para 3 dias";
		}
	
	
	
}
