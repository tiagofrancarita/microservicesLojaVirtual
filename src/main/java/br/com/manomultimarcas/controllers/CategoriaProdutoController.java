package br.com.manomultimarcas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.model.CategoriaProduto;
import br.com.manomultimarcas.model.dto.CategoriaProdutoDto;
import br.com.manomultimarcas.repository.CategoriaProdutoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Controller
@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarCategoria")
	public ResponseEntity<CategoriaProdutoDto> salvarCategoria(@RequestBody  CategoriaProduto categoriaProduto) throws ExceptionLojaVirtual{
		
		if (categoriaProduto == null  ) {
			throw new ExceptionLojaVirtual("Categoria não pode ser nulo.");
			
		}
		
		if(categoriaProduto.getEmpresa().getId() == null){
			throw new ExceptionLojaVirtual("Campo empresa é obrigatório.");
		}
		
		if (categoriaProdutoRepository.existeCategoria(categoriaProduto.getDescricaoCategoria().toUpperCase())) {
			throw new ExceptionLojaVirtual("Categoria já existe." + categoriaProduto.getDescricaoCategoria());
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		CategoriaProdutoDto categoriaProdutoDto = new CategoriaProdutoDto();
		categoriaProdutoDto.setId(categoriaSalva.getId());
		categoriaProdutoDto.setNomeDesc(categoriaSalva.getDescricaoCategoria());
		categoriaProdutoDto.setEmpresa(categoriaSalva.getEmpresa().getId().toString());
		
		return new ResponseEntity<CategoriaProdutoDto>(categoriaProdutoDto, HttpStatus.OK);
	}
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/deletarCategoria") //URL para receber o json 
	public ResponseEntity<String> deletarAcesso(@RequestBody CategoriaProduto categoriaProduto) {// Recebe o json e converte para objeto
		
		categoriaProdutoRepository.deleteById(categoriaProduto.getId());
		return new ResponseEntity<String>("Categoria excluída com sucesso.",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/buscaCategoriaDesc/{desc}") //URL para receber o json 
	public ResponseEntity<List<CategoriaProduto>> obterCategoriaDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
		
		List <CategoriaProduto> categoriaProdutos =  categoriaProdutoRepository.buscarCategoriaDescricao(desc.toUpperCase());
		
		return new ResponseEntity<List<CategoriaProduto>>(categoriaProdutos, HttpStatus.OK);
		
	}
}