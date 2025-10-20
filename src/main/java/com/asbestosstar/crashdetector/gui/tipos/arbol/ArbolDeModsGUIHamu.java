package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.asbestosstar.crashdetector.MonitorDePID;
import com.asbestosstar.crashdetector.config.ConfigColor;
import com.asbestosstar.crashdetector.config.ElementoConfig;

public class ArbolDeModsGUIHamu extends ArbolDeModsGUI {

	public static String ID = "hamu";

	// Elementos de configuración de color para este tema
	private ConfigColor colorFondoPrincipal;
	private ConfigColor colorTextoBotonReset;
	private ConfigColor colorTextoCampoBuscar;
	private ConfigColor colorTextoComboFiltro;
	private ConfigColor colorTextoRenderer;
	private ConfigColor colorTextoOverlayCarga;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public void recargarApariencia() {
		// 1) Inicializar o cargar los colores desde configuración
		inicializarColoresConfiguracion();

		// 2) Título y colores de fondo del tema Hamu
		setTitle(MonitorDePID.idioma.tituloArbolDeMods());
		getContentPane().setBackground(colorFondoPrincipal.obtener());

		// 3) Textos y tooltips dinámicos (los colores de texto se aplican en el
		// renderer)
		if (botonReset != null) {
			botonReset.setText(MonitorDePID.idioma.botonResetearArbol());
			// Nota: El color del texto del botón se controla mediante el Look & Feel o el
			// renderer
		}
		if (campoBuscar != null) {
			campoBuscar.setToolTipText(MonitorDePID.idioma.tipBuscar());
		}

		// 4) Volver a crear el modelo del combo (respetando selección si aplica)
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

		// 5) Recargar iconos (por si cambiaron assets/tema)
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

		// 6) Imagen “Hamu” inferior
		if (imagenHamu != null) {
			imagenHamu.setIcon(new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/hamu.png").toString()));
		}

		// 7) Overlay de carga (gif + texto)
		if (gifCarga != null) {
			ImageIcon icon = new ImageIcon(MonitorDePID.carpeta.resolve("imagenes/padoru.gif").toString());
			if (icon.getIconWidth() <= 0) {
				icon = crearIcono("", "…");
			}
			gifCarga.setIcon(icon);
			gifCarga.setText(MonitorDePID.idioma.cargando());
		}

		// 8) Renderer (refresca textos de contenedores según idioma y aplica nuevos
		// iconos)
		if (arbolModulos != null) {
			arbolModulos.setCellRenderer(new RenderizadorCeldasArbol());
		}

		// 9) Reconstruir el árbol para que todos los rótulos
		// (raíz/paquetes/contenedores) se actualicen
		construirArbolInicialAsync();

		// 10) Refrescar la ventana
		revalidate();
		repaint();
	}

	/**
	 * Inicializa los elementos de configuración de color para este tema. Si no
	 * existen en el archivo de configuración, se crean con valores por defecto.
	 */
	private void inicializarColoresConfiguracion() {
		// Fondo principal
		colorFondoPrincipal = ConfigColor.de("tema.hamu.color.fondo", new Color(144, 203, 239)); // #90cbef

		// Colores de texto (pueden ser usados por el renderer o componentes)
		colorTextoBotonReset = ConfigColor.de("tema.hamu.color.texto.boton.reset", Color.BLACK);
		colorTextoCampoBuscar = ConfigColor.de("tema.hamu.color.texto.tooltip.buscar", Color.DARK_GRAY);
		colorTextoComboFiltro = ConfigColor.de("tema.hamu.color.texto.combo.filtro", Color.BLACK);
		colorTextoRenderer = ConfigColor.de("tema.hamu.color.texto.renderer", Color.BLACK);
		colorTextoOverlayCarga = ConfigColor.de("tema.hamu.color.texto.overlay.carga", Color.WHITE);
	}

	@Override
	public List<ElementoConfig> obtenerElementosConfigs() {
		List<ElementoConfig> elementos = new ArrayList<>();

		elementos.add(colorFondoPrincipal);
		elementos.add(colorTextoBotonReset);
		elementos.add(colorTextoCampoBuscar);
		elementos.add(colorTextoComboFiltro);
		elementos.add(colorTextoRenderer);
		elementos.add(colorTextoOverlayCarga);

		return elementos;
	}
}