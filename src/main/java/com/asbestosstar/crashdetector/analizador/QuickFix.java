package com.asbestosstar.crashdetector.analizador;

import java.awt.Color;
import java.awt.Font;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.asbestosstar.crashdetector.Config;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;

/**
 * Representa una solución rápida con componentes GUI configurables
 */
public class QuickFix {
	// Campo para almacenar el estado del checkbox
	public boolean tieneMantener = false;

	public final String etiqueta;
	public boolean tieneCheckbox = false;
	public java.util.List<ComponenteGUI> componentes = new java.util.ArrayList<>();

	private QuickFix(Builder builder) {
		this.etiqueta = builder.etiqueta;
		this.tieneCheckbox = builder.tieneCheckbox;
		componentes = builder.componentes;
	}

	public static class Builder {
		private final String etiqueta;
		private boolean tieneCheckbox = false;
		public final java.util.List<ComponenteGUI> componentes = new java.util.ArrayList<>();

		public Builder(String etiqueta) {
			this.etiqueta = etiqueta;
		}

		/** Añade un checkbox de retención */
		public Builder conRetener() {
			this.tieneCheckbox = true;
			return this;
		}

		public Builder agregarBoton(String texto, AccionBoton accion, boolean desactivarOtros) {
			componentes.add(new BotonGUI(texto, accion, desactivarOtros));
			return this;
		}

		/** Añade un botón con comportamiento predeterminado (no desactiva otros) */
		public Builder agregarBoton(String texto, AccionBoton accion) {
			return agregarBoton(texto, accion, false);
		}

		/** Añade una etiqueta adicional */
		public Builder agregarEtiqueta(String texto) {
			componentes.add(new EtiquetaGUI(texto));
			return this;
		}

		public Builder agregarComponente(ComponenteGUI componente) {
			componentes.add(componente);
			return this;
		}

		public QuickFix construir() {
			return new QuickFix(this);
		}
	}

	// Interfaz para componentes GUI
	public interface ComponenteGUI {
		JComponent crearComponente(Supplier<Boolean> estadoRetener);

		default boolean debeDesactivarOtros() {
			return false; // Por defecto, no desactiva otros botones
		}
	}

	// Clase para botones
	static class BotonGUI implements ComponenteGUI {
		private final String texto;
		private final AccionBoton accion;
		private final boolean desactivarOtros;

		public BotonGUI(String texto, AccionBoton accion, boolean desactivarOtros) {
			this.texto = texto;
			this.accion = accion;
			this.desactivarOtros = desactivarOtros;
		}

		@Override
		public JComponent crearComponente(Supplier<Boolean> estadoRetener) {
			JButton boton = new JButton(texto);
			estilizarBoton(boton);
			boton.addActionListener(e -> {
				// Ejecutar la acción del botón
				accion.ejecutar(estadoRetener.get());
				// Mostrar siempre un diálogo que diga "listo"
				JOptionPane.showMessageDialog(null, "¡Listo!", "Información", JOptionPane.INFORMATION_MESSAGE);
			});
			return boton;
		}

		@Override
		public boolean debeDesactivarOtros() {
			return desactivarOtros;
		}

		private void estilizarBoton(JButton boton) {
			if (!CrashDetectorGUI.esMac()) {
				boton.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
				boton.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
				boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			}
		}
	}

	// Clase para etiquetas
	static class EtiquetaGUI implements ComponenteGUI {
		private final String texto;

		public EtiquetaGUI(String texto) {
			this.texto = texto;
		}

		@Override
		public JComponent crearComponente(Supplier<Boolean> estadoRetener) {
			JLabel label = new JLabel(texto);
			if (!CrashDetectorGUI.esMac()) {
				label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				label.setBackground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorBoton()));
				label.setForeground(Config.convertirAColor(Config.obtenerInstancia().obtenerColorTexto()));
				label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			}
			return label;
		}
	}

	// Selector reutilizable
	public static class SelectorGUI implements ComponenteGUI {
		private final JComboBox<String> comboBox;

		public SelectorGUI(JComboBox<String> comboBox) {
			this.comboBox = comboBox;
			if (!CrashDetectorGUI.esMac()) {
				comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
				comboBox.setBackground(Color.WHITE);
				comboBox.setForeground(Color.DARK_GRAY);
				comboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
			}
		}

		@Override
		public JComponent crearComponente(Supplier<Boolean> estadoRetener) {
			return comboBox;
		}
	}

	// Interfaz para acciones de botón
	@FunctionalInterface
	public interface AccionBoton {
		void ejecutar(boolean retenerSeleccionado);
	}
}
