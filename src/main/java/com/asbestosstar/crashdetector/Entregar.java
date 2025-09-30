package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.Buscardor;
import com.asbestosstar.crashdetector.cargador.Cargador;

//Handover
public class Entregar {

    public static File archivo = new File("crash_detector/entregar");
    private static final String MASK = "*********************************";

    // escritor
    public static void comenzarEntregar() {
        String idApp = App.obtenerApp() != null ? App.obtenerApp().id() : "";
Buscardor.cargadoresPredetermindado();
        // se detectan cargadores activos sin tocar la lista global
        List<Cargador> activos = new ArrayList<Cargador>();
        for (Cargador c : Cargador.cargadores) {
            try {
                if (c.cargadorEsActivado()) {
                    activos.add(c);
                }
            } catch (Throwable ignored) {}
        }

        // args actuales sin usar ARGS_DE_APP
        String args = obtenerArgsDelPrograma();
        if (!Config.obtenerInstancia().habilitarTokenDeAccesoEnLaEntregaDelMonitorDePID()) {
            args = eliminarTokenDeAcceso(args); // ahora enmascara en lugar de borrar
        }

        // no fijar Statics.ARGS_DE_APP aqui
        // escribir archivo
        String contenido = construirContenidoArchivo(idApp, args, activos);
        escribirArchivo(contenido);
    }

    // lector
    public static void recibir() {
        if (!archivo.exists() || !archivo.isFile()) {
            return;
        }
        try {
            List<String> lineas = java.nio.file.Files.readAllLines(archivo.toPath(), StandardCharsets.UTF_8);
            String idApp = "";
            String args = "";
            String idsCargadores = "";

            for (String l : lineas) {
                int p = l.indexOf(':');
                if (p <= 0) continue;
                String k = l.substring(0, p).trim();
                String v = l.substring(p + 1).trim();
                if ("app".equals(k)) idApp = v;
                else if ("args".equals(k)) args = v;
                else if ("cargadores".equals(k)) idsCargadores = v;
            }

            // restaurar app
            Statics.APP = buscarAppPorId(idApp);

            // fijar ARGS_DE_APP solo aqui
            Statics.ARGS_DE_APP = args != null ? args : "";

            
            Buscardor.cargadoresPredetermindado();
            // agregar cargadores activos segun archivo sin limpiar la lista global
            if (idsCargadores != null && !idsCargadores.isEmpty()) {
                String[] parts = idsCargadores.split(",");
                for (String id : parts) {
                    String t = id.trim();
                    if (t.isEmpty()) continue;
                    Cargador inst = buscarCargadorPorId(t);
                    if (inst != null && !Cargador.cargadores_activados.contains(inst)) {
                        Cargador.cargadores_activados.add(inst);
                    }
                }
            }
        } catch (IOException ignored) {}
        archivo.delete();
    }

    // utilidades

    private static String construirContenidoArchivo(String idApp, String args, List<Cargador> activos) {
        StringBuilder ids = new StringBuilder();
        if (activos != null && !activos.isEmpty()) {
            for (Cargador c : activos) {
                try {
                    String id = c.id();
                    if (id != null && !id.isEmpty()) {
                        if (ids.length() > 0) ids.append(',');
                        ids.append(id);
                    }
                } catch (Throwable ignored) {}
            }
        }
        StringBuilder out = new StringBuilder();
        out.append("app: ").append(idApp == null ? "" : idApp).append('\n');
        out.append("args: ").append(args == null ? "" : args).append('\n');
        out.append("cargadores: ").append(ids.toString()).append('\n');
        return out.toString();
    }

    private static void escribirArchivo(String contenido) {
        try {
            File dir = archivo.getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            java.nio.file.Files.write(archivo.toPath(), contenido.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {}
    }

    private static App buscarAppPorId(String id) {
        if (id == null || id.isEmpty()) return null;
        try {
            if (App.APPS instanceof java.util.Map) {
                @SuppressWarnings("unchecked")
                java.util.Map<String, App> mapa = (java.util.Map<String, App>) App.APPS;
                return mapa.get(id);
            }
            if (App.APPS instanceof java.lang.Iterable) {
                for (Object o : (Iterable<?>) App.APPS) {
                    if (o instanceof App && id.equals(((App)o).id())) return (App)o;
                }
            }
        } catch (Throwable ignored) {}
        return null;
    }

    private static Cargador buscarCargadorPorId(String id) {
        for (Cargador c : Cargador.cargadores) {
            try {
                if (id.equals(c.id())) return c;
            } catch (Throwable ignored) {}
        }
        return null;
    }

    /**
     * Enmascara el valor del access token, preservando el campo/clave y (si existían) las comillas.
     * Soporta variantes:
     *   --accessToken <valor>
     *   --accessToken="<valor>"
     *   --accessToken='<valor>'
     *   --accessToken=<valor>
     *   "accessToken":"valor"  (JSON)
     *   'accessToken':'valor'  (JSON con comillas simples)
     */
    private static String eliminarTokenDeAcceso(String args) {
        if (args == null || args.isEmpty()) return "";

        String out = args;

        // Formatos con '=' y comillas
        out = out.replaceAll("(?i)(--accessToken\\s*=\\s*\")([^\"]*)(\")", "$1" + MASK + "$3");
        out = out.replaceAll("(?i)(--accessToken\\s*=\\s*')([^']*)(')", "$1" + MASK + "$3");
        // Formato con '=' sin comillas
        out = out.replaceAll("(?i)(--accessToken\\s*=\\s*)(\\S+)", "$1" + MASK);

        // Formatos con espacio y comillas
        out = out.replaceAll("(?i)(--accessToken\\s+\")([^\"]*)(\")", "$1" + MASK + "$3");
        out = out.replaceAll("(?i)(--accessToken\\s+')([^']*)(')", "$1" + MASK + "$3");
        // Formato con espacio sin comillas
        out = out.replaceAll("(?i)(--accessToken\\s+)(\\S+)", "$1" + MASK);

        // Estilo JSON (dobles y simples comillas)
        out = out.replaceAll("(?i)(\"accessToken\"\\s*:\\s*\")([^\"]*)(\")", "$1" + MASK + "$3");
        out = out.replaceAll("(?i)('accessToken'\\s*:\\s*')([^']*)(')", "$1" + MASK + "$3");

        return out;
    }

    // intenta obtener args del programa desde sun.java.command
    private static String obtenerArgsDelPrograma() {
        try {
            String cmd = System.getProperty("sun.java.command", "");
            if (cmd == null || cmd.isEmpty()) {
                // intento alterno con RuntimeMXBean (nota: trae args de la JVM, no siempre los del programa)
                cmd = String.join(" ", ManagementFactory.getRuntimeMXBean().getInputArguments());
                return cmd != null ? cmd : "";
            }
            // Devolver tal cual (incluye clase/jar + args)
            return cmd;
        } catch (Throwable t) {
            return "";
        }
    }
}
