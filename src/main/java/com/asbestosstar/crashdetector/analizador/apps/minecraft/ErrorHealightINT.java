package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.analizador.rapido.VerificacionRapida;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Verificación que detecta el error crítico causado por el mod 'healight' en
 * Minecraft 1.20.1 MCForge 47.10, donde se produce un
 * 'java.lang.NoSuchFieldError: INT'.
 * 
 * Este error ocurre porque el mod 'healight' hace referencia a un campo
 * estático 'INT' que ya no existe en la clase 'AttributeModifier.Operation' en
 * versiones recientes de Minecraft (1.20+), lo que provoca un fallo al
 * inicializar entidades.
 */
public class ErrorHealightINT implements VerificacionRapida {

	private boolean activado = false;
	private String mensaje = "";
	private boolean encontradoHealight = false;

	private static final String HEALIGHT = "healight";
	private static final String NO_SUCH_FIELD_INT = "java.lang.NoSuchFieldError: INT";
	private static final String LIVING_ENTITY_CLINIT = "LivingEntity.<clinit>";
	private static final String ENTITY_TYPE_CLINIT = "EntityType.<clinit>";
	private static final String WORLD_ENTITY = "net.minecraft.world.entity";

	@Override
	public String[] patronesRapidos() {
		return new String[] { HEALIGHT, NO_SUCH_FIELD_INT, LIVING_ENTITY_CLINIT, ENTITY_TYPE_CLINIT, WORLD_ENTITY };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		if (evento.linea.contains(HEALIGHT)) {
			encontradoHealight = true;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	/**
	 * Analiza el contenido del log en busca del patrón de error específico
	 * relacionado con 'healight' y el campo faltante 'INT'.
	 */
	@Override
	public void verificar(Consola consola) {
	}

	@Override
	public boolean quiereAnalizarLineas() {
		return encontradoHealight && !activado;
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (!encontradoHealight || activado || linea == null) {
			return;
		}

		// Patrón que detecta la cadena de errores típica:
		// Caused by: java.lang.NoSuchFieldError: INT
		// y que aparezca en contexto de inicialización de LivingEntity o EntityType
		if (lineaContieneErrorHealightINT(linea)) {

			// Verificar si el mod 'healight' está presente en la lista de mods cargados
			this.mensaje = MonitorDePID.idioma.errorHealightINT();
			this.activado = true;
		}
	}

	private boolean lineaContieneErrorHealightINT(String linea) {
		return linea.contains(NO_SUCH_FIELD_INT) && (linea.contains(LIVING_ENTITY_CLINIT)
				|| linea.contains(ENTITY_TYPE_CLINIT) || linea.contains(WORLD_ENTITY));
	}

	/**
	 * Devuelve una nueva instancia de esta verificación.
	 */
	@Override
	public Verificaciones nueva() {
		return new ErrorHealightINT();
	}

	/**
	 * Indica si se detectó el error durante la verificación.
	 */
	@Override
	public boolean activado() {
		return this.activado;
	}

	/**
	 * Devuelve el mensaje de error formateado para mostrar al usuario.
	 */
	public String obtenerMensaje() {
		return this.mensaje;
	}

	/**
	 * Prioridad alta, ya que impide el inicio del juego.
	 */
	@Override
	public float prioridad() {
		return 900.0f;
	}

	/**
	 * Devuelve una solución rápida (QuickFix) sugerida.
	 */
	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.solucionHealightINT()).construir();
	}

	/**
	 * Identificador único de este tipo de error.
	 */
	@Override
	public String id() {
		return "error_healight_int";
	}

	/**
	 * Nombre legible del error para mostrar en la interfaz.
	 */
	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorHealightINT();
	}

	/**
	 * Este error no requiere análisis de trazas específicas (stack trace), ya que
	 * se detecta por contenido del log general.
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (trazo == null || trazo.trace == null) {
			return false;
		}

		return lineaContieneErrorHealightINT(trazo.trace);
	}

	@Override
	public String mensaje() {
		// TODO Auto-generated method stub
		return mensaje;
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}