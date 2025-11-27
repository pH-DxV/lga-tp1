package br.unitins.topicos1.lgc.Cliente.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;

public record ClienteDTOResponse(
    Long id,
    String nome,
    String login,
    String cpf,
    LocalDate dataNascimento,
    // Peso removido daqui (removido o campo "Double peso")
    Set<Perfil> perfis,
    List<TelefoneDTOResponse> telefones,
    List<EnderecoDTOResponse> enderecos
) {
    public static ClienteDTOResponse valueOf(Cliente cliente) {
        
        // Conversão segura da lista de Telefones
        List<TelefoneDTOResponse> tels = cliente.getTelefones() == null ? List.of() : 
            cliente.getTelefones().stream().map(TelefoneDTOResponse::valueOf).collect(Collectors.toList());
        
        // Conversão segura da lista de Endereços
        List<EnderecoDTOResponse> ends = cliente.getEnderecos() == null ? List.of() :
            cliente.getEnderecos().stream().map(EnderecoDTOResponse::valueOf).collect(Collectors.toList());

        return new ClienteDTOResponse(
            cliente.getId(),
            cliente.getNome(),
            cliente.getLogin(),
            cliente.getCpf(),
            cliente.getDataNascimento(),
            // cliente.getPeso() removido daqui
            cliente.getPerfis(),
            tels,
            ends
        );
    }
}