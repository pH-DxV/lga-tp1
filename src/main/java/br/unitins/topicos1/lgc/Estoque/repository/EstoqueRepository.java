package br.unitins.topicos1.lgc.Estoque.repository;

import br.unitins.topicos1.lgc.Estoque.model.Estoque;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstoqueRepository implements PanacheRepository<Estoque> {
    
    public Estoque findByIdCafe(Long idCafe) {
        return find("cafe.id", idCafe).firstResult();
    }
}