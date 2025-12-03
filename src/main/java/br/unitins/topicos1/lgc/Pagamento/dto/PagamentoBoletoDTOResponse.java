package br.unitins.topicos1.lgc.Pagamento.dto;

import java.time.LocalDate;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoBoleto;

public record PagamentoBoletoDTOResponse (
    Long id,
    Double valor,
    Boolean confirmado,
    String codigoBarras, // O número para pagar
    LocalDate dataVencimento
) {
    public static PagamentoBoletoDTOResponse valueOf(PagamentoBoleto p) {
        return new PagamentoBoletoDTOResponse(
            p.getId(),
            p.getValor(),
            p.getConfirmado(),
            p.getCodigoBarras(),
            p.getDataVencimento()
        );
    }
}
