package br.unitins.topicos1.lgc.Estado.service;

import java.util.List;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;

public interface EstadoService {
    
    List<EstadoDTOResponse> findAll();
    List<EstadoDTOResponse> findByNome(String nome);
    EstadoDTOResponse findById(Long id);


    EstadoDTOResponse create(EstadoDTO dto);
    void update(Long id, EstadoDTO dto);
    void delete(Long id);
    
}
