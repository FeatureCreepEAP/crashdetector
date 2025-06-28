package com.asbestosstar.crashdetector.parches.minecraft;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;

import com.asbestosstar.crashdetector.CrashDetectorLogger;
import com.asbestosstar.crashdetector.parches.ConfigDeParches;
import com.asbestosstar.crashdetector.parches.Parche;
import com.asbestosstar.crashdetector.parches.ParcheClassNode;

public class PreferIPv4StackParch implements ParcheClassNode{

	public static String id = "preferIPv4Stack";
	
	@Override
	public Set<String> clases() {
		// TODO Auto-generated method stub
		return new HashSet<String>();
	}

	@Override
	public Parche<ClassNode> nuevo() {
		// TODO Auto-generated method stub
		return new PreferIPv4StackParch();
	}

	@Override
	public String id() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String nombre_de_gui() {
		// TODO Auto-generated method stub
		return "preferIPv4Stack";
	}

	@Override
	public void parchClassNode(ClassNode node, String nombre) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean activar() {
		boolean ret = ConfigDeParches.obtenerInstancia().estaActivo(this.id());
		if(ret) {
			CrashDetectorLogger.log("preferIPV4Stack");
			System.setProperty("java.net.preferIPv4Stack", "true");
		}
		
		return ret;
	}
	
	

}
