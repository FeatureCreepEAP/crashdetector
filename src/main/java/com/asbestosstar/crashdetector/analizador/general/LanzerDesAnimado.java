package com.asbestosstar.crashdetector.analizador.general;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.Idioma;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * Detecta cuando el usuario está usando un launcher explícitamente desaconsejado.
 * Este verificador ignora completamente el archivo de launchers recomendados,
 * a menos que exista y sea no vacío (en cuyo caso muestra la lista al final).
 */
public class LanzerDesAnimado implements Verificaciones {

    public static final Path ARCHIVO_DESANIMADOS = Statics.carpeta.resolve("lanzeres_desanimados.json");
    public static final Path ARCHIVO_ANIMADOS = Statics.carpeta.resolve("lanzeres_animados.json");
    public static final String LANZADOR_ACTUAL = Statics.lanzer_del_app;

    private boolean activado = false;
    private String mensaje = "";
    boolean completa=true;

    @Override
    public void verificar(Consola consola) {
    	if(completa) {
    		return;
    	}
    	this.completa=true;
        if (LANZADOR_ACTUAL == null || LANZADOR_ACTUAL.trim().isEmpty()) {
            return;
        }

        // Si el archivo de desanimados no existe o está vacío, no hacer nada
        if (!ARCHIVO_DESANIMADOS.toFile().exists()) {
            return;
        }

        String contenido;
        try {
            contenido = new String(Files.readAllBytes(ARCHIVO_DESANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
        } catch (IOException e) {
            return;
        }

        if (contenido == null || contenido.trim().isEmpty()) {
            return;
        }

        // Parsear launchers desaconsejados
        Nodo raiz;
        try {
            raiz = Json.leer(contenido);
        } catch (Exception e) {
            return;
        }

        if (!raiz.esObjeto()) {
            return;
        }

        // Ver si el launcher actual está en la lista de desaconsejados
        Nodo entrada = raiz.obtener(LANZADOR_ACTUAL);
        if (entrada == null || entrada.esArreglo()) {
            return;
        }

        // Extraer motivo en el idioma correcto
        String motivo = "";
        if (entrada.esObjeto()) {
            String langActual = MonitorDePID.idioma.codigo();
            String langRespaldo = Idioma.idioma_respaldo.obtener();
            String[] orden = { langActual, langRespaldo, "es" };
            for (String lang : orden) {
                if (lang != null && !lang.isEmpty()) {
                    Nodo nodoMotivo = entrada.obtener(lang);
                    if (nodoMotivo != null && !nodoMotivo.esObjeto() && !nodoMotivo.esArreglo()) {
                        String txt = nodoMotivo.comoCadena();
                        if (txt != null && !txt.trim().isEmpty()) {
                            motivo = txt;
                            break;
                        }
                    }
                }
            }
        }

        // Construir mensaje
        StringBuilder sb = new StringBuilder();
        sb.append(MonitorDePID.idioma.lanzer_desanimado_titulo(LANZADOR_ACTUAL));
        if (!motivo.isEmpty()) {
            sb.append(nl_html).append(motivo);
        }
        sb.append(nl_html)
          .append(MonitorDePID.idioma.lanzer_desanimado_problemas_comunes());

        // Solo mencionar launchers animados si el archivo existe y tiene contenido
        if (ARCHIVO_ANIMADOS.toFile().exists()) {
            try {
                String contenidoAnim = new String(Files.readAllBytes(ARCHIVO_ANIMADOS), java.nio.charset.StandardCharsets.UTF_8);
                if (contenidoAnim != null && !contenidoAnim.trim().isEmpty()) {
                    Nodo raizAnim = Json.leer(contenidoAnim);
                    if (raizAnim.esArreglo() && raizAnim.tamano() > 0) {
                        sb.append(nl_html)
                          .append(MonitorDePID.idioma.lanzer_desanimado_usar_animados());
                        sb.append(" ");
                        boolean primero = true;
                        for (int i = 0; i < raizAnim.tamano(); i++) {
                            Nodo item = raizAnim.en(i);
                            if (item != null && !item.esObjeto() && !item.esArreglo()) {
                                String id = item.comoCadena();
                                if (id != null && !id.trim().isEmpty()) {
                                    if (!primero) sb.append(", ");
                                    sb.append("<code>").append(id).append("</code>");
                                    primero = false;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ignored) {
                // Ignorar errores al leer el archivo de animados
            }
        }

        this.mensaje = sb.toString();
        this.activado = true;
    }

    @Override
    public Verificaciones nueva() {
        return new LanzerDesAnimado();
    }

    @Override
    public boolean activado() {
        return activado;
    }

    @Override
    public float prioridad() {
        return 1400.0f;
    }

    @Override
    public String mensaje() {
        return mensaje;
    }

    @Override
    public String nombre() {
        return MonitorDePID.idioma.nombre_lanzer_desanimado();
    }

    @Override
    public QuickFix solucion() {
        Builder builder = new Builder(nombre());
        builder.agregarEtiqueta(MonitorDePID.idioma.lanzer_desanimado_cambiar_lanzer());
        return builder.construir();
    }

    @Override
    public boolean ocupaTrazo(TraceInfo trazo) {
        return false;
    }

    @Override
    public String id() {
        return "lanzer_desanimado";
    }
	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"+this.getClass().getSimpleName()+".java";
	}
	
	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
	
	
    
    
    
    
}