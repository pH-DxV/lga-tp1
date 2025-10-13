package br.unitins.topicos1.lgc.Municipio.dto;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;

public record MunicipioDTOResponse(
    
    Long id,
    String nome,
    EstadoDTOResponse estado) {  
    
    public static MunicipioDTOResponse valueOf(Municipio municipio) {
        return new MunicipioDTOResponse(
            municipio.getId(),
            municipio.getNome(),
            EstadoDTOResponse.valueOf(municipio.getEstado())
        );
    }
}