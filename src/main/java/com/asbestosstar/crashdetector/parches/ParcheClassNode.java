package com.asbestosstar.crashdetector.parches;

import org.objectweb.asm.tree.ClassNode;

public interface ParcheClassNode extends Parche<ClassNode> {

	/**
	 * Usa void porque fabric-loader/SpongeMixinConfig usan void. ¡¡¡No Override!!!
	 */
	@Override
	default ClassNode parche(ClassNode contento, String nombre) {
		parchClassNode(contento, nombre);
		return contento;
	}

	/**
	 * Es donde transformas
	 * 
	 * @param node
	 * @param nombre
	 */
	public void parchClassNode(ClassNode node, String nombre);

	@Override
	public default Class<ClassNode> tipo() {
		return ClassNode.class;
	}

}
