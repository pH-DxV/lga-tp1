package br.unitins.topicos1.lgc.Usuario.repository;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {

    public List<Usuario> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Usuario findByLoginAndSenha(String login, String senha) {
        // Assumindo que o campo no banco é 'login' e 'senha'
        return find("login = ?1 AND senha = ?2", login, senha).firstResult();
    }
}