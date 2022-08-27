package br.com.manomultimarcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.manomultimarcas.model.ImagemProduto;

@Repository
@Transactional
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {
	
	@Query(value = "SELECT imgProd FROM ImagemProduto imgProd WHERE imgProd.produto.id = ?1")
	List<ImagemProduto> buscaImagemProduto(Long idProduto);
	
	@Transactional
	@Modifying(flushAutomatically = true)
	@Query(nativeQuery = true, value = "DELETE FROM imagem_produto WHERE produtoid = ?1")
	void deleteImagens(Long idProduto);

}
