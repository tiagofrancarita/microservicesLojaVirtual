package br.com.manomultimarcas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "PessoaJuridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Pessoa {
	
	private static final long serialVersionUID = 1L;
	
	@CNPJ(message = "O cnpj informado é inválido")
	@Column(nullable = false)
	private String cnpj;
	
	@NotBlank(message = "O campo inscrição Estadual deve ser informado!")
	@NotNull(message = "O campo inscrição Estadual deve ser informado!")
	@Column(nullable = false)
	private String inscEstadual;
	
	@NotBlank(message = "O campo inscrição munincipal deve ser informado!")
	@NotNull(message = "O campo inscrição munincipal deve ser informado!")
	@Column(nullable = false)
	private String inscMunincipal;
	
	@NotBlank(message = "O campo nome fantasia deve ser informado!")
	@NotNull(message = "O campo nome fantasia deve ser informado!")
	@Column(nullable = false)
	private String nomeFantasia;
	
	@NotBlank(message = "O campo razão social deve ser informado!")
	@NotNull(message = "O campo razão social deve ser informado!")
	@Column(nullable = false)
	private String razaoSocial;
	
	@Column
	private String categoria;
	
	public String getCnpj() {
		
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	public String getInscEstadual() {
		return inscEstadual;
	}
	
	public void setInscEstadual(String inscEstadual) {
		this.inscEstadual = inscEstadual;
	}
	
	public String getInscMunincipal() {
		return inscMunincipal;
	}
	
	public void setInscMunincipal(String inscMunincipal) {
		this.inscMunincipal = inscMunincipal;
	}
	
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	
	public String getRazaoSocial() {
		return razaoSocial;
	}
	
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
