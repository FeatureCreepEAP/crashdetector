package com.asbestosstar.crashdetector.idioma;

import java.util.List;

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
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Tienes Mods con Packages/Paquetes duplicados. Puedes solucionarlo eliminando el paquete (carpeta) del jar, puedes abrir el jar en un software de archivo como WinRAR o 7z, también puedes cambiar la extensión del archivo de .jar a zip y luego eliminar la carpeta y luego cambiarla nuevamente a un archivo .jar.</span>";
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

@Override
public String inicioApp() {
	// TODO Auto-generated method stub
	return "Inicio de Juego/App";
}

@Override
public String adjustesCrashDetector() {
	// TODO Auto-generated method stub
	return "Ajustes CrashDetector";
}

@Override
public String confidencialidad() {
	// TODO Auto-generated method stub
	return "Confidencialidad";
}

@Override
public String tooltip() {
	// TODO Auto-generated method stub
	return "Tooltip";
}

@Override
public String config() {
	// TODO Auto-generated method stub
	return "Configuración";
}

@Override
public String abrirCarpeta() {
	// TODO Auto-generated method stub
	return "Abrir Carpeta";
}

@Override
public String actualizar() {
	// TODO Auto-generated method stub
	return "Actualizar";
}

@Override
public String anadirRegistro() {
	// TODO Auto-generated method stub
	return "Añadir Registro";
}

@Override
public String usarIdiomaDelSistema() {
	// TODO Auto-generated method stub
	return "Usar idioma del sistema";
}

@Override
public String volver() {
	// TODO Auto-generated method stub
	return "Volver";
}

@Override
public String colorFondo() {
    return "Color de fondo (#RRGGBB):";
}

@Override
public String colorTexto() {
    return "Color de texto (#RRGGBB):";
}

@Override
public String colorBoton() {
    return "Color de botón (#RRGGBB):";
}

@Override
public String colorCajaTexto() {
    return "Color de caja de texto (#RRGGBB):";
}

@Override
public String colorEnlace() {
    return "Color de enlace (#RRGGBB):";
}

@Override
public String colorTitulosConsolas() {
    return "Color de títulos de consolas (#RRGGBB):";
}

@Override
public String colorError() {
    return "Color de error (#RRGGBB):";
}

@Override
public String colorAdvertencia() {
    return "Color de advertencia (#RRGGBB):";
}

@Override
public String colorInfo() {
    return "Color de información (#RRGGBB):";
}

@Override
public String colorTitulo() {
    return "Color de título (#RRGGBB):";
}

@Override
public String colorEnlaceTexto() {
    return "Color de enlace texto (#RRGGBB):";
}
@Override
public String transformacionDeMinecraftCodigo0() {
    return "Solo abrir CrashDetector en falla";
}

@Override
public String activar_parche() {
    return "Activar Parche";
}

@Override
public String noHaySolucionDisponible() {
    return "No Hay Solución Disponible";
}

@Override
public String error() {
    return "error";
}

@Override
public String error_al_eliminar_jar() {
    return "Error al eliminar Jar";
}

@Override
public String jar_eliminado_exitosamente() {
    return "Jar eliminado exitosamente";
}

@Override
public String exito() {
    return "éxito";
}

@Override
public String eliminar() {
    return "eliminar";
}

@Override
public String error_al_eliminar_paquete() {
    return "Error al eliminar paquete";
}

@Override
public String paquete_eliminado_exitosamente() {
    return "Paquete eliminado exitosamente";
}

@Override
public String eliminar_paquete() {
    return "Eliminar paquete";
}

@Override
public String no_se_encontraron_mods_con_paquete() {
    return "No se encontraron mods con paquete";
}

@Override
public String no_se_puede_eliminar_paquete() {
    return "No se puede eliminar paquete";
}

@Override
public String eliminar_jar() {
    return "Eliminar jar";
}

@Override
public String no_se_encontraron_mods_duplicados() {
    return "No se encontraron mods duplicados";
}
@Override
public String archivo_no_encontrado() {
    return "Archivo No Encontrado";
}

@Override
public String directorio_eliminado() {
    return "Directorio Eliminado";
}

@Override
public String error_al_eliminar_jar_anidado() {
    return "Error al eliminar jar anidado";
}

@Override
public String archivo_interno_no_encontrado() {
    return "Archivo interno no encontrado";
}

@Override
public String archivo_eliminado() {
    return "archivo eliminado";
}

@Override
public String error_al_eliminar_archivo() {
    return "error al eliminar archivo";
}

@Override
public String archivo_externo_no_valido() {
    return "archivo externo no valido";
}

@Override
public String elemento_mod_eliminado() {
    return "Elemento mod eliminado";
}

@Override
public String error_al_reemplazar_jar_externo() {
    return "Error al reemplazar jar externo";
}

@Override
public String error_al_eliminar_elemento_mod() {
    return "error al eliminar elemento mod";
}

@Override
public String error_al_eliminar_directorio() {
    return "error al eliminar directorio";
}

@Override
public String formato_invalido_para_jar_anidado() {
    return "formato invalido para jar anidado";
}

@Override
public String jar_anidado_eliminado() {
    return "jar anidado eliminado";
}

@Override
public String error_al_limpiar_temporales() {
    return "error al limpiar temporales";
}

@Override
public String mensaje_de_trace_fatal_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mensaje de Trace Fatal Última (No traducido):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mensaje de Trace Última (No traducido):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "Tienes faltas clases (Advertencia), generalmente está bien pero no siempre. Coremods malos o faltas dependencias son razones comunes para este problema.";
}

@Override
public String solucionFaltasClases() {
    return "Tienes faltas clases (FATAL), es muy importante. Coremods malos o faltas dependencias son razones comunes para este problema.";
}

@Override
public String solucionParaJavaInstallar() {
    return "Ambos lanzadores tienen las versiones de Java correctas pero no todas, puedes instalar la versión de Java correcta desde el Administrador de paquetes en tu sistema o con los botones.";
}

@Override
public String error_animacion_no_encontrada() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mod con Animación Falta: " + "</b>";
}

@Override
public String nombre_de_error_animacion_minecraft() {
    return "NoSuchElementException (Excepción No Elemento) Falta Animación";
}

@Override
public String no_se_encontraron_mods_para_eliminar() {
    return "No se encontraron mods para eliminar";
}

@Override
public String opcionesGCInvalidas() {
    return "Reemplazar opciones de GC conflictivas con -XX:+UseG1GC";
}

@Override
public String aumentarMemoriaHeap() {
    return "Aumentar el tamaño de la memoria del heap utilizando la opción -Xmx.";
}

@Override
public String aumentarMemoriaPermgen() {
    return "Aumentar el tamaño de la memoria permgen utilizando la opción -XX:MaxPermSize.";
}

@Override
public String utilizarJVM64Bits() {
    return "Utilizar una JVM de 64 bits para aumentar la memoria disponible.";
}

@Override
public String optimizarCodigo() {
    return "Optimizar el código para reducir el uso de memoria y mejorar el rendimiento.";
}

@Override
public String utilizarRecolectorBasuraEficiente() {
    return "Utilizar un recolector de basura eficiente para reducir la pausa de la aplicación.";
}

@Override
public String modulos() {
    return "Módulos";
}

@Override
public String paquete() {
    return "Paquete";
}

@Override
public String solucionRegistrosMalMapeados() {
    return "Hay faltas IDs. Causas comunes son mods faltantes o datos de artículos faltantes. Carpetas comunes de datos son datafiedcontents/ y kubejs/ u otras carpetas de mods.";
}

@Override
public String nombre_de_registros_mal_mapeados() {
    return "registros mal mapeados";
}


/**
 * Devuelve el mensaje de error para el cierre causado por AuthMe.
 */
public String mensajeCierreAuthMe() {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin 'AuthMe' falló al cargar y ha cerrado el servidor.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
public String nombreProblemaCierreAuthMe() {
    return "Problema de cierre por AuthMe";
}

/**
 * Devuelve la solución de cierre del servidor por AuthMe.
 */
public String solucionCierreAuthMe() {
    return "La regla 'stopServer' cambió a 'true'.";
}

/**
 * Devuelve la solución de configuración del plugin AuthMe.
 */
public String solucionConfigurarPluginAuthMe() {
    return "Configurar el plugin AuthMe (plugins/AuthMe/config.yml)";
}

/**
 * Devuelve la solución de instalar otra versión del plugin AuthMe.
 */
public String solucionInstalarVersionDiferenteAuthMe() {
    return "Instalar una versión diferente del plugin 'AuthMe'";
}

/**
 * Devuelve la solución de eliminar el plugin AuthMe.
 */
public String solucionEliminarPluginAuthMe() {
    return "Eliminar el plugin 'AuthMe'";
}



/**
 * Devuelve el mensaje de error para mundos corruptos por Multiverse.
 */
@Override
public String mensajeProblemaCargaMultiverso(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mundo '" + nombreMundo + "' no se pudo cargar porque contiene errores y probablemente está corrupto.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaCargaMultiverso() {
    return "Problema de carga de Multiverse";
}

/**
 * Devuelve la solución de reparar el mundo.
 */
@Override
public String solucionRepararMundo(String nombreMundo) {
    return "Repara el mundo '" + nombreMundo + "', por ejemplo usando Minecraft Region Fixer o MCEdit.";
}

/**
 * Devuelve la solución de eliminar la carpeta del mundo.
 */
@Override
public String solucionEliminarCarpetaMundo(String nombreMundo) {
    return "Elimina la carpeta del mundo '" + nombreMundo + "'.";
}





/**
 * Devuelve el mensaje de error para configuración inválida de PermissionsEx.
 */
@Override
public String mensajeConfiguracionPermissionsEx() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La configuración de la extensión 'PermissionsEx' es inválida.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaConfiguracionPermissionsEx() {
    return "Problema de configuración de PermissionsEx";
}

/**
 * Devuelve la solución de configurar PermissionsEx.
 */
@Override
public String solucionConfigurarPermissionsEx() {
    return "Configurar el plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
}

/**
 * Devuelve la solución de eliminar el plugin PermissionsEx.
 */
@Override
public String solucionEliminarPluginPermissionsEx() {
    return "Eliminar el plugin 'PermissionsEx'";
}




/**
 * Devuelve el mensaje de error para plugins con nombre ambiguo.
 */
@Override
public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Hay múltiples archivos de plugins para el plugin de nombre '" + nombrePlugin + "': '" + primerPath + "' y '" + segundoPath + "'.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaNombrePluginAmbiguo() {
    return "Problema de nombre ambiguo de plugin";
}

/**
 * Devuelve la solución de eliminar un plugin específico.
 */
@Override
public String solucionEliminarPlugin(String nombrePlugin) {
    return "Eliminar el plugin '" + nombrePlugin + "'";
}






/**
 * Devuelve el mensaje de error para excepciones al cargar chunks.
 */
@Override
public String mensajeCargaChunk() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Hubo una excepción mientras el mundo cargaba los chunks.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaCargaChunk() {
    return "Excepción al cargar chunks";
}



/**
 * Devuelve la solución de eliminar el chunk problemático.
 */
@Override
public String solucionEliminarChunk() {
    return "Quita el chunk problemático del mundo, por ejemplo con MCEdit o quitando el archivo de región.";
}






/**
 * Devuelve el mensaje de error para excepciones al ejecutar comandos de plugins.
 */
@Override
public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + "' no puede ejecutar el comando '/" + comando + "'.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaExcepcionComandoPlugin() {
    return "Excepción al ejecutar comando de plugin";
}

/**
 * Devuelve la solución de instalar otra versión del plugin.
 */
@Override
public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
    return "Instalar una versión diferente del plugin '" + nombrePlugin + "'";
}










/**
 * Devuelve el mensaje de error para dependencias individuales.
 */
@Override
public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + 
           "' necesita como dependencia la extensión '" + dependencia + "'.</b> ";
}

/**
 * Devuelve el mensaje de error para múltiples dependencias.
 */
@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + 
           "' es falto de los siguientes plugins requeridos " + deps.toString() + ".</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaDependenciaPlugin() {
    return "Dependencia de plugin faltante";
}

/**
 * Devuelve la solución de instalar un plugin específico.
 */
@Override
public String solucionInstalarPlugin(String nombrePlugin) {
    return "Instalar el plugin '" + nombrePlugin + "'";
}





/**
 * Devuelve el mensaje de error para versión de API incompatible.
 */
@Override
public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + 
           "' necesita la versión de API '" + versionAPI + "' que no es compatible con el servidor actual.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaVersionAPIIncompatible() {
    return "Versión de API incompatible";
}

/**
 * Devuelve la solución de instalar una versión específica del servidor.
 */
@Override
public String solucionInstalarVersionServidor(String version) {
    return "Instala la versión '" + version + "' del software de tu servidor.";
}




@Override
public String mensajeMundoDuplicado(String nombreMundo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mundo '" + nombreMundo + 
           "' es un duplicado de otro mundo y no se puede cargar.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaMundoDuplicado() {
    return "Mundo Duplicado";
}

/**
 * Devuelve la solución de eliminar el archivo 'uid.dat' del mundo.
 */
@Override
public String solucionEliminarUID(String nombreMundo) {
    return "Elimina el archivo 'uid.dat' del mundo '" + nombreMundo + "'";
}

/**
 * Devuelve la solución de eliminar la carpeta completa del mundo.
 */
@Override
public String solucionEliminarMundo(String nombreMundo) {
    return "Elimina la carpeta del mundo '" + nombreMundo + "'";
}


/**
 * Devuelve el mensaje de error para entidades de bloque problemáticas.
 */
@Override
public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "<b style='color:#" + config.obtenerColorError() + "'>La entidad de bloque '" + nombre + 
           "' del tipo '" + tipo + "' en la ubicación " + coords + " está causando errores durante el ticking.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaTickingEntidadBloque() {
    return "Entidad de Bloque Problemática";
}

/**
 * Devuelve la solución de eliminar la entidad de bloque.
 */
@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "Elimina la entidad '" + nombre + "' en la ubicación " + coords + " usando MCEdit o editando el mundo directamente.";
}





/**
 * Devuelve el mensaje de error para mods duplicados en Fabric.
 */
@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + "' tiene múltiples versiones instaladas.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModDuplicadoFabric() {
    return "Mod Duplicado en Fabric";
}

/**
 * Devuelve la solución de eliminar el archivo duplicado.
 */
@Override
public String solucionEliminarModDuplicado(String rutaMod) {
    return "Elimina el archivo duplicado del mod: " + rutaMod;
}

/**
 * Devuelve el mensaje de error para mods incompatibles.
 */
@Override
public String mensajeModIncompatible(String primerMod, String segundoMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Los mods '" + primerMod + 
           "' y '" + segundoMod + "' son incompatibles entre sí.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModIncompatibleFabric() {
    return "Mod Incompatible en Fabric";
}

/**
 * Devuelve la solución de eliminar el primer mod incompatible.
 */
@Override
public String solucionEliminarMod(String nombreMod) {
    return "Elimina el mod '" + nombreMod + "'";
}





/**
 * Devuelve el mensaje de error para mods con problemas fatales.
 */
@Override
public String mensajeModFatal(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + "' tiene un error crítico y no puede ejecutarse.</b> ";
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModFatal() {
    return "Mod con Error Crítico";
}





/**
 * Devuelve el mensaje de error para dependencias faltantes en mods (plural).
 */
@Override
public String mensajeModDependenciaPlural(List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) {
            deps.append(", ");
        }
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>Los siguientes mods son requeridos y no están instalados: " + deps.toString() + ".</b>";
}

/**
 * Devuelve el mensaje de error para dependencias faltantes en mods (singular).
 */
@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + 
               "' necesita como dependencia el mod '" + dependencia + "'.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + 
               "' necesita el mod '" + dependencia + "' con versión " + version + ".</b>";
    }
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaDependenciaMod() {
    return "Dependencia de mod faltante";
}

/**
 * Devuelve la solución de instalar un mod específico.
 */
@Override
public String solucionInstalarMod(String nombreMod) {
    return "Instale el mod '" + nombreMod + "'";
}

/**
 * Devuelve la solución de instalar un mod con versión específica.
 */
@Override
public String solucionInstalarModConVersion(String nombreMod, String version) {
    return "Instale el mod '" + nombreMod + "' con la versión " + version;
}




/**
 * Devuelve el mensaje de error para plugins que no soportan ticking regional (singular).
 */
@Override
public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + 
           "' no es compatible con ticking regional de Folia.</b> ";
}

/**
 * Devuelve el mensaje de error para múltiples plugins que no soportan ticking regional (plural).
 */
@Override
public String mensajePluginTickingRegionalPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes plugins no son compatibles con el ticking regional de Folia: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaPluginTickingRegional() {
    return "Plugin incompatible con Ticking Regional";
}


/**
 * Devuelve la solución de instalar un software sin ticking regional.
 */
@Override
public String solucionInstalarSoftwareSinTickingRegional(String software) {
    return "Instalar un software sin ticking regional, como " + software;
}











/**
 * Devuelve el mensaje de error para un mod faltante en datapack (singular).
 */
@Override
public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + 
           "' está faltando en el datapack.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples mods faltantes en datapack (plural).
 */
@Override
public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes mods están faltando en el datapack: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModFaltanteEnDatapack() {
    return "Mod faltante en datapack";
}






/**
 * Devuelve el mensaje de error para un mod que generó una excepción (singular).
 */
@Override
public String mensajeModExcepcionSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + "' ha generado un error.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples mods que generaron excepciones (plural).
 */
@Override
public String mensajeModExcepcionPlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes mods han generado errores: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModExcepcion() {
    return "Mod de Forge con Excepción";
}

/**
 * Devuelve la solución de instalar una versión diferente del mod.
 */
@Override
public String solucionInstalarVersionDiferenteMod(String nombreMod) {
    return "Instalar una versión diferente del mod '" + nombreMod + "'";
}





/**
 * Devuelve el mensaje de error para un mod incompatible con la versión de Minecraft (singular).
 */
@Override
public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + 
           "' no es compatible con la versión de Minecraft " + versionMC + ".</b>";
}

/**
 * Devuelve el mensaje de error para múltiples mods incompatibles con Minecraft (plural).
 */
@Override
public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes mods no son compatibles con las versiones de Minecraft: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append(" (Minecraft ").append(versionesMC.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaModIncompatibleConMinecraft() {
    return "Mod incompatible con Minecraft";
}


/**
 * Devuelve la solución de instalar una versión diferente de Forge.
 */
@Override
public String solucionInstalarVersionForge(String versionMC) {
    return "Instalar una versión de Forge compatible con Minecraft " + versionMC;
}







/**
 * Devuelve el mensaje de error para un mod faltante (singular).
 */
@Override
public String mensajeDependenciaModFaltante(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + "' está faltando y es requerido para cargar el mundo o plugin.</b>";
}



/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaDependenciaModFaltante() {
    return "Mod faltante";
}










/**
 * Devuelve el mensaje de error para un mod faltante en el mundo (singular).
 */
@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mundo fue guardado con el mod '" + nombreMod + 
           "' que parece estar ausente.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples mods faltantes en el mundo (plural).
 */
@Override
public String mensajeWorldModFaltantePlural(List<String> mods) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("El mundo fue guardado con los siguientes mods que parecen estar ausentes: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaWorldModFaltante() {
    return "Mod faltante en el mundo";
}













/**
 * Devuelve el mensaje de error para discrepancia de versión de mod en un mundo (singular).
 */
@Override
public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El mundo fue guardado con el mod '" + nombreMod + 
           "' versión " + versionEsperada + ", y ahora está en la versión " + versionActual + ".</b>";
}

/**
 * Devuelve el mensaje de error para discrepancias de versión de mods en múltiples mods (plural).
 */
@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes mods tienen discrepancias de versión en el mundo guardado: ");
    
    for (int i = 0; i < mods.size(); i++) {
        if (i > 0) {
            if (i == mods.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(mods.get(i)).append("'");
        sb.append(" (Guardado: ").append(versionesEsperadas.get(i)).append(", Actual: ").append(versionesActuales.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaVersionModMundo() {
    return "Versión de mod en el mundo guardado";
}










/**
 * Devuelve el mensaje de error para intentar cargar un mundo desde una versión más reciente.
 */
@Override
public String mensajeVersionDowngrade() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Intentaste cargar un mundo creado con una versión más nueva de Minecraft.</b>";
}


/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaVersionDowngrade() {
    return "Intento de cargar un mundo desde una versión inferior";
}

/**
 * Devuelve la solución de instalar una versión más nueva de Minecraft.
 */
@Override
public String solucionVersionDowngrade() {
    return "Instalar una nueva versión de Minecraft.";
}

/**
 * Devuelve la solución de generar un nuevo mundo.
 */
@Override
public String solucionGenerarNuevoMundo() {
    return "Generar un nuevo mundo.";
}



/**
 * Devuelve el mensaje de error para un plugin con dependencia faltante (singular).
 */
@Override
public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + 
           "' necesita como dependencia el plugin '" + dependencia + "'.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples plugins con dependencias faltantes (plural).
 */
@Override
public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes plugins necesitan dependencias que no están instaladas: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            sb.append(", ");
        }
        sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaDependenciaPluginFaltante() {
    return "Plugin con dependencia faltante";
}


/**
 * Devuelve el mensaje de error para un plugin incompatible (singular).
 */
@Override
public String mensajePluginIncompatibleSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>La extensión '" + nombrePlugin + "' es incompatible con la versión actual del servidor.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples plugins incompatibles (plural).
 */
@Override
public String mensajePluginIncompatiblePlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Las siguientes extensiones son incompatibles con la versión actual del servidor: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaPluginIncompatible() {
    return "Plugin incompatible con PocketMine-MP";
}


/**
 * Devuelve el mensaje de error para un plugin con error de ejecución (singular).
 */
@Override
public String mensajePluginEjecucionSingular(String nombrePlugin) {
    return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin + "' ha generado un error durante la ejecución.</b>";
}

/**
 * Devuelve el mensaje de error para múltiples plugins con errores de ejecución (plural).
 */
@Override
public String mensajePluginEjecucionPlural(List<String> plugins) {
    StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
    sb.append("Los siguientes plugins han generado errores durante la ejecución: ");
    
    for (int i = 0; i < plugins.size(); i++) {
        if (i > 0) {
            if (i == plugins.size() - 1) {
                sb.append(" y ");
            } else {
                sb.append(", ");
            }
        }
        sb.append("'").append(plugins.get(i)).append("'");
    }
    
    sb.append(".</b>");
    return sb.toString();
}

/**
 * Devuelve el nombre del problema para mostrar en la interfaz.
 */
@Override
public String nombreProblemaPluginEjecucion() {
    return "Plugin con error de ejecución";
}

@Override
public String nombreLegacyRandomSourceMultiHilos() {
	// TODO Auto-generated method stub
	return "LegacyRandomSource Multi Hilos";
}

@Override
public String mensajeLegacyRandomSourceMultiHilos() {
	// TODO Auto-generated method stub
    return "<b style='color:#" + config.obtenerColorError() + "'>Tienes problema con mutiple hilos acesando la clase LegacyRandomSource. Puedes obtaner mas information con la mod Unsafe World Random Access Detector o la mod C2ME.</b> ";


}





}
