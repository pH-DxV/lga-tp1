package br.unitins.topicos1.lgc.Usuario.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Override
    @Transactional
    public UsuarioDTOResponse create(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setPeso(dto.peso());
        
        repository.persist(entity);
        
        return UsuarioDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public UsuarioDTOResponse update(Long id, UsuarioDTO dto) {
        Usuario entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setPeso(dto.peso());
        
        return UsuarioDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // ATENÇÃO: Ao deletar um usuário, os telefones e endereços
        // serão apagados em cascata (devido ao 'cascade = CascadeType.ALL')
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    @Override
    public UsuarioDTOResponse findById(Long id) {
        Usuario entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }
        return UsuarioDTOResponse.valueOf(entity);
    }

    @Override
    public List<UsuarioDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(UsuarioDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(UsuarioDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}