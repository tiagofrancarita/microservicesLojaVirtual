package br.com.manomultimarcas.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import br.com.manomultimarcas.model.ContaPagar;
import br.com.manomultimarcas.repository.ContaPagarRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Controller
public class ContaPagarController {

	private static final Logger logger = LoggerFactory.getLogger(ContaPagarController.class);

	@Autowired
	private ContaPagarRepository contaPagarRepository;

	@ResponseBody // Poder dar um retorno da API
	@PostMapping(value = "**/salvarContaPagar") // URL para receber o json
	public ResponseEntity<ContaPagar> salvarContaPagar(@RequestBody @Valid ContaPagar contaPagar) throws ExceptionLojaVirtual {

		logger.info("Cadastro de contas a pagar iniciado.");
		
		if (contaPagar.getEmpresa() == null || contaPagar.getEmpresa().getId() <= 0) {
			logger.error("Cadastro de contas a pagar encerrado com erro.");
			logger.error("Empresa responsável deve ser informada.");
			throw new ExceptionLojaVirtual("Empresa responsável deve ser imformada.");	
		}
		
		if (contaPagar.getPessoa() == null || contaPagar.getPessoa().getId() <= 0) {
			logger.error("Cadastro de contas a pagar encerrado com erro.");
			logger.error("pessoa responsável deve ser informada.");
			throw new ExceptionLojaVirtual("Pessoa responsável deve ser imformada.");	
		}
		
		if (contaPagar.getPessoaFornecedor() == null || contaPagar.getPessoaFornecedor().getId() <= 0) {
			logger.error("Cadastro de contas a pagar encerrado com erro.");
			logger.error("Fornecedor responsável deve ser informada.");
			throw new ExceptionLojaVirtual("Fornecedor responsável deve ser imformada.");	
		}
		
		if (contaPagar.getId() == null) {
			List<ContaPagar> contaPagars = contaPagarRepository.existeContaPagar(contaPagar.getDescricao().toUpperCase().trim(), contaPagar.getEmpresa().getId());
			if (!contaPagars.isEmpty()) {
				logger.error("Cadastro de contas a pagar encerrado com erro.");
				logger.error("Já existe uma conta a pagar com essa descrição");
				throw new ExceptionLojaVirtual("Fornecedor responsável deve ser imformada.");	
			}
		}
	
		logger.info("Contas a pagar cadastrado.");
		logger.info("Cadastro de contas a pagar finalizado com sucesso.");
		ContaPagar contaPagarSalva = contaPagarRepository.save(contaPagar);
		return new ResponseEntity<ContaPagar>(contaPagarSalva, HttpStatus.OK);

	}

	@ResponseBody // Poder dar um retorno da API
	@PostMapping(value = "**/deletarContaPagar") // URL para receber o json
	public ResponseEntity<String> deletarContaPagar(@RequestBody ContaPagar contaPagar) {// Recebe o json e converte para objeto

		contaPagarRepository.deleteById(contaPagar.getId());
		return new ResponseEntity<String>("Conta a pagar excluída", HttpStatus.OK);

	}

	@ResponseBody
	@DeleteMapping(value = "**/deletarContaPagarId/{id}") // URL para receber o json
	public ResponseEntity<String> deletarContaPagarId(@PathVariable("id") Long id) {// Recebe o json e converte para
																					// objeto

		contaPagarRepository.deleteById(id);

		return new ResponseEntity<String>("Conta a pagar excluída", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/obterContaId/{id}") // URL para receber o json
	public ResponseEntity<ContaPagar> obterContaId(@PathVariable("id") Long id) throws ExceptionLojaVirtual {// Recebe o json e
			
		ContaPagar contaPagar = contaPagarRepository.findById(id).orElse(null);

		if (contaPagar == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " + id);
		}

		return new ResponseEntity<ContaPagar>(contaPagar, HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/obterContaPagarDesc/{desc}") // URL para receber o json
	public ResponseEntity<List<ContaPagar>> obterContaPagarDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
																								
		List<ContaPagar> contaPagars = contaPagarRepository.buscarContasDesc(desc.toUpperCase());

		return new ResponseEntity<List<ContaPagar>>(contaPagars, HttpStatus.OK);

	}
}