package com.asbestosstar.crashdetector.bajo.vectorapi;

public interface CDByteVector {

	int indexOf(byte[] datos, byte valor);

	int indexOf(byte[] datos, byte[] patron);

	boolean contiene(byte[] datos, byte valor);

	boolean contiene(byte[] datos, byte[] patron);

	int contar(byte[] datos, byte valor);
}