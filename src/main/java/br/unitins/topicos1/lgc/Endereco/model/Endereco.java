package br.unitins.topicos1.lgc.Endereco.model;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Endereco extends DefaultEntity {

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(length = 100)
    private String rua;

    @Column(length = 100)
    private String complemento;

    // --- 4. ADICIONAR ESTE BLOCO (A CORREÇÃO) ---
    // Muitos endereços pertencem a Um Usuário
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    // --- FIM DA CORREÇÃO ---


    // --- GETTERS E SETTERS ---
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    // --- GETTER E SETTER PARA O NOVO CAMPO ---
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
