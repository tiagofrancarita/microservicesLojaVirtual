package br.com.manomultimarcas.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
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
import br.com.manomultimarcas.model.Acesso;
import br.com.manomultimarcas.repository.AcessoRepository;
import br.com.manomultimarcas.services.AcessoService;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Controller
@RestController
public class AcessoController {
	
	private static final Logger logger = LoggerFactory.getLogger(AcessoController.class);
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private AcessoService acessoService;
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/salvarAcesso") //URL para receber o json 
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		logger.info("Processo de cadastro de acesso iniciado.");
		
		if (acesso.getId() == null) {
			
			List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(acesso.getDescricao().toUpperCase());
			
		if (!acessos.isEmpty()) {
				logger.error("Processo de cadastro de acesso encerrado com erro.");
				logger.error("Descrição já cadastrada..");
				throw new ExceptionLojaVirtual("Descrição já cadastrada." + acesso.getDescricao());
			}
		}

		logger.info("Acesso cadastrado.");
		logger.info("Processo de cadastro de acesso finalizado com sucesso.");
		Acesso acessoSalvo = acessoService.save(acesso);
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/deletarAcesso") //URL para receber o json 
	public ResponseEntity<String> deletarAcesso(@RequestBody Acesso acesso) {// Recebe o json e converte para objeto
		
		acessoRepository.deleteById(acesso.getId());
		return new ResponseEntity<String>("Acesso excluído",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarAcessoPorId/{id}") //URL para receber o json 
	public ResponseEntity<String>  deletarAcessoPorId(@PathVariable("id") Long id) {// Recebe o json e converte para objeto
		
		acessoRepository.deleteById(id);
		
		return new ResponseEntity<String>("Acesso excluído",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcesso/{id}") //URL para receber o json 
	public ResponseEntity<?> obterAcessoId(@PathVariable("id") Long id) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		Acesso acesso =  acessoRepository.findById(id).orElse(null);
		
		if (acesso == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " +  id);
		}
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterAcessoDesc/{desc}") //URL para receber o json 
	public ResponseEntity<List<Acesso>> obterAcessoDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
		
		List <Acesso> acesso =  acessoRepository.buscarAcessoDescricao(desc.toUpperCase());
		
		return new ResponseEntity<List<Acesso>>(acesso, HttpStatus.OK);
		
	}
}