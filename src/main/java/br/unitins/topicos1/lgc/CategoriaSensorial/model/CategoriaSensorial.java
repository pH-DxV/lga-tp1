package br.unitins.topicos1.lgc.CategoriaSensorial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum CategoriaSensorial {

    DOCES(1L, "Doces / Açucaradas", "Notas agradáveis, suaves e que remetem ao dulçor natural do café."),
    FRUTADAS(2L, "Frutadas", "Remetem a frutas frescas ou secas."),
    NOZES(3L, "Nozes e Castanhas", "Mais comuns em torras médias e escuras."),
    ESPECIARIAS(4L, "Especiarias e Ervas", "Podem remeter a temperos ou ervas secas."),
    FLORAIS(5L, "Florais", "Comuns em cafés etíopes e torras claras."),
    AMADEIRADAS(6L, "Amadeiradas / Defumadas", "Mais perceptíveis em torras escuras."),
    CEREAL(7L, "Cereal / Panificação", "Notas de pão, malte, cereais."),
    FERMENTADAS(8L, "Fermentadas / Complexas", "Mais raras, encontradas em cafés fermentados.");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    @JsonProperty("descricao")
    public final String DESCRICAO;

    CategoriaSensorial(Long id, String nome, String descricao) {
        this.ID = id;
        this.NOME = nome;
        this.DESCRICAO = descricao;
    }
}