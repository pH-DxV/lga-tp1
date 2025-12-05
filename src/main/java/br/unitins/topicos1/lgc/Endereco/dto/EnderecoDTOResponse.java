package br.unitins.topicos1.lgc.Endereco.dto;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;

public record EnderecoDTOResponse(
    Long id,
    String cep,
    String rua,
    String numero,
    String complemento,
    String bairro,
    MunicipioDTOResponse municipio
) {
    public static EnderecoDTOResponse valueOf(Endereco e) {
        return new EnderecoDTOResponse(
            e.getId(),
            e.getCep(),
            e.getRua(),
            e.getNumero(),
            e.getComplemento(),
            e.getBairro(),
            MunicipioDTOResponse.valueOf(e.getMunicipio())
        );
    }
}