package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;
import com.asbestosstar.crashdetector.analizador.Verificaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Detecta errores AbstractMethodError específicos donde una clase no implementa
 * un método de una interfaz. Extrae los nombres concretos y el origen desde el
 * trace.
 */
public class ErrorMetodoAbstractoNoImplementado implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";

	/**
	 * Enlace HTML hacia la línea del log donde se detectó el error.
	 */
	private String enlaceHtml = "";

	/**
	 * Bandera ligera que indica si el log parece contener este tipo de error. Sirve
	 * para evitar trabajo innecesario en el verificador por línea.
	 */
	private boolean posibleErrorMetodoAbstracto = false;

	/**
	 * Caché de las líneas de la consola, calculado una sola vez en la verificación
	 * global para reutilizar en el análisis por línea (cuando se necesita contexto
	 * hacia abajo).
	 */
	private String[] lineasConsola = null;

	private static final Pattern PATRON_ABSTRACT_METHOD = Pattern.compile(
			"java\\.lang\\.AbstractMethodError: Receiver class ([^ ]+) does not define or inherit an implementation of the resolved method '([^']+)' of interface ([^\\.]+\\.[^\\s]+)");

	@Override
	public void verificar(Consola consola) {
		String contenido = consola.contenido_verificar;
		this.lineasConsola = contenido != null ? contenido.split(Verificaciones.nl) : null;

		// Trabajo global mínimo: solo comprobamos si aparecen los fragmentos clave.
		// Si no aparecen, el verificador por línea se saltará directamente.
		if (contenido != null && contenido.contains("java.lang.AbstractMethodError")
				&& contenido.contains("does not define or inherit an implementation")
				&& contenido.contains("of interface")) {
			posibleErrorMetodoAbstracto = true;
		} else {
			posibleErrorMetodoAbstracto = false;
		}
	}

	@Override
	public void verificar(Consola consola, String linea, int numero_de_linea) {
		// Si ya se activó o el log no tiene pinta de contener este error, no hacemos
		// nada.
		if (activado || !posibleErrorMetodoAbstracto || linea == null) {
			return;
		}

		String recorte = linea.trim();
		if (!(recorte.contains("java.lang.AbstractMethodError")
				&& recorte.contains("does not define or inherit an implementation")
				&& recorte.contains("of interface"))) {
			return;
		}

		Matcher m = PATRON_ABSTRACT_METHOD.matcher(recorte);
		if (!m.find()) {
			return;
		}

		String claseConcreta = m.group(1); // Ej: Aru.Aru.ashvehicle.entity.vehicle.F117Entity
		String firmaMetodo = m.group(2); // Ej: boolean canShoot(net.minecraft.world.entity.LivingEntity)
		String interfaz = m.group(3); // Ej:
										// com.atsuishio.superbwarfare.entity.vehicle.base.WeaponVehicleEntity

		// Buscar origen en las líneas siguientes del stack trace (máx. 10 líneas).
		// Se apoya en el array cacheado en la verificación global.
		String origen = "";
		if (lineasConsola != null && numero_de_linea >= 0 && numero_de_linea < lineasConsola.length) {
			for (int j = numero_de_linea + 1; j < Math.min(numero_de_linea + 11, lineasConsola.length); j++) {
				String l = lineasConsola[j].trim();
				if (l.startsWith("at ")) {
					// Extraer modid, jar o paquete de la línea de stack
					String posibleOrigen = VerificacionDeStackTrace.extraerModidDeLinea(l);
					if (posibleOrigen == null || VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
						// Intentar con JAR
						java.util.List<String> jars = VerificacionDeStackTrace.extraerJarsDeLinea(l);
						if (!jars.isEmpty()) {
							posibleOrigen = jars.get(0);
						} else {
							// Último recurso: paquete
							posibleOrigen = VerificacionDeStackTrace.extraerPaqueteDeLinea(l);
						}
					}
					if (posibleOrigen != null && !posibleOrigen.isEmpty()
							&& !VerificacionDeStackTrace.esModNoPermite(posibleOrigen)) {
						origen = posibleOrigen;
						break;
					}
				} else if (!l.isEmpty() && !l.startsWith("Caused by") && !l.startsWith("...")) {
					// Ya no es parte del stack trace inmediato
					break;
				}
			}
		}

		// Registrar el enlace a la línea concreta del error
		this.enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

		// Construir mensaje detallado
		this.mensaje = MonitorDePID.idioma.errorMetodoAbstractoNoImplementadoDetallado(claseConcreta, firmaMetodo,
				interfaz, origen) + Verificaciones.nl_html + enlaceHtml;

		this.activado = true;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}
		String t = trazo.trace;
		return t.contains("java.lang.AbstractMethodError") && t.contains("does not define or inherit an implementation")
				&& t.contains("of interface");
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public boolean activado() {
		return this.activado;
	}

	@Override
	public String mensaje() {
		return this.mensaje;
	}

	@Override
	public float prioridad() {
		return 1200.0f;
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionMetodoAbstractoNoImplementado()).construir();
	}

	@Override
	public String id() {
		return "metodo_abstracto_no_implementado";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorMetodoAbstractoNoImplementado();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/general/"
				+ this.getClass().getSimpleName() + ".java";
	}

}
