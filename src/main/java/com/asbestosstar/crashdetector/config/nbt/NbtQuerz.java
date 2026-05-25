package com.asbestosstar.crashdetector.config.nbt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NBTSerializer;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.io.SNBTUtil;
import net.querz.nbt.tag.ByteArrayTag;
import net.querz.nbt.tag.ByteTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.DoubleTag;
import net.querz.nbt.tag.EndTag;
import net.querz.nbt.tag.FloatTag;
import net.querz.nbt.tag.IntArrayTag;
import net.querz.nbt.tag.IntTag;
import net.querz.nbt.tag.ListTag;
import net.querz.nbt.tag.LongArrayTag;
import net.querz.nbt.tag.LongTag;
import net.querz.nbt.tag.NumberTag;
import net.querz.nbt.tag.ShortTag;
import net.querz.nbt.tag.StringTag;
import net.querz.nbt.tag.Tag;

/**
 * Motor NBT basado en Querz.
 *
 * Esta es la unica clase que debe importar net.querz.*.
 */
public class NbtQuerz implements Nbt.Motor {

	@Override
	public String nombre() {
		return "querz-nbt";
	}

	@Override
	public Nbt.Nodo leer(byte[] datos) {
		return leer(datos, false);
	}

	@Override
	public Nbt.Nodo leer(byte[] datos, boolean comprimidoGzip) {
		if (datos == null)
			throw new IllegalArgumentException("datos no puede ser null");

		try {
			ByteArrayInputStream in = new ByteArrayInputStream(datos);
			NamedTag named = new NBTDeserializer(comprimidoGzip).fromStream(in);
			return new Nbt.Nodo(named.getTag(), this);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer NBT", e);
		}
	}

	@Override
	public Nbt.Nodo leerAuto(byte[] datos) {
		if (datos == null)
			throw new IllegalArgumentException("datos no puede ser null");

		try {
			InputStream in = detectarGzip(new ByteArrayInputStream(datos));
			NamedTag named = new NBTDeserializer(false).fromStream(in);
			return new Nbt.Nodo(named.getTag(), this);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer NBT", e);
		}
	}

	@Override
	public Nbt.Nodo leerArchivo(File archivo) {
		if (archivo == null)
			throw new IllegalArgumentException("archivo no puede ser null");

		try {
			return leerAuto(Files.readAllBytes(archivo.toPath()));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer archivo NBT: " + archivo, e);
		}
	}

	@Override
	public byte[] escribirBytes(Nbt.Nodo nodo) {
		return escribirBytes(nodo, true);
	}

	@Override
	public byte[] escribirBytes(Nbt.Nodo nodo, boolean comprimirGzip) {
		if (nodo == null)
			throw new IllegalArgumentException("nodo no puede ser null");

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			new NBTSerializer(comprimirGzip).toStream(new NamedTag("", tag(nodo)), out);
			return out.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo escribir NBT", e);
		}
	}

	@Override
	public void escribirArchivo(Nbt.Nodo nodo, File archivo) {
		escribirArchivo(nodo, archivo, true);
	}

	@Override
	public void escribirArchivo(Nbt.Nodo nodo, File archivo, boolean comprimirGzip) {
		if (archivo == null)
			throw new IllegalArgumentException("archivo no puede ser null");

		try {
			Files.write(archivo.toPath(), escribirBytes(nodo, comprimirGzip));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo escribir archivo NBT: " + archivo, e);
		}
	}

	@Override
	public Nbt.Nodo leerSnbt(String snbt) {
		if (snbt == null)
			throw new IllegalArgumentException("snbt no puede ser null");

		try {
			Tag<?> t = SNBTUtil.fromSNBT(snbt);
			return new Nbt.Nodo(t, this);
		} catch (Exception e) {
			throw new IllegalArgumentException("No se pudo leer SNBT", e);
		}
	}

	@Override
	public String escribirSnbt(Nbt.Nodo nodo) {
		if (nodo == null)
			throw new IllegalArgumentException("nodo no puede ser null");

		try {
			return SNBTUtil.toSNBT(tag(nodo));
		} catch (Exception e) {
			throw new IllegalArgumentException("No se pudo escribir SNBT", e);
		}
	}

	@Override
	public Nbt.Nodo crearObjeto() {
		return new Nbt.Nodo(new CompoundTag(), this);
	}

	@Override
	public Nbt.Nodo crearLista() {
		return new Nbt.Nodo(ListTag.createUnchecked(EndTag.class), this);
	}

	@Override
	public boolean esObjeto(Nbt.Nodo nodo) {
		return tag(nodo) instanceof CompoundTag;
	}

	@Override
	public boolean esLista(Nbt.Nodo nodo) {
		return tag(nodo) instanceof ListTag;
	}

	@Override
	public boolean esNumero(Nbt.Nodo nodo) {
		return tag(nodo) instanceof NumberTag;
	}

	@Override
	public boolean esCadena(Nbt.Nodo nodo) {
		return tag(nodo) instanceof StringTag;
	}

	@Override
	public boolean esByteArray(Nbt.Nodo nodo) {
		return tag(nodo) instanceof ByteArrayTag;
	}

	@Override
	public boolean esIntArray(Nbt.Nodo nodo) {
		return tag(nodo) instanceof IntArrayTag;
	}

	@Override
	public boolean esLongArray(Nbt.Nodo nodo) {
		return tag(nodo) instanceof LongArrayTag;
	}

	@Override
	public List<String> claves(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (!(t instanceof CompoundTag))
			return new ArrayList<String>();

		List<String> salida = new ArrayList<String>();
		CompoundTag c = (CompoundTag) t;

		for (Map.Entry<String, Tag<?>> e : c) {
			salida.add(e.getKey());
		}

		return salida;
	}

	@Override
	public Nbt.Nodo obtener(Nbt.Nodo actual, String clave) {
		CompoundTag c = asegurarObjeto(actual);

		Tag<?> hijo = c.get(clave);
		if (hijo == null) {
			hijo = new CompoundTag();
			c.put(clave, hijo);
		}

		return new Nbt.Nodo(hijo, this, c, clave, null);
	}

	@Override
	public boolean contiene(Nbt.Nodo actual, String clave) {
		Tag<?> t = tag(actual);
		if (!(t instanceof CompoundTag))
			return false;

		return ((CompoundTag) t).containsKey(clave);
	}

	@Override
	public boolean eliminar(Nbt.Nodo actual, String clave) {
		Tag<?> t = tag(actual);
		if (!(t instanceof CompoundTag))
			throw new IllegalStateException("No es CompoundTag");

		CompoundTag c = (CompoundTag) t;
		boolean existia = c.containsKey(clave);
		c.remove(clave);
		return existia;
	}

	@Override
	public Nbt.Nodo poner(Nbt.Nodo actual, Object valor) {
		Tag<?> nuevo = convertirValor(valor);
		reemplazarActual(actual, nuevo);
		return actual;
	}

	@Override
	public Nbt.Nodo agregar(Nbt.Nodo actual, Object valor) {
		ListTag<?> listaBase = asegurarLista(actual);
		Tag<?> nuevo = convertirValor(valor);

		@SuppressWarnings("unchecked")
		ListTag<Tag<?>> lista = (ListTag<Tag<?>>) listaBase;

		try {
			lista.addUnchecked(nuevo);
		} catch (IllegalArgumentException e) {
			throw new IllegalStateException(
					"No se puede agregar " + nuevo.getClass().getSimpleName() + " a esta lista NBT", e);
		}

		return new Nbt.Nodo(nuevo, this, lista, null, lista.size() - 1);
	}

	@Override
	public Nbt.Nodo agregarNodo(Nbt.Nodo actual, Nbt.Nodo valor) {
		return agregar(actual, valor);
	}

	@Override
	public Nbt.Nodo convertirEnObjeto(Nbt.Nodo actual) {
		reemplazarActual(actual, new CompoundTag());
		return actual;
	}

	@Override
	public Nbt.Nodo convertirEnLista(Nbt.Nodo actual) {
		reemplazarActual(actual, ListTag.createUnchecked(EndTag.class));
		return actual;
	}

	@Override
	public int tamano(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);

		if (t instanceof ListTag)
			return ((ListTag<?>) t).size();

		if (t instanceof CompoundTag)
			return claves(nodo).size();

		return 0;
	}

	@Override
	public Nbt.Nodo en(Nbt.Nodo nodo, int indice) {
		Tag<?> t = tag(nodo);
		if (!(t instanceof ListTag))
			throw new IllegalStateException("No es ListTag");

		ListTag<?> lista = (ListTag<?>) t;
		Tag<?> hijo = lista.get(indice);

		return new Nbt.Nodo(hijo, this, lista, null, indice);
	}

	@Override
	public String comoCadena(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);

		if (t instanceof StringTag)
			return ((StringTag) t).getValue();

		if (t instanceof ByteTag) {
			byte b = ((ByteTag) t).asByte();
			if (b == 0)
				return "false";
			if (b == 1)
				return "true";
			return String.valueOf(b);
		}

		if (t instanceof ShortTag)
			return String.valueOf(((ShortTag) t).asShort());

		if (t instanceof IntTag)
			return String.valueOf(((IntTag) t).asInt());

		if (t instanceof LongTag)
			return String.valueOf(((LongTag) t).asLong());

		if (t instanceof FloatTag)
			return String.valueOf(((FloatTag) t).asFloat());

		if (t instanceof DoubleTag)
			return String.valueOf(((DoubleTag) t).asDouble());

		return escribirSnbt(nodo);
	}

	@Override
	public byte comoByte(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asByte();
		return Byte.parseByte(comoCadena(nodo));
	}

	@Override
	public short comoShort(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asShort();
		return Short.parseShort(comoCadena(nodo));
	}

	@Override
	public int comoEntero(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asInt();
		return Integer.parseInt(comoCadena(nodo));
	}

	@Override
	public long comoLargo(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asLong();
		return Long.parseLong(comoCadena(nodo));
	}

	@Override
	public float comoFloat(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asFloat();
		return Float.parseFloat(comoCadena(nodo));
	}

	@Override
	public double comoDouble(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof NumberTag)
			return ((NumberTag<?>) t).asDouble();
		return Double.parseDouble(comoCadena(nodo));
	}

	@Override
	public boolean comoBooleano(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof ByteTag)
			return ((ByteTag) t).asByte() != 0;
		return Boolean.parseBoolean(comoCadena(nodo));
	}

	@Override
	public byte[] comoByteArray(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (!(t instanceof ByteArrayTag))
			throw new IllegalStateException("No es ByteArrayTag");
		return ((ByteArrayTag) t).getValue();
	}

	@Override
	public int[] comoIntArray(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (!(t instanceof IntArrayTag))
			throw new IllegalStateException("No es IntArrayTag");
		return ((IntArrayTag) t).getValue();
	}

	@Override
	public long[] comoLongArray(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (!(t instanceof LongArrayTag))
			throw new IllegalStateException("No es LongArrayTag");
		return ((LongArrayTag) t).getValue();
	}

	private Tag<?> tag(Nbt.Nodo nodo) {
		if (nodo == null || !(nodo.interno instanceof Tag))
			throw new IllegalArgumentException("El nodo no pertenece al motor Querz NBT");
		return (Tag<?>) nodo.interno;
	}

	private CompoundTag asegurarObjeto(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof CompoundTag)
			return (CompoundTag) t;

		CompoundTag nuevo = new CompoundTag();
		reemplazarActual(nodo, nuevo);
		return nuevo;
	}

	private ListTag<?> asegurarLista(Nbt.Nodo nodo) {
		Tag<?> t = tag(nodo);
		if (t instanceof ListTag)
			return (ListTag<?>) t;

		ListTag<?> nuevo = ListTag.createUnchecked(EndTag.class);
		reemplazarActual(nodo, nuevo);
		return nuevo;
	}

	@SuppressWarnings("unchecked")
	private void reemplazarActual(Nbt.Nodo nodo, Tag<?> nuevo) {
		if (nodo.padre instanceof CompoundTag && nodo.claveEnPadre != null) {
			((CompoundTag) nodo.padre).put(nodo.claveEnPadre, nuevo);
		} else if (nodo.padre instanceof ListTag && nodo.indiceEnPadre != null) {
			ListTag<Tag<?>> lista = (ListTag<Tag<?>>) nodo.padre;
			lista.set(nodo.indiceEnPadre.intValue(), nuevo);
		}

		nodo.interno = nuevo;
	}

	private Tag<?> convertirValor(Object valor) {
		if (valor == null)
			return new StringTag("");

		if (valor instanceof Nbt.Nodo)
			return tag((Nbt.Nodo) valor).clone();

		if (valor instanceof Tag)
			return ((Tag<?>) valor).clone();

		if (valor instanceof Boolean)
			return new ByteTag(((Boolean) valor).booleanValue());

		if (valor instanceof Byte)
			return new ByteTag(((Byte) valor).byteValue());

		if (valor instanceof Short)
			return new ShortTag(((Short) valor).shortValue());

		if (valor instanceof Integer)
			return new IntTag(((Integer) valor).intValue());

		if (valor instanceof Long)
			return new LongTag(((Long) valor).longValue());

		if (valor instanceof Float)
			return new FloatTag(((Float) valor).floatValue());

		if (valor instanceof Double)
			return new DoubleTag(((Double) valor).doubleValue());

		if (valor instanceof byte[])
			return new ByteArrayTag((byte[]) valor);

		if (valor instanceof int[])
			return new IntArrayTag((int[]) valor);

		if (valor instanceof long[])
			return new LongArrayTag((long[]) valor);

		if (valor instanceof Number)
			return new DoubleTag(((Number) valor).doubleValue());

		return new StringTag(String.valueOf(valor));
	}

	private static InputStream detectarGzip(InputStream entrada) throws IOException {
		PushbackInputStream pb = new PushbackInputStream(entrada, 2);

		int b1 = pb.read();
		int b2 = pb.read();

		if (b2 != -1)
			pb.unread(b2);
		if (b1 != -1)
			pb.unread(b1);

		if (b1 == 0x1F && b2 == 0x8B)
			return new GZIPInputStream(pb);

		return pb;
	}
}