package br.unitins.topicos1.lgc.jpa.model;

import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PerfilConverter implements AttributeConverter<Perfil, Long> {

    @Override
    public Long convertToDatabaseColumn(Perfil perfil) {
        return (perfil == null) ? null : perfil.ID;
    }

    @Override
    public Perfil convertToEntityAttribute(Long id) {
        return Perfil.valueOf(id);
    }
}