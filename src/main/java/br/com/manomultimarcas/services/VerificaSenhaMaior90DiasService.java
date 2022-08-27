package br.com.manomultimarcas.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import br.com.manomultimarcas.model.Usuario;
import br.com.manomultimarcas.repository.UsuarioRepository;

@Service
public class VerificaSenhaMaior90DiasService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	//@Scheduled(initialDelay = 2000, fixedDelay = 86400000) /*Roda a cada 24h*/
	@Scheduled(cron = "0 0 11 * * *", zone ="America/Sao_Paulo" )/*Irá rodar todo dia as 11h da manhã, horario de brasilia-SP*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		
		for (Usuario usuario : usuarios) {
			
			StringBuilder msgNotificacaoSenha = new StringBuilder();
			msgNotificacaoSenha.append("Olá,  ").append(usuario.getPessoa().getNome()).append("<br/>");
			msgNotificacaoSenha.append("Está na hora de renovar sua senha, já passou 90 dias de validade.").append("<br/>");
			msgNotificacaoSenha.append("Troque sua senha na loja XPTO.");
			serviceSendEmail.enviaEmailHtml("Troca de senha", msgNotificacaoSenha.toString(), usuario.getLogin());
			Thread.sleep(5000);
			
		}
	}
}