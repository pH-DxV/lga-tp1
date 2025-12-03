package br.unitins.topicos1.lgc.Pagamento.service;

import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTOResponse;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTOResponse;

public interface PagamentoService {
    
    PagamentoCartaoDTOResponse pagarCartao(PagamentoCartaoDTO dto);

    PagamentoPixDTOResponse pagarPix(PagamentoPixDTO dto);

    PagamentoBoletoDTOResponse pagarBoleto(PagamentoBoletoDTO dto);
}