package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.buscar.Buscardor;

/**
 * Analiza errores cuando falla el registro de suscriptores automáticos. Detecta
 * específicamente el error "Failed to register automatic subscribers. ModID:
 * [modid], class [classname]". Incluye tanto el modid como la clase en el
 * mensaje y usa Buscardor para encontrar los JARs relacionados.
 */
public class ErrorRegistroSuscriptoresAutomaticos implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String modId = "";
	private String nombreClase = "";
	private List<String> modsUbicacion = new ArrayList<>();

	@Override
	public void verificar(Consola consola) {
		String contenidoConsola = consola.contenido_verificar;

		// Analiza cada línea del registro buscando el patrón específico de error
		for (String linea : contenidoConsola.split(Verificaciones.nl)) {
			// Detecta el error específico de registro de suscriptores automáticos
			if (linea.contains("Failed to register automatic subscribers. ModID:")) {

				// Extrae el modid y el nombre de la clase usando expresión regular
				Pattern pattern = Pattern
						.compile("Failed to register automatic subscribers\\. ModID: ([^,]+), class ([^\\s]+)");
				Matcher matcher = pattern.matcher(linea);

				if (matcher.find()) {
					modId = matcher.group(1).trim();
					nombreClase = matcher.group(2).trim();

					// Convierte el nombre de clase a formato de ruta para buscar en JARs
					String classPath = nombreClase.replace('.', '/') + ".class";

					// Busca mods que contienen esta clase
					List<ArchivoDeMod> modsPotenciales = Buscardor.buscarModsConTermino(classPath);

					// Extrae las ubicaciones para publicar de cada mod encontrado
					for (ArchivoDeMod mod : modsPotenciales) {
						modsUbicacion.add(mod.ubicacion_para_publicar());
					}

					mensaje = MonitorDePID.idioma.errorRegistroSuscriptoresAutomaticos(modId, nombreClase,
							modsUbicacion) + Verificaciones.nl_html;
					activado = true;
					break; // Detiene al encontrar el primer error
				}
			}
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorRegistroSuscriptoresAutomaticos();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Prioridad media-alta - error que impide funcionalidad específica del mod
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombre_de_error_registro_suscriptores_automaticos();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.paso1_registro_suscriptores_automaticos(modId, nombreClase))
				.agregarEtiqueta(
						MonitorDePID.idioma.paso2_registro_suscriptores_automaticos(modId, nombreClase, modsUbicacion))
				.agregarEtiqueta(MonitorDePID.idioma.paso3_registro_suscriptores_automaticos(modId))
				.agregarEtiqueta(MonitorDePID.idioma.paso4_registro_suscriptores_automaticos()) // Nuevo paso añadido
				.construir();
	}
}