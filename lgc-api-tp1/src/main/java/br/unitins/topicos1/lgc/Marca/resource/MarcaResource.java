package br.unitins.topicos1.lgc.Marca.resource;

import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    @POST
    public Response create(@Valid MarcaDTO dto) {
        return Response.status(Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid MarcaDTO dto) {
        return Response.ok(marcaService.update(id, dto)).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        marcaService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response findAll() {
        return Response.ok(marcaService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(marcaService.findById(id)).build();
    }

    @GET
    @Path("/search/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(marcaService.findByNome(nome)).build();
    }
}
