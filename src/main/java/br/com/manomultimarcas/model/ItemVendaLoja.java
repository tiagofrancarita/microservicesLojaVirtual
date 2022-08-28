package br.com.manomultimarcas.model;

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

@Entity
@Table(name = "itemVendaLoja")
@SequenceGenerator(name = "seqitemVendaLoja", sequenceName = "seqitemVendaLoja", allocationSize = 1,initialValue = 1)
public class ItemVendaLoja implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqitemVendaLoja")
	private Long id;
	
	@ManyToOne
	@JoinColumn (name = "produtoID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produtoFK"))
	private Produto produto;
	
	@ManyToOne
	@JoinColumn (name = "vendaCompraLojaVirtualID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "vendaCompraLojaVirtualFK"))
	private VendaCompraLojaVirtual vendacompralojavirtual;
	
	@Column(nullable = false)
	private Double quantidade;
	
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

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public VendaCompraLojaVirtual getVendacompralojavirtual() {
		return vendacompralojavirtual;
	}

	public void setVendacompralojavirtual(VendaCompraLojaVirtual vendacompralojavirtual) {
		this.vendacompralojavirtual = vendacompralojavirtual;
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
		ItemVendaLoja other = (ItemVendaLoja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}