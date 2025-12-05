package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;

public interface EnderecoService {

    // --- CRUD ---
    EnderecoDTOResponse create(EnderecoDTO dto);
    EnderecoDTOResponse update(Long id, EnderecoDTO dto);
    void delete(Long id);

    // --- CONSULTAS ---
    EnderecoDTOResponse findById(Long id);
    List<EnderecoDTOResponse> findAll();
    
    // Buscas específicas
    List<EnderecoDTOResponse> findByCep(String cep);
    List<EnderecoDTOResponse> findByRua(String rua);
}