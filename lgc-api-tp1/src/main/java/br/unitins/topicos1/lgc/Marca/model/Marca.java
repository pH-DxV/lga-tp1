package br.unitins.topicos1.lgc.Marca.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Marca extends DefaultEntity {
    
    @Column(unique = true, nullable = false, length = 60)
    private String nome;

    @Column(length = 255)
    private String descricao;


    public Marca(){
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
