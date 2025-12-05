package br.unitins.topicos1.lgc.Frete.service;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;

public interface FreteService {
    
    // Calcula o frete baseando-se no CEP (Estado)
    Double calcularFrete(Endereco enderecoEntrega);
    
}