package br.unitins.topicos1.lgc.Tratamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum Tratamento {

    NATURAL(1L, "Natural (Seco)", "Fruto seco inteiro ao sol, sem remoção da casca."),
    LAVADO(2L, "Lavado (Wet Process)", "Polpa removida antes da secagem, grão lavado."),
    HONEY(3L, "Honey (Mucilage Process)", "Polpa removida, mas a mucilagem (mel) é mantida durante a secagem."),
    SEMI_LAVADO(4L, "Semi-Lavado", "Processo intermediário, parte da mucilagem é removida mecanicamente."),
    DESCASCADO_NATURAL(5L, "Descascado Natural", "Casca removida e o grão seco ainda com a mucilagem."),
    ANAEROBICO(6L, "Anaeróbico", "Fermentação controlada em tanques sem oxigênio."),
    FERMENTACAO_CONTROLADA(7L, "Fermentação Controlada (Aeróbica)", "Fermentação com oxigênio, monitorando tempo e pH."),
    EXPERIMENTAL(8L, "Experimental / Híbrido", "Combinação de diferentes métodos ou técnicas inovadoras.");


    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;
    
    // --- CAMPO ADICIONADO ---
    @JsonProperty("descricao")
    public final String DESCRICAO;

    // --- CONSTRUTOR ATUALIZADO ---
    Tratamento(long id, String nome, String descricao) {
        this.ID = id;
        this.NOME = nome;
        this.DESCRICAO = descricao;
    }

    // Método 'valueOf' (para o Converter)
    public static Tratamento valueOf(Long id) {
        if (id == null) {
            return null;
        }
        for (Tratamento tratamento : Tratamento.values()) {
            if (id.equals(tratamento.ID)) {
                return tratamento;
            }
        }
        throw new IllegalArgumentException("ID de Tratamento inválido: " + id);
    }
}