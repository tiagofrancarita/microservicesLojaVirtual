package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.PessoaJuridica;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<PessoaJuridica, Long> {

	@Query("SELECT pj from PessoaJuridica pj WHERE pj.cnpj = ?1")
	public PessoaJuridica cnpjExistente(String cnpj);
	
	@Query("SELECT pj from PessoaJuridica pj WHERE pj.inscEstadual = ?1")
	public PessoaJuridica insEstadualExistente(String inscEstadual);
	
	@Query("SELECT pj from PessoaJuridica pj WHERE pj.inscMunincipal = ?1")
	public PessoaJuridica insMunincipalExistente(String inscMunincipal);
	
	@Query("SELECT pj from PessoaJuridica pj WHERE upper(trim(pj.nome)) like %?1%")
	public List<PessoaJuridica> pesquisaPorNomePj(String nome);
	
	@Query("SELECT pj from PessoaJuridica pj WHERE pj.cnpj = ?1 ")
	public List<PessoaJuridica> consultaCnpj(String cnpj);

}