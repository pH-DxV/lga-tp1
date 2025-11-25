package br.unitins.topicos1.lgc.Pedido.dto;

import jakarta.validation.constraints.NotNull;

public record PedidoDTO(
    @NotNull(message = "O usuário é obrigatório")
    Long idUsuario,

    @NotNull(message = "O endereço de entrega é obrigatório")
    Long idEnderecoEntrega
    
    // Futuramente receberemos aqui a List<ItemPedidoDTO>
) {}