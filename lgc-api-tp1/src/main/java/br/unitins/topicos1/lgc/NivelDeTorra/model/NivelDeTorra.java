package br.unitins.topicos1.lgc.NivelDeTorra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = Shape.OBJECT)
public enum NivelDeTorra {

    CLARA(1L, "Clara"),
    MEDIA_CLARA(2L, "Média-Clara"),
    MEDIA(3L, "Média"),
    MEDIA_ESCURA(4L, "Média-Escura"),
    ESCURA(5L, "Escura");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    NivelDeTorra(long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    // Método 'valueOf' para o AttributeConverter
    public static NivelDeTorra valueOf(Long id) {
        if (id == null) {
            return null;
        }
        for (NivelDeTorra nivel : NivelDeTorra.values()) {
            if (id.equals(nivel.ID)) { // Use .equals() para comparar Long
                return nivel;
            }
        }
        // Lança uma exceção se o ID for inválido
        throw new IllegalArgumentException("ID de NivelDeTorra inválido: " + id);
    }
}