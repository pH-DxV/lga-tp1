package br.unitins.topicos1.lgc.Usuario.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;

public record UsuarioDTOResponse(
    Long id,
    String nome,
    String login, // Adicionado
    String cpf,
    LocalDate dataNascimento,
    Double peso,
    Set<Perfil> perfis, // Adicionado: Retorna os perfis (User, Adm)
    List<TelefoneDTOResponse> telefones,
    List<EnderecoDTOResponse> enderecos
) {
    public static UsuarioDTOResponse valueOf(Usuario usuario) {
        
        List<TelefoneDTOResponse> tels = usuario.getTelefones() == null ? null : 
            usuario.getTelefones().stream().map(TelefoneDTOResponse::valueOf).collect(Collectors.toList());
        
        List<EnderecoDTOResponse> ends = usuario.getEnderecos() == null ? null :
            usuario.getEnderecos().stream().map(EnderecoDTOResponse::valueOf).collect(Collectors.toList());

        return new UsuarioDTOResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.getLogin(), // Adicionado
            usuario.getCpf(),
            usuario.getDataNascimento(),
            usuario.getPeso(),
            usuario.getPerfis(), // Adicionado
            tels,
            ends
        );
    }
}