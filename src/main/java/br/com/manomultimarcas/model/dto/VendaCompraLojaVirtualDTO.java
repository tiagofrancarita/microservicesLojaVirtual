package br.com.manomultimarcas.model.dto;


import java.math.BigDecimal;

public class VendaCompraLojaVirtualDTO {


    private BigDecimal valorTotal;

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
