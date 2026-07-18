package com.asbestosstar.crashdetector.escanernube;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Contrato común para los proveedores de análisis remoto.
 */
public interface ClienteEscaneoNube {

	ResultadoEscaneoNube escanear(File archivo, AtomicBoolean cancelado) throws Exception;
}
