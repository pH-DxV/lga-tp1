package br.unitins.topicos1.lgc.Pagamento.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import java.math.BigDecimal;

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
import br.unitins.topicos1.lgc.Security.service.SecurityService;
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

    @Inject
    SecurityService securityService;

    // --- CARTÃO DE CRÉDITO ---
    @Override
    @Transactional
    public PagamentoCartaoDTOResponse pagarCartao(PagamentoCartaoDTO dto) {
        // 1. Busca e Valida o Pedido
        Pedido pedido = validarPedido(dto.idPedido());

        // 2. Validação de Segurança: O usuário logado é dono deste pedido?
        securityService.validarPermissao(pedido.getUsuario());

        // 3. Validações de Cartão (Simulação)
        if ("000".equals(dto.cvv())) { 
             throw new BadRequestException("Pagamento recusado pela operadora.");
        }

        PagamentoCartao pagamento = new PagamentoCartao();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido()); // Já é BigDecimal
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

    // --- PIX ---
    @Override
    @Transactional
    public PagamentoPixDTOResponse pagarPix(PagamentoPixDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());
        
        securityService.validarPermissao(pedido.getUsuario());

        PagamentoPix pagamento = new PagamentoPix();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido()); // Já é BigDecimal
        pagamento.setConfirmado(false);
        
        pedido.setPagamento(pagamento);
        
        pagamento.setChavePixDestino(UUID.randomUUID().toString());
        pagamento.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(30));

        pagamentoRepository.persist(pagamento);

        return PagamentoPixDTOResponse.valueOf(pagamento);
    }

    // --- BOLETO ---
    @Override
    @Transactional
    public PagamentoBoletoDTOResponse pagarBoleto(PagamentoBoletoDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        securityService.validarPermissao(pedido.getUsuario());

        PagamentoBoleto pagamento = new PagamentoBoleto();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido()); // Já é BigDecimal
        pagamento.setConfirmado(false); 

        pedido.setPagamento(pagamento);

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
        // Validação com BigDecimal: compareTo <= 0
        if (pedido.getTotalPedido() == null || pedido.getTotalPedido().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Valor do pedido inválido.");
        }
        // Regra: Não pode pagar pedido já pago ou cancelado
        if (pedido.getStatus() != PedidoStatus.AGUARDANDO_PAGAMENTO) {
            throw new BadRequestException("Este pedido não pode ser pago (Status: " + pedido.getStatus().getLabel() + ")");
        }
        return pedido;
    }
}