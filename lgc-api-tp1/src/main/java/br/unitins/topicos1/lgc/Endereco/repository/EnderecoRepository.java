package br.unitins.topicos1.lgc.Endereco.repository;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public Endereco findByCep(String cep){

        return find("cep = ?1", cep).firstResult();
    }
    
}
