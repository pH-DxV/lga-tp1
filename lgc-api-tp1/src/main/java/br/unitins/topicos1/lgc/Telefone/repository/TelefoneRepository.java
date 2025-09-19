package br.unitins.topicos1.lgc.Telefone.repository;

import br.unitins.topicos1.lgc.Telefone.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone>{

    public Telefone findByDdd(String ddd){

        return find("ddd = ?1", ddd).firstResult();

    }
    
    public Telefone findByNumero(String numero){

        return find("ddd = ?1", numero).firstResult();
    }

}
