package com.asbestosstar.crashdetector.idioma;

import java.util.List;
import java.util.Set;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

public class Portuges implements Idioma {
	private final Config config = Config.obtenerInstancia();

	@Override
	public String carpeta_de_mods_no_valido() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Pasta de mods inválida</span>";
	}

	@Override
	public String no_se_donde_esta_jar() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Não foi possível encontrar o JAR do CrashDetector</span>";
	}

	@Override
	public String buscando_para_pid(long pid) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Procurando pelo PID: " + pid + "</span>";
	}

	@Override
	public String pid_esta_muerto(long pid) {
		return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + pid
				+ ") foi finalizado!</span>";
	}

	@Override
	public String no_tenemos_jvm() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Sem JVM disponível</span>";
	}

	@Override
	public String problema_con_graficas_ati() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Atualizar seus drivers ATI/AMD pode ajudar. Leia este guia para corrigir: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace()
				+ "'>Guia de atualização de drivers</a> https://www.amd.com/pt/support/download/drivers.html Baixar </span>";
	}

	@Override
	public String problema_con_graficas_nouveau() {
		return "<span style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Algumas versões antigas apresentam problemas com a interface de inicialização do Nouveau.</span>";
	}

	@Override
	public String problema_con_graficas_general() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Problema detectado com seus drivers de vídeo. Usuários de AMD/ATI devem atualizar os drivers. Usuários de NVIDIA devem garantir que todos os processos javaw.exe estejam usando a GPU dedicada. Leia este guia: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
	}

	@Override
	public String fmlEarlyWindow() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Falha ao carregar janela inicial do FML. Para corrigi-lo, vá para (.minecraft/config/fml.toml) e defina earlyWindowProvider para \"none\". Usuários de Mac M1 devem usar Java ARM. Este problema também pode ocorrer com drivers desatualizados. Se usar Windows e a configuração não funcionar, consulte: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#"
				+ config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
	}

	@Override
	public String no_tienes_las_dependencias_necesarias() {
		return "<span style='color:#" + config.obtenerColorError() + "'>Dependências faltando:</span>";
	}

	@Override
	public String linea_de_dependencia(String linea) {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>"
				+ linea.replace("Requested by", "Solicitado por").replace("Expected range", "Intervalo esperado")
				+ "</span>";
	}

	@Override
	public String local_headless(String archivo) {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Seu relatório do CrashDetector está aqui: <a href='" + archivo + "' style='color:#"
				+ config.obtenerColorEnlace() + "'>Visualizar Relatório</a></span>";
	}

	@Override
	public String texto_de_gui() {
		return "<span style='color:#" + config.obtenerColorInfo()
				+ "'>Esta é a interface gráfica do CrashDetector. Ignore-a se o jogo fechar normalmente.</span>";
	}

	@Override
	public String texto_de_boton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorBoton() + "'>Visualizar Relatório</span>";
	}

	@Override
	public String texto_debajo_de_buton_local_enlace() {
		return "<span style='color:#" + config.obtenerColorInfo() + "'>Visualizar relatório local no navegador.</span>";
	}

	@Override
	public String texto_de_buton_compartir_enlace() {
		return "Compartilhar Relatório";
	}

	@Override
	public String texto_debajo_de_buton_compartir_enlace() {
		return "Compartilhar relatório. Seus logs serão enviados para securelogger.net e o relatório para outros sites.";
	}

	@Override
	public String problematico_jar() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>JAR problemático detectado (prioridade: FATAL > Alta > Baixa):</b>";
	}

	@Override
	public String nivel() {
		return "<b style='color:#" + config.obtenerColorError() + "'> Nível:</b> ";
	}

	@Override
	public String posibilidad_fatal() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Potencialmente fatal:</b> ";
	}

	@Override
	public String modids_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>ModIDs problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
	}

	@Override
	public String packages_problematicos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Pacotes problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
	}

	@Override
	public String falta_de_clases_fatales() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você tem classes fatais (FATAL), isso é muito grave. Causas comuns são CoreMods defeituosos ou dependências fatais. Use o QuickFix para procurar mods com classes fatais. Classes fatais ausentes detectadas:</b>";
	}

	@Override
	public String corchetes_ondulados() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Conteúdo entre chaves (mais importantes no topo, mostrando primeiros 20):</b>";
	}

	@Override
	public String config_spongemixin_problematico() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Configuração problemática do SpongeMixin detectada: </b>";
	}

	@Override
	public String module_resolution_exception() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Você tem mods com pacotes duplicados. Você pode resolver isso removendo o pacote (pasta) duplicado do arquivo JAR. Você pode abrir o JAR em um programa de arquivamento como WinRAR ou 7z, ou também pode alterar a extensão do arquivo de .jar para .zip, excluir a pasta e depois renomeá-lo novamente para um arquivo .jar.</span>";
	}

	@Override
	public String modlauncher_mods_duplicado(String linea) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mods duplicados detectados</b> "
				+ linea.replace("from mod files", "de arquivos de mod");
	}

	@Override
	public String mcforge_mod_sospechoso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge detectou mod suspeito:</b> ";
	}

	@Override
	public String lithostichctov() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>CTOV requer lithostitched. Instale aqui: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#"
				+ config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
	}

	@Override
	public String necesitasSodiumParaIris() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Para usar shaders Iris/Oculus, você precisa do SODIUM ou outro loader compatível (Rubidium, Embedium, Bedium)</b>";
	}

	@Override
	public String kubeJSResourcePack(String mod_nombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Problema com expansão KubeJS </b>" + mod_nombre;
	}

	@Override
	public String problema_con_graficas_nvidia_windows_viejo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Problemas detectados com drivers NVIDIA em versões anteriores ao Windows 11." + "</span><br/><br/>"
				+ "Para garantir que o Minecraft (e a JVM atual) use a GPU dedicada NVIDIA, siga estes passos:<br/><br/>"
				+ "1. <strong>Identifique o executável do Java:</strong><br/>"
				+ "   - Este programa está usando o seguinte executável do Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Se você não vir um caminho específico, pode encontrar o executável do Java pesquisando por 'java.exe' no sistema.<br/><br/>"
				+ "2. <strong>Abra o Painel de Controle da NVIDIA:</strong><br/>"
				+ "   - Clique com o botão direito na área de trabalho e selecione 'Painel de Controle da NVIDIA'.<br/><br/>"
				+ "3. <strong>Configure a GPU preferida:</strong><br/>"
				+ "   - No Painel de Controle da NVIDIA, vá para 'Gerenciar configurações 3D'.<br/>"
				+ "   - Selecione a opção 'Programa específico'.<br/>"
				+ "   - Clique em 'Adicionar' e localize o executável do Java identificado anteriormente (ex.: 'java.exe').<br/>"
				+ "   - Certifique-se de que esteja configurado para usar o 'Processador de alto desempenho (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Salve as alterações:</strong><br/>"
				+ "   - Aplique as alterações e feche o Painel de Controle da NVIDIA.<br/><br/>"
				+ "5. <strong>Reinicie o Minecraft:</strong><br/>"
				+ "   - Reinicie o Minecraft para que as alterações tenham efeito.<br/><br/>"
				+ "Se você usa Windows Server 2022 ou Windows 10, esses passos são válidos desde que tenha os drivers NVIDIA mais recentes instalados.<br/><br/>"
				+ "Nota: Se você não encontrar o Painel de Controle da NVIDIA, certifique-se de que os drivers NVIDIA estão corretamente instalados.";
	}

	@Override
	public String problema_con_graficas_nvidia_windows_nuevo() {
		return "<span style='color:#" + config.obtenerColorError() + "'>"
				+ "Problemas detectados com drivers NVIDIA no Windows 11/Server 2025 ou posterior."
				+ "</span><br/><br/>"
				+ "Para garantir que o Minecraft (e a JVM atual) use a GPU dedicada NVIDIA, siga estes passos:<br/><br/>"
				+ "1. <strong>Identifique o executável do Java:</strong><br/>"
				+ "   - Este programa está usando o seguinte executável do Java: " + obtenerRutaJava() + "<br/>"
				+ "   - Se você não vir um caminho específico, pode encontrar o executável do Java pesquisando por 'java.exe' no sistema.<br/><br/>"
				+ "2. <strong>Abra o aplicativo Configurações:</strong><br/>"
				+ "   - Pressione as teclas <code>Win + I</code> para abrir o aplicativo Configurações.<br/>"
				+ "   - Navegue até <strong>Sistema > Tela > Gráficos</strong>.<br/><br/>"
				+ "3. <strong>Configure a GPU preferida:</strong><br/>"
				+ "   - Na seção 'Gráficos', clique em 'Configurações de gráficos padrão'.<br/>"
				+ "   - Selecione 'Aplicativos de desktop' e depois clique em 'Procurar'.<br/>"
				+ "   - Localize e selecione o executável do Java identificado anteriormente (ex.: 'java.exe').<br/>"
				+ "   - Uma vez adicionado, selecione o aplicativo Java na lista e configure-o para usar 'Alto desempenho (NVIDIA)'.<br/><br/>"
				+ "4. <strong>Salve as alterações:</strong><br/>"
				+ "   - Aplique as alterações e feche o aplicativo Configurações.<br/><br/>"
				+ "5. <strong>Reinicie o Minecraft:</strong><br/>"
				+ "   - Reinicie o Minecraft para que as alterações tenham efeito.<br/><br/>"
				+ "Se você usa Windows 11 ou Windows Server 2025+, esses passos são válidos desde que tenha os drivers NVIDIA mais recentes instalados.<br/><br/>"
				+ "Nota: Se você não encontrar a opção de configuração de gráficos, certifique-se de que os drivers NVIDIA estão corretamente instalados.";
	}

	@Override
	public String segundo60Tick() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Seu Servidor ou Mundo tem ticks de mais de 60 segundos. Isso pode ser devido a mods tornando o servidor mais lento ou ao hardware ser muito fraco.</b>";
	}

	@Override
	public String noTieneMemoria() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você não tem RAM/Memória suficiente. Você precisa alocar mais.</b>";
	}

	@Override
	public String theseus() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>O Theseus também tem mais problemas, incluindo falhas ao remover mods quando você tenta. Se você precisa jogar com arquivos mrpack, pode usar outros lançadores, como Prism Launcher (apenas para modrinth.com), ATLauncher (apenas para modrinth.com) ou Hello Minecraft Launcher (compatível com modrinth.com e bbsmc.net).</b>";
	}

	@Override
	public String noTieneConsolaDeLauncherCursedForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Você está usando \"Ignorar Início do Launcher\" (App CurseForge). Às vezes isso causa problemas difíceis de detectar. Isso ocorre devido à opção \"Ignorar Início do Launcher\" em versões antigas do App CurseForge ou na versão nova. Desative-a e use a opção \"Mojang Launcher\" nas configurações do CurseForge. Você pode assistir a este vídeo em inglês de Claws of Berk (vá para 1:11) "
				+ "<a href='https://youtu.be/g847O_2LjoE?si=8Y_oj3zyyhYTrSkv&t=71' style='color: inherit;'>aqui</a>.</b>";
	}

	@Override
	public String faltar_de_clases_advertencia() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Aviso: Classes ausentes detectadas (nível de aviso). Geralmente não é grave, mas pode causar problemas — diferente de erros fatais. Causas comuns incluem CoreMods defeituosos ou dependências ausentes. Use o QuickFix para verificar mods com classes faltantes. Classes ausentes detectadas:</b>";
	}

	@Override
	public String noResultados() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Sem Resultados</b>";
	}

	@Override
	public String ubicacionesDeLogs() {
		return "<b style='color:#" + config.obtenerColorInfo() + "'>Locações dos Logs:</b>";
	}

	@Override
	public String infoDeVerificaciones() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Aqui estão os resultados das suas verificações. Faça com calma; geralmente, a causa correta está na verificação 1 ou 2. O resto (erros 3 e acima) pode ser usado para confirmação, mas geralmente são erros em cascata que você pode ignorar. As falhas ocorrem em camadas, então corrigir o problema principal resolverá esse erro específico. No entanto, amanhã pode aparecer um novo erro não relacionado ao atual, pois muitas vezes um erro impede que outro apareça no console.</b>";
	}

	@Override
	public String versionDeJava() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Por favor, use o Java 17 para as versões 1.17-1.20.4 e o Java 21 para qualquer versão mais recente. Use o Java 8 para versões mais antigas. [Guia](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Se você ainda tiver problemas, pode ser porque algum mod tem arquivos muito novos ou antigos.</b>";
	}

	@Override
	public String java22() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>O Java 22 e superior não funciona em versões do Minecraft abaixo de 1.20.5 para a maioria dos modloaders devido ao ASM estar desatualizado.</b>"
				+ versionDeJava();
	}

	@Override
	public String javaObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>O Java está obsoleto </b>" + versionDeJava();
	}

	@Override
	public String jpms_modules_faltas(String mod_necesitas, String submod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Você precisa do módulo JPMS " + mod_necesitas
				+ " de " + submod + "</b>";
	}

	@Override
	public String null_pointer_error(String metodo, String objeto) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Não é possível invocar " + metodo + " porque "
				+ objeto + " é nulo</b>";
	}

	@Override
	public String analisisAvanzado() {
		return "Análise Avançada";
	}

	@Override
	public String seleccionarCarpeta() {
		return "Selecionar Pasta";
	}

	@Override
	public String cadenaBusqueda() {
		return "String de Busca";
	}

	@Override
	public String usarRegex() {
		return "Usar Regex";
	}

	@Override
	public String ignorarMayusculas() {
		return "Ignorar Maiúsculas";
	}

	@Override
	public String buscar() {
		return "Buscar";
	}

	@Override
	public String busquedaEnProgreso() {
		return "Busca em Andamento";
	}

	@Override
	public String noSeEncontraronResultados() {
		return "Nenhum Resultado Encontrado";
	}

	@Override
	public String errorBusqueda() {
		return "Erro na Busca";
	}

	@Override
	public String omitirYCerrar() {
		return "Ignorar e Fechar";
	}

	@Override
	public String guardarYCerrar() {
		return "Salvar e Fechar";
	}

	@Override
	public String pegaLosRegistrosAqui() {
		return "Cole os registros aqui";
	}

	@Override
	public String archivo() {
		return "Arquivo";
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
		return "Ponto Final do Relatório";
	}

	@Override
	public String sitoDeLogging() {
		return "Site de Registro:";
	}

	@Override
	public String apiDeLogging() {
		return "API de Registro:";
	}

	@Override
	public String anonimizarRegistros() {
		return "Anonimizar registros (Beta)";
	}

	@Override
	public String botonDeCompartirInforme() {
		return "Compartilhar Relatório e todos os registros selecionados";
	}

	@Override
	public String arco() {
		return "Esta caixa de diálogo permite que você compartilhe logs usando a API do SecureLogger "
				+ "em <a href=\"https://securelogger.net\">securelogger.net</a>. Ao clicar no botão para compartilhar o relatório, "
				+ "seu relatório é enviado ao endpoint selecionado (padrão: asbestosstar.egoism.jp) (pode ser alterado na parte inferior). "
				+ "Você pode compartilhar todos os logs selecionados junto com o relatório. Se não quiser fazer upload, não use esta caixa de diálogo! "
				+ "Não processamos seu relatório no endpoint oficial (<a href=\"https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb\">https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb</a>); "
				+ "apenas removemos links não permitidos. O código está aqui: <a href=\"https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb\">código-fonte</a>. "
				+ "Isso é usado apenas para exibir informações sobre sua falha e o link para os logs. No entanto, é possível usar um endpoint personalizado que pode não usar os mesmos métodos. "
				+ "Você está usando o site de relatórios " + Config.obtenerInstancia().obtenerSitoDeInformes()
				+ " e o site de logs " + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". "
				+ "Você também pode compartilhar logs individuais sem um relatório clicando nos botões de compartilhamento ao lado dos nomes dos logs individuais; "
				+ "os logs serão enviados ao site de logs selecionado. O CrashDetector possui anonimização padrão de logs, que tenta remover nomes de usuário, UUIDs, "
				+ "tokens de acesso, IDs de sessão, endereços IP e outros dados. No entanto, não é perfeita. Ainda assim, o autor do modpack pode desativá-la. "
				+ "Ela pode ser ativada ou desativada pela caixa de seleção na parte inferior desta tela. Você é o controlador dos seus próprios dados; cabe a você decidir onde enviar seus dados. "
				+ "Os sites de logs são de propriedade de terceiros cuja titularidade muitas vezes é ocultada por motivos de privacidade. Você assume total responsabilidade pela gestão dos seus dados e pelos riscos envolvidos. "
				+ "A Caixa de Diálogo de Compartilhamento do CrashDetector é apenas uma interface que permite gerenciar isso. "
				+ "É importante que você esteja ciente do GDPR e ARCO. "
				+ "Se você estiver na Europa, pode usar o <a href=\"https://securelogger.top\">securelogger.top</a> hospedado na Alemanha pela Hetzner. "
				+ "Para mais informações legais, consulte os seguintes links: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFPDPPP.pdf\">LFPDPPP</a>, "
				+ "<a href=\"https://eur-lex.europa.eu/legal-content/EN/TXT/PDF/?uri=CELEX:32016R0679\">GDPR</a>, "
				+ "<a href=\"https://www.ppc.go.jp/files/pdf/20220401_personal_basicpolicy.pdf\">Política Básica de Proteção de Dados Pessoais no Japão</a>.";
	}

	@Override
	public String enlaceDelReporte() {
		return "Link do Relatório:";
	}

	@Override
	public String guardarConfigDeCompartir() {
		return "Salvar Configuração de Compartilhamento";
	}

	@Override
	public String registroDemasiadoGrande() {
		return "O registro é muito grande para este site de registro. Escolha outro e tente novamente.";
	}

	@Override
	public String errorConPublicarRegistro(String error) {
		return "Erro ao publicar registro " + error;
	}

	@Override
	public String apiDeRegistroNoExiste() {
		return "A API de Registro não existe. Por favor, altere a API de registro nas configurações.";
	}

	@Override
	public String errorSSL() {
		return "Você está com um Erro SSL. Isso é comum em versões antigas do Java, "
				+ "incluindo as versões do Java 8 no Launcher Minecraft padrão "
				+ "e as versões em sun.com e java.com. Isso afeta vários aspectos, "
				+ "como os arquivos JAR do instalador do MinecraftForge, a função de compartilhar relatórios "
				+ "do CrashDetector ao usar o endpoint padrão, alguns mods que exigem internet "
				+ "e alguns sites de registro. Se isso acontecer enquanto você tenta compartilhar um relatório, "
				+ "basta anexar uma captura de tela e selecionar um site de registro compatível "
				+ "com versões mais antigas do Java 8.";
	}

	@Override
	public String errorJavaFMLVersion(String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "JavaFML Incompatível: Requer versão "
				+ requerido + ", detectado " + encontrado + "</b>";
	}

	@Override
	public String errorJavaFML_MCForge() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Atenção! JavaFML requer uma versão específica do Minecraft Forge</b>";
	}

	@Override
	public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "O arquivo JAR '" + archivoJar
				+ "' requer o provedor de linguagem '" + proveedor + "' versão '" + requerido
				+ "' ou superior, mas apenas a versão '" + encontrado + "' foi encontrada.</b>";
	}

	@Override
	public String advertenciaMalwareFalso() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "ALERTA! O Crash Assistant é um detector de malware falso. Ele bloqueia intencionalmente o lançamento do jogo, ignorando sua liberdade de continuar jogando com os mods que ele visa. "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Ver código MalwareMod.java</a>   "
				+ "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Ver código JarInJarHelper.java</a>. "
				+ "Apenas este mod está na lista deles no momento, e eles estão realmente indo atrás apenas do site de registro padrão, que pode ser alterado pelo usuário, e isso só ocorre se você escolher explicitamente usar o recurso integrado de compartilhamento de logs. O CrashAssistant NÃO faz nenhuma verificação para determinar qual site de registro está sendo usado e não explica como alterá-lo (há um menu suspenso na parte inferior da caixa de diálogo de compartilhamento), e independentemente do site configurado, o CrashAssistant bloqueará o lançamento do jogo. Em sua mensagem, eles dizem para fazer sua própria pesquisa, FAÇA ISSO, examine o código do CrashDetector e do Crash Assistant e entenda o que eles fazem, NÃO confie em uma apelação à autoridade.</b>";
	}

	@Override
	public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String classeNaoEncontrada) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "O mod '" + idMod
				+ "' falhou porque a classe necessária não foi encontrada: '" + classeNaoEncontrada
				+ "'. Certifique-se de que todas as dependências estão instaladas corretamente.</b>";
	}

	@Override
	public String waterMediaTL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "O Watermedia bloqueou o uso do TLauncher.</b>";
	}

	@Override
	public String optifineObsoleta() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Você está usando uma versão do Optifine para uma versão obsoleta do Minecraft. Você precisa usar a versão do Optifine correspondente à versão do Minecraft que você está usando.</b>";
	}

	@Override
	public String servicioMLNoPudoCargar(String servicio) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Não foi possível carregar o Serviço do ModLauncher: </b>" + servicio + ".";
	}

	@Override
	public String errorConJSONDeRegistro(String arquivoJar, String recurso) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erro ao analisar o arquivo JSON '" + recurso
				+ "' do arquivo JAR '" + arquivoJar + "'. Há problemas com o registro.</b>";
	}

	@Override
	public String errorVersionDependencia(String modId, String dependencia, String requerido, String atual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erro: O mod '" + modId
				+ "' requer a versão '" + requerido + "' ou superior de '" + dependencia + "', mas foi encontrada '"
				+ atual + "'." + "</span>";
	}

	@Override
	public String gpu_no_compatible() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sua GPU não suporta a versão do OpenGL necessária para esta versão do jogo. Atualize seus drivers ou troque sua placa gráfica.</b>";
	}

	@Override
	public String recomendacionMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Aumente a memória alocada para o jogo ou reduza o uso de mods/plugins</b>";
	}

	@Override
	public String error32BitMemoria() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "JVM de 32 bits detectada: Não pode usar mais de 4GB de RAM. "
				+ "Instale uma JVM de 64 bits para aproveitar toda a sua memória disponível</b>";
	}

	@Override
	public String permGenError() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro crítico de memória PermGen. Aumente o espaço de memória permanente ou reduza a carga de classes</b>";
	}

	public String errorCompatibilidadJava8() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro de compatibilidade entre Java 8 e versões modernas</b>";
	}

	public String errorJava9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 9+ não suportado - Use Java 8 para versões antigas do Forge</b>";
	}

	public String errorJava8Requerido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Java 8 necessário (versão 52.0). Atualize ou configure corretamente</b>";
	}

	@Override
	public String errorDeBloqueTeselado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro crítico de compatibilidade: Blocos não suportados nesta versão. "
				+ "Verifique se as versões dos mods e do servidor são compatíveis</b>";
	}

	@Override
	public String errorMonitorLWJGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erro de configuração de monitores: "
				+ "Não foi possível definir o modo de tela. " + "Verifique:</b>"
				+ "<br>- Configuração de múltiplos monitores" + "<br>- Drivers de placa gráfica atualizados"
				+ "<br>- Resolução suportada pelo sistema";
	}

	@Override
	public String errorOpcionesGCJava() {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erro nas opções do Java: "
				+ "Opções conflitantes do coletor de lixo. "
				+ "Verifique se você não combina vários algoritmos GC nos parâmetros da JVM</b>";
	}

	@Override
	public String errorConfigMCForge() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro crítico de configuração do NightConfig/Forge: "
				+ "Arquivo de configuração corrompido ou incompleto. "
				+ "Isso pode ser causado por arquivos de configuração vazios (muitas vezes de 0 bytes) na pasta 'config' em versões antigas ou personalizadas do NightConfig. "
				+ "Na maioria das versões, o Night Config Fixes resolverá o problema, mas se você estiver usando uma versão incompatível ou personalizada do NightConfig, precisará excluir os arquivos de configuração manualmente. "
				+ "Esse problema é mais comum em versões antigas do MC Forge (que incluem o NightConfig) e em mods antigos do FabricMC que empacotam o NightConfig, mas também pode ocorrer em algumas versões personalizadas do NightConfig. "
				+ "Mais informações sobre as soluções estão disponíveis em <a href='https://www.curseforge.com/minecraft/mc-mods/night-config-fixes'>Night Config Fixes</a>.</b>";
	}

	@Override
	public String problema_con_graficas_intel() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro de driver Intel HD Graphics detectado. Soluções:</b>"
				+ "<br>1. Atualize os drivers Intel em <a href='https://www.intel.com/content/www/us/en/download-center/home.html'>intel.com</a> (versão mínima 15.xx.xx.xx)"
				+ "<br>2. No Minecraft: Opções → Vídeo → Ative 'Enable VBOs' e 'VSync'"
				+ "<br>3. Atribua 1GB-2GB de RAM ao jogo no launcher"
				+ "<br>4. Desative temporariamente o antivírus/firewall durante a atualização";
	}

	public String nombre_de_faltar_de_clases_advertencia() {
		return "Aviso: Classes ausentes detectadas";
	}

	public String nombre_de_bloque_teselado() {
		return "Erro de renderização de blocos";
	}

	public String nombre_de_contenido_de_stacktrace() {
		return "Análise de stack trace";
	}

	public String nombre_de_cursed_consola() {
		return "Console CurseForge incompleta";
	}

	public String nombre_de_early_window() {
		return "Erro de janela inicial (FMLEarlyWindow)";
	}

	public String nombre_de_drivers() {
		return "Problemas com drivers de vídeo";
	}

	public String nombre_de_error_de_config_mcforge() {
		return "Configuração corrompida do MCForge";
	}

	public String nombre_de_error_de_monitor_lwjgl() {
		return "Falha no modo de exibição (LWJGL)";
	}

	public String nombre_de_fabricmc_runtime_error_provided_by() {
		return "Erro de inicialização do FabricMC";
	}

	public String nombre_de_falta_module_jmps() {
		return "Módulos JPMS ausentes";
	}

	public String nombre_de_faltar_de_clases() {
		return "Classes críticas ausentes";
	}

	public String nombre_de_faltas_dependencias_de_modlauncher() {
		return "Dependências ausentes do ModLauncher";
	}

	public String nombre_de_java_versiones() {
		return "Versões incompatíveis do Java";
	}

	public String nombre_de_faltar_de_kubejs_resourcepack() {
		return "Erro de recursos do KubeJS";
	}

	public String nombre_de_lenguaje_proveedor_check() {
		return "Provedor de linguagem incompatível";
	}

	public String nombre_de_faltar_de_liyhostictchctov() {
		return "Erro Litchhost";
	}

	public String nombre_de_malware_falso_crash_assistant() {
		return "Falso positivo de malware detectado";
	}

	public String nombre_de_mcforge_mod_sespechoso() {
		return "Mod suspeito detectado";
	}

	public String nombre_de_mods_duplicados_modlauncher() {
		return "Mods duplicados no ModLauncher";
	}

	public String nombre_de_modules_duplicados_jmps() {
		return "Conflitos de módulos JPMS";
	}

	public String nombre_de_necesitas_sodium() {
		return "Sodium necessário para Iris";
	}

	public String nombre_de_no_puede_analizar_json_de_registro() {
		return "Erro ao analisar JSON de registro";
	}

	public String nombre_de_no_tiene_memoria() {
		return "Memória insuficiente";
	}

	public String nombre_de_null_pointer() {
		return "Erro de ponteiro nulo (NullPointerException)";
	}

	public String nombre_de_opciones_java_gc_invalidas() {
		return "Opções inválidas de GC do Java";
	}

	public String nombre_de_optifine_obsoleta() {
		return "OptiFine obsoleta/incompatível";
	}

	public String nombre_de_60_segundo_trick() {
		return "Tick crítico do servidor (60s)";
	}

	public String nombre_de_servicio_de_modlauncher_no_funciona() {
		return "Serviços do ModLauncher falharam";
	}

	public String nombre_de_spongemixin_configs_problematicos() {
		return "Configurações problemáticas do SpongeMixing";
	}

	public String nombre_de_theseus() {
		return "Theseus incompatível";
	}

	public String nombre_de_watermedia_tl() {
		return "Launcher TLauncher não suportado pelo WATERMeDIA";
	}

	@Override
	public String auditorias_transformer() {
		return "Auditorias Transformer";
	}

	@Override
	public String auditorias_transformer_detectadas() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Estes são os resultados dos conteúdos das Auditorias Transformer no Launcher Vanilla. Geralmente, é menos preciso que o analisador de StackTrace, mas o Launcher Vanilla nem sempre tem conteúdo para {}</b>";
	}

	@Override
	public String descripcionEscanerMCreator() {
		return "Isso verifica seus mods em busca de mods criados com o MCreator. Embora a maioria dos mods do MCreator seja boa e existam muitos mods incríveis feitos com o MCreator, às vezes eles têm problemas e uma má reputação. Isso ajuda a identificá-los. Observe que até os mais bem avaliados podem não ser realmente do MCreator; por exemplo, podem ter integração com o MCreator.";
	}

	@Override
	public String escanear() {
		return "Escanear";
	}

	@Override
	public String cargando() {
		return "Carregando";
	}

	@Override
	public String codigo() {
		// TODO Auto-generated method stub
		return "pt";
	}

	@Override
	public String inicioApp() {
		return "Início do Jogo/App";
	}

	@Override
	public String ajustesCrashDetector() {
		return "Configurações do CrashDetector";
	}

	@Override
	public String confidencialidad() {
		return "Privacidade";
	}

	@Override
	public String tooltip() {
		return "Dica de ferramenta";
	}

	@Override
	public String config() {
		return "Configuração";
	}

	@Override
	public String abrirCarpeta() {
		return "Abrir Pasta";
	}

	@Override
	public String actualizar() {
		return "Atualizar";
	}

	@Override
	public String anadirRegistro() {
		return "Adicionar Registro";
	}

	@Override
	public String usarIdiomaDelSistema() {
		return "Usar idioma do sistema";
	}

	@Override
	public String volver() {
		return "Voltar";
	}

	@Override
	public String colorFondo() {
		return "Cor de fundo (#RRGGBB):";
	}

	@Override
	public String colorTexto() {
		return "Cor do texto (#RRGGBB):";
	}

	@Override
	public String colorBoton() {
		return "Cor do botão (#RRGGBB):";
	}

	@Override
	public String colorCajaTexto() {
		return "Cor da caixa de texto (#RRGGBB):";
	}

	@Override
	public String colorEnlace() {
		return "Cor do link (#RRGGBB):";
	}

	@Override
	public String colorTitulosConsolas() {
		return "Cor dos títulos das consoles (#RRGGBB):";
	}

	@Override
	public String colorError() {
		return "Cor de erro (#RRGGBB):";
	}

	@Override
	public String colorAdvertencia() {
		return "Cor de aviso (#RRGGBB):";
	}

	@Override
	public String colorInfo() {
		return "Cor de informação (#RRGGBB):";
	}

	@Override
	public String colorTitulo() {
		return "Cor do título (#RRGGBB):";
	}

	@Override
	public String colorEnlaceTexto() {
		return "Cor do texto do link (#RRGGBB):";
	}

	@Override
	public String transformacionDeMinecraftCodigo0() {
		return "Abrir CrashDetector apenas em caso de falha";
	}

	@Override
	public String activar_parche() {
		return "Ativar Patch";
	}

	@Override
	public String noHaySolucionDisponible() {
		return "Nenhuma solução disponível";
	}

	@Override
	public String error() {
		return "erro";
	}

	@Override
	public String error_al_eliminar_jar() {
		return "Erro ao excluir Jar";
	}

	@Override
	public String jar_eliminado_exitosamente() {
		return "Jar excluído com sucesso";
	}

	@Override
	public String exito() {
		return "sucesso";
	}

	@Override
	public String eliminar() {
		return "excluir";
	}

	@Override
	public String error_al_eliminar_paquete() {
		return "Erro ao excluir pacote";
	}

	@Override
	public String paquete_eliminado_exitosamente() {
		return "Pacote excluído com sucesso";
	}

	@Override
	public String eliminar_paquete() {
		return "Excluir pacote";
	}

	@Override
	public String no_se_encontraron_mods_con_paquete() {
		return "Nenhum mod com pacote encontrado";
	}

	@Override
	public String no_se_puede_eliminar_paquete() {
		return "Não é possível excluir o pacote";
	}

	@Override
	public String eliminar_jar() {
		return "Excluir jar";
	}

	@Override
	public String no_se_encontraron_mods_duplicados() {
		return "Nenhum mod duplicado encontrado";
	}

	@Override
	public String archivo_no_encontrado() {
		return "Arquivo Não Encontrado";
	}

	@Override
	public String directorio_eliminado() {
		return "Diretório Excluído";
	}

	@Override
	public String error_al_eliminar_jar_anidado() {
		return "Erro ao excluir Jar aninhado";
	}

	@Override
	public String archivo_interno_no_encontrado() {
		return "Arquivo interno não encontrado";
	}

	@Override
	public String archivo_eliminado() {
		return "arquivo excluído";
	}

	@Override
	public String error_al_eliminar_archivo() {
		return "erro ao excluir arquivo";
	}

	@Override
	public String archivo_externo_no_valido() {
		return "arquivo externo inválido";
	}

	@Override
	public String elemento_mod_eliminado() {
		return "Elemento mod excluído";
	}

	@Override
	public String error_al_reemplazar_jar_externo() {
		return "Erro ao substituir Jar externo";
	}

	@Override
	public String error_al_eliminar_elemento_mod() {
		return "erro ao excluir elemento mod";
	}

	@Override
	public String error_al_eliminar_directorio() {
		return "erro ao excluir diretório";
	}

	@Override
	public String formato_invalido_para_jar_anidado() {
		return "formato inválido para Jar aninhado";
	}

	@Override
	public String jar_anidado_eliminado() {
		return "Jar aninhado excluído";
	}

	@Override
	public String error_al_limpiar_temporales() {
		return "erro ao limpar arquivos temporários";
	}

	@Override
	public String mensaje_de_trace_fatal_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Mensagem de Trace Fatal Última (Não traduzida):</b> ";
	}

	@Override
	public String mensaje_de_trace_ultima_no_traductado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mensagem de Trace Última (Não traduzida):</b> ";
	}

	@Override
	public String solucionParaAdvertenciaFaltasClases() {
		return "Você pode pesquisar no banco de dados WaifuNeoForge para encontrar mods. Selecione a versão do jogo, o carregador de mods e a classe. Use a combinação mais próxima. Você pode pesquisar uma vez por minuto.";
	}

	@Override
	public String solucionFaltasClases() {
		return "Você pode pesquisar no banco de dados WaifuNeoForge para encontrar mods. Selecione a versão do jogo, o carregador de mods e a classe. Use a combinação mais próxima. Você pode pesquisar uma vez por minuto.";
	}

	@Override
	public String solucionParaJavaInstallar() {
		return "Ambos os lançadores têm as versões corretas do Java, mas nem todas; você pode instalar a versão correta do Java a partir do gerenciador de pacotes no seu sistema ou usando os botões.";
	}

	@Override
	public String error_animacion_no_encontrada() {
		return "<b style='color:#" + config.obtenerColorError() + "'>Mod com Animação Ausente: " + "</b>";
	}

	@Override
	public String nombre_de_error_animacion_minecraft() {
		return "NoSuchElementException (Exceção Sem Elemento) Animação Ausente";
	}

	@Override
	public String no_se_encontraron_mods_para_eliminar() {
		return "Nenhum mod foi encontrado para excluir";
	}

	@Override
	public String opcionesGCInvalidas() {
		return "Substituir opções de GC conflitantes por -XX:+UseG1GC";
	}

	@Override
	public String aumentarMemoriaHeap() {
		return "Aumentar o tamanho da memória heap usando a opção -Xmx.";
	}

	@Override
	public String aumentarMemoriaPermgen() {
		return "Aumentar o tamanho da memória permgen usando a opção -XX:MaxPermSize.";
	}

	@Override
	public String utilizarJVM64Bits() {
		return "Usar uma JVM de 64 bits para aumentar a memória disponível.";
	}

	@Override
	public String optimizarCodigo() {
		return "Otimizar o código para reduzir o uso de memória e melhorar o desempenho.";
	}

	@Override
	public String utilizarRecolectorBasuraEficiente() {
		return "Usar um coletor de lixo eficiente para reduzir as pausas na aplicação.";
	}

	@Override
	public String modulos() {
		return "Módulos";
	}

	@Override
	public String paquete() {
		return "Pacote";
	}

	@Override
	public String solucionRegistrosMalMapeados() {
		return "Há IDs faltando. Causas comuns são mods faltando ou dados de itens faltando. Pastas de dados comuns são datafiedcontents/ e kubejs/ ou outras pastas de mods.";
	}

	@Override
	public String nombre_de_registros_mal_mapeados() {
		return "registros incorretamente mapeados";
	}

	public String mensajeCierreAuthMe() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>O plugin 'AuthMe' falhou ao carregar e desligou o servidor.</b> ";
	}

	public String nombreProblemaCierreAuthMe() {
		return "Problema de desligamento causado pelo AuthMe";
	}

	public String solucionCierreAuthMe() {
		return "A regra 'stopServer' mudou para 'true'.";
	}

	public String solucionConfigurarPluginAuthMe() {
		return "Configure o plugin AuthMe (plugins/AuthMe/config.yml)";
	}

	public String solucionInstalarVersionDiferenteAuthMe() {
		return "Instale uma versão diferente do plugin 'AuthMe'";
	}

	public String solucionEliminarPluginAuthMe() {
		return "Remover o plugin 'AuthMe'";
	}

	@Override
	public String mensajeProblemaCargaMultiverso(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mundo '" + nombreMundo
				+ "' não pôde ser carregado pois contém erros e provavelmente está corrompido.</b> ";
	}

	@Override
	public String nombreProblemaCargaMultiverso() {
		return "Problema ao carregar mundo do Multiverse";
	}

	@Override
	public String solucionRepararMundo(String nombreMundo) {
		return "Repare o mundo '" + nombreMundo + "', por exemplo usando Minecraft Region Fixer ou MCEdit.";
	}

	@Override
	public String solucionEliminarCarpetaMundo(String nombreMundo) {
		return "Exclua a pasta do mundo '" + nombreMundo + "'.";
	}

	@Override
	public String mensajeConfiguracionPermissionsEx() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>A configuração da extensão 'PermissionsEx' é inválida.</b> ";
	}

	@Override
	public String nombreProblemaConfiguracionPermissionsEx() {
		return "Problema na configuração do PermissionsEx";
	}

	@Override
	public String solucionConfigurarPermissionsEx() {
		return "Configure o plugin PermissionsEx (plugins/PermissionsEx/permissions.yml)";
	}

	@Override
	public String solucionEliminarPluginPermissionsEx() {
		return "Remover o plugin 'PermissionsEx'";
	}

	@Override
	public String mensajeNombrePluginAmbiguo(String nombrePlugin, String primerPath, String segundoPath) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Há múltiplos arquivos de plugins com o nome '"
				+ nombrePlugin + "': '" + primerPath + "' e '" + segundoPath + "'.</b> ";
	}

	@Override
	public String nombreProblemaNombrePluginAmbiguo() {
		return "Problema com nome ambíguo de plugin";
	}

	@Override
	public String solucionEliminarPlugin(String nombrePlugin) {
		return "Remover o plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeCargaChunk() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Ocorreu uma exceção enquanto o mundo carregava os chunks. Se existir para sua plataforma, o FeatureRecycler poderá resolver o problema. https://www.curseforge.com/minecraft/mc-mods/feature-recycler</b>";
	}

	@Override
	public String nombreProblemaCargaChunk() {
		return "Exceção ao carregar chunks";
	}

	@Override
	public String solucionEliminarChunk() {
		return "Remova o chunk problemático do mundo, por exemplo usando MCEdit ou excluindo o arquivo de região.";
	}

	@Override
	public String mensajeExcepcionComandoPlugin(String nombrePlugin, String comando) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' não pode executar o comando '/" + comando + "'.</b> ";
	}

	@Override
	public String nombreProblemaExcepcionComandoPlugin() {
		return "Exceção ao executar comando do plugin";
	}

	@Override
	public String solucionInstalarVersionDiferentePlugin(String nombrePlugin) {
		return "Instale uma versão diferente do plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeDependenciaPluginUnica(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' requer a seguinte dependência: '" + dependencia + "'.</b> ";
	}

	@Override
	public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
		StringBuilder deps = new StringBuilder();
		for (int i = 0; i < dependencias.size(); i++) {
			if (i > 0)
				deps.append(", ");
			deps.append("'").append(dependencias.get(i)).append("'");
		}
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' está faltando as seguintes dependências: " + deps.toString() + ".</b> ";
	}

	@Override
	public String nombreProblemaDependenciaPlugin() {
		return "Dependência de plugin ausente";
	}

	@Override
	public String solucionInstalarPlugin(String nombrePlugin) {
		return "Instale o plugin '" + nombrePlugin + "'";
	}

	@Override
	public String mensajeVersionAPIIncompatible(String nombrePlugin, String versionAPI) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' requer a versão da API '" + versionAPI + "' que não é compatível com o servidor atual.</b> ";
	}

	@Override
	public String nombreProblemaVersionAPIIncompatible() {
		return "Versão de API incompatível";
	}

	@Override
	public String solucionInstalarVersionServidor(String version) {
		return "Instale a versão '" + version + "' do software do seu servidor.";
	}

	@Override
	public String mensajeMundoDuplicado(String nombreMundo) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mundo '" + nombreMundo
				+ "' é uma duplicata de outro mundo e não pode ser carregado.</b> ";
	}

	@Override
	public String nombreProblemaMundoDuplicado() {
		return "Mundo Duplicado";
	}

	@Override
	public String solucionEliminarUID(String nombreMundo) {
		return "Exclua o arquivo 'uid.dat' do mundo '" + nombreMundo + "'";
	}

	@Override
	public String solucionEliminarMundo(String nombreMundo) {
		return "Exclua a pasta do mundo '" + nombreMundo + "'";
	}

	@Override
	public String mensajeTickingEntidadBloque(String nombre, String tipo, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "<b style='color:#" + config.obtenerColorError() + "'>A entidade de bloco '" + nombre + "' do tipo '"
				+ tipo + "' na localização " + coords + " está causando erros no ticking.</b> ";
	}

	@Override
	public String nombreProblemaTickingEntidadBloque() {
		return "Entidade de Bloco Problemática";
	}

	@Override
	public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
		String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
		return "Remova a entidade '" + nombre + "' na localização " + coords
				+ " usando MCEdit ou editando o mundo diretamente.";
	}

	@Override
	public String mensajeModDuplicadoFabric(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod
				+ "' tem múltiplas versões instaladas.</b> ";
	}

	@Override
	public String nombreProblemaModDuplicadoFabric() {
		return "Mod Duplicado no Fabric";
	}

	@Override
	public String solucionEliminarModDuplicado(String rutaMod) {
		return "Exclua o arquivo duplicado do mod: " + rutaMod;
	}

	@Override
	public String mensajeModIncompatible(String primerMod, String segundoMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Os mods '" + primerMod + "' e '" + segundoMod
				+ "' são incompatíveis entre si.</b> ";
	}

	@Override
	public String nombreProblemaModIncompatibleFabric() {
		return "Mod Incompatível no Fabric";
	}

	@Override
	public String solucionEliminarMod(String nombreMod) {
		return "Remova o mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModFatal(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod
				+ "' tem um erro crítico e não pode ser executado.</b> ";
	}

	@Override
	public String nombreProblemaModFatal() {
		return "Mod com Erro Crítico";
	}

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
				+ "'>Os seguintes mods são necessários mas não estão instalados: " + deps.toString() + ".</b>";
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
		if (version == null || version.isEmpty()) {
			return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' requer o mod '"
					+ dependencia + "' como dependência.</b>";
		} else {
			return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' requer o mod '"
					+ dependencia + "' na versão " + version + ".</b>";
		}
	}

	@Override
	public String nombreProblemaDependenciaMod() {
		return "Dependência de mod ausente";
	}

	@Override
	public String solucionInstalarMod(String nombreMod) {
		return "Instale o mod '" + nombreMod + "'";
	}

	@Override
	public String solucionInstalarModConVersion(String nombreMod, String version) {
		return "Instale o mod '" + nombreMod + "' com a versão " + version;
	}

	@Override
	public String mensajePluginTickingRegionalSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' não é compatível com o ticking regional do Folia.</b> ";
	}

	@Override
	public String mensajePluginTickingRegionalPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes plugins não são compatíveis com o ticking regional do Folia: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginTickingRegional() {
		return "Plugin incompatível com Ticking Regional";
	}

	@Override
	public String solucionInstalarSoftwareSinTickingRegional(String software) {
		return "Instale uma versão sem ticking regional, como " + software;
	}

	@Override
	public String mensajeModFaltanteEnDatapackSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod
				+ "' está faltando no datapack.</b>";
	}

	@Override
	public String mensajeModFaltanteEnDatapackPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes mods estão faltando no datapack: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModFaltanteEnDatapack() {
		return "Mod ausente no datapack";
	}

	@Override
	public String mensajeModExcepcionSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' causou um erro.</b>";
	}

	@Override
	public String mensajeModExcepcionPlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes mods causaram erros: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaModExcepcion() {
		return "Mod do Forge com Exceção";
	}

	@Override
	public String solucionInstalarVersionDiferenteMod(String nombreMod) {
		return "Instale uma versão diferente do mod '" + nombreMod + "'";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftSingular(String nombreMod, String versionMC) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod
				+ "' não é compatível com a versão do Minecraft " + versionMC + ".</b>";
	}

	@Override
	public String mensajeModIncompatibleConMinecraftPlural(List<String> mods, List<String> versionesMC) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes mods não são compatíveis com as respectivas versões do Minecraft: ");

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

	@Override
	public String nombreProblemaModIncompatibleConMinecraft() {
		return "Mod incompatível com o Minecraft";
	}

	@Override
	public String solucionInstalarVersionForge(String versionMC) {
		return "Instale uma versão do Forge compatível com o Minecraft " + versionMC;
	}

	@Override
	public String mensajeDependenciaModFaltante(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod
				+ "' está faltando e é necessário para carregar o mundo ou plugin.</b>";
	}

	@Override
	public String nombreProblemaDependenciaModFaltante() {
		return "Mod ausente";
	}

	@Override
	public String mensajeWorldModFaltanteSingular(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mundo foi salvo com o mod '" + nombreMod
				+ "' que parece estar ausente.</b>";
	}

	@Override
	public String mensajeWorldModFaltantePlural(List<String> mods) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("O mundo foi salvo com os seguintes mods que parecem estar ausentes: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaWorldModFaltante() {
		return "Mod ausente no mundo";
	}

	@Override
	public String mensajeVersionModMundoSingular(String nombreMod, String versionEsperada, String versionActual) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O mundo foi salvo com o mod '" + nombreMod
				+ "' na versão " + versionEsperada + ", e agora está na versão " + versionActual + ".</b>";
	}

	@Override
	public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas,
			List<String> versionesActuales) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes mods têm versões diferentes no mundo salvo: ");

		for (int i = 0; i < mods.size(); i++) {
			if (i > 0) {
				if (i == mods.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(mods.get(i)).append("'");
			sb.append(" (Salvo: ").append(versionesEsperadas.get(i)).append(", Atual: ")
					.append(versionesActuales.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaVersionModMundo() {
		return "Incompatibilidade de versão do mod no mundo salvo";
	}

	@Override
	public String mensajeVersionDowngrade() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você tentou carregar um mundo criado com uma versão mais nova do Minecraft.</b>";
	}

	@Override
	public String nombreProblemaVersionDowngrade() {
		return "Tentativa de carregar mundo de versão superior";
	}

	@Override
	public String solucionVersionDowngrade() {
		return "Instale uma versão mais recente do Minecraft.";
	}

	@Override
	public String solucionGenerarNuevoMundo() {
		return "Gere um novo mundo.";
	}

	@Override
	public String mensajeDependenciaPluginFaltanteSingular(String nombrePlugin, String dependencia) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' precisa do seguinte plugin como dependência: '" + dependencia + "'.</b>";
	}

	@Override
	public String mensajeDependenciaPluginFaltantePlural(List<String> plugins, List<String> dependencias) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes plugins precisam de dependências que não estão instaladas: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append("'").append(plugins.get(i)).append("' (").append(dependencias.get(i)).append(")");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaDependenciaPluginFaltante() {
		return "Plugin com dependência ausente";
	}

	@Override
	public String mensajePluginIncompatibleSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' é incompatível com a versão atual do servidor.</b>";
	}

	@Override
	public String mensajePluginIncompatiblePlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes plugins são incompatíveis com a versão atual do servidor: ");

		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}

		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginIncompatible() {
		return "Plugin incompatível com PocketMine-MP";
	}

	@Override
	public String mensajePluginEjecucionSingular(String nombrePlugin) {
		return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin
				+ "' causou um erro durante a execução.</b>";
	}

	@Override
	public String mensajePluginEjecucionPlural(List<String> plugins) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Os seguintes plugins causaram erros durante a execução: ");
		for (int i = 0; i < plugins.size(); i++) {
			if (i > 0) {
				if (i == plugins.size() - 1) {
					sb.append(" e ");
				} else {
					sb.append(", ");
				}
			}
			sb.append("'").append(plugins.get(i)).append("'");
		}
		sb.append(".</b>");
		return sb.toString();
	}

	@Override
	public String nombreProblemaPluginEjecucion() {
		return "Plugin com erro de execução";
	}

	@Override
	public String nombreLegacyRandomSourceMultiHilos() {
		return "LegacyRandomSource Multi Threads";
	}

	@Override
	public String mensajeLegacyRandomSourceMultiHilos() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você tem problemas com múltiplas threads acessando a classe LegacyRandomSource. Obtenha mais informações usando o mod 'Unsafe World Random Access Detector' ou 'C2ME'.</b>";
	}

	@Override
	public String desdeUltimoExito() {
		return "Desde o Último Sucesso";
	}

	@Override
	public String noHayCambios() {
		return "Sem Alterações";
	}

	@Override
	public String desdeUltimoIntento() {
		return "Desde a Última Tentativa";
	}

	@Override
	public String fallo() {
		return "Falhou";
	}

	@Override
	public String diferentesDeLasMods() {
		return "Diferente das Mods";
	}

	@Override
	public String historialDeMods() {
		return "Histórico de Mods";
	}

	@Override
	public String archivo0() {
		return "Arquivo0";
	}

	@Override
	public String archivo1() {
		return "Arquivo1";
	}

	@Override
	public String comparar() {
		return "Comparar";
	}

	@Override
	public String seleccionarDosArchivos() {
		return "Selecionar Dois Arquivos";
	}

	@Override
	public String archivoExito() {
		return "Arquivo de Sucesso";
	}

	@Override
	public String archivoFalla() {
		return "Arquivo com Falha";
	}

	@Override
	public String errorComparandoArchivos() {
		return "Erro ao Comparar Arquivos";
	}

	@Override
	public String comparando() {
		return "Comparando";
	}

	@Override
	public String con() {
		return "Com";
	}

	@Override
	public String descripcionPanelHistoriaMods() {
		return "<html><body style='font-family: sans-serif; font-size: 12px;'>"
				+ "<p><b>Painel de Comparação do Histórico de Mods</b></p>"
				+ "<p>Este painel permite comparar duas listas de mods de sessões distintas. "
				+ "Selecione um arquivo na coluna esquerda e outro na direita para visualizar as alterações entre as versões.</p>"

				+ "<h3>Como usá-lo:</h3>" + "<ol>"
				+ "<li><b>Seleção de arquivos:</b> Clique nos botões de rádio ao lado dos nomes dos arquivos. "
				+ "Arquivos terminados em <span style='color: #4CAF50; font-weight: bold;'>.sucesso</span> indicam sessões bem-sucedidas, "
				+ "enquanto os com <span style='color: #F44336; font-weight: bold;'>.falha</span> representam erros ou falhas.</li>"

				+ "<li><b>Comparação automática:</b> Ao pressionar o botão 'Comparar', o sistema analisará as duas listas selecionadas "
				+ "e exibirá quais mods foram adicionados (+) ou removidos (-).</li>"

				+ "<li><b>Visualização dos resultados:</b> Os resultados são mostrados em formato HTML com cores codificadas: "
				+ "<ul>" + "<li><span style='color: green;'>+ Mod adicionado</span></li>"
				+ "<li><span style='color: red;'>- Mod removido</span></li>" + "</ul></li>" + "</ol>"

				+ "<h3>Principais características:</h3>" + "<ul>"
				+ "<li>Suporta qualquer combinação de arquivos (sucesso/falha).</li>"
				+ "<li>Mostra diferenças bidirecionais para identificar mudanças exatas.</li>"
				+ "<li>Inclui rolagem para listas longas de mods.</li>"
				+ "<li>Contém imagens explicativas para melhor compreensão visual.</li>" + "</ul>"

				+ "<p>Desenvolvido com <3️ para ajudar no rastreamento de alterações nas suas configurações.</p>"
				+ "</body></html>";
	}

	@Override
	public String tieneErrorIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Você pode estar enfrentando um problema relacionado ao IPv6. " + "Há duas soluções: "
				+ "1) Adicionar o argumento JVM <code>-Djava.net.preferIPv4Stack=true</code> no seu lançador, ou "
				+ "2) Usar o botão 'QuickFix' no CrashDetector para aplicar um patch que ative automaticamente essa configuração."
				+ "</b>";
	}

	@Override
	public String parcheIPv4() {
		return "Correção IPv4/6";
	}

	@Override
	public String carpetaHMCL() {
		return "Pasta HMCL (Somente para HelloMineCraftLauncher)";
	}

	@Override
	public String descripcionCurseforge() {
		return "Nota: Nenhum registro é gerado se a opção \"Pular Launcher\" estiver ativada em Configurações > Minecraft";
	}

	@Override
	public String descripcionPrism() {
		return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derivados: Clique com o botão direito na instância e selecione \"Editar Instância\". Na janela que abrir, procure pela seção \"Registros do Minecraft\" ou algo semelhante.<br>"
				+ "Esses registros contêm a saída padrão (STDOUT), essencial para diagnosticar erros.";
	}

	@Override
	public String descripcionHMCL() {
		return "HMCL (HelloMinecraftLauncher): Você deve selecionar a pasta onde o HMCL está instalado e escolher a pasta \".hmcl\". Os registros do HMCL são salvos nesse local e contêm informações importantes sobre erros.<br>";
	}

	@Override
	public String descripcionFenix() {
		return "LauncherFenix: O lançador tem uma aba de desenvolvedor com registros detalhados. Você pode encontrar essa aba no menu de configurações do lançador.";
	}

	@Override
	public String descripcionATLauncher() {
		return "ATLauncher: Existe uma janela pop-up com os registros. Possui botões para copiar e enviar os registros. Os registros são gerados automaticamente ao iniciar o jogo e contêm informações críticas para diagnóstico.";
	}

	@Override
	public String descripcionGDLauncher() {
		return "GDLauncher: Clique com o botão direito na instância e selecione \"Configurações\". Em seguida, vá à seção de Registros para visualizar a informação importante da saída padrão (STDOUT).";
	}

	@Override
	public String descripcionLinksMarkdown() {
		return "Links em Markdown: Cole aqui qualquer link que contenha registros em formato Markdown. O sistema tentará extrair automaticamente os links dos registros (latest.log, launcher_log.txt, debug.log, etc.) e analisá-los.";
	}

	@Override
	public String noRegistroLauncherTitulo() {
		return "Registro do Launcher Não Encontrado";
	}

	@Override
	public String imagenNoEncontrada() {
		return "Imagem não encontrada";
	}

	@Override
	public String noRegistroDeLauncher() {
		return "GENÉRICO: Selecione o tipo de launcher que você está usando. Os registros do launcher (launcher_log.txt, stdout, etc.) contêm informações vitais sobre erros que não aparecem no latest.log. O CrashDetector não consegue ler os registros do seu launcher — talvez ele não gere um arquivo, então você precisa colar os registros manualmente.<br>"
				+ "Para mais informações, veja <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">esta questão</a>. Esses registros contêm a saída padrão (STDOUT), essencial para diagnosticar muitos tipos de erro.";
	}

	@Override
	public String faltar_de_clases_create() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Classes ausentes do Create detectadas. O Create mudou muito — muitas classes foram removidas. Especialmente desde o Create 6 (fevereiro de 2025), addons para versões antigas do Create não funcionam mais. O QuickFix não tem solução para isso. Atualize seus addons do Create, remova os obsoletos ou use a versão correta do Create para os addons desejados.</b>";
	}

	@Override
	public String faltar_de_clases_epicfight() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Classes ausentes do EpicFight detectadas. O EpicFight mudou muito — muitas classes foram removidas. O QuickFix não tem solução para isso. Atualize seus addons do EpicFight, remova os obsoletos ou use a versão correta do EpicFight para os addons desejados.</b>";
	}

	@Override
	public String openJ9NoSoportado() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você está usando o OpenJ9, que não é suportado por este aplicativo. Muitos programas, incluindo este, não suportam o OpenJ9 devido a diferenças na implementação da JVM. O QuickFix não pode resolver esse problema automaticamente. Você precisa instalar um JDK compatível, como Oracle JDK, OpenJDK Hotspot ou outras alternativas recomendadas.</b>";
	}

	@Override
	public String necesitasJDK11() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Esta versão do aplicativo requer Java 11 (JDK 11) para funcionar corretamente. Você está usando uma versão antiga do Java que não é compatível. O QuickFix não pode atualizar seu Java automaticamente. Você precisa baixar e instalar o JDK 11 ou uma versão superior compatível pelos links fornecidos na solução.</b>";
	}

	@Override
	public String memoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Você alocou memória em excesso, fazendo com que o sistema não tenha recursos suficientes. Isso ocorre quando a quantidade de RAM definida é maior que a disponível ou ao usar uma JVM de 32 bits, que não lida bem com grandes alocações.</b>";
	}

	@Override
	public String recomendacionMemoriaExcesiva() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Para resolver, reduza a memória alocada ao app. O máximo recomendado depende do seu sistema, mas geralmente não deve ultrapassar 70-80% da RAM total. Em JVMs de 32 bits, o limite é de cerca de 2-3 GB, independentemente da RAM física.</b>";
	}

	@Override
	public String disminuirMemoriaHeap() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para reduzir a memória heap, abra as configurações do seu launcher e encontre a opção de memória RAM. Reduza o valor máximo (Xmx) para um valor adequado. Exemplo: com 8 GB de RAM, use 3-4 GB; com 16 GB, use 6-8 GB. Deixe memória suficiente para o sistema operacional e outros programas.</b>";
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
		return "<b style='color:#" + config.obtenerColorError() + "'>Unsatisfied Link Error: Failed to load library "
				+ nombreBiblioteca + ". Possible solutions:<br/><br/>"
				+ "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Add the JAR file containing the shared library to the classpath.<br/><br/>"
				+ "This error occurs when Minecraft cannot locate essential files for execution. "
				+ "It is usually caused by an incomplete Minecraft installation or system permission issues.</b>";
	}

	@Override
	public String nombre_de_error_enlace_insatisfecho() {
		return "Unsatisfied Link Error";
	}

	@Override
	public String solucion_para_error_enlace_insatisfecho() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Failed to load a library. Possible solutions:<br/><br/>"
				+ "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>"
				+ "b) Add the JAR file containing the shared library to the classpath.<br/><br/>"
				+ "These technical solutions are intended for advanced users. Most users should try "
				+ "reinstalling Minecraft before modifying these parameters.</b>";
	}

	@Override
	public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Conflito de ID: O ID <strong>" + id
				+ "</strong> já está em uso pelo <strong>" + modOrigen + "</strong> ao tentar adicionar <strong>"
				+ modDestino
				+ "</strong>. Isso ocorre quando dois mods tentam usar o mesmo ID para elementos diferentes.</b>";
	}

	@Override
	public String conflicto_id_maximo() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>O limite máximo de IDs permitidos foi excedido. Isso acontece quando mods tentam registrar blocos ou itens com IDs fora do intervalo suportado pela sua versão do Minecraft.</b>";
	}

	@Override
	public String nombre_de_conflicto_ids() {
		return "Conflito de ID";
	}

	@Override
	public String solucion_maximo_rango() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Para resolver isso no Minecraft 1.12.2, instale <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Para 1.7.10, use <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
	}

	@Override
	public String solucion_colision_id() {
		return "<b style='color:#" + config.obtenerColorInfo()
				+ "'>Use ferramentas como <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> ou <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> para resolver conflitos de ID.</b>";
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
		return "Usar IdFix Minus ou IdFix";
	}

	@Override
	public String usar_minecraft_id_resolver() {
		return "Usar Minecraft-ID-Resolver";
	}

	@Override
	public String ver_documentacion_jp() {
		return "Ver documentação em japonês";
	}

	@Override
	public String escanearDeMCreator() {
		return "Escanear MCreator";
	}

	@Override
	public String tituloArbolDeMods() {
		return "Árvore de Módulos e Classes";
	}

	@Override
	public String tipoBusqueda() {
		return "Tipo";
	}

	@Override
	public String filtroTodos() {
		return "Todos";
	}

	@Override
	public String filtroPaquetes() {
		return "Pacotes";
	}

	@Override
	public String filtroClases() {
		return "Classes";
	}

	@Override
	public String filtroMetodos() {
		return "Métodos";
	}

	@Override
	public String filtroCampos() {
		return "Campos";
	}

	@Override
	public String filtroReferenciasCampo() {
		return "Referências de Campo";
	}

	@Override
	public String filtroReferenciasMetodo() {
		return "Referências de Método";
	}

	@Override
	public String filtroReferenciasClase() {
		return "Referências de Classe";
	}

	@Override
	public String tipBuscar() {
		return "Digite aqui para pesquisar na árvore de mods";
	}

	@Override
	public String botonBuscar() {
		return "Pesquisar";
	}

	@Override
	public String botonResetearArbol() {
		return "Redefinir Árvore";
	}

	@Override
	public String modsCargados() {
		return "Mods Carregados";
	}

	@Override
	public String clases() {
		return "Classes";
	}

	@Override
	public String metodos() {
		return "Métodos";
	}

	@Override
	public String campos() {
		return "Campos";
	}

	@Override
	public String referencias() {
		return "Referências";
	}

	@Override
	public String resultadosBusqueda() {
		return "Resultados da Pesquisa";
	}

	@Override
	public String buscarReferencias() {
		return "Buscar Referências";
	}

	@Override
	public String referenciasMod() {
		return "Referências do Mod";
	}

	@Override
	public String referenciasClase() {
		return "Referências da Classe";
	}

	@Override
	public String referenciasMetodo() {
		return "Referências do Método";
	}

	@Override
	public String referenciasCampo() {
		return "Referências do Campo";
	}

	@Override
	public String noSeEncontraronReferencias() {
		return "Nenhuma referência encontrada";
	}

	@Override
	public String detalleMod() {
		return "Detalhes do Mod:";
	}

	@Override
	public String ubicacion() {
		return "Localização";
	}

	@Override
	public String nombres() {
		return "Nomes";
	}

	@Override
	public String modulo() {
		return "Módulo";
	}

	@Override
	public String detalleClase() {
		return "Detalhes da Classe:";
	}

	@Override
	public String detalleMetodo() {
		return "Detalhes do Método:";
	}

	@Override
	public String detalleCampo() {
		return "Detalhes do Campo:";
	}

	@Override
	public String clase() {
		return "Classe";
	}

	@Override
	public String tipo() {
		return "Tipo";
	}

	@Override
	public String referenciasAMetodos() {
		return "Referências para Métodos:";
	}

	@Override
	public String referenciasACampos() {
		return "Referências para Campos:";
	}

	@Override
	public String arbolDeMods() {
		return "Árvore de Mods";
	}

	@Override
	public String metodo() {
		return "Método";
	}

	@Override
	public String campo() {
		return "Campo";
	}

	@Override
	public String descompilar() {
		return "Descompilar";
	}

	@Override
	public String exportar() {
		return "Exportar";
	}

	@Override
	public String importar() {
		return "Importar";
	}

	@Override
	public String errorImportar() {
		return "Erro ao Importar";
	}

	@Override
	public String estructuraImportada() {
		return "Estrutura Importada";
	}

	@Override
	public String estructuraExportada() {
		return "Estrutura Exportada";
	}

	@Override
	public String errorExportar() {
		return "Erro ao Exportar";
	}

	@Override
	public String exportando() {
		return "Exportando";
	}

	@Override
	public String exportacionTardara() {
		return "A exportação pode demorar";
	}

	@Override
	public String porFavorEspere() {
		return "Aguarde";
	}

	@Override
	public String noTienesVLCBin() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Você não possui os binários do VLC. O WaterMedia requer os binários do VLC. Instale manualmente em https://www.videolan.org/vlc/.  </b>";
	}

	@Override
	public String descargar_vlc() {
		return "Baixar VLC";
	}

	@Override
	public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: O nome do módulo '" + nombreModulo
				+ "' contém caracteres inválidos. A parte '" + parteInvalida
				+ "' não é um identificador Java válido. Isso ocorre quando um mod usa palavras reservadas do Java (como 'true', 'class') ou caracteres não permitidos em seu nome.</b>";
	}

	@Override
	public String nombre_de_error_caracteres_invalidos() {
		return "Caracteres Inválidos no Nome do Mod";
	}

	@Override
	public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
		return "O nome do mod '" + nombreModulo + "' é inválido porque contém '" + parteInvalida
				+ "', que é uma palavra reservada do Java ou um caractere inválido. "
				+ "Procure nos logs qual mod corresponde a esse nome (geralmente o nome do arquivo JAR)";
	}

	@Override
	public String paso2_caracteres_invalidos(String nombreModulo) {
		return "Acesse a pasta do mod e edite o arquivo <b>mods.toml</b> dentro da pasta <b>/META-INF/</b>. "
				+ "Altere o valor de <b>modId</b> para usar apenas letras, números e sublinhados, sem palavras reservadas do Java";
	}

	@Override
	public String paso3_caracteres_invalidos() {
		return "Exemplo de nome válido: 'truemod_shot_enchantment' em vez de 'true.shot.enchantment'. "
				+ "Lembre-se que nomes de mods não podem conter pontos, hífens ou palavras reservadas do Java como 'true', 'false' ou 'class'";
	}

	@Override
	public String errorDependenciaModFaltante(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico com o mod: '" + nombreJar
				+ "'. Falta o campo obrigatório 'mandatory' em suas dependências. Isso ocorre quando o arquivo mods.toml não especifica se a dependência é obrigatória.</b>";
	}

	@Override
	public String nombre_de_error_dependencia_mod_faltante() {
		return "Dependência de Mod com Campo 'mandatory' Ausente";
	}

	@Override
	public String paso1_dependencia_mod_faltante(String nombreJar) {
		return "O mod problemático é: <b>" + nombreJar
				+ "</b>. Este arquivo tem um erro na configuração de dependências";
	}

	@Override
	public String paso2_dependencia_mod_faltante(String nombreJar) {
		return "Abra o arquivo <b>mods.toml</b> dentro da pasta <b>/META-INF/</b> do mod <b>" + nombreJar + "</b>";
	}

	@Override
	public String paso3_dependencia_mod_faltante() {
		return "Na seção de dependências, certifique-se de que cada entrada inclua <b>mandatory=true</b> ou <b>mandatory=false</b> (ex: modId=\"forge\", mandatory=true, versionRange=\"[1.21.8,)\" )";
	}

	@Override
	public String errorAccessTransformerInvalido(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico com o mod: '" + nombreJar
				+ "'. Configuração inválida de access transformer. Isso ocorre quando o arquivo de configuração tem sintaxe incorreta ou referências a classes/métodos inexistentes.</b>";
	}

	@Override
	public String nombre_de_error_access_transformer_invalido() {
		return "Access Transformer Inválido";
	}

	@Override
	public String paso1_access_transformer_invalido(String nombreJar) {
		return "O mod problemático é: <b>" + nombreJar
				+ "</b>. Este mod contém uma configuração inválida de access transformer";
	}

	@Override
	public String paso2_access_transformer_invalido(String nombreJar) {
		return "Abra o arquivo <b>accessTransformer.cfg</b> dentro do mod <b>" + nombreJar
				+ "</b> (normalmente na pasta raiz do arquivo JAR)";
	}

	@Override
	public String paso3_access_transformer_invalido() {
		return "Corrija a sintaxe do access transformer. As linhas devem seguir o formato: <b>access class.method</b> (ex: public net.minecraft.world.entity.Entity.func_200560_a). Remova linhas com referências a classes ou métodos que não existam na sua versão do Minecraft";
	}

	@Override
	public String errorDiscrepanciaModID(String nombreMod) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erro crítico: Discrepância entre o ID do mod na anotação @Mod e o arquivo mods.toml. O mod '"
				+ nombreMod + "' não pode ser carregado porque os IDs não coincidem.</b>";
	}

	@Override
	public String nombre_de_error_discrepancia_mod_id() {
		return "Discrepância entre @Mod e mods.toml";
	}

	@Override
	public String paso1_discrepancia_mod_id(String nombreMod) {
		return "O mod em desenvolvimento '" + nombreMod
				+ "' tem uma discrepância entre o ID na anotação <b>@Mod</b> e o valor em <b>mods.toml</b>";
	}

	@Override
	public String paso2_discrepancia_mod_id() {
		return "Verifique se o ID na sua classe principal coincide com o valor <b>modId</b> no arquivo <b>/META-INF/mods.toml</b>. Exemplo: <b>@Mod(\"meumod\")</b> deve coincidir com <b>modId=\"meumod\"</b>";
	}

	@Override
	public String paso3_discrepancia_mod_id() {
		return "Se estiver usando Gradle, execute <b>clean</b> após alterações para garantir que os recursos sejam atualizados corretamente. Às vezes, arquivos antigos permanecem na pasta build";
	}

	@Override
	public String errorModEnPlataformaIncorrecta(String nombreClase, String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "cliente" : "servidor";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servidor" : "cliente";

		return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Tentando carregar a classe '"
				+ nombreClase + "' no ambiente de " + plataforma + ", mas ela foi feita para " + plataformaOpuesta
				+ ". <b>Use a função 'Árvore de Mods' na barra lateral para descobrir qual mod está tentando carregar esta classe</b>. "
				+ "Mods são feitos especificamente para uma plataforma e não funcionam na outra.</b>";
	}

	@Override
	public String nombre_de_error_mod_plataforma_incorrecta() {
		return "Mod em Plataforma Incorreta";
	}

	@Override
	public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
		return "Na aba <b>Árvore de Mods</b> (à direita), busque referências à classe <b>" + nombreClase
				+ "</b> para identificar qual mod está causando o problema";
	}

	@Override
	public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
		String plataforma = entornoInvalido.equals("CLIENT") ? "cliente" : "servidor";
		String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servidor" : "cliente";

		return "O mod identificado é um mod de <b>" + plataformaOpuesta
				+ "</b> e não deve ser usado em seu ambiente de " + plataforma + ".";
	}

	@Override
	public String paso3_mod_plataforma_incorrecta() {
		return "Remova o mod problemático da pasta <b>mods</b>. Se precisar de funcionalidade semelhante nesta plataforma, "
				+ "procure um mod alternativo feito especificamente para <b>cliente</b> ou <b>servidor</b>, conforme necessário";
	}

	@Override
	public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erro crítico: Falta metadados para o modid '").append(modIdFaltante).append("'. ");

		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			mensaje.append("Os seguintes mods podem estar causando o problema: <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				mensaje.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsPotenciales.size() > 3)
				mensaje.append(", e outros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Isso ocorre quando um mod depende de outro que não está instalado ou tem um arquivo mods.toml incorreto.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_metadata_mods_toml_faltante() {
		return "Metadados mods.toml Ausentes";
	}

	@Override
	public String paso1_metadata_mods_toml_faltante(String modIdFaltante, List<String> modsPotenciales) {
		if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
			StringBuilder paso = new StringBuilder("Os seguintes mods dependem de '").append(modIdFaltante)
					.append("': <b>");
			for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
				paso.append(modsPotenciales.get(i));
				if (i < modsPotenciales.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsPotenciales.size() > 3)
				paso.append(", e outros...");
			paso.append("</b>. Use a função <b>Árvore de Mods</b> para confirmar qual mod está causando o problema");
			return paso.toString();
		}
		return "Um mod está tentando depender de '" + modIdFaltante
				+ "', mas este mod não está instalado. Use a função <b>Árvore de Mods</b> para identificar qual mod está causando o problema";
	}

	@Override
	public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Você tem duas opções:<br/>" + "1. <b>Instale o mod ausente</b>: Encontre e instale o mod com ID '"
				+ modIdFaltante + "'<br/>"
				+ "2. <b>Remova o mod dependente</b>: Se não precisar da funcionalidade, remova o mod que depende de '"
				+ modIdFaltante + "'";
	}

	@Override
	public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
		return "Se o mod '" + modIdFaltante + "' for uma biblioteca (como 'forge', 'minecraft', 'curios'), "
				+ "certifique-se de ter as versões corretas do Minecraft e do Forge instaladas. "
				+ "Se for um mod comum, verifique na página de download os pré-requisitos necessários";
	}

	@Override
	public String errorSistemaSonido() {
		return "<b style='color:#" + config.obtenerColorAdvertencia()
				+ "'>Aviso: Falha ao iniciar o sistema de som. Sons e música foram desativados. Esse erro está comumente associado ao mod SoundPhysicsMod e pode ser causado por conflitos com outras bibliotecas de áudio.</b>";
	}

	@Override
	public String nombre_de_error_sistema_sonido() {
		return "Erro no Sistema de Som";
	}

	@Override
	public String paso1_sistema_sonido() {
		return "O erro está comumente relacionado ao <b>SoundPhysicsMod</b>. Verifique se você tem a versão mais recente compatível com sua versão do Minecraft";
	}

	@Override
	public String paso2_sistema_sonido() {
		return "Se você usa outros mods de som (como Sound Filters, Dynamic Surroundings, etc.), tente remover temporariamente o SoundPhysicsMod para ver se resolve o conflito";
	}

	@Override
	public String paso3_sistema_sonido() {
		return "Verifique a pasta <b>logs</b> para mensagens adicionais relacionadas a LWJGL ou OpenAL, que podem indicar problemas com as bibliotecas de som subjacentes";
	}

	@Override
	public String errorSinListenersEnClase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erro crítico: A classe '").append(nombreClase)
				.append("' está registrada como ouvinte de eventos, mas não possui métodos válidos. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Esta classe está presente nos seguintes mods: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", e outros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Isso ocorre quando uma classe é registrada para escutar eventos sem ter métodos anotados com @SubscribeEvent.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_sin_listeners_en_clase() {
		return "Classe Registrada sem Ouvintes de Eventos";
	}

	@Override
	public String paso1_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder("A classe problemática está nestes mods: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", e outros...");
			paso.append("</b>. Esses mods estão tentando registrar eventos sem métodos válidos");
			return paso.toString();
		}
		return "A classe <b>" + nombreClase
				+ "</b> foi registrada para escutar eventos, mas não possui métodos com a anotação <b>@SubscribeEvent</b>. Use a função <b>Árvore de Mods</b> para identificar qual mod contém esta classe";
	}

	@Override
	public String paso2_sin_listeners_en_clase(String nombreClase) {
		return "No código-fonte, verifique que a classe <b>" + nombreClase + "</b> contenha pelo menos um método com: "
				+ "<b>@SubscribeEvent public void nomeMetodo(EventoEspecifico evento) { ... }</b>. "
				+ "Se for uma classe interna, certifique-se de que não esteja marcada como estática";
	}

	@Override
	public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
		StringBuilder paso = new StringBuilder();

		if (!modsUbicacion.isEmpty()) {
			paso.append("Para os mods identificados (<b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 1)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 2)
				paso.append(", etc.");
			paso.append("</b>): ");

			if (modsUbicacion.size() == 1) {
				paso.append("contate o desenvolvedor desse mod para corrigir o problema. ");
			} else {
				paso.append("contate os desenvolvedores desses mods para corrigir o problema. ");
			}
		}

		paso.append(
				"Se você for o desenvolvedor, remova o registro dessa classe no EventBus ou adicione métodos @SubscribeEvent válidos");

		return paso.toString();
	}

	@Override
	public String errorUnionFileSystemCorrupto(String nombreArchivo) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erro crítico: Ocorreu uma exceção 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' ao processar o arquivo '"
				+ nombreArchivo
				+ "'. Este erro específico indica que o launcher não baixou ou extraiu corretamente os arquivos do modpack. "
				+ "A mensagem 'zip END header not found' revela que o arquivo JAR está incompleto ou corrompido, o que é extremamente comum em launchers que gerenciam mal o download de arquivos grandes. "
				+ "Este problema afeta principalmente usuários do Twitch/CurseForge, Technic Launcher e especialmente usuários do Luna Pixel, pois esses launchers muitas vezes falham em verificar a integridade completa dos arquivos baixados. "
				+ "Usuários do Luna Pixel deveriam considerar o ATLauncher como uma alternativa mais estável, pois ele lida melhor com a integridade dos arquivos e evita esse erro específico. "
				+ "O sistema não pode carregar os mods porque o formato ZIP está danificado, impedindo que o Forge leia os recursos necessários para iniciar o jogo.</b>";
	}

	@Override
	public String nombre_de_error_union_filesystem_corrupto() {
		return "Erro UnionFileSystem - Arquivo Corrompido";
	}

	@Override
	public String paso1_union_filesystem_corrupto(String nombreArchivo) {
		return "Reinstale completamente o modpack do zero";
	}

	@Override
	public String paso2_union_filesystem_corrupto() {
		return "Se você usa Luna Pixel, mude para o ATLauncher";
	}

	@Override
	public String paso3_union_filesystem_corrupto() {
		return "Verifique sua conexão com a internet e espaço em disco antes de reinstalar";
	}

	@Override
	public String habilitarProxySysOutSysErrMensaje() {
		return "Habilitar ProxySysOutSysErr?\n\n"
				+ "Esta opção dá ao CrashDetector acesso ao System.out e System.err quando o launcher não fornece registros.\n\n"
				+ "Deve ser habilitada apenas quando você não conseguir colar um log manualmente.\n\n"
				+ "Aviso: Isso pode interferir com alguns mods ou launchers.\n\n"
				+ "É necessário reiniciar o jogo/aplicativo para que as alterações tenham efeito.";
	}

	@Override
	public String confirmacionTitulo() {
		return "Confirmação";
	}

	@Override
	public String proxyHabilitadoMensaje() {
		return "ProxySysOutSysErr habilitado com sucesso.\n\n"
				+ "É necessário reiniciar o CrashDetector para que as alterações tenham efeito.";
	}

	@Override
	public String informacionTitulo() {
		return "Informação";
	}

	@Override
	public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError,
			boolean connectorPresente) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");

		if (azureLibError && geckoLibError) {
			mensaje.append("Erro crítico: AzureLib e GeckoLib foram inicializados muito cedo! ");
		} else if (azureLibError) {
			mensaje.append("Erro crítico: AzureLib foi inicializado muito cedo! ");
		} else if (geckoLibError) {
			mensaje.append("Erro crítico: GeckoLib foi inicializado muito cedo! ");
		}

		mensaje.append("Esse erro ocorre ao tentar usar mods Fabric com versões não-Fabric dessas bibliotecas. ");

		if (connectorPresente) {
			mensaje.append(
					"Um mod de compatibilidade (Sinytra Connector ou specialcompatibilityoperation) foi detectado, indicando que você está tentando usar mods Fabric em um ambiente Forge ou FeatureCreep. ");
			mensaje.append(
					"Verifique o erro 'Erro de inicialização do FabricMC' nos logs para identificar qual mod específico está causando o problema. ");
		}

		mensaje.append(
				"AzureLib e GeckoLib são bibliotecas essenciais para mods de animação, mas devem corresponder à plataforma correta (Fabric ou Forge). ");
		mensaje.append(
				"O jogo não pode carregar corretamente os mods de animação devido a esse conflito de inicialização.");

		mensaje.append("</b>");
		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_azure_geckolib_inicializo_pronto() {
		return "Biblioteca Inicializada Muito Cedo";
	}

	@Override
	public String paso1_azure_geckolib_inicializo_pronto() {
		return "Verifique o erro 'Erro de inicialização do FabricMC' nos logs para identificar o mod problemático";
	}

	@Override
	public String paso2_azure_geckolib_inicializo_pronto() {
		return "Verifique se está usando a versão correta do AzureLib/GeckoLib para sua plataforma (Forge ou Fabric)";
	}

	@Override
	public String errorCompatibilidadC2ME() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erro crítico: Incompatibilidade entre C2ME e mods de conexão. "
				+ "Este erro ocorre porque o C2ME tenta acessar componentes internos do Java que são restritos em ambientes com "
				+ "Sinytra Connector ou specialcompatibilityoperation ou outros mods de compatibilidade Fabric/Forge. "
				+ "<b>O C2ME não é compatível com esses ambientes, mas <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> é a alternativa recomendada</b> que funciona corretamente "
				+ "com mods de conexão. O jogo não pode iniciar devido a conflitos de permissão de segurança do Java.</b>";
	}

	@Override
	public String nombre_de_error_compatibilidad_c2me() {
		return "Incompatibilidade do C2ME com Mods de Conexão";
	}

	@Override
	public String paso1_compatibilidad_c2me() {
		return "Remova o C2ME da pasta de mods";
	}

	@Override
	public String paso2_compatibilidad_c2me() {
		return "Baixe e instale <a href='https://www.mcmod.cn/class/15818.html'>C3ME</a> no lugar (compatível com Sinytra Connector)";
	}

	@Override
	public String paso3_compatibilidad_c2me() {
		return "Verifique se todos os mods de conexão (como Sinytra Connector) estão atualizados para a versão mais recente";
	}

	@Override
	public String errorJEIPluginFallido(String nombreClase, String modId, String pluginId) {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Erro crítico: Falha ao carregar o plugin JEI para o mod '" + modId + "'. A classe '" + nombreClase
				+ "' (ID do plugin: '" + pluginId
				+ "') gerou um erro que está fazendo o jogo crashar durante a inicialização. "
				+ "Esse problema ocorre quando um mod possui uma integração JEI incompatível ou corrompida que interrompe a inicialização do jogo.</b>";
	}

	@Override
	public String nombre_de_error_jei_plugin_fallido() {
		return "Plugin JEI Falhou - Causa Crash";
	}

	@Override
	public String paso1_jei_plugin_fallido(String modId) {
		return "O mod <b>" + modId
				+ "</b> contém um plugin JEI corrompido que está causando o crash. Use a função <b>Árvore de Mods</b> para confirmar qual mod está gerando o problema";
	}

	@Override
	public String paso2_jei_plugin_fallido(String modId) {
		return "Remova temporariamente o mod <b>" + modId
				+ "</b> da pasta de mods para verificar se isso resolve o crash";
	}

	@Override
	public String paso3_jei_plugin_fallido(String modId) {
		return "Procure por atualizações do mod <b>" + modId
				+ "</b> ou entre em contato com seu desenvolvedor relatando o problema com o plugin JEI. "
				+ "Enquanto isso, o mod deve ser removido para conseguir iniciar o jogo";
	}

	@Override
	public String tituloLectador() {
		return "Leitor de Logs - Crash Detector";
	}

	@Override
	public String obtenerTituloLeyenda() {
		return "LEGENDA DE CORES";
	}

	@Override
	public String obtenerErrorEnLeyenda() {
		return "Erros críticos";
	}

	@Override
	public String obtenerStacktraceEnLeyenda() {
		return "Rastros de pilha";
	}

	@Override
	public String obtenerTituloError() {
		return "Erro";
	}

	@Override
	public String obtenerErrorAlProcesarLinea() {
		return "Ocorreu um erro ao processar a linha selecionada";
	}

	@Override
	public String obtenerNombreError() {
		return "NOME DO ERRO";
	}

	@Override
	public String obtenerDescripcionError() {
		return "DESCRIÇÃO DETALHADA";
	}

	@Override
	public String obtenerSeleccionarConsola() {
		return "SELECIONAR LOG";
	}

	@Override
	public String obtenerNombreErrorPorDefecto() {
		return "Erro não identificado";
	}

	@Override
	public String obtenerDescripcionErrorPorDefecto() {
		return "Um erro crítico foi detectado nos logs. "
				+ "Verifique o rastro de pilha para identificar a causa raiz.";
	}

	@Override
	public String obtenerErrorLecturaArchivo() {
		return "Não foi possível ler o arquivo de log. " + "Verifique se o arquivo existe e tem permissões de leitura.";
	}

	@Override
	public String obtenerEtiquetaBotonLectador() {
		return "Analisador de Logs";
	}

	@Override
	public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
		StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		mensaje.append("Erro crítico: Falha ao registrar assinantes automáticos para o mod '").append(modId)
				.append("'. ");

		mensaje.append("Classe problemática: <b>").append(nombreClase).append("</b>. ");

		if (!modsUbicacion.isEmpty()) {
			mensaje.append("Esta classe está localizada em: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				mensaje.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					mensaje.append(", ");
			}
			if (modsUbicacion.size() > 3)
				mensaje.append(", e outros...");
			mensaje.append("</b>. ");
		}

		mensaje.append(
				"Este erro ocorre quando um mod tenta registrar uma classe como assinante de eventos automaticamente, mas a classe não pode ser carregada. ");
		mensaje.append(
				"<b>Verifique outros erros no log, pois a causa real pode estar em um carregamento anterior falho</b>.");
		mensaje.append("</b>");

		return mensaje.toString();
	}

	@Override
	public String nombre_de_error_registro_suscriptores_automaticos() {
		return "Falha no Registro de Assinantes Automáticos";
	}

	@Override
	public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
		return "O mod <b>" + modId + "</b> está tentando registrar a classe <b>" + nombreClase
				+ "</b> como assinante automático, mas falhou. Verifique se esta classe existe e é acessível";
	}

	@Override
	public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase,
			List<String> modsUbicacion) {
		if (!modsUbicacion.isEmpty()) {
			StringBuilder paso = new StringBuilder(
					"A classe problemática <b>" + nombreClase + "</b> está nestes arquivos: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				paso.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					paso.append(", ");
			}
			if (modsUbicacion.size() > 3)
				paso.append(", e outros...");
			paso.append("</b>. ");
			paso.append("Use a função <b>Árvore de Mods</b> para confirmar qual arquivo contém a classe problemática");
			return paso.toString();
		}
		return "A classe <b>" + nombreClase + "</b> não foi encontrada em nenhum arquivo de mod. Verifique se o mod <b>"
				+ modId
				+ "</b> está instalado corretamente. Use a função <b>Árvore de Mods</b> para ajudar a identificar o problema";
	}

	@Override
	public String paso3_registro_suscriptores_automaticos(String modId) {
		return "Atualize o mod <b>" + modId
				+ "</b> para a última versão compatível com sua versão do Minecraft e Forge. "
				+ "Se o problema persistir, contate o desenvolvedor do mod relatando o erro com a classe problemática";
	}

	@Override
	public String paso4_registro_suscriptores_automaticos() {
		return "Verifique <b>outros erros no log</b> antes desta mensagem, pois o problema real pode estar em um carregamento anterior falhado. "
				+ "Às vezes um erro anterior impede o carregamento das classes necessárias para o registro de eventos";
	}

	@Override
	public String limpiado() {
		// TODO Auto-generated method stub
		return "Limpo";
	}

	@Override
	public String original() {
		// TODO Auto-generated method stub
		return "Original";
	}

	@Override
	public String verEnConsola() {
		// TODO Auto-generated method stub
		return "Ver no Log";
	}

	@Override
	public String advertencia() {
		// TODO Auto-generated method stub
		return "Aviso";
	}

	@Override
	public String fatal() {
		// TODO Auto-generated method stub
		return "Fatal";
	}

	@Override
	public String noRegistroDeBattly() {
		// TODO Auto-generated method stub
		return "O BattlyLauncher não possui um log ou console disponível para copiar. Você pode usar o ProxySysOutSysErr para interceptar STDOUT e STDERR reiniciando o jogo, mas o ProxySysOutSysErr pode entrar em conflito com mods que modificam STDOUT ou STDERR";
	}

	@Override
	public String noRegistroDeNightWorld() {
		// TODO Auto-generated method stub
		return "Você precisa habilitar o modo de depuração nas configurações do NightWorld para obter um log do launcher. Isso é muito importante, especialmente porque inclui STDOUT e STDERR";
	}

	@Override
	public String noRegistroDeMCServidor() {
		// TODO Auto-generated method stub
		return "Você precisa salvar ou colar o conteúdo do Terminal do seu servidor, pois ele contém informações não presentes em outros logs, incluindo STDOUT, STDERR e outros erros. Por favor, cole o conteúdo da sua última sessão. Para o futuro, você pode salvar a saída do terminal em um arquivo chamado cd_launcherlog. Para evitar ter que colar, adicione >> cd_launcherlog após o comando de inicialização, conforme mostrado na imagem. Observe que isso impedirá a exibição no terminal; a saída só aparecerá nesse arquivo a partir de então.";
	}

	@Override
	public String errorLexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
		sb.append("Erro crítico: transformador do LexForge detectado em um ambiente NeoForge. ");
		sb.append("</b>");

		sb.append("Classe envolvida: <b>").append(claseReceptora).append("</b>. ");
		sb.append("A interface afetada é <b>").append(interfazObjetivo).append("</b> ");
		sb.append("e o método ausente é <b>").append(firmaMetodoFaltante).append("</b>. ");

		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("A classe foi encontrada em: <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", e outros...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Nenhum JAR contendo essa classe foi localizado; pode estar ofuscado ou embutido como jar-em-jar. ");
		}

		sb.append(
				"Essa falha ocorre quando um transformador/serviço do ModLauncher compilado para MinecraftForge/LexForge ");
		sb.append("é carregado no NeoForge com uma versão incompatível da API do ModLauncher. ");
		sb.append("Atualize ou substitua o componente para NeoForge.");
		return sb.toString();
	}

	@Override
	public String nombre_de_LexForgeMLTransformerEnNeoForge() {
		return "Transformador do LexForge usado no NeoForge";
	}

	@Override
	public String paso1_LexForgeMLTransformerEnNeoForge(String claseReceptora, String interfazObjetivo,
			String firmaMetodoFaltante) {
		return "Identifique o transformador incompatível: <b>" + claseReceptora + "</b>. " + "A API esperada é <b>"
				+ interfazObjetivo + "</b> e o método ausente é <b>" + firmaMetodoFaltante + "</b>. "
				+ "Verifique se o mod registra essa classe em <b>META-INF/services</b> e remova ou desative-a no NeoForge.";
	}

	@Override
	public String paso2_LexForgeMLTransformerEnNeoForge(List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder();
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Localização do(s) mod(s) envolvido(s): <b>");
			for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
				sb.append(modsUbicacion.get(i));
				if (i < modsUbicacion.size() - 1 && i < 2)
					sb.append(", ");
			}
			if (modsUbicacion.size() > 3)
				sb.append(", e outros...");
			sb.append("</b>. ");
		} else {
			sb.append(
					"Nenhum JAR contendo a classe foi encontrado. Verifique dependências embutidas (jar-em-jar) e ofuscadas. ");
		}
		sb.append("Remova temporariamente esses JARs ou use versões compatíveis com NeoForge para confirmar a origem.");
		return sb.toString();
	}

	@Override
	public String paso3_LexForgeMLTransformerEnNeoForge() {
		return "Substitua o componente por uma versão específica para NeoForge ou recompile-o contra a "
				+ "versão do ModLauncher usada pelo NeoForge. Evite binários antigos do LexForge/MinecraftForge.";
	}

	@Override
	public String paso4_LexForgeMLTransformerEnNeoForge() {
		return "Limpe sua pasta de mods e remova duplicatas jar-em-jar. Limpe o cache do launcher se necessário "
				+ "e reinicie para garantir que nenhum transformador do LexForge esteja sendo carregado.";
	}

	@Override
	public String errorWaterMediaXenonIncompatible(String modNombre, String modId, List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("WaterMedia não pode iniciar: Xenon ");
		sb.append("(").append(modId).append(") ");
		if (modNombre != null && !modNombre.isEmpty())
			sb.append("[").append(modNombre).append("] ");
		sb.append("é incompatível.</b> ");
		sb.append("Remova o Xenon e use Embeddium ou Sodium. ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Detectado em: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", e outros...");
			sb.append("</b>.");
		}
		return sb.toString();
	}

	@Override
	public String nombreDeWaterMediaXenonIncompatible() {
		return "WaterMedia incompatível com Xenon";
	}

	@Override
	public String paso1WaterMediaXenonIncompatible(String modNombre, String modId) {
		String label = "Xenon (" + modId + ")";
		if (modNombre != null && !modNombre.isEmpty())
			label += " [" + modNombre + "]";
		return "Detectado " + label + " incompatível com WaterMedia. Remova-o do seu perfil.";
	}

	@Override
	public String paso2WaterMediaXenonIncompatible(List<String> modsUbicacion) {
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			StringBuilder sb = new StringBuilder("Localizações: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", e outros...");
			sb.append("</b>. Exclua esse JAR.");
			return sb.toString();
		}
		return "Nenhum JAR encontrado. Verifique a pasta de mods e remova o Xenon.";
	}

	@Override
	public String paso3WaterMediaXenonIncompatible() {
		return "Instale Embeddium ou Sodium como substituto e reinicie o jogo.";
	}

	@Override
	public String nombreDeTaczDeflaterCerrado() {
		return "Erro ao comprimir (TACZ)";
	}

	@Override
	public String errorTaczDeflaterCerrado(java.util.List<String> modsUbicacion) {
		StringBuilder sb = new StringBuilder("<b>Deflater fechado durante cópia de recursos do TACZ.</b> ");
		if (modsUbicacion != null && !modsUbicacion.isEmpty()) {
			sb.append("Relacionado a: <b>");
			for (int i = 0; i < Math.min(3, modsUbicacion.size()); i++) {
				if (i > 0)
					sb.append(", ");
				sb.append(modsUbicacion.get(i));
			}
			if (modsUbicacion.size() > 3)
				sb.append(", e outros");
			sb.append("</b>. ");
		}
		sb.append(
				"<br/><b>Solução:</b> em <code>tacz/tacz-pre.toml</code>, defina <code>DefaultPackDebug=true</code>. ")
				.append("Se necessário, gere um mapa primeiro e depois ative-o.");
		return sb.toString();
	}

	@Override
	public String pasoTaczDeflaterCerrado() {
		return "Em tacz/tacz-pre.toml, defina DefaultPackDebug=true. Se necessário, gere um mapa primeiro e depois ative-o.";
	}

	@Override
	public String nombreDeFuncionesDeDensidadNoVinculadas() {
		return "Funções de densidade não vinculadas";
	}

	@Override
	public String errorFuncionesDeDensidadNoVinculadas(java.util.List<String> claves) {
		StringBuilder sb = new StringBuilder("<b>Faltam funções de densidade no registro.</b> ");
		if (claves != null && !claves.isEmpty()) {
			sb.append("Faltando: ");
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
				"<br/><b>Solução:</b> instale ou ative o mod/datapack que define essas funções e reinicie. Outra causa comum desse problema é quando você tem o mod necessário, mas ele apresenta um problema ou conflito com outro mod; por exemplo, o Terralith tem muitos problemas e pode causar este erro e outros, incluindo erros com JSON.");
		return sb.toString();
	}

	@Override
	public String pasoFuncionesDeDensidadNoVinculadas() {
		return "Instale ou ative o mod/pacote de dados que fornece essas funções e reinicie o jogo.";
	}

	@Override
	public String errorRailwaysCreate6Alfa(String claveFaltante) {
		// Mensagem curta, em cor de erro, mencionando explicitamente o mod
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Entrada de registro não encontrada: ").append(claveFaltante).append(". ");
		sb.append("Comum com a versão alfa do Steam & Railways para Create 6.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeRailwaysCreate6Alfa() {
		return "Create 6: Steam & Railways (alfa)";
	}

	@Override
	public String pasoRailwaysCreate6Alfa() {
		return "Remova ou substitua a versão alfa do Steam & Railways para Create 6 por uma versão compatível.";
	}

	@Override
	public String errorConflictoMultiworldRendimiento() {
		// Curto, com cor de erro e recomendação direta
		StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Conflito de carregamento: Multiworld junto com Sodium/Embeddium/Rubidium causa ")
				.append("IncompatibleClassChangeError (FabricLoader.getInstance). ")
				.append("Sugestão: remova o Multiworld ou o mod de desempenho, ou use versões compatíveis.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombreDeConflictoMultiworldRendimiento() {
		return "Conflito: Multiworld com mods de desempenho";
	}

	@Override
	public String pasoConflictoMultiworldRendimiento() {
		return "Desinstale o Multiworld ou o Sodium/Embeddium/Rubidium, ou atualize para versões mutuamente compatíveis.";
	}

	@Override
	public String problema_con_graficas_sodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Sodium detectou um driver gráfico incompatível. "
				+ "Atualize o driver da sua GPU para o mínimo exigido ou siga o guia do Sodium." + "</b>";
	}

	@Override
	public String nombreErrorContextoOpenGL() {
		return "Erro de Contexto OpenGL";
	}

	@Override
	public String errorContextoOpenGL() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falha no OpenGL: sem contexto atual ou função não disponível neste contexto. "
				+ "Pode também ser um problema com os drivers de vídeo." + "</b>";
	}

	@Override
	public String paso1ErrorContextoOpenGL() {
		return "Atualize/reinstale os drivers da GPU e reinicie; desative sobreposições e teste sem mods de desempenho.";
	}

	@Override
	public String copiadoAlPortapapeles() {
		return "Link copiado para a área de transferência.";
	}

	@Override
	public String buscarDentroDeComprimidos() {
		return "Pesquisar dentro de arquivos compactados (.zip/.jar/.war/.ear/.fpm/.rar para Java*)";
	}

	@Override
	public String error_resolucion_textura(String recurso, String tamaño) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro de resolução de textura: Não foi possível carregar " + recurso + " - tamanho: " + tamaño
				+ "</b>";
	}

	@Override
	public String nombre_de_error_resolucion_textura() {
		return "Erro de Resolução de Textura";
	}

	@Override
	public String solucion_resolucion_textura() {
		return "Esse erro ocorre quando as texturas são muito grandes ou há muitos pacotes de recursos. "
				+ "Tente usar pacotes de recursos de resolução mais baixa ou remova alguns pacotes. "
				+ "Verifique se você não adicionou texturas personalizadas com resolução acima do limite permitido.";
	}

	@Override
	public String error_modlauncher_path() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro nos serviços do ModLauncher: caminho com caracteres inválidos. "
				+ "Os serviços do ModLauncher não conseguem processar caminhos que contenham caracteres não ASCII ou caracteres especiais. "
				+ "Caracteres problemáticos incluem: ¡, !, مرحبا, Olá, سلام, Привет, 你好, Saluton, こんにちは, 안녕하세요, e especialmente o caractere '\"' quando no final do nome. "
				+ "Componentes comuns de serviço no ModLauncher incluem CrashDetector, "
				+ Config.obtenerInstancia().obtenerNombreCD()
				+ ", FeatureCreep, Vivicraft, Optifine, Sodium, clonos, Iris Shaders/Oculus, MixerLogger, CrashAssistant e Sintrya Connector. "
				+ "Você pode remover todos os serviços, mas outros problemas podem surgir devido ao nome do caminho. "
				+ "Solução: renomeie a instância para usar apenas caracteres ASCII (a-z, A-Z, 0-9), sem espaços ou caracteres especiais.</b>";
	}

	@Override
	public String nombre_error_modlauncher_path() {
		return "Erro de Caminho no ModLauncher";
	}

	@Override
	public String solucion_modlauncher_path() {
		return "Este erro ocorre quando o caminho da instância contém caracteres não ASCII ou caracteres especiais. "
				+ "Os serviços do ModLauncher não conseguem lidar com esses caminhos. "
				+ "Solução: renomeie a instância para usar apenas caracteres ASCII (a-z, A-Z, 0-9) e evite espaços e caracteres especiais. "
				+ "Preste atenção especial ao caractere '\"', que é altamente problemático, especialmente quando no final do nome.";
	}

	@Override
	public String tituloEditorCodice() {
		return "Editor de Codice";
	}

	@Override
	public String nuevo() {
		return "Novo";
	}

	@Override
	public String actualizarSeleccionado() {
		return "Atualizar selecionado";
	}

	@Override
	public String eliminarSeleccionado() {
		return "Excluir selecionado";
	}

	@Override
	public String exportarJSON() {
		return "Exportar JSON...";
	}

	@Override
	public String guardarTodo() {
		return "Salvar tudo";
	}

	@Override
	public String general() {
		return "Geral";
	}

	@Override
	public String id() {
		return "ID";
	}

	@Override
	public String paraBuscar() {
		return "Texto a pesquisar";
	}

	@Override
	public String filtro() {
		return "Filtro (id)";
	}

	@Override
	public String criticalidad() {
		return "Critérios (AVISO/ERRO/FATAL)";
	}

	@Override
	public String prioridad() {
		return "Prioridade";
	}

	@Override
	public String lista() {
		return "Verificações";
	}

	@Override
	public String colIdioma() {
		return "Idioma";
	}

	@Override
	public String colNombre() {
		return "Nome";
	}

	@Override
	public String colResultado() {
		return "Resultado";
	}

	@Override
	public String vistaJson() {
		return "Visualização JSON";
	}

	@Override
	public String idiomas() {
		return "Idiomas (todos obrigatórios)";
	}

	@Override
	public String elegirFiltro() {
		return "Escolher...";
	}

	@Override
	public String eligeFiltroMsg() {
		return "Selecione um filtro";
	}

	@Override
	public String eligeFiltroTitulo() {
		return "Filtros disponíveis";
	}

	@Override
	public String faltanCampos() {
		return "Preencha todos os campos gerais obrigatórios.";
	}

	@Override
	public String critInvalida() {
		return "Critério inválido. Use AVISO, ERRO ou FATAL.";
	}

	@Override
	public String filtroNoExiste() {
		return "O filtro especificado não existe.";
	}

	@Override
	public String faltanIdiomas() {
		return "Preencha nome e resultado para todos os idiomas:";
	}

	@Override
	public String verificacionInvalida() {
		return "Uma verificação é inválida. Verifique os campos.";
	}

	@Override
	public String guardadoOk() {
		return "Salvo com sucesso.";
	}

	@Override
	public String editorCodiceBoton() {
		return "Adicionar razões";
	}

	@Override
	public String descripcionEditorCodice() {
		return "Você pode registrar razões aqui. Você precisa de um ID, uma string sem caracteres especiais, acentos ou espaços. Para filtros, você pode usar \"linha contém\" para procurar uma string em uma linha, \"tudo contém\" se o log tem uma string, \"regex linha\" se uma linha corresponde a uma expressão regular, e \"regex tudo\" (sugerimos usar as versões de linha). Você precisa definir a criticidade: FATAL, ERRO ou ADVERTÊNCIA para as cores. Para todos os idiomas, é necessário escrever um nome e um resultado que aparecerá na tela. Você pode adicionar mais verificações ou remover outras. Salvo ao concluir.";
	}

	@Override
	public String descartarCambios() {
		return "Descartar as alterações não salvas na verificação atual?";
	}

	@Override
	public String confirmacion() {
		return "Confirmação";
	}

	@Override
	public String guardarAntesDeSalir() {
		return "Deseja salvar as alterações antes de sair?";
	}

	@Override
	public String salirSinGuardar() {
		return "Sair sem salvar";
	}

	@Override
	public String errorConfiguracionServicio(String clase, List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorError()).append("'>");
		sb.append("Erro crítico: Falha ao carregar um serviço do modlauncher (IDependencyLocator).<br>");
		sb.append("🔹 <b>Classe problemática:</b> <code>").append(clase).append("</code><br>");

		if (mods != null && !mods.isEmpty()) {
			sb.append("🔸 <b>Mod afetado:</b> ").append(String.join(", ", mods)).append("<br>");
		} else {
			sb.append(
					"🔸 <b>Mod não identificado.</b> Verifique mods recentemente instalados, de desenvolvimento ou mal empacotados.<br>");
		}

		sb.append("🔸 <b>Causa:</b> O arquivo <code>META-INF/services/...</code> do mod está corrompido, ");
		sb.append("é incompatível com esta versão do Forge/NeoForge, ou o mod é para uma versão incorreta.<br>");
		sb.append("🔸 <b>Consequência:</b> O Forge/NeoForge não pode registrar as dependências do mod, ");
		sb.append("impedindo o início do jogo.<br>");
		sb.append("🔸 <b>Solução:</b> Atualize, reinstale ou remova o mod problemático. ");
		sb.append(
				"Se estiver usando mods de desenvolvimento, certifique-se de que foram compilados para a sua versão exata do Forge/NeoForge.");
		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String nombre_error_configuracion_servicio() {
		return "Erro de Configuração de Serviço (IDependencyLocator)";
	}

	@Override
	public String paso1_configuracion_servicio(List<String> mods) {
		if (mods == null || mods.isEmpty()) {
			return "1. Identifique o mod causador: verifique mods instalados recentemente ou em desenvolvimento.";
		}
		return "1. O mod problemático é: " + String.join(", ", mods);
	}

	@Override
	public String paso2_configuracion_servicio() {
		return "2. Atualize, reinstale ou remova o mod. Certifique-se de usar uma versão compatível com seu Forge/NeoForge.";
	}

	@Override
	public String errorMetodoInexistente(String metodo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Erro crítico: Método inexistente.</b><br>"
				+ "O mod tentou chamar o método <b style='color:#" + colorCodigo + "'>" + metodo + "</b>, "
				+ "que não existe nesta versão do jogo ou de outro mod.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_metodo_inexistente() {
		return "Método Inexistente (NoSuchMethodError)";
	}

	@Override
	public String paso1_metodo_inexistente() {
		return "1. Este erro ocorre quando um mod é incompatível com a versão atual do jogo ou de outro mod.";
	}

	@Override
	public String paso2_metodo_inexistente() {
		return "2. Atualize todos os mods envolvidos. Se persistir, reporte o erro ao autor do mod afetado.";
	}

	@Override
	public String errorCampoInexistente(String campo, String lineaCompleta) {
		String colorError = config.obtenerColorError();
		String colorCodigo = "888888";

		return "<b style='color:#" + colorError + "'>Erro crítico: Campo inexistente.</b><br>"
				+ "O mod tentou acessar o campo <b style='color:#" + colorCodigo + "'>" + campo + "</b>, "
				+ "que não existe nesta versão do jogo ou de outro mod.<br>" + "<span style='color:#" + colorCodigo
				+ "; font-family:monospace;'>" + escapeHtml(lineaCompleta) + "</span>";
	}

	@Override
	public String nombre_error_campo_inexistente() {
		return "Campo Inexistente (NoSuchFieldError)";
	}

	@Override
	public String paso1_campo_inexistente() {
		return "1. Este erro geralmente ocorre quando um mod é incompatível com a versão atual do jogo ou de outro mod.";
	}

	@Override
	public String paso2_campo_inexistente() {
		return "2. Atualize todos os mods afetados. Se o problema persistir, entre em contato com o autor do mod que gerou o erro.";
	}

	@Override
	public String mensajeAyudar() {
		String iconoCompartir = Statics.carpeta.resolve("imagenes").resolve("boton_compartir_icon.png").toAbsolutePath()
				.toUri().toString();

		String colorTexto = Config.obtenerInstancia().obtenerColorInfo();

		return "" + "<div style='color:" + colorTexto + ";'>" + "  <strong>Precisa de ajuda?</strong><br>"
				+ "  Se você não sabe como corrigir ou a razão não está aqui, pode obter ajuda em nossas redes sociais. "
				+ "  Use o botão <img src='" + iconoCompartir
				+ "' alt='Compartilhar' style='height:12px;vertical-align:middle;'/> "
				+ "  <strong>Compartilhar</strong> para obter links dos registros e resultados para nossa equipe. "
				+ "  Se você é criador de modpack ou uma corporação, edite <code>crash_detector/plantilla.htm</code> "
				+ "  para personalizar os links da sua equipe." + "</div>";
	}

	@Override
	public String restablecerPlantilla() {
		return "Redefinir Modelo";
	}

	@Override
	public String restablecer() {
		return "Redefinir";
	}

	@Override
	public String restablecerImagenMensjae(String nombreImagen) {
		return "Redefinir " + nombreImagen + " para os valores padrão?";
	}

	@Override
	public String restablecerPlantillaMensaje() {
		return "Redefinir modelo para os valores padrão?";
	}

	@Override
	public String faltar_de_clases_azurelib() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Faltando classes do AzureLib. Se você já tem o AzureLib, por favor instale uma versão anterior a 8 de outubro de 2025. Era comum. Se você não tem o AzureLib, instale a versão atual.</b>";
	}

	@Override
	public String errorHealightINT() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "O mod <code>healight</code> está causando um erro crítico: <code>java.lang.NoSuchFieldError: INT</code>. "
				+ "Esse erro ocorre porque o mod tenta acessar um campo que já não existe na versão MCForge 47.10 do Minecraft 1.20+. "
				+ "O jogo não pode iniciar devido a esse problema.</b>";
	}

	@Override
	public String solucionHealightINT() {
		return "• Remova ou atualize o mod <code>healight</code>. "
				+ "A versão atual não é compatível com o MinecraftForge 47.10 para 1.20.1. "
				+ "Procure uma versão mais recente do mod ou considere usar uma alternativa.";
	}

	@Override
	public String nombreErrorHealightINT() {
		return "Erro crítico: healight - Campo 'INT' não encontrado";
	}

	@Override
	public String errorMetodoAbstractoNoImplementadoDetallado(String clase, String metodo, String interfaz,
			String origen) {
		String colorError = config.obtenerColorError();
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(colorError).append(";'>").append("A classe <code>").append(clase)
				.append("</code> não implementa o método necessário:<br>").append("<code>").append(metodo)
				.append("</code><br>").append("da interface <code>").append(interfaz).append("</code>.");

		if (!origen.isEmpty()) {
			sb.append("<br><br>Mod ou arquivo suspeito: <code>").append(origen).append("</code>.");
		}

		sb.append("</b>");
		return sb.toString();
	}

	@Override
	public String solucionMetodoAbstractoNoImplementado() {
		return "• Esse erro ocorre quando um mod implementa uma interface mas omite um método obrigatório. "
				+ "Atualize <b>ambos os mods</b> envolvidos (o que define a interface e o que a implementa). "
				+ "Se você não sabe quais são, procure pelos nomes mostrados na mensagem de erro.";
	}

	@Override
	public String nombreErrorMetodoAbstractoNoImplementado() {
		return "Método de Interface Não Implementado (AbstractMethodError)";
	}

	@Override
	public String errorMetadataAnimacionEnServidor() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Um mod está tentando carregar uma classe do <b>lado do cliente</b> "
				+ "(<code>AnimationMetadataSection</code>) em um <b>servidor dedicado</b>, o que é impossível. "
				+ "Esse erro geralmente ocorre quando um mod não separa corretamente seu código entre cliente e servidor. "
				+ "A presença do <code>ModernFix</code> pode expor esse problema, embora não seja a causa direta.</b>";
	}

	@Override
	public String solucionErrorMetadataAnimacionEnServidor() {
		return "• <b>Solução rápida:</b> Remova temporariamente o <code>ModernFix</code> para confirmar se o servidor inicia. "
				+ "Se funcionar, o problema está em outro mod que carrega classes do cliente no servidor.<br>"
				+ "• <b>Solução permanente:</b> Identifique o mod culpado (procure mods com recursos animados, texturas personalizadas ou bibliotecas gráficas) e atualize-o ou remova-o.<br>";
	}

	@Override
	public String nombreErrorMetadataAnimacionEnServidor() {
		return "Classe do cliente carregada no servidor (AnimationMetadataSection)";
	}

	@Override
	public String errorConfiguracionConnectorCorrupta() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "O arquivo de configuração de um mod do <code>Sinytra Connector</code> está corrompido. "
				+ "Isso geralmente ocorre quando o arquivo é preenchido com caracteres nulos (<code>\\u0000</code>) "
				+ "devido a um encerramento inesperado do jogo, falhas na gravação ou conflitos de mods.</b>";
	}

	@Override
	public String solucionConfiguracionConnectorCorrupta() {
		return "• Navegue até a pasta <code>config/</code> da sua instância do Minecraft.<br>"
				+ "• Localize e exclua os arquivos de configuração dos mods do connector.<br>"
				+ "• Reinicie o jogo: o Sinytra Connector gerará um novo arquivo de configuração limpo.";
	}

	@Override
	public String nombreErrorConfiguracionConnectorCorrupta() {
		return "Configuração do Sinytra Connector Corrompida";
	}

	@Override
	public String errorJarCorruptoConNombre(String nombreJar) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "O arquivo <code>" + nombreJar
				+ "</code> está corrompido ou incompleto.<br>"
				+ "O sistema não pode ler seu conteúdo porque o cabeçalho final do arquivo ZIP está faltando.<br>"
				+ "Esse erro geralmente ocorre após um download interrompido ou falha do launcher.</b>";
	}

	@Override
	public String nombreErrorJarCorruptoConNombre() {
		return "Arquivo JAR corrompido (com nome específico)";
	}

	@Override
	public String solucionJarCorrupto() {
		return "• <b>Exclua o arquivo corrompido</b> e baixe-o novamente da fonte oficial (CurseForge, MinecraftStorage, etc.).<br>"
				+ "• Se você usa um launcher como CurseForge, Technic ou Luna Pixel, considere mudar para <b>ATLauncher</b> ou <b>Prism Launcher</b>, "
				+ "que verificam melhor a integridade dos arquivos.<br>"
				+ "• Certifique-se de que sua conexão com a internet esteja estável durante o download.";
	}

	@Override
	public String errorCargaNBTMundoCorruptoConByte(String byteCorrupto) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Não foi possível carregar o mundo porque um de seus arquivos NBT está corrompido "
				+ "(por exemplo: <code>level.dat</code>, <code>playerdata/*.dat</code> ou chunks).<br>"
				+ "O erro específico é: <code>UTFDataFormatException: entrada malformada em torno do byte "
				+ byteCorrupto + "</code>.<br><br>" + "<b style='color:#" + config.obtenerColorAdvertencia() + ";'>"
				+ "⚠️ Antes de tentar qualquer reparo, faça um backup completo da pasta do mundo.</b><br><br>"
				+ "Você pode tentar reparar o arquivo corrompido usando um <b>editor NBT</b> como <a href='https://github.com/tryashtar/nbt-studio'>NBT Studio</a>.<br>"
				+ "Se o dano for grave, use um <b>editor hexadecimal</b> (como HxD) para inspecionar e corrigir bytes inválidos "
				+ "(somente se você tiver experiência com o formato NBT).<br>"
				+ "Como último recurso, restaure a partir de um backup ou use a função <i>reparar mundo</i> de mods como <code>FTB Backup</code>.</b>";
	}

	@Override
	public String solucionErrorCargaNBTMundoCorrupto() {
		return "• <b>Faça um backup completo da pasta do mundo</b> antes de tentar qualquer reparo.<br>"
				+ "• Use um editor NBT (como o NBT Studio) para abrir e corrigir o arquivo corrompido.<br>"
				+ "• Se falhar, inspecione o arquivo com um editor hexadecimal na posição do byte corrompido.<br>"
				+ "• Se não tiver experiência, restaure a partir de um backup recente.";
	}

	@Override
	public String nombreErrorCargaNBTMundoCorrupto() {
		return "Mundo corrompido: erro ao carregar dados NBT";
	}

	@Override
	public String problema_con_openAL() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Você tem um problema com o OpenAL. Às vezes os drivers Nouveau podem causar isso, mas às vezes a versão do OpenAL incluída na aplicação não é compatível com a versão da sua distribuição e você precisa usar a versão da sua distro. Isso é especialmente comum em distribuições baseadas em Red Hat e com mods de som como o Sound Physics Remastered. Veja este guia para mais ajuda: <a href='https://www.reddit.com/r/linux_gaming/comments/15zrzcw/how_to_fix_minecraft_sound_problems_using/' target='_blank'>Como corrigir problemas de som no Minecraft usando Linux</a>.</span>";
	}

	@Override
	public String errorArchivoBloqueadoPorOtroProceso() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "O servidor não pode iniciar porque um arquivo do mundo está bloqueado por outro processo.<br>"
				+ "Isso geralmente acontece se:<br>" + "• Já existe uma instância do servidor em execução.<br>"
				+ "• Um antivírus ou explorador de arquivos tem a pasta do mundo aberta.<br>"
				+ "• O processo anterior não foi fechado corretamente e deixou arquivos bloqueados.</b>";
	}

	@Override
	public String solucionErrorArchivoBloqueadoPorOtroProceso() {
		return "• <b>Feche todas as instâncias do servidor</b> (incluindo processos em segundo plano como javaw.exe).<br>"
				+ "• Se você usa um painel de hospedagem (como Multicraft), reinicie completamente o servidor pelo painel.<br>"
				+ "• <b>Desative temporariamente seu antivírus</b> se suspeitar que ele esteja bloqueando os arquivos.<br>"
				+ "• Em sistemas locais, feche qualquer janela do Explorador de Arquivos que mostre a pasta do mundo.<br>"
				+ "• Se o problema persistir, exclua manualmente o arquivo <code>session.lock</code> dentro da pasta do mundo (somente se tiver certeza de que não há outro servidor ativo).";
	}

	@Override
	public String nombreErrorArchivoBloqueadoPorOtroProceso() {
		return "Arquivo do mundo bloqueado por outro processo";
	}

	@Override
	public String errorClaseFinalExtendida(String claseHija, String clasePadreFinal) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "O mod tentou estender a classe <code>"
				+ clasePadreFinal + "</code>, " + "mas essa classe agora é <b>final</b> e não pode ser herdada.<br>"
				+ "A classe problemática é: <code>" + claseHija + "</code>.<br><br>"
				+ "Isso geralmente ocorre quando um mod foi compilado para uma versão anterior do Minecraft ou de outro mod base, "
				+ "que marcou classes como <code>final</code> em versões recentes.</b>";
	}

	@Override
	public String solucionErrorClaseFinalExtendida() {
		return "• <b>Atualize todos os mods envolvidos</b>, especialmente aqueles que possam estar relacionados ao mod base mencionado.<br>"
				+ "• Se o problema persistir, procure uma versão do mod compatível com sua versão atual do Minecraft e suas dependências.<br>"
				+ "• Em alguns casos, remover temporariamente o mod que contém a classe filha pode ajudar a confirmar a causa.";
	}

	@Override
	public String nombreErrorClaseFinalExtendida() {
		return "Tentativa de herdar de uma classe final";
	}

	@Override
	public String errorRubidiumObsoletoConIris() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Você está usando <b>Rubidium</b> (um fork obsoleto do Sodium para Forge) junto com <b>Iris ou Oculus</b>.<br>"
				+ "Em versões recentes do Minecraft (1.19.2+), "
				+ "o Rubidium não acompanhou as atualizações do Sodium e suas dependências apresentaram problemas.<br><br>"
				+ "Este erro também pode ocorrer se houver um conflito entre mods de desempenho (Sodium, Rubidium, Embeddium, Bedium, Xeonium, etc.) ou Iris Shaders com outro mod.</b>";
	}

	@Override
	public String solucionRubidiumObsoletoConIris() {
		return "• <b>Remova o Rubidium</b> da sua pasta <code>mods</code>.<br>"
				+ "• <b>Instale o <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a></b>, "
				+ "o fork ativo e compatível do Sodium para Forge que suporta Iris/Oculus em 1.20+.<br>"
				+ "• Certifique-se de não ter mais de um fork do Sodium instalado ao mesmo tempo (ex: Rubidium + Embeddium).<br>"
				+ "• Se você estiver usando Oculus em vez de Iris, verifique se ele também é compatível com sua versão de Forge e Embeddium.";
	}

	@Override
	public String nombreErrorRubidiumObsoletoConIris() {
		return "Rubidium obsoleto com Iris/Oculus (OptionInstance é final)";
	}

	@Override
	public String errorVoiceChatPuertoOcupado() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "O mod <code>Simple Voice Chat</code> não consegue iniciar seu servidor de voz porque "
				+ "a porta UDP já está em uso ou o endereço IP configurado é inválido.<br>"
				+ "Isso não impede o jogo de iniciar, mas desativa a funcionalidade de chat de voz.</b>";
	}

	@Override
	public String solucionErrorVoiceChatPuertoOcupado() {
		return "• <b>Feche qualquer outra instância do Minecraft</b> ou aplicativo que esteja usando a porta UDP 24454.<br>"
				+ "• Se você estiver em um servidor, certifique-se de que <b>nenhum outro serviço</b> esteja usando essa porta.<br>"
				+ "• Nas configurações do mod (<code>config/voicechat/</code>), mude a porta UDP para uma livre (por exemplo, 24455).<br>"
				+ "• Se estiver usando um endereço IP personalizado, verifique se está correto ou deixe em branco para usar o padrão.";
	}

	@Override
	public String nombreErrorVoiceChatPuertoOcupado() {
		return "Chat de Voz: porta UDP ocupada ou IP inválido";
	}

	@Override
	public String errorBlockItemNuloCreate(String nombreBlockItem) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "O BlockItem <code>" + nombreBlockItem
				+ "</code> tem um bloco nulo.<br>"
				+ "Esse erro geralmente ocorre em <b>addons do Create</b> (como <code>dndecor</code>, <code>createdeco</code>) "
				+ "quando há conflitos com <code>Amendments</code>, <code>Moonshine</code> ou inicialização incorreta de blocos.<br>"
				+ "<b>Nota:</b> Esse não é um erro direto do Amendments, mas um sintoma de um problema mais profundo no carregamento do registro.</b>";
	}

	@Override
	public String solucionErrorBlockItemNuloCreate() {
		return "• <b>Atualize todos os mods relacionados:</b> Create, Amendments, Moonshine e qualquer addon (especialmente <code>dndecor</code> e <code>createdeco</code>).<br>"
				+ "• Se o problema persistir, <b>remova temporariamente os addons do Create</b> um por um para identificar o culpado.<br>"
				+ "• Certifique-se de que <b>Amendments e Moonshine sejam compatíveis</b> com sua versão do Create e do Forge.<br>"
				+ "• Verifique se há versões beta ou forks atualizados dos addons problemáticos.";
	}

	@Override
	public String nombreErrorBlockItemNuloCreate() {
		return "BlockItem nulo em addon do Create";
	}

	@Override
	public String modIncompatibleConCargadorActivo(List<String> mods) {
		StringBuilder sb = new StringBuilder();
		sb.append("<b style='color:#").append(config.obtenerColorAdvertencia()).append(";'>").append(
				"Foram encontrados mods que não pertencem a nenhuma plataforma ativa (Forge, Fabric, etc.):<ul>");
		for (String mod : mods) {
			sb.append("<li><code>").append(mod).append("</code></li>");
		}
		sb.append("</ul>Isto geralmente ocorre quando:<br>")
				.append("• Mods de <b>Fabric e Forge</b> são misturados na mesma pasta.<br>")
				.append("• Um mod é instalado para uma versão incompatível do Minecraft.<br>")
				.append("• O mod está corrompido ou não é um arquivo JAR válido.</b>");
		return sb.toString();
	}

	@Override
	public String solucionModIncompatibleConCargadorActivo() {
		return "• <b>Verifique se todos os mods são para a mesma plataforma</b> (Forge <b>ou</b> Fabric, não ambos).<br>"
				+ "• Use a <b>árvore de mods</b> para identificar qual plataforma cada arquivo é detectado.<br>"
				+ "• Remova qualquer mod que você não reconheça ou que seja de uma plataforma diferente.<br>"
				+ "• Se você usa um launcher como CurseForge ou Prism, certifique-se de que o perfil esteja configurado corretamente.";
	}

	@Override
	public String nombreModIncompatibleConCargadorActivo() {
		return "Mod incompatível com carregador ativo";
	}

	@Override
	public String errorCreacionModeloFallida(String modid, String nombreModelo) {
		return "<b style='color:#" + config.obtenerColorError() + ";'>" + "Falha ao criar o modelo <code>" + modid + ":"
				+ nombreModelo + "</code>.<br>" + "Isso indica que o mod <code>" + modid
				+ "</code> possui recursos corrompidos, ausentes "
				+ "ou incompatíveis com sua versão do Minecraft.</b>";
	}

	@Override
	public String solucionErrorCreacionModeloFallida() {
		return "• <b>Atualize o mod</b> para a versão mais recente compatível com sua instância.<br>"
				+ "• Se estiver usando uma versão de desenvolvimento ou personalizada, volte para a versão oficial.<br>"
				+ "• Verifique se o arquivo JAR não está corrompido (reinstale-o).<br>"
				+ "• Se o problema persistir, relate o erro ao autor do mod incluindo este log.";
	}

	@Override
	public String nombreErrorCreacionModeloFallida() {
		return "Falha ao criar modelo de recurso";
	}

	@Override
	public String conflictoMoonlightIceberg() {
		return "<b style='color:#" + config.obtenerColorError() + ";'>"
				+ "Foi detectado um conflito crítico entre os mods <code>Moonlight</code> e <code>Iceberg</code>.<br>"
				+ "Ambos tentam registrar sistemas de recarga de recursos de forma incompatível, "
				+ "o que causa uma falha do OpenGL por falta de um contexto gráfico válido.<br>"
				+ "Esse problema é comum ao usar versões do Forge que incluem adaptadores para mods do Fabric.</b>";
	}

	@Override
	public String solucionConflictoMoonlightIceberg() {
		return "• <b>Atualize ambos os mods</b> para as últimas versões compatíveis com sua versão do Forge.<br>"
				+ "• Se o problema persistir, <b>remova temporariamente o Iceberg</b>, pois o Moonlight geralmente é uma dependência mais crítica para outros mods.<br>"
				+ "• Certifique-se de não ter versões duplicadas ou misturadas do Forge/Fabric desses mods.<br>"
				+ "• Verifique se algum outro mod (como Supplementaries, Citadel, etc.) já inclui funcionalidade do Iceberg internamente.";
	}

	@Override
	public String nombreConflictoMoonlightIceberg() {
		return "Conflito crítico: Moonlight vs Iceberg (OpenGL sem contexto)";
	}

	@Override
	public String instantanea() {
		return "Instantâneo";
	}

	@Override
	public Object desdeUltimaInstantanea() {
		return "Desde o último instantâneo";
	}

	@Override
	public String seleccionarUnArchivo() {
		return "Selecionar um arquivo";
	}

	@Override
	public String instantaneaCreadaCorrectamente() {
		return "Instantâneo criado com sucesso";
	}

	@Override
	public String errorCreandoInstantanea() {
		return "Erro ao criar o instantâneo";
	}

	@Override
	public String consejo() {
		return "Dica";
	}

	@Override
	public String resultadoMuestra() {
		return "Mostrar Resultado";
	}

	@Override
	public String historaDeModsDesc() {
		return "<html>" + "<body style='font-family: sans-serif; font-size: 12px;'>"
				+ "  <b>Dica:</b> Selecione dois arquivos de histórico para comparar a lista de mods. "
				+ "  O resultado mostra <span style='color:%s;'>adicionados (+)</span> e "
				+ "  <span style='color:%s;'>removidos (&#8722;)</span> com base em nomes normalizados. "
				+ "  Use o botão 'Instantâneo' para criar uma cópia de um arquivo existente com a extensão .instantanea."
				+ "</body>" + "</html>";
	}

	@Override
	public String texto_de_boton_compartir_markdown() {
		return "Obter Links de Logs como Markdown sem Relatório";
	}

	@Override
	public String titulo_configuracion() {
		return "Configuração";
	}

	@Override
	public String columna_url() {
		return "URL";
	}

	@Override
	public String error_inesperado_al_compartir() {
		return "Erro inesperado ao compartilhar.";
	}

	@Override
	public String error_inesperado_al_generar_enlaces() {
		return "Erro inesperado ao gerar links.";
	}

	@Override
	public String error_inesperado_al_procesar_boton() {
		return "Erro inesperado ao processar botão.";
	}

	@Override
	public String sin_archivo_para_abrir() {
		return "Nenhum arquivo associado para abrir.";
	}

	@Override
	public String archivo_no_existe_prefijo() {
		return "O arquivo não existe:\n";
	}

	@Override
	public String no_se_pudo_editar_se_copia_ruta() {
		return "Não foi possível abrir no editor.\nO caminho será copiado para a área de transferência.";
	}

	@Override
	public String no_se_pudo_abrir_se_copia_ruta() {
		return "Não foi possível abrir o arquivo; o caminho foi copiado para a área de transferência.";
	}

	@Override
	public String escritorio_no_soportado_se_copia_ruta() {
		return "Área de trabalho não suportada; o caminho foi copiado para a área de transferência.";
	}

	@Override
	public String limite_de_solicitudes() {
		return "Você está enfrentando um limite de solicitações. Tente usar outro site de registro ou outra API de registro.";
	}

	@Override
	public String texto_de_boton_compartir_enlace() {
		return "Compartilhar Link";
	}

	@Override
	public String infoDeTrazos() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Corrigir as partes superiores dos troncos é a principal prioridade. " + "O formato é Nível, Linha. "
				+ "Todos os registros têm um sistema de numeração. " + Verificaciones.nl_html
				+ "Geralmente, você precisa procurar nos níveis mais baixos em todos os registros; rastros com níveis altos geralmente são falsos positivos. "
				+ "É importante usar sua habilidade de ler na console, pois a análise de rastros não é perfeita quando há muitos rastros."
				+ "</b>";
	}

	// --- Localizador de Canary de Mandado (Warrant Canary) ---
	/**
	 * Texto do botão para o localizador de canary de mandado. Exemplo: "Localizador
	 * de canary de mandado"
	 */
	@Override
	public String buscador_canario_de_orden_label() {
		return "Localizador de Canary de Mandado";
	}

	/**
	 * Mensagem exibida na caixa de diálogo informando que o recurso estará
	 * disponível em breve.
	 */
	@Override
	public String buscador_canario_de_orden_mensaje_proximamente() {
		return "Este recurso estará disponível em breve.";
	}

	/**
	 * Título da caixa de diálogo que informa sobre a disponibilidade futura do
	 * localizador de canary de mandado.
	 */
	@Override
	public String buscador_canario_de_orden_titulo_proximamente() {
		return "Em Breve";
	}

	@Override
	public String nombre_de_mods_incompatibles_crash_assistant() {
		return "Mods Incompatíveis com o Crash Assistant (Falso)";
	}

	@Override
	public String nombre_de_modpack_incompatible_crash_assistant() {
		return "Mod Incompatível com Modpack usando CrashAssistant";
	}

	@Override
	public String advertenciaCrashAssistantModpackIncompatibleFalso() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>O Crash Assistant possui uma lista de mods que ele diz serem incompatíveis, mas não temos evidências disso e o erro está apenas em inglês. Se você quiser jogar com esses mods, pode editar o arquivo <code>config/crash_assistant/config.toml</code> e alterar <code>enabled = true</code> na seção [compatibility] para <code>enabled = false</code>.</b>";
	}

	@Override
	public String advertenciaCrashAssistantModsIncompatibles() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>O Crash Assistant tem a capacidade de dizer que mods são incompatíveis, mas às vezes isso é incorreto e a mensagem de erro está apenas em um idioma. Se você quiser usar esses mods, pode editar o arquivo <code>config/crash_assistant/problematic_mods_config.json</code> e mudar <code>should_crash_on_startup</code> de <code>true</code> para <code>false</code>.</b>";
	}

	@Override
	public String errorDependenciaSimple(String modId, String dependencia, String actual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erro: O mod '" + modId + "' requer o mod '"
				+ dependencia + "'. Atualmente, " + actual + "." + "</span>";
	}

	@Override
	public String errorDependenciaNoInstalada(String modId, String dependencia, String requerido) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erro: O mod '" + modId
				+ "' requer a versão '" + requerido + "' ou superior do '" + dependencia
				+ "', mas o mod não está instalado." + "</span>";
	}

	// Na classe MonitorDePID.idioma (adicione este método)
	public String errorSuperbWarfareIncompatible(String modId, String dependencia, String versionActual) {
		return "<span style='color:#" + config.obtenerColorError() + "'>" + "Erro: O mod '" + modId
				+ "' é incompatível com a versão atual de '" + dependencia + "'. "
				+ "Você deve remover o mod 'Iris/Oculus & GeckoLib Compat', pois ele é incompatível com o Superb Warfare e não funciona com a versão mais recente do GeckoLib. "
				+ "Versão atual: " + versionActual + "</span>";
	}

	public String fallo_ejecucion_tarea_descripcion(String clase) {
		return "Erro: Falha ao executar a tarefa para a classe '" + clase + "'. "
				+ "Esse erro é comum com mods que não são compatíveis entre si ou que têm conflitos com outros mods instalados.";
	}

	public String nombre_fallos_ejecucion_tareas() {
		return "Falhas na execução de tarefas";
	}

	public String recomendacion_fallos_ejecucion() {
		return "Esse tipo de erro geralmente ocorre quando há incompatibilidades entre mods. "
				+ "Especialmente comum com mods que não funcionam corretamente com o ConnectorMod.";
	}

	public String info_clase_problematica() {
		return "Classe problemática:";
	}

	public String ver_en_log() {
		return "Ver no Log";
	}

	public String no_se_encontraron_clases_problema() {
		return "Nenhuma classe específica com problemas de execução foi encontrada.";
	}

	@Override
	public String errorConflictoOptiFineEMF() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre OptiFine e Entity Model Features (EMF). "
				+ "Esses mods não são compatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEMF() {
		return "Conflito entre OptiFine e Entity Model Features";
	}

	@Override
	public String pasoConflictoOptiFineEMF() {
		return "Desinstale o OptiFine ou o Entity Model Features, pois eles não são compatíveis entre si.";
	}

	@Override
	public String errorConflictoOptiFineFusion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre OptiFine e Fusion. "
				+ "Esses mods não são compatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineFusion() {
		return "Conflito entre OptiFine e Fusion";
	}

	@Override
	public String pasoConflictoOptiFineFusion() {
		return "Desinstale o OptiFine ou o Fusion, pois eles não são compatíveis entre si.";
	}

	@Override
	public String errorConflictoFlywheelSodium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "O Flywheel (necessário pelo Create) precisa do Sodium 0.6.0-beta.2 ou superior. O Rubidium é 0.5.3. "
				+ "Considere usar o <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> como alternativa."
				+ "</b>";
	}

	@Override
	public String nombreDeConflictoFlywheelSodium() {
		return "Conflito entre Flywheel e versão do Sodium";
	}

	@Override
	public String pasoConflictoFlywheelSodium() {
		return "Atualize o Sodium para 0.6.0-beta.2 ou superior, ou instale o <a href='https://www.curseforge.com/minecraft/mc-mods/embeddium'>Embeddium</a> como uma alternativa compatível.";
	}

	@Override
	public String errorConflictoOptiFineEpicFight() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre OptiFine e Epic Fight. "
				+ "Esses mods não são compatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEpicFight() {
		return "Conflito entre OptiFine e Epic Fight";
	}

	@Override
	public String pasoConflictoOptiFineEpicFight() {
		return "Desinstale o OptiFine ou o Epic Fight, pois eles não são compatíveis entre si.";
	}

	@Override
	public String errorConflictoOptiFineRubidium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre OptiFine e Rubidium. "
				+ "Esses mods são incompatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineRubidium() {
		return "Conflito entre OptiFine e Rubidium";
	}

	@Override
	public String pasoConflictoOptiFineRubidium() {
		return "Desinstale OptiFine ou Rubidium, pois eles são incompatíveis entre si.";
	}

	@Override
	public String errorFreeCamServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "FreeCam está tentando carregar em um servidor dedicado, mas é compatível apenas com o cliente. "
				+ "Remova FreeCam do servidor ou certifique-se de que ele está instalado apenas no cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorFreeCamServidor() {
		return "FreeCam em servidor dedicado";
	}

	@Override
	public String pasoErrorFreeCamServidor() {
		return "Remova FreeCam do servidor dedicado, pois ele deve ser instalado apenas no cliente.";
	}

	@Override
	public String errorEntityTextureFeaturesServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Entity Texture Features (ETF) está tentando carregar em um servidor dedicado, mas é compatível apenas com o cliente. "
				+ "Remova ETF do servidor ou certifique-se de que ele está instalado apenas no cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorEntityTextureFeaturesServidor() {
		return "Entity Texture Features em servidor dedicado";
	}

	@Override
	public String pasoErrorEntityTextureFeaturesServidor() {
		return "Remova Entity Texture Features do servidor dedicado, pois ele deve ser instalado apenas no cliente.";
	}

	@Override
	public String errorEULANoAceptado() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Você deve aceitar o EULA do Minecraft para executar o servidor. "
				+ "Edite o arquivo eula.txt e mude 'eula=false' para 'eula=true'." + "</b>";
	}

	@Override
	public String nombreDeErrorEULANoAceptado() {
		return "EULA do Minecraft não aceito";
	}

	@Override
	public String pasoErrorEULANoAceptado() {
		return "Edite o arquivo eula.txt na pasta do servidor e mude 'eula=false' para 'eula=true'.";
	}

	@Override
	public String errorOptiFineServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "OptiFine está tentando carregar em um servidor dedicado, mas é compatível apenas com o cliente. "
				+ "Remova OptiFine do servidor ou certifique-se de que ele está instalado apenas no cliente." + "</b>";
	}

	@Override
	public String nombreDeErrorOptiFineServidor() {
		return "OptiFine em servidor dedicado";
	}

	@Override
	public String pasoErrorOptiFineServidor() {
		return "Remova o OptiFine do servidor dedicado, pois ele deve ser instalado apenas no cliente. Esse problema também costuma ocorrer devido à instalação de uma versão do OptiFine para a versão incorreta do Minecraft ou a um conflito entre outro mod e o OptiFine.";
	}

	@Override
	public String errorIronSpellbooksVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Iron's Spellbooks está marcado incorretamente para 1.20.1, mas usa métodos do 1.21.1. "
				+ "O mod está tentando usar ResourceLocation.fromNamespaceAndPath, que não existe no 1.20.1." + "</b>";
	}

	@Override
	public String nombreDeErrorIronSpellbooksVersion() {
		return "Erro de versão no Iron's Spellbooks";
	}

	@Override
	public String pasoErrorIronSpellbooksVersion() {
		return "Certifique-se de estar usando a versão correta do Iron's Spellbooks compatível com sua versão do Minecraft.";
	}

	@Override
	public String errorConflictoOptiFineEmbeddium() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre OptiFine e Embeddium. "
				+ "Esses mods são incompatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoOptiFineEmbeddium() {
		return "Conflito entre OptiFine e Embeddium";
	}

	@Override
	public String pasoConflictoOptiFineEmbeddium() {
		return "Desinstale OptiFine ou Embeddium, pois eles são incompatíveis entre si.";
	}

	@Override
	public String noPuedeAnalizarJSON() {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>É comum com mods conflitantes de geração de mundo, especialmente Terralinth, AmplifiedNether, Nullscape e Incendium, e outros mods de geração de mundo. Também pode ser necessário instalar um mod ausente.</span>";
	}

	@Override
	public String errorControllableServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Controllable está tentando carregar em um servidor dedicado, mas é compatível apenas com o cliente. "
				+ "Remova Controllable do servidor ou certifique-se de que ele está instalado apenas no cliente."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorControllableServidor() {
		return "Controllable em servidor dedicado";
	}

	@Override
	public String pasoErrorControllableServidor() {
		return "Remova Controllable do servidor dedicado, pois ele deve ser instalado apenas no cliente.";
	}

	@Override
	public String errorSupplementariesCargaServidor() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Supplementaries está causando um erro que impede a inicialização do servidor. "
				+ "O mod tem problemas com o registro de comportamentos de fogo que causam uma falha durante o carregamento dos datapacks."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorSupplementariesCargaServidor() {
		return "Supplementaries impede a inicialização do servidor";
	}

	@Override
	public String pasoErrorSupplementariesCargaServidor() {
		return "Tente atualizar o Supplementaries para a versão mais recente ou desative-o temporariamente para permitir a inicialização do servidor.";
	}

	@Override
	public String errorGroovyModloaderModuloFaltante() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Groovy Modloader (GML) encontrou um problema com módulos Jackson ausentes. "
				+ "Alguns mods, como Valkyrien Skies, podem causar este erro por não incluírem todas as dependências necessárias."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorGroovyModloaderModuloFaltante() {
		return "Módulo Jackson ausente no Groovy Modloader";
	}

	@Override
	public String pasoErrorGroovyModloaderModuloFaltante() {
		return "Remova o Groovy Modloader e mods relacionados, como Valkyrien Skies, que possam causar conflitos de dependências.";
	}

	@Override
	public String errorEveryCompatNombreInvalido() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Every Compat encontrou um nome inválido de bloco de madeira. "
				+ "Every Compat geralmente tem muitos problemas. Não o use!" + "</b>";
	}

	@Override
	public String nombreDeErrorEveryCompatNombreInvalido() {
		return "Nome inválido no Every Compat";
	}

	@Override
	public String pasoErrorEveryCompatNombreInvalido() {
		return "Verifique os pacotes de recursos ou mods que usam Every Compat, pois podem conter nomes de blocos inválidos.";
	}

	@Override
	public String errorCodigo1073741819() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um código de erro (-1073741819) que pode ser causado por overlays como GameCaster da Razer, Discord, OBS Studio ou problemas com drivers da NVIDIA."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCodigo1073741819() {
		return "Código de erro -1073741819";
	}

	@Override
	public String pasoErrorCodigo1073741819() {
		return "Tente desativar overlays como GameCaster, Discord ou OBS Studio e verifique se seus drivers da NVIDIA estão atualizados.";
	}

	@Override
	public String errorImmersiveTooltipsSinDependencia() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Immersive Tooltips requer Immersive Messages como dependência, mas não está instalado. "
				+ "Instale Immersive Messages para que Immersive Tooltips funcione corretamente." + "</b>";
	}

	@Override
	public String nombreDeErrorImmersiveTooltipsSinDependencia() {
		return "Immersive Tooltips sem dependência";
	}

	@Override
	public String pasoErrorImmersiveTooltipsSinDependencia() {
		return "Instale Immersive Messages, pois é uma dependência necessária para o Immersive Tooltips.";
	}

	@Override
	public String errorMedievalOriginsCast() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Medieval Origins tem um problema de compatibilidade com Apoli Mod onde ItemStack não pode ser convertido (cast) para EntityLinkedItemStack. "
				+ "Isso é comum em versões posteriores à 6.6.0. Considere usar uma versão anterior ou alternar entre versões do Fabric e Forge."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorMedievalOriginsCast() {
		return "Erro de conversão (cast) no Medieval Origins";
	}

	@Override
	public String pasoErrorMedievalOriginsCast() {
		return "Use uma versão do Medieval Origins 6.6.0 ou anterior, ou tente alternar entre as versões Fabric e Forge do mod.";
	}

	@Override
	public String errorReignOfNetherMusicManager() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Reign of Nether está causando um erro com um Registry Object ausente no MusicManager. "
				+ "Esse problema está relacionado ao mixin do MusicManager do Reign of Nether." + "</b>";
	}

	@Override
	public String nombreDeErrorReignOfNetherMusicManager() {
		return "Erro do MusicManager no Reign of Nether";
	}

	@Override
	public String pasoErrorReignOfNetherMusicManager() {
		return "Tente atualizar o Reign of Nether ou removê-lo temporariamente para resolver o erro.";
	}

	@Override
	public String errorYesSteveModelLinux() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "YesSteveModel suporta o servidor YSM apenas no Linux ou Android. "
				+ "Esse problema foi corrigido em versões mais recentes a partir de 23 de novembro de 2025 no Modrinth."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorYesSteveModelLinux() {
		return "YesSteveModel incompatível com Linux";
	}

	@Override
	public String pasoErrorYesSteveModelLinux() {
		return "Atualize o YesSteveModel para uma versão mais recente no Modrinth, pois o problema foi corrigido após 23 de novembro.";
	}

	@Override
	public String errorConflictoMovingElevatorsOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre Moving Elevators e OptiFine. "
				+ "Esses mods são incompatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoMovingElevatorsOptiFine() {
		return "Conflito entre Moving Elevators e OptiFine";
	}

	@Override
	public String pasoConflictoMovingElevatorsOptiFine() {
		return "Desinstale OptiFine ou Moving Elevators, pois eles são incompatíveis entre si.";
	}

	@Override
	public String errorConflictoFabricAPIOptiFine() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito crítico entre Fabric API (fabric-resource-loader-v0) e OptiFine. "
				+ "Esses mods são incompatíveis e causam uma falha de injeção que impede o início do jogo." + "</b>";
	}

	@Override
	public String nombreDeConflictoFabricAPIOptiFine() {
		return "Conflito entre Fabric API e OptiFine";
	}

	@Override
	public String pasoConflictoFabricAPIOptiFine() {
		return "Desinstale o OptiFine ou atualize o Fabric API para uma versão compatível.";
	}

	@Override
	public String errorModLauncherTransformationService(String claseProveedor) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Um mod possui um ITransformationService defeituoso que não pode ser instanciado: " + claseProveedor
				+ ". " + "Esse mod deve ser removido para permitir a inicialização do jogo." + "</b>";
	}

	@Override
	public String nombreDeErrorModLauncherTransformationService() {
		return "ITransformationService defeituoso";
	}

	@Override
	public String pasoErrorModLauncherTransformationService(String claseProveedor) {
		return "Remova o mod que contém a classe " + claseProveedor
				+ ", pois ele possui um ITransformationService defeituoso.";
	}

	@Override
	public String errorVersionInvalidaMod(String version) {
		return "<span style='color:#" + config.obtenerColorError()
				+ "'>Um mod possui uma especificação de versão inválida. "
				+ "A versão deve estar envolvida por colchetes. "
				+ "Você pode usar o utilitário grep/greprf do painel lateral buscando a versão </span>" + version
				+ "<span style='color:#" + config.obtenerColorError()
				+ "'> para identificar qual mod tem o problema.</span>";
	}

	@Override
	public String nombreDeErrorVersionInvalidaMod() {
		return "Versão inválida no mod";
	}

	@Override
	public String pasoErrorVersionInvalidaMod() {
		return "Use o utilitário grep/greprf do painel lateral para buscar a versão problemática e encontrar o mod que a contém.";
	}

	@Override
	public String errorStackSmashingDetected() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um erro de stack smashing que encerrou o processo. "
				+ "Isso pode ser causado por problemas com Early Window no Forge/NeoForge/PillowMC ou com LWJGL 3.2.2 e versões mais recentes."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorStackSmashingDetected() {
		return "Stack Smashing Detectado";
	}

	@Override
	public String pasoErrorStackSmashingDetected() {
		return "Verifique as configurações do Early Window e considere usar uma versão diferente do LWJGL ou revisar os mods relacionados à janela inicial.";
	}

	@Override
	public String errorVersionClaseGregTechEasyCore() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "GregTechEasyCore é apenas para um modpack específico e não deve ser usado em instalações gerais, pois causa um problema."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorVersionClaseGregTechEasyCore() {
		return "GregTechEasyCore com versão incompatível do Java";
	}

	@Override
	public String pasoErrorVersionClaseGregTechEasyCore() {
		return "Remova o GregTechEasyCore, pois ele é apenas para um modpack específico e não é compatível com sua instalação geral.";
	}

	@Override
	public String errorConflictoMoniLabsConnectorExtras() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito entre MoniLabs e Connector Extras relacionado às modificações do KubeJS. "
				+ "Esses mods são incompatíveis em suas modificações do KubeJS." + "</b>";
	}

	@Override
	public String nombreDeConflictoMoniLabsConnectorExtras() {
		return "Conflito entre MoniLabs e Connector Extras";
	}

	@Override
	public String pasoConflictoMoniLabsConnectorExtras() {
		return "Tente desinstalar um dos mods (MoniLabs ou Connector Extras), pois há conflito em suas modificações do KubeJS.";
	}

	@Override
	public String errorCompatibilidadIrisDH() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "O Iris requer o Distant Horizons [2.0.4] ou a DH API versão [1.1.0] ou superior. "
				+ "Consulte o guia de compatibilidade em https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e para resolver o problema."
				+ "</b>";
	}

	@Override
	public String nombreDeErrorCompatibilidadIrisDH() {
		return "Compatibilidade entre Iris e Distant Horizons";
	}

	@Override
	public String pasoErrorCompatibilidadIrisDH() {
		return "Consulte o guia de compatibilidade em https://gist.github.com/Steveplays28/52db568f297ded527da56dbe6deeec0e e atualize o Iris e o Distant Horizons para versões compatíveis.";
	}

	@Override
	public String faltar_de_clases_minecraft() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do Minecraft. Possíveis razões:</b>" + "<ul>"
				+ "<li>Você tem mods para outras versões do jogo. Você pode usar o <a href='https://wagyourtail.xyz/Projects/MinecraftMappingViewer' target='_blank'>MinecraftMappingViewer</a> para verificar se a classe existe na sua versão.</li>"
				+ "<li>Você tem uma instalação corrompida do Minecraft (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "<li>Você tem um coremod defeituoso (se um coremod falhar, pode bloquear o carregamento da classe).</li>"
				+ "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_dangerzone() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do DangerZone. Possíveis razões:</b>" + "<ul>"
				+ "<li>Você tem mods para outras versões do jogo.</li>" + "<li>Você tem coremods defeituosos.</li>"
				+ "<li>Você tem um launcher ou instalação corrompida.</li>" + "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_featurecreep() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do FeatureCreep. Possíveis razões:</b>" + "<ul>"
				+ "<li>Você tem mods para outras versões do FeatureCreep (ex.: ESR vs Nightly ou v4 vs v12).</li>"
				+ "<li>Você pode instalar o FeatureCreep pelo CurseForge ou MinecraftStorage.</li>" + "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_modlauncher() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do ModLauncher. Possíveis razões:</b>" + "<ul>"
				+ "<li>Seus mods são para uma build diferente do MinecraftForge, PillowMC ou NeoForge (o ModLauncher é usado com esses carregadores).</li>"
				+ "<li>Existem muitas atualizações de modloaders para uma única versão do Minecraft.</li>"
				+ "<li>Você tem uma instalação corrompida do seu launcher (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_minecraftforge() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do Minecraft Forge. Possíveis razões:</b>" + "<ul>"
				+ "<li>Seus mods são para uma build diferente do MinecraftForge.</li>"
				+ "<li>Existem muitas atualizações de modloaders para uma única versão do Minecraft.</li>"
				+ "<li>Você tem uma instalação corrompida (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_neoforged() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do NeoForge. Possíveis razões:</b>" + "<ul>"
				+ "<li>Seus mods são para uma build diferente do NeoForge.</li>"
				+ "<li>Existem muitas atualizações de modloaders para uma única versão do Minecraft.</li>"
				+ "<li>Você tem uma instalação corrompida (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_fabricloader() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do Fabric Loader. Possíveis razões:</b>" + "<ul>"
				+ "<li>Seus mods são para uma build diferente do Fabric Loader.</li>"
				+ "<li>Existem muitas atualizações de modloaders para uma única versão do Minecraft.</li>"
				+ "<li>Você tem uma instalação corrompida (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "<li>Muitos mods exigem o Fabric API. Por favor, instale o Fabric API se necessário.</li>" + "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String faltar_de_clases_pillowmc() {
		return "<b style='color:#" + config.obtenerColorError()
				+ "'>Estão faltando classes do PillowMC. Possíveis razões:</b>" + "<ul>"
				+ "<li>Seus mods são para uma build diferente do PillowMC.</li>"
				+ "<li>Existem muitas atualizações de modloaders para uma única versão do Minecraft.</li>"
				+ "<li>Você tem uma instalação corrompida (comum com CurseForge App, ModrinthApp/Theseus/Astralrinth e outros launchers de modpacks). <a href='https://www.youtube.com/watch?v=EeAf_PKXl8c' target='_blank'>Veja o tutorial</a> para resolver problemas com o CurseForge.</li>"
				+ "</ul>"
				+ "<p>Observação: você pode usar a ferramenta <b>grepr/fgrepr</b> na barra lateral para encontrar os mods que fazem referência às classes ausentes, desde que use '/' nos nomes.</p>";
	}

	@Override
	public String uraniumLag() {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Você tem um mod que está intencionalmente causando lag. Uranium é um mod de lag. Ele nem sempre causa falhas, mas eventualmente pode causar."
				+ "</b>";
	}

	@Override
	public String errorFallingAttackVersion() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Falling Attack está marcado como compatível com 1.19.*, mas é para 1.20.*, o que causa um erro de 'classe não encontrada'. "
				+ "O mod tenta usar DamageSources que não existem na versão atual do Minecraft." + "</b>";
	}

	@Override
	public String nombreDeErrorFallingAttackVersion() {
		return "Erro de versão do Falling Attack";
	}

	@Override
	public String pasoErrorFallingAttackVersion() {
		return "Certifique-se de estar usando a versão correta do Falling Attack compatível com sua versão do Minecraft.";
	}

	@Override
	public String necesitasInstalarCfr() {
		String sistema = System.getProperty("os.name").toLowerCase();
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<html>")
				.append("Você precisa instalar o CFR (Class File Reader) para usar este recurso.<br><br>");

		if (sistema.contains("linux") || sistema.contains("netbsd") || sistema.contains("freebsd")) {
			mensaje.append(
					"Em sistemas Linux, NetBSD ou FreeBSD, você pode instalar o CFR pelo gerenciador de pacotes.<br>")
					.append("Procure o pacote em: <a href=\"https://pkgs.org/search/?q=cfr\">https://pkgs.org/search/?q=cfr</a><br><br>");
		}

		mensaje.append("Alternativamente, você pode baixar a versão modificada usada pelo FabricMC em:<br>")
				.append("<a href=\"https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar\">")
				.append("https://maven.fabricmc.net/net/fabricmc/cfr/0.2.2/cfr-0.2.2.jar</a><br><br>")
				.append("Salve-o na seguinte pasta:<br>").append("<b>")
				.append(new java.io.File(System.getProperty("user.home"), "crash_detector/cfr/").getAbsolutePath())
				.append("</b><br><br>")
				.append("⚠️ <b>Importante:</b> após instalar o CFR, reinicie o mod para que ele seja reconhecido corretamente.")
				.append("</html>");
		return mensaje.toString();
	}

	@Override
	public String cfrNoHayRetrato() {
		return "Sem retrato disponível";
	}

	@Override
	public String cfrClaseNoEncontrada(String nombreClase) {
		return "Não foi possível encontrar a classe: " + nombreClase;
	}

	@Override
	public String tituloCfrSakura() {
		return "Descompilador CFR – Sakura Riddle (Não Oficial)";
	}

	@Override
	public String cfrClaseActual() {
		return "Classe atual";
	}

	@Override
	public String cfrRetratoDeSakura() {
		return "Retrato de Sakura Riddle";
	}

	@Override
	public String cfrErrorCargarRetrato() {
		return "Erro ao carregar o retrato";
	}

	public String noticiaLegalCFR() {
		return "Este programa com interface gráfica (GUI) para descompilar mods foi projetado para ajudar os usuários a identificar as causas de falhas no software. "
				+ "No entanto, a descompilação de mods pode ser necessária, e os usuários devem ter cuidado para não usar o código gerado para infringir a Lei Federal de Direitos Autorais. "
				+ "Recomenda-se verificar a licença do mod correspondente antes de usar qualquer código obtido. Além disso, muitos mods disponibilizam oficialmente seu código-fonte, "
				+ "que geralmente é mais limpo e fácil de entender do que o código descompilado. Lembre-se de que o respeito à propriedade intelectual e às licenças de uso é fundamental para "
				+ "a comunidade de desenvolvedores de mods. Você pode consultar a Lei Federal de Direitos Autorais do México no seguinte link: "
				+ "<a href=\"https://www.diputados.gob.mx/LeyesBiblio/pdf/LFDA.pdf\" target=\"_blank\">Ley Federal de Derechos de Autor (Espanhol)</a> "
				+ "e a versão em inglês aqui: <a href=\"https://www.indautor.gob.mx/documentos/marco-juridico/L_Fed_Derecho_de_Autor_(English).pdf\" target=\"_blank\">Copyright Law (English)</a>. "
				+ "Como você está no CurseForge, também fornecemos o link para a Lei de Direitos Autorais dos EUA em inglês: "
				+ "<a href=\"https://www.copyright.gov/title17/title17.pdf\" target=\"_blank\">U.S. Copyright Law</a>. "
				+ "Além disso, recomenda-se que os usuários pesquisem as leis aplicáveis em sua localização. "
				+ "Nossa GUI é apenas para verificações simples; para análise avançada, use o fork Enigma da FabricMC disponível em "
				+ "<a href=\"https://github.com/FabricMC/Enigma\" target=\"_blank\">GitHub</a>. Se desejar editar arquivos JAR para criar patches quando o código-fonte não estiver disponível, use o Recaf em "
				+ "<a href=\"https://recaf.coley.software/home.html\" target=\"_blank\">seu site</a>.";
	}

	@Override
	public String botonDescargarCfr() {
		return "Baixar CFR";
	}

	@Override
	public String botonAbrirCarpetaCfr() {
		return "Abrir pasta de instalação";
	}

	@Override
	public String colorFondoPrincipal() {
		return "Cor de fundo principal";
	}

	@Override
	public String colorTextoBotonReset() {
		return "Cor do texto do botão de reiniciar";
	}

	@Override
	public String colorTextoCampoBuscar() {
		return "Cor do texto do campo de busca";
	}

	@Override
	public String colorTextoComboFiltro() {
		return "Cor do texto do menu suspenso de filtro";
	}

	@Override
	public String colorTextoRenderer() {
		return "Cor do texto do renderizador";
	}

	@Override
	public String colorTextoOverlayCarga() {
		return "Cor do texto da sobreposição de carregamento";
	}

	@Override
	public String colorBorde() {
		return "Cor da borda";
	}

	@Override
	public String colorFondoRetrato() {
		return "Cor de fundo no modo retrato";
	}

	@Override
	public String colorEnlaceCompartir() {
		return "Cor do link de compartilhamento";
	}

	@Override
	public String colorFondoCampoCompartir() {
		return "Cor de fundo do campo de compartilhamento";
	}

	@Override
	public String rosaFondo() {
		return "Rosa de fundo";
	}

	@Override
	public String rosaSuave() {
		return "Rosa suave";
	}

	@Override
	public String moradoAcento() {
		return "Roxo de destaque";
	}

	@Override
	public String textoOscuro() {
		return "Texto escuro";
	}

	@Override
	public String bordeSuave() {
		return "Borda suave";
	}

	@Override
	public String fondoCampo() {
		return "Fundo do campo";
	}

	@Override
	public String fondoVistaPrevia() {
		return "Fundo da visualização prévia";
	}

	@Override
	public String sintaxisConstructor() {
		return "Cor de sintaxe: construtor";
	}

	@Override
	public String sintaxisMensajeAyudar() {
		return "Cor de sintaxe: mensagem de ajuda";
	}

	@Override
	public String sintaxisEtiquetasHtml() {
		return "Cor de sintaxe: tags HTML";
	}

	@Override
	public String colorFondoVentana() {
		return "Cor de fundo da janela";
	}

	@Override
	public String colorPanel() {
		return "Cor do painel";
	}

	@Override
	public String colorBotonTexto() {
		return "Cor do texto do botão";
	}

	@Override
	public String colorCampo() {
		return "Cor do campo";
	}

	@Override
	public String colorBordeDestacado() {
		return "Cor da borda destacada";
	}

	@Override
	public String colorSeleccionTexto() {
		return "Cor de fundo da seleção de texto";
	}

	@Override
	public String colorTextoSeleccionado() {
		return "Cor do texto selecionado";
	}

	@Override
	public String colorEstadoExito() {
		return "Cor de estado: sucesso";
	}

	@Override
	public String colorEstadoFallo() {
		return "Cor de estado: falha";
	}

	@Override
	public String colorEstadoInstantanea() {
		return "Cor de estado: instantânea";
	}

	@Override
	public String colorResultadoAnadido() {
		return "Cor de resultado adicionado";
	}

	@Override
	public String colorResultadoEliminado() {
		return "Cor de resultado removido";
	}

	@Override
	public String colorBordeScroll() {
		return "Cor da borda da barra de rolagem";
	}

	@Override
	public String colorFondoPanel() {
		return "Cor de fundo do painel";
	}

	@Override
	public String colorBeigeListas() {
		return "Bege das listas";
	}

	@Override
	public String colorTextoListas() {
		return "Cor do texto nas listas";
	}

	@Override
	public String colorBordeListas() {
		return "Cor da borda das listas";
	}

	@Override
	public String colorBotonFondo() {
		return "Cor de fundo do botão";
	}

	@Override
	public String colorBordeBoton() {
		return "Cor da borda do botão";
	}

	@Override
	public String colorDoradoTexto() {
		return "Cor dourada do texto";
	}

	@Override
	public String colorPila() {
		return "Cor do rastro de pilha (stack trace)";
	}

	@Override
	public String colorTextoPanel() {
		return "Cor do texto do painel";
	}

	@Override
	public String colorTextoNegro() {
		return "Cor de texto preto";
	}

	@Override
	public String colorTextoPrincipal() {
		return "Cor do texto principal";
	}

	@Override
	public String colorFondoResultados() {
		return "Cor de fundo dos resultados";
	}

	@Override
	public String colorEstado() {
		return "Cor de estado";
	}

	@Override
	public String colorTextoDescripcion() {
		return "Cor do texto de descrição";
	}

	@Override
	public String colorTextoEstado() {
		return "Cor do texto de estado";
	}

	@Override
	public String colorTextoExtra() {
		return "Cor do texto extra";
	}

	@Override
	public String colorSeparador() {
		return "Cor do separador";
	}

	@Override
	public String problema_safe_fetch32_jdk17() {
		return "Foi detectado um erro nativo <code>StubRoutines::SafeFetch32</code>. "
				+ "Esse problema ocorre no macOS com o JDK 17.0.9 e foi corrigido no JDK 17.0.10 ou posterior. https://github.com/async-profiler/async-profiler/issues/747 https://www.minecraftforum.net/forums/support/java-edition-support/3192465-issues-with-jre-17-0-9-in-macos-sonoma-14-2-1";
	}

	@Override
	public String nombre_problema_safe_fetch32_jdk17() {
		return "Erro nativo SafeFetch32 no JDK 17.0.9 (macOS)";
	}

	@Override
	public String solucion_actualizar_jdk_macos() {
		return "Atualize seu JDK para a versão 17.0.10 ou superior (por exemplo, 17.0.15).";
	}

	@Override
	public String solucion_usar_lanzador_con_jdk_actualizado() {
		return "Se você usa um launcher como MultiMC, Prism Launcher ou TLauncher, configure-o para usar um JDK mais recente. "
				+ "Alguns já incluem o JDK 17.0.15 integrado.";
	}

	@Override
	public String solucion_desactivar_spark_mod() {
		return "O mod Spark também pode contribuir para esse erro. Considere desativá-lo temporariamente. https://www.reddit.com/r/fabricmc/comments/17snlol/game_crashing_when_creating_world/";
	}

	@Override
	public String problema_mcef_inicializacion_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "O mod MCEF (Chromium Embedded Framework) está causando um travamento silencioso.</b>" + "<ul>"
				+ "<li>O MCEF está sendo inicializado no final do log, o que normalmente indica que o jogo travou durante o carregamento.</li>"
				+ "<li>Este mod é conhecido por causar falhas em sistemas Linux, macOS ou com certas versões do Java.</li>"
				+ "<li>Nem sempre aparece um erro explícito, mas o jogo nunca chega ao menu principal.</li>" + "</ul>"
				+ "<p>Se você não precisa da funcionalidade de navegador no jogo (como mapas web ou páginas embutidas), remova o mod.</p>";
	}

	@Override
	public String nombre_problema_mcef_inicializacion() {
		return "Problema de inicialização do MCEF (mod de navegador embutido)";
	}

	@Override
	public String solucion_eliminar_mod_mcef() {
		return "Remova o arquivo do mod MCEF (procure por 'mcef' no nome do arquivo) da pasta 'mods'.";
	}

	@Override
	public String solucion_verificar_compatibilidad_mcef() {
		return "Se realmente precisar dele, certifique-se de usar uma versão compatível com seu sistema operacional e versão do Minecraft.";
	}

	@Override
	public String conflicto_iris_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito entre <b>OptiFine</b> e <b>Iris/Oculus</b>.</b>" + "<ul>"
				+ "<li>O OptiFine modifica a renderização do Minecraft de forma incompatível com o Iris ou Oculus.</li>"
				+ "<li>O erro <code>MixinLevelRenderer failed injection check</code> vem de <code>mixins.iris.json</code> ou <code>mixins.oculus.json</code>.</li>"
				+ "</ul>"
				+ "<p>Esses mods não podem ser usados juntos. Remova o OptiFine para usar shaders com Iris ou Oculus.</p>";
	}

	@Override
	public String nombre_conflicto_iris_optifine() {
		return "Conflito entre OptiFine e Iris/Oculus";
	}

	@Override
	public String solucion_eliminar_optifine() {
		return "Remova o arquivo do OptiFine da pasta 'mods'.";
	}

	@Override
	public String solucion_usar_iris_sin_optifine() {
		return "Use o Iris ou Oculus sem o OptiFine para shaders modernos.";
	}

	@Override
	public String conflicto_modernfix_optifine_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um conflito entre <b>ModernFix</b> e <b>OptiFine</b>.</b>" + "<ul>"
				+ "<li>ModernFix não é compatível com OptiFine porque quebra funcionalidades do Forge e deixa a inicialização mais lenta.</li>"
				+ "<li>O próprio ModernFix avisa: <i>\"Use of ModernFix with OptiFine is not supported\"</i>.</li>"
				+ "</ul>" + "<p>Você deve remover um dos dois mods para que o jogo funcione corretamente.</p>";
	}

	@Override
	public String nombre_conflicto_modernfix_optifine() {
		return "Conflito entre ModernFix e OptiFine";
	}

	@Override
	public String solucion_eliminar_optifine_o_modernfix() {
		return "Remova o OptiFinite ou o ModernFix da pasta 'mods'. Eles não podem ser usados juntos.";
	}

	@Override
	public String solucion_usar_alternativa_modernfix() {
		return "Se precisar de otimizações, considere usar apenas o OptiFine ou substituir o ModernFix por mods mais leves, como FerriteCore ou EntityCulling.";
	}

	@Override
	public String error_clave_registro_mayusculas_html(String clave) {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Erro: chave de registro inválida com caracteres não permitidos.</b>" + "<ul>"
				+ "<li><b>Chave detectada:</b> <code>" + escapeHtml(clave) + "</code></li>"
				+ "<li>No Minecraft, todas as chaves de registro (tags, receitas, conquistas, etc.) devem estar em <b>letras minúsculas</b> e usar apenas letras, números, sublinhados, hifens e barras.</li>"
				+ "<li>Esse erro geralmente é causado por um mod mal programado ou um datapack defeituoso.</li>"
				+ "</ul>"
				+ "<p><b>Dica importante:</b> Use a ferramenta <b>grepr</b> ou <b>fgrepr</b> na barra lateral e ative a opção <b>\"Buscar em arquivos JAR\"</b> para identificar qual mod contém essa chave inválida.</p>";
	}

	@Override
	public String nombre_error_clave_registro_mayusculas() {
		return "Chave de registro com maiúsculas ou caracteres inválidos";
	}

	@Override
	public String solucion_buscar_clave_en_archivos() {
		return "Use 'grepr' ou 'fgrepr' com \"Buscar em arquivos JAR\" para localizar o mod causador.";
	}

	@Override
	public String solucion_eliminar_mod_reciente() {
		return "Se não conseguir identificar o mod, remova mods recentes, especialmente os que adicionam blocos, itens ou ferramentas.";
	}

	@Override
	public String error_entrypoint_fabric_html(String modNombre) {
		return "<b style='color:#" + config.obtenerColorError() + "'>" + "Erro ao carregar o mod <b>"
				+ escapeHtml(modNombre) + "</b>.</b>" + "<ul>"
				+ "<li>O mod falhou ao inicializar um de seus componentes (por exemplo, o menu de configurações).</li>"
				+ "<li>Isso geralmente ocorre por incompatibilidade com a versão do Minecraft, Fabric ou com outros mods.</li>"
				+ "</ul>" + "<p>Se o erro persistir, remova ou atualize o mod <b>" + escapeHtml(modNombre)
				+ "</b>.</p>";
	}

	@Override
	public String nombre_error_entrypoint_fabric() {
		return "Erro de inicialização de mod (Fabric Entrypoint)";
	}

	@Override
	public String solucion_eliminar_mod(String modNombre) {
		return "Remova o mod '" + modNombre + "' da pasta 'mods'.";
	}

	@Override
	public String solucion_actualizar_mod(String modNombre) {
		return "Atualize o mod '" + modNombre + "' para uma versão compatível com sua instalação.";
	}

	@Override
	public String error_en_garde_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um erro relacionado ao mod <b>En Garde!</b>.</b>" + "<ul>"
				+ "<li>Este mod adiciona mecânicas de combate corpo a corpo (parry, bloqueio, etc.).</li>"
				+ "<li>O erro geralmente ocorre por incompatibilidade com outros mods de combate (como Epic Fight, DualRiders, etc.) ou por usar uma versão incorreta para o seu Minecraft.</li>"
				+ "</ul>"
				+ "<p>Se você não usa combate avançado, considere remover o En Garde! para evitar conflitos.</p>";
	}

	@Override
	public String nombre_error_en_garde() {
		return "Erro no mod En Garde!";
	}

	@Override
	public String solucion_actualizar_en_garde() {
		return "Certifique-se de usar a versão do En Garde! compatível com sua versão do Minecraft e seu carregador (Fabric/Forge).";
	}

	@Override
	public String solucion_eliminar_conflicto_mod_combate() {
		return "Se você usa outros mods de combate (Epic Fight, Caelus, etc.), desative-os ou remova o En Garde! para evitar conflitos.";
	}

	@Override
	public String error_idletweaks_html() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um erro causado pelo mod <b>IdleTweaks</b>.</b>" + "<ul>"
				+ "<li>O IdleTweaks tentou liberar um canal de rede que já não existe (<code>Tried to release unknown channel</code>).</li>"
				+ "<li>Esse erro geralmente ocorre em versões antigas do mod ou ao usá-lo em servidores mal configurados.</li>"
				+ "</ul>"
				+ "<p>IdleTweaks é um mod de qualidade de vida, mas pode causar instabilidade. Considere atualizá-lo ou removê-lo.</p>";
	}

	@Override
	public String nombre_error_idletweaks() {
		return "Erro no IdleTweaks (canal de rede desconhecido)";
	}

	@Override
	public String solucion_actualizar_idletweaks() {
		return "Atualize o IdleTweaks para a versão mais recente compatível com seu Minecraft.";
	}

	@Override
	public String solucion_eliminar_idletweaks() {
		return "Remova o IdleTweaks da pasta 'mods' se não for necessário.";
	}

	@Override
	public String mensagjePirataMC() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foi detectado um erro de autenticação (HTTP 401) ao tentar fazer login no Minecraft.</b>"
				+ "<p>Esse erro <b>raramente é a causa direta da falha</b>, mas indica que você está usando uma conta não autenticada (pirata).</p>"
				+ "<p>Canais oficiais de suporte (projetos corporativos, VTubers, criadores de modpacks, etc.) <b>não podem ajudá-lo</b> se você usar uma cópia pirata, "
				+ "devido a restrições em suas regras de chat, contratos, acordos com Mojang/Microsoft ou políticas de reputação.</p>"
				+ "<p>Essa verificação pode ser <b>desativada nas configurações corporativas</b> do detector. "
				+ "Aviso: a detecção antipirataria <b>não é perfeita</b> e pode ser acionada em ambientes de desenvolvimento, com internet instável ou ao usar launchers modificados.</p>";
	}

	@Override
	public String infoDeDerechosMiranda() {
		return "<b>Direitos Miranda se você tentar entrar no suporte mesmo assim:</b>";
	}

	@Override
	public String nombrePirataMC() {
		return "Minecraft pirata";
	}

	@Override
	public String desactivarVerificacionPirata() {
		return "Desativar verificação antipirataria";
	}

	@Override
	public String comprarMC() {
		return "Comprar Minecraft";
	}

	// --- LanzerNoAnimado ---
	@Override
	public String lanzer_no_animado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>" + "Você está usando o launcher <code>" + id
				+ "</code>, que <b>não está na lista de launchers recomendados</b>.</b>";
	}

	@Override
	public String lanzer_no_animado_problemas_comunes() {
		return "<p>Embora possa funcionar, launchers não recomendados costumam causar:</p>" + "<ul>"
				+ "<li>Instalações corrompidas de mods ou do aplicativo.</li>"
				+ "<li>O jogo não inicia ou trava sem erro claro.</li>"
				+ "<li>Estrutura de pastas incomum (dificulta o diagnóstico).</li>"
				+ "<li>Comportamento imprevisível com Java, memória ou mods.</li>" + "</ul>";
	}

	@Override
	public String lanzer_no_animado_usar_animados() {
		return "Para uma melhor experiência, use um dos seguintes launchers recomendados:";
	}

	@Override
	public String nombre_lanzer_no_animado() {
		return "Launcher não recomendado";
	}

	@Override
	public String lanzer_no_animado_cambiar_a_animado() {
		return "Mude para um launcher da lista recomendada.";
	}

	// --- LanzerDesAnimado ---
	@Override
	public String lanzer_desanimado_titulo(String id) {
		return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
				+ "Você está usando um <b>launcher desaconselhado</b>: <code>" + id + "</code>.</b>";
	}

	@Override
	public String lanzer_desanimado_problemas_comunes() {
		return "<p>Launchers desaconselhados podem causar:</p>" + "<ul>"
				+ "<li>Instalações corrompidas do aplicativo ou mods.</li>"
				+ "<li>O jogo não inicia ou falha silenciosamente.</li>"
				+ "<li>Organização incomum de arquivos (difícil depurar).</li>"
				+ "<li>Incerteza sobre como ele gerencia mods, Java ou memória.</li>" + "</ul>";
	}

	@Override
	public String lanzer_desanimado_usar_animados() {
		return "Recomenda-se vivamente usar um dos seguintes launchers:";
	}

	@Override
	public String nombre_lanzer_desanimado() {
		return "Launcher desaconselhado";
	}

	@Override
	public String lanzer_desanimado_cambiar_lanzer() {
		return "Mude para um launcher recomendado para receber suporte.";
	}

	// --- FaltaModAnimado ---
	@Override
	public String falta_mod_animado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Faltam mods recomendados para este ambiente.</b>";
	}

	@Override
	public String nombre_falta_mod_animado() {
		return "Mods recomendados ausentes";
	}

	@Override
	public String falta_mod_animado_instalar() {
		return "Instale os mods recomendados para uma experiência ideal.";
	}

	// --- TienesModDesAnimado ---
	@Override
	public String tienes_mod_desanimado_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Foram detectados mods desaconselhados na sua instalação.</b>";
	}

	@Override
	public String nombre_tienes_mod_desanimado() {
		return "Mods desaconselhados detectados";
	}

	@Override
	public String tienes_mod_desanimado_eliminar() {
		return "Remova os mods desaconselhados para evitar problemas.";
	}

	@Override
	public String antimanipulacion_titulo() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "Modificação não autorizada detectada em arquivos críticos. Você editou arquivos manualmente ou está usando um launcher não confiável.</b>";
	}

	@Override
	public String nombre_antimanipulacion() {
		return "Manipulação detectada";
	}

	@Override
	public String antimanipulacion_reinstalar() {
		return "Reinstale os arquivos originais para restaurar a integridade.";
	}

	@Override
	public String configuracionCorporativa() {
		return "تنظیمات شرکتی";
	}

	@Override
	public String idiomaRespaldo() {
		return "زبان پشتیبان";
	}

	@Override
	public String buscardorHabilitado() {
		return "فعال‌سازی جستجوگر";
	}

	@Override
	public String nombreHerramienta() {
		return "نام ابزار";
	}

	@Override
	public String condenarPirateria() {
		return "محکومیت دزدی نرم‌افزاری";
	}

	@Override
	public String lanzadoresRecomendados() {
		return "راه‌اندازهای توصیه‌شده";
	}

	@Override
	public String lanzadoresDesaconsejados() {
		return "راه‌اندازهای نامناسب";
	}

	@Override
	public String modsRecomendados() {
		return "افزونه‌های توصیه‌شده";
	}

	@Override
	public String modsDesaconsejados() {
		return "افزونه‌های نامناسب";
	}

	@Override
	public String antiTamper() {
		return "ضد دستکاری";
	}

	@Override
	public String proximamente() {
		return "به زودی";
	}

	@Override
	public String informacion() {
		return "اطلاعات";
	}

	@Override
	public String errorCargandoImagen() {
		return "خطا در بارگذاری تصویر";
	}

	@Override
	public String configuracionBasica() {
		return "Configurações Básicas";
	}

	@Override
	public String funcionalidades() {
		return "Funcionalidades";
	}

	@Override
	public String derechosMiranda() {
		return "Direitos Miranda (ALTAMENTE recomendados)";
	}

	@Override
	public String gestionVerificaciones() {
		return "Gestão de Verificações";
	}

	@Override
	public String idVerificacion() {
		return "ID";
	}

	@Override
	public String nombreVerificacion() {
		return "Nome";
	}

	@Override
	public String codigoVerificacion() {
		return "Código";
	}

	@Override
	public String documentacionVerificacion() {
		return "Documentação";
	}

	@Override
	public String verificacionesHabilitadas() {
		return "Verificações Habilitadas:";
	}

	@Override
	public String verificacionesDeshabilitadas() {
		return "Verificações Desabilitadas:";
	}

	@Override
	public String deshabilitarNoCorporativas() {
		return "Desabilitar todas as não corporativas";
	}

	@Override
	public String verCodigo() {
		return "Ver Código";
	}

	@Override
	public String verDocumentacion() {
		return "Ver Documentação";
	}

	@Override
	public String seleccionaVerificacionDeshabilitar() {
		return "Selecione uma verificação para desabilitar.";
	}

	@Override
	public String seleccionaVerificacionHabilitar() {
		return "Selecione uma verificação para habilitar.";
	}

	@Override
	public String verificacionesNoCorporativasDeshabilitadas() {
		return "%d verificações não recomendadas para uso corporativo foram desabilitadas.";
	}

	@Override
	public String noVerificacionesNoCorporativas() {
		return "Não há verificações não corporativas para desabilitar.";
	}

	@Override
	public String operacionCompletada() {
		return "Operação concluída";
	}

	@Override
	public String mensajeAmaneKanata() {
		return "Sentimos sua falta, Amane Kanata";
	}

	@Override
	public String colorVerificacionCorporativa() {
		return "Cor de Verificação Corporativa";
	}

	@Override
	public String nombreLanzador() {
		return "Nome do Launcher";
	}

	@Override
	public String motivo() {
		return "Motivo";
	}

	@Override
	public String lanzadoresNoRecomendados() {
		return "Launchers Desaconselhados";
	}

	@Override
	public String moverADesaconsejados() {
		return "Desaconselhar";
	}

	@Override
	public String moverARecomendados() {
		return "Recomendar";
	}

	@Override
	public String guardarCambios() {
		return "Salvar Alterações";
	}

	@Override
	public String cancelar() {
		return "Cancelar";
	}

	@Override
	public String seleccionaLanzadorMover() {
		return "Por favor, selecione um launcher para mover.";
	}

	@Override
	public String cambiosGuardadosExitosamente() {
		return "As alterações foram salvas com sucesso!";
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
		return "Motivos";
	}

	@Override
	public String agregarLanzador() {
		return "Adicionar launcher";
	}

	@Override
	public String quitarLanzador() {
		return "Remover launcher";
	}

	@Override
	public String editarRazones() {
		return "Editar motivos";
	}

	@Override
	public String seleccionaLanzadorQuitar() {
		return "Selecione um launcher para remover.";
	}

	@Override
	public String seleccionaLanzadorEditar() {
		return "Selecione um launcher para editar.";
	}

	@Override
	public String editarRazonesPara(String idLanzador) {
		return "Editar motivos para " + idLanzador;
	}

	@Override
	public String agregarNuevoIdioma() {
		return "Adicionar novo idioma";
	}

	@Override
	public String aceptar() {
		return "Aceitar";
	}

	@Override
	public String seleccionaCodigoIdioma() {
		return "Selecione o idioma";
	}

	@Override
	public String lanzadoresRecomendadosAviso() {
		return "Esses são os launchers que o CrashDetector recomenda como bons.";
	}

	@Override
	public String colorResultadoCorrecto() {
		return "Resultado correto";
	}

	public String modsNoRecomendados() {
		return "Mods Desaconselhados";
	}

	public String agregarMod() {
		return "Adicionar mod";
	}

	public String quitarMod() {
		return "Remover mod";
	}

	public String modId() {
		return "ID do mod / Nome do JBoss Modules";
	}

	public String rutaMod() {
		return "Caminho / arquivo do mod";
	}

	public String errorDebeIndicarMod() {
		return "Você deve informar pelo menos o modid ou o caminho do mod.";
	}

	public String modsNoRecomendadosAviso() {
		return "Aqui você pode registrar mods desaconselhados para que o CrashDetector os detecte caso estejam instalados.";
	}

	@Override
	public String anularNormal() {
		return "Desativar Normal";
	}

	@Override
	public String anularNormalDescripcion() {
		return "O CrashDetector deve avisar mesmo que não ocorra um crash real.";
	}

	@Override
	public String modsRecomendadosAviso() {
		return "Registre os mods que o CrashDetector recomenda. Se estiverem ausentes, o CrashDetector poderá avisar.";
	}

	@Override
	public String descripcionDerechosPirateria() {
		return "" + "Se você decidir ativar o aviso antipirataria, recomenda-se definir aqui "
				+ "os direitos da pessoa que solicita suporte, como medida preventiva.\n\n"

				+ "Ao contrário do que se acredita comumente, muitas comunidades e canais de suporte populares "
				+ "NÃO exigem que avisos antipirataria estejam ativados para fornecer ajuda. No entanto, "
				+ "documentar esses direitos pode ser útil caso alguém acesse o canal de suporte mesmo assim.\n\n"

				+ "Você pode se basear em documentos oficiais, como o Manual de Direitos Básicos do Preso "
				+ "no México:\n"
				+ "https://www.gob.mx/cms/uploads/attachment/file/342687/Cartilla_Derechos_b_sicos_del_detenido.pdf\n\n"

				+ "Assim como em princípios legais comparáveis usados em outros países, incluindo "
				+ "Estados Unidos, Federação Russa, República Popular da China, República Islâmica "
				+ "do Irã e República Popular Democrática da Coreia.\n\n"

				+ "Alguns exemplos de direitos que podem ser incluídos são:\n"
				+ "• O direito de não fornecer informações desnecessárias para o suporte, como o launcher usado, "
				+ "nome de usuário ou UUID.\n" + "• O direito de não se autoincriminar.\n"
				+ "• O direito de recusar responder perguntas que não sejam necessárias para resolver o problema.\n"
				+ "• O direito de receber orientação dentro do chat.\n"
				+ "• O direito de usar o recurso integrado de anonimização de logs do CrashDetector.\n\n"

				+ "Este texto aceita conteúdo HTML.";
	}

	@Override
	public String editar() {
		return "Editar";
	}

	@Override
	public String advertenciaHashLento() {
		return "Aviso: adicionar muitos arquivos grandes pode fazer com que a verificação leve vários minutos. O CrashDetector precisará calcular o hash de cada arquivo antes de continuar. Recomenda-se proteger apenas os arquivos estritamente necessários.";
	}

	@Override
	public String agregarArchivo() {
		return "Adicionar arquivo";
	}

	@Override
	public String agregarCarpeta() {
		return "Adicionar pasta";
	}

	@Override
	public String quitar() {
		return "Remover";
	}

	@Override
	public String rutaArchivo() {
		return "Caminho do arquivo";
	}

	@Override
	public String errorRutaFueraDirectorio() {
		return "O caminho selecionado está fora do diretório atual do jogo. Apenas arquivos e pastas dentro do diretório atual ou seus subdiretórios são permitidos.";
	}

	@Override
	public String mensajeDeSylentBell() {
		return "<html><div style='width:150px; text-align:center;'>"
				+ "As opiniões e comentários de Sylent Bell não refletem necessariamente as nossas; "
				+ "achamos apenas que seria engraçado colocá-la aqui. CrashDetector é secular." + "</div></html>";
	}

	@Override
	public String gmlIPV6() {
		return "<b style='color:#" + config.obtenerColorError() + "'>"
				+ "O mod GML (Groovy ModLoader) requer essas alterações e é a causa mais comum deste problema.</b>";
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
				+ "Foi detectado o uso do <i>Independente Flywheel</i>.</b>"
				+ "<p><b>O Independente Flywheel está obsoleto (descontinuado)</b> e não deve ser usado em versões modernas.</p>"
				+ "<p>As versões atuais do <b>Create</b> <b>já incluem o Flywheel</b>, então instalá-lo separadamente "
				+ "causa conflitos de compatibilidade e erros de carregamento.</p>"
				+ "<p>Alguns mods que dependem explicitamente do Independent Flywheel podem "
				+ "<b>não funcionar</b> ou <b>funcionar de forma instável</b>. "
				+ "Em certos casos avançados, esses mods podem funcionar se você "
				+ "<b>editar manualmente o arquivo <code>mods.toml</code></b> para ajustar os intervalos de versão, "
				+ "embora isso <b>não seja recomendado</b>.</p>"
				+ (mods.isEmpty() ? ""
						: "<p><b>Mods detectados que fazem referência ao Flywheel:</b></p>" + "<ul>"
								+ listaMods.toString() + "</ul>")
				+ "<p>A solução recomendada é <b>remover o Independente Flywheel</b> e usar apenas "
				+ "a versão incluída no Create.</p>";

		return mensaje;
	}

	@Override
	public String nombreIndependenteFlywheel() {
		return "Flywheel Independente";
	}

	@Override
	public String mensajeFloralEnchantments() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Foi detectado um erro relacionado ao mod <i>Floral Enchantments</i>.</b>"
				+ "<p>O crash é causado por uma falha interna do mod ao manipular dados do jogo, "
				+ "o que gera uma <b>NullPointerException</b> durante a execução.</p>"
				+ "<p>Esse problema geralmente é resolvido atualizando ou removendo o mod.</p>";
	}

	@Override
	public String nombreFloralEnchantments() {
		return "Erro do Floral Enchantments";
	}

	@Override
	public String mixinExtrasDuplicados() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorAdvertencia() + "'>"
				+ "Você tem a versão NeoForge e a versão normal do MixinExtras instaladas. Se estiver usando MinecraftForge, pode instalar a correção em <a href='https://www.curseforge.com/minecraft/mc-mods/mixin-extras-neoforge-on-forge-fix' style='color: inherit;'>este link</a>.</b>";
	}

	@Override
	public String mensajeIrisSombrasTerreno() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Foi detectado um erro nas sombras do terreno com shaders (Iris).</b>"
				+ "<p>O problema ocorre durante a renderização do terreno.</p>"
				+ "<p>Recomenda-se <b>tentar jogar sem shaders</b> ou reduzir a qualidade gráfica, "
				+ "especialmente em configurações <b>Ultra</b>.</p>";
	}

	@Override
	public String nombreIrisSombrasTerreno() {
		return "Sombras do terreno (Iris)";
	}

	@Override
	public String mensajeTickLargoServidor() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Um tick do servidor excessivamente longo foi detectado.</b>"
				+ "<p>Isso indica que o jogo ficou travado por muito tempo em um único tick.</p>"
				+ "<p>Recomenda-se <b>verificar o thread dump</b> gerado no log para identificar a causa.</p>"
				+ "<p>A <b>Análise de Stack Trace</b> pode ajudá-lo a localizar a origem do travamento.</p>"
				+ "<p>Além disso, o botão <b>Ver no log</b> destacará em vermelho os mods possivelmente responsáveis, "
				+ "bem como entradas cercadas por <code>$modid$</code>, que geralmente indicam a origem do problema. Para análise em tempo real, recomendamos usar o amostrador de CPU no VisualVM. Certifique-se de que seu servidor ou computador seja potente o suficiente para lidar com os mods que você está usando — é possível que todos os seus mods funcionem corretamente, mas você tenha muitos deles.</p>";
	}

	@Override
	public String nombreTickLargoServidor() {
		return "Tick longo do servidor";
	}

	@Override
	public String tituloLFPDPPP() {
		return "Lei Federal de Proteção de Dados Pessoais em Posse de Particulares";
	}

	@Override
	public String aceptarPermanentemente() {
		return "Aceitar permanentemente";
	}

	public String mensajeAdvertenciaIdiomaCoreano() {
		return "A tradução coreana contém gírias do sul que devem ser evitadas para cumprir a lei. "
				+ "O uso de linguagem estrangeira, especialmente proveniente do Sul, é estritamente proibido "
				+ "pela Lei de Proteção da Língua Cultural de Pyongyang.";
	}

	public String enlaceDocumentacionIdiomaCoreano() {
		return "Para mais informações, consulte o documento oficial da lei: "
				+ "<a href='https://www.dailynk.com/english/wp-content/uploads/sites/2/2023/03/Pyongyang-Cultural-Language-Protection-Act_English-and-Korean-Versions_Daily-NK.pdf'>Lei de Proteção da Língua Cultural de Pyongyang</a>";
	}

	public String leerLeyCompleta() {
		return "Ler Lei Completa";
	}

	public String errorAbriendoEnlace() {
		return "Erro ao abrir o link";
	}

	public String actaProteccionIdiomaCultural() {
		return "Lei de Proteção da Língua Cultural de Pyongyang";
	}

	@Override
	public String canarioTitulo() {
		return "Canário de Ordem Judicial";
	}

	@Override
	public String canario1984Titulo() {
		return "1984 — Monitor de Vigilância";
	}

	@Override
	public String revisar() {
		return "Verificar";
	}

	@Override
	public String cerrar() {
		return "Fechar";
	}

	@Override
	public String canarioTodoSeguro() {
		return "Todos os serviços relatam status seguro.";
	}

	@Override
	public String canarioComprometido(int c) {
		return "ALERTA: " + c + " serviço(s) relatam status inseguro.";
	}

	@Override
	public String colorAlerta() {
		return "Cor de alerta";
	}

	public String opcionesMunidiales() {
		return "Opções Munidial";
	}

	public String consentimientoLFPDPPP() {
		return "Consentimento LFPDPPP";
	}

	public String habilitarTokenAccesoEnEntregar() {
		return "Habilitar handoff do token de acesso em Handoff para ReLauncher (desaconselhado).";
	}

	public String consolaDesarrollo() {
		return "Console de desenvolvimento";
	}

	public String mundial() {
		return "Global";
	}

	public String ningun() {
		return "Nenhum";
	}

	// Consola del desarrollador
	public String consolaDesarrollador() {
		return "Console do desenvolvedor";
	}

	public String bajar() {
		return "Baixar";
	}

	public String logsSoporte() {
		return "Logs de suporte";
	}

	public String detenerProceso() {
		return "Parar processo";
	}

	// Menú contextual
	public String copiarSeleccion() {
		return "Copiar seleção";
	}

	public String seleccionarTodo() {
		return "Selecionar tudo";
	}

	public String copiarTodo() {
		return "Copiar tudo";
	}

	public String guardarTodoComoArchivo() {
		return "Salvar tudo como arquivo";
	}

	public String obtenerEnlaceSoporte() {
		return "Obter link de suporte";
	}

	public String borrarTodo() {
		return "Limpar tudo";
	}

	// Colores configurables
	public String colorFondoConsola() {
		return "Cor de fundo do console";
	}

	public String colorTextoConsola() {
		return "Cor do texto do console";
	}

	// Consentimiento / logs
	public String consentimientoConfirmadoPendienteImplementacion() {
		return "Consentimento confirmado.\nA integração para compartilhar logs será implementada aqui.";
	}

	@Override
	public String usarSakuraOriginal() {
		return "Usar a imagem original do Sakura Riddle";
	}

	@Override
	public String canario1984Descripcion() {
		return "" + "Um \"warrant canary\" é um mecanismo de transparência.\n\n"
				+ "Enquanto esta mensagem existir e os serviços aparecerem como seguros, "
				+ "significa que o projeto NÃO recebeu ordens judiciais secretas, "
				+ "solicitações de censura nem pedidos legais de vigilância.\n\n"
				+ "Se algum canário desaparecer ou for marcado como inseguro, "
				+ "isso indica que houve uma mudança legal.\n\n"
				+ "Este painel verifica todos os canários registrados no sistema e exibe " + "seu estado atual.\n\n"
				+ "Clique em \"Verificar\" para atualizar os estados.";
	}

	@Override
	public String confirmacionReEstablarTodos() {
		return "Redefinir todas as opções para os valores padrão?";
	}

	@Override
	public String gui() {
		return "GUI";
	}

	@Override
	public String sinOpciones() {
		return "Sem opções";
	}

	@Override
	public String seleccionaColor() {
		return "Selecionar cor";
	}

	@Override
	public String botonMostrarGUI() {
		return "Mostrar GUI";
	}

	@Override
	public String botonGuardarTodo() {
		return "Salvar tudo";
	}

	@Override
	public String botonRestablecerTodo() {
		return "Redefinir tudo";
	}

	@Override
	public String nombreLuckPermsNoCargado() {
		return "LuckPerms não carregado";
	}

	@Override
	public String mensajeLuckPermsNoCargado() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Foi detectado um erro ao acessar a API do LuckPerms.</b>"
				+ "<p>A mensagem indica que o <b>LuckPerms não estava carregado</b> no momento em que outro plugin tentou usá-lo.</p>"
				+ "<p><b>Causas possíveis:</b></p>" + "<ul>"
				+ "<li>O plugin <b>LuckPerms não está instalado</b> ou <b>falhou ao iniciar</b>.</li>"
				+ "<li>Outro plugin está tentando acessar o LuckPerms <b>muito cedo</b> ou de <b>forma incorreta</b>.</li>"
				+ "</ul>" + "<p>Recomenda-se <b>verificar o console</b> usando o link para identificar "
				+ "o plugin que está chamando o LuckPerms e verificar sua compatibilidade.</p>";
	}

	@Override
	public String nombreIrisShaderpackNoEncontrado() {
		return "Shaderpack do Iris não carregado";
	}

	@Override
	public String mensajeIrisShaderpackNoEncontrado(String shaderpack) {
		String nombre = shaderpack == null || shaderpack.isEmpty() ? "desconhecido" : shaderpack;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Foi detectado um erro ao carregar um shaderpack com Iris/Oculus.</b>"
				+ "<p><b>Shaderpack afetado:</b> " + nombre + "</p>"
				+ "<p>O Minecraft não conseguiu abrir o arquivo do shaderpack (FileSystemNotFoundException).</p>"
				+ "<p><b>Soluções possíveis:</b></p>" + "<ul>"
				+ "<li>Verifique se o shaderpack está corretamente instalado na pasta <b>shaderpacks</b>.</li>"
				+ "<li>Baixe novamente o shaderpack, pois o arquivo pode estar corrompido.</li>"
				+ "<li>Se o problema persistir, exclua o arquivo <b>config/iris.properties</b> para redefinir a configuração do Iris.</li>"
				+ "</ul>" + "<p>Após aplicar as alterações, reinicie o jogo.</p>";
	}

	@Override
	public String nombreNightConfigNoSePuedeEscribir() {
		return "Não foi possível escrever o arquivo de configuração";
	}

	@Override
	public String mensajeNightConfigNoSePuedeEscribir(String ruta) {
		String archivo = ruta == null || ruta.isEmpty() ? "desconhecido" : ruta;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ocorreu um erro ao salvar um arquivo de configuração.</b>" + "<p><b>Arquivo afetado:</b> " + archivo
				+ "</p>" + "<p>O Minecraft não pôde escrever o arquivo usando gravação atômica (REPLACE_ATOMIC).</p>"
				+ "<p><b>Isso geralmente ocorre por:</b></p>" + "<ul>"
				+ "<li>Permissões incorretas na pasta ou no arquivo.</li>"
				+ "<li>O arquivo está marcado como somente leitura.</li>"
				+ "<li>Outro programa (antivírus, backup, editor) está bloqueando o arquivo.</li>" + "</ul>"
				+ "<p><b>Recomendações:</b></p>" + "<ul>"
				+ "<li>Verifique se você tem permissões de escrita na pasta.</li>"
				+ "<li>Remova o atributo de somente leitura do arquivo.</li>"
				+ "<li>Feche programas que possam estar usando esse arquivo.</li>" + "</ul>";
	}

	@Override
	public String nombreAccesoDenegadoBackupConfig() {
		return "Acesso negado ao criar backup";
	}

	@Override
	public String mensajeAccesoDenegadoBackupConfig(String origen, String backup) {
		String src = origen == null || origen.isEmpty() ? "desconhecido" : origen;
		String dst = backup == null || backup.isEmpty() ? "desconhecido" : backup;
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Ocorreu um erro de permissões ao criar um backup do arquivo de configuração.</b>"
				+ "<p><b>Arquivo original:</b> " + src + "</p>" + "<p><b>Arquivo de backup:</b> " + dst + "</p>"
				+ "<p>O sistema operacional bloqueou o acesso durante o salvamento do arquivo.</p>"
				+ "<p><b>Isso geralmente ocorre por:</b></p>" + "<ul>" + "<li>Permissões insuficientes na pasta.</li>"
				+ "<li>O arquivo está marcado como somente leitura.</li>"
				+ "<li>Outro programa (antivírus, ferramenta de sincronização, editor) está usando o arquivo.</li>"
				+ "</ul>" + "<p><b>Recomendações:</b></p>" + "<ul>"
				+ "<li>Verifique as permissões da pasta <b>config</b>.</li>"
				+ "<li>Feche programas que possam estar acessando o arquivo.</li>"
				+ "<li>Tente iniciar o launcher ou o Minecraft como administrador.</li>" + "</ul>";
	}

	@Override
	public String cdlauncherHabilitarConsola() {
		return "Habilitar Console";
	}

	@Override
	public String cdlauncherDescripcionCompleta() {
		return "" + "<b>Ferramentas de Depuração</b><br><br>"
				+ "Aqui você pode ativar recursos avançados para depurar o CrashDetector e seus jogos.<br><br>"
				+ "Recomenda-se habilitar o console de desenvolvimento para obter informações detalhadas, rastros e diagnósticos durante a análise.<br><br>"
				+ "Se você precisar testar um servidor multiplayer no modo online, pode ser necessário permitir a transferência do token de acesso ao processo do CrashDetector nas configurações de privacidade. "
				+ "Isso geralmente <b>não é recomendado</b> em outros casos.<br><br>"
				+ "Instruções completas: <a href='https://example.com'>Link!</a>";
	}

	@Override
	public String nombreSimpleCloudsIncompatibilidadShaders() {
		return "Incompatibilidade: Simple Clouds vs Shaders";
	}

	@Override
	public String mensajeSimpleCloudsIncompatibilidadShaders() {
		return "<b style='color:#" + Config.obtenerInstancia().obtenerColorError() + "'>"
				+ "Incompatibilidade detectada entre Simple Clouds e os shaders.</b>"
				+ "<p>O Simple Clouds não é compatível com mods de sombra (Iris/Oculus) quando o Distant Horizons está instalado.</p>"
				+ "<p><b>Opções recomendadas:</b></p>" + "<ul>"
				+ "<li>Remova o <b>Simple Clouds</b> se quiser usar shaders.</li>"
				+ "<li>Ou então, desinstale o <b>Iris ou Oculus</b> se preferir manter o Simple Clouds.</li>" + "</ul>"
				+ "<p>Essa limitação vem do próprio mod Simple Clouds e não pode ser resolvida sem modificar seu código.</p>";
	}

	@Override
	public String colorBotonBaraLateral() {
		return "Cor do botão da barra lateral";
	}

	@Override
	public String profilerTitulo() {
		return "Analisador de Desempenho";
	}

	@Override
	public String profilerDescripcion() {
		return "Ferramenta de análise de desempenho baseada em instrumentação e amostragem.";
	}

	@Override
	public String profilerIniciar() {
		return "Iniciar";
	}

	@Override
	public String profilerDetener() {
		return "Parar";
	}

	@Override
	public String profilerLimpiar() {
		return "Limpar";
	}

	@Override
	public String profilerEstadoIniciado() {
		return "Analisador de desempenho iniciado.";
	}

	@Override
	public String profilerEstadoDetenido() {
		return "Analisador de desempenho parado.";
	}

	@Override
	public String profilerMuestraHilo(String nombreHilo) {
		return "Amostra recebida da thread: " + nombreHilo;
	}

	@Override
	public String samplerDescripcion() {
		return "Amostragem periódica de pilhas para detectar gargalos e travamentos.";
	}

	@Override
	public String entrarAlJuego() {
		return "Entrar no Jogo";
	}

}
