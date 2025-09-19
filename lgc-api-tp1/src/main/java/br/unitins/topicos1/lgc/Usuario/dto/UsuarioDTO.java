package br.unitins.topicos1.lgc.Usuario.dto;

import java.time.LocalDate;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonFormat;

public record UsuarioDTO(

    String cpf,
    String nome,
    Long idMunicipioNaturalidade,
    @Schema(type = SchemaType.STRING, example = "01/01/1990")
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate dataNascimento,
    Double peso

){

}
