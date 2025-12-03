package br.unitins.topicos1.lgc.Pagamento.dto;

import java.time.LocalDateTime;
import br.unitins.topicos1.lgc.Pagamento.model.PagamentoPix;

public record PagamentoPixDTOResponse(
    Long id,
    Double valor,
    Boolean confirmado,
    String chavePix, // A chave para o usuário pagar
    LocalDateTime dataExpiracao
) {
    public static PagamentoPixDTOResponse valueOf(PagamentoPix p) {
        return new PagamentoPixDTOResponse(
            p.getId(),
            p.getValor(),
            p.getConfirmado(),
            p.getChavePixDestino(),
            p.getDataExpiracaoToken()
        );
    }
}