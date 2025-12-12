package br.unitins.topicos1.lgc.Endereco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EnderecoDTO(
    @NotBlank(message = "O CEP é obrigatório.")
    String cep,

    @NotBlank(message = "A rua é obrigatória.")
    String rua,

    @NotBlank(message = "O número é obrigatório.")
    String numero,

    String complemento,
    
    String bairro,

    @NotNull(message = "O município é obrigatório.")
    Long idMunicipio
    
) {}
