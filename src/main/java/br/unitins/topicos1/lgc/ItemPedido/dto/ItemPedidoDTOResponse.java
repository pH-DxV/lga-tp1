package br.unitins.topicos1.lgc.ItemPedido.dto;

import java.math.BigDecimal; // Importe BigDecimal

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.model.ItemPedido;

public record ItemPedidoDTOResponse(
    String nomeCafe,
    BigDecimal precoUnitario, // Alterado para BigDecimal
    Integer quantidade,
    BigDecimal desconto,      // Alterado para BigDecimal
    BigDecimal subTotal,      // Alterado para BigDecimal
    CafeDTOResponse cafe
) {
    public static ItemPedidoDTOResponse valueOf(ItemPedido item) {
        return new ItemPedidoDTOResponse(
            item.getCafe().getNome(),
            item.getPrecoUnitario(),
            item.getQuantidade(),
            // Garante que não retorne null, usa BigDecimal.ZERO se necessário
            item.getDesconto() != null ? item.getDesconto() : BigDecimal.ZERO,
            item.getSubTotal(), // O método do Model já retorna BigDecimal
            // Usa o método de conveniência do CafeDTOResponse (que assume estoque 0 ou null, já que é histórico)
            CafeDTOResponse.valueOf(item.getCafe())
        );
    }
}