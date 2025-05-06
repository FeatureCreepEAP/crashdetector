package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

public class Espanol implements Idioma {
    Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>no es una carpeta de mods valida</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>No se donde esta el archivo JAR de CrashDetector</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Buscando para PID: " + String.valueOf(pid) + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid) + ") esta muerto!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>No tenemos JVM</span>";
    }

    @Override
    public String problema_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Actualizar tus controladores ATI/AMD podría ayudarte.Lee esta guía para solucionarlo: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guía de actualización de controladores</a> https://www.amd.com/es/support/download/drivers.html Descargar </span>";
    }
    
    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Algunas versiones antiguas a veces tienen algunos problemas con algunos gráficos Nouveau en la pantalla de carga temprana.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Tienes un problema con tus controladores gráficos. Si tienes una GPU o APU AMD/ATI, actualiza tus controladores gráficos AMD. Si tienes una tarjeta gráfica NVIDIA, asegúrate de marcar el juego y todas las instancias de javaw.exe para usar la tarjeta gráfica dedicada. Lee esta guía: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guía de actualización de controladores</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Tu ventana FML Early está fallando. Para cambiar esto, ve a (.minecraft/config/fml.toml) y edita earlyWindowProvider para que sea earlyWindowProvider=\"none\". Si estás en una Mac M1, también asegúrate de estar usando una versión ARM de Java, no una versión Intel x64. Este también es un problema común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guía de actualización de controladores</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>No tienes todas las dependencias necesarias:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "Solicitado por").replace("Expected range", "rango esperado") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Tu Informe de CrashDetector esta aqui <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>ver informe</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Esta es el GUI de CrashDetector. Si el juego cierra sin problemas, ignoralo.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>Ver Informe</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Ver Un Informe Local en tu navegador.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "Compartir Informe";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Compartir Informe. Sus registros se cargarán en securelogger.net y tu informe se cargará a otro sitio.";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Archivos JAR problemáticos detectados (Priorizar FATAL, luego nivel alto y línea baja):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> nivel:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Posiblemente Fatal:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>ModIDs problemáticos detectados (Priorizar FATAL, luego nivel bajo y línea baja):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Paquetes problemáticos detectados (Priorizar FATAL, luego nivel bajo y línea baja):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Clases fatales faltantes detectadas:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Contenido en {} (Lo más importante arriba, solo 20 primeros):</b>";
    }

    @Override
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Configuración problemática de SpongeMixin detectada: " + "</b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Tienes Mods con Packages/Paquetes duplicados: " + modules + " package duplicado " + paquete.replace(".", "/") + ". Puedes solucionarlo eliminando el paquete (carpeta) del jar, puedes abrir el jar en un software de archivo como WinRAR o 7z, también puedes cambiar la extensión del archivo de .jar a zip y luego eliminar la carpeta y luego cambiarla nuevamente a un archivo .jar.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Tienes Mods duplicado</b> " + linea.replace("from mod files", "de mod archivos ");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge sospechosos esta mod tiene una ploblema:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV Necesita lithostitched, puedes instalarlo aquí: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Para usar Iris Shaders o Oculus necesitas SODIUM o una copia para otra cargadora (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Problema con KubeJS extension </b>" + mod_nombre;
    }
@Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Problemas detectados con controladores NVIDIA en versiones anteriores a Windows 11."
            + "</span><br/><br/>"
            + "Para asegurarte de que Minecraft (y la JVM actual) utilice la tarjeta gráfica dedicada NVIDIA, sigue estos pasos:<br/><br/>"
            + "1. <strong>Identifica el ejecutable de Java:</strong><br/>"
            + "   - Este programa está utilizando el siguiente ejecutable de Java: "
            + obtenerRutaJava() + "<br/>"
            + "   - Si no ves una ruta específica, puedes encontrar el ejecutable de Java buscando 'java.exe' en tu sistema.<br/><br/>"
            + "2. <strong>Abre el Panel de Control de NVIDIA:</strong><br/>"
            + "   - Haz clic derecho en el escritorio y selecciona 'Panel de control de NVIDIA'.<br/><br/>"
            + "3. <strong>Configura la GPU preferida:</strong><br/>"
            + "   - En el Panel de Control de NVIDIA, ve a 'Administrar configuración 3D'.<br/>"
            + "   - Selecciona la opción 'Programa específico'.<br/>"
            + "   - Haz clic en 'Agregar' y busca el ejecutable de Java identificado anteriormente (ej.: 'java.exe').<br/>"
            + "   - Asegúrate de que esté configurado para usar el 'Procesador de alto rendimiento (NVIDIA)'.<br/><br/>"
            + "4. <strong>Guarda los cambios:</strong><br/>"
            + "   - Aplica los cambios y cierra el Panel de Control de NVIDIA.<br/><br/>"
            + "5. <strong>Reinicia Minecraft:</strong><br/>"
            + "   - Reinicia Minecraft para que los cambios surtan efecto.<br/><br/>"
            + "Si usas Windows Server 2022 o Windows 10, estos pasos son válidos siempre que tengas instalados los controladores NVIDIA más recientes.<br/><br/>"
            + "Nota: Si no puedes encontrar el Panel de Control de NVIDIA, asegúrate de que los controladores NVIDIA estén correctamente instalados.";
}

@Override
public String problema_con_graficas_nvidia_windows_nuevo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Problemas detectados con controladores NVIDIA en Windows 11/Server 2025 o posterior."
            + "</span><br/><br/>"
            + "Para asegurarte de que Minecraft (y la JVM actual) utilice la tarjeta gráfica dedicada NVIDIA, sigue estos pasos:<br/><br/>"
            + "1. <strong>Identifica el ejecutable de Java:</strong><br/>"
            + "   - Este programa está utilizando el siguiente ejecutable de Java: "
            + obtenerRutaJava() + "<br/>"
            + "   - Si no ves una ruta específica, puedes encontrar el ejecutable de Java buscando 'java.exe' en tu sistema.<br/><br/>"
            + "2. <strong>Abre la aplicación Configuración:</strong><br/>"
            + "   - Presiona las teclas <code>Win + I</code> para abrir la aplicación Configuración.<br/>"
            + "   - Navega a <strong>Sistema > Pantalla > Gráficos</strong>.<br/><br/>"
            + "3. <strong>Configura la GPU preferida:</strong><br/>"
            + "   - En la sección 'Gráficos', haz clic en 'Configuración de gráficos predeterminada'.<br/>"
            + "   - Selecciona 'Aplicaciones de escritorio' y luego haz clic en 'Examinar'.<br/>"
            + "   - Busca y selecciona el ejecutable de Java identificado anteriormente (ej.: 'java.exe').<br/>"
            + "   - Una vez añadido, selecciona la aplicación Java en la lista y configúrala para usar el 'Rendimiento alto (NVIDIA)'.<br/><br/>"
            + "4. <strong>Guarda los cambios:</strong><br/>"
            + "   - Aplica los cambios y cierra la aplicación Configuración.<br/><br/>"
            + "5. <strong>Reinicia Minecraft:</strong><br/>"
            + "   - Reinicia Minecraft para que los cambios surtan efecto.<br/><br/>"
            + "Si usas Windows 11 o Windows Server 2025+, estos pasos son válidos siempre que tengas instalados los controladores NVIDIA más recientes.<br/><br/>"
            + "Nota: Si no encuentras la opción de configuración de gráficos, asegúrate de que los controladores NVIDIA estén correctamente instalados.";
}

@Override
public String segundo60Tick() {
	// TODO Auto-generated method stub
	return "<b style='color:#" + config.obtenerColorError() + "'>Tu Serivdor o Mundo tiene tics mas de 60 segundos. Esto puede deberse a que los mods hacen que el servidor sea más lento o a que el hardware es demasiado débil.</b>";
}
    
    
@Override
public String noTieneMemoria() {
	// TODO Auto-generated method stub
	return "<b style='color:#" + config.obtenerColorError() + "'>No tienes sufficiente RAM/Memoria. Necesitas asignar mas.</b>";
}
     


@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Si estás usando Theseus/ModrinthApp no podemos ayudarte mucho porque Theseus no tiene una consola de Launcher. Theseus también tiene más problemas, incluidos versiones de Cargadores de Mods viejas, software espía, registros malos, y más. La empresa de Modrinth tampoco es honesta. Hacen acusaciones falsas de que los desarrolladores de mods usan bots para aumentar sus descargas y han cambiado sus afirmaciones de monetización muchas veces.</b>";
}


@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>No tienes launcher_log.txt. Necesitamos este archivo para buscar errores. Esto se debe a la opción \"Omitir inicio Launcher\". Desactívala.</b>";
}


@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Advertencia: Clases faltantes detectadas:</b>";
}

@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No Resultos</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Ubicación de Logs:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Aquí están los resultados de tus verificaciones. Arreglar las partes superiores de los troncos es la primera prioridad. Hazlo lentamente.</b>";
}

@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Por favor, usa Java 17 para las versiones 1.17-1.20.4 y Java 21 para cualquier versión más nueva. Usa Java 8 para versiones más antiguas. [Guía](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Si aún tienes problemas, podría ser porque algún mod tiene archivos demasiado nuevos o antiguos.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java 22 y superior no funcionan en versiones de Minecraft inferiores a 1.20.5 para la mayoría de los cargadores de mods debido a que ASM está desactualizado.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Java está obsoleto </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
	// TODO Auto-generated method stub
    return "<b style='color:#" + config.obtenerColorError() + "'>Necesitas JPMS Module"+ mod_necesitas +" desde " + submod + "</b>";
}


@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>No se puede invocar " + metodo + " porque " + objeto + " es nulo</b>";
}

@Override
public String analisisAvanzado() {
    return "Análisis Avanzado";
}

@Override
public String seleccionarCarpeta() {
    return "Seleccionar Carpeta";
}

@Override
public String cadenaBusqueda() {
    return "Cadena de Búsqueda";
}

@Override
public String usarRegex() {
    return "Usar Regex";
}

@Override
public String ignorarMayusculas() {
    return "Ignorar Mayúsculas";
}

@Override
public String buscar() {
    return "Buscar";
}

@Override
public String busquedaEnProgreso() {
    return "Búsqueda en Progreso";
}

@Override
public String noSeEncontraronResultados() {
    return "No Se Encontraron Resultados";
}

@Override
public String errorBusqueda() {
    return "Error en la Búsqueda";
}

//@Override
//public String noRegistroDeLauncher() {
//	// TODO Auto-generated method stub
//	return new String ("¡No se encontraron registros del launcher! Esto puede dificultar la investigación.\n"
//			+ "                \n"
//			+ "                Para obtener los registros correctos:\n"
//			+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTA: Los registros detectados automáticamente NO son los correctos.\n"
//			+ "                  1. Abre la interfaz de la instancia\n"
//			+ "                  2. Ve a la sección \"Minecraft Log\"\n"
//			+ "                  3. Haz clic derecho y copia el contenido\n"
//			+ "                \n"
//			+ "                - LauncherFenix:\n"
//			+ "                  1. Abre la \"Consola de Desarrollador\"\n"
//			+ "                  2. Copia el contenido completo\n"
//			+ "                \n"
//			+ "                - CurseForgeApp:\n"
//			+ "                  1. Reinicia el juego SIN saltar el launcher\n"
//			+ "                  \n"
//			+ "                ");
//}



@Override
public String noRegistroDeLauncher() {
	// TODO Auto-generated method stub
	return new String ("¡No se encontraron registros del launcher! Esto puede dificultar la investigación.\n"
			+ "                \n"
			+ "                Para obtener los registros correctos:\n"
			+ "                - MultiMC/PolyMC/PrismLauncher/PollyMC/UltimMC: NOTA: Los registros detectados automáticamente NO son los correctos.\n"
			+ "                  1. Abre la interfaz de la instancia\n"
			+ "                  2. Ve a la sección \"Minecraft Log\"\n"
			+ "                  3. Haz clic derecho y copia el contenido\n"
			+ "                - CurseForgeApp:\n"
			+ "                  1. Reinicia el juego SIN saltar el launcher\n"
			+ "                  \n"
			+ "                ");
}

@Override
public String omitirYCerrar() {
	// TODO Auto-generated method stub
	return 	"Omitir y Cerrar";
}

@Override
public String guardarYCerrar() {
	// TODO Auto-generated method stub
	return "Guardar y Cerrar";
}

@Override
public String pegaLosRegistrosAqui() {
	// TODO Auto-generated method stub
	return "Pega los registros aquí";
}

@Override
public String archivo() {
	return "Archivo";
}

@Override
public String incluir() {
	return "Incluir";
}

@Override
public String abrir() {
	return "Abrir";
}

@Override
public String endpointDeInforme() {
	// TODO Auto-generated method stub
	return "Endpoint del Informe";
}

@Override
public String sitoDeLogging() {
	// TODO Auto-generated method stub
	return "Sitio de Logging:";
}

@Override
public String apiDeLogging() {
	// TODO Auto-generated method stub
	return "API de Logging:";
}

@Override
public String anonimizarRegistros() {
	// TODO Auto-generated method stub
	return "Anonimizar registros (Beta)";
}

@Override
public String botonDeCompartirInforme() {
	// TODO Auto-generated method stub
	return "Compartir Informe y todos los registros seleccionados";
}

@Override
public String arco() {
    return "Este diálogo permite compartir registros usando la API de SecureLogger "
            + "en securelogger.net. Al presionar los botones de compartir, los archivos "
            + "se suben al sitio seleccionado (predeterminado asbestosstar.egoism.jp). Puede compartir todos los registros "
            + "seleccionados junto con el reporte. ¡Si no quieres subir, no usas este diálogo! No procesamos su informe en el punto final oficial (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); simplemente eliminamos los enlaces no permitidos, el código está aquí https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Esto solo se utiliza para mostrar la información sobre su fallo y el enlace a los registros. Sin embargo, es posible usar un punto final personalizado que podría no tener los mismos métodos. Estás usando el sitio de informes " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " y el sitio de registros " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". CrashDetector tiene anonimización de registros por defecto, que intenta eliminar nombres de usuario, UUID, tokens de acceso, ID de sesión, direcciones IP y otros datos. Sin embargo, no es perfecto. No obstante, el autor del modpack puede desactivarla. Se puede activar o desactivar con la casilla de verificación en la parte inferior de esta pantalla.";
}

@Override
public String enlaceDelReporte() {
	// TODO Auto-generated method stub
	return "Enlance de Informe:";
}

@Override
public String guardarConfigDeCompartir() {
    return "Guardar Config de Compartir";
}

@Override
public String registroDemasiadoGrande() {
    return "El registro es demasiado grande para este sitio de registro. Elija otro e inténtelo nuevamente.";
}

@Override
public String errorConPublicarRegistro(String error) {
    return "Error publicando registro " + error;
}

@Override
public String apiDeRegistroNoExiste() {
    return "API de Registro no existe. Por favor cambiar API de registro en la configuración.";
}

@Override
public String errorSSL() {
    return "Tienes Error SSL. Es común con versiones de Java viejas, "
            + "incluye las versiones de Java 8 en el Launcher Minecraft predeterminado "
            + "y las versiones en sun.com y java.com. Esto afecta a muchos aspectos, "
            + "como los archivos JAR del instalador de MinecraftForge, la función para compartir informes "
            + "de CrashDetector al usar el endpoint predeterminado, algunos mods que requieren internet "
            + "y algunos sitios de registro. Si te ocurre esto al intentar compartir un informe, "
            + "simplemente adjunta una captura de pantalla y selecciona un sitio de registro compatible "
            + "con versiones anteriores de Java 8.";
}


@Override
public String errorJavaFMLVersion(String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JavaFML Incompatible: Requiere versión " + requerido 
         + ", detectado " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "¡Atención! JavaFML requiere una versión específica de Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Proveedor " + proveedor + " incompatible: "
         + "Requiere " + requerido + ", encontrado " + encontrado + "</b>";
}

@Override
public String advertenciaMalwareFalso() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "¡ALERTA! Crash Assistant es un detector de malware falso. Bloquea el juego intencionalmente y no tiene en cuenta tu libertad de seguir jugando con los mods a los que apunta. "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/02caebaaac98e3e226337e27b226ead568363815/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java'>Ver código MalwareMod.java</a>   "
         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Ver código JarInJarHelper.java</a></b>";
}






    
}
