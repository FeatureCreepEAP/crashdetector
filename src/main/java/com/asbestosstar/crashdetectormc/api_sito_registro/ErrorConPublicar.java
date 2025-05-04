package com.asbestosstar.crashdetectormc.api_sito_registro;

public class ErrorConPublicar extends Exception {

	public String problema;
	
	public ErrorConPublicar(String problema) {
		this.problema=problema;
	}
	
	
}
