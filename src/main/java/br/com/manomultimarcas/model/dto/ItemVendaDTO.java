package br.com.manomultimarcas.model.dto;

import br.com.manomultimarcas.model.Produto;

public class ItemVendaDTO {

    private Produto produto;

    private Double quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
