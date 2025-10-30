package br.unitins.topicos1.lgc.jpa.model;

import br.unitins.topicos1.lgc.Tratamento.model.Tratamento;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TratamentoConverter implements AttributeConverter<Tratamento, Long> {

    @Override
    public Long convertToDatabaseColumn(Tratamento tratamento) {
        // Converte o Enum para seu ID (Long) para salvar no banco
        return (tratamento == null) ? null : tratamento.ID;
    }

    @Override
    public Tratamento convertToEntityAttribute(Long id) {
        // Converte o ID (Long) do banco de volta para o Enum
        return Tratamento.valueOf(id);
    }
}
