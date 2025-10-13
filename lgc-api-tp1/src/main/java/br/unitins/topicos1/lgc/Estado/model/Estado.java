package br.unitins.topicos1.lgc.Estado.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Regiao.model.Regiao;
import jakarta.persistence.Entity;

@Entity
public class Estado extends DefaultEntity{

    private String nome;
    private String sigla;
    private Regiao regiao;


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public Regiao getRegiao() {
        return regiao;
    }
    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }
    
}
