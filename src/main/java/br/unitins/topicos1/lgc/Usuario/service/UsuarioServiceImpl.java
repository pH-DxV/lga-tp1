package br.unitins.topicos1.lgc.Usuario.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Hash.service.HashService;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
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

    @Inject
    HashService hashService; // Injeta o serviço de criptografia

    @Override
    @Transactional
    public UsuarioDTOResponse create(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        
        // --- CRIPTOGRAFAR A SENHA ---
        String senhaHash = hashService.getHashSenha(dto.senha());
        entity.setSenha(senhaHash);

        // --- DEFINIR PERFIL ---
        // Transforma o ID (1 ou 2) no Enum Perfil e adiciona ao Set
        entity.setPerfis(Set.of(Perfil.valueOf(dto.idPerfil().longValue())));

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
        entity.setLogin(dto.login());
        
        // Atualização de senha deve ser feita com cuidado. 
        // Aqui estamos permitindo atualizar direto, mas o ideal é um endpoint separado.
        String senhaHash = hashService.getHashSenha(dto.senha());
        entity.setSenha(senhaHash);

        // Atualiza o perfil
        entity.setPerfis(Set.of(Perfil.valueOf(dto.idPerfil().longValue())));

        entity.setDataNascimento(dto.dataNascimento());
        entity.setPeso(dto.peso());
        
        return UsuarioDTOResponse.valueOf(entity);
    }

    // --- Método Auxiliar para o Login (que você pediu antes) ---
    @Override
    public Usuario findByLoginAndSenha(String login, String senhaHash) {
        // A senha que chega aqui JÁ DEVE ESTAR hashada pelo AuthResource
        return repository.findByLoginAndSenha(login, senhaHash);
    }

    // ... (métodos delete, findById, findAll, findByNome mantêm-se iguais)
    
    @Override
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new NotFoundException("Usuário não encontrado.");
    }

    @Override
    public UsuarioDTOResponse findById(Long id) {
        Usuario entity = repository.findById(id);
        if (entity == null) throw new NotFoundException("Usuário não encontrado.");
        return UsuarioDTOResponse.valueOf(entity);
    }

    @Override
    public List<UsuarioDTOResponse> findAll() {
        return repository.listAll().stream().map(UsuarioDTOResponse::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(UsuarioDTOResponse::valueOf).collect(Collectors.toList());
    }
}