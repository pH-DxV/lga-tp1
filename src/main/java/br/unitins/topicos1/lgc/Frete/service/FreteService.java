package br.unitins.topicos1.lgc.Frete.service;

import java.math.BigDecimal;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;

public interface FreteService {
    
    // Calcula o frete baseando-se no CEP (Estado)
    BigDecimal calcularFrete(Endereco enderecoEntrega);
    
}