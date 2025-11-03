package br.unitins.topicos1.lgc.Endereco.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.repository.EnderecoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Override
    public List<EnderecoDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(e -> EnderecoDTOResponse.valueOf(e)) // Converte Model para DTO
                .collect(Collectors.toList());
    }

    @Override
    public List<EnderecoDTOResponse> findByCep(String cep) {
        return repository.findByCep(cep).stream() // Supondo que findByCep retorne List<Endereco>
                .map(EnderecoDTOResponse::valueOf) // Converte Model para DTO
                .collect(Collectors.toList());
    }
    
    @Override
    public List<EnderecoDTOResponse> findByRua(String rua) {
        return repository.findByRua(rua).stream() // Supondo que findByRua retorne List<Endereco>
                .map(EnderecoDTOResponse::valueOf) // Converte Model para DTO
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnderecoDTOResponse create(EnderecoDTO dto) {
        Endereco entity = new Endereco();
        entity.setCep(dto.cep());
        entity.setRua(dto.rua());
        entity.setComplemento(dto.complemento());
        
        repository.persist(entity);
        
        return EnderecoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public EnderecoDTOResponse update(Long id, EnderecoDTO dto) {
        Endereco entity = repository.findById(id);
        if (entity == null) {
            throw new NotFoundException("Endereço não encontrado.");
        }
        entity.setCep(dto.cep());
        entity.setRua(dto.rua());
        entity.setComplemento(dto.complemento());
        
        return EnderecoDTOResponse.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Endereço não encontrado.");
        }
    }
}