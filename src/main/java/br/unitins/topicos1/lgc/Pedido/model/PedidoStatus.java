package br.unitins.topicos1.lgc.Pedido.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat (shape = Shape.OBJECT)
public enum PedidoStatus {
    
    AGUARDANDO_PAGAMENTO(1, "Aguardando Pagamento"),
    PAGO(2, "Pago"),
    SEPARACAO(3, "Em Separação"),
    ENVIADO(4, "Enviado"),
    ENTREGUE(5, "Entregue"),
    CANCELADO(6, "Cancelado");

    private final Integer id;
    private final String label;

    PedidoStatus(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }
    
    public static PedidoStatus valueOf(Integer id) {
        if (id == null) return null;
        for (PedidoStatus status : PedidoStatus.values()) {
            if (status.getId().equals(id)) return status;
        }
        throw new IllegalArgumentException("Id inválido");
    }
}
