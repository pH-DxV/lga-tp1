package br.unitins.topicos1.lgc.Marca.service;

import java.util.List;

import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;

public interface MarcaService {

    List<MarcaDTOResponse> findAll();
    List<MarcaDTOResponse> findByNome(String nome);
    List<MarcaDTOResponse> findById(Long id);

    MarcaDTOResponse create (MarcaDTO dto);
    MarcaDTOResponse update (Long id, MarcaDTO dto);
    void delete (Long id);
    

    
}
