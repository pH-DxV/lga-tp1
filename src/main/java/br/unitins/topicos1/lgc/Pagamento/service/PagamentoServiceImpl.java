package br.unitins.topicos1.lgc.Pagamento.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoBoleto;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoCartao;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoPix;
import br.unitins.topicos1.lgc.Pagamento.repository.PagamentoRepository;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Pedido.model.PedidoStatus;
import br.unitins.topicos1.lgc.Pedido.repository.PedidoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoServiceImpl implements PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    @Inject
    PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public PagamentoCartaoDTOResponse pagarCartao(PagamentoCartaoDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        if ("000".equals(dto.cvv())) { 
             throw new BadRequestException("Pagamento recusado pela operadora.");
        }

        PagamentoCartao pagamento = new PagamentoCartao();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(true);
        pagamento.setDataConfirmacao(LocalDateTime.now());
        
        // Atualiza status do pedido para PAGO
        pedido.setPagamento(pagamento);
        pedido.setStatus(PedidoStatus.PAGO);
        
        pagamento.setNomeTitular(dto.nomeTitular());
        String num = dto.numeroCartao();
        String mascara = "**** **** **** " + num.substring(num.length() - 4);
        pagamento.setNumeroCartao(mascara); 
        pagamento.setBandeira(dto.bandeira());
        
        pagamentoRepository.persist(pagamento);
        
        return PagamentoCartaoDTOResponse.valueOf(pagamento);
    }

    @Override
    @Transactional
    public PagamentoPixDTOResponse pagarPix(PagamentoPixDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        PagamentoPix pagamento = new PagamentoPix();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(false);
        
        // Mantém ou define explicitamente como AGUARDANDO (se já não estiver)
        pedido.setPagamento(pagamento);
        // pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO); // Já nasce assim, mas reforça
        
        pagamento.setChavePixDestino(UUID.randomUUID().toString());
        pagamento.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(30));

        pagamentoRepository.persist(pagamento);

        return PagamentoPixDTOResponse.valueOf(pagamento);
    }

    @Override
    @Transactional
    public PagamentoBoletoDTOResponse pagarBoleto(PagamentoBoletoDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        PagamentoBoleto pagamento = new PagamentoBoleto();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(false);

        pedido.setPagamento(pagamento);
        // pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);

        pagamento.setCodigoBarras("34191.79001 01043.510047 91020.150008 8 " + System.currentTimeMillis());
        pagamento.setDataVencimento(LocalDate.now().plusDays(3));

        pagamentoRepository.persist(pagamento);

        return PagamentoBoletoDTOResponse.valueOf(pagamento);
    }

    private Pedido validarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        if (pedido.getTotalPedido() <= 0) {
            throw new BadRequestException("Valor do pedido inválido.");
        }
        if (pedido.getStatus() == PedidoStatus.PAGO 
            || pedido.getStatus() == PedidoStatus.CANCELADO 
            || pedido.getStatus() == PedidoStatus.ENVIADO 
            || pedido.getStatus() == PedidoStatus.ENTREGUE) {
            throw new BadRequestException("Este pedido não pode ser pago (Status: " + pedido.getStatus().getLabel() + ")");
        }
        return pedido;
    }
}