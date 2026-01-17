package com.asbestosstar.crashdetector.gui.tipos.modsbuenas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.Statics;
import com.asbestosstar.crashdetector.gui.CrashDetectorGUI;
import com.asbestosstar.crashdetector.gui.elementos.BotonDeBarraLateralDerecha;
import com.asbestosstar.crashdetector.gui.tipos.TipoGUI;
import com.asbestosstar.crashdetector.json.Json;
import com.asbestosstar.crashdetector.json.Json.Nodo;

/**
 * GUI base para registrar mods recomendados ("animados"). Esta clase ESCRIBE el
 * archivo leído por FaltaModAnimado.
 *
 * Formato JSON (arreglo de objetos): [ { "modid": "ejemplo", "ruta":
 * "mods/archivo.jar", "abrir_cd_si_coincide": true, "razon": { "es": "texto",
 * "en": "text" } } ]
 */
public abstract class ModsBuenasGUI extends JDialog implements CrashDetectorGUI, BotonDeBarraLateralDerecha {

	private static final long serialVersionUID = 1L;

	public static Map<String, Supplier<ModsBuenasGUI>> GUIS = new HashMap<>();

	/** Archivo usado por el verificador FaltaModAnimado */
	protected final Path archivo = Statics.carpeta.resolve("mods_animados.json");

	/** Lista de entradas de mods */
	protected final List<EntradaMod> mods = new ArrayList<>();

	protected static class EntradaMod {
		public String modid;
		public String ruta;
		/** En JSON: "abrir_cd_si_coincide". En UI: "Override normal". */
		public boolean abrirCD;
		public Map<String, String> razones = new HashMap<>();

		public boolean esValida() {
			return (modid != null && !modid.trim().isEmpty()) || (ruta != null && !ruta.trim().isEmpty());
		}
	}

	@Override
	public TipoGUI tipo() {
		return TipoGUI.MODS_BUENAS;
	}

	/*
	 * ========================= CARGA / GUARDADO =========================
	 */

	protected void cargarDatos() {
		mods.clear();
		if (!Files.exists(archivo))
			return;

		try {
			String json = new String(Files.readAllBytes(archivo), StandardCharsets.UTF_8);
			if (json == null || json.trim().isEmpty())
				return;

			Nodo raiz = Json.leer(json);
			if (raiz == null || !raiz.esArreglo())
				return;

			for (int i = 0; i < raiz.tamano(); i++) {
				Nodo n = raiz.en(i);
				if (n == null || !n.esObjeto())
					continue;

				EntradaMod e = new EntradaMod();

				Nodo m = n.obtener("modid");
				if (m != null && !m.esObjeto() && !m.esArreglo())
					e.modid = m.comoCadena();

				Nodo r = n.obtener("ruta");
				if (r != null && !r.esObjeto() && !r.esArreglo())
					e.ruta = r.comoCadena();

				Nodo a = n.obtener("abrir_cd_si_coincide");
				if (a != null) {
					try {
						e.abrirCD = a.comoBooleano();
					} catch (Exception ignored) {
					}
				}

				Nodo razon = n.obtener("razon");
				if (razon != null && razon.esObjeto()) {
					for (String lang : razon.claves()) {
						Nodo txt = razon.obtener(lang);
						if (txt != null && !txt.esObjeto() && !txt.esArreglo()) {
							String v = txt.comoCadena();
							if (v != null) {
								v = v.trim();
								if (!v.isEmpty())
									e.razones.put(lang, v);
							}
						}
					}
				}

				if (e.esValida())
					mods.add(e);
			}
		} catch (Exception ignored) {
		}
	}

	protected void guardarDatos() {
		try {
			Nodo raiz = Json.leer("[]");

			for (EntradaMod e : mods) {
				if (e == null || !e.esValida())
					continue;

				Nodo obj = Json.crearObjeto();

				if (e.modid != null && !e.modid.trim().isEmpty())
					obj.obtener("modid").poner(e.modid.trim());

				if (e.ruta != null && !e.ruta.trim().isEmpty())
					obj.obtener("ruta").poner(e.ruta.trim());

				if (e.abrirCD)
					obj.obtener("abrir_cd_si_coincide").poner(true);

				if (e.razones != null && !e.razones.isEmpty()) {
					Nodo r = Json.crearObjeto();
					for (Map.Entry<String, String> en : e.razones.entrySet()) {
						if (en.getKey() == null)
							continue;
						String v = en.getValue();
						if (v == null)
							continue;
						v = v.trim();
						if (v.isEmpty())
							continue;
						r.obtener(en.getKey()).poner(v);
					}
					obj.obtener("razon").poner(r);
				}

				raiz.agregar(obj);
			}

			Files.write(archivo, raiz.aBytesUtf8());
		} catch (IOException ignored) {
		}
	}

	/*
	 * ========================= UTILIDADES PARA UI =========================
	 */

	/**
	 * Convierte nombres (los que se muestran en UI) a códigos ISO usados en JSON.
	 * Debe coincidir con el resto del proyecto.
	 */
	public static String obtenerCodigoIdioma(String nombreIdioma) {
		if (nombreIdioma == null)
			return "es";
		switch (nombreIdioma) {
		case "Español":
			return "es";
		case "English":
			return "en";
		case "العربية":
			return "ar";
		case "Português":
			return "pt";
		case "فارسی":
			return "fa";
		case "Русский":
			return "ru";
		case "简体中文":
			return "zh";
		case "Esperanto":
			return "eo";
		case "日本語":
			return "ja";
		case "한국어":
			return "ko";
		default:
			return "es";
		}
	}

	/**
	 * Devuelve un texto compacto para mostrar razones en la tabla. Ejemplo: "es:
	 * ... | en: ..."
	 */
	protected String resumenRazones(Map<String, String> razones) {
		if (razones == null || razones.isEmpty())
			return "";

		StringBuilder sb = new StringBuilder();
		boolean primero = true;

		// Orden preferido: idioma actual, respaldo, es, resto
		String langActual = MonitorDePID.idioma.codigo();
		String langRespaldo = null;
		try {
			langRespaldo = com.asbestosstar.crashdetector.Idioma.idioma_respaldo.obtener();
		} catch (Exception ignored) {
		}

		List<String> orden = new ArrayList<>();
		if (langActual != null && !langActual.isEmpty())
			orden.add(langActual);
		if (langRespaldo != null && !langRespaldo.isEmpty() && !orden.contains(langRespaldo))
			orden.add(langRespaldo);
		if (!orden.contains("es"))
			orden.add("es");

		for (String k : razones.keySet()) {
			if (k != null && !orden.contains(k))
				orden.add(k);
		}

		for (String lang : orden) {
			String txt = razones.get(lang);
			if (txt == null)
				continue;
			txt = txt.trim();
			if (txt.isEmpty())
				continue;

			if (!primero)
				sb.append(" | ");
			sb.append(lang).append(": ").append(txt);
			primero = false;
		}

		return sb.toString();
	}

	/**
	 * Diálogo multilingüe para editar razones (formato JSON "razon": { lang: texto
	 * }). Inspirado en el diálogo de SylentBell (banderas + código).
	 */
	protected void editarRazonesMultilingue(String titulo, Map<String, String> razonesReferencia, java.awt.Color fondo,
			java.awt.Color texto, java.awt.Color caja, java.awt.Color borde, java.awt.Color botonFondo) {
		if (razonesReferencia == null)
			return;

		JDialog dialogo = new JDialog(this, titulo, true);
		dialogo.setLayout(new BorderLayout(10, 10));
		dialogo.setSize(680, 460);

		JPanel panelCampos = new JPanel(new GridBagLayout());
		panelCampos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panelCampos.setOpaque(true);
		panelCampos.setBackground(fondo);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new java.awt.Insets(6, 6, 6, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Idiomas disponibles (nombre visible -> ruta bandera)
		LinkedHashMap<String, String> mapaIdiomas = mapaParaComboBoxIdiomas();
		Map<String, JTextField> camposTexto = new HashMap<>();

		int fila = 0;
		for (Map.Entry<String, String> entry : mapaIdiomas.entrySet()) {
			String nombreVisible = entry.getKey();
			String rutaBandera = entry.getValue();

			String codigo = obtenerCodigoIdioma(nombreVisible);

			JLabel etiqueta = new JLabel(codigo);
			etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
			etiqueta.setForeground(texto);
			etiqueta.setOpaque(false);

			// Cargar icono de bandera si existe
			try {
				java.io.File f = Statics.carpeta.resolve(rutaBandera).toFile();
				if (f.exists()) {
					ImageIcon icon = new ImageIcon(f.getAbsolutePath());
					Image img = icon.getImage().getScaledInstance(18, 12, Image.SCALE_SMOOTH);
					etiqueta.setIcon(new ImageIcon(img));
					etiqueta.setIconTextGap(8);
				}
			} catch (Exception ignored) {
			}

			gbc.gridx = 0;
			gbc.gridy = fila;
			gbc.weightx = 0.20;
			panelCampos.add(etiqueta, gbc);

			gbc.gridx = 1;
			gbc.weightx = 0.80;

			JTextField campo = new JTextField(40);
			campo.setText(razonesReferencia.getOrDefault(codigo, ""));
			campo.setBackground(caja);
			campo.setForeground(texto);
			campo.setCaretColor(texto);
			campo.setBorder(BorderFactory.createLineBorder(borde, 1));

			camposTexto.put(codigo, campo);
			panelCampos.add(campo, gbc);

			fila++;
		}

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotones.setOpaque(true);
		panelBotones.setBackground(fondo);

		JButton botonAceptar = new JButton(MonitorDePID.idioma.aceptar());
		JButton botonCancelar = new JButton(MonitorDePID.idioma.cancelar());

		estilizarBotonSimple(botonAceptar, botonFondo, java.awt.Color.WHITE, borde);
		estilizarBotonSimple(botonCancelar, botonFondo, java.awt.Color.WHITE, borde);

		panelBotones.add(botonAceptar);
		panelBotones.add(botonCancelar);

		botonAceptar.addActionListener(e -> {
			Map<String, String> nuevas = new HashMap<>();
			for (Map.Entry<String, JTextField> c : camposTexto.entrySet()) {
				String v = c.getValue().getText();
				if (v != null)
					v = v.trim();
				if (v != null && !v.isEmpty())
					nuevas.put(c.getKey(), v);
			}
			razonesReferencia.clear();
			razonesReferencia.putAll(nuevas);
			dialogo.dispose();
		});

		botonCancelar.addActionListener(e -> dialogo.dispose());

		dialogo.add(new JScrollPane(panelCampos), BorderLayout.CENTER);
		dialogo.add(panelBotones, BorderLayout.SOUTH);
		dialogo.setLocationRelativeTo(this);
		dialogo.setVisible(true);
	}

	protected void estilizarBotonSimple(JButton btn, java.awt.Color fondo, java.awt.Color texto, java.awt.Color borde) {
		if (btn == null)
			return;
		btn.setBackground(fondo);
		btn.setForeground(texto);
		btn.setFocusPainted(false);
		btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
		btn.setBorder(BorderFactory.createLineBorder(borde, 1));
	}

	protected void forzarFondoEnPanel(JComponent c, java.awt.Color fondo) {
		if (c == null)
			return;
		c.setOpaque(true);
		c.setBackground(fondo);
	}
}
