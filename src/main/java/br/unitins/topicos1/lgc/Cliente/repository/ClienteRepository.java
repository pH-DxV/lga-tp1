package br.unitins.topicos1.lgc.Cliente.repository;

import java.util.List;
import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    /**
     * Busca todos os clientes cujo nome contenha o texto informado (case-insensitive).
     * @param nome o nome a ser buscado.
     * @return uma lista de clientes.
     */
    public List<Cliente> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
    
    /**
     * Busca um cliente pelo CPF (deve ser único).
     * @param cpf o CPF a ser buscado.
     * @return o cliente encontrado ou null.
     */
    public Cliente findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }
    
    /**
     * Busca um cliente pelo login (deve ser único).
     * @param login o login a ser buscado.
     * @return o cliente encontrado ou null.
     */
    public Cliente findByLogin(String login) {
        return find("login", login).firstResult();
    }

    @Override
    public List<Cliente> listAll() {
        // Força a busca apenas onde existe registro na tabela cliente (comportamento padrão, mas reforçando)
        return find("SELECT c FROM Cliente c").list(); 
    }
}
