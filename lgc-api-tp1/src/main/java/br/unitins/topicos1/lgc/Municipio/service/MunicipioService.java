package br.unitins.topicos1.lgc.Municipio.service;

import java.util.List;

import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;

public interface MunicipioService {

    List<Municipio> findAll();
    List<Municipio> findByNome(String nome);
    Municipio findById(Long id);


    Municipio create(MunicipioDTO dto);
    void update(Long id, MunicipioDTO dto);
    void delete(Long id);

}