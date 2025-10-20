package com.asbestosstar.crashdetector.gui.tipos.quickfix;

import java.awt.Color;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Implementación "DemonSlayers" centrada en APARIENCIA.
 *
 * - Define ruta/tamaño de la imagen del pie. - Ajusta el color del separador. -
 * Ajusta opacidades para mantener look transparente. - `id()` propio (codename
 * en español).
 */
public class PanelQuickFixDemonSlayers extends TodosQuickFixesGUI {

	private static final long serialVersionUID = 1L;
	public static String ID = "demonslayers";

	@Override
	protected String rutaImagenPie() {
		return MonitorDePID.carpeta.resolve("imagenes/demonslayers.png").toString();
	}

	// Si quieres que sea 1024x1024 como decía el comentario original, cambia a
	// 1024.
	@Override
	protected int anchoImagenPie() {
		return 128;
	}

	@Override
	protected int altoImagenPie() {
		return 128;
	}

	@Override
	protected String textoFallbackImagen() {
		return "(No se pudo cargar demonslayers.png)";
	}

	@Override
	protected Color colorSeparador() {
		return Color.LIGHT_GRAY;
	}

	@Override
	protected void aplicarApariencia() {
		// Mantener transparencia (como tu versión inicial):
		getViewport().setOpaque(false);
		setOpaque(false);
		if (panelContenedor != null)
			panelContenedor.setOpaque(false);
		if (piePanel != null)
			piePanel.setOpaque(false);

		// Si quisieras agregar colores o bordes, hazlo aquí (NO localizado)
		// panelContenedor.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); //
		// ya se define en base
		revalidate();
		repaint();
	}

	// ====== CrashDetectorGUI ======

	@Override
	public String id() {
		// Codename simple en español
		return ID;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		// TODO Auto-generated method stub
		return null;
	}
}
