package br.unitins.topicos1.lgc.Pagamento.dto;

import java.time.LocalDateTime;
import br.unitins.topicos1.lgc.Pagamento.model.Pagamento;

public record PagamentoResumoDTO(
    Long id,
    Double valor,
    Boolean confirmado,
    LocalDateTime dataConfirmacao // Este campo é crucial para o seu relatório
) {
    public static PagamentoResumoDTO valueOf(Pagamento p) {
        if (p == null) return null;
        return new PagamentoResumoDTO(
            p.getId(),
            p.getValor(),
            p.getConfirmado(),
            p.getDataConfirmacao() // Garante que a data real seja passada
        );
    }
}