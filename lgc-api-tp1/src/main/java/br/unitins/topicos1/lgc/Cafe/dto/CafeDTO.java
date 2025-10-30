package br.unitins.topicos1.lgc.Cafe.dto;

import java.util.Set;

import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CafeDTO(
    
    @NotBlank(message = "O nome do café não pode ser nulo.")
    @Size(max = 100, message = "O nome do café excede 100 caracteres.")
    String nome,
    
    @Size(max = 500, message = "A descrição excede 500 caracteres.")
    String descricao,
    
    @NotNull(message = "O ID da Marca não pode ser nulo.")
    Long idMarca,

    @NotNull(message = "O ID da Categoria do Café não pode ser nulo.")
    Long idCategoriaDoCafe,
    
    // Long idRegiaoDeOrigem, // TODO

    @NotNull(message = "O ID do Nível de Torra não pode ser nulo.")
    Long idNivelDeTorra,
    
    @NotNull(message = "O ID do Tratamento não pode ser nulo.")
    Long idTratamento,
    
    Set<NotaSensorial> notasSensoriais,
    
    @PositiveOrZero(message = "A pontuação SCA deve ser um valor positivo.")
    Integer pontuacaoSCA,
    
    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    Double preco,
    
    @NotNull(message = "O peso não pode ser nulo.")
    @Positive(message = "O peso deve ser um valor positivo.")
    Double peso,
    
    @NotNull(message = "O estoque não pode ser nulo.")
    @PositiveOrZero(message = "O estoque deve ser zero ou maior.")
    Integer estoque
) {}