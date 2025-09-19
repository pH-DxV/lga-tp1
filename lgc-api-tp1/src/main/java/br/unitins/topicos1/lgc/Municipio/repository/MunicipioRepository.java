package br.unitins.topicos1.lgc.Municipio.repository;

import java.util.List;

import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {

    public List<Municipio> findByNome(String nome) {
        return find("SELECT m FROM Municipio m WHERE m.nome LIKE ?1 ", "%"+nome+"%").list();
    }
    
}
