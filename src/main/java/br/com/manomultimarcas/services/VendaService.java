package br.com.manomultimarcas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void exclusaoLogicaVenda(Long idVenda) {

        String exclusaoLogicaVenda =
                        "begin;"
                        + " UPDATE vd_cp_loja_virtual SET excluido= true WHERE id =" + idVenda+" ; "
                        + " commit;";

        jdbcTemplate.execute(exclusaoLogicaVenda);

    }


    public void deletaTotalVenda(Long idVenda) {

        String exclusaoTotalVenda =
                                    "begin;"
                                    + " UPDATE nota_fiscal_venda SET venda_compra_loja_virtualid= null WHERE venda_compra_loja_virtualid =" + idVenda+" ; "
                                    + " DELETE FROM nota_fiscal_venda WHERE venda_compra_loja_virtualid =" + idVenda +" ; "
                                    + " DELETE FROM item_venda_loja WHERE venda_compra_loja_virtualid   =" + idVenda +" ; "
                                    + " DELETE FROM status_rastreio WHERE venda_compra_loja_virtualid   =" + idVenda +" ; "
                                    + " DELETE FROM vd_cp_loja_virtual WHERE id =" + idVenda +" ; "
                                    + " commit;";

        jdbcTemplate.execute(exclusaoTotalVenda);

    }

}
