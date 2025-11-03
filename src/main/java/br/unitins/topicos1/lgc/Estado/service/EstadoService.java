package br.unitins.topicos1.lgc.Estado.service;

import java.util.List;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;

public interface EstadoService {

    // Métodos de Consulta
    List<EstadoDTOResponse> findAll();
    List<EstadoDTOResponse> findByNome(String nome);
    EstadoDTOResponse findById(Long id); // Adicionei este, pois é padrão

    // Métodos de Modificação
    EstadoDTOResponse create(EstadoDTO dto);
    EstadoDTOResponse update(Long id, EstadoDTO dto);
    void delete(Long id);
}
