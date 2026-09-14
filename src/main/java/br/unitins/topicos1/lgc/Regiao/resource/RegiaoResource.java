package br.unitins.topicos1.lgc.Regiao.resource;

import br.unitins.topicos1.lgc.Regiao.model.Regiao;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/regioes")
@Produces(MediaType.APPLICATION_JSON)
public class RegiaoResource {

    @GET
    @PermitAll
    public Response buscarTodos() {
        return Response.ok(Regiao.values()).build();
    }
}