package br.com.manomultimarcas;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import br.com.manomultimarcas.controllers.PessoaController;
import br.com.manomultimarcas.enums.TipoEndereco;
import br.com.manomultimarcas.model.Endereco;
import br.com.manomultimarcas.model.PessoaJuridica;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualManosMultimarcasApplication.class)
public class TestePessoaUsuario extends TestCase {

	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadastroPessoa() throws ExceptionLojaVirtual {
		
		PessoaJuridica juridicaTeste = new PessoaJuridica();
		
		juridicaTeste.setCnpj("07588993333007754");
		juridicaTeste.setInscEstadual("695394183305");
		juridicaTeste.setNome("Nome Teste PJ Email");
		juridicaTeste.setInscMunincipal("1234456");
		juridicaTeste.setNomeFantasia("Teste Fantasia PJ");
		juridicaTeste.setRazaoSocial("Teste salvar pj");
		juridicaTeste.setEmail("tiagofranca.rita@gmail.com");
		juridicaTeste.setTelefone("(21) 964867991");
		juridicaTeste.setCategoria("Categoria2");
		juridicaTeste.setTipoPessoa("J");
		
		//juridicaTeste.setEnderecos(null);
		//juridicaTeste.setTipoPessoa("");
		
		Endereco enderecoCobranca = new Endereco();
		enderecoCobranca.setBairro("Santa Cruz");
		enderecoCobranca.setCep("23575-901");
		enderecoCobranca.setCidade("Rio de Janeiro");
		enderecoCobranca.setComplemento("BL 10 Apt 103");
		enderecoCobranca.setEmpresa(juridicaTeste);
		enderecoCobranca.setLogradouro("Avenida Padre Guilherme Decaminada");
		enderecoCobranca.setNumero("415");
		enderecoCobranca.setPessoa(juridicaTeste);
		enderecoCobranca.setTipoEndereco(TipoEndereco.COBRANCA);
		enderecoCobranca.setUf("RJ");
		
		Endereco enderecoEntrega = new Endereco();
		enderecoEntrega.setBairro("Cosmos");
		enderecoEntrega.setCep("23575-901");
		enderecoEntrega.setCidade("Rio de Janeiro");
		enderecoEntrega.setComplemento("BL 10 Apt 101");
		enderecoEntrega.setEmpresa(juridicaTeste);
		enderecoEntrega.setLogradouro("Avenida Padre Guilherme Decaminada");
		enderecoEntrega.setNumero("415");
		enderecoEntrega.setPessoa(juridicaTeste);
		enderecoEntrega.setTipoEndereco(TipoEndereco.ENTREGA);
		enderecoEntrega.setUf("RJ");
		
		juridicaTeste.getEndereco().add(enderecoEntrega);
		juridicaTeste.getEndereco().add(enderecoCobranca);
		
		juridicaTeste = pessoaController.salvarPj(juridicaTeste).getBody();
		
		assertEquals(true, juridicaTeste.getId() > 0);
		
		for (Endereco endereco : juridicaTeste.getEndereco()) {
			assertEquals(true, endereco.getId() > 0);
			
		}
		
		assertEquals(2, juridicaTeste.getEndereco().size());
	
	}
}