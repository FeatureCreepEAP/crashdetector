package com.asbestosstar.crashdetector.gui.tipos.hardware;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.asbestosstar.crashdetector.Statics;

/**
 * Paleta obtenida de la imagen VAllure.
 *
 * No contiene valores RGB fijos. El acento se calcula a partir de los píxeles
 * visibles de la imagen y el resto de colores se deriva de ese acento y de los
 * colores del Look & Feel actual.
 */
public final class TemaVallure {

	private static volatile Paleta cache;

	private TemaVallure() {
	}

	public static Paleta paleta() {
		Paleta valor = cache;
		if (valor != null) {
			return valor;
		}
		synchronized (TemaVallure.class) {
			if (cache == null) {
				cache = crearPaleta();
			}
			return cache;
		}
	}

	public static ImageIcon iconoEscalado(int anchoMaximo, int altoMaximo) {
		BufferedImage imagen = cargarImagen();
		if (imagen == null) {
			return null;
		}
		double escala = Math.min((double) anchoMaximo / imagen.getWidth(), (double) altoMaximo / imagen.getHeight());
		escala = Math.min(1.0, escala);
		int ancho = Math.max(1, (int) Math.round(imagen.getWidth() * escala));
		int alto = Math.max(1, (int) Math.round(imagen.getHeight() * escala));
		Image redimensionada = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
		return new ImageIcon(redimensionada);
	}

	private static Paleta crearPaleta() {
		Color fondoLaf = colorUI("Panel.background", Color.WHITE);
		Color textoLaf = colorUI("Label.foreground", Color.BLACK);
		Color seleccionLaf = colorUI("Table.selectionBackground", fondoLaf);
		Color textoSeleccionLaf = colorUI("Table.selectionForeground", textoLaf);

		BufferedImage imagen = cargarImagen();
		Color acento = extraerAcento(imagen);
		if (acento == null) {
			acento = colorUI("Button.background", seleccionLaf);
		}

		float[] hsb = Color.RGBtoHSB(acento.getRed(), acento.getGreen(), acento.getBlue(), null);
		float saturacion = limitar(Math.max(hsb[1], 0.45f));
		float brillo = limitar(Math.max(hsb[2], 0.55f));

		Color fondo = mezclar(fondoLaf, acento, 0.08f);
		Color panel = mezclar(fondoLaf, acento, 0.16f);
		Color boton = Color.getHSBColor(hsb[0], saturacion, brillo);
		Color recomendado = Color.getHSBColor(0.33f, limitar(saturacion * 0.85f), limitar(brillo * 0.82f));
		Color desaconsejado = Color.getHSBColor(0.0f, limitar(saturacion * 0.95f), limitar(brillo * 0.92f));
		Color neutral = textoLaf;
		Color borde = mezclar(textoLaf, acento, 0.45f);
		Color seleccion = mezclar(seleccionLaf, acento, 0.35f);

		return new Paleta(fondo, panel, textoLaf, boton, recomendado, neutral, desaconsejado, borde, seleccion,
				textoSeleccionLaf);
	}

	private static BufferedImage cargarImagen() {
		try {
			File externa = Statics.carpeta.resolve("imagenes").resolve("vallure.png").toFile();
			if (externa.isFile()) {
				return ImageIO.read(externa);
			}
		} catch (Throwable ignorado) {
		}

		try {
			URL recurso = TemaVallure.class.getResource("/imagenes/vallure.png");
			if (recurso != null) {
				return ImageIO.read(recurso);
			}
		} catch (Throwable ignorado) {
		}
		return null;
	}

	private static Color extraerAcento(BufferedImage imagen) {
		if (imagen == null) {
			return null;
		}
		long sumaR = 0;
		long sumaG = 0;
		long sumaB = 0;
		long pesoTotal = 0;

		int pasoX = Math.max(1, imagen.getWidth() / 100);
		int pasoY = Math.max(1, imagen.getHeight() / 100);
		for (int y = 0; y < imagen.getHeight(); y += pasoY) {
			for (int x = 0; x < imagen.getWidth(); x += pasoX) {
				int argb = imagen.getRGB(x, y);
				int alpha = (argb >>> 24) & 0xFF;
				if (alpha < 32) {
					continue;
				}
				int r = (argb >>> 16) & 0xFF;
				int g = (argb >>> 8) & 0xFF;
				int b = argb & 0xFF;
				float[] hsb = Color.RGBtoHSB(r, g, b, null);
				long peso = Math.max(1, Math.round(alpha * (0.2f + hsb[1]) * (0.2f + hsb[2])));
				sumaR += r * peso;
				sumaG += g * peso;
				sumaB += b * peso;
				pesoTotal += peso;
			}
		}
		if (pesoTotal == 0) {
			return null;
		}
		return new Color((int) (sumaR / pesoTotal), (int) (sumaG / pesoTotal), (int) (sumaB / pesoTotal));
	}

	private static Color mezclar(Color a, Color b, float proporcionB) {
		float p = limitar(proporcionB);
		float q = 1.0f - p;
		return new Color(Math.round(a.getRed() * q + b.getRed() * p), Math.round(a.getGreen() * q + b.getGreen() * p),
				Math.round(a.getBlue() * q + b.getBlue() * p));
	}

	private static Color colorUI(String clave, Color respaldo) {
		Color color = UIManager.getColor(clave);
		return color == null ? respaldo : color;
	}

	private static float limitar(float valor) {
		return Math.max(0.0f, Math.min(1.0f, valor));
	}

	public static final class Paleta {
		public final Color fondo;
		public final Color panel;
		public final Color texto;
		public final Color boton;
		public final Color recomendado;
		public final Color neutral;
		public final Color desaconsejado;
		public final Color borde;
		public final Color seleccion;
		public final Color textoSeleccion;

		Paleta(Color fondo, Color panel, Color texto, Color boton, Color recomendado, Color neutral,
				Color desaconsejado, Color borde, Color seleccion, Color textoSeleccion) {
			this.fondo = fondo;
			this.panel = panel;
			this.texto = texto;
			this.boton = boton;
			this.recomendado = recomendado;
			this.neutral = neutral;
			this.desaconsejado = desaconsejado;
			this.borde = borde;
			this.seleccion = seleccion;
			this.textoSeleccion = textoSeleccion;
		}
	}
}
