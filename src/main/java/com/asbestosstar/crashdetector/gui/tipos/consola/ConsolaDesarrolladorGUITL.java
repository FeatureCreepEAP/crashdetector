package com.asbestosstar.crashdetector.gui.tipos.consola;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.asbestosstar.crashdetector.ConfigMunidial;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.lfpdppp.LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos;

/**
 * Consola del desarrollador estilo TL.
 */
public class ConsolaDesarrolladorGUITL extends ConsolaDesarrolladorGUI {

	public static String ID = "consola_dev_tl";

	private JTextArea area;
	private JScrollPane scroll;

	private ConfigColor fondo = ConfigColor.de("consola.dev.fondo", java.awt.Color.BLACK);
	private ConfigColor texto = ConfigColor.de("consola.dev.texto", java.awt.Color.GREEN);

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void init() {

		setTitle(MonitorDePID.idioma.consolaDesarrollo());
		setSize(900, 600);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);

		area = new JTextArea();
		area.setEditable(false);
		area.setBackground(fondo.obtener());
		area.setForeground(texto.obtener());

		scroll = new JScrollPane(area);
		add(scroll, BorderLayout.CENTER);

		// Barra inferior
		JPanel barra = new JPanel(new BorderLayout());

		JButton bajar = new JButton("⬇ " + MonitorDePID.idioma.bajar());
		JButton logs = new JButton("📎 " + MonitorDePID.idioma.logsSoporte());
		JButton stop = new JButton("⛔ " + MonitorDePID.idioma.detenerProceso());

		barra.add(bajar, BorderLayout.WEST);
		barra.add(logs, BorderLayout.CENTER);
		barra.add(stop, BorderLayout.EAST);

		add(barra, BorderLayout.SOUTH);

		// Scroll al fondo
		bajar.addActionListener(e -> area.setCaretPosition(area.getDocument().getLength()));

		// Compartir logs
		logs.addActionListener(e -> compartirLogs());

		// Matar PID (MonitorDePID.pid es long)
		stop.addActionListener(e -> {

			long pid = MonitorDePID.pid;

			if (pid <= 0)
				return;

			try {

				String os = System.getProperty("os.name").toLowerCase();

				if (os.contains("win")) {
					// Windows
					Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
				} else {
					// Linux / macOS / Unix
					Runtime.getRuntime().exec("kill -9 " + pid);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		});

		// Menú contextual
		JPopupMenu menu = new JPopupMenu();

		menu.add(crearItem(MonitorDePID.idioma.copiarSeleccion(), () -> area.copy()));
		menu.add(crearItem(MonitorDePID.idioma.seleccionarTodo(), () -> area.selectAll()));
		menu.add(crearItem(MonitorDePID.idioma.copiarTodo(), () -> {
			area.selectAll();
			area.copy();
		}));

		menu.add(crearItem(MonitorDePID.idioma.guardarTodoComoArchivo(), this::guardarComoArchivo));
		menu.add(crearItem(MonitorDePID.idioma.obtenerEnlaceSoporte(), this::compartirLogs));
		menu.add(crearItem(MonitorDePID.idioma.borrarTodo(), () -> area.setText("")));

		area.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					menu.show(area, e.getX(), e.getY());
				}
			}
		});

		setVisible(true);
	}

	private JMenuItem crearItem(String nombre, Runnable accion) {
		JMenuItem it = new JMenuItem(nombre);
		it.addActionListener(e -> accion.run());
		return it;
	}

	@Override
	public void agregarLinea(String linea) {
		if (area != null) {
			area.append(linea + "\n");
		}
	}

	/**
	 * Comparte logs para soporte (requiere consentimiento LFPDPPP).
	 */
	private void compartirLogs() {

		ConfigMunidial cfg = ConfigMunidial.obtenerInstancia();

		if (!cfg.obtenerConsentimientoLFPDPPP()) {

			TipoGUI.LFPDPPP
					.obtenerGUIPredeterminado(
							LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos.ID,
							() -> new LeyFederalDeProteccionDeDatosPersonalesEnPosesionDeLosParticularesGUIConLogos())
					.init();

			return;
		}

		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.consentimientoConfirmadoPendienteImplementacion(),
				MonitorDePID.idioma.logsSoporte(), JOptionPane.INFORMATION_MESSAGE);
	}

	private void guardarComoArchivo() {
		try {
			JFileChooser fc = new JFileChooser();
			if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				Files.write(f.toPath(), area.getText().getBytes("UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void recargarApariencia() {

		if (area != null) {
			area.setBackground(fondo.obtener());
			area.setForeground(texto.obtener());
		}

		if (scroll != null) {
			scroll.getViewport().setBackground(fondo.obtener());
		}

		revalidate();
		repaint();
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {

		ArrayList<ElementoConfig> ret = new ArrayList<>();

		fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());

		ret.add(fondo);
		ret.add(texto);

		return ret;
	}
}
