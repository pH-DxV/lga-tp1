package br.unitins.topicos1.lgc.CategoriaDoCafe.service;

import java.util.List;

import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;

public interface CategoriaDoCafeService {

    List<CategoriaDoCafeDTOResponse> findAll();
    CategoriaDoCafeDTOResponse findById(Long id);
    List<CategoriaDoCafeDTOResponse> findByNome(String nome);

    CategoriaDoCafeDTOResponse create(CategoriaDoCafeDTO dto);
    CategoriaDoCafeDTOResponse update(Long id, CategoriaDoCafeDTO dto);
    void delete(Long id);
    
}
