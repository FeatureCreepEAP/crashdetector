package com.asbestosstar.crashdetector.idioma;

import java.util.List;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Espanol implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>no es una carpeta de mods valida</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>No se donde esta el archivo JAR de CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Buscando para PID: " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") esta muerto!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>No tenemos JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Actualizar tus controladores ATI/AMD podría ayudarte.Lee esta guía para solucionarlo: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Guía de actualización de controladores</a> https://www.amd.com/es/support/download/drivers.html Descargar </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Algunas versiones antiguas a veces tienen algunos problemas con algunos gráficos Nouveau en la pantalla de carga temprana.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tienes un problema con tus controladores gráficos. Si tienes una GPU o APU AMD/ATI, actualiza tus controladores gráficos AMD. Si tienes una tarjeta gráfica NVIDIA, asegúrate de marcar el juego y todas las instancias de javaw.exe para usar la tarjeta gráfica dedicada. Lee esta guía: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guía de actualización de controladores</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tu ventana FML Early está fallando. Para cambiar esto, ve a (.minecraft/config/fml.toml) y edita earlyWindowProvider para que sea earlyWindowProvider=\"none\". Si estás en una Mac M1, también asegúrate de estar usando una versión ARM de Java, no una versión Intel x64. Este también es un problema común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guía de actualización de controladores</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>No tienes todas las dependencias necesarias:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Solicitado por").replace("Expected range", "rango esperado")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Tu Informe de CrashDetector esta aqui <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>ver informe</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Esta es el GUI de CrashDetector. Si el juego cierra sin problemas, ignoralo.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Ver Informe</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Ver Un Informe Local en tu navegador.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Compartir Informe";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Compartir Informe. Sus registros se cargarán en securelogger.net y tu informe se cargará a otro sitio.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Archivos JAR problemáticos detectados (Priorizar FATAL, luego nivel alto y línea baja):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> nivel:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Posiblemente Fatal:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ModIDs problemáticos detectados (Priorizar FATAL, luego nivel bajo y línea baja):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Paquetes problemáticos detectados (Priorizar FATAL, luego nivel bajo y línea baja):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes Clases fatales (FATAL), es muy importante, causas comunes son Coremods Malos o Fatals Dependencias. Puedes Usar QuickFix para buscar para mods con las clases fatales. Clases fatales faltantes detectadas:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Contenido en {} (Lo más importante arriba, solo 20 primeros):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Configuración problemática de SpongeMixin detectada: " + "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tienes Mods con Packages/Paquetes duplicados. Puedes solucionarlo eliminando el paquete (carpeta) del jar, puedes abrir el jar en un software de archivo como WinRAR o 7z, también puedes cambiar la extensión del archivo de .jar a zip y luego eliminar la carpeta y luego cambiarla nuevamente a un archivo .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Tienes Mods duplicado</b> "
				+ linea.replace("from mod files", "de mod archivos ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge sospechosos esta mod tiene una ploblema:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV Necesita lithostitched, puedes instalarlo aquí: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Para usar Iris Shaders o Oculus necesitas SODIUM o una copia para otra cargadora (Rubidium, Embedium, Bedium)</b>";
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
				+ "   - Este programa está utilizando el siguiente ejecutable de Java: " + obtenerRutaJava() + "<br/>"
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
				+ "   - Este programa está utilizando el siguiente ejecutable de Java: " + obtenerRutaJava() + "<br/>"
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tu Serivdor o Mundo tiene tics mas de 60 segundos. Esto puede deberse a que los mods hacen que el servidor sea más lento o a que el hardware es demasiado débil.</b>";
	}

	@Override
	public String noTieneMemoria() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>No tienes sufficiente RAM/Memoria. Necesitas asignar mas.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Si estás usando Theseus/ModrinthApp no podemos ayudarte mucho porque Theseus no tiene una consola de Launcher. Theseus también tiene más problemas, incluidos versiones de Cargadores de Mods viejas, software espía, registros malos, y más. La empresa de Modrinth tampoco es honesta. Hacen acusaciones falsas de que los desarrolladores de mods usan bots para aumentar sus descargas y han cambiado sus afirmaciones de monetización muchas veces.</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>No tienes launcher_log.txt. Necesitamos este archivo para buscar errores. Esto se debe a la opción \"Omitir inicio Launcher\". Desactívala.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Advertencia: Tienes faltas clases (Advertencia), generalmente está bien pero no siempre, es diferente de faltas clases fatalas. Coremods malos o faltas dependencias son razones comunes para este problema. Puedes usar QuickFix para buscar para mods con los clases. Clases faltantes detectadas:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>No Resultos</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Ubicación de Logs:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Aquí están los resultados de tus verificaciones. Hazlo lentamente, generlmente la razon correcto es en verificion 1 o 2, otras (errores 3+) puedes usar para confirmacion pero generalment son errores cascades y puedes ignorar son. Los fallos se producen en capas, por lo que solucionar el problema correcto solucionará este error en particular hoy, pero es posible que mañana vuelva a aparecer un nuevo error no relacionado con el error actual, ya que a menudo un error puede impedir que otro aparezca en la consola.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Por favor, usa Java 17 para las versiones 1.17-1.20.4 y Java 21 para cualquier versión más nueva. Usa Java 8 para versiones más antiguas. [Guía](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Si aún tienes problemas, podría ser porque algún mod tiene archivos demasiado nuevos o antiguos.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 y superior no funcionan en versiones de Minecraft inferiores a 1.20.5 para la mayoría de los cargadores de mods debido a que ASM está desactualizado.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java está obsoleto </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>Necesitas JPMS Module" + mod_necesitas + " desde "
				+ submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>No se puede invocar " + metodo + " porque "
				+ objeto + " es nulo</b>";
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
	public String omitirYCerrar() {
		// TODO Auto-generated method stub
		return "Omitir y Cerrar";
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
				+ Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado()
				+ ". También puedes compartir registros individuales sin un informe presionando los botones de compartir junto a los nombres de los registros individuales; los registros irán al sitio de registros seleccionado. CrashDetector tiene una anonimización de registros predeterminada, que intenta eliminar nombres de usuario, UUID, tokens de acceso, IDs de sesión, direcciones IP y otros datos. Sin embargo, no es perfecta. Aún así, el autor del modpack puede desactivarla. Se puede activar o desactivar con la casilla de verificación en la parte inferior de esta pantalla. Eres el controlador de tus propios datos; tú decides dónde subes tus datos. Los sitios de registro son propiedad de terceros cuya propiedad a menudo está oculta por privacidad. Asumes toda la responsabilidad de gestionar tus datos y los riesgos involucrados. El Diálogo de Compartir de CrashDetector es simplemente una interfaz que te permite gestionar eso. Es importante que estés al tanto del RGPD y ARCO.";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML Incompatible: Requiere versión "
				+ requerido + ", detectado " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "¡Atención! JavaFML requiere una versión específica de Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "El archivo JAR '" + archivoJar
				+ "' requiere el proveedor de lenguaje '" + proveedor + "' versión '" + requerido
				+ "' o superior, pero solo se encontró la versión '" + encontrado + "'.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "El mod '" + idMod
				+ "' ha fallado porque no se encontró la clase requerida: '" + claseNoEncontrada
				+ "'. Asegúrate de que todas las dependencias estén instaladas correctamente.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error al analizar el archivo JSON '" + recurso
				+ "' del archivo JAR '" + archivoJar + "'. Tiene problemas con el registro.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: El mod '" + modId
				+ "' requiere la versión '" + requerido + "' o superior de '" + dependencia + "', pero se encontró '"
				+ actual + "'." + "</span>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error de configuración de monitores: "
				+ "No se pudo establecer el modo de pantalla. " + "Verifica:</b>"
				+ "<br>- Configuración de múltiples monitores" + "<br>- Controladores de tarjeta gráfica actualizados"
				+ "<br>- Resolución soportada por el sistema";
	}

	@Override
	public String errorOpcionesGCJava() {
		// TODO Auto-generated method stub

		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error en opciones de Java: "
				+ "Opciones de recolector de basura conflictivas. "
				+ "Verifica que no combines múltiples algoritmos GC en los parámetros JVM</b>";

	}

	@Override
	public String errorConfigMCForge() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error crítico de configuración Forge: "
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

	public String nombre_de_contenido_de_stacktrace() {
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
	public String ajustesCrashDetector() {
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mensaje de Trace Fatal Última (No traducido):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mensaje de Trace Última (No traducido):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Puedes buscar en la base de datos WaifuNeoForge para encontrar mods. Selecciona la versión del juego, el cargador de mods y la clase. Usa la combinación más similar. Puedes buscar una vez por minuto.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Puedes buscar en la base de datos WaifuNeoForge para encontrar mods. Selecciona la versión del juego, el cargador de mods y la clase. Usa la combinación más similar. Puedes buscar una vez por minuto.";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>El plugin 'AuthMe' falló al cargar y ha cerrado el servidor.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mundo '" + nombreMundo
				+ "' no se pudo cargar porque contiene errores y probablemente está corrupto.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La configuración de la extensión 'PermissionsEx' es inválida.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Hay múltiples archivos de plugins para el plugin de nombre '" + nombrePlugin + "': '" + primerPath
				+ "' y '" + segundoPath + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Hubo una excepción mientras el mundo cargaba los chunks.</b> ";
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
	 * Devuelve el mensaje de error para excepciones al ejecutar comandos de
	 * plugins.
	 */
	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' no puede ejecutar el comando '/" + comando + "'.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' necesita como dependencia la extensión '" + dependencia + "'.</b> ";
	}

	/**
	 * Devuelve el mensaje de error para múltiples dependencias.
	 */
	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' es falto de los siguientes plugins requeridos " + deps.toString() + ".</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' necesita la versión de API '" + versionAPI + "' que no es compatible con el servidor actual.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mundo '" + nombreMundo
				+ "' es un duplicado de otro mundo y no se puede cargar.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La entidad de bloque '" + nombre + "' del tipo '"
				+ tipo + "' en la ubicación " + coords + " está causando errores durante el ticking.</b> ";
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
		return "Elimina la entidad '" + nombre + "' en la ubicación " + coords
				+ " usando MCEdit o editando el mundo directamente.";
	}

	/**
	 * Devuelve el mensaje de error para mods duplicados en Fabric.
	 */
	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' tiene múltiples versiones instaladas.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Los mods '" + primerMod + "' y '" + segundoMod
				+ "' son incompatibles entre sí.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' tiene un error crítico y no puede ejecutarse.</b> ";
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Los siguientes mods son requeridos y no están instalados: " + deps.toString() + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para dependencias faltantes en mods (singular).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
					+ "' necesita como dependencia el mod '" + dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod + "' necesita el mod '"
					+ dependencia + "' con versión " + version + ".</b>";
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
	 * Devuelve el mensaje de error para plugins que no soportan ticking regional
	 * (singular).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' no es compatible con ticking regional de Folia.</b> ";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins que no soportan ticking
	 * regional (plural).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' está faltando en el datapack.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en datapack
	 * (plural).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' ha generado un error.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods que generaron excepciones
	 * (plural).
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
	 * Devuelve el mensaje de error para un mod incompatible con la versión de
	 * Minecraft (singular).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' no es compatible con la versión de Minecraft " + versionMC + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods incompatibles con Minecraft
	 * (plural).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mod '" + nombreMod
				+ "' está faltando y es requerido para cargar el mundo o plugin.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>El mundo fue guardado con el mod '" + nombreMod
				+ "' que parece estar ausente.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples mods faltantes en el mundo
	 * (plural).
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
	 * Devuelve el mensaje de error para discrepancia de versión de mod en un mundo
	 * (singular).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El mundo fue guardado con el mod '" + nombreMod
				+ "' versión " + versionEsperada + ", y ahora está en la versión " + versionActual + ".</b>";
	}

	/**
	 * Devuelve el mensaje de error para discrepancias de versión de mods en
	 * múltiples mods (plural).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
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
			sb.append(" (Guardado: ").append(versionesEsperadas.get(i)).append(", Actual: ")
					.append(versionesActuales.get(i)).append(")");
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
	 * Devuelve el mensaje de error para intentar cargar un mundo desde una versión
	 * más reciente.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Intentaste cargar un mundo creado con una versión más nueva de Minecraft.</b>";
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
	 * Devuelve el mensaje de error para un plugin con dependencia faltante
	 * (singular).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' necesita como dependencia el plugin '" + dependencia + "'.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con dependencias
	 * faltantes (plural).
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
		return "<b style='color:#" + config.obtenerColorError() + "'>La extensión '" + nombrePlugin
				+ "' es incompatible con la versión actual del servidor.</b>";
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
	 * Devuelve el mensaje de error para un plugin con error de ejecución
	 * (singular).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>El plugin '" + nombrePlugin
				+ "' ha generado un error durante la ejecución.</b>";
	}

	/**
	 * Devuelve el mensaje de error para múltiples plugins con errores de ejecución
	 * (plural).
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
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes problema con mutiple hilos acesando la clase LegacyRandomSource. Puedes obtaner mas information con la mod Unsafe World Random Access Detector o la mod C2ME.</b> ";

	}

	@Override
	public String desdeUltimoExito() {
		// TODO Auto-generated method stub
		return "Desde Ultima Exito";
	}

	@Override
	public String noHayCambios() {
		// TODO Auto-generated method stub
		return "No Hay Cambios";
	}

	@Override
	public String desdeUltimoIntento() {
		// TODO Auto-generated method stub
		return "Desde Ultimo Intento";
	}

	@Override
	public String fallo() {
		// TODO Auto-generated method stub
		return "Fallo";
	}

	@Override
	public String diferentesDeLasMods() {
		// TODO Auto-generated method stub
		return "Diferentes De Las Mods";
	}

	@Override
	public String historialDeMods() {
		// TODO Auto-generated method stub
		return "HistorialDeMods";
	}

	@Override
	public String archivo0() {
		// TODO Auto-generated method stub
		return "Archivo0";
	}

	@Override
	public String archivo1() {
		// TODO Auto-generated method stub
		return "Archivo1";
	}

	@Override
	public String comparar() {
		// TODO Auto-generated method stub
		return "Comparar";
	}

	@Override
	public String seleccionarDosArchivos() {
		// TODO Auto-generated method stub
		return "Seleccionar Dos Archivos";
	}

	@Override
	public String archivoExito() {
		// TODO Auto-generated method stub
		return "Archivo Exito";
	}

	@Override
	public String archivoFalla() {
		// TODO Auto-generated method stub
		return "Archivo Falla";
	}

	@Override
	public String errorComparandoArchivos() {
		// TODO Auto-generated method stub
		return "Error Comparando Archivos";
	}

	@Override
	public String comparando() {
		// TODO Auto-generated method stub
		return "Comparando";
	}

	@Override
	public String con() {
		// TODO Auto-generated method stub
		return "Con";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Panel de Comparación de Historial de Mods</b></p>"
				+ "<p>Este panel le permite comparar dos listas de mods (módulos) de diferentes sesiones de ejecución. "
				+ "Seleccione un archivo de la columna izquierda y otro de la derecha para ver los cambios realizados entre ambas versiones.</p>"

				+ "<h3>Cómo usarlo:</h3>" + "<ol>"
				+ "<li><b>Selección de archivos:</b> Haga clic en los botones de radio junto a los nombres de los archivos. "
				+ "Los archivos terminados en <span style='color: #4CAF50; font-weight: bold;'>.exito</span> indican sesiones exitosas, "
				+ "mientras que los de <span style='color: #F44336; font-weight: bold;'>.falla</span> corresponden a fallos.</li>"

				+ "<li><b>Comparación automática:</b> Al presionar el botón 'Compare', el sistema analizará las dos listas seleccionadas "
				+ "y mostrará los mods añadidos (+) o eliminados (-) en cada dirección.</li>"

				+ "<li><b>Visualización de resultados:</b> Los resultados se presentan en formato HTML con colores codificados: "
				+ "<ul>" + "<li><span style='color: green;'>+ Mod añadido</span></li>"
				+ "<li><span style='color: red;'>- Mod eliminado</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Características clave:</h3>" + "<ul>"
				+ "<li>Soporta selección de cualquier combinación de archivos (éxito/fallo).</li>"
				+ "<li>Muestra diferencias bidireccionales para identificar cambios precisos.</li>"
				+ "<li>Incluye scroll para navegar por listas largas de mods.</li>"
				+ "<li>Integración con imágenes explicativas para mejorar la comprensión visual.</li>" + "</ul>"

				+ "<p>Desarrollado con <3️ para ayudarle a rastrear modificaciones en sus configuraciones.</p>"
				+ "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Es posible que tengas un problema relacionado con IPv6. " + "Existen dos soluciones: "
				+ "1) Agregar el argumento JVM <code>-Djava.net.preferIPv4Stack=true</code> en tu lanzador, o "
				+ "2) Usar el botón 'QuickFix' en CrashDetector para aplicar un parche que active esta configuración automáticamente."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		// TODO Auto-generated method stub
		return "Parche IPV4/6";
	}

	@Override
	public String carpetaHMCL() {
		// TODO Auto-generated method stub
		return "Carpeta HMCL (Solo para HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Nota: No se genera registro si está activada la opción \"Saltar Launcher\" en Ajustes > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derivados: Haga clic derecho sobre la instancia y seleccione \"Editar Instancia\". En la ventana que se abre, busque la sección \"Registros de Minecraft\" o similar.<br>"
				+ "Estos registros contienen la salida estándar (STDOUT) que es fundamental para diagnosticar errores.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Debe seleccionar la carpeta donde HMCL está instalado y elegir la carpeta \".hcml\". Los registros de HMCL se guardan en esta ubicación y contienen información importante sobre errores.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: El lanzador tiene una pestaña de desarrollo que muestra registros detallados. Puede encontrar esta pestaña en el menú de opciones del lanzador.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Hay un diálogo emergente con los registros. Tiene botones para copiar y subir los registros. Los registros se generan automáticamente al iniciar el juego y contienen información crítica para diagnóstico.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Haga clic derecho sobre la instancia y seleccione \"Configuración\". Luego vaya a la sección de Registros para ver la información importante que contiene la salida estándar (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Enlaces con Markdown: Pegue aquí cualquier enlace que contenga registros en formato Markdown. El sistema intentará extraer automáticamente los enlaces a registros (latest.log, launcher_log.txt, debug.log, etc.) y analizarlos.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Registro de Launcher No Encontrado";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Imagen no encontrada";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "GENÉRICO: Seleccione el tipo de launcher que está utilizando. Los registros del launcher (launcher_log.txt, stdout, etc.) contienen información crucial sobre errores que no aparecen en latest.log. CrashDetector no puede leer los registros de tu Launcher, es posible que no tenga un archivo de registro y deba pegar los registros manualmente.<br>"
				+ "Para más información, consulte <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">este problema</a>. Estos registros contienen la salida estándar (STDOUT), necesaria para diagnosticar muchos tipos de errores.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de Create. Create cambió mucho: muchas clases fueron eliminadas. Especialmente desde Create 6 (febrero 2025), los addons para versiones antiguas de Create no funcionan. QuickFix no tiene una solución para este problema. Necesitas actualizar los addons de Create, eliminar los obsoletos, o usar la versión correcta de Create para los addons que deseas usar.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de EpicFight. EpicFight cambió mucho: muchas clases fueron eliminadas. QuickFix no tiene una solución para este problema. Necesitas actualizar los addons de EpicFight, eliminar los obsoletos, o usar la versión correcta de EpicFight para los addons que deseas usar.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estás utilizando OpenJ9, que no es compatible con Esta Aplicacion. Muchos apps, incluido éste, no soportan OpenJ9 debido a diferencias en la implementación de la JVM. QuickFix no puede resolver este problema automáticamente. Necesitas instalar un JDK compatible como Oracle JDK, OpenJDK Hotspot u otros alternativos recomendados.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Esta versión de la applicacion requiere Java 11 (JDK 11) para funcionar correctamente. Estás utilizando una versión anterior de Java que no es compatible. QuickFix no puede actualizar tu Java automáticamente. Necesitas descargar e instalar JDK 11 o una versión superior compatible desde los enlaces proporcionados en la solución.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Has asignado demasiada memoria, lo que está causando que el sistema no tenga suficientes recursos para funcionar correctamente. Esto suele ocurrir cuando se especifica una cantidad de RAM mayor a la disponible en tu sistema o cuando se usa una JVM de 32 bits que no puede manejar grandes cantidades de memoria.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Para resolver este problema, debes reducir la cantidad de memoria asignada a la app. La cantidad máxima recomendada depende de tu sistema, pero generalmente no debe exceder el 70-80% de tu memoria RAM total. Si usas una JVM de 32 bits, el límite máximo es de aproximadamente 2-3 GB, independientemente de la cantidad de RAM física que tengas.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para disminuir la memoria heap asignada a la app, abre la configuración de tu lanzador y busca la opción de memoria RAM. Reduce el valor máximo (Xmx) a una cantidad más adecuada. Por ejemplo, si tienes 8 GB de RAM, asigna entre 3-4 GB a la app. Si tienes 16 GB de RAM, puedes asignar entre 6-8 GB. Recuerda dejar suficiente memoria para el sistema operativo y otros programas.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Faltan archivos esenciales de Forge. El archivo '"
				+ archivo
				+ "' no se encuentra en tu instalación. Esto suele ocurrir cuando la instalación de Forge se interrumpió o se eliminaron archivos importantes. QuickFix no puede recuperar estos archivos automáticamente. Necesitas reinstalar Forge correctamente desde el instalador oficial.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge no puede encontrar la versión de Minecraft requerida. Se necesita la versión " + version
				+ " pero no se encuentra en el archivo '" + archivo
				+ "'. Esto ocurre cuando hay una incompatibilidad entre la versión de Minecraft y la versión de Forge que estás utilizando. Asegúrate de descargar la versión correcta de Forge que coincida con tu versión de Minecraft.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>No se puede encontrar el target 'fmlclient' necesario para iniciar Forge. Esto indica que la instalación de Forge está incompleta o dañada. Es probable que los archivos esenciales de Forge no se hayan instalado correctamente. Necesitas reinstalar Forge usando el instalador oficial.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>No se puede encontrar la clase principal de Minecraft en el cargador de clases. Esto suele indicar que la instalación de Forge está incompleta o que hay un conflicto con otros mods. Es posible que los archivos de Minecraft se hayan dañado durante la instalación de Forge. Necesitas reinstalar Forge correctamente.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La instalación de Forge no está completa. Esto puede deberse a una instalación interrumpida, archivos eliminados o una incompatibilidad con tu versión de Minecraft. Forge necesita archivos específicos para funcionar correctamente, y algunos de ellos están faltando en tu instalación actual.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Instalación incompleta de Forge";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para resolver este problema, necesitas reinstalar Forge correctamente. Asegúrate de descargar la versión adecuada para tu versión de Minecraft y seguir el proceso de instalación completo sin interrumpirlo.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Descargar Forge oficialmente";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Cómo reinstalar Forge correctamente";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Instrucciones para reinstalar Forge:</h3>" + "<ol>"
				+ "<li>Descarga el instalador correcto de Forge desde el sitio oficial (versión recomendada para tu versión de Minecraft)</li>"
				+ "<li>Cierra completamente tu launcher de Minecraft</li>"
				+ "<li>Ejecuta el instalador de Forge como administrador</li>"
				+ "<li>Selecciona la opción 'Installer' (no 'Installer (run client)')</li>"
				+ "<li>Elige la carpeta de tu perfil de Minecraft en el launcher</li>"
				+ "<li>Presiona 'OK' y espera a que termine la instalación</li>"
				+ "<li>Reinicia tu launcher y verifica que Forge aparezca en la lista de perfiles</li>" + "</ol>"
				+ "<p><b>Nota importante:</b> Si usas un launcher personalizado, asegúrate de seleccionar la carpeta correcta del perfil.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Instrucciones para reinstalar Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Error de enlace insatisfecho: No se pudo cargar la biblioteca " + nombreBiblioteca
				+ ". Posibles soluciones:<br/><br/>"
				+ "a) Añade el directorio que contiene la biblioteca compartida a -Djava.library.path o -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Añade el archivo JAR que contiene la biblioteca compartida al classpath.<br/><br/>"
				+ "Este error ocurre cuando Minecraft no puede encontrar archivos esenciales para su funcionamiento. "
				+ "Esto suele deberse a una instalación incompleta de Minecraft o a problemas con los permisos del sistema.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Error de enlace insatisfecho";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>No se pudo cargar una biblioteca. Posibles soluciones:<br/><br/>"
				+ "a) Añade el directorio que contiene la biblioteca compartida a -Djava.library.path o -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Añade el archivo JAR que contiene la biblioteca compartida al classpath.<br/><br/>"
				+ "Estas soluciones técnicas son para usuarios avanzados. La mayoría de los usuarios deberían intentar "
				+ "reinstalar Minecraft antes de modificar estos parámetros.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Colisión de IDs: El ID <strong>" + id
				+ "</strong> ya está ocupado por <strong>" + modOrigen + "</strong> al intentar agregar <strong>"
				+ modDestino
				+ "</strong>. Esto sucede cuando dos mods intentan usar el mismo ID para diferentes elementos.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Se ha excedido el rango máximo de IDs permitidos. Esto ocurre cuando los mods intentan registrar bloques o ítems con IDs fuera del rango permitido por tu versión de Minecraft.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Conflicto de IDs";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para resolver esto en Minecraft 1.12.2, instala <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Para 1.7.10, usa <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Usa herramientas como <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> o <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> para resolver colisiones de IDs.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Instalar JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Instalar EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Usar IdFix Minus o IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Usar Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Ver documentación japonesa";
	}

	@Override
	public String escanearDeMCreator() {
		// TODO Auto-generated method stub
		return "Escanear MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 * 
	 * @return Título de la ventana
	 */
	@Override
	public String tituloArbolDeMods() {
		return "Árbol de Módulos y Clases";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 * 
	 * @return Texto de la etiqueta
	 */
	@Override
	public String tipoBusqueda() {
		return "Tipo";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroTodos() {
		return "Todos";
	}

	/**
	 * Obtiene el texto para el filtro "Paquetes".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroPaquetes() {
		return "Paquetes";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroClases() {
		return "Clases";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroMetodos() {
		return "Métodos";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroCampos() {
		return "Campos";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Referencias de Campo";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Referencias de Método";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasClase() {
		return "Referencias de Clase";
	}

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 * 
	 * @return Texto del tooltip
	 */
	@Override
	public String tipBuscar() {
		return "Escriba aquí para buscar en el árbol de mods";
	}

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonBuscar() {
		return "Buscar";
	}

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonResetearArbol() {
		return "Resetear Árbol";
	}

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String modsCargados() {
		return "Mods Cargados";
	}

	/**
	 * Obtiene el texto para la categoría de clases.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String clases() {
		return "Clases";
	}

	/**
	 * Obtiene el texto para la categoría de métodos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String metodos() {
		return "Métodos";
	}

	/**
	 * Obtiene el texto para la categoría de campos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String campos() {
		return "Campos";
	}

	/**
	 * Obtiene el texto para la categoría de referencias.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String referencias() {
		return "Referencias";
	}

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 * 
	 * @return Texto de resultados
	 */
	@Override
	public String resultadosBusqueda() {
		return "Resultados de Búsqueda";
	}

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 * 
	 * @return Texto de la opción
	 */
	@Override
	public String buscarReferencias() {
		return "Buscar Referencias";
	}

	/**
	 * Obtiene el texto para las referencias de mod.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMod() {
		return "Referencias del Mod";
	}

	/**
	 * Obtiene el texto para las referencias de clase.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasClase() {
		return "Referencias de la Clase";
	}

	/**
	 * Obtiene el texto para las referencias de método.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMetodo() {
		return "Referencias del Método";
	}

	/**
	 * Obtiene el texto para las referencias de campo.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasCampo() {
		return "Referencias del Campo";
	}

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 * 
	 * @return Mensaje de error
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "No se encontraron referencias";
	}

	/**
	 * Obtiene el texto para el detalle de mod.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMod() {
		return "Detalles del Mod:";
	}

	/**
	 * Obtiene el texto para la ubicación.
	 * 
	 * @return Etiqueta de ubicación
	 */
	@Override
	public String ubicacion() {
		return "Ubicación";
	}

	/**
	 * Obtiene el texto para los nombres.
	 * 
	 * @return Etiqueta de nombres
	 */
	@Override
	public String nombres() {
		return "Nombres";
	}

	/**
	 * Obtiene el texto para el módulo.
	 * 
	 * @return Etiqueta de módulo
	 */
	@Override
	public String modulo() {
		return "Módulo";
	}

	/**
	 * Obtiene el texto para el detalle de clase.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleClase() {
		return "Detalles de la Clase:";
	}

	/**
	 * Obtiene el texto para el detalle de método.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMetodo() {
		return "Detalles del Método:";
	}

	/**
	 * Obtiene el texto para el detalle de campo.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleCampo() {
		return "Detalles del Campo:";
	}

	/**
	 * Obtiene el texto para la clase.
	 * 
	 * @return Etiqueta de clase
	 */
	@Override
	public String clase() {
		return "Clase";
	}

	/**
	 * Obtiene el texto para el tipo.
	 * 
	 * @return Etiqueta de tipo
	 */
	@Override
	public String tipo() {
		return "Tipo";
	}

	/**
	 * Obtiene el texto para las referencias a métodos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasAMetodos() {
		return "Referencias a Métodos:";
	}

	/**
	 * Obtiene el texto para las referencias a campos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasACampos() {
		return "Referencias a Campos:";
	}

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String arbolDeMods() {
		return "Árbol de Mods";
	}

	/**
	 * Obtiene el texto para método.
	 * 
	 * @return Palabra "Método"
	 */
	@Override
	public String metodo() {
		return "Método";
	}

	/**
	 * Obtiene el texto para campo.
	 * 
	 * @return Palabra "Campo"
	 */
	@Override
	public String campo() {
		return "Campo";
	}

	@Override
	public String descompilar() {
		// TODO Auto-generated method stub
		return "Descompilar";
	}

	@Override
	public String exportar() {
		// TODO Auto-generated method stub
		return "Exportar";
	}

	@Override
	public String importar() {
		// TODO Auto-generated method stub
		return "Importar";
	}

	@Override
	public String errorImportar() {
		// TODO Auto-generated method stub
		return "Error Importar";
	}

	@Override
	public String estructuraImportada() {
		// TODO Auto-generated method stub
		return "Estructura Importada";
	}

	@Override
	public String estructuraExportada() {
		// TODO Auto-generated method stub
		return "Estructura Exportada";
	}

	@Override
	public String errorExportar() {
		// TODO Auto-generated method stub
		return "Error Exportar";
	}

	@Override
	public String exportando() {
		// TODO Auto-generated method stub
		return "Exportando";
	}

	@Override
	public String exportacionTardara() {
		// TODO Auto-generated method stub
		return "Exportacion Tardara";
	}

	@Override
	public String porFavorEspere() {
		// TODO Auto-generated method stub
		return "Por Favor Espere";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>No tienes las binarias de VLC. Necesitas las binarias de VLC para WaterMedia. Necesitas instalar manualmente desde https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Descargar VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Error crítico: El nombre del módulo '"
				+ nombreModulo + "' contiene caracteres inválidos. La parte '" + parteInvalida
				+ "' no es un identificador Java válido. Esto ocurre cuando un mod usa palabras reservadas de Java (como 'true', 'class') o caracteres no permitidos en su nombre.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Caracteres Inválidos en Nombre de Mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "El nombre del mod '" + nombreModulo + "' es inválido porque contiene '" + parteInvalida
				+ "', que es una palabra reservada de Java o un carácter no permitido. "
				+ "Busca en los logs qué mod corresponde a este nombre (normalmente el nombre del archivo JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Accede a la carpeta del mod y edita el archivo <b>mods.toml</b> dentro de la carpeta <b>/META-INF/</b>. "
				+ "Cambia el valor de <b>modId</b> para que use solo letras, números y guiones bajos, sin palabras reservadas de Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Ejemplo de nombre válido: 'truemod_shot_enchantment' en lugar de 'true.shot.enchantment'. "
				+ "Recuerda que los nombres de mod no pueden contener puntos, guiones, ni palabras reservadas de Java como 'true', 'false' o 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Error crítico con el mod: '" + nombreJar
				+ "'. Falta el campo obligatorio 'mandatory' en sus dependencias. Esto ocurre cuando el archivo mods.toml no especifica si la dependencia es obligatoria.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependencia de Mod con Campo Obligatorio Faltante";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "El mod problemático es: <b>" + nombreJar
				+ "</b>. Este archivo tiene un error en su configuración de dependencias";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Abre el archivo <b>mods.toml</b> dentro de la carpeta <b>/META-INF/</b> del mod <b>" + nombreJar
				+ "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "En la sección de dependencias, asegúrate que cada entrada incluya <b>mandatory=true</b> o <b>mandatory=false</b> (ej: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Error crítico con el mod: '" + nombreJar
				+ "'. Configuración inválida de access transformer. Esto ocurre cuando el archivo de configuración tiene sintaxis incorrecta o referencias a clases/métodos que no existen.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer Inválido";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "El mod problemático es: <b>" + nombreJar
				+ "</b>. Este mod contiene una configuración inválida de access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Abre el archivo <b>accessTransformer.cfg</b> dentro del mod <b>" + nombreJar
				+ "</b> (normalmente en la carpeta raíz del archivo JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Corrige la sintaxis del access transformer. Las líneas deben seguir el formato: <b>access class.method</b> (ej: public net.minecraft.world.entity.Entity.func_200560_a). Elimina líneas con referencias a clases o métodos que no existan en tu versión de Minecraft";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Error crítico: Discrepancia entre el ID del mod en la anotación @Mod y el archivo mods.toml. El mod '"
				+ nombreMod + "' no puede cargarse porque los IDs no coinciden.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Discrepancia entre @Mod y mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "El mod en desarrollo '" + nombreMod
				+ "' tiene una discrepancia entre el ID en la anotación <b>@Mod</b> y el valor en <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Verifica que el ID en tu clase principal coincida con el valor <b>modId</b> en el archivo <b>/META-INF/mods.toml</b>. Ejemplo: <b>@Mod(\"mimod\")</b> debe coincidir con <b>modId=\"mimod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Si estás usando Gradle, ejecuta <b>clean</b> después de hacer cambios para asegurar que los recursos se actualicen correctamente. A veces los archivos antiguos permanecen en la carpeta build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "cliente" : "servidor";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servidor" : "cliente";

		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Error crítico: Se está intentando cargar la clase '" + nombreClase + "' en el entorno de "
				+ plataforma + ", pero está diseñada para " + plataformaOpuesta
				+ ". <b>Usa la función 'Arbol de Mods' en la barra lateral para buscar qué mod está intentando cargar esta clase</b>. "
				+ "Los mods están construidos específicamente para una plataforma y no funcionan en la otra.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod en Plataforma Incorrecta";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "En la pestaña <b>Arbol de Mods</b> (a la derecha), busca referencias a la clase <b>" + nombreClase
				+ "</b> para identificar qué mod está causando el problema";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "cliente" : "servidor";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servidor" : "cliente";

		return "El mod identificado es un mod de <b>" + plataformaOpuesta + "</b> y no debe usarse en tu entorno de "
				+ plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Elimina el mod problemático de tu carpeta <b>mods</b>. Si necesitas funcionalidad similar para esta plataforma, "
				+ "busca un mod alternativo específicamente diseñado para <b>cliente</b> o <b>servidor</b> según corresponda";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Error crítico: Falta metadata para el modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Los siguientes mods podrían estar causando el problema: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", y otros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Esto ocurre cuando un mod depende de otro mod que no está instalado o tiene un archivo mods.toml incorrecto.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Metadata de mods.toml Faltante";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Los siguientes mods dependen de '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", y otros...");
			paso.append("</b>. Usa la función <b>Arbol de Mods</b> para confirmar qué mod está causando el problema");
			return paso.toString();
		}
		return "Un mod está intentando depender de '" + modIdFaltante
				+ "', pero este mod no está instalado. Usa la función <b>Arbol de Mods</b> para identificar qué mod está causando el problema";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Tienes dos opciones:<br/>" + "1. <b>Instala el mod faltante</b>: Busca e instala el mod con ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Elimina el mod dependiente</b>: Si no necesitas la funcionalidad, elimina el mod que depende de '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Si el mod '" + modIdFaltante + "' es una biblioteca (como 'forge', 'minecraft', 'curios'), "
				+ "asegúrate de tener instalada la versión correcta de Minecraft y Forge. "
				+ "Si es un mod regular, busca en su página de descarga los requisitos previos necesarios";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Advertencia: Error al iniciar el sistema de sonido. Se han desactivado los sonidos y la música. Este error está comúnmente asociado con el mod SoundPhysicsMod y puede deberse a conflictos con otras librerías de sonido.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Error en el Sistema de Sonido";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "El error está comúnmente relacionado con <b>SoundPhysicsMod</b>. Verifica si tienes instalada la versión más reciente compatible con tu versión de Minecraft";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Si usas otros mods de sonido (como Sound Filters, Dynamic Surroundings, etc.), prueba eliminando temporalmente SoundPhysicsMod para ver si resuelve el conflicto";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Revisa la carpeta <b>logs</b> para encontrar mensajes adicionales relacionados con LWJGL o OpenAL, que pueden indicar problemas con las librerías de sonido subyacentes";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Error crítico: La clase '").append(nombreClase)
				.append("' está registrada como listener de eventos pero no contiene métodos válidos. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Esta clase se encuentra en los siguientes mods: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", y otros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Esto ocurre cuando se registra una clase para escuchar eventos sin tener métodos anotados con @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Clase Registrada sin Listeners de Eventos";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("La clase problemática se encuentra en estos mods: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", y otros...");
			paso.append("</b>. Estos mods están intentando registrar eventos sin métodos válidos");
			return paso.toString();
		}
		return "La clase <b>" + nombreClase
				+ "</b> fue registrada para escuchar eventos pero no contiene métodos con la anotación <b>@SubscribeEvent</b>. Usa la función <b>Arbol de Mods</b> para identificar qué mod contiene esta clase";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "En el código fuente, verifica que la clase <b>" + nombreClase + "</b> contenga al menos un método con: "
				+ "<b>@SubscribeEvent public void nombreMetodo(EventoEspecifico evento) { ... }</b>. "
				+ "Si es una clase interna, asegúrate que no esté marcada como estática";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Para los mods identificados (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", etc.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("contacta al desarrollador de este mod para que corrija el problema. ");
			} else {
				paso.append("contacta a los desarrolladores de estos mods para que corrijan el problema. ");
			}
		}

		paso.append(
				"Si eres el desarrollador, elimina el registro de esta clase en el EventBus o añade métodos @SubscribeEvent válidos");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Error crítico: Error en UnionFileSystem al procesar '" + nombreArchivo + "'. ";

		mensaje += "Este error es muy común en modpacks preconfigurados y está directamente relacionado con problemas del lanzador. ";

		mensaje += "El sistema no puede leer correctamente los archivos del mod debido a que están corruptos o incompletos.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Error UnionFileSystem - Archivo Corrupto";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "Se detectó el error específico <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> con el archivo <b>"
				+ nombreArchivo + "</b>.";

		paso += " Este es un error conocido en lanzadores de modpacks cuando los archivos no se descargan completamente.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Reinstala completamente el modpack. Este error ocurre principalmente cuando el lanzador no completa la descarga de todos los archivos. "
				+ "Si estás usando <b>Luna Pixel</b>, te recomendamos encarecidamente usar <b>ATLauncher</b> en su lugar, "
				+ "ya que este lanzador maneja mejor los archivos de mods y evita este error específico.";

	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Si el problema persiste después de reinstalar: <br/>" + "1. <b>Cambia a otro lanzador</b> <br/>"
				+ "2. Si usas <b>Luna Pixel</b>, <b>usa ATLauncher</b> que es más confiable para evitar este error específico<br/>"
				+ "3. Verifica tu conexión a internet y espacio en disco antes de reinstalar el modpack";

	}

	/**
	 * Obtiene el mensaje de confirmación para habilitar el proxy de
	 * System.out/System.err
	 * 
	 * @return Mensaje explicativo con advertencias y requisitos
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "¿Habilitar ProxySysOutSysErr?\n\n"
				+ "Esta opción da a CrashDetector acceso a System.out y System.err cuando el launcher no proporciona registros.\n\n"
				+ "Solo debe habilitarse cuando no puedas pegar un registro manualmente.\n\n"
				+ "Advertencia: Esto puede interferir con algunos mods o launchers.\n\n"
				+ "Se requiere reiniciar el juego/app para que los cambios surtan efecto.";
	}

	/**
	 * Obtiene el título para diálogos de confirmación
	 * 
	 * @return Título en español para ventanas de confirmación
	 */
	@Override
	public String confirmacionTitulo() {
		return "Confirmación";
	}

	/**
	 * Obtiene el mensaje de éxito tras habilitar el proxy
	 * 
	 * @return Mensaje informativo sobre el estado del proxy
	 */
	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr habilitado correctamente.\n\n"
				+ "Se requiere reiniciar CrashDetector para que los cambios surtan efecto.";
	}

	/**
	 * Obtiene el título para diálogos informativos
	 * 
	 * @return Título en español para ventanas de información
	 */
	@Override
	public String informacionTitulo() {
		return "Información";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Error crítico: ¡AzureLib y GeckoLib se inicializaron demasiado pronto! ");
		} else if (azureLibError) {
			mensaje.append("Error crítico: ¡AzureLib se inicializó demasiado pronto! ");
		} else if (geckoLibError) {
			mensaje.append("Error crítico: ¡GeckoLib se inicializó demasiado pronto! ");
		}

		mensaje.append(
				"Este error ocurre cuando se intenta usar mods Fabric con versiones no-Fabric de estas librerías. ");

		if (connectorPresente) {
			mensaje.append(
					"Se detectó un mod de compatibilidad (Sinytra Connector o specialcompatibilityoperation), lo que indica que estás intentando usar mods Fabric en un entorno Forge o FeatureCreep. ");
			mensaje.append(
					"Revisa el error 'Error de inicialización de FabricMC' en los logs para identificar qué mod específico está causando el problema. ");
		}

		mensaje.append(
				"AzureLib y GeckoLib son librerías esenciales para mods de animación, pero deben coincidir con la plataforma correcta (Fabric o Forge). ");
		mensaje.append(
				"El juego no puede cargar correctamente los mods de animación debido a este conflicto de inicialización.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Librería Inicializada Demasiado Pronto";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Revisa el error 'Error de inicialización de FabricMC' para identificar el mod problemático";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Verifica que estés usando la versión correcta de AzureLib/GeckoLib para tu plataforma (Forge o Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Error crítico: Incompatibilidad entre C2ME y mods de conexión. "
				+ "Este error ocurre porque C2ME intenta acceder a componentes internos de Java que están restringidos en entornos con "
				+ "Sinytra Connector o specialcompatibilityoperation u otros mods de compatibilidad Fabric/Forge. "
				+ "<b>C2ME no es compatible con estos entornos, pero <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> es la alternativa recomendada</b> que funciona correctamente "
				+ "con mods de conexión. El juego no puede iniciar debido a este conflicto de permisos de seguridad de Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Incompatibilidad C2ME con Mods de Conexión";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Elimina C2ME de tu carpeta de mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Descarga e instala <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> en su lugar (compatible con Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Verifica que todos los mods de conexión (como Sinytra Connector) estén actualizados a su última versión";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Error crítico: Fallo al cargar el plugin JEI para el mod '" + modId + "'. La clase '" + nombreClase
				+ "' (plugin ID: '" + pluginId
				+ "') generó un error que está causando que el juego se bloquee durante el inicio. "
				+ "Este problema ocurre cuando un mod tiene una integración JEI incompatible o defectuosa que interrumpe la inicialización del juego.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI Fallido - Causa Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "El mod <b>" + modId
				+ "</b> contiene un plugin JEI defectuoso que está causando el crash. Usa la función <b>Arbol de Mods</b> para confirmar qué mod está generando el problema";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Elimina temporalmente el mod <b>" + modId
				+ "</b> de tu carpeta de mods para verificar si resuelve el crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Busca actualizaciones del mod <b>" + modId
				+ "</b> o contacta a su desarrollador reportando el problema con el plugin JEI. "
				+ "Mientras tanto, el mod debe ser eliminado para poder iniciar el juego";
	}

	/**
	 * Obtiene el título de la aplicación
	 * 
	 * @return Título de la ventana principal
	 */
	@Override
	public String tituloLectador() {
		return "Lector de Consolas - Crash Detector";
	}

	/**
	 * Obtiene el título para la leyenda de colores
	 * 
	 * @return Título de la sección de leyenda
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "LEYENDA DE COLORES";
	}

	/**
	 * Obtiene el texto para identificar errores en la leyenda
	 * 
	 * @return Texto descriptivo para errores
	 */
	@Override
	public String obtenerErrorEnLeyenda() {
		return "Errores críticos";
	}

	/**
	 * Obtiene el texto para identificar stacktraces en la leyenda
	 * 
	 * @return Texto descriptivo para stacktraces
	 */
	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Trazas de pila";
	}

	/**
	 * Obtiene el título para ventanas de error
	 * 
	 * @return Título estándar para mensajes de error
	 */
	@Override
	public String obtenerTituloError() {
		return "Error";
	}

	/**
	 * Obtiene el mensaje para errores al procesar líneas de log
	 * 
	 * @return Mensaje de error específico
	 */
	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Ocurrió un error al procesar la línea seleccionada";
	}

	/**
	 * Obtiene el título para el área de nombre de error
	 * 
	 * @return Título del panel de nombre de error
	 */
	@Override
	public String obtenerNombreError() {
		return "NOMBRE DEL ERROR";
	}

	/**
	 * Obtiene el título para el área de descripción de error
	 * 
	 * @return Título del panel de descripción de error
	 */
	@Override
	public String obtenerDescripcionError() {
		return "DESCRIPCIÓN DETALLADA";
	}

	/**
	 * Obtiene el título para el selector de consolas
	 * 
	 * @return Título del combobox de selección
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "SELECCIONAR CONSOLA";
	}

	/**
	 * Obtiene el nombre predeterminado para errores
	 * 
	 * @return Nombre genérico para errores
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Error no identificado";
	}

	/**
	 * Obtiene la descripción predeterminada para errores
	 * 
	 * @return Descripción genérica para errores
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Se detectó un error crítico en los registros. "
				+ "Revise la traza de pila para identificar la causa raíz.";
	}

	/**
	 * Obtiene el mensaje para errores de lectura de archivos
	 * 
	 * @return Mensaje específico para fallos en lectura
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "No se pudo leer el archivo de registros. "
				+ "Verifique que el archivo existe y tiene permisos de lectura.";
	}

	/**
	 * Obtiene la etiqueta para el botón en la barra lateral
	 * 
	 * @return Texto que aparecerá en el botón lateral
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Analizador de Logs";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Error crítico: Fallo al registrar suscriptores automáticos para el mod '").append(modId)
				.append("'. ");

		mensaje.append("Clase problemática: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Esta clase se encuentra en: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", y otros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Este error ocurre cuando un mod intenta registrar una clase como suscriptor automático pero la clase no se puede cargar correctamente. ");
		mensaje.append(
				"<b>Revisa otros errores en el log, ya que el problema real podría estar en otra parte del registro</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Fallo en Registro de Suscriptores Automáticos";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "El mod <b>" + modId + "</b> está intentando registrar la clase <b>" + nombreClase
				+ "</b> como suscriptor automático, pero ha fallado. Verifica que esta clase exista y sea accesible";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"La clase problemática <b>" + nombreClase + "</b> se encuentra en estos archivos: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", y otros...");
			paso.append("</b>. ");
			paso.append(
					"Usa la función <b>Arbol de Mods</b> para confirmar qué archivo específico contiene la clase problemática");
			return paso.toString();
		}
		return "La clase <b>" + nombreClase + "</b> no se encuentra en ningún archivo de mod. Verifica que el mod <b>"
				+ modId
				+ "</b> esté correctamente instalado. Usa la función <b>Arbol de Mods</b> para ayudar a identificar el problema";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Actualiza el mod <b>" + modId
				+ "</b> a la última versión compatible con tu versión de Minecraft y Forge. "
				+ "Si el problema persiste, contacta al desarrollador del mod reportando el error con la clase problemática";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Revisa <b>otros errores en el log</b> antes de este mensaje, ya que el problema real podría estar en otra parte del registro. "
				+ "A veces un error previo impide que se carguen correctamente las clases necesarias para el registro de suscriptores";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "Limpiado";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "Original";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "Ver en Consola";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "Advertencia";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "Fatal";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "BattlyLauncher no tiene un registro ni una consola para copiando. Puedes usar ProxySysOutSysErr para interceptar STDOUT y STDERR y reiniciar el juego pero ProxySysOutSysErr puede conflictar con mods modificando STDOUT o STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Necesitas hablicar modo de depuración en la configuracion de NightWorld para obtener un registro de lanzer. Es muy importante especialemente por que tiene STDOUT y STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Necesitas guardar o pegar el contenido de la Terminal de tu servidor porque tiene informacion no en otro registros incluyendo STDOUT, STDERR, y otras errores. Por favor pegar el contenido de la ultima sesion. Para la future, puedes guardar el contenido de la terminal al archivo cd_launcherlog Para evitar tener que pegarlo, añade >> cd_launcherlog después del comando, como se muestra en la imagen. Ten en cuenta que esto impedirá que se muestre en la terminal; solo aparecerá en ese archivo una vez hecho esto.";
	}

//Métodos para Idioma relacionados con la verificación LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Error crítico: transformador de LexForge detectado en un entorno NeoForge. ");
		sb.append("</b>");

		sb.append("Clase implicada: <b>").append(claseReceptora).append("</b>. ");
		sb.append("La interfaz afectada es <b>").append(interfazObjetivo).append("</b> ");
		sb.append("y falta el método <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("La clase se encontró en: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", y otros...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"No se localizaron JARs que contengan esa clase; podría estar sombreada o incluida como jar-in-jar. ");
		}

		sb.append(
				"Este fallo aparece cuando un transformador/servicio de ModLauncher compilado para MinecraftForge/LexForge ");
		sb.append("se carga bajo NeoForge con una versión incompatible de la API de ModLauncher. ");
		sb.append("Actualiza o reemplaza el componente para NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformador de LexForge usado en NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identifica el transformador incompatible: <b>" + claseReceptora + "</b>. " + "La API esperada es <b>"
				+ interfazObjetivo + "</b> y falta <b>" + firmaMetodoFaltante + "</b>. "
				+ "Revisa si el mod registra esta clase en <b>META-INF/services</b> y elimínala o desactívala en NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Ubicación del/los mod(s) implicados: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", y otros...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"No se encontraron JARs que contengan la clase. Comprueba jar-in-jar y dependencias sombreadas. ");
		}
		sb.append("Retira temporalmente esos JARs o usa versiones compatibles con NeoForge para confirmar el origen.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Sustituye el componente por una versión específica de NeoForge o recompílalo contra la "
				+ "versión de ModLauncher usada por NeoForge. Evita binarios antiguos de LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Limpia la carpeta de mods y elimina duplicados jar-in-jar. Vacía cachés del lanzador si es necesario "
				+ "y vuelve a iniciar para verificar que no se cargan transformadores de LexForge.";
	}
//En tu clase de idioma:

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia no puede iniciar: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("es incompatible.</b> ");
		sb.append("Retira Xenon y usa Embeddium o Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Detectado en: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", y otros...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia incompatible con Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Se detectó " + label + " incompatible con WaterMedia. Quítalo del perfil.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Ubicaciones: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", y otros...");
			sb.append("</b>. Elimina ese JAR.");
			return sb.toString();
		}
		return "No se localizaron JARs. Revisa la carpeta mods y elimina Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Instala Embeddium o Sodium como sustituto y reinicia el juego.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Error al comprimir (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater cerrado durante copia de recursos de TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Relacionado con: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", y otros");
			sb.append("</b>. ");
		}
		sb.append("<br/><b>Solución:</b> en <code>tacz/tacz-pre.toml</code> pon <code>DefaultPackDebug=true</code>. ")
				.append("Si hace falta, genera un mapa primero y luego actívalo.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "En tacz/tacz-pre.toml pon DefaultPackDebug=true. Si hace falta, genera un mapa primero y luego actívalo.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Funciones de densidad no vinculadas";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Faltan funciones de densidad en el registro.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Faltan: ");
			for (int i = 0; i < Math.min(4, claves.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append("<code>").append(claves.get(i)).append("</code>");
			}
			if (claves.size() > 4)
				sb.append(", …");
			sb.append(". ");
		}
		sb.append("<br/><b>Solución:</b> instala o activa el mod/datapack que define esas funciones y reinicia.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Instala o activa el mod/datapack que aporta esas funciones y reinicia el juego.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mensaje breve, en color de error, mencionando explícitamente el mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Entrada de registro no presente: ").append(claveFaltante).append(". ");
		sb.append("Frecuente con la alfa de Steam & Railways para Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Quita o reemplaza la alfa de Steam & Railways para Create 6 por una versión compatible.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Corto, con color de error y recomendación directa
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Conflicto de carga: Multiworld junto con Sodium/Embeddium/Rubidium provoca ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Sugerencia: quita Multiworld o el mod de rendimiento, o usa versiones compatibles.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Conflicto: Multiworld con mods de rendimiento";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Desinstala Multiworld o Sodium/Embeddium/Rubidium, o actualiza a versiones compatibles entre sí.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium detectó un controlador gráfico incompatible. "
				+ "Actualiza el driver de tu GPU al mínimo requerido o sigue la guía de Sodium." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Error de contexto OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OpenGL falló: no hay contexto actual o la función no está disponible en este contexto. "
				+ "También puede ser un problema de controladores de vídeo." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Actualiza/reinstala los controladores de la GPU y reinicia; desactiva superposiciones y prueba sin mods de rendimiento.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Enlace copiado al portapapeles.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		// TODO Auto-generated method stub
		return "Buscar dentro de comprimidos (.zip/.jar/.war/.ear/.fpm/.rar de Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Error de resolución de texturas: No se puede ajustar " + recurso + " - tamaño: " + tamaño + "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Error de Resolución de Texturas";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Este error ocurre cuando las texturas son demasiado grandes o hay demasiados paquetes de recursos. "
				+ "Intenta usar paquetes de recursos de menor resolución o elimina algunos paquetes de recursos. "
				+ "Verifica que no hayas agregado texturas personalizadas con resolución mayor a la permitida.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Error de servicios de ModLauncher: Ruta con caracteres no válidos. "
				+ "Los servicios de ModLauncher no pueden procesar rutas que contienen caracteres no ASCII o caracteres especiales. "
				+ "Caracteres problemáticos incluyen: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, y especialmente el carácter '\"' cuando está al final del nombre. "
				+ "Componentes de servicios comunes en ModLauncher incluyen CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant y Sintrya Connector. "
				+ "Puedes eliminar todos los servicios, pero otros problemas pueden surgir debido al nombre de la ruta. "
				+ "Solución: Renombra la instancia para usar solo caracteres ASCII (a-z, A-Z, 0-9), sin espacios ni caracteres especiales.</b>";
	}// TODO incluye un Buscardor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "Error de Ruta en ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Este error ocurre cuando la ruta de la instancia contiene caracteres no ASCII o caracteres especiales. "
				+ "Los servicios de ModLauncher no pueden manejar estas rutas. "
				+ "Solución: Renombra la instancia para usar solo caracteres ASCII (a-z, A-Z, 0-9) y evita espacios y caracteres especiales. "
				+ "Presta especial atención al carácter '\"' que es muy problemático, especialmente cuando está al final del nombre.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Editor de Codice";
	}

	@Override
	public String nuevo() {
		return "Nuevo";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Actualizar seleccionado";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Eliminar seleccionado";
	}

	@Override
	public String exportarJSON() {
		return "Exportar JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Guardar todo";
	}

	@Override
	public String general() {
		return "General";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Texto a buscar";
	}

	@Override
	public String filtro() {
		return "Filtro (id)";
	}

	@Override
	public String criticalidad() {
		return "Criticalidad (ADVERTENCIA/ERROR/FATAL)";
	}

	@Override
	public String prioridad() {
		return "Prioridad";
	}

	@Override
	public String lista() {
		return "Verificaciones";
	}

	@Override
	public String colIdioma() {
		return "Idioma";
	}

	@Override
	public String colNombre() {
		return "Nombre";
	}

	@Override
	public String colResultado() {
		return "Resultado";
	}

	@Override
	public String vistaJson() {
		return "Vista previa JSON";
	}

	@Override
	public String idiomas() {
		return "Idiomas (todos obligatorios)";
	}

	@Override
	public String elegirFiltro() {
		return "Elegir...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Seleccione un filtro";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Filtros disponibles";
	}

	@Override
	public String faltanCampos() {
		return "Complete todos los campos generales obligatorios.";
	}

	@Override
	public String critInvalida() {
		return "Criticalidad inválida. Use ADVERTENCIA, ERROR o FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "El filtro indicado no existe.";
	}

	@Override
	public String faltanIdiomas() {
		return "Complete nombre y resultado para todos los idiomas:";
	}

	@Override
	public String verificacionInvalida() {
		return "Una verificación es inválida. Revise los campos.";
	}

	@Override
	public String guardadoOk() {
		return "Guardado correcto.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Agregar razones";
	}

	@Override
	public String descripcionEditorCodice() {
		// TODO Auto-generated method stub
		return "Puedes registrar razones aqui. Neceistas una ID, una string sin caractares speciales o marcos accentos o espacios. Para filtros puedes usar son \"linea containe\" para buscar para un string en una linea, \"todo containe\" si el registro tiene un string, \"regex linea\" si una linea tiene regex, y \"regex todos\" sugeste usar las versiones de linea. Necesitas poder la criticalidad, FATAL, ERROR, o ADVERTENCIA para los colores. Para todas las idiomas necesitas escribir un nombre y respunta para la pantalla.Puedes agregar mas verificaciones o eliminar otras. Guardas cunado completa.";
	}

	@Override
	public String descartarCambios() {
		return "¿Descartar los cambios no guardados en la verificación actual?";
	}

	@Override
	public String confirmacion() {
		return "Confirmación";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "¿Desea guardar los cambios antes de salir?";
	}

	@Override
	public String salirSinGuardar() {
		return "Salir sin guardar";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Error critico: Falla al cargar un servicio de modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Clase problematica:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod afectado:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append("🔸 <b>Mod no identificado.</b> Revisa mods recientes, de desarrollo o mal empaquetados.<br>");
		}

		sb.append("🔸 <b>Causa:</b> El archivo <code>META-INF/services/...</code> del mod esta corrupto, ");
		sb.append("es incompatible con esta version de Forge/NeoForge, o el mod es para una version incorrecta.<br>");
		sb.append("🔸 <b>Consecuencia:</b> Forge/NeoForge no puede registrar dependencias del mod, ");
		sb.append("lo que impide el inicio del juego.<br>");
		sb.append("🔸 <b>Solucion:</b> Actualiza, reinstala o elimina el mod problemático. ");
		sb.append(
				"Si usas mods de desarrollo, asegurate de que esten compilados para tu version exacta de Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Error de Configuracion de Servicio (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identifica el mod causante: revisa mods instalados recientemente o de desarrollo.";
		}
		return "1. El mod problemático es: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Actualiza, reinstala o elimina el mod. Asegurate de usar una version compatible con tu Forge/NeoForge.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris neutro para nombres de clase/campo

		return "<b style='color:#" + colorError + "'>Error critico: Campo inexistente.</b><br>"
				+ "El mod intento acceder al campo <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "que no existe en esta version del juego o de otro mod.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Campo Inexistente (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Este error suele ocurrir cuando un mod es incompatible con la version actual del juego o de otro mod.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Actualiza todos los mods afectados. Si el problema persiste, contacta al autor del mod que genero el error.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris para metodos/clases

		return "<b style='color:#" + colorError + "'>Error critico: Metodo inexistente.</b><br>"
				+ "El mod intento llamar al metodo <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "que no existe en esta version del juego o de otro mod.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Metodo Inexistente (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Este error ocurre cuando un mod es incompatible con la version actual del juego o de otro mod.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Actualiza todos los mods involucrados. Si persiste, reporta el error al autor del mod afectado.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = MonitorDePID.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png")
				.toAbsolutePath().toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>¿Necesitas ayuda?</strong><br>"
				+ "  Si no sabes cómo arreglarlo o la razón no está aquí, puedes recibir ayuda en nuestras redes sociales. "
				+ "  Usa el botón <img src='" + iconoCompartir
				+ "' alt='Compartir' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Compartir</strong> para obtener enlaces a los registros y a los resultados para nuestro equipo. "
				+ "  Si eres un creador de modpack o una corporación, edita <code>crash_detector/plantilla.htm</code> "
				+ "  para personalizar los enlaces de tu equipo." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		// TODO Auto-generated method stub
		return "Restablecer Plantilla";
	}

	@Override
	public String restablecer() {
		// TODO Auto-generated method stub
		return "Restablecer";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		// TODO Auto-generated method stub
		return "¿Restablecer " + nombreImagen + " a valores predeterminados?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		// TODO Auto-generated method stub
		return "¿Restablecer plantilla a valores predeterminados?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Faltan clases de AzureLib. Si ya tienes AzureLib, por favor instala una versión anterior al 8 de octubre de 2025. Era común. Si no tienes AzureLib, instala la versión actual.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "El mod <code>healight</code> está causando un error crítico: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Este error ocurre porque el mod intenta acceder a un campo que ya no existe en la version de MCForge 47.10 Minecraft 1.20+. "
				+ "El juego no puede iniciar debido a este problema.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Elimina o actualiza el mod <code>healight</code>. "
				+ "La versión actual no es compatible con MinecraftForge 47.10 para 1.20.1. "
				+ "Busca una versión más reciente del mod o considera usar una alternativa.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Error crítico: healight - Campo 'INT' no encontrado";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("La clase <code>").append(clase)
				.append("</code> no implementa el método requerido:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("de la interfaz <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Mod o archivo sospechoso: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Este error ocurre cuando un mod implementa una interfaz pero omite un método obligatorio. "
				+ "Actualiza <b>ambos mods</b> involucrados (el que define la interfaz y el que la implementa). "
				+ "Si no sabes cuáles son, busca los nombres que aparecen en el mensaje de error.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Método de interfaz no implementado (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Un mod está intentando cargar una clase del <b>lado del cliente</b> "
				+ "(<code>AnimationMetadataSection</code>) en un <b>servidor dedicado</b>, lo que es imposible. "
				+ "Este error suele aparecer cuando un mod no separa correctamente su código entre cliente y servidor. "
				+ "La presencia de <code>ModernFix</code> puede exponer este problema, aunque no es la causa directa.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Opción rápida:</b> Elimina temporalmente <code>ModernFix</code> para confirmar si el servidor inicia. "
				+ "Si funciona, el problema está en otro mod que carga clases del cliente en el servidor.<br>"
				+ "• <b>Solución permanente:</b> Identifica el mod culpable (busca mods con recursos animados, texturas personalizadas o librerías gráficas) y actualízalo o elimínalo.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Clase de cliente cargada en servidor (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "El archivo de configuración de un mod de <code>Sinytra Connector</code> está corrupto. "
				+ "Esto suele ocurrir cuando el archivo se llena de caracteres nulos (<code>\\u0000</code>) "
				+ "debido a un cierre inesperado del juego, fallos de escritura o conflictos de mods.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Navega a la carpeta <code>config/</code> de tu instancia de Minecraft.<br>"
				+ "• Busca y elimina las configs de mods de connector.<br>"
				+ "• Reinicia el juego: Sinytra Connector generará un nuevo archivo de configuración limpio.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Configuración de Sinytra Connector corrupta";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "El archivo <code>" + nombreJar
				+ "</code> está corrupto o incompleto.<br>"
				+ "El sistema no puede leer su contenido porque falta el encabezado final del archivo ZIP.<br>"
				+ "Este error suele ocurrir tras una descarga interrumpida o un fallo del lanzador.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Archivo JAR corrupto (con nombre específico)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Elimina el archivo corrupto</b> y vuelve a descargarlo desde la fuente oficial (CurseForge, MinecraftStorage, etc.).<br>"
				+ "• Si usas un lanzador como CurseForge, Technic o Luna Pixel, considera cambiar a <b>ATLauncher</b> o <b>Prism Launcher</b>, "
				+ "que verifican mejor la integridad de los archivos.<br>"
				+ "• Asegúrate de que tu conexión a internet sea estable durante la descarga.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "No se puede cargar el mundo porque uno de sus archivos NBT está corrupto "
				+ "(por ejemplo: <code>level.dat</code>, <code>playerdata/*.dat</code>, o chunks).<br>"
				+ "El error específico es: <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Antes de intentar cualquier reparación, haz una copia de seguridad completa de la carpeta del mundo.</b><br><br>"
				+ "Puedes intentar reparar el archivo corrupto usando un <b>editor NBT</b> como <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Si el daño es grave, usa un <b>editor hexadecimal</b> (como HxD) para inspeccionar y corregir bytes inválidos "
				+ "(solo si tienes experiencia con el formato NBT).<br>"
				+ "Como último recurso, restaura desde una copia de seguridad o usa el <i>world repair</i> de mods como <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Haz una copia de seguridad completa de la carpeta del mundo</b> antes de intentar cualquier reparación.<br>"
				+ "• Usa un editor NBT (como NBT Studio) para abrir y corregir el archivo dañado.<br>"
				+ "• Si falla, inspecciona el archivo con un editor hexadecimal en la posición del byte corrupto.<br>"
				+ "• Si no tienes experiencia, restaura desde una copia de seguridad reciente.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Mundo corrupto: error al cargar datos NBT";
	}

	@Override
	public String problema_con_openAL() {
		// TODO Auto-generated method stub
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tienes un problema con OpenAL. A veces los controladores Nouveau pueden causarlo, pero a veces la versión de OpenAL incluida en la aplicación no es compatible con la versión en tu distribución y necesitas usar la versión proporcionada por tu distro. Consulta esta guía para más ayuda: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Solución a problemas de sonido en Minecraft en Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "El servidor no puede iniciar porque un archivo del mundo está bloqueado por otro proceso.<br>"
				+ "Esto suele ocurrir si:<br>" + "• Ya hay una instancia del servidor en ejecución.<br>"
				+ "• Un antivirus o explorador de archivos tiene abierta la carpeta del mundo.<br>"
				+ "• El proceso anterior no se cerró correctamente y dejó archivos bloqueados.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Cierra todas las instancias del servidor</b> (incluyendo procesos en segundo plano como javaw.exe).<br>"
				+ "• Si usas un panel de hosting (como Multicraft), reinicia completamente el servidor desde el panel.<br>"
				+ "• <b>Desactiva temporalmente tu antivirus</b> si sospechas que está bloqueando los archivos.<br>"
				+ "• En sistemas locales, cierra cualquier ventana del Explorador de Windows que muestre la carpeta del mundo.<br>"
				+ "• Si el problema persiste, elimina manualmente el archivo <code>session.lock</code> dentro de la carpeta del mundo (solo si estás seguro de que no hay otro servidor activo).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Archivo del mundo bloqueado por otro proceso";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "El mod intentó extender la clase <code>"
				+ clasePadreFinal + "</code>, " + "pero esta clase ahora es <b>final</b> y no puede heredarse.<br>"
				+ "La clase problemática es: <code>" + claseHija + "</code>.<br><br>"
				+ "Esto suele ocurrir cuando un mod está compilado para una versión anterior de Minecraft o de otro mod base, "
				+ "que ha marcado clases como <code>final</code> en versiones recientes.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Actualiza todos los mods involucrados</b>, especialmente aquellos que podrían estar relacionados con el mod base mencionado.<br>"
				+ "• Si el problema persiste, busca una versión del mod compatible con tu versión actual de Minecraft y sus dependencias.<br>"
				+ "• En algunos casos, eliminar temporalmente el mod que contiene la clase hija puede ayudar a confirmar la causa.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Intento de heredar de una clase final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Estás usando <b>Rubidium</b> (un fork obsoleto de Sodium para Forge) junto con <b>Iris u Oculus</b>.<br>"
				+ "En versiones recientes de Minecraft (1.19.2+), "
				+ "Rubidium no ha seguido el ritmo de Sodium y sus dependencias han tenido problemas.<br><br>"
				+ "Este error también puede ocurrir si hay un conflicto entre mods de rendimiento (Sodium, Rubidium, Embeddium, Bedium, Xeonium, etc.) o Iris Shaders y otro mod.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Elimina Rubidium</b> de tu carpeta <code>mods</code>.<br>"
				+ "• <b>Instala <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "el fork activo y compatible de Sodium para Forge que sí soporta Iris/Oculus en 1.20+.<br>"
				+ "• Asegúrate de no tener más de un fork de Sodium instalado al mismo tiempo (por ejemplo: Rubidium + Embeddium).<br>"
				+ "• Si usas Oculus en lugar de Iris, verifica que también sea compatible con tu versión de Forge y Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium obsoleto con Iris/Oculus (OptionInstance es final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "El mod <code>Simple Voice Chat</code> no puede iniciar su servidor de voz porque "
				+ "el puerto UDP ya está en uso o la dirección IP configurada no es válida.<br>"
				+ "Esto no impide que el juego se inicie, pero desactiva la funcionalidad de chat de voz.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Cierra cualquier otra instancia de Minecraft</b> o aplicación que use el puerto UDP 24454.<br>"
				+ "• Si estás en un servidor, asegúrate de que <b>ningún otro servicio</b> esté usando ese puerto.<br>"
				+ "• En la configuración del mod (<code>config/voicechat/</code>), cambia el puerto UDP a uno libre (por ejemplo, 24455).<br>"
				+ "• Si usas una dirección IP personalizada, verifica que sea correcta o déjala en blanco para usar la predeterminada.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat: puerto UDP ocupado o IP inválida";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "El BlockItem <code>" + nombreBlockItem
				+ "</code> tiene un bloque nulo.<br>"
				+ "Este error suele ocurrir en <b>addons de Create</b> (como <code>dndecor</code>, <code>createdeco</code>) "
				+ "cuando hay conflictos con <code>Amendments</code>, <code>Moonshine</code>, o una inicialización incorrecta de bloques.<br>"
				+ "<b>Nota:</b> Este no es un error de Amendments directamente, sino un síntoma de un problema más profundo en la carga de registros.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Actualiza todos los mods relacionados:</b> Create, Amendments, Moonshine, y cualquier addon (especialmente <code>dndecor</code> y <code>createdeco</code>).<br>"
				+ "• Si el problema persiste, <b>elimina temporalmente los addons de Create</b> uno por uno para identificar al culpable.<br>"
				+ "• Asegúrate de que <b>Amendments y Moonshine sean compatibles</b> con tu versión de Create y Forge.<br>"
				+ "• Revisa si hay versiones beta o forks actualizados de los addons problemáticos.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem nulo en addon de Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>")
				.append("Se encontraron mods que no pertenecen a ninguna plataforma activa (Forge, Fabric, etc.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Esto suele ocurrir cuando:<br>")
				.append("• Se mezclan mods de <b>Fabric y Forge</b> en la misma carpeta.<br>")
				.append("• Se instala un mod para una versión incompatible de Minecraft.<br>")
				.append("• El mod está corrupto o no es un archivo JAR válido.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Verifica que todos los mods sean para la misma plataforma</b> (Forge <b>o</b> Fabric, no ambos).<br>"
				+ "• Usa el <b>árbol de mods</b> para identificar qué plataforma detecta cada archivo.<br>"
				+ "• Elimina cualquier mod que no reconozcas o que sea de una plataforma diferente.<br>"
				+ "• Si usas un lanzador como CurseForge o Prism, asegúrate de que el perfil esté configurado correctamente.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod incompatible con cargador activo";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "No se pudo crear el modelo <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Esto indica que el mod <code>" + modid
				+ "</code> tiene recursos corruptos, faltantes " + "o incompatibles con tu versión de Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Actualiza el mod</b> a la última versión compatible con tu instancia.<br>"
				+ "• Si usas una versión de desarrollo o personalizada, vuelve a la versión oficial.<br>"
				+ "• Verifica que el archivo JAR no esté corrupto (reinstálalo).<br>"
				+ "• Si el problema persiste, reporta el error al autor del mod incluyendo este log.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Fallo al crear modelo de recurso";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Se ha detectado un conflicto crítico entre los mods <code>Moonlight</code> e <code>Iceberg</code>.<br>"
				+ "Ambos intentan registrar sistemas de recarga de recursos de forma incompatible, "
				+ "lo que provoca un fallo de OpenGL al no haber un contexto gráfico válido.<br>"
				+ "Este problema es común cuando se usan versiones de Forge que incluyen adaptadores de mods de Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Actualiza ambos mods</b> a sus últimas versiones compatibles con tu versión de Forge.<br>"
				+ "• Si el problema persiste, <b>elimina temporalmente Iceberg</b>, ya que Moonlight suele ser una dependencia más crítica para otros mods.<br>"
				+ "• Asegúrate de no tener versiones duplicadas o mezcladas de Forge/Fabric de estos mods.<br>"
				+ "• Revisa si algún otro mod (como Supplementaries, Citadel, etc.) ya incluye funcionalidad de Iceberg internamente.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Conflicto crítico: Moonlight vs Iceberg (OpenGL sin contexto)";
	}

	@Override
	public String instantanea() {
		// TODO Auto-generated method stub
		return "Instantánea";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		// TODO Auto-generated method stub
		return "Desde Última Instantánea";
	}

	@Override
	public String seleccionarUnArchivo() {
		// TODO Auto-generated method stub
		return "Seleccionar un archivo";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		// TODO Auto-generated method stub
		return "Instantánea creada correctamente";
	}

	@Override
	public String errorCreandoInstantanea() {
		// TODO Auto-generated method stub
		return "Error al crear la instantánea";
	}

	@Override
	public String consejo() {
		// TODO Auto-generated method stub
		return "Consejo";
	}

	@Override
	public String resultadoMuestra() {
		// TODO Auto-generated method stub
		return "Resultado Muestra";
	}

	@Override
	public String historaDeModsDesc() {
		// TODO Auto-generated method stub
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Consejo:</b> Selecciona dos archivos de historial para comparar la lista de mods. "
				+ "  El resultado muestra <span style='color:%s;'>añadidos (+)</span> y "
				+ "  <span style='color:%s;'>eliminados (&#8722;)</span> basados en nombres normalizados. "
				+ "  Usa el botón 'Instantánea' para crear una copia de un archivo existente con extensión .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		// TODO Auto-generated method stub
		return "Obtener Enlaces a Registros Como Markdown sin Informe";
	}

	@Override
	public String titulo_configuracion() {
		return "Configuración";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Error inesperado al compartir.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Error inesperado al generar enlaces.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Error inesperado al procesar el botón.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "No hay archivo asociado para abrir.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "El archivo no existe:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "No se pudo abrir en un editor.\nSe copiará la ruta al portapapeles.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "No se pudo abrir el archivo; la ruta se copió al portapapeles.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "El escritorio no está soportado; se copió la ruta al portapapeles.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Estás experimentando un límite de solicitudes. Intenta usar otro sitio de registro o otra API de registro.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		// TODO Auto-generated method stub
		return "Compartir Enlace";
	}

	@Override
	public String infoDeTrazos() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Arreglar las partes superiores de los troncos es la primera prioridad. "
				+ "El formato es Nivel, Línea. " + "Todos los registros tienen un sistema de numeración. "
				+ Verificaciones.nl_html
				+ "Generalmente necesitas buscar en los niveles más bajos en todos los registros; trazos con niveles altos generalmente son falsos positivos. "
				+ "Es importante usar tu habilidad para ver en la consola, ya que el análisis de trazos no es perfecto cuando hay muchos trazos."
				+ "</b>";
	}

}
