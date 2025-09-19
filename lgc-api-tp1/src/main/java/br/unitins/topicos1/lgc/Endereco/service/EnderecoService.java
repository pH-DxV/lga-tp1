package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;

public interface EnderecoService {

    List<Endereco> findAll();
    List<Endereco> findByCep(String cep);
    List<Endereco> findByRua(String rua);
    Endereco findById(Long id);

    Endereco create(EnderecoDTO dto);
    void update(Long id, EnderecoDTO dto);
    void delete(Long id);
}
