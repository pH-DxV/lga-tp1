package br.unitins.topicos1.lgc.Marca.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.Marca.model.Marca;
import br.unitins.topicos1.lgc.Marca.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService {

    @Inject
    MarcaRepository marcaRepository;

    @Override
    public List<MarcaDTOResponse> findAll() {
        return marcaRepository.listAll().stream()
                .map(MarcaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<MarcaDTOResponse> findByNome(String nome) {
        return marcaRepository.findByNome(nome).stream()
                .map(MarcaDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public MarcaDTOResponse findById(Long id) {
        Marca marca = marcaRepository.findById(id);
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada para o ID: " + id);
        }
        return MarcaDTOResponse.valueOf(marca);
    }
}