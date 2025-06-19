package com.asbestosstar.crashdetector.idioma;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.Idioma;

public class Portuges implements Idioma {
    private final Config config = Config.obtenerInstancia();

    @Override
    public String carpeta_de_mods_no_valido() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Pasta de mods inválida</span>";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Não foi possível encontrar o JAR do CrashDetector</span>";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Procurando pelo PID: " + pid + "</span>";
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>(PID: " + pid + ") foi finalizado!</span>";
    }

    @Override
    public String no_tenemos_jvm() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Sem JVM disponível</span>";
    }

@Override
public String problema_con_graficas_ati() {
    return "<span style='color:#" + config.obtenerColorError() + "'>Atualizar seus drivers ATI/AMD pode ajudar. Leia este guia para corrigir: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de atualização de drivers</a> https://www.amd.com/pt/support/download/drivers.html Baixar </span>";
}

    @Override
    public String probelma_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Algumas versões antigas apresentam problemas com a interface de inicialização do Nouveau.</span>";
    }

    @Override
    public String probelma_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Problema detectado com seus drivers de vídeo. Usuários de AMD/ATI devem atualizar os drivers. Usuários de NVIDIA devem garantir que todos os processos javaw.exe estejam usando a GPU dedicada. Leia este guia: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Falha ao carregar janela inicial do FML. Para corrigi-lo, vá para (.minecraft/config/fml.toml) e defina earlyWindowProvider para \"none\". Usuários de Mac M1 devem usar Java ARM. Este problema também pode ocorrer com drivers desatualizados. Se usar Windows e a configuração não funcionar, consulte: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesitas() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Dependências faltando:</span>";
    }

    @Override
    public String linea_de_dependencia(String linea) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>" + linea.replace("Requested by", "Solicitado por").replace("Expected range", "Intervalo esperado") + "</span>";
    }

    @Override
    public String local_headless(String archivo) {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Seu relatório do CrashDetector está aqui: <a href='" + archivo + "' style='color:#" + config.obtenerColorEnlace() + "'>Visualizar Relatório</a></span>";
    }

    @Override
    public String texto_de_gui() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Esta é a interface gráfica do CrashDetector. Ignore-a se o jogo fechar normalmente.</span>";
    }

    @Override
    public String texto_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorBoton() + "'>Visualizar Relatório</span>";
    }

    @Override
    public String texto_debajo_de_buton_local_enlance() {
        return "<span style='color:#" + config.obtenerColorInfo() + "'>Visualizar relatório local no navegador.</span>";
    }

    @Override
    public String texto_de_buton_compartir_enlance() {
        return "Compartilhar Relatório";
    }

    @Override
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Compartilhar relatório. Seus logs serão enviados para securelogger.net e o relatório para outros sites.";
    }

    @Override
    public String problematico_jar() {
        return "<b style='color:#" + config.obtenerColorError() + "'>JAR problemático detectado (prioridade: FATAL > Alta > Baixa):</b>";
    }

    @Override
    public String nivel() {
        return "<b style='color:#" + config.obtenerColorError() + "'> Nível:</b> ";
    }

    @Override
    public String possibladad_fatal() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Potencialmente fatal:</b> ";
    }

    @Override
    public String modids_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>ModIDs problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Pacotes problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
    }

    @Override
    public String faltar_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Classes críticas faltando:</b>";
    }

    @Override
    public String corchetes_ondulados() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Conteúdo entre chaves (mais importantes no topo, mostrando primeiros 20):</b>";
    }

    @Override
    public String config_spongemixin_problematico() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Configuração problemática do SpongeMixin detectada: </b>";
    }

    @Override
    public String module_resolution_exception() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Você tem mods com pacotes duplicados. Você pode resolver isso removendo o pacote (pasta) duplicado do arquivo JAR. Você pode abrir o JAR em um programa de arquivamento como WinRAR ou 7z, ou também pode alterar a extensão do arquivo de .jar para .zip, excluir a pasta e depois renomeá-lo novamente para um arquivo .jar.</span>";
    }

    @Override
    public String modlauncher_mods_duplicado(String linea) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Mods duplicados detectados</b> " + linea.replace("from mod files", "de arquivos de mod");
    }

    @Override
    public String mcforge_mod_suspechoso() {
        return "<b style='color:#" + config.obtenerColorError() + "'>MinecraftForge detectou mod suspeito:</b> ";
    }

    @Override
    public String lithostichctov() {
        return "<b style='color:#" + config.obtenerColorError() + "'>CTOV requer lithostitched. Instale aqui: <a href='https://www.curseforge.com/minecraft/mc-mods/lithostitched' style='color:#" + config.obtenerColorEnlace() + "'>Lithostitched</a></b>";
    }

    @Override
    public String necesitasSodiumParaIris() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Para usar shaders Iris/Oculus, você precisa do SODIUM ou outro loader compatível (Rubidium, Embedium, Bedium)</b>";
    }

    @Override
    public String kubeJSResourcePack(String mod_nombre) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Problema com expansão KubeJS </b>" + mod_nombre;
    }
    
    @Override
public String problema_con_graficas_nvidia_windows_viejo() {
    return "<span style='color:#" + config.obtenerColorError() + "'>"
            + "Problemas detectados com drivers NVIDIA em versões anteriores ao Windows 11."
            + "</span><br/><br/>"
            + "Para garantir que o Minecraft (e a JVM atual) use a GPU dedicada NVIDIA, siga estes passos:<br/><br/>"
            + "1. <strong>Identifique o executável do Java:</strong><br/>"
            + "   - Este programa está usando o seguinte executável do Java: "
            + obtenerRutaJava() + "<br/>"
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
            + "   - Este programa está usando o seguinte executável do Java: "
            + obtenerRutaJava() + "<br/>"
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Seu Servidor ou Mundo tem ticks de mais de 60 segundos. Isso pode ser devido a mods tornando o servidor mais lento ou ao hardware ser muito fraco.</b>";
}



@Override
public String noTieneMemoria() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Você não tem RAM/Memória suficiente. Você precisa alocar mais.</b>";
}

@Override
public String theseus() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Se você está usando Theseus/ModrinthApp, não podemos ajudar muito porque o Theseus não tem um console de Launcher. O Theseus também tem mais problemas, incluindo versões antigas de Carregadores de Mods, software espião, registros ruins e mais. A empresa Modrinth também não é honesta. Eles fazem acusações falsas de que os desenvolvedores de mods usam bots para aumentar seus downloads e mudaram suas afirmações de monetização várias vezes.</b>";
}

@Override
public String noTieneConsolaDeLauncherCursedForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Você não tem o arquivo launcher_log.txt. Precisamos desse arquivo para procurar erros. Isso ocorre devido à opção \"Ignorar inicialização do Launcher\". Desative-a.</b>";
}



@Override
public String faltar_de_clases_advertencia() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Aviso: Classes ausentes detectadas:</b>";
}


@Override
public String noResultos() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Sem Resultados</b>";
}

@Override
public String ubicacionesDeLogs() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Locações dos Logs:</b>";
}

@Override
public String infoDeVerificaciones() {
    return "<b style='color:#" + config.obtenerColorInfo() + "'>Aqui estão os resultados das suas verificações. Corrigir as partes superiores dos registros é a primeira prioridade. Faça isso lentamente.</b>";
}

@Override
public String versionDeJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Por favor, use o Java 17 para as versões 1.17-1.20.4 e o Java 21 para qualquer versão mais recente. Use o Java 8 para versões mais antigas. [Guia](https://mikumikudance.jp/index.php?title=Installing_Java_For_Minecraft). Se você ainda tiver problemas, pode ser porque algum mod tem arquivos muito novos ou antigos.</b>";
}

@Override
public String java22() {
    return "<b style='color:#" + config.obtenerColorError() + "'>O Java 22 e superior não funciona em versões do Minecraft abaixo de 1.20.5 para a maioria dos modloaders devido ao ASM estar desatualizado.</b>" + versionDeJava();
}

@Override
public String javaObsoleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>O Java está obsoleto </b>" + versionDeJava();
}

@Override
public String jpms_modules_faltas(String mod_necesitas, String submod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Você precisa do módulo JPMS " + mod_necesitas + " de " + submod + "</b>";
}

@Override
public String null_pointer_error(String metodo, String objeto) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Não é possível invocar " + metodo + " porque " + objeto + " é nulo</b>";
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
public String noRegistroDeLauncher() {
    return "Nenhum registro do launcher encontrado! Isso pode dificultar a investigação.\n"
            + "                \n"
            + "                Para obter os registros corretos:\n"
            + "                - MultiMC/PolyMC/PrismLauncher: NOTA: Os registros detectados automaticamente NÃO estão corretos.\n"
            + "                  1. Abra a interface da instância\n"
            + "                  2. Vá para a seção \"Minecraft Log\"\n"
            + "                  3. Clique com o botão direito e copie o conteúdo\n"
            + "                - CurseForgeApp:\n"
            + "                  1. Reinicie o jogo SEM pular o launcher\n"
            + "                  \n";
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
    return "Este diálogo permite que você compartilhe registros usando a API SecureLogger "
            + "em securelogger.net. Ao pressionar o botão para compartilhar o relatório, seu relatório é enviado ao "
            + "ponto de extremidade selecionado (padrão asbestosstar.egoism.jp) (Pode ser alterado na parte inferior). Você pode compartilhar todos os registros selecionados "
            + "junto com o relatório. Se você não deseja fazer upload, não use este diálogo! Não processamos seu relatório no ponto de extremidade oficial (https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb); apenas removemos links não permitidos. O código está aqui: https://pagure.io/CrashDetectorMC/blob/main/f/src/main/resources/crash_detector_servidor.rb. Isso é usado exclusivamente para exibir informações sobre seu crash e o link para os registros. No entanto, é possível usar um ponto de extremidade personalizado que pode não ter os mesmos métodos. Você está usando o site de relatórios " 
            + Config.obtenerInstancia().obtenerSitoDeInformes() + " e o site de registros " 
            + Config.obtenerInstancia().obtenerSitioDeRegistrosSeleccionado() + ". Você também pode compartilhar registros individuais sem um relatório pressionando os botões de compartilhamento ao lado dos nomes dos registros; os registros irão para o site de registros selecionado. O CrashDetector tem uma anonimização padrão de registros, que tenta remover nomes de usuário, UUIDs, tokens de acesso, IDs de sessão, endereços IP e outros dados. No entanto, ela não é perfeita. Mesmo assim, o autor do modpack pode desativá-la. Ela pode ser ativada ou desativada com a caixa de seleção na parte inferior desta tela. Você é o controlador de seus próprios dados; você decide onde faz o upload de seus dados. Os sites de registro são de propriedade de terceiros cuja propriedade muitas vezes está oculta por razões de privacidade. Você assume total responsabilidade pela gestão de seus dados e pelos riscos envolvidos. O Diálogo de Compartilhamento do CrashDetector é apenas uma interface que permite gerenciar isso. É importante que você esteja ciente do GDPR e do ARCO.";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "JavaFML Incompatível: Requer versão " + requerido 
         + ", detectado " + encontrado + "</b>";
}

@Override
public String errorJavaFML_MCForge() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>"
         + "Atenção! JavaFML requer uma versão específica do Minecraft Forge</b>";
}

@Override
public String errorProveedorVersion(String archivoJar, String proveedor, String requerido, String encontrado) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "O arquivo JAR '" + archivoJar + "' requer o provedor de linguagem '" + proveedor + "' versão '"
         + requerido + "' ou superior, mas apenas a versão '" + encontrado + "' foi encontrada.</b>";
}

//@Override
//public String advertenciaMalwareFalso() {
//    return "<b style='color:#" + config.obtenerColorError()+ "'>"
//         + "ALERTA! O Crash Assistant é um detector de malware falso. Ele bloqueia intencionalmente o lançamento do jogo, ignorando sua liberdade de continuar jogando com os mods que ele visa. "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/8decd964e629100f36fc72ca2eb3c1226652f223/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/mod_list/MalwareMod.java#L7'>Ver código MalwareMod.java</a>   "
//         + "<a href='https://github.com/KostromDan/Crash-Assistant/blob/0787924a97a29fec8a83969fae3e87e30d816f22/common_config/src/main/java/dev/kostromdan/mods/crash_assistant/common_config/loading_utils/JarInJarHelper.java#L185'>Ver código JarInJarHelper.java</a>. "
//         + "Apenas este mod está na lista deles no momento, e eles estão realmente indo atrás apenas do site de registro padrão, que pode ser alterado pelo usuário, e isso só ocorre se você escolher explicitamente usar o recurso integrado de compartilhamento de logs. O CrashAssistant NÃO faz nenhuma verificação para determinar qual site de registro está sendo usado e não explica como alterá-lo (há um menu suspenso na parte inferior da caixa de diálogo de compartilhamento), e independentemente do site configurado, o CrashAssistant bloqueará o lançamento do jogo. Em sua mensagem, eles dizem para fazer sua própria pesquisa, FAÇA ISSO, examine o código do CrashDetector e do Crash Assistant e entenda o que eles fazem, NÃO confie em uma apelação à autoridade.</b>";
//}


@Override
public String error_clase_no_encontrada_mcforge_mod_suspechoso(String idMod, String classeNaoEncontrada) {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "O mod '" + idMod + "' falhou porque a classe necessária não foi encontrada: '"
         + classeNaoEncontrada + "'. Certifique-se de que todas as dependências estão instaladas corretamente.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Erro ao analisar o arquivo JSON '" + recurso + "' do arquivo JAR '" + arquivoJar
         + "'. Há problemas com o registro.</b>";
}


@Override
public String errorVersionDependencia(String modId, String dependencia, String requerido, String atual) {
    return "<span style='color:#" + config.obtenerColorError()+ "'>" 
        + "Erro: O mod '" + modId + "' requer a versão '" + requerido 
        + "' ou superior de '" + dependencia + "', mas foi encontrada '" + atual + "'."
        + "</span>";
}

@Override
public String gpu_no_compatible() {
    return "<b style='color:#" + config.obtenerColorError()+ "'>"
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
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Erro de configuração de monitores: "
         + "Não foi possível definir o modo de tela. "
         + "Verifique:</b>"
         + "<br>- Configuração de múltiplos monitores"
         + "<br>- Drivers de placa gráfica atualizados"
         + "<br>- Resolução suportada pelo sistema";
}

@Override
public String errorOpcionesGCJava() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Erro nas opções do Java: "
         + "Opções conflitantes do coletor de lixo. "
         + "Verifique se você não combina vários algoritmos GC nos parâmetros da JVM</b>";
}

@Override
public String errorConfigMCForge() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
         + "Erro crítico de configuração do Forge: "
         + "Arquivo de configuração corrompido ou incompleto. "
         + "Exclua a pasta 'config' e reinicie o jogo</b>";
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

public String nombre_de_contento_de_stacktrace() {
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
public String adjustesCrashDetector() {
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Mensagem de Trace Fatal Última (Não traduzida):</b> ";
}

@Override
public String mensaje_de_trace_ultima_no_traductado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Mensagem de Trace Última (Não traduzida):</b> ";
}

@Override
public String solucionParaAdvertenciaFaltasClases() {
    return "Você tem classes ausentes (Aviso), geralmente está tudo bem, mas nem sempre. Coremods ruins ou dependências ausentes são razões comuns para esse problema.";
}

@Override
public String solucionFaltasClases() {
    return "Você tem classes ausentes (FATAL), é muito importante. Coremods ruins ou dependências ausentes são razões comuns para esse problema.";
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






}
