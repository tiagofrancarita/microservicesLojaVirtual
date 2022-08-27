package br.com.manomultimarcas.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.model.NotaItemProduto;
import br.com.manomultimarcas.repository.NotaItemProdutoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@RestController
public class NotaItemProdutoController {
	
	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRespository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarNotaItemProduto")
	public ResponseEntity<NotaItemProduto> salvarNotaItemProduto(@RequestBody @Valid NotaItemProduto notaItemProduto) throws ExceptionLojaVirtual {
				
		if (notaItemProduto.getId() == null) {
			
			if (notaItemProduto.getProduto() == null || notaItemProduto.getProduto().getId() <= 0) {
				throw new ExceptionLojaVirtual("O produto deve ser informado.");
			}
			
			if (notaItemProduto.getNotaFiscalCompra() == null || notaItemProduto.getNotaFiscalCompra().getId() <= 0) {
				throw new ExceptionLojaVirtual("A nota fiscal de compra deve ser informado.");
			}
			
			if (notaItemProduto.getEmpresa() == null || notaItemProduto.getEmpresa().getId() <= 0) {
				throw new ExceptionLojaVirtual("A empresa  deve ser informado.");
			}
			
			List<NotaItemProduto> notaExistente = notaItemProdutoRespository.buscaNotaItemPorProdutoNota(notaItemProduto.getProduto().getId(),
																																								notaItemProduto.getNotaFiscalCompra().getId());
			if (!notaExistente.isEmpty()) {
				throw new ExceptionLojaVirtual("Já existe este produto cadastrado para esta nota");
			}	
		}
		
		if (notaItemProduto.getQuantidade() <= 0) {
			throw new ExceptionLojaVirtual("A quantidade do produto deve ser informada. ");
		}
		
		NotaItemProduto notaItemProdutoSalva = notaItemProdutoRespository.save(notaItemProduto);
		
		notaItemProdutoSalva = notaItemProdutoRespository.findById(notaItemProduto.getId()).get();
		
		return new ResponseEntity<NotaItemProduto>(notaItemProdutoSalva, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarNotaItemProduto/{id}") //URL para receber o json 
	public ResponseEntity<String>  deletarNotaItemProdutoPorId(@PathVariable("id") Long id) {// Recebe o json e converte para objeto
		
		notaItemProdutoRespository.deleteById(id); //deleta nota fiscal
		
		return new ResponseEntity<String>("Nota item produto excluído.",HttpStatus.OK);
		
	}
}