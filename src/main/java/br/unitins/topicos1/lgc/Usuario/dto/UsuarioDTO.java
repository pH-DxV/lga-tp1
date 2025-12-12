package br.unitins.topicos1.lgc.Usuario.dto;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome,

    @NotBlank(message = "O login não pode ser nulo.")
    String login,

    @NotBlank(message = "A senha não pode ser nula.")
    String senha,

    @NotBlank(message = "O CPF não pode ser nulo.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    String cpf,

    @NotNull(message = "O perfil deve ser informado (1=Adm, 2=User).")
    Integer idPerfil,

    LocalDate dataNascimento,

        // --- CAMPOS NOVOS OBRIGATÓRIOS ---
    
    @NotNull(message = "É obrigatório informar ao menos um telefone.")
    @Size(min = 1, message = "Informe pelo menos um telefone.")
    @Valid 
    List<TelefoneDTO> telefones,

    @NotNull(message = "É obrigatório informar ao menos um endereço.")
    @Size(min = 1, message = "Informe pelo menos um endereço.")
    @Valid 
    List<EnderecoDTO> enderecos
    
) {}