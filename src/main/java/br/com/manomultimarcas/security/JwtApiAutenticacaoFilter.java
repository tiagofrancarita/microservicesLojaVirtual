package br.com.manomultimarcas.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtApiAutenticacaoFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtApiAutenticacaoFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
		/*Estabelece a autenticacao do usuário.  */
		Authentication authentication = new JWTTokenAutenticacaoService()
																	.getAuthentication((HttpServletRequest)request, (HttpServletResponse)response);
		logger.info("Autenticação estabelecida.");
	
		/*Processo de autenticacao para o spring security*/
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
		
		}catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("Ocorreu um erro no sistema, por favor entre em contato com o suporte T.I.   \n " +  
															e.getMessage() );
			logger.error("Ocorreu um erro no sistema, por favor entre em contato com o suporte T.I.   \n " + e.getMessage() + e.getCause());
			
		}	
	}
}