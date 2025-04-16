package com.asbestosstar.crashdetectormc;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

    // Valores predeterminados para los campos de configuración
    private static final String VALOR_POR_DEFECTO_SITO_DE_INFORMES = "https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb";
    private static final String VALOR_POR_DEFECTO_COLOR_FONDO = "282828"; // Gris oscuro
    private static final String VALOR_POR_DEFECTO_COLOR_TEXTO = "FFFFFF"; // Blanco
    private static final String VALOR_POR_DEFECTO_COLOR_BOTON = "46468C"; // Azul oscuro
    private static final String VALOR_POR_DEFECTO_COLOR_CAJA_TEXTO = "3C3C3C"; // Gris medio
    private static final String VALOR_POR_DEFECTO_COLOR_ENLACE = "96C8FF"; // Azul claro
    private static final String VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS = "4287F5"; // Azul brillante

    // Ruta al archivo de configuración
    private static final String RUTA_ARCHIVO_CONFIG = "crashdetector_config.properties";

    // Propiedades de configuración
    private Properties propiedadesConfig;

    // Instancia única para la clase Config (patrón Singleton)
    private static Config instancia;

    /**
     * Constructor privado para cargar o crear el archivo de configuración.
     */
    private Config() {
        propiedadesConfig = new Properties();

        // Cargar el archivo de configuración si existe, de lo contrario crearlo con valores predeterminados
        File archivoConfig = new File(RUTA_ARCHIVO_CONFIG);
        if (archivoConfig.exists()) {
            try (FileReader lector = new FileReader(archivoConfig)) {
                propiedadesConfig.load(lector);
            } catch (IOException e) {
                System.err.println("Error al leer el archivo de configuración: " + e.getMessage());
            }
        } else {
            // Crear configuración predeterminada
            propiedadesConfig.setProperty("sito_de_informes", VALOR_POR_DEFECTO_SITO_DE_INFORMES);
            propiedadesConfig.setProperty("color_fondo", VALOR_POR_DEFECTO_COLOR_FONDO);
            propiedadesConfig.setProperty("color_texto", VALOR_POR_DEFECTO_COLOR_TEXTO);
            propiedadesConfig.setProperty("color_boton", VALOR_POR_DEFECTO_COLOR_BOTON);
            propiedadesConfig.setProperty("color_caja_texto", VALOR_POR_DEFECTO_COLOR_CAJA_TEXTO);
            propiedadesConfig.setProperty("color_enlace", VALOR_POR_DEFECTO_COLOR_ENLACE);
            propiedadesConfig.setProperty("color_de_titulos_de_consolas", VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS);

            // Guardar la configuración predeterminada en un archivo
            try (FileWriter escritor = new FileWriter(archivoConfig)) {
                propiedadesConfig.store(escritor, "Configuración de CrashDetectorMC");
            } catch (IOException e) {
                System.err.println("Error al crear el archivo de configuración: " + e.getMessage());
            }
        }
    }

    /**
     * Obtener la instancia única de la clase Config.
     *
     * @return La instancia de Config.
     */
    public static synchronized Config obtenerInstancia() {
        if (instancia == null) {
            instancia = new Config();
        }
        return instancia;
    }

    /**
     * Obtener el valor de sito_de_informes.
     *
     * @return El valor de sito_de_informes.
     */
    public String obtenerSitoDeInformes() {
        return propiedadesConfig.getProperty("sito_de_informes", VALOR_POR_DEFECTO_SITO_DE_INFORMES);
    }

    /**
     * Establecer el valor de sito_de_informes.
     *
     * @param sitioDeInformes El nuevo valor de sito_de_informes.
     */
    public void establecerSitoDeInformes(String sitioDeInformes) {
        propiedadesConfig.setProperty("sito_de_informes", sitioDeInformes);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de fondo.
     *
     * @return El valor del color de fondo.
     */
    public String obtenerColorFondo() {
        return propiedadesConfig.getProperty("color_fondo", VALOR_POR_DEFECTO_COLOR_FONDO);
    }

    /**
     * Establecer el valor del color de fondo.
     *
     * @param color El nuevo valor del color de fondo.
     */
    public void establecerColorFondo(String color) {
        propiedadesConfig.setProperty("color_fondo", color);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de texto.
     *
     * @return El valor del color de texto.
     */
    public String obtenerColorTexto() {
        return propiedadesConfig.getProperty("color_texto", VALOR_POR_DEFECTO_COLOR_TEXTO);
    }

    /**
     * Establecer el valor del color de texto.
     *
     * @param color El nuevo valor del color de texto.
     */
    public void establecerColorTexto(String color) {
        propiedadesConfig.setProperty("color_texto", color);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de botón.
     *
     * @return El valor del color de botón.
     */
    public String obtenerColorBoton() {
        return propiedadesConfig.getProperty("color_boton", VALOR_POR_DEFECTO_COLOR_BOTON);
    }

    /**
     * Establecer el valor del color de botón.
     *
     * @param color El nuevo valor del color de botón.
     */
    public void establecerColorBoton(String color) {
        propiedadesConfig.setProperty("color_boton", color);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de caja de texto.
     *
     * @return El valor del color de caja de texto.
     */
    public String obtenerColorCajaTexto() {
        return propiedadesConfig.getProperty("color_caja_texto", VALOR_POR_DEFECTO_COLOR_CAJA_TEXTO);
    }

    /**
     * Establecer el valor del color de caja de texto.
     *
     * @param color El nuevo valor del color de caja de texto.
     */
    public void establecerColorCajaTexto(String color) {
        propiedadesConfig.setProperty("color_caja_texto", color);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de enlace.
     *
     * @return El valor del color de enlace.
     */
    public String obtenerColorEnlace() {
        return propiedadesConfig.getProperty("color_enlace", VALOR_POR_DEFECTO_COLOR_ENLACE);
    }

    /**
     * Establecer el valor del color de enlace.
     *
     * @param color El nuevo valor del color de enlace.
     */
    public void establecerColorEnlace(String color) {
        propiedadesConfig.setProperty("color_enlace", color);
        guardarConfiguracion();
    }

    /**
     * Obtener el valor del color de títulos de consolas.
     *
     * @return El valor del color de títulos de consolas.
     */
    public String obtenerColorDeTitulosDeConsolas() {
        return propiedadesConfig.getProperty("color_de_titulos_de_consolas", VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS);
    }

    /**
     * Establecer el valor del color de títulos de consolas.
     *
     * @param color El nuevo valor del color de títulos de consolas.
     */
    public void establecerColorDeTitulosDeConsolas(String color) {
        propiedadesConfig.setProperty("color_de_titulos_de_consolas", color);
        guardarConfiguracion();
    }

    /**
     * Guardar la configuración actual en el archivo.
     */
    private void guardarConfiguracion() {
        try (FileWriter escritor = new FileWriter(RUTA_ARCHIVO_CONFIG)) {
            propiedadesConfig.store(escritor, "Configuración de CrashDetectorMC");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo de configuración: " + e.getMessage());
        }
    }
    
    
       /**
     * Convierte un código de color HTML (#RRGGBB) en un objeto Color.
     *
     * @param hexColor Código de color en formato hexadecimal (e.g., "#RRGGBB").
     * @return Objeto Color correspondiente al código hexadecimal.
     */
    public static Color convertirAColor(String color) {
    String hexColor = "#"+color;
        if (hexColor == null || !hexColor.startsWith("#") || hexColor.length() != 7) {
            throw new IllegalArgumentException("Formato de color inválido: " + hexColor);
        }
        int r = Integer.parseInt(hexColor.substring(1, 3), 16);
        int g = Integer.parseInt(hexColor.substring(3, 5), 16);
        int b = Integer.parseInt(hexColor.substring(5, 7), 16);
        return new Color(r, g, b);
    }
    
}
