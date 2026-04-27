package com.asbestosstar.crashdetector.gui.tipos.importador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class DialogoConflictoImportacionGUI extends JDialog implements CrashDetectorGUI {

	public static final Map<String, Supplier<DialogoConflictoImportacionGUI>> GUIS = new HashMap<>();

	protected ConflictoImportacion conflicto;
	protected ConflictoImportacion.Decision decision = ConflictoImportacion.Decision.CANCELAR;

	protected JLabel imagenTema;
	protected JTextArea areaMensaje;

	protected JButton botonReemplazar;
	protected JButton botonSaltar;
	protected JButton botonRenombrar;
	protected JButton botonCancelar;

	protected JPanel panelIzquierdo;
	protected JPanel panelCentro;
	protected JPanel panelBotones;

	public DialogoConflictoImportacionGUI() {
	}

	public DialogoConflictoImportacionGUI(ConflictoImportacion conflicto) {
		this.conflicto = conflicto;
	}

	public void establecerConflicto(ConflictoImportacion conflicto) {
		this.conflicto = conflicto;
	}

	@Override
	public TipoGUI<?> tipo() {
		return TipoGUI.IMPORTADOR_CONFLICTO;
	}

	@Override
	public void init() {
		setTitle(MonitorDePID.idioma.importadorConflictoTitulo());
		setModal(true);
		setSize(720, 440);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout(8, 8));

		panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(230, 360));

		imagenTema = new JLabel("", SwingConstants.CENTER);
		panelIzquierdo.add(imagenTema, BorderLayout.NORTH);

		JLabel mensaje = new JLabel(MonitorDePID.idioma.importadorYumeiriTeExtraniamos(), SwingConstants.CENTER);
		panelIzquierdo.add(mensaje, BorderLayout.SOUTH);

		panelCentro = new JPanel(new BorderLayout());
		areaMensaje = new JTextArea(construirTextoConflicto());
		areaMensaje.setEditable(false);
		areaMensaje.setLineWrap(true);
		areaMensaje.setWrapStyleWord(true);
		panelCentro.add(areaMensaje, BorderLayout.CENTER);

		panelBotones = new JPanel();

		botonReemplazar = new JButton(MonitorDePID.idioma.importadorBotonReemplazar());
		botonSaltar = new JButton(MonitorDePID.idioma.importadorBotonSaltar());
		botonRenombrar = new JButton(MonitorDePID.idioma.importadorBotonRenombrar());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		botonReemplazar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.REEMPLAZAR));
		botonSaltar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.SALTAR));
		botonRenombrar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.RENOMBRAR));
		botonCancelar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.CANCELAR));

		panelBotones.add(botonReemplazar);
		panelBotones.add(botonSaltar);
		panelBotones.add(botonRenombrar);
		panelBotones.add(botonCancelar);

		add(panelIzquierdo, BorderLayout.WEST);
		add(panelCentro, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		recargarApariencia();
	}

	public ConflictoImportacion.Decision mostrarYObtenerDecision() {
		if (getContentPane().getComponentCount() == 0) {
			init();
		}

		setVisible(true);
		return decision;
	}

	protected void cerrar(ConflictoImportacion.Decision decision) {
		this.decision = decision;
		dispose();
	}

	protected String construirTextoConflicto() {
		if (conflicto == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		sb.append(MonitorDePID.idioma.importadorConflictoMensaje()).append("\n\n");
		sb.append(MonitorDePID.idioma.importadorRuta()).append(": ").append(valor(conflicto.rutaRelativa))
				.append("\n\n");

		sb.append(MonitorDePID.idioma.importadorArchivoExistente()).append(":\n");
		sb.append("  ").append(conflicto.archivoExistente).append("\n");
		sb.append("  ").append(MonitorDePID.idioma.importadorTamano()).append(": ").append(conflicto.tamanoExistente)
				.append("\n");
		sb.append("  ").append(MonitorDePID.idioma.importadorFecha()).append(": ")
				.append(formatearFecha(conflicto.fechaExistente)).append("\n\n");

		sb.append(MonitorDePID.idioma.importadorArchivoNuevo()).append(":\n");
		sb.append("  ").append(conflicto.archivoNuevo).append("\n");
		sb.append("  ").append(MonitorDePID.idioma.importadorTamano()).append(": ").append(conflicto.tamanoNuevo)
				.append("\n");
		sb.append("  ").append(MonitorDePID.idioma.importadorFecha()).append(": ")
				.append(formatearFecha(conflicto.fechaNueva)).append("\n\n");

		if (conflicto.tipo == ConflictoImportacion.Tipo.MOD) {
			sb.append(MonitorDePID.idioma.importadorInfoMod()).append(":\n");
			sb.append("  CurseForge actual: ").append(valor(conflicto.curseForgeIdExistente)).append("\n");
			sb.append("  CurseForge importado: ").append(valor(conflicto.curseForgeIdNuevo)).append("\n");
			sb.append("  Modrinth proyecto actual: ").append(valor(conflicto.modrinthProjectIdExistente)).append("\n");
			sb.append("  Modrinth proyecto importado: ").append(valor(conflicto.modrinthProjectIdNuevo)).append("\n");
			sb.append("  Modrinth versión actual: ").append(valor(conflicto.modrinthVersionIdExistente)).append("\n");
			sb.append("  Modrinth versión importada: ").append(valor(conflicto.modrinthVersionIdNuevo)).append("\n\n");

			if (conflicto.importadoPareceMasNuevo) {
				sb.append(MonitorDePID.idioma.importadorModImportadoMasNuevo()).append("\n");
			} else if (conflicto.importadoPareceMasViejo) {
				sb.append(MonitorDePID.idioma.importadorModImportadoMasViejo()).append("\n");
			}
		}

		return sb.toString();
	}

	protected String valor(String texto) {
		return texto == null ? "" : texto;
	}

	protected String formatearFecha(long fecha) {
		if (fecha <= 0) {
			return "";
		}

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fecha));
	}
}