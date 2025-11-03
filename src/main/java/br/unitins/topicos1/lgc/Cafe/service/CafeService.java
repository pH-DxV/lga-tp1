package br.unitins.topicos1.lgc.Cafe.service;

import java.util.List;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;

public interface CafeService {

    CafeDTOResponse create(CafeDTO dto);
    CafeDTOResponse update(Long id, CafeDTO dto);
    void delete(Long id);
    
    CafeDTOResponse findById(Long id);
    List<CafeDTOResponse> findAll();
    List<CafeDTOResponse> findByNome(String nome);
    List<CafeDTOResponse> findByPontuacao(Integer minSCA, Integer maxSCA);
}