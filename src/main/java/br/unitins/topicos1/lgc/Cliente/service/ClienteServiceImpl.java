package br.unitins.topicos1.lgc.Cliente.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
import br.unitins.topicos1.lgc.Cliente.model.Cliente;
import br.unitins.topicos1.lgc.Cliente.repository.ClienteRepository;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Hash.service.HashService;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;

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
    JsonWebToken jwt;
    
    @Inject
    MunicipioRepository municipioRepository; // Necessário para buscar o municipio do endereço

    // --- FUNÇÃO AUXILIAR DE SEGURANÇA ---
    private void validarPermissao(Long idClienteIdDaUrl) {
        String loginLogado = jwt.getName();
        boolean isAdmin = jwt.getGroups().contains("Administrador");
        if (isAdmin) return;
        
        // Em alguns JWTs o ID pode vir como claim, se não, usamos login para comparar
        // Assumindo que você configurou para vir o claim 'id'
        try {
            Long idLogado = Long.parseLong(jwt.getClaim("id").toString());
            if (!idClienteIdDaUrl.equals(idLogado)) {
                throw new ForbiddenException("Você não tem permissão para gerenciar a conta de outro cliente.");
            }
        } catch (Exception e) {
             // Fallback se não tiver claim ID: tenta comparar login se possível ou bloqueia
             // Por segurança, se não conseguir validar, bloqueia.
             if (!isAdmin) throw new ForbiddenException("Erro ao validar permissão do usuário.");
        }
    }

    @Override
    @Transactional
    public ClienteDTOResponse create(ClienteDTO dto) {
        // Validações de unicidade
        if (repository.findByCpf(dto.cpf()) != null) throw new ValidationException("CPF já cadastrado.");
        if (repository.findByLogin(dto.login()) != null) throw new ValidationException("Login já cadastrado.");

        Cliente entity = new Cliente();
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        entity.setSenha(hashService.getHashSenha(dto.senha()));
        entity.setPerfis(Set.of(Perfil.USER)); // Força perfil
        entity.setDataNascimento(dto.dataNascimento());
        
        // --- 1. PROCESSAR TELEFONES (Obrigatórios) ---
        // O DTO já deve ter validado @NotNull e @Size(min=1)
        List<Telefone> telefones = new ArrayList<>();
        
        for (TelefoneDTO telDto : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(telDto.ddd());
            telefone.setNumero(telDto.numero());
            telefone.setUsuario(entity); // Vínculo Bidirecional
            telefones.add(telefone);
        }
        entity.setTelefones(telefones);

        // --- 2. PROCESSAR ENDEREÇOS (Obrigatórios) ---
        List<Endereco> enderecos = new ArrayList<>();
        
        for (EnderecoDTO endDto : dto.enderecos()) {
            // Busca o município (o DTO de endereço deve ter idMunicipio)
            Municipio municipio = municipioRepository.findById(endDto.idMunicipio());
            if (municipio == null) {
                throw new NotFoundException("Município não encontrado (ID: " + endDto.idMunicipio() + ")");
            }

            Endereco endereco = new Endereco();
            endereco.setCep(endDto.cep());
            endereco.setRua(endDto.rua());
            endereco.setNumero(endDto.numero());
            endereco.setComplemento(endDto.complemento());
            endereco.setBairro(endDto.bairro());
            endereco.setMunicipio(municipio);
            endereco.setUsuario(entity); // Vínculo Bidirecional
            enderecos.add(endereco);
        }
        entity.setEnderecos(enderecos);
        
        // O Persist no Pai salva os filhos em cascata (CascadeType.ALL)
        repository.persist(entity);
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public ClienteDTOResponse update(Long id, ClienteDTO dto) {
        validarPermissao(id);
        Cliente entity = repository.findById(id);
        if (entity == null) throw new NotFoundException("Cliente não encontrado.");
        
        // Atualiza dados básicos
        entity.setNome(dto.nome());
        entity.setCpf(dto.cpf());
        entity.setLogin(dto.login());
        
        // Se a senha vier preenchida, atualiza. (Lógica opcional, mas comum)
        if (dto.senha() != null && !dto.senha().isBlank()) {
             entity.setSenha(hashService.getHashSenha(dto.senha()));
        }
        
        entity.setDataNascimento(dto.dataNascimento());
        
        // NOTA: Geralmente não atualizamos listas inteiras (telefones/endereços) no PUT do cliente
        // para não apagar dados sem querer. O ideal é usar os endpoints específicos de
        // telefone/endereço para adicionar ou remover itens.
        // Mas se quiser substituir tudo, a lógica seria limpar a lista atual e adicionar a nova.
        
        return ClienteDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        validarPermissao(id);
        if (!repository.deleteById(id)) throw new NotFoundException("Cliente não encontrado.");
    }

    @Override
    public ClienteDTOResponse findById(Long id) {
        validarPermissao(id);
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