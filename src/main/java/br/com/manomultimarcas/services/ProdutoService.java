package br.com.manomultimarcas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.manomultimarcas.model.Produto;
import br.com.manomultimarcas.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
public Produto salvarCategoria(Produto produto) {
		
	produto = produtoRepository.save(produto);
		
		return produtoRepository.save(produto);	
	}

}
