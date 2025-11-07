package br.unitins.topicos1.lgc.Telefone.repository;

import java.util.List;

import br.unitins.topicos1.lgc.TelefoneResourceTest.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<TelefoneResourceTest> {

    // Permite buscar todos os telefones de um usuário específico
    public List<TelefoneResourceTest> findByUsuario(Long idUsuario) {
        return find("usuario.id = ?1", idUsuario).list();
    }
}