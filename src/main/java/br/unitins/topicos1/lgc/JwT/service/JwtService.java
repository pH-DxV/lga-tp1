package br.unitins.topicos1.lgc.JwT.service;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;

public interface JwtService {

    String generateJwt(Usuario usuario);
    
}