package br.unitins.topicos1.lgc.Municipio.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import jakarta.persistence.Entity;


@Entity
public class Municipio extends DefaultEntity{

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}