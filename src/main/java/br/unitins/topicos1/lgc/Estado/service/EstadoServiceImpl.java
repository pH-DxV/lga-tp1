package br.unitins.topicos1.lgc.Estado.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
import br.unitins.topicos1.lgc.Estado.model.Estado;
import br.unitins.topicos1.lgc.Estado.repository.EstadoRepository;
import br.unitins.topicos1.lgc.Regiao.model.Regiao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository repository;

    @Override
    public List<EstadoDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(EstadoDTOResponse::valueOf) // Conversão
                .collect(Collectors.toList());
    }

    @Override
    public List<EstadoDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream() // Supondo que findByNome retorne List<Estado>
                .map(EstadoDTOResponse::valueOf) // Conversão
                .collect(Collectors.toList());
    }

    @Override
    public EstadoDTOResponse findById(Long id) {
        Estado estado = repository.findById(id);
        if (estado == null) {
            throw new NotFoundException("Estado não encontrado.");
        }
        return EstadoDTOResponse.valueOf(estado);
    }

    @Override
    @Transactional
    public EstadoDTOResponse create(EstadoDTO dto) {
        Estado entity = new Estado();
        entity.setNome(dto.nome());
        entity.setSigla(dto.sigla());
        entity.setRegiao(Regiao.valueOf(dto.idRegiao())); // Convertendo ID do Enum
        
        repository.persist(entity);
        
        return EstadoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public EstadoDTOResponse update(Long id, EstadoDTO dto) {
        Estado entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Estado não encontrado.");
        }
        entity.setNome(dto.nome());
        entity.setSigla(dto.sigla());
        entity.setRegiao(Regiao.valueOf(dto.idRegiao()));
        
        return EstadoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Estado não encontrado.");
        }
    }
}