// GPUFixGUI.java
package com.asbestosstar.crashdetector.gui.tipos.gpu;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class GPUFixGUI extends JFrame implements BotonDeBarraLateralDerecha {

	public static Map<String, Supplier<GPUFixGUI>> GUIS = new HashMap<>();

	public JLabel imagenOptimus;
	public JTextArea areaInfo;
	public JButton botonAplicar;
	public JButton botonAbrirFuenteTLauncher;
	public JButton botonAbrirVirusTotal;
	public JButton botonAbrirOptimusLinux;

	public boolean inicializada = false;

	@Override
	public void init() {
		if (inicializada) {
			recargarApariencia();
			setVisible(true);
			toFront();
			return;
		}

		inicializada = true;

		setTitle(MonitorDePID.idioma.gpuFixTitulo());
		setSize(900, 560);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		imagenOptimus = new JLabel("", JLabel.CENTER);

		areaInfo = new JTextArea();
		areaInfo.setEditable(false);
		areaInfo.setLineWrap(true);
		areaInfo.setWrapStyleWord(true);

		botonAplicar = new JButton();
		botonAbrirFuenteTLauncher = new JButton();
		botonAbrirVirusTotal = new JButton();
		botonAbrirOptimusLinux = new JButton();

		JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
		barra.add(botonAplicar);
		// barra.add(botonAbrirFuenteTLauncher);
		// barra.add(botonAbrirVirusTotal);
		barra.add(botonAbrirOptimusLinux);

		add(imagenOptimus, BorderLayout.WEST);
		add(areaInfo, BorderLayout.CENTER);
		add(barra, BorderLayout.SOUTH);

		botonAplicar.addActionListener(e -> aplicarFixGPU());
		botonAbrirFuenteTLauncher.addActionListener(e -> abrirUrl("https://tlauncher.org/fixgpu"));
		botonAbrirVirusTotal.addActionListener(e -> abrirUrl(
				"https://www.virustotal.com/gui/file/04594b6d8663708b7258e6647671e9bb81a9e88b6300026d370c0ffc75b2259d/behavior"));
		botonAbrirOptimusLinux.addActionListener(e -> abrirUrl("https://rpmfusion.org/Howto/Optimus"));

		recargarApariencia();
		setVisible(true);
	}

	public void aplicarFixGPU() {
		if (esWindows()) {
			aplicarFixWindows();
		} else if (esMac()) {
			aplicarFixMac();
		} else if (esLinux()) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixLinuxManual());
		} else {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixSistemaNoSoportado());
		}
	}

	public void aplicarFixWindows() {
		try {
			String javaw = obtenerJavawActual();
			if (javaw == null || javaw.trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixJavaNoDetectado());
				return;
			}

			String clave = "HKCU\\SOFTWARE\\Microsoft\\DirectX\\UserGpuPreferences";
			String valor = "GpuPreference=2;";

			new ProcessBuilder("reg", "add", clave, "/v", javaw, "/t", "REG_SZ", "/d", valor, "/f").start().waitFor();
//Obtaine esta info desde https://tlauncher.org/fixgpu y su virustotal https://www.virustotal.com/gui/file/04594b6d8663708b7258e6647671e9bb81a9e88b6300026d370c0ffc75b2259d/behavior funcion de registro
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixWindowsAplicado(javaw));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixErrorAplicando() + ": " + t.getMessage());
		}
	}

	public void aplicarFixMac() {
		try {
			// 1 = GPU dedicada, 2 = cambio automatico. Requiere Mac con GPU dedicada.
			new ProcessBuilder("sudo", "pmset", "-a", "gpuswitch", "1").start().waitFor();
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixMacAplicado());
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.gpuFixMacError() + ": " + t.getMessage());
		}
	}

	public String obtenerJavawActual() {
		String home = MonitorDePID.jvm();

		return home;
	}

	public void abrirUrl(String url) {
		try {
			Desktop.getDesktop().browse(java.net.URI.create(url));
		} catch (Throwable t) {
			CrashDetectorLogger.logException(t);
		}
	}

	public boolean esWindows() {
		return System.getProperty("os.name", "").toLowerCase().contains("windows");
	}

	public boolean esMac() {
		return System.getProperty("os.name", "").toLowerCase().contains("mac");
	}

	public boolean esLinux() {
		return System.getProperty("os.name", "").toLowerCase().contains("linux");
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.GPU_FIX;
	}
}