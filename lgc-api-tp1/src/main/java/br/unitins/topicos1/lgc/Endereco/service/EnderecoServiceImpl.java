package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Override
    public List<Endereco> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Endereco> findByCep(String cep) {
        return repository.findByCep(cep);
    }

    @Override
    public List<Endereco> findByRua(String rua) {
        return repository.findByRua(rua);
    }

    @Override
    public Endereco findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Endereco create(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setRua(dto.rua());
        endereco.setComplemento(dto.complemento());

        repository.persist(endereco);
        return endereco;
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoDTO dto) {
        Endereco endereco = repository.findById(id);
        if (endereco != null) {
            endereco.setCep(dto.cep());
            endereco.setRua(dto.rua());
            endereco.setComplemento(dto.complemento());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}