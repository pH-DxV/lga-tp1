package br.unitins.topicos1.lgc.Pagamento.dto;

import jakarta.validation.constraints.NotNull;

public record PagamentoPixDTO(
    @NotNull(message = "O ID do Pedido é obrigatório.")
    Long idPedido
) {}