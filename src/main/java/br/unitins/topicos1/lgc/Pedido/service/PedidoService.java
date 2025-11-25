package br.unitins.topicos1.lgc.Pedido.service;

import java.util.List;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;

public interface PedidoService {
    PedidoDTOResponse create(PedidoDTO dto);
    PedidoDTOResponse findById(Long id);
    List<PedidoDTOResponse> findAll();
    List<PedidoDTOResponse> findByUsuario(Long idUsuario);
}