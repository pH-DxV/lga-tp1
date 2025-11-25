package br.unitins.topicos1.lgc.Endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importe o NotNull
import jakarta.validation.constraints.Size;

public record EnderecoDTO(
    @NotBlank(message = "O CEP não pode ser nulo.")
    @Size(min = 8, max = 8, message = "O CEP deve ter 8 caracteres.")
    String cep,

    @NotBlank(message = "A rua não pode ser nula.")
    String rua,

    String complemento,

    // --- ADICIONE ESTE CAMPO ---
    @NotNull(message = "O ID do usuário é obrigatório.")
    Long idUsuario
) {}
