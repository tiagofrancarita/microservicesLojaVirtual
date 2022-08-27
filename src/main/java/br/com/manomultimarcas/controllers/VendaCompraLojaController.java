package br.com.manomultimarcas.controllers;

import javax.validation.Valid;

import br.com.manomultimarcas.model.Endereco;
import br.com.manomultimarcas.model.PessoaFisica;
import br.com.manomultimarcas.model.dto.VendaCompraLojaVirtualDTO;
import br.com.manomultimarcas.repository.EnderecoRepository;
import br.com.manomultimarcas.repository.NotaFiscalVendaRepository;
import br.com.manomultimarcas.repository.PessoaFisicaRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.model.VendaCompraLojaVirtual;
import br.com.manomultimarcas.repository.VendaCompraLojaVirtualRepository;

@RestController
public class VendaCompraLojaController {

	private static final Logger logger = LoggerFactory.getLogger(VendaCompraLojaController.class);

	public final static String VENDA_INICIO = "O Fluxo de venda foi iniciado com sucesso.";
	public final static String VENDA_FINALIZADA_SUCESSO = "O Fluxo de venda foi finalizado com sucesso.";
	public final static String VENDA_FINALIZADA_ERRO = "O Fluxo venda encerrado com erro.";

	@Autowired
	private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private NotaFiscalVendaRepository notaFiscalVendaRepository;

	
	@ResponseBody
	@PostMapping(value = "**/salvarVendaLoja")
	public ResponseEntity<VendaCompraLojaVirtualDTO> salvarVendaLoja(@RequestBody @Valid
					VendaCompraLojaVirtual vendaCompraLojaVirtual) throws ExceptionLojaVirtual {


			vendaCompraLojaVirtual.getPessoa().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			PessoaFisica pessoaFisica = pessoaController.salvarPf(vendaCompraLojaVirtual.getPessoa()).getBody();
			vendaCompraLojaVirtual.setPessoa(pessoaFisica);

			vendaCompraLojaVirtual.getEnderecoCobranca().setPessoa(pessoaFisica);
			vendaCompraLojaVirtual.getEnderecoCobranca().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			Endereco enderecoCobranca = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoCobranca());
			vendaCompraLojaVirtual.setEnderecoCobranca(enderecoCobranca);

			vendaCompraLojaVirtual.getEnderecoEntrega().setPessoa(pessoaFisica);
			vendaCompraLojaVirtual.getEnderecoEntrega().setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			Endereco enderecoEntrega = enderecoRepository.save(vendaCompraLojaVirtual.getEnderecoEntrega());
			vendaCompraLojaVirtual.setEnderecoEntrega(enderecoEntrega);

			vendaCompraLojaVirtual.getNotaFiscalVenda().setEmpresa(vendaCompraLojaVirtual.getEmpresa());

			// Salva primeiro a venda
			vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.saveAndFlush(vendaCompraLojaVirtual);

			//Faz a associação da venda com a nota fiscal
			vendaCompraLojaVirtual.getNotaFiscalVenda().setVendacompralojavirtual(vendaCompraLojaVirtual);


			// Faz a persistencia novamente da nota.
			notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());
			vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.saveAndFlush(vendaCompraLojaVirtual);

			VendaCompraLojaVirtualDTO vendaLojaVirtualDto = new VendaCompraLojaVirtualDTO();
			vendaLojaVirtualDto.setValorTotal(vendaCompraLojaVirtual.getValorTotal());

			return new ResponseEntity<VendaCompraLojaVirtualDTO>(vendaLojaVirtualDto, HttpStatus.OK);

		}
	}
