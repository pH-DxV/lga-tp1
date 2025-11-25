package br.unitins.topicos1.lgc.ItemPedido.dto;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.model.ItemPedido;

public record ItemPedidoDTOResponse(
    String nomeCafe,
    Double precoUnitario,
    Integer quantidade,
    Double desconto,
    Double subTotal,
    CafeDTOResponse cafe
) {
    public static ItemPedidoDTOResponse valueOf(ItemPedido item) {
        return new ItemPedidoDTOResponse(
            item.getCafe().getNome(),
            item.getPrecoUnitario(),
            item.getQuantidade(),
            item.getDesconto() != null ? item.getDesconto() : 0.0,
            item.getSubTotal(),
            CafeDTOResponse.valueOf(item.getCafe())
        );
    }
}