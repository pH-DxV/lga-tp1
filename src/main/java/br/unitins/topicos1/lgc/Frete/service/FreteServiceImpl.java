package br.unitins.topicos1.lgc.Frete.service;

import java.math.BigDecimal;

import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Estado.model.Estado;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FreteServiceImpl implements FreteService {

    @Override
    public BigDecimal calcularFrete(Endereco endereco) {
        // Lógica de simulação baseada na localização
        if (endereco == null || endereco.getMunicipio() == null || endereco.getMunicipio().getEstado() == null) {
            return 0.0;
        }

        Estado estado = endereco.getMunicipio().getEstado();

        // Regra de Negócio Simulada:
        // 1. Frete para Tocantins (TO) = R$ 10,00
        if ("TO".equalsIgnoreCase(estado.getSigla())) {
            return 10.00;
        }
        
        // 2. Frete para Região Norte (exceto TO) e Nordeste = R$ 25,00
        // (Assumindo que seu Enum Regiao tem IDs, ex: 2=Nordeste, 3=Norte)
        Long idRegiao = estado.getRegiao().ID;
        if (idRegiao == 2L || idRegiao == 3L) {
            return 25.00;
        }

        // 3. Frete para o resto do Brasil = R$ 40,00
        return 40.00;
    }
}