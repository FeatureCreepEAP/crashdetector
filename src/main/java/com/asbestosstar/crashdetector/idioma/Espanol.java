package com.asbestosstar.crashdetector.idioma;

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
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Configuración problemática de SpongeMixin detectada: " + "</b>";
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
			+ "                - MultiMC/PolyMC/PrismLauncher: NOTA: Los registros detectados automáticamente NO son los correctos.\n"
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
    return "Este diálogo te permite compartir registros utilizando la API de SecureLogger "
            + "en securelogger.net. Al presionar el botón para compartir el informe, tu informe se envía al "
            + "punto final seleccionado (por defecto asbestosstar.egoism.jp) (Se puede cambiar en la parte inferior). Puedes compartir todos los registros seleccionados "
            + "junto con el informe. Si no deseas subir, ¡no uses este diálogo! No procesamos tu informe en el punto final oficial (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); solo eliminamos enlaces no permitidos. El código está aquí: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Esto se utiliza únicamente para mostrar información sobre tu fallo y el enlace a los registros. Sin embargo, es posible usar un punto final personalizado que podría no tener los mismos métodos. Estás usando el sitio de informes " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " y el sitio de registros " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". También puedes compartir registros individuales sin un informe presionando los botones de compartir junto a los nombres de los registros individuales; los registros irán al sitio de registros seleccionado. CrashDetector tiene una anonimización de registros predeterminada, que intenta eliminar nombres de usuario, UUID, tokens de acceso, IDs de sesión, direcciones IP y otros datos. Sin embargo, no es perfecta. Aún así, el autor del modpack puede desactivarla. Se puede activar o desactivar con la casilla de verificación en la parte inferior de esta pantalla. Eres el controlador de tus propios datos; tú decides dónde subes tus datos. Los sitios de registro son propiedad de terceros cuya propiedad a menudo está oculta por privacidad. Asumes toda la responsabilidad de gestionar tus datos y los riesgos involucrados. El Diálogo de Compartir de CrashDetector es simplemente una interfaz que te permite gestionar eso. Es importante que estés al tanto del RGPD y ARCO.";
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
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "El archivo JAR '" + archivoJar + "' requiere el proveedor de lenguaje '" + proveedor + "' versión '"
         + requerido + "' o superior, pero solo se encontró la versión '" + encontrado + "'.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError() + "'>"
//         + "¡ALERTA! Crash Assistant es un detector de malware falso. Bloquea intencionalmente el lanzamiento del juego, ignorando tu libertad de seguir jugando con los mods que apunta. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Ver código MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Ver código JarInJarHelper.java</a>. "
//         + "Solo este mod está en su lista en este momento y solo están yendo realmente tras el sitio de registro predeterminado, que puede ser cambiado por el usuario, y eso solo ocurre si eliges explícitamente usar la función integrada de compartir registros. CrashAssistant NO realiza ninguna verificación para determinar qué sitio de registro se está utilizando ni explica cómo cambiarlo (hay un menú desplegable en la parte inferior del cuadro de diálogo de compartir), y sin importar qué sitio tengas configurado, CrashAssistant bloqueará el lanzamiento del juego. En su mensaje dicen que hagas tu propia investigación, HAZLO, investiga el código de CrashDetector y Crash Assistant y entiende lo que hacen, NO te bases en una apelación a la autoridad.</b>";
//}


@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "El mod '" + idMod + "' ha fallado porque no se encontró la clase requerida: '"
         + claseNoEncontrada + "'. Asegúrate de que todas las dependencias estén instaladas correctamente.</b>";
}



@Override
public String waterMediaTL() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Watermedia ha bloqeuado jugar con TLauncher.</b>";
}

@Override
public String optifineObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Estás usando una versión de Optifine para una versión de Minecraft obsoleta. Necesitas usar la versión de Optifine para la versión de Minecraft que estás usando.</b>";
}

@Override
public String servicioMLNoPudoCargar(String servicio) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "No se pudo cargar el Servicio de ModLauncher: </b>" + servicio + ".";
}

@Override
public String errorConJSONDeRegistro(String archivoJar, String recurso) {
	// TODO Auto-generated method stub
    return "<b style='color:#" + config.obtenerColorError() + "'>"
    + "Error al analizar el archivo JSON '" + recurso + "' del archivo JAR '" + archivoJar
    + "'. Tiene problemas con el registro.</b>";
}


@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
    return "<span style='color:#" + config.obtenerColorError() + "'>" 
        + "Error: El mod '" + modId + "' requiere la versión '" + requerido 
        + "' o superior de '" + dependencia + "', pero se encontró '" + actual + "'."
        + "</span>";
}

@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Tu GPU no soporta la versión de OpenGL requerida por esta Version del Juego. Actualiza tus drivers o cambia de tarjeta gráfica</b>";
}

@Override
public String recomendacionMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Aumenta la memoria asignada al juego o reduce el consumo de mods/plugins</b>";
}

@Override
public String error32BitMemoria() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "JVM de 32 bits detectada: No puede utilizar más de 4GB de RAM. "
         + "Instala una JVM de 64 bits para aprovechar toda tu memoria disponible</b>";
}

@Override
public String permGenError() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Error crítico de memoria PermGen. Aumenta el espacio de memoria permanente o reduce la carga de clases</b>";
}

 
public String errorCompatibilidadJava8() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Error de compatibilidad entre Java 8 y versiones modernas</b>";
}

public String errorJava9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 9+ no compatible - Usa Java 8 para Forge antiguo</b>";
}

public String errorJava8Requerido() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Java 8 requerido (versión 52.0). Actualiza o configura correctamente</b>";
}





@Override
public String errorDeBloqueTeselado() {
	// TODO Auto-generated method stub
	return "<b style='color:#" + config.obtenerColorError() + "'>"
    + "Error crítico de compatibilidad: Bloques no soportados en esta versión. "
    + "Verifica que las versiones de mods y servidor sean compatibles</b>";
	}

@Override
public String errorMonitorLWJGL() {
	// TODO Auto-generated method stub
	    return "<b style='color:#" + config.obtenerColorError() + "'>"
	         + "Error de configuración de monitores: "
	         + "No se pudo establecer el modo de pantalla. "
	         + "Verifica:</b>"
	         + "<br>- Configuración de múltiples monitores"
	         + "<br>- Controladores de tarjeta gráfica actualizados"
	         + "<br>- Resolución soportada por el sistema";
}

@Override
public String errorOpcionesGCJava() {
	// TODO Auto-generated method stub

    return "<b style='color:#" + config.obtenerColorError() + "'>"
    + "Error en opciones de Java: "
    + "Opciones de recolector de basura conflictivas. "
    + "Verifica que no combines múltiples algoritmos GC en los parámetros JVM</b>";


}

@Override
public String errorConfigMCForge() {
	// TODO Auto-generated method stub
    return "<b style='color:#" + config.obtenerColorError() + "'>"
    + "Error crítico de configuración Forge: "
    + "Archivo de configuración corrupto o incompleto. "
    + "Elimina la carpeta 'config' y reinicia el juego</b>";
    }


@Override
public String problema_con_graficas_intel() {
 return "<b style='color:#" + config.obtenerColorError() + "'>"
      + "Error de controlador Intel HD Graphics detectado. Soluciones:</b>"
      + "<br>1. Actualiza los controladores Intel desde <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (versión mínima 15.xx.xx.xx)"
      + "<br>2. En Minecraft: Opciones → Video → Activa 'Enable VBOs' y 'VSync'"
      + "<br>3. Asigna 1GB-2GB de RAM al juego en el launcher"
      + "<br>4. Desactiva temporalmente antivirus/firewall durante la actualización";
}


public String nombre_de_faltar_de_clases_advertencia() {
    return "Advertencia: Clases faltantes detectadas";
}

public String nombre_de_bloque_teselado() {
    return "Error de renderizado de bloques";
}

public String nombre_de_contento_de_stacktrace() {
    return "Análisis de stack trace";
}

public String nombre_de_cursed_consola() {
    return "Consola CurseForge incompleta";
}

public String nombre_de_early_window() {
    return "Error de ventana temprana (FMLEarlyWindow)";
}

public String nombre_de_drivers() {
    return "Problemas con controladores de video";
}

public String nombre_de_error_de_config_mcforge() {
    return "Configuración corrupta de MCForge";
}

public String nombre_de_error_de_monitor_lwjgl() {
    return "Fallo en modo de visualización (LWJGL)";
}

public String nombre_de_fabricmc_runtime_error_provided_by() {
    return "Error de inicialización de FabricMC";
}

public String nombre_de_falta_module_jmps() {
    return "Módulos JPMS faltantes";
}

public String nombre_de_faltar_de_clases() {
    return "Clases críticas faltantes";
}

public String nombre_de_faltas_dependencias_de_modlauncher() {
    return "Dependencias faltantes de ModLauncher";
}

public String nombre_de_java_versiones() {
    return "Versiones de Java incompatibles";
}

public String nombre_de_faltar_de_kubejs_resourcepack() {
    return "Error de recursos KubeJS";
}

public String nombre_de_lenguaje_proveedor_check() {
    return "Proveedor de lenguaje incompatible";
}

public String nombre_de_faltar_de_liyhostictchctov() {
    return "Litchhost";
}

public String nombre_de_malware_falso_crash_assistant() {
    return "Falso positivo de malware detectado";
}

public String nombre_de_mcforge_mod_sespechoso() {
    return "Mod sospechoso detectado";
}

public String nombre_de_mods_duplicados_modlauncher() {
    return "Mods duplicados en ModLauncher";
}

public String nombre_de_modules_duplicados_jmps() {
    return "Conflictos de módulos JPMS";
}

public String nombre_de_necesitas_sodium() {
    return "Sodium requerido para Iris";
}

public String nombre_de_no_puede_analizar_json_de_registro() {
    return "Error al analizar JSON de registro";
}

public String nombre_de_no_tiene_memoria() {
    return "Memoria insuficiente";
}

public String nombre_de_null_pointer() {
    return "Error de puntero nulo (NullPointerException)";
}

public String nombre_de_opciones_java_gc_invalidas() {
    return "Opciones de GC de Java inválidas";
}

public String nombre_de_optifine_obsoleta() {
    return "OptiFine obsoleta/no compatible";
}

public String nombre_de_60_segundo_trick() {
    return "Tick de servidor crítico (60s)";
}

public String nombre_de_servicio_de_modlauncher_no_funciona() {
    return "Servicios ModLauncher fallidos";
}

public String nombre_de_spongemixin_configs_problematicos() {
    return "Configuraciones SpongeMixing problemáticas";
}

public String nombre_de_theseus() {
    return "Theseus no compatible";
}

public String nombre_de_watermedia_tl() {
    return "Launcher TLauncher no soportado por WATERMeDIA";
}

@Override
public String auditorias_transformer() {
    return "Auditorías Transformer";
}

@Override
public String auditorias_transformer_detectadas() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Estos son los resultados de los contenidos de las Auditorías Transformer en el Launcher Vainilla. Generalmente es menos preciso que el analizador de StackTrace, pero el Launcher Vainilla no siempre tiene contenido de {}</b>";
}

@Override
public String descripcionEscanerMCreator() {
    return "Esto busca en tus mods aquellos creados con MCreator. Aunque la mayoría de los mods de MCreator están bien y hay muchos mods geniales hechos con MCreator, a veces tienen problemas y tienen mala reputación. Esto ayuda a identificarlos. Ten en cuenta que incluso los mejor valorados pueden no ser realmente de MCreator; por ejemplo, pueden tener integración con MCreator.";
}

@Override
public String escanear() {
    return "Escanear";
}

@Override
public String cargando() {
    return "Cargando";
}

@Override
public String codigo() {
	// TODO Auto-generated method stub
	return "es";
}



}
