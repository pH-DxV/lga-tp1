package br.unitins.topicos1.lgc.Pedido.repository;

import java.util.List;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    
    // Busca todos os pedidos de um usuário
    public List<Pedido> findByUsuario(Long idUsuario) {
        return find("usuario.id = ?1", idUsuario).list();
    }
}