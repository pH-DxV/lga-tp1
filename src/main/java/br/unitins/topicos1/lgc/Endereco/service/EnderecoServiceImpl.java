package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.repository.EnderecoRepository;
import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    MunicipioRepository municipioRepository;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public EnderecoDTOResponse create(EnderecoDTO dto) {
        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) throw new NotFoundException("Município não encontrado.");
        
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        Endereco entity = new Endereco();
        entity.setCep(dto.cep());
        entity.setRua(dto.rua());
        entity.setNumero(dto.numero());
        entity.setComplemento(dto.complemento());
        entity.setBairro(dto.bairro());
        entity.setMunicipio(municipio);
        entity.setUsuario(usuario);

        repository.persist(entity);

        return EnderecoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public EnderecoDTOResponse update(Long id, EnderecoDTO dto) {
        Endereco entity = repository.findById(id);
        if (entity == null) throw new NotFoundException("Endereço não encontrado.");

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) throw new NotFoundException("Município não encontrado.");

        entity.setCep(dto.cep());
        entity.setRua(dto.rua());
        entity.setNumero(dto.numero());
        entity.setComplemento(dto.complemento());
        entity.setBairro(dto.bairro());
        entity.setMunicipio(municipio);

        return EnderecoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) throw new NotFoundException("Endereço não encontrado.");
    }

    @Override
    public EnderecoDTOResponse findById(Long id) {
        Endereco entity = repository.findById(id);
        if (entity == null) throw new NotFoundException("Endereço não encontrado.");
        return EnderecoDTOResponse.valueOf(entity);
    }

    @Override
    public List<EnderecoDTOResponse> findAll() {
        return repository.listAll().stream().map(EnderecoDTOResponse::valueOf).collect(Collectors.toList());
    }

    @Override
    public List<EnderecoDTOResponse> findByCep(String cep) {
        return repository.findByCep(cep).stream().map(EnderecoDTOResponse::valueOf).collect(Collectors.toList());
    }
    
    @Override
    public List<EnderecoDTOResponse> findByRua(String rua) {
        return repository.findByRua(rua).stream().map(EnderecoDTOResponse::valueOf).collect(Collectors.toList());
    }
}