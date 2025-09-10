package com.asbestosstar.crashdetector.idioma;

import java.util.List;

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
    public String problema_con_graficas_nouveau() {
        return "<span style='color:#" + config.obtenerColorAdvertencia() + "'>Algumas versões antigas apresentam problemas com a interface de inicialização do Nouveau.</span>";
    }

    @Override
    public String problema_con_graficas_general() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Problema detectado com seus drivers de vídeo. Usuários de AMD/ATI devem atualizar os drivers. Usuários de NVIDIA devem garantir que todos os processos javaw.exe estejam usando a GPU dedicada. Leia este guia: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
    }

    @Override
    public String fmlEarlyWindow() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Falha ao carregar janela inicial do FML. Para corrigi-lo, vá para (.minecraft/config/fml.toml) e defina earlyWindowProvider para \"none\". Usuários de Mac M1 devem usar Java ARM. Este problema também pode ocorrer com drivers desatualizados. Se usar Windows e a configuração não funcionar, consulte: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
    }

    @Override
    public String no_tienes_las_dependencias_necesarias() {
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
        return "<b style='color:#" + config.obtenerColorError() + "'>JAR problemático detectado (prioridade: FATAL > Alta > Baixa):</b>";
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
        return "<b style='color:#" + config.obtenerColorError() + "'>ModIDs problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
    }

    @Override
    public String packages_problematicos() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Pacotes problemáticos detectados (prioridade: FATAL > Alta > Baixa):</b>";
    }

    @Override
    public String falta_de_clases_fatales() {
        return "<b style='color:#" + config.obtenerColorError() + "'>Você tem classes fatais (FATAL), isso é muito grave. Causas comuns são CoreMods defeituosos ou dependências fatais. Use o QuickFix para procurar mods com classes fatais. Classes fatais ausentes detectadas:</b>";
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
    public String mcforge_mod_sospechoso() {
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Aviso: Classes ausentes detectadas (nível de aviso). Geralmente não é grave, mas pode causar problemas — diferente de erros fatais. Causas comuns incluem CoreMods defeituosos ou dependências ausentes. Use o QuickFix para verificar mods com classes faltantes. Classes ausentes detectadas:</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Mensagem de Trace Fatal Última (Não traduzida):</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin 'AuthMe' falhou ao carregar e desligou o servidor.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mundo '" + nombreMundo + "' não pôde ser carregado pois contém erros e provavelmente está corrompido.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>A configuração da extensão 'PermissionsEx' é inválida.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Há múltiplos arquivos de plugins com o nome '" + nombrePlugin + "': '" + primerPath + "' e '" + segundoPath + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Ocorreu uma exceção ao carregar os chunks do mundo.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + "' não pode executar o comando '/" + comando + "'.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + 
           "' requer a seguinte dependência: '" + dependencia + "'.</b> ";
}

@Override
public String mensajeDependenciaPluginMultiples(String nombrePlugin, List<String> dependencias) {
    StringBuilder deps = new StringBuilder();
    for (int i = 0; i < dependencias.size(); i++) {
        if (i > 0) deps.append(", ");
        deps.append("'").append(dependencias.get(i)).append("'");
    }
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + 
           "' está faltando as seguintes dependências: " + deps.toString() + ".</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + 
           "' requer a versão da API '" + versionAPI + "' que não é compatível com o servidor atual.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mundo '" + nombreMundo + 
           "' é uma duplicata de outro mundo e não pode ser carregado.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>A entidade de bloco '" + nombre + 
           "' do tipo '" + tipo + "' na localização " + coords + " está causando erros no ticking.</b> ";
}

@Override
public String nombreProblemaTickingEntidadBloque() {
    return "Entidade de Bloco Problemática";
}

@Override
public String solucionEliminarEntidadBloque(String nombre, int[] coordenadas) {
    String coords = "(" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
    return "Remova a entidade '" + nombre + "' na localização " + coords + " usando MCEdit ou editando o mundo diretamente.";
}

@Override
public String mensajeModDuplicadoFabric(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' tem múltiplas versões instaladas.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Os mods '" + primerMod + 
           "' e '" + segundoMod + "' são incompatíveis entre si.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' tem um erro crítico e não pode ser executado.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Os seguintes mods são necessários mas não estão instalados: " + deps.toString() + ".</b>";
}

@Override
public String mensajeDependenciaModFaltante(String nombreMod, String dependencia, String version) {
    if (version == null || version.isEmpty()) {
        return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + 
               "' requer o mod '" + dependencia + "' como dependência.</b>";
    } else {
        return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + 
               "' requer o mod '" + dependencia + "' na versão " + version + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + 
           "' não é compatível com o ticking regional do Folia.</b> ";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + 
           "' está faltando no datapack.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + 
           "' não é compatível com a versão do Minecraft " + versionMC + ".</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mod '" + nombreMod + "' está faltando e é necessário para carregar o mundo ou plugin.</b>";
}

@Override
public String nombreProblemaDependenciaModFaltante() {
    return "Mod ausente";
}

@Override
public String mensajeWorldModFaltanteSingular(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>O mundo foi salvo com o mod '" + nombreMod + 
           "' que parece estar ausente.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O mundo foi salvo com o mod '" + nombreMod + 
           "' na versão " + versionEsperada + ", e agora está na versão " + versionActual + ".</b>";
}

@Override
public String mensajeVersionModMundoPlural(List<String> mods, List<String> versionesEsperadas, List<String> versionesActuales) {
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
        sb.append(" (Salvo: ").append(versionesEsperadas.get(i)).append(", Atual: ").append(versionesActuales.get(i)).append(")");
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Você tentou carregar um mundo criado com uma versão mais nova do Minecraft.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + 
           "' precisa do seguinte plugin como dependência: '" + dependencia + "'.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + "' é incompatível com a versão atual do servidor.</b>";
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

@Override public String nombreProblemaPluginIncompatible() { return "Plugin incompatível com PocketMine-MP"; } @Override public String mensajePluginEjecucionSingular(String nombrePlugin) { return "<b style='color:#" + config.obtenerColorError() + "'>O plugin '" + nombrePlugin + "' causou um erro durante a execução.</b>"; } @Override public String mensajePluginEjecucionPlural(List<String> plugins) { StringBuilder sb = new StringBuilder("<b style='color:#").append(config.obtenerColorError()).append("'>"); sb.append("Os seguintes plugins causaram erros durante a execução: "); for (int i = 0; i < plugins.size(); i++) { if (i > 0) { if (i == plugins.size() - 1) { sb.append(" e "); } else { sb.append(", "); } } sb.append("'").append(plugins.get(i)).append("'"); } sb.append(".</b>"); return sb.toString(); } @Override public String nombreProblemaPluginEjecucion() { return "Plugin com erro de execução"; } @Override public String nombreLegacyRandomSourceMultiHilos() { return "LegacyRandomSource Multi Threads"; } @Override public String mensajeLegacyRandomSourceMultiHilos() { return "<b style='color:#" + config.obtenerColorError() + "'>Você tem problemas com múltiplas threads acessando a classe LegacyRandomSource. Obtenha mais informações usando o mod 'Unsafe World Random Access Detector' ou 'C2ME'.</b>"; }



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
        
        + "<h3>Como usá-lo:</h3>"
        + "<ol>"
        + "<li><b>Seleção de arquivos:</b> Clique nos botões de rádio ao lado dos nomes dos arquivos. "
        + "Arquivos terminados em <span style='color: #4CAF50; font-weight: bold;'>.sucesso</span> indicam sessões bem-sucedidas, "
        + "enquanto os com <span style='color: #F44336; font-weight: bold;'>.falha</span> representam erros ou falhas.</li>"
        
        + "<li><b>Comparação automática:</b> Ao pressionar o botão 'Comparar', o sistema analisará as duas listas selecionadas "
        + "e exibirá quais mods foram adicionados (+) ou removidos (-).</li>"
        
        + "<li><b>Visualização dos resultados:</b> Os resultados são mostrados em formato HTML com cores codificadas: "
        + "<ul>"
        + "<li><span style='color: green;'>+ Mod adicionado</span></li>"
        + "<li><span style='color: red;'>- Mod removido</span></li>"
        + "</ul></li>"
        + "</ol>"
        
        + "<h3>Principais características:</h3>"
        + "<ul>"
        + "<li>Suporta qualquer combinação de arquivos (sucesso/falha).</li>"
        + "<li>Mostra diferenças bidirecionais para identificar mudanças exatas.</li>"
        + "<li>Inclui rolagem para listas longas de mods.</li>"
        + "<li>Contém imagens explicativas para melhor compreensão visual.</li>"
        + "</ul>"
        
        + "<p>Desenvolvido com <3️ para ajudar no rastreamento de alterações nas suas configurações.</p>"
        + "</body></html>";
}


@Override
public String tieneErrorIPV6() {
    return "<b style='color:#" + config.obtenerColorError() + "'>"
           + "Você pode estar enfrentando um problema relacionado ao IPv6. "
           + "Há duas soluções: "
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
    return "Prism/MultiMC/PolyMC/PollyMC/UltimMC/Derivados: Clique com o botão direito na instância e selecione \"Editar Instância\". Na janela que abrir, procure pela seção \"Registros do Minecraft\" ou algo semelhante.<br>" +
           "Esses registros contêm a saída padrão (STDOUT), essencial para diagnosticar erros.";
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
    return "GENÉRICO: Selecione o tipo de launcher que você está usando. Os registros do launcher (launcher_log.txt, stdout, etc.) contêm informações vitais sobre erros que não aparecem no latest.log. O CrashDetector não consegue ler os registros do seu launcher — talvez ele não gere um arquivo, então você precisa colar os registros manualmente.<br>" +
           "Para mais informações, veja <a href=\"https://github.com/HMCL-dev/HMCL/issues/2663 \">esta questão</a>. Esses registros contêm a saída padrão (STDOUT), essencial para diagnosticar muitos tipos de erro.";
}

@Override
public String faltar_de_clases_create() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Classes ausentes do Create detectadas. O Create mudou muito — muitas classes foram removidas. Especialmente desde o Create 6 (fevereiro de 2025), addons para versões antigas do Create não funcionam mais. O QuickFix não tem solução para isso. Atualize seus addons do Create, remova os obsoletos ou use a versão correta do Create para os addons desejados.</b>";
}

@Override
public String faltar_de_clases_epicfight() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Classes ausentes do EpicFight detectadas. O EpicFight mudou muito — muitas classes foram removidas. O QuickFix não tem solução para isso. Atualize seus addons do EpicFight, remova os obsoletos ou use a versão correta do EpicFight para os addons desejados.</b>";
}

@Override
public String openJ9NoSoportado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Você está usando o OpenJ9, que não é suportado por este aplicativo. Muitos programas, incluindo este, não suportam o OpenJ9 devido a diferenças na implementação da JVM. O QuickFix não pode resolver esse problema automaticamente. Você precisa instalar um JDK compatível, como Oracle JDK, OpenJDK Hotspot ou outras alternativas recomendadas.</b>";
}

@Override
public String necesitasJDK11() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Esta versão do aplicativo requer Java 11 (JDK 11) para funcionar corretamente. Você está usando uma versão antiga do Java que não é compatível. O QuickFix não pode atualizar seu Java automaticamente. Você precisa baixar e instalar o JDK 11 ou uma versão superior compatível pelos links fornecidos na solução.</b>";
}

@Override
public String memoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorError() + "'>Você alocou memória em excesso, fazendo com que o sistema não tenha recursos suficientes. Isso ocorre quando a quantidade de RAM definida é maior que a disponível ou ao usar uma JVM de 32 bits, que não lida bem com grandes alocações.</b>";
}

@Override
public String recomendacionMemoriaExcesiva() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Para resolver, reduza a memória alocada ao app. O máximo recomendado depende do seu sistema, mas geralmente não deve ultrapassar 70-80% da RAM total. Em JVMs de 32 bits, o limite é de cerca de 2-3 GB, independentemente da RAM física.</b>";
}

@Override
public String disminuirMemoriaHeap() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Para reduzir a memória heap, abra as configurações do seu launcher e encontre a opção de memória RAM. Reduza o valor máximo (Xmx) para um valor adequado. Exemplo: com 8 GB de RAM, use 3-4 GB; com 16 GB, use 6-8 GB. Deixe memória suficiente para o sistema operacional e outros programas.</b>";
}

@Override
public String forgeArchivosFaltantes(String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Faltan archivos esenciales de Forge. El archivo '" + archivo + "' no se encuentra en tu instalación. Esto suele ocurrir cuando la instalación de Forge se interrumpió o se eliminaron archivos importantes. QuickFix no puede recuperar estos archivos automáticamente. Necesitas reinstalar Forge correctamente desde el instalador oficial.</b>";
}

@Override
public String forgeVersionNoEncontrada(String version, String archivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Forge no puede encontrar la versión de Minecraft requerida. Se necesita la versión " + version + " pero no se encuentra en el archivo '" + archivo + "'. Esto ocurre cuando hay una incompatibilidad entre la versión de Minecraft y la versión de Forge que estás utilizando. Asegúrate de descargar la versión correcta de Forge que coincida con tu versión de Minecraft.</b>";
}

@Override
public String forgeTargetFmlclientNoEncontrado() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No se puede encontrar el target 'fmlclient' necesario para iniciar Forge. Esto indica que la instalación de Forge está incompleta o dañada. Es probable que los archivos esenciales de Forge no se hayan instalado correctamente. Necesitas reinstalar Forge usando el instalador oficial.</b>";
}

@Override
public String forgeClaseMinecraftFaltante() {
    return "<b style='color:#" + config.obtenerColorError() + "'>No se puede encontrar la clase principal de Minecraft en el cargador de clases. Esto suele indicar que la instalación de Forge está incompleta o que hay un conflicto con otros mods. Es posible que los archivos de Minecraft se hayan dañado durante la instalación de Forge. Necesitas reinstalar Forge correctamente.</b>";
}

@Override
public String forgeInstallacionNoCompleta() {
    return "<b style='color:#" + config.obtenerColorError() + "'>La instalación de Forge no está completa. Esto puede deberse a una instalación interrumpida, archivos eliminados o una incompatibilidad con tu versión de Minecraft. Forge necesita archivos específicos para funcionar correctamente, y algunos de ellos están faltando en tu instalación actual.</b>";
}

@Override
public String nombre_de_forge_instalacion_no_completa() {
    return "Instalación incompleta de Forge";
}

@Override
public String solucion_para_forge_instalacion_no_completa() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Para resolver este problema, necesitas reinstalar Forge correctamente. Asegúrate de descargar la versión adecuada para tu versión de Minecraft y seguir el proceso de instalación completo sin interrumpirlo.</b>";
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
    return "<html><body style='width: 500px;'>" +
           "<h3 style='color:#" + config.obtenerColorTitulo() + "'>Instrucciones para reinstalar Forge:</h3>" +
           "<ol>" +
           "<li>Descarga el instalador correcto de Forge desde el sitio oficial (versión recomendada para tu versión de Minecraft)</li>" +
           "<li>Cierra completamente tu launcher de Minecraft</li>" +
           "<li>Ejecuta el instalador de Forge como administrador</li>" +
           "<li>Selecciona la opción 'Installer' (no 'Installer (run client)')</li>" +
           "<li>Elige la carpeta de tu perfil de Minecraft en el launcher</li>" +
           "<li>Presiona 'OK' y espera a que termine la instalación</li>" +
           "<li>Reinicia tu launcher y verifica que Forge aparezca en la lista de perfiles</li>" +
           "</ol>" +
           "<p><b>Nota importante:</b> Si usas un launcher personalizado, asegúrate de seleccionar la carpeta correcta del perfil.</p>" +
           "</body></html>";
}

@Override
public String titulo_instrucciones_reinstaler_mcforge() {
    return "Instrucciones para reinstalar Forge";
}

@Override
public String error_enlace_insatisfecho(String nombreBiblioteca) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Unsatisfied Link Error: Failed to load library " + nombreBiblioteca + ". Possible solutions:<br/><br/>" +
           "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>" +
           "b) Add the JAR file containing the shared library to the classpath.<br/><br/>" +
           "This error occurs when Minecraft cannot locate essential files for execution. " +
           "It is usually caused by an incomplete Minecraft installation or system permission issues.</b>";
}

@Override
public String nombre_de_error_enlace_insatisfecho() {
    return "Unsatisfied Link Error";
}

@Override
public String solucion_para_error_enlace_insatisfecho() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Failed to load a library. Possible solutions:<br/><br/>" +
           "a) Add the directory containing the shared library to -Djava.library.path or -Dorg.lwjgl.librarypath.<br/>" +
           "b) Add the JAR file containing the shared library to the classpath.<br/><br/>" +
           "These technical solutions are intended for advanced users. Most users should try " +
           "reinstalling Minecraft before modifying these parameters.</b>";
}

@Override
public String conflicto_id_colision_especifico(String id, String modOrigen, String modDestino) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Conflito de ID: O ID <strong>" + id + "</strong> já está em uso pelo <strong>" + modOrigen + "</strong> ao tentar adicionar <strong>" + modDestino + "</strong>. Isso ocorre quando dois mods tentam usar o mesmo ID para elementos diferentes.</b>";
}

@Override
public String conflicto_id_maximo() {
    return "<b style='color:#" + config.obtenerColorError() + "'>O limite máximo de IDs permitidos foi excedido. Isso acontece quando mods tentam registrar blocos ou itens com IDs fora do intervalo suportado pela sua versão do Minecraft.</b>";
}

@Override
public String nombre_de_conflicto_ids() {
    return "Conflito de ID";
}

@Override
public String solucion_maximo_rango() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Para resolver isso no Minecraft 1.12.2, instale <a href='https://www.curseforge.com/minecraft/mc-mods/justenoughids'>JustEnoughIDs</a>. Para 1.7.10, use <a href='https://www.curseforge.com/minecraft/mc-mods/endless-ids'>EndlessIDs</a>.</b>";
}

@Override
public String solucion_colision_id() {
    return "<b style='color:#" + config.obtenerColorTexto() + "'>Use ferramentas como <a href='https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1291014-idfix-and-idfix-minus-mods-for-resolving-id'>IdFix Minus</a> ou <a href='https://github.com/SS111/Minecraft-ID-Resolver'>Minecraft-ID-Resolver</a> para resolver conflitos de ID.</b>";
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
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Você não possui os binários do VLC. O WaterMedia requer os binários do VLC. Instale manualmente em https://www.videolan.org/vlc/.  </b>";
}

@Override
public String descargar_vlc() {
    return "Baixar VLC";
}

@Override
public String errorCaracteresInvalidosEnNombre(String nombreModulo, String parteInvalida) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: O nome do módulo '" + nombreModulo + 
           "' contém caracteres inválidos. A parte '" + parteInvalida + 
           "' não é um identificador Java válido. Isso ocorre quando um mod usa palavras reservadas do Java (como 'true', 'class') ou caracteres não permitidos em seu nome.</b>";
}

@Override
public String nombre_de_error_caracteres_invalidos() {
    return "Caracteres Inválidos no Nome do Mod";
}

@Override
public String paso1_caracteres_invalidos(String nombreModulo, String parteInvalida) {
    return "O nome do mod '" + nombreModulo + "' é inválido porque contém '" + parteInvalida + 
           "', que é uma palavra reservada do Java ou um caractere inválido. " +
           "Procure nos logs qual mod corresponde a esse nome (geralmente o nome do arquivo JAR)";
}

@Override
public String paso2_caracteres_invalidos(String nombreModulo) {
    return "Acesse a pasta do mod e edite o arquivo <b>mods.toml</b> dentro da pasta <b>/META-INF/</b>. " +
           "Altere o valor de <b>modId</b> para usar apenas letras, números e sublinhados, sem palavras reservadas do Java";
}

@Override
public String paso3_caracteres_invalidos() {
    return "Exemplo de nome válido: 'truemod_shot_enchantment' em vez de 'true.shot.enchantment'. " +
           "Lembre-se que nomes de mods não podem conter pontos, hífens ou palavras reservadas do Java como 'true', 'false' ou 'class'";
}

@Override
public String errorDependenciaModFaltante(String nombreJar) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico com o mod: '" + nombreJar + "'. Falta o campo obrigatório 'mandatory' em suas dependências. Isso ocorre quando o arquivo mods.toml não especifica se a dependência é obrigatória.</b>";
}

@Override
public String nombre_de_error_dependencia_mod_faltante() {
    return "Dependência de Mod com Campo 'mandatory' Ausente";
}

@Override
public String paso1_dependencia_mod_faltante(String nombreJar) {
    return "O mod problemático é: <b>" + nombreJar + "</b>. Este arquivo tem um erro na configuração de dependências";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico com o mod: '" + nombreJar + "'. Configuração inválida de access transformer. Isso ocorre quando o arquivo de configuração tem sintaxe incorreta ou referências a classes/métodos inexistentes.</b>";
}

@Override
public String nombre_de_error_access_transformer_invalido() {
    return "Access Transformer Inválido";
}

@Override
public String paso1_access_transformer_invalido(String nombreJar) {
    return "O mod problemático é: <b>" + nombreJar + "</b>. Este mod contém uma configuração inválida de access transformer";
}

@Override
public String paso2_access_transformer_invalido(String nombreJar) {
    return "Abra o arquivo <b>accessTransformer.cfg</b> dentro do mod <b>" + nombreJar + "</b> (normalmente na pasta raiz do arquivo JAR)";
}

@Override
public String paso3_access_transformer_invalido() {
    return "Corrija a sintaxe do access transformer. As linhas devem seguir o formato: <b>access class.method</b> (ex: public net.minecraft.world.entity.Entity.func_200560_a). Remova linhas com referências a classes ou métodos que não existam na sua versão do Minecraft";
}

@Override
public String errorDiscrepanciaModID(String nombreMod) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Discrepância entre o ID do mod na anotação @Mod e o arquivo mods.toml. O mod '" + nombreMod + "' não pode ser carregado porque os IDs não coincidem.</b>";
}

@Override
public String nombre_de_error_discrepancia_mod_id() {
    return "Discrepância entre @Mod e mods.toml";
}

@Override
public String paso1_discrepancia_mod_id(String nombreMod) {
    return "O mod em desenvolvimento '" + nombreMod + "' tem uma discrepância entre o ID na anotação <b>@Mod</b> e o valor em <b>mods.toml</b>";
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
    
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Tentando carregar a classe '" + nombreClase + 
           "' no ambiente de " + plataforma + ", mas ela foi feita para " + plataformaOpuesta + 
           ". <b>Use a função 'Árvore de Mods' na barra lateral para descobrir qual mod está tentando carregar esta classe</b>. " +
           "Mods são feitos especificamente para uma plataforma e não funcionam na outra.</b>";
}

@Override
public String nombre_de_error_mod_plataforma_incorrecta() {
    return "Mod em Plataforma Incorreta";
}

@Override
public String paso1_mod_plataforma_incorrecta(String nombreClase, String entornoInvalido) {
    return "Na aba <b>Árvore de Mods</b> (à direita), busque referências à classe <b>" + nombreClase + 
           "</b> para identificar qual mod está causando o problema";
}

@Override
public String paso2_mod_plataforma_incorrecta(String entornoInvalido) {
    String plataforma = entornoInvalido.equals("CLIENT") ? "cliente" : "servidor";
    String plataformaOpuesta = entornoInvalido.equals("CLIENT") ? "servidor" : "cliente";
    
    return "O mod identificado é um mod de <b>" + plataformaOpuesta + "</b> e não deve ser usado em seu ambiente de " + plataforma + ".";
}

@Override
public String paso3_mod_plataforma_incorrecta() {
    return "Remova o mod problemático da pasta <b>mods</b>. Se precisar de funcionalidade semelhante nesta plataforma, " +
           "procure um mod alternativo feito especificamente para <b>cliente</b> ou <b>servidor</b>, conforme necessário";
}

@Override
public String errorMetadataModsTomlFaltante(String modIdFaltante, List<String> modsPotenciales) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Erro crítico: Falta metadados para o modid '").append(modIdFaltante).append("'. ");
    
    if (modsPotenciales != null && !modsPotenciales.isEmpty()) {
        mensaje.append("Os seguintes mods podem estar causando o problema: <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            mensaje.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsPotenciales.size() > 3) mensaje.append(", e outros...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Isso ocorre quando um mod depende de outro que não está instalado ou tem um arquivo mods.toml incorreto.");
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
        StringBuilder paso = new StringBuilder("Os seguintes mods dependem de '").append(modIdFaltante).append("': <b>");
        for (int i = 0; i < Math.min(modsPotenciales.size(), 3); i++) {
            paso.append(modsPotenciales.get(i));
            if (i < modsPotenciales.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsPotenciales.size() > 3) paso.append(", e outros...");
        paso.append("</b>. Use a função <b>Árvore de Mods</b> para confirmar qual mod está causando o problema");
        return paso.toString();
    }
    return "Um mod está tentando depender de '" + modIdFaltante + "', mas este mod não está instalado. Use a função <b>Árvore de Mods</b> para identificar qual mod está causando o problema";
}

@Override
public String paso2_metadata_mods_toml_faltante(String modIdFaltante) {
    return "Você tem duas opções:<br/>" +
           "1. <b>Instale o mod ausente</b>: Encontre e instale o mod com ID '" + modIdFaltante + "'<br/>" +
           "2. <b>Remova o mod dependente</b>: Se não precisar da funcionalidade, remova o mod que depende de '" + modIdFaltante + "'";
}

@Override
public String paso3_metadata_mods_toml_faltante(String modIdFaltante) {
    return "Se o mod '" + modIdFaltante + "' for uma biblioteca (como 'forge', 'minecraft', 'curios'), " +
           "certifique-se de ter as versões corretas do Minecraft e do Forge instaladas. " +
           "Se for um mod comum, verifique na página de download os pré-requisitos necessários";
}


@Override
public String errorSistemaSonido() {
    return "<b style='color:#" + config.obtenerColorAdvertencia() + "'>Aviso: Falha ao iniciar o sistema de som. Sons e música foram desativados. Esse erro está comumente associado ao mod SoundPhysicsMod e pode ser causado por conflitos com outras bibliotecas de áudio.</b>";
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
    mensaje.append("Erro crítico: A classe '").append(nombreClase).append("' está registrada como ouvinte de eventos, mas não possui métodos válidos. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("Esta classe está presente nos seguintes mods: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", e outros...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Isso ocorre quando uma classe é registrada para escutar eventos sem ter métodos anotados com @SubscribeEvent.");
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
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", e outros...");
        paso.append("</b>. Esses mods estão tentando registrar eventos sem métodos válidos");
        return paso.toString();
    }
    return "A classe <b>" + nombreClase + "</b> foi registrada para escutar eventos, mas não possui métodos com a anotação <b>@SubscribeEvent</b>. Use a função <b>Árvore de Mods</b> para identificar qual mod contém esta classe";
}

@Override
public String paso2_sin_listeners_en_clase(String nombreClase) {
    return "No código-fonte, verifique que a classe <b>" + nombreClase + "</b> contenha pelo menos um método com: " +
           "<b>@SubscribeEvent public void nomeMetodo(EventoEspecifico evento) { ... }</b>. " +
           "Se for uma classe interna, certifique-se de que não esteja marcada como estática";
}

@Override
public String paso3_sin_listeners_en_clase(String nombreClase, List<String> modsUbicacion) {
    StringBuilder paso = new StringBuilder();
    
    if (!modsUbicacion.isEmpty()) {
        paso.append("Para os mods identificados (<b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 2); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 1) paso.append(", ");
        }
        if (modsUbicacion.size() > 2) paso.append(", etc.");
        paso.append("</b>): ");
        
        if (modsUbicacion.size() == 1) {
            paso.append("contate o desenvolvedor desse mod para corrigir o problema. ");
        } else {
            paso.append("contate os desenvolvedores desses mods para corrigir o problema. ");
        }
    }
    
    paso.append("Se você for o desenvolvedor, remova o registro dessa classe no EventBus ou adicione métodos @SubscribeEvent válidos");
    
    return paso.toString();
}

@Override
public String errorUnionFileSystemCorrupto(String nombreArchivo) {
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Ocorreu uma exceção 'cpw.mods.niofs.union.UnionFileSystem$UncheckedIOException' ao processar o arquivo '" + 
           nombreArchivo + "'. Este erro específico indica que o launcher não baixou ou extraiu corretamente os arquivos do modpack. " +
           "A mensagem 'zip END header not found' revela que o arquivo JAR está incompleto ou corrompido, o que é extremamente comum em launchers que gerenciam mal o download de arquivos grandes. " +
           "Este problema afeta principalmente usuários do Twitch/CurseForge, Technic Launcher e especialmente usuários do Luna Pixel, pois esses launchers muitas vezes falham em verificar a integridade completa dos arquivos baixados. " +
           "Usuários do Luna Pixel deveriam considerar o ATLauncher como uma alternativa mais estável, pois ele lida melhor com a integridade dos arquivos e evita esse erro específico. " +
           "O sistema não pode carregar os mods porque o formato ZIP está danificado, impedindo que o Forge leia os recursos necessários para iniciar o jogo.</b>";
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
    return "Habilitar ProxySysOutSysErr?\n\n" +
           "Esta opção dá ao CrashDetector acesso ao System.out e System.err quando o launcher não fornece registros.\n\n" +
           "Deve ser habilitada apenas quando você não conseguir colar um log manualmente.\n\n" +
           "Aviso: Isso pode interferir com alguns mods ou launchers.\n\n" +
           "É necessário reiniciar o jogo/aplicativo para que as alterações tenham efeito.";
}

@Override
public String confirmacionTitulo() {
    return "Confirmação";
}

@Override
public String proxyHabilitadoMensaje() {
    return "ProxySysOutSysErr habilitado com sucesso.\n\n" +
           "É necessário reiniciar o CrashDetector para que as alterações tenham efeito.";
}

@Override
public String informacionTitulo() {
    return "Informação";
}

@Override
public String errorAzureGeckoLibInicializoPronto(boolean azureLibError, boolean geckoLibError, boolean connectorPresente) {
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
        mensaje.append("Um mod de compatibilidade (Sinytra Connector ou specialcompatibilityoperation) foi detectado, indicando que você está tentando usar mods Fabric em um ambiente Forge ou FeatureCreep. ");
        mensaje.append("Verifique o erro 'Erro de inicialização do FabricMC' nos logs para identificar qual mod específico está causando o problema. ");
    }
    
    mensaje.append("AzureLib e GeckoLib são bibliotecas essenciais para mods de animação, mas devem corresponder à plataforma correta (Fabric ou Forge). ");
    mensaje.append("O jogo não pode carregar corretamente os mods de animação devido a esse conflito de inicialização.");
    
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Incompatibilidade entre C2ME e mods de conexão. " +
           "Este erro ocorre porque o C2ME tenta acessar componentes internos do Java que são restritos em ambientes com " +
           "Sinytra Connector ou specialcompatibilityoperation ou outros mods de compatibilidade Fabric/Forge. " +
           "<b>O C2ME não é compatível com esses ambientes, mas <a href='https://www.curseforge.com/minecraft/mc-mods/c3me'>C3ME</a> é a alternativa recomendada</b> que funciona corretamente " +
           "com mods de conexão. O jogo não pode iniciar devido a conflitos de permissão de segurança do Java.</b>";
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
    return "<b style='color:#" + config.obtenerColorError() + "'>Erro crítico: Falha ao carregar o plugin JEI para o mod '" + modId + 
           "'. A classe '" + nombreClase + "' (ID do plugin: '" + pluginId + 
           "') gerou um erro que está fazendo o jogo crashar durante a inicialização. " +
           "Esse problema ocorre quando um mod possui uma integração JEI incompatível ou corrompida que interrompe a inicialização do jogo.</b>";
}

@Override
public String nombre_de_error_jei_plugin_fallido() {
    return "Plugin JEI Falhou - Causa Crash";
}

@Override
public String paso1_jei_plugin_fallido(String modId) {
    return "O mod <b>" + modId + "</b> contém um plugin JEI corrompido que está causando o crash. Use a função <b>Árvore de Mods</b> para confirmar qual mod está gerando o problema";
}

@Override
public String paso2_jei_plugin_fallido(String modId) {
    return "Remova temporariamente o mod <b>" + modId + "</b> da pasta de mods para verificar se isso resolve o crash";
}

@Override
public String paso3_jei_plugin_fallido(String modId) {
    return "Procure por atualizações do mod <b>" + modId + "</b> ou entre em contato com seu desenvolvedor relatando o problema com o plugin JEI. " +
           "Enquanto isso, o mod deve ser removido para conseguir iniciar o jogo";
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
    return "Um erro crítico foi detectado nos logs. " +
           "Verifique o rastro de pilha para identificar a causa raiz.";
}

@Override
public String obtenerErrorLecturaArchivo() {
    return "Não foi possível ler o arquivo de log. " +
           "Verifique se o arquivo existe e tem permissões de leitura.";
}

@Override
public String obtenerEtiquetaBotonLectador() {
    return "Analisador de Logs";
}

@Override
public String errorRegistroSuscriptoresAutomaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    StringBuilder mensaje = new StringBuilder("<b style='color:#" + config.obtenerColorError() + "'>");
    mensaje.append("Erro crítico: Falha ao registrar assinantes automáticos para o mod '").append(modId).append("'. ");
    
    mensaje.append("Classe problemática: <b>").append(nombreClase).append("</b>. ");
    
    if (!modsUbicacion.isEmpty()) {
        mensaje.append("Esta classe está localizada em: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            mensaje.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) mensaje.append(", ");
        }
        if (modsUbicacion.size() > 3) mensaje.append(", e outros...");
        mensaje.append("</b>. ");
    }
    
    mensaje.append("Este erro ocorre quando um mod tenta registrar uma classe como assinante de eventos automaticamente, mas a classe não pode ser carregada. ");
    mensaje.append("<b>Verifique outros erros no log, pois a causa real pode estar em um carregamento anterior falho</b>.");
    mensaje.append("</b>");
    
    return mensaje.toString();
}

@Override
public String nombre_de_error_registro_suscriptores_automaticos() {
    return "Falha no Registro de Assinantes Automáticos";
}

@Override
public String paso1_registro_suscriptores_automaticos(String modId, String nombreClase) {
    return "O mod <b>" + modId + "</b> está tentando registrar a classe <b>" + nombreClase + 
           "</b> como assinante automático, mas falhou. Verifique se esta classe existe e é acessível";
}

@Override
public String paso2_registro_suscriptores_automaticos(String modId, String nombreClase, List<String> modsUbicacion) {
    if (!modsUbicacion.isEmpty()) {
        StringBuilder paso = new StringBuilder("A classe problemática <b>" + nombreClase + "</b> está nestes arquivos: <b>");
        for (int i = 0; i < Math.min(modsUbicacion.size(), 3); i++) {
            paso.append(modsUbicacion.get(i));
            if (i < modsUbicacion.size() - 1 && i < 2) paso.append(", ");
        }
        if (modsUbicacion.size() > 3) paso.append(", e outros...");
        paso.append("</b>. ");
        paso.append("Use a função <b>Árvore de Mods</b> para confirmar qual arquivo contém a classe problemática");
        return paso.toString();
    }
    return "A classe <b>" + nombreClase + "</b> não foi encontrada em nenhum arquivo de mod. Verifique se o mod <b>" + modId + 
           "</b> está instalado corretamente. Use a função <b>Árvore de Mods</b> para ajudar a identificar o problema";
}

@Override
public String paso3_registro_suscriptores_automaticos(String modId) {
    return "Atualize o mod <b>" + modId + "</b> para a última versão compatível com sua versão do Minecraft e Forge. " +
           "Se o problema persistir, contate o desenvolvedor do mod relatando o erro com a classe problemática";
}

@Override
public String paso4_registro_suscriptores_automaticos() {
    return "Verifique <b>outros erros no log</b> antes desta mensagem, pois o problema real pode estar em um carregamento anterior falhado. " +
           "Às vezes um erro anterior impede o carregamento das classes necessárias para o registro de eventos";
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









}
