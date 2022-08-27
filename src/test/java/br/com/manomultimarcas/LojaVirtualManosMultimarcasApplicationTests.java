package br.com.manomultimarcas;

import java.util.Calendar;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.manomultimarcas.controllers.AcessoController;
import br.com.manomultimarcas.model.Acesso;
import br.com.manomultimarcas.repository.AcessoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import junit.framework.TestCase;

@Profile("test")
@SpringBootTest(classes = LojaVirtualManosMultimarcasApplication.class)
public class LojaVirtualManosMultimarcasApplicationTests extends TestCase {
	
	@Autowired
	private AcessoController acessoController;
	
	// Má pratica usar o repository direto.
	@Autowired
	private AcessoRepository acessoRepository;
	
	@Autowired
	private WebApplicationContext wac;
	
	// Teste do end-point salvar acesso.
	@Test
	public void testRestApiCadastroAcesso() throws JsonProcessingException, Exception {
		
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		MockMvc mockMvc = builder.build();
		
		Acesso acesso = new Acesso();
		
		ObjectMapper mapper = new ObjectMapper();
		
		acesso.setDescricao("ROLE_COMPRADOR" + Calendar.getInstance().getTimeInMillis());
		
		ResultActions retornoApi = mockMvc
														.perform(MockMvcRequestBuilders.post("/salvarAcesso")
														.content(mapper.writeValueAsString(acesso))
														.accept(org.springframework.http.MediaType.APPLICATION_JSON)
														.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
		
		System.out.println("Api retorno" + retornoApi.andReturn().getResponse().getContentAsString());
		
		// converter o retorno da api para um objeto de acesso.
		
		Acesso objetoRetorno = mapper.
												readValue(retornoApi.andReturn().getResponse().getContentAsString(),
												Acesso.class);
		
		assertEquals(acesso.getDescricao(), objetoRetorno.getDescricao());
	}
	
	// Teste do end-point excluir acesso.
		@Test
		public void testRestApiExcluirAcesso() throws JsonProcessingException, Exception {
			
			DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
			MockMvc mockMvc = builder.build();
			
			Acesso acesso = new Acesso();
			
			ObjectMapper mapper = new ObjectMapper();
			
			acesso.setDescricao("ROLE_TESTE_DELETAR");
			
			acesso = acessoRepository.save(acesso);
			
			ResultActions retornoApi = mockMvc
															.perform(MockMvcRequestBuilders.post("/deletarAcesso")
															.content(mapper.writeValueAsString(acesso))
															.accept(org.springframework.http.MediaType.APPLICATION_JSON)
															.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
			
			System.out.println("Api retorno: "  + retornoApi.andReturn().getResponse().getContentAsString());
			System.out.println("Status retorno: "  + retornoApi.andReturn().getResponse().getStatus());
			
			assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
			// converter o retorno da api para um objeto de acesso.
			
		}
		
		// Teste do end-point excluir acesso por id.
				@Test
				public void testRestApiExcluirAcessoPorID() throws JsonProcessingException, Exception {
					
					DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
					MockMvc mockMvc = builder.build();
					
					Acesso acesso = new Acesso();
					
					ObjectMapper mapper = new ObjectMapper();
					
					acesso.setDescricao("ROLE_TESTE_DELETAR");
					
					acesso = acessoRepository.save(acesso);
					
					ResultActions retornoApi = mockMvc
																	.perform(MockMvcRequestBuilders.delete("/deletarAcessoPorId/" + acesso.getId())
																	.content(mapper.writeValueAsString(acesso))
																	.accept(org.springframework.http.MediaType.APPLICATION_JSON)
																	.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
					
					System.out.println("Api retorno: "  + retornoApi.andReturn().getResponse().getContentAsString());
					System.out.println("Status retorno: "  + retornoApi.andReturn().getResponse().getStatus());
					
					assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
					// converter o retorno da api para um objeto de acesso.
				}
				
				// Teste do end-point obter acesso por id.
				@Test
				public void testRestApiObterAcessoId() throws JsonProcessingException, Exception {
					
					DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
					MockMvc mockMvc = builder.build();
					
					Acesso acesso = new Acesso();
				
					acesso.setDescricao("ROLE_COMPRADOR" + Calendar.getInstance().getTimeInMillis());
					
					acesso = acessoRepository.save(acesso);
					
					ObjectMapper mapper = new ObjectMapper();
					
					ResultActions retornoApi = mockMvc
																	.perform(MockMvcRequestBuilders.get("/obterAcesso/" + acesso.getId())
																	.content(mapper.writeValueAsString(acesso))
																	.accept(org.springframework.http.MediaType.APPLICATION_JSON)
																	.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
					
					
					assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
				Acesso acessoRetorno =	mapper.readValue(retornoApi.andReturn().getResponse().getContentAsString() , Acesso.class);
				
				assertEquals(acesso.getDescricao(), acessoRetorno.getDescricao());
				
				}
				// /obterAcessoDesc/{desc}
				// Teste do end-point obter acesso por descricao.
				@Test
				public void testRestApiObterAcessoDescricao() throws JsonProcessingException, Exception {
					
					DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
					MockMvc mockMvc = builder.build();
					
					Acesso acesso = new Acesso();
					
					acesso.setDescricao("ROLE_COMPRADOR" + Calendar.getInstance().getTimeInMillis());
					
					ObjectMapper mapper = new ObjectMapper();
					
					acesso = acessoRepository.save(acesso);
					
					ResultActions retornoApi = mockMvc
																	.perform(MockMvcRequestBuilders.get("/obterAcessoDesc/OBTER_LIST")
																	.content(mapper.writeValueAsString(acesso))
																	.accept(org.springframework.http.MediaType.APPLICATION_JSON)
																	.contentType(org.springframework.http.MediaType.APPLICATION_JSON));
					
					
				assertEquals(200, retornoApi.andReturn().getResponse().getStatus());
				
				List<Acesso> retornoApiList = mapper.
																readValue(retornoApi.andReturn().getResponse().getContentAsString(), 
																new TypeReference<List<Acesso>>() {});
				
				assertEquals(1, retornoApiList.size());
				assertEquals(acesso.getDescricao(), retornoApiList.get(0).getDescricao());
				
				acessoRepository.deleteById(acesso.getId());
				
				}
	
	@Test
	public void testCadastroAcesso() throws ExceptionLojaVirtual {
		
		String cadAcessoTeste = "ROLE_COMPRADOR" + Calendar.getInstance().getTimeInMillis();
		
		Acesso acesso = new Acesso();
	
		acesso.setDescricao(cadAcessoTeste);
		
		//Valida se antes de salvar no banco o id está nulo / vazio
			assertEquals(true, acesso.getId() == null);
		
		 //Gravou no banco de dados
			acesso = acessoController.salvarAcesso(acesso).getBody();
		
		//Após gravar no banco, verifica se o id é maior que 0.
			assertEquals(true, acesso.getId()>0);
		
		//Verifica se o campo descricao, foi salvo da forma correta.
			assertEquals("ROLE_ADMIN", acesso.getDescricao() );
		
		//Teste de carregamento.
			Acesso acesso2 = acessoRepository.findById(acesso.getId()).get();
			assertEquals(acesso.getId(), acesso2.getId());
		
		//Teste Deletar
			acessoRepository.deleteById(acesso2.getId());
			acessoRepository.flush(); //Roda o sql para deletar o id no banco
			Acesso acesso3 = acessoRepository.findById(acesso2.getId()).orElse(null); // Faz a busca por um codigo que foi apagado do banco, 																													//caso nao encontre tras um valor nullo.
			assertEquals(true, acesso3 == null);
			
			//Teste Query
				acesso = new Acesso();
				
				acesso.setDescricao("ROLE_VENDEDOR" + Calendar.getInstance().getTimeInMillis());
				
				acesso = acessoController.salvarAcesso(acesso).getBody();
				
				List<Acesso> acessos = acessoRepository.buscarAcessoDescricao("VENDEDOR".trim().toUpperCase());
				
				assertEquals(1, acessos.size());
				
				acessoRepository.deleteById(acesso.getId());	
	}
}