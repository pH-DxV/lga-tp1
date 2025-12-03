package br.unitins.topicos1.lgc.Pagamento.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum FormaPagamento {

    CARTAO_CREDITO(1L, "Cartão de Crédito", true),
    PIX(2L, "PIX", false),
    BOLETO(3L, "Boleto Bancário", false);

    @JsonProperty("id")
    public final Long ID;

    @JsonProperty("nome")
    public final String NOME;

    @JsonProperty("requer_dados_cartao")
    public final Boolean REQUER_DADOS_CARTAO;

    FormaPagamento(long id, String nome, Boolean requerCartao) {
        this.ID = id;
        this.NOME = nome;
        this.REQUER_DADOS_CARTAO = requerCartao;
    }

    public static FormaPagamento valueOf(Long id) {
        if (id == null) return null;
        for (FormaPagamento forma : FormaPagamento.values()) {
            if (id.equals(forma.ID)) return forma;
        }
        throw new IllegalArgumentException("ID de FormaPagamento inválido: " + id);
    }

    @JsonCreator
    public static FormaPagamento fromObject(Map<String, Object> obj) {
        if (obj != null && obj.containsKey("id")) {
            Number id = (Number) obj.get("id");
            return valueOf(id.longValue());
        }
        return null;
    }
}