package br.unitins.topicos1.lgc.Cliente.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
    
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome,

    @NotBlank(message = "O login não pode ser nulo.")
    String login,

    @NotBlank(message = "A senha não pode ser nula.")
    String senha,

    @NotBlank(message = "O CPF não pode ser nulo.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    String cpf,

    // SEM idPerfil, pois o perfil é forçado como USER no Service.

    LocalDate dataNascimento,
    

    @NotNull(message = "É obrigatório informar ao menos um telefone.")
    @Size(min = 1, message = "Informe pelo menos um telefone.")
    @Valid // Valida o conteúdo da lista (os TelefoneDTOs)
    List<TelefoneDTO> telefones,

    @NotNull(message = "É obrigatório informar ao menos um endereço.")
    @Size(min = 1, message = "Informe pelo menos um endereço.")
    @Valid // Valida o conteúdo da lista (os EnderecoDTOs)
    List<EnderecoDTO> enderecos
) {}