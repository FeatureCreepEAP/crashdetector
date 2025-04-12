package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Idioma;

public class Portuges implements Idioma {

    @Override
    public String carpeta_de_mods_no_valido() {
        return "Não é uma pasta válida para mods";
    }

    @Override
    public String no_se_donde_esta_jar() {
        return "Eu não sei onde está o arquivo JAR do CrashDetector";
    }

    @Override
    public String buscando_para_pid(long pid) {
        return "Procurando pelo PID: " + String.valueOf(pid);
    }

    @Override
    public String pid_esta_muerto(long pid) {
        return "(PID: " + String.valueOf(pid) + ") está morto!";
    }

    @Override
    public String no_tenemos_jvm() {
        return "Não temos JVM";
    }
    
    @Override
public String probelma_con_graficas_ati() {
    return "Atualizar seus drivers pode ajudar. Tenha em mente que procurar atualizações da maneira usual não encontrará nenhuma quando os drivers estiverem em um estado corrompido, então é importante seguir o guia vinculado. Importante: Se você tiver gráficos Nvidia, certifique-se de configurar qualquer coisa relacionada ao Minecraft (como Java e launchers) para priorizar o desempenho alto tanto nas configurações do Windows quanto no painel de controle da Nvidia. Leia este guia para resolver isso: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String probelma_con_graficas_nouveau() {
    return "Algumas versões antigas às vezes têm alguns problemas com gráficos Nouveau na tela de carregamento inicial.";
}

@Override
public String probelma_con_graficas_general() {
    return "Você tem um problema com seus drivers gráficos. Se você tiver uma GPU ou APU AMD/ATI, atualize seus drivers gráficos AMD. Se você tiver uma placa de vídeo NVIDIA, certifique-se de marcar o jogo e todas as instâncias de javaw.exe para usar a placa de vídeo dedicada. Leia este guia: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String fmlEarlyWindow() {
    return "Sua janela FML Early está falhando. "
            + "Para mudar isso, vá para (.)minecraft/config/fml.toml "
            + "Edite earlyWindowProvider para ser earlyWindowProvider=\"none\" "
            + "Se você estiver em um Mac M1, também certifique-se de estar usando uma versão ARM do Java, não uma versão Intel x64. "
            + "Este também é um problema comum se você tiver drivers desatualizados. Consulte este guia se estiver no Windows e desativar isso não funcionar: https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F";
}

@Override
public String no_tienes_las_dependencias_necesitas() {
    return "Você não tem todas as dependências necessárias:";
}

@Override
public String linea_de_dependencia(String linea) {
    return linea.replace("Requested by", "Solicitado por").replace("Expected range", "Intervalo esperado");
}

    @Override
    public String local_headless(String archivo) {
        return "Seu relatório do CrashDetector está aqui: " + archivo;
    }


    @Override
    // Texto da interface gráfica em português
    public String texto_de_gui() {
        return "Esta é a interface gráfica do CrashDetector. Se o jogo fechar sem problemas, ignore-o.";
    }

    @Override
    // Versão em português
    public String texto_de_buton_local_enlance() {
        return "Ver Relatório";
    }

    @Override
    // Texto abaixo do botão local
    public String texto_debajo_de_buton_local_enlance() {
        return "Visualize um relatório local no navegador";
    }

    @Override
    // Texto do botão de compartilhar
    public String texto_de_buton_compartir_enlance() {
        return "Compartilhar Relatório";
    }

    @Override
    // Detalhes do botão de compartilhar
    public String texto_debajo_de_buton_compartir_enlance() {
        return "Compartilhar relatório, logs serão carregados para securelogger.net e armazenados em outro site por 3 dias";
    }
   @Override
public String problematico_jar() {
    return "<b>Arquivos JAR potencialmente problemáticos (Priorizar FATAL > Nível alto > Linha baixa):</b>";
}

@Override
public String nivel() {
    return "<b>lvl: </b>";
}

@Override
public String possibladad_fatal() {
    return "<b>Possivelmente Fatal:</b> ";
}

@Override
public String modids_problematicos() {
    return "<b>ModIDs problemáticos encontrados (Priorizar FATAL > Nível baixo > Linha baixa):</b>";
}

@Override
public String packages_problematicos() {
    return "<b>Pacotes problemáticos encontrados (Priorizar FATAL > Nível baixo > Linha baixa):</b>";
}

@Override
public String faltar_de_clases_fatales() {
    return "<b>Classes fatais ausentes encontradas:</b>";
}

@Override
public String corchetes_ondulados() {
    return "<b>Conteúdo em {} (Mais importante no topo, apenas 20 primeiros):</b>";
}

@Override
public String config_spongemixin_problematico(String archivo) {
    return "<b>Configuração problemática do SpongeMixin detectada: " + archivo + "</b>";
}



@Override
public String module_resolution_exception(String modules, String paquete) {
    return "Você tem Mods com pacotes duplicados: " + modules + " pacote duplicado " + paquete.replace(".", "/") + ". Você pode resolver isso removendo o pacote (pasta) do arquivo jar. Você pode abrir o arquivo jar usando um software de arquivamento como file-roller, WinRAR ou 7-Zip, ou alterar a extensão do arquivo de .jar para .zip, excluir a pasta e depois alterá-la novamente para um arquivo .jar.";
}


@Override
public String modlauncher_mods_duplicado(String linea) {
    return "<b>Você tem Mods duplicados</b> " + linea.replace("from mod files", "de arquivos de mods");
}

@Override
public String mcforge_mod_suspechoso() {
    return "<b>MinecraftForge suspeito: Este mod tem um problema:</b> ";
}

@Override
public String lithostichctov() {
    return "<b>CTOV precisa de lithostitched, você pode instalá-lo aqui https://www.curseforge.com/minecraft/mc-mods/lithostitched</b>";
}


}
