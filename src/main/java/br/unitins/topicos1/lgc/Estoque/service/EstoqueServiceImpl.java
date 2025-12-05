package br.unitins.topicos1.lgc.Estoque.service;

import java.time.LocalDateTime;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.Estoque.model.Estoque;
import br.unitins.topicos1.lgc.Estoque.repository.EstoqueRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EstoqueServiceImpl implements EstoqueService {

    @Inject
    EstoqueRepository estoqueRepository;

    @Inject
    CafeRepository cafeRepository;

    @Override
    @Transactional
    public void iniciarEstoque(Long idCafe, Integer quantidadeInicial) {
        Cafe cafe = cafeRepository.findById(idCafe);
        if (cafe == null) throw new NotFoundException("Café não encontrado.");
        
        // Evita duplicidade
        if (estoqueRepository.findByIdCafe(idCafe) != null) {
             throw new BadRequestException("O estoque para este café já foi iniciado.");
        }

        Estoque estoque = new Estoque();
        estoque.setCafe(cafe);
        estoque.setQuantidade(quantidadeInicial != null ? quantidadeInicial : 0);
        estoque.setDataUltimaMovimentacao(LocalDateTime.now());
        
        estoqueRepository.persist(estoque);
    }

    @Override
    @Transactional
    public void adicionarEstoque(Long idCafe, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByIdCafe(idCafe);
        if (estoque == null) throw new NotFoundException("Estoque não iniciado para este produto.");
        
        if (quantidade == null || quantidade <= 0) throw new BadRequestException("Quantidade deve ser positiva.");

        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoque.setDataUltimaMovimentacao(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void baixarEstoque(Long idCafe, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByIdCafe(idCafe);
        
        if (estoque == null) {
            throw new NotFoundException("Estoque não encontrado para este produto.");
        }
        
        if (estoque.getQuantidade() < quantidade) {
            throw new BadRequestException("Estoque insuficiente. Disponível: " + estoque.getQuantidade());
        }

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoque.setDataUltimaMovimentacao(LocalDateTime.now());
    }

    @Override
    public Integer consultarQuantidade(Long idCafe) {
        Estoque estoque = estoqueRepository.findByIdCafe(idCafe);
        return (estoque != null) ? estoque.getQuantidade() : 0;
    }
}