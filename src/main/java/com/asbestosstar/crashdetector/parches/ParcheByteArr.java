package com.asbestosstar.crashdetector.parches;

public interface ParcheByteArr extends Parche<byte[]> {

	@Override
	public default Class<byte[]> tipo() {
		return byte[].class;
	}

}
