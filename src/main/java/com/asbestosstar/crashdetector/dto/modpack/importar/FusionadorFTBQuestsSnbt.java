package com.asbestosstar.crashdetector.dto.modpack.importar;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.asbestosstar.crashdetector.config.nbt.Nbt;
import com.asbestosstar.crashdetector.config.snbt.Snbt;

public class FusionadorFTBQuestsSnbt {

	public static boolean puedeFusionar(InfoEntradaImportacion info, Path destino) {
		String ruta = "";

		if (info != null && info.rutaRelativa != null) {
			ruta = info.rutaRelativa;
		} else if (destino != null) {
			ruta = destino.toString();
		}

		ruta = ruta.replace('\\', '/').toLowerCase();

		if (!ruta.endsWith(".snbt")) {
			return false;
		}

		return ruta.contains("config/ftbquests/") || ruta.contains("/ftbquests/") || ruta.startsWith("ftbquests/")
				|| ruta.startsWith("config/ftbquests/");
	}

	public static byte[] fusionar(byte[] bytesEntrada, Path destino) throws IOException {
		if (bytesEntrada == null) {
			throw new IOException("No hay bytes importados para fusionar.");
		}

		if (destino == null || !Files.isRegularFile(destino)) {
			throw new IOException("No existe archivo destino para fusionar: " + destino);
		}

		String textoExistente = new String(Files.readAllBytes(destino), StandardCharsets.UTF_8);
		String textoNuevo = new String(bytesEntrada, StandardCharsets.UTF_8);

		Nbt.Nodo existente = Snbt.leer(textoExistente);
		Nbt.Nodo nuevo = Snbt.leer(textoNuevo);

		Nbt.Nodo fusionado = fusionarNodos(existente, nuevo, "");

		String salida = Snbt.escribir(fusionado);
		return salida.getBytes(StandardCharsets.UTF_8);
	}

	private static Nbt.Nodo fusionarNodos(Nbt.Nodo existente, Nbt.Nodo nuevo, String claveActual) {
		if (existente == null) {
			return clonar(nuevo);
		}

		if (nuevo == null) {
			return existente;
		}

		if (existente.esObjeto() && nuevo.esObjeto()) {
			return fusionarObjetos(existente, nuevo);
		}

		if (existente.esLista() && nuevo.esLista()) {
			return fusionarListas(existente, nuevo, claveActual);
		}

		/*
		 * Si ambos tienen la misma clave escalar, se conserva el archivo existente.
		 * Esto evita que un modpack importado cambie el titulo, icono, orden, filename,
		 * group, etc. del archivo que ya existe.
		 */
		return existente;
	}

	private static Nbt.Nodo fusionarObjetos(Nbt.Nodo existente, Nbt.Nodo nuevo) {
		List<String> clavesNuevas = nuevo.claves();

		for (String clave : clavesNuevas) {
			Nbt.Nodo valorNuevo = nuevo.obtener(clave);

			if (!existente.contiene(clave)) {
				existente.obtener(clave).poner(clonar(valorNuevo));
				continue;
			}

			Nbt.Nodo valorExistente = existente.obtener(clave);
			fusionarNodos(valorExistente, valorNuevo, clave);
		}

		return existente;
	}

	private static Nbt.Nodo fusionarListas(Nbt.Nodo existente, Nbt.Nodo nuevo, String claveActual) {
		if (listaDeObjetosConId(existente) || listaDeObjetosConId(nuevo)) {
			return fusionarListaPorId(existente, nuevo);
		}

		return fusionarListaPorValor(existente, nuevo);
	}

	private static Nbt.Nodo fusionarListaPorId(Nbt.Nodo existente, Nbt.Nodo nuevo) {
		Map<String, Nbt.Nodo> existentesPorId = new HashMap<String, Nbt.Nodo>();

		for (int i = 0; i < existente.tamano(); i++) {
			Nbt.Nodo entrada = existente.en(i);
			String id = obtenerId(entrada);

			if (id != null && !id.trim().isEmpty()) {
				existentesPorId.put(id, entrada);
			}
		}

		for (int i = 0; i < nuevo.tamano(); i++) {
			Nbt.Nodo entradaNueva = nuevo.en(i);
			String id = obtenerId(entradaNueva);

			if (id != null && !id.trim().isEmpty()) {
				Nbt.Nodo entradaExistente = existentesPorId.get(id);

				if (entradaExistente != null) {
					fusionarNodos(entradaExistente, entradaNueva, "");
				} else {
					existente.agregar(clonar(entradaNueva));
				}

				continue;
			}

			if (!listaContieneValor(existente, entradaNueva)) {
				existente.agregar(clonar(entradaNueva));
			}
		}

		return existente;
	}

	private static Nbt.Nodo fusionarListaPorValor(Nbt.Nodo existente, Nbt.Nodo nuevo) {
		HashSet<String> valores = new HashSet<String>();

		for (int i = 0; i < existente.tamano(); i++) {
			valores.add(valorComparable(existente.en(i)));
		}

		for (int i = 0; i < nuevo.tamano(); i++) {
			Nbt.Nodo entradaNueva = nuevo.en(i);
			String valor = valorComparable(entradaNueva);

			if (valores.add(valor)) {
				existente.agregar(clonar(entradaNueva));
			}
		}

		return existente;
	}

	private static boolean listaDeObjetosConId(Nbt.Nodo lista) {
		if (lista == null || !lista.esLista()) {
			return false;
		}

		for (int i = 0; i < lista.tamano(); i++) {
			Nbt.Nodo entrada = lista.en(i);

			if (entrada != null && entrada.esObjeto()) {
				String id = obtenerId(entrada);

				if (id != null && !id.trim().isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean listaContieneValor(Nbt.Nodo lista, Nbt.Nodo valor) {
		String buscado = valorComparable(valor);

		for (int i = 0; i < lista.tamano(); i++) {
			if (buscado.equals(valorComparable(lista.en(i)))) {
				return true;
			}
		}

		return false;
	}

	private static String obtenerId(Nbt.Nodo nodo) {
		try {
			if (nodo == null || !nodo.esObjeto()) {
				return "";
			}

			if (!nodo.contiene("id")) {
				return "";
			}

			return nodo.obtener("id").comoCadena();
		} catch (Throwable t) {
			return "";
		}
	}

	private static String valorComparable(Nbt.Nodo nodo) {
		try {
			return Snbt.escribir(nodo);
		} catch (Throwable t) {
			try {
				return nodo.comoCadena();
			} catch (Throwable ignored) {
				return String.valueOf(nodo);
			}
		}
	}

	private static Nbt.Nodo clonar(Nbt.Nodo nodo) {
		if (nodo == null) {
			return null;
		}

		return Snbt.leer(Snbt.escribir(nodo));
	}
}