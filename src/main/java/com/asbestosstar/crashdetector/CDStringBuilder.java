package com.asbestosstar.crashdetector;

import com.asbestosstar.crashdetectormc.analyzador.Verificaciones;

public class CDStringBuilder {

	StringBuilder esta = new StringBuilder();
	StringBuilder super_strbld;
	
	
	public CDStringBuilder(StringBuilder super_strbld) {
		this.super_strbld=super_strbld;
	}
	
	
	public CDStringBuilder append(String str) {
		if(str.equals(Verificaciones.nl)&&!esta.toString().endsWith(Verificaciones.nl)&&!esta.toString().replace(" ", "").equals("")) {
			esta.append(str);
		}else if (str.equals(Verificaciones.nl_html) && !esta.toString().endsWith(Verificaciones.nl_html)&&!esta.toString().replace(" ", "").equals("")) {
			esta.append(str);
		}
		else if(!esta.toString().contains(str)&&!super_strbld.toString().contains(str)) {
			esta.append(str);
		}
		return this;
	}
	
	@Override
	public String toString() {
		return esta.toString();
	}
	
	public int length() {
		return esta.length();
	}


	/**
	 * Puedes Append si es un duplicado
	 * @param str
	 * @return
	 */
	public CDStringBuilder appendDupe(String str) {
		// TODO Auto-generated method stub
		esta.append(str);
		return this;
	}
	
}
