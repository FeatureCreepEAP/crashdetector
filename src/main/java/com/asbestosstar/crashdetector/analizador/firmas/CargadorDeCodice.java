package com.asbestosstar.crashdetector.analizador.firmas;

import java.util.List;

import com.asbestosstar.crashdetector.analizador.firmas.v0.FirmasV0;
import com.asbestosstar.crashdetector.analizador.firmas.v0.VerificacionFirmasV0;

/**
 * Enrutador de esquemas.
 *
 * Por ahora delega a V0.
 *
 * El parser V0: - acepta archivos antiguos con códigos históricos incorrectos
 * como "kp" y "jp" - normaliza esos códigos a "ko" y "ja" - no exige que
 * existan todos los idiomas - permite idiomas adicionales registrados
 * dinámicamente
 */
public class CargadorDeCodice {

	public static List<VerificacionFirmasV0> cargarVerificaciones() {
		return FirmasV0.cargar();
	}

	public static void guardarSchema0(List<VerificacionFirmasV0> verificaciones) throws java.io.IOException {
		FirmasV0.guardar(verificaciones);
	}
}