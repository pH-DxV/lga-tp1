package br.unitins.topicos1.lgc.Cafe.model;

// Imports das entidades de relacionamento
import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Marca.model.Marca;
import br.unitins.topicos1.lgc.RegiaoDeOrigem.model.RegiaoDeOrigem;

// Imports dos Enums simples
import br.unitins.topicos1.lgc.NivelDeTorra.model.NivelDeTorra;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.Tratamento.model.Tratamento;

// --- Imports Adicionados para a nova coleção de Notas ---
import java.util.Set; // Para a coleção de múltiplas notas
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
// --- Fim dos Imports Adicionados ---

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cafe extends DefaultEntity {
    
    // --- CAMPOS ADICIONADOS ---
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;
    
    // --- FIM DA ADIÇÃO ---

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

    @Enumerated(EnumType.STRING) 
    private NivelDeTorra nivelDeTorra;

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
    
    // --- COLEÇÃO DE NOTAS SENSORIAIS ---
    
    @ElementCollection(fetch = FetchType.EAGER) 
    @CollectionTable(name = "cafe_notasensorial",
                     joinColumns = @JoinColumn(name = "id_cafe"))
    @Enumerated(EnumType.STRING) 
    @Column(name = "nota_sensorial", length = 30)
    private Set<NotaSensorial> notasSensoriais;

    // --- CONSTRUTOR VAZIO ---
    public Cafe() {
    }

    // --- GETTERS & SETTERS ---

    // --- GETTERS E SETTERS ADICIONADOS ---
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
    // --- FIM DA ADIÇÃO ---

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

    public Set<NotaSensorial> getNotasSensoriais() {
        return notasSensoriais;
    }
    public void setNotasSensoriais(Set<NotaSensorial> notasSensoriais) {
        this.notasSensoriais = notasSensoriais;
    }
}