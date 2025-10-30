package br.unitins.topicos1.lgc.NotaSensorial.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.unitins.topicos1.lgc.CategoriaSensorial.model.CategoriaSensorial;

import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum NotaSensorial {

    // 1. Doces / Açucaradas
    MEL(1L, "Mel", CategoriaSensorial.DOCES),
    CARAMELO(2L, "Caramelo", CategoriaSensorial.DOCES),
    ACUCAR_MASCAVO(3L, "Açúcar mascavo", CategoriaSensorial.DOCES),
    CHOCOLATE_AO_LEITE(4L, "Chocolate ao leite", CategoriaSensorial.DOCES),
    CHOCOLATE_AMARGO(5L, "Chocolate amargo", CategoriaSensorial.DOCES),
    BAUNILHA(6L, "Baunilha", CategoriaSensorial.DOCES),

    // 2. Frutadas
    FRUTAS_VERMELHAS(7L, "Frutas vermelhas", CategoriaSensorial.FRUTADAS),
    FRUTAS_CITRICAS(8L, "Frutas cítricas", CategoriaSensorial.FRUTADAS),
    FRUTAS_TROPICAIS(9L, "Frutas tropicais", CategoriaSensorial.FRUTADAS),
    MACA_VERDE(10L, "Maçã verde", CategoriaSensorial.FRUTADAS),
    UVA(11L, "Uva", CategoriaSensorial.FRUTADAS),
    DAMASCO(12L, "Damasco", CategoriaSensorial.FRUTADAS),
    PESSEGO(13L, "Pêssego", CategoriaSensorial.FRUTADAS),

    // 3. Nozes e Castanhas
    AMENDOAS(14L, "Amêndoas", CategoriaSensorial.NOZES),
    AVELA(15L, "Avelã", CategoriaSensorial.NOZES),
    NOZ(16L, "Noz", CategoriaSensorial.NOZES),
    CASTANHA_DO_PARA(17L, "Castanha-do-pará", CategoriaSensorial.NOZES),
    AMENDOIM(18L, "Amendoim", CategoriaSensorial.NOZES),

    // ... (e assim por diante para todas as outras categorias) ...

    VINHO_TINTO(30L, "Vinho tinto", CategoriaSensorial.FERMENTADAS); // Exemplo da última

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    @JsonProperty("categoria")
    public final CategoriaSensorial CATEGORIA;

    NotaSensorial(Long id, String nome, CategoriaSensorial categoria) {
        this.ID = id;
        this.NOME = nome;
        this.CATEGORIA = categoria;
    }
    
    // Este valueOf NÃO será usado pelo AttributeConverter, 
    // mas pode ser útil para buscas internas se necessário.
    public static NotaSensorial valueOf(Long id) {
        if (id == null) return null;
        for (NotaSensorial nota : NotaSensorial.values()) {
            if (id.equals(nota.ID)) return nota;
        }
        throw new IllegalArgumentException("ID de NotaSensorial inválido: " + id);
    }
}
