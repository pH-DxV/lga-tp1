package br.unitins.topicos1.lgc.Auth.resource;

import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
import br.unitins.topicos1.lgc.Hash.service.HashService;
import br.unitins.topicos1.lgc.JwT.service.JwtService;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.service.UsuarioService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/login")
    @PermitAll
    public Response login(@Valid AuthDTO dto) {
        
        // 1. Criptografa a senha que chegou para comparar com o banco
        String hashDaSenha = hashService.getHashSenha(dto.senha());

        // 2. Busca o usuário usando o login e o hash
        Usuario usuario = usuarioService.findByLoginAndSenha(dto.login(), hashDaSenha);

        // 3. Se retornou nulo, o login falhou
        if (usuario == null) {
            return Response.status(Status.NO_CONTENT).build(); 
            // Ou Status.UNAUTHORIZED (401) se preferir ser mais explícito
        }
        
        // 4. Gera o Token (Nosso serviço já lida com a lista de perfis internamente)
        String token = jwtService.generateJwt(usuario);

        // 5. Retorna o token no cabeçalho da resposta
        return Response.ok()
                .header("Authorization", token)
                .build();
    }
}