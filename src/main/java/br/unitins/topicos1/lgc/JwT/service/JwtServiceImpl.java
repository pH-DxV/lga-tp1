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

        Set<String> roles = new HashSet<String>();
        for (Perfil perfil : usuario.getPerfis()) {
            roles.add(perfil.LABEL); 
        }

        return Jwt.issuer("unitins-jwt")
                // --- CORREÇÃO IMPORTANTE ---
                // Usamos o LOGIN como subject, pois é isso que o SecurityService verifica.
                .subject(usuario.getLogin()) 
                // ---------------------------
                .upn(usuario.getNome()) 
                .groups(roles) 
                .claim("id", usuario.getId()) // Garante que o ID do usuário vá no token
                .expiresAt(expiryDate)
                .sign();
    }
}