package br.unitins.topicos1.lgc.Cliente.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
import br.unitins.topicos1.lgc.Cliente.service.ClienteService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService service;

    /**
     * Endpoint público para o cadastro de novos clientes.
     * O perfil é forçado como USER no ClienteServiceImpl.
     */
    @POST
    @Transactional
    @PermitAll
    public Response create(@Valid ClienteDTO dto) {
        ClienteDTOResponse response = service.create(dto);
        // Retorna 201 Created com o novo cliente
        return Response.status(Status.CREATED).entity(response).build();
    }

    /**
     * Endpoint para atualização de dados de um cliente existente.
     * Geralmente acessado por admins ou pelo próprio usuário logado (depende da segurança).
     */
    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Administrador", "Usuario"})
    public Response update(@PathParam("id") Long id, @Valid ClienteDTO dto) {
        ClienteDTOResponse response = service.update(id, dto);
        // Retorna 200 OK com o objeto atualizado
        return Response.ok(response).build();
    }

    /**
     * Endpoint para exclusão de um cliente.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Administrador", "Usuario"})
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        // Retorna 204 No Content
        return Response.noContent().build();
    }

    /**
     * Lista todos os clientes. (Acesso geralmente restrito a ADM).
     */
    @GET
    @RolesAllowed({"Administrador"})
    public Response findAll() {
        List<ClienteDTOResponse> lista = service.findAll();
        return Response.ok(lista).build();
    }

    /**
     * Busca um cliente pelo ID.
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    /**
     * Busca clientes por parte do nome.
     */
    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Administrador"})
    public Response findByNome(@PathParam("nome") String nome) {
        List<ClienteDTOResponse> lista = service.findByNome(nome);
        return Response.ok(lista).build();
    }
}