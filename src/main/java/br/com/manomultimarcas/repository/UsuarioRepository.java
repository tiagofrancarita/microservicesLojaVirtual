package br.com.manomultimarcas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.manomultimarcas.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.dataAtualSenha <= current_date - 90")
	List<Usuario> usuarioSenhaVencida();
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.login = ?1")
	Usuario findUserByLogin(String login);

	@Query(value ="SELECT u from Usuario u WHERE u.pessoa.id = ?1 or u.pessoa.email = ?2" )
	Usuario findUseByPessoa(Long id, String email);

	@Query(value =" SELECT constraint_name from information_schema.constraint_column_usage "
								+ " WHERE table_name = 'usuario_acesso' and column_name = 'acessoid' "
								+ " and constraint_name <> 'unique_acesso_user' ", nativeQuery = true )
	String consultaConstraintRole();
	
	/**
	 * 
	 * Ao recriar o banco para que a query abaixo funcione, criar na tabela acesso,
	 *  um acesso com a descrição 'ROLE_USER' e 'ROLE_ADMIN'.
	 * 
	 * */

	@Transactional
	@Modifying
	@Query(value = " INSERT INTO usuario_acesso (usuarioid, acessoid) VALUES(?1, "
			+ "(SELECT id  FROM acesso WHERE descricao = 'ROLE_USER' ))", nativeQuery = true)
	void cadastroAcessoBasicoUsuario(Long id);
	
	@Transactional
	@Modifying
	@Query(value = " INSERT INTO usuario_acesso (usuarioid, acessoid) VALUES(?1, "
			+ "(SELECT id  FROM acesso WHERE descricao = ?2 ))", nativeQuery = true)
	void cadastroAcessoUserPJ(Long id, String acesso);
		
}