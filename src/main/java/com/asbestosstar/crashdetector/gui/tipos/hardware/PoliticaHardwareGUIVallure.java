package com.asbestosstar.crashdetector.gui.tipos.hardware;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.bajo.hw.politica.CatalogoPlataformas;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Entrada;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.Estado;
import com.asbestosstar.crashdetector.bajo.hw.politica.ModeloPoliticaHardware.TipoEntrada;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Editor corporativo de plataformas permitidas/desaconsejadas.
 *
 * La lista incorporada solo aporta sugerencias visuales. La política efectiva
 * comienza vacía y solo se guarda cuando el administrador pulsa Guardar.
 */
public class PoliticaHardwareGUIVallure extends PoliticaHardwareGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "vallure_hardware_policy";

	private final TemaVallure.Paleta paleta = TemaVallure.paleta();

	private final ConfigColor colorFondo = ConfigColor.de("tema.hardware.vallure.fondo", paleta.fondo);
	private final ConfigColor colorPanel = ConfigColor.de("tema.hardware.vallure.panel", paleta.panel);
	private final ConfigColor colorTexto = ConfigColor.de("tema.hardware.vallure.texto", paleta.texto);
	private final ConfigColor colorBoton = ConfigColor.de("tema.hardware.vallure.boton", paleta.boton);
	private final ConfigColor colorRecomendado = ConfigColor.de("tema.hardware.vallure.recomendado",
			paleta.recomendado);
	private final ConfigColor colorNeutral = ConfigColor.de("tema.hardware.vallure.neutral", paleta.neutral);
	private final ConfigColor colorDesaconsejado = ConfigColor.de("tema.hardware.vallure.desaconsejado",
			paleta.desaconsejado);
	private final ConfigColor colorBorde = ConfigColor.de("tema.hardware.vallure.borde", paleta.borde);
	private final ConfigColor colorSeleccion = ConfigColor.de("tema.hardware.vallure.seleccion", paleta.seleccion);
	private final ConfigColor colorTextoSeleccion = ConfigColor.de("tema.hardware.vallure.texto_seleccion",
			paleta.textoSeleccion);

	private final Map<TipoEntrada, TablaPolitica> tablas = new EnumMap<TipoEntrada, TablaPolitica>(TipoEntrada.class);

	private JPanel panelRaiz;
	private JPanel panelCabecera;
	private JPanel panelCentro;
	private JPanel panelInferior;
	private JLabel etiquetaLogo;
	private JLabel etiquetaTitulo;
	private JLabel etiquetaDescripcion;
	private JLabel etiquetaDetectado;
	private JTabbedPane pestanas;
	private JSpinner spinnerRam;
	private JSpinner spinnerGhz;
	private JSpinner spinnerHilos;
	private JButton botonAplicarSugerencias;
	private JButton botonLimpiar;
	private JButton botonGuardar;
	private JButton botonCancelar;

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.politicaHardwareTitulo());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(false);
		setResizable(true);
		setMinimumSize(new Dimension(900, 650));
		setPreferredSize(new Dimension(1120, 760));

		cargarEstadosTemporales();
		construirInterfaz();
		recargarApariencia();

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void construirInterfaz() {
		panelRaiz = new JPanel(new BorderLayout(10, 10));
		panelRaiz.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		panelCabecera = new JPanel(new BorderLayout(14, 8));
		etiquetaLogo = new JLabel();
		etiquetaLogo.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon icono = TemaVallure.iconoEscalado(220, 150);
		if (icono != null) {
			etiquetaLogo.setIcon(icono);
		} else {
			etiquetaLogo.setText(MonitorDePID.idioma.imagenNoEncontrada());
		}

		JPanel panelTextoCabecera = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		etiquetaTitulo = new JLabel(MonitorDePID.idioma.politicaHardwareTitulo());
		Font fuenteTitulo = fuenteBase().deriveFont(Font.BOLD, fuenteBase().getSize2D() + 5.0f);
		etiquetaTitulo.setFont(fuenteTitulo);
		panelTextoCabecera.add(etiquetaTitulo, gbc);

		gbc.gridy++;
		gbc.insets = new Insets(6, 0, 0, 0);
		etiquetaDescripcion = new JLabel(html(MonitorDePID.idioma.politicaHardwareDescripcion(), 720));
		panelTextoCabecera.add(etiquetaDescripcion, gbc);

		gbc.gridy++;
		etiquetaDetectado = new JLabel(html(construirTextoDetectado(), 720));
		panelTextoCabecera.add(etiquetaDetectado, gbc);

		panelCabecera.add(etiquetaLogo, BorderLayout.WEST);
		panelCabecera.add(panelTextoCabecera, BorderLayout.CENTER);

		panelCentro = new JPanel(new BorderLayout(8, 8));
		pestanas = new JTabbedPane();
		agregarPestana(TipoEntrada.SISTEMA_OPERATIVO, MonitorDePID.idioma.politicaHardwareSistemasOperativos());
		agregarPestana(TipoEntrada.CPU, MonitorDePID.idioma.politicaHardwareProcesadores());
		agregarPestana(TipoEntrada.ARQUITECTURA, MonitorDePID.idioma.politicaHardwareArquitecturas());
		panelCentro.add(pestanas, BorderLayout.CENTER);

		panelInferior = new JPanel(new BorderLayout(8, 8));
		panelInferior.add(crearPanelMinimos(), BorderLayout.CENTER);
		panelInferior.add(crearPanelBotones(), BorderLayout.SOUTH);

		panelRaiz.add(panelCabecera, BorderLayout.NORTH);
		panelRaiz.add(panelCentro, BorderLayout.CENTER);
		panelRaiz.add(panelInferior, BorderLayout.SOUTH);
		setContentPane(panelRaiz);
	}

	private void agregarPestana(TipoEntrada tipo, String titulo) {
		TablaPolitica tabla = new TablaPolitica(tipo, CatalogoPlataformas.porTipo(tipo));
		tablas.put(tipo, tabla);
		pestanas.addTab(titulo, tabla.panel);
	}

	private JPanel crearPanelMinimos() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createTitledBorder(MonitorDePID.idioma.politicaHardwareRequisitosMinimos()));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(4, 7, 4, 7);
		gbc.anchor = GridBagConstraints.WEST;

		spinnerRam = new JSpinner(new SpinnerNumberModel(ramMinimaConfigurada(), 0.0, 4096.0, 0.5));
		spinnerGhz = new JSpinner(new SpinnerNumberModel(ghzMinimosConfigurados(), 0.0, 20.0, 0.1));
		spinnerHilos = new JSpinner(new SpinnerNumberModel(hilosMinimosConfigurados(), 0, 4096, 1));

		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(new JLabel(MonitorDePID.idioma.politicaHardwareRamMinima()), gbc);
		gbc.gridx = 1;
		panel.add(spinnerRam, gbc);
		gbc.gridx = 2;
		panel.add(new JLabel(MonitorDePID.idioma.politicaHardwareGhzMinimos()), gbc);
		gbc.gridx = 3;
		panel.add(spinnerGhz, gbc);
		gbc.gridx = 4;
		panel.add(new JLabel(MonitorDePID.idioma.politicaHardwareHilosMinimos()), gbc);
		gbc.gridx = 5;
		panel.add(spinnerHilos, gbc);
		gbc.gridx = 6;
		gbc.weightx = 1.0;
		panel.add(new JLabel(MonitorDePID.idioma.politicaHardwareCeroDesactiva()), gbc);
		return panel;
	}

	private JPanel crearPanelBotones() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
		botonAplicarSugerencias = new JButton(MonitorDePID.idioma.politicaHardwareAplicarSugerencias());
		botonLimpiar = new JButton(MonitorDePID.idioma.politicaHardwareLimpiarPolitica());
		botonGuardar = new JButton(MonitorDePID.idioma.guardarCambios());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		botonAplicarSugerencias.addActionListener(e -> aplicarSugerencias());
		botonLimpiar.addActionListener(e -> limpiarPolitica());
		botonGuardar.addActionListener(e -> guardar());
		botonCancelar.addActionListener(e -> dispose());

		panel.add(botonAplicarSugerencias);
		panel.add(botonLimpiar);
		panel.add(botonGuardar);
		panel.add(botonCancelar);
		return panel;
	}

	private void aplicarSugerencias() {
		int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.politicaHardwareConfirmarSugerencias(),
				MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}
		aplicarSugerenciasAlModelo();
		actualizarTablas();
	}

	private void limpiarPolitica() {
		int respuesta = JOptionPane.showConfirmDialog(this, MonitorDePID.idioma.politicaHardwareConfirmarLimpiar(),
				MonitorDePID.idioma.confirmacion(), JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}
		limpiarModelo();
		spinnerRam.setValue(Double.valueOf(0.0));
		spinnerGhz.setValue(Double.valueOf(0.0));
		spinnerHilos.setValue(Integer.valueOf(0));
		actualizarTablas();
	}

	private void guardar() {
		guardarModelo(((Number) spinnerRam.getValue()).doubleValue(), ((Number) spinnerGhz.getValue()).doubleValue(),
				((Number) spinnerHilos.getValue()).intValue());
		JOptionPane.showMessageDialog(this, MonitorDePID.idioma.politicaHardwareGuardado(),
				MonitorDePID.idioma.informacion(), JOptionPane.INFORMATION_MESSAGE);
	}

	private void actualizarTablas() {
		for (TablaPolitica tabla : tablas.values()) {
			tabla.modelo.fireTableDataChanged();
			tabla.aplicarFiltro();
		}
	}

	private Color colorPara(Entrada entrada) {
		Estado estado = estadoTemporal(entrada);
		Estado visible = estado == Estado.SIN_REGLA ? entrada.sugerencia() : estado;
		switch (visible) {
		case RECOMENDADO:
			return colorRecomendado.obtener();
		case DESACONSEJADO:
			return colorDesaconsejado.obtener();
		case NEUTRAL:
		case SIN_REGLA:
		default:
			return colorNeutral.obtener();
		}
	}

	private String textoEstado(Estado estado) {
		if (estado == null) {
			return MonitorDePID.idioma.politicaHardwareSinRegla();
		}
		switch (estado) {
		case RECOMENDADO:
			return MonitorDePID.idioma.politicaHardwareRecomendado();
		case NEUTRAL:
			return MonitorDePID.idioma.politicaHardwareNeutral();
		case DESACONSEJADO:
			return MonitorDePID.idioma.politicaHardwareDesaconsejado();
		case SIN_REGLA:
		default:
			return MonitorDePID.idioma.politicaHardwareSinRegla();
		}
	}

	@Override
	public void recargarApariencia() {
		if (panelRaiz == null) {
			return;
		}
		aplicarFondo(panelRaiz);
		aplicarFondo(panelCabecera);
		aplicarFondo(panelCentro);
		aplicarFondo(panelInferior);

		etiquetaTitulo.setForeground(colorTexto.obtener());
		etiquetaDescripcion.setForeground(colorTexto.obtener());
		etiquetaDetectado.setForeground(colorTexto.obtener());
		etiquetaLogo.setForeground(colorTexto.obtener());

		for (TablaPolitica tabla : tablas.values()) {
			tabla.aplicarApariencia();
		}

		estilizarBoton(botonAplicarSugerencias);
		estilizarBoton(botonLimpiar);
		estilizarBoton(botonGuardar);
		estilizarBoton(botonCancelar);

		revalidate();
		repaint();
	}

	private void aplicarFondo(Component componente) {
		if (componente != null) {
			componente.setBackground(colorFondo.obtener());
			if (componente instanceof JPanel) {
				JPanel panel = (JPanel) componente;
				for (Component hijo : panel.getComponents()) {
					if (hijo instanceof JPanel) {
						aplicarFondo(hijo);
					} else if (hijo instanceof JLabel) {
						hijo.setForeground(colorTexto.obtener());
					}
				}
			}
		}
	}

	private void estilizarBoton(JButton boton) {
		if (boton == null) {
			return;
		}
		boton.setBackground(colorBoton.obtener());
		boton.setForeground(colorTextoSeleccion.obtener());
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
	}

	private Font fuenteBase() {
		Font fuente = UIManager.getFont("Label.font");
		return fuente == null ? getFont() : fuente;
	}

	private static String html(String texto, int ancho) {
		return "<html><div style='width:" + ancho + "px'>" + texto + "</div></html>";
	}

	@Override
	public String id() {
		return ID;
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<ElementoConfig>();

		colorFondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorFondo());
		colorPanel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorPanel());
		colorTexto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorTexto());
		colorBoton.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorBoton());
		colorRecomendado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorRecomendado());
		colorNeutral.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorNeutral());
		colorDesaconsejado.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorDesaconsejado());
		colorBorde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorBorde());
		colorSeleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorSeleccion());
		colorTextoSeleccion
				.establecerNombreParaMostrar(() -> MonitorDePID.idioma.politicaHardwareColorTextoSeleccion());

		elementos.add(colorFondo);
		elementos.add(colorPanel);
		elementos.add(colorTexto);
		elementos.add(colorBoton);
		elementos.add(colorRecomendado);
		elementos.add(colorNeutral);
		elementos.add(colorDesaconsejado);
		elementos.add(colorBorde);
		elementos.add(colorSeleccion);
		elementos.add(colorTextoSeleccion);
		return elementos;
	}

	private final class TablaPolitica {
		final JPanel panel;
		final JTextField campoBuscar;
		final JCheckBox soloReglas;
		final ModeloTablaPolitica modelo;
		final JTable tabla;
		final TableRowSorter<ModeloTablaPolitica> sorter;

		TablaPolitica(TipoEntrada tipo, List<Entrada> entradas) {
			panel = new JPanel(new BorderLayout(6, 6));
			JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
			barra.add(new JLabel(MonitorDePID.idioma.politicaHardwareBuscar()));
			campoBuscar = new JTextField(28);
			soloReglas = new JCheckBox(MonitorDePID.idioma.politicaHardwareMostrarReglas());
			barra.add(campoBuscar);
			barra.add(soloReglas);
			barra.add(new JLabel(MonitorDePID.idioma.politicaHardwareLeyenda()));
			panel.add(barra, BorderLayout.NORTH);

			modelo = new ModeloTablaPolitica(entradas);
			tabla = new JTable(modelo);
			tabla.setFillsViewportHeight(true);
			tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tabla.setRowHeight(Math.max(24, tabla.getRowHeight()));
			tabla.setAutoCreateRowSorter(false);

			sorter = new TableRowSorter<ModeloTablaPolitica>(modelo);
			tabla.setRowSorter(sorter);

			TableCellRenderer renderer = new RenderizadorFila();
			for (int i = 0; i < modelo.getColumnCount(); i++) {
				tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			tabla.getColumnModel().getColumn(3).setCellEditor(new EditorEstado());
			tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
			tabla.getColumnModel().getColumn(1).setPreferredWidth(360);
			tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
			tabla.getColumnModel().getColumn(3).setPreferredWidth(180);

			campoBuscar.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
					aplicarFiltro();
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					aplicarFiltro();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					aplicarFiltro();
				}
			});
			soloReglas.addActionListener(e -> aplicarFiltro());

			panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
		}

		void aplicarFiltro() {
			final String buscar = ModeloPoliticaHardware.normalizar(campoBuscar.getText());
			final boolean solo = soloReglas.isSelected();
			sorter.setRowFilter(new RowFilter<ModeloTablaPolitica, Integer>() {
				@Override
				public boolean include(Entry<? extends ModeloTablaPolitica, ? extends Integer> entry) {
					Entrada plataforma = modelo.entrada(entry.getIdentifier().intValue());
					if (solo && estadoTemporal(plataforma) == Estado.SIN_REGLA) {
						return false;
					}
					if (buscar.isEmpty()) {
						return true;
					}
					String texto = ModeloPoliticaHardware
							.normalizar(plataforma.familia() + " " + plataforma.nombre() + " " + plataforma.id());
					return texto.contains(buscar);
				}
			});
		}

		void aplicarApariencia() {
			panel.setBackground(colorFondo.obtener());
			tabla.setBackground(colorPanel.obtener());
			tabla.setForeground(colorTexto.obtener());
			tabla.setGridColor(colorBorde.obtener());
			tabla.setSelectionBackground(colorSeleccion.obtener());
			tabla.setSelectionForeground(colorTextoSeleccion.obtener());
			campoBuscar.setBackground(colorPanel.obtener());
			campoBuscar.setForeground(colorTexto.obtener());
			campoBuscar.setCaretColor(colorTexto.obtener());
			campoBuscar.setBorder(BorderFactory.createLineBorder(colorBorde.obtener()));
			soloReglas.setBackground(colorFondo.obtener());
			soloReglas.setForeground(colorTexto.obtener());
			modelo.fireTableDataChanged();
		}
	}

	private final class ModeloTablaPolitica extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final List<Entrada> entradas;

		ModeloTablaPolitica(List<Entrada> entradas) {
			this.entradas = entradas;
		}

		Entrada entrada(int fila) {
			return entradas.get(fila);
		}

		@Override
		public int getRowCount() {
			return entradas.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int columna) {
			switch (columna) {
			case 0:
				return MonitorDePID.idioma.politicaHardwareFamilia();
			case 1:
				return MonitorDePID.idioma.politicaHardwarePlataforma();
			case 2:
				return MonitorDePID.idioma.politicaHardwareSugerencia();
			default:
				return MonitorDePID.idioma.politicaHardwarePoliticaCorporativa();
			}
		}

		@Override
		public Class<?> getColumnClass(int columna) {
			return columna == 3 ? Estado.class : String.class;
		}

		@Override
		public boolean isCellEditable(int fila, int columna) {
			return columna == 3;
		}

		@Override
		public Object getValueAt(int fila, int columna) {
			Entrada entrada = entradas.get(fila);
			switch (columna) {
			case 0:
				return entrada.familia();
			case 1:
				return entrada.nombre();
			case 2:
				return textoEstado(entrada.sugerencia());
			default:
				return estadoTemporal(entrada);
			}
		}

		@Override
		public void setValueAt(Object valor, int fila, int columna) {
			if (columna != 3 || !(valor instanceof Estado)) {
				return;
			}
			Entrada entrada = entradas.get(fila);
			estadosTemporales.put(clave(entrada), (Estado) valor);
			fireTableRowsUpdated(fila, fila);
		}
	}

	private final class RenderizadorFila extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccionado, boolean foco,
				int filaVista, int columnaVista) {
			JLabel label = (JLabel) super.getTableCellRendererComponent(tabla, valor, seleccionado, foco, filaVista,
					columnaVista);
			int filaModelo = tabla.convertRowIndexToModel(filaVista);
			Entrada entrada = ((ModeloTablaPolitica) tabla.getModel()).entrada(filaModelo);

			if (valor instanceof Estado) {
				label.setText(textoEstado((Estado) valor));
			}
			label.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
			if (seleccionado) {
				label.setBackground(colorSeleccion.obtener());
				label.setForeground(colorTextoSeleccion.obtener());
			} else {
				label.setBackground(colorPanel.obtener());
				label.setForeground(colorPara(entrada));
			}
			return label;
		}
	}

	private final class EditorEstado extends AbstractCellEditor implements TableCellEditor {
		private static final long serialVersionUID = 1L;
		private final JComboBox<Estado> combo = new JComboBox<Estado>(Estado.values());

		EditorEstado() {
			combo.setRenderer(new DefaultListCellRenderer() {
				private static final long serialVersionUID = 1L;

				@Override
				public Component getListCellRendererComponent(javax.swing.JList<?> lista, Object valor, int indice,
						boolean seleccionado, boolean foco) {
					JLabel label = (JLabel) super.getListCellRendererComponent(lista, valor, indice, seleccionado,
							foco);
					label.setText(textoEstado((Estado) valor));
					label.setBackground(seleccionado ? colorSeleccion.obtener() : colorPanel.obtener());
					label.setForeground(seleccionado ? colorTextoSeleccion.obtener() : colorTexto.obtener());
					return label;
				}
			});
		}

		@Override
		public Object getCellEditorValue() {
			return combo.getSelectedItem();
		}

		@Override
		public Component getTableCellEditorComponent(JTable tabla, Object valor, boolean seleccionado, int fila,
				int columna) {
			combo.setSelectedItem(valor);
			return combo;
		}
	}
}
