package br.com.manomultimarcas.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import br.com.manomultimarcas.model.Usuario;
import br.com.manomultimarcas.repository.UsuarioRepository;
import br.com.manomultimarcas.util.ApplicationContextLoad;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/* 
 * Criar a autenticacao e retorna a autenticacao JWT.
 */

@Service
@Component
public class JWTTokenAutenticacaoService {
	
	private static final Logger logger = LoggerFactory.getLogger(JWTTokenAutenticacaoService.class);
	
	/*Token de validade 11 dias.. realizar a conversao do tempo para milessegundos 11 dias --> 959990000*/
	/*Tokemn expira em 10 minutos. */
	private static final long EXPIRATION_TIME = 600000;
	
	/*Chave de senha pra unir com o JWT */
	private static final String SECRET = "83cb3e74a81e53350dc27e6a368fc3c2";
	/* Prefixo do token */
	private static final String TOKEN_PREFIX = "Bearer";
	
	/*Retorno da requisição  */
	private static final String HEADER_STRING = "Authorization";
	
	
	// Metodo para gerar o token e resposta para o client (front)
	public  void addAuthentication(HttpServletResponse response, String username)throws Exception {
		
		// Montagem do token
		
		String JWT  = Jwts.builder() //Chama o gerador de token
				                 .setSubject(username) // Adiciona o user
				                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Tempo de expiracao.
				                 .signWith(SignatureAlgorithm.HS512, SECRET).compact(); // Faz a compactação e gera o token.
		
		// Exemplo:.. Bearer *-/a*iuudhfysfgsdyufgdsyufgdsyufgdsygdsfgds.f37ghf64634g76f3g4f6743g43*/
		String token = TOKEN_PREFIX + " " + JWT;
		
		// Envia a resposta para o client (front), API...
		response.addHeader(HEADER_STRING, token);
		
		liberacaoCors(response);
		
		// Usado para visualizar no postman para teste.
		response.getWriter().write("{\"Authorization\": \"" + token +"\"}");		                
	}
	
	//Retorna o usuario validado com o token ou caso nao seja valido retorna null.
	public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		
		
		String token = request.getHeader(HEADER_STRING);
		try {
			
		if (token != null) {
			
			String tokenPuro = token.replace(TOKEN_PREFIX, "").trim();
			
			/* Faz validacao do token do usuario da requisicao e obtem o user   */
			String user = Jwts.parser()
								.setSigningKey(SECRET)
								.parseClaimsJws(tokenPuro)
								.getBody().getSubject(); // Faz a decodificacao do token e extrai o usuario.
			
			if (user != null) {
				
				Usuario usuario = ApplicationContextLoad
											.getApplicationContext()
											.getBean(UsuarioRepository.class)
											.findUserByLogin(user);
				
				if (usuario != null) {
					
					return new UsernamePasswordAuthenticationToken(
																									usuario.getLogin(), 
																									usuario.getSenha(),
																									usuario.getAuthorities());
				}		
			}		
		}	
		}catch (SignatureException signatureExcep) {
			response.getWriter().write("O token informado é inválido." + signatureExcep);
			logger.error("O token informado é inválido." + signatureExcep);
			
		}catch (ExpiredJwtException expiredTokenExcep) {
			response.getWriter().write("O token informado expirou." + "Erro: " +  expiredTokenExcep);
			logger.error("O token informado expirou." + "Erro: " + expiredTokenExcep);
		}finally {
			liberacaoCors(response);
		}
		
		return null;
	}
	 // Liberacao para erro de cors no navegador.
	private void liberacaoCors(HttpServletResponse response) {
		
		if (response.getHeader("Acess-Control-Allow-Origin") == null) {
			response.addHeader("Acess-Control-Allow-Origin", "*");
		}
		
		if (response.getHeader("Acess-Control-Allow-Headers") == null) {
			response.addHeader("Acess-Control-Allow-Headers", "*");
		}
		
		if (response.getHeader("Acess-Control-Request-Headers") == null) {
			response.addHeader("Acess-Control-Request-Headers", "*");
		}
		
		if (response.getHeader("Acess-Control-Allow-Method") == null) {
			response.addHeader("Acess-Control-Allow-Method", "*");
		}
	}
}