package br.com.manomultimarcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.CategoriaProduto;

@Repository
@Transactional
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
	
	@Query("SELECT categ from CategoriaProduto categ WHERE upper(trim(categ.descricaoCategoria)) like %?1%")
	public CategoriaProduto descricaoExistente(String descricaoCategoria);
	
	@Query(nativeQuery = true, 
				value="SELECT COUNT(1) > 0 FROM  categoria_produto WHERE upper(descricao_categoria) = ?1 ;")
	public boolean existeCategoria(String descricaoCategoria);

	@Query(nativeQuery = true, 
			value="SELECT * FROM  categoria_produto WHERE upper(trim(descricao_categoria)) LIKE %?1% ")
	public List<CategoriaProduto> buscarCategoriaDescricao(String descricaoCategoria);
}