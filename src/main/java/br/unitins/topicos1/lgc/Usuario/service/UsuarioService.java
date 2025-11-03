package br.unitins.topicos1.lgc.Usuario.service;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

public interface UsuarioService {

    // --- CRUD ---
    UsuarioDTOResponse create(UsuarioDTO dto);
    UsuarioDTOResponse update(Long id, UsuarioDTO dto);
    void delete(Long id);

    // --- CONSULTAS ---
    UsuarioDTOResponse findById(Long id);
    List<UsuarioDTOResponse> findAll();
    List<UsuarioDTOResponse> findByNome(String nome);
}