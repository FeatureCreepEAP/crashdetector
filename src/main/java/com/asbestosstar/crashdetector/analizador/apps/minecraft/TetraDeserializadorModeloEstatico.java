package com.asbestosstar.crashdetector.analizador.apps.minecraft;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.VerificacionDeStackTrace.TraceInfo;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

public class TetraDeserializadorModeloEstatico implements Verificaciones {

	// Indica si el log contiene indicios globales del error
	private boolean posibleErrorTetra = false;

	// Indica si ya se encontró la línea con el mensaje principal
	private boolean encontroMensajePrincipal = false;

	// Indica si ya se encontró la línea con el método implicado
	private boolean encontroMetodoRelacionado = false;

	// Indica si esta verificación fue activada
	private boolean activado = false;

	// Enlace a una línea relevante del log
	private String enlace = "";

	@Override
	public void verificar(Consola consola) {
		// Detección global ligera para evitar comprobaciones más costosas por línea
		if (consola.contenido_verificar.contains("No deserializer found for module model type: static")
				&& consola.contenido_verificar
						.contains("se.mickelus.tetra.module.model.ModuleModelRegistry$Deserializer.deserialize")) {
			posibleErrorTetra = true;
		}
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int num) {
		// Salir temprano si no existen ambos indicios globales
		if (!posibleErrorTetra || activado) {
			return;
		}

		// Buscar la línea del mensaje principal
		if (!encontroMensajePrincipal && linea.contains("No deserializer found for module model type: static")) {
			encontroMensajePrincipal = true;

			// Guardar enlace a la línea más útil para el usuario
			if (this.enlace.isEmpty()) {
				this.enlace = consola.agregarErrorALectador(num, this);
			}
		}

		// Buscar la línea del método implicado
		if (!encontroMetodoRelacionado
				&& linea.contains("se.mickelus.tetra.module.model.ModuleModelRegistry$Deserializer.deserialize")) {
			encontroMetodoRelacionado = true;

			// Si todavía no hay enlace, usar esta línea
			if (this.enlace.isEmpty()) {
				this.enlace = consola.agregarErrorALectador(num, this);
			}
		}

		// El error se considera confirmado cuando ambas cadenas aparecen en el log,
		// aunque estén en líneas distintas
		if (encontroMensajePrincipal && encontroMetodoRelacionado) {
			this.activado = true;
		}
	}

	@Override
	public Verificaciones nueva() {
		return new TetraDeserializadorModeloEstatico();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return 1400;
	}

	@Override
	public String mensaje() {
		return MonitorDePID.idioma.mensajeTetraDeserializadorModeloEstatico() + this.enlace;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreTetraDeserializadorModeloEstatico();
	}

	@Override
	public QuickFix solucion() {
		return QuickFix.NINGUN;
	}

	@Override
	public boolean ocupaTrazo(TraceInfo trazo) {
		return false;
	}

	@Override
	public String id() {
		return "tetra_deserializador_modelo_estatico";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public String enlaceACodigo() {
		return Statics.GIT
				+ "src/main/java/analizador/apps/minecraft/"
				+ this.getClass().getSimpleName() + ".java";
	}
}