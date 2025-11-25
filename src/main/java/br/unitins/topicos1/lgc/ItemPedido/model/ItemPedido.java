package br.unitins.topicos1.lgc.ItemPedido.model;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity {

    @Column(nullable = false)
    private Double precoUnitario; // O "Snapshot" do preço

    @Column(nullable = false)
    private Integer quantidade;
    
    private Double desconto; // Opcional

    @ManyToOne
    @JoinColumn(name = "id_cafe", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    // --- GETTERS E SETTERS ---

    public Double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(Double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Cafe getCafe() {
        return cafe;
    }

    public void setCafe(Cafe cafe) {
        this.cafe = cafe;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    // Método utilitário (não é salvo no banco, é calculado)
    public Double getSubTotal() {
        return (this.precoUnitario * this.quantidade) - (this.desconto != null ? this.desconto : 0.0);
    }
}