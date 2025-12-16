package br.unitins.topicos1.lgc.Pagamento.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.unitins.topicos1.lgc.Pagamento.model.Pagamento;

public record PagamentoResponseDTO(
    Long id,
    BigDecimal valor,
    Boolean confirmado,
    LocalDateTime dataConfirmacao
) {
    public static PagamentoResponseDTO valueOf(Pagamento p) {
        return new PagamentoResponseDTO(
            p.getId(),
            p.getValor(),
            p.getConfirmado(),
            p.getDataConfirmacao()
        );
    }
}