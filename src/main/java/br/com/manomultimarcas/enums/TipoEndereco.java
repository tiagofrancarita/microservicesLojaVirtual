package br.com.manomultimarcas.enums;

public enum TipoEndereco {
	
	COBRANCA ("Cobrança"),
	ENTREGA ("Entrega");
	
	private String descricao;
	
	TipoEndereco(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
