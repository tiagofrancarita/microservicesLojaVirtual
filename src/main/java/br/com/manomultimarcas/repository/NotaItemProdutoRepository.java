package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.manomultimarcas.model.NotaItemProduto;

@Repository
@Transactional
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	@Query("SELECT ntItemProd from NotaItemProduto ntItemProd WHERE ntItemProd.id = ?1 and ntItemProd.notaFiscalCompra.id = ?2")
	List<NotaItemProduto> buscaNotaItemPorProdutoNota(Long idProduto, Long idNotaFiscal);
	
	@Query("SELECT ntItemProd from NotaItemProduto ntItemProd WHERE ntItemProd.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorProduto(Long idProduto);
	
	@Query("SELECT ntItemProd from NotaItemProduto ntItemProd WHERE ntItemProd.notaFiscalCompra.id = ?1")
	List<NotaItemProduto> buscaNotaItemPorNotaFiscal(Long idNotaFiscal);
	
	@Query("SELECT ntItemProd from NotaItemProduto ntItemProd WHERE ntItemProd.empresa.id= ?1")
	List<NotaItemProduto> buscarNotaItemPorEmpresa(Long idEmpresa);
}