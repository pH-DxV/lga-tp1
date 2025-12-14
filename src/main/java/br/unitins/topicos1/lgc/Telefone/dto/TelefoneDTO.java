package br.unitins.topicos1.lgc.Telefone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneDTO(
    @NotBlank(message = "O DDD não pode ser nulo.")
    @Size(min = 2, max = 2, message = "O DDD deve ter 2 dígitos.")
    String ddd,

    @NotBlank(message = "O número não pode ser nulo.")
    @Size(min = 8, max = 9, message = "O número deve ter 8 ou 9 dígitos.")
    String numero
    
) {}