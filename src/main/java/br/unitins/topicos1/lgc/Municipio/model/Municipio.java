package br.unitins.topicos1.lgc.Municipio.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Estado.model.Estado;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Municipio extends DefaultEntity{

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;


    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado(){
        return estado;
    }

    public void setEstado(Estado estado){
        this.estado = estado;
    }

}