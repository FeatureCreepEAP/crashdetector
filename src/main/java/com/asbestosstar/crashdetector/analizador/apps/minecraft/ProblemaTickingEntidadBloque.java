package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.QuickFix.Builder;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta crashes por "Ticking block entity" y también "Ticking entity".
 * Diseñado para ser tolerante a: - prefijos tipo "[Server thread/ERROR]" -
 * mayúsculas/minúsculas - secciones de detalles lejos de la línea "Description"
 *
 * Formatos soportados (entre otros): - "Description: Ticking block entity" -
 * "Description: Ticking entity" - "-- Entity being ticked --" + "Entity Type:"
 * + "Entity Name:" + "Entity's Block location: World: (x,y,z)"
 */
public class ProblemaTickingEntidadBloque implements VerificacionRapida {

	// Estado final
	private boolean activado = false;
	private String mensaje = "";
	private String nombreEntidad = "";
	private String tipoEntidad = "";
	private int[] coordenadas = new int[] { 0, 0, 0 }; // [x,y,z]
	private boolean coordenadasValidas = false;
	private String enlaceHtml = "";

	// Flags globales
	private boolean hayTickingEnLog = false;

	// Control de activación al ver "Description"
	private int lineaDescription = -1;
	private boolean descriptionDetectada = false;

	// Secciones modernas (Forge 1.16+ suele traer estas secciones)
	private boolean seccionEntidadTickeadaActiva = false;
	private int lineaInicioSeccionEntidad = -1;

	private boolean seccionEntidadBloqueTickeadaActiva = false;
	private int lineaInicioSeccionBloque = -1;
	private boolean posibleTicking = false;

	// Ventanas de parseo (conservadoras pero amplias)
	private static final int VENTANA_DESDE_DESCRIPTION = 600; // para cubrir casos donde los detalles están lejos
	private static final int VENTANA_SECCION_DETALLES = 120; // líneas a partir del encabezado de sección

	private static final String TICKING_BLOCK = "Description: Ticking block entity";
	private static final String TICKING_ENTITY = "Description: Ticking entity";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TICKING_BLOCK, TICKING_ENTITY, "-- Entity being ticked --",
				"-- Block entity being ticked --" };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {

		if (evento == null || evento.linea == null) {
			return;
		}

		posibleTicking = true;

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		if (contenido == null) {
			hayTickingEnLog = false;
			return;
		}
		String lower = contenido;
		hayTickingEnLog = lower.contains("Description: Ticking block entity")
				|| lower.contains("Description: Ticking entity");// TODO eliminar lower
	}

	@Override
	public boolean quiereAnalizarLineas() {
		if (!hayTickingEnLog)
			return false;

		return true;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!hayTickingEnLog || linea == null) {
			return;
		}

		String l = linea.trim();
		String lower = l;

		// 1) Detectar la "Description" (acepta ticking entity y ticking block entity,
		// con prefijos)
		if (lower.contains("Description: Ticking block entity") || lower.contains("Description: Ticking entity")) {
			descriptionDetectada = true;
			lineaDescription = numero_de_linea;

			// Registrar la línea del error SOLO una vez (la primera description encontrada)
			if (enlaceHtml == null || enlaceHtml.isEmpty()) {
				enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			}
			// No retornamos: a veces la misma línea ya trae más info en otros formatos
		}

		// Si nunca vimos la description, evitamos falsos positivos
		if (!descriptionDetectada) {
			return;
		}

		// Si estamos demasiado lejos de la description y no estamos dentro de
		// secciones, cortamos.
		// (Aun así, si se detectan encabezados de sección, se reactivará el parseo.)
		int deltaDesc = numero_de_linea - lineaDescription;
		if (deltaDesc > VENTANA_DESDE_DESCRIPTION && !seccionEntidadTickeadaActiva
				&& !seccionEntidadBloqueTickeadaActiva) {
			return;
		}

		// 2) Activar secciones modernas (aparecen lejos de "Description" en muchos
		// crash reports)
		// Ejemplo del log: "-- Entity being ticked --"
		if (lower.contains("-- entity being ticked --")) {
			seccionEntidadTickeadaActiva = true;
			lineaInicioSeccionEntidad = numero_de_linea;
			// No retornamos: el contenido relevante empieza a continuación
		}

		// Algunas versiones/reportes usan variaciones; esta cubre casos típicos de
		// block entity
		if (lower.contains("-- block entity being ticked --") || lower.contains("-- blockentity being ticked --")) {
			seccionEntidadBloqueTickeadaActiva = true;
			lineaInicioSeccionBloque = numero_de_linea;
		}

		// 3) Desactivar secciones cuando se sale de la ventana
		if (seccionEntidadTickeadaActiva) {
			int d = numero_de_linea - lineaInicioSeccionEntidad;
			if (d > VENTANA_SECCION_DETALLES) {
				seccionEntidadTickeadaActiva = false;
			}
		}
		if (seccionEntidadBloqueTickeadaActiva) {
			int d = numero_de_linea - lineaInicioSeccionBloque;
			if (d > VENTANA_SECCION_DETALLES) {
				seccionEntidadBloqueTickeadaActiva = false;
			}
		}

		// 4) Parseo "clásico" (cerca de la description) para ticking block entity (y
		// algunos entity)
		// - Name:
		// - Block:
		// - Block location: World: (x,y,z)
		// Lo ejecutamos si estamos relativamente cerca de la description o si estamos
		// dentro de la sección de bloque.
		boolean dentroVentanaCercaDescription = deltaDesc >= 0 && deltaDesc <= VENTANA_DESDE_DESCRIPTION;
		if (dentroVentanaCercaDescription || seccionEntidadBloqueTickeadaActiva) {

			// Nombre de entidad de bloque (formato clásico)
			if (l.startsWith("Name: ")) {
				this.nombreEntidad = l.substring("Name: ".length()).trim();
				actualizarMensajeSiCorresponde();
			}

			// Tipo de bloque: usar substring y NO split(":"), porque rompe
			// "minecraft:chest"
			if (l.startsWith("Block: ")) {
				this.tipoEntidad = l.substring("Block: ".length()).trim();
				actualizarMensajeSiCorresponde();
			}

			// Variante común en algunos reportes
			// "Block entity being ticked: <tipo>@..."
			if (lower.contains("block entity being ticked")) {
				String extraido = extraerDespuesDeDosPuntos(l);
				if (!extraido.isEmpty()) {
					// muchas veces viene "modid:bloque@..." -> nos quedamos con lo de antes del @
					int arroba = extraido.indexOf('@');
					this.tipoEntidad = (arroba > 0 ? extraido.substring(0, arroba).trim() : extraido.trim());
					actualizarMensajeSiCorresponde();
				}
			}

			// Coordenadas block entity (formato clásico)
			if (lower.startsWith("block location: world: (")) {
				if (parsearWorldXYZPrimeraTupla(l)) {
					actualizarMensajeSiCorresponde();
				}
			}
		}

		// 5) Parseo moderno "Ticking entity" (esto es lo que tu log tiene)
		// En el log de ejemplo:
		// - "Entity Type: minecraft:drowned (....)"
		// - "Entity Name: Drowned"
		// - "Entity's Block location: World: (157,50,-642), ..."
		if (seccionEntidadTickeadaActiva || dentroVentanaCercaDescription) {

			if (lower.startsWith("entity type:")) {
				// Nos quedamos con "minecraft:drowned" si existe (antes del primer espacio o
				// antes de "(")
				String val = l.substring("Entity Type:".length()).trim();
				int paren = val.indexOf('(');
				if (paren > 0)
					val = val.substring(0, paren).trim();
				int espacio = val.indexOf(' ');
				if (espacio > 0)
					val = val.substring(0, espacio).trim();
				if (!val.isEmpty()) {
					this.tipoEntidad = val;
					actualizarMensajeSiCorresponde();
				}
			}

			if (lower.startsWith("entity name:")) {
				String val = l.substring("Entity Name:".length()).trim();
				if (!val.isEmpty()) {
					this.nombreEntidad = val;
					actualizarMensajeSiCorresponde();
				}
			}

			// Preferimos Block location (int) si existe
			if (lower.startsWith("entity's block location: world: (")) {
				if (parsearWorldXYZPrimeraTupla(l)) {
					actualizarMensajeSiCorresponde();
				}
			}

			// Fallback: si solo existe exact location con decimales, intentamos convertir a
			// int (floor)
			if (!coordenadasValidas && lower.startsWith("entity's exact location:")) {
				if (parsearExactLocationADesdeDecimales(l)) {
					actualizarMensajeSiCorresponde();
				}
			}
		}
	}

	private void actualizarMensajeSiCorresponde() {
		if (enlaceHtml == null || enlaceHtml.isEmpty()) {
			return;
		}
		if (!nombreEntidad.isEmpty() || !tipoEntidad.isEmpty() || coordenadasValidas) {
			this.mensaje = MonitorDePID.idioma.mensajeTickingEntidadBloque(nombreEntidad, tipoEntidad, coordenadas)
					+ " " + enlaceHtml;
			activado = true;
		}
	}

	/**
	 * Extrae lo que venga después del primer ':' de una línea. Ej: "Entity Type:
	 * minecraft:drowned (...)" -> "minecraft:drowned (...)"
	 */
	private static String extraerDespuesDeDosPuntos(String linea) {
		if (linea == null)
			return "";
		int idx = linea.indexOf(':');
		if (idx < 0 || idx + 1 >= linea.length())
			return "";
		return linea.substring(idx + 1).trim();
	}

	/**
	 * Parsea la primera tupla World: (x,y,z) que aparezca en la línea. Funciona
	 * con: - "Block location: World: (x,y,z)" - "Entity's Block location: World:
	 * (x,y,z), Section: ..."
	 */
	private boolean parsearWorldXYZPrimeraTupla(String linea) {
		if (linea == null)
			return false;

		int idxWorld = linea.indexOf("World:");
		if (idxWorld < 0)
			return false;

		int inicioParen = linea.indexOf('(', idxWorld);
		int finParen = linea.indexOf(')', inicioParen);
		if (inicioParen < 0 || finParen < 0 || finParen <= inicioParen)
			return false;

		String dentro = linea.substring(inicioParen + 1, finParen);
		String[] partes = dentro.split(",");
		if (partes.length != 3)
			return false;

		try {
			coordenadas[0] = Integer.parseInt(partes[0].trim());
			coordenadas[1] = Integer.parseInt(partes[1].trim());
			coordenadas[2] = Integer.parseInt(partes[2].trim());
			coordenadasValidas = true;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Parsea "Entity's Exact location: 157.50, 50.00, -641.50" y lo convierte a
	 * enteros (floor) como fallback.
	 */
	private boolean parsearExactLocationADesdeDecimales(String linea) {
		if (linea == null)
			return false;

		String val = extraerDespuesDeDosPuntos(linea);
		if (val.isEmpty())
			return false;

		String[] partes = val.split(",");
		if (partes.length != 3)
			return false;

		try {
			double x = Double.parseDouble(partes[0].trim());
			double y = Double.parseDouble(partes[1].trim());
			double z = Double.parseDouble(partes[2].trim());
			coordenadas[0] = (int) Math.floor(x);
			coordenadas[1] = (int) Math.floor(y);
			coordenadas[2] = (int) Math.floor(z);
			coordenadasValidas = true;
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ProblemaTickingEntidadBloque();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 750.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreProblemaTickingEntidadBloque();
	}

	@Override
	public QuickFix solucion() {
		// Nota: este quickfix originalmente es para block entity, pero aquí lo
		// reutilizamos
		// para ticking entity también (mismo concepto: borrar lo que está crasheando).
		return new Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionEliminarEntidadBloque(nombreEntidad, coordenadas))
				.construir();
	}

	@Override
	public String id() {
		return "problema_ticking_entidad_bloque";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;
		String lower = t;

		// Coincidencia genérica (entity o block entity)
		if (lower.contains("description: ticking block entity") || lower.contains("description: ticking entity")) {
			return true;
		}

		// Sección moderna (por si el stacktrace incluye el header)
		if (lower.contains("-- entity being ticked --") || lower.contains("-- block entity being ticked --")) {
			return true;
		}

		// Afinar por tipo/nombre
		if (!tipoEntidad.isEmpty() && lower.contains(tipoEntidad)) {
			return true;
		}
		if (!nombreEntidad.isEmpty() && lower.contains(nombreEntidad)) {
			return true;
		}

		// Afinar por coordenadas (variante típica en reportes)
		if (coordenadasValidas) {
			String coords1 = "world: (" + coordenadas[0] + "," + coordenadas[1] + "," + coordenadas[2] + ")";
			String coords2 = "world: (" + coordenadas[0] + ", " + coordenadas[1] + ", " + coordenadas[2] + ")";
			if (lower.contains(coords1) || lower.contains(coords2)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}