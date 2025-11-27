package br.unitins.topicos1.lgc.Cliente.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import br.unitins.topicos1.lgc.Cliente.repository.ClienteRepository;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.validation.ValidationException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;

    @Inject
    br.unitins.topicos1.lgc.Hash.service.HashService hashService; // Injeta o serviço de criptografia

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
        
        // 1. Criptografia
        entity.setSenha(hashService.getHashSenha(dto.senha()));

        // 2. REGRA DE NEGÓCIO: Força o perfil de Usuário Comum (USER)
        entity.setPerfis(Set.of(Perfil.USER));

        entity.setDataNascimento(dto.dataNascimento());
        
        repository.persist(entity);
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public ClienteDTOResponse update(Long id, ClienteDTO dto) {
        Cliente entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Cliente não encontrado.");
        }
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        
        // Atualiza a senha
        entity.setSenha(hashService.getHashSenha(dto.senha()));
        
        // Mantém o perfil inalterado
        
        entity.setDataNascimento(dto.dataNascimento());
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new NotFoundException("Cliente não encontrado.");
    }

    @Override
    public ClienteDTOResponse findById(Long id) {
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