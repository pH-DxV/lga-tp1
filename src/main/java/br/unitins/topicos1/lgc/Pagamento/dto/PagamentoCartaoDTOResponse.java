package br.unitins.topicos1.lgc.Pagamento.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoCartao;

public record PagamentoCartaoDTOResponse(
    Long id,
    BigDecimal valor,
    Boolean confirmado,
    LocalDateTime dataConfirmacao,
    String nomeTitular,
    String numeroCartaoMask, // Apenas os últimos dígitos
    String bandeira
) {
    public static PagamentoCartaoDTOResponse valueOf(PagamentoCartao p) {
        return new PagamentoCartaoDTOResponse(
            p.getId(),
            p.getValor(),
            p.getConfirmado(),
            p.getDataConfirmacao(),
            p.getNomeTitular(),
            p.getNumeroCartao(), // O model já deve ter o número mascarado
            p.getBandeira()
        );
    }
}