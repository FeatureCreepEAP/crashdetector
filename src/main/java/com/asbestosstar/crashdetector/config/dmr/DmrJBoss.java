package com.asbestosstar.crashdetector.config.dmr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.jboss.dmr.ModelNode;

import com.asbestosstar.crashdetector.config.json.Json;
import com.asbestosstar.crashdetector.config.json.JsonDMR;

/**
 * Motor DMR basado en JBoss DMR. Reutiliza casi todo JsonDMR, pero cambia
 * lectura/escritura principal a DMR.
 */
public class DmrJBoss extends JsonDMR implements Dmr.Motor {

	@Override
	public String nombre() {
		return "jboss-dmr";
	}

	/**
	 * Lee texto DMR. Si falla, intenta JSON como respaldo.
	 */
	@Override
	public Json.Nodo leer(String textoDmr) {
		try {
			ModelNode nodo = ModelNode.fromString(textoDmr);
			return new Json.Nodo(nodo, this);
		} catch (Throwable t) {
			try {
				ModelNode nodo = ModelNode.fromJSONString(textoDmr);
				return new Json.Nodo(nodo, this);
			} catch (Throwable t2) {
				throw new IllegalStateException("No se pudo leer texto DMR ni JSON DMR", t2);
			}
		}
	}

	/**
	 * Lee JSON usando el parser JSON de JBoss DMR.
	 */
	@Override
	public Json.Nodo leerJson(String json) {
		try {
			ModelNode nodo = ModelNode.fromJSONString(json);
			return new Json.Nodo(nodo, this);
		} catch (Throwable t) {
			throw new IllegalStateException("No se pudo leer JSON DMR", t);
		}
	}

	/**
	 * Lee DMR binario.
	 */
	@Override
	public Json.Nodo leerBytes(byte[] bytes) {
		try {
			ByteArrayInputStream entrada = new ByteArrayInputStream(bytes);
			ModelNode nodo = ModelNode.fromStream(entrada);
			return new Json.Nodo(nodo, this);
		} catch (Throwable t) {
			throw new IllegalStateException("No se pudo leer DMR binario", t);
		}
	}

	/**
	 * Escritura principal: texto DMR, no JSON.
	 */
	@Override
	public String escribir(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).toString();
	}

	/**
	 * Escritura JSON DMR.
	 */
	@Override
	public String escribirJson(Json.Nodo nodo) {
		return ((ModelNode) nodo.interno).toJSONString(true);
	}

	/**
	 * Escritura DMR binaria.
	 */
	@Override
	public byte[] escribirBytes(Json.Nodo nodo) {
		try {
			ByteArrayOutputStream salida = new ByteArrayOutputStream();
			((ModelNode) nodo.interno).writeExternal(salida);
			return salida.toByteArray();
		} catch (Throwable t) {
			throw new IllegalStateException("No se pudo escribir DMR binario", t);
		}
	}
}