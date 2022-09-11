package br.com.manomultimarcas.repository;

import br.com.manomultimarcas.model.VendaCompraLojaVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VendaCompraLojaVirtualRepository extends JpaRepository<VendaCompraLojaVirtual, Long> {

    @Query(value = "SELECT a FROM VendaCompraLojaVirtual a  WHERE a.id = ?1 and a.excluido = false ")
    VendaCompraLojaVirtual findByIdExclusao(Long id);

    @Query(value="select i.vendacompralojavirtual from ItemVendaLoja i where "
            + " i.vendacompralojavirtual.excluido = false and i.produto.id = ?1")
    List<VendaCompraLojaVirtual> vendaPorProduto(Long idProduto);


}