package br.unitins.topicos1.lgc.Usuario.dto;

import java.time.LocalDate;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioDTO(
    @NotBlank(message = "O nome não pode ser nulo.")
    String nome,

    @NotBlank(message = "O CPF não pode ser nulo.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos.")
    String cpf,

    LocalDate dataNascimento,
    
    Double peso
) {}