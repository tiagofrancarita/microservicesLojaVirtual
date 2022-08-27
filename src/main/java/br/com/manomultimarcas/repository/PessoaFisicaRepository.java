package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.PessoaFisica;

@Repository
@Transactional
public interface PessoaFisicaRepository extends CrudRepository<PessoaFisica, Long> {
	
	@Query("SELECT pf from PessoaFisica pf WHERE pf.cpf = ?1")
	public PessoaFisica cpfExistente(String cpf);
	
	@Query("SELECT pf from PessoaFisica pf WHERE pf.cpf = ?1")
	public List<PessoaFisica> listaCpfCadastrados(String cpf);
	
	@Query("SELECT pf from PessoaFisica pf WHERE upper(trim(pf.nome)) like %?1%")
	public List<PessoaFisica> pesquisaPorNomePf(String nome);
	
	@Query("SELECT pf from PessoaFisica pf WHERE pf.cpf = ?1 ")
	public List<PessoaFisica> pesquisaPorCpfPf(String cpf);

}