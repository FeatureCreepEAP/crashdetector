package com.asbestosstar.crashdetector;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.cargador.Cargador;

//Handover
public class Entregar {

    public static File archivo = new File("crash_detector/entregar");

    // escritor
    public static void comenzarEntregar() {
        String idApp = App.obtenerApp() != null ? App.obtenerApp().id() : "";

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
            args = eliminarTokenDeAcceso(args);
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
                    if (o instanceof App a && id.equals(a.id())) return a;
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

    private static String eliminarTokenDeAcceso(String args) {
        if (args == null || args.isEmpty()) return "";
        String sin = args.replaceAll("(?i)\\s*--token\\s+\\S+", "");
        sin = sin.replaceAll("(?i)\\s*-token\\s+\\S+", "");
        sin = sin.replaceAll("(?i)\\s*token\\s*=\\s*\\S+", "");
        sin = sin.replaceAll("(?i)\\s*token\\s+\\S+", "");
        return sin.trim().replaceAll("\\s{2,}", " ");
    }

    // intenta obtener args del programa desde sun.java.command
    private static String obtenerArgsDelPrograma() {
        try {
            String cmd = System.getProperty("sun.java.command", "");
            if (cmd == null || cmd.isEmpty()) {
                // intento alterno con RuntimeMXBean
                cmd = String.join(" ", ManagementFactory.getRuntimeMXBean().getInputArguments());
                // inputArguments trae args de la JVM no del programa, se usa solo si no hay nada
                return cmd != null ? cmd : "";
            }
            // sun.java.command trae clase o jar seguido de args
            // se remueve el primer token y se devuelve el resto
            String[] partes = cmd.split("\\s+");
            if (partes.length <= 1) return "";
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < partes.length; i++) {
                if (i > 1) sb.append(' ');
                sb.append(partes[i]);
            }
            return sb.toString();
        } catch (Throwable t) {
            return "";
        }
    }
}
