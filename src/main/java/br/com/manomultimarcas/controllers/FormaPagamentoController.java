package br.com.manomultimarcas.controllers;

import br.com.manomultimarcas.model.Acesso;
import br.com.manomultimarcas.model.FormaPagamento;
import br.com.manomultimarcas.repository.FormaPagamentoRepository;
import br.com.manomultimarcas.util.ExceptionLojaVirtual;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FormaPagamentoController {

    private static final Logger logger = LoggerFactory.getLogger(FormaPagamentoController.class);

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarFormaPagamento")
    public ResponseEntity<FormaPagamento> salvarFormaPagamento(@RequestBody FormaPagamento formaPagamento) throws ExceptionLojaVirtual {// Recebe o json e converte para objeto

        logger.info("Processo de cadastro de forma de pagamento iniciado.");

        if (formaPagamento.getId() == null) {

            List<FormaPagamento> formaPagamentos = formaPagamentoRepository.buscarFormaPagamento(formaPagamento.getDescricao().toUpperCase());

            if (!formaPagamentos.isEmpty()) {
                logger.error("Processo de cadastro de forma de pagamento encerrado com erro.");
                logger.error("forma de pagamento já cadastrada.");
                throw new ExceptionLojaVirtual("forma de pagamento já cadastrada." + formaPagamento.getDescricao());
            }
        }

        logger.info("Acesso cadastrado.");
        logger.info("Processo de cadastro de acesso finalizado com sucesso.");
        FormaPagamento formaPagamentoSalva = formaPagamentoRepository.save(formaPagamento);

        return new ResponseEntity<FormaPagamento>(formaPagamentoSalva, HttpStatus.OK);
    }
}