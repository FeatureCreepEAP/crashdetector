package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta un error de implementación faltante en DecoratedPotBlockEntity.
 *
 * Ejemplo: Receiver class
 * com.min01.archaeology.blockentity.DecoratedPotBlockEntity does not define or
 * inherit an implementation of the resolved method 'abstract
 * net.minecraft.core.BlockPos getBlockPos()'
 *
 * Es un error común en la versión 1.19.2 de L_Enders_Cataclysm. Solución
 * conocida: Instalar el mod PotFix.
 */
public class ErrorPotBlockEntity implements Verificaciones {

	private boolean activado = false;
	private String enlace = "";

	private static final String DECORATED_POT_BLOCK_ENTITY = "com.min01.archaeology.blockentity.DecoratedPotBlockEntity";
	private static final String DECORATED_POT_BLOCK_ENTITY_CORTO = "DecoratedPotBlockEntity";
	private static final String DOES_NOT_DEFINE_OR_INHERIT = "does not define or inherit an implementation";

	@Override
	public String[] patronesRapidos() {
		return new String[] { DECORATED_POT_BLOCK_ENTITY, DOES_NOT_DEFINE_OR_INHERIT };
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		if (evento == null || evento.linea == null) {
			return;
		}

		verificarPorLinea(evento.consola, evento.linea, evento.numeroDeLinea);
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numero_de_linea) {

		// Buscamos la línea exacta del error
		if (lineaContieneErrorPotBlockEntity(linea)) {
			this.enlace = consola.agregarErrorALectador(numero_de_linea, this);
			activado = true;
		}
	}

	private boolean lineaContieneErrorPotBlockEntity(String linea) {
		return linea.contains(DECORATED_POT_BLOCK_ENTITY_CORTO) && linea.contains(DOES_NOT_DEFINE_OR_INHERIT);
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorPotBlockEntity();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1500.0f;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeErrorPotBlockEntity() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorPotBlockEntity();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public String id() {
		return "error_pot_block_entity";
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

}