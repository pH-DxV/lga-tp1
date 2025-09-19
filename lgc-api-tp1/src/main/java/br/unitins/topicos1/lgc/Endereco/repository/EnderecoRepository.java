package br.unitins.topicos1.lgc.Endereco.repository;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return find("SELECT e FROM Endereco e WHERE e.cep LIKE ?1", "%" + cep + "%").list();
    }

    public List<Endereco> findByRua(String rua) {
        return find("SELECT e FROM Endereco e WHERE e.rua LIKE ?1", "%" + rua + "%").list();
    }
}
