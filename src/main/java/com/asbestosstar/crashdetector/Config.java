package com.asbestosstar.crashdetector;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

	// Valores predeterminados para los campos de configuración
	public static final String VALOR_POR_DEFECTO_SITO_DE_INFORMES = "https://asbestosstar.egoism.jp/crash_detector/crash_detector_servidor.rb";
	private static final String VALOR_POR_DEFECTO_COLOR_FONDO = "282828"; // Gris oscuro
	private static final String VALOR_POR_DEFECTO_COLOR_TEXTO = "FFFFFF"; // Blanco
	private static final String VALOR_POR_DEFECTO_COLOR_BOTON = "46468C"; // Azul oscuro
	private static final String VALOR_POR_DEFECTO_COLOR_CAJA_TEXTO = "3C3C3C"; // Gris medio
	private static final String VALOR_POR_DEFECTO_COLOR_ENLACE = "56CCF2"; // Azul claro
	private static final String VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS = "56CCF2"; // Azul brillante
	private static final String VALOR_POR_DEFECTO_COLOR_ERROR = "FF6347"; // rojo
	private static final String VALOR_POR_DEFECTO_COLOR_ADVERTENCIA = "FFD700"; // narajnja
	private static final String VALOR_POR_DEFECTO_COLOR_INFO = "FFFFFF"; // blanco
	private static final String VALOR_POR_DEFECTO_COLOR_TITULO = "56CCF2"; // azul
	private static final String VALOR_POR_DEFECTO_COLOR_ENLACE_TEXTO = "56CCF2"; // azul mas contrast

	public static final String HMCL_CARPETA = "";// ES SOLO PARA HMCL

	// Valores predeterminados para las nuevas propiedades
	public static final String VALOR_POR_DEFECTO_API_DE_REGISTROS_SELECCIONADA = "SecureLogger";
	public static final String VALOR_POR_DEFECTO_SITIO_DE_REGISTROS_SELECCIONADO = "https://securelogger.net/save/log?";
	private static final boolean VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS = true;
	private static final boolean PROXY_SYSOUT_SYSERR = false;
	private static final boolean EXTREGAR_TOKEN_DE_ACCESO = false;
	public static final String NOMBRE_CD = "CrashDetector";

	// Ruta al archivo de configuración
	private static final String RUTA_ARCHIVO_CONFIG = MonitorDePID.carpeta.resolve("crashdetector_config.properties")
			.toString();
	public static File archivoConfig = new File(RUTA_ARCHIVO_CONFIG);

	// Propiedades de configuración
	public Properties propiedadesConfig;

	// Instancia única para la clase Config (patrón Singleton)
	private static Config instancia;

	/**
	 * Constructor privado para cargar o crear el archivo de configuración.
	 */
	private Config() {
		propiedadesConfig = new Properties();

		// Cargar el archivo de configuración si existe, de lo contrario crearlo con
		// valores predeterminados
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
			propiedadesConfig.setProperty("color_de_titulos_de_consolas",
					VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS);
			propiedadesConfig.setProperty("color_error", VALOR_POR_DEFECTO_COLOR_ERROR);
			propiedadesConfig.setProperty("color_advertencia", VALOR_POR_DEFECTO_COLOR_ADVERTENCIA);
			propiedadesConfig.setProperty("color_info", VALOR_POR_DEFECTO_COLOR_INFO);
			propiedadesConfig.setProperty("color_titulo", VALOR_POR_DEFECTO_COLOR_TITULO);
			propiedadesConfig.setProperty("color_enlace_texto", VALOR_POR_DEFECTO_COLOR_ENLACE_TEXTO);

			propiedadesConfig.setProperty("hmcl_carpeta", HMCL_CARPETA);

			// Añadimos las nuevas propiedades predeterminadas
			propiedadesConfig.setProperty("api_de_registros_seleccionada",
					VALOR_POR_DEFECTO_API_DE_REGISTROS_SELECCIONADA);
			propiedadesConfig.setProperty("sitio_de_registros_seleccionado",
					VALOR_POR_DEFECTO_SITIO_DE_REGISTROS_SELECCIONADO);
			propiedadesConfig.setProperty("anonimizar_registros",
					String.valueOf(VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS));
			propiedadesConfig.setProperty("proxy_sysout_syserr", String.valueOf(PROXY_SYSOUT_SYSERR));
			propiedadesConfig.setProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID",
					String.valueOf(EXTREGAR_TOKEN_DE_ACCESO));

			propiedadesConfig.setProperty("nombre_cd", NOMBRE_CD);

			guardar();
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

	public void guardar() {
		// Guardar la configuración predeterminada en un archivo
		archivoConfig.getParentFile().mkdirs();
		try (FileWriter escritor = new FileWriter(archivoConfig)) {
			propiedadesConfig.store(escritor, "Configuración de CrashDetectorMC");
		} catch (IOException e) {
			System.err.println("Error al crear el archivo de configuración: " + e.getMessage());
		}
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
	 * Obtener el valor del color de fondo.
	 *
	 * @return El valor del color de fondo.
	 */
	public String obtenerColorFondo() {
		return propiedadesConfig.getProperty("color_fondo", VALOR_POR_DEFECTO_COLOR_FONDO);
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
	 * Obtener el valor del color de botón.
	 *
	 * @return El valor del color de botón.
	 */
	public String obtenerColorBoton() {
		return propiedadesConfig.getProperty("color_boton", VALOR_POR_DEFECTO_COLOR_BOTON);
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
	 * Obtener el valor del color de enlace.
	 *
	 * @return El valor del color de enlace.
	 */
	public String obtenerColorEnlace() {
		return propiedadesConfig.getProperty("color_enlace", VALOR_POR_DEFECTO_COLOR_ENLACE);
	}

	/**
	 * Obtener el valor del color de títulos de consolas.
	 *
	 * @return El valor del color de títulos de consolas.
	 */
	public String obtenerColorDeTitulosDeConsolas() {
		return propiedadesConfig.getProperty("color_de_titulos_de_consolas",
				VALOR_POR_DEFECTO_COLOR_DE_TITULOS_DE_CONSOLAS);
	}

	/**
	 * Obtener el valor del color de error.
	 *
	 * @return El valor del color de error.
	 */
	public String obtenerColorError() {
		return propiedadesConfig.getProperty("color_error", VALOR_POR_DEFECTO_COLOR_ERROR);
	}

	/**
	 * Obtener el valor del color de advertencia.
	 *
	 * @return El valor del color de advertencia.
	 */
	public String obtenerColorAdvertencia() {
		return propiedadesConfig.getProperty("color_advertencia", VALOR_POR_DEFECTO_COLOR_ADVERTENCIA);
	}

	/**
	 * Obtener el valor del color de informacion.
	 *
	 * @return El valor del color de informacion.
	 */
	public String obtenerColorInfo() {
		return propiedadesConfig.getProperty("color_info", VALOR_POR_DEFECTO_COLOR_INFO);
	}

	public String obtenerColorTitulo() {
		return propiedadesConfig.getProperty("color_titulo", VALOR_POR_DEFECTO_COLOR_TITULO);
	}

	/**
	 * Obtener el valor de api_seleccionada.
	 *
	 * @return El valor de api_seleccionada.
	 */
	public String obtenerApiSeleccionada() {
		return propiedadesConfig.getProperty("api_de_registros_seleccionada",
				VALOR_POR_DEFECTO_API_DE_REGISTROS_SELECCIONADA);
	}

	/**
	 * Obtener el valor de sitio_seleccionado.
	 *
	 * @return El valor de sitio_seleccionado.
	 */
	public String obtenerSitioDeRegistrosSeleccionado() {
		return propiedadesConfig.getProperty("sitio_de_registros_seleccionado",
				VALOR_POR_DEFECTO_SITIO_DE_REGISTROS_SELECCIONADO);
	}

	public void guardarColorFondo(String valor) {
		propiedadesConfig.setProperty("color_fondo", valor);
		this.guardar();
	}

	public void guardarColorTexto(String valor) {
		propiedadesConfig.setProperty("color_texto", valor);
		this.guardar();
	}

	public void guardarColorBoton(String valor) {
		propiedadesConfig.setProperty("color_boton", valor);
		this.guardar();
	}

	public void guardarColorCajaTexto(String valor) {
		propiedadesConfig.setProperty("color_caja_texto", valor);
		this.guardar();
	}

	public void guardarColorEnlace(String valor) {
		propiedadesConfig.setProperty("color_enlace", valor);
		this.guardar();
	}

	public void guardarColorDeTitulosDeConsolas(String valor) {
		propiedadesConfig.setProperty("color_de_titulos_de_consolas", valor);
		this.guardar();
	}

	public void guardarColorError(String valor) {
		propiedadesConfig.setProperty("color_error", valor);
		this.guardar();
	}

	public void guardarColorAdvertencia(String valor) {
		propiedadesConfig.setProperty("color_advertencia", valor);
		this.guardar();
	}

	public void guardarColorInfo(String valor) {
		propiedadesConfig.setProperty("color_info", valor);
		this.guardar();
	}

	public void guardarColorTitulo(String valor) {
		propiedadesConfig.setProperty("color_titulo", valor);
		this.guardar();
	}

	public void guardarColorEnlaceTexto(String valor) {
		propiedadesConfig.setProperty("color_enlace_texto", valor);
		this.guardar();
	}

	/**
	 * Verifica si los registros deben anonimizarse.
	 *
	 * @return true si se debe anonimizar, false en caso contrario.
	 */
	public boolean esAnonimizarRegistros() {
		return Boolean.parseBoolean(propiedadesConfig.getProperty("anonimizar_registros",
				String.valueOf(VALOR_POR_DEFECTO_ANONIMIZAR_REGISTROS)));
	}

	/**
	 * Guarda el valor de api_seleccionada en la configuración.
	 *
	 * @param valor El nuevo valor para api_seleccionada.
	 */
	public void guardarApiSeleccionada(String valor) {
		propiedadesConfig.setProperty("api_de_registros_seleccionada", valor);
		this.guardar();
	}

	/**
	 * Guarda el valor de sitio_seleccionado en la configuración.
	 *
	 * @param valor El nuevo valor para sitio_seleccionado.
	 */
	public void guardarSitioRegistrosSeleccionado(String valor) {
		propiedadesConfig.setProperty("sitio_de_registros_seleccionado", valor);
		this.guardar();
	}

	/**
	 * Guarda el estado de anonimizar_registros en la configuración.
	 *
	 * @param valor true para activar la anonimización, false para desactivarla.
	 */
	public void guardarAnonimizarRegistros(boolean valor) {
		propiedadesConfig.setProperty("anonimizar_registros", String.valueOf(valor));
		this.guardar();
	}

	/**
	 * Guarda el valor de sitio_seleccionado en la configuración.
	 *
	 * @param valor El nuevo valor para sitio_seleccionado.
	 */
	public void guardarSitioDeInformes(String valor) {
		propiedadesConfig.setProperty("sito_de_informes", valor);
		this.guardar();
	}

	/**
	 * Convierte un código de color HTML en un objeto {@link Color} de forma segura.
	 * <p>
	 * Acepta los formatos "#RRGGBB" o "RRGGBB" (con o sin el numeral inicial).
	 *
	 * @param color Cadena del color en formato hexadecimal (ej. "#FF0000" o
	 *              "FF0000").
	 * @return Objeto {@link Color} correspondiente al valor hexadecimal.
	 * @throws IllegalArgumentException Si la cadena es nula, está vacía o tiene un
	 *                                  formato no válido.
	 * @throws NumberFormatException    Si ocurre un error al convertir los valores
	 *                                  hexadecimales.
	 */
	public static Color convertirAColor(String color) throws IllegalArgumentException, NumberFormatException {

		if (color == null) {
			throw new IllegalArgumentException("El valor del color no puede ser nulo.");
		}

		String hex = color.trim();

		// Aceptar tanto "#RRGGBB" como "RRGGBB"
		if (hex.startsWith("#")) {
			hex = hex.substring(1);
		}

		// Validar longitud exacta
		if (hex.length() != 6) {
			throw new IllegalArgumentException("Formato de color inválido (debe ser RRGGBB): " + color);
		}

		// Validar caracteres permitidos
		if (!hex.matches("^[0-9A-Fa-f]{6}$")) {
			throw new IllegalArgumentException("El color contiene caracteres no válidos: " + color);
		}

		int r = Integer.parseInt(hex.substring(0, 2), 16);
		int g = Integer.parseInt(hex.substring(2, 4), 16);
		int b = Integer.parseInt(hex.substring(4, 6), 16);

		return new Color(r, g, b);
	}

	/** * Devuelve el color en formato HTML hexadecimal "#RRGGBB". */
	public static String colorAHexHtml(Color c) {
		return Integer.toHexString(c.getRGB()).substring(2).toUpperCase();
	}

	public String obtenerCarpetaHMCL() {
		// TODO Auto-generated method stub
		return propiedadesConfig.getProperty("hmcl_carpeta", HMCL_CARPETA);
	}

	public String guardarCarpetaHMCL(String string) {
		// TODO Auto-generated method stub
		return propiedadesConfig.getProperty("hmcl_carpeta", string);
	}

	public boolean obtenerProxySysOutSysErr() {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(
				propiedadesConfig.getProperty("proxy_sysout_syserr", String.valueOf(PROXY_SYSOUT_SYSERR)));
	}

	public void guardarProxySysOutSysErr(boolean valor) {
		// TODO Auto-generated method stub
		propiedadesConfig.setProperty("proxy_sysout_syserr", String.valueOf(valor));
	}

	/**
	 * No es recomendado , solo existe para CDLauncher si necesitas servidores en
	 * modio enlinea pero activado es menos seguro. TODO mover a una config solo
	 * para usarios fin
	 * 
	 * @return
	 */
	public boolean obtenerHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID() {
		// TODO Auto-generated method stub
		return Boolean.parseBoolean(propiedadesConfig.getProperty(
				"habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID", String.valueOf(EXTREGAR_TOKEN_DE_ACCESO)));

	}

	/**
	 * No es recomendado , solo existe para CDLauncher si necesitas servidores en
	 * modio enlinea pero activado es menos seguro. TODO mover a una config solo
	 * para usarios fin
	 * 
	 * @return
	 */
	public void guardarHabilitarTokenDeAccesoEnLaEntregaDelMonitorDePID(boolean valor) {
		// TODO Auto-generated method stub
		propiedadesConfig.setProperty("habilitar_token_de_acceso_en_la_entrega_del_MonitorDePID",
				String.valueOf(valor));
	}

	/**
	 * Estabalar el nombre public de esta GUI
	 * 
	 * @param valor
	 */
	public void guardarNombreCD(String valor) {
		propiedadesConfig.setProperty("nombre_cd", valor);
		this.guardar();
	}

	public String obtenerNombreCD() {
		// TODO Auto-generated method stub
		return propiedadesConfig.getProperty("nombre_cd", NOMBRE_CD);
	}
}
