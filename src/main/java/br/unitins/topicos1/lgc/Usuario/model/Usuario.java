package br.unitins.topicos1.lgc.Usuario.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Set; // Importante para os perfis

import br.unitins.topicos1.lgc.DefaultEntity.model.DefaultEntity;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import br.unitins.topicos1.lgc.Telefone.model.Telefone;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable; // Para a tabela de perfis
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario extends DefaultEntity {

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(length = 11, nullable = false, unique = true)
    private String cpf;

    // --- NOVOS CAMPOS DE AUTENTICAÇÃO ---
    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;
    // ------------------------------------

    private LocalDate dataNascimento;
    
    private Double peso;

    // --- RELACIONAMENTOS ---

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos;

    // Lista de Perfis (Permissões)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_perfil", joinColumns = @JoinColumn(name = "id_usuario"))
    @Column(name = "id_perfil") 
    private Set<Perfil> perfis;
    
    // --- GETTERS E SETTERS ---

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public List<Telefone> getTelefones() { return telefones; }
    public void setTelefones(List<Telefone> telefones) { this.telefones = telefones; }

    public List<Endereco> getEnderecos() { return enderecos; }
    public void setEnderecos(List<Endereco> enderecos) { this.enderecos = enderecos; }

    public Set<Perfil> getPerfis() { return perfis; }
    public void setPerfis(Set<Perfil> perfis) { this.perfis = perfis; }
}