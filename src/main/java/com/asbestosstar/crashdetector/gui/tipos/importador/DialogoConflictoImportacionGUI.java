package com.asbestosstar.crashdetector.gui.tipos.importador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.deps.DescargadorDependenciasMaven;
import com.asbestosstar.crashdetector.dto.modpack.importar.ConflictoImportacion;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;

public abstract class DialogoConflictoImportacionGUI extends JDialog implements CrashDetectorGUI {

	public static final Map<String, Supplier<DialogoConflictoImportacionGUI>> GUIS = new HashMap<>();

	public ConflictoImportacion conflicto;
	public ConflictoImportacion.Decision decision = ConflictoImportacion.Decision.CANCELAR;

	public JLabel imagenTema;
	public JTextArea areaMensaje;

	public JButton botonReemplazar;
	public JButton botonSaltar;
	public JButton botonRenombrar;
	public JButton botonCancelar;
	public JButton botonFusionar;
	public JButton botonDescargarDepsNbt;

	public JPanel panelIzquierdo;
	public JPanel panelCentro;
	public JPanel panelBotones;

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
		botonFusionar = new JButton(MonitorDePID.idioma.importadorBotonFusionar());
		botonDescargarDepsNbt = new JButton(MonitorDePID.idioma.descargar_nbt_para_quests());
		botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		botonReemplazar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.REEMPLAZAR));
		botonSaltar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.SALTAR));
		botonRenombrar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.RENOMBRAR));
		botonFusionar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.FUSIONAR));
		botonDescargarDepsNbt.addActionListener(e -> accionDescargarDependenciasNbt());
		botonCancelar.addActionListener(e -> cerrar(ConflictoImportacion.Decision.CANCELAR));

		panelBotones.add(botonReemplazar);
		panelBotones.add(botonSaltar);
		panelBotones.add(botonRenombrar);

		if (puedeMostrarBotonFusionar()) {
			panelBotones.add(botonFusionar);

			if (!dependenciaNbtDisponible()) {
				panelBotones.add(botonDescargarDepsNbt);
			}
		}

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

	public void cerrar(ConflictoImportacion.Decision decision) {
		this.decision = decision;
		dispose();
	}

	public String construirTextoConflicto() {
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

	public boolean puedeMostrarBotonFusionar() {
		if (conflicto == null || conflicto.rutaRelativa == null) {
			return false;
		}

		String r = conflicto.rutaRelativa.replace('\\', '/').toLowerCase();

		if (!r.endsWith(".snbt")) {
			return false;
		}

		return r.contains("config/ftbquests/") || r.contains("/ftbquests/") || r.startsWith("ftbquests/")
				|| r.startsWith("config/ftbquests/");
	}

	public String valor(String texto) {
		return texto == null ? "" : texto;
	}

	public String formatearFecha(long fecha) {
		if (fecha <= 0) {
			return "";
		}

		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fecha));
	}

	public static final File CARPETA_DEPS_IMPORTADOR = new File(Statics.carpeta_mundial_como_archivo, "deps");

	public boolean dependenciaNbtDisponible() {
		if (nombreMotorNbtDisponible()) {
			return true;
		}

		if (!CARPETA_DEPS_IMPORTADOR.exists() || !CARPETA_DEPS_IMPORTADOR.isDirectory()) {
			return false;
		}

		File[] archivos = CARPETA_DEPS_IMPORTADOR.listFiles();

		if (archivos == null) {
			return false;
		}

		for (File archivo : archivos) {
			if (archivo == null || !archivo.isFile()) {
				continue;
			}

			String n = archivo.getName().toLowerCase();

			if (archivo.length() > 0L && (n.equals("nbt-6.1.jar") || n.startsWith("nbt-"))) {
				return true;
			}
		}

		return false;
	}

	public boolean nombreMotorNbtDisponible() {
		try {
			return !"ninguno".equalsIgnoreCase(com.asbestosstar.crashdetector.config.nbt.Nbt.nombreMotor());
		} catch (Throwable t) {
			return false;
		}
	}

	public void accionDescargarDependenciasNbt() {
		int confirmar = JOptionPane.showConfirmDialog(this,
				MonitorDePID.idioma.importador_confirmar_descargar_nbt_para_quests(),
				MonitorDePID.idioma.descargar_nbt(), JOptionPane.YES_NO_OPTION);

		if (confirmar != JOptionPane.YES_OPTION) {
			return;
		}

		if (botonDescargarDepsNbt != null) {
			botonDescargarDepsNbt.setEnabled(false);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean exito = false;
				String mensaje = "";

				try {
					if (!CARPETA_DEPS_IMPORTADOR.exists()) {
						CARPETA_DEPS_IMPORTADOR.mkdirs();
					}

					List<DescargadorDependenciasMaven.CoordenadaMaven> coordenadas = new ArrayList<DescargadorDependenciasMaven.CoordenadaMaven>();

					coordenadas.add(new DescargadorDependenciasMaven.CoordenadaMaven("com.github.Querz", "NBT", "6.1"));

					DescargadorDependenciasMaven.ResultadoDescarga r = DescargadorDependenciasMaven
							.descargarDependencias(coordenadas);

					exito = r != null && r.exito;
					mensaje = r == null ? MonitorDePID.idioma.resultado_nulo() : r.mensaje;
				} catch (Throwable t) {
					CrashDetectorLogger.logException(t);
					mensaje = t.getMessage();
				}

				final boolean exitoFinal = exito;
				final String mensajeFinal = mensaje;

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (botonDescargarDepsNbt != null) {
							botonDescargarDepsNbt.setEnabled(!dependenciaNbtDisponible());
						}

						String texto;

						if (exitoFinal) {
							texto = MonitorDePID.idioma
									.dependencia_nbt_descargada_reiniciar(Statics.nombre_cd.obtener());
						} else {
							texto = MonitorDePID.idioma.no_se_pudo_descargar_dependencia_nbt();

							if (mensajeFinal != null && !mensajeFinal.trim().isEmpty()) {
								texto += "\n\n" + mensajeFinal;
							}
						}

						JOptionPane.showMessageDialog(DialogoConflictoImportacionGUI.this, texto);
					}
				});
			}
		}).start();
	}

}