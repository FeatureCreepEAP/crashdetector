package com.asbestosstar.crashdetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
	public String[] parsear(String linea) throws IOException {
		List<String> resultado = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean entreComillas = false;

		for (int i = 0; i < linea.length(); i++) {
			char c = linea.charAt(i);

			if (c == '"') {
				entreComillas = !entreComillas;
				continue;
			}

			if (c == ',' && !entreComillas) {
				resultado.add(sb.toString());
				sb.setLength(0); // Limpiar
				continue;
			}

			sb.append(c);
		}

		resultado.add(sb.toString());
		return resultado.toArray(new String[0]);
	}
}