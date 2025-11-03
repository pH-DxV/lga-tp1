package br.unitins.topicos1.lgc.Cafe.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class CafeRepository implements PanacheRepository<Cafe> {

    // Método que já tínhamos (para a regra de deleção)
    public long countByMarca(Long idMarca) {
        return count("marca.id = ?1", idMarca);
    }

    // Método que já tínhamos (para a regra de deleção)
    public long countByCategoriaDoCafe(Long idCategoria) {
        return count("categoriaDoCafe.id = ?1", idCategoria);
    }
    
    // Busca por nome (LIKE)
    public List<Cafe> findByNomeLike(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
    
    // Busca por faixa de pontuação SCA (que criamos antes)
    public List<Cafe> findByPontuacaoRange(Integer minSCA, Integer maxSCA) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("1 = 1 "); 

        if (minSCA != null) {
            query.append("AND pontuacaoSCA >= :minSCA ");
            params.put("minSCA", minSCA);
        }

        if (maxSCA != null) {
            query.append("AND pontuacaoSCA <= :maxSCA ");
            params.put("maxSCA", maxSCA);
        }
        
        return find(query.toString(), params).list();
    }
}
