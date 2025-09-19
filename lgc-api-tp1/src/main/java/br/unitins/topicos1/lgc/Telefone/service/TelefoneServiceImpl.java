package br.unitins.topicos1.lgc.Telefone.service;

import java.util.List;

import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;
import br.unitins.topicos1.lgc.Telefone.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {


    @Inject
    TelefoneRepository repository;



    @Override
    public List<Telefone> findAll() {
        return repository.listAll();
    }
    @Override
    public Telefone findById(Long id) {
        return repository.findById(id);
    }
    @Override
    public Telefone findByDdd(String ddd) {
        return repository.findByDdd(ddd);
    }
    @Override
    public Telefone findByNumero(String numero) {
        return repository.findByNumero(numero);
    }



    @Override
    @Transactional
    public Telefone create(TelefoneDTO dto) {
        Telefone telefone = new Telefone();
        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        repository.persist(telefone);
        return telefone;
    }
    @Override
    @Transactional
    public void update(Long id, TelefoneDTO dto) {
        Telefone telefone = repository.findById(id);
        if (telefone != null) {
            telefone.setDdd(dto.ddd());
            telefone.setNumero(dto.numero());
        }
    }
    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    
}
