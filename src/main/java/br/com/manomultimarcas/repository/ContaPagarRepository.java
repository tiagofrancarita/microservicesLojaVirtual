package br.com.manomultimarcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.ContaPagar;

@Repository
@Transactional
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	@Query("SELECT contPagar from ContaPagar contPagar WHERE UPPER(trim(contPagar.descricao)) LIKE %?1%")
	List<ContaPagar> buscarContasDesc(String desc);
	
	@Query("SELECT contPagar from ContaPagar contPagar WHERE UPPER(trim(contPagar.pessoa.id)) = ?1")
	List<ContaPagar> buscarContasPessoa(Long idPessoa);
	
	@Query("SELECT contPagar from ContaPagar contPagar WHERE UPPER(trim(contPagar.pessoaFornecedor.id)) = ?1")
	List<ContaPagar> buscarContasFornecedor(Long idFornecedor);
	
	@Query("SELECT contPagar from ContaPagar contPagar WHERE UPPER(trim(contPagar.empresa.id)) = ?1")
	List<ContaPagar> buscarContasEmpresa(Long idEmpresa);
	
	@Query("SELECT contPagar from ContaPagar contPagar WHERE UPPER(trim(contPagar.descricao)) LIKE %?1% and empresaid = ?2 ")
	List<ContaPagar> existeContaPagar(String desc, Long idEmpresa);

}
