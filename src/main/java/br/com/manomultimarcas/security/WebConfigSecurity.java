package br.com.manomultimarcas.security;

import javax.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import br.com.manomultimarcas.services.ImplementacaoUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter implements HttpSessionListener {
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository
																.withHttpOnlyFalse())
																.disable().authorizeRequests().antMatchers("/").permitAll()
																.antMatchers("/index").permitAll()
																.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
																.anyRequest().authenticated().and().logout().logoutSuccessUrl("/index")
																/*Redireciona ou retorna para o index quando o usuario desloga.*/
																
																.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
																/*Mapeamento de logout do sistema.*/
																
																.and().addFilterAfter(new JWTLoginFilter("/login", authenticationManager()),
																		UsernamePasswordAuthenticationFilter.class)
																.addFilterBefore(new JwtApiAutenticacaoFilter(), 
																		UsernamePasswordAuthenticationFilter.class);
																/*Filtra requisições pra login com o JWT*/
	}
	
	// Consultar o user no banco com o spring security
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
// Ignora algumas urls livre de autenticacao.
	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers(HttpMethod.GET, "/salvarAcesso", "/deletarAcesso")
		//.antMatchers(HttpMethod.POST, "/salvarAcesso", "/deletarAcesso");
	}
}