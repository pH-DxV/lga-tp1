package br.unitins.topicos1.lgc.Pagamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PagamentoCartaoDTO(
    @NotNull(message = "O ID do Pedido é obrigatório.")
    Long idPedido,

    @NotBlank(message = "O nome no cartão é obrigatório.")
    String nomeTitular,

    @NotBlank(message = "O número do cartão é obrigatório.")
    @Size(min = 13, max = 19, message = "Número de cartão inválido.")
    String numeroCartao,

    @NotBlank(message = "A bandeira é obrigatória.")
    String bandeira, 

    @NotBlank(message = "A validade é obrigatória.")
    String validade,

    @NotBlank(message = "O CVV é obrigatório.")
    @Size(min = 3, max = 4)
    String cvv
) {}