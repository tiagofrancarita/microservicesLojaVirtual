package br.com.manomultimarcas.controllers;

import javax.validation.Valid;

import br.com.manomultimarcas.model.*;
import br.com.manomultimarcas.model.dto.ItemVendaDTO;
import br.com.manomultimarcas.model.dto.VendaCompraLojaVirtualDTO;
import br.com.manomultimarcas.repository.*;
import br.com.manomultimarcas.services.VendaService;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VendaCompraLojaController {

	private static final Logger logger = LoggerFactory.getLogger(VendaCompraLojaController.class);

	public final static String VENDA_INICIO = "O Fluxo de venda foi iniciado com sucesso.";
	public final static String VENDA_FINALIZADA_SUCESSO = "O Fluxo de venda foi finalizado com sucesso.";
	public final static String VENDA_FINALIZADA_ERRO = "O Fluxo venda encerrado com erro.";
	public final static String FLUXO_CRIACAO_STATUS_RASTREIO_INICIO = "Criação do status rastreio iniciada";
	public final static String FLUXO_CRIACAO_STATUS_RASTREIO_FINALIZACAO_SUCESSO = "Criação do status rastreio finalizada com sucesso";
	public final static String FLUXO_CRIACAO_STATUS_RASTREIO_FINALIZACAO_ERRO = "Criação do status rastreio finalizada com erro";

	@Autowired
	private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private NotaFiscalVendaRepository notaFiscalVendaRepository;

	@Autowired
	private StatusRastreioRepository statusRastreioRepository;

	@Autowired
	private VendaService vendaService;

	
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


			for (int i = 0; i < vendaCompraLojaVirtual.getItemVendaLojas().size(); i++){
				vendaCompraLojaVirtual.getItemVendaLojas().get(i).setEmpresa(vendaCompraLojaVirtual.getEmpresa());
				vendaCompraLojaVirtual.getItemVendaLojas().get(i).setVendacompralojavirtual(vendaCompraLojaVirtual);

			}

			// Salva a venda e todos os seus dados no banco.
			vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.saveAndFlush(vendaCompraLojaVirtual);

			//Criacao do status rastreio.
			StatusRastreio statusRastreio = new StatusRastreio();
			statusRastreio.setCentroDistribuicao("CP-Rio de Janeiro");
			statusRastreio.setCidade("Rio de Janeiro");
			statusRastreio.setEstado("Rio de Janeiro");
			statusRastreio.setEmpresa(vendaCompraLojaVirtual.getEmpresa());
			statusRastreio.setStatus("Em Separação");
			statusRastreio.setVendacompralojavirtual(vendaCompraLojaVirtual);

			// Finalização status reastreio
			statusRastreioRepository.save(statusRastreio);

			//Faz a associação da venda com a nota fiscal
			vendaCompraLojaVirtual.getNotaFiscalVenda().setVendacompralojavirtual(vendaCompraLojaVirtual);


			// Faz a persistencia novamente da nota.
			notaFiscalVendaRepository.saveAndFlush(vendaCompraLojaVirtual.getNotaFiscalVenda());
			vendaCompraLojaVirtual = vendaCompraLojaVirtualRepository.saveAndFlush(vendaCompraLojaVirtual);

			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();
			compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());

			compraLojaVirtualDTO.setEnderecoEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
			compraLojaVirtualDTO.setEnderecoCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());

			compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());
			compraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());

			for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()) {

				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setQuantidade(item.getQuantidade());
				itemVendaDTO.setProduto(item.getProduto());

				compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
			}

			return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);

		}

		@ResponseBody
		@GetMapping(value = "**/consultaVendaId/{idVenda}")
		public ResponseEntity<VendaCompraLojaVirtualDTO> consultaVendaId(@PathVariable("idVenda")Long idVenda){

			VendaCompraLojaVirtual compraLojaVirtual = vendaCompraLojaVirtualRepository.findByIdExclusao(idVenda);

			if (compraLojaVirtual == null){

				compraLojaVirtual = new VendaCompraLojaVirtual();
			}

			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

			compraLojaVirtualDTO.setId(compraLojaVirtual.getId());

			compraLojaVirtualDTO.setValorTotal(compraLojaVirtual.getValorTotal());
			compraLojaVirtualDTO.setPessoa(compraLojaVirtual.getPessoa());

			compraLojaVirtualDTO.setEnderecoEntrega(compraLojaVirtual.getEnderecoEntrega());
			compraLojaVirtualDTO.setEnderecoCobranca(compraLojaVirtual.getEnderecoCobranca());

			compraLojaVirtualDTO.setValorDesconto(compraLojaVirtual.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(compraLojaVirtual.getValorFrete());

			for (ItemVendaLoja item : compraLojaVirtual.getItemVendaLojas()) {

				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setQuantidade(item.getQuantidade());
				itemVendaDTO.setProduto(item.getProduto());

				compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
			}

			return new ResponseEntity<VendaCompraLojaVirtualDTO>(compraLojaVirtualDTO, HttpStatus.OK);
		}

		@ResponseBody
		@DeleteMapping(value = "**/deletaTotalVenda/{idVenda}")
		public ResponseEntity<String> deletaTotalVenda(@PathVariable(value = "idVenda") Long idVenda){

			vendaService.deletaTotalVenda(idVenda);

			return new ResponseEntity<String>("Venda excluída com sucesso.", HttpStatus.OK);

		}
	@ResponseBody
	@DeleteMapping(value = "**/deletaTotalVenda/{idVenda}")
	public ResponseEntity<String> exclusaoVendaLogica(@PathVariable(value = "idVenda") Long idVenda){

		vendaService.exclusaoLogicaVenda(idVenda);

		return new ResponseEntity<String>("Venda excluída com sucesso.", HttpStatus.OK);

	}

	@ResponseBody
	@GetMapping(value = "**/consultaVendaPorProdutoId/{idProduto}")
	public ResponseEntity<List<VendaCompraLojaVirtualDTO>> consultaVendaPorProdutoId(@PathVariable("idProduto")Long idProduto){

		List<VendaCompraLojaVirtual> compraLojaVirtual = vendaCompraLojaVirtualRepository.vendaPorProduto(idProduto);

		if (compraLojaVirtual == null){

			compraLojaVirtual = new ArrayList<VendaCompraLojaVirtual>();
		}

		List<VendaCompraLojaVirtualDTO> compraLojaVirtualDTOList = new ArrayList<VendaCompraLojaVirtualDTO>();

		for (VendaCompraLojaVirtual vendaCompraLojaVirtual : compraLojaVirtual) {

			VendaCompraLojaVirtualDTO compraLojaVirtualDTO = new VendaCompraLojaVirtualDTO();

			compraLojaVirtualDTO.setId(vendaCompraLojaVirtual.getId());

			compraLojaVirtualDTO.setValorTotal(vendaCompraLojaVirtual.getValorTotal());
			compraLojaVirtualDTO.setPessoa(vendaCompraLojaVirtual.getPessoa());

			compraLojaVirtualDTO.setEnderecoEntrega(vendaCompraLojaVirtual.getEnderecoEntrega());
			compraLojaVirtualDTO.setEnderecoCobranca(vendaCompraLojaVirtual.getEnderecoCobranca());

			compraLojaVirtualDTO.setValorDesconto(vendaCompraLojaVirtual.getValorDesconto());
			compraLojaVirtualDTO.setValorFrete(vendaCompraLojaVirtual.getValorFrete());

			for (ItemVendaLoja item : vendaCompraLojaVirtual.getItemVendaLojas()) {

				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setQuantidade(item.getQuantidade());
				itemVendaDTO.setProduto(item.getProduto());

				compraLojaVirtualDTO.getItemVendaLoja().add(itemVendaDTO);
			}

			compraLojaVirtualDTOList.add(compraLojaVirtualDTO);

		}

		return new ResponseEntity<List<VendaCompraLojaVirtualDTO>>(compraLojaVirtualDTOList, HttpStatus.OK);
	}
}