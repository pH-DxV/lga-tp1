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
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CafeServiceImpl implements CafeService {

    @Inject
    CafeRepository cafeRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    CategoriaDoCafeRepository categoriaRepository;
    
    // TODO: Injetar RegiaoDeOrigemRepository quando estiver pronto

    @Override
    @Transactional
    public CafeDTOResponse create(CafeDTO dto) {
        Cafe cafe = new Cafe();
        
        // --- ADICIONADO ---
        cafe.setNome(dto.nome());
        cafe.setDescricao(dto.descricao());
        // --- FIM DA ADIÇÃO ---
        
        // 1. Busca as entidades relacionadas pelos IDs
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada.");
        }
        
        CategoriaDoCafe categoria = categoriaRepository.findById(dto.idCategoriaDoCafe());
        if (categoria == null) {
            throw new NotFoundException("Categoria do Café não encontrada.");
        }
        
        // TODO: Buscar RegiaoDeOrigem
        
        // 2. Converte os IDs dos Enums para os Enums reais
        NivelDeTorra nivelTorra = NivelDeTorra.valueOf(dto.idNivelDeTorra());
        Tratamento tratamento = Tratamento.valueOf(dto.idTratamento());
        
        // 3. Define os atributos
        cafe.setMarca(marca);
        cafe.setCategoriaDoCafe(categoria);
        cafe.setNivelDeTorra(nivelTorra);
        cafe.setTratamento(tratamento);
        cafe.setNotasSensoriais(dto.notasSensoriais());
        cafe.setPontuacaoSCA(dto.pontuacaoSCA());
        cafe.setPreco(dto.preco());
        cafe.setPeso(dto.peso());
        cafe.setEstoque(dto.estoque());
        
        // 4. Persiste no banco
        cafeRepository.persist(cafe);
        
        return CafeDTOResponse.valueOf(cafe);
    }

    @Override
    @Transactional
    public CafeDTOResponse update(Long id, CafeDTO dto) {
        Cafe cafe = cafeRepository.findById(id);
        if (cafe == null) {
            throw new NotFoundException("Café não encontrado.");
        }

        // --- ADICIONADO ---
        cafe.setNome(dto.nome());
        cafe.setDescricao(dto.descricao());
        // --- FIM DA ADIÇÃO ---
        
        // Repete a mesma lógica do create para buscar e atualizar
        Marca marca = marcaRepository.findById(dto.idMarca());
        if (marca == null) {
            throw new NotFoundException("Marca não encontrada.");
        }
        
        CategoriaDoCafe categoria = categoriaRepository.findById(dto.idCategoriaDoCafe());
        if (categoria == null) {
            throw new NotFoundException("Categoria do Café não encontrada.");
        }
        
        // TODO: Buscar RegiaoDeOrigem
        
        NivelDeTorra nivelTorra = NivelDeTorra.valueOf(dto.idNivelDeTorra());
        Tratamento tratamento = Tratamento.valueOf(dto.idTratamento());
        
        cafe.setMarca(marca);
        cafe.setCategoriaDoCafe(categoria);
        cafe.setNivelDeTorra(nivelTorra);
        cafe.setTratamento(tratamento);
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
        if (cafe == null) {
            throw new NotFoundException("Café não encontrado.");
        }
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