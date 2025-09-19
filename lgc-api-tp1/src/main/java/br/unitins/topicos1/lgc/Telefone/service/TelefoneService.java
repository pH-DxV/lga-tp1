package br.unitins.topicos1.lgc.Telefone.service;

import java.util.List;

import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;

public interface TelefoneService {

    List<Telefone> findAll();
    Telefone findById(Long id);
    Telefone findByDdd(String ddd);
    Telefone findByNumero(String numero);

    Telefone create(TelefoneDTO dto);
    void update(Long id, TelefoneDTO dto);
    void delete(Long id);
}
