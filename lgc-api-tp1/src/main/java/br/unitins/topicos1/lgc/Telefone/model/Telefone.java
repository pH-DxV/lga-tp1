package br.unitins.topicos1.lgc.Telefone.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Telefone extends DefaultEntity {

    @Column(name = "ddd", nullable = false)
    private String ddd;

    @Column(name = "numero", nullable = false)
    private String numero;


    // GET E SET

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    
    
}
