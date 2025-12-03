package br.unitins.topicos1.lgc.Pagamento.dto;

import jakarta.validation.constraints.NotNull;

// Este DTO serve como base ou para situações onde precisamos apenas do ID do pedido.
// Se necessário, você pode torná-lo uma classe abstrata ou usar diretamente nos filhos.
public class PagamentoDTO {
    
    @NotNull(message = "O ID do Pedido é obrigatório.")
    private Long idPedido;

    public PagamentoDTO() {
    }

    public PagamentoDTO(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}