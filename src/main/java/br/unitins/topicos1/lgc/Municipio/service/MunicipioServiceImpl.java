package br.unitins.topicos1.lgc.Municipio.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Estado.model.Estado;
import br.unitins.topicos1.lgc.Estado.repository.EstadoRepository;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService {

    @Inject
    MunicipioRepository repository;

    @Inject
    EstadoRepository estadoRepository; // Necessário para criar a associação

    @Override
    public List<MunicipioDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(MunicipioDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public MunicipioDTOResponse findById(Long id) {
        Municipio municipio = repository.findById(id);
        if (municipio == null) {
            throw new NotFoundException("Município não encontrado.");
        }
        return MunicipioDTOResponse.valueOf(municipio);
    }

    @Override
    public List<MunicipioDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream() // Supondo que findByNome retorne List<Municipio>
                .map(MunicipioDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MunicipioDTOResponse create(MunicipioDTO dto) {
        // Busca o estado pelo ID
        Estado estado = estadoRepository.findById(dto.idEstado());
        if (estado == null) {
            throw new NotFoundException("Estado não encontrado para o ID: " + dto.idEstado());
        }

        Municipio entity = new Municipio();
        entity.setNome(dto.nome());
        entity.setEstado(estado); // Associa a entidade Estado
        
        repository.persist(entity);
        
        return MunicipioDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public MunicipioDTOResponse update(Long id, MunicipioDTO dto) {
        Municipio entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Município não encontrado.");
        }

        Estado estado = estadoRepository.findById(dto.idEstado());
        if (estado == null) {
            throw new NotFoundException("Estado não encontrado para o ID: " + dto.idEstado());
        }

        entity.setNome(dto.nome());
        entity.setEstado(estado);
        
        return MunicipioDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Município não encontrado.");
        }
    }
}