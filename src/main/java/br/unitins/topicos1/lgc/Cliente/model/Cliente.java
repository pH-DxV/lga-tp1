package br.unitins.topicos1.lgc.Cliente.model;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

// NOTA: Esta entidade consolida Usuario e Cliente, 
// removendo a necessidade de herança entre elas, se for o novo caminho.

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {

}