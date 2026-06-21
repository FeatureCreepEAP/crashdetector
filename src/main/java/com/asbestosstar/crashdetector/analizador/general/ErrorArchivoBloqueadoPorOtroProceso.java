package com.asbestosstar.crashdetector.analizador.general;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Detecta cuando el servidor no puede iniciar porque un archivo del mundo está
 * bloqueado por otro proceso (por ejemplo: otra instancia del servidor,
 * antivirus, explorador de archivos).
 */
public class ErrorArchivoBloqueadoPorOtroProceso implements Verificaciones {

	private static final String TEXTO_ERROR = "java.io.IOException: The process cannot access the file because another process has locked a portion of the file";

	private boolean activado = false;
	private String mensaje = "";

	@Override
	public String[] patronesRapidos() {
		return new String[] { TEXTO_ERROR };
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

		if (linea.contains(TEXTO_ERROR)) {
			String enlaceHtml = consola.agregarErrorALectador(numero_de_linea, this);
			this.mensaje = MonitorDePID.idioma.errorArchivoBloqueadoPorOtroProceso() + enlaceHtml;
			this.activado = true;
		}
	}

	/**
	 * Indica si este verificador "ocupa" un trazo concreto del stack trace.
	 * <p>
	 * Para evitar falsos positivos, solo devuelve {@code true} cuando:
	 * <ul>
	 * <li>El verificador ya se ha activado, y</li>
	 * <li>El texto del error de archivo bloqueado aparece en el trazo.</li>
	 * </ul>
	 * Es intencionadamente conservador: se prefiere un falso negativo a marcar
	 * trazos que no correspondan realmente a este problema.
	 * </p>
	 */
	@Override
	@Override
	public String[] ocupaTrazo() {
		return new String[] { TEXTO_ERROR };
	}

	@Override
	public Verificaciones nueva() {
		return new ErrorArchivoBloqueadoPorOtroProceso();
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
		return 840.0f; // Alta: impide iniciar el servidor
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.solucionErrorArchivoBloqueadoPorOtroProceso()).construir();
	}

	@Override
	public String id() {
		return "archivo_bloqueado_por_otro_proceso";
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.nombreErrorArchivoBloqueadoPorOtroProceso();
	}

	@Override
	public Documento docs() {
		// TODO Auto-generated method stub
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}

}