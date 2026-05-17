package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Espanol implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "es";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "espanol";
	}

	@Override
	public String nombre_del_idioma() {
		return "Español";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_mexico.png");
	}

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
				+ "'>Theseus también tiene más problemas, incluyendo fallas al eliminar mods cuando lo intentas. Si necesitas jugar con archivos mrpack, puedes usar otros lanzadores como Prism Launcher (solo para modrinth.com), ATLauncher (solo para modrinth.com) o Hello Minecraft Launcher (compatible con modrinth.com y bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Estás usando \"Omitir inicio Launcher\" (CurseForge App). A veces esto causa problemas difíciles de detectar. Esto se debe a la opción \"Omitir inicio Launcher\" en versiones antiguas de la App CurseForge o en la versión nueva. Desactívala y usa la opción \"Mojang Launcher\" en la configuración de CurseForge. Puedes ver este video en inglés de Claws of Berk (ir a 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>aquí</a>.</b>";
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
				+ "en <a href=\"https://securelogger.net\">securelogger.net</a>. Al presionar el botón para compartir el informe, "
				+ "tu informe se envía al punto final seleccionado (por defecto asbestosstar.egoism.jp) (se puede cambiar en la parte inferior). "
				+ "Puedes compartir todos los registros seleccionados junto con el informe. Si no deseas subir, ¡no uses este diálogo! "
				+ "No procesamos tu informe en el punto final oficial (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "solo eliminamos enlaces no permitidos. El código está aquí: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">código fuente</a>. "
				+ "Esto se utiliza únicamente para mostrar información sobre tu fallo y el enlace a los registros. Sin embargo, es posible usar un punto final personalizado que podría no tener los mismos métodos. "
				+ "Estás usando el sitio de informes " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " y el sitio de registros " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "También puedes compartir registros individuales sin un informe presionando los botones de compartir junto a los nombres de los registros individuales; "
				+ "los registros irán al sitio de registros seleccionado. CrashDetector tiene una anonimización de registros predeterminada, que intenta eliminar nombres de usuario, UUID, "
				+ "tokens de acceso, IDs de sesión, direcciones IP y otros datos. Sin embargo, no es perfecta. Aún así, el autor del modpack puede desactivarla. "
				+ "Se puede activar o desactivar con la casilla de verificación en la parte inferior de esta pantalla. Eres el controlador de tus propios datos; tú decides dónde subes tus datos. "
				+ "Los sitios de registro son propiedad de terceros cuya propiedad a menudo está oculta por privacidad. Asumes toda la responsabilidad de gestionar tus datos y los riesgos involucrados. "
				+ "El Diálogo de Compartir de CrashDetector es simplemente una interfaz que te permite gestionar eso. "
				+ "Es importante que estés al tanto del RGPD y ARCO. "
				+ "Si estás en Europa, puedes usar <a href=\"https://securelogger.top\">securelogger.top</a> alojado en Alemania por Hetzner. "
				+ "Para más información legal, consulta los siguientes enlaces: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">RGPD</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Política Básica de Protección de Datos en Japón</a>.";
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

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "¡ALERTA! Crash Assistant es un detector de malware falso. Bloquea intencionalmente el lanzamiento del juego, ignorando tu libertad de seguir jugando con los mods que apunta. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Ver código MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Ver código JarInJarHelper.java</a>. "
				+ "Solo este mod está en su lista en este momento y solo están yendo realmente tras el sitio de registro predeterminado, que puede ser cambiado por el usuario, y eso solo ocurre si eliges explícitamente usar la función integrada de compartir registros. CrashAssistant NO realiza ninguna verificación para determinar qué sitio de registro se está utilizando ni explica cómo cambiarlo (hay un menú desplegable en la parte inferior del cuadro de diálogo de compartir), y sin importar qué sitio tengas configurado, CrashAssistant bloqueará el lanzamiento del juego. En su mensaje dicen que hagas tu propia investigación, HAZLO, investiga el código de CrashDetector y Crash Assistant y entiende lo que hacen, NO te bases en una apelación a la autoridad.</b>";
	}

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
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Error crítico de configuración NightConfig/Forge: "
				+ "Archivo de configuración corrupto o incompleto. "
				+ "Esto puede ser causado por archivos de configuración vacíos (a menudo de 0 bytes) en la carpeta 'config' en versiones anteriores o personalizadas de NightConfig. "
				+ "Para la mayoría de las versiones, Night Config Fixes resolverá el problema, pero si estás utilizando una versión no compatible o una versión personalizada de NightConfig, deberás eliminar los archivos de configuración manualmente. "
				+ "Este problema es más común en versiones antiguas de MC Forge (que viene con NightConfig) y en mods de FabricMC antiguos que empaquetan NightConfig, pero también puede existir en algunas versiones personalizadas de NightConfig. "
				+ "Más información sobre las soluciones está disponible en <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
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
				+ "'>Hubo una excepción mientras el mundo cargaba los chunks. Si existe para tu plataforma, es posible que FeatureRecycler pueda resolver el problema. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
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
	 * Devuelve el mensaje de error para entidades o entidades de bloque
	 * problemáticas, detallando los pasos de recuperación según la plataforma.
	 */
	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		// Mensaje principal: Solo el texto descriptivo lleva el color de error
		String mensajeBase = "<span style='color:#" + color + "'>La Entidad o Entidad de Bloque '</span>" + nombre
				+ "<span style='color:#" + color + "'>' del tipo '</span>" + tipo + "<span style='color:#" + color
				+ "'>' en la ubicación </span>" + coords + "<span style='color:#" + color
				+ "'> está causando errores de ticking.</span><br><br>";

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>" + "Instrucciones de recuperación:<br>"
				+ "1. **MCForge**: Ve a '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Ve a 'config/neoforge-server.toml'.<br>"
				+ "   *(Nota: En partidas locales/Singleplayer, los mundos están en la carpeta 'saves')*.<br>"
				+ "3. Cambia **removeErroringBlockEntities** y **removeErroringEntities** a **true**.<br><br>"
				+ "Otras opciones:<br>"
				+ "- **MCEdit**: Úsalo para eliminar manualmente la entidad en las coordenadas indicadas.<br>"
				+ "- **Neruina (Mod)**: Puede evitar el crash, pero no siempre funciona y puede dificultar el debug al estar instalado."
				+ "</span>";

		return mensajeBase + instrucciones;
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
		sb.append(
				"<br/><b>Solución:</b> instala o activa el mod/datapack que define esas funciones y reinicia. Otra razón común para este problema es cuando tienes el mod necesario, pero tiene un problema o conflicto con otro mod; por ejemplo, Terralith tiene muchos problemas y puede provocar este error y otros, incluyendo errores con JSON.");
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
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

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
				+ "'>Tienes un problema con OpenAL. A veces los controladores Nouveau pueden causarlo, pero a veces la versión de OpenAL incluida en la aplicación no es compatible con la versión en tu distribución y necesitas usar la versión proporcionada por tu distro, es especialmente común con distribuciones Red Hat y con mods de sonido como Sound Physics Remastered. Consulta esta guía para más ayuda: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Solución a problemas de sonido en Minecraft en Linux</a>.</span>";
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

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Texto del botón para el buscador de canario de orden. Ejemplo: "Buscador de
	 * canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Buscador de canario de orden";
	}

	/**
	 * Mensaje mostrado en el cuadro de diálogo informando que la función estará
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Esta función estará disponible próximamente.";
	}

	/**
	 * Título del cuadro de diálogo que informa sobre la disponibilidad futura del
	 * buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Próximamente";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mods Incompatibles Con Crash Assistant (Falso)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		// TODO Auto-generated method stub
		return "Mod Incompatible Con Paquete de Mods (Modpack) usando CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant tiene una lista de mods que dice son incompatibles, pero no tenemos evidencia de que lo sean y el error solo está en inglés. Si deseas jugar con estos mods, puedes editar el archivo <code>config/crash_assistant/config.toml</code> y cambiar <code>enabled = true</code> en la sección [compatibility] a <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		// TODO Auto-generated method stub
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant tiene la capacidad de marcar mods como incompatibles, pero a veces esto es incorrecto y el mensaje de error solo está disponible en inglés. Si deseas jugar con estos mods, puedes editar el archivo <code>config/crash_assistant/problematic_mods_config.json</code> y cambiar <code>should_crash_on_startup</code> de <code>true</code> a <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: El mod '" + modId
				+ "' requiere el mod '" + dependencia + "'. Actualmente, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: El mod '" + modId
				+ "' requiere la versión '" + requerido + "' o superior de '" + dependencia
				+ "', pero el mod no está instalado." + "</span>";
	}

	// En la clase MonitorDePID.idioma (añadir este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Error: El mod '" + modId
				+ "' es incompatible con la versión actual de '" + dependencia + "'. "
				+ "Debes eliminar el mod 'Iris/Oculus & GeckoLib Compat' ya que es incompatible con Superb Warfare y no funciona con la última versión de GeckoLib. "
				+ "Versión actual: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Error: No se pudo ejecutar la tarea para la clase '" + clase + "'. "
				+ "Este error es común con mods que no son compatibles entre sí o que tienen conflictos con otros mods instalados.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Fallos en la ejecución de tareas";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Este tipo de error suele ocurrir cuando hay incompatibilidades entre mods. "
				+ "Especialmente común con mods que no funcionan correctamente con ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Clase problemática:";
	}

	public String no_se_encontraron_clases_problema() {
		return "No se encontraron clases específicas con problemas de ejecución.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre OptiFine y Entity Model Features (EMF). "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Conflicto OptiFine y Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Desinstala OptiFine o Entity Model Features, ya que no son compatibles entre sí.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre OptiFine y Fusion. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Conflicto OptiFine y Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Desinstala OptiFine o Fusion, ya que no son compatibles entre sí.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (requerido por Create) necesita Sodium 0.6.0-beta.2 o superior. Rubidium es 0.5.3. "
				+ "Considera usar <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> como alternativa."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Conflicto Flywheel y versión de Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Actualiza Sodium a 0.6.0-beta.2 o superior, o instala <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> como alternativa compatible.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre OptiFine y Epic Fight. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Conflicto OptiFine y Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Desinstala OptiFine o Epic Fight, ya que no son compatibles entre sí.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre OptiFine y Rubidium. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Conflicto OptiFine y Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Desinstala OptiFine o Rubidium, ya que no son compatibles entre sí.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam está intentando cargar en un servidor dedicado, pero solo es compatible con el cliente. "
				+ "Elimina FreeCam del servidor o asegúrate de que solo esté instalado en el cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam en servidor dedicado";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Elimina FreeCam del servidor dedicado, ya que solo debe estar instalado en el cliente.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) está intentando cargar en un servidor dedicado, pero solo es compatible con el cliente. "
				+ "Elimina ETF del servidor o asegúrate de que solo esté instalado en el cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features en servidor dedicado";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Elimina Entity Texture Features del servidor dedicado, ya que solo debe estar instalado en el cliente.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Debes aceptar el EULA de Minecraft para ejecutar el servidor. "
				+ "Edita el archivo eula.txt y cambia 'eula=false' a 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA de Minecraft no aceptado";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Edita el archivo eula.txt en la carpeta del servidor y cambia 'eula=false' a 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine está intentando cargar en un servidor dedicado, pero solo es compatible con el cliente. "
				+ "Elimina OptiFine del servidor o asegúrate de que solo esté instalado en el cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine en servidor dedicado";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Elimina OptiFine del servidor dedicado, ya que solo debe estar instalado en el cliente. Este problema también suele deberse a la instalación de una versión de Optifine para la versión incorrecta de Minecraft o a un conflicto con otro mod y Optifine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks está marcado incorrectamente para 1.20.1 pero usa métodos de 1.21.1. "
				+ "El mod está intentando usar ResourceLocation.fromNamespaceAndPath, que no existe en 1.20.1."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Error de versión en Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Asegúrate de usar la versión correcta de Iron's Spellbooks compatible con tu versión de Minecraft.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre OptiFine y Embeddium. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Conflicto OptiFine y Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Desinstala OptiFine o Embeddium, ya que no son compatibles entre sí.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Es común con mods de generación del mundo conflictivos, especialmente Terralinth, AmplifiedNether, Nullscape e Incendium, y otros mods de generación de mundo. También es posible que necesites instalar un mod faltante.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable está intentando cargar en un servidor dedicado, pero solo es compatible con el cliente. "
				+ "Elimina Controllable del servidor o asegúrate de que solo esté instalado en el cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable en servidor dedicado";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Elimina Controllable del servidor dedicado, ya que solo debe estar instalado en el cliente.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries está causando un error que impide la carga del servidor. "
				+ "El mod tiene problemas con el registro de comportamientos de fuego que provocan un fallo durante la carga de datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries impide la carga del servidor";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Intenta actualizar Supplementaries a la última versión o desactívalo temporalmente para permitir la carga del servidor.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) encontró un problema con módulos Jackson faltantes. "
				+ "Algunos mods como Valkyrien Skies pueden causar este error al no incluir todas las dependencias necesarias."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Módulo Jackson faltante en Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Elimina Groovy Modloader y mods relacionados como Valkyrien Skies que puedan causar conflictos de dependencias.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat encontró un nombre de bloque de madera inválido. "
				+ "Every Compat generalmente tiene muchos problemas. ¡No lo uses!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Nombre inválido en Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Verifica los packs de recursos o mods que usan Every Compat, ya que pueden tener nombres de bloques inválidos.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un código de error (-1073741819) que puede ser causado por overlays como GameCaster de Razer, Discord, OBS Studio o problemas con drivers de NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Código de error -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Intenta desactivar overlays como GameCaster, Discord o OBS Studio, y verifica que tus drivers de NVIDIA estén actualizados.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips requiere Immersive Messages como dependencia pero no está instalada. "
				+ "Instala Immersive Messages para que Immersive Tooltips funcione correctamente." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips sin dependencia";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Instala Immersive Messages, ya que es una dependencia necesaria para Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins tiene un problema de compatibilidad con Apoli Mod donde ItemStack no puede ser casteado a EntityLinkedItemStack. "
				+ "Esto es común en versiones superiores a 6.6.0. Considera usar una versión anterior o cambiar entre versiones de Fabric y Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Error de casteo en Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Usa una versión de Medieval Origins 6.6.0 o anterior, o intenta cambiar entre versiones de Fabric y Forge del mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether está causando un error con un Registry Object no presente en MusicManager. "
				+ "Este problema está relacionado con el mixin de MusicManager de Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Error de MusicManager en Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Intenta actualizar Reign of Nether o eliminarlo temporalmente para resolver el error.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel solo soporta el servidor YSM en Linux o Android. "
				+ "Este problema ha sido corregido en versiones más recientes desde el 23 de noviembre de 2025 en Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel no compatible con Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Actualiza YesSteveModel a una versión más reciente desde Modrinth, ya que el problema ha sido corregido después del 23 de noviembre.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre Moving Elevators y OptiFine. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Conflicto Moving Elevators y OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Desinstala OptiFine o Moving Elevators, ya que no son compatibles entre sí.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto crítico entre Fabric API (fabric-resource-loader-v0) y OptiFine. "
				+ "Estos mods no son compatibles y provocan un fallo de inyección que impide el inicio del juego."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Conflicto Fabric API y OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Desinstala OptiFine o actualiza Fabric API a una versión compatible.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un mod tiene un ITransformationService defectuoso que no puede ser instanciado: " + claseProveedor
				+ ". " + "Este mod debe ser eliminado para permitir la carga del juego." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService defectuoso";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Elimina el mod que contiene la clase " + claseProveedor
				+ ", ya que tiene un ITransformationService defectuoso.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Un mod tiene una especificación de versión inválida. "
				+ "La versión debe estar rodeada por corchetes cuadrados. "
				+ "Puedes usar la utilidad grep/greprf del panel lateral buscando la versión </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> para identificar qué mod tiene el problema.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Versión inválida en mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Usa la utilidad grep/greprf del panel lateral para buscar la versión problemática y encuentra el mod que la contiene.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un error de stack smashing que terminó el proceso. "
				+ "Esto puede ser causado por problemas con Early Window en Forge/NeoForge/PillowMC o con LWJGL 3.2.2 y más nuevos."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Detectado";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Verifica los ajustes de Early Window y considera usar una versión diferente de LWJGL o revisar los mods relacionados con la ventana temprana.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore solo es para un modpack específico y no debe usarse en instalaciones generales, ya que causa un problema."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore con versión incompatible de Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Elimina GregTechEasyCore, ya que solo es para un modpack específico y no es compatible con tu instalación general.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó un conflicto entre MoniLabs y Connector Extras relacionado con modificaciones de KubeJS. "
				+ "Estos mods no son compatibles en sus modificaciones de KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Conflicto MoniLabs y Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Intenta desinstalar uno de los mods (MoniLabs o Connector Extras) ya que tienen conflictos con sus modificaciones de KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris requiere Distant Horizons [2.0.4] o DH API versión [1.1.0] o más reciente. "
				+ "Consulta la guía de compatibilidad en https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e para resolver el problema."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Compatibilidad Iris y Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Consulta la guía de compatibilidad en https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e y actualiza Iris y Distant Horizons a versiones compatibles.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de Minecraft. Posibles razones:</b>" + "<ul>"
				+ "<li>Tienes mods para otras versiones del juego. Puedes usar <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> para verificar si la clase existe en tu versión.</li>"
				+ "<li>Tienes una instalación defectuosa de Minecraft (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "<li>Tienes un coremod defectuoso (si un coremod falla, puede bloquear la carga de la clase).</li>"
				+ "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de DangerZone. Posibles razones:</b>" + "<ul>"
				+ "<li>Tienes mods para otras versiones del juego.</li>" + "<li>Tienes coremods defectuosos.</li>"
				+ "<li>Tienes un lanzador o instalación defectuosa.</li>" + "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de FeatureCreep. Posibles razones:</b>" + "<ul>"
				+ "<li>Tienes mods para otras versiones de FeatureCreep (ej.: ESR vs Nightly o v4 vs v12).</li>"
				+ "<li>Puedes instalar FeatureCreep desde CurseForge o MinecraftStorage.</li>" + "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de ModLauncher. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de MinecraftForge, PillowMC o NeoForge (ModLauncher es usado con estos cargadores).</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una versión de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa de tu lanzador (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de Minecraft Forge. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de MinecraftForge.</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una versión de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de NeoForge. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de NeoForge.</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una versión de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de Fabric Loader. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de Fabric Loader.</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una versión de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "<li>Muchos mods requieren Fabric API. Por favor, instala Fabric API si es necesario.</li>" + "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tienes clases faltantes de PillowMC. Posibles razones:</b>" + "<ul>"
				+ "<li>Tus mods son para una build diferente de PillowMC.</li>"
				+ "<li>Hay muchas actualizaciones de modloaders para una versión de Minecraft.</li>"
				+ "<li>Tienes una instalación defectuosa (común con CurseForge App, ModrinthApp/Theseus/Astralrinth y otros lanzadores de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Ver tutorial</a> para solucionar problemas con CurseForge.</li>"
				+ "</ul>"
				+ "<p>Nota: Puedes usar la herramienta <b>grepr/fgrepr</b> en la barra lateral para encontrar los mods que hacen referencia a las clases faltantes, siempre que uses '/' en los nombres.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Tienes un mod que intencionalmente causa lag. Uranium es un mod de lag. No siempre causa fallos, pero eventualmente puede hacerlo."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack está marcado como compatible con 1.19.* pero es para 1.20.*, lo que causa un error de clase no encontrada. "
				+ "El mod intenta usar DamageSources que no existen en la versión de Minecraft actual." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Error de versión en Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Asegúrate de usar la versión correcta de Falling Attack compatible con tu versión de Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Necesitas instalar CFR (Class File Reader) para usar esta funcionalidad.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append("En sistemas Linux, NetBSD o FreeBSD, puedes instalar CFR desde tu gestor de paquetes.<br>")
					.append("Busca el paquete en: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Alternativamente, puedes descargar la versión modificada usada por FabricMC desde:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Guárdalo en la siguiente carpeta:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Importante:</b> después de instalar CFR, debes reiniciar el mod para que lo reconozca correctamente.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Sin retrato disponible";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "No se pudo encontrar la clase: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Descompilador CFR – Sakura Riddle (No Oficial)";
	}

	@Override
	public String cfrClaseActual() {
		return "Clase actual";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Retrato de Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Error al cargar el retrato";
	}

	public String noticiaLegalCFR() {
		return "Este programa de interfaz gráfica (GUI) para decompilar mods está diseñado para ayudar a los usuarios a identificar las causas de fallos en el software. "
				+ "Sin embargo, la decompilación de mods puede ser necesaria, y los usuarios deben tener cuidado de no utilizar el código generado para infringir la Ley Federal de Derechos de Autor. "
				+ "Se recomienda revisar la licencia del mod correspondiente antes de usar cualquier código obtenido. Además, a menudo los mods proporcionan su código fuente oficialmente, "
				+ "que es generalmente más limpio y fácil de entender que el código descompilado. Recuerde que el respeto por la propiedad intelectual y las licencias de uso es fundamental para "
				+ "la comunidad de desarrollo de mods. Puede consultar la Ley Federal de Derechos de Autor en el siguiente enlace: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Español)</a> "
				+ "y la versión en inglés aquí: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Dado que estamos en CurseForge, también proporcionamos el enlace a la Ley de Derechos de Autor de EE. UU. en inglés: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Además, se recomienda que los usuarios consulten dónde se encuentran y se informen sobre las leyes aplicables en su ubicación. "
				+ "Nuestra GUI es solo para comprobaciones simples; para un análisis más avanzado, deberían usar el Fork Enigma de FabricMC disponible en "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Si desean editar archivos JAR para hacer parches cuando no hay código fuente disponible, pueden utilizar Recaf en "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">su sitio web</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Descargar CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Abrir carpeta de instalación";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Color de fondo principal";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Color del texto del botón de reinicio";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Color del texto del campo de búsqueda";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Color del texto del menú desplegable de filtro";
	}

	@Override
	public String colorTextoRenderer() {
		return "Color del texto del renderizador";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Color del texto del overlay de carga";
	}

	@Override
	public String colorBorde() {
		return "Color del borde";
	}

	@Override
	public String colorFondoRetrato() {
		return "Color de fondo en modo retrato";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Color del enlace para compartir";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Color de fondo del campo de compartir";
	}

	@Override
	public String rosaFondo() {
		return "Rosa de fondo";
	}

	@Override
	public String rosaSuave() {
		return "Rosa suave";
	}

	@Override
	public String moradoAcento() {
		return "Morado de acento";
	}

	@Override
	public String textoOscuro() {
		return "Texto oscuro";
	}

	@Override
	public String bordeSuave() {
		return "Borde suave";
	}

	@Override
	public String fondoCampo() {
		return "Fondo de campo";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Fondo de vista previa";
	}

	@Override
	public String sintaxisConstructor() {
		return "Color de sintaxis: constructor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Color de sintaxis: mensaje de ayuda";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Color de sintaxis: etiquetas HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Color de fondo de la ventana";
	}

	@Override
	public String colorPanel() {
		return "Color del panel";
	}

	@Override
	public String colorBotonTexto() {
		return "Color del texto del botón";
	}

	@Override
	public String colorCampo() {
		return "Color del campo";
	}

	@Override
	public String colorBordeDestacado() {
		return "Color del borde destacado";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Color de fondo de selección de texto";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Color del texto seleccionado";
	}

	@Override
	public String colorEstadoExito() {
		return "Color de estado: éxito";
	}

	@Override
	public String colorEstadoFallo() {
		return "Color de estado: fallo";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Color de estado: instantánea";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Color de resultado añadido";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Color de resultado eliminado";
	}

	@Override
	public String colorBordeScroll() {
		return "Color del borde del scroll";
	}

	@Override
	public String colorFondoPanel() {
		return "Color de fondo del panel";
	}

	@Override
	public String colorBeigeListas() {
		return "Beige de las listas";
	}

	@Override
	public String colorTextoListas() {
		return "Color del texto en listas";
	}

	@Override
	public String colorBordeListas() {
		return "Color del borde de las listas";
	}

	@Override
	public String colorBotonFondo() {
		return "Color de fondo del botón";
	}

	@Override
	public String colorBordeBoton() {
		return "Color del borde del botón";
	}

	@Override
	public String colorDoradoTexto() {
		return "Color dorado del texto";
	}

	@Override
	public String colorPila() {
		return "Color de la pila (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Color del texto del panel";
	}

	@Override
	public String colorTextoNegro() {
		return "Color de texto negro";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Color del texto principal";
	}

	@Override
	public String colorFondoResultados() {
		return "Color de fondo de los resultados";
	}

	@Override
	public String colorEstado() {
		return "Color de estado";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Color del texto de descripción";
	}

	@Override
	public String colorTextoEstado() {
		return "Color del texto de estado";
	}

	@Override
	public String colorTextoExtra() {
		return "Color del texto extra";
	}

	@Override
	public String colorSeparador() {
		return "Color del separador";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Se ha detectado un error nativo <code>StubRoutines::SafeFetch32</code>. "
				+ "Este problema ocurre en macOS con JDK 17.0.9 y se corrige en JDK 17.0.10 o versiones posteriores. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Error nativo SafeFetch32 en JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Actualiza tu JDK a la versión 17.0.10 o superior (por ejemplo, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Si usas un lanzador como MultiMC, Prism Launcher o TLauncher, configúralo para usar un JDK más reciente. "
				+ "Algunos ya incluyen JDK 17.0.15 integrado.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "El mod Spark también puede contribuir a este error. Considera desactivarlo temporalmente. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "El mod MCEF (Chromium Embedded Framework) está causando un cuelgue silencioso.</b>" + "<ul>"
				+ "<li>MCEF se está inicializando al final del log, lo que normalmente significa que el juego se quedó colgado durante la carga.</li>"
				+ "<li>Este mod es conocido por causar fallos en sistemas Linux, macOS o con ciertas versiones de Java.</li>"
				+ "<li>No siempre aparece un error explícito, pero el juego nunca llega al menú principal.</li>"
				+ "</ul>"
				+ "<p>Si no necesitas funcionalidad de navegador dentro del juego (como mapas web o páginas integradas), elimina el mod.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Problema de inicialización de MCEF (mod de navegador embebido)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Elimina el archivo del mod MCEF (busca 'mcef' en el nombre del archivo) de la carpeta 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Si realmente lo necesitas, asegúrate de usar una versión compatible con tu sistema operativo y versión de Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se ha detectado un conflicto entre <b>OptiFine</b> e <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine modifica el renderizado de Minecraft de forma incompatible con Iris u Oculus.</li>"
				+ "<li>El error <code>MixinLevelRenderer failed injection check</code> proviene de <code>mixins.iris.json</code> o <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Estos mods no pueden usarse juntos. Elimina OptiFine para usar shaders con Iris u Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Conflicto entre OptiFine e Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Elimina el archivo de OptiFine de la carpeta 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Usa Iris u Oculus sin OptiFine para shaders modernos.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se ha detectado un conflicto entre <b>ModernFix</b> y <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix no es compatible con OptiFine porque rompe funcionalidades de Forge y ralentiza el inicio.</li>"
				+ "<li>El propio ModernFix advierte: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Debes eliminar uno de los dos mods para que el juego funcione correctamente.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Conflicto entre ModernFix y OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Elimina OptiFine o ModernFix de la carpeta 'mods'. No pueden usarse juntos.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Si necesitas optimizaciones, considera usar solo OptiFine, o remplazar ModernFix por mods más ligeros como FerriteCore o EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Error: clave de registro inválida con caracteres no permitidos.</b>" + "<ul>"
				+ "<li><b>Clave detectada:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>En Minecraft, todas las claves de registro (etiquetas, recetas, logros, etc.) deben estar en <b>minúsculas</b> y usar solo letras, números, guiones bajos, guiones y barras.</li>"
				+ "<li>Este error suele ser causado por un mod mal programado o un datapack defectuoso.</li>" + "</ul>"
				+ "<p><b>Consejo importante:</b> Usa la herramienta <b>grepr</b> o <b>fgrepr</b> en la barra lateral y activa la opción <b>\"Buscar en archivos JAR\"</b> para encontrar qué mod contiene esta clave incorrecta.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Clave de registro con mayúsculas o caracteres inválidos";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Usa 'grepr' o 'fgrepr' con \"Buscar en archivos JAR\" para localizar el mod culpable.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Si no puedes identificar el mod, elimina mods recientes, especialmente los que añaden bloques, items o herramientas.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Error al cargar el mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>El mod falló al inicializar uno de sus componentes (por ejemplo, el menú de configuración).</li>"
				+ "<li>Esto suele ocurrir por incompatibilidad con la versión de Minecraft, Fabric o con otros mods.</li>"
				+ "</ul>" + "<p>Si el error persiste, elimina o actualiza el mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Error de inicialización de mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Elimina el mod '" + modNombre + "' de la carpeta 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Actualiza el mod '" + modNombre + "' a una versión compatible con tu instalación.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se ha detectado un error relacionado con el mod <b>En Garde!</b>.</b>" + "<ul>"
				+ "<li>Este mod añade mecánicas de combate cuerpo a cuerpo (parry, bloqueo, etc.).</li>"
				+ "<li>El error suele ocurrir por incompatibilidad con otros mods de combate (como Epic Fight, DualRiders, etc.) o por usar una versión incorrecta para tu Minecraft.</li>"
				+ "</ul>" + "<p>Si no usas combate avanzado, considera eliminar En Garde! para evitar conflictos.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Error en el mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Asegúrate de usar la versión de En Garde! compatible con tu versión de Minecraft y tu cargador (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Si usas otros mods de combate (Epic Fight, Caelus, etc.), desactívalos o elimina En Garde! para evitar conflictos.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se ha detectado un error causado por el mod <b>IdleTweaks</b>.</b>" + "<ul>"
				+ "<li>IdleTweaks intentó liberar un canal de red que ya no existe (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Este error suele ocurrir en versiones antiguas del mod o al usarlo en servidores mal configurados.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks es un mod de calidad de vida, pero puede causar inestabilidad. Considera actualizarlo o eliminarlo.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Error en IdleTweaks (canal de red desconocido)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Actualiza IdleTweaks a la última versión compatible con tu Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Elimina IdleTweaks de la carpeta 'mods' si no lo necesitas.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se ha detectado un error de autenticación (HTTP 401) al intentar iniciar sesión en Minecraft.</b>"
				+ "<p>Este error <b>rara vez es la causa directa del crash</b>, pero indica que estás usando una cuenta no autenticada (pirata).</p>"
				+ "<p>Los canales de soporte oficiales (proyectos corporativos, VTubers, creadores de modpacks, etc.) <b>no pueden ayudarte</b> si usas una copia pirata, "
				+ "debido a restricciones de sus reglas de chat, contratos, acuerdos con Mojang/Microsoft, o políticas de reputación.</p>"
				+ "<p>Esta verificación se puede <b>deshabilitar en la configuración corporativa</b> del detector. "
				+ "Advertencia: la detección antipiratería <b>no es perfecta</b> y puede activarse en entornos de desarrollo, con internet inestable, o al usar launchers modificados.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Derechos Miranda si intentas unirte al soporte igualmente:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft pirateado";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Deshabilitar verificación antipiratería";
	}

	@Override
	public String comprarMC() {
		return "Comprar Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Estás usando el lanzador <code>" + id
				+ "</code>, que <b>no está en la lista de lanzadores recomendados</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Aunque puede funcionar, los lanzadores no recomendados suelen causar:</p>" + "<ul>"
				+ "<li>Instalaciones corruptas de mods o la App.</li>"
				+ "<li>El juego no inicia o se cuelga sin error claro.</li>"
				+ "<li>Estructura de carpetas inusual (dificulta el diagnóstico).</li>"
				+ "<li>Comportamiento impredecible con Java, memoria o mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Para una mejor experiencia, usa uno de estos lanzadores recomendados:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Lanzador no recomendado";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Cambia a un lanzador de la lista recomendada.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Estás usando un <b>lanzador desaconsejado</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Los lanzadores desaconsejados pueden causar:</p>" + "<ul>"
				+ "<li>Instalaciones corruptas de la App o mods.</li>"
				+ "<li>El juego no inicia o falla silenciosamente.</li>"
				+ "<li>Organización inusual de archivos (difícil de depurar).</li>"
				+ "<li>Incertidumbre sobre cómo gestiona mods, Java o memoria.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Se recomienda encarecidamente usar uno de los siguientes lanzadores:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Lanzador desaconsejado";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Cambia a un lanzador recomendado para recibir soporte.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Faltan mods recomendados para este entorno.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Faltan mods recomendados";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Instala los mods recomendados para una experiencia óptima.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectaron mods desaconsejados en tu instalación.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Mods desaconsejados detectados";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Elimina los mods desaconsejados para evitar problemas.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Se detectó manipulación no autorizada en archivos críticos. Has modificado archivos o estás usando un lanzador no confiable.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Manipulación detectada";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Reinstala los archivos originales para restaurar la integridad.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Configuración Corporativa";
	}

	@Override
	public String idiomaRespaldo() {
		return "Idioma de Respaldo";
	}

	@Override
	public String buscardorHabilitado() {
		return "Habilitar Buscardor";
	}

	@Override
	public String nombreHerramienta() {
		return "Nombre de la Herramienta";
	}

	@Override
	public String condenarPirateria() {
		return "Condenar Piratería";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Lanzadores Recomendados";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Lanzadores Desaconsejados";
	}

	@Override
	public String modsRecomendados() {
		return "Mods Recomendados";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods Desaconsejados";
	}

	@Override
	public String antiTamper() {
		return "AntiTamper";
	}

	@Override
	public String proximamente() {
		return "Próximamente";
	}

	@Override
	public String informacion() {
		return "Información";
	}

	@Override
	public String errorCargandoImagen() {
		return "Error cargando imagen";
	}

	@Override
	public String configuracionBasica() {
		return "Configuración Básica";
	}

	@Override
	public String funcionalidades() {
		return "Funcionalidades";
	}

	@Override
	public String derechosMiranda() {
		return "Derechos Miranda (MUY recomendados)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Gestión de Verificaciones";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Nombre";
	}

	@Override
	public String codigoVerificacion() {
		return "Código";
	}

	@Override
	public String documentacionVerificacion() {
		return "Documentación";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Verificaciones Habilitadas:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Verificaciones Deshabilitadas:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Deshabilitar todas no corporativas";
	}

	@Override
	public String verCodigo() {
		return "Ver Código";
	}

	@Override
	public String verDocumentacion() {
		return "Ver Documentación";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Seleccione una verificación para deshabilitar.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Seleccione una verificación para habilitar.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "Se deshabilitaron %d verificaciones no recomendadas para uso corporativo.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "No hay verificaciones no corporativas para deshabilitar.";
	}

	@Override
	public String operacionCompletada() {
		return "Operación completada";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Te extrañamos Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Color de Verificación Corporativa";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "Nombre del Lanzador";
	}

	@Override
	public String motivo() {
		return "Motivo";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Lanzadores Desaconsejados";
	}

	@Override
	public String moverADesaconsejados() {
		return "Desaconsejar";
	}

	@Override
	public String moverARecomendados() {
		return "Recomendar";
	}

	@Override
	public String guardarCambios() {
		return "Guardar Cambios";
	}

	@Override
	public String cancelar() {
		return "Cancelar";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Por favor selecciona un lanzador para mover.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "¡Los cambios han sido guardados exitosamente!";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Este lanzador no es recomendado debido a problemas de seguridad y estabilidad conocidos.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEn(String nombreLanzador) {
		return "This launcher is not recommended due to known security and stability issues.";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoPt(String nombreLanzador) {
		return "Este lançador não é recomendado devido a problemas conhecidos de segurança e estabilidade.";
	}

	@Override
	public String razones() {
		return "Razones";
	}

	@Override
	public String agregarLanzador() {
		return "Agregar lanzador";
	}

	@Override
	public String quitarLanzador() {
		return "Quitar lanzador";
	}

	@Override
	public String editarRazones() {
		return "Editar razones";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Selecciona un lanzador para quitar.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Selecciona un lanzador para editar.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Editar razones para " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Agregar nuevo idioma";
	}

	@Override
	public String aceptar() {
		return "Aceptar";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Selecciona el idioma";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Estos lanzadores son los que CrashDetector sugiere como buenos.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Resultado correcto";
	}

	public String modsNoRecomendados() {
		return "Mods desaconsejados";
	}

	public String agregarMod() {
		return "Agregar mod";
	}

	public String quitarMod() {
		return "Quitar mod";
	}

	public String modId() {
		return "Mod ID/Nombre JBoss Modules";
	}

	public String rutaMod() {
		return "Ruta / archivo del mod";
	}

	public String errorDebeIndicarMod() {
		return "Debe indicar al menos el modid o la ruta del mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Aquí puedes registrar mods desaconsejados para que CrashDetector los detecte si están instalados.";
	}

	@Override
	public String anularNormal() {
		// TODO Auto-generated method stub
		return "Anular Normal";
	}

	@Override
	public String anularNormalDescripcion() {
		// TODO Auto-generated method stub
		return "CrashDetector debería avisar aunque no crashee";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Registra mods que CrashDetector recomienda. Si faltan, CrashDetector puede avisar.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Si decides habilitar la advertencia antipiratería, se recomienda definir aquí "
				+ "los derechos de la persona que solicita soporte, como medida preventiva.\n\n"

				+ "Contrario a una creencia común, muchas comunidades y canales de soporte populares "
				+ "NO requieren activar advertencias antipiratería para brindar ayuda. Sin embargo, "
				+ "documentar estos derechos puede ser útil en caso de que una persona acceda al canal "
				+ "de soporte de todos modos.\n\n"

				+ "Puedes basarte en documentos oficiales como la Cartilla de Derechos Básicos del Detenido "
				+ "en México:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Así como en principios legales comparables utilizados en otros países, incluyendo "
				+ "Estados Unidos, la Federación de Rusia, la República Popular China, la República Islámica "
				+ "de Irán y la República Popular Democrática de Corea.\n\n"

				+ "Algunos ejemplos de derechos que pueden incluirse son:\n"
				+ "• El derecho a no proporcionar información innecesaria para el soporte, como el lanzador utilizado, "
				+ "el nombre de usuario o el UUID.\n" + "• El derecho a no autoincriminarse.\n"
				+ "• El derecho a rechazar responder preguntas que no sean necesarias para la resolución del problema.\n"
				+ "• El derecho a recibir orientación dentro del chat.\n"
				+ "• El derecho a utilizar el anonimizado de registros (logs) integrado en CrashDetector.\n\n"

				+ "Este texto acepta contenido HTML.";
	}

	@Override
	public String editar() {
		// TODO Auto-generated method stub
		return "Editar";
	}

	@Override
	public String advertenciaHashLento() {
		return "Advertencia: añadir muchos archivos grandes puede hacer que la verificación "
				+ "tarde varios minutos. CrashDetector deberá calcular el hash de cada archivo "
				+ "antes de continuar. Se recomienda proteger solo los archivos estrictamente necesarios.";
	}

	@Override
	public String agregarArchivo() {
		return "Agregar archivo";
	}

	@Override
	public String agregarCarpeta() {
		return "Agregar carpeta";
	}

	@Override
	public String quitar() {
		return "Quitar";
	}

	@Override
	public String rutaArchivo() {
		return "Ruta del archivo";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "La ruta seleccionada está fuera del directorio actual del juego. "
				+ "Solo se permiten archivos y carpetas dentro del directorio actual o sus subdirectorios.";
	}

	@Override
	public String mensajeDeSylentBell() {
		// TODO Auto-generated method stub
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Las opiniones y comentarios de Sylent Bell no necesariamente coinciden con los nuestros; "
				+ "solo pensamos que sería gracioso ponerla aquí. CrashDetector es secular." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "El mod GML (Groovy ModLoader) requiere estos cambios y es el origen más común de este problema.</b>";
	}

	@Override
	public String mensajeIndependenteFlywheel(Set<String> mods) {
		StringBuilder listaMods = new StringBuilder();
		if (!mods.isEmpty()) {
			for (String mod : mods) {
				listaMods.append("<li>").append(mod).append("</li>");
			}
		}

		String mensaje = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado el uso de <i>Independiente Flywheel</i>.</b>"
				+ "<p><b>Independiente Flywheel está obsoleto (deprecated)</b> y no debe utilizarse en versiones modernas.</p>"
				+ "<p>Las versiones actuales de <b>Create</b> <b>ya incluyen Flywheel</b>, por lo que instalarlo de forma independiente "
				+ "provoca conflictos de compatibilidad y errores de carga.</p>"
				+ "<p>Algunos mods que dependen explícitamente de Independiente Flywheel pueden "
				+ "<b>no funcionar</b> o <b>funcionar de forma inestable</b>. "
				+ "En ciertos casos avanzados, estos mods pueden llegar a funcionar si se "
				+ "<b>edita manualmente el archivo <code>mods.toml</code></b> para ajustar los rangos de versión, "
				+ "aunque esto <b>no es recomendado</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mods detectados que hacen referencia a Flywheel:</b></p>" + "<ul>"
								+ listaMods.toString() + "</ul>")
				+ "<p>La solución recomendada es <b>eliminar Independiente Flywheel</b> y utilizar únicamente "
				+ "la versión incluida con Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel Independiente";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado un error relacionado con el mod <i>Floral Enchantments</i>.</b>"
				+ "<p>El crash está causado por un fallo interno del mod al manejar datos del juego, "
				+ "lo que provoca una <b>NullPointerException</b> durante la ejecución.</p>"
				+ "<p>Este problema suele resolverse actualizando el mod o eliminándolo .</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Error de Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Tienes la versión NeoForge de MixinExtras y la versión normal. Si estás en MinecraftForge, puedes instalar <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>este enlace</a> para la solución.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado un error en las sombras del terreno con shaders (Iris).</b>"
				+ "<p>El problema ocurre durante el renderizado del terreno.</p>"
				+ "<p>Se recomienda <b>probar el juego sin shaders</b> o reducir la calidad gráfica, "
				+ "especialmente en configuraciones <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Sombras del terreno (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado un tick del servidor excesivamente largo.</b>"
				+ "<p>Esto indica que el juego se ha quedado bloqueado durante demasiado tiempo en un solo tick.</p>"
				+ "<p>Se recomienda <b>revisar el thread dump</b> generado en el log para identificar la causa.</p>"
				+ "<p>El <b>Análisis de Stack Trace</b> puede ayudarte a localizar el origen del bloqueo.</p>"
				+ "<p>Además, el botón <b>Ver en log</b> resaltará en rojo los posibles mods responsables, "
				+ "así como entradas rodeadas por <code>$modid$</code>, que suelen indicar el origen del problema. Para el escaneo en tiempo real, recomendamos usar el muestreador de CPU en VisualVM. Asegúrate de que tu servidor o computadora sea lo suficientemente potente como para manejar los mods que estás usando, ya que es posible que todos tus mods funcionen correctamente, pero que tengas demasiados.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick largo del servidor";
	}

	@Override
	public String tituloLFPDPPP() {
		return "LEY FEDERAL DE PROTECCIÓN DE DATOS PERSONALES EN POSESIÓN DE LOS PARTICULARES";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Aceptar permanentemente";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "Acta de Protección del Idioma Cultural de Pyongyang";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "La traducción coreana contiene términos de slang del sur que deben evitarse para cumplir con la ley. "
				+ "El uso de lenguaje extranjero, especialmente proveniente  del Sur, está estrictamente prohibido "
				+ "según el Acta de Protección del Idioma Cultural de Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Para más información, consulte el documento oficial de la ley: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Acta de Protección del Idioma Cultural de Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "Leer Ley Completa";
	}

	public String errorAbriendoEnlace() {
		return "Error al abrir el enlace";
	}

	@Override
	public String canarioTitulo() {
		return "Canario de Orden Judicial";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Monitor de Vigilancia";
	}

	@Override
	public String revisar() {
		return "Revisar";
	}

	@Override
	public String cerrar() {
		return "Cerrar";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Todos los servicios reportan estado seguro.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ALERTA: " + c + " servicio(s) reportan estado inseguro.";
	}

	@Override
	public String colorAlerta() {
		return "Color de alerta";
	}

	public String opcionesMunidiales() {
		return "Opciones munidiales";
	}

	public String consentimientoLFPDPPP() {
		return "Consentimiento LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Habilitar la transferencia del token de acceso en Handoff para ReLauncher (desaconsejado).";
	}

	public String consolaDesarrollo() {
		return "Consola de desarrollo";
	}

	public String mundial() {
		return "Mundial";
	}

	public String ningun() {
		return "Ningún";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Consola del desarrollador";
	}

	public String bajar() {
		return "Bajar";
	}

	public String logsSoporte() {
		return "Logs para soporte";
	}

	public String detenerProceso() {
		return "Detener proceso";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Copiar selección";
	}

	public String seleccionarTodo() {
		return "Seleccionar todo";
	}

	public String copiarTodo() {
		return "Copiar todo";
	}

	public String guardarTodoComoArchivo() {
		return "Guardar todo como archivo";
	}

	public String obtenerEnlaceSoporte() {
		return "Obtener enlace de soporte";
	}

	public String borrarTodo() {
		return "Borrar todo";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Color de fondo de la consola";
	}

	public String colorTextoConsola() {
		return "Color del texto de la consola";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Consentimiento confirmado.\nLa integración de compartir logs se implementará aquí.";
	}

	@Override
	public String usarSakuraOriginal() {
		// TODO Auto-generated method stub
		return "Usar la Imagen de Sakura Riddle Original";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "Un \"warrant canary\" es un mecanismo de transparencia.\n\n"
				+ "Mientras este mensaje exista y los servicios aparezcan como seguros, "
				+ "significa que el proyecto NO ha recibido órdenes judiciales secretas, "
				+ "requerimientos de censura, ni solicitudes legales de vigilancia.\n\n"
				+ "Si algún canario deja de estar presente o marca como inseguro, "
				+ "eso indica que algo ha cambiado legalmente.\n\n"
				+ "Este panel revisa todos los canarios registrados en el sistema y muestra " + "su estado actual.\n\n"
				+ "Pulsa \"Revisar\" para actualizar los estados.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		// TODO Auto-generated method stub
		return "¿Restablecer todas las opciones a sus valores por defecto?";
	}

	@Override
	public String gui() {
		// TODO Auto-generated method stub
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		// TODO Auto-generated method stub
		return "Sin Opciones";
	}

	@Override
	public String seleccionaColor() {
		// TODO Auto-generated method stub
		return "Seleccionar color";
	}

	@Override
	public String botonMostrarGUI() {
		return "Mostrar GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Guardar todo";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Restablecer todo";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms no cargado";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado un error al acceder a la API de LuckPerms.</b>"
				+ "<p>El mensaje indica que <b>LuckPerms no estaba cargado</b> en el momento en que otro plugin intentó usarlo.</p>"
				+ "<p><b>Posibles causas:</b></p>" + "<ul>"
				+ "<li>El plugin <b>LuckPerms no está instalado</b> o <b>falló al iniciarse</b>.</li>"
				+ "<li>Otro plugin está intentando acceder a LuckPerms demasiado pronto o de forma incorrecta.</li>"
				+ "</ul>" + "<p>Se recomienda <b>revisar la consola</b> usando el enlace para identificar "
				+ "el plugin que está llamando a LuckPerms y verificar su compatibilidad.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack de Iris no cargado";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "desconocido" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha detectado un error al cargar un shaderpack con Iris/Oculus.</b>"
				+ "<p><b>Shaderpack afectado:</b> " + nombre + "</p>"
				+ "<p>Minecraft no pudo abrir el archivo del shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Posibles soluciones:</b></p>" + "<ul>"
				+ "<li>Verifica que el shaderpack esté correctamente instalado en la carpeta <b>shaderpacks</b>.</li>"
				+ "<li>Vuelve a descargar el shaderpack, ya que el archivo podría estar corrupto.</li>"
				+ "<li>Si el problema persiste, elimina el archivo <b>config/iris.properties</b> para reiniciar la configuración de Iris.</li>"
				+ "</ul>" + "<p>Después de aplicar los cambios, inicia el juego nuevamente.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "No se pudo escribir el archivo de configuración";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "desconocido" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha producido un error al guardar un archivo de configuración.</b>"
				+ "<p><b>Archivo afectado:</b> " + archivo + "</p>"
				+ "<p>Minecraft no pudo escribir el archivo usando escritura atómica (REPLACE_ATOMIC).</p>"
				+ "<p><b>Esto suele ocurrir por:</b></p>" + "<ul>"
				+ "<li>Permisos incorrectos en la carpeta o el archivo.</li>"
				+ "<li>El archivo está marcado como solo lectura.</li>"
				+ "<li>Otro programa (antivirus, backup, editor) está bloqueando el archivo.</li>" + "</ul>"
				+ "<p><b>Recomendaciones:</b></p>" + "<ul>"
				+ "<li>Verifica que tienes permisos de escritura en la carpeta.</li>"
				+ "<li>Quita el atributo de solo lectura del archivo.</li>"
				+ "<li>Cierra programas que puedan estar usando ese archivo.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Acceso denegado al crear copia de seguridad";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "desconocido" : origen;
		String dst = backup == null || backup.isEmpty() ? "desconocido" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se ha producido un error de permisos al crear una copia de seguridad del archivo de configuración.</b>"
				+ "<p><b>Archivo original:</b> " + src + "</p>" + "<p><b>Archivo de respaldo:</b> " + dst + "</p>"
				+ "<p>El sistema operativo ha bloqueado el acceso durante el guardado del archivo.</p>"
				+ "<p><b>Esto suele ocurrir por:</b></p>" + "<ul>" + "<li>Permisos insuficientes en la carpeta.</li>"
				+ "<li>El archivo está marcado como solo lectura.</li>"
				+ "<li>Otro programa (antivirus, sincronización, editor) está usando el archivo.</li>" + "</ul>"
				+ "<p><b>Recomendaciones:</b></p>" + "<ul>"
				+ "<li>Verifica los permisos de la carpeta <b>config</b>.</li>"
				+ "<li>Cierra programas que puedan estar accediendo al archivo.</li>"
				+ "<li>Intenta iniciar el launcher o Minecraft como administrador.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		// TODO Auto-generated method stub
		return "Habilitar Consola";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Herramientas de depuración</b><br><br>"
				+ "Aquí puedes activar funciones avanzadas para depurar CrashDetector y tus juegos.<br><br>"
				+ "Se recomienda habilitar la consola de desarrollo para obtener información detallada, trazas y diagnósticos durante el análisis.<br><br>"
				+ "Si necesitas probar un servidor multijugador en modo online, puede ser necesario permitir la transferencia del token de acceso al proceso de CrashDetector desde los ajustes de privacidad. "
				+ "Esto generalmente <b>no se recomienda</b> en otros casos.<br><br>"
				+ "Instrucciones completas: <a href='https://example.com'>¡Enlace!</a>";// TODO
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilidad detectada entre Simple Clouds y los shaders.</b>"
				+ "<p>Simple Clouds no es compatible con mods de sombras (Iris/Oculus) cuando está instalado Distant Horizons.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Elimina <b>Simple Clouds</b> si deseas usar shaders.</li>"
				+ "<li>O bien, desinstala <b>Iris u Oculus</b> si prefieres mantener Simple Clouds.</li>" + "</ul>"
				+ "<p>Esta limitación proviene del propio mod Simple Clouds y no puede resolverse sin modificar su código.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Incompatibilidad: Simple Clouds vs Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Color del botón de la barra lateral";
	}

	@Override
	public String profilerTitulo() {
		return "Profiler";
	}

	@Override
	public String profilerDescripcion() {
		return "Herramienta de análisis de rendimiento basada en instrumentación y muestreo.";
	}

	@Override
	public String profilerIniciar() {
		return "Iniciar";
	}

	@Override
	public String profilerDetener() {
		return "Detener";
	}

	@Override
	public String profilerLimpiar() {
		return "Limpiar";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profiler iniciado.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profiler detenido.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Muestra recibida del hilo: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Muestreo periódico de stacks para detectar cuellos de botella y bloqueos.";
	}

	@Override
	public String entrarAlJuego() {
		// TODO Auto-generated method stub
		return "Entrar al Juego";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error en la ruta del sistema detectado.</b>"
				+ "<p>Minecraft no pudo iniciar debido a caracteres ilegales en el nombre de una carpeta.</p>"
				+ "<p>El sistema detectó un carácter no válido en la ruta (por ejemplo: ':' u otros símbolos especiales).</p>"
				+ "<p><b>Solución recomendada:</b></p>" + "<ul>"
				+ "<li>Renombra la carpeta de la instancia o del perfil.</li>"
				+ "<li>Usa únicamente caracteres ASCII básicos (A-Z, a-z, 0-9).</li>"
				+ "<li>No utilices tildes, símbolos especiales, espacios problemáticos ni emojis.</li>" + "</ul>"
				+ "<p>Ejemplo válido: <b>MiInstancia1</b></p>"
				+ "<p>Ejemplo inválido: <b>Instancia🔥</b> o <b>Instancia:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Ruta inválida: caracteres no permitidos";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Fallo detectado en shaders de Twilight Forest con GPU Intel.</b>"
				+ "<p>Este error está relacionado con los controladores gráficos de Intel al cargar los shaders del mod Twilight Forest.</p>"
				+ "<p>El fallo ocurre dentro del driver (igxelpicd64) y no es un problema directo del mod ni de Minecraft.</p>"
				+ "<p><b>Soluciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Actualizar los drivers Intel a la versión más reciente disponible.</li>"
				+ "<li>Probar específicamente las versiones 31.0.101.8331 o 31.0.101.8247 WHQL, que según comentarios no presentan este problema.</li>"
				+ "</ul>" + "<p>Seguimiento oficial del problema:</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Nota:</b> Algunas GPUs Intel más antiguas pueden no recibir actualizaciones que solucionen este problema.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash: Twilight Forest + Drivers Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge: proveedor de lenguaje no pudo cargarse";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Proveedor desconocido" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge no pudo cargar un proveedor de lenguaje.</b>"
				+ "<p>Se produjo un error al inicializar un IModLanguageProvider.</p>"
				+ "<p><b>Proveedor que falló:</b> " + providerTexto + "</p>"
				+ "<p>Este problema suele ocurrir cuando:</p>" + "<ul>"
				+ "<li>Falta una dependencia requerida (por ejemplo, Kotlin for Forge).</li>"
				+ "<li>La versión del mod no es compatible con tu versión de Forge.</li>"
				+ "<li>El archivo del mod está corrupto.</li>" + "</ul>" + "<p><b>Soluciones recomendadas:</b></p>"
				+ "<ul>" + "<li>Reinstalar el mod correspondiente.</li>"
				+ "<li>Verificar que todas sus dependencias estén instaladas.</li>"
				+ "<li>Asegurarse de usar versiones compatibles con tu Forge actual.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash: Lets Do Compat (RecipeManager intercept)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Crash detectado en Lets Do Compat (interceptApply).</b>"
				+ "<p>El error ocurre dentro de una transformación del método "
				+ "<b>RecipeManager.interceptApply</b> realizada por Lets Do Compat.</p>" + "<p>Esto suele indicar:</p>"
				+ "<ul>" + "<li>Incompatibilidad entre Lets Do Compat y otro mod que modifica recetas.</li>"
				+ "<li>Versión incorrecta para tu versión de Minecraft.</li>"
				+ "<li>Conflicto entre transformadores (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>Soluciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Probar a eliminar temporalmente Lets Do Compat para confirmar el conflicto.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI: fallo en Item Group (plugin incompatible)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin desconocido"
				: String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI detectó un fallo al construir un grupo de items.</b>"
				+ "<p>Uno o más plugins provocaron un error mientras JEI generaba la lista de ingredientes.</p>"
				+ "<p><b>Grupos/Plugins afectados:</b> " + listaPlugins + "</p>"
				+ "<p>Este problema es común cuando:</p>" + "<ul>"
				+ "<li>Un plugin de JEI está mal implementado o desactualizado.</li>"
				+ "<li>Existe incompatibilidad con la versión actual de JEI.</li>"
				+ "<li>Se usa Fabric API y algún mod registra incorrectamente su Item Group.</li>" + "</ul>"
				+ "<p><b>Soluciones recomendadas:</b></p>" + "<ul>" + "<li>Actualizar JEI y los mods listados.</li>"
				+ "<li>Eliminar temporalmente los plugins afectados para confirmar el conflicto.</li>"
				+ "<li>Reportar el error al desarrollador del mod correspondiente.</li>" + "</ul>"
				+ "<p>Los items de estos grupos no aparecerán en la lista de ingredientes hasta que el problema sea corregido.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Versión inválida de mod (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Desconocida" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "No se pudo localizar el mod" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Versión de mod inválida detectada.</b>" + "<p>La versión <b>" + v
				+ "</b> no cumple con el formato SemVer válido.</p>"
				+ "<p>El error indica un pre-release vacío (termina con '+').</p>" + "<p><b>Mod responsable:</b><br>"
				+ u + "</p>" + "<p><b>Solución recomendada:</b></p>" + "<ul>"
				+ "<li>Editar el archivo del mod y corregir la versión.</li>"
				+ "<li>Eliminar el '+' final si no hay metadata posterior.</li>"
				+ "<li>Actualizar el mod a una versión corregida.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS: Acceso ilegal entre módulos";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Acceso ilegal entre módulos (JPMS) detectado.</b>"
				+ "<p>El sistema de módulos de Java (JPMS) bloqueó un acceso entre clases.</p>"
				+ "<p><b>Clase que intenta acceder:</b><br>" + claseOrigen + " (módulo: " + moduloOrigen + ")</p>"
				+ "<p><b>Clase bloqueada:</b><br>" + claseDestino + " (módulo: " + moduloDestino + ")</p>"
				+ "<p>Este tipo de error ocurre cuando un mod no declara correctamente "
				+ "exports u opens en su module-info.java.</p>" + "<p><b>Posibles causas:</b></p>" + "<ul>"
				+ "<li>El módulo no exporta el paquete necesario.</li>"
				+ "<li>Falta la directiva <b>opens</b> para reflexión.</li>"
				+ "<li>El mod no está correctamente configurado para JPMS.</li>" + "</ul>"
				+ "<p>Este problema debe ser corregido por el desarrollador del mod.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin: clase mal ubicada en paquete mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Clase ubicada incorrectamente en paquete Mixin.</b>"
				+ "<p>Una clase normal fue colocada dentro de un paquete declarado como mixin.</p>"
				+ "<p><b>Clase conflictiva:</b><br>" + clase + "</p>" + "<p><b>Paquete mixin declarado:</b><br>"
				+ paquete + "</p>" + "<p><b>Archivo mixins responsable:</b><br>" + archivoMixin + "</p>"
				+ "<p>Las clases normales no deben estar dentro del paquete definido en mixins.json.</p>"
				+ "<p>Solo las clases anotadas como mixin deben existir en ese paquete.</p>"
				+ "<p><b>Solución para dev:</b> Mover las clases normales fuera del paquete mixin "
				+ "o corregir la configuración del archivo mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Problema detectado con drivers de GPU Matrox.</b>"
				+ "<p>El log indica que el fallo ocurrió dentro de una librería del controlador Matrox.</p>"
				+ "<p>Las GPUs Matrox (especialmente modelos G200/G400 usados en servidores) "
				+ "no están diseñadas para renderizado 3D moderno y pueden no soportar "
				+ "las versiones de OpenGL requeridas por Minecraft.</p>" + "<p><b>Soluciones recomendadas:</b></p>"
				+ "<ul>" + "<li>Actualizar el controlador Matrox a la versión más reciente disponible.</li>"
				+ "<li>Instalar drivers oficiales en lugar de drivers genéricos del sistema.</li>"
				+ "<li>Si el hardware es antiguo, usar una GPU compatible con OpenGL 3.2 o superior.</li>" + "</ul>"
				+ "<p>En servidores, estas GPUs suelen ser solo para salida básica de video "
				+ "y no para aplicaciones 3D como Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod: GPU no compatible";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod no pudo detectar una GPU compatible.</b>"
				+ "<p>El mod <b>VulkanMod</b> intentó iniciar usando Vulkan pero no encontró una GPU que soporte Vulkan de forma adecuada.</p>"
				+ "<p>Esto suele ocurrir cuando:</p>" + "<ul>" + "<li>La GPU no soporta Vulkan.</li>"
				+ "<li>Los drivers de la GPU están desactualizados o faltan.</li>"
				+ "<li>Se está usando un adaptador gráfico incorrecto (por ejemplo, GPU integrada en vez de dedicada).</li>"
				+ "</ul>" + "<p><b>Soluciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Actualizar los drivers de la GPU a la versión más reciente.</li>"
				+ "<li>Verificar que tu GPU soporte Vulkan.</li>"
				+ "<li>Si tienes dos GPUs, forzar el uso de la dedicada para Minecraft.</li>"
				+ "<li>Si tu GPU no soporta Vulkan, desinstalar VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType inválido para outline";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod intentó aplicar un outline a un RenderType incompatible.</b>" + "<p>El error fue:</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>Se detectó el mod enchant-outline / better-enchants en el reporte.</b></p>"
					+ "<p>Este mod es conocido por causar este problema en versiones recientes de Minecraft.</p>"
					+ "<p><b>Solución recomendada:</b> eliminar o actualizar enchant-outline.</p>";
		} else {
			base += "<p>Este problema suele estar relacionado con mods que modifican el renderizado "
					+ "(Entity Model Features, Entity Texture Features, Visuality o conflictos con Sodium).</p>"
					+ "<p><b>Solución recomendada:</b> actualizar o desactivar mods de render uno por uno.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory nulo";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG intentó guardar un DimensionalInventory nulo.</b>" + "<p>El juego lanzó:</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \"d\" is null</code>"
				+ "<p>Este es un bug conocido de DivineRPG relacionado con el sistema de inventario Vethean.</p>"
				+ "<p><b>Solución recomendada:</b></p>" + "<ul>"
				+ "<li>Ir al archivo de configuración de DivineRPG.</li>"
				+ "<li>Establecer <code>saferVetheanInventory=true</code></li>"
				+ "<li>Guardar y reiniciar el juego.</li>" + "</ul>"
				+ "<p>También se recomienda actualizar DivineRPG si hay una versión más reciente disponible.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Conflicto en Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un conflicto en el sistema de renderizado.</b>" + "<p>El juego lanzó:</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Este error suele estar relacionado con conflictos entre mods de render "
				+ "como Iris, OptiFine, VulkanMod u otros que modifican el pipeline gráfico.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Fallo interno de Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un fallo interno del Feather Client.</b>" + "<p>El juego lanzó:</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Este error es causado por Feather Client.</p>" + "<p>Feather no es recomendado porque:</p>"
				+ "<ul>" + "<li>Usa versiones propietarias y modificadas de mods populares.</li>"
				+ "<li>Rompe compatibilidad con Fabric estándar.</li>"
				+ "<li>Funciona como un modpack prearmado con modificaciones internas.</li>"
				+ "<li>Suele causar conflictos con Sodium y otros mods de rendimiento.</li>" + "</ul>"
				+ "<p>Se recomienda usar una instalación estándar de Fabric en lugar de Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Conflicto Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Conflicto detectado entre Iris y Flywheel en Create 6.</b>" + "<p>El juego lanzó:</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Se detectaron referencias internas <code>$irisflw$</code>, "
				+ "lo que indica un conflicto entre Iris y Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 para Create 6 solo es compatible oficialmente con NeoForge.</p>"
				+ "<p>Si estás usando Forge o Fabric, esta combinación puede causar este error.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Modelo GeckoLib no encontrado";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod no pudo encontrar un modelo de GeckoLib.</b>" + "<p>Modelo afectado:</p>" + "<code>" + modelo
				+ "</code>" + "<p>Este error ocurre cuando un archivo <code>.geo.json</code> no existe "
				+ "o está mal configurado dentro del mod.</p>" + "<p>Posibles causas:</p>" + "<ul>"
				+ "<li>El modelo fue eliminado pero aún es referenciado.</li>"
				+ "<li>Error en la ruta del archivo.</li>" + "<li>Archivo faltante dentro del JAR.</li>"
				+ "<li>Versión incompatible del mod.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – Animación inexistente";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon intentó reproducir una animación inexistente.</b>" + "<p>Animación:</p>" + "<code>"
				+ animacion + "</code>" + "<p>Grupo:</p>" + "<code>" + grupo + "</code>"
				+ "<p>Este error suele ocurrir cuando:</p>" + "<ul>"
				+ "<li>Se mezclan versiones incompatibles de Cobblemon.</li>"
				+ "<li>Un addon no coincide con la versión instalada.</li>"
				+ "<li>Faltan recursos o animaciones internas.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Fallo interno de Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un fallo interno del Lunar Client.</b>"
				+ "<p>El error proviene del propio cliente Lunar y no del juego base ni de los mods.</p>"
				+ "<p>Lunar Client usa modificaciones internas y personalizadas que pueden "
				+ "causar incompatibilidades con mods o configuraciones específicas.</p>"
				+ "<p>Se recomienda probar con una instalación estándar de Minecraft "
				+ "para descartar problemas propios del cliente.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Acceso ilegal a método o campo";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod intentó acceder a un método o campo protegido/privado.</b>" + "<p>Clase responsable:</p>"
				+ "<code>" + clase + "</code>" + "<p>Miembro accedido:</p>" + "<code>" + miembro + "</code>"
				+ "<p>Este error suele ocurrir cuando:</p>" + "<ul>"
				+ "<li>El mod fue compilado para otra versión de Minecraft.</li>"
				+ "<li>Hay mezcla de mappings incompatibles.</li>" + "<li>El mod está desactualizado.</li>"
				+ "<li>Se usa el cargador incorrecto (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Error al cargar datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un datapack o resourcepack falló al cargarse.</b>" + "<p>Archivo problemático:</p>" + "<code>"
				+ archivo + "</code>" + "<p>Pack:</p>" + "<code>" + pack + "</code>"
				+ "<p>El juego no pudo parsear este archivo y eso provocó errores de carga del registry.</p>"
				+ "<p>Este problema suele deberse a:</p>" + "<ul>" + "<li>JSON mal formado.</li>"
				+ "<li>Versión incompatible del pack.</li>"
				+ "<li>Pack desactualizado para la versión actual del juego.</li>"
				+ "<li>Conflicto entre datapacks.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Error de compilación de shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Falló la compilación de un shader.</b>"
				+ "<p>El juego no pudo compilar uno de los shaders activos.</p>"
				+ "<p>Este problema suele estar relacionado con Sodium, Iris o shaderpacks incompatibles.</p>"
				+ "<p>Recomendaciones:</p>" + "<ul>" + "<li>Probar un shader diferente.</li>"
				+ "<li>Desactivar temporalmente los shaders.</li>" + "<li>Actualizar los drivers de la GPU.</li>"
				+ "<li>Si el problema continúa, probar iniciar el juego sin Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Error al crear o cargar un modelo";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<p>Se produjo un error al intentar crear o cargar un modelo de Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Modelo afectado: <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Este tipo de error suele ocurrir cuando:</p>");
		sb.append("<ul>");
		sb.append("<li>Un mod tiene un modelo mal configurado.</li>");
		sb.append("<li>Un modelo JSON está dañado o incompleto.</li>");
		sb.append("<li>Existe un conflicto entre mods que modifican modelos o renderizado.</li>");
		sb.append("<li>Un resource pack o datapack contiene modelos incompatibles.</li>");
		sb.append("</ul>");
		sb.append("<p>Intenta identificar qué mod o paquete de recursos proporciona el modelo indicado.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Posible causa detectada:</b></p>");
		sb.append("<p>Se detectó actividad del mod <b>Cooler Animations</b> en el registro.</p>");
		sb.append(
				"<p>Este mod modifica el sistema de animaciones y modelos, y en algunos casos puede causar errores de carga de modelos.</p>");
		sb.append(
				"<p>Si el problema continúa, intenta iniciar el juego sin Cooler Animations para comprobar si el error desaparece.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Problema con Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un error relacionado con Starlight.</b>"
				+ "<p>El error ocurrió dentro de <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Esto indica un fallo en el motor de iluminación del mod <b>Starlight</b>.</p>"
				+ "<p>Starlight es un mod que modifica completamente el sistema de iluminación de Minecraft y es conocido por causar diversos problemas en algunos entornos de mods.</p>"
				+ "<p>Este es solo uno de varios errores conocidos asociados con Starlight.</p>"
				+ "<p>Si el problema continúa, intenta iniciar el juego sin Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Problema con AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un crash nativo relacionado con Effekseer.</b>"
				+ "<p>El error ocurrió dentro de la librería nativa <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Esta librería es utilizada por el mod <b>AAAParticles</b> desarrollado por ChloePrime.</p>"
				+ "<p>Los crashes en librerías nativas suelen indicar problemas dentro del propio mod o en sus dependencias nativas.</p>"
				+ "<p>Si el problema continúa, intenta iniciar el juego sin AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un crash nativo del entorno de ejecución de Java (JVM).</b>"
				+ "<p>Este tipo de error ocurre dentro de la propia Máquina Virtual de Java (por ejemplo, en <code>jvm.dll</code>, <code>libjvm.so</code>, etc.), "
				+ "y no necesariamente es causado por un mod.</p>"
				+ "<p>Aunque en raras ocasiones puede originarse por mods que usan bibliotecas nativas incompatibles, "
				+ "<b>es mucho más probable que se deba a una versión defectuosa, corrupta o desactualizada de la JVM</b>.</p>"
				+ "<p>Esto es especialmente común si estás usando una compilación antigua o no oficial de tu versión de Java (por ejemplo, builds comunitarias sin soporte).</p>"
				+ "<p><b>Recomendamos usar una JVM confiable y mantenida:</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (estable, bien probada, ideal para Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (soporte multiplataforma, incluyendo macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (certificada, con soporte LTS gratuito)</li>"
				+ "<li><b>Oracle JDK</b> (oficial, con actualizaciones regulares)</li>" + "</ul>"
				+ "<p>Evita builds desconocidas, personalizadas o muy antiguas, ya que pueden contener errores críticos en el motor de la JVM.</p>"
				+ "<p><b>¿Usas Prism Launcher o TLauncher?</b> Es muy fácil configurar una JVM personalizada: "
				+ "en Prism Launcher, ve a <i>Instalaciones</i> → <i>Editar instancia</i> → <i>Configuración de Java</i>; "
				+ "en TLauncher, ve a <i>Settings</i> → <i>Java Settings</i> y selecciona la ruta de tu JDK/JRE descargado. "
				+ "¡No necesitas cambiar la JVM del sistema!</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Conflicto entre Paranoia y C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un conflicto entre los mods Paranoia y C2ME.</b>"
				+ "<p>El error indica que <code>ThreadLocalRandom</code> fue accedido desde un hilo incorrecto.</p>"
				+ "<p>Esto suele ocurrir cuando el mod <b>Paranoia</b> ejecuta código que no es seguro para múltiples hilos mientras <b>C2ME</b> está realizando optimizaciones de multithreading.</p>"
				+ "<p>Este tipo de conflicto es común con mods creados con MCreator.</p>"
				+ "<p>Si el problema continúa, intenta iniciar el juego sin Paranoia o desactivar C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Directorio de assets de Minecraft faltante";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft no pudo localizar el directorio de assets.</b>"
				+ "<p>El launcher intentó iniciar el juego con una ruta de assets inválida.</p>"
				+ "<p>Esto significa que los archivos de recursos del juego (assets) no están presentes o no fueron instalados correctamente.</p>"
				+ "<p>Este problema suele ocurrir con instalaciones incorrectas de Minecraft o launchers mal configurados.</p>"
				+ "<p>También puede ocurrir al usar launchers no oficiales que manejan incorrectamente los assets como FreshCraft.</p>"
				+ "<p>Si el problema continúa, intenta reinstalar el modpack o iniciar el juego desde un launcher oficial o confiable.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Instalar " + dependencia + " versión " + version + " o posterior";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Reemplazar " + dependencia + " por una versión entre " + min + " y " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "El mod " + mod + " requiere " + dependencia + " versión mínima " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "El mod " + mod + " requiere " + dependencia + " versión " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "El mod " + mod + " requiere " + dependencia + " entre " + min + " y " + max + " (actual: " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Versión incompatible de Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un crash causado por una versión antigua de Cupboard.</b>"
				+ "<p>El error ocurre dentro de <code>ClientConfigCompat.setupNeoforge</code> debido a que "
				+ "<code>ModList.get()</code> devuelve <code>null</code>.</p>"
				+ "<p>Este es un problema conocido en versiones antiguas del mod <b>Cupboard</b>.</p>"
				+ "<p>Las versiones antiguas como <b>3.2</b> contienen este bug.</p>"
				+ "<p><b>Solución:</b> actualiza Cupboard a la versión <b>3.5</b> o posterior.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		// TODO Auto-generated method stub
		return "Es porque estás en macOS y el juego está intentando usar OpenGL, lo cual no es compatible con las últimas versiones de macOS. "
				+ "Necesitas usar una versión de Minecraft que soporte Metal o usar Linux si tienes un Mac Intel o M1 o M2 pero no M3+ o Neo.";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Animación GeckoLib no encontrada";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod no pudo cargar un archivo de animación de GeckoLib.</b>" + "<p>Archivo afectado:</p>"
				+ "<code>" + archivo + "</code>"
				+ "<p>Este error ocurre cuando un archivo <code>.json</code> de animación no existe, "
				+ "tiene errores de sintaxis o la ruta es incorrecta.</p>" + "<p>Posibles causas:</p>" + "<ul>"
				+ "<li>El archivo fue eliminado pero aún es referenciado en el código.</li>"
				+ "<li>Error de sintaxis dentro del archivo JSON.</li>"
				+ "<li>Ruta incorrecta definida en el registro del mod.</li>"
				+ "<li>Conflictos de dependencias o versión incompatible.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Animación GeckoLib no encontrada";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod no pudo encontrar un archivo de animación de GeckoLib.</b>" + "<p>Archivo afectado:</p>"
				+ "<code>" + archivo + "</code>"
				+ "<p>Este error ocurre cuando GeckoLib intenta cargar una animación que no existe en la ruta especificada. "
				+ "A diferencia de un error de carga (sintaxis), este error indica que el archivo falta físicamente o la ruta es errónea.</p>"
				+ "<p>Posibles causas:</p>" + "<ul>"
				+ "<li>El archivo <code>.json</code> fue eliminado o no se incluyó en el JAR final del mod.</li>"
				+ "<li>Error tipográfico en la ruta definida en el código (por ejemplo: 'animations' vs 'animaciones').</li>"
				+ "<li>Discrepancia entre mayúsculas y minúsculas (el sistema operativo del servidor es Linux (sensible) y el desarrollo fue en Windows (insensible)).</li>"
				+ "<li>El mod no está completamente actualizado o sus dependencias están rotas.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Conflicto de Registro Duplicado";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal: Solo el texto descriptivo lleva el color de error
		String mensajeBase = "<span style='color:#" + color
				+ "'>Conflicto crítico: Se ha intentado registrar un objeto dos veces. " + "Los mods </span>" + mod1
				+ "<span style='color:#" + color + "'> y </span>" + mod2 + "<span style='color:#" + color
				+ "'> están intentando registrar el mismo objeto. " + "Objeto en conflicto: </span>" + objeto
				+ "<br><br>";

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Esto generalmente ocurre cuando dos mods diferentes añaden un objeto con el mismo nombre "
				+ "en el mismo namespace, o cuando hay un error en el código de uno de los mods.<br><br>"
				+ "<b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Verifica si uno de los mods es una actualización o bifurcación del otro.</li>"
				+ "<li>Intenta eliminar uno de los dos mods conflictivos.</li>"
				+ "<li>Revisa los archivos de configuración de ambos mods para ver si puedes cambiar el ID del objeto.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Falta Fabric Rendering API";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color
				+ "'>Un mod (generalmente Porting Lib o sus dependientes) ha fallado porque la </span>"
				+ "Fabric Rendering API<span style='color:#" + color + "'> no está disponible.</span><br><br>";

		// Instrucciones de reparación (Actualizadas para versiones modernas donde
		// Indium es obsoleto)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solución recomendada:</b><br>"
				+ "El mensaje sugiere instalar Indium, pero este mod está obsoleto en versiones modernas del juego.<br>"
				+ "<ul>"
				+ "<li><b>Actualiza Sodium</b> a la versión <b>0.6.0</b> o superior (esta versión incluye el soporte necesario).</li>"
				+ "<li>Asegúrate de tener instalado <b>Fabric API</b> si aún no lo tienes.</li>"
				+ "<li>Si usas una versión antigua del juego (1.20.6 o inferior), entonces instala Indium.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Restricciones de dependencias no cumplidas";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Se encontraron </span>" + cantidad
				+ "<span style='color:#" + color + "'> restricciones de dependencias que no se cumplen.</span><br><br>";

		// Construcción de la lista de conflictos
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Se detectaron conflictos en los siguientes archivos:</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Dependencia
				String jar = par[1]; // Archivo JAR
				// Variable en color por defecto, texto fijo en color error
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Archivo: </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Requiere: </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Esto ocurre cuando dos o más mods requieren versiones diferentes e incompatibles de una misma librería interna.<br><br>"
				+ "<b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Intenta actualizar o eliminar los mods listados arriba para resolver la incompatibilidad.</li>"
				+ "<li>Si no encuentras una versión compatible, puedes intentar editar manualmente el archivo <code>mods.toml</code> dentro del archivo JAR del mod (usando un compresor como WinRAR o 7-Zip) para cambiar o eliminar la restricción de versión, aunque esto puede causar inestabilidad.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Se encontraron </span>" + cantidad
				+ "<span style='color:#" + color + "'> restricciones de dependencias que no se cumplen.</span><br><br>";

		// Construcción de la lista agrupada por Mod
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Mods involucrados y dependencias solicitadas:</span><br><ul>");

			for (Map.Entry<String, List<String>> entry : conflictosPorMod.entrySet()) {
				String archivo = entry.getKey();
				List<String> dependencias = entry.getValue();

				// Nombre del Mod (color por defecto)
				listaDetalle.append("<li><b>").append(archivo).append("</b>");

				// Lista de dependencias para este mod
				listaDetalle.append("<ul>");
				for (String dep : dependencias) {
					// Dependencia (color por defecto)
					listaDetalle.append("<li>").append(dep).append("</li>");
				}
				listaDetalle.append("</ul></li>");
			}
			listaDetalle.append("</ul><br>");
		} else {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>No se pudieron determinar los archivos específicos desde el log.</span><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Este error ocurre cuando los mods incluyen versiones internas de librerías (JarInJar) que son incompatibles entre sí.<br><br>"
				+ "<b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Revisa la lista superior para identificar qué mods solicitan versiones diferentes de la misma librería.</li>"
				+ "<li>Intenta actualizar ambos mods a sus últimas versiones.</li>"
				+ "<li>Como último recurso, puedes abrir el archivo <code>.jar</code> del mod con un compresor (como WinRAR), editar <code>META-INF/mods.toml</code> y modificar manualmente el rango de versión de la dependencia, aunque esto es arriesgado y puede romper el mod.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina impide la depuración";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Advertencia principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Advertencia:</b> El mod <b>Neruina</b> está fallando al intentar manejar un error, lo que oculta la verdadera causa del crash.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina a menudo no es necesario y dificulta saber qué está fallando realmente. Se recomienda eliminarlo para depurar.</span><br><br>";

		// Instrucciones de recuperación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Instrucciones de recuperación:</b><br>"
				+ "1. **MCForge**: Ve a '[nombre_del_mundo]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge**: Ve a 'config/neoforge-server.toml'.<br>"
				+ "   *(Nota: En partidas locales/Singleplayer, los mundos están en la carpeta 'saves')*.<br>"
				+ "3. Cambia **removeErroringBlockEntities** y **removeErroringEntities** a **true**.<br><br>"
				+ "<b>Otras opciones:</b><br>"
				+ "- **MCEdit**: Úsalo para eliminar manualmente la entidad en las coordenadas indicadas.<br>"
				+ "- Si este error persiste, es posible que Neruina no esté funcionando correctamente y simplemente esté generando errores nuevos."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Error de Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> ha detectado un conflicto: Un <b>AttributeMap</b> fue modificado sin tener un dueño asignado.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Esto generalmente ocurre cuando un mod intenta modificar atributos de una entidad (como vida, daño, velocidad) "
				+ "en un momento inadecuado o de forma incorrecta.</span><br><br>";

		// Nota específica sobre Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Se ha detectado el mod Chest Cavity en el log.</b> "
					+ "Este mod es una causa común de este error específico debido a cómo maneja los atributos de las entidades.</span><br><br>";
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Si Chest Cavity está instalado, intenta actualizarlo o eliminarlo temporalmente para verificar si es la causa.</li>"
				+ "<li>Revisa si hay otros mods que modifiquen atributos de mobs y prueba desactivándolos.</li>"
				+ "<li>Busca actualizaciones de <b>Apothic Attributes</b> ya que podría ser un error corregido en versiones recientes.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Error de DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Se ha producido un error de incompatibilidad con <b>DecoratedPotBlockEntity</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Este es un problema conocido en la versión 1.19.2 del mod <b>L_Enders_Cataclysm</b>, "
				+ "donde falta una implementación requerida por el juego.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'>" + "<b>Solución recomendada:</b><br>"
				+ "Instala el mod <b>PotFix (Cataclysm Patch)</b> para corregir este error.<br>"
				+ "Puedes descargarlo aquí: <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Error de Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Se ha detectado un conflicto causado por <b>Preloading Tricks</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "El error <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "indica que el mod está manipulando clases del sistema de módulos de Java de forma incorrecta.</span><br><br>";

		// Explicación y Solución
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> es un mod diseñado principalmente para <b>desarrolladores</b>. "
				+ "Realiza operaciones complejas de modificación de clases (mixins) en una etapa muy temprana de carga del juego, "
				+ "lo que puede romper fácilmente la estabilidad si hay otras interacciones.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Elimina el mod <b>Preloading Tricks</b>. Generalmente no es necesario para jugar en servidores públicos o packs estables.</li>"
				+ "<li>Si eres desarrollador y lo necesitas para pruebas, revisa tu configuración de entorno.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Incompatibilidad Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Se ha detectado un conflicto entre <b>Simple Radio</b> y <b>Lexiconfig</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "El error ocurre durante el proceso de 'shelveLexicons', lo que indica una incompatibilidad binaria entre ambas librerías.</span><br><br>";

		// Solución específica
		String solucion = "<span style='color:#" + color + "'>" + "<b>Causa conocida:</b><br>"
				+ "Simple Radio suele estar diseñado para versiones antiguas de Lexiconfig, mientras que tienes instalada una versión más reciente.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Intenta usar una versión más antigua de <b>Lexiconfig</b>.</li>"
				+ "<li>Se recomienda probar la versión <b>1.3.11</b> o anteriores, las cuales suelen ser compatibles con Simple Radio.</li>"
				+ "<li>Si el problema persiste, verifica si hay una actualización de Simple Radio disponible.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Error de Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Se ha detectado un error relacionado con <b>Mob AI Tweaks</b>.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "El error proviene de un Mixin (<code>$mob-ai-tweaks$onSpawned</code>) que interviene "
				+ "cuando una entidad aparece (spawnea). Esto suele indicar un conflicto con otro mod "
				+ "que también modifica el comportamiento de aparición de mobs.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'><b>Solución recomendada:</b><br>" + "<ul>"
				+ "<li>Intenta eliminar <b>Mob AI Tweaks</b> para verificar si la inestabilidad desaparece.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Verificación de GPU (OpenGL / Selección de GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Desactivar verificación de GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>El verificador de GPU podría haber causado el cierre del juego.</b>";
	}

	public String gpu_crash_causas() {
		return "La verificación comenzó pero no terminó. Esto suele indicar un fallo en OpenGL o en los drivers gráficos.<br><br>"
				+ "Posibles causas:<br>" + "- Drivers desactualizados o inestables<br>" + "- Problemas con OpenGL<br>"
				+ "- GPUs antiguas o configuraciones híbridas";
	}

	public String gpu_crash_recomendaciones() {
		return "Recomendaciones:<br>" + "- Actualizar drivers de la GPU<br>" + "- Forzar uso de GPU dedicada<br>"
				+ "- Evitar entornos remotos o virtualizados";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>El juego no está usando la mejor GPU disponible.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Esto puede reducir el rendimiento (FPS bajos), pero normalmente no causa crashes por sí solo.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Recomendaciones:<br>" + "- Forzar GPU dedicada en el panel de control<br>"
				+ "- Configurar Java/Minecraft en modo alto rendimiento";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>Nota:</b> Este sistema de detección no es 100% perfecto.";
	}

	public String gpu_consumo_energia() {
		return "Las GPUs más potentes consumen más energía y pueden reducir la duración de la batería en laptops.";
	}

	public String gpu_parche_info() {
		return "Puedes desactivar esta verificación usando el botón de solución rápida.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Advertencia de Estabilidad CPU Intel 13/14 Gen";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Posible inestabilidad en procesador Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Se ha detectado un procesador " + cpu
				+ " con microcódigo " + microcode + "." + "</b> "
				+ "Los procesadores Intel de 13ª y 14ª generación han presentado problemas de inestabilidad debido a un exceso de voltaje solicitado, "
				+ "lo que puede acortar la vida útil del procesador.<br><br>"
				+ "Se recomienda actualizar el microcódigo o la BIOS de tu placa madre a una versión que incluya el microcódigo <b>"
				+ targetMicrocode + "</b> o superior. "
				+ "<b>Advertencia:</b> Actualizar la BIOS conlleva riesgos si no se hace correctamente.<br><br>"
				+ "<i>Nota: Esto casi con seguridad NO es la causa de tu crash actual, es solo un aviso informativo de salud del hardware.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "No volver a advertirme sobre esto";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Leer artículo en " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Explorador de Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixins encontrados";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Todos";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod con mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Filtra por mod que contenga mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "Recargar";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Descompilar selección";
	}

	@Override
	public String mixinsTargets() {
		return "Targets";
	}

	@Override
	public String mixinsTarget() {
		return "Target";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Targets del método";
	}

	@Override
	public String mixinsMetodos() {
		return "Métodos";
	}

	@Override
	public String mixinsCampos() {
		return "Campos";
	}

	@Override
	public String mixinsCantidad() {
		return "Cantidad de mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Detalle del mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Detalle del target";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Detalle del método mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Detalle del campo mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Detalle del conflicto";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Buscar posibles conflictos";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Resultados de conflictos";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Posibles conflictos";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Error al descompilar";
	}

	@Override
	public String mixinsColorPanel() {
		return "Color del panel de mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "Color de texto de mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Color de texto secundario de mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Esta herramienta muestra mods con mixins de SpongePowered y permite inspeccionar sus clases, targets, métodos y campos.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Usa el selector superior para filtrar por un mod específico o mostrar todos los mods con mixins.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Expande el árbol para ver cada mixin, sus clases objetivo, los métodos anotados y los campos shadow.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Haz clic derecho sobre un mod, mixin, target, método o campo para buscar posibles conflictos con otros mixins.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Puedes descompilar la selección actual o un resultado de conflicto para inspeccionar el código relacionado.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Color de fondo del selector de mods";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Color de fondo del panel de detalles";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Color de selección del texto";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Color del texto seleccionado";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Color del texto de ayuda";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Color de fondo del árbol";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Color del texto seleccionado del árbol";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Color de fondo seleccionado del árbol";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Color del borde de selección del árbol";
	}

	@Override
	public String depmapTitulo() {
		return "Mapa de Dependencias";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Mapa";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Dependientes";
	}

	@Override
	public String depmapRecargar() {
		return "Recargar";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Descompilar selección";
	}

	@Override
	public String depmapVerReferencias() {
		return "Ver referencias";
	}

	@Override
	public String depmapDependencias() {
		return "Dependencias";
	}

	@Override
	public String depmapDependientes() {
		return "Dependientes";
	}

	@Override
	public String depmapDependiente() {
		return "Dependiente";
	}

	@Override
	public String depmapSinDependencias() {
		return "Sin dependientes";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Seleccionar mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod base";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod dependiente";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Paquete";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Comprobar no alineadas";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Resultados de dependencias no alineadas";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Clase inexistente";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Clase referenciada";
	}

	@Override
	public String depmapOrigen() {
		return "Origen";
	}

	@Override
	public String depmapDestino() {
		return "Destino";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Detalle de la dependencia";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Detalle de la referencia";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Método origen";
	}

	@Override
	public String depmapModBase() {
		return "Mod base";
	}

	@Override
	public String depmapTodos() {
		return "Todos";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Selecciona un mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Selecciona el mod base, el dependiente y el paquete";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Selecciona una referencia o hallazgo para descompilar";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Error al descompilar";
	}

	@Override
	public String depmapAyuda1() {
		return "Esta herramienta construye un mapa de dependencias entre mods usando referencias de clases entre ellos.";
	}

	@Override
	public String depmapAyuda2() {
		return "La pestaña de mapa muestra un grafo de burbujas con cada mod enlazado a las dependencias que usa.";
	}

	@Override
	public String depmapAyuda3() {
		return "La pestaña de dependientes ordena los mods desde el que más dependientes tiene hasta el que no tiene ninguno.";
	}

	@Override
	public String depmapAyuda4() {
		return "Puedes inspeccionar referencias concretas entre un mod y sus dependencias, así como descompilar clases relacionadas.";
	}

	@Override
	public String depmapAyuda5() {
		return "La comprobación de dependencias no alineadas busca referencias a clases inexistentes dentro de un paquete o subpaquete del mod base.";
	}

	@Override
	public String depmapColorPanel() {
		return "Color de paneles";
	}

	@Override
	public String depmapColorTexto() {
		return "Color de texto principal";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Color de texto secundario";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Color del texto de ayuda";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Color de fondo del grafo";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Color de fondo de listas";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Color de fondo del árbol";
	}

	@Override
	public String depmapColorNodo() {
		return "Color de nodos del grafo";
	}

	@Override
	public String depmapColorEnlace() {
		return "Color de enlaces del grafo";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Color de selección";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Color del texto seleccionado";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Problema con un addon de AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un error de AzureLib al cargar animaciones.</b>"
				+ "<p>El juego encontró un JSON de animaciones con un formato incorrecto.</p>"
				+ "<p>Este problema suele ser causado por uno de los mods o addons que usan <b>AzureLib</b>.</p>"
				+ "<p><b>Recomendación:</b></p>" + "<ul>"
				+ "<li>Usa el <b>DepMap</b> de la barra lateral para localizar todos los addons que dependan de AzureLib.</li>"
				+ "<li>Prueba iniciar el juego sin algunos de esos addons hasta encontrar el que falla.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Problema con Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un problema causado por Decocraft Nature.</b>"
				+ "<p>El error ocurre al inicializar el mixin config "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Este problema puede corregirse editando el archivo JAR del mod.</p>" + "<p><b>Pasos:</b></p>"
				+ "<ul>"
				+ "<li>Abre el archivo JAR con un archivador como File Roller, Ark, WinRAR, 7-Zip o WinZip.</li>"
				+ "<li>Entra en <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Elimina esta línea:</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Importante:</b> conserva la única línea vacía al final del archivo.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error detectado en Tetra o en uno de sus addons.</b>"
				+ "<p>El bitácora indica que no se encontró un deserializador para un tipo de modelo usado por <b>Tetra</b> o alguno de sus complementos.</p>"
				+ "<p><b>Pasos recomendados:</b></p>" + "<ul>"
				+ "<li>Primero, elimina o desactiva los <b>addons de Tetra</b> y prueba nuevamente.</li>"
				+ "<li>Si el error continúa, prueba quitar también <b>Tetra</b>.</li>"
				+ "<li>Puedes intentar localizar addons relacionados con Tetra en el <b>DepMap</b>, aunque no siempre aparecerán allí.</li>"
				+ "</ul>"
				+ "<p>En algunos casos el problema proviene de un addon, pero en otros lo causa el propio <b>Tetra</b>.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Error de deserialización de modelo en Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error detectado en Simple Emotes.</b>"
				+ "<p>El bitácora contiene la cadena <b>$simpleemotes$setupAnimTAIL</b>, la cual apunta directamente al mod <b>Simple Emotes</b>.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Quita o desactiva <b>Simple Emotes</b> y prueba nuevamente.</li>"
				+ "<li>Si usas mods que cambian animaciones del jugador o del modelo, revisa posibles incompatibilidades con <b>Simple Emotes</b>.</li>"
				+ "<li>Actualiza <b>Simple Emotes</b> y cualquier mod relacionado con animaciones a versiones compatibles.</li>"
				+ "</ul>"
				+ "<p>Este error suele indicar que <b>Simple Emotes</b> está involucrado directamente en el fallo.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Error en Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Advertencia sobre SKLauncher.</b>"
				+ "<p>Durante los últimos meses se han observado varios casos de <b>corrupción</b> y otros problemas relacionados con <b>SKLauncher</b>.</p>"
				+ "<p>Esto no significa que SKLauncher sea siempre la causa del error, pero sí puede estar contribuyendo al problema.</p>"
				+ "<p><b>Señales de posible corrupción:</b></p>" + "<ul>"
				+ "<li>El juego se cierra muy temprano durante el arranque.</li>"
				+ "<li>El juego también falla incluso <b>sin mods instalados</b>.</li>" + "</ul>"
				+ "<p>Si ocurre alguno de esos casos, prueba usar <b>otro launcher</b> para comprobar si el problema desaparece.</p>"
				+ "<p>Consulta la lista de launchers recomendados aquí:</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Ver documentación de launchers</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Advertencia: posibles problemas con SKLauncher";
	}

	@Override
	public String guardTitulo() {
		return "CD Guard";
	}

	@Override
	public String guardBotonLateral() {
		return "Guard";
	}

	@Override
	public String guardEscanearTodo() {
		return "Escanear servidores y malware";
	}

	@Override
	public String guardEscanearServidores() {
		return "Buscar servidores";
	}

	@Override
	public String guardEscanearMalware() {
		return "Buscar malware";
	}

	@Override
	public String guardTablaServidores() {
		return "Servidores problemáticos";
	}

	@Override
	public String guardTablaMalware() {
		return "Hallazgos de malware";
	}

	@Override
	public String guardColServidor() {
		return "Servidor";
	}

	@Override
	public String guardColDefinicion() {
		return "Definición";
	}

	@Override
	public String guardColMensaje() {
		return "Mensaje";
	}

	@Override
	public String guardColUbicacion() {
		return "Ubicación";
	}

	@Override
	public String guardColClase() {
		return "Clase";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Descompilar";
	}

	@Override
	public String guardCfrTitulo() {
		return "Código descompilado";
	}

	@Override
	public String guardDescripcion1() {
		return "Esta herramienta permite buscar servidores problemáticos y posibles hallazgos de malware en mods.";
	}

	@Override
	public String guardDescripcion2() {
		return "Puede haber falsos positivos, especialmente cuando otras definiciones o escáneres de malware son agresivos.";
	}

	@Override
	public String guardDescripcion3() {
		return "La comprobación de servidores usa definiciones externas. Si no las tienes descargadas, primero tendrás que descargarlas.";
	}

	@Override
	public String guardDescripcion4() {
		return "Si ya tienes definiciones locales, la herramienta te dejará decidir si quieres reutilizarlas o actualizarlas.";
	}

	@Override
	public String guardDescripcion5() {
		return "En la tabla de malware, si una clase está disponible, podrás descompilarla con CFR para inspeccionarla.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Escaneando servidores y malware...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Buscando servidores problemáticos...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Buscando malware...";
	}

	@Override
	public String guardEstadoListo() {
		return "Listo";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Definiciones no encontradas";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "No se encontraron definiciones de servidores. ¿Quieres descargarlas ahora?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Descargar";
	}

	@Override
	public String guardDefsCancelar() {
		return "Cancelar";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Definiciones de servidores";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Ya existen definiciones locales. ¿Quieres usarlas tal cual o actualizarlas?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Usar locales";
	}

	@Override
	public String guardDefsActualizar() {
		return "Actualizar";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Lista de TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Error al descompilar";
	}

	@Override
	public String guardColorPanel() {
		return "Color de panel";
	}

	@Override
	public String guardColorTexto() {
		return "Color de texto";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Color de texto secundario";
	}

	@Override
	public String guardColorTabla() {
		return "Color de tablas";
	}

	@Override
	public String guardColorSeleccion() {
		return "Color de selección";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Color del texto seleccionado";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Compartir instancia/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "La función para compartir la instancia o el modpack todavía no está implementada.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Color del botón de compartir principal";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Color del botón de compartir enlaces";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Color del texto de los botones de compartir";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Compartir instancia";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Compartir instancia";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Formato";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Servicio de subida";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Empaquetar y compartir";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Refrescar";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Listo";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Empaquetando selección...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Subiendo archivo...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Error";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Código";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Enlace";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Debes mantener la aplicación abierta para que la transferencia siga disponible.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "No hay carpetas o archivos seleccionados.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Ese formato todavía no está soportado.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "El servicio seleccionado no está disponible.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Transferencia iniciada correctamente.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "No se pudo subir el archivo seleccionado.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Color de panel";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Color de texto";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Tipos recomendados: mods, configs, saves, worlds, datapacks, resource packs y archivos de opciones. Evita incluir material privado que no sea necesario.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Las extensiones pueden añadir sus propios servicios de subida. Los servicios integrados por defecto deben mostrarse aquí.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app: hasta 5 GiB como subida normal; entre 5 y 10 GiB requiere mantener el emisor abierto. En la implementación actual del proyecto, la integración real todavía está pendiente.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com: pensado como servicio con retención temporal. Todavía no está soportado por esta implementación.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent: modo más seguro al ser distribución P2P directa, sin alojamiento central. Todavía no está soportado por esta implementación.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "Por defecto se seleccionan las carpetas y archivos más comunes de una instancia para facilitar soporte técnico.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Si incluyes la carpeta interna de CrashDetector, también viajarán configuraciones, registros y datos auxiliares, así que puedes deseleccionarla si no hace falta.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Posible Fracturiser detectado. Evidencias:";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Posible ladrón de información detectado. Evidencias:";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Clase sospechosa:";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Archivo sospechoso:";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Posible Bright SDK detectado. Evidencias:";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Paquete sospechoso:";
	}

	@Override
	public String docsTituloVentana() {
		return "Lector de documentos";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Cómo usar este lector</b><br>"
				+ "Seleccione un idioma en la parte inferior para ver la documentación disponible en ese idioma.<br>"
				+ "En el panel izquierdo puede navegar por carpetas y documentos.<br>"
				+ "Al hacer clic en un documento, su contenido aparecerá a la derecha.<br>"
				+ "Los enlaces internos con protocolo <b>docs://</b> abren otros documentos dentro de este mismo lector.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Documentos";
	}

	@Override
	public String docsVisorTitulo() {
		return "Contenido";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "No hay documentos para este idioma.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "No se encontraron archivos Markdown en este idioma.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "No se encontró el documento solicitado.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Error al abrir el documento:";
	}

	@Override
	public String docsCargando() {
		return "Cargando documentos...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Ilustración no disponible";
	}

	@Override
	public String colorPanelSecundario() {
		return "Color de panel secundario";
	}

	@Override
	public String colorTextoSuave() {
		return "Color de texto suave";
	}

	@Override
	public String colorSeleccion() {
		return "Color de selección";
	}

	@Override
	public String colorFondoDocumento() {
		return "Color de fondo del documento";
	}

	@Override
	public String iaTituloVentana() {
		return "IA";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Análisis con IA";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Agente de análisis de crashes";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Esta herramienta abre un agente externo que puede ayudarte a analizar crashes, errores y registros relacionados con Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Para usar este sistema, abre el enlace, inicia sesión con una cuenta de Baidu y luego utiliza el agente para revisar tu crash o tus logs.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Necesitarás una cuenta de Baidu para acceder al agente.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Copiar enlace";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Abrir enlace";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Imagen no disponible";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Posible error de shaders con Oculus o Iris detectado.</b>"
				+ "<p>El bitácora contiene tanto <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "como <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Esta combinación suele indicar un problema al evaluar una variable dentro de un shader, "
				+ "frecuentemente relacionado con <b>Oculus</b>, <b>Iris</b>, o con el <b>shader pack</b> que está en uso.</p>"
				+ "<p><b>Orden recomendado de prueba:</b></p>" + "<ul>"
				+ "<li>Primero, inicia el juego <b>sin shaders activados</b>.</li>"
				+ "<li>Si el problema continúa, prueba iniciar <b>sin Oculus o Iris</b>.</li>"
				+ "<li>Actualiza <b>Oculus/Iris</b>, el <b>shader pack</b> y los mods gráficos relacionados.</li>"
				+ "<li>Si usas otros mods de renderizado o gráficos, revisa incompatibilidades entre ellos.</li>"
				+ "</ul>"
				+ "<p>En la práctica, este fallo suele venir del <b>shader pack</b> o de su interacción con <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Posible error de shaders con Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(desconocido)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(desconocido)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se intentó usar un ítem que no existe.</b>" + "<p>El bitácora contiene la línea <b>Item: " + itemHtml
				+ " does not exist</b>.</p>"
				+ "<p>Esto normalmente significa que algún <b>datapack</b>, <b>mod</b> o <b>configuración</b> "
				+ "está haciendo referencia a un ítem que no está presente en el juego.</p>"
				+ "<p><b>Qué revisar:</b></p>" + "<ul>"
				+ "<li>Comprueba si tienes instalado el mod que debería aportar el ítem <b>" + itemHtml + "</b>.</li>"
				+ "<li>Si sí lo tienes, puede que sea la <b>versión incorrecta</b>, que el ítem haya sido cambiado o eliminado, "
				+ "o que el mod tenga algún problema y convenga quitarlo.</li>"
				+ "<li>Si no tienes ese mod, prueba a <b>instalarlo</b>.</li>" + "</ul>"
				+ "<p><b>Para averiguar qué mod o datapack está pidiendo ese ítem:</b></p>" + "<ul>"
				+ "<li>Usa la utilidad <b>grepr</b> en la barra lateral.</li>"
				+ "<li><b>No</b> selecciones una carpeta.</li>" + "<li>Activa la opción <b>search in archives</b>.</li>"
				+ "<li>En el texto de búsqueda, escribe el <b>namespace</b>, es decir, la parte antes de los dos puntos: "
				+ "<b>" + namespaceHtml + "</b>.</li>" + "</ul>"
				+ "<p>Eso suele ayudar a encontrar qué archivo, mod o datapack está haciendo la referencia inválida.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Ítem inexistente referenciado";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error de modelo detectado para Rhyhorn.</b>"
				+ "<p>El bitácora contiene la línea <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Aunque el modelo usa el namespace de <b>Cobblemon</b>, esta línea suele ser causada por el mod "
				+ "<b>Cobblemon: Pinkan Islands</b>, que es de donde proviene ese <b>Rhyhorn</b>.</p>"
				+ "<p><b>Qué probar:</b></p>" + "<ul>"
				+ "<li>Quita o desactiva <b>Cobblemon: Pinkan Islands</b> y prueba nuevamente.</li>"
				+ "<li>Actualiza <b>Cobblemon</b> y <b>Cobblemon: Pinkan Islands</b> a versiones compatibles entre sí.</li>"
				+ "<li>Si el problema empezó tras actualizar uno de esos mods, prueba una combinación de versiones distinta.</li>"
				+ "</ul>"
				+ "<p>Este fallo normalmente indica un modelo faltante, mal registrado o incompatible dentro de "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Error de modelo de Rhyhorn en Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error detectado en Cold Sweat.</b>"
				+ "<p>El bitácora contiene indicios como <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> y un <b>NullPointerException</b> donde "
				+ "el registro aparece como nulo.</p>"
				+ "<p>Esto suele indicar un problema de <b>Cold Sweat</b> al construir o llenar "
				+ "<b>tags dinámicos</b>, normalmente por incompatibilidad, error interno del mod "
				+ "o una combinación conflictiva con otro mod o datapack.</p>" + "<p><b>Qué probar:</b></p>" + "<ul>"
				+ "<li>Quita o desactiva <b>Cold Sweat</b> y prueba nuevamente.</li>"
				+ "<li>Actualiza <b>Cold Sweat</b> a una versión compatible con tu versión de Minecraft y tu loader.</li>"
				+ "<li>Si usas datapacks o mods que alteran <b>tags</b>, <b>biomas</b>, <b>temperaturas</b> o registros relacionados, revísalos también.</li>"
				+ "<li>Si el error empezó tras actualizar mods, prueba una combinación de versiones distinta.</li>"
				+ "</ul>" + "<p>En este caso, <b>Cold Sweat</b> está involucrado directamente en el fallo.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Error de Cold Sweat en tags dinámicos";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Línea detectada:</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Se detectó un ClassCastException.</b>"
				+ "<p>Esto significa que una clase fue tratada como si fuera otra clase o interfaz incompatible.</p>"
				+ detalle + "<p>Este tipo de error suele ser causado por una de estas situaciones:</p>" + "<ul>"
				+ "<li><b>Dos mods incompatibles</b> entre sí.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> o parches que modifican una clase y hacen que otra parte del juego espere un tipo distinto.</li>"
				+ "<li>Otros mods presentes en el <b>stacktrace</b> que están provocando el miscast.</li>" + "</ul>"
				+ "<p><b>Qué revisar:</b></p>" + "<ul>"
				+ "<li>Mira las líneas del <b>stacktrace</b> relacionadas con este error.</li>"
				+ "<li>Presta especial atención a nombres de mods o clases con el formato <b>$modid$algo</b>, porque suelen señalar mods involucrados.</li>"
				+ "<li>Prueba actualizar, quitar o separar mods que parezcan estar relacionados con la conversión inválida.</li>"
				+ "</ul>" + "<p>Aunque un <b>ClassCastException</b> no siempre es fatal, muy a menudo sí lo es.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ClassCastException detectado";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Posible incompatibilidad entre Valkyrien Skies Tournament y Lithium/Radium detectada.</b>"
				+ "<p>El bitácora contiene una <b>InvalidInjectionException</b> donde aparece un mixin de "
				+ "<b>Lithium</b> sobre <b>POI</b> junto con <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Este problema suele ocurrir cuando se usa una versión <b>antigua de Lithium</b> o un "
				+ "<b>fork basado en Lithium viejo</b>, como <b>Radium Reforged</b>, junto con "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>Qué probar:</b></p>" + "<ul>"
				+ "<li>Actualiza <b>Lithium</b> a una versión más nueva y compatible.</li>"
				+ "<li>Si estás en <b>Forge/NeoForge</b> y usas <b>Radium Reforged</b> u otro fork viejo, quítalo.</li>"
				+ "<li>En su lugar, prueba <b>Harium</b>, que es un fork moderno de Radium sincronizado con mejoras recientes de Lithium.</li>"
				+ "<li>Si el problema empezó tras actualizar mods, revisa la combinación exacta entre <b>VS Tournament</b> y tu mod de optimización AI/POI.</li>"
				+ "</ul>"
				+ "<p>En la práctica, este fallo suele venir de una implementación vieja de <b>Lithium/Radium</b> que no encaja bien con <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Incompatibilidad VS Tournament con Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament parece ser demasiado antiguo para tu versión de Valkyrien Skies.</b>"
				+ "<p>El bitácora contiene un <b>NoClassDefFoundError</b> para "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> y también una línea de "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Eso normalmente significa que tienes una versión <b>vieja de VS Tournament</b> que intenta "
				+ "usar clases internas antiguas de <b>Valkyrien Skies</b> que ya no existen.</p>"
				+ "<p><b>Qué hacer:</b></p>" + "<ul>" + "<li>Quita <b>VS Tournament</b> antiguo.</li>"
				+ "<li>Usa <b>VS Tournament Reforged</b> en su lugar.</li>"
				+ "<li>Comprueba además que la versión de <b>Valkyrien Skies</b> coincida con la versión soportada por el addon.</li>"
				+ "</ul>"
				+ "<p>La recomendación de cambiar a <b>VS Tournament Reforged</b> encaja con el estado actual del proyecto: "
				+ "la versión original de Tournament sigue listada como mod antiguo para 1.18.2, mientras que "
				+ "<b>VS Tournament Reforged</b> se publica aparte y actualmente anuncia soporte para Valkyrien "
				+ "2.4.9+ en Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament antiguo incompatible con Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Clave API mundial de CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Endpoint de CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Endpoint de TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Endpoint de MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Auto-backup activado";
	}

	public String autoBackupFrecuencia() {
		return "Frecuencia de auto-backup";
	}

	public String autoBackupDiasConservar() {
		return "Días para conservar auto-backups";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Tamaño máximo de auto-backups (MB)";
	}

	public String actualizadorModsTitulo() {
		return "Actualizador de mods";
	}

	public String actualizadorModsBotonSidebar() {
		return "Actualizador";
	}

	public String actualizadorModsDescripcion() {
		return "Busca actualizaciones disponibles para los mods del modpack. Puedes actualizar todos o aplicar actualizaciones individuales.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Buscar actualizaciones";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Actualizar todo";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Actualizar";
	}

	public String actualizadorModsEstadoListo() {
		return "Listo";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Buscando actualizaciones...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Actualizando...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "No hay actualizaciones disponibles.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Actualizaciones encontradas: " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Actualizaciones aplicadas: " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Error actualizando.";
	}

	public String actualizadorModsSinSeleccion() {
		return "No hay actualizaciones seleccionadas.";
	}

	public String actualizadorModsColumnaMod() {
		return "Mod";
	}

	public String actualizadorModsColumnaActual() {
		return "Actual";
	}

	public String actualizadorModsColumnaNueva() {
		return "Nueva";
	}

	public String actualizadorModsColumnaFuente() {
		return "Fuente";
	}

	public String actualizadorModsColumnaLoader() {
		return "Loader";
	}

	public String actualizadorModsColumnaRuta() {
		return "Ruta";
	}

	public String actualizadorModsColumnaAccion() {
		return "Acción";
	}

	public String actualizadorModsColorFondo() {
		return "Actualizador: fondo";
	}

	public String actualizadorModsColorPanel() {
		return "Actualizador: panel";
	}

	public String actualizadorModsColorTexto() {
		return "Actualizador: texto";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Actualizador: texto suave";
	}

	public String actualizadorModsColorBoton() {
		return "Actualizador: botón";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Actualizador: texto del botón";
	}

	public String actualizadorModsColorTabla() {
		return "Actualizador: tabla";
	}

	public String actualizadorModsColorSeleccion() {
		return "Actualizador: selección";
	}

	public String importadorYumeiriTeExtraniamos() {

		return "Te extrañamos, Yumeiri Reyu.";

	}

	public String importadorColorFondo() {
		return "Importador: fondo";
	}

	public String importadorColorPanel() {
		return "Importador: panel";
	}

	public String importadorColorTexto() {
		return "Importador: texto";
	}

	public String importadorColorTextoSuave() {
		return "Importador: texto suave";
	}

	public String importadorColorBoton() {
		return "Importador: botón";
	}

	public String importadorColorBotonTexto() {
		return "Importador: texto del botón";
	}

	public String importadorColorBorde() {
		return "Importador: borde";
	}

	public String importadorConflictoTitulo() {
		return "Conflicto al importar";
	}

	public String importadorConflictoMensaje() {
		return "Ya existe un archivo en el destino.";
	}

	public String importadorRuta() {
		return "Ruta";
	}

	public String importadorArchivoExistente() {
		return "Archivo existente";
	}

	public String importadorArchivoNuevo() {
		return "Archivo importado";
	}

	public String importadorTamano() {
		return "Tamaño";
	}

	public String importadorFecha() {
		return "Última modificación";
	}

	public String importadorInfoMod() {
		return "Información del mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "El mod importado parece ser más nuevo.";
	}

	public String importadorModImportadoMasViejo() {
		return "El mod importado parece ser más viejo.";
	}

	public String importadorBotonReemplazar() {
		return "Reemplazar";
	}

	public String importadorBotonSaltar() {
		return "Saltar";
	}

	public String importadorBotonRenombrar() {
		return "Renombrar nuevo";
	}

	public String importadorModpackTitulo() {
		return "Importar modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "Importar";
	}

	public String importadorModpackDescripcion() {
		return "Importa un modpack a la instancia actual. Puedes arrastrar un archivo .zip, .mrpack u otro formato soportado, o seleccionarlo manualmente.";
	}

	public String importadorModpackFormato() {
		return "Formato";
	}

	public String importadorModpackArrastraArchivo() {
		return "Arrastra aquí tu modpack o selecciona un archivo";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Seleccionar archivo";
	}

	public String importadorModpackBotonImportar() {
		return "Importar";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Seleccionar modpack";
	}

	public String importadorModpackEstadoListo() {
		return "Listo";
	}

	public String importadorModpackEstadoImportando() {
		return "Importando...";
	}

	public String importadorModpackEstadoError() {
		return "Error importando.";
	}

	public String importadorModpackSinArchivo() {
		return "Selecciona un archivo de modpack primero.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Este formato no soporta importación.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Importación terminada.\nCopiados: " + copiados + "\nReemplazados: " + reemplazados + "\nSaltados: "
				+ saltados + "\nRenombrados: " + renombrados + "\nErrores: " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Importador de modpacks: fondo";
	}

	public String importadorModpackColorPanel() {
		return "Importador de modpacks: panel";
	}

	public String importadorModpackColorTexto() {
		return "Importador de modpacks: texto";
	}

	public String importadorModpackColorTextoSuave() {
		return "Importador de modpacks: texto suave";
	}

	public String importadorModpackColorBoton() {
		return "Importador de modpacks: botón";
	}

	public String importadorModpackColorBotonTexto() {
		return "Importador de modpacks: texto del botón";
	}

	public String importadorModpackColorDrop() {
		return "Importador de modpacks: zona de arrastre";
	}

	public String importadorModpackColorBorde() {
		return "Importador de modpacks: borde";
	}

	public String jgitTituloIzzy() {
		return "Centro Git de Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "No hay retrato de Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "Instalación de JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Abrir carpeta de instalación";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Abrir página de JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Repositorio local";
	}

	public String jgitCrearRepositorioLocal() {
		return "Crear repositorio Git aquí";
	}

	public String jgitCommitManual() {
		return "Commit manual";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "Manual";
	}

	public String jgitForgePersonalizado() {
		return "Forge personalizado";
	}

	public String jgitEstablecerRemoteManual() {
		return "Establecer remote manual";
	}

	public String jgitCrearRemoteConAPI() {
		return "Crear remote con API";
	}

	public String jgitPushManual() {
		return "Push manual";
	}

	public String jgitSeccionAutomaticos() {
		return "Automatización";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit automático después del backup";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push automático después del commit";
	}

	public String jgitSeccionHerramientas() {
		return "Herramientas";
	}

	public String jgitAbrirGuiSwing() {
		return "Abrir visor Swing de JGit";
	}

	public String jgitEstado() {
		return "Estado";
	}

	public String jgitClasspath() {
		return "JGit en classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Todos los artefactos JGit";
	}

	public String jgitRepositorio() {
		return "Repositorio";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "Carpeta actual";
	}

	public String jgitNoSePudoCrearRepo() {
		return "No se pudo crear el repositorio.";
	}

	public String jgitEscribaRemote() {
		return "Escriba la URL del remote:";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "No se pudo guardar el remote.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "La API de forge todavía no está implementada.";
	}

	public String jgitNoHayCambiosOError() {
		return "No hay cambios para commit o ocurrió un error.";
	}

	public String jgitNoSePudoPush() {
		return "No se pudo hacer push.";
	}

	public String jgitTituloVentanaSwing() {
		return "Visor Git";
	}

	public String jgitNoHayRepositorio() {
		return "No hay repositorio Git en esta carpeta.";
	}

	public String jgitArchivosModificados() {
		return "Archivos modificados";
	}

	public String jgitArchivosNuevos() {
		return "Archivos nuevos";
	}

	public String jgitUltimosCommits() {
		return "Últimos commits";
	}

	public String jgitError() {
		return "Error de JGit";
	}

	public String si() {
		return "Sí";
	}

	public String no() {
		return "No";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Descargar dependencias faltantes";
	}

	public String jgitNoFaltanDependencias() {
		return "No faltan dependencias de JGit.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Faltan " + cantidad + " dependencias de JGit. ¿Quieres descargarlas desde Maven Central?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Dependencias descargadas: " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Errores de descarga";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Reinicia CrashDetector para que los nuevos JARs entren al classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Artefactos faltantes";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Artefactos faltantes en classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Artefactos faltantes en la carpeta de instalación";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Dependencias instaladas en carpeta";
	}

	public String jgitForgeNoSeleccionada() {
		return "No hay forge seleccionada.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "La forge no está registrada: " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL de la forge:";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Nombre del repositorio:";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Descripción del repositorio:";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace opcional:";
	}

	public String jgitEscribaTokenForge() {
		return "Token API de la forge:";
	}

	public String jgitErrorCrearRemote() {
		return "Error al crear remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilidad detectada entre Controlify y Remove Reloading Screen.</b>"
				+ "<p>El log contiene las líneas <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "y <b>$rrls$init</b>, lo cual normalmente indica un conflicto entre <b>Controlify</b> y "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Causa probable:</b> Remove Reloading Screen modifica partes de la pantalla de carga o del proceso de carga, "
				+ "mientras Controlify intenta inicializar su configuración antes de que el sistema esté completamente listo.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>" + "<li>Elimina <b>Remove Reloading Screen</b>.</li>"
				+ "<li>O actualiza <b>Controlify</b> y <b>Remove Reloading Screen</b> si hay versiones nuevas disponibles.</li>"
				+ "<li>Si el problema continúa, mantén <b>Controlify</b> y quita cualquier mod que altere la pantalla de carga.</li>"
				+ "</ul>"
				+ "<p>Los mods que modifican la pantalla de carga suelen causar incompatibilidades con otros mods, "
				+ "y normalmente ofrecen poco beneficio práctico en comparación con los problemas que pueden provocar.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Incompatibilidad: Controlify vs Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Posible problema con Biomes O' Plenty y líquidos personalizados.</b>"
				+ "<p>El log contiene el error <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> junto con una referencia a <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Esto posiblemente está relacionado con <b>Biomes O' Plenty</b>, especialmente con biomas, niebla "
				+ "o líquidos personalizados. Sin embargo, no es completamente seguro que Biomes O' Plenty sea la única causa.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Intenta editar los datos del jugador para moverlo a otra ubicación del mundo.</li>"
				+ "<li>Prueba cargar el mundo sin <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Si el mundo carga después de mover al jugador, el problema probablemente ocurre en una zona específica, "
				+ "bioma específico o líquido personalizado cercano.</li>"
				+ "<li>También puedes probar actualizando <b>Biomes O' Plenty</b> y los mods relacionados con renderizado, niebla, "
				+ "shaders o dimensiones.</li>" + "</ul>"
				+ "<p>Si quitar Biomes O' Plenty permite iniciar el juego, revisa si el jugador estaba dentro o cerca de un bioma "
				+ "o fluido agregado por ese mod.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Posible problema: Biomes O' Plenty y FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error interno de reflexión de Kotlin detectado.</b>"
				+ "<p>El log contiene <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> con un mensaje parecido a "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Este tipo de error es común con ciertas versiones de <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "En este caso aparece una clase de <b>Inventory Profiles Next</b>, pero el mismo problema también puede ocurrir "
				+ "con otros mods que usan Kotlin.</p>" + "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Actualiza <b>Fabric Language Kotlin</b> a la versión <b>2.3.40</b>, si está disponible para tu versión de Minecraft.</li>"
				+ "<li>Si actualizar no funciona, intenta bajar <b>Fabric Language Kotlin</b> a la versión <b>2.3.10</b>.</li>"
				+ "<li>Actualiza también <b>Inventory Profiles Next</b> si el log menciona clases de ese mod.</li>"
				+ "<li>Si el error aparece con otro mod, revisa si ese mod depende de Kotlin y prueba cambiar la versión de "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Referencia técnica relacionada: "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Fabric Language Kotlin issue #183</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Error de Kotlin: reflexión interna";
	}

	public String tituloEscanerMCreator() {
		return "Escáner MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Escaneando mods...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Por favor espera.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Resultados del análisis MCreator:";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "No se encontraron mods de MCreator.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Escaneo completado.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Error durante el escaneo:";
	}

	public String escanerMCreatorCargando() {
		return "Cargando...";
	}

	public String escanerMCreatorCompletado() {
		return "Completado";
	}

	public String escanerMCreatorError() {
		return "Error";
	}

	@Override
	public String textoNormal() {
		return "Texto normal";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "No se encontró la consola para el archivo: ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "línea seleccionada en lectador: ";
	}

	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Posible problema con Motion Blur.</b>"
				+ "<p>El log contiene una referencia a <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "y también el error <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Estas líneas pueden aparecer separadas en el log, pero juntas suelen indicar que el problema está relacionado "
				+ "con el mod <b>Motion Blur</b> o con su manejo de shaders/buffers gráficos.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Prueba iniciar el juego sin <b>Motion Blur</b>.</li>"
				+ "<li>Si el juego inicia correctamente sin ese mod, mantenlo desinstalado o busca una versión más reciente.</li>"
				+ "<li>También puedes probar sin shaders u otros mods de renderizado si el problema continúa.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Posible problema: Motion Blur";
	}

	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Posible conflicto con Generator Accelerator.</b>"
				+ "<p>El log contiene una diferencia entre firmas <b>Found</b> y <b>Available</b>, junto con clases de "
				+ "<b>Generator Accelerator</b>, por ejemplo <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Este error probablemente está causado por <b>Generator Accelerator</b>. También puede estar relacionado "
				+ "con una incompatibilidad entre ese mod y ciertas versiones de <b>owo-lib</b>.</p>"
				+ "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Prueba iniciar el juego sin <b>Generator Accelerator</b>.</li>"
				+ "<li>Si el juego inicia correctamente, mantén ese mod desinstalado o busca una versión diferente.</li>"
				+ "<li>Prueba actualizar o cambiar la versión de <b>owo-lib</b>, especialmente si otros mods también dependen de owo.</li>"
				+ "<li>Verifica que <b>Generator Accelerator</b>, <b>owo-lib</b>, el loader y la versión de Minecraft sean compatibles entre sí.</li>"
				+ "</ul>"
				+ "<p>La causa más probable es que Generator Accelerator esté intentando aplicar una modificación con una firma "
				+ "que no coincide con la versión actual de una clase o dependencia.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Posible conflicto: Generator Accelerator y owo-lib";
	}

	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Falta un renderer compatible con Fabric Rendering API.</b>"
				+ "<p>El log contiene un error donde <b>RendererAccess.getRenderer()</b> devuelve <b>null</b>, "
				+ "causando un fallo al intentar usar <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Este problema suele ocurrir cuando <b>Fabric Rendering API</b> no está disponible correctamente. "
				+ "Una causa común es usar <b>Sodium</b>, especialmente versiones antiguas que reemplazan o desactivan partes "
				+ "del sistema de renderizado esperado por otros mods.</p>" + "<p><b>Solución recomendada:</b></p>"
				+ "<ul>" + "<li>Instala el mod <b>Indium</b>.</li>"
				+ "<li>Asegúrate de que <b>Indium</b> sea compatible con tu versión de <b>Sodium</b>, Fabric Loader y Minecraft.</li>"
				+ "<li>Si ya tienes Indium instalado, actualiza <b>Sodium</b>, <b>Indium</b> y <b>Fabric API</b>.</li>"
				+ "<li>Si el problema continúa, prueba temporalmente sin Sodium para confirmar que el fallo está relacionado con el renderer.</li>"
				+ "</ul>"
				+ "<p>Indium normalmente restaura la compatibilidad con Fabric Rendering API para mods que dependen de ese sistema "
				+ "mientras Sodium está instalado.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Falta Indium / Fabric Rendering API";
	}

	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Entrada duplicada detectada en un registro de Minecraft.</b>"
				+ "<p>El log contiene un error parecido a <b>Duplicate entry on id</b>, por ejemplo "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>En versiones modernas de Minecraft, este tipo de error suele ocurrir cuando dos mods intentan registrar "
				+ "entradas distintas usando el mismo ID interno.</p>" + "<p><b>Opciones recomendadas:</b></p>" + "<ul>"
				+ "<li>Elimina uno de los mods que está registrando la entrada duplicada.</li>"
				+ "<li>Si reconoces los nombres mencionados en el error, revisa qué mod agrega esas entradas y prueba sin ese mod.</li>"
				+ "<li>Si no reconoces los nombres, usa la utilidad <b>grepr</b> en la barra lateral.</li>"
				+ "<li>En <b>grepr</b>, activa la búsqueda dentro de archivos comprimidos <b>.jar</b>, <b>.zip</b> y <b>.fpm</b>.</li>"
				+ "<li>También activa la búsqueda en <b>archivos binarios</b>, porque algunos nombres o IDs pueden estar dentro de clases compiladas.</li>"
				+ "</ul>"
				+ "<p>Busca los valores mencionados en el error, como <b>maroon</b> o <b>mint</b>, para encontrar qué mod los contiene.</p>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Entrada duplicada en ID de mod";
	}

	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – memoria de video insuficiente";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft generó un error OpenGL por falta de memoria gráfica.</b>" + "<p>El juego lanzó:</p>"
				+ "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Esto normalmente significa que la tarjeta gráfica o el sistema no pudieron reservar suficiente memoria para texturas, shaders, modelos, buffers o efectos visuales.</p>"
				+ "<p><b>Causas comunes:</b></p>" + "<ul>" + "<li>Shaders demasiado pesados.</li>"
				+ "<li>Resource packs de alta resolución.</li>" + "<li>Demasiados mods visuales o de renderizado.</li>"
				+ "<li>Distancia de renderizado demasiado alta.</li>" + "<li>Poca VRAM disponible.</li>"
				+ "<li>Drivers de video desactualizados o inestables.</li>" + "</ul>"
				+ "<p><b>Solución recomendada:</b></p>" + "<ul>" + "<li>Desactivar shaders temporalmente.</li>"
				+ "<li>Usar resource packs de menor resolución.</li>"
				+ "<li>Bajar la distancia de renderizado y simulación.</li>"
				+ "<li>Reducir calidad gráfica, sombras, partículas y mipmaps.</li>"
				+ "<li>Actualizar los drivers de la tarjeta gráfica.</li>"
				+ "<li>Cerrar otros programas que usen GPU o mucha memoria.</li>" + "</ul>"
				+ "<p>Si el error empezó después de instalar un shader, texture pack o mod visual, lo más probable es que ese sea el causante.</p>";
	}

	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode o mixin inválido";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft encontró un error de verificación de bytecode.</b>"
				+ "<p>Este problema normalmente ocurre cuando una manipulación de bytecode, transformer o mixin falló.</p>"
				+ "<p>El juego lanzó:</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Ubicación:</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Razón:</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Qué buscar:</b></p>" + "<ul>" + "<li>Buscar en la ubicación del error.</li>"
				+ "<li>Buscar el tipo mencionado en <code>Reason</code>.</li>"
				+ "<li>Revisar el stack trace para clases de mods sospechosas.</li>"
				+ "<li>Buscar nombres de clases de mods alrededor del error para obtener ideas.</li>" + "</ul>"
				+ "<p><b>Uso recomendado de Grepr:</b></p>" + "<ul>"
				+ "<li>Abrir <b>Grepr</b> en la barra lateral.</li>"
				+ "<li>Activar la opción para buscar dentro de archivos <code>.jar</code>, <code>.zip</code> o <code>.fpm</code>.</li>"
				+ "<li>Buscar el nombre básico de la clase, no necesariamente el paquete completo.</li>" + "</ul>"
				+ "<p>Ejemplo: si aparece <code>paquete.Clase</code>, buscar solamente:</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Esto puede ayudar a encontrar qué mod contiene o modifica esa clase.</p>"
				+ "<p>Soluciones comunes: actualizar el mod afectado, quitar mods incompatibles, revisar addons del mod principal, o probar sin mods que usan mixins/transformers sobre la misma clase.</p>";
	}

	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Error de compatibilidad: un mod intenta sobrescribir un método final.</b>"
				+ "<p>La bitácora contiene un error <b>IncompatibleClassChangeError</b> con el texto "
				+ "<b>overrides final method</b>.</p>" + "<p>Clase afectada: <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Método final afectado: <code>" + metodoFinal + "</code></p>"
				+ "<p>Este error normalmente ocurre cuando un mod fue compilado para una versión diferente de Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt, o una librería base.</p>" + "<p><b>Qué probar:</b></p>" + "<ul>"
				+ "<li>Actualiza el mod que contiene la clase indicada.</li>"
				+ "<li>Si el problema empezó después de actualizar Minecraft o el cargador, prueba una versión compatible del mod.</li>"
				+ "<li>Si la clase pertenece a <b>Immersive Portals</b>, revisa que <b>Immersive Portals</b> coincida exactamente con tu versión de Minecraft y cargador.</li>"
				+ "<li>Evita mezclar mods hechos para versiones distintas del cargador o de Minecraft.</li>" + "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Un mod intenta sobrescribir un método final";
	}
	
	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft fue cerrado por un comando de crash.</b>"
				+ "<p>La bitácora indica que se ejecutó el comando <code>" + comandoDetectado + "</code>.</p>"
				+ "<p>Esto normalmente significa que el juego no se cerró por un error normal de mods, sino porque alguien "
				+ "usó un comando diseñado para provocar un crash manualmente.</p>"
				+ "<p><b>Qué revisar:</b></p>"
				+ "<ul>"
				+ "<li>Revisa quién ejecutó el comando en la consola o dentro del juego.</li>"
				+ "<li>Si fue una prueba, puedes ignorar este crash.</li>"
				+ "<li>Si ocurrió sin intención, revisa command blocks, scripts, datapacks, mods de administración o permisos de operadores.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Crash provocado por comando";
	}
	
	
	
	
	
	

}
