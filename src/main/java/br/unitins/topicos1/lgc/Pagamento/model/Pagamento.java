package br.unitins.topicos1.lgc.Pagamento.model;

import java.time.LocalDateTime;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pagamento extends DefaultEntity {

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Boolean confirmado;

    private LocalDateTime dataConfirmacao;

    @OneToOne
    @JoinColumn(name = "id_pedido", unique = true) 
    private Pedido pedido;

    // Getters e Setters
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public Boolean getConfirmado() { return confirmado; }
    public void setConfirmado(Boolean confirmado) { this.confirmado = confirmado; }

    public LocalDateTime getDataConfirmacao() { return dataConfirmacao; }
    public void setDataConfirmacao(LocalDateTime dataConfirmacao) { this.dataConfirmacao = dataConfirmacao; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}