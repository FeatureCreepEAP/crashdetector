package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Analiza errores cuando los mods contienen caracteres inválidos en sus nombres
 * de módulo. Detecta específicamente el error "Invalid module name: 'X' is not
 * a Java identifier". Incluye el nombre del módulo y la parte inválida en los
 * mensajes.
 */
public class ErrorCaracteresInvalidosEnNombre implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String nombreModulo = "";
	private String parteInvalida = "";

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error de
		// nombre inválido
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			// Detecta cualquier caso de "Invalid module name: 'X' is not a Java identifier"
			if (linea.contains("Invalid module name: '") && linea.contains("' is not a Java identifier")) {

				// Extrae el nombre completo del módulo y la parte inválida usando expresiones
				// regulares
				Pattern pattern = Pattern.compile("IllegalArgumentException: ([^:]+): Invalid module name: '([^']+)'");
				Matcher matcher = pattern.matcher(linea);

				if (matcher.find()) {
					nombreModulo = matcher.group(1);
					parteInvalida = matcher.group(2);

					mensaje = MonitorDePID.idioma.errorCaracteresInvalidosEnNombre(nombreModulo, parteInvalida)
							+ Verificaciones.nl_html;
					activado = true;
					break; // Detiene al encontrar el primer error (es crítico y ocurre una vez)
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorCaracteresInvalidosEnNombre();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 950.0f; // Máxima prioridad - error crítico que impide iniciar el juego
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_caracteres_invalidos();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_caracteres_invalidos(nombreModulo, parteInvalida))
				.agregarEtiqueta(MonitorDePID.idioma.paso2_caracteres_invalidos(nombreModulo))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_caracteres_invalidos()).construir();
	}
}