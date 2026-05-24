package com.asbestosstar.crashdetector.idioma;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Frances implements Idioma {
	Config config = Config.obtenerInstancia();

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "fr";
	}

	@Override
	public String nombre_del_idioma_espanol_minusculas_ascii() {
		return "frances";
	}

	@Override
	public String nombre_del_idioma() {
		return "Français";
	}

	@Override
	public Path imagen_bandera() {
		return Statics.carpeta.resolve("imagenes").resolve("bandera_burkina_faso.png");
	}

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>n'est pas un dossier de mods valide</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Je ne sais pas où se trouve le fichier JAR de CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Recherche pour PID : " + String.valueOf(pid)
				+ "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + String.valueOf(pid)
				+ ") est mort !</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Nous n'avons pas de JVM</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Mettre à jour vos pilotes ATI/AMD pourrait vous aider. Lisez ce guide pour résoudre le problème : <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Guide de mise à jour des pilotes</a> https://www.amd.com/es/support/download/drivers.html Télécharger </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Certaines anciennes versions rencontrent parfois des problèmes avec certains pilotes graphiques Nouveau sur l'écran de chargement précoce.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vous avez un problème avec vos pilotes graphiques. Si vous avez un GPU ou APU AMD/ATI, mettez à jour vos pilotes AMD. Si vous avez une carte graphique NVIDIA, assurez-vous de configurer le jeu et toutes les instances de javaw.exe pour utiliser la carte graphique dédiée. Lisez ce guide : <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guide de mise à jour des pilotes</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Tu ventana FML Early está fallando. Para cambiar esto, ve a (.minecraft/config/fml.toml) y edita earlyWindowProvider para que sea earlyWindowProvider=\\\"none\\\". Si estás en una Mac M1, también asegúrate de estar usando una version ARM de Java, no una version Intel x64. Este también es un problema común si tienes controladores desactualizados. Consulta esta guía si estás en Windows y desactivar esto no funciona: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guide de mise à jour des pilotes</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vous n'avez pas toutes les dépendances nécessaires :</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Demandé par").replace("Expected range", "plage attendue") + "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Votre rapport CrashDetector est ici <a href='"
				+ archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>voir le rapport</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Ceci est l'interface graphique de CrashDetector. Si le jeu se ferme sans problème, ignorez-la.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Voir le rapport</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Voir un rapport local dans votre navigateur.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Partager le rapport";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Partager le rapport. Vos journaux seront téléversés sur securelogger.net et votre rapport sera envoyé vers un autre site.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Fichiers JAR problématiques détectés (prioriser FATAL, puis niveau élevé et ligne basse) :</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> niveau :</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Possiblement fatal :</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ModIDs problématiques détectés (prioriser FATAL, puis niveau bas et ligne basse) :</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Packages problématiques détectés (prioriser FATAL, puis niveau bas et ligne basse) :</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes fatales (FATAL), c'est très important. Les causes courantes sont de mauvais coremods ou des dépendances critiques manquantes. Vous pouvez utiliser QuickFix pour rechercher les mods contenant ces classes fatales. Classes fatales manquantes détectées :</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Contenu dans {} (le plus important en haut, seulement les 20 premiers) :</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Configuration SpongeMixin problématique détectée : " + "</b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des mods avec des packages/dossiers en double. Vous pouvez corriger cela en supprimant le package (dossier) du jar. Vous pouvez ouvrir le jar avec un logiciel d'archive comme WinRAR ou 7z, ou bien changer l'extension du fichier de .jar à .zip, supprimer le dossier, puis remettre l'extension .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vous avez des mods en double</b> "
				+ linea.replace("from mod files", "depuis des fichiers de mod ");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>MinecraftForge suspect : ce mod présente un problème :</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV nécessite lithostitched, vous pouvez l'installer ici : <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Pour utiliser Iris Shaders ou Oculus, vous avez besoin de SODIUM ou d'un équivalent pour un autre chargeur (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Problème avec l'extension KubeJS </b>"
				+ mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Problèmes détectés avec les pilotes NVIDIA sur les versions antérieures à Windows 11."
				+ "</span><br/><br/>"
				+ "Pour vous assurer que Minecraft (et la JVM actuelle) utilise la carte graphique dédiée NVIDIA, suivez ces étapes :<br/><br/>"
				+ "1. <strong>Identifiez l'exécutable Java :</strong><br/>"
				+ "   - Ce programme utilise l'exécutable Java suivant : " + obtenerRutaJava() + "<br/>"
				+ "   - Si vous ne voyez pas de chemin spécifique, vous pouvez trouver l'exécutable Java en cherchant 'java.exe' sur votre système.<br/><br/>"
				+ "2. <strong>Ouvrez le Panneau de configuration NVIDIA :</strong><br/>"
				+ "   - Faites un clic droit sur le bureau et sélectionnez 'Panneau de configuration NVIDIA'.<br/><br/>"
				+ "3. <strong>Configurez le GPU préféré :</strong><br/>"
				+ "   - Dans le Panneau de configuration NVIDIA, allez dans 'Gérer les paramètres 3D'.<br/>"
				+ "   - Sélectionnez l'option 'Paramètres du programme'.<br/>"
				+ "   - Cliquez sur 'Ajouter' et recherchez l'exécutable Java identifié précédemment (ex. : 'java.exe').<br/>"
				+ "   - Assurez-vous qu'il est configuré pour utiliser le 'Processeur haute performance (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Enregistrez les modifications :</strong><br/>"
				+ "   - Appliquez les changements et fermez le Panneau de configuration NVIDIA.<br/><br/>"
				+ "5. <strong>Redémarrez Minecraft :</strong><br/>"
				+ "   - Redémarrez Minecraft pour que les changements prennent effet.<br/><br/>"
				+ "Si vous utilisez Windows Server 2022 ou Windows 10, ces étapes sont valables tant que vous avez installé les derniers pilotes NVIDIA.<br/><br/>"
				+ "Remarque : Si vous ne trouvez pas le Panneau de configuration NVIDIA, assurez-vous que les pilotes NVIDIA sont correctement installés.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Problèmes détectés avec les pilotes NVIDIA sur Windows 11/Server 2025 ou ultérieur."
				+ "</span><br/><br/>"
				+ "Pour vous assurer que Minecraft (et la JVM actuelle) utilise la carte graphique dédiée NVIDIA, suivez ces étapes :<br/><br/>"
				+ "1. <strong>Identifiez l'exécutable Java :</strong><br/>"
				+ "   - Ce programme utilise l'exécutable Java suivant : " + obtenerRutaJava() + "<br/>"
				+ "   - Si vous ne voyez pas de chemin spécifique, vous pouvez trouver l'exécutable Java en cherchant 'java.exe' sur votre système.<br/><br/>"
				+ "2. <strong>Ouvrez l'application Paramètres :</strong><br/>"
				+ "   - Appuyez sur les touches <code>Win + I</code> pour ouvrir l'application Paramètres.<br/>"
				+ "   - Accédez à <strong>Système > Affichage > Graphiques</strong>.<br/><br/>"
				+ "3. <strong>Configurez le GPU préféré :</strong><br/>"
				+ "   - Dans la section 'Graphiques', cliquez sur 'Modifier les paramètres graphiques par défaut'.<br/>"
				+ "   - Sélectionnez 'Applications de bureau' puis cliquez sur 'Parcourir'.<br/>"
				+ "   - Recherchez et sélectionnez l'exécutable Java identifié précédemment (ex. : 'java.exe').<br/>"
				+ "   - Une fois ajouté, sélectionnez l'application Java dans la liste et configurez-la pour utiliser 'Hautes performances (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Enregistrez les modifications :</strong><br/>"
				+ "   - Appliquez les changements et fermez l'application Paramètres.<br/><br/>"
				+ "5. <strong>Redémarrez Minecraft :</strong><br/>"
				+ "   - Redémarrez Minecraft pour que les changements prennent effet.<br/><br/>"
				+ "Si vous utilisez Windows 11 ou Windows Server 2025+, ces étapes sont valables tant que vous avez installé les derniers pilotes NVIDIA.<br/><br/>"
				+ "Remarque : Si vous ne trouvez pas l'option de configuration graphique, assurez-vous que les pilotes NVIDIA sont correctement installés.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Votre serveur ou monde a des ticks dépassant 60 secondes. Cela peut être dû à des mods qui ralentissent le serveur ou à un matériel trop faible.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous n'avez pas assez de RAM/Mémoire. Vous devez en allouer davantage.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Theseus présente également plus de problèmes, y compris des échecs lors de la suppression de mods. Si vous devez jouer avec des fichiers mrpack, vous pouvez utiliser d'autres lanceurs comme Prism Launcher (uniquement pour modrinth.com), ATLauncher (uniquement pour modrinth.com) ou Hello Minecraft Launcher (compatible avec modrinth.com et bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Vous utilisez \\\"Ignorer le lancement du launcher\\\" (Application CurseForge). Parfois, cela cause des problèmes difficiles à détecter. Cela est dû à l'option \\\"Ignorer le lancement du launcher\\\" dans les anciennes versions de l'application CurseForge ou dans la nouvelle version. Désactivez-la et utilisez l'option \\\"Lanceur Mojang\\\" dans les paramètres de CurseForge. Vous pouvez regarder cette vidéo en anglais de Claws of Berk (aller à 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>ici</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Avertissement : Il manque des classes (Avertissement), généralement ce n'est pas grave mais pas toujours, c'est différent des classes manquantes fatales. De mauvais coremods ou des dépendances manquantes sont des raisons courantes de ce problème. Vous pouvez utiliser QuickFix pour rechercher les mods contenant ces classes. Classes manquantes détectées :</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Aucun résultat</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Emplacement des logs :</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Voici les résultats de vos vérifications. Procédez lentement, généralement la raison correcte se trouve dans la vérification 1 ou 2, les autres (erreurs 3+) peuvent servir de confirmation mais sont souvent des erreurs en cascade que vous pouvez ignorer. Les pannes se produisent en couches, donc résoudre le bon problème corrigera cette erreur spécifique aujourd'hui, mais il est possible qu'une nouvelle erreur non liée apparaisse demain, car souvent une erreur peut empêcher une autre d'apparaître dans la console.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Veuillez utiliser Java 17 pour les versions 1.17-1.20.4 et Java 21 pour toute version plus récente. Utilisez Java 8 pour les versions plus anciennes. [Guide](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Si vous avez encore des problèmes, cela pourrait être parce qu'un mod a des fichiers trop récents ou trop anciens.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Java 22 et supérieur ne fonctionnent pas sur les versions de Minecraft inférieures à 1.20.5 pour la plupart des chargeurs de mods car ASM est obsolète.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Java est obsolète </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Vous avez besoin du module JPMS " + mod_necesitas
				+ " depuis " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Impossible d'invoquer " + metodo + " car " + objeto
				+ " est nul</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Analyse Avancée";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Sélectionner le dossier";
	}

	@Override
	public String cadenaBusqueda() {
		return "Chaîne de recherche";
	}

	@Override
	public String usarRegex() {
		return "Utiliser Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Ignorer les majuscules";
	}

	@Override
	public String buscar() {
		return "Rechercher";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Recherche en cours";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Aucun résultat trouvé";
	}

	@Override
	public String errorBusqueda() {
		return "Erreur lors de la recherche";
	}

	@Override
	public String omitirYCerrar() {
		return "Ignorer et fermer";
	}

	@Override
	public String guardarYCerrar() {
		return "Enregistrer et fermer";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Collez les journaux ici";
	}

	@Override
	public String archivo() {
		return "Fichier";
	}

	@Override
	public String incluir() {
		return "Inclure";
	}

	@Override
	public String abrir() {
		return "Ouvrir";
	}

	@Override
	public String endpointDeInforme() {
		return "Point de terminaison du rapport";
	}

	@Override
	public String sitoDeLogging() {
		return "Site de journalisation :";
	}

	@Override
	public String apiDeLogging() {
		return "API de journalisation :";
	}

	@Override
	public String anonimizarRegistros() {
		return "Anonymiser les journaux (bêta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Partager le rapport et tous les journaux sélectionnés";
	}

	@Override
	public String arco() {
		return "Cette boîte de dialogue vous permet de partager des journaux en utilisant l’API SecureLogger "
				+ "sur <a href=\\\"https://securelogger.net\\\">securelogger.net</a>. En cliquant sur le bouton de partage du rapport, "
				+ "votre rapport est envoyé vers le point de terminaison sélectionné (par défaut asbestosstar.egoism.jp) "
				+ "(modifiable en bas). Vous pouvez partager tous les journaux sélectionnés avec le rapport. "
				+ "Si vous ne souhaitez pas téléverser de données, n’utilisez pas cette fonctionnalité. "
				+ "Nous ne traitons pas votre rapport sur le point de terminaison officiel "
				+ "(<a href=\\\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\\\">lien</a>) ; "
				+ "nous supprimons uniquement les liens non autorisés. Le code source est disponible ici : "
				+ "<a href=\\\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\\\">code source</a>. "
				+ "Cela sert uniquement à afficher des informations sur votre erreur et le lien vers les journaux. "
				+ "Cependant, vous pouvez utiliser un point de terminaison personnalisé qui peut ne pas appliquer les mêmes règles. "
				+ "Vous utilisez actuellement le site de rapports " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " et le site de journaux " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Vous pouvez également partager des journaux individuels sans rapport via les boutons correspondants. "
				+ "CrashDetector propose une anonymisation automatique des journaux, supprimant noms d’utilisateur, UUID, "
				+ "tokens, identifiants de session, adresses IP et autres données sensibles. "
				+ "Cependant, cette anonymisation n’est pas parfaite et peut être désactivée. "
				+ "Vous gardez le contrôle total de vos données et choisissez où les envoyer. "
				+ "Les sites de journalisation sont des services tiers dont la propriété peut être inconnue. "
				+ "Vous assumez la responsabilité des risques liés au partage de vos données. "
				+ "Cette interface vous aide simplement à gérer cela. "
				+ "Il est important de connaître les réglementations comme le RGPD. "
				+ "Si vous êtes en Europe, vous pouvez utiliser "
				+ "<a href=\\\"https://securelogger.top\\\">securelogger.top</a> (hébergé en Allemagne). "
				+ "Pour plus d’informations juridiques : "
				+ "<a href=\\\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\\\">LFPDPPP</a>, "
				+ "<a href=\\\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\\\">RGPD</a>, "
				+ "<a href=\\\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\\\">Politique japonaise de protection des données</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "Lien du rapport :";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Enregistrer la configuration de partage";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "Le journal (log) est trop volumineux pour ce site. Veuillez choisir un autre site et réessayer.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Erreur lors de la publication du journal : " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "L'API du journal n'existe pas. Veuillez changer l'API du journal dans la configuration.";
	}

	@Override
	public String errorSSL() {
		return "Vous avez une erreur SSL. C'est courant avec les anciennes versions de Java, "
				+ "y compris les versions Java 8 du lanceur Minecraft par défaut "
				+ "et celles de sun.com ou java.com. Cela affecte plusieurs fonctions, "
				+ "comme les fichiers JAR de l'installateur MinecraftForge, la fonction de partage de rapports "
				+ "de CrashDetector (avec le point de terminaison par défaut), certains mods nécessitant Internet "
				+ "et certains sites de journaux. Si cela se produit lors du partage d'un rapport, "
				+ "veuillez joindre une capture d'écran et sélectionner un site de journaux compatible "
				+ "avec les anciennes versions de Java 8.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Incompatibilité JavaFML : Version requise "
				+ requerido + ", version détectée " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Attention ! JavaFML nécessite une version spécifique de Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Le fichier JAR '" + archivoJar
				+ "' nécessite le fournisseur de langage '" + proveedor + "' version '" + requerido
				+ "' ou supérieure, mais seule la version '" + encontrado + "' a été trouvée.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ALERTE ! Crash Assistant est un faux détecteur de malware. Il bloque intentionnellement le lancement du jeu, ignorant votre liberté de jouer avec les mods qu'il cible. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Voir le code MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Voir le code JarInJarHelper.java</a>. "
				+ "Seul ce mod est dans sa liste pour le moment, et ils ciblent principalement le site de journal par défaut, qui peut être changé par l'utilisateur. Cela ne se produit que si vous choisissez explicitement d'utiliser la fonction de partage intégrée. CrashAssistant ne vérifie pas quel site de journal est utilisé ni n'explique comment le changer (il y a un menu déroulant en bas de la boîte de dialogue de partage), et quel que soit le site configuré, CrashAssistant bloquera le lancement du jeu. Leur message vous demande de faire vos propres recherches : FAITES-LE. Examinez le code de CrashDetector et Crash Assistant pour comprendre ce qu'ils font. NE vous fiez pas à un argument d'autorité.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String claseNoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Le mod '" + idMod
				+ "' a échoué car la classe requise est introuvable : '" + claseNoEncontrada
				+ "'. Assurez-vous que toutes les dépendances sont correctement installées.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Watermedia a bloqué le jeu avec TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Vous utilisez une version d'Optifine pour une version obsolète de Minecraft. Vous devez utiliser la version d'Optifine correspondant à la version de Minecraft que vous utilisez.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Échec du chargement du service ModLauncher : </b>" + servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String archivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erreur lors de l'analyse du fichier JSON '"
				+ recurso + "' dans le fichier JAR '" + archivoJar + "'. Problème avec le journal.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erreur : Le mod '" + modId
				+ "' nécessite la version '" + requerido + "' ou supérieure de '" + dependencia + "', mais la version '"
				+ actual + "' a été trouvée." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Votre GPU ne prend pas en charge la version d'OpenGL requise par cette version du jeu. Mettez à jour vos pilotes ou changez de carte graphique.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Augmentez la mémoire allouée au jeu ou réduisez la consommation des mods/plugins.</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "JVM 32 bits détectée : Impossible d'utiliser plus de 4 Go de RAM. "
				+ "Installez une JVM 64 bits pour profiter de toute votre mémoire disponible.</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur critique de mémoire PermGen. Augmentez l'espace de mémoire permanente ou réduisez le chargement des classes.</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur de compatibilité entre Java 8 et les versions modernes.</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ non compatible - Utilisez Java 8 pour les anciennes versions de Forge.</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 requis (version 52.0). Mettez à jour ou configurez correctement.</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur critique de compatibilité : Blocs non pris en charge dans cette version. "
				+ "Vérifiez que les versions des mods et du serveur sont compatibles.</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erreur de configuration des moniteurs : "
				+ "Impossible d'établir le mode d'affichage. " + "Vérifiez :</b>"
				+ "<br>- La configuration multi-écrans" + "<br>- Les pilotes de la carte graphique à jour"
				+ "<br>- La résolution prise en charge par le système";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erreur dans les options Java : "
				+ "Options de ramasse-miettes (GC) conflictuelles. "
				+ "Vérifiez que vous ne combinez pas plusieurs algorithmes GC dans les paramètres JVM.</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur critique de configuration NightConfig/Forge : "
				+ "Fichier de configuration corrompu ou incomplet. "
				+ "Cela peut être causé par des fichiers de configuration vides (souvent de 0 octets) dans le dossier 'config' sur les anciennes versions ou les versions personnalisées de NightConfig. "
				+ "Pour la plupart des versions, 'Night Config Fixes' résoudra le problème, mais si vous utilisez une version non prise en charge ou personnalisée de NightConfig, vous devrez supprimer les fichiers de configuration manuellement. "
				+ "Ce problème est plus courant sur les anciennes versions de MC Forge (qui inclut NightConfig) et sur les anciens mods FabricMC qui embarquent NightConfig, mais il peut aussi exister dans certaines versions personnalisées de NightConfig. "
				+ "Plus d'informations sur les solutions sont disponibles sur <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur de pilote Intel HD Graphics détectée. Solutions :</b>"
				+ "<br>1. Mettez à jour les pilotes Intel depuis <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (version minimale 15.xx.xx.xx)"
				+ "<br>2. Dans Minecraft : Options → Vidéo → Activez 'Enable VBOs' et 'VSync'"
				+ "<br>3. Allouez 1 Go à 2 Go de RAM au jeu dans le lanceur"
				+ "<br>4. Désactivez temporairement l'antivirus/pare-feu pendant la mise à jour";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Avertissement : Classes manquantes détectées";
	}

	public String nombre_de_bloque_teselado() {
		return "Erreur de rendu de blocs";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Analyse de la pile d'exécution (stack trace)";
	}

	public String nombre_de_cursed_consola() {
		return "Console CurseForge incomplète";
	}

	public String nombre_de_early_window() {
		return "Erreur de fenêtre initiale (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Problèmes de pilotes graphiques";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Configuration MCForge corrompue";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Échec du mode d'affichage (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Erreur d'initialisation de FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Modules JPMS manquants";
	}

	public String nombre_de_faltar_de_clases() {
		return "Classes critiques manquantes";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Dépendances ModLauncher manquantes";
	}

	public String nombre_de_java_versiones() {
		return "Versions de Java incompatibles";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Erreur de ressources KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Fournisseur de langage incompatible";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Lithostitched manquant";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Faux positif malware détecté";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Mod suspect détecté";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Mods en double dans ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Conflits de modules JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Sodium requis pour Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Erreur d'analyse du JSON du journal";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Mémoire insuffisante";
	}

	public String nombre_de_null_pointer() {
		return "Erreur de pointeur nul (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Options GC Java invalides";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine obsolète/incompatible";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick serveur critique (60s)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Échec des services ModLauncher";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Configurations SpongeMixin problématiques";
	}

	public String nombre_de_theseus() {
		return "Theseus non compatible";
	}

	public String nombre_de_watermedia_tl() {
		return "Lanceur TLauncher non pris en charge par WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "Audits Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Voici les résultats des audits Transformer dans le Lanceur Vanille. C'est généralement moins précis que l'analyseur de StackTrace, mais le Lanceur Vanille n'a pas toujours le contenu de {}</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Ceci recherche les mods créés avec MCreator. Bien que la plupart des mods MCreator soient corrects et qu'il existe many excellents mods faits avec MCreator, ils ont parfois des problèmes et une mauvaise réputation. Cela aide à les identifier. Notez que même les mieux notés peuvent ne pas être réellement faits avec MCreator ; par exemple, ils peuvent avoir une intégration avec MCreator.";
	}

	@Override
	public String escanear() {
		return "Scanner";
	}

	@Override
	public String cargando() {
		return "Chargement";
	}

	@Override
	public String inicioApp() {
		return "Démarrage du Jeu/App";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Paramètres CrashDetector";
	}

	@Override
	public String confidencialidad() {
		return "Confidentialité";
	}

	@Override
	public String tooltip() {
		return "Info-bulle";
	}

	@Override
	public String config() {
		return "Configuration";
	}

	@Override
	public String abrirCarpeta() {
		return "Ouvrir le dossier";
	}

	@Override
	public String actualizar() {
		return "Actualiser";
	}

	@Override
	public String anadirRegistro() {
		return "Ajouter un journal";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Utiliser la langue du système";
	}

	@Override
	public String volver() {
		return "Retour";
	}

	@Override
	public String colorFondo() {
		return "Couleur de fond (#RRGGBB) :";
	}

	@Override
	public String colorTexto() {
		return "Couleur du texte (#RRGGBB) :";
	}

	@Override
	public String colorBoton() {
		return "Couleur du bouton (#RRGGBB) :";
	}

	@Override
	public String colorCajaTexto() {
		return "Couleur de la zone de texte (#RRGGBB) :";
	}

	@Override
	public String colorEnlace() {
		return "Couleur du lien (#RRGGBB) :";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Couleur des titres des consoles (#RRGGBB) :";
	}

	@Override
	public String colorError() {
		return "Couleur d'erreur (#RRGGBB) :";
	}

	@Override
	public String colorAdvertencia() {
		return "Couleur d'avertissement (#RRGGBB) :";
	}

	@Override
	public String colorInfo() {
		return "Couleur d'information (#RRGGBB) :";
	}

	@Override
	public String colorTitulo() {
		return "Couleur du titre (#RRGGBB) :";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Couleur du texte du lien (#RRGGBB) :";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Ouvrir CrashDetector uniquement en cas d'échec";
	}

	@Override
	public String activar_parche() {
		return "Activer le correctif";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Aucune solution disponible";
	}

	@Override
	public String error() {
		return "Erreur";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Erreur lors de la suppression du Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "Jar supprimé avec succès";
	}

	@Override
	public String exito() {
		return "Succès";
	}

	@Override
	public String eliminar() {
		return "Supprimer";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Erreur lors de la suppression du package";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Package supprimé avec succès";
	}

	@Override
	public String eliminar_paquete() {
		return "Supprimer le package";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Aucun mod trouvé avec ce package";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Impossible de supprimer le package";
	}

	@Override
	public String eliminar_jar() {
		return "Supprimer le jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Aucun mod en double trouvé";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Fichier introuvable";
	}

	@Override
	public String directorio_eliminado() {
		return "Répertoire supprimé";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Erreur lors de la suppression du jar imbriqué";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Fichier interne introuvable";
	}

	@Override
	public String archivo_eliminado() {
		return "Fichier supprimé";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "Erreur lors de la suppression du fichier";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "Fichier externe invalide";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Élément du mod supprimé";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Erreur lors du remplacement du jar externe";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "Erreur lors de la suppression de l'élément du mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "Erreur lors de la suppression du répertoire";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "Format invalide pour le jar imbriqué";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "Jar imbriqué supprimé";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "Erreur lors du nettoyage des fichiers temporaires";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Dernier message de trace fatale (Non traduit) :</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Dernier message de trace (Non traduit) :</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Vous pouvez rechercher dans la base de données WaifuNeoForge pour trouver des mods. Sélectionnez la version du jeu, le chargeur de mods et la classe. Utilisez la combinaison la plus similaire. Vous pouvez effectuer une recherche une fois par minute.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Vous pouvez rechercher dans la base de données WaifuNeoForge pour trouver des mods. Sélectionnez la version du jeu, le chargeur de mods et la classe. Utilisez la combinaison la plus similaire. Vous pouvez effectuer une recherche une fois par minute.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Les deux lanceurs disposent des versions Java correctes, mais pas de toutes. Vous pouvez installer la version Java appropriée via le gestionnaire de packages de votre système ou avec les boutons fournis.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod avec animation manquante : " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (Élément manquant) Animation manquante";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Aucun mod trouvé à supprimer";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Remplacer les options GC conflictuelles par -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Augmenter la taille de la mémoire heap en utilisant l'option -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Augmenter la taille de la mémoire permgen en utilisant l'option -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Utiliser une JVM 64 bits pour augmenter la mémoire disponible.";
	}

	@Override
	public String optimizarCodigo() {
		return "Optimiser le code pour réduire l'utilisation de la mémoire et améliorer les performances.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Utiliser un ramasse-miettes efficace pour réduire les pauses de l'application.";
	}

	@Override
	public String modulos() {
		return "Modules";
	}

	@Override
	public String paquete() {
		return "Package";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Il manque des IDs. Les causes courantes sont des mods manquants ou des données d'objets manquantes. Les dossiers de données communs sont datafiedcontents/ et kubejs/ ou d'autres dossiers de mods.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "Journaux mal mappés";
	}

	/**
	 * Devuelve el mensaje de error para el cierre causado por AuthMe.
	 */
	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Le plugin 'AuthMe' n'a pas pu se charger et a arrêté le serveur.</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	public String nombreProblemaCierreAuthMe() {
		return "Problème d'arrêt par AuthMe";
	}

	/**
	 * Devuelve la solución de cierre del servidor por AuthMe.
	 */
	public String solucionCierreAuthMe() {
		return "La règle 'stopServer' a été changée en 'true'.";
	}

	/**
	 * Devuelve la solución de configuración del plugin AuthMe.
	 */
	public String solucionConfigurarPluginAuthMe() {
		return "Configurer le plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	/**
	 * Devuelve la solución de instalar otra versión del plugin AuthMe.
	 */
	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Installer une version différente du plugin 'AuthMe'";
	}

	/**
	 * Devuelve la solución de eliminar el plugin AuthMe.
	 */
	public String solucionEliminarPluginAuthMe() {
		return "Supprimer le plugin 'AuthMe'";
	}

	/**
	 * Devuelve el mensaje de error para mundos corruptos por Multiverse.
	 */
	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le monde '" + nombreMundo
				+ "' n'a pas pu être chargé car il contient des erreurs et est probablement corrompu.</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Problème de chargement Multiverse";
	}

	/**
	 * Devuelve la solución de reparar el mundo.
	 */
	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Réparez le monde '" + nombreMundo + "', par exemple en utilisant Minecraft Region Fixer ou MCEdit.";
	}

	/**
	 * Devuelve la solución de eliminar la carpeta del mundo.
	 */
	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Supprimez le dossier du monde '" + nombreMundo + "'.";
	}

	/**
	 * Devuelve el mensaje de error para configuración inválida de PermissionsEx.
	 */
	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La configuration du plugin 'PermissionsEx' est invalide.</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Problème de configuration PermissionsEx";
	}

	/**
	 * Devuelve la solución de configurar PermissionsEx.
	 */
	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Configurer le plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	/**
	 * Devuelve la solución de eliminar el plugin PermissionsEx.
	 */
	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Supprimer le plugin 'PermissionsEx'";
	}

	/**
	 * Devuelve el mensaje de error para plugins con nombre ambiguo.
	 */
	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Il existe plusieurs fichiers de plugins pour le plugin nommé '" + nombrePlugin + "': '"
				+ primerPath + "' et '" + segundoPath + "'.</b> ";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Problème de nom de plugin ambigu";
	}

	/**
	 * Devuelve la solución de eliminar un plugin específico.
	 */
	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Supprimer le plugin '" + nombrePlugin + "'";
	}

	/**
	 * Devuelve el mensaje de error para excepciones al cargar chunks.
	 */
	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Une exception s'est produite pendant que le monde chargeait les chunks. Si disponible pour votre plateforme, FeatureRecycler pourrait résoudre le problème. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	/**
	 * Devuelve el nombre del problema para mostrar en la interfaz.
	 */
	@Override
	public String nombreProblemaCargaChunk() {
		return "Exception lors du chargement des chunks";
	}

	/**
	 * Devuelve la solución de eliminar el chunk problemático.
	 */
	@Override
	public String solucionEliminarChunk() {
		return "Supprimez le chunk problématique du monde, par exemple avec MCEdit ou en supprimant le fichier de région.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' ne peut pas exécuter la commande '/" + comando + "'.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Erreur lors de l’exécution d’une commande du plugin";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Installe une autre version du plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' a besoin du plugin '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' a besoin des plugins suivants : " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Plugin manquant";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Installe le plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' demande la version API '" + versionAPI + "' qui n’est pas compatible avec ton serveur.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Version API non compatible";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Installe la version '" + version + "' du serveur.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le monde '" + nombreMundo
				+ "' est en double et ne peut pas être chargé.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Monde en double";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Supprime le fichier 'uid.dat' dans le monde '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Supprime le dossier du monde '" + nombreMundo + "'";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		String color = config.obtenerColorError();

		String mensajeBase = "<span style='color:#" + color + "'>L’entité '" + nombre + "' de type '" + tipo
				+ "' à la position " + coords + " provoque une erreur (ticking).</span><br><br>";

		String instrucciones = "<span style='color:#" + color + "'>" + "Solution :<br>"
				+ "1. **MCForge** : va dans '[nom_du_monde]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge** : va dans 'config/neoforge-server.toml'.<br>"
				+ "   (Les mondes solo sont dans le dossier 'saves')<br>"
				+ "3. Mets **removeErroringBlockEntities** et **removeErroringEntities** à **true**.<br><br>"
				+ "Autres options :<br>" + "- Utilise **MCEdit** pour supprimer l’entité à ces coordonnées.<br>"
				+ "- Le mod **Neruina** peut aider mais ne règle pas toujours le problème." + "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Entité problématique";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Supprime l’entité '" + nombre + "' à la position " + coords;
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' est installé plusieurs fois.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod en double (Fabric)";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Supprime le fichier en double : " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Les mods '" + primerMod + "' et '" + segundoMod
				+ "' ne sont pas compatibles.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mods incompatibles";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Supprime le mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' a une erreur grave et ne peut pas fonctionner.</b> ";
	}

	/**
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaModFatal() {
		return "Mod avec erreur grave";
	}

	/**
	 * Donne le message d’erreur pour des dépendances de mods manquantes (pluriel).
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
				+ "'>Les mods suivants sont nécessaires mais ne sont pas installés : " + deps.toString() + ".</b>";
	}

	/**
	 * Donne le message d’erreur pour une dépendance de mod manquante (singulier).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod + "' a besoin du mod '"
					+ dependencia + "'.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod + "' a besoin du mod '"
					+ dependencia + "' en version " + version + ".</b>";
		}
	}

	/**
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaDependenciaMod() {
		return "Dépendance de mod manquante";
	}

	/**
	 * Donne la solution pour installer un mod précis.
	 */
	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Installe le mod '" + nombreMod + "'";
	}

	/**
	 * Donne la solution pour installer un mod avec une version précise.
	 */
	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Installe le mod '" + nombreMod + "' en version " + version;
	}

	/**
	 * Donne le message d’erreur pour un plugin qui ne supporte pas le ticking
	 * régional (singulier).
	 */
	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' n’est pas compatible avec le ticking régional de Folia.</b> ";
	}

	/**
	 * Donne le message d’erreur pour plusieurs plugins qui ne supportent pas le
	 * ticking régional (pluriel).
	 */
	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les plugins suivants ne sont pas compatibles avec le ticking régional de Folia : ");

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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plugin non compatible avec le ticking régional";
	}

	/**
	 * Donne la solution d’installer un logiciel sans ticking régional.
	 */
	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Installe un logiciel sans ticking régional, comme " + software;
	}

	/**
	 * Donne le message d’erreur pour un mod manquant dans le datapack (singulier).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' manque dans le datapack.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs mods manquants dans le datapack
	 * (pluriel).
	 */
	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les mods suivants manquent dans le datapack : ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" et ");
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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Mod manquant dans le datapack";
	}

	/**
	 * Donne le message d’erreur pour un mod qui a généré une exception (singulier).
	 */
	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' a généré une erreur.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs mods qui ont généré des exceptions
	 * (pluriel).
	 */
	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les mods suivants ont généré des erreurs : ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" et ");
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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaModExcepcion() {
		return "Mod Forge avec erreur";
	}

	/**
	 * Donne la solution d’installer une autre version du mod.
	 */
	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Installe une autre version du mod '" + nombreMod + "'";
	}

	/**
	 * Donne le message d’erreur pour un mod incompatible avec la version de
	 * Minecraft (singulier).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' n’est pas compatible avec la version de Minecraft " + versionMC + ".</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs mods incompatibles avec Minecraft
	 * (pluriel).
	 */
	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les mods suivants ne sont pas compatibles avec les versions de Minecraft : ");

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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Mod non compatible avec Minecraft";
	}

	/**
	 * Donne la solution d’installer une version différente de Forge.
	 */
	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Installe une version de Forge compatible avec Minecraft " + versionMC;
	}

	/**
	 * Donne le message d’erreur pour un mod manquant (singulier).
	 */
	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le mod '" + nombreMod
				+ "' manque et il est nécessaire pour charger le monde ou le plugin.</b>";
	}

	/**
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mod manquant";
	}

	/**
	 * Donne le message d’erreur pour un mod manquant dans le monde (singulier).
	 */
	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le monde a été enregistré avec le mod '"
				+ nombreMod + "' qui est absent.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs mods manquants dans le monde
	 * (pluriel).
	 */
	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Le monde a été enregistré avec les mods suivants qui sont absents : ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" et ");
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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaWorldModFaltante() {
		return "Mod manquant dans le monde";
	}

	/**
	 * Donne le message d’erreur pour une différence de version de mod (singulier).
	 */
	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le monde a été enregistré avec le mod '"
				+ nombreMod + "' en version " + versionEsperada + ", mais la version actuelle est " + versionActual
				+ ".</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs différences de version de mods
	 * (pluriel).
	 */
	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les mods suivants ont des différences de version dans le monde enregistré : ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" et ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Enregistré : ").append(versionesEsperadas.get(i)).append(", Actuel : ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	/**
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaVersionModMundo() {
		return "Version de mod différente dans le monde";
	}

	/**
	 * Donne le message d’erreur pour un monde ouvert avec une version trop
	 * ancienne.
	 */
	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Tu essaies d’ouvrir un monde créé avec une version plus récente de Minecraft.</b>";
	}

	/**
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Version de Minecraft trop ancienne";
	}

	/**
	 * Donne la solution d’installer une version plus récente de Minecraft.
	 */
	@Override
	public String solucionVersionDowngrade() {
		return "Installe une version plus récente de Minecraft.";
	}

	/**
	 * Donne la solution de créer un nouveau monde.
	 */
	@Override
	public String solucionGenerarNuevoMundo() {
		return "Crée un nouveau monde.";
	}

	/**
	 * Donne le message d’erreur pour un plugin avec dépendance manquante
	 * (singulier).
	 */
	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' a besoin du plugin '" + dependencia + "'.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs plugins avec dépendances manquantes
	 * (pluriel).
	 */
	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les plugins suivants ont des dépendances manquantes : ");

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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Plugin avec dépendance manquante";
	}

	/**
	 * Donne le message d’erreur pour un plugin incompatible (singulier).
	 */
	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' n’est pas compatible avec la version actuelle du serveur.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs plugins incompatibles (pluriel).
	 */
	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les plugins suivants ne sont pas compatibles avec la version actuelle du serveur : ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" et ");
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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin non compatible avec PocketMine-MP";
	}

	/**
	 * Donne le message d’erreur pour un plugin avec erreur d’exécution (singulier).
	 */
	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Le plugin '" + nombrePlugin
				+ "' a généré une erreur pendant l’exécution.</b>";
	}

	/**
	 * Donne le message d’erreur pour plusieurs plugins avec erreurs d’exécution
	 * (pluriel).
	 */
	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Les plugins suivants ont généré des erreurs pendant l’exécution : ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" et ");
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
	 * Donne le nom du problème à afficher dans l’interface.
	 */
	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin avec erreur d’exécution";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource multi-threads";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Il y a un problème avec plusieurs threads qui accèdent à la classe LegacyRandomSource. Tu peux avoir plus d’informations avec le mod Unsafe World Random Access Detector ou le mod C2ME.</b> ";
	}

	@Override
	public String desdeUltimoExito() {
		return "Depuis le dernier succès";
	}

	@Override
	public String noHayCambios() {
		return "Aucun changement";
	}

	@Override
	public String desdeUltimoIntento() {
		return "Depuis la dernière tentative";
	}

	@Override
	public String fallo() {
		return "Échec";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Différents des mods";
	}

	@Override
	public String historialDeMods() {
		return "Historique des mods";
	}

	@Override
	public String archivo0() {
		return "Fichier0";
	}

	@Override
	public String archivo1() {
		return "Fichier1";
	}

	@Override
	public String comparar() {
		return "Comparer";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "Sélectionner deux fichiers";
	}

	@Override
	public String archivoExito() {
		return "Fichier réussite";
	}

	@Override
	public String archivoFalla() {
		return "Fichier échec";
	}

	@Override
	public String errorComparandoArchivos() {
		return "Erreur lors de la comparaison des fichiers";
	}

	@Override
	public String comparando() {
		return "Comparaison";
	}

	@Override
	public String con() {
		return "Avec";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Panneau de comparaison de l’historique des mods</b></p>"
				+ "<p>Ce panneau vous permet de comparer deux listes de mods de différentes sessions d’exécution. "
				+ "Sélectionnez un fichier dans la colonne de gauche et un autre dans la colonne de droite pour voir les changements entre les deux versions.</p>"

				+ "<h3>Comment l’utiliser :</h3>" + "<ol>"
				+ "<li><b>Sélection des fichiers :</b> Cliquez sur les boutons radio à côté des noms des fichiers. "
				+ "Les fichiers qui se terminent par <span style='color: #4CAF50; font-weight: bold;'>.succes</span> indiquent des sessions réussies, "
				+ "tandis que ceux qui se terminent par <span style='color: #F44336; font-weight: bold;'>.echec</span> correspondent à des échecs.</li>"

				+ "<li><b>Comparaison automatique :</b> Quand vous appuyez sur le bouton 'Comparer', le système analyse les deux listes choisies "
				+ "et affiche les mods ajoutés (+) ou supprimés (-) dans chaque sens.</li>"

				+ "<li><b>Affichage des résultats :</b> Les résultats sont présentés en HTML avec des couleurs : "
				+ "<ul>" + "<li><span style='color: green;'>+ Mod ajouté</span></li>"
				+ "<li><span style='color: red;'>- Mod supprimé</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Fonctions principales :</h3>" + "<ul>"
				+ "<li>Prend en charge toutes les combinaisons de fichiers (succès/échec).</li>"
				+ "<li>Affiche les différences dans les deux sens pour repérer les changements exacts.</li>"
				+ "<li>Inclut le défilement pour parcourir de longues listes de mods.</li>"
				+ "<li>Intègre des images explicatives pour faciliter la compréhension visuelle.</li>" + "</ul>"

				+ "<p>Développé avec soin pour vous aider à suivre les modifications de vos configurations.</p>"
				+ "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Il est possible que vous ayez un problème lié à IPv6. " + "Il existe deux solutions : "
				+ "1) Ajouter l’argument JVM <code>-Djava.net.preferIPv4Stack=true</code> dans votre lanceur, ou "
				+ "2) Utiliser le bouton 'QuickFix' dans CrashDetector pour appliquer automatiquement ce réglage."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Correctif IPv4/IPv6";
	}

	@Override
	public String carpetaHMCL() {
		return "Dossier HMCL (seulement pour HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Note : aucun journal n’est généré si l’option \\\"Passer le launcher\\\" est activée dans Paramètres > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/derivés : faites un clic droit sur l’instance puis choisissez \\\"Modifier l’instance\\\". Dans la fenêtre qui s’ouvre, cherchez la section \\\"Journaux Minecraft\\\" ou une section similaire.<br>"
				+ "Ces journaux contiennent la sortie standard (STDOUT), qui est très importante pour diagnostiquer les erreurs.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher) : vous devez sélectionner le dossier où HMCL est installé et choisir le dossier \\\".hmcl\\\". Les journaux de HMCL sont enregistrés à cet endroit et contiennent des informations importantes sur les erreurs.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix : le lanceur possède un onglet de développement qui affiche des journaux détaillés. Vous pouvez trouver cet onglet dans le menu des options du lanceur.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher : il y a une fenêtre qui affiche les journaux. Elle contient des boutons pour copier et envoyer les journaux. Les journaux sont générés automatiquement au démarrage du jeu et contiennent des informations importantes pour le diagnostic.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher : faites un clic droit sur l’instance puis choisissez \\\"Configuration\\\". Ensuite, allez dans la section \\\"Journaux\\\" pour voir les informations importantes contenues dans la sortie standard (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Liens Markdown : collez ici tout lien contenant des journaux au format Markdown. Le système essaiera d’extraire automatiquement les liens vers les journaux (latest.log, launcher_log.txt, debug.log, etc.) et de les analyser.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Journal du launcher introuvable";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Image non trouvée";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "GÉNÉRIQUE : sélectionnez le type de launcher que vous utilisez. Les journaux du launcher (launcher_log.txt, stdout, etc.) contiennent des informations importantes sur des erreurs qui n’apparaissent pas dans latest.log. CrashDetector ne peut pas lire les journaux de votre launcher ; il est possible qu’il n’y ait pas de fichier journal et que vous deviez coller les journaux manuellement.<br>"
				+ "Pour plus d’informations, consultez <a href=\\\"https://github.com/HMCL-dev/HMCL/issues/2663 \\\">ce problème</a>. Ces journaux contiennent la sortie standard (STDOUT), nécessaire pour diagnostiquer beaucoup de types d’erreurs.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Il manque des classes de Create. Create a beaucoup changé : de nombreuses classes ont été supprimées. Surtout depuis Create 6 (février 2025), les addons pour les anciennes versions ne fonctionnent plus. QuickFix ne propose pas de solution pour ce problème. Vous devez mettre à jour les addons de Create, supprimer ceux qui sont obsolètes ou utiliser la version correcte de Create pour les addons que vous souhaitez utiliser.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Il manque des classes d’EpicFight. EpicFight a beaucoup changé : de nombreuses classes ont été supprimées. QuickFix ne propose pas de solution pour ce problème. Vous devez mettre à jour les addons d’EpicFight, supprimer ceux qui sont obsolètes ou utiliser la version correcte d’EpicFight pour les addons que vous souhaitez utiliser.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous utilisez OpenJ9, qui n’est pas compatible avec cette application. De nombreuses applications, y compris celle-ci, ne supportent pas OpenJ9 en raison de différences dans l’implémentation de la JVM. QuickFix ne peut pas résoudre ce problème automatiquement. Vous devez installer un JDK compatible comme Oracle JDK, OpenJDK HotSpot ou d’autres alternatives recommandées.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Cette version de l’application nécessite Java 11 (JDK 11) pour fonctionner correctement. Vous utilisez une version de Java non compatible. QuickFix ne peut pas mettre à jour Java automatiquement. Vous devez télécharger et installer JDK 11 ou une version supérieure compatible.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez alloué trop de mémoire, ce qui empêche le système de fonctionner correctement. Cela se produit généralement lorsque la quantité de RAM dépasse celle disponible sur votre système ou lorsque vous utilisez une JVM 32 bits incapable de gérer de grandes quantités de mémoire.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Pour résoudre ce problème, réduisez la mémoire allouée à l’application. En général, elle ne doit pas dépasser 70 à 80 % de votre RAM totale. Si vous utilisez une JVM 32 bits, la limite est d’environ 2 à 3 Go.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Pour réduire la mémoire heap, ouvrez les paramètres de votre launcher et ajustez la valeur Xmx. Par exemple : 3–4 Go pour 8 Go de RAM, 6–8 Go pour 16 Go de RAM. Laissez toujours suffisamment de mémoire pour le système et les autres applications.</b>";
	}

	@Override
	public String forgeArchivosFaltantes(String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Des fichiers essentiels de Forge sont manquants. Le fichier '" + archivo
				+ "' est introuvable dans votre installation. Cela se produit généralement lorsqu’une installation a été interrompue. Vous devez réinstaller Forge correctement depuis le site officiel.</b>";
	}

	@Override
	public String forgeVersionNoEncontrada(String version, String archivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Forge ne peut pas trouver la version de Minecraft requise. La version " + version
				+ " est nécessaire mais introuvable dans le fichier '" + archivo
				+ "'. Vérifiez que la version de Forge correspond à votre version de Minecraft.</b>";
	}

	@Override
	public String forgeTargetFmlclientNoEncontrado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Le target 'fmlclient' est introuvable. L’installation de Forge est probablement incomplète ou corrompue. Réinstallez Forge en utilisant l’installateur officiel.</b>";
	}

	@Override
	public String forgeClaseMinecraftFaltante() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La classe principale de Minecraft est introuvable dans le classloader. Cela indique une installation incomplète ou un conflit de mods. Réinstallez Forge correctement.</b>";
	}

	@Override
	public String forgeInstallacionNoCompleta() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>L’installation de Forge est incomplète. Cela peut être dû à une interruption, à des fichiers supprimés ou à une incompatibilité avec votre version de Minecraft. Des fichiers nécessaires sont manquants.</b>";
	}

	@Override
	public String nombre_de_forge_instalacion_no_completa() {
		return "Installation de Forge incomplète";
	}

	@Override
	public String solucion_para_forge_instalacion_no_completa() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Pour résoudre ce problème, réinstallez Forge correctement en téléchargeant la version adaptée à votre version de Minecraft et en suivant toutes les étapes sans interruption.</b>";
	}

	@Override
	public String descargar_forge_oficial() {
		return "Télécharger Forge officiellement";
	}

	@Override
	public String reinstalar_forge_correctamente() {
		return "Comment réinstaller Forge correctement";
	}

	@Override
	public String instrucciones_reinstalar_forge() {
		return "<html><body style='width: 500px;'>" + "<h3 style='color:#" + config.obtenerColorTitulo()
				+ "'>Instructions pour réinstaller Forge :</h3>" + "<ol>"
				+ "<li>Téléchargez l’installateur correct de Forge depuis le site officiel (version recommandée pour votre version de Minecraft)</li>"
				+ "<li>Fermez complètement votre launcher Minecraft</li>"
				+ "<li>Exécutez l’installateur Forge en tant qu’administrateur</li>"
				+ "<li>Sélectionnez l’option 'Installer' (pas 'Installer (run client)')</li>"
				+ "<li>Choisissez le dossier de votre profil Minecraft dans le launcher</li>"
				+ "<li>Cliquez sur 'OK' et attendez la fin de l’installation</li>"
				+ "<li>Redémarrez votre launcher et vérifiez que Forge apparaît dans la liste des profils</li>"
				+ "</ol>"
				+ "<p><b>Remarque importante :</b> Si vous utilisez un launcher personnalisé, assurez-vous de sélectionner le bon dossier de profil.</p>"
				+ "</body></html>";
	}

	@Override
	public String titulo_instrucciones_reinstaler_mcforge() {
		return "Instructions pour réinstaller Forge";
	}

	@Override
	public String error_enlace_insatisfecho(String nombreBiblioteca) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erreur de lien non satisfait : Impossible de charger la bibliothèque " + nombreBiblioteca
				+ ". Solutions possibles :<br/><br/>"
				+ "a) Ajoutez le répertoire contenant la bibliothèque partagée à -Djava.library.path ou -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Ajoutez le fichier JAR contenant la bibliothèque partagée au classpath.<br/><br/>"
				+ "Cette erreur se produit lorsque Minecraft ne trouve pas les fichiers essentiels à son fonctionnement. "
				+ "Cela est généralement dû à une installation incomplète de Minecraft ou à des problèmes de permissions système.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Erreur de lien non satisfait";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Impossible de charger une bibliothèque. Solutions possibles :<br/><br/>"
				+ "a) Ajoutez le répertoire contenant la bibliothèque partagée à -Djava.library.path ou -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Ajoutez le fichier JAR contenant la bibliothèque partagée au classpath.<br/><br/>"
				+ "Ces solutions techniques sont destinées aux utilisateurs avancés. La plupart des utilisateurs devraient essayer "
				+ "de réinstaller Minecraft avant de modifier ces paramètres.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Collision d'IDs : L'ID <strong>" + id
				+ "</strong> est déjà occupé par <strong>" + modOrigen + "</strong> lors de l'ajout de <strong>"
				+ modDestino
				+ "</strong>. Cela se produit lorsque deux mods tentent d'utiliser le même ID pour des éléments différents.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>La plage maximale d'IDs autorisés a été dépassée. Cela se produit lorsque les mods tentent d'enregistrer des blocs ou des objets avec des IDs hors de la plage autorisée par votre version de Minecraft.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Conflit d'IDs";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Pour résoudre ce problème sur Minecraft 1.12.2, installez <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Pour la 1.7.10, utilisez <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Utilisez des outils comme <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> ou <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> pour résoudre les collisions d'IDs.</b>";
	}

	@Override
	public String instalar_justenoughids() {
		return "Installer JustEnoughIDs";
	}

	@Override
	public String instalar_endlessids() {
		return "Installer EndlessIDs";
	}

	@Override
	public String usar_idfix_minus() {
		return "Utiliser IdFix Minus ou IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Utiliser Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Voir la documentation japonaise";
	}

	@Override
	public String escanearDeMCreator() {
		return "Scanner MCreator";
	}

	/**
	 * Obtiene el título de la ventana del árbol de mods.
	 * 
	 * @return Título de la ventana
	 */
	@Override
	public String tituloArbolDeMods() {
		return "Arbre des Modules et Classes";
	}

	/**
	 * Obtiene el texto para la etiqueta de tipo de búsqueda.
	 * 
	 * @return Texto de la etiqueta
	 */
	@Override
	public String tipoBusqueda() {
		return "Type";
	}

	/**
	 * Obtiene el texto para el filtro "Todos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroTodos() {
		return "Tous";
	}

	/**
	 * Obtiene el texto para el filtro "Paquetes".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroPaquetes() {
		return "Packages";
	}

	/**
	 * Obtiene el texto para el filtro "Clases".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroClases() {
		return "Classes";
	}

	/**
	 * Obtiene el texto para el filtro "Métodos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroMetodos() {
		return "Méthodes";
	}

	/**
	 * Obtiene el texto para el filtro "Campos".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroCampos() {
		return "Champs";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Campo".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasCampo() {
		return "Références de Champ";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Método".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasMetodo() {
		return "Références de Méthode";
	}

	/**
	 * Obtiene el texto para el filtro "Referencias de Clase".
	 * 
	 * @return Texto del filtro
	 */
	@Override
	public String filtroReferenciasClase() {
		return "Références de Classe";
	}

	/**
	 * Obtiene el texto para el tooltip del campo de búsqueda.
	 * 
	 * @return Texto del tooltip
	 */
	@Override
	public String tipBuscar() {
		return "Saisissez ici pour rechercher dans l'arbre des mods";
	}

	/**
	 * Obtiene el texto para el botón de búsqueda.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonBuscar() {
		return "Rechercher";
	}

	/**
	 * Obtiene el texto para el botón de resetear árbol.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String botonResetearArbol() {
		return "Réinitialiser l'arbre";
	}

	/**
	 * Obtiene el texto para indicar los mods cargados.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String modsCargados() {
		return "Mods chargés";
	}

	/**
	 * Obtiene el texto para la categoría de clases.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String clases() {
		return "Classes";
	}

	/**
	 * Obtiene el texto para la categoría de métodos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String metodos() {
		return "Méthodes";
	}

	/**
	 * Obtiene el texto para la categoría de campos.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String campos() {
		return "Champs";
	}

	/**
	 * Obtiene el texto para la categoría de referencias.
	 * 
	 * @return Texto de la categoría
	 */
	@Override
	public String referencias() {
		return "Références";
	}

	/**
	 * Obtiene el texto para los resultados de búsqueda.
	 * 
	 * @return Texto de resultados
	 */
	@Override
	public String resultadosBusqueda() {
		return "Résultats de recherche";
	}

	/**
	 * Obtiene el texto para la opción de buscar referencias.
	 * 
	 * @return Texto de la opción
	 */
	@Override
	public String buscarReferencias() {
		return "Rechercher les références";
	}

	/**
	 * Obtiene el texto para las referencias de mod.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMod() {
		return "Références du Mod";
	}

	/**
	 * Obtiene el texto para las referencias de clase.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasClase() {
		return "Références de la Classe";
	}

	/**
	 * Obtiene el texto para las referencias de método.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasMetodo() {
		return "Références de la Méthode";
	}

	/**
	 * Obtiene el texto para las referencias de campo.
	 * 
	 * @return Texto descriptivo
	 */
	@Override
	public String referenciasCampo() {
		return "Références du Champ";
	}

	/**
	 * Obtiene el texto cuando no se encuentran referencias.
	 * 
	 * @return Mensaje de error
	 */
	@Override
	public String noSeEncontraronReferencias() {
		return "Aucune référence trouvée";
	}

	/**
	 * Obtiene el texto para el detalle de mod.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMod() {
		return "Détails du Mod :";
	}

	/**
	 * Obtiene el texto para la ubicación.
	 * 
	 * @return Etiqueta de ubicación
	 */
	@Override
	public String ubicacion() {
		return "Emplacement";
	}

	/**
	 * Obtiene el texto para los nombres.
	 * 
	 * @return Etiqueta de nombres
	 */
	@Override
	public String nombres() {
		return "Noms";
	}

	/**
	 * Obtiene el texto para el módulo.
	 * 
	 * @return Etiqueta de módulo
	 */
	@Override
	public String modulo() {
		return "Module";
	}

	/**
	 * Obtiene el texto para el detalle de clase.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleClase() {
		return "Détails de la Classe :";
	}

	/**
	 * Obtiene el texto para el detalle de método.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleMetodo() {
		return "Détails de la Méthode :";
	}

	/**
	 * Obtiene el texto para el detalle de campo.
	 * 
	 * @return Título de detalle
	 */
	@Override
	public String detalleCampo() {
		return "Détails du Champ :";
	}

	/**
	 * Obtiene el texto para la clase.
	 * 
	 * @return Etiqueta de clase
	 */
	@Override
	public String clase() {
		return "Classe";
	}

	/**
	 * Obtiene el texto para el tipo.
	 * 
	 * @return Etiqueta de tipo
	 */
	@Override
	public String tipo() {
		return "Type";
	}

	/**
	 * Obtiene el texto para las referencias a métodos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasAMetodos() {
		return "Références aux Méthodes :";
	}

	/**
	 * Obtiene el texto para las referencias a campos.
	 * 
	 * @return Etiqueta de referencias
	 */
	@Override
	public String referenciasACampos() {
		return "Références aux Champs :";
	}

	/**
	 * Obtiene el texto para el botón de árbol de mods.
	 * 
	 * @return Texto del botón
	 */
	@Override
	public String arbolDeMods() {
		return "Arbre des Mods";
	}

	/**
	 * Obtiene el texto para método.
	 * 
	 * @return Palabra "Método"
	 */
	@Override
	public String metodo() {
		return "Méthode";
	}

	/**
	 * Obtiene el texto para campo.
	 * 
	 * @return Palabra "Campo"
	 */
	@Override
	public String campo() {
		return "Champ";
	}

	@Override
	public String descompilar() {
		return "Décompiler";
	}

	@Override
	public String exportar() {
		return "Exporter";
	}

	@Override
	public String importar() {
		return "Importer";
	}

	@Override
	public String errorImportar() {
		return "Erreur d'importation";
	}

	@Override
	public String estructuraImportada() {
		return "Structure importée";
	}

	@Override
	public String estructuraExportada() {
		return "Structure exportée";
	}

	@Override
	public String errorExportar() {
		return "Erreur d'exportation";
	}

	@Override
	public String exportando() {
		return "Exportation en cours";
	}

	@Override
	public String exportacionTardara() {
		return "L'exportation prendra du temps";
	}

	@Override
	public String porFavorEspere() {
		return "Veuillez patienter";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Les binaires VLC sont manquants. Vous avez besoin des binaires VLC pour WaterMedia. Veuillez les installer manuellement depuis https://www.videolan.org/vlc/. </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Télécharger VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erreur critique : Le nom du module '"
				+ nombreModulo + "' contient des caractères invalides. La partie '" + parteInvalida
				+ "' n'est pas un identifiant Java valide. Cela se produit lorsqu'un mod utilise des mots réservés Java (comme 'true', 'class') ou des caractères non autorisés dans son nom.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Caractères invalides dans le nom du mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "Le nom du mod '" + nombreModulo + "' est invalide car il contient '" + parteInvalida
				+ "', qui est un mot réservé Java ou un caractère non autorisé. "
				+ "Recherchez dans les logs quel mod correspond à ce nom (généralement le nom du fichier JAR).";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Accédez au dossier du mod et éditez le fichier <b>mods.toml</b> situé dans le dossier <b>/META-INF/</b>. "
				+ "Modifiez la valeur de <b>modId</b> pour n'utiliser que des lettres, des chiffres et des underscores, sans mots réservés Java.";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Exemple de nom valide : 'truemod_shot_enchantment' au lieu de 'true.shot.enchantment'. "
				+ "Rappelez-vous que les noms de mods ne peuvent pas contenir de points, de tirets, ni de mots réservés Java comme 'true', 'false' ou 'class'.";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erreur critique avec le mod : '" + nombreJar
				+ "'. Le champ obligatoire 'mandatory' est manquant dans ses dépendances. Cela se produit lorsque le fichier mods.toml ne spécifie pas si la dépendance est obligatoire.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dépendance de mod avec champ obligatoire manquant";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "Le mod problématique est : <b>" + nombreJar
				+ "</b>. Ce fichier présente une erreur dans sa configuration de dépendances.";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Ouvrez le fichier <b>mods.toml</b> situé dans le dossier <b>/META-INF/</b> du mod <b>" + nombreJar
				+ "</b>.";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Dans la section des dépendances, assurez-vous que chaque entrée inclut <b>mandatory=true</b> ou <b>mandatory=false</b> (ex : modId=\\\"forge\\\", mandatory=true, versionRange=\\\"[1.21.8,)\\\" ).";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erreur critique avec le mod : '" + nombreJar
				+ "'. Configuration d'access transformer invalide. Cela se produit lorsque le fichier de configuration a une syntaxe incorrecte ou fait référence à des classes/méthodes qui n'existent pas.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer invalide";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "Le mod problématique est : <b>" + nombreJar
				+ "</b>. Ce mod contient une configuration d'access transformer invalide.";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Ouvrez le fichier <b>accessTransformer.cfg</b> situé dans le mod <b>" + nombreJar
				+ "</b> (généralement à la racine du fichier JAR).";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Corrigez la syntaxe de l'access transformer. Les lignes doivent suivre le format : <b>access class.method</b> (ex : public net.minecraft.world.entity.Entity.func_200560_a). Supprimez les lignes faisant référence à des classes ou méthodes qui n'existent pas dans votre version de Minecraft.";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erreur critique : Discrépance entre l'ID du mod dans l'annotation @Mod et le fichier mods.toml. Le mod '"
				+ nombreMod + "' ne peut pas être chargé car les IDs ne correspondent pas.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Discrépance entre @Mod et mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "Le mod en développement '" + nombreMod
				+ "' présente une discrépance entre l'ID dans l'annotation <b>@Mod</b> et la valeur dans <b>mods.toml</b>.";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Vérifiez que l'ID dans votre classe principale correspond à la valeur <b>modId</b> dans le fichier <b>/META-INF/mods.toml</b>. Exemple : <b>@Mod(\\\"mimod\\\")</b> doit correspondre à <b>modId=\\\"mimod\\\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Si vous utilisez Gradle, exécutez <b>clean</b> après avoir effectué des modifications pour vous assurer que les ressources sont mises à jour correctement. Parfois, d'anciens fichiers persistent dans le dossier build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "serveur";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "serveur" : "client";

		return "<b style='color:#" + config.obtenerColorError() + "'>Erreur critique : La classe '" + nombreClase
				+ "' tente d'être chargée dans l'environnement " + plataforma + ", mais elle est conçue pour "
				+ plataformaOpuesta
				+ ". <b>Utilisez la fonction 'Arbre des Mods' dans la barre latérale pour rechercher quel mod tente de charger cette classe</b>. "
				+ "Les mods sont construits spécifiquement pour une plateforme et ne fonctionnent pas sur l'autre.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod sur la mauvaise plateforme";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Dans l'onglet <b>Arbre des Mods</b> (à droite), recherchez les références à la classe <b>" + nombreClase
				+ "</b> pour identifier quel mod cause le problème";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "client" : "serveur";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "serveur" : "client";

		return "Le mod identifié est un mod <b>" + plataformaOpuesta
				+ "</b> et ne doit pas être utilisé dans votre environnement " + plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Supprimez le mod problématique de votre dossier <b>mods</b>. Si vous avez besoin d'une fonctionnalité similaire pour cette plateforme, "
				+ "cherchez un mod alternatif spécifiquement conçu pour le <b>client</b> ou le <b>serveur</b> selon le cas";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erreur critique : Métadonnées manquantes pour le modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Les mods suivants pourraient être à l'origine du problème : <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", et d'autres...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Cela se produit lorsqu'un mod dépend d'un autre mod qui n'est pas installé ou dont le fichier mods.toml est incorrect.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Métadonnées mods.toml manquantes";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Les mods suivants dépendent de '").append(modIdFaltante)
					.append("' : <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", et d'autres...");
			paso.append("</b>. Utilisez la fonction <b>Arbre des Mods</b> pour confirmer quel mod cause le problème");
			return paso.toString();
		}
		return "Un mod tente de dépendre de '" + modIdFaltante
				+ "', mais ce mod n'est pas installé. Utilisez la fonction <b>Arbre des Mods</b> pour identifier quel mod cause le problème";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Vous avez deux options :<br/>"
				+ "1. <b>Installez le mod manquant</b> : Recherchez et installez le mod avec l'ID '" + modIdFaltante
				+ "'<br/>"
				+ "2. <b>Supprimez le mod dépendant</b> : Si vous n'avez pas besoin de la fonctionnalité, supprimez le mod qui dépend de '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Si le mod '" + modIdFaltante + "' est une bibliothèque (comme 'forge', 'minecraft', 'curios'), "
				+ "assurez-vous d'avoir installé la version correcte de Minecraft et Forge. "
				+ "S'il s'agit d'un mod standard, consultez sa page de téléchargement pour connaître les prérequis nécessaires";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Avertissement : Erreur lors du démarrage du système audio. Les sons et la musique ont été désactivés. Cette erreur est souvent associée au mod SoundPhysicsMod et peut être due à des conflits avec d'autres bibliothèques audio.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Erreur du système audio";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "L'erreur est souvent liée à <b>SoundPhysicsMod</b>. Vérifiez si vous avez installé la version la plus récente compatible avec votre version de Minecraft";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Si vous utilisez d'autres mods audio (comme Sound Filters, Dynamic Surroundings, etc.), essayez de supprimer temporairement SoundPhysicsMod pour voir si cela résout le conflit";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Consultez le dossier <b>logs</b> pour trouver des messages supplémentaires liés à LWJGL ou OpenAL, qui peuvent indiquer des problèmes avec les bibliothèques audio sous-jacentes";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erreur critique : La classe '").append(nombreClase)
				.append("' est enregistrée comme listener d'événements mais ne contient aucune méthode valide. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Cette classe se trouve dans les mods suivants : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", et d'autres...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Cela se produit lorsqu'une classe est enregistrée pour écouter des événements sans avoir de méthodes annotées avec @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Classe enregistrée sans listeners d'événements";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("La classe problématique se trouve dans ces mods : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", et d'autres...");
			paso.append("</b>. Ces mods tentent d'enregistrer des événements sans méthodes valides");
			return paso.toString();
		}
		return "La classe <b>" + nombreClase
				+ "</b> a été enregistrée pour écouter des événements mais ne contient aucune méthode avec l'annotation <b>@SubscribeEvent</b>. Utilisez la fonction <b>Arbre des Mods</b> pour identifier quel mod contient cette classe";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "Dans le code source, vérifiez que la classe <b>" + nombreClase
				+ "</b> contient au moins une méthode avec : "
				+ "<b>@SubscribeEvent public void nomMethode(EvenementSpecifique evenement) { ... }</b>. "
				+ "S'il s'agit d'une classe interne, assurez-vous qu'elle n'est pas marquée comme static";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Pour les mods identifiés (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", etc.");
			paso.append("</b>) : ");

			if (modsUbicacion.size() == 1) {
				paso.append("contactez le développeur de ce mod pour qu'il corrige le problème. ");
			} else {
				paso.append("contactez les développeurs de ces mods pour qu'ils corrigent le problème. ");
			}
		}

		paso.append(
				"Si vous êtes le développeur, supprimez l'enregistrement de cette classe dans l'EventBus ou ajoutez des méthodes @SubscribeEvent valides");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		String mensaje = "<b style='color:#" + config.obtenerColorError()
				+ "'>Erreur critique : Erreur dans UnionFileSystem lors du traitement de '" + nombreArchivo + "'. ";

		mensaje += "Cette erreur est très courante dans les modpacks préconfigurés et est directement liée à des problèmes du lanceur. ";

		mensaje += "Le système ne peut pas lire correctement les fichiers du mod car ils sont corrompus ou incomplets.</b>";

		return mensaje;
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Erreur UnionFileSystem - Fichier corrompu";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		String paso = "L'erreur spécifique <b>cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException</b> a été détectée avec le fichier <b>"
				+ nombreArchivo + "</b>.";

		paso += " Il s'agit d'une erreur connue des lanceurs de modpacks lorsque les fichiers ne sont pas entièrement téléchargés.";

		return paso;
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Réinstallez complètement le modpack. Cette erreur se produit principalement lorsque le lanceur ne termine pas le téléchargement de tous les fichiers. "
				+ "Si vous utilisez <b>Luna Pixel</b>, nous vous recommandons vivement d'utiliser <b>ATLauncher</b> à la place, "
				+ "car ce lanceur gère mieux les fichiers de mods et évite cette erreur spécifique.";

	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Si le problème persiste après la réinstallation : <br/>" + "1. <b>Changez de lanceur</b> <br/>"
				+ "2. Si vous utilisez <b>Luna Pixel</b>, <b>utilisez ATLauncher</b> qui est plus fiable pour éviter cette erreur spécifique<br/>"
				+ "3. Vérifiez votre connexion Internet et l'espace disque avant de réinstaller le modpack";

	}

	/**
	 * Obtiene el mensaje de confirmación para habilitar el proxy de
	 * System.out/System.err
	 * 
	 * @return Mensaje explicativo con advertencias y requisitos
	 */
	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Activer ProxySysOutSysErr ?\\n\\n"
				+ "Cette option donne à CrashDetector l'accès à System.out et System.err lorsque le lanceur ne fournit pas de journaux.\\n\\n"
				+ "À activer uniquement si vous ne pouvez pas coller un journal manuellement.\\n\\n"
				+ "Avertissement : Cela peut interférer avec certains mods ou lanceurs.\\n\\n"
				+ "Un redémarrage du jeu/de l'application est requis pour que les changements prennent effet.";
	}

	/**
	 * Obtiene el título para diálogos de confirmación
	 * 
	 * @return Título en español para ventanas de confirmación
	 */
	@Override
	public String confirmacionTitulo() {
		return "Confirmation";
	}

	/**
	 * Obtiene el mensaje de éxito tras habilitar el proxy
	 * 
	 * @return Mensaje informativo sobre el estado del proxy
	 */
	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr activé avec succès.\\n\\n"
				+ "Un redémarrage de CrashDetector est requis pour que les changements prennent effet.";
	}

	/**
	 * Obtiene el título para diálogos informativos
	 * 
	 * @return Título en español para ventanas de información
	 */
	@Override
	public String informacionTitulo() {
		return "Information";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Erreur critique : AzureLib et GeckoLib se sont initialisés trop tôt ! ");
		} else if (azureLibError) {
			mensaje.append("Erreur critique : AzureLib s'est initialisé trop tôt ! ");
		} else if (geckoLibError) {
			mensaje.append("Erreur critique : GeckoLib s'est initialisé trop tôt ! ");
		}

		mensaje.append(
				"Cette erreur se produit lorsque l'on tente d'utiliser des mods Fabric avec des versions non-Fabric de ces bibliothèques. ");

		if (connectorPresente) {
			mensaje.append(
					"Un mod de compatibilité (Sinytra Connector ou specialcompatibilityoperation) a été détecté, ce qui indique que vous essayez d'utiliser des mods Fabric dans un environnement Forge ou FeatureCreep. ");
			mensaje.append(
					"Vérifiez l'erreur 'Erreur d'initialisation de FabricMC' dans les journaux pour identifier quel mod spécifique cause le problème. ");
		}

		mensaje.append(
				"AzureLib et GeckoLib sont des bibliothèques essentielles pour les mods d'animation, mais elles doivent correspondre à la bonne plateforme (Fabric ou Forge). ");
		mensaje.append(
				"Le jeu ne peut pas charger correctement les mods d'animation en raison de ce conflit d'initialisation.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Bibliothèque initialisée trop tôt";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Vérifiez l'erreur 'Erreur d'initialisation de FabricMC' pour identifier le mod problématique";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Vérifiez que vous utilisez la version correcte d'AzureLib/GeckoLib pour votre plateforme (Forge ou Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erreur critique : Incompatibilité entre C2ME et les mods de connexion. "
				+ "Cette erreur se produit parce que C2ME tente d'accéder à des composants internes de Java qui sont restreints dans les environnements avec "
				+ "Sinytra Connector, specialcompatibilityoperation ou d'autres mods de compatibilité Fabric/Forge. "
				+ "<b>C2ME n'est pas compatible avec ces environnements, mais <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> est l'alternative recommandée</b> qui fonctionne correctement "
				+ "avec les mods de connexion. Le jeu ne peut pas démarrer en raison de ce conflit de permissions de sécurité Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Incompatibilité C2ME avec les mods de connexion";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Supprimez C2ME de votre dossier de mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Téléchargez et installez <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> à la place (compatible avec Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Vérifiez que tous les mods de connexion (comme Sinytra Connector) sont mis à jour vers leur dernière version";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erreur critique : Échec du chargement du plugin JEI pour le mod '" + modId + "'. La classe '"
				+ nombreClase + "' (ID du plugin : '" + pluginId
				+ "') a généré une erreur qui provoque le crash du jeu lors du démarrage. "
				+ "Ce problème se produit lorsqu'un mod possède une intégration JEI incompatible ou défectueuse qui interrompt l'initialisation du jeu.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI défectueux - Cause du crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "Le mod <b>" + modId
				+ "</b> contient un plugin JEI défectueux qui provoque le crash. Utilisez la fonction <b>Arbre des Mods</b> pour confirmer quel mod génère le problème";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Supprimez temporairement le mod <b>" + modId
				+ "</b> de votre dossier de mods pour vérifier si cela résout le crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Recherchez des mises à jour pour le mod <b>" + modId
				+ "</b> ou contactez son développeur en signalant le problème avec le plugin JEI. "
				+ "En attendant, le mod doit être supprimé pour pouvoir lancer le jeu";
	}

	/**
	 * Obtiene el título de la aplicación
	 * 
	 * @return Título de la ventana principal
	 */
	@Override
	public String tituloLectador() {
		return "Lecteur de Consoles - Crash Detector";
	}

	/**
	 * Obtiene el título para la leyenda de colores
	 * 
	 * @return Título de la sección de leyenda
	 */
	@Override
	public String obtenerTituloLeyenda() {
		return "LÉGENDE DES COULEURS";
	}

	/**
	 * Obtiene el texto para identificar errores en la leyenda
	 * 
	 * @return Texto descriptivo para errores
	 */
	@Override
	public String obtenerErrorEnLeyenda() {
		return "Erreurs critiques";
	}

	/**
	 * Obtiene el texto para identificar stacktraces en la leyenda
	 * 
	 * @return Texto descriptivo para stacktraces
	 */
	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Traces de pile";
	}

	/**
	 * Obtiene el título para ventanas de error
	 * 
	 * @return Título estándar para mensajes de error
	 */
	@Override
	public String obtenerTituloError() {
		return "Erreur";
	}

	/**
	 * Obtiene el mensaje para errores al procesar líneas de log
	 * 
	 * @return Mensaje de error específico
	 */
	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Une erreur s'est produite lors du traitement de la ligne sélectionnée";
	}

	/**
	 * Obtiene el título para el área de nombre de error
	 * 
	 * @return Título del panel de nombre de error
	 */
	@Override
	public String obtenerNombreError() {
		return "NOM DE L'ERREUR";
	}

	/**
	 * Obtiene el título para el área de descripción de error
	 * 
	 * @return Título del panel de descripción de error
	 */
	@Override
	public String obtenerDescripcionError() {
		return "DESCRIPTION DÉTAILLÉE";
	}

	/**
	 * Obtiene el título para el selector de consolas
	 * 
	 * @return Título del combobox de selección
	 */
	@Override
	public String obtenerSeleccionarConsola() {
		return "SÉLECTIONNER LA CONSOLE";
	}

	/**
	 * Obtiene el nombre predeterminado para errores
	 * 
	 * @return Nombre genérico para errores
	 */
	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Erreur non identifiée";
	}

	/**
	 * Obtiene la descripción predeterminada para errores
	 * 
	 * @return Descripción genérica para errores
	 */
	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Une erreur critique a été détectée dans les journaux. "
				+ "Veuillez examiner la trace de pile pour identifier la cause racine.";
	}

	/**
	 * Obtiene el mensaje para errores de lectura de archivos
	 * 
	 * @return Mensaje específico para fallos en lectura
	 */
	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Impossible de lire le fichier journal. "
				+ "Vérifiez que le fichier existe et dispose des permissions de lecture.";
	}

	/**
	 * Obtiene la etiqueta para el botón en la barra lateral
	 * 
	 * @return Texto que aparecerá en el botón lateral
	 */
	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Analyseur de Logs";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erreur critique : Échec de l'enregistrement des abonnés automatiques pour le mod '")
				.append(modId).append("'. ");

		mensaje.append("Classe problématique : <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Cette classe se trouve dans : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", et d'autres...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Cette erreur se produit lorsqu'un mod tente d'enregistrer une classe comme abonné automatique mais que la classe ne peut pas être chargée correctement. ");
		mensaje.append(
				"<b>Vérifiez les autres erreurs dans le log, car le problème réel pourrait se trouver ailleurs dans le journal</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Échec de l'enregistrement des abonnés automatiques";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "Le mod <b>" + modId + "</b> tente d'enregistrer la classe <b>" + nombreClase
				+ "</b> comme abonné automatique, mais l'opération a échoué. Vérifiez que cette classe existe et est accessible";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"La classe problématique <b>" + nombreClase + "</b> se trouve dans ces fichiers : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", et d'autres...");
			paso.append("</b>. ");
			paso.append(
					"Utilisez la fonction <b>Arbre des Mods</b> pour confirmer quel fichier spécifique contient la classe problématique");
			return paso.toString();
		}
		return "La classe <b>" + nombreClase + "</b> ne se trouve dans aucun fichier de mod. Vérifiez que le mod <b>"
				+ modId
				+ "</b> est correctement installé. Utilisez la fonction <b>Arbre des Mods</b> pour aider à identifier le problème";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Mettez à jour le mod <b>" + modId
				+ "</b> vers la dernière version compatible avec votre version de Minecraft et Forge. "
				+ "Si le problème persiste, contactez le développeur du mod en signalant l'erreur avec la classe problématique";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Vérifiez <b>les autres erreurs dans le log</b> précédant ce message, car le problème réel pourrait se trouver ailleurs dans le journal. "
				+ "Parfois, une erreur précédente empêche le chargement correct des classes nécessaires à l'enregistrement des abonnés";
	}

	@Override
	public String limpiado() {
		return "Nettoyé";
	}

	@Override
	public String original() {
		return "Original";
	}

	@Override
	public String verEnConsola() {
		return "Voir dans la console";
	}

	@Override
	public String advertencia() {
		return "Avertissement";
	}

	@Override
	public String fatal() {
		return "Fatal";
	}

	@Override
	public String noRegistroDeBattly() {
		return "BattlyLauncher ne fournit ni journal ni console à copier. Vous pouvez utiliser ProxySysOutSysErr pour intercepter STDOUT et STDERR et redémarrer le jeu, mais ProxySysOutSysErr peut entrer en conflit avec des mods modifiant STDOUT ou STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		return "Vous devez activer le mode débogage dans la configuration de NightWorld pour obtenir un journal du lanceur. C'est très important, surtout parce qu'il gère STDOUT et STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		return "Vous devez sauvegarder ou coller le contenu du terminal de votre serveur, car il contient des informations absentes des autres journaux, y compris STDOUT, STDERR et d'autres erreurs. Veuillez coller le contenu de la dernière session. Pour l'avenir, vous pouvez enregistrer le contenu du terminal dans le fichier cd_launcherlog. Pour éviter d'avoir à le coller, ajoutez >> cd_launcherlog après la commande, comme indiqué sur l'image. Notez que cela empêchera l'affichage dans le terminal ; le contenu n'apparaîtra que dans ce fichier une fois l'opération effectuée.";
	}

//Métodos para Idioma relacionados con la verificación LexForgeMLTransformerEnNeoForge

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Erreur critique : Transformateur LexForge détecté dans un environnement NeoForge. ");
		sb.append("</b>");

		sb.append("Classe impliquée : <b>").append(claseReceptora).append("</b>. ");
		sb.append("L'interface affectée est <b>").append(interfazObjetivo).append("</b> ");
		sb.append("et la méthode <b>").append(firmaMetodoFaltante).append("</b> est manquante. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("La classe a été trouvée dans : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", et d'autres...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Aucun JAR contenant cette classe n'a été localisé ; elle pourrait être ombragée (shaded) ou incluse en tant que jar-in-jar. ");
		}

		sb.append(
				"Cette erreur apparaît lorsqu'un transformateur/service ModLauncher compilé pour MinecraftForge/LexForge ");
		sb.append("est chargé sous NeoForge avec une version incompatible de l'API ModLauncher. ");
		sb.append("Mettez à jour ou remplacez le composant pour NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformateur LexForge utilisé dans NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identifiez le transformateur incompatible : <b>" + claseReceptora + "</b>. " + "L'API attendue est <b>"
				+ interfazObjetivo + "</b> et il manque <b>" + firmaMetodoFaltante + "</b>. "
				+ "Vérifiez si le mod enregistre cette classe dans <b>META-INF/services</b> et supprimez-la ou désactivez-la dans NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Emplacement du/des mod(s) impliqué(s) : <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", et d'autres...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Aucun JAR contenant la classe n'a été trouvé. Vérifiez les jar-in-jar et les dépendances ombragées. ");
		}
		sb.append(
				"Retirez temporairement ces JARs ou utilisez des versions compatibles avec NeoForge pour confirmer l'origine.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Remplacez le composant par une version spécifique à NeoForge ou recompilez-le contre la "
				+ "version de ModLauncher utilisée par NeoForge. Évitez les binaires anciens de LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Nettoyez le dossier des mods et supprimez les doublons jar-in-jar. Videz les caches du lanceur si nécessaire "
				+ "et redémarrez pour vérifier que les transformateurs LexForge ne sont plus chargés.";
	}
//En tu clase de idioma:

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia ne peut pas démarrer : Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("est incompatible.</b> ");
		sb.append("Supprimez Xenon et utilisez Embeddium ou Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Détecté dans : <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", et d'autres...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia incompatible avec Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "L'élément " + label + ", incompatible avec WaterMedia, a été détecté. Retirez-le du profil.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Emplacements : <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", et d'autres...");
			sb.append("</b>. Supprimez ce JAR.");
			return sb.toString();
		}
		return "Aucun JAR localisé. Vérifiez le dossier mods et supprimez Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Installez Embeddium ou Sodium comme substitut et redémarrez le jeu.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Erreur de compression (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater fermé lors de la copie des ressources TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Lié à : <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", et autres");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>Solution :</b> dans <code>tacz/tacz-pre.toml</code> mettez <code>DefaultPackDebug=true</code>. ")
				.append("Si nécessaire, générez d'abord une carte puis activez-le.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Dans tacz/tacz-pre.toml, mettez DefaultPackDebug=true. Si nécessaire, générez d'abord une carte puis activez-le.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Fonctions de densité non liées";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Fonctions de densité manquantes dans le journal.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Manquantes : ");
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
				"<br/><b>Solution :</b> installez ou activez le mod/datapack qui définit ces fonctions et redémarrez. Une autre cause commune de ce problème est lorsque vous avez le mod nécessaire, mais qu'il présente un problème ou un conflit avec un autre mod ; par exemple, Terralith a beaucoup de problèmes et peut provoquer cette erreur et d'autres, y compris des erreurs JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Installez ou activez le mod/datapack qui fournit ces fonctions et redémarrez le jeu.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mensaje breve, en color de error, mencionando explícitamente el mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Entrée de journal absente : ").append(claveFaltante).append(". ");
		sb.append("Fréquent avec l'alpha de Steam & Railways pour Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6 : Steam & Railways (alpha)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Supprimez ou remplacez l'alpha de Steam & Railways pour Create 6 par une version compatible.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Corto, con color de error y recomendación directa
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Conflit de chargement : Multiworld utilisé avec Sodium/Embeddium/Rubidium provoque ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Suggestion : supprimez Multiworld ou le mod de performance, ou utilisez des versions compatibles.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Conflit : Multiworld avec les mods de performance";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Désinstallez Multiworld ou Sodium/Embeddium/Rubidium, ou mettez à jour vers des versions compatibles entre elles.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium a détecté un pilote graphique incompatible. "
				+ "Mettez à jour le pilote de votre GPU vers la version minimale requise ou suivez le guide de Sodium."
				+ "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Erreur de contexte OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Échec OpenGL : aucun contexte actuel ou la fonction n'est pas disponible dans ce contexte. "
				+ "Cela peut aussi être un problème de pilotes vidéo." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Mettez à jour/réinstallez les pilotes du GPU et redémarrez ; désactivez les superpositions (overlays) et testez sans mods de performance.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Lien copié dans le presse-papiers.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Rechercher dans les archives (.zip/.jar/.war/.ear/.fpm/.rar de Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur de résolution de textures : Impossible d'ajuster " + recurso + " - taille : " + tamaño
				+ "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Erreur de résolution de textures";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Cette erreur se produit lorsque les textures sont trop grandes ou qu'il y a trop de packs de ressources. "
				+ "Essayez d'utiliser des packs de ressources de moindre résolution ou supprimez certains packs. "
				+ "Vérifiez que vous n'avez pas ajouté de textures personnalisées avec une résolution supérieure à celle autorisée.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur des services ModLauncher : Chemin contenant des caractères invalides. "
				+ "Les services ModLauncher ne peuvent pas traiter les chemins contenant des caractères non-ASCII ou spéciaux. "
				+ "Les caractères problématiques incluent : ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, et surtout le caractère '\\\"' lorsqu'il est à la fin du nom. "
				+ "Les composants de services courants dans ModLauncher incluent CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clones, Iris Shaders/Oculus, MixerLogger, CrashAssistant et Sinytra Connector. "
				+ "Vous pouvez supprimer tous les services, mais d'autres problèmes peuvent survenir en raison du nom du chemin. "
				+ "Solution : Renommez l'instance pour n'utiliser que des caractères ASCII (a-z, A-Z, 0-9), sans espaces ni caractères spéciaux.</b>";
	}// TODO incluye un Buscardor para mods con servicios

	@Override
	public String nombre_error_modlauncher_path() {
		return "Erreur de chemin dans ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Cette erreur se produit lorsque le chemin de l'instance contient des caractères non-ASCII ou spéciaux. "
				+ "Les services ModLauncher ne peuvent pas gérer ces chemins. "
				+ "Solution : Renommez l'instance pour n'utiliser que des caractères ASCII (a-z, A-Z, 0-9) et évitez les espaces et les caractères spéciaux. "
				+ "Faites particulièrement attention au caractère '\\\"' qui est très problématique, surtout lorsqu'il est à la fin du nom.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Éditeur de Code";
	}

	@Override
	public String nuevo() {
		return "Nouveau";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Mettre à jour la sélection";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Supprimer la sélection";
	}

	@Override
	public String exportarJSON() {
		return "Exporter JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Tout enregistrer";
	}

	@Override
	public String general() {
		return "Général";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Texte à rechercher";
	}

	@Override
	public String filtro() {
		return "Filtre (id)";
	}

	@Override
	public String criticalidad() {
		return "Criticité (AVERTISSEMENT/ERREUR/FATAL)";
	}

	@Override
	public String prioridad() {
		return "Priorité";
	}

	@Override
	public String lista() {
		return "Vérifications";
	}

	@Override
	public String colIdioma() {
		return "Langue";
	}

	@Override
	public String colNombre() {
		return "Nom";
	}

	@Override
	public String colResultado() {
		return "Résultat";
	}

	@Override
	public String vistaJson() {
		return "Aperçu JSON";
	}

	@Override
	public String idiomas() {
		return "Langues (toutes obligatoires)";
	}

	@Override
	public String elegirFiltro() {
		return "Choisir...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Sélectionnez un filtre";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Filtres disponibles";
	}

	@Override
	public String faltanCampos() {
		return "Veuillez remplir tous les champs généraux obligatoires.";
	}

	@Override
	public String critInvalida() {
		return "Criticité invalide. Utilisez AVERTISSEMENT, ERREUR ou FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "Le filtre indiqué n'existe pas.";
	}

	@Override
	public String faltanIdiomas() {
		return "Veuillez remplir le nom et le résultat pour toutes les langues :";
	}

	@Override
	public String verificacionInvalida() {
		return "Une vérification est invalide. Veuillez vérifier les champs.";
	}

	@Override
	public String guardadoOk() {
		return "Enregistrement réussi.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Ajouter des raisons";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Vous pouvez enregistrer des raisons ici. Vous avez besoin d'un ID, d'une chaîne sans caractères spéciaux, accents ou espaces. Pour les filtres, vous pouvez utiliser \"linea containe\" pour rechercher une chaîne dans une ligne, \"todo containe\" si le journal contient une chaîne, \"regex linea\" si une ligne correspond à une regex, et \"regex todos\" (il est suggéré d'utiliser les versions de ligne). Vous devez définir la criticité : FATAL, ERROR ou ADVERTENCIA pour les couleurs. Pour toutes les langues, vous devez écrire un nom et une réponse pour l'affichage. Vous pouvez ajouter plus de vérifications ou en supprimer d'autres. Enregistrez lorsque c'est complet.";
	}

	@Override
	public String descartarCambios() {
		return "Ignorer les modifications non enregistrées de la vérification actuelle ?";
	}

	@Override
	public String confirmacion() {
		return "Confirmation";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Voulez-vous enregistrer les modifications avant de quitter ?";
	}

	@Override
	public String salirSinGuardar() {
		return "Quitter sans enregistrer";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Erreur critique : Échec du chargement d'un service modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Classe problématique :</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod affecté :</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Mod non identifié.</b> Vérifiez les mods récents, de développement ou mal empaquetés.<br>");
		}

		sb.append("🔸 <b>Cause :</b> Le fichier <code>META-INF/services/...</code> du mod est corrompu, ");
		sb.append(
				"incompatible avec cette version de Forge/NeoForge, ou le mod est destiné à une version incorrecte.<br>");
		sb.append("🔸 <b>Conséquence :</b> Forge/NeoForge ne peut pas enregistrer les dépendances du mod, ");
		sb.append("ce qui empêche le démarrage du jeu.<br>");
		sb.append("🔸 <b>Solution :</b> Mettez à jour, réinstallez ou supprimez le mod problématique. ");
		sb.append(
				"Si vous utilisez des mods de développement, assurez-vous qu'ils sont compilés pour votre version exacte de Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Erreur de configuration de service (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identifiez le mod responsable : vérifiez les mods installés récemment ou ceux en développement.";
		}
		return "1. Le mod problématique est : " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Mettez à jour, réinstallez ou supprimez le mod. Assurez-vous d'utiliser une version compatible avec votre Forge/NeoForge.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris neutre pour noms de classe/champ

		return "<b style='color:#" + colorError + "'>Erreur critique : Champ inexistant.</b><br>"
				+ "Le mod a tenté d'accéder au champ <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "qui n'existe pas dans cette version du jeu ou d'un autre mod.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Champ inexistant (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Cette erreur se produit généralement lorsqu'un mod est incompatible avec la version actuelle du jeu ou d'un autre mod.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Mettez à jour tous les mods affectés. Si le problème persiste, contactez l'auteur du mod qui a généré l'erreur.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888"; // Gris pour méthodes/classes

		return "<b style='color:#" + colorError + "'>Erreur critique : Méthode inexistante.</b><br>"
				+ "Le mod a tenté d'appeler la méthode <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "qui n'existe pas dans cette version du jeu ou d'un autre mod.<br>" + "<span style='color:#"
				+ colorCodigo + "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Méthode inexistante (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Cette erreur se produit lorsqu'un mod est incompatible avec la version actuelle du jeu ou d'un autre mod.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Mettez à jour tous les mods impliqués. Si le problème persiste, signalez l'erreur à l'auteur du mod affecté.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Besoin d'aide ?</strong><br>"
				+ "  Si vous ne savez pas comment résoudre le problème ou si la raison n'est pas ici, vous pouvez obtenir de l'aide sur nos réseaux sociaux. "
				+ "  Utilisez le bouton <img src='" + iconoCompartir
				+ "' alt='Partager' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Partager</strong> pour obtenir des liens vers les journaux et les résultats pour notre équipe. "
				+ "  Si vous êtes un créateur de modpack ou une entreprise, éditez <code>crash_detector/plantilla.htm</code> "
				+ "  pour personnaliser les liens de votre équipe." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Réinitialiser le modèle";
	}

	@Override
	public String restablecer() {
		return "Réinitialiser";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Réinitialiser " + nombreImagen + " aux valeurs par défaut ?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Réinitialiser le modèle aux valeurs par défaut ?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Classes AzureLib manquantes. Si vous avez déjà AzureLib, veuillez installer une version antérieure au 8 octobre 2025. C'était courant. Si vous n'avez pas AzureLib, installez la version actuelle.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Le mod <code>healight</code> provoque une erreur critique : <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Cette erreur se produit car le mod tente d'accéder à un champ qui n'existe plus dans la version MCForge 47.10 pour Minecraft 1.20+. "
				+ "Le jeu ne peut pas démarrer à cause de ce problème.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Supprimez ou mettez à jour le mod <code>healight</code>. "
				+ "La version actuelle n'est pas compatible avec MinecraftForge 47.10 pour 1.20.1. "
				+ "Recherchez une version plus récente du mod ou envisagez d'utiliser une alternative.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Erreur critique : healight - Champ 'INT' introuvable";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("La classe <code>").append(clase)
				.append("</code> n'implémente pas la méthode requise :<br>").append("<code>").append(metodo)
				.append("</code><br>").append("de l'interface <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Mod ou fichier suspect : <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Cette erreur se produit lorsqu'un mod implémente une interface mais omet une méthode obligatoire. "
				+ "Mettez à jour <b>les deux mods</b> impliqués (celui qui définit l'interface et celui qui l'implémente). "
				+ "Si vous ne savez pas lesquels ils sont, recherchez les noms qui apparaissent dans le message d'erreur.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Méthode d'interface non implémentée (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Un mod tente de charger une classe du <b>côté client</b> "
				+ "(<code>AnimationMetadataSection</code>) sur un <b>serveur dédié</b>, ce qui est impossible. "
				+ "Cette erreur apparaît généralement lorsqu'un mod ne sépare pas correctement son code entre client et serveur. "
				+ "La présence de <code>ModernFix</code> peut exposer ce problème, bien qu'elle n'en soit pas la cause directe.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Solution rapide :</b> Supprimez temporairement <code>ModernFix</code> pour confirmer si le serveur démarre. "
				+ "Si cela fonctionne, le problème vient d'un autre mod qui charge des classes client sur le serveur.<br>"
				+ "• <b>Solution permanente :</b> Identifiez le mod coupable (recherchez des mods avec des ressources animées, des textures personnalisées ou des bibliothèques graphiques) et mettez-le à jour ou supprimez-le.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Classe client chargée sur le serveur (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Le fichier de configuration d'un mod <code>Sinytra Connector</code> est corrompu. "
				+ "Cela se produit souvent lorsque le fichier est rempli de caractères nuls (<code>\\\\u0000</code>) "
				+ "en raison d'une fermeture inattendue du jeu, d'échecs d'écriture ou de conflits de mods.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Accédez au dossier <code>config/</code> de votre instance Minecraft.<br>"
				+ "• Recherchez et supprimez les configurations des mods connector.<br>"
				+ "• Redémarrez le jeu : Sinytra Connector générera un nouveau fichier de configuration propre.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Configuration de Sinytra Connector corrompue";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Le fichier <code>" + nombreJar
				+ "</code> est corrompu ou incomplet.<br>"
				+ "Le système ne peut pas lire son contenu car l'en-tête final du fichier ZIP est manquant.<br>"
				+ "Cette erreur se produit généralement après un téléchargement interrompu ou un échec du lanceur.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Fichier JAR corrompu (avec nom spécifique)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Supprimez le fichier corrompu</b> et téléchargez-le à nouveau depuis la source officielle (CurseForge, MinecraftStorage, etc.).<br>"
				+ "• Si vous utilisez un lanceur comme CurseForge, Technic ou Luna Pixel, envisagez de passer à <b>ATLauncher</b> ou <b>Prism Launcher</b>, "
				+ "qui vérifient mieux l'intégrité des fichiers.<br>"
				+ "• Assurez-vous que votre connexion Internet est stable pendant le téléchargement.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Impossible de charger le monde car l'un de ses fichiers NBT est corrompu "
				+ "(par exemple : <code>level.dat</code>, <code>playerdata/*.dat</code>, ou des chunks).<br>"
				+ "L'erreur spécifique est : <code>UTFDataFormatException: malformed input around byte " + byteCorrupto
				+ "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Avant de tenter toute réparation, faites une sauvegarde complète du dossier du monde.</b><br><br>"
				+ "Vous pouvez essayer de réparer le fichier corrompu en utilisant un <b>éditeur NBT</b> comme <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Si les dommages sont graves, utilisez un <b>éditeur hexadécimal</b> (comme HxD) pour inspecter et corriger les octets invalides "
				+ "(uniquement si vous avez de l'expérience avec le format NBT).<br>"
				+ "En dernier recours, restaurez depuis une sauvegarde ou utilisez la fonction <i>world repair</i> de mods comme <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Faites une sauvegarde complète du dossier du monde</b> avant de tenter toute réparation.<br>"
				+ "• Utilisez un éditeur NBT (comme NBT Studio) pour ouvrir et corriger le fichier endommagé.<br>"
				+ "• En cas d'échec, inspectez le fichier avec un éditeur hexadécimal à la position de l'octet corrompu.<br>"
				+ "• Si vous n'avez pas d'expérience, restaurez depuis une sauvegarde récente.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Monde corrompu : erreur lors du chargement des données NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Vous avez un problème avec OpenAL. Parfois, les pilotes Nouveau peuvent en être la cause, mais il arrive aussi que la version d'OpenAL incluse dans l'application ne soit pas compatible avec la version de votre distribution et que vous deviez utiliser la version fournie par votre distribution. C'est particulièrement courant avec les distributions Red Hat et avec des mods audio comme Sound Physics Remastered. Consultez ce guide pour plus d'aide : <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Solution aux problèmes de son dans Minecraft sous Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Le serveur ne peut pas démarrer car un fichier du monde est verrouillé par un autre processus.<br>"
				+ "Cela se produit généralement si :<br>"
				+ "• Une instance du serveur est déjà en cours d'exécution.<br>"
				+ "• Un antivirus ou un explorateur de fichiers a ouvert le dossier du monde.<br>"
				+ "• Le processus précédent ne s'est pas fermé correctement et a laissé des fichiers verrouillés.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Fermez toutes les instances du serveur</b> (y compris les processus en arrière-plan comme javaw.exe).<br>"
				+ "• Si vous utilisez un panneau d'hébergement (comme Multicraft), redémarrez complètement le serveur depuis le panneau.<br>"
				+ "• <b>Désactivez temporairement votre antivirus</b> si vous soupçonnez qu'il bloque les fichiers.<br>"
				+ "• Sur les systèmes locaux, fermez toute fenêtre de l'Explorateur Windows affichant le dossier du monde.<br>"
				+ "• Si le problème persiste, supprimez manuellement le fichier <code>session.lock</code> dans le dossier du monde (uniquement si vous êtes sûr qu'aucun autre serveur n'est actif).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Fichier du monde verrouillé par un autre processus";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Le mod a tenté d'étendre la classe <code>"
				+ clasePadreFinal + "</code>, "
				+ "mais cette classe est désormais <b>final</b> et ne peut pas être héritée.<br>"
				+ "La classe problématique est : <code>" + claseHija + "</code>.<br><br>"
				+ "Cela se produit généralement lorsqu'un mod est compilé pour une version antérieure de Minecraft ou d'un autre mod de base, "
				+ "qui a marqué des classes comme <code>final</code> dans les versions récentes.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Mettez à jour tous les mods impliqués</b>, en particulier ceux qui pourraient être liés au mod de base mentionné.<br>"
				+ "• Si le problème persiste, recherchez une version du mod compatible avec votre version actuelle de Minecraft et ses dépendances.<br>"
				+ "• Dans certains cas, supprimer temporairement le mod contenant la classe fille peut aider à confirmer la cause.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Tentative d'héritage d'une classe final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Vous utilisez <b>Rubidium</b> (un fork obsolète de Sodium pour Forge) avec <b>Iris ou Oculus</b>.<br>"
				+ "Dans les versions récentes de Minecraft (1.19.2+), "
				+ "Rubidium n'a pas suivi le rythme de Sodium et ses dépendances ont rencontré des problèmes.<br><br>"
				+ "Cette erreur peut également se produire s'il y a un conflit entre des mods de performance (Sodium, Rubidium, Embeddium, Bedium, Xeonium, etc.) ou Iris Shaders et un autre mod.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Supprimez Rubidium</b> de votre dossier <code>mods</code>.<br>"
				+ "• <b>Installez <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "le fork actif et compatible de Sodium pour Forge qui prend en charge Iris/Oculus en 1.20+.<br>"
				+ "• Assurez-vous de ne pas avoir plus d'un fork de Sodium installé en même temps (par exemple : Rubidium + Embeddium).<br>"
				+ "• Si vous utilisez Oculus au lieu d'Iris, vérifiez qu'il est également compatible avec votre version de Forge et Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium obsolète avec Iris/Oculus (OptionInstance est final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Le mod <code>Simple Voice Chat</code> ne peut pas démarrer son serveur vocal car "
				+ "le port UDP est déjà utilisé ou l'adresse IP configurée n'est pas valide.<br>"
				+ "Cela n'empêche pas le jeu de démarrer, mais désactive la fonctionnalité de chat vocal.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Fermez toute autre instance de Minecraft</b> ou application utilisant le port UDP 24454.<br>"
				+ "• Si vous êtes sur un serveur, assurez-vous qu'<b>aucun autre service</b> n'utilise ce port.<br>"
				+ "• Dans la configuration du mod (<code>config/voicechat/</code>), changez le port UDP pour un port libre (par exemple, 24455).<br>"
				+ "• Si vous utilisez une adresse IP personnalisée, vérifiez qu'elle est correcte ou laissez-la vide pour utiliser la valeur par défaut.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Voice Chat : port UDP occupé ou IP invalide";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Le BlockItem <code>" + nombreBlockItem
				+ "</code> a un bloc nul.<br>"
				+ "Cette erreur se produit généralement dans les <b>addons de Create</b> (comme <code>dndecor</code>, <code>createdeco</code>) "
				+ "lorsqu'il y a des conflits avec <code>Amendments</code>, <code>Moonshine</code>, ou une initialisation incorrecte des blocs.<br>"
				+ "<b>Note :</b> Ce n'est pas une erreur directe d'Amendments, mais un symptôme d'un problème plus profond dans le chargement des journaux.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Mettez à jour tous les mods concernés :</b> Create, Amendments, Moonshine, et tout addon (spécialement <code>dndecor</code> et <code>createdeco</code>).<br>"
				+ "• Si le problème persiste, <b>supprimez temporairement les addons de Create</b> un par un pour identifier le coupable.<br>"
				+ "• Assurez-vous que <b>Amendments et Moonshine sont compatibles</b> avec votre version de Create et Forge.<br>"
				+ "• Vérifiez s'il existe des versions bêta ou des forks mis à jour des addons problématiques.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem nul dans un addon de Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>").append(
				"Des mods ont été trouvés qui n'appartiennent à aucune plateforme active (Forge, Fabric, etc.) :<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Cela se produit généralement lorsque :<br>")
				.append("• On mélange des mods <b>Fabric et Forge</b> dans le même dossier.<br>")
				.append("• On installe un mod pour une version incompatible de Minecraft.<br>")
				.append("• Le mod est corrompu ou n'est pas un fichier JAR valide.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Vérifiez que tous les mods sont pour la même plateforme</b> (Forge <b>ou</b> Fabric, pas les deux).<br>"
				+ "• Utilisez l'<b>arbre des mods</b> pour identifier quelle plateforme détecte chaque fichier.<br>"
				+ "• Supprimez tout mod que vous ne reconnaissez pas ou qui provient d'une plateforme différente.<br>"
				+ "• Si vous utilisez un lanceur comme CurseForge ou Prism, assurez-vous que le profil est configuré correctement.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod incompatible avec le chargeur actif";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Impossible de créer le modèle <code>" + modid
				+ ":" + nombreModelo + "</code>.<br>" + "Cela indique que le mod <code>" + modid
				+ "</code> a des ressources corrompues, manquantes "
				+ "ou incompatibles avec votre version de Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Mettez à jour le mod</b> vers la dernière version compatible avec votre instance.<br>"
				+ "• Si vous utilisez une version de développement ou personnalisée, revenez à la version officielle.<br>"
				+ "• Vérifiez que le fichier JAR n'est pas corrompu (réinstallez-le).<br>"
				+ "• Si le problème persiste, signalez l'erreur à l'auteur du mod en incluant ce log.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Échec de la création du modèle de ressource";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Un conflit critique a été détecté entre les mods <code>Moonlight</code> et <code>Iceberg</code>.<br>"
				+ "Les deux tentent d'enregistrer des systèmes de rechargement de ressources de manière incompatible, "
				+ "ce qui provoque un échec OpenGL car il n'y a pas de contexte graphique valide.<br>"
				+ "Ce problème est courant lorsqu'on utilise des versions de Forge qui incluent des adaptateurs de mods Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Mettez à jour les deux mods</b> vers leurs dernières versions compatibles avec votre version de Forge.<br>"
				+ "• Si le problème persiste, <b>supprimez temporairement Iceberg</b>, car Moonlight est souvent une dépendance plus critique pour d'autres mods.<br>"
				+ "• Assurez-vous de ne pas avoir de versions dupliquées ou mélangées de Forge/Fabric de ces mods.<br>"
				+ "• Vérifiez si un autre mod (comme Supplementaries, Citadel, etc.) n'inclut pas déjà la fonctionnalité d'Iceberg en interne.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Conflit critique : Moonlight vs Iceberg (OpenGL sans contexte)";
	}

	@Override
	public String instantanea() {
		return "Instantané";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "Depuis le dernier instantané";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Sélectionner un fichier";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Instantané créé avec succès";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Erreur lors de la création de l'instantané";
	}

	@Override
	public String consejo() {
		return "Conseil";
	}

	@Override
	public String resultadoMuestra() {
		return "Résultat de l'échantillon";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Conseil :</b> Sélectionnez deux fichiers d'historique pour comparer la liste des mods. "
				+ "  Le résultat montre les éléments <span style='color:%s;'>ajoutés (+)</span> et "
				+ "  <span style='color:%s;'>supprimés (&#8722;)</span> basés sur les noms normalisés. "
				+ "  Utilisez le bouton 'Instantané' pour créer une copie d'un fichier existant avec l'extension .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Obtenir les liens vers les journaux en Markdown sans rapport";
	}

	@Override
	public String titulo_configuracion() {
		return "Configuration";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Erreur inattendue lors du partage.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Erreur inattendue lors de la génération des liens.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Erreur inattendue lors du traitement du bouton.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Aucun fichier associé à ouvrir.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "Le fichier n'existe pas :\\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Impossible d'ouvrir dans un éditeur.\\nLe chemin sera copié dans le presse-papiers.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Impossible d'ouvrir le fichier ; le chemin a été copié dans le presse-papiers.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Le bureau n'est pas pris en charge ; le chemin a été copié dans le presse-papiers.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Vous avez atteint une limite de requêtes. Essayez d'utiliser un autre site de journaux ou une autre API de journaux.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Partager le lien";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Corriger les parties supérieures des traces est la première priorité. "
				+ "Le format est Niveau, Ligne. " + "Tous les journaux ont un système de numérotation. "
				+ Verificaciones.nl_html
				+ "Généralement, vous devez rechercher dans les niveaux les plus bas de tous les journaux ; les traces avec des niveaux élevés sont souvent des faux positifs. "
				+ "Il est important d'utiliser votre capacité à lire la console, car l'analyse des traces n'est pas parfaite lorsqu'il y a beaucoup de traces."
				+ "</b>";
	}

	// --- Buscador de Canario de Orden (Warrant Canary) ---

	/**
	 * Texto del botón para el buscador de canario de orden. Ejemplo: "Buscador de
	 * canario de orden"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Recherche de Warrant Canary";
	}

	/**
	 * Mensaje mostrado en el cuadro de diálogo informando que la función estará
	 * disponible próximamente.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Cette fonctionnalité sera bientôt disponible.";
	}

	/**
	 * Título del cuadro de diálogo que informa sobre la disponibilidad futura del
	 * buscador de canario de orden.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Bientôt disponible";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Mods incompatibles avec Crash Assistant (Faux)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Mod incompatible avec le pack de mods (Modpack) utilisant CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant possède une liste de mods qu'il dit incompatibles, mais nous n'avons aucune preuve qu'ils le soient et l'erreur n'est qu'en anglais. Si vous souhaitez jouer avec ces mods, vous pouvez éditer le fichier <code>config/crash_assistant/config.toml</code> et changer <code>enabled = true</code> dans la section [compatibility] en <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Crash Assistant a la capacité de marquer des mods comme incompatibles, mais parfois c'est incorrect et le message d'erreur n'est disponible qu'en anglais. Si vous souhaitez jouer avec ces mods, vous pouvez éditer le fichier <code>config/crash_assistant/problematic_mods_config.json</code> et changer <code>should_crash_on_startup</code> de <code>true</code> à <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erreur : Le mod '" + modId
				+ "' nécessite le mod '" + dependencia + "'. Actuellement, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erreur : Le mod '" + modId
				+ "' nécessite la version '" + requerido + "' ou supérieure de '" + dependencia
				+ "', mais le mod n'est pas installé." + "</span>";
	}

	// En la clase MonitorDePID.idioma (añadir este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erreur : Le mod '" + modId
				+ "' est incompatible avec la version actuelle de '" + dependencia + "'. "
				+ "Vous devez supprimer le mod 'Iris/Oculus & GeckoLib Compat' car il est incompatible avec Superb Warfare et ne fonctionne pas avec la dernière version de GeckoLib. "
				+ "Version actuelle : " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Erreur : Impossible d'exécuter la tâche pour la classe '" + clase + "'. "
				+ "Cette erreur est courante avec des mods qui ne sont pas compatibles entre eux ou qui ont des conflits avec d'autres mods installés.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Échecs d'exécution des tâches";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Ce type d'erreur se produit généralement lorsqu'il y a des incompatibilités entre les mods. "
				+ "Particulièrement courant avec les mods qui ne fonctionnent pas correctement avec ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Classe problématique :";
	}

	public String no_se_encontraron_clases_problema() {
		return "Aucune classe spécifique avec des problèmes d'exécution n'a été trouvée.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre OptiFine et Entity Model Features (EMF). "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Conflit OptiFine et Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Désinstallez OptiFine ou Entity Model Features, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre OptiFine et Fusion. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Conflit OptiFine et Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Désinstallez OptiFine ou Fusion, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Flywheel (requis par Create) nécessite Sodium 0.6.0-beta.2 ou supérieur. Rubidium est en 0.5.3. "
				+ "Envisagez d'utiliser <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> comme alternative."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Conflit Flywheel et version de Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Mettez à jour Sodium vers la version 0.6.0-beta.2 ou supérieure, ou installez <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> comme alternative compatible.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre OptiFine et Epic Fight. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Conflit OptiFine et Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Désinstallez OptiFine ou Epic Fight, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre OptiFine et Rubidium. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Conflit OptiFine et Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Désinstallez OptiFine ou Rubidium, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam tente de se charger sur un serveur dédié, mais n'est compatible qu'avec le client. "
				+ "Supprimez FreeCam du serveur ou assurez-vous qu'il n'est installé que sur le client." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam sur serveur dédié";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Supprimez FreeCam du serveur dédié, car il ne doit être installé que sur le client.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) tente de se charger sur un serveur dédié, mais n'est compatible qu'avec le client. "
				+ "Supprimez ETF du serveur ou assurez-vous qu'il n'est installé que sur le client." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features sur serveur dédié";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Supprimez Entity Texture Features du serveur dédié, car il ne doit être installé que sur le client.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Vous devez accepter l'EULA de Minecraft pour exécuter le serveur. "
				+ "Éditez le fichier eula.txt et changez 'eula=false' en 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA de Minecraft non accepté";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Éditez le fichier eula.txt dans le dossier du serveur et changez 'eula=false' en 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine tente de se charger sur un serveur dédié, mais n'est compatible qu'avec le client. "
				+ "Supprimez OptiFine du serveur ou assurez-vous qu'il n'est installé que sur le client." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine sur serveur dédié";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Supprimez OptiFine du serveur dédié, car il ne doit être installé que sur le client. Ce problème est aussi souvent dû à l'installation d'une version d'Optifine pour la mauvaise version de Minecraft ou à un conflit entre un autre mod et Optifine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks est incorrectement marqué pour la 1.20.1 mais utilise des méthodes de la 1.21.1. "
				+ "Le mod tente d'utiliser ResourceLocation.fromNamespaceAndPath, qui n'existe pas en 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Erreur de version dans Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Assurez-vous d'utiliser la version correcte d'Iron's Spellbooks compatible avec votre version de Minecraft.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre OptiFine et Embeddium. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Conflit OptiFine et Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Désinstallez OptiFine ou Embeddium, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>C'est courant avec des mods de génération de monde conflictuels, surtout Terralith, Amplified Nether, Nullscape et Incendium, ainsi que d'autres mods de génération de monde. Il est aussi possible que vous deviez installer un mod manquant.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable tente de se charger sur un serveur dédié, mais n'est compatible qu'avec le client. "
				+ "Supprimez Controllable du serveur ou assurez-vous qu'il n'est installé que sur le client." + "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable sur serveur dédié";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Supprimez Controllable du serveur dédié, car il ne doit être installé que sur le client.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries provoque une erreur qui empêche le chargement du serveur. "
				+ "Le mod a des problèmes avec le journal des comportements de feu qui provoquent un échec lors du chargement des datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries empêche le chargement du serveur";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Essayez de mettre à jour Supplementaries vers la dernière version ou désactivez-le temporairement pour permettre le chargement du serveur.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) a rencontré un problème avec des modules Jackson manquants. "
				+ "Certains mods comme Valkyrien Skies peuvent causer cette erreur en n'incluant pas toutes les dépendances nécessaires."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Module Jackson manquant dans Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Supprimez Groovy Modloader et les mods associés comme Valkyrien Skies qui peuvent causer des conflits de dépendances.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat a rencontré un nom de bloc de bois invalide. "
				+ "Every Compat a généralement beaucoup de problèmes. Ne l'utilisez pas !" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Nom invalide dans Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Vérifiez les packs de ressources ou les mods qui utilisent Every Compat, car ils peuvent avoir des noms de blocs invalides.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un code d'erreur (-1073741819) a été détecté, qui peut être causé par des overlays comme GameCaster de Razer, Discord, OBS Studio ou des problèmes avec les pilotes NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Code d'erreur -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Essayez de désactiver les overlays comme GameCaster, Discord ou OBS Studio, et vérifiez que vos pilotes NVIDIA sont à jour.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips nécessite Immersive Messages comme dépendance, mais il n'est pas installé. "
				+ "Installez Immersive Messages pour qu'Immersive Tooltips fonctionne correctement." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips sans dépendance";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Installez Immersive Messages, car c'est une dépendance nécessaire pour Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins a un problème de compatibilité avec Apoli Mod où ItemStack ne peut pas être casté en EntityLinkedItemStack. "
				+ "C'est courant dans les versions supérieures à 6.6.0. Envisagez d'utiliser une version antérieure ou de changer entre les versions Fabric et Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Erreur de cast dans Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Utilisez une version de Medieval Origins 6.6.0 ou antérieure, ou essayez de changer entre les versions Fabric et Forge du mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether provoque une erreur avec un Registry Object absent dans MusicManager. "
				+ "Ce problème est lié au mixin de MusicManager de Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Erreur de MusicManager dans Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Essayez de mettre à jour Reign of Nether ou de le supprimer temporairement pour résoudre l'erreur.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel ne supporte le serveur YSM que sous Linux ou Android. "
				+ "Ce problème a été corrigé dans les versions plus récentes depuis le 23 novembre 2025 sur Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel non compatible avec Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Mettez à jour YesSteveModel vers une version plus récente depuis Modrinth, car le problème a été corrigé après le 23 novembre.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre Moving Elevators et OptiFine. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Conflit Moving Elevators et OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Désinstallez OptiFine ou Moving Elevators, car ils ne sont pas compatibles entre eux.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit critique a été détecté entre Fabric API (fabric-resource-loader-v0) et OptiFine. "
				+ "Ces mods ne sont pas compatibles et provoquent un échec d'injection qui empêche le démarrage du jeu."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Conflit Fabric API et OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Désinstallez OptiFine ou mettez à jour Fabric API vers une version compatible.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un mod possède un ITransformationService défectueux qui ne peut pas être instancié : "
				+ claseProveedor + ". " + "Ce mod doit être supprimé pour permettre le chargement du jeu." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService défectueux";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Supprimez le mod contenant la classe " + claseProveedor
				+ ", car il possède un ITransformationService défectueux.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Un mod a une spécification de version invalide. " + "La version doit être entourée de crochets. "
				+ "Vous pouvez utiliser l'utilitaire grep/greprf du panneau latéral en cherchant la version </span>"
				+ version + "<span style='color:#" + config.obtenerColorError()
				+ "'> pour identifier quel mod pose problème.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Version invalide dans un mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Utilisez l'utilitaire grep/greprf du panneau latéral pour rechercher la version problématique et trouvez le mod qui la contient.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Une erreur de stack smashing a été détectée, mettant fin au processus. "
				+ "Cela peut être causé par des problèmes avec Early Window dans Forge/NeoForge/PillowMC ou avec LWJGL 3.2.2 et plus récent."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing détecté";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Vérifiez les paramètres de Early Window et envisagez d'utiliser une version différente de LWJGL ou de revoir les mods liés à la fenêtre initiale.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore est destiné uniquement à un modpack spécifique et ne doit pas être utilisé dans des installations générales, car cela cause un problème."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore avec version Java incompatible";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Supprimez GregTechEasyCore, car il est destiné uniquement à un modpack spécifique et n'est pas compatible avec votre installation générale.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit a été détecté entre MoniLabs et Connector Extras concernant les modifications de KubeJS. "
				+ "Ces mods ne sont pas compatibles dans leurs modifications de KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Conflit MoniLabs et Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Essayez de désinstaller l'un des mods (MoniLabs ou Connector Extras), car ils entrent en conflit avec leurs modifications de KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iris nécessite Distant Horizons [2.0.4] ou DH API version [1.1.0] ou plus récente. "
				+ "Consultez le guide de compatibilité sur https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e pour résoudre le problème."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Compatibilité Iris et Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Consultez le guide de compatibilité sur https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e et mettez à jour Iris et Distant Horizons vers des versions compatibles.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes Minecraft manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vous avez des mods pour d'autres versions du jeu. Vous pouvez utiliser <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> pour vérifier si la classe existe dans votre version.</li>"
				+ "<li>Vous avez une installation défectueuse de Minecraft (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "<li>Vous avez un coremod défectueux (si un coremod échoue, il peut bloquer le chargement de la classe).</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes DangerZone manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vous avez des mods pour d'autres versions du jeu.</li>"
				+ "<li>Vous avez des coremods défectueux.</li>"
				+ "<li>Vous avez un lanceur ou une installation défectueuse.</li>" + "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes FeatureCreep manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vous avez des mods pour d'autres versions de FeatureCreep (ex. : ESR vs Nightly ou v4 vs v12).</li>"
				+ "<li>Vous pouvez installer FeatureCreep depuis CurseForge ou MinecraftStorage.</li>" + "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes ModLauncher manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vos mods sont pour une build différente de MinecraftForge, PillowMC ou NeoForge (ModLauncher est utilisé avec ces chargeurs).</li>"
				+ "<li>Il y a beaucoup de mises à jour de chargeurs de mods pour une version de Minecraft.</li>"
				+ "<li>Vous avez une installation défectueuse de votre lanceur (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes Minecraft Forge manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vos mods sont pour une build différente de MinecraftForge.</li>"
				+ "<li>Il y a beaucoup de mises à jour de chargeurs de mods pour une version de Minecraft.</li>"
				+ "<li>Vous avez une installation défectueuse (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes NeoForge manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vos mods sont pour une build différente de NeoForge.</li>"
				+ "<li>Il y a beaucoup de mises à jour de chargeurs de mods pour une version de Minecraft.</li>"
				+ "<li>Vous avez une installation défectueuse (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes Fabric Loader manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vos mods sont pour une build différente de Fabric Loader.</li>"
				+ "<li>Il y a beaucoup de mises à jour de chargeurs de mods pour une version de Minecraft.</li>"
				+ "<li>Vous avez une installation défectueuse (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "<li>De nombreux mods nécessitent Fabric API. Veuillez installer Fabric API si nécessaire.</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Vous avez des classes PillowMC manquantes. Raisons possibles :</b>" + "<ul>"
				+ "<li>Vos mods sont pour une build différente de PillowMC.</li>"
				+ "<li>Il y a beaucoup de mises à jour de chargeurs de mods pour une version de Minecraft.</li>"
				+ "<li>Vous avez une installation défectueuse (courant avec CurseForge App, ModrinthApp/Theseus/Astralrinth et autres lanceurs de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Voir le tutoriel</a> pour résoudre les problèmes avec CurseForge.</li>"
				+ "</ul>"
				+ "<p>Note : Vous pouvez utiliser l'outil <b>grepr/fgrepr</b> dans la barre latérale pour trouver les mods qui font référence aux classes manquantes, à condition d'utiliser '/' dans les noms.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Vous avez un mod qui cause intentionnellement du lag. Uranium est un mod de lag. Il ne cause pas toujours de crash, mais peut éventuellement le faire."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack est marqué comme compatible avec 1.19.* mais est destiné à 1.20.*, ce qui cause une erreur de classe introuvable. "
				+ "Le mod tente d'utiliser DamageSources qui n'existent pas dans la version actuelle de Minecraft."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Erreur de version dans Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Assurez-vous d'utiliser la version correcte de Falling Attack compatible avec votre version de Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Vous devez installer CFR (Class File Reader) pour utiliser cette fonctionnalité.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append(
					"Sur les systèmes Linux, NetBSD ou FreeBSD, vous pouvez installer CFR depuis votre gestionnaire de paquets.<br>")
					.append("Recherchez le paquet sur : <a href=\\\"https://pkgs.org/search/?q=cfr\\\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append(
				"Alternativement, vous pouvez télécharger la version modifiée utilisée par FabricMC depuis :<br>")
				.append("<a href=\\\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\\\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Enregistrez-le dans le dossier suivant :<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Important :</b> après avoir installé CFR, vous devez redémarrer le mod pour qu'il le reconnaisse correctement.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Aucun portrait disponible";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Impossible de trouver la classe : " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Décompilateur CFR – Sakura Riddle (Non officiel)";
	}

	@Override
	public String cfrClaseActual() {
		return "Classe actuelle";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Portrait de Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Erreur lors du chargement du portrait";
	}

	public String noticiaLegalCFR() {
		return "Cette interface graphique (GUI) pour décompiler les mods est conçue pour aider les utilisateurs à identifier les causes des pannes logicielles. "
				+ "Cependant, la décompilation de mods peut être nécessaire, et les utilisateurs doivent veiller à ne pas utiliser le code généré pour enfreindre les lois locales sur le droit d'auteur. "
				+ "Il est recommandé de consulter la licence du mod correspondant avant d'utiliser tout code obtenu. De plus, les mods fournissent souvent leur code source officiellement, "
				+ "qui est généralement plus propre et plus facile à comprendre que le code décompilé. Rappelez-vous que le respect de la propriété intellectuelle et des licences d'utilisation est fondamental pour "
				+ "la communauté de développement de mods. Vous pouvez consulter les lois sur le droit d'auteur applicables dans votre juridiction. "
				+ "Notre GUI est uniquement destinée à des vérifications simples ; pour une analyse plus avancée, vous devriez utiliser le Fork Enigma de FabricMC disponible sur "
				+ "<a href=\\\"https://github.com/FabricMC/Enigma\\\" target=\\\"_blank\\\">GitHub</a>. Si vous souhaitez éditer des fichiers JAR pour créer des correctifs lorsque le code source n'est pas disponible, vous pouvez utiliser Recaf sur "
				+ "<a href=\\\"https://recaf.coley.software/home.html\\\" target=\\\"_blank\\\">son site web</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Télécharger CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Ouvrir le dossier d'installation";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Couleur de fond principal";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Couleur du texte du bouton de réinitialisation";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Couleur du texte du champ de recherche";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Couleur du texte du menu déroulant de filtre";
	}

	@Override
	public String colorTextoRenderer() {
		return "Couleur du texte du moteur de rendu";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Couleur du texte de l'overlay de chargement";
	}

	@Override
	public String colorBorde() {
		return "Couleur de la bordure";
	}

	@Override
	public String colorFondoRetrato() {
		return "Couleur de fond en mode portrait";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Couleur du lien de partage";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Couleur de fond du champ de partage";
	}

	@Override
	public String rosaFondo() {
		return "Rose de fond";
	}

	@Override
	public String rosaSuave() {
		return "Rose doux";
	}

	@Override
	public String moradoAcento() {
		return "Violet d'accentuation";
	}

	@Override
	public String textoOscuro() {
		return "Texte sombre";
	}

	@Override
	public String bordeSuave() {
		return "Bordure douce";
	}

	@Override
	public String fondoCampo() {
		return "Fond du champ";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Fond de l'aperçu";
	}

	@Override
	public String sintaxisConstructor() {
		return "Couleur de syntaxe : constructeur";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Couleur de syntaxe : message d'aide";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Couleur de syntaxe : balises HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Couleur de fond de la fenêtre";
	}

	@Override
	public String colorPanel() {
		return "Couleur du panneau";
	}

	@Override
	public String colorBotonTexto() {
		return "Couleur du texte du bouton";
	}

	@Override
	public String colorCampo() {
		return "Couleur du champ";
	}

	@Override
	public String colorBordeDestacado() {
		return "Couleur de la bordure mise en évidence";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Couleur de fond de la sélection de texte";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Couleur du texte sélectionné";
	}

	@Override
	public String colorEstadoExito() {
		return "Couleur d'état : succès";
	}

	@Override
	public String colorEstadoFallo() {
		return "Couleur d'état : échec";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Couleur d'état : instantané";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Couleur du résultat ajouté";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Couleur du résultat supprimé";
	}

	@Override
	public String colorBordeScroll() {
		return "Couleur de la bordure de défilement";
	}

	@Override
	public String colorFondoPanel() {
		return "Couleur de fond du panneau";
	}

	@Override
	public String colorBeigeListas() {
		return "Beige des listes";
	}

	@Override
	public String colorTextoListas() {
		return "Couleur du texte dans les listes";
	}

	@Override
	public String colorBordeListas() {
		return "Couleur de la bordure des listes";
	}

	@Override
	public String colorBotonFondo() {
		return "Couleur de fond du bouton";
	}

	@Override
	public String colorBordeBoton() {
		return "Couleur de la bordure du bouton";
	}

	@Override
	public String colorDoradoTexto() {
		return "Couleur dorée du texte";
	}

	@Override
	public String colorPila() {
		return "Couleur de la pile d'exécution (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Couleur du texte du panneau";
	}

	@Override
	public String colorTextoNegro() {
		return "Couleur du texte noir";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Couleur du texte principal";
	}

	@Override
	public String colorFondoResultados() {
		return "Couleur de fond des résultats";
	}

	@Override
	public String colorEstado() {
		return "Couleur d'état";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Couleur du texte de description";
	}

	@Override
	public String colorTextoEstado() {
		return "Couleur du texte d'état";
	}

	@Override
	public String colorTextoExtra() {
		return "Couleur du texte supplémentaire";
	}

	@Override
	public String colorSeparador() {
		return "Couleur du séparateur";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Une erreur native <code>StubRoutines::SafeFetch32</code> a été détectée. "
				+ "Ce problème survient sur macOS avec JDK 17.0.9 et est corrigé dans JDK 17.0.10 ou les versions ultérieures. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Erreur native SafeFetch32 dans JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Mettez à jour votre JDK vers la version 17.0.10 ou supérieure (par exemple, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Si vous utilisez un lanceur comme MultiMC, Prism Launcher ou TLauncher, configurez-le pour utiliser un JDK plus récent. "
				+ "Certains incluent déjà JDK 17.0.15 intégré.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "Le mod Spark peut également contribuer à cette erreur. Envisagez de le désactiver temporairement. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Le mod MCEF (Chromium Embedded Framework) provoque un blocage silencieux.</b>" + "<ul>"
				+ "<li>MCEF s'initialise à la fin du journal, ce qui signifie généralement que le jeu s'est bloqué pendant le chargement.</li>"
				+ "<li>Ce mod est connu pour causer des pannes sur les systèmes Linux, macOS ou avec certaines versions de Java.</li>"
				+ "<li>Aucune erreur explicite n'apparaît toujours, mais le jeu n'atteint jamais le menu principal.</li>"
				+ "</ul>"
				+ "<p>Si vous n'avez pas besoin de fonctionnalités de navigateur dans le jeu (comme des cartes web ou des pages intégrées), supprimez le mod.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Problème d'initialisation de MCEF (mod de navigateur embarqué)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Supprimez le fichier du mod MCEF (cherchez 'mcef' dans le nom du fichier) du dossier 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Si vous en avez vraiment besoin, assurez-vous d'utiliser une version compatible avec votre système d'exploitation et votre version de Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit a été détecté entre <b>OptiFine</b> et <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>OptiFine modifie le rendu de Minecraft d'une manière incompatible avec Iris ou Oculus.</li>"
				+ "<li>L'erreur <code>MixinLevelRenderer failed injection check</code> provient de <code>mixins.iris.json</code> ou <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Ces mods ne peuvent pas être utilisés ensemble. Supprimez OptiFine pour utiliser des shaders avec Iris ou Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Conflit entre OptiFine et Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Supprimez le fichier d'OptiFine du dossier 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Utilisez Iris ou Oculus sans OptiFine pour les shaders modernes.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Un conflit a été détecté entre <b>ModernFix</b> et <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix n'est pas compatible avec OptiFine car il casse des fonctionnalités de Forge et ralentit le démarrage.</li>"
				+ "<li>ModernFix lui-même avertit : <i>\\\"Use of ModernFix with OptiFine is not supported\\\"</i>.</li>"
				+ "</ul>" + "<p>Vous devez supprimer l'un des deux mods pour que le jeu fonctionne correctement.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Conflit entre ModernFix et OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Supprimez OptiFine ou ModernFix du dossier 'mods'. Ils ne peuvent pas être utilisés ensemble.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Si vous avez besoin d'optimisations, envisagez d'utiliser uniquement OptiFine, ou remplacez ModernFix par des mods plus légers comme FerriteCore ou EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erreur : clé de registre invalide avec des caractères non autorisés.</b>" + "<ul>"
				+ "<li><b>Clé détectée :</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>Dans Minecraft, toutes les clés de registre (tags, recettes, succès, etc.) doivent être en <b>minuscules</b> et utiliser uniquement des lettres, des chiffres, des underscores, des tirets et des barres obliques.</li>"
				+ "<li>Cette erreur est généralement causée par un mod mal programmé ou un datapack défectueux.</li>"
				+ "</ul>"
				+ "<p><b>Conseil important :</b> Utilisez l'outil <b>grepr</b> ou <b>fgrepr</b> dans la barre latérale et activez l'option <b>\\\"Rechercher dans les fichiers JAR\\\"</b> pour trouver quel mod contient cette clé incorrecte.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Clé de registre avec majuscules ou caractères invalides";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Utilisez 'grepr' ou 'fgrepr' avec l'option \\\"Rechercher dans les fichiers JAR\\\" pour localiser le mod coupable.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Si vous ne pouvez pas identifier le mod, supprimez les mods récents, en particulier ceux qui ajoutent des blocs, des objets ou des outils.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erreur lors du chargement du mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>Le mod a échoué lors de l'initialisation de l'un de ses composants (par exemple, le menu de configuration).</li>"
				+ "<li>Cela se produit généralement en raison d'une incompatibilité avec la version de Minecraft, Fabric ou avec d'autres mods.</li>"
				+ "</ul>" + "<p>Si l'erreur persiste, supprimez ou mettez à jour le mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Erreur d'initialisation du mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Supprimez le mod '" + modNombre + "' du dossier 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Mettez à jour le mod '" + modNombre + "' vers une version compatible avec votre installation.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Une erreur liée au mod <b>En Garde!</b> a été détectée.</b>" + "<ul>"
				+ "<li>Ce mod ajoute des mécaniques de combat au corps à corps (parade, blocage, etc.).</li>"
				+ "<li>L'erreur se produit généralement en raison d'une incompatibilité avec d'autres mods de combat (comme Epic Fight, DualRiders, etc.) ou en utilisant une version incorrecte pour votre Minecraft.</li>"
				+ "</ul>"
				+ "<p>Si vous n'utilisez pas de combat avancé, envisagez de supprimer En Garde! pour éviter les conflits.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Erreur dans le mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Assurez-vous d'utiliser la version de En Garde! compatible avec votre version de Minecraft et votre chargeur (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Si vous utilisez d'autres mods de combat (Epic Fight, Caelus, etc.), désactivez-les ou supprimez En Garde! pour éviter les conflits.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Une erreur causée par le mod <b>IdleTweaks</b> a été détectée.</b>" + "<ul>"
				+ "<li>IdleTweaks a tenté de libérer un canal réseau qui n'existe plus (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Cette erreur se produit souvent sur d'anciennes versions du mod ou lorsqu'il est utilisé sur des serveurs mal configurés.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks est un mod d'amélioration de la qualité de vie, mais il peut causer de l'instabilité. Envisagez de le mettre à jour ou de le supprimer.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Erreur dans IdleTweaks (canal réseau inconnu)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Mettez à jour IdleTweaks vers la dernière version compatible avec votre Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Supprimez IdleTweaks du dossier 'mods' si vous n'en avez pas besoin.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Une erreur d'authentification (HTTP 401) a été détectée lors de la tentative de connexion à Minecraft.</b>"
				+ "<p>Cette erreur <b>rarement la cause directe du crash</b>, mais indique que vous utilisez un compte non authentifié (pirate).</p>"
				+ "<p>Les canaux de support officiels (projets d'entreprise, VTubers, créateurs de modpacks, etc.) <b>ne peuvent pas vous aider</b> si vous utilisez une copie piratée, "
				+ "en raison de restrictions dans leurs règles de chat, contrats, accords avec Mojang/Microsoft, ou politiques de réputation.</p>"
				+ "<p>Cette vérification peut être <b>désactivée dans la configuration corporative</b> du détecteur. "
				+ "Avertissement : la détection anti-piratage <b>n'est pas parfaite</b> et peut se déclencher dans des environnements de développement, avec une connexion Internet instable, ou lors de l'utilisation de lanceurs modifiés.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Droits Miranda si vous essayez quand même de rejoindre le support :</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft piraté";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Désactiver la vérification anti-piratage";
	}

	@Override
	public String comprarMC() {
		return "Acheter Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Vous utilisez le lanceur <code>" + id
				+ "</code>, qui <b>n'est pas dans la liste des lanceurs recommandés</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Bien qu'il puisse fonctionner, les lanceurs non recommandés causent souvent :</p>" + "<ul>"
				+ "<li>Des installations corrompues de mods ou de l'application.</li>"
				+ "<li>Le jeu ne démarre pas ou se bloque sans erreur claire.</li>"
				+ "<li>Une structure de dossiers inhabituelle (ce qui rend le diagnostic difficile).</li>"
				+ "<li>Un comportement imprévisible avec Java, la mémoire ou les mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Pour une meilleure expérience, utilisez l'un de ces lanceurs recommandés :";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Lanceur non recommandé";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Passez à un lanceur de la liste recommandée.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Vous utilisez un <b>lanceur déconseillé</b> : <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Les lanceurs déconseillés peuvent causer :</p>" + "<ul>"
				+ "<li>Des installations corrompues de l'application ou des mods.</li>"
				+ "<li>Le jeu ne démarre pas ou échoue silencieusement.</li>"
				+ "<li>Une organisation inhabituelle des fichiers (difficile à déboguer).</li>"
				+ "<li>Une incertitude sur la gestion des mods, de Java ou de la mémoire.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Il est fortement recommandé d'utiliser l'un des lanceurs suivants :";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Lanceur déconseillé";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Passez à un lanceur recommandé pour recevoir du support.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Des mods recommandés pour cet environnement sont manquants.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Mods recommandés manquants";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Installez les mods recommandés pour une expérience optimale.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Des mods déconseillés ont été détectés dans votre installation.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Mods déconseillés détectés";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Supprimez les mods déconseillés pour éviter les problèmes.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Une manipulation non autorisée de fichiers critiques a été détectée. Vous avez modifié des fichiers ou vous utilisez un lanceur non fiable.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Manipulation détectée";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Réinstallez les fichiers originaux pour restaurer l'intégrité.";
	}

	@Override
	public String configuracionCorporativa() {
		return "Configuration Corporative";
	}

	@Override
	public String idiomaRespaldo() {
		return "Langue de secours";
	}

	@Override
	public String buscardorHabilitado() {
		return "Activer le chercheur";
	}

	@Override
	public String nombreHerramienta() {
		return "Nom de l'outil";
	}

	@Override
	public String condenarPirateria() {
		return "Condamner le piratage";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "Lanceurs Recommandés";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "Lanceurs Déconseillés";
	}

	@Override
	public String modsRecomendados() {
		return "Mods Recommandés";
	}

	@Override
	public String modsDesaconsejados() {
		return "Mods Déconseillés";
	}

	@Override
	public String antiTamper() {
		return "Anti-Tamper";
	}

	@Override
	public String proximamente() {
		return "Bientôt disponible";
	}

	@Override
	public String informacion() {
		return "Information";
	}

	@Override
	public String errorCargandoImagen() {
		return "Erreur lors du chargement de l'image";
	}

	@Override
	public String configuracionBasica() {
		return "Configuration de base";
	}

	@Override
	public String funcionalidades() {
		return "Fonctionnalités";
	}

	@Override
	public String derechosMiranda() {
		return "Droits Miranda (TRÈS recommandés)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Gestion des vérifications";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Nom";
	}

	@Override
	public String codigoVerificacion() {
		return "Code";
	}

	@Override
	public String documentacionVerificacion() {
		return "Documentation";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Vérifications activées :";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Vérifications désactivées :";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Désactiver toutes les non-corporatives";
	}

	@Override
	public String verCodigo() {
		return "Voir le code";
	}

	@Override
	public String verDocumentacion() {
		return "Voir la documentation";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Sélectionnez une vérification à désactiver.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Sélectionnez une vérification à activer.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "%d vérifications non recommandées pour un usage corporatif ont été désactivées.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Il n'y a pas de vérifications non-corporatives à désactiver.";
	}

	@Override
	public String operacionCompletada() {
		return "Opération terminée";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Tu nous manques, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Couleur de vérification corporative";
	}

	// Métodos para la gestión de lanzadores
	@Override
	public String nombreLanzador() {
		return "Nom du lanceur";
	}

	@Override
	public String motivo() {
		return "Raison";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Lanceurs déconseillés";
	}

	@Override
	public String moverADesaconsejados() {
		return "Déconseiller";
	}

	@Override
	public String moverARecomendados() {
		return "Recommander";
	}

	@Override
	public String guardarCambios() {
		return "Enregistrer les modifications";
	}

	@Override
	public String cancelar() {
		return "Annuler";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Veuillez sélectionner un lanceur à déplacer.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "Les modifications ont été enregistrées avec succès !";
	}

	@Override
	public String motivoDesaconsejoPredeterminadoEs(String nombreLanzador) {
		return "Ce lanceur n'est pas recommandé en raison de problèmes de sécurité et de stabilité connus.";
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
		return "Raisons";
	}

	@Override
	public String agregarLanzador() {
		return "Ajouter un lanceur";
	}

	@Override
	public String quitarLanzador() {
		return "Supprimer le lanceur";
	}

	@Override
	public String editarRazones() {
		return "Modifier les raisons";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Sélectionnez un lanceur à supprimer.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Sélectionnez un lanceur à modifier.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Modifier les raisons pour " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Ajouter une nouvelle langue";
	}

	@Override
	public String aceptar() {
		return "Accepter";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Sélectionnez la langue";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Ces lanceurs sont ceux que CrashDetector suggère comme étant bons.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Résultat correct";
	}

	public String modsNoRecomendados() {
		return "Mods déconseillés";
	}

	public String agregarMod() {
		return "Ajouter un mod";
	}

	public String quitarMod() {
		return "Supprimer le mod";
	}

	public String modId() {
		return "ID du mod / Nom JBoss Modules";
	}

	public String rutaMod() {
		return "Chemin / fichier du mod";
	}

	public String errorDebeIndicarMod() {
		return "Vous devez indiquer au moins l'ID du mod ou le chemin du mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Ici, vous pouvez enregistrer les mods déconseillés afin que CrashDetector les détecte s'ils sont installés.";
	}

	@Override
	public String anularNormal() {
		return "Annuler la normale";
	}

	@Override
	public String anularNormalDescripcion() {
		return "CrashDetector devrait avertir même s'il n'y a pas de crash";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Enregistrez les mods que CrashDetector recommande. S'ils manquent, CrashDetector peut avertir.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Si vous décidez d'activer l'avertissement anti-piratage, il est recommandé de définir ici "
				+ "les droits de la personne qui demande de l'aide, à titre préventif.\\n\\n"

				+ "Contrairement à une croyance commune, de nombreuses communautés et canaux d'assistance populaires "
				+ "NE nécessitent PAS l'activation des avertissements anti-piratage pour fournir de l'aide. Cependant, "
				+ "documenter ces droits peut être utile si une personne accède quand même au canal "
				+ "d'assistance.\\n\\n"

				+ "Vous pouvez vous baser sur des documents officiels comme la Charte des Droits Fondamentaux du Détenu "
				+ "au Mexique :\\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\\n\\n"

				+ "Ainsi que sur des principes juridiques comparables utilisés dans d'autres pays, y compris "
				+ "les États-Unis, la Fédération de Russie, la République populaire de Chine, la République islamique "
				+ "d'Iran et la République populaire démocratique de Corée.\\n\\n"

				+ "Quelques exemples de droits qui peuvent être inclus sont :\\n"
				+ "• Le droit de ne pas fournir d'informations inutiles pour l'assistance, comme le lanceur utilisé, "
				+ "le nom d'utilisateur ou l'UUID.\\n" + "• Le droit de ne pas s'auto-incriminer.\\n"
				+ "• Le droit de refuser de répondre à des questions qui ne sont pas nécessaires à la résolution du problème.\\n"
				+ "• Le droit de recevoir des orientations dans le chat.\\n"
				+ "• Le droit d'utiliser l'anonymisation des journaux (logs) intégrée à CrashDetector.\\n\\n"

				+ "Ce texte accepte le contenu HTML.";
	}

	@Override
	public String editar() {
		return "Modifier";
	}

	@Override
	public String advertenciaHashLento() {
		return "Avertissement : l'ajout de nombreux fichiers volumineux peut faire prendre plusieurs minutes à la vérification. "
				+ "CrashDetector devra calculer le hachage de chaque fichier avant de continuer. "
				+ "Il est recommandé de ne protéger que les fichiers strictement nécessaires.";
	}

	@Override
	public String agregarArchivo() {
		return "Ajouter un fichier";
	}

	@Override
	public String agregarCarpeta() {
		return "Ajouter un dossier";
	}

	@Override
	public String quitar() {
		return "Supprimer";
	}

	@Override
	public String rutaArchivo() {
		return "Chemin du fichier";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "Le chemin sélectionné est en dehors du répertoire actuel du jeu. "
				+ "Seuls les fichiers et dossiers situés dans le répertoire actuel ou ses sous-répertoires sont autorisés.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "Les opinions et commentaires de Sylent Bell ne coïncident pas nécessairement avec les nôtres ; "
				+ "nous pensons juste qu'il serait amusant de la mettre ici. CrashDetector est laïc." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Le mod GML (Groovy ModLoader) nécessite ces changements et est la source la plus courante de ce problème.</b>";
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
				+ "L'utilisation de <i>Flywheel Indépendant</i> a été détectée.</b>"
				+ "<p><b>Flywheel Indépendant est obsolète (deprecated)</b> et ne doit pas être utilisé dans les versions modernes.</p>"
				+ "<p>Les versions actuelles de <b>Create</b> <b>incluent déjà Flywheel</b>, donc l'installer séparément "
				+ "provoque des conflits de compatibilité et des erreurs de chargement.</p>"
				+ "<p>Certains mods qui dépendent explicitement de Flywheel Indépendant peuvent "
				+ "<b>ne pas fonctionner</b> ou <b>fonctionner de manière instable</b>. "
				+ "Dans certains cas avancés, ces mods peuvent fonctionner si vous "
				+ "<b>modifiez manuellement le fichier <code>mods.toml</code></b> pour ajuster les plages de version, "
				+ "bien que cela <b>ne soit pas recommandé</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mods détectés faisant référence à Flywheel :</b></p>" + "<ul>" + listaMods.toString()
								+ "</ul>")
				+ "<p>La solution recommandée est de <b>supprimer Flywheel Indépendant</b> et d'utiliser uniquement "
				+ "la version incluse avec Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel Indépendant";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur liée au mod <i>Floral Enchantments</i> a été détectée.</b>"
				+ "<p>Le crash est causé par une erreur interne du mod lors de la gestion des données du jeu, "
				+ "ce qui provoque une <b>NullPointerException</b> pendant l'exécution.</p>"
				+ "<p>Ce problème se résout généralement en mettant à jour le mod ou en le supprimant.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Erreur de Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Vous avez la version NeoForge de MixinExtras et la version normale. Si vous êtes sur MinecraftForge, vous pouvez installer <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>ce lien</a> pour la solution.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur dans les ombres du terrain avec les shaders (Iris) a été détectée.</b>"
				+ "<p>Le problème survient lors du rendu du terrain.</p>"
				+ "<p>Il est recommandé d'<b>essayer le jeu sans shaders</b> ou de réduire la qualité graphique, "
				+ "en particulier dans les configurations <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Ombres du terrain (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un tick serveur excessivement long a été détecté.</b>"
				+ "<p>Cela indique que le jeu s'est bloqué pendant trop longtemps sur un seul tick.</p>"
				+ "<p>Il est recommandé d'<b>examiner le thread dump</b> généré dans le log pour identifier la cause.</p>"
				+ "<p>L'<b>Analyse de la pile d'exécution (Stack Trace)</b> peut vous aider à localiser l'origine du blocage.</p>"
				+ "<p>De plus, le bouton <b>Voir dans le log</b> mettra en rouge les mods potentiellement responsables, "
				+ "ainsi que les entrées entourées par <code>$modid$</code>, qui indiquent souvent l'origine du problème. Pour l'analyse en temps réel, nous recommandons d'utiliser l'échantillonneur CPU dans VisualVM. Assurez-vous que votre serveur ou ordinateur est suffisamment puissant pour gérer les mods que vous utilisez, car il est possible que tous vos mods fonctionnent correctement, mais que vous en ayez trop.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick serveur long";
	}

	@Override
	public String tituloLFPDPPP() {
		return "LOI FÉDÉRALE SUR LA PROTECTION DES DONNÉES PERSONNES DÉTENUES PAR DES PARTICULIERS";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Accepter définitivement";
	}

	// Métodos para la Acta de Protección del Idioma Cultural de Pyongyang
	public String actaProteccionIdiomaCultural() {
		return "Loi sur la protection de la langue culturelle de Pyongyang";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "La traduction coréenne contient des termes d'argot du Sud qui doivent être évités pour se conformer à la loi. "
				+ "L'utilisation de langage étranger, en particulier provenant du Sud, est strictement interdite "
				+ "selon la Loi sur la protection de la langue culturelle de Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Pour plus d'informations, consultez le document officiel de la loi : "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Loi sur la protection de la langue culturelle de Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "Lire la loi complète";
	}

	public String errorAbriendoEnlace() {
		return "Erreur lors de l'ouverture du lien";
	}

	@Override
	public String canarioTitulo() {
		return "Warrant Canary (Canari judiciaire)";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Moniteur de surveillance";
	}

	@Override
	public String revisar() {
		return "Vérifier";
	}

	@Override
	public String cerrar() {
		return "Fermer";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Tous les services signalent un état sûr.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ALERTE : " + c + " service(s) signalent un état non sécurisé.";
	}

	@Override
	public String colorAlerta() {
		return "Couleur d'alerte";
	}

	public String opcionesMunidiales() {
		return "Options mondiales";
	}

	public String consentimientoLFPDPPP() {
		return "Consentement LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Activer le transfert du jeton d'accès dans Handoff pour ReLauncher (déconseillé).";
	}

	public String consolaDesarrollo() {
		return "Console de développement";
	}

	public String mundial() {
		return "Mondial";
	}

	public String ningun() {
		return "Aucun";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Console du développeur";
	}

	public String bajar() {
		return "Descendre";
	}

	public String logsSoporte() {
		return "Logs pour le support";
	}

	public String detenerProceso() {
		return "Arrêter le processus";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Copier la sélection";
	}

	public String seleccionarTodo() {
		return "Sélectionner tout";
	}

	public String copiarTodo() {
		return "Copier tout";
	}

	public String guardarTodoComoArchivo() {
		return "Enregistrer tout comme fichier";
	}

	public String obtenerEnlaceSoporte() {
		return "Obtenir le lien de support";
	}

	public String borrarTodo() {
		return "Tout effacer";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Couleur de fond de la console";
	}

	public String colorTextoConsola() {
		return "Couleur du texte de la console";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Consentement confirmé.\\nL'intégration du partage de logs sera implémentée ici.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Utiliser l'image originale de Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "Un \\\"warrant canary\\\" est un mécanisme de transparence.\\n\\n"
				+ "Tant que ce message existe et que les services apparaissent comme sûrs, "
				+ "cela signifie que le projet N'A PAS reçu d'ordres judiciaires secrets, "
				+ "d'exigences de censure, ni de demandes légales de surveillance.\\n\\n"
				+ "Si un canari cesse d'être présent ou est marqué comme non sécurisé, "
				+ "cela indique que quelque chose a changé juridiquement.\\n\\n"
				+ "Ce panneau vérifie tous les canaris enregistrés dans le système et affiche "
				+ "leur état actuel.\\n\\n" + "Appuyez sur \\\"Vérifier\\\" pour mettre à jour les états.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Réinitialiser toutes les options à leurs valeurs par défaut ?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Sans options";
	}

	@Override
	public String seleccionaColor() {
		return "Sélectionner une couleur";
	}

	@Override
	public String botonMostrarGUI() {
		return "Afficher la GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Tout enregistrer";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Tout réinitialiser";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms non chargé";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur lors de l'accès à l'API de LuckPerms a été détectée.</b>"
				+ "<p>Le message indique que <b>LuckPerms n'était pas chargé</b> au moment où un autre plugin a tenté de l'utiliser.</p>"
				+ "<p><b>Causes possibles :</b></p>" + "<ul>"
				+ "<li>Le plugin <b>LuckPerms n'est pas installé</b> ou <b>n'a pas réussi à démarrer</b>.</li>"
				+ "<li>Un autre plugin tente d'accéder à LuckPerms trop tôt ou de manière incorrecte.</li>" + "</ul>"
				+ "<p>Il est recommandé d'<b>examiner la console</b> en utilisant le lien pour identifier "
				+ "le plugin qui appelle LuckPerms et de vérifier sa compatibilité.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack Iris non chargé";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "inconnu" : shaderpack;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur lors du chargement d'un shaderpack avec Iris/Oculus a été détectée.</b>"
				+ "<p><b>Shaderpack affecté :</b> " + nombre + "</p>"
				+ "<p>Minecraft n'a pas pu ouvrir le fichier du shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Solutions possibles :</b></p>" + "<ul>"
				+ "<li>Vérifiez que le shaderpack est correctement installé dans le dossier <b>shaderpacks</b>.</li>"
				+ "<li>Téléchargez à nouveau le shaderpack, car le fichier pourrait être corrompu.</li>"
				+ "<li>Si le problème persiste, supprimez le fichier <b>config/iris.properties</b> pour réinitialiser la configuration d'Iris.</li>"
				+ "</ul>" + "<p>Après avoir appliqué les modifications, lancez à nouveau le jeu.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Impossible d'écrire le fichier de configuration";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "inconnu" : ruta;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur s'est produite lors de l'enregistrement d'un fichier de configuration.</b>"
				+ "<p><b>Fichier affecté :</b> " + archivo + "</p>"
				+ "<p>Minecraft n'a pas pu écrire le fichier en utilisant l'écriture atomique (REPLACE_ATOMIC).</p>"
				+ "<p><b>Cela se produit généralement à cause de :</b></p>" + "<ul>"
				+ "<li>Permissions incorrectes sur le dossier ou le fichier.</li>"
				+ "<li>Le fichier est marqué en lecture seule.</li>"
				+ "<li>Un autre programme (antivirus, sauvegarde, éditeur) bloque le fichier.</li>" + "</ul>"
				+ "<p><b>Recommandations :</b></p>" + "<ul>"
				+ "<li>Vérifiez que vous avez les permissions d'écriture sur le dossier.</li>"
				+ "<li>Retirez l'attribut de lecture seule du fichier.</li>"
				+ "<li>Fermez les programmes qui pourraient utiliser ce fichier.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Accès refusé lors de la création de la sauvegarde";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "inconnu" : origen;
		String dst = backup == null || backup.isEmpty() ? "inconnu" : backup;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur de permissions s'est produite lors de la création d'une sauvegarde du fichier de configuration.</b>"
				+ "<p><b>Fichier original :</b> " + src + "</p>" + "<p><b>Fichier de sauvegarde :</b> " + dst + "</p>"
				+ "<p>Le système d'exploitation a bloqué l'accès pendant l'enregistrement du fichier.</p>"
				+ "<p><b>Cela se produit généralement à cause de :</b></p>" + "<ul>"
				+ "<li>Permissions insuffisantes sur le dossier.</li>"
				+ "<li>Le fichier est marqué en lecture seule.</li>"
				+ "<li>Un autre programme (antivirus, synchronisation, éditeur) utilise le fichier.</li>" + "</ul>"
				+ "<p><b>Recommandations :</b></p>" + "<ul>"
				+ "<li>Vérifiez les permissions du dossier <b>config</b>.</li>"
				+ "<li>Fermez les programmes qui pourraient accéder au fichier.</li>"
				+ "<li>Essayez de lancer le launcher ou Minecraft en tant qu'administrateur.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Activer la console";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Outils de débogage</b><br><br>"
				+ "Ici, vous pouvez activer des fonctions avancées pour déboguer CrashDetector et vos jeux.<br><br>"
				+ "Il est recommandé d'activer la console de développement pour obtenir des informations détaillées, des traces et des diagnostics pendant l'analyse.<br><br>"
				+ "Si vous devez tester un serveur multijoueur en mode en ligne, il peut être nécessaire d'autoriser le transfert du jeton d'accès au processus de CrashDetector depuis les paramètres de confidentialité. "
				+ "Ceci n'est généralement <b>pas recommandé</b> dans d'autres cas.<br><br>"
				+ "Instructions complètes : <a href='https://example.com'>Lien !</a>";// TODO
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilité détectée entre Simple Clouds et les shaders.</b>"
				+ "<p>Simple Clouds n'est pas compatible avec les mods d'ombres (Iris/Oculus) lorsque Distant Horizons est installé.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Supprimez <b>Simple Clouds</b> si vous souhaitez utiliser des shaders.</li>"
				+ "<li>Ou bien, désinstallez <b>Iris ou Oculus</b> si vous préférez garder Simple Clouds.</li>"
				+ "</ul>"
				+ "<p>Cette limitation vient du mod Simple Clouds lui-même et ne peut être résolue sans modifier son code.</p>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Incompatibilité : Simple Clouds vs Shaders";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Couleur du bouton de la barre latérale";
	}

	@Override
	public String profilerTitulo() {
		return "Profileur";
	}

	@Override
	public String profilerDescripcion() {
		return "Outil d'analyse de performance basé sur l'instrumentation et l'échantillonnage.";
	}

	@Override
	public String profilerIniciar() {
		return "Démarrer";
	}

	@Override
	public String profilerDetener() {
		return "Arrêter";
	}

	@Override
	public String profilerLimpiar() {
		return "Effacer";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Profileur démarré.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Profileur arrêté.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Échantillon reçu du thread : " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Échantillonnage périodique des piles d'exécution pour détecter les goulots d'étranglement et les blocages.";
	}

	@Override
	public String entrarAlJuego() {
		return "Entrer dans le jeu";
	}

	@Override
	public String mensajeRutaCaracteresInvalidos() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur dans le chemin du système détectée.</b>"
				+ "<p>Minecraft n'a pas pu démarrer en raison de caractères illégaux dans le nom d'un dossier.</p>"
				+ "<p>Le système a détecté un caractère invalide dans le chemin (par exemple : ':' ou d'autres symboles spéciaux).</p>"
				+ "<p><b>Solution recommandée :</b></p>" + "<ul>"
				+ "<li>Renommez le dossier de l'instance ou du profil.</li>"
				+ "<li>Utilisez uniquement des caractères ASCII de base (A-Z, a-z, 0-9).</li>"
				+ "<li>N'utilisez pas d'accents, de symboles spéciaux, d'espaces problématiques ni d'emojis.</li>"
				+ "</ul>" + "<p>Exemple valide : <b>MonInstance1</b></p>"
				+ "<p>Exemple invalide : <b>Instance🔥</b> ou <b>Instance:Mod</b></p>";
	}

	@Override
	public String nombreRutaCaracteresInvalidos() {
		return "Chemin invalide : caractères non autorisés";
	}

	@Override
	public String mensajeTwilightForestIntelShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Échec détecté des shaders de Twilight Forest avec GPU Intel.</b>"
				+ "<p>Cette erreur est liée aux pilotes graphiques Intel lors du chargement des shaders du mod Twilight Forest.</p>"
				+ "<p>L'échec se produit au sein du pilote (igxelpicd64) et n'est pas un problème direct du mod ni de Minecraft.</p>"
				+ "<p><b>Solutions recommandées :</b></p>" + "<ul>"
				+ "<li>Mettez à jour les pilotes Intel vers la version la plus récente disponible.</li>"
				+ "<li>Essayez spécifiquement les versions 31.0.101.8331 ou 31.0.101.8247 WHQL, qui selon les commentaires ne présentent pas ce problème.</li>"
				+ "</ul>" + "<p>Suivi officiel du problème :</p>"
				+ "<p><a href='https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273'>"
				+ "https://github.com/IGCIT/Intel-GPU-Community-Issue-Tracker-IGCIT/issues/1273</a></p>"
				+ "<p><b>Note :</b> Certains GPU Intel plus anciens peuvent ne pas recevoir de mises à jour corrigeant ce problème.</p>";
	}

	@Override
	public String nombreTwilightForestIntelShaders() {
		return "Crash : Twilight Forest + Pilotes Intel";
	}

	@Override
	public String nombreForgeLanguageProviderNoCarga() {
		return "Forge : le fournisseur de langue n'a pas pu se charger";
	}

	@Override
	public String mensajeForgeLanguageProviderNoCarga(String provider) {
		String providerTexto = (provider == null || provider.isEmpty()) ? "Fournisseur inconnu" : provider;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Forge n'a pas pu charger un fournisseur de langue.</b>"
				+ "<p>Une erreur s'est produite lors de l'initialisation d'un IModLanguageProvider.</p>"
				+ "<p><b>Fournisseur ayant échoué :</b> " + providerTexto + "</p>"
				+ "<p>Ce problème se produit généralement lorsque :</p>" + "<ul>"
				+ "<li>Une dépendance requise est manquante (par exemple, Kotlin for Forge).</li>"
				+ "<li>La version du mod n'est pas compatible avec votre version de Forge.</li>"
				+ "<li>Le fichier du mod est corrompu.</li>" + "</ul>" + "<p><b>Solutions recommandées :</b></p>"
				+ "<ul>" + "<li>Réinstallez le mod correspondant.</li>"
				+ "<li>Vérifiez que toutes ses dépendances sont installées.</li>"
				+ "<li>Assurez-vous d'utiliser des versions compatibles avec votre Forge actuel.</li>" + "</ul>";
	}

	@Override
	public String nombreLetsDoCompatInterceptApply() {
		return "Crash : Lets Do Compat (interception RecipeManager)";
	}

	@Override
	public String mensajeLetsDoCompatInterceptApply() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Crash détecté dans Lets Do Compat (interceptApply).</b>"
				+ "<p>L'erreur se produit lors d'une transformation de la méthode "
				+ "<b>RecipeManager.interceptApply</b> effectuée par Lets Do Compat.</p>"
				+ "<p>Cela indique généralement :</p>" + "<ul>"
				+ "<li>Une incompatibilité entre Lets Do Compat et un autre mod qui modifie les recettes.</li>"
				+ "<li>Une version incorrecte pour votre version de Minecraft.</li>"
				+ "<li>Un conflit entre transformateurs (mixin/coremod).</li>" + "</ul>"
				+ "<p><b>Solutions recommandées :</b></p>" + "<ul>"
				+ "<li>Essayez de supprimer temporairement Lets Do Compat pour confirmer le conflit.</li>" + "</ul>";
	}

	@Override
	public String nombreJEIItemGroupCrash() {
		return "JEI : échec dans Item Group (plugin incompatible)";
	}

	@Override
	public String mensajeJEIItemGroupCrash(java.util.Set<String> plugins) {
		String listaPlugins = (plugins == null || plugins.isEmpty()) ? "Plugin inconnu" : String.join(", ", plugins);
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "JEI a détecté un échec lors de la construction d'un groupe d'objets.</b>"
				+ "<p>Un ou plusieurs plugins ont provoqué une erreur pendant que JEI générait la liste des ingrédients.</p>"
				+ "<p><b>Groupes/Plugins affectés :</b> " + listaPlugins + "</p>"
				+ "<p>Ce problème est courant lorsque :</p>" + "<ul>"
				+ "<li>Un plugin JEI est mal implémenté ou obsolète.</li>"
				+ "<li>Il existe une incompatibilité avec la version actuelle de JEI.</li>"
				+ "<li>Fabric API est utilisé et un mod enregistre incorrectement son Item Group.</li>" + "</ul>"
				+ "<p><b>Solutions recommandées :</b></p>" + "<ul>" + "<li>Mettez à jour JEI et les mods listés.</li>"
				+ "<li>Supprimez temporairement les plugins affectés pour confirmer le conflit.</li>"
				+ "<li>Signalez l'erreur au développeur du mod correspondant.</li>" + "</ul>"
				+ "<p>Les objets de ces groupes n'apparaîtront pas dans la liste des ingrédients tant que le problème ne sera pas corrigé.</p>";
	}

	@Override
	public String nombreVersionInvalida() {
		return "Version de mod invalide (SemVer)";
	}

	@Override
	public String mensajeVersionInvalida(String version, String ubicacion) {
		String v = (version == null || version.isEmpty()) ? "Inconnue" : version;
		String u = (ubicacion == null || ubicacion.isEmpty()) ? "Impossible de localiser le mod" : ubicacion;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Version de mod invalide détectée.</b>" + "<p>La version <b>" + v
				+ "</b> ne respecte pas le format SemVer valide.</p>"
				+ "<p>L'erreur indique une pre-release vide (se termine par '+').</p>"
				+ "<p><b>Mod responsable :</b><br>" + u + "</p>" + "<p><b>Solution recommandée :</b></p>" + "<ul>"
				+ "<li>Éditez le fichier du mod et corrigez la version.</li>"
				+ "<li>Supprimez le '+' final s'il n'y a pas de métadonnées suivantes.</li>"
				+ "<li>Mettez à jour le mod vers une version corrigée.</li>" + "</ul>";
	}

	@Override
	public String nombreJPMSIllegalAccess() {
		return "JPMS : Accès illégal entre modules";
	}

	@Override
	public String mensajeJPMSIllegalAccess(String claseOrigen, String moduloOrigen, String claseDestino,
			String moduloDestino) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Accès illégal entre modules (JPMS) détecté.</b>"
				+ "<p>Le système de modules Java (JPMS) a bloqué un accès entre classes.</p>"
				+ "<p><b>Classe tentant d'accéder :</b><br>" + claseOrigen + " (module : " + moduloOrigen + ")</p>"
				+ "<p><b>Classe bloquée :</b><br>" + claseDestino + " (module : " + moduloDestino + ")</p>"
				+ "<p>Ce type d'erreur se produit lorsqu'un mod ne déclare pas correctement "
				+ "les exports ou opens dans son module-info.java.</p>" + "<p><b>Causes possibles :</b></p>" + "<ul>"
				+ "<li>Le module n'exporte pas le package nécessaire.</li>"
				+ "<li>La directive <b>opens</b> est manquante pour la réflexion.</li>"
				+ "<li>Le mod n'est pas correctement configuré pour JPMS.</li>" + "</ul>"
				+ "<p>Ce problème doit être corrigé par le développeur du mod.</p>";
	}

	@Override
	public String nombreMixinClaseMalUbicada() {
		return "Mixin : classe mal placée dans le package mixin";
	}

	@Override
	public String mensajeMixinClaseMalUbicada(String clase, String paquete, String archivoMixin) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Classe incorrectement placée dans le package Mixin.</b>"
				+ "<p>Une classe normale a été placée dans un package déclaré comme mixin.</p>"
				+ "<p><b>Classe conflictuelle :</b><br>" + clase + "</p>" + "<p><b>Package mixin déclaré :</b><br>"
				+ paquete + "</p>" + "<p><b>Fichier mixins responsable :</b><br>" + archivoMixin + "</p>"
				+ "<p>Les classes normales ne doivent pas se trouver dans le package défini dans mixins.json.</p>"
				+ "<p>Seules les classes annotées comme mixin doivent exister dans ce package.</p>"
				+ "<p><b>Solution pour les développeurs :</b> Déplacez les classes normales hors du package mixin "
				+ "ou corrigez la configuration du fichier mixins.json.</p>";
	}

	@Override
	public String problema_con_graficas_matrox() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Problème détecté avec les pilotes GPU Matrox.</b>"
				+ "<p>Le log indique que l'échec s'est produit dans une bibliothèque du pilote Matrox.</p>"
				+ "<p>Les GPU Matrox (en particulier les modèles G200/G400 utilisés dans les serveurs) "
				+ "ne sont pas conçus pour le rendu 3D moderne et peuvent ne pas prendre en charge "
				+ "les versions d'OpenGL requises par Minecraft.</p>" + "<p><b>Solutions recommandées :</b></p>"
				+ "<ul>" + "<li>Mettez à jour le pilote Matrox vers la version la plus récente disponible.</li>"
				+ "<li>Installez les pilotes officiels plutôt que les pilotes génériques du système.</li>"
				+ "<li>Si le matériel est ancien, utilisez un GPU compatible avec OpenGL 3.2 ou supérieur.</li>"
				+ "</ul>"
				+ "<p>Dans les serveurs, ces GPU sont souvent utilisés uniquement pour la sortie vidéo de base "
				+ "et non pour les applications 3D comme Minecraft.</p>";
	}

	@Override
	public String nombreVulkanModNoEncuentraGPU() {
		return "VulkanMod : GPU non compatible";
	}

	@Override
	public String mensajeVulkanModNoEncuentraGPU() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VulkanMod n'a pas pu détecter un GPU compatible.</b>"
				+ "<p>Le mod <b>VulkanMod</b> a tenté de démarrer en utilisant Vulkan mais n'a pas trouvé de GPU prenant correctement en charge Vulkan.</p>"
				+ "<p>Cela se produit généralement lorsque :</p>" + "<ul>"
				+ "<li>Le GPU ne prend pas en charge Vulkan.</li>"
				+ "<li>Les pilotes du GPU sont obsolètes ou manquants.</li>"
				+ "<li>Un adaptateur graphique incorrect est utilisé (par exemple, GPU intégré au lieu de dédié).</li>"
				+ "</ul>" + "<p><b>Solutions recommandées :</b></p>" + "<ul>"
				+ "<li>Mettez à jour les pilotes du GPU vers la version la plus récente.</li>"
				+ "<li>Vérifiez que votre GPU prend en charge Vulkan.</li>"
				+ "<li>Si vous avez deux GPU, forcez l'utilisation du dédié pour Minecraft.</li>"
				+ "<li>Si votre GPU ne prend pas en charge Vulkan, désinstallez VulkanMod.</li>" + "</ul>";
	}

	@Override
	public String nombreRenderOutlineRendertypeInvalido() {
		return "RenderType invalide pour le contour";
	}

	@Override
	public String mensajeRenderOutlineRendertypeInvalido(boolean enchantDetectado) {
		String base = "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod a tenté d'appliquer un contour à un RenderType incompatible.</b>" + "<p>L'erreur était :</p>"
				+ "<code>Can't render an outline for this rendertype!</code>";

		if (enchantDetectado) {
			base += "<p><b>Le mod enchant-outline / better-enchants a été détecté dans le rapport.</b></p>"
					+ "<p>Ce mod est connu pour causer ce problème dans les versions récentes de Minecraft.</p>"
					+ "<p><b>Solution recommandée :</b> supprimez ou mettez à jour enchant-outline.</p>";
		} else {
			base += "<p>Ce problème est généralement lié à des mods qui modifient le rendu "
					+ "(Entity Model Features, Entity Texture Features, Visuality ou conflits avec Sodium).</p>"
					+ "<p><b>Solution recommandée :</b> mettez à jour ou désactivez les mods de rendu un par un.</p>";
		}

		return base;
	}

	@Override
	public String nombreDivineRPGDimensionalInventoryNPE() {
		return "DivineRPG – DimensionalInventory nul";
	}

	@Override
	public String mensajeDivineRPGDimensionalInventoryNPE() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "DivineRPG a tenté de sauvegarder un DimensionalInventory nul.</b>" + "<p>Le jeu a lancé :</p>"
				+ "<code>Cannot invoke DimensionalInventory.saveInventory(...) because \\\"d\\\" is null</code>"
				+ "<p>C'est un bug connu de DivineRPG lié au système d'inventaire Vethean.</p>"
				+ "<p><b>Solution recommandée :</b></p>" + "<ul>"
				+ "<li>Allez dans le fichier de configuration de DivineRPG.</li>"
				+ "<li>Définissez <code>saferVetheanInventory=true</code></li>"
				+ "<li>Enregistrez et redémarrez le jeu.</li>" + "</ul>"
				+ "<p>Il est également recommandé de mettre à jour DivineRPG si une version plus récente est disponible.</p>";
	}

	@Override
	public String nombreRenderPassNoCerrado() {
		return "Conflit dans Render Pass";
	}

	@Override
	public String mensajeRenderPassNoCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un conflit dans le système de rendu a été détecté.</b>" + "<p>Le jeu a lancé :</p>"
				+ "<code>Close the existing render pass before performing additional commands</code>"
				+ "<p>Cette erreur est généralement liée à des conflits entre mods de rendu "
				+ "comme Iris, OptiFine, VulkanMod ou d'autres qui modifient le pipeline graphique.</p>";
	}

	@Override
	public String nombreProblemaFeatherClient() {
		return "Échec interne de Feather Client";
	}

	@Override
	public String mensajeProblemaFeatherClientSodium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un échec interne de Feather Client a été détecté.</b>" + "<p>Le jeu a lancé :</p>"
				+ "<code>NoClassDefFoundError: feather/lib/sentry/Sentry</code>"
				+ "<p>Cette erreur est causée par Feather Client.</p>" + "<p>Feather n'est pas recommandé car :</p>"
				+ "<ul>" + "<li>Il utilise des versions propriétaires et modifiées de mods populaires.</li>"
				+ "<li>Il rompt la compatibilité avec Fabric standard.</li>"
				+ "<li>Il fonctionne comme un modpack pré-assemblé avec des modifications internes.</li>"
				+ "<li>Il cause souvent des conflits avec Sodium et d'autres mods de performance.</li>" + "</ul>"
				+ "<p>Il est recommandé d'utiliser une installation standard de Fabric au lieu de Feather.</p>";
	}

	@Override
	public String nombreConflictoIrisFlywheelCreate() {
		return "Conflit Iris + Flywheel (Create 6)";
	}

	@Override
	public String mensajeConflictoIrisFlywheelCreate() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Conflit détecté entre Iris et Flywheel dans Create 6.</b>" + "<p>Le jeu a lancé :</p>"
				+ "<code>NoSuchFieldError: TESSELATION_SHADERS</code>"
				+ "<p>Des références internes <code>$irisflw$</code> ont été détectées, "
				+ "ce qui indique un conflit entre Iris et Flywheel.</p>"
				+ "<p>Iris Flywheel 2.0 pour Create 6 n'est officiellement compatible qu'avec NeoForge.</p>"
				+ "<p>Si vous utilisez Forge ou Fabric, cette combinaison peut causer cette erreur.</p>";
	}

	@Override
	public String nombreModeloGeckoNoEncontrado() {
		return "Modèle GeckoLib introuvable";
	}

	@Override
	public String mensajeModeloGeckoNoEncontrado(String modelo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod n'a pas pu trouver un modèle GeckoLib.</b>" + "<p>Modèle affecté :</p>" + "<code>" + modelo
				+ "</code>" + "<p>Cette erreur se produit lorsqu'un fichier <code>.geo.json</code> n'existe pas "
				+ "ou est mal configuré dans le mod.</p>" + "<p>Causes possibles :</p>" + "<ul>"
				+ "<li>Le modèle a été supprimé mais est toujours référencé.</li>"
				+ "<li>Erreur dans le chemin du fichier.</li>" + "<li>Fichier manquant dans le JAR.</li>"
				+ "<li>Version incompatible du mod.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaAnimacionCobblemon() {
		return "Cobblemon – Animation inexistante";
	}

	@Override
	public String mensajeAnimacionCobblemon(String animacion, String grupo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Cobblemon a tenté de lire une animation inexistante.</b>" + "<p>Animation :</p>" + "<code>"
				+ animacion + "</code>" + "<p>Groupe :</p>" + "<code>" + grupo + "</code>"
				+ "<p>Cette erreur se produit généralement lorsque :</p>" + "<ul>"
				+ "<li>Des versions incompatibles de Cobblemon sont mélangées.</li>"
				+ "<li>Un addon ne correspond pas à la version installée.</li>"
				+ "<li>Des ressources ou animations internes sont manquantes.</li>" + "</ul>";
	}

	@Override
	public String nombreProblemaLunarClient() {
		return "Échec interne de Lunar Client";
	}

	@Override
	public String mensajeProblemaLunarClient() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un échec interne de Lunar Client a été détecté.</b>"
				+ "<p>L'erreur provient du client Lunar lui-même et non du jeu de base ni des mods.</p>"
				+ "<p>Lunar Client utilise des modifications internes et personnalisées qui peuvent "
				+ "causer des incompatibilités avec des mods ou des configurations spécifiques.</p>"
				+ "<p>Il est recommandé d'essayer avec une installation standard de Minecraft "
				+ "pour exclure les problèmes propres au client.</p>";
	}

	@Override
	public String nombreAccesoIlegalMod() {
		return "Accès illégal à une méthode ou un champ";
	}

	@Override
	public String mensajeAccesoIlegalMod(String clase, String miembro) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod a tenté d'accéder à une méthode ou un champ protégé/privé.</b>"
				+ "<p>Classe responsable :</p>" + "<code>" + clase + "</code>" + "<p>Membre accédé :</p>" + "<code>"
				+ miembro + "</code>" + "<p>Cette erreur se produit généralement lorsque :</p>" + "<ul>"
				+ "<li>Le mod a été compilé pour une autre version de Minecraft.</li>"
				+ "<li>Il y a un mélange de mappings incompatibles.</li>" + "<li>Le mod est obsolète.</li>"
				+ "<li>Le chargeur incorrect est utilisé (Fabric/Forge/NeoForge).</li>" + "</ul>";
	}

	@Override
	public String nombreErrorParseoDataPack() {
		return "Erreur lors du chargement du datapack/resourcepack";
	}

	@Override
	public String mensajeErrorParseoDataPack(String archivo, String pack) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un datapack ou resourcepack n'a pas pu se charger.</b>" + "<p>Fichier problématique :</p>" + "<code>"
				+ archivo + "</code>" + "<p>Pack :</p>" + "<code>" + pack + "</code>"
				+ "<p>Le jeu n'a pas pu analyser ce fichier, ce qui a provoqué des erreurs de chargement du registre.</p>"
				+ "<p>Ce problème est généralement dû à :</p>" + "<ul>" + "<li>Un JSON mal formé.</li>"
				+ "<li>Une version incompatible du pack.</li>"
				+ "<li>Un pack obsolète pour la version actuelle du jeu.</li>" + "<li>Un conflit entre datapacks.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorCompilacionShader() {
		return "Erreur de compilation de shader";
	}

	@Override
	public String mensajeErrorCompilacionShader() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "La compilation d'un shader a échoué.</b>"
				+ "<p>Le jeu n'a pas pu compiler l'un des shaders actifs.</p>"
				+ "<p>Ce problème est généralement lié à Sodium, Iris ou à des shaderpacks incompatibles.</p>"
				+ "<p>Recommandations :</p>" + "<ul>" + "<li>Essayez un shader différent.</li>"
				+ "<li>Désactivez temporairement les shaders.</li>" + "<li>Mettez à jour les pilotes du GPU.</li>"
				+ "<li>Si le problème persiste, essayez de lancer le jeu sans Sodium.</li>" + "</ul>";
	}

	public String nombreErrorCreacionModelo() {
		return "Erreur lors de la création ou du chargement d'un modèle";
	}

	public String mensajeErrorCreacionModelo(String modelo) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<p>Une erreur s'est produite lors de la tentative de création ou de chargement d'un modèle Minecraft.</p>");
		if (modelo != null && !modelo.isEmpty()) {
			sb.append("<p>Modèle affecté : <code>").append(modelo).append("</code></p>");
		}
		sb.append("<p>Ce type d'erreur se produit généralement lorsque :</p>");
		sb.append("<ul>");
		sb.append("<li>Un mod a un modèle mal configuré.</li>");
		sb.append("<li>Un modèle JSON est endommagé ou incomplet.</li>");
		sb.append("<li>Il existe un conflit entre des mods qui modifient les modèles ou le rendu.</li>");
		sb.append("<li>Un resource pack ou datapack contient des modèles incompatibles.</li>");
		sb.append("</ul>");
		sb.append("<p>Essayez d'identifier quel mod ou pack de ressources fournit le modèle indiqué.</p>");
		return sb.toString();
	}

	public String posibleConflictoCoolerAnimations() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p><b>Cause possible détectée :</b></p>");
		sb.append("<p>Une activité du mod <b>Cooler Animations</b> a été détectée dans le journal.</p>");
		sb.append(
				"<p>Ce mod modifie le système d'animations et de modèles, et dans certains cas, peut causer des erreurs de chargement de modèles.</p>");
		sb.append(
				"<p>Si le problème persiste, essayez de lancer le jeu sans Cooler Animations pour vérifier si l'erreur disparaît.</p>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaStarlight() {
		return "Problème avec Starlight";
	}

	@Override
	public String problemaBlockStarlightEngineDetectado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur liée à Starlight a été détectée.</b>"
				+ "<p>L'erreur s'est produite dans <code>BlockStarLightEngine.initNibble</code>.</p>"
				+ "<p>Cela indique un échec dans le moteur d'éclairage du mod <b>Starlight</b>.</p>"
				+ "<p>Starlight est un mod qui modifie complètement le système d'éclairage de Minecraft et est connu pour causer divers problèmes dans certains environnements de mods.</p>"
				+ "<p>Ceci n'est qu'une des nombreuses erreurs connues associées à Starlight.</p>"
				+ "<p>Si le problème persiste, essayez de lancer le jeu sans Starlight.</p>";
	}

	@Override
	public String nombreProblemaAAAParticlesEffekseer() {
		return "Problème avec AAAParticles / Effekseer";
	}

	@Override
	public String problemaAAAParticlesEffekseer() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un crash natif lié à Effekseer a été détecté.</b>"
				+ "<p>L'erreur s'est produite dans la bibliothèque native <code>EffekseerNativeForJava</code>.</p>"
				+ "<p>Cette bibliothèque est utilisée par le mod <b>AAAParticles</b> développé par ChloePrime.</p>"
				+ "<p>Les crashes dans les bibliothèques natives indiquent généralement des problèmes au sein du mod lui-même ou de ses dépendances natives.</p>"
				+ "<p>Si le problème persiste, essayez de lancer le jeu sans AAAParticles.</p>";
	}

	@Override
	public String javaProblematica() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un crash natif de l'environnement d'exécution Java (JVM) a été détecté.</b>"
				+ "<p>Ce type d'erreur se produit au sein de la Machine Virtuelle Java elle-même (par exemple, dans <code>jvm.dll</code>, <code>libjvm.so</code>, etc.), "
				+ "et n'est pas nécessairement causé par un mod.</p>"
				+ "<p>Bien que dans de rares cas, cela puisse provenir de mods utilisant des bibliothèques natives incompatibles, "
				+ "<b>il est beaucoup plus probable que cela soit dû à une version défectueuse, corrompue ou obsolète de la JVM</b>.</p>"
				+ "<p>Cela est particulièrement courant si vous utilisez une compilation ancienne ou non officielle de votre version de Java (par exemple, des builds communautaires non supportées).</p>"
				+ "<p><b>Nous recommandons d'utiliser une JVM fiable et maintenue :</b></p>" + "<ul>"
				+ "<li><b>Red Hat Build of OpenJDK</b> (stable, bien testée, idéale pour Windows/Linux)</li>"
				+ "<li><b>OpenLogic OpenJDK</b> (support multiplateforme, y compris macOS Intel)</li>"
				+ "<li><b>Azul Zulu</b> (certifiée, avec support LTS gratuit)</li>"
				+ "<li><b>Oracle JDK</b> (officielle, avec mises à jour régulières)</li>" + "</ul>"
				+ "<p>Évitez les builds inconnues, personnalisées ou très anciennes, car elles peuvent contenir des erreurs critiques dans le moteur de la JVM.</p>"
				+ "<p><b>Vous utilisez Prism Launcher ou TLauncher ?</b> Il est très facile de configurer une JVM personnalisée : "
				+ "dans Prism Launcher, allez dans <i>Instalaciones</i> → <i>Editar instancia</i> → <i>Configuration de Java</i>; "
				+ "dans TLauncher, allez dans <i>Settings</i> → <i>Java Settings</i> et sélectionnez le chemin de votre JDK/JRE téléchargé. "
				+ "Vous n'avez pas besoin de changer la JVM du système !</p>";
	}

	@Override
	public String nombreProblemaParanoiaC2ME() {
		return "Conflit entre Paranoia et C2ME";
	}

	@Override
	public String problemaParanoiaC2ME() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un conflit entre les mods Paranoia et C2ME a été détecté.</b>"
				+ "<p>L'erreur indique que <code>ThreadLocalRandom</code> a été accédé depuis un thread incorrect.</p>"
				+ "<p>Cela se produit généralement lorsque le mod <b>Paranoia</b> exécute du code qui n'est pas sûr pour les threads multiples pendant que <b>C2ME</b> effectue des optimisations multithread.</p>"
				+ "<p>Ce type de conflit est courant avec les mods créés avec MCreator.</p>"
				+ "<p>Si le problème persiste, essayez de lancer le jeu sans Paranoia ou désactivez C2ME.</p>";
	}

	@Override
	public String nombreProblemaAssetsDirFaltante() {
		return "Répertoire des assets Minecraft manquant";
	}

	@Override
	public String problemaAssetsDirFaltante() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft n'a pas pu localiser le répertoire des assets.</b>"
				+ "<p>Le lanceur a tenté de démarrer le jeu avec un chemin d'assets invalide.</p>"
				+ "<p>Cela signifie que les fichiers de ressources du jeu (assets) ne sont pas présents ou n'ont pas été installés correctement.</p>"
				+ "<p>Ce problème se produit généralement avec des installations incorrectes de Minecraft ou des lanceurs mal configurés.</p>"
				+ "<p>Cela peut aussi se produire lors de l'utilisation de lanceurs non officiels qui gèrent incorrectement les assets, comme FreshCraft.</p>"
				+ "<p>Si le problème persiste, essayez de réinstaller le modpack ou de lancer le jeu depuis un lanceur officiel ou fiable.</p>";
	}

	@Override
	public String dependenciaInstalar(String dependencia, String version) {
		return "Installer " + dependencia + " version " + version + " ou ultérieure";
	}

	@Override
	public String dependenciaReemplazarRango(String dependencia, String min, String max) {
		return "Remplacer " + dependencia + " par une version entre " + min + " et " + max;
	}

	@Override
	public String dependenciaFaltanteMinima(String mod, String dependencia, String version) {
		return "Le mod " + mod + " nécessite " + dependencia + " version minimale " + version;
	}

	@Override
	public String dependenciaFaltanteWildcard(String mod, String dependencia, String version) {
		return "Le mod " + mod + " nécessite " + dependencia + " version " + version;
	}

	@Override
	public String dependenciaVersionIncorrecta(String mod, String dependencia, String min, String max, String actual) {

		return "Le mod " + mod + " nécessite " + dependencia + " entre " + min + " et " + max + " (actuelle : " + actual
				+ ")";
	}

	@Override
	public String nombreProblemaCupboardVersion() {
		return "Version incompatible de Cupboard";
	}

	@Override
	public String problemaCupboardVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un crash causé par une ancienne version de Cupboard a été détecté.</b>"
				+ "<p>L'erreur se produit dans <code>ClientConfigCompat.setupNeoforge</code> car "
				+ "<code>ModList.get()</code> renvoie <code>null</code>.</p>"
				+ "<p>C'est un problème connu dans les anciennes versions du mod <b>Cupboard</b>.</p>"
				+ "<p>Les anciennes versions comme la <b>3.2</b> contiennent ce bug.</p>"
				+ "<p><b>Solution :</b> mettez à jour Cupboard vers la version <b>3.5</b> ou ultérieure.</p>";
	}

	@Override
	public String fmlEarlyWindowMacOSOpenGL() {
		return "C'est parce que vous êtes sur macOS et que le jeu tente d'utiliser OpenGL, ce qui n'est pas compatible avec les dernières versions de macOS. "
				+ "Vous devez utiliser une version de Minecraft qui prend en charge Metal ou utiliser Linux si vous avez un Mac Intel ou M1/M2 (mais pas M3+ ou Neo).";
	}

	@Override
	public String nombreAnimacionGeckoNoEncontrada() {
		return "Animation GeckoLib introuvable";
	}

	@Override
	public String mensajeAnimacionGeckoNoEncontrada(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod n'a pas pu charger un fichier d'animation GeckoLib.</b>" + "<p>Fichier affecté :</p>"
				+ "<code>" + archivo + "</code>"
				+ "<p>Cette erreur se produit lorsqu'un fichier d'animation <code>.json</code> n'existe pas, "
				+ "contient des erreurs de syntaxe ou que le chemin est incorrect.</p>" + "<p>Causes possibles :</p>"
				+ "<ul>" + "<li>Le fichier a été supprimé mais est toujours référencé dans le code.</li>"
				+ "<li>Erreur de syntaxe dans le fichier JSON.</li>"
				+ "<li>Chemin incorrect défini dans le journal du mod.</li>"
				+ "<li>Conflits de dépendances ou version incompatible.</li>" + "</ul>";
	}

	@Override
	public String nombreAnimacionGeckoInexiste() {
		return "Animation GeckoLib introuvable";
	}

	@Override
	public String mensajeAnimacionGeckoInexiste(String archivo) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un mod n'a pas pu trouver un fichier d'animation GeckoLib.</b>" + "<p>Fichier affecté :</p>"
				+ "<code>" + archivo + "</code>"
				+ "<p>Cette erreur se produit lorsque GeckoLib tente de charger une animation qui n'existe pas au chemin spécifié. "
				+ "Contrairement à une erreur de chargement (syntaxe), cette erreur indique que le fichier manque physiquement ou que le chemin est erroné.</p>"
				+ "<p>Causes possibles :</p>" + "<ul>"
				+ "<li>Le fichier <code>.json</code> a été supprimé ou n'a pas été inclus dans le JAR final du mod.</li>"
				+ "<li>Erreur typographique dans le chemin défini dans le code (par exemple : 'animations' vs 'animaciones').</li>"
				+ "<li>Différence de casse (le système d'exploitation du serveur est Linux (sensible à la casse) et le développement a été fait sous Windows (insensible)).</li>"
				+ "<li>Le mod n'est pas entièrement à jour ou ses dépendances sont cassées.</li>" + "</ul>";
	}

	@Override
	public String nombreRegistroDuplicadoObjeto() {
		return "Conflit d'enregistrement dupliqué";
	}

	@Override
	public String mensajeRegistroDuplicadoObjeto(String mod1, String mod2, String objeto) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal: Solo el texto descriptivo lleva el color de error
		String mensajeBase = "<span style='color:#" + color
				+ "'>Conflit critique : Un objet a été enregistré deux fois. " + "Les mods </span>" + mod1
				+ "<span style='color:#" + color + "'> et </span>" + mod2 + "<span style='color:#" + color
				+ "'> tentent d'enregistrer le même objet. " + "Objet en conflit : </span>" + objeto + "<br><br>";

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Cela se produit généralement lorsque deux mods différents ajoutent un objet avec le même nom "
				+ "dans le même namespace, ou lorsqu'il y a une erreur dans le code de l'un des mods.<br><br>"
				+ "<b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Vérifiez si l'un des mods est une mise à jour ou un fork de l'autre.</li>"
				+ "<li>Essayez de supprimer l'un des deux mods conflictuels.</li>"
				+ "<li>Vérifiez les fichiers de configuration des deux mods pour voir si vous pouvez changer l'ID de l'objet.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreFalloFabricRenderingAPI() {
		return "Fabric Rendering API manquante";
	}

	@Override
	public String mensajeFalloFabricRenderingAPI() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color
				+ "'>Un mod (généralement Porting Lib ou ses dépendants) a échoué car l'</span>"
				+ "API de rendu Fabric<span style='color:#" + color + "'> n'est pas disponible.</span><br><br>";

		// Instrucciones de reparación (Actualizadas para versiones modernas donde
		// Indium es obsoleto)
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solution recommandée :</b><br>"
				+ "Le message suggère d'installer Indium, mais ce mod est obsolète dans les versions modernes du jeu.<br>"
				+ "<ul>"
				+ "<li><b>Mettez à jour Sodium</b> vers la version <b>0.6.0</b> ou supérieure (cette version inclut le support nécessaire).</li>"
				+ "<li>Assurez-vous d'avoir installé <b>Fabric API</b> si ce n'est pas déjà fait.</li>"
				+ "<li>Si vous utilisez une ancienne version du jeu (1.20.6 ou inférieure), installez alors Indium.</li>"
				+ "</ul></span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreRestriccionesDependenciaNoCumplidas() {
		return "Contraintes de dépendances non respectées";
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad, List<String[]> conflictos) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Nous avons trouvé </span>" + cantidad
				+ "<span style='color:#" + color + "'> contraintes de dépendances non respectées.</span><br><br>";

		// Construcción de la lista de conflictos
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictos != null && !conflictos.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Des conflits ont été détectés dans les fichiers suivants :</span><br><ul>");
			for (String[] par : conflictos) {
				String dep = par[0]; // Dependencia
				String jar = par[1]; // Archivo JAR
				// Variable en color por defecto, texto fijo en color error
				listaDetalle.append("<li>").append("<span style='color:#").append(color).append("'>Fichier : </span>")
						.append(jar).append("<br><span style='color:#").append(color).append("'>Requiert : </span>")
						.append(dep).append("</li>");
			}
			listaDetalle.append("</ul><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Cela se produit lorsque deux mods ou plus nécessitent des versions différentes et incompatibles d'une même bibliothèque interne.<br><br>"
				+ "<b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Essayez de mettre à jour ou de supprimer les mods listés ci-dessus pour résoudre l'incompatibilité.</li>"
				+ "<li>Si vous ne trouvez pas de version compatible, vous pouvez essayer de modifier manuellement le fichier <code>mods.toml</code> à l'intérieur du fichier JAR du mod (en utilisant un compresseur comme WinRAR ou 7-Zip) pour changer ou supprimer la contrainte de version, bien que cela puisse causer de l'instabilité.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String mensajeRestriccionesDependenciaNoCumplidas(String cantidad,
			Map<String, List<String>> conflictosPorMod) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>Nous avons trouvé </span>" + cantidad
				+ "<span style='color:#" + color + "'> contraintes de dépendances non respectées.</span><br><br>";

		// Construcción de la lista agrupada por Mod
		StringBuilder listaDetalle = new StringBuilder();
		if (conflictosPorMod != null && !conflictosPorMod.isEmpty()) {
			listaDetalle.append("<span style='color:#").append(color)
					.append("'>Mods impliqués et dépendances demandées :</span><br><ul>");

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
					.append("'>Impossible de déterminer les fichiers spécifiques depuis le log.</span><br>");
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>"
				+ "Cette erreur se produit lorsque les mods incluent des versions internes de bibliothèques (JarInJar) qui sont incompatibles entre elles.<br><br>"
				+ "<b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Vérifiez la liste ci-dessus pour identifier quels mods demandent des versions différentes de la même bibliothèque.</li>"
				+ "<li>Essayez de mettre à jour les deux mods vers leurs dernières versions.</li>"
				+ "<li>En dernier recours, vous pouvez ouvrir le fichier <code>.jar</code> du mod avec un compresseur (comme WinRAR), éditer <code>META-INF/mods.toml</code> et modifier manuellement la plage de version de la dépendance, bien que cela soit risqué et puisse casser le mod.</li>"
				+ "</ul></span>";

		return mensajeBase + listaDetalle.toString() + instrucciones;
	}

	@Override
	public String nombreNeruinaOcultaAdvertencia() {
		return "Neruina empêche le débogage";
	}

	@Override
	public String mensajeNeruinaOcultaAdvertencia() {
		String color = Config.obtenerInstancia().obtenerColorAdvertencia();

		// Advertencia principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Avertissement :</b> Le mod <b>Neruina</b> échoue à gérer une erreur, ce qui masque la véritable cause du crash.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Neruina n'est souvent pas nécessaire et rend difficile la connaissance de ce qui échoue réellement. Il est recommandé de le supprimer pour le débogage.</span><br><br>";

		// Instrucciones de recuperación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Instructions de récupération :</b><br>"
				+ "1. **MCForge** : Allez dans '[nom_du_monde]/serverconfig/forge-server.toml'.<br>"
				+ "2. **NeoForge** : Allez dans 'config/neoforge-server.toml'.<br>"
				+ "   *(Note : En partie locale/Singleplayer, les mondes sont dans le dossier 'saves')*.<br>"
				+ "3. Changez **removeErroringBlockEntities** et **removeErroringEntities** en **true**.<br><br>"
				+ "<b>Autres options :</b><br>"
				+ "- **MCEdit** : Utilisez-le pour supprimer manuellement l'entité aux coordonnées indiquées.<br>"
				+ "- Si cette erreur persiste, il est possible que Neruina ne fonctionne pas correctement et génère simplement de nouvelles erreurs."
				+ "</span>";

		return mensajeBase + instrucciones;
	}

	@Override
	public String nombreApothicAttributeSinDueño() {
		return "Erreur d'Apothic Attributes";
	}

	@Override
	public String mensajeApothicAttributeSinDueño(boolean chestCavityDetectado) {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "<b>Apothic Attributes</b> a détecté un conflit : Un <b>AttributeMap</b> a été modifié sans avoir de propriétaire assigné.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "Cela se produit généralement lorsqu'un mod tente de modifier les attributs d'une entité (comme la vie, les dégâts, la vitesse) "
				+ "à un moment inapproprié ou de manière incorrecte.</span><br><br>";

		// Nota específica sobre Chest Cavity
		String notaChestCavity = "";
		if (chestCavityDetectado) {
			notaChestCavity = "<span style='color:#" + color + "'>"
					+ "<b>Le mod Chest Cavity a été détecté dans le log.</b> "
					+ "Ce mod est une cause fréquente de cette erreur spécifique en raison de la façon dont il gère les attributs des entités.</span><br><br>";
		}

		// Instrucciones de reparación
		String instrucciones = "<span style='color:#" + color + "'>" + "<b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Si Chest Cavity est installé, essayez de le mettre à jour ou de le supprimer temporairement pour vérifier s'il est la cause.</li>"
				+ "<li>Vérifiez s'il y a d'autres mods qui modifient les attributs des mobs et essayez de les désactiver.</li>"
				+ "<li>Recherchez des mises à jour d'<b>Apothic Attributes</b> car il pourrait s'agir d'une erreur corrigée dans les versions récentes.</li>"
				+ "</ul></span>";

		return mensajeBase + notaChestCavity + instrucciones;
	}

	@Override
	public String nombreErrorPotBlockEntity() {
		return "Erreur de DecoratedPot (Cataclysm)";
	}

	@Override
	public String mensajeErrorPotBlockEntity() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Une erreur d'incompatibilité avec <b>DecoratedPotBlockEntity</b> s'est produite.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "C'est un problème connu dans la version 1.19.2 du mod <b>L_Enders_Cataclysm</b>, "
				+ "où une implémentation requise par le jeu est manquante.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'>" + "<b>Solution recommandée :</b><br>"
				+ "Installez le mod <b>PotFix (Cataclysm Patch)</b> pour corriger cette erreur.<br>"
				+ "Vous pouvez le télécharger ici : <a href='https://www.curseforge.com/minecraft/mc-mods/potfix-cataclysm-patch'>CurseForge - PotFix</a>"
				+ "</span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorPreloadingTricks() {
		return "Erreur de Preloading Tricks";
	}

	@Override
	public String mensajeErrorPreloadingTricks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Un conflit causé par <b>Preloading Tricks</b> a été détecté.</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "L'erreur <i>ClassCastException: String cannot be cast to ModuleDescriptor</i> "
				+ "indique que le mod manipule les classes du système de modules Java de manière incorrecte.</span><br><br>";

		// Explicación y Solución
		String explicacion = "<span style='color:#" + color + "'>"
				+ "<b>Preloading Tricks</b> est un mod conçu principalement pour les <b>développeurs</b>. "
				+ "Il effectue des opérations complexes de modification de classes (mixins) à une étape très précoce du chargement du jeu, "
				+ "ce qui peut facilement briser la stabilité s'il y a d'autres interactions.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Supprimez le mod <b>Preloading Tricks</b>. Il n'est généralement pas nécessaire pour jouer sur des serveurs publics ou des packs stables.</li>"
				+ "<li>Si vous êtes développeur et en avez besoin pour les tests, vérifiez la configuration de votre environnement.</li>"
				+ "</ul></span>";

		return mensajeBase + explicacion;
	}

	@Override
	public String nombreErrorSimpleRadioLexiconfig() {
		return "Incompatibilité Simple Radio / Lexiconfig";
	}

	@Override
	public String mensajeErrorSimpleRadioLexiconfig() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Un conflit entre <b>Simple Radio</b> et <b>Lexiconfig</b> a été détecté.</span><br><br>"
				+ "<span style='color:#" + color + "'>"
				+ "L'erreur se produit lors du processus 'shelveLexicons', ce qui indique une incompatibilité binaire entre les deux bibliothèques.</span><br><br>";

		// Solución específica
		String solucion = "<span style='color:#" + color + "'>" + "<b>Cause connue :</b><br>"
				+ "Simple Radio est généralement conçu pour d'anciennes versions de Lexiconfig, tandis que vous avez installé une version plus récente.</span><br><br>"
				+ "<span style='color:#" + color + "'><b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Essayez d'utiliser une version plus ancienne de <b>Lexiconfig</b>.</li>"
				+ "<li>Il est recommandé d'essayer la version <b>1.3.11</b> ou antérieures, qui sont généralement compatibles avec Simple Radio.</li>"
				+ "<li>Si le problème persiste, vérifiez s'il y a une mise à jour de Simple Radio disponible.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	@Override
	public String nombreErrorMobAITweaks() {
		return "Erreur de Mob AI Tweaks";
	}

	@Override
	public String mensajeErrorMobAITweaks() {
		String color = Config.obtenerInstancia().obtenerColorError();

		// Mensaje principal
		String mensajeBase = "<span style='color:#" + color + "'>"
				+ "Une erreur liée à <b>Mob AI Tweaks</b> a été détectée.</span><br><br>" + "<span style='color:#"
				+ color + "'>" + "L'erreur provient d'un Mixin (<code>$mob-ai-tweaks$onSpawned</code>) qui intervient "
				+ "lorsqu'une entité apparaît (spawn). Cela indique généralement un conflit avec un autre mod "
				+ "qui modifie également le comportement d'apparition des mobs.</span><br><br>";

		// Solución
		String solucion = "<span style='color:#" + color + "'><b>Solution recommandée :</b><br>" + "<ul>"
				+ "<li>Essayez de supprimer <b>Mob AI Tweaks</b> pour vérifier si l'instabilité disparaît.</li>"
				+ "</ul></span>";

		return mensajeBase + solucion;
	}

	public String nombre_verificacion_gpu() {
		return "Vérification du GPU (OpenGL / Sélection du GPU)";
	}

	public String desactivar_parche_gpu() {
		return "Désactiver la vérification du GPU";
	}

	// ==================== CRASH ====================

	public String gpu_crash_posible() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Le vérificateur de GPU pourrait avoir causé la fermeture du jeu.</b>";
	}

	public String gpu_crash_causas() {
		return "La vérification a commencé mais ne s'est pas terminée. Cela indique généralement un échec dans OpenGL ou dans les pilotes graphiques.<br><br>"
				+ "Causes possibles :<br>" + "- Pilotes obsolètes ou instables<br>" + "- Problèmes avec OpenGL<br>"
				+ "- GPUs anciennes ou configurations hybrides";
	}

	public String gpu_crash_recomendaciones() {
		return "Recommandations :<br>" + "- Mettre à jour les pilotes du GPU<br>"
				+ "- Forcer l'utilisation du GPU dédié<br>" + "- Éviter les environnements distants ou virtualisés";
	}

	// ==================== NO ÓPTIMA ====================

	public String gpu_no_optima() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Le jeu n'utilise pas le meilleur GPU disponible.</b>";
	}

	public String gpu_no_optima_detalles() {
		return "Cela peut réduire les performances (FPS bas), mais ne cause généralement pas de crashs par lui-même.";
	}

	public String gpu_recomendaciones_rendimiento() {
		return "Recommandations :<br>" + "- Forcer le GPU dédié dans le panneau de configuration<br>"
				+ "- Configurer Java/Minecraft en mode haute performance";
	}

	// ==================== GENERALES ====================

	public String gpu_nota_precision() {
		return "<b>Note :</b> Ce système de détection n'est pas parfait à 100 %.";
	}

	public String gpu_consumo_energia() {
		return "Les GPUs les plus puissantes consomment plus d'énergie et peuvent réduire l'autonomie de la batterie sur les ordinateurs portables.";
	}

	public String gpu_parche_info() {
		return "Vous pouvez désactiver cette vérification en utilisant le bouton de solution rapide.";
	}

	@Override
	public String nombreVerificacionRaptorLake() {
		return "Avertissement de stabilité CPU Intel 13/14 Gen";
	}

	@Override
	public String advertenciaRaptorLakeTitulo() {
		return "Instabilité possible du processeur Intel Raptor Lake";
	}

	@Override
	public String advertenciaRaptorLakeDetalle(String cpu, String microcode, String targetMicrocode) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Un processeur " + cpu
				+ " avec le microcode " + microcode + " a été détecté." + "</b> "
				+ "Les processeurs Intel de 13e et 14e génération ont présenté des problèmes d'instabilité dus à une demande de tension excessive, "
				+ "ce qui peut raccourcir la durée de vie du processeur.<br><br>"
				+ "Il est recommandé de mettre à jour le microcode ou le BIOS de votre carte mère vers une version incluant le microcode <b>"
				+ targetMicrocode + "</b> ou supérieur. "
				+ "<b>Avertissement :</b> La mise à jour du BIOS comporte des risques si elle n'est pas effectuée correctement.<br><br>"
				+ "<i>Note : Ceci n'est presque certainement PAS la cause de votre crash actuel, c'est juste un avertissement informatif sur la santé du matériel.</i>";
	}

	@Override
	public String desactivarVerificacionRaptorLake() {
		return "Ne plus m'avertir à ce sujet";
	}

	@Override
	public String verArticuloRaptorLake(String fuente) {
		return "Lire l'article sur " + fuente;
	}

	@Override
	public String tituloMixins() {
		return "Explorateur de Mixins";
	}

	@Override
	public String mixinsBotonLateral() {
		return "Mixins";
	}

	@Override
	public String mixinsRaiz() {
		return "Mixins trouvés";
	}

	@Override
	public String mixinsTodosLosMods() {
		return "Tous";
	}

	@Override
	public String mixinsModConMixin() {
		return "Mod avec mixins";
	}

	@Override
	public String mixinsTooltipCombo() {
		return "Filtrer par mod contenant des mixins";
	}

	@Override
	public String mixinsRecargar() {
		return "Recharger";
	}

	@Override
	public String mixinsDescompilarSeleccion() {
		return "Décompiler la sélection";
	}

	@Override
	public String mixinsTargets() {
		return "Cibles";
	}

	@Override
	public String mixinsTarget() {
		return "Cible";
	}

	@Override
	public String mixinsTargetsMetodo() {
		return "Cibles de la méthode";
	}

	@Override
	public String mixinsMetodos() {
		return "Méthodes";
	}

	@Override
	public String mixinsCampos() {
		return "Champs";
	}

	@Override
	public String mixinsCantidad() {
		return "Quantité de mixins";
	}

	@Override
	public String mixinsDetalleMixin() {
		return "Détail du mixin";
	}

	@Override
	public String mixinsDetalleTarget() {
		return "Détail de la cible";
	}

	@Override
	public String mixinsDetalleMetodo() {
		return "Détail de la méthode mixin";
	}

	@Override
	public String mixinsDetalleCampo() {
		return "Détail du champ mixin";
	}

	@Override
	public String mixinsDetalleConflicto() {
		return "Détail du conflit";
	}

	@Override
	public String mixinsBuscarConflictos() {
		return "Rechercher les conflits possibles";
	}

	@Override
	public String mixinsResultadosConflictos() {
		return "Résultats des conflits";
	}

	@Override
	public String mixinsConflictosPosibles() {
		return "Conflits possibles";
	}

	@Override
	public String mixinsErrorDescompilar() {
		return "Erreur lors de la décompilation";
	}

	@Override
	public String mixinsColorPanel() {
		return "Couleur du panneau de mixins";
	}

	@Override
	public String mixinsColorTexto() {
		return "Couleur du texte des mixins";
	}

	@Override
	public String mixinsColorTextoSuave() {
		return "Couleur du texte secondaire des mixins";
	}

	@Override
	public String mixinsAyudaUso1() {
		return "Cet outil affiche les mods avec des mixins SpongePowered et permet d'inspecter leurs classes, cibles, méthodes et champs.";
	}

	@Override
	public String mixinsAyudaUso2() {
		return "Utilisez le sélecteur supérieur pour filtrer par un mod spécifique ou afficher tous les mods avec des mixins.";
	}

	@Override
	public String mixinsAyudaUso3() {
		return "Développez l'arbre pour voir chaque mixin, ses classes cibles, les méthodes annotées et les champs shadow.";
	}

	@Override
	public String mixinsAyudaUso4() {
		return "Faites un clic droit sur un mod, un mixin, une cible, une méthode ou un champ pour rechercher les conflits possibles avec d'autres mixins.";
	}

	@Override
	public String mixinsAyudaUso5() {
		return "Vous pouvez décompiler la sélection actuelle ou un résultat de conflit pour inspecter le code associé.";
	}

	@Override
	public String mixinsColorComboFondo() {
		return "Couleur de fond du sélecteur de mods";
	}

	@Override
	public String mixinsColorAreaContenidoFondo() {
		return "Couleur de fond du panneau de détails";
	}

	@Override
	public String mixinsColorSeleccionTexto() {
		return "Couleur de la sélection du texte";
	}

	@Override
	public String mixinsColorSeleccionTextoActivo() {
		return "Couleur du texte sélectionné";
	}

	@Override
	public String mixinsColorAyudaTexto() {
		return "Couleur du texte d'aide";
	}

	@Override
	public String mixinsColorArbolFondo() {
		return "Couleur de fond de l'arbre";
	}

	@Override
	public String mixinsColorRendererSeleccionTexto() {
		return "Couleur du texte sélectionné de l'arbre";
	}

	@Override
	public String mixinsColorRendererSeleccionFondo() {
		return "Couleur de fond de la sélection de l'arbre";
	}

	@Override
	public String mixinsColorRendererBordeSeleccion() {
		return "Couleur de la bordure de sélection de l'arbre";
	}

	@Override
	public String depmapTitulo() {
		return "Carte des Dépendances";
	}

	@Override
	public String depmapBotonLateral() {
		return "DepMap";
	}

	@Override
	public String depmapPestanaMapa() {
		return "Carte";
	}

	@Override
	public String depmapPestanaDependientes() {
		return "Dépendants";
	}

	@Override
	public String depmapRecargar() {
		return "Recharger";
	}

	@Override
	public String depmapDescompilarSeleccion() {
		return "Décompiler la sélection";
	}

	@Override
	public String depmapVerReferencias() {
		return "Voir les références";
	}

	@Override
	public String depmapDependencias() {
		return "Dépendances";
	}

	@Override
	public String depmapDependientes() {
		return "Dépendants";
	}

	@Override
	public String depmapDependiente() {
		return "Dépendant";
	}

	@Override
	public String depmapSinDependencias() {
		return "Sans dépendants";
	}

	@Override
	public String depmapSeleccionarMod() {
		return "Sélectionner un mod";
	}

	@Override
	public String depmapSeleccionarModBase() {
		return "Mod de base";
	}

	@Override
	public String depmapSeleccionarDependiente() {
		return "Mod dépendant";
	}

	@Override
	public String depmapSeleccionarPaquete() {
		return "Package";
	}

	@Override
	public String depmapComprobarNoAlineadas() {
		return "Vérifier les non-alignés";
	}

	@Override
	public String depmapResultadoNoAlineadas() {
		return "Résultats des dépendances non alignées";
	}

	@Override
	public String depmapClaseInexistente() {
		return "Classe inexistante";
	}

	@Override
	public String depmapClaseReferenciada() {
		return "Classe référencée";
	}

	@Override
	public String depmapOrigen() {
		return "Origine";
	}

	@Override
	public String depmapDestino() {
		return "Destination";
	}

	@Override
	public String depmapDependenciaDetalle() {
		return "Détail de la dépendance";
	}

	@Override
	public String depmapReferenciaDetalle() {
		return "Détail de la référence";
	}

	@Override
	public String depmapMetodoOrigen() {
		return "Méthode d'origine";
	}

	@Override
	public String depmapModBase() {
		return "Mod de base";
	}

	@Override
	public String depmapTodos() {
		return "Tous";
	}

	@Override
	public String depmapSeleccioneUnMod() {
		return "Sélectionnez un mod";
	}

	@Override
	public String depmapSeleccioneParametrosNoAlineadas() {
		return "Sélectionnez le mod de base, le dépendant et le package";
	}

	@Override
	public String depmapSeleccioneClaseParaDescompilar() {
		return "Sélectionnez une référence ou une découverte à décompiler";
	}

	@Override
	public String depmapErrorDescompilar() {
		return "Erreur lors de la décompilation";
	}

	@Override
	public String depmapAyuda1() {
		return "Cet outil construit une carte des dépendances entre les mods en utilisant les références de classes entre eux.";
	}

	@Override
	public String depmapAyuda2() {
		return "L'onglet Carte affiche un graphe à bulles avec chaque mod lié aux dépendances qu'il utilise.";
	}

	@Override
	public String depmapAyuda3() {
		return "L'onglet Dépendants trie les mods de celui qui a le plus de dépendants à celui qui n'en a aucun.";
	}

	@Override
	public String depmapAyuda4() {
		return "Vous pouvez inspecter des références concrètes entre un mod et ses dépendances, ainsi que décompiler les classes associées.";
	}

	@Override
	public String depmapAyuda5() {
		return "La vérification des dépendances non alignées recherche des références à des classes inexistantes dans un package ou sous-package du mod de base.";
	}

	@Override
	public String depmapColorPanel() {
		return "Couleur des panneaux";
	}

	@Override
	public String depmapColorTexto() {
		return "Couleur du texte principal";
	}

	@Override
	public String depmapColorTextoSecundario() {
		return "Couleur du texte secondaire";
	}

	@Override
	public String depmapColorAyudaTexto() {
		return "Couleur du texte d'aide";
	}

	@Override
	public String depmapColorGrafoFondo() {
		return "Couleur de fond du graphe";
	}

	@Override
	public String depmapColorListaFondo() {
		return "Couleur de fond des listes";
	}

	@Override
	public String depmapColorArbolFondo() {
		return "Couleur de fond de l'arbre";
	}

	@Override
	public String depmapColorNodo() {
		return "Couleur des nœuds du graphe";
	}

	@Override
	public String depmapColorEnlace() {
		return "Couleur des liens du graphe";
	}

	@Override
	public String depmapColorSeleccion() {
		return "Couleur de la sélection";
	}

	@Override
	public String depmapColorSeleccionTexto() {
		return "Couleur du texte sélectionné";
	}

	@Override
	public String nombreProblemaAzureLibAnimaciones() {
		return "Problème avec un addon AzureLib";
	}

	@Override
	public String mensajeProblemaAzureLibAnimaciones() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur d'AzureLib lors du chargement des animations a été détectée.</b>"
				+ "<p>Le jeu a trouvé un JSON d'animations avec un format incorrect.</p>"
				+ "<p>Ce problème est généralement causé par l'un des mods ou addons qui utilisent <b>AzureLib</b>.</p>"
				+ "<p><b>Recommandation :</b></p>" + "<ul>"
				+ "<li>Utilisez le <b>DepMap</b> de la barre latérale pour localiser tous les addons qui dépendent d'AzureLib.</li>"
				+ "<li>Essayez de lancer le jeu sans certains de ces addons jusqu'à trouver celui qui échoue.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreProblemaDecocraftNatureEssentialPartnerMod() {
		return "Problème avec Decocraft Nature";
	}

	@Override
	public String mensajeProblemaDecocraftNatureEssentialPartnerMod() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un problème causé par Decocraft Nature a été détecté.</b>"
				+ "<p>L'erreur se produit lors de l'initialisation de la configuration mixin "
				+ "<code>com/razz/essentialpartnermod/mixins.json</code>.</p>"
				+ "<p>Ce problème peut être corrigé en éditant le fichier JAR du mod.</p>" + "<p><b>Étapes :</b></p>"
				+ "<ul>"
				+ "<li>Ouvrez le fichier JAR avec un archiviste comme File Roller, Ark, WinRAR, 7-Zip ou WinZip.</li>"
				+ "<li>Accédez à <code>META-INF/MANIFEST.MF</code>.</li>" + "<li>Supprimez cette ligne :</li>" + "</ul>"
				+ "<code>MixinConfigs: com/razz/essentialpartnermod/mixins.json</code>"
				+ "<p><b>Important :</b> conservez la seule ligne vide à la fin du fichier.</p>";
	}

	@Override
	public String mensajeTetraDeserializadorModeloEstatico() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur a été détectée dans Tetra ou l'un de ses addons.</b>"
				+ "<p>Le journal indique qu'un désérialisateur pour un type de modèle utilisé par <b>Tetra</b> ou l'un de ses compléments est introuvable.</p>"
				+ "<p><b>Étapes recommandées :</b></p>" + "<ul>"
				+ "<li>Tout d'abord, supprimez ou désactivez les <b>addons de Tetra</b> et réessayez.</li>"
				+ "<li>Si l'erreur persiste, essayez de supprimer également <b>Tetra</b>.</li>"
				+ "<li>Vous pouvez essayer de localiser les addons liés à Tetra dans le <b>DepMap</b>, bien qu'ils n'y apparaissent pas toujours.</li>"
				+ "</ul>"
				+ "<p>Dans certains cas, le problème vient d'un addon, mais dans d'autres, il est causé par <b>Tetra</b> lui-même.</p>";
	}

	@Override
	public String nombreTetraDeserializadorModeloEstatico() {
		return "Erreur de désérialisation de modèle dans Tetra";
	}

	@Override
	public String mensajeSimpleEmotesSetupAnimTail() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Une erreur a été détectée dans Simple Emotes.</b>"
				+ "<p>Le journal contient la chaîne <b>$simpleemotes$setupAnimTAIL</b>, qui pointe directement vers le mod <b>Simple Emotes</b>.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Supprimez ou désactivez <b>Simple Emotes</b> et réessayez.</li>"
				+ "<li>Si vous utilisez des mods qui changent les animations du joueur ou du modèle, vérifiez les incompatibilités possibles avec <b>Simple Emotes</b>.</li>"
				+ "<li>Mettez à jour <b>Simple Emotes</b> et tout mod lié aux animations vers des versions compatibles.</li>"
				+ "</ul>"
				+ "<p>Cette erreur indique généralement que <b>Simple Emotes</b> est directement impliqué dans l'échec.</p>";
	}

	@Override
	public String nombreSimpleEmotesSetupAnimTail() {
		return "Erreur dans Simple Emotes";
	}

	// En Idioma

	@Override
	public String mensajeAdvertenciaSKLauncher() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Avertissement concernant SKLauncher.</b>"
				+ "<p>Au cours des derniers mois, plusieurs cas de <b>corruption</b> et d'autres problèmes liés à <b>SKLauncher</b> ont été observés.</p>"
				+ "<p>Cela ne signifie pas que SKLauncher est toujours la cause de l'erreur, mais il peut contribuer au problème.</p>"
				+ "<p><b>Signes de corruption possible :</b></p>" + "<ul>"
				+ "<li>Le jeu se ferme très tôt pendant le démarrage.</li>"
				+ "<li>Le jeu échoue également même <b>sans mods installés</b>.</li>" + "</ul>"
				+ "<p>Si l'un de ces cas se produit, essayez d'utiliser <b>un autre lanceur</b> pour vérifier si le problème disparaît.</p>"
				+ "<p>Consultez la liste des lanceurs recommandés ici :</p>"
				+ "<p><a href='https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/docs/ingles/minecraft/Launchers.md'>Voir la documentation des lanceurs</a></p>";
	}

	@Override
	public String nombreAdvertenciaSKLauncher() {
		return "Avertissement : problèmes possibles avec SKLauncher";
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
		return "Scanner les serveurs et les malwares";
	}

	@Override
	public String guardEscanearServidores() {
		return "Rechercher les serveurs";
	}

	@Override
	public String guardEscanearMalware() {
		return "Rechercher les malwares";
	}

	@Override
	public String guardTablaServidores() {
		return "Serveurs problématiques";
	}

	@Override
	public String guardTablaMalware() {
		return "Découvertes de malware";
	}

	@Override
	public String guardColServidor() {
		return "Serveur";
	}

	@Override
	public String guardColDefinicion() {
		return "Définition";
	}

	@Override
	public String guardColMensaje() {
		return "Message";
	}

	@Override
	public String guardColUbicacion() {
		return "Emplacement";
	}

	@Override
	public String guardColClase() {
		return "Classe";
	}

	@Override
	public String guardColCfr() {
		return "CFR";
	}

	@Override
	public String guardCfr() {
		return "Décompiler";
	}

	@Override
	public String guardCfrTitulo() {
		return "Code décompilé";
	}

	@Override
	public String guardDescripcion1() {
		return "Cet outil permet de rechercher des serveurs problématiques et d'éventuelles découvertes de malware dans les mods.";
	}

	@Override
	public String guardDescripcion2() {
		return "Il peut y avoir des faux positifs, surtout lorsque d'autres définitions ou scanners de malware sont agressifs.";
	}

	@Override
	public String guardDescripcion3() {
		return "La vérification des serveurs utilise des définitions externes. Si vous ne les avez pas téléchargées, vous devrez d'abord les télécharger.";
	}

	@Override
	public String guardDescripcion4() {
		return "Si vous avez déjà des définitions locales, l'outil vous permettra de décider si vous souhaitez les réutiliser ou les mettre à jour.";
	}

	@Override
	public String guardDescripcion5() {
		return "Dans le tableau des malwares, si une classe est disponible, vous pourrez la décompiler avec CFR pour l'inspecter.";
	}

	@Override
	public String guardEstadoEscaneandoTodo() {
		return "Scan des serveurs et des malwares en cours...";
	}

	@Override
	public String guardEstadoEscaneandoServidores() {
		return "Recherche de serveurs problématiques en cours...";
	}

	@Override
	public String guardEstadoEscaneandoMalware() {
		return "Recherche de malware en cours...";
	}

	@Override
	public String guardEstadoListo() {
		return "Prêt";
	}

	@Override
	public String guardDefsNoEncontradasTitulo() {
		return "Définitions introuvables";
	}

	@Override
	public String guardDefsNoEncontradasMensaje() {
		return "Aucune définition de serveur n'a été trouvée. Voulez-vous les télécharger maintenant ?";
	}

	@Override
	public String guardDefsDescargar() {
		return "Télécharger";
	}

	@Override
	public String guardDefsCancelar() {
		return "Annuler";
	}

	@Override
	public String guardDefsActualizarTitulo() {
		return "Définitions de serveurs";
	}

	@Override
	public String guardDefsActualizarMensaje() {
		return "Des définitions locales existent déjà. Voulez-vous les utiliser telles quelles ou les mettre à jour ?";
	}

	@Override
	public String guardDefsUsarLocales() {
		return "Utiliser les locales";
	}

	@Override
	public String guardDefsActualizar() {
		return "Mettre à jour";
	}

	@Override
	public String guardFuenteDefinicionesTLauncher() {
		return "Liste de TLauncher";
	}

	@Override
	public String guardErrorDescompilar() {
		return "Erreur lors de la décompilation";
	}

	@Override
	public String guardColorPanel() {
		return "Couleur du panneau";
	}

	@Override
	public String guardColorTexto() {
		return "Couleur de du texte";
	}

	@Override
	public String guardColorTextoSecundario() {
		return "Couleur du texte secondaire";
	}

	@Override
	public String guardColorTabla() {
		return "Couleur des tableaux";
	}

	@Override
	public String guardColorSeleccion() {
		return "Couleur de la sélection";
	}

	@Override
	public String guardColorSeleccionTexto() {
		return "Couleur du texte sélectionné";
	}

	@Override
	public String texto_de_boton_compartir_instancia_modpack() {
		return "Partager l'instance/modpack";
	}

	@Override
	public String popup_compartir_instancia_modpack() {
		return "La fonction de partage de l'instance ou du modpack n'est pas encore implémentée.";
	}

	@Override
	public String colorBotonCompartirVerdeOscuro() {
		return "Couleur du bouton principal de partage";
	}

	@Override
	public String colorBotonCompartirVerdeClaro() {
		return "Couleur du bouton de partage de liens";
	}

	@Override
	public String colorTextoBotonesCompartir() {
		return "Couleur du texte des boutons de partage";
	}

	@Override
	public String compartirInstanciaTitulo() {
		return "Partager l'instance";
	}

	@Override
	public String compartirInstanciaBotonLateral() {
		return "Partager l'instance";
	}

	@Override
	public String compartirInstanciaFormato() {
		return "Format";
	}

	@Override
	public String compartirInstanciaServicio() {
		return "Service d'upload";
	}

	@Override
	public String compartirInstanciaBotonCompartir() {
		return "Empaqueter et partager";
	}

	@Override
	public String compartirInstanciaBotonRefrescar() {
		return "Actualiser";
	}

	@Override
	public String compartirInstanciaEstadoListo() {
		return "Prêt";
	}

	@Override
	public String compartirInstanciaEstadoEmpaquetando() {
		return "Empaquetage de la sélection...";
	}

	@Override
	public String compartirInstanciaEstadoSubiendo() {
		return "Téléversement du fichier...";
	}

	@Override
	public String compartirInstanciaEstadoError() {
		return "Erreur";
	}

	@Override
	public String compartirInstanciaCodigo() {
		return "Code";
	}

	@Override
	public String compartirInstanciaEnlace() {
		return "Lien";
	}

	@Override
	public String compartirInstanciaMantenerAbierto() {
		return "Vous devez maintenir l'application ouverte pour que le transfert reste disponible.";
	}

	@Override
	public String compartirInstanciaSinSeleccion() {
		return "Aucun dossier ou fichier sélectionné.";
	}

	@Override
	public String compartirInstanciaFormatoNoSoportado() {
		return "Ce format n'est pas encore pris en charge.";
	}

	@Override
	public String compartirInstanciaServicioNoDisponible() {
		return "Le service sélectionné n'est pas disponible.";
	}

	@Override
	public String compartirInstanciaSubidaCompleta() {
		return "Transfert initié avec succès.";
	}

	@Override
	public String compartirInstanciaErrorSubir() {
		return "Impossible de téléverser le fichier sélectionné.";
	}

	@Override
	public String compartirInstanciaColorPanel() {
		return "Couleur du panneau";
	}

	@Override
	public String compartirInstanciaColorTexto() {
		return "Couleur du texte";
	}

	@Override
	public String compartirInstanciaPolitica1() {
		return "Types recommandés : mods, configs, saves, worlds, datapacks, resource packs et fichiers d'options. Évitez d'inclure du matériel privé non nécessaire.";
	}

	@Override
	public String compartirInstanciaPolitica2() {
		return "Les extensions peuvent ajouter leurs propres services d'upload. Les services intégrés par défaut doivent s'afficher ici.";
	}

	@Override
	public String compartirInstanciaPolitica3() {
		return "wormhole.app : jusqu'à 5 Gio en upload normal ; entre 5 et 10 Gio nécessite de maintenir l'émetteur ouvert. Dans l'implémentation actuelle du projet, l'intégration réelle est encore en attente.";
	}

	@Override
	public String compartirInstanciaPolitica4() {
		return "limewire.com : conçu comme un service avec rétention temporaire. Pas encore pris en charge par cette implémentation.";
	}

	@Override
	public String compartirInstanciaPolitica5() {
		return "bittorrent : mode plus sûr car il s'agit d'une distribution P2P directe, sans hébergement central. Pas encore pris en charge par cette implémentation.";
	}

	@Override
	public String compartirInstanciaPolitica6() {
		return "Par défaut, les dossiers et fichiers les plus courants d'une instance sont sélectionnés pour faciliter le support technique.";
	}

	@Override
	public String compartirInstanciaPolitica7() {
		return "Si vous incluez le dossier interne de CrashDetector, les configurations, journaux et données auxiliaires seront également inclus, vous pouvez donc les désélectionner si ce n'est pas nécessaire.";
	}

	@Override
	public String malware_fracturiser_detectado() {
		return "Fracturiser possible détecté. Preuves :";
	}

	@Override
	public String malware_info_stealer_detectado() {
		return "Voleur d'informations possible détecté. Preuves :";
	}

	@Override
	public String malware_evidencia_clase_sospechosa() {
		return "Classe suspecte :";
	}

	@Override
	public String malware_evidencia_archivo_sospechoso() {
		return "Fichier suspect :";
	}

	@Override
	public String malware_brightsdk_detectado() {
		return "Bright SDK possible détecté. Preuves :";
	}

	@Override
	public String malware_evidencia_paquete_sospechoso() {
		return "Package suspect :";
	}

	@Override
	public String docsTituloVentana() {
		return "Lecteur de documents";
	}

	@Override
	public String docsAyudaDeUso() {
		return "<b>Comment utiliser ce lecteur</b><br>"
				+ "Sélectionnez une langue en bas pour voir la documentation disponible dans cette langue.<br>"
				+ "Dans le panneau de gauche, vous pouvez naviguer dans les dossiers et les documents.<br>"
				+ "En cliquant sur un document, son contenu apparaîtra à droite.<br>"
				+ "Les liens internes avec le protocole <b>docs://</b> ouvrent d'autres documents dans ce même lecteur.";
	}

	@Override
	public String docsArbolTitulo() {
		return "Documents";
	}

	@Override
	public String docsVisorTitulo() {
		return "Contenu";
	}

	@Override
	public String docsNoHayDocumentos() {
		return "Aucun document pour cette langue.";
	}

	@Override
	public String docsNoHayMarkdown() {
		return "Aucun fichier Markdown trouvé pour cette langue.";
	}

	@Override
	public String docsDocumentoNoEncontrado() {
		return "Le document demandé est introuvable.";
	}

	@Override
	public String docsErrorAlAbrirDocumento() {
		return "Erreur lors de l'ouverture du document :";
	}

	@Override
	public String docsCargando() {
		return "Chargement des documents...";
	}

	@Override
	public String docsImagenNoDisponible() {
		return "Illustration non disponible";
	}

	@Override
	public String colorPanelSecundario() {
		return "Couleur du panneau secondaire";
	}

	@Override
	public String colorTextoSuave() {
		return "Couleur du texte doux";
	}

	@Override
	public String colorSeleccion() {
		return "Couleur de la sélection";
	}

	@Override
	public String colorFondoDocumento() {
		return "Couleur de fond du document";
	}

	@Override
	public String iaTituloVentana() {
		return "IA";
	}

	@Override
	public String iaTituloPrincipal() {
		return "Analyse avec IA";
	}

	@Override
	public String iaDescripcionTitulo() {
		return "Agent d'analyse de crashes";
	}

	@Override
	public String iaDescripcionCuerpo() {
		return "Cet outil ouvre un agent externe qui peut vous aider à analyser les crashes, les erreurs et les journaux liés à Minecraft.";
	}

	@Override
	public String iaDescripcionUso() {
		return "Pour utiliser ce système, ouvrez le lien, connectez-vous avec un compte Baidu, puis utilisez l'agent pour examiner votre crash ou vos logs.";
	}

	@Override
	public String iaAvisoCuentaBaidu() {
		return "Vous aurez besoin d'un compte Baidu pour accéder à l'agent.";
	}

	@Override
	public String iaCopiarEnlace() {
		return "Copier le lien";
	}

	@Override
	public String iaAbrirEnlace() {
		return "Ouvrir le lien";
	}

	@Override
	public String iaImagenNoDisponible() {
		return "Image non disponible";
	}

	@Override
	public String mensajeOculusIrisUnknownShaderVariable() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur possible des shaders avec Oculus ou Iris détectée.</b>"
				+ "<p>Le journal contient à la fois <b>kroppeb.stareval.resolver.ExpressionResolver.resolveExpressionInternal</b> "
				+ "et <b>java.lang.RuntimeException: Unknown variable:</b>.</p>"
				+ "<p>Cette combinaison indique généralement un problème lors de l'évaluation d'une variable dans un shader，"
				+ "souvent lié à <b>Oculus</b>, <b>Iris</b>, ou au <b>pack de shaders</b> utilisé.</p>"
				+ "<p><b>Ordre de test recommandé：</b></p>" + "<ul>"
				+ "<li>Tout d'abord，lancez le jeu <b>sans activer les shaders</b>.</li>"
				+ "<li>Si le problème persiste，essayez de lancer <b>sans Oculus ni Iris</b>.</li>"
				+ "<li>Mettez à jour <b>Oculus/Iris</b>, le <b>pack de shaders</b> et les mods graphiques associés.</li>"
				+ "<li>Si vous utilisez d'autres mods de rendu ou graphiques，vérifiez les incompatibilités entre eux.</li>"
				+ "</ul>"
				+ "<p>En pratique，cette panne provient souvent du <b>pack de shaders</b> ou de son interaction avec <b>Oculus/Iris</b>.</p>";
	}

	@Override
	public String nombreOculusIrisUnknownShaderVariable() {
		return "Erreur possible des shaders avec Oculus/Iris";
	}

	@Override
	public String mensajeItemNoExiste(String itemFaltante, String namespace) {
		String itemHtml = itemFaltante == null || itemFaltante.isEmpty() ? "(inconnu)" : itemFaltante;
		String namespaceHtml = namespace == null || namespace.isEmpty() ? "(inconnu)" : namespace;

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Tentative d'utilisation d'un objet inexistant.</b>" + "<p>Le journal contient la ligne <b>Item: "
				+ itemHtml + " does not exist</b>.</p>"
				+ "<p>Cela signifie généralement qu'un <b>datapack</b>, un <b>mod</b> ou une <b>configuration</b> "
				+ "fait référence à un objet qui n'est pas présent dans le jeu.</p>" + "<p><b>À vérifier :</b></p>"
				+ "<ul>" + "<li>Vérifiez si vous avez installé le mod qui est censé fournir l'objet <b>" + itemHtml
				+ "</b>.</li>"
				+ "<li>S'il est installé, il peut s'agir de la <b>mauvaise version</b>, l'objet a pu être modifié ou supprimé, "
				+ "ou le mod présente un problème et il serait préférable de le retirer.</li>"
				+ "<li>Si vous n'avez pas ce mod, essayez de <b>l'installer</b>.</li>" + "</ul>"
				+ "<p><b>Pour savoir quel mod ou datapack demande cet objet :</b></p>" + "<ul>"
				+ "<li>Utilisez l'utilitaire <b>grepr</b> dans la barre latérale.</li>"
				+ "<li><b>Ne</b> sélectionnez pas de dossier.</li>"
				+ "<li>Activez l'option <b>search in archives</b>.</li>"
				+ "<li>Dans le texte de recherche, saisissez le <b>namespace</b>, c'est-à-dire la partie avant les deux-points : "
				+ "<b>" + namespaceHtml + "</b>.</li>" + "</ul>"
				+ "<p>Cela aide généralement à trouver quel fichier, mod ou datapack effectue la référence invalide.</p>";
	}

	@Override
	public String nombreItemNoExiste() {
		return "Objet inexistant référencé";
	}

	@Override
	public String mensajeCobblemonPinkanIslandsRhyhornModelo() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur de modèle détectée pour Rhyhorn.</b>"
				+ "<p>Le journal contient la ligne <b>Unable to load model cobblemon:rhyhorn_male.geo for cobblemon:rhyhorn</b>.</p>"
				+ "<p>Bien que le modèle utilise l'espace de noms (namespace) de <b>Cobblemon</b>, cette ligne est généralement causée par le mod "
				+ "<b>Cobblemon: Pinkan Islands</b>, d'où provient ce <b>Rhyhorn</b>.</p>" + "<p><b>À essayer :</b></p>"
				+ "<ul>" + "<li>Supprimez ou désactivez <b>Cobblemon: Pinkan Islands</b> et réessayez.</li>"
				+ "<li>Mettez à jour <b>Cobblemon</b> et <b>Cobblemon: Pinkan Islands</b> vers des versions compatibles entre elles.</li>"
				+ "<li>Si le problème a commencé après la mise à jour de l'un de ces mods, essayez une combinaison de versions différente.</li>"
				+ "</ul>"
				+ "<p>Cette panne indique généralement un modèle manquant, mal enregistré ou incompatible au sein de "
				+ "<b>Cobblemon: Pinkan Islands</b>.</p>";
	}

	@Override
	public String nombreCobblemonPinkanIslandsRhyhornModelo() {
		return "Erreur de modèle de Rhyhorn dans Cobblemon: Pinkan Islands";
	}

	@Override
	public String mensajeColdSweatInitDynamicTags() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur détectée dans Cold Sweat.</b>"
				+ "<p>Le journal contient des indices tels que <b>$cold_sweat$onBuildStart</b>, "
				+ "<b>InitDynamicTagsEvent.fillTag</b> et une <b>NullPointerException</b> où "
				+ "le registre apparaît comme nul.</p>"
				+ "<p>Cela indique généralement un problème de <b>Cold Sweat</b> lors de la construction ou du remplissage "
				+ "des <b>tags dynamiques</b>, souvent dû à une incompatibilité, une erreur interne du mod "
				+ "ou une combinaison conflictuelle avec un autre mod ou datapack.</p>" + "<p><b>À essayer :</b></p>"
				+ "<ul>" + "<li>Supprimez ou désactivez <b>Cold Sweat</b> et réessayez.</li>"
				+ "<li>Mettez à jour <b>Cold Sweat</b> vers une version compatible avec votre version de Minecraft et votre chargeur (loader).</li>"
				+ "<li>Si vous utilisez des datapacks ou des mods qui altèrent les <b>tags</b>, les <b>biomes</b>, les <b>températures</b> ou les registres associés, vérifiez-les également.</li>"
				+ "<li>Si l'erreur a commencé après la mise à jour des mods, essayez une combinaison de versions différente.</li>"
				+ "</ul>" + "<p>Dans ce cas, <b>Cold Sweat</b> est directement impliqué dans la panne.</p>";
	}

	@Override
	public String nombreColdSweatInitDynamicTags() {
		return "Erreur de Cold Sweat dans les tags dynamiques";
	}

	@Override
	public String mensajeClassCastExceptionGeneral(String lineaClassCast) {
		String detalle = lineaClassCast == null || lineaClassCast.isEmpty() ? ""
				: "<p><b>Ligne détectée :</b></p><p><code>" + lineaClassCast + "</code></p>";

		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "ClassCastException détecté.</b>"
				+ "<p>Cela signifie qu'une classe a été traitée comme si elle était une autre classe ou une interface incompatible.</p>"
				+ detalle + "<p>Ce type d'erreur est généralement causé par l'une de ces situations :</p>" + "<ul>"
				+ "<li><b>Deux mods incompatibles</b> entre eux.</li>"
				+ "<li><b>Mixins</b>, <b>transformers</b> ou patches qui modifient une classe et amènent une autre partie du jeu à attendre un type différent.</li>"
				+ "<li>Autres mods présents dans le <b>stacktrace</b> qui provoquent la mauvaise conversion (miscast).</li>"
				+ "</ul>" + "<p><b>À vérifier :</b></p>" + "<ul>"
				+ "<li>Consultez les lignes du <b>stacktrace</b> liées à cette erreur.</li>"
				+ "<li>Portez une attention particulière aux noms de mods ou de classes au format <b>$modid$algo</b>, car ils indiquent généralement les mods impliqués.</li>"
				+ "<li>Essayez de mettre à jour, supprimer ou isoler les mods qui semblent liés à la conversion invalide.</li>"
				+ "</ul>"
				+ "<p>Bien qu'un <b>ClassCastException</b> ne soit pas toujours fatal, il l'est très souvent.</p>";
	}

	@Override
	public String nombreClassCastExceptionGeneral() {
		return "ClassCastException détecté";
	}

	@Override
	public String mensajeValkyrienSkiesTournamentLithiumPoiInjection() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilité potentielle détectée entre Valkyrien Skies Tournament et Lithium/Radium.</b>"
				+ "<p>Le journal contient une <b>InvalidInjectionException</b> où apparaît un mixin de "
				+ "<b>Lithium</b> sur <b>POI</b> ainsi que <b>valkyrienskies-common.mixins.json:feature.poi.MixinPOIManager</b>.</p>"
				+ "<p>Ce problème survient généralement lorsqu'une <b>ancienne version de Lithium</b> ou un "
				+ "<b>fork basé sur un ancien Lithium</b>, comme <b>Radium Reforged</b>, est utilisé conjointement avec "
				+ "<b>VS Tournament</b>.</p>" + "<p><b>À essayer :</b></p>" + "<ul>"
				+ "<li>Mettez à jour <b>Lithium</b> vers une version plus récente et compatible.</li>"
				+ "<li>Si vous êtes sous <b>Forge/NeoForge</b> et utilisez <b>Radium Reforged</b> ou un autre ancien fork, supprimez-le.</li>"
				+ "<li>À la place, essayez <b>Harium</b>, qui est un fork moderne de Radium synchronisé avec les améliorations récentes de Lithium.</li>"
				+ "<li>Si le problème a commencé après la mise à jour de mods, vérifiez la combinaison exacte entre <b>VS Tournament</b> et votre mod d'optimisation AI/POI.</li>"
				+ "</ul>"
				+ "<p>En pratique, ce dysfonctionnement provient généralement d'une ancienne implémentation de <b>Lithium/Radium</b> qui ne fonctionne pas bien avec <b>VS Tournament</b>.</p>";
	}

	@Override
	public String nombreValkyrienSkiesTournamentLithiumPoiInjection() {
		return "Incompatibilité de VS Tournament avec Lithium/Radium";
	}

	@Override
	public String mensajeVSTournamentVSConfigClassNoExiste() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "VS Tournament semble être trop ancien pour votre version de Valkyrien Skies.</b>"
				+ "<p>Le journal contient une <b>NoClassDefFoundError</b> pour "
				+ "<b>org/valkyrienskies/core/impl/config/VSConfigClass</b> ainsi qu'une ligne de "
				+ "<b>org.valkyrienskies.tournament.TournamentMod.init(...)</b>.</p>"
				+ "<p>Cela signifie généralement que vous utilisez une <b>ancienne version de VS Tournament</b> qui tente "
				+ "d'utiliser des classes internes obsolètes de <b>Valkyrien Skies</b> qui n'existent plus.</p>"
				+ "<p><b>Que faire :</b></p>" + "<ul>" + "<li>Supprimez l'ancien <b>VS Tournament</b>.</li>"
				+ "<li>Utilisez <b>VS Tournament Reforged</b> à la place.</li>"
				+ "<li>Vérifiez également que la version de <b>Valkyrien Skies</b> correspond à la version prise en charge par l'addon.</li>"
				+ "</ul>"
				+ "<p>La recommandation de passer à <b>VS Tournament Reforged</b> correspond à l'état actuel du projet： "
				+ "la version originale de Tournament est toujours répertoriée comme un ancien mod pour la 1.18.2, tandis que "
				+ "<b>VS Tournament Reforged</b> est publié séparément et annonce actuellement la prise en charge de Valkyrien "
				+ "2.4.9+ sur Forge 1.20.1.</p>";
	}

	@Override
	public String nombreVSTournamentVSConfigClassNoExiste() {
		return "VS Tournament ancien incompatible avec Valkyrien Skies";
	}

	public String curseForgeClaveApiMundial() {
		return "Clé API globale de CurseForge";
	}

	public String curseForgeEndpoint() {
		return "Point de terminaison CurseForge";
	}

	public String tlmodsEndpoint() {
		return "Point de terminaison TLMods";
	}

	public String minecraftStorageEndpoint() {
		return "Point de terminaison MinecraftStorage";
	}

	public String autoBackupActivado() {
		return "Sauvegarde automatique activée";
	}

	public String autoBackupFrecuencia() {
		return "Fréquence de sauvegarde automatique";
	}

	public String autoBackupDiasConservar() {
		return "Jours de conservation des sauvegardes automatiques";
	}

	public String autoBackupTamanoMaximoMB() {
		return "Taille maximale des sauvegardes automatiques (MB)";
	}

	public String actualizadorModsTitulo() {
		return "Gestionnaire de mises à jour des mods";
	}

	public String actualizadorModsBotonSidebar() {
		return "Mises à jour";
	}

	public String actualizadorModsDescripcion() {
		return "Recherche les mises à jour disponibles pour les mods du modpack. Vous pouvez tout mettre à jour ou appliquer des mises à jour individuelles.";
	}

	public String actualizadorModsBotonEscanear() {
		return "Vérifier les mises à jour";
	}

	public String actualizadorModsBotonActualizarTodo() {
		return "Mettre tout à jour";
	}

	public String actualizadorModsBotonActualizarUno() {
		return "Mettre à jour";
	}

	public String actualizadorModsEstadoListo() {
		return "Prêt";
	}

	public String actualizadorModsEstadoEscaneando() {
		return "Recherche de mises à jour...";
	}

	public String actualizadorModsEstadoActualizando() {
		return "Mise à jour...";
	}

	public String actualizadorModsEstadoSinActualizaciones() {
		return "Aucune mise à jour disponible.";
	}

	public String actualizadorModsEstadoEncontradas(int n) {
		return "Mises à jour trouvées : " + n;
	}

	public String actualizadorModsEstadoActualizadas(int n) {
		return "Mises à jour appliquées : " + n;
	}

	public String actualizadorModsEstadoError() {
		return "Erreur lors de la mise à jour.";
	}

	public String actualizadorModsSinSeleccion() {
		return "Aucune mise à jour sélectionnée.";
	}

	public String actualizadorModsColumnaMod() {
		return "Mod";
	}

	public String actualizadorModsColumnaActual() {
		return "Actuel";
	}

	public String actualizadorModsColumnaNueva() {
		return "Nouveau";
	}

	public String actualizadorModsColumnaFuente() {
		return "Source";
	}

	public String actualizadorModsColumnaLoader() {
		return "Chargeur";
	}

	public String actualizadorModsColumnaRuta() {
		return "Chemin";
	}

	public String actualizadorModsColumnaAccion() {
		return "Action";
	}

	public String actualizadorModsColorFondo() {
		return "Updater : fond";
	}

	public String actualizadorModsColorPanel() {
		return "Updater : panneau";
	}

	public String actualizadorModsColorTexto() {
		return "Updater : texte";
	}

	public String actualizadorModsColorTextoSuave() {
		return "Updater : texte discret";
	}

	public String actualizadorModsColorBoton() {
		return "Updater : bouton";
	}

	public String actualizadorModsColorBotonTexto() {
		return "Updater : texte du bouton";
	}

	public String actualizadorModsColorTabla() {
		return "Updater : tableau";
	}

	public String actualizadorModsColorSeleccion() {
		return "Updater : sélection";
	}

	public String importadorYumeiriTeExtraniamos() {
		return "Tu nous manques, Yumeiri Reyu.";
	}

	public String importadorColorFondo() {
		return "Importateur : fond";
	}

	public String importadorColorPanel() {
		return "Importateur : panneau";
	}

	public String importadorColorTexto() {
		return "Importateur : texte";
	}

	public String importadorColorTextoSuave() {
		return "Importateur : texte discret";
	}

	public String importadorColorBoton() {
		return "Importateur : bouton";
	}

	public String importadorColorBotonTexto() {
		return "Importateur : texte du bouton";
	}

	public String importadorColorBorde() {
		return "Importateur : bordure";
	}

	public String importadorConflictoTitulo() {
		return "Conflit lors de l'importation";
	}

	public String importadorConflictoMensaje() {
		return "Un fichier existe déjà à la destination.";
	}

	public String importadorRuta() {
		return "Chemin";
	}

	public String importadorArchivoExistente() {
		return "Fichier existant";
	}

	public String importadorArchivoNuevo() {
		return "Fichier importé";
	}

	public String importadorTamano() {
		return "Taille";
	}

	public String importadorFecha() {
		return "Dernière modification";
	}

	public String importadorInfoMod() {
		return "Informations sur le mod";
	}

	public String importadorModImportadoMasNuevo() {
		return "Le mod importé semble plus récent.";
	}

	public String importadorModImportadoMasViejo() {
		return "Le mod importé semble plus ancien.";
	}

	public String importadorBotonReemplazar() {
		return "Remplacer";
	}

	public String importadorBotonSaltar() {
		return "Ignorer";
	}

	public String importadorBotonRenombrar() {
		return "Renommer le nouveau";
	}

	public String importadorModpackTitulo() {
		return "Importer un modpack";
	}

	public String importadorModpackBotonSidebar() {
		return "Importer";
	}

	public String importadorModpackDescripcion() {
		return "Importez un modpack dans l'instance actuelle. Vous pouvez glisser-déposer un fichier .zip, .mrpack ou autre format pris en charge, ou le sélectionner manuellement.";
	}

	public String importadorModpackFormato() {
		return "Format";
	}

	public String importadorModpackArrastraArchivo() {
		return "Glissez votre modpack ici ou sélectionnez un fichier";
	}

	public String importadorModpackBotonSeleccionar() {
		return "Sélectionner un fichier";
	}

	public String importadorModpackBotonImportar() {
		return "Importer";
	}

	public String importadorModpackSeleccionarArchivo() {
		return "Sélectionner un modpack";
	}

	public String importadorModpackEstadoListo() {
		return "Prêt";
	}

	public String importadorModpackEstadoImportando() {
		return "Importation...";
	}

	public String importadorModpackEstadoError() {
		return "Erreur lors de l'importation.";
	}

	public String importadorModpackSinArchivo() {
		return "Veuillez d'abord sélectionner un fichier de modpack.";
	}

	public String importadorModpackFormatoNoSoportado() {
		return "Ce format ne prend pas en charge l'importation.";
	}

	public String importadorModpackResultado(int copiados, int reemplazados, int saltados, int renombrados,
			int errores) {
		return "Importation terminée.\nCopiés : " + copiados + "\nRemplacés : " + reemplazados + "\nIgnorés : "
				+ saltados + "\nRenommés : " + renombrados + "\nErreurs : " + errores;
	}

	public String importadorModpackColorFondo() {
		return "Importateur de modpacks : fond";
	}

	public String importadorModpackColorPanel() {
		return "Importateur de modpacks : panneau";
	}

	public String importadorModpackColorTexto() {
		return "Importateur de modpacks : texte";
	}

	public String importadorModpackColorTextoSuave() {
		return "Importateur de modpacks : texte discret";
	}

	public String importadorModpackColorBoton() {
		return "Importateur de modpacks : bouton";
	}

	public String importadorModpackColorBotonTexto() {
		return "Importateur de modpacks : texte du bouton";
	}

	public String importadorModpackColorDrop() {
		return "Importateur de modpacks : zone de glisser-déposer";
	}

	public String importadorModpackColorBorde() {
		return "Importateur de modpacks : bordure";
	}

	public String jgitTituloIzzy() {
		return "Centre Git d'Izzy";
	}

	public String jgitRetratoIzzy() {
		return "Izzy";
	}

	public String jgitNoHayRetratoIzzy() {
		return "Pas de portrait d'Izzy";
	}

	public String jgitSeccionInstalacion() {
		return "Installation de JGit";
	}

	public String jgitAbrirCarpetaInstalacion() {
		return "Ouvrir le dossier d'installation";
	}

	public String jgitAbrirPaginaDescarga() {
		return "Ouvrir la page de JGit";
	}

	public String jgitSeccionRepositorio() {
		return "Dépôt local";
	}

	public String jgitCrearRepositorioLocal() {
		return "Créer un dépôt Git ici";
	}

	public String jgitCommitManual() {
		return "Commit manuel";
	}

	public String jgitSeccionRemote() {
		return "Remote";
	}

	public String jgitForgeManual() {
		return "Manuel";
	}

	public String jgitForgePersonalizado() {
		return "Forge personnalisé";
	}

	public String jgitEstablecerRemoteManual() {
		return "Définir le remote manuellement";
	}

	public String jgitCrearRemoteConAPI() {
		return "Créer un remote via l'API";
	}

	public String jgitPushManual() {
		return "Push manuel";
	}

	public String jgitSeccionAutomaticos() {
		return "Automatisation";
	}

	public String jgitAutoCommitDespuesBackup() {
		return "Commit automatique après sauvegarde";
	}

	public String jgitAutoPushDespuesCommit() {
		return "Push automatique après commit";
	}

	public String jgitSeccionHerramientas() {
		return "Outils";
	}

	public String jgitAbrirGuiSwing() {
		return "Ouvrir la visionneuse Swing de JGit";
	}

	public String jgitEstado() {
		return "État";
	}

	public String jgitClasspath() {
		return "JGit dans le classpath";
	}

	public String jgitTodosLosArtefactos() {
		return "Tous les artefacts JGit";
	}

	public String jgitRepositorio() {
		return "Dépôt";
	}

	public String jgitRemote() {
		return "Remote";
	}

	public String jgitCarpetaActual() {
		return "Dossier actuel";
	}

	public String jgitNoSePudoCrearRepo() {
		return "Impossible de créer le dépôt.";
	}

	public String jgitEscribaRemote() {
		return "Saisissez l'URL du remote :";
	}

	public String jgitNoSePudoGuardarRemote() {
		return "Impossible d'enregistrer le remote.";
	}

	public String jgitApiForgeAunNoImplementada() {
		return "L'API de forge n'est pas encore implémentée.";
	}

	public String jgitNoHayCambiosOError() {
		return "Aucune modification à committer ou une erreur est survenue.";
	}

	public String jgitNoSePudoPush() {
		return "Impossible de pousser (push).";
	}

	public String jgitTituloVentanaSwing() {
		return "Visionneuse Git";
	}

	public String jgitNoHayRepositorio() {
		return "Aucun dépôt Git dans ce dossier.";
	}

	public String jgitArchivosModificados() {
		return "Fichiers modifiés";
	}

	public String jgitArchivosNuevos() {
		return "Nouveaux fichiers";
	}

	public String jgitUltimosCommits() {
		return "Derniers commits";
	}

	public String jgitError() {
		return "Erreur JGit";
	}

	public String si() {
		return "Oui";
	}

	public String no() {
		return "Non";
	}

	public String jgitDescargarDependenciasFaltantes() {
		return "Télécharger les dépendances manquantes";
	}

	public String jgitNoFaltanDependencias() {
		return "Aucune dépendance JGit manquante.";
	}

	public String jgitConfirmarDescargaDependencias(int cantidad) {
		return "Il manque " + cantidad + " dépendances JGit. Voulez-vous les télécharger depuis Maven Central ?";
	}

	public String jgitDependenciasDescargadas(int cantidad) {
		return "Dépendances téléchargées : " + cantidad;
	}

	public String jgitErroresDescarga() {
		return "Erreurs de téléchargement";
	}

	public String jgitReiniciarParaCargarClasspath() {
		return "Redémarrez CrashDetector pour que les nouveaux JARs soient ajoutés au classpath.";
	}

	public String jgitArtefactosFaltantes() {
		return "Artefacts manquants";
	}

	public String jgitArtefactosFaltantesClasspath() {
		return "Artefacts manquants dans le classpath";
	}

	public String jgitArtefactosFaltantesCarpeta() {
		return "Artefacts manquants dans le dossier d'installation";
	}

	public String jgitDependenciasEnCarpeta() {
		return "Dépendances installées dans le dossier";
	}

	public String jgitForgeNoSeleccionada() {
		return "Aucune forge sélectionnée.";
	}

	public String jgitForgeNoRegistrada(String id) {
		return "La forge n'est pas enregistrée : " + id;
	}

	public String jgitEscribaUrlForge() {
		return "URL de la forge :";
	}

	public String jgitEscribaNombreRepositorio() {
		return "Nom du dépôt :";
	}

	public String jgitEscribaDescripcionRepositorio() {
		return "Description du dépôt :";
	}

	public String jgitEscribaNamespaceOpcional() {
		return "Namespace facultatif :";
	}

	public String jgitEscribaTokenForge() {
		return "Jeton API de la forge :";
	}

	public String jgitErrorCrearRemote() {
		return "Erreur lors de la création du remote";
	}

	@Override
	public String mensajeControlifyRemoveReloadingScreen() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilité détectée entre Controlify et Remove Reloading Screen.</b>"
				+ "<p>Le journal contient les lignes <b>Attempted to fetch default config before DefaultConfigManager was ready!</b> "
				+ "et <b>$rrls$init</b>, ce qui indique généralement un conflit entre <b>Controlify</b> et "
				+ "<b>Remove Reloading Screen</b>.</p>"
				+ "<p><b>Cause probable :</b> Remove Reloading Screen modifie des parties de l'écran de chargement ou du processus de chargement, "
				+ "tandis que Controlify tente d'initialiser sa configuration avant que le système ne soit entièrement prêt.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>" + "<li>Supprimez <b>Remove Reloading Screen</b>.</li>"
				+ "<li>Ou mettez à jour <b>Controlify</b> et <b>Remove Reloading Screen</b> si de nouvelles versions sont disponibles.</li>"
				+ "<li>Si le problème persiste, conservez <b>Controlify</b> et supprimez tout mod qui modifie l'écran de chargement.</li>"
				+ "</ul>"
				+ "<p>Les mods qui modifient l'écran de chargement causent souvent des incompatibilités avec d'autres mods, "
				+ "et offrent généralement peu d'avantages pratiques par rapport aux problèmes qu'ils peuvent provoquer.</p>";
	}

	@Override
	public String nombreControlifyRemoveReloadingScreen() {
		return "Incompatibilité : Controlify vs Remove Reloading Screen";
	}

	@Override
	public String mensajeBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Problème possible avec Biomes O' Plenty et les liquides personnalisés.</b>"
				+ "<p>Le journal contient l'erreur <b>class org.joml.Vector4f cannot be cast to class "
				+ "net.minecraft.client.renderer.fog.FogData</b> ainsi qu'une référence à <b>Biomes O' Plenty</b>.</p>"
				+ "<p>Cela est probablement lié à <b>Biomes O' Plenty</b>, en particulier aux biomes, au brouillard "
				+ "ou aux liquides personnalisés. Cependant, il n'est pas certain que Biomes O' Plenty soit la seule cause.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Essayez de modifier les données du joueur pour le déplacer à un autre endroit du monde.</li>"
				+ "<li>Essayez de charger le monde sans <b>Biomes O' Plenty</b>.</li>"
				+ "<li>Si le monde se charge après avoir déplacé le joueur, le problème survient probablement dans une zone spécifique, "
				+ "un biome spécifique ou près d'un liquide personnalisé.</li>"
				+ "<li>Vous pouvez également essayer de mettre à jour <b>Biomes O' Plenty</b> et les mods liés au rendu, au brouillard, "
				+ "aux shaders ou aux dimensions.</li>" + "</ul>"
				+ "<p>Si retirer Biomes O' Plenty permet de lancer le jeu, vérifiez si le joueur se trouvait dans ou près d'un biome "
				+ "ou d'un fluide ajouté par ce mod.</p>";
	}

	@Override
	public String nombreBiomesOPlentyFogDataLiquidosPersonalizados() {
		return "Problème possible : Biomes O' Plenty et FogData";
	}

	@Override
	public String mensajeKotlinReflectionInternalErrorVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur interne de réflexion Kotlin détectée.</b>"
				+ "<p>Le journal contient <b>kotlin.reflect.jvm.internal.KotlinReflectionInternalError</b> avec un message similaire à "
				+ "<b>Property 'none' not resolved</b>.</p>"
				+ "<p>Ce type d'erreur est courant avec certaines versions de <b>Fabric Language Kotlin</b> / <b>Kotlin</b>. "
				+ "Dans ce cas, une classe de <b>Inventory Profiles Next</b> apparaît, mais le même problème peut aussi survenir "
				+ "avec d'autres mods utilisant Kotlin.</p>" + "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Mettez à jour <b>Fabric Language Kotlin</b> vers la version <b>2.3.40</b>, si disponible pour votre version de Minecraft.</li>"
				+ "<li>Si la mise à jour ne fonctionne pas, essayez de rétrograder <b>Fabric Language Kotlin</b> vers la version <b>2.3.10</b>.</li>"
				+ "<li>Mettez également à jour <b>Inventory Profiles Next</b> si le journal mentionne des classes de ce mod.</li>"
				+ "<li>Si l'erreur apparaît avec un autre mod, vérifiez si ce mod dépend de Kotlin et essayez de changer la version de "
				+ "<b>Fabric Language Kotlin</b>.</li>" + "</ul>" + "<p>Référence technique connexe : "
				+ "<a href='https://github.com/FabricMC/fabric-language-kotlin/issues/183'>Problème #183 de Fabric Language Kotlin</a>.</p>";
	}

	@Override
	public String nombreKotlinReflectionInternalErrorVersion() {
		return "Erreur Kotlin : réflexion interne";
	}

	public String tituloEscanerMCreator() {
		return "Scanner MCreator";
	}

	public String escanerMCreatorEscaneandoMods() {
		return "Analyse des mods en cours...";
	}

	public String escanerMCreatorPorFavorEspera() {
		return "Veuillez patienter.";
	}

	public String escanerMCreatorResultadosAnalisis() {
		return "Résultats de l'analyse MCreator :";
	}

	public String escanerMCreatorNoSeEncontraronMods() {
		return "Aucun mod MCreator trouvé.";
	}

	public String escanerMCreatorEscaneoCompletado() {
		return "Analyse terminée.";
	}

	public String escanerMCreatorErrorDuranteEscaneo() {
		return "Erreur lors de l'analyse :";
	}

	public String escanerMCreatorCargando() {
		return "Chargement...";
	}

	public String escanerMCreatorCompletado() {
		return "Terminé";
	}

	public String escanerMCreatorError() {
		return "Erreur";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String textoNormal() {
		return "Texte normal";
	}

	@Override
	public String noSeEncontroConsolaParaArchivo() {
		return "Console non trouvée pour le fichier : ";
	}

	@Override
	public String lineaSeleccionadaEnLectador() {
		return "ligne sélectionnée dans le lecteur : ";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String mensajeMotionBlurBufferCerrado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Problème possible avec Motion Blur.</b>"
				+ "<p>Le journal contient une référence à <b>net.natural.motionblur.shader.FrameBlendingManager.writeBlendParamsUBO</b> "
				+ "ainsi que l'erreur <b>java.lang.IllegalStateException: Buffer already closed</b>.</p>"
				+ "<p>Ces lignes peuvent apparaître séparément dans le journal, mais ensemble, elles indiquent généralement que le problème est lié "
				+ "au mod <b>Motion Blur</b> ou à sa gestion des shaders/buffers graphiques.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Essayez de lancer le jeu sans <b>Motion Blur</b>.</li>"
				+ "<li>Si le jeu démarre correctement sans ce mod, gardez-le désinstallé ou cherchez une version plus récente.</li>"
				+ "<li>Vous pouvez également essayer sans shaders ni autres mods de rendu si le problème persiste.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreMotionBlurBufferCerrado() {
		return "Problème possible : Motion Blur";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String mensajeGeneratorAcceleratorOwoVersion() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Conflit possible avec Generator Accelerator.</b>"
				+ "<p>Le journal contient une différence entre les signatures <b>Found</b> et <b>Available</b>, ainsi que des classes de "
				+ "<b>Generator Accelerator</b>, par exemple <b>dev/sixik/generator_accelerator/common/features/FastTarget</b>.</p>"
				+ "<p>Cette erreur est probablement causée par <b>Generator Accelerator</b>. Elle peut également être liée "
				+ "à une incompatibilité entre ce mod et certaines versions de <b>owo-lib</b>.</p>"
				+ "<p><b>Options recommandées :</b></p>" + "<ul>"
				+ "<li>Essayez de lancer le jeu sans <b>Generator Accelerator</b>.</li>"
				+ "<li>Si le jeu démarre correctement, gardez ce mod désinstallé ou cherchez une version différente.</li>"
				+ "<li>Essayez de mettre à jour ou de changer la version de <b>owo-lib</b>, surtout si d'autres mods dépendent aussi de owo.</li>"
				+ "<li>Vérifiez que <b>Generator Accelerator</b>, <b>owo-lib</b>, le chargeur et la version de Minecraft sont compatibles entre eux.</li>"
				+ "</ul>"
				+ "<p>La cause la plus probable est que Generator Accelerator essaie d'appliquer une modification avec une signature "
				+ "qui ne correspond pas à la version actuelle d'une classe ou d'une dépendance.</p>";
	}

	@Override
	public String nombreGeneratorAcceleratorOwoVersion() {
		return "Conflit possible : Generator Accelerator et owo-lib";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String mensajeFabricRenderingApiFaltaIndium() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Un renderer compatible avec Fabric Rendering API est manquant.</b>"
				+ "<p>Le journal contient une erreur où <b>RendererAccess.getRenderer()</b> renvoie <b>null</b>, "
				+ "provoquant un échec lors de la tentative d'utilisation de <b>Renderer.materialFinder()</b>.</p>"
				+ "<p>Ce problème survient généralement lorsque <b>Fabric Rendering API</b> n'est pas disponible correctement. "
				+ "Une cause courante est l'utilisation de <b>Sodium</b>, en particulier les anciennes versions qui remplacent ou désactivent des parties "
				+ "du système de rendu attendu par d'autres mods.</p>" + "<p><b>Solution recommandée :</b></p>" + "<ul>"
				+ "<li>Installez le mod <b>Indium</b>.</li>"
				+ "<li>Assurez-vous que <b>Indium</b> est compatible avec votre version de <b>Sodium</b>, Fabric Loader et Minecraft.</li>"
				+ "<li>Si Indium est déjà installé, mettez à jour <b>Sodium</b>, <b>Indium</b> et <b>Fabric API</b>.</li>"
				+ "<li>Si le problème persiste, essayez temporairement sans Sodium pour confirmer que l'erreur est liée au renderer.</li>"
				+ "</ul>"
				+ "<p>Indium restaure normalement la compatibilité avec Fabric Rendering API pour les mods qui dépendent de ce système "
				+ "lorsque Sodium est installé.</p>";
	}

	@Override
	public String nombreFabricRenderingApiFaltaIndium() {
		return "Indium manquant / Fabric Rendering API";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String mensajeEntradaDuplicadaIdModerno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Entrée dupliquée détectée dans un registre Minecraft.</b>"
				+ "<p>Le journal contient une erreur similaire à <b>Duplicate entry on id</b>, par exemple "
				+ "<b>current=maroon, previous=mint</b>.</p>"
				+ "<p>Dans les versions modernes de Minecraft, ce type d'erreur se produit généralement lorsque deux mods tentent d'enregistrer "
				+ "des entrées distinctes en utilisant le même ID interne.</p>" + "<p><b>Options recommandées :</b></p>"
				+ "<ul>" + "<li>Supprimez l'un des mods qui enregistre l'entrée dupliquée.</li>"
				+ "<li>Si vous reconnaissez les noms mentionnés dans l'erreur, vérifiez quel mod ajoute ces entrées et essayez sans ce mod.</li>"
				+ "<li>Si vous ne reconnaissez pas les noms, utilisez l'utilitaire <b>grepr</b> dans la barre latérale.</li>"
				+ "<li>Dans <b>grepr</b>, activez la recherche dans les fichiers compressés <b>.jar</b>, <b>.zip</b> et <b>.fpm</b>.</li>"
				+ "<li>Activez également la recherche dans les <b>fichiers binaires</b>, car certains noms ou ID peuvent se trouver dans des classes compilées.</li>"
				+ "</ul>"
				+ "<li>Recherchez les valeurs mentionnées dans l'erreur, comme <b>maroon</b> ou <b>mint</b>, pour trouver quel mod les contient.</li>";
	}

	@Override
	public String nombreEntradaDuplicadaIdModerno() {
		return "Entrée dupliquée dans l'ID du mod";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String nombreOpenGLMemoriaInsuficiente() {
		return "OpenGL – mémoire vidéo insuffisante";
	}

	@Override
	public String mensajeOpenGLMemoriaInsuficiente() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft a généré une erreur OpenGL due à un manque de mémoire graphique.</b>"
				+ "<p>Le jeu a lancé :</p>" + "<code>GL_OUT_OF_MEMORY</code>"
				+ "<p>Cela signifie généralement que la carte graphique ou le système n'a pas pu allouer suffisamment de mémoire pour les textures, les shaders, les modèles, les tampons ou les effets visuels.</p>"
				+ "<p><b>Causes courantes :</b></p>" + "<ul>" + "<li>Shaders trop lourds.</li>"
				+ "<li>Packs de ressources haute résolution.</li>" + "<li>Trop de mods visuels ou de rendu.</li>"
				+ "<li>Distance d'affichage trop élevée.</li>" + "<li>Peu de VRAM disponible.</li>"
				+ "<li>Pilotes graphiques obsolètes ou instables.</li>" + "</ul>"
				+ "<p><b>Solution recommandée :</b></p>" + "<ul>" + "<li>Désactiver temporairement les shaders.</li>"
				+ "<li>Utiliser des packs de ressources de moindre résolution.</li>"
				+ "<li>Réduire la distance d'affichage et de simulation.</li>"
				+ "<li>Réduire la qualité graphique, les ombres, les particules et les mipmaps.</li>"
				+ "<li>Mettre à jour les pilotes de la carte graphique.</li>"
				+ "<li>Fermer les autres programmes utilisant le GPU ou beaucoup de mémoire.</li>" + "</ul>"
				+ "<p>Si l'erreur a commencé après l'installation d'un shader, d'un pack de textures ou d'un mod visuel, c'est probablement la cause.</p>";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String nombreErrorVerificacionBytecode() {
		return "VerifyError – bytecode ou mixin invalide";
	}

	@Override
	public String mensajeErrorVerificacionBytecode(String ubicacion, String razon, String claseSospechosa) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft a rencontré une erreur de vérification de bytecode.</b>"
				+ "<p>Ce problème survient généralement lorsqu'une manipulation de bytecode, un transformateur ou un mixin échoue.</p>"
				+ "<p>Le jeu a lancé :</p>" + "<code>java.lang.VerifyError</code>"
				+ (ubicacion.length() > 0 ? "<p><b>Emplacement :</b></p><code>" + ubicacion + "</code>" : "")
				+ (razon.length() > 0 ? "<p><b>Raison :</b></p><code>" + razon + "</code>" : "")
				+ "<p><b>Ce qu'il faut chercher :</b></p>" + "<ul>" + "<li>Vérifier l'emplacement de l'erreur.</li>"
				+ "<li>Chercher le type mentionné dans <code>Reason</code>.</li>"
				+ "<li>Examiner la trace de la pile pour des classes de mods suspectes.</li>"
				+ "<li>Rechercher des noms de classes de mods autour de l'erreur pour obtenir des indices.</li>"
				+ "</ul>" + "<p><b>Utilisation recommandée de Grepr :</b></p>" + "<ul>"
				+ "<li>Ouvrir <b>Grepr</b> dans la barre latérale.</li>"
				+ "<li>Activer l'option pour chercher dans les fichiers <code>.jar</code>, <code>.zip</code> ou <code>.fpm</code>.</li>"
				+ "<li>Chercher le nom de base de la classe, pas nécessairement le paquet complet.</li>" + "</ul>"
				+ "<p>Exemple : si <code>paquete.Clase</code> apparaît, cherchez seulement :</p>" + "<code>"
				+ (claseSospechosa.length() > 0 ? claseSospechosa : "Clase") + "</code>"
				+ "<p>Cela peut aider à trouver quel mod contient ou modifie cette classe.</p>"
				+ "<p>Solutions courantes : mettre à jour le mod affecté, supprimer les mods incompatibles, vérifier les addons du mod principal, ou essayer sans les mods qui utilisent des mixins/transformateurs sur la même classe.</p>";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String errorMetodoFinalSobrescrito(String claseQueSobrescribe, String metodoFinal) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Erreur de compatibilité : un mod tente de surcharger une méthode final.</b>"
				+ "<p>Le journal contient une erreur <b>IncompatibleClassChangeError</b> avec le texte "
				+ "<b>overrides final method</b>.</p>" + "<p>Classe affectée : <code>" + claseQueSobrescribe
				+ "</code></p>" + "<p>Méthode final affectée : <code>" + metodoFinal + "</code></p>"
				+ "<p>Cette erreur se produit généralement lorsqu'un mod a été compilé pour une version différente de Minecraft, "
				+ "Forge, NeoForge, Fabric, Quilt, ou d'une bibliothèque de base.</p>" + "<p><b>Que tester :</b></p>"
				+ "<ul>" + "<li>Mettez à jour le mod contenant la classe indiquée.</li>"
				+ "<li>Si le problème a commencé après avoir mis à jour Minecraft ou le chargeur, essayez une version compatible du mod.</li>"
				+ "<li>Si la classe appartient à <b>Immersive Portals</b>, vérifiez que <b>Immersive Portals</b> correspond exactement à votre version de Minecraft et du chargeur.</li>"
				+ "<li>Évitez de mélanger des mods conçus pour des versions différentes du chargeur ou de Minecraft.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreErrorMetodoFinalSobrescrito() {
		return "Un mod tente de surcharger une méthode final";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String errorCrashProvocadoPorComando(String comandoDetectado) {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Minecraft a été fermé par une commande de crash.</b>"
				+ "<p>Le journal indique que la commande <code>" + comandoDetectado + "</code> a été exécutée.</p>"
				+ "<p>Cela signifie généralement que le jeu ne s'est pas fermé à cause d'une erreur normale de mods, mais parce que quelqu'un "
				+ "a utilisé une commande conçue pour provoquer un crash manuellement.</p>"
				+ "<p><b>À vérifier :</b></p>" + "<ul>"
				+ "<li>Vérifiez qui a exécuté la commande dans la console ou dans le jeu.</li>"
				+ "<li>Si c'était un test, vous pouvez ignorer ce crash.</li>"
				+ "<li>Si cela s'est produit involontairement, vérifiez les blocs de commande, les scripts, les datapacks, les mods d'administration ou les permissions des opérateurs.</li>"
				+ "</ul>";
	}

	@Override
	public String nombreCrashProvocadoPorComando() {
		return "Crash provoqué par une commande";
	}

	// French (Burkina Faso variant) (Français)
	public String impactoAlto() {
		return "Élevé";
	}

	public String impactoMedio() {
		return "Moyen";
	}

	public String impactoBajo() {
		return "Bas";
	}

	public String impactoBajoMedio() {
		return "Bas/Moyen";
	}

	public String riesgoAlto() {
		return "Élevé";
	}

	public String riesgoMedio() {
		return "Moyen";
	}

	public String riesgoBajo() {
		return "Bas";
	}

	public String riesgoMedioAlto() {
		return "Moyen/Élevé";
	}

	public String tituloCrearConfigBBE() {
		return "Créer la configuration de Better Block Entities";
	}

	public String descripcionCrearConfigBBE() {
		return "Le fichier BBEConfig.json n'existe pas.";
	}

	public String sugerenciaCrearConfigBBE() {
		return "Créer BBEConfig.json avec des optimisations pour les coffres, shulkers, panneaux, lits, cloches et bannières.";
	}

	public String tituloActivarOptimizacionMaestraBBE() {
		return "Activer l'optimisation principale de BBE";
	}

	public String descripcionActivarOptimizacionMaestraBBE() {
		return "Better Block Entities ne semble pas avoir l'optimisation principale activée.";
	}

	public String sugerenciaActivarOptimizacionMaestraBBE() {
		return "Ajouter {\"option\":\"optimize.master\",\"value\":true}";
	}

	public String tituloActivarCullingTextoCartelesBBE() {
		return "Activer le culling du texte des panneaux";
	}

	public String descripcionActivarCullingTextoCartelesBBE() {
		return "Réduit le rendu du texte des panneaux à distance.";
	}

	public String sugerenciaActivarCullingTextoCartelesBBE() {
		return "Ajouter {\"option\":\"misc.sign_text_culling\",\"value\":true}";
	}

	public String tituloAumentarSleepDelayEntityCulling() {
		return "Augmenter le sleepDelay de EntityCulling";
	}

	public String descripcionAumentarSleepDelayEntityCulling() {
		return "EntityCulling vérifiera les entités moins fréquemment.";
	}

	public String sugerenciaAumentarSleepDelayEntityCulling() {
		return "\"sleepDelay\": 153";
	}

	public String tituloSubirLimiteHitboxEntityCulling() {
		return "Augmenter le limite de hitbox";
	}

	public String descripcionSubirLimiteHitboxEntityCulling() {
		return "Permet un comportement de culling plus agressif avant de basculer vers des chemins plus lents.";
	}

	public String sugerenciaSubirLimiteHitboxEntityCulling() {
		return "\"hitboxLimit\": 90";
	}

	public String tituloDesactivarDatosF3EntityCulling() {
		return "Désactiver les données F3 de EntityCulling";
	}

	public String descripcionDesactivarDatosF3EntityCulling() {
		return "Supprime les informations de débogage supplémentaires du mod.";
	}

	public String sugerenciaDesactivarDatosF3EntityCulling() {
		return "\"disableF3\": true";
	}

	public String tituloActivarBufferingSignsImmediatelyFast() {
		return "Activer la mise en tampon expérimentale des panneaux";
	}

	public String descripcionActivarBufferingSignsImmediatelyFast() {
		return "Peut améliorer les performances lorsqu'il y a beaucoup de panneaux.";
	}

	public String sugerenciaActivarBufferingSignsImmediatelyFast() {
		return "\"experimental_sign_text_buffering\": true";
	}

	public String tituloReducirConflictosResourcePacksImmediatelyFast() {
		return "Réduire la gestion des conflits de packs de ressources";
	}

	public String descripcionReducirConflictosResourcePacksImmediatelyFast() {
		return "Peut supprimer du travail supplémentaire, mais peut aussi causer des problèmes visuels avec les packs de ressources.";
	}

	public String sugerenciaReducirConflictosResourcePacksImmediatelyFast() {
		return "\"experimental_disable_resource_pack_conflict_handling\": true";
	}

	public String tituloOcultarBotonNCR() {
		return "Masquer le bouton de No Chat Reports";
	}

	public String descripcionOcultarBotonNCR() {
		return "Changement d'interface ; n'améliore pas beaucoup les FPS, mais nettoie l'écran.";
	}

	public String sugerenciaOcultarBotonNCR() {
		return "\"showNCRButton\": false";
	}

	public String tituloActivarMixinsExperimentalesLithium() {
		return "Activer les mixins expérimentaux de Lithium";
	}

	public String descripcionActivarMixinsExperimentalesLithium() {
		return "Active des optimisations expérimentales supplémentaires.";
	}

	public String sugerenciaActivarMixinsExperimentalesLithium() {
		return "mixin.experimental=true";
	}

	public String tituloUsarDetectorThreadingPequenoFerriteCore() {
		return "Utiliser un petit détecteur de threading";
	}

	public String descripcionUsarDetectorThreadingPequenoFerriteCore() {
		return "Réduit la mémoire, mais peut être plus risqué.";
	}

	public String sugerenciaUsarDetectorThreadingPequenoFerriteCore() {
		return "useSmallThreadingDetector=true";
	}

	public String tituloModernFixRecursosDinamicos() {
		return "Activer les ressources dynamiques de ModernFix";
	}

	public String descripcionModernFixRecursosDinamicos() {
		return "Peut réduire l'utilisation de la mémoire et la charge en chargeant les ressources plus efficacement.";
	}

	public String tituloModernFixRenderizadoresDinamicosEntidades() {
		return "Activer les rendus dynamiques d'entités";
	}

	public String descripcionModernFixRenderizadoresDinamicosEntidades() {
		return "Peut améliorer les performances en gérant les rendus d'entités plus efficacement.";
	}

	public String tituloModernFixRenderizadoItemsRapido() {
		return "Activer le rendu rapide des objets";
	}

	public String descripcionModernFixRenderizadoItemsRapido() {
		return "Peut améliorer les performances lors du rendu des objets.";
	}

	public String tituloModernFixWorldgenAllocation() {
		return "Réduire les allocations lors de la génération du monde";
	}

	public String descripcionModernFixWorldgenAllocation() {
		return "Peut réduire les déchets mémoire pendant la génération du monde.";
	}

	public String tituloModernFixDeduplicacionIngredientes() {
		return "Activer la déduplication des ingrédients";
	}

	public String descripcionModernFixDeduplicacionIngredientes() {
		return "Réduit les objets dupliqués liés aux recettes et ingrédients.";
	}

	public String tituloSodiumRenderCielo() {
		return "Activer l'optimisation/rendu du ciel dans Sodium";
	}

	public String descripcionSodiumRenderCielo() {
		return "Peut ajuster le comportement de rendu du ciel.";
	}

	public String tituloActivarLightmapCaching() {
		return "Activer le cache du lightmap";
	}

	public String descripcionActivarLightmapCaching() {
		return "Évite de recalculer l'éclairage inutilement.";
	}

	public String sugerenciaActivarLightmapCaching() {
		return "enable_lightmap_caching: true";
	}

	public String tituloOcultarTextoF3BadOptimizations() {
		return "Masquer le texte F3 de BadOptimizations";
	}

	public String descripcionOcultarTextoF3BadOptimizations() {
		return "Réduit le bruit de débogage sur l'écran F3.";
	}

	public String sugerenciaOcultarTextoF3BadOptimizations() {
		return "show_f3_text: false";
	}

	public String tituloDesactivarLogConfigBadOptimizations() {
		return "Désactiver le journal de configuration";
	}

	public String descripcionDesactivarLogConfigBadOptimizations() {
		return "Évite d'imprimer toute la configuration au démarrage.";
	}

	public String sugerenciaDesactivarLogConfigBadOptimizations() {
		return "log_config: false";
	}

	public String tituloActivarSerializadorGCFreeC2ME() {
		return "Activer le sérialiseur sans GC de C2ME";
	}

	public String descripcionActivarSerializadorGCFreeC2ME() {
		return "Réduit les allocations mémoire lors du chargement ou de la sauvegarde des chunks.";
	}

	public String sugerenciaActivarSerializadorGCFreeC2ME() {
		return "[ioSystem] gcFreeChunkSerializer = true";
	}

	public String tituloDesactivarSyncPlayerTicketsC2ME() {
		return "Désactiver syncPlayerTickets";
	}

	public String descripcionDesactivarSyncPlayerTicketsC2ME() {
		return "Peut améliorer les performances des chunks, mais peut affecter les contraptions techniques.";
	}

	public String sugerenciaDesactivarSyncPlayerTicketsC2ME() {
		return "[chunkSystem] syncPlayerTickets = false";
	}

	public String tituloUsarCullingHojasDepthMoreCulling() {
		return "Utiliser le culling des feuilles DEPTH";
	}

	public String descripcionUsarCullingHojasDepthMoreCulling() {
		return "Utilise un mode de culling des feuilles plus agressif que le mode normal.";
	}

	public String sugerenciaUsarCullingHojasDepthMoreCulling() {
		return "leavesCullingMode = \"DEPTH\"";
	}

	public String tituloActivarEndGatewayCullingMoreCulling() {
		return "Activer le culling des End Gateway";
	}

	public String descripcionActivarEndGatewayCullingMoreCulling() {
		return "Évite le rendu inutile des End Gateways.";
	}

	public String sugerenciaActivarEndGatewayCullingMoreCulling() {
		return "endGatewayCulling = true";
	}

	public String tituloActivarActivationRangeServerCore() {
		return "Activer l'activation range";
	}

	public String descripcionActivarActivationRangeServerCore() {
		return "Réduit les ticks des entités éloignées du joueur.";
	}

	public String sugerenciaActivarActivationRangeServerCore() {
		return "activation-range: enabled: true";
	}

	public String tituloActivarRangoVerticalServerCore() {
		return "Activer la plage verticale";
	}

	public String descripcionActivarRangoVerticalServerCore() {
		return "Réduit les ticks des entités situées très haut ou très bas par rapport au joueur.";
	}

	public String sugerenciaActivarRangoVerticalServerCore() {
		return "use-vertical-range: true";
	}

	// French (Burkina Faso variant) (Français)
	public String impactoNegativoAlto() {
		return "Impact négatif élevé";
	}

	public String advertenciaModsCulling() {
		return "Les mods de culling peuvent causer des incompatibilités avec certains mods, des plantages, des erreurs où le jeu cesse de tiquer correctement, et peuvent aussi empêcher les fermes automatiques ou les usines de fonctionner.";
	}

	public String tituloModBadOptimizations() {
		return "Ajouter BadOptimizations";
	}

	public String descripcionModBadOptimizations() {
		return "Ajoute des micro-optimisations client comme le cache du lightmap, le cache du ciel et la réduction d'appels inutiles.";
	}

	public String tituloModBBE() {
		return "Ajouter Better Block Entities";
	}

	public String descripcionModBBE() {
		return "Optimise le rendu des entités de blocs comme les coffres, shulkers, lits, cloches, bannières et panneaux.";
	}

	public String tituloModC2ME() {
		return "Ajouter Concurrent Chunk Management Engine";
	}

	public String descripcionModC2ME() {
		return "Améliore le chargement, la génération et la gestion des chunks via le traitement concurrent. Peut être très puissant, mais peut aussi causer des incompatibilités dans les gros modpacks.";
	}

	public String tituloModEntityCulling() {
		return "Ajouter EntityCulling";
	}

	public String descripcionModEntityCulling() {
		return "Empêche le rendu des entités non visibles. " + advertenciaModsCulling();
	}

	public String tituloModFerriteCore() {
		return "Ajouter FerriteCore";
	}

	public String descripcionModFerriteCore() {
		return "Réduit l'utilisation de la mémoire grâce à la déduplication et à des structures internes plus efficaces.";
	}

	public String tituloModImmediatelyFast() {
		return "Ajouter ImmediatelyFast";
	}

	public String descripcionModImmediatelyFast() {
		return "Optimise plusieurs parties du rendu immédiat, du texte, des tampons, des cartes et de l'interface.";
	}

	public String tituloModLithium() {
		return "Ajouter Lithium";
	}

	public String descripcionModLithium() {
		return "Optimise la logique du jeu, les entités, les blocs, la physique et d'autres systèmes sans trop modifier le comportement vanilla.";
	}

	public String tituloModModernFix() {
		return "Ajouter ModernFix";
	}

	public String descripcionModModernFix() {
		return "Ajoute de nombreuses optimisations de mémoire, chargement, ressources et performances générales. Ses outils liés aux atlas peuvent entrer en conflit avec les mods qui réduisent la taille des atlas.";
	}

	public String tituloModMoreCulling() {
		return "Ajouter More Culling";
	}

	public String descripcionModMoreCulling() {
		return "Ajoute du culling pour les blocs, les feuilles, les cadres d'objets, les peintures, la pluie, les balises et d'autres éléments. "
				+ advertenciaModsCulling();
	}

	public String tituloModScalableLux() {
		return "Ajouter ScalableLux";
	}

	public String descripcionModScalableLux() {
		return "Optimise les calculs liés à l'éclairage et peut améliorer les performances dans les mondes avec de nombreux changements de lumière.";
	}

	public String tituloModServerCore() {
		return "Ajouter ServerCore";
	}

	public String descripcionModServerCore() {
		return "Ajoute des optimisations côté serveur, activation range, contrôles de mobcaps, réduction de ticks et améliorations de chargement.";
	}

	public String tituloModSodium() {
		return "Ajouter Sodium";
	}

	public String descripcionModSodium() {
		return "Mod principal d'optimisation du rendu. C'est généralement l'une des améliorations les plus importantes pour les FPS.";
	}

	public String tituloModVMP() {
		return "Ajouter Very Many Players";
	}

	public String descripcionModVMP() {
		return "Optimise les systèmes du serveur pour gérer beaucoup de joueurs. L'ID de mod attendu est vmp.";
	}

	public String tituloModMCMT() {
		return "Ajouter MCMT";
	}

	public String descripcionModMCMT() {
		return "Tente de multithreader des parties du serveur Minecraft. Peut améliorer les performances dans certains cas, mais présente un risque élevé d'incompatibilités, d'erreurs de ticking et de comportements étranges.";
	}

	public String tituloLiabilityUranium() {
		return "Retirer Uranium";
	}

	public String descripcionLiabilityUranium() {
		return "Uranium est un mod conçu pour laguer intentionnellement le jeu. Il ne doit pas être installé si vous souhaitez de bonnes performances.";
	}

	// French (Burkina Faso variant) (Français)
	public String tituloAmbientalSinXmx() {
		return "Configurer la mémoire maximale Java";
	}

	public String descripcionAmbientalSinXmx(int mods, String minimo, String maximoSeguro) {
		return "-Xmx n'a pas été détecté dans les arguments fournis. Pour " + mods + " mods, le minimum suggéré est "
				+ minimo + ", sans dépasser environ " + maximoSeguro + ".";
	}

	public String sugerenciaAmbientalSinXmx(String minimo) {
		return "Ajouter -Xmx" + minimo.replace(" ", "");
	}

	public String tituloAmbientalDemasiadaMemoria() {
		return "Réduire la mémoire assignée";
	}

	public String descripcionAmbientalDemasiadaMemoria(String xmx, String total, String maximoSeguro) {
		return "L'instance a assigné " + xmx + " sur " + total
				+ ". Il n'est pas recommandé d'assigner plus de 80 % de la RAM disponible.";
	}

	public String sugerenciaAmbientalDemasiadaMemoria(String maximoSeguro) {
		return "Réduire -Xmx à " + maximoSeguro + " ou moins.";
	}

	public String tituloAmbientalMemoriaInsuficiente() {
		return "Augmenter la mémoire assignée";
	}

	public String descripcionAmbientalMemoriaInsuficiente(int mods, String xmx, String minimo) {
		return "L'instance a " + xmx + " assignés. Pour " + mods + " mods, le minimum suggéré est " + minimo + ".";
	}

	public String sugerenciaAmbientalMemoriaInsuficiente(String minimo) {
		return "Augmenter -Xmx à au moins " + minimo + ".";
	}

	public String tituloAmbientalJava8GC() {
		return "Utiliser G1GC ou Shenandoah sur Java 8";
	}

	public String descripcionAmbientalJava8GC() {
		return "Sur Java 8, il est recommandé d'utiliser G1GC ou Shenandoah pour réduire les pauses et améliorer la stabilité.";
	}

	public String sugerenciaAmbientalJava8GC() {
		return "Ajouter -XX:+UseG1GC ou -XX:+UseShenandoahGC.";
	}

	public String tituloAmbientalZGC() {
		return "Utiliser ZGC";
	}

	public String descripcionAmbientalZGC(String ramTotal) {
		return "L'équipement a plus de 12 Go de RAM (" + ramTotal
				+ "). Si la distribution Java le supporte, ZGC peut réduire les pauses du ramasse-miettes.";
	}

	public String sugerenciaAmbientalZGC() {
		return "Sur Java 17 ou supérieur, essayez -XX:+UseZGC.";
	}

	public String tituloAmbientalAikar() {
		return "Ajouter les flags d'Aikar";
	}

	public String descripcionAmbientalAikar() {
		return "Sur Java 17 ou antérieur, les flags d'Aikar améliorent généralement le comportement de G1GC pour Minecraft.";
	}

	public String sugerenciaAmbientalAikar() {
		return "Utiliser les flags d'Aikar, y compris -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200.";
	}

	public String tituloAmbientalRedHatJDK() {
		return "Utiliser Red Hat JDK";
	}

	public String descripcionAmbientalRedHatJDK(int javaMayor, String os) {
		return "Pour Java " + javaMayor + " sur " + os
				+ ", Red Hat JDK est recommandé pour sa stabilité et sa compatibilité.";
	}

	public String sugerenciaAmbientalRedHatJDK() {
		return "Installer Red Hat JDK pour Java 8 ou Java 11.";
	}

	public String tituloAmbientalAzulPrime() {
		return "Envisager Azul Prime";
	}

	public String descripcionAmbientalAzulPrime() {
		return "Sur Linux avec Java 16 ou supérieur et plus de 16 Go de RAM, Azul Prime peut être un bon choix de performance.";
	}

	public String sugerenciaAmbientalAzulPrime() {
		return "Essayez Azul Prime si l'équipement a plus de 16 Go de RAM.";
	}

	public String tituloAmbientalGraalVM() {
		return "Envisager GraalVM";
	}

	public String descripcionAmbientalGraalVM() {
		return "Avec Java 16 ou supérieur et plus de 16 Go de RAM, GraalVM peut être une alternative utile hors Linux.";
	}

	public String sugerenciaAmbientalGraalVM() {
		return "Essayez GraalVM si l'équipement a plus de 16 Go de RAM.";
	}

	public String tituloAmbientalDiscoBajo() {
		return "Vérifier l'espace libre sur le disque";
	}

	public String descripcionAmbientalDiscoBajo(String libre) {
		return "Le disque a peu d'espace libre : " + libre
				+ ". Minecraft peut échouer, sauvegarder lentement ou corrompre les données s'il manque d'espace.";
	}

	public String sugerenciaAmbientalDiscoBajo() {
		return "Libérez de l'espace jusqu'à avoir au moins 20 Go disponibles.";
	}

	public String tituloAmbientalWindowsRHEL9() {
		return "Envisager RHEL 9 pour les tests";
	}

	public String descripcionAmbientalWindowsRHEL9() {
		return "Sur Windows, il est recommandé d'envisager RHEL 9 car il inclut Red Hat JDK, est stable, téléchargeable gratuitement pour les individus et c'est là que la plupart des tests sont effectués.";
	}

	public String sugerenciaAmbientalWindowsRHEL9() {
		return "Essayez l'instance sur RHEL 9 si vous cherchez une stabilité de test maximale.";
	}

	public String tituloAmbientalRaptorLake() {
		return "Avertissement Intel Raptor Lake";
	}

	public String descripcionAmbientalRaptorLake() {
		return "Le problème de Raptor Lake marqué par la vérification existante a été détecté. Cela peut causer de l'instabilité, des plantages et des erreurs qui semblent provenir du modpack.";
	}

	public String sugerenciaAmbientalRaptorLake() {
		return "Mettez à jour le BIOS/microcode et vérifiez l'avertissement de Raptor Lake avant de blâmer le modpack.";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String tituloAmbientalNeoForge1201Antiguo() {
		return "Ancienne version de NeoForge 1.20.1 détectée";
	}

	@Override
	public String descripcionAmbientalNeoForge1201Antiguo() {
		return "FancyModLoader 47 ou un chemin compatible avec NeoForge 1.20.1 a été détecté. "
				+ "NeoForge 1.20.1 était une bifurcation de MinecraftForge 1.20.1 et est généralement compatible au niveau binaire, "
				+ "mais cette ligne a été abandonnée plus tôt et peut manquer de plusieurs optimisations disponibles dans Forge.";
	}

	@Override
	public String sugerenciaAmbientalNeoForge1201Antiguo() {
		return "Pour la version 1.20.1, si le modpack le permet, envisagez d'utiliser MinecraftForge 1.20.1 au lieu de NeoForge 1.20.1.";
	}

	@Override
	public String tituloAmbientalGPU() {
		return "Problème de GPU détecté";
	}

	@Override
	public String descripcionAmbientalGPU() {
		return "Une autre vérification a déjà détecté un problème possible de GPU, OpenGL ou de sélection de carte graphique.";
	}

	@Override
	public String sugerenciaAmbientalGPU() {
		return "Vérifiez que Minecraft utilise le bon GPU, mettez à jour les pilotes et évitez les configurations hybrides instables.";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String gpuFixTitulo() {
		return "Configuration du GPU";
	}

	@Override
	public String gpuFixBotonSidebar() {
		return "GPU";
	}

	@Override
	public String gpuFixBotonAplicar() {
		return "Appliquer la configuration";
	}

	@Override
	public String gpuFixBotonFuenteTLauncher() {
		return "Ouvrir le guide TLauncher";
	}

	@Override
	public String gpuFixBotonVirusTotal() {
		return "Ouvrir l'analyse VirusTotal";
	}

	@Override
	public String gpuFixBotonOptimusLinux() {
		return "Ouvrir le guide NVIDIA Optimus";
	}

	@Override
	public String gpuFixTextoWindows() {
		return "CrashDetector a détecté que Minecraft pourrait ne pas utiliser le GPU haute performance.\n\n"
				+ "Sous Windows, on peut définir des clés de registre dans "
				+ "HKEY_CURRENT_USER\\\\Software\\\\Microsoft\\\\DirectX\\\\UserGpuPreferences "
				+ "pour forcer javaw.exe à utiliser le GPU dédié.\n\n"
				+ "GpuPreference=0 = décision automatique de Windows.\n"
				+ "GpuPreference=1 = économie d'énergie / GPU intégré.\n"
				+ "GpuPreference=2 = GPU haute performance.\n\n"
				+ "Une partie de ces informations a été obtenue grâce à la recherche publiée par TLauncher et à l'analyse "
				+ "du comportement disponible sur VirusTotal.";
	}

	@Override
	public String gpuFixTextoLinux() {
		return "CrashDetector a détecté un problème possible lié à NVIDIA Optimus ou PRIME.\n\n"
				+ "Selon la distribution Linux utilisée, il peut être nécessaire de configurer NVIDIA Optimus, "
				+ "nvidia-prime, switcheroo-control ou d'autres systèmes hybrides.\n\n"
				+ "Sur Fedora/RHEL et dérivés, il est généralement recommandé de suivre la documentation de RPMFusion.\n\n"
				+ "Le bouton ci-dessous ouvrira la documentation officielle recommandée.";
	}

	@Override
	public String gpuFixTextoMac() {
		return "CrashDetector a détecté un problème possible de sélection de GPU.\n\n"
				+ "Sur certains systèmes macOS avec GPU hybride, il est possible de forcer l'utilisation du GPU dédié "
				+ "via des configurations système avancées.\n\n"
				+ "Le bouton appliquer tentera d'exécuter une commande pour prioriser le GPU haute performance.";
	}

	@Override
	public String gpuFixTextoOtroSistema() {
		return "CrashDetector a détecté un problème possible lié au GPU, "
				+ "mais il n'existe pas d'implémentation spécifique pour ce système d'exploitation.";
	}

	@Override
	public String gpuFixLinuxManual() {
		return "Sous Linux, la configuration doit généralement être effectuée manuellement selon la distribution, "
				+ "le pilote NVIDIA et le système Optimus/PRIME utilisé.";
	}

	@Override
	public String gpuFixSistemaNoSoportado() {
		return "Système d'exploitation non pris en charge pour la configuration automatique du GPU.";
	}

	@Override
	public String gpuFixJavaNoDetectado() {
		return "Impossible de détecter le chemin actuel de javaw.exe.";
	}

	@Override
	public String gpuFixWindowsAplicado(String ruta) {
		return "La configuration du GPU a été appliquée avec succès pour :\n\n" + ruta + "\n\n"
				+ "GpuPreference=2 indique un GPU haute performance.";
	}

	@Override
	public String gpuFixErrorAplicando() {
		return "Une erreur s'est produite lors de la tentative d'application de la configuration du GPU";
	}

	@Override
	public String gpuFixMacAplicado() {
		return "La configuration du GPU haute performance a été appliquée.";
	}

	@Override
	public String gpuFixMacError() {
		return "Impossible d'appliquer la configuration du GPU sur macOS";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String rendimientoTitulo() {
		return "Gestionnaire de performance";
	}

	@Override
	public String rendimientoBotonSidebar() {
		return "Performance";
	}

	@Override
	public String rendimientoBotonAnalizar() {
		return "Analyser la performance";
	}

	@Override
	public String rendimientoBotonAbrirGPU() {
		return "Ouvrir la configuration du GPU";
	}

	@Override
	public String rendimientoDescripcion() {
		return "Ce panneau vérifie les problèmes environnementaux, les mods recommandés ou risqués, et les options de configuration qui peuvent améliorer les performances. Toutes les options ne fonctionnent pas ensemble, toutes ne conviennent pas à chaque version de Minecraft et toutes ne sont pas compatibles avec chaque chargeur de mods. C'est bien : vous n'avez pas besoin d'un score d'optimisation parfait.";
	}

	@Override
	public String rendimientoNotaCompatibilidad() {
		return "Note : ces suggestions sont des possibilités, pas un ordre d'appliquer tout. Certaines options peuvent entrer en conflit entre elles ou ne pas être adaptées à votre version, lanceur, chargeur de mods ou modpack.";
	}

	@Override
	public String rendimientoPestanaResumen() {
		return "Résumé";
	}

	@Override
	public String rendimientoPestanaAmbiental() {
		return "Problèmes environnementaux";
	}

	@Override
	public String rendimientoPestanaMods() {
		return "Mods recommandés et risques";
	}

	@Override
	public String rendimientoPestanaConfigs() {
		return "Options de configuration";
	}

	@Override
	public String rendimientoResumenTitulo() {
		return "Résumé de l'analyse";
	}

	@Override
	public String rendimientoResumenAmbiental(int cantidad) {
		return "Problèmes environnementaux trouvés : " + cantidad;
	}

	@Override
	public String rendimientoResumenMods(int cantidad) {
		return "Suggestions ou risques de mods trouvés : " + cantidad;
	}

	@Override
	public String rendimientoResumenConfigs(int cantidad) {
		return "Suggestions de configuration trouvées : " + cantidad;
	}

	@Override
	public String rendimientoResumenGPU() {
		return "Un problème de GPU a été détecté. C'est pourquoi le bouton pour ouvrir la configuration du GPU a été activé.";
	}

	@Override
	public String rendimientoSinHallazgos() {
		return "Aucune suggestion trouvée dans cette section.";
	}

	@Override
	public String rendimientoSugerencia() {
		return "Suggestion";
	}

	@Override
	public String rendimientoColorFondo() {
		return "Performance - fond";
	}

	@Override
	public String rendimientoColorPanel() {
		return "Performance - panneau";
	}

	@Override
	public String rendimientoColorTexto() {
		return "Performance - texte";
	}

	@Override
	public String rendimientoColorTextoSecundario() {
		return "Performance - texte secondaire";
	}

	@Override
	public String rendimientoColorBoton() {
		return "Performance - bouton";
	}

	@Override
	public String rendimientoColorBotonTexto() {
		return "Performance - texte du bouton";
	}

	@Override
	public String rendimientoColorSeleccion() {
		return "Performance - sélection";
	}

	// French (Burkina Faso variant) (Français)
	@Override
	public String mensajeDialogoCompartirPrimitiva() {
		return "Vous avez subi un crash. Si aucune fenêtre contextuelle avec une solution n'apparaît, veuillez envoyer les journaux au centre de support.";
	}

	@Override
	public String irAModoNormalEstiloTL() {
		return "Aller en mode normal";
	}

	@Override
	public String noHayEnlacesParaCopiar() {
		return "Il n'y a pas de liens à copier.";
	}

	@Override
	public String error_inesperado() {
		return "Erreur inattendue";
	}

	@Override
	public String centroDeSoporte() {
		return "Centre de support";
	}

	@Override
	public String noHayCentroSoporteConfigurado() {
		return "Aucun centre de support n'est configuré.";
	}

	// French (Burkina Faso variant) (Français)
	public String historialMCLogs() {
		return "Historique de MCLogs";
	}

	public String endpoint() {
		return "Point de terminaison (Endpoint)";
	}

	public String slug() {
		return "Identifiant (Slug)";
	}

	public String tokenEliminacion() {
		return "Jeton de suppression";
	}

	public String enlace() {
		return "Lien";
	}

	public String lineas() {
		return "Lignes";
	}

	public String errores() {
		return "Erreurs";
	}

	public String eliminarRegistroMCLogs() {
		return "Supprimer l'enregistrement";
	}

	public String faltanDatosParaEliminarMCLogs() {
		return "L'identifiant (slug) ou le jeton de suppression est manquant.";
	}

	public String confirmarEliminarMCLogs() {
		return "Êtes-vous sûr de vouloir supprimer cet enregistrement de MCLogs ?";
	}

	public String registroEliminadoMCLogs() {
		return "Enregistrement supprimé avec succès.";
	}

	public String confirmar() {
		return "Confirmer";
	}

	public String colorCampoTexto() {
		return "Couleur du champ de texte";
	}

	// French (Burkina Faso variant) (Français)
	public String historialCDPaste() {
		return "Historique de CDPaste";
	}

	public String enlaceRaw() {
		return "Lien brut (raw)";
	}

	public String tamano() {
		return "Taille";
	}

	public String eliminarRegistroCDPaste() {
		return "Supprimer l'enregistrement CDPaste";
	}

	public String faltanDatosParaEliminarCDPaste() {
		return "L'identifiant (slug) de l'enregistrement CDPaste est manquant.";
	}

	public String confirmarEliminarCDPaste() {
		return "Êtes-vous sûr de vouloir supprimer cet enregistrement de CDPaste ?";
	}

	public String registroEliminadoCDPaste() {
		return "Enregistrement CDPaste supprimé avec succès.";
	}

	// French (Burkina Faso variant) (Français)
	public String launcherGenerico() {
		return "Générique";
	}

	public String launcherServidorMinecraft() {
		return "Serveur Minecraft";
	}

	public String descargandoYPreparandoEnlaces() {
		return "Téléchargement et préparation des liens...";
	}

	public String seleccioneArchivoLog() {
		return "Sélectionnez un fichier journal";
	}

	public String archivoNoValido() {
		return "Le fichier n'est pas valide.";
	}

	public String archivoSeleccionado() {
		return "Fichier sélectionné :";
	}

	public String presioneGuardarParaAgregarAnalisis() {
		return "Appuyez sur Enregistrer et fermer pour l'ajouter à l'analyse.";
	}

	public String errorAlCargarArchivoArrastrado() {
		return "Erreur lors du chargement du fichier glissé";
	}

	public String errorAlAbrirArchivo() {
		return "Erreur lors de l'ouverture du fichier";
	}

	public String errorDosPuntos() {
		return "Erreur";
	}

	// French (Burkina Faso variant) (Français)
	public String eliminarRegistros() {
		return "Supprimer les enregistrements";
	}

	// French (Burkina Faso variant) (Français)
	public String editorConfigsMods() {
		return "Éditeur de configurations de mods";
	}

	public String abrirConfig() {
		return "Ouvrir la config";
	}

	public String guardarConfig() {
		return "Enregistrer la config";
	}

	public String recargarConfig() {
		return "Recharger";
	}

	public String rutaConfig() {
		return "Chemin";
	}

	public String tipoConfig() {
		return "Type";
	}

	public String claveConfig() {
		return "Clé";
	}

	public String valorConfig() {
		return "Valeur";
	}

	public String buscarConfig() {
		return "Rechercher";
	}

	public String sinArchivoSeleccionado() {
		return "Aucun fichier sélectionné";
	}

	public String archivoNoSoportado() {
		return "Le fichier n'est pris en charge par aucun moteur disponible";
	}

	public String configGuardada() {
		return "La configuration a été enregistrée avec succès";
	}

	public String errorCargandoConfig() {
		return "Erreur lors du chargement de la configuration";
	}

	public String errorGuardandoConfig() {
		return "Erreur lors de l'enregistrement de la configuration";
	}

	public String seleccionarArchivoConfig() {
		return "Sélectionner un fichier de configuration";
	}

}
