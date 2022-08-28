package br.com.manomultimarcas.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.model.AvaliacaoProduto;
import br.com.manomultimarcas.repository.AvaliacaoProdutoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@RestController
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@ResponseBody
	@PostMapping(value = "**/salvarAvaliacaoProduto")
	public ResponseEntity<AvaliacaoProduto> salvarAvaliacaoProduto(@Valid @RequestBody AvaliacaoProduto avaliacaoProduto) throws ExceptionLojaVirtual{
		
		if (avaliacaoProduto.getEmpresa() == null || (avaliacaoProduto.getEmpresa() != null
				&& avaliacaoProduto.getEmpresa().getId() <= 0)) {
			throw new ExceptionLojaVirtual("Informe a empresa.");
		}
		
		if (avaliacaoProduto.getProduto() == null || (avaliacaoProduto.getProduto() != null
				&& avaliacaoProduto.getProduto().getId() <= 0)) {
			throw new ExceptionLojaVirtual("Para cadastrar uma avaliação deve conter um produto associado.");
		}
		
		if (avaliacaoProduto.getPessoa() == null || (avaliacaoProduto.getPessoa() != null
				&& avaliacaoProduto.getPessoa().getId() <= 0)) {
			throw new ExceptionLojaVirtual("Para cadastrar uma avaliação deve conter um cliente associado.");
		}
		
		avaliacaoProduto = avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
		
		return new ResponseEntity<AvaliacaoProduto>(avaliacaoProduto, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarAvaliacaoProdutoPorId/{idAvaliacao}") //URL para receber o json
	public ResponseEntity<String>  deletarAvaliacaoProdutoPorId(@PathVariable("idAvaliacao") Long idAvaliacao) {// Recebe o json e converte para objeto
		
		avaliacaoProdutoRepository.deleteById(idAvaliacao);
		
		return new ResponseEntity<String>("Avaliação excluída com sucesso !",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/AvaliacaoProduto/{idProduto}") //URL para receber o json 
	public ResponseEntity<List<AvaliacaoProduto>> AvaliacaoProduto(@PathVariable("idProduto") Long idProduto) {// Recebe o json e converte para objeto
		
		List<AvaliacaoProduto> avaliacaoProdutos = avaliacaoProdutoRepository.AvaliacaoProduto(idProduto);
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutos, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/AvaliacaoProdutoPessoa/{idProduto}/{idPessoa}") //URL para receber o json 
	public ResponseEntity<List<AvaliacaoProduto>> 
											AvaliacaoProdutoPessoa(@PathVariable("idProduto") Long idProduto,@PathVariable("idPessoa") Long idPessoa) {// Recebe o json e converte para objeto
		
		List<AvaliacaoProduto> avaliacaoProdutosPessoas = avaliacaoProdutoRepository.AvaliacaoProdutoPessoa(idProduto, idPessoa);
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProdutosPessoas, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/AvaliacaoPessoa/{idPessoa}") //URL para receber o json 
	public ResponseEntity<List<AvaliacaoProduto>> AvaliacaoPessoa(@PathVariable("idPessoa") Long idPessoa) {// Recebe o json e converte para objeto
		
		List<AvaliacaoProduto> avaliacaoPessoas = avaliacaoProdutoRepository.AvaliacaoPessoa(idPessoa);
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoPessoas, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAvaliacaoId/{idAvaliacao}") //URL para receber o json 
	public ResponseEntity<?> obterAvaliacaoPorId(@PathVariable("idAvaliacao") Long idAvaliacao) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		AvaliacaoProduto avaliacaoProdutos =  avaliacaoProdutoRepository.findById(idAvaliacao).orElse(null);
		
		if (avaliacaoProdutos == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " +  idAvaliacao);
		}
		
		return new ResponseEntity<AvaliacaoProduto>(avaliacaoProdutos, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAvaliacaoProdutoDesc/{desc}") //URL para receber o json 
	public ResponseEntity<List<AvaliacaoProduto>> obterAvaliacaoProdutoDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
		
		List <AvaliacaoProduto> avaliacaoProduto =  avaliacaoProdutoRepository.buscarAcessoDescricao(desc.toUpperCase());
		
		return new ResponseEntity<List<AvaliacaoProduto>>(avaliacaoProduto, HttpStatus.OK);
		
	}
}