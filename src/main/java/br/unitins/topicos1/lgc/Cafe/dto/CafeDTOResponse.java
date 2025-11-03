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
    // RegiaoDeOrigemDTOResponse regiaoDeOrigem, // TODO
    NivelDeTorra nivelDeTorra,
    Tratamento tratamento,
    Set<NotaSensorial> notasSensoriais,
    Integer pontuacaoSCA,
    Double preco,
    Double peso,
    Integer estoque
) {
    public static CafeDTOResponse valueOf(Cafe cafe) {
        // TODO: Adicionar regiaoDeOrigem quando estiver pronto
        return new CafeDTOResponse(
            cafe.getId(),
            cafe.getNome(),
            cafe.getDescricao(),
            MarcaDTOResponse.valueOf(cafe.getMarca()),
            CategoriaDoCafeDTOResponse.valueOf(cafe.getCategoriaDoCafe()),
            cafe.getNivelDeTorra(),
            cafe.getTratamento(),
            cafe.getNotasSensoriais(),
            cafe.getPontuacaoSCA(),
            cafe.getPreco(),
            cafe.getPeso(),
            cafe.getEstoque()
        );
    }
}