package br.unitins.topicos1.lgc.Usuario.repository;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public List<Usuario> findByNome(String nome) {
        return find("SELECT u FROM Usuario u WHERE u.nome LIKE ?1", "%" + nome + "%").list();
    }
}