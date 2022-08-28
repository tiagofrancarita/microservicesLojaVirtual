package br.com.manomultimarcas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vdCpLojaVirtual")
@SequenceGenerator(name = "seqvdCpLojaVirtual", sequenceName = "seqvdCpLojaVirtual", allocationSize = 1, initialValue = 1)
public class VendaCompraLojaVirtual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqvdCpLojaVirtual")
    private Long id;

    @NotNull(message = "O comprador deve ser informado")
    @ManyToOne(targetEntity = PessoaFisica.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoaFK"))
    private PessoaFisica pessoa;

    @NotNull(message = "O endereço de entrega deve ser informado")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EnderecoEntregaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "enderecoEntregaFK"))
    private Endereco enderecoEntrega;

    @NotNull(message = "O endereço de cobrança deve ser informado")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EnderecoCobrancaID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "EnderecoCobrancaFK"))
    private Endereco enderecoCobranca;

    @NotNull(message = "A forma de pagamento deve ser informada")
    @ManyToOne
    @JoinColumn(name = "FormaPagamentoID", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "FormaPagamentoFK"))
    private FormaPagamento formaPagamento;

    @JsonIgnoreProperties(allowGetters = true)
    @NotNull(message = "O número da nota fiscal deve ser informado.")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notaFiscalVendaID", nullable = true,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "notaFiscalVendaFK"))
    private NotaFiscalVenda notaFiscalVenda;

    @ManyToOne
    @JoinColumn(name = "cupom_desc_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cupom_desc_fk"))
    private CupomDesconto cupomDesconto;

    @NotNull(message = "O valor do frete deve ser informado. Frete minimo R$ 10,00")
    @Range(min = 10, message = "Valor do frete inválido!.")
    @Column(nullable = false)
    private BigDecimal valorFrete;

    @Range(min = 1, message = "Valor inválido")
    @Column(nullable = false)
    private BigDecimal valorTotal;

    private BigDecimal valorDesconto;

    @Range(min = 1, message = "Dia de entrega é inválido, favor verifique!.")
    @Column(nullable = false)
    private Integer diasEntrega;

    @NotNull(message = "A data da venda deve ser informada")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dtVenda;

    @NotNull(message = "A data da entrega deve ser informada")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dtEntrega;

    @NotNull(message = "Empresa deve ser informada.")
    @ManyToOne(targetEntity = PessoaJuridica.class)
    @JoinColumn(name = "empresaid", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresaidFK"))
    private PessoaJuridica empresa;

    public PessoaJuridica getEmpresa() {

    	return empresa;
    }

    public void setEmpresa(PessoaJuridica empresa) {

    	this.empresa = empresa;
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

    public NotaFiscalVenda getNotaFiscalVenda() {
        return notaFiscalVenda;
    }

    public void setNotaFiscalVenda(NotaFiscalVenda notaFiscalVenda) {
        this.notaFiscalVenda = notaFiscalVenda;
    }

    public CupomDesconto getCupomDesconto() {
        return cupomDesconto;
    }

    public void setCupomDesconto(CupomDesconto cupomDesconto) {
        this.cupomDesconto = cupomDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public Integer getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(Integer diasEntrega) {
        this.diasEntrega = diasEntrega;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VendaCompraLojaVirtual other = (VendaCompraLojaVirtual) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}