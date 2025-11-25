package br.unitins.topicos1.lgc.ItemPedido.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoDTO(
    @NotNull(message = "A quantidade é obrigatória.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    Integer quantidade,

    @NotNull(message = "O ID do café é obrigatório.")
    Long idCafe
) {}