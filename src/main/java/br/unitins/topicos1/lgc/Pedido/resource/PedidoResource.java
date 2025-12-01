package br.unitins.topicos1.lgc.Pedido.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @POST
    @Transactional
    @RolesAllowed({"Administrador","Usuario"})
    public Response create(@Valid PedidoDTO dto) {
        PedidoDTOResponse response = service.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @GET
    @RolesAllowed({"Administrador"})
    public Response findAll() {
        List<PedidoDTOResponse> lista = service.findAll();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador","Usuario"})
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findByUsuario(@PathParam("idUsuario") Long idUsuario) {
        List<PedidoDTOResponse> lista = service.findByUsuario(idUsuario);
        return Response.ok(lista).build();
    }
}