package br.unitins.topicos1.lgc.Municipio.service;

import java.util.List;

import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;

public interface MunicipioService {

    // Métodos de Consulta
    List<MunicipioDTOResponse> findAll();
    MunicipioDTOResponse findById(Long id); // Essencial ter
    List<MunicipioDTOResponse> findByNome(String nome);

    // Métodos de Modificação
    MunicipioDTOResponse create(MunicipioDTO dto);
    MunicipioDTOResponse update(Long id, MunicipioDTO dto);
    void delete(Long id);
}