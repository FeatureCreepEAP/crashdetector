package com.asbestosstar.crashdetector.gui.tipos.transferidor_clases;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

/**
 * Apariencia Eon of Stars para el transferidor de clases.
 */
public class TransferidorClasesEonOfStars extends TransferidorClasesGUI {

	private static final long serialVersionUID = 1L;
	public static final String ID = "eon_of_stars";

	private final ConfigColor fondo = ConfigColor.de("tema.eon_of_stars.transferidor.fondo", new Color(20, 13, 24));
	private final ConfigColor panel = ConfigColor.de("tema.eon_of_stars.transferidor.panel", new Color(42, 24, 43));
	private final ConfigColor campo = ConfigColor.de("tema.eon_of_stars.transferidor.campo", new Color(25, 18, 31));
	private final ConfigColor texto = ConfigColor.de("tema.eon_of_stars.transferidor.texto", new Color(242, 234, 225));
	private final ConfigColor textoSuave = ConfigColor.de("tema.eon_of_stars.transferidor.texto_suave",
			new Color(198, 184, 190));
	private final ConfigColor acento = ConfigColor.de("tema.eon_of_stars.transferidor.acento", new Color(104, 67, 146));
	private final ConfigColor borde = ConfigColor.de("tema.eon_of_stars.transferidor.borde", new Color(116, 75, 108));
	private final ConfigColor seleccion = ConfigColor.de("tema.eon_of_stars.transferidor.seleccion",
			new Color(71, 48, 92));

	@Override
	public String id() {
		return ID;
	}

	@Override
	protected Color colorFondo() {
		return fondo.obtener();
	}

	@Override
	protected Color colorPanel() {
		return panel.obtener();
	}

	@Override
	protected Color colorCampo() {
		return campo.obtener();
	}

	@Override
	protected Color colorTexto() {
		return texto.obtener();
	}

	@Override
	protected Color colorTextoSuave() {
		return textoSuave.obtener();
	}

	@Override
	protected Color colorAcento() {
		return acento.obtener();
	}

	@Override
	protected Color colorBorde() {
		return borde.obtener();
	}

	@Override
	protected Color colorSeleccion() {
		return seleccion.obtener();
	}

	@Override
	protected String rutaImagenTema() {
		return "imagenes/eon_of_stars.png";
	}

	@Override
	protected List<ElementoConfig> configuracionesTema() {
		fondo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		panel.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorPanel());
		campo.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorFondo());
		texto.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		textoSuave.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorTexto());
		acento.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());
		borde.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBorde());
		seleccion.establecerNombreParaMostrar(() -> MonitorDePID.idioma.colorBoton());

		List<ElementoConfig> ret = new ArrayList<ElementoConfig>();
		ret.add(fondo);
		ret.add(panel);
		ret.add(campo);
		ret.add(texto);
		ret.add(textoSuave);
		ret.add(acento);
		ret.add(borde);
		ret.add(seleccion);
		return ret;
	}
}
