package br.com.manomultimarcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.manomultimarcas.model.NotaFiscalCompra;

@Repository
@Transactional
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {
	
	@Query("SELECT notaFisc  FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.numeroNota)) LIKE %?1%")
	List<NotaFiscalCompra> buscaNotaNumNot(String numNota);
	
	@Query("SELECT notaFisc  FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.descricaoObs)) LIKE %?1%")
	List<NotaFiscalCompra> buscaNotaDesc(String desc);
	
	@Query("SELECT notaFisc  FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.pessoa.id)) = ?1")
	List<NotaFiscalCompra> buscaNotaPorPessoa(Long idPessoa);
	
	@Query("SELECT notaFisc  FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.contaPagar.id)) = ?1")
	List<NotaFiscalCompra> buscaNotaPorContaPagar(Long idContaPagar);
	
	@Query("SELECT notaFisc FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.empresa.id)) = ?1")
	List<NotaFiscalCompra> buscaNotaPorEmpresa(Long idEmpresa);
	
	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(nativeQuery = true, value = "DELETE FROM NotaItemProduto WHERE notaFiscalCompraID = ?1")
	void deletarItemNotaFiscalCompra(Long idNotaFiscalCompra);

	@Query("SELECT notaFisc FROM NotaFiscalCompra notaFisc WHERE UPPER(TRIM(notaFisc.descricaoObs))  LIKE %?1% and empresaid = ?2")
	List<NotaFiscalCompra> existeNotaDescObs(String desc, Long id);
	
	@Query(nativeQuery = true, value ="SELECT count(1) > 0 FROM nota_fiscal_compra notaFisc WHERE UPPER(TRIM(notaFisc.descricao_obs))  LIKE %?1%")
	boolean existeNotaDescObs(String desc);

}