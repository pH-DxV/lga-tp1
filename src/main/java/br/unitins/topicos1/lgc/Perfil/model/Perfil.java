package br.unitins.topicos1.lgc.Perfil.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
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
        if (id == null) return null;
        for (Perfil perfil : Perfil.values()) {
            if (id.equals(perfil.ID)) return perfil;
        }
        throw new IllegalArgumentException("id inválido");
    }

    // --- ADICIONE ESTE MÉTODO ---
    @JsonCreator
    public static Perfil fromObject(Map<String, Object> obj) {
        if (obj != null && obj.containsKey("id")) {
            Number id = (Number) obj.get("id");
            return valueOf(id.longValue());
        }
        return null;
    }
    // ---------------------------
}