package br.com.manomultimarcas.model;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "AvaliacaoProduto")
@SequenceGenerator(name = "seqAvaliacaoProduto", sequenceName = "seqAvaliacaoProduto", allocationSize = 1,initialValue = 1)
public class AvaliacaoProduto implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAvaliacaoProduto")
	private Long id;

	@Range(min = 0, max = 10, message = "Informe a nota do produto.")
	@Column(nullable = false)
	private Integer nota;
	
	@Column(nullable = false)
	private String descricao;
	
	@ManyToOne
	@JoinColumn (name = "produtoID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produtoFK"))
	private Produto produto;
	
	@ManyToOne(targetEntity = PessoaFisica.class) //muitos para um
	@JoinColumn (name = "pessoaID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoaFK") )
	private PessoaFisica pessoa;
	
	@ManyToOne(targetEntity = PessoaJuridica.class) //muitos para um
	@JoinColumn (name = "empresaid",nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresaidFK") )
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

	public Integer getNota() {

		return nota;
	}

	public void setNota(Integer nota) {

		this.nota = nota;
	}

	public String getDescricao() {

		return descricao;
	}

	public void setDescricao(String descricao) {

		this.descricao = descricao;
	}

	public Produto getProduto() {

		return produto;
	}

	public void setProduto(Produto produto) {

		this.produto = produto;
	}

	public PessoaFisica getPessoa() {

		return pessoa;
	}

	public void setPessoa(PessoaFisica pessoa) {

		this.pessoa = pessoa;
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
		AvaliacaoProduto other = (AvaliacaoProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}