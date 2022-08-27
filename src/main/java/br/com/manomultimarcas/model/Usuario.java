package br.com.manomultimarcas.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "Usuario")
@SequenceGenerator(name = "seqUsuario", sequenceName = "seqUsuario", allocationSize = 1,initialValue = 1)
public class Usuario implements UserDetails {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsuario")
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String login;
	
	@Column(nullable = false)
	private String senha;
	
	@ManyToOne(targetEntity = Pessoa.class) //muitos para um
	@JoinColumn (name = "pessoaID",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoaFK") )
	private Pessoa pessoa;
	
	@ManyToOne(targetEntity = Pessoa.class) //muitos para um
	@JoinColumn (name = "empresaid",nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresaidFK") )
	private Pessoa empresa;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataAtualSenha;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuarioAcesso", uniqueConstraints = @UniqueConstraint(columnNames = {"usuarioID", "acessoID"},
	name = "uniqueAcessoUser"),
	joinColumns = @JoinColumn(name="usuarioID", referencedColumnName = "id", table = "usuario", 
	unique = false, foreignKey = @ForeignKey(name="usuarioFK", value = ConstraintMode.CONSTRAINT)),
	inverseJoinColumns = @JoinColumn(name="acessoID",unique = false , referencedColumnName = "id", table = "acesso",
	foreignKey = @ForeignKey(name="acessoFK", value = ConstraintMode.CONSTRAINT)))
	private List<Acesso> acessos;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return this.acessos;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Date getDataAtualSenha() {
		return dataAtualSenha;
	}
	
	public void setDataAtualSenha(Date dataAtualSenha) {
		this.dataAtualSenha = dataAtualSenha;
	}
	
	public Pessoa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Pessoa empresa) {
		this.empresa = empresa;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Acesso> getAcessos() {
		return acessos;
	}

	public void setAcessos(List<Acesso> acessos) {
		this.acessos = acessos;
	}

	@Override
	public String getPassword() {
		
		return this.senha;
	}
	
	@Override
	public String getUsername() {
		
		return this.login;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		
		return true;
	}
}