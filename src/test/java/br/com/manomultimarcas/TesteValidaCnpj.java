package br.com.manomultimarcas;

import br.com.manomultimarcas.util.ValidaCnpj;

public class TesteValidaCnpj {
	
	public static void main(String[] args) {
		
		boolean isCnpj = ValidaCnpj.isCNPJ("16.040.209/0001-19");
		
		System.out.println("CNPJ VALIDO: " + isCnpj);
		
	}
}