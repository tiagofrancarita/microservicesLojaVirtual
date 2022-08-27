package br.com.manomultimarcas;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import br.com.manomultimarcas.controllers.PessoaController;
import br.com.manomultimarcas.enums.TipoEndereco;
import br.com.manomultimarcas.model.Endereco;
import br.com.manomultimarcas.model.PessoaFisica;
import br.com.manomultimarcas.model.PessoaJuridica;
import br.com.manomultimarcas.repository.PessoaRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;

@Profile("test")
@SpringBootTest(classes = LojaVirtualManosMultimarcasApplication.class)
public class TestePessoaFisica {
	
	@Autowired
	private PessoaController pessoaController;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Test
	public void testCadastroPessoaFisica() throws ExceptionLojaVirtual {
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 Date date = null;
		 try {
			date = formatter.parse("07/06/1995");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		PessoaJuridica pessoaJuridica = pessoaRepository.cnpjExistente("4345345677783910657335676745");
		 
		PessoaFisica pessoaFisicaTeste = new PessoaFisica();
		
		pessoaFisicaTeste.setNome("Tiago de França Santa Rita");
		pessoaFisicaTeste.setCpf("965.500.010-99");
		pessoaFisicaTeste.setEmail("tiagofranca.riitaa@yahoo.com.br");
		pessoaFisicaTeste.setTelefone("(21)964867990");
		pessoaFisicaTeste.setTipoPessoa("Fisica");
		pessoaFisicaTeste.setDataNascimento(date);
		pessoaFisicaTeste.setEmpresa(pessoaJuridica);
	
		Endereco enderecoCobranca = new Endereco();
		enderecoCobranca.setBairro("Centro");
		enderecoCobranca.setCep("23575-901");
		enderecoCobranca.setCidade("Rio de Janeiro");
		enderecoCobranca.setComplemento("BL 10 Apt 103");
		enderecoCobranca.setLogradouro("Avenida Padre Guilherme Decaminada");
		enderecoCobranca.setNumero("415");
		enderecoCobranca.setPessoa(pessoaFisicaTeste);
		enderecoCobranca.setTipoEndereco(TipoEndereco.COBRANCA);
		enderecoCobranca.setUf("RJ");
		enderecoCobranca.setEmpresa(pessoaJuridica);
		
		Endereco enderecoEntrega = new Endereco();
		enderecoEntrega.setBairro("Carobinha");
		enderecoEntrega.setCep("23575-901");
		enderecoEntrega.setCidade("Rio de Janeiro");
		enderecoEntrega.setComplemento("BL 10 Apt 101");
		enderecoEntrega.setLogradouro("Avenida Padre Guilherme Decaminada");
		enderecoEntrega.setNumero("415");
		enderecoEntrega.setPessoa(pessoaFisicaTeste);
		enderecoEntrega.setTipoEndereco(TipoEndereco.ENTREGA);
		enderecoEntrega.setUf("RJ");
		enderecoEntrega.setEmpresa(pessoaJuridica);
		
		pessoaFisicaTeste.getEndereco().add(enderecoEntrega);
		pessoaFisicaTeste.getEndereco().add(enderecoCobranca);

		pessoaFisicaTeste = pessoaController.salvarPf(pessoaFisicaTeste).getBody();
		
		assertEquals(true, pessoaFisicaTeste.getId() > 0);
		
		for (Endereco endereco : pessoaFisicaTeste.getEndereco()) {
			assertEquals(true, endereco.getId() > 0);
			
		}
		
		assertEquals(2, pessoaFisicaTeste.getEndereco().size());
	
	}
}