package br.com.manomultimarcas.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.manomultimarcas.model.Usuario;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	
	/* Configurando o gerenciador de autenticacao */
	public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
		
		super(new AntPathRequestMatcher(url));
		
		setAuthenticationManager(authenticationManager);
		
	}
	/*Retorna o usuario ao processar a autenticacao.  */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		/*Obter usuario*/
		Usuario user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
		
		/*Retorna user com login e senha*/
		return  getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getSenha()));
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		try {
			new JWTTokenAutenticacaoService().addAuthentication(response, authResult.getName());
			logger.info("Token obtido com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Falha ao obter o token." + e.getMessage());
		}
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		
		if (failed instanceof BadCredentialsException) {
			
			response.getWriter().write("Usuário(a) ou senha inválidos, favor Verificar os dados digitados.");
			logger.error("Usuário(a) ou senha inválidos, favor Verificar os dados digitados.");
			
		}else {
			response.getWriter().write("Falha ao realizar o login, entre em contato com a equipe responsável pelo suporte do sistema."
					+ "Falha:" + failed.getMessage() + failed.getClass());
			
			logger.error("Falha ao realizar o login, entre em contato com a equipe responsável pelo suporte do sistema."
					+ "Falha:" + failed.getMessage() + failed.getClass());
		}
	}
}