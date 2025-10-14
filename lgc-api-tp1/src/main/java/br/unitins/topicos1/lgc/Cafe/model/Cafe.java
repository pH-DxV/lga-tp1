package br.unitins.topicos1.lgc.Cafe.model;

import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Marca.model.Marca;
import br.unitins.topicos1.lgc.NivelDeTorra.model.NivelDeTorra;
import br.unitins.topicos1.lgc.NotasSensoriais.model.NotasSensoriais;
import br.unitins.topicos1.lgc.RegiaoDeOrigem.model.RegiaoDeOrigem;
import br.unitins.topicos1.lgc.Tratamento.model.Tratamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cafe extends DefaultEntity {
    

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

   @ManyToOne
    @JoinColumn(name = "id_categoria_cafe")
    private CategoriaDoCafe categoriaDoCafe;

    @ManyToOne
    @JoinColumn(name = "id_regiao_origem")
    private RegiaoDeOrigem regiaoDeOrigem;


    // --- ATRIBUTOS ENUMERADOS ---

    @Enumerated(EnumType.STRING) // Salva o texto do enum no banco e não o número
    private NivelDeTorra nivelDeTorra;

    @Enumerated(EnumType.STRING)
    private NotasSensoriais notasSensoriais;

    @Enumerated(EnumType.STRING)
    private Tratamento tratamento;


    // --- ATRIBUTOS PRIMITIVOS ---

    @Column(name = "pontuacao_sca")
    private Integer pontuacaoSCA;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Double peso;

    @Column(nullable = false)
    private Integer estoque;


    // --- GET & SET ---
    public Marca getMarca() {
        return marca;
    }
    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public CategoriaDoCafe getCategoriaDoCafe() {
        return categoriaDoCafe;
    }
    public void setCategoriaDoCafe(CategoriaDoCafe categoriaDoCafe) {
        this.categoriaDoCafe = categoriaDoCafe;
    }

    public RegiaoDeOrigem getRegiaoDeOrigem() {
        return regiaoDeOrigem;
    }
    public void setRegiaoDeOrigem(RegiaoDeOrigem regiaoDeOrigem) {
        this.regiaoDeOrigem = regiaoDeOrigem;
    }

    public NivelDeTorra getNivelDeTorra() {
        return nivelDeTorra;
    }
    public void setNivelDeTorra(NivelDeTorra nivelDeTorra) {
        this.nivelDeTorra = nivelDeTorra;
    }

    public NotasSensoriais getNotasSensoriais() {
        return notasSensoriais;
    }
    public void setNotasSensoriais(NotasSensoriais notasSensoriais) {
        this.notasSensoriais = notasSensoriais;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }
    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }

    public Integer getPontuacaoSCA() {
        return pontuacaoSCA;
    }
    public void setPontuacaoSCA(Integer pontuacaoSCA) {
        this.pontuacaoSCA = pontuacaoSCA;
    }

    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getEstoque() {
        return estoque;
    }
    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }


    
}
