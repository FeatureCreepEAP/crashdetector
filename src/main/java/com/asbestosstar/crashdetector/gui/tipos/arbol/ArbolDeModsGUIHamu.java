package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.awt.Color;

import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.MonitorDePID;

public class ArbolDeModsGUIHamu extends ArbolDeModsGUI {

	public static String ID = "hamu";

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void recargarApariencia() {
		// 1) Título y colores de fondo del tema Hamu
		setTitle(MonitorDePID.idioma.tituloArbolDeMods());
		getContentPane().setBackground(new Color(144, 203, 239)); // #90cbef

		// 2) Textos y tooltips dinámicos
		if (botonReset != null) {
			botonReset.setText(MonitorDePID.idioma.botonResetearArbol());
		}
		if (campoBuscar != null) {
			campoBuscar.setToolTipText(MonitorDePID.idioma.tipBuscar());
		}

		// 3) Volver a crear el modelo del combo (respetando selección si aplica)
		if (comboFiltro != null) {
			Object previa = comboFiltro.getSelectedItem();
			comboFiltro.removeAllItems();
			String[] opcionesFiltro = { "*", MonitorDePID.idioma.filtroPaquetes(), MonitorDePID.idioma.filtroClases(),
					MonitorDePID.idioma.filtroMetodos(), MonitorDePID.idioma.filtroCampos(),
					MonitorDePID.idioma.filtroReferenciasCampo(), MonitorDePID.idioma.filtroReferenciasMetodo(),
					MonitorDePID.idioma.filtroReferenciasClase(), "Constantes" };
			for (String op : opcionesFiltro)
				comboFiltro.addItem(op);

			// Reintentar seleccionar lo que el usuario tenía (si existe aún)
			if (previa != null) {
				for (int i = 0; i < comboFiltro.getItemCount(); i++) {
					if (previa.equals(comboFiltro.getItemAt(i))) {
						comboFiltro.setSelectedIndex(i);
						break;
					}
				}
			}
		}

		// 4) Recargar iconos (por si cambiaron assets/tema)
		iconoMod = crearIcono(MonitorDePID.carpeta.resolve("imagenes/mod.png").toString(), "M");
		iconoClase = crearIcono(MonitorDePID.carpeta.resolve("imagenes/clase.png").toString(), "C");
		iconoMetodo = crearIcono(MonitorDePID.carpeta.resolve("imagenes/metodo.png").toString(), "m");
		iconoCampo = crearIcono(MonitorDePID.carpeta.resolve("imagenes/campo.png").toString(), "f");
		iconoPaquete = crearIcono(MonitorDePID.carpeta.resolve("imagenes/paquete.png").toString(), "P");
		iconoReferenciaMetodo = crearIcono(MonitorDePID.carpeta.resolve("imagenes/referencia_metodo.png").toString(),
				"RM");
		iconoReferenciaCampo = crearIcono(MonitorDePID.carpeta.resolve("imagenes/referencia_campo.png").toString(),
				"RC");
		// Si no hay imagen de constantes, reusar una conocida
		iconoConstante = crearIcono(MonitorDePID.carpeta.resolve("imagenes/constante.png").toString(), "K");
		if (iconoConstante.getIconWidth() <= 0) {
			iconoConstante = crearIcono(MonitorDePID.carpeta.resolve("imagenes/referencia_campo.png").toString(), "K");
		}

		// 5) Imagen “Hamu” inferior
		if (imagenHamu != null) {
			imagenHamu.setIcon(new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/hamu.png").toString()));
		}

		// 6) Overlay de carga (gif + texto)
		if (gifCarga != null) {
			ImageIcon icon = new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/padoru.gif").toString());
			if (icon.getIconWidth() <= 0) {
				icon = crearIcono("", "…");
			}
			gifCarga.setIcon(icon);
			gifCarga.setText(MonitorDePID.idioma.cargando());
		}

		// 7) Renderer (refresca textos de contenedores según idioma y aplica nuevos
		// iconos)
		if (arbolModulos != null) {
			arbolModulos.setCellRenderer(new RenderizadorCeldasArbol());
		}

		// 8) Reconstruir el árbol para que todos los rótulos
		// (raíz/paquetes/contenedores) se actualicen
		construirArbolInicialAsync();

		// 9) Refrescar la ventana
		revalidate();
		repaint();
	}

}