package br.unitins.topicos1.lgc.CategoriaDoCafe.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import br.unitins.topicos1.lgc.CategoriaDoCafe.repository.CategoriaDoCafeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.validation.ConstraintViolationException;

@ApplicationScoped
public class CategoriaDoCafeServiceImpl implements CategoriaDoCafeService {
    
    @Inject
    CategoriaDoCafeRepository categoriaRepository;

    @Inject
    CafeRepository cafeRepository;

    @Override
    @Transactional
    public CategoriaDoCafeDTOResponse create(CategoriaDoCafeDTO dto) {
        // Validação de nome único removida.
        CategoriaDoCafe entity = new CategoriaDoCafe();
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        categoriaRepository.persist(entity);
        return CategoriaDoCafeDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public CategoriaDoCafeDTOResponse update(Long id, CategoriaDoCafeDTO dto) {
        CategoriaDoCafe entity = categoriaRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Categoria não encontrada.");
        }
        entity.setNome(dto.nome());
        entity.setDescricao(dto.descricao());
        return CategoriaDoCafeDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CategoriaDoCafe entity = categoriaRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Categoria não encontrada.");
        }
        if (cafeRepository.countByCategoriaDoCafe(id) > 0) {
            throw new ConstraintViolationException("Não é possível excluir. Esta categoria possui cafés vinculados.", null);
        }
        categoriaRepository.delete(entity);
    }

    @Override
    public List<CategoriaDoCafeDTOResponse> findAll() {
        return categoriaRepository.listAll().stream()
                .map(CategoriaDoCafeDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
    
    @Override
    public CategoriaDoCafeDTOResponse findById(Long id) {
        CategoriaDoCafe entity = categoriaRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Categoria não encontrada.");
        }
        return CategoriaDoCafeDTOResponse.valueOf(entity);
    }

    @Override
    public List<CategoriaDoCafeDTOResponse> findByNome(String nome) {
        return categoriaRepository.findByNome(nome).stream()
                .map(CategoriaDoCafeDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}
