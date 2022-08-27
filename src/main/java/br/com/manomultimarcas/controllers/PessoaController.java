package br.com.manomultimarcas.controllers;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.manomultimarcas.enums.TipoPessoa;
//import br.com.manomultimarcas.model.Endereco;
import br.com.manomultimarcas.model.PessoaFisica;
import br.com.manomultimarcas.model.PessoaJuridica;
import br.com.manomultimarcas.model.dto.CepDTO;
import br.com.manomultimarcas.model.dto.ConsultaCnpjDto;
//import br.com.manomultimarcas.repository.EnderecoRepository;
import br.com.manomultimarcas.repository.PessoaFisicaRepository;
import br.com.manomultimarcas.repository.PessoaRepository;
import br.com.manomultimarcas.services.ContagemApiService;
import br.com.manomultimarcas.services.PessoaUserService;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import br.com.manomultimarcas.util.ValidaCnpj;
import br.com.manomultimarcas.util.ValidaCpf;
import ch.qos.logback.classic.Logger;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private ContagemApiService contagemApiService;
	
	@ResponseBody
	@GetMapping(value = "**/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable("cep") String cep){
	
		return new ResponseEntity<CepDTO>(pessoaUserService.consultaCep(cep), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjReceitaWs/{cnpj}")
	public ResponseEntity<ConsultaCnpjDto> consultaCnpjReceitaWs(@PathVariable("cnpj") String cnpj){
		
	  return new ResponseEntity<ConsultaCnpjDto>(pessoaUserService.consultaCnpjReceitaWS(cnpj), HttpStatus.OK);
		
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaNomePf/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPfNome(@PathVariable("nome") String nome){
		
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorNomePf(nome.trim().toUpperCase());
		
		contagemApiService.contagemEndPointPF();
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCpfPf/{cpf}")
	public ResponseEntity<List<PessoaFisica>> consultaPfCpf(@PathVariable("cpf") String cpf){
		
		List<PessoaFisica> fisicas = pessoaFisicaRepository.pesquisaPorCpfPf(cpf);
		
		return new ResponseEntity<List<PessoaFisica>>(fisicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaNomePj/{nome}")
	public ResponseEntity<List<PessoaJuridica>> consultaPjNome(@PathVariable("nome") String nome){
		
		List<PessoaJuridica> fisicas = pessoaRepository.pesquisaPorNomePj(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<PessoaJuridica>>(fisicas, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/consultaCnpjPj/{cnpj}")
	public ResponseEntity<List<PessoaJuridica>> consultaPjCnpj(@PathVariable("cnpj") String cnpj){
		
		List<PessoaJuridica> juridicas = pessoaRepository.consultaCnpj(cnpj);
		
		return new ResponseEntity<List<PessoaJuridica>>(juridicas, HttpStatus.OK);
	}
	
	private static final Logger Logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(PessoaController.class);
	
	/* end-point, micro-serviço, api   */
	@ResponseBody
	@PostMapping(value = "**/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPj (@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionLojaVirtual{
		
		Logger.info("Iniciando o cadastro de pessoa jurídica.");

		if (pessoaJuridica == null) {
			
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Pessoa jurídica não pode ser nulo.");
			throw new ExceptionLojaVirtual("Pessoa jurídica não pode ser nulo.");
		}
		
		if (pessoaJuridica.getTipoPessoa() == null) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Necessário informar o tipo de pessoa juridica: Juridico ou Fornecedor");
			throw new ExceptionLojaVirtual("Necessário informar o tipo de pessoa juridica");
			
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.cnpjExistente(pessoaJuridica.getCnpj()) != null) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("CNPJ já cadastrado" + pessoaJuridica.getCnpj());
			throw new ExceptionLojaVirtual("CNPJ já cadastrado" + pessoaJuridica.getCnpj());
			
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.insEstadualExistente(pessoaJuridica.getInscEstadual()) != null) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Incrição Estadual já cadastrado" + pessoaJuridica.getInscEstadual());
			throw new ExceptionLojaVirtual("Incrição Estadual já cadastrado" + pessoaJuridica.getInscEstadual());
			
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.insMunincipalExistente(pessoaJuridica.getInscMunincipal()) != null) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Incrição Munincipal já cadastrado" + pessoaJuridica.getInscMunincipal());
			throw new ExceptionLojaVirtual("Incrição Munincipal  já cadastrado" + pessoaJuridica.getInscMunincipal());
			
		}
		
		if (!ValidaCnpj.isCNPJ(pessoaJuridica.getCnpj())) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Cnpj informado é inválido, favor verificar" + pessoaJuridica.getCnpj());
			throw new ExceptionLojaVirtual("Cnpj informado é inválido, favor verificar" + pessoaJuridica.getCnpj());
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			
			for (int p = 0; p < pessoaJuridica.getEndereco().size(); p++) {
				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEndereco().get(p).getCep());
				pessoaJuridica.getEndereco().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEndereco().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEndereco().get(p).setLogradouro(cepDTO.getLogradouro());
				pessoaJuridica.getEndereco().get(p).setUf(cepDTO.getUf());
			}
			
		}else {
			/*
			for (int p = 0; p < pessoaJuridica.getEndereco().size(); p++) {
				Endereco enderecoTemp = enderecoRepository.findById(pessoaJuridica.getEndereco().get(p).getId()).get();
				
				if (!enderecoTemp.getCep().equals(pessoaJuridica.getEndereco().get(p).getCep())) {
					CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEndereco().get(p).getCep());
					
					pessoaJuridica.getEndereco().get(p).setBairro(cepDTO.getBairro());
					pessoaJuridica.getEndereco().get(p).setCidade(cepDTO.getLocalidade());
					pessoaJuridica.getEndereco().get(p).setLogradouro(cepDTO.getLogradouro());
					pessoaJuridica.getEndereco().get(p).setUf(cepDTO.getUf());
				}
			}*/
			
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJuridica(pessoaJuridica);
		Logger.info("Processo de cadastro de pessoa jurídica finalizado com sucesso");
		Logger.info("Cadastro realizado.");
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
		
	}
	
	/* end-point, micro-serviço, api   */
	@ResponseBody
	@PostMapping(value = "**/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPf (@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionLojaVirtual{
		
		Logger.info("Iniciando o cadastro de pessoa fisica.");
		
		if (pessoaFisica == null) {
			
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("Pessoa fisica não pode ser nulo.");
			throw new ExceptionLojaVirtual("Pessoa fisica não pode ser nulo.");
		}
		
		if (pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.name());
			
		}
		
		if (pessoaFisica.getId() == null && pessoaFisicaRepository.cpfExistente(pessoaFisica.getCpf()) != null) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("CPF já cadastrado" + pessoaFisica.getCpf());
			throw new ExceptionLojaVirtual("CPF já cadastrado com o número informado." + pessoaFisica.getCpf());
			
		}
		
		if (!ValidaCpf.isCPF(pessoaFisica.getCpf())) {
			Logger.error("Processo de cadastro encerrado com falhas.");
			Logger.error("CPF informado é inválido, favor verificar" + pessoaFisica.getCpf());
			throw new ExceptionLojaVirtual("CPF informado é inválido, favor verificar" + pessoaFisica.getCpf());
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		Logger.info("Processo de cadastro de pessoa fisica finalizado com sucesso");
		Logger.info("Cadastro realizado.");
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
		
	}
}