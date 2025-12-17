package br.unitins.topicos1.lgc.Pedido.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoResumoDTO;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Pedido.model.PedidoStatus;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

public record PedidoDTOResponse(
    Long id,
    LocalDateTime dataHora,
    BigDecimal totalPedido,
    BigDecimal valorFrete,
    UsuarioDTOResponse usuario, // Mantemos o objeto completo ou o ID
    EnderecoDTOResponse enderecoEntrega,
    List<ItemPedidoDTOResponse> itens,
    PagamentoResumoDTO pagamento,
    PedidoStatus status
) {
    public static PedidoDTOResponse valueOf(Pedido pedido) {
        List<ItemPedidoDTOResponse> listaItens = (pedido.getItens() != null) ?
            pedido.getItens().stream().map(ItemPedidoDTOResponse::valueOf).toList() : 
            List.of();

        return new PedidoDTOResponse(
            pedido.getId(),
            pedido.getDataHora(),
            pedido.getTotalPedido(),
            pedido.getValorFrete(),
            UsuarioDTOResponse.valueOf(pedido.getUsuario()), // Aqui passamos o usuário
            EnderecoDTOResponse.valueOf(pedido.getEnderecoEntrega()),
            listaItens,
            PagamentoResumoDTO.valueOf(pedido.getPagamento()),
            pedido.getStatus()
        );
    }
}