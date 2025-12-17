package br.unitins.topicos1.lgc.Pedido.dto;

import java.util.List;

import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PedidoDTO(
    // idUsuario REMOVIDO: O usuário é identificado pelo Token JWT

    @NotNull(message = "O endereço de entrega é obrigatório")
    Long idEnderecoEntrega,
    
    @NotNull(message = "O pedido deve conter itens.")
    @Size(min = 1, message = "O pedido deve ter no mínimo um item.")
    @Valid
    List<ItemPedidoDTO> itens
) {}