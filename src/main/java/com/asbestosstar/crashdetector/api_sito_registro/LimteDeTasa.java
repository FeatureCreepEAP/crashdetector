package com.asbestosstar.crashdetector.api_sito_registro;

public class LimteDeTasa extends Exception {

	public String problema;

	public LimteDeTasa(String problema) {
		this.problema = problema;
	}

}
