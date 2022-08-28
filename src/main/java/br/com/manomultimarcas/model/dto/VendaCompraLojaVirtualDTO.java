package br.com.manomultimarcas.model.dto;


import br.com.manomultimarcas.model.Endereco;
import br.com.manomultimarcas.model.FormaPagamento;
import br.com.manomultimarcas.model.PessoaFisica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaCompraLojaVirtualDTO {


    private BigDecimal valorTotal;

    private Long id;

    private PessoaFisica pessoa;

    private Endereco enderecoEntrega;

    private Endereco enderecoCobranca;

    private FormaPagamento formaPagamento;

    private BigDecimal valorFrete;

    private Date dtVenda;

    private Date dtEntrega;

    private BigDecimal valorDesconto;

    private List<ItemVendaDTO> ItemVendaLoja  = new ArrayList<ItemVendaDTO>();

    public List<ItemVendaDTO> getItemVendaLoja() {
        return ItemVendaLoja;
    }

    public void setItemVendaLoja(List<ItemVendaDTO> itemVendaLoja) {
        ItemVendaLoja = itemVendaLoja;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PessoaFisica getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaFisica pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Endereco getEnderecoCobranca() {
        return enderecoCobranca;
    }

    public void setEnderecoCobranca(Endereco enderecoCobranca) {
        this.enderecoCobranca = enderecoCobranca;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Date getDtVenda() {
        return dtVenda;
    }

    public void setDtVenda(Date dtVenda) {
        this.dtVenda = dtVenda;
    }

    public Date getDtEntrega() {
        return dtEntrega;
    }

    public void setDtEntrega(Date dtEntrega) {
        this.dtEntrega = dtEntrega;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
