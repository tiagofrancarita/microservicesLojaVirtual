package br.com.manomultimarcas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.manomultimarcas.model.Acesso;
import br.com.manomultimarcas.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso save(Acesso acesso) {
		
		/* Qualquer validação é feita aqui antes de salvar */
		return acessoRepository.save(acesso);
	}
}