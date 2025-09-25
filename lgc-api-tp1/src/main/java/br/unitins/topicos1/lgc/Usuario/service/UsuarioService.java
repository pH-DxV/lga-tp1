package br.unitins.topicos1.lgc.Usuario.service;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;

public interface UsuarioService {
    

    List<Usuario> findAll();
    List<Usuario> findByNome(String nome);
    Usuario findById(Long id);

    
    Usuario create(UsuarioDTO dto);
    void update(Long id, UsuarioDTO dto);
    void delete(Long id);

    
}
