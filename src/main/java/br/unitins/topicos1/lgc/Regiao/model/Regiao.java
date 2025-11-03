package br.unitins.topicos1.lgc.Regiao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Regiao {

    CENTRO_OESTE(1, "Centro-Oeste"),
    NORDESTE(2, "Nordeste"),
    NORTE(3, "Norte"),
    SUDESTE(4, "Sudeste"),
    SUL(5, "Sul");


    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    Regiao(long id, String nome){
        this.ID = id;
        this.NOME = nome;

    }

    public static Regiao valueOf (Long id){
        for (Regiao regiao : Regiao.values()){
            if (id == regiao.ID)
            return regiao;
        }
        return null;
    }
    
}
