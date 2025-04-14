package com.asbestosstar.crashdetectormc.buscar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//TODO Mods de Carpetas
public interface ArchivoDeMod {

	public static ArchivoDeMod origin = new Origin();

	public void establecerDesde();

	public ArchivoDeMod obtenerDesde();

	public List<ArchivoDeMod> mods_en_mods();
	
	public List<String> obtenerArchivos();
	
	public InputStream obtenerStream(String arcivo) throws FileNotFoundException;
	
	public String nombre();
	
	public String ubicacion();

	class Origin implements ArchivoDeMod {

		@Override
		public void establecerDesde() {
			// TODO Auto-generated method stub

		}

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
		public List<String> obtenerArchivos() {
			// TODO Auto-generated method stub
			return new ArrayList<>();
		}

		@Override
		public InputStream obtenerStream(String arcivo) throws FileNotFoundException {
			// TODO Auto-generated method stub
			throw new FileNotFoundException();
		}

		@Override
		public String nombre() {
			// TODO Auto-generated method stub
			return "";
		}

		@Override
		public String ubicacion() {
			// TODO Auto-generated method stub
			return "";
		}

	}

}
