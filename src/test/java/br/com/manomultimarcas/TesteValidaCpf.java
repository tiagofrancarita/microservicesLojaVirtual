package br.com.manomultimarcas;

import br.com.manomultimarcas.util.ValidaCpf;

public class TesteValidaCpf {
	
public static void main(String[] args) {
		
		boolean isCpf = ValidaCpf.isCPF("645.714.720-80");
		
		System.out.println("CPF VALIDO: " + isCpf);
		
	}
}