package br.unitins.topicos1.lgc.Cafe.repository;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CafeRepository implements PanacheRepository<Cafe>{
    
    // preencher com a query dos tipos de buscas possiveis (formato de texto: De acordo com o banco de dados)


    // Adicione este método dentro da classe CafeRepository.java
    public long countByCategoriaDoCafe(Long idCategoria) {

        return count("categoriaDoCafe.id = ?1", idCategoria);
        
    }

}
