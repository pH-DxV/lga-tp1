package br.unitins.topicos1.lgc.Pagamento.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class PagamentoPix extends Pagamento {

    private String chavePixDestino;
    private LocalDateTime dataExpiracaoToken;

    // Getters e Setters
    public String getChavePixDestino() { return chavePixDestino; }
    public void setChavePixDestino(String chavePixDestino) { this.chavePixDestino = chavePixDestino; }
    public LocalDateTime getDataExpiracaoToken() { return dataExpiracaoToken; }
    public void setDataExpiracaoToken(LocalDateTime dataExpiracaoToken) { this.dataExpiracaoToken = dataExpiracaoToken; }
}
