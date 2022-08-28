package br.com.manomultimarcas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.manomultimarcas.model.Usuario;
import br.com.manomultimarcas.repository.UsuarioRepository;

/*
 * Implementacao de detalhes do usuario
 */
@Service
public class ImplementacaoUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(ImplementacaoUserDetailsService.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		logger.info("Inicio do carregamento do usuário...");
		
		Usuario usuario = usuarioRepository.findUserByLogin(username); // Recebe o login para consulta */
		
		if (usuario == null) {
			logger.info("Falha no processo de carregamento");
			logger.error("Usuario não encontrado na base.");
			throw new UsernameNotFoundException("Usuario não encontrado na base.");
			
		}
		logger.info("Usuário carregado com sucesso.");
		logger.info("Fim do processo de carregamento do usuário.");
		return new User(usuario.getLogin(), usuario.getPassword(), usuario.getAuthorities());
		
	}
}