package br.unitins.topicos1.lgc.Perfil.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Perfil {

    ADM (1L, "Administrador"),
    USER (2L, "Usuario");

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("label")
    public final String LABEL;

    Perfil(Long id, String label) {
        this.ID = id;
        this.LABEL = label;
    }

    public static Perfil valueOf(Long id) {
        if (id == null)
            return null;
        
        for (Perfil perfil : Perfil.values())
            if (perfil.ID.equals(id))
                return perfil;
        
        throw new IllegalArgumentException("id invalido");
    }

}