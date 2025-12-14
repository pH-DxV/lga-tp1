package br.unitins.topicos1.lgc.Cafe.dto;

import java.util.Set;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.NivelDeTorra.model.NivelDeTorra;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.Tratamento.model.Tratamento;

public record CafeDTOResponse(
    Long id,
    String nome,
    String descricao,
    MarcaDTOResponse marca,
    CategoriaDoCafeDTOResponse categoriaDoCafe,
    // RegiaoDeOrigemDTOResponse regiaoDeOrigem, 
    NivelDeTorra nivelDeTorra,
    Tratamento tratamento,
    Set<NotaSensorial> notasSensoriais,
    Integer pontuacaoSCA,
    Double preco,
    Double peso,
    Integer estoque // O campo continua existindo para o Front-end
) {
    
    // --- MÉTODO PRINCIPAL ATUALIZADO ---
    // Agora recebe o 'saldoEstoque' vindo do EstoqueService
    public static CafeDTOResponse valueOf(Cafe cafe, Integer saldoEstoque) {
        return new CafeDTOResponse(
            cafe.getId(),
            cafe.getNome(),
            cafe.getDescricao(),
            MarcaDTOResponse.valueOf(cafe.getMarca()),
            CategoriaDoCafeDTOResponse.valueOf(cafe.getCategoriaDoCafe()),
            // TODO: regiaoDeOrigem
            cafe.getNivelDeTorra(),
            cafe.getTratamento(),
            cafe.getNotasSensoriais(),
            cafe.getPontuacaoSCA(),
            cafe.getPreco(),
            cafe.getPeso(),
            saldoEstoque // Usa o valor passado externamente
        );
    }

    // Método de conveniência (opcional, assume 0 se não informado)
    public static CafeDTOResponse valueOf(Cafe cafe) {
        return valueOf(cafe, 0); 
    }
}