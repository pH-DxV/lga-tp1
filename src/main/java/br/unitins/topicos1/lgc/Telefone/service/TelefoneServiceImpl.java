package br.unitins.topicos1.lgc.Telefone.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;
import br.unitins.topicos1.lgc.Telefone.repository.TelefoneRepository;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    @Inject
    TelefoneRepository repository;

    @Inject
    UsuarioRepository usuarioRepository; // Necessário para associar

    @Override
    @Transactional
    public TelefoneDTOResponse create(TelefoneDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        Telefone entity = new Telefone();
        entity.setDdd(dto.ddd());
        entity.setNumero(dto.numero());
        entity.setUsuario(usuario); // Associa o telefone ao usuário

        repository.persist(entity);

        return TelefoneDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public TelefoneDTOResponse update(Long id, TelefoneDTO dto) {
        Telefone entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Telefone não encontrado.");
        }
        
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        entity.setDdd(dto.ddd());
        entity.setNumero(dto.numero());
        entity.setUsuario(usuario);

        return TelefoneDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Telefone não encontrado.");
        }
    }

    @Override
    public TelefoneDTOResponse findById(Long id) {
        Telefone entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Telefone não encontrado.");
        }
        return TelefoneDTOResponse.valueOf(entity);
    }

    @Override
    public List<TelefoneDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(TelefoneDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<TelefoneDTOResponse> findByUsuario(Long idUsuario) {
        return repository.findByUsuario(idUsuario).stream()
                .map(TelefoneDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}