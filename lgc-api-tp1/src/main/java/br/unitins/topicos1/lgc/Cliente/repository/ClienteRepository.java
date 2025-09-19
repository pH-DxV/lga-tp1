package br.unitins.topicos1.lgc.Cliente.repository;

import java.util.List;

import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository  implements PanacheRepository<Cliente>{

    public List<Cliente> findByNome(String nome){

        return find("UPPER (nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();

    }
    
    public Cliente findByCpf(String cpf){

        return find("cpf = ?1", cpf).firstResult();

    }
    
}
