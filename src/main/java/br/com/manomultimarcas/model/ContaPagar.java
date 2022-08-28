package br.com.manomultimarcas.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import br.com.manomultimarcas.enums.StatusContaPagar;

@Entity
@Table(name = "ContaPagar")
@SequenceGenerator(name = "seqContaPagar", sequenceName = "seqContaPagar", allocationSize = 1,initialValue = 1)
public class ContaPagar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqContaPagar")
	private Long id;
	
	@ManyToOne(targetEntity = PessoaFisica.class) //muitos para um
	@JoinColumn (name = "pessoaID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoaFK") )
	private PessoaFisica pessoa;
	
	@ManyToOne(targetEntity = PessoaJuridica.class) //muitos para um
	@JoinColumn (name = "pessoaFornecedorID", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoaFornecedorFK") )
	private PessoaJuridica pessoaFornecedor;
	
	@ManyToOne(targetEntity = PessoaJuridica.class) //muitos para um
	@JoinColumn (name = "empresaid",nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresaidFK") )
	private PessoaJuridica empresa;
	
	@NotNull(message = "Favor informar o campo status da conta a pagar")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusContaPagar statusContaPagar;
	
	@NotNull(message = "Favor informar o campo descrição da conta a pagar.")
	@Column(nullable = false)
	private String descricao;
	
	@NotNull(message = "Favor informar a data de vencimento da conta a pagar.")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dtVencimento;
	
	@Temporal(TemporalType.DATE)
	private Date dtPagamento;
	
	@NotNull(message = "Favor informar o valor total da conta a pagar.")
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	private BigDecimal valorDesconto;
	
	public PessoaJuridica getEmpresa() {
		return empresa;
	}

	public void setEmpresa(PessoaJuridica empresa) {
		this.empresa = empresa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
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

	public Long getId() {
		return id;
	}

	public PessoaFisica getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaFisica pessoa) {
		this.pessoa = pessoa;
	}
	public StatusContaPagar getStatusContaPagar() {
		return statusContaPagar;
	}
	public void setStatusContaPagar(StatusContaPagar statusContaPagar) {
		this.statusContaPagar = statusContaPagar;
	}
	
	public PessoaJuridica getPessoaFornecedor() {
		return pessoaFornecedor;
	}

	public void setPessoaFornecedor(PessoaJuridica pessoaFornecedor) {
		this.pessoaFornecedor = pessoaFornecedor;
	}

	public void setId(Long id) {
		this.id = id;
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
		ContaPagar other = (ContaPagar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
