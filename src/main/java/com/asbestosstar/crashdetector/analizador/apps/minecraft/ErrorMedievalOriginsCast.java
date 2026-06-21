package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
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

	private static final String MEDIEVAL_ORIGINS = "medievalorigins";
	private static final String ITEMSTACK_CAST = "class net.minecraft.world.item.ItemStack cannot be cast to class";
	private static final String ENTITY_LINKED_ITEMSTACK = "io.github.apace100.apoli.access.EntityLinkedItemStack";

	@Override
	public String[] patronesRapidos() {
		return new String[] { MEDIEVAL_ORIGINS, ITEMSTACK_CAST, ENTITY_LINKED_ITEMSTACK };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
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

		// Buscamos la línea que contiene el error de casteo de ItemStack a
		// EntityLinkedItemStack
		if (linea.contains(ITEMSTACK_CAST) && linea.contains(ENTITY_LINKED_ITEMSTACK)) {

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
	public String[] ocupaTrazo() {
		return new String[] { ITEMSTACK_CAST, ENTITY_LINKED_ITEMSTACK, MEDIEVAL_ORIGINS };
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}
}