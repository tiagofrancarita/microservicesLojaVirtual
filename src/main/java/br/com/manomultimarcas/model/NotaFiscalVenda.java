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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NotaFiscalVenda")
@SequenceGenerator(name = "seqStatusNotaFiscalVenda", sequenceName = "seqStatusNotaFiscalVenda", allocationSize = 1,initialValue = 1)
public class NotaFiscalVenda implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqStatusNotaFiscalVenda")
	private Long id;
	
	@Column(nullable = false)
	private String numeroNota;
	
	@Column(nullable = false)
	private String serieNota;
	
	@Column(nullable = false)
	private String tipoNota;
	
	@Column(columnDefinition = "text", nullable = false) //Coluna de texto sem limite
	private String notaXML;
	
	@Column(columnDefinition = "text", nullable = false)//Coluna de texto sem limite
	private String notaPDF;
	
	@OneToOne
	@JoinColumn (name = "vendaCompraLojaVirtualID", nullable = true,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "vendaCompraLojaVirtualvendaCompraLojaVirtualFK"))
	private VendaCompraLojaVirtual vendacompralojavirtual;
	
	@ManyToOne(targetEntity = PessoaJuridica.class) //muitos para um
	@JoinColumn (name = "empresaid",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresaidFK") )
	private PessoaJuridica empresa;
	
	public VendaCompraLojaVirtual getVendacompralojavirtual() {
		return vendacompralojavirtual;
	}

	public void setVendacompralojavirtual(VendaCompraLojaVirtual vendacompralojavirtual) {
		this.vendacompralojavirtual = vendacompralojavirtual;
	}

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

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public String getSerieNota() {
		return serieNota;
	}

	public void setSerieNota(String serieNota) {
		this.serieNota = serieNota;
	}

	public String getTipoNota() {
		return tipoNota;
	}

	public void setTipoNota(String tipoNota) {
		this.tipoNota = tipoNota;
	}

	public String getNotaXML() {
		return notaXML;
	}

	public void setNotaXML(String notaXML) {
		this.notaXML = notaXML;
	}

	public String getNotaPDF() {
		return notaPDF;
	}

	public void setNotaPDF(String notaPDF) {
		this.notaPDF = notaPDF;
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
		NotaFiscalVenda other = (NotaFiscalVenda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
