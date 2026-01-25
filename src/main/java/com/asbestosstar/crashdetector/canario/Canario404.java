package com.asbestosstar.crashdetector.canario;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Para canarios buscando para un archivo
 */
public interface Canario404 extends CanarioDeOrdenJudicial {

	/**
	 * Si no existe el servicio no es seguro
	 * 
	 * @return
	 */
	public String urlDeArchivoBuscar();

	@Override
	public default boolean seguro() {

		try {
			URL url = new URL(urlDeArchivoBuscar());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			// Intentar primero con HEAD (más liviano)
			con.setRequestMethod("HEAD");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setInstanceFollowRedirects(true);

			int codigo = con.getResponseCode();

			// Algunos servidores no aceptan HEAD, intentar GET si falla
			if (codigo == HttpURLConnection.HTTP_BAD_METHOD) {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");
				con.setConnectTimeout(5000);
				con.setReadTimeout(5000);
				con.setInstanceFollowRedirects(true);
				codigo = con.getResponseCode();
			}

			// Seguro mientras NO sea 404
			return codigo != HttpURLConnection.HTTP_NOT_FOUND;

		} catch (Exception e) {
			// Cualquier error de red se considera inseguro
			return false;
		}
	}

}
