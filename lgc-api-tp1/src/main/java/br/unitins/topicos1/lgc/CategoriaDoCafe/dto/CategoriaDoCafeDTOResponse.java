package br.unitins.topicos1.lgc.CategoriaDoCafe.dto;

import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;

public record CategoriaDoCafeDTOResponse(

    Long id,
    String nome,
    String descricao

) {
    
    public static CategoriaDoCafeDTOResponse valueOf(CategoriaDoCafe categoria){

        return new CategoriaDoCafeDTOResponse(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );

    }

}
