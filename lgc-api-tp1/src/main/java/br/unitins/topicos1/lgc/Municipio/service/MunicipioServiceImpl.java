package br.unitins.topicos1.lgc.Municipio.service;

import java.util.List;

import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository repository;

    @Override
    public List<Municipio> findAll() {
        return repository.listAll();
    }

    @Override
    public List<Municipio> findByNome(String nome) {
        return repository.findByNome(nome);
    }

    @Override
    public Municipio findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Municipio create(MunicipioDTO dto) {
        Municipio municipio = new Municipio();
        municipio.setNome(dto.nome());

        repository.persist(municipio);

        return municipio;
    }

    @Override
    @Transactional
    public void update(Long id, MunicipioDTO dto) {
        Municipio municipio = repository.findById(id);
        municipio.setNome(dto.nome());
    }

    @Override
    @Transactional
    public void delete(Long id) {
       repository.deleteById(id);
    }
    
}

