package br.unitins.topicos1.lgc.Usuario.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import br.unitins.topicos1.lgc.Usuario.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @GET
    public Response buscarTodos() { // Corrigido
        List<UsuarioDTOResponse> lista = service.findAll();
        return Response.ok(lista).build();
    }

    // Adicionado o findById (essencial)
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/find/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome) { // Corrigido
        List<UsuarioDTOResponse> lista = service.findByNome(nome);
        return Response.ok(lista).build();
    }

    @POST
    @Transactional // Adicionado
    public Response incluir(UsuarioDTO dto) { // Corrigido
        UsuarioDTOResponse retorno = service.create(dto);
        return Response.status(Status.CREATED).entity(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional // Adicionado
    public Response alterar(@PathParam("id") Long id, UsuarioDTO dto) { // Corrigido
        UsuarioDTOResponse retorno = service.update(id, dto);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Adicionado
    public Response apagar(@PathParam("id") Long id) { // Corrigido
        service.delete(id);
        return Response.noContent().build();
    }
}