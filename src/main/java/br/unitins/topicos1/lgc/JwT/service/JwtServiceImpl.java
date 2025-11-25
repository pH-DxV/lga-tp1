package br.unitins.topicos1.lgc.JwT.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.unitins.topicos1.lgc.Perfil.model.Perfil;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(24);

    @Override
    public String generateJwt(Usuario usuario) {
        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);

        // --- ADAPTAÇÃO PARA LISTA DE PERFIS ---
        Set<String> roles = new HashSet<String>();
        
        // Percorre todos os perfis do usuário e adiciona na lista de "roles" (papéis)
        for (Perfil perfil : usuario.getPerfis()) {
            roles.add(perfil.LABEL); // Ou perfil.name() se preferir "ADM"/"USER"
        }

        return Jwt.issuer("unitins-jwt")
                .subject(usuario.getCpf()) // Pode usar o Login ou CPF como identificador principal
                .upn(usuario.getNome()) // User Principal Name (Nome legível)
                .groups(roles) // Define as permissões
                .claim("id", usuario.getId()) // Guarda o ID no token (útil para o front)
                .expiresAt(expiryDate)
                .sign();
    }
}