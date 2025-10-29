package br.unitins.topicos1.lgc.jpa.model;

import br.unitins.topicos1.lgc.NivelDeTorra.model.NivelDeTorra;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class NivelDeTorraConverter implements AttributeConverter<NivelDeTorra, Long> {

    @Override
    public Long convertToDatabaseColumn(NivelDeTorra nivelDeTorra) {
        // Converte o Enum para seu ID (Long) para salvar no banco
        return (nivelDeTorra == null) ? null : nivelDeTorra.ID;
    }

    @Override
    public NivelDeTorra convertToEntityAttribute(Long id) {
        // Converte o ID (Long) do banco de volta para o Enum
        return NivelDeTorra.valueOf(id);
    }
}