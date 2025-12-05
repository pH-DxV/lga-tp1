package br.unitins.topicos1.lgc.RegiaoDeOrigem.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity; // IMPORTANTE: Importe esta anotação

@Entity // IMPORTANTE: Adicione esta anotação acima da classe
public class RegiaoDeOrigem extends DefaultEntity {

    @Column(nullable = false, length = 100)
    private String nome;

    // Outros atributos que você possa ter definido (país, descrição, etc.)
    // Exemplo:
    // private String pais;

    // --- GETTERS E SETTERS ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    // ... getters e setters para outros campos ...
}