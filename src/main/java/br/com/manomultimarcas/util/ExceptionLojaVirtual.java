package br.com.manomultimarcas.util;

public class ExceptionLojaVirtual extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExceptionLojaVirtual(String msgErro) {
		super(msgErro);
	}
}