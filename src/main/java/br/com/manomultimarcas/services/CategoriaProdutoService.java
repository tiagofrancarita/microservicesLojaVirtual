package br.com.manomultimarcas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.manomultimarcas.model.CategoriaProduto;
import br.com.manomultimarcas.repository.CategoriaProdutoRepository;

@Service
public class CategoriaProdutoService {
	
	@Autowired
	private CategoriaProdutoRepository categoriaRepository;
	
	public CategoriaProduto salvarCategoria(CategoriaProduto categoriaProduto) {
		
		categoriaProduto = categoriaRepository.save(categoriaProduto);
		
		return categoriaRepository.save(categoriaProduto);	
	}
}