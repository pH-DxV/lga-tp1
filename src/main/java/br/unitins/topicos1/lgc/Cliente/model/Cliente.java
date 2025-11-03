package br.unitins.topicos1.lgc.Cliente.model;

import java.time.LocalDate;
import java.util.List;

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cliente extends DefaultEntity {


    @Column(name = "cpf", unique = true, nullable = false)
    private String cpf;

    @Column(name = "nome")
    private String nome;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_telephone", nullable =  false)
    private Telefone telefone;

    @Column(name = "date+_de_nascimento")
    private LocalDate dataDeNascimento;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_cliente")
    private List<Endereco> listaDeEndereco;

    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true, nullable = false)
    private Usuario usuario;



    // GET E SET
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Telefone getTelefone() {
        return telefone;
    }
    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }
    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }
    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }
    public List<Endereco> getListaDeEndereco() {
        return listaDeEndereco;
    }
    public void setListaDeEndereco(List<Endereco> listaDeEndereco) {
        this.listaDeEndereco = listaDeEndereco;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    

    
}
