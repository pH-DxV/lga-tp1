package br.unitins.topicos1.lgc.Estado.dto;

import br.unitins.topicos1.lgc.Estado.model.Estado;
import br.unitins.topicos1.lgc.Regiao.model.Regiao;

public record EstadoDTOResponse(
    
    Long id,
    String nome,
    String sigla,
    Regiao regiao
    
) {  
    
    public static EstadoDTOResponse valueOf(Estado estado) {
        return new EstadoDTOResponse(
            estado.getId(),
            estado.getNome(),
            estado.getSigla(),
            estado.getRegiao()
        );

    }

}