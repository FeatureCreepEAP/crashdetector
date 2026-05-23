package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta errores de compatibilidad con Medieval Origins donde ItemStack no
 * puede ser casteado a EntityLinkedItemStack, común en versiones superiores a
 * 6.6.0.
 */
public class ErrorMedievalOriginsCast implements Verificaciones {

	private boolean activado = false;
	private String mensaje = "";
	private String enlaceHtml = "";
	private boolean encontradoMedievalOrigins = false;

	/**
	 * Método de compatibilidad — busca si Medieval Origins está presente en el
	 * contenido completo del registro.
	 */
	@Override
	public void verificar(Consola consola) {
		// Verificamos si Medieval Origins está presente en el contenido del registro
		if (consola.contenido_verificar != null) {
			encontradoMedievalOrigins = consola.contenido_verificar.toLowerCase().contains("medievalorigins");
		}
	}

	/**
	 * Análisis por línea del registro.
	 * <p>
	 * Se busca el patrón característico del error donde ItemStack no puede ser
	 * casteado a EntityLinkedItemStack, común en versiones de Medieval Origins
	 * superiores a 6.6.0.
	 * </p>
	 */
	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {
		if (activado) {
			// Si ya se activó, no seguimos verificando más líneas.
			return;
		}

		// Buscamos la línea que contiene el error de casteo de ItemStack a
		// EntityLinkedItemStack
		if (linea.contains("class net.minecraft.world.item.ItemStack cannot be cast to class")
				&& linea.contains("io.github.apace100.apoli.access.EntityLinkedItemStack")
				&& encontradoMedievalOrigins) {

			// Enlazar a la línea del error en el lector
			enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);

			// Mensaje de error en HTML con referencia al problema de Medieval Origins
			mensaje = MonitorDePID.idioma.errorMedievalOriginsCast() + Verificaciones.nl_html;
			activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorMedievalOriginsCast();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 900.0f; // Alta prioridad: rompe la carga del juego
	}

	@Override
	public String mensaje() {
		return activado ? (mensaje + enlaceHtml) : "";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreDeErrorMedievalOriginsCast();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre()).agregarEtiqueta(MonitorDePID.idioma.pasoErrorMedievalOriginsCast())
				.construir();
	}

	@Override
	public String id() {
		return "error_medieval_origins_cast";
	}

	/**
	 * Asocia esta verificación con un trazo específico del stack.
	 * <p>
	 * Devuelve true si el trazo contiene las cadenas clave del error de
	 * compatibilidad de Medieval Origins.
	 * </p>
	 */
	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		if (!activado || trazo == null || trazo.trace == null) {
			return false;
		}

		String t = trazo.trace;

		return t.contains("class net.minecraft.world.item.ItemStack cannot be cast to class")
				&& t.contains("io.github.apace100.apoli.access.EntityLinkedItemStack")
				&& t.toLowerCase().contains("medievalorigins");
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		// TODO Auto-generated method stub
		return "https://pagure.io/CrashDetectorMC/blob/main/f/src/main/java/com/asbestosstar/crashdetector/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}

}