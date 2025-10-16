package br.unitins.topicos1.lgc.CategoriaDoCafe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaDoCafeDTO(

    @NotBlank(message = "O nome da categoria não pode ser nulo ou vazio.")
    @Size(min = 2, max = 60, message = "O nome da categoria deve ter entre 2 e 60 caracteres.")
    String nome,

    String descricao
    
) {
    
}
