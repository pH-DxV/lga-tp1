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

    // --- CARTÃO DE CRÉDITO ---
    @Override
    @Transactional
    public PagamentoCartaoDTOResponse pagarCartao(PagamentoCartaoDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        if (dto.cvv().equals("000")) { 
             throw new BadRequestException("Pagamento recusado pela operadora.");
        }

        PagamentoCartao pagamento = new PagamentoCartao();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(true);
        pagamento.setDataConfirmacao(LocalDateTime.now());
        
        pagamento.setNomeTitular(dto.nomeTitular());
        String mascara = "**** **** **** " + dto.numeroCartao().substring(dto.numeroCartao().length() - 4);
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

        PagamentoPix pagamento = new PagamentoPix();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(false); // Pix começa como pendente
        pagamento.setDataConfirmacao(null);
        
        // Gera chave aleatória simulando o "Copia e Cola"
        pagamento.setChavePixDestino(UUID.randomUUID().toString());
        pagamento.setDataExpiracaoToken(LocalDateTime.now().plusMinutes(30)); // Expira em 30 min

        pagamentoRepository.persist(pagamento);

        return PagamentoPixDTOResponse.valueOf(pagamento);
    }

    // --- BOLETO ---
    @Override
    @Transactional
    public PagamentoBoletoDTOResponse pagarBoleto(PagamentoBoletoDTO dto) {
        Pedido pedido = validarPedido(dto.idPedido());

        PagamentoBoleto pagamento = new PagamentoBoleto();
        pagamento.setPedido(pedido);
        pagamento.setValor(pedido.getTotalPedido());
        pagamento.setConfirmado(false); // Boleto começa como pendente
        pagamento.setDataConfirmacao(null);

        // Gera código de barras simulado
        pagamento.setCodigoBarras("34191.79001 01043.510047 91020.150008 8 " + System.currentTimeMillis());
        pagamento.setDataVencimento(LocalDate.now().plusDays(3)); // Vence em 3 dias

        pagamentoRepository.persist(pagamento);

        return PagamentoBoletoDTOResponse.valueOf(pagamento);
    }

    // Método auxiliar para validar o pedido e evitar repetição de código
    private Pedido validarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido);
        if (pedido == null) {
            throw new NotFoundException("Pedido não encontrado.");
        }
        if (pedido.getTotalPedido() <= 0) {
            throw new BadRequestException("Valor do pedido inválido.");
        }
        return pedido;
    }
}