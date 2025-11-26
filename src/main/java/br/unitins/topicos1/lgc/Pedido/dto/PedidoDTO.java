package br.unitins.topicos1.lgc.Pedido.dto;

import java.util.List;

import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PedidoDTO(
    @NotNull(message = "O usuário é obrigatório")
    Long idUsuario,

    @NotNull(message = "O endereço de entrega é obrigatório")
    Long idEnderecoEntrega,
    
    // --- ESTE É O CAMPO QUE ESTÁ FALTANDO ---
    @NotNull(message = "O pedido deve conter itens.")
    @Size(min = 1, message = "O pedido deve ter no mínimo um item.")
    List<ItemPedidoDTO> itens
) {}