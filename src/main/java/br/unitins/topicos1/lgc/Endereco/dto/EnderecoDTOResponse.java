package br.unitins.topicos1.lgc.Endereco.dto;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;

public record EnderecoDTOResponse(
    Long id,
    String cep,
    String rua,
    String complemento
) {
    // O método 'valueOf' que faz a conversão
    public static EnderecoDTOResponse valueOf(Endereco endereco) {
        return new EnderecoDTOResponse(
            endereco.getId(),
            endereco.getCep(),
            endereco.getRua(),
            endereco.getComplemento()
        );
    }
}