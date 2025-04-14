package com.asbestosstar.crashdetectormc;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

    // Valores predeterminados para los campos de configuración
    private static final String VALOR_POR_DEFECTO_SITO_DE_INFORMES = "https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb";
    private static final String VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS = "4287f5";

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
     * Obtener el valor de color_de_titulos_de_consolas.
     *
     * @return El valor de color_de_titulos_de_consolas.
     */
    public String obtenerColorDeTitulosDeConsolas() {
        return propiedadesConfig.getProperty("color_de_titulos_de_consolas", VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS);
    }

    /**
     * Establecer el valor de color_de_titulos_de_consolas.
     *
     * @param color El nuevo valor de color_de_titulos_de_consolas.
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
}