package br.unitins.topicos1.lgc.Telefone.resource;

import java.util.List;

import br.unitins.topicos1.lgc.TelefoneResourceTest.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.TelefoneResourceTest.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.TelefoneResourceTest.service.TelefoneService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
// import jakarta.inject.Inject;
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

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    
    @Inject
    TelefoneService service;

    @POST
    @Transactional
    public Response create(@Valid TelefoneDTO dto) {
        TelefoneDTOResponse response = service.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid TelefoneDTO dto) {
        TelefoneDTOResponse response = service.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }
    
    @GET
    public Response buscarTodos() {
        List<TelefoneDTOResponse> lista = service.findAll(); 
        return Response.ok(lista).build();
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    public Response findByUsuario(@PathParam("idUsuario") Long idUsuario) {
        List<TelefoneDTOResponse> lista = service.findByUsuario(idUsuario); 
        return Response.ok(lista).build();
    }
}