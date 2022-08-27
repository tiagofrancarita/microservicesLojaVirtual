package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.manomultimarcas.model.AvaliacaoProduto;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
	
	@Query(value = "SELECT avalProd FROM AvaliacaoProduto avalProd WHERE avalProd.produto.id = ?1")
	List<AvaliacaoProduto> AvaliacaoProduto(Long idProduto);
	
	@Query(value = "SELECT avalProd FROM AvaliacaoProduto avalProd WHERE avalProd.produto.id = ?1 and avalProd.pessoa.id = ?2")
	List<AvaliacaoProduto> AvaliacaoProdutoPessoa(Long idProduto, Long idPessoa);
	
	@Query(value = "SELECT avalProd FROM AvaliacaoProduto avalProd WHERE avalProd.pessoa.id = ?1")
	List<AvaliacaoProduto> AvaliacaoPessoa(Long idPessoa);

	@Query("SELECT avalProd FROM AvaliacaoProduto avalProd WHERE UPPER(TRIM(avalProd.descricao)) LIKE %?1%")
	List<AvaliacaoProduto> buscarAcessoDescricao(String upperCase);

}