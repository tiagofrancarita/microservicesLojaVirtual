package br.com.manomultimarcas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContagemApiService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void contagemEndPointPF() {
		jdbcTemplate.execute("begin; UPDATE tabela_acesso_end_point SET qtd_acesso_end_point = qtd_acesso_end_point + 1 WHERE nome_end_point = 'END-POINT-NOME-PESSOA-FISICA'; commit;");	
	}
}