package br.unitins.topicos1.lgc.Pedido.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

public record PedidoDTOResponse(
    Long id,
    LocalDateTime dataHora,
    Double totalPedido,
    UsuarioDTOResponse usuario,
    EnderecoDTOResponse enderecoEntrega,
    // ESTE CAMPO É OBRIGATÓRIO PARA O TESTE PASSAR:
    List<ItemPedidoDTOResponse> itens 
) {
    public static PedidoDTOResponse valueOf(Pedido pedido) {
        // Garante que a lista não é nula para evitar erros
        List<ItemPedidoDTOResponse> listaItens = (pedido.getItens() != null) ?
            pedido.getItens().stream().map(ItemPedidoDTOResponse::valueOf).toList() :
            List.of();

        return new PedidoDTOResponse(
            pedido.getId(),
            pedido.getDataHora(),
            pedido.getTotalPedido(),
            UsuarioDTOResponse.valueOf(pedido.getUsuario()),
            EnderecoDTOResponse.valueOf(pedido.getEnderecoEntrega()),
            listaItens // Passa a lista
        );
    }
}