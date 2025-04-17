package com.asbestosstar.crashdetectormc.idioma;

import com.asbestosstar.crashdetectormc.Config;
import com.asbestosstar.crashdetectormc.Idioma;

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
    public String probelma_con_graficas_ati() {
        return "<span style='color:#" + config.obtenerColorError() + "'>Atualizar os drivers pode ajudar. Nota: quando os drivers estão corrompidos, a atualização normal pode não funcionar. Siga o guia abaixo. IMPORTANTE: Usuários de NVIDIA devem definir prioridade máxima para desempenho no painel de controle e nas configurações do Windows para todos os processos relacionados ao Minecraft (Java e launcher). Leia este guia: <a href='https://forums.minecraftforge.net/topic/125488-rules-and-frequently-asked-questions-faq/#:~:text=How%20do%20I%20update%20my%20drivers%3F' style='color:#" + config.obtenerColorEnlace() + "'>Guia de Atualização de Drivers</a></span>";
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
    public String config_spongemixin_problematico(String archivo) {
        return "<b style='color:#" + config.obtenerColorError() + "'>Configuração problemática do SpongeMixin detectada: </b>" + archivo;
    }

    @Override
    public String module_resolution_exception(String modules, String paquete) {
        return "<span style='color:#" + config.obtenerColorError() + "'>Pacotes duplicados detectados nos mods: " + modules + " duplicando " + paquete.replace(".", "/") + ". Para resolver, remova a pasta do jar usando programas como WinRAR/7z ou renomeie o .jar para .zip, remova a pasta e renomeie novamente.</span>";
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
}
