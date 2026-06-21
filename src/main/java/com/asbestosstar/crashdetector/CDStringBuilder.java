package com.asbestosstar.crashdetector;

import com.asbestosstar.crashdetector.analizador.VerificacionesLegacy;

public class CDStringBuilder {

	StringBuilder esta = new StringBuilder();

	public CDStringBuilder append(String str) {
		if (str.equals(VerificacionesLegacy.nl) && !esta.toString().endsWith(VerificacionesLegacy.nl)
				&& !esta.toString().replace(" ", "").equals("")) {
			esta.append(str);
		} else if (str.equals(VerificacionesLegacy.nl_html) && !esta.toString().endsWith(VerificacionesLegacy.nl_html)
				&& !esta.toString().replace(" ", "").equals("")) {
			esta.append(str);
		} else if (!esta.toString().contains(str)) {
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
	 * 
	 * @param str
	 * @return
	 */
	public CDStringBuilder appendDupe(String str) {
		// TODO Auto-generated method stub
		esta.append(str);
		return this;
	}

}
