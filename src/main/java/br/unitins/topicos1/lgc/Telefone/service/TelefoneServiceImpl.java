package br.unitins.topicos1.lgc.Telefone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Security.service.SecurityService;
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
    UsuarioRepository usuarioRepository; 

    @Inject
    JsonWebToken jwt;
    
    @Inject
    SecurityService securityService; // Injeção da Interface SecurityService

    @Override
    @Transactional
    public TelefoneDTOResponse create(TelefoneDTO dto) {
        // Lógica para determinar o dono do telefone (similar ao que discutimos)
        Long idUsuarioAlvo = null;
        
        // Tenta pegar do Token (se for auto-cadastro)
        // Nota: O claim "id" deve ter sido adicionado no JwtService
        if (jwt.getClaim("id") != null) {
             idUsuarioAlvo = Long.parseLong(jwt.getClaim("id").toString());
        }
        
        // Se o DTO tiver o campo idUsuario (para Admin criar para outros), 
        // você deve adicionar a lógica aqui para ler do DTO se o token for de Admin.

        if (idUsuarioAlvo == null) {
            throw new NotFoundException("Não foi possível identificar o usuário dono do telefone.");
        }
        
        Usuario usuario = usuarioRepository.findById(idUsuarioAlvo);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        // Validação de Segurança usando o serviço injetado
        securityService.validarPermissao(usuario);

        Telefone entity = new Telefone();
        entity.setDdd(dto.ddd());
        entity.setNumero(dto.numero());
        entity.setUsuario(usuario);

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
        
        // Validação de Segurança
        securityService.validarPermissao(entity.getUsuario());
        
        entity.setDdd(dto.ddd());
        entity.setNumero(dto.numero());
        
        return TelefoneDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Telefone entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Telefone não encontrado.");
        }
        
        // Validação de Segurança
        securityService.validarPermissao(entity.getUsuario());
        
        repository.delete(entity);
    }

    @Override
    public TelefoneDTOResponse findById(Long id) {
        Telefone entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Telefone não encontrado.");
        }
        
        // Validação de Segurança
        securityService.validarPermissao(entity.getUsuario());
        
        return TelefoneDTOResponse.valueOf(entity);
    }

    @Override
    public List<TelefoneDTOResponse> findAll() {
        // Geralmente restrito a Admin no Resource.
        // Se precisar de validação aqui, seria algo como:
        // securityService.validarPermissao((Usuario) null); // Isso exigiria adaptação no SecurityService para aceitar null como "apenas admin"
        
        return repository.listAll().stream()
                .map(TelefoneDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<TelefoneDTOResponse> findByUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");
        
        // Validação de Segurança
        securityService.validarPermissao(usuario);

        return repository.findByUsuario(idUsuario).stream()
                .map(TelefoneDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}