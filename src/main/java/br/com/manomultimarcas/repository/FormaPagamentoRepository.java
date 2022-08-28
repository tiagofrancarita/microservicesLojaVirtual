package br.com.manomultimarcas.repository;


import br.com.manomultimarcas.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento,Long> {

    @Query("select a from FormaPagamento a where upper(trim(a.descricao)) like %?1%")
    List<FormaPagamento> buscarFormaPagamento(String desc);
}
