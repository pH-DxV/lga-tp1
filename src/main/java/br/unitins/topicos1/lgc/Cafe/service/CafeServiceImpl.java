package br.unitins.topicos1.lgc.Cafe.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.CategoriaDoCafe.model.CategoriaDoCafe;
import br.unitins.topicos1.lgc.CategoriaDoCafe.repository.CategoriaDoCafeRepository;
import br.unitins.topicos1.lgc.Estoque.service.EstoqueService;
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

    @Inject
    EstoqueService estoqueService; 

    // --- Método Auxiliar para Conversão (Resolve o erro de inferência) ---
    private CafeDTOResponse converterParaDTO(Cafe cafe) {
        Integer saldo = estoqueService.consultarQuantidade(cafe.getId());
        return CafeDTOResponse.valueOf(cafe, saldo);
    }
    // ---------------------------------------------------------------------

    @Override
    public boolean verificarEstoque(Long idCafe, Integer quantidade) {
        Integer saldoAtual = estoqueService.consultarQuantidade(idCafe);
        return saldoAtual >= quantidade;
    }

    @Override
    @Transactional
    public void baixarEstoque(Long idCafe, Integer quantidade) {
        estoqueService.baixarEstoque(idCafe, quantidade);
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
        
        cafeRepository.persist(cafe);
        
        // Inicia o estoque
        estoqueService.iniciarEstoque(cafe.getId(), dto.estoque());
        
        // Retorna o DTO com o estoque inicial que acabamos de setar
        return CafeDTOResponse.valueOf(cafe, dto.estoque());
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
        
        // Busca o saldo atual para retornar no DTO
        Integer saldo = estoqueService.consultarQuantidade(id);
        
        return CafeDTOResponse.valueOf(cafe, saldo);
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
        
        return converterParaDTO(cafe);
    }

    @Override
    public List<CafeDTOResponse> findAll() {
        // CORREÇÃO AQUI: Usando o método auxiliar para evitar erro de inferência
        return cafeRepository.listAll().stream()
                .map(this::converterParaDTO) 
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTOResponse> findByNome(String nome) {
        // CORREÇÃO AQUI
        return cafeRepository.findByNomeLike(nome).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTOResponse> findByPontuacao(Integer minSCA, Integer maxSCA) {
        // CORREÇÃO AQUI
        return cafeRepository.findByPontuacaoRange(minSCA, maxSCA).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }
}