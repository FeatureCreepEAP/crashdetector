package com.asbestosstar.crashdetector.api_sito_registro;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.Consola;

public class StikkedAPI implements APIdeSitioDeRegistro {

	private static final int MAX_SIZE_BYTES = 16 * 1024 * 1024; // 16MB

	@Override
	public String nombre() {
		return "stikked";
	}

	@Override
	public String publicarRegistro(Consola registro) throws DemasiadoGrande, ErrorConPublicar {
		String texto = registro.obtainerContenidoParaPublicar().trim();

		if (texto.equals("")) {
			throw new ErrorConPublicar("Registro no tiene texto " + registro.archivo.toString());
		}

		byte[] textobytes = texto.getBytes(StandardCharsets.UTF_8);
		if (textobytes.length > MAX_SIZE_BYTES) {
			throw new DemasiadoGrande();
		}

		// Prepare parameters
		String params = "";
		try {
			params = String.format("text=%s&title=CrashLog&name=CrashDetector&private=1&lang=java",
					URLEncoder.encode(texto, StandardCharsets.UTF_8.toString()));
		} catch (Exception e) {
			throw new ErrorConPublicar("Encoding error: " + e.getMessage());
		}

		try {
			URL url = new URL(APIdeSitioDeRegistro.sitioDeConfig());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(params.length()));
			conn.setDoOutput(true);

			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.writeBytes(params);
				wr.flush();
			}

			int responseCode = conn.getResponseCode();
			if (responseCode != 200) {
				throw new ErrorConPublicar("HTTP error: " + responseCode);
			}

			StringBuilder response = new StringBuilder();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {

				String line;
				while ((line = in.readLine()) != null) {
					response.append(line);
				}
			}

			if (response.toString().startsWith("Error:")) {
				throw new ErrorConPublicar(response.toString());
			} else {
				return response.toString();
			}
		} catch (IOException e) {
			throw new ErrorConPublicar("error de red: " + e.getMessage());
		}
	}

	@Override
	public List<String> sitiosPorDefecto() {
		List<String> sitios = new ArrayList<>();
		sitios.add("https://paste.mikumikudance.jp/api/create");
		sitios.add("https://paste.mikumikudance.jp/en/api/create");
		// sitios.add("https://paste.atlauncher.com/api/create");
		return sitios;
	}
}