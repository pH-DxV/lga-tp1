package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;

public interface EnderecoService {

    // --- MÉTODOS DE CONSULTA (retornam DTOResponse) ---
    List<EnderecoDTOResponse> findAll();
    List<EnderecoDTOResponse> findByCep(String cep);
    List<EnderecoDTOResponse> findByRua(String rua);

    // --- MÉTODOS DE MODIFICAÇÃO ---
    EnderecoDTOResponse create(EnderecoDTO dto);
    EnderecoDTOResponse update(Long id, EnderecoDTO dto); // Mudei de 'void' para retornar o DTO (melhor prática)
    void delete(Long id);
}