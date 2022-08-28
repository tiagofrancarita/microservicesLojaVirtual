package br.com.manomultimarcas.enums;

public enum StatusContaPagar {
	
	COBRANÇA("A Pagar"),
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Paga");
	
	private String descricao;
	
	private StatusContaPagar(String descricao) {
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
