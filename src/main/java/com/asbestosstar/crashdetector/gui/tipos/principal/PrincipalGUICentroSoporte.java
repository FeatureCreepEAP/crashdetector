package com.asbestosstar.crashdetector.gui.tipos.principal;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartir;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirCentroSoporte;
import com.asbestosstar.crashdetector.gui.tipos.compartir.DialogoCompartirPrimitiva;

public class PrincipalGUICentroSoporte extends PrincipalGUIPrimitiva {

	public static String ID = "centro_soporte";

	public boolean yaEjecutado = false;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void constructir(Instant utc, CountDownLatch latch) {
		this.tiempoFallo = utc;
		this.cerrojo = latch;
		MonitorDePID.gui_principal_activo = this;
		ejecutarFlujoCentroSoporte();
	}

	@Override
	public void init() {
		this.tiempoFallo = Instant.now();
		ejecutarFlujoCentroSoporte();
	}

	public void ejecutarFlujoCentroSoporte() {
		if (yaEjecutado) {
			return;
		}

		yaEjecutado = true;

		SwingUtilities.invokeLater(() -> {
			try {
				crearPopups();
				abrirDialogoCentroSoporte();
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		});
	}

	public void crearPopups() {
		for (VerificacionesLegacy ver : obtenerPopups()) {
			if (ver == null) {
				continue;
			}

			String contenido = ver.comoString();
			if (contenido == null || contenido.trim().isEmpty()) {
				continue;
			}

			SwingUtilities.invokeLater(() -> crearPopupVerificacion(contenido));
		}
	}

	public void abrirDialogoCentroSoporte() {
		DialogoCompartirCentroSoporte dialogo = new DialogoCompartirCentroSoporte();
		dialogo.preperar(tiempoFallo != null ? tiempoFallo : Instant.now());
	}

	@Override
	public void inicializarInterfaz() {
	}

	@Override
	public void recargarApariencia() {
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		return new ArrayList<ElementoConfig>();
	}

	@Override
	public void actualizarTextoBotonLauncherParaCDLauncher() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aplicarColoresAnalizador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aplicarColoresLanzer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aplicarContenidoDeLaPantallaAnalizador() {
		// TODO Auto-generated method stub

	}

	@Override
	public void aplicarContenidoDeLaPantallaLanzer() {
		// TODO Auto-generated method stub

	}
}