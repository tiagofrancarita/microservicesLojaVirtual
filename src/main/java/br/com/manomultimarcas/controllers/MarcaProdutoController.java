package br.com.manomultimarcas.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.model.MarcaProduto;
import br.com.manomultimarcas.repository.MarcaProdutoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Controller
@RestController
public class MarcaProdutoController {
	
	private static final Logger logger = LoggerFactory.getLogger(MarcaProdutoController.class);
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/salvarMarca") //URL para receber o json 
	public ResponseEntity<MarcaProduto> salvarMarca(@RequestBody @Valid MarcaProduto marcaProduto) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		logger.info("Processo de cadastro de marca iniciado.");
		
		if (marcaProduto.getId() == null) {
			List<MarcaProduto> marcaProdutos = marcaProdutoRepository.existeMarcaDesc(marcaProduto.getDescricaoMarca().toUpperCase(), marcaProduto.getEmpresa().getId());
				if (!marcaProdutos.isEmpty()) {
					logger.error("Processo de cadastro de marca encerrado com erro.");
					logger.error("Descrição já cadastrada..");
					throw new ExceptionLojaVirtual("Descrição já cadastrada." + marcaProduto.getDescricaoMarca());
			}
		
		}

		logger.info("marca cadastrada.");
		logger.info("Processo de cadastro de marca finalizado com sucesso.");
		
		MarcaProduto marcaProdutoSalva = marcaProdutoRepository.save(marcaProduto);
		
		return new ResponseEntity<MarcaProduto>(marcaProdutoSalva, HttpStatus.OK);
	}
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/deletarMarca") //URL para receber o json 
	public ResponseEntity<String> deletarAcesso(@RequestBody MarcaProduto marcaProduto) {// Recebe o json e converte para objeto
		
		marcaProdutoRepository.deleteById(marcaProduto.getId());
		return new ResponseEntity<String>("Marca excluída com sucesso",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarMarcaPorId/{id}") //URL para receber o json 
	public ResponseEntity<String>  deletarMarcaPorId(@PathVariable("id") Long id) {// Recebe o json e converte para objeto
		
		
		marcaProdutoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Acesso excluído",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterMarca/{id}") //URL para receber o json 
	public ResponseEntity<?> obterMarcaId(@PathVariable("id") Long id) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		MarcaProduto marcaProduto =  marcaProdutoRepository.findById(id).orElse(null);
		
		if (marcaProduto == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " +  id);
		}
		
		return new ResponseEntity<MarcaProduto>(marcaProduto, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterMarcaDesc/{desc}") //URL para receber o json 
	public ResponseEntity<List<MarcaProduto>> obterMarcaDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
		
		List <MarcaProduto> marcaProdutos =  marcaProdutoRepository.buscarMarcaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<MarcaProduto>>(marcaProdutos, HttpStatus.OK);
		
	}
}