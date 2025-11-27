package br.unitins.topicos1.lgc.Cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import br.unitins.topicos1.lgc.CategoriaDoCafe.repository.CategoriaDoCafeRepository;
import br.unitins.topicos1.lgc.Marca.model.Marca;
import br.unitins.topicos1.lgc.Marca.repository.MarcaRepository;
import br.unitins.topicos1.lgc.NivelDeTorra.model.NivelDeTorra;
import br.unitins.topicos1.lgc.Tratamento.model.Tratamento;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CafeServiceImpl implements CafeService {

    @Inject
    CafeRepository cafeRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    CategoriaDoCafeRepository categoriaRepository;

    @Override
    public boolean verificarEstoque(Long idCafe, Integer quantidade) {
        Cafe cafe = cafeRepository.findById(idCafe);
        if (cafe == null) return false;
        return cafe.getEstoque() >= quantidade;
    }

    @Override
    @Transactional
    public void baixarEstoque(Long idCafe, Integer quantidade) {
        Cafe cafe = cafeRepository.findById(idCafe);
        
        if (cafe == null) {
            throw new NotFoundException("Café não encontrado.");
        }
        if (cafe.getEstoque() < quantidade) {
            throw new BadRequestException("Estoque insuficiente para o café: " + cafe.getNome());
        }

        // Lógica de Baixa
        cafe.setEstoque(cafe.getEstoque() - quantidade);
    }

    @Override
    @Transactional
    public CafeDTOResponse create(CafeDTO dto) {
        Cafe cafe = new Cafe();
        
        cafe.setNome(dto.nome());
        cafe.setDescricao(dto.descricao());
        
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) throw new NotFoundException("Marca não encontrada.");
        cafe.setMarca(marca);
        
        CategoriaDoCafe categoria = categoriaRepository.findById(dto.idCategoriaDoCafe());
        if (categoria == null) throw new NotFoundException("Categoria do Café não encontrada.");
        cafe.setCategoriaDoCafe(categoria);
        
        cafe.setNivelDeTorra(NivelDeTorra.valueOf(dto.idNivelDeTorra()));
        cafe.setTratamento(Tratamento.valueOf(dto.idTratamento()));
        
        cafe.setNotasSensoriais(dto.notasSensoriais());
        cafe.setPontuacaoSCA(dto.pontuacaoSCA());
        cafe.setPreco(dto.preco());
        cafe.setPeso(dto.peso());
        cafe.setEstoque(dto.estoque());
        
        cafeRepository.persist(cafe);
        
        return CafeDTOResponse.valueOf(cafe);
    }

    @Override
    @Transactional
    public CafeDTOResponse update(Long id, CafeDTO dto) {
        Cafe cafe = cafeRepository.findById(id);
        if (cafe == null) throw new NotFoundException("Café não encontrado.");

        cafe.setNome(dto.nome());
        cafe.setDescricao(dto.descricao());

        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) throw new NotFoundException("Marca não encontrada.");
        cafe.setMarca(marca);
        
        CategoriaDoCafe categoria = categoriaRepository.findById(dto.idCategoriaDoCafe());
        if (categoria == null) throw new NotFoundException("Categoria do Café não encontrada.");
        cafe.setCategoriaDoCafe(categoria);
        
        cafe.setNivelDeTorra(NivelDeTorra.valueOf(dto.idNivelDeTorra()));
        cafe.setTratamento(Tratamento.valueOf(dto.idTratamento()));
        
        cafe.setNotasSensoriais(dto.notasSensoriais());
        cafe.setPontuacaoSCA(dto.pontuacaoSCA());
        cafe.setPreco(dto.preco());
        cafe.setPeso(dto.peso());
        cafe.setEstoque(dto.estoque());
        
        return CafeDTOResponse.valueOf(cafe);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!cafeRepository.deleteById(id)) {
            throw new NotFoundException("Café não encontrado.");
        }
    }

    @Override
    public CafeDTOResponse findById(Long id) {
        Cafe cafe = cafeRepository.findById(id);
        if (cafe == null) throw new NotFoundException("Café não encontrado.");
        return CafeDTOResponse.valueOf(cafe);
    }

    @Override
    public List<CafeDTOResponse> findAll() {
        return cafeRepository.listAll().stream()
                .map(CafeDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTOResponse> findByNome(String nome) {
        return cafeRepository.findByNomeLike(nome).stream()
                .map(CafeDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTOResponse> findByPontuacao(Integer minSCA, Integer maxSCA) {
        return cafeRepository.findByPontuacaoRange(minSCA, maxSCA).stream()
                .map(CafeDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}