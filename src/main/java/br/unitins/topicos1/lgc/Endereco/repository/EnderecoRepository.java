package br.unitins.topicos1.lgc.Endereco.repository;

import java.util.List;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return find("cep LIKE ?1", "%" + cep + "%").list();
    }

    public List<Endereco> findByRua(String rua) {
        return find("UPPER(rua) LIKE ?1", "%" + rua.toUpperCase() + "%").list();
    }
    
    // Útil para buscar endereços de um usuário específico
    public List<Endereco> findByUsuario(Long idUsuario) {
        return find("usuario.id = ?1", idUsuario).list();
    }
}