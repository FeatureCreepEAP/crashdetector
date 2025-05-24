package com.asbestosstar.crashdetector.buscar;

import java.util.ArrayList;
import java.util.List;

//TODO Mods de Carpetas
public interface ArchivoDeMod {

	public static ArchivoDeMod origin = new Origin();

	public ArchivoDeMod obtenerDesde();

	public List<ArchivoDeMod> mods_en_mods();
			
	public List<String> nombre();
	
	public String ubicacion();
	
	public List<String> clases();
	
	public boolean tieneNombreRecursivo(String nombre);
	
	public String obtenerNombreRecursivo(String nombre);
	
	public boolean tieneArchivoRecursivo(String archivo);
	
	public String obtenerArchivoRecursivo(String archivo);
	
	public List<String> archivos();
	
    /**
     * Busca recursivamente mods que contengan el archivo, clase o paquete especificado.
     * @param termino Nombre del archivo, clase o paquete a buscar
     * @return Lista de mods que contienen el elemento buscado
     */
    public List<ArchivoDeMod> buscarModsCon(String termino);


	class Origin implements ArchivoDeMod {

		@Override
		public ArchivoDeMod obtenerDesde() {
			// TODO Auto-generated method stub
			return origin;
		}

		@Override
		public List<ArchivoDeMod> mods_en_mods() {
			// TODO Auto-generated method stub
			return new ArrayList<>();
		}

		@Override
		public List<String> nombre() {
			// TODO Auto-generated method stub
			return new ArrayList<String>();
		}

		@Override
		public String ubicacion() {
			// TODO Auto-generated method stub
			return "";
		}

		@Override
		public List<String> clases() {
			// TODO Auto-generated method stub
			return new ArrayList<String>();
		}

		@Override
		public boolean tieneNombreRecursivo(String nombre) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String obtenerNombreRecursivo(String nombre) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean tieneArchivoRecursivo(String paquete) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String obtenerArchivoRecursivo(String paquete) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> archivos() {
			// TODO Auto-generated method stub
			return new ArrayList<String>();
		}

		@Override
		public List<ArchivoDeMod> buscarModsCon(String termino) {
			// TODO Auto-generated method stub
			return new ArrayList<ArchivoDeMod>();
		}

	}

}
