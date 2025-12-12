package br.unitins.topicos1.lgc.Security.service;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;

@ApplicationScoped
public class SecurityServiceImpl implements SecurityService {

    @Inject
    JsonWebToken jwt;

    @Override
    public void validarPermissao(Usuario donoDoRecurso) {
        String loginLogado = jwt.getName();
        
        // Se não tiver login no token (ex: token inválido ou sem claims), bloqueia por segurança
        if (loginLogado == null) {
             throw new ForbiddenException("Usuário não identificado no token.");
        }

        // Verifica se é Admin
        boolean isAdmin = jwt.getGroups() != null && jwt.getGroups().contains("Administrador");
        if (isAdmin) {
            return; // Admin pode tudo
        }

        // Verifica se é o dono (pelo login)
        if (!donoDoRecurso.getLogin().equals(loginLogado)) {
             throw new ForbiddenException("Você não tem permissão para gerenciar dados de outro usuário.");
        }
    }
    
    @Override
    public void validarPermissao(Long idUsuarioAlvo) {
         boolean isAdmin = jwt.getGroups() != null && jwt.getGroups().contains("Administrador");
         if (isAdmin) return;

         // Tenta pegar o ID do token (assumindo que adicionamos o claim "id" no JwtService)
         // Nota: getClaim retorna Object, então .toString() é seguro se não for nulo
         Object claimId = jwt.getClaim("id");
         
         if (claimId == null || !claimId.toString().equals(idUsuarioAlvo.toString())) {
             throw new ForbiddenException("Você não tem permissão para gerenciar dados de outro usuário.");
         }
    }
}