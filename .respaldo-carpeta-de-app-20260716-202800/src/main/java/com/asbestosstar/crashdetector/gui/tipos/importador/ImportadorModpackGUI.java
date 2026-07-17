package com.asbestosstar.crashdetector.gui.tipos.importador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.TransferHandler;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.dto.modpack.ProveedorMods;
import com.asbestosstar.crashdetector.dto.modpack.importar.PoliticaImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResolutorConflictosImportacion;
import com.asbestosstar.crashdetector.dto.modpack.importar.ResultadoImportacion;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.gui.tipos.modapi.PanelAPIBase;

public abstract class ImportadorModpackGUI extends JFrame implements CrashDetectorGUI {

	public static final Map<String, Supplier<ImportadorModpackGUI>> GUIS = new HashMap<>();

	public JLabel imagenTema;
	public JTextArea areaDescripcion;
	public JPanel panelDrop;
	public JLabel etiquetaArchivo;
	public JComboBox<String> comboFormato;
	public JButton botonSeleccionar;
	public JButton botonImportar;
	public JLabel etiquetaEstado;

	public Path archivoSeleccionado;
	public Path carpetaDestino;
	public SwingWorker<Void, Void> workerImportar;

	@Override
	public TipoGUI<?> tipo() {
		return TipoGUI.IMPORTADOR_MODPACK;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.importadorModpackTitulo());
		setSize(820, 560);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout(8, 8));

		carpetaDestino = Paths.get(".").toAbsolutePath().normalize();

		JPanel panelIzquierdo = new JPanel(new BorderLayout(8, 8));
		panelIzquierdo.setPreferredSize(new Dimension(250, 400));
		panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		imagenTema = new JLabel("", JLabel.CENTER);

		areaDescripcion = new JTextArea();
		areaDescripcion.setEditable(false);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		areaDescripcion.setText(MonitorDePID.idioma.importadorModpackDescripcion());

		panelIzquierdo.add(imagenTema, BorderLayout.NORTH);
		panelIzquierdo.add(new JScrollPane(areaDescripcion), BorderLayout.CENTER);

		JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		JPanel filaFormato = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		comboFormato = new JComboBox<String>();
		recargarFormatosImportables();

		filaFormato.add(new JLabel(MonitorDePID.idioma.importadorModpackFormato() + ":"));
		filaFormato.add(comboFormato);

		panelDrop = new JPanel(new BorderLayout());
		panelDrop.setPreferredSize(new Dimension(420, 180));
		panelDrop.setBorder(BorderFactory.createDashedBorder(null, 5, 5));

		etiquetaArchivo = new JLabel(MonitorDePID.idioma.importadorModpackArrastraArchivo(), JLabel.CENTER);
		panelDrop.add(etiquetaArchivo, BorderLayout.CENTER);
		instalarDragDrop();

		JPanel filaBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
		botonSeleccionar = new JButton(MonitorDePID.idioma.importadorModpackBotonSeleccionar());
		botonImportar = new JButton(MonitorDePID.idioma.importadorModpackBotonImportar());
		etiquetaEstado = new JLabel(MonitorDePID.idioma.importadorModpackEstadoListo());

		botonSeleccionar.addActionListener(e -> seleccionarArchivo());
		botonImportar.addActionListener(e -> importarAsync());

		filaBotones.add(botonSeleccionar);
		filaBotones.add(botonImportar);
		filaBotones.add(etiquetaEstado);

		panelPrincipal.add(filaFormato, BorderLayout.NORTH);
		panelPrincipal.add(panelDrop, BorderLayout.CENTER);
		panelPrincipal.add(filaBotones, BorderLayout.SOUTH);

		add(panelIzquierdo, BorderLayout.WEST);
		add(panelPrincipal, BorderLayout.CENTER);

		recargarApariencia();
		setVisible(true);
	}

	public void recargarFormatosImportables() {
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<String>();

		for (Map.Entry<String, Supplier<ProveedorMods>> entrada : PanelAPIBase.PROVEEDORES_MODS.entrySet()) {
			try {
				ProveedorMods proveedor = entrada.getValue().get();

				if (proveedor != null && proveedor.soportaImportarModpack()) {
					modelo.addElement(entrada.getKey());
				}
			} catch (Throwable t) {
				CrashDetectorLogger.logException(t);
			}
		}

		comboFormato.setModel(modelo);
	}

	public void instalarDragDrop() {
		panelDrop.setTransferHandler(new TransferHandler() {
			@Override
			public boolean canImport(TransferSupport support) {
				return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
			}

			@Override
			public boolean importData(TransferSupport support) {
				try {
					if (!canImport(support)) {
						return false;
					}

					List<?> archivos = (List<?>) support.getTransferable()
							.getTransferData(DataFlavor.javaFileListFlavor);

					if (archivos == null || archivos.isEmpty()) {
						return false;
					}

					File archivo = (File) archivos.get(0);
					establecerArchivoSeleccionado(archivo.toPath());
					return true;
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					return false;
				}
			}
		});
	}

	public void seleccionarArchivo() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(MonitorDePID.idioma.importadorModpackSeleccionarArchivo());

		int resultado = chooser.showOpenDialog(this);

		if (resultado == JFileChooser.APPROVE_OPTION) {
			establecerArchivoSeleccionado(chooser.getSelectedFile().toPath());
		}
	}

	public void establecerArchivoSeleccionado(Path archivo) {
		archivoSeleccionado = archivo.toAbsolutePath().normalize();

		if (etiquetaArchivo != null) {
			etiquetaArchivo.setText(archivoSeleccionado.getFileName().toString());
		}
	}

	public ProveedorMods obtenerProveedorSeleccionado() {
		String id = (String) comboFormato.getSelectedItem();

		if (id == null) {
			return null;
		}

		Supplier<ProveedorMods> supplier = PanelAPIBase.PROVEEDORES_MODS.get(id);

		if (supplier == null) {
			return null;
		}

		ProveedorMods proveedor = supplier.get();

		if (proveedor == null || !proveedor.soportaImportarModpack()) {
			return null;
		}

		return proveedor;
	}

	public void importarAsync() {
		final ProveedorMods proveedor = obtenerProveedorSeleccionado();

		if (proveedor == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.importadorModpackFormatoNoSoportado(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (archivoSeleccionado == null) {
			JOptionPane.showMessageDialog(this, MonitorDePID.idioma.importadorModpackSinArchivo(),
					MonitorDePID.idioma.error(), JOptionPane.WARNING_MESSAGE);
			return;
		}

		setEstado(MonitorDePID.idioma.importadorModpackEstadoImportando());
		setBotonesActivos(false);

		workerImportar = new SwingWorker<Void, Void>() {
			private ResultadoImportacion resultado;

			@Override
			protected Void doInBackground() throws Exception {
				PoliticaImportacion politica = new PoliticaImportacion();

				ResolutorConflictosImportacion resolutor = (conflicto, pol) -> {
					DialogoConflictoImportacionGUI dialogo = TipoGUI.IMPORTADOR_CONFLICTO.obtenerGUIPredeterminado(
							DialogoConflictoImportacionYumeiriReyu.ID, DialogoConflictoImportacionYumeiriReyu::new);

					dialogo.establecerConflicto(conflicto);
					return dialogo.mostrarYObtenerDecision();
				};

				resultado = proveedor.importarModpack(archivoSeleccionado, carpetaDestino, politica, resolutor);
				return null;
			}

			@Override
			protected void done() {
				try {
					get();

					String mensaje = MonitorDePID.idioma.importadorModpackResultado(resultado.copiados,
							resultado.reemplazados, resultado.saltados, resultado.renombrados, resultado.errores);

					JOptionPane.showMessageDialog(ImportadorModpackGUI.this, mensaje, MonitorDePID.idioma.informacion(),
							JOptionPane.INFORMATION_MESSAGE);

					setEstado(MonitorDePID.idioma.importadorModpackEstadoListo());
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					setEstado(MonitorDePID.idioma.importadorModpackEstadoError());

					JOptionPane.showMessageDialog(ImportadorModpackGUI.this, t.getMessage(),
							MonitorDePID.idioma.error(), JOptionPane.ERROR_MESSAGE);
				} finally {
					setBotonesActivos(true);
				}
			}
		};

		workerImportar.execute();
	}

	public void setEstado(String texto) {
		if (etiquetaEstado != null) {
			etiquetaEstado.setText(texto);
		}
	}

	public void setBotonesActivos(boolean activo) {
		if (botonSeleccionar != null) {
			botonSeleccionar.setEnabled(activo);
		}

		if (botonImportar != null) {
			botonImportar.setEnabled(activo);
		}

		if (comboFormato != null) {
			comboFormato.setEnabled(activo);
		}
	}
}