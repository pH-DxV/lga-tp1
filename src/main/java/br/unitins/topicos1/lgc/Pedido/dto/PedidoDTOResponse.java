package br.unitins.topicos1.lgc.Pedido.dto;

import java.time.LocalDateTime;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

public record PedidoDTOResponse(
    Long id,
    LocalDateTime dataHora,
    Double totalPedido,
    UsuarioDTOResponse usuario,
    EnderecoDTOResponse enderecoEntrega
) {
    public static PedidoDTOResponse valueOf(Pedido pedido) {
        return new PedidoDTOResponse(
            pedido.getId(),
            pedido.getDataHora(),
            pedido.getTotalPedido(),
            UsuarioDTOResponse.valueOf(pedido.getUsuario()),
            EnderecoDTOResponse.valueOf(pedido.getEnderecoEntrega())
        );
    }
}