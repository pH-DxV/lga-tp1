package br.unitins.topicos1.lgc.Estoque.model;

import java.time.LocalDateTime;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Estoque extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_cafe", unique = true, nullable = false)
    private Cafe cafe;

    @Column(nullable = false)
    private Integer quantidade;

    private LocalDateTime dataUltimaMovimentacao;

    // Construtor vazio para JPA
    public Estoque() {}

    // Getters e Setters
    public Cafe getCafe() {
        return cafe;
    }
    public void setCafe(Cafe cafe) {
        this.cafe = cafe; 
    }

    public Integer getQuantidade() {
        return quantidade; 
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade; 
    }

    public LocalDateTime getDataUltimaMovimentacao() {
        return dataUltimaMovimentacao;
    }
    public void setDataUltimaMovimentacao(LocalDateTime dataUltimaMovimentacao) {
        this.dataUltimaMovimentacao = dataUltimaMovimentacao;
    }
}