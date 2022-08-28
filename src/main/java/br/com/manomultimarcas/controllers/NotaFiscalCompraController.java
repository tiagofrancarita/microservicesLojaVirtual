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
import br.com.manomultimarcas.model.NotaFiscalCompra;
import br.com.manomultimarcas.repository.NotaFiscalCompraRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@RestController
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@ResponseBody //Poder dar um retorno da API
	@PostMapping(value = "**/salvarNotaFiscalCompra") //URL para receber o json 
	public ResponseEntity<NotaFiscalCompra> salvarNotaFiscalCompra(@RequestBody @Valid NotaFiscalCompra notaFiscalCompra) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto

		
		if (notaFiscalCompra.getId() == null) {
			if (notaFiscalCompra.getDescricaoObs() !=null) {
				List<NotaFiscalCompra> fiscalCompras = notaFiscalCompraRepository.buscaNotaDesc(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());
				boolean existe = notaFiscalCompraRepository.existeNotaDescObs(notaFiscalCompra.getDescricaoObs().toUpperCase().trim());
				if (!fiscalCompras.isEmpty()) {
					throw new ExceptionLojaVirtual("Já existe uma nota cadastrada com essa descrição." +  notaFiscalCompra.getDescricaoObs());
					
				}
				if (existe) {
					throw new ExceptionLojaVirtual("Já existe uma nota cadastrada com essa descrição." +  notaFiscalCompra.getDescricaoObs());
				}
				if (notaFiscalCompra.getDescricaoObs() !=null) {
					List<NotaFiscalCompra> fiscalComprasNum = notaFiscalCompraRepository.buscaNotaNumNot(notaFiscalCompra.getNumeroNota().toUpperCase().trim());
					
					if (!fiscalComprasNum.isEmpty()) {
						throw new ExceptionLojaVirtual("Já existe uma nota cadastrada com esse numero." + "Número Nota: " +  notaFiscalCompra.getNumeroNota());
					}	
				}	
			}
		}
		


		if (notaFiscalCompra.getPessoa() == null || notaFiscalCompra.getPessoa().getId() <=0) {
			throw new ExceptionLojaVirtual("Necessário informar a pessoa jurídica responsavél pela nota.");
		}
		
		if (notaFiscalCompra.getEmpresa() == null || notaFiscalCompra.getEmpresa().getId() <=0) {
			throw new ExceptionLojaVirtual("Necessário informar a empresa responsavél pela nota.");
		}
		
		if (notaFiscalCompra.getContaPagar() == null || notaFiscalCompra.getContaPagar().getId() <=0) {
			throw new ExceptionLojaVirtual("Necessário informar a conta a pagar da nota.");
		}
		
		

		NotaFiscalCompra notaFiscalCompraSalva = notaFiscalCompraRepository.save(notaFiscalCompra);
		System.out.println("Cadastro realizado");
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompraSalva, HttpStatus.OK);
	}
	
	@ResponseBody
	@DeleteMapping(value = "**/deletarNotaFiscalCompraPorId/{id}") //URL para receber o json 
	public ResponseEntity<String>  deletarNotaFiscalCompraPorId(@PathVariable("id") Long id) {// Recebe o json e converte para objeto
		
		
		notaFiscalCompraRepository.deletarItemNotaFiscalCompra(id);// Deleta item nota que está relacionado com a nota fiscal
		
		notaFiscalCompraRepository.deleteById(id); //deleta nota fiscal
		
		return new ResponseEntity<String>("Acesso excluído",HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/obterNotaFiscalCompra/{id}") //URL para receber o json 
	public ResponseEntity<?> obterNotaFiscalPorId(@PathVariable("id") Long id) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto
		
		NotaFiscalCompra notaFiscalCompra =  notaFiscalCompraRepository.findById(id).orElse(null);
		
		if (notaFiscalCompra == null) {
			throw new ExceptionLojaVirtual("Código informado não existe." + "Código: " +  id);
		}
		
		return new ResponseEntity<NotaFiscalCompra>(notaFiscalCompra, HttpStatus.OK);
		
	}
	
	
	@ResponseBody
	@GetMapping(value = "**/buscarNotaFiscalPorDesc/{desc}") //URL para receber o json 
	public ResponseEntity<List<NotaFiscalCompra>> obterMarcaDesc(@PathVariable("desc") String desc) {// Recebe o json e converte para objeto
		
		List <NotaFiscalCompra> notaFiscalCompras =  notaFiscalCompraRepository.buscaNotaDesc(desc.toUpperCase());
		
		return new ResponseEntity<List<NotaFiscalCompra>>(notaFiscalCompras, HttpStatus.OK);
		
	}

}
