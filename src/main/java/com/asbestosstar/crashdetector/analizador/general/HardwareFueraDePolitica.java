package com.asbestosstar.crashdetector.analizador.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.asbestosstar.crashdetector.Consola;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.analizador.QuickFix;
import com.asbestosstar.crashdetector.analizador.Verificaciones;
import com.asbestosstar.crashdetector.analizador.rapido.EventoDeCoincidencia;
import com.asbestosstar.crashdetector.bajo.hw.politica.CatalogoPlataformas;
import com.asbestosstar.crashdetector.bajo.hw.politica.DetectorHardwareLigero;
import com.asbestosstar.crashdetector.bajo.hw.politica.DetectorHardwareLigero.InfoLocal;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Entrada;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Estado;
import com.asbestosstar.crashdetector.bajo.hw.politica.PoliticaHardwareConfig;
import com.asbestosstar.crashdetector.config.ConfigBoolean;
import com.asbestosstar.crashdetector.gui.tipos.docs.Documento;

/**
 * Advierte únicamente cuando una regla corporativa explícita se incumple.
 *
 * RECOMENDADO y NEUTRAL son etiquetas informativas y nunca generan avisos. Si
 * todas las plataformas están en SIN_REGLA y los mínimos valen cero, la clase
 * termina sin detectar nada.
 */
public class HardwareFueraDePolitica implements Verificaciones {

	public static final ConfigBoolean config = ConfigBoolean.de("ignorar_politica_hardware_corporativa", false);
	public static boolean hayProblema = false;

	private boolean activado;
	private boolean completa;
	private String mensaje = "";

	@Override
	public String[] patronesRapidos() {
		verificar();
		return new String[0];
	}

	private void verificar() {
		if (completa || config.obtener()) {
			return;
		}
		completa = true;

		if (!PoliticaHardwareConfig.tieneAlgunaRestriccion()) {
			return;
		}

		InfoLocal info = DetectorHardwareLigero.detectar();
		List<String> problemas = new ArrayList<String>();

		Entrada sistema = ModeloPoliticaHardware.buscarMejorCoincidencia(CatalogoPlataformas.sistemasOperativos(),
				info.detalleSistemaOperativo());
		if (esDesaconsejado(sistema)) {
			problemas.add(MonitorDePID.idioma.politicaHardwareDetalleSO(valor(info.detalleSistemaOperativo())));
		}

		Entrada cpu = ModeloPoliticaHardware.buscarMejorCoincidencia(CatalogoPlataformas.procesadores(), info.cpu());
		if (esDesaconsejado(cpu)) {
			problemas.add(MonitorDePID.idioma.politicaHardwareDetalleCPU(valor(info.cpu())));
		}

		Entrada arquitectura = ModeloPoliticaHardware.buscarMejorCoincidencia(CatalogoPlataformas.arquitecturas(),
				info.arquitectura());
		if (esDesaconsejado(arquitectura)) {
			problemas.add(MonitorDePID.idioma.politicaHardwareDetalleArquitectura(valor(info.arquitectura())));
		}

		double ramMinima = PoliticaHardwareConfig.ramMinimaGB();
		if (ramMinima > 0.0 && info.ramGB() > 0.0 && info.ramGB() + 0.01 < ramMinima) {
			problemas
					.add(MonitorDePID.idioma.politicaHardwareDetalleRAM(formatear(info.ramGB()), formatear(ramMinima)));
		}

		double ghzMinimos = PoliticaHardwareConfig.ghzMinimos();
		if (ghzMinimos > 0.0 && info.ghz() > 0.0 && info.ghz() + 0.001 < ghzMinimos) {
			problemas.add(MonitorDePID.idioma.politicaHardwareDetalleGHz(formatear(info.ghz()), formatear(ghzMinimos)));
		}

		int hilosMinimos = PoliticaHardwareConfig.hilosMinimos();
		if (hilosMinimos > 0 && info.hilos() < hilosMinimos) {
			problemas.add(MonitorDePID.idioma.politicaHardwareDetalleHilos(Integer.toString(info.hilos()),
					Integer.toString(hilosMinimos)));
		}

		if (problemas.isEmpty()) {
			return;
		}

		StringBuilder detalles = new StringBuilder();
		for (String problema : problemas) {
			if (detalles.length() > 0) {
				detalles.append("<br>");
			}
			detalles.append("&#8226; ").append(problema);
		}

		mensaje = "<b>" + MonitorDePID.idioma.politicaHardwareAdvertenciaTitulo() + "</b><br><br>"
				+ MonitorDePID.idioma.politicaHardwareAdvertenciaDetalle(detalles.toString()) + "<br><br>"
				+ MonitorDePID.idioma.politicaHardwareContactoAdministrador();
		activado = true;
		hayProblema = true;
	}

	private boolean esDesaconsejado(Entrada entrada) {
		return entrada != null && PoliticaHardwareConfig.obtenerEstado(entrada) == Estado.DESACONSEJADO;
	}

	private String valor(String texto) {
		return texto == null || texto.trim().isEmpty() ? MonitorDePID.idioma.politicaHardwareNoDetectado()
				: texto.trim();
	}

	private String formatear(double valor) {
		return String.format(Locale.ROOT, "%.2f", valor).replaceAll("0+$", "").replaceAll("\\.$", "");
	}

	@Override
	public void verificarCoincidencia(EventoDeCoincidencia evento) {
		// La verificación es local y se ejecuta una sola vez.
	}

	@Override
	public void verificarPorLinea(Consola consola, String linea, int numeroDeLinea) {
		// No depende del contenido del registro.
	}

	@Override
	public Verificaciones nueva() {
		return new HardwareFueraDePolitica();
	}

	@Override
	public boolean activado() {
		return activado;
	}

	@Override
	public float prioridad() {
		return -1470.0f;
	}

	@Override
	public String mensaje() {
		return mensaje;
	}

	@Override
	public String nombre() {
		return MonitorDePID.idioma.politicaHardwareNombreVerificacion();
	}

	@Override
	public QuickFix solucion() {
		return new QuickFix.Builder(nombre())
				.agregarEtiqueta(MonitorDePID.idioma.politicaHardwareContactoAdministrador()).construir();
	}

	@Override
	public String[] ocupaTrazo() {
		return new String[0];
	}

	@Override
	public String id() {
		return "hardware_fuera_de_politica";
	}

	@Override
	public Documento docs() {
		return Documento.NINGUN;
	}

	@Override
	public boolean recomendadoParaCorperata() {
		return true;
	}
}
