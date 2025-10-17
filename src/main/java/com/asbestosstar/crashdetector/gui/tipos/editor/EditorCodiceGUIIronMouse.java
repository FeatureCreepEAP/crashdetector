package com.asbestosstar.crashdetector.gui.tipos.editor;

/**
 * Editor de codice.json (schema 0) SIN pestañas. El usuario define TODOS los
 * idiomas de cada verificación en una sola vista. - Izquierda: lista de
 * verificaciones. - Centro: formulario completo (campos generales + tabla de
 * idiomas). - Derecha: vista previa JSON. - Usa MonitorDePID.idioma PARA TODAS
 * LAS ETIQUETAS (debes implementar esos métodos en Idioma). - Crea el archivo
 * si no existe. - Fuerza a completar todos los campos antes de
 * guardar/actualizar.
 *
 * Compatibilidad: Java 8.
 */
public class EditorCodiceGUIIronMouse extends EditorFirmasGUI {

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return "ironmouse";
	}

	@Override
	public void recargarApariencia() {
		// TODO Auto-generated method stub
	    recargarTextosYEstilo();
	}

	

}