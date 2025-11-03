package br.unitins.topicos1.lgc.CategoriaDoCafe.repository;

import java.util.List;

import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaDoCafeRepository implements PanacheRepository<CategoriaDoCafe> {

    public List<CategoriaDoCafe> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
    
}
