package br.com.manomultimarcas.services;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.manomultimarcas.model.PessoaFisica;
import br.com.manomultimarcas.model.PessoaJuridica;
import br.com.manomultimarcas.model.Usuario;
import br.com.manomultimarcas.model.dto.CepDTO;
import br.com.manomultimarcas.model.dto.ConsultaCnpjDto;
import br.com.manomultimarcas.repository.PessoaFisicaRepository;
import br.com.manomultimarcas.repository.PessoaRepository;
import br.com.manomultimarcas.repository.UsuarioRepository;

@Service
public class PessoaUserService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	public PessoaJuridica salvarPessoaJuridica (PessoaJuridica pessoaJuridica) {
		
		for (int i = 0 ; i < pessoaJuridica.getEndereco().size(); i++) {
			
			pessoaJuridica.getEndereco().get(i).setPessoa(pessoaJuridica);
			pessoaJuridica.getEndereco().get(i).setEmpresa(pessoaJuridica);
			
		}
		pessoaJuridica = pessoaRepository.save(pessoaJuridica);
		
		Usuario usuarioPJ = usuarioRepository.findUseByPessoa(pessoaJuridica.getId(), pessoaJuridica.getEmail());
		
		if (usuarioPJ == null ) {
			
			String constraint = usuarioRepository.consultaConstraintRole();
			if (constraint != null) {
				jdbcTemplate.execute("begin; ALTER TABLE usuario_acesso DROP CONSTRAINT " + constraint + " ; commit ;");
			}
			
			usuarioPJ = new Usuario();
			usuarioPJ.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPJ.setEmpresa(pessoaJuridica);
			usuarioPJ.setPessoa(pessoaJuridica);
			usuarioPJ.setLogin(pessoaJuridica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPJ.setSenha(senhaCript);
			
			usuarioPJ = usuarioRepository.save(usuarioPJ);
			
			usuarioRepository.cadastroAcessoBasicoUsuario(usuarioPJ.getId());
			usuarioRepository.cadastroAcessoUserPJ(usuarioPJ.getId(),"ROLE_ADMIN");
			
			StringBuilder mensagemHtml = new StringBuilder();
			
			mensagemHtml.append("<b>Segue abaixo os dados de acesso ao sistema. </b> <br/>");
			mensagemHtml.append("<b>Login:</b>" + pessoaJuridica.getEmail()+"<br/>");
			mensagemHtml.append("<b>Senha:</b>").append(senha).append("<br/> <br/>");
			mensagemHtml.append("<b>Atenciosamente,</b><br/>");
			mensagemHtml.append("<b>Loja Xpto</b>");
			
			try {
				serviceSendEmail.enviaEmailHtml("Acesso ao sistema", mensagemHtml.toString(), pessoaJuridica.getEmail());
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (MessagingException e) {
			
				e.printStackTrace();
			} 
		}
		return pessoaJuridica;
		
	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica pessoaFisica) {
		
		for (int i = 0 ; i < pessoaFisica.getEndereco().size(); i++) {
			
			pessoaFisica.getEndereco().get(i).setPessoa(pessoaFisica);
			//pessoaFisica.getEndereco().get(i).setEmpresa(pessoaFisica);
			
		}
		
		pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
		
		Usuario usuarioPF = usuarioRepository.findUseByPessoa(pessoaFisica.getId(), pessoaFisica.getEmail());
		
		if (usuarioPF == null ) {
			
			String constraint = usuarioRepository.consultaConstraintRole();
			if (constraint != null) {
				jdbcTemplate.execute("begin; ALTER TABLE usuario_acesso DROP CONSTRAINT " + constraint + " ; commit ;");
			}
			
			usuarioPF = new Usuario();
			usuarioPF.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPF.setEmpresa(pessoaFisica.getEmpresa());
			usuarioPF.setPessoa(pessoaFisica);
			usuarioPF.setLogin(pessoaFisica.getEmail());
			
			String senha = "" + Calendar.getInstance().getTimeInMillis();
			String senhaCript = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPF.setSenha(senhaCript);
			
			usuarioPF = usuarioRepository.save(usuarioPF);
			
			usuarioRepository.cadastroAcessoBasicoUsuario(usuarioPF.getId());
			//usuarioRepository.cadastroAcessoUserPJ(usuarioPJ.getId(),"ROLE_ADMIN");
			
			StringBuilder mensagemHtml = new StringBuilder();
			
			mensagemHtml.append("<b>Segue abaixo os dados de acesso ao sistema. </b> <br/>");
			mensagemHtml.append("<b>Login:</b>" + pessoaFisica.getEmail()+"<br/>");
			mensagemHtml.append("<b>Senha:</b>").append(senha).append("<br/> <br/>");
			mensagemHtml.append("<b>Atenciosamente,</b><br/>");
			mensagemHtml.append("<b>Loja Xpto</b>");
			
			try {
				serviceSendEmail.enviaEmailHtml("Acesso ao sistema", mensagemHtml.toString(), pessoaFisica.getEmail());
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (MessagingException e) {
			
				e.printStackTrace();
			} 
		}
		return pessoaFisica;
	}
	
	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/"+ cep +"/json/", CepDTO.class).getBody();
		
	}
	
	public ConsultaCnpjDto consultaCnpjReceitaWS(String cnpj) {
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDto.class).getBody();
	}
}