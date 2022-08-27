package br.com.manomultimarcas.model.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class ImagemProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String imagemOriginal;

    private String imagemMiniatura;

    private Long Produto;

    private Long Empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagemOriginal() {
        return imagemOriginal;
    }

    public void setImagemOriginal(String imagemOriginal) {
        this.imagemOriginal = imagemOriginal;
    }

    public String getImagemMiniatura() {
        return imagemMiniatura;
    }

    public void setImagemMiniatura(String imagemMiniatura) {
        this.imagemMiniatura = imagemMiniatura;
    }

    public Long getProduto() {
        return Produto;
    }

    public void setProduto(Long produto) {
        Produto = produto;
    }

    public Long getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(Long empresa) {
        Empresa = empresa;
    }
}
