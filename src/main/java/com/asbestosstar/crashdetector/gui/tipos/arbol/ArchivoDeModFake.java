package com.asbestosstar.crashdetector.gui.tipos.arbol;

import java.util.ArrayList;
import java.util.List;

import com.asbestosstar.crashdetector.buscar.ArchivoDeMod;
import com.asbestosstar.crashdetector.cargador.Cargador;

/**
 * Implementación mínima de ArchivoDeMod solo para mostrar la ubicación en
 * resultados.
 */
public class ArchivoDeModFake implements ArchivoDeMod {
	private final String ubicacion;

	public ArchivoDeModFake(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public ArchivoDeMod obtenerDesde() {
		return origin;
	}

	@Override
	public List<ArchivoDeMod> mods_en_mods() {
		return new ArrayList<>();
	}

	@Override
	public List<String> nombre() {
		return new ArrayList<>();
	}

	@Override
	public String ubicacion() {
		return ubicacion;
	}

	@Override
	public List<String> clases() {
		return new ArrayList<>();
	}

	@Override
	public boolean tieneNombreRecursivo(String nombre) {
		return false;
	}

	@Override
	public String obtenerNombreRecursivo(String nombre) {
		return null;
	}

	@Override
	public boolean tieneArchivoRecursivo(String archivo) {
		return false;
	}

	@Override
	public String obtenerArchivoRecursivo(String archivo) {
		return null;
	}

	@Override
	public List<String> archivos() {
		return new ArrayList<>();
	}

	@Override
	public List<ArchivoDeMod> buscarModsCon(String termino) {
		return new ArrayList<>();
	}

	@Override
	public boolean existeClase(String nombreClase) {
		return false;
	}

	@Override
	public byte[] obtenerBytesClase(String nombreClase) {
		return null;
	}

	@Override
	public List<String> obtenerTodosLosNombresDeClases() {
		return new ArrayList<>();
	}

	@Override
	public List<Cargador> cargadores() {
		return new ArrayList<>();
	}

	@Override
	public int precargarTodasLasClasesRecursivo() {
		return 0;
	}
}
