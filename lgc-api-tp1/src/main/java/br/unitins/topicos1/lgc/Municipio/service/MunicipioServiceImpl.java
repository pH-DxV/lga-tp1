package br.unitins.topicos1.lgc.Municipio.service;

import java.util.List;

import br.unitins.topicos1.lgc.Estado.repository.EstadoRepository;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository repository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public List<MunicipioDTOResponse> findAll() {
        return repository
                    .listAll()
                    .stream()
                    .map(m -> MunicipioDTOResponse.valueOf(m))
                    .toList();
    }

    @Override
    public List<MunicipioDTOResponse> findByNome(String nome) {
        return repository
                    .findByNome(nome)
                    .stream()
                    .map(m -> MunicipioDTOResponse.valueOf(m))
                    .toList();
    }

    @Override
    public MunicipioDTOResponse findById(Long id) {
        return MunicipioDTOResponse.valueOf(repository.findById(id));
    }

    @Override
    @Transactional
    public MunicipioDTOResponse create(MunicipioDTO dto) {
        Municipio municipio = new Municipio();
        municipio.setNome(dto.nome());
        municipio.setEstado(estadoRepository.findById(dto.idEstado()));

        repository.persist(municipio);

        return MunicipioDTOResponse.valueOf(municipio);
    }

    @Override
    @Transactional
    public void update(Long id, MunicipioDTO dto) {
        Municipio municipio = repository.findById(id);
        municipio.setNome(dto.nome());
        municipio.setEstado(estadoRepository.findById(dto.idEstado()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
       repository.deleteById(id);
    }
    
}

