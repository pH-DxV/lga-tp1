package br.unitins.topicos1.lgc.Auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
    @NotBlank(message = "O login é obrigatório")
    String login,
    
    @NotBlank(message = "A senha é obrigatória")
    String senha
) {}