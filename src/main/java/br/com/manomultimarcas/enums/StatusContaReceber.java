package br.com.manomultimarcas.enums;

public enum StatusContaReceber {
	
	COBRANÇA("A Pagar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Paga");
	
	private String descricao;
	
	private StatusContaReceber(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	@Override
	public String toString() {
		return this.descricao;
	}

}
