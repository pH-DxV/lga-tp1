package br.unitins.topicos1.lgc.Usuario.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;

public record UsuarioDTOResponse(
    Long id,
    String nome,
    String cpf,
    LocalDate dataNascimento,
    Double peso,
    List<TelefoneDTOResponse> telefone,
    List<EnderecoDTOResponse> endereco
) {
    public static UsuarioDTOResponse valueOf(Usuario usuario) {
        
        // Converte a lista de Model Telefone para DTO Telefone
        List<TelefoneDTOResponse> tels = usuario.getTelefones() == null ? null : 
            usuario.getTelefones().stream()
            .map(TelefoneDTOResponse::valueOf)
            .collect(Collectors.toList());
        
        // Converte a lista de Model Endereco para DTO Endereco
        List<EnderecoDTOResponse> ends = usuario.getEnderecos() == null ? null :
            usuario.getEnderecos().stream()
            .map(EnderecoDTOResponse::valueOf)
            .collect(Collectors.toList());

        return new UsuarioDTOResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getDataNascimento(),
            usuario.getPeso(),
            tels,
            ends
        );
    }
}