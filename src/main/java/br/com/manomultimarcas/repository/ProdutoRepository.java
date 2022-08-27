package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	@Query(nativeQuery = true, 
				value="SELECT COUNT(1) > 0 FROM  Produto WHERE upper(trim(nome)) = upper(trim(?1)) ;")
	public boolean existeProduto(String descricao);
	
	@Query(nativeQuery = true, 
			value=" SELECT COUNT(1) > 0 FROM  Produto WHERE upper(trim(nome)) = upper(trim(?1)) and empresaid = ?2 ;")
	public boolean existeProduto(String descricao, Long idEmpresa);

	@Query(nativeQuery = true, 
			value=" SELECT * FROM  Produto WHERE upper(trim(descricao)) LIKE %?1% ")
	public List<Produto> buscarProdutoNome(String descricao);
	
	@Query(nativeQuery = true, 
			value=" SELECT * FROM  Produto WHERE upper(trim(descricao)) LIKE %?1% and empresaid = ?2 ; ")
	public List<Produto> buscarProdutoNome(String descricao, Long idEmpresa);

}