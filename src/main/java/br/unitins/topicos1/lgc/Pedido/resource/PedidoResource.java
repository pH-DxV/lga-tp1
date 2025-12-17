package br.unitins.topicos1.lgc.Pedido.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.service.PedidoService;
import jakarta.annotation.security.PermitAll;
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

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @POST
    @Transactional
    // @RolesAllowed({"Administrador","Usuario"})
    @PermitAll
    public Response create(@Valid PedidoDTO dto) {
        LOG.info("INICIANDO METODO create");

            // --- DEBUG TEMPORÁRIO ---
    // Injetar JsonWebToken jwt; na classe antes de usar
    if (jwt != null) {
        LOG.info("Usuário logado: " + jwt.getName()); // Deve ser o login
        LOG.info("Roles (groups): " + jwt.getGroups()); // Deve conter "Usuario" ou "Administrador"
        LOG.info("Claim 'id': " + jwt.getClaim("id")); // Deve ser o ID numérico
    } else {
        LOG.error("JWT é nulo!");
    }
    // ------------------------
        PedidoDTOResponse response = service.create(dto);
        LOG.info("PEDIDO CRIADO COM SUCESSO. ID= " + response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @GET
    @RolesAllowed({"Administrador"})
    public Response findAll() {
        LOG.info("LISTANDO TODOS OS PEDIDOS [ ADM ACESS ]");
        List<PedidoDTOResponse> lista = service.findAll();
        LOG.info("TOTAL DE PEDIDOS RETORNADOS: "+lista.size());
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador","Usuario"})
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO OS DETALHES DO PEDIDO: "+ id + " POR ID");
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findByUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.info("BUSCANDO PEDIDOS POR USUARIO: '" + idUsuario + "'" );
        List<PedidoDTOResponse> lista = service.findByUsuario(idUsuario);
        LOG.info("TOTAL DE PEDIDOS RETORNADOS: "+lista.size());
        return Response.ok(lista).build();
    }
}