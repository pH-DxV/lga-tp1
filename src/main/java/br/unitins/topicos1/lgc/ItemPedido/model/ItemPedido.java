package br.unitins.topicos1.lgc.ItemPedido.model;

import java.math.BigDecimal; // Importe BigDecimal

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido extends DefaultEntity {

    // --- MUDANÇA: Double -> BigDecimal ---
    @Column(nullable = false, precision = 10, scale = 2) // precision=digitos totais, scale=decimais
    private BigDecimal precoUnitario; 

    @Column(nullable = false)
    private Integer quantidade;
    
    // --- MUDANÇA: Double -> BigDecimal ---
    @Column(precision = 10, scale = 2)
    private BigDecimal desconto; 

    @ManyToOne
    @JoinColumn(name = "id_cafe", nullable = false)
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    // --- GETTERS E SETTERS ---

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
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
    
    // --- MÉTODO DE CÁLCULO ATUALIZADO ---
    public BigDecimal getSubTotal() {
        // Cálculo: (preco * quantidade) - desconto
        
        // 1. Multiplica Preço x Quantidade
        // new BigDecimal(quantidade) converte o Integer para BigDecimal para permitir a conta
        BigDecimal total = this.precoUnitario.multiply(new BigDecimal(this.quantidade));
        
        // 2. Subtrai o desconto (se houver)
        if (this.desconto != null) {
            total = total.subtract(this.desconto);
        }
        
        return total;
    }
}