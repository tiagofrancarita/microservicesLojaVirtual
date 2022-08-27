package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.manomultimarcas.model.MarcaProduto;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {
	
	@Query("SELECT marcaProduto FROM MarcaProduto marcaProduto WHERE UPPER(TRIM(marcaProduto.descricaoMarca)) LIKE %?1%")
	List<MarcaProduto> buscarMarcaDesc(String desc);
	
	@Query("SELECT marcaProduto FROM MarcaProduto marcaProduto WHERE UPPER(trim(marcaProduto.descricaoMarca)) LIKE %?1% and empresaid = ?2")
	List<MarcaProduto> existeMarcaDesc(String desc, Long idEmpresa);

}