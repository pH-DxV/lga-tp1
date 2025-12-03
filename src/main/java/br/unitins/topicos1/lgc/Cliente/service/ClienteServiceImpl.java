package br.unitins.topicos1.lgc.Cliente.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import br.unitins.topicos1.lgc.Cliente.repository.ClienteRepository;
import br.unitins.topicos1.lgc.Hash.service.HashService;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import jakarta.validation.ValidationException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;

    @Inject
    HashService hashService;

    @Inject
    JsonWebToken jwt; // INJETADO: Para pegar o login do usuário logado

    // --- FUNÇÃO AUXILIAR DE SEGURANÇA ---
    /**
     * Valida se o usuário logado (do token) tem permissão de Administrador
     * ou se está agindo sobre o próprio recurso (self-service).
     * @param idClienteIdDaUrl o ID do cliente que está sendo acessado/modificado.
     */
    private void validarPermissao(Long idClienteIdDaUrl) {
        boolean isAdmin = jwt.getGroups().contains("Administrador");

        // Se for Admin, ele pode agir sobre qualquer ID.
        if (isAdmin) return;
        
        // Se não for Admin, precisa ser self-service.
        // O ID do usuário logado é o 'id' que colocamos no token (claim customizada).
        Long idLogado = jwt.getClaim("id"); 
        
        if (!idClienteIdDaUrl.equals(idLogado)) {
            // Se o ID da URL não for o mesmo ID do token, é Forbidden (403)
            throw new ForbiddenException("Você não tem permissão para gerenciar a conta de outro cliente.");
        }
    }
    // -------------------------------------

    @Override
    @Transactional
    public ClienteDTOResponse create(ClienteDTO dto) {
        // Validações de unicidade (CPF e Login)
        if (repository.findByCpf(dto.cpf()) != null) {
            throw new ValidationException("CPF já cadastrado.");
        }
        if (repository.findByLogin(dto.login()) != null) {
            throw new ValidationException("Login já cadastrado.");
        }

        Cliente entity = new Cliente();
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        
        entity.setSenha(hashService.getHashSenha(dto.senha()));
        entity.setPerfis(Set.of(Perfil.USER)); // Força perfil
        entity.setDataNascimento(dto.dataNascimento());
        
        repository.persist(entity);
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public ClienteDTOResponse update(Long id, ClienteDTO dto) {
        // --- ADIÇÃO DA VALIDAÇÃO ---
        validarPermissao(id); 
        // --------------------------

        Cliente entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Cliente não encontrado.");
        }
        
        // Atualizações:
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        entity.setSenha(hashService.getHashSenha(dto.senha()));
        entity.setDataNascimento(dto.dataNascimento());
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // --- ADIÇÃO DA VALIDAÇÃO ---
        validarPermissao(id);
        // --------------------------

        if (!repository.deleteById(id)) throw new NotFoundException("Cliente não encontrado.");
    }

    @Override
    public ClienteDTOResponse findById(Long id) {
        // --- ADIÇÃO DA VALIDAÇÃO ---
        validarPermissao(id);
        // --------------------------

        Cliente entity = repository.findById(id);
        if (entity == null) throw new NotFoundException("Cliente não encontrado.");
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    public List<ClienteDTOResponse> findAll() {
        return repository.listAll().stream().map(ClienteDTOResponse::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTOResponse> findByNome(String nome) {
        return repository.findByNome(nome).stream().map(ClienteDTOResponse::valueOf).collect(Collectors.toList());
    }
}