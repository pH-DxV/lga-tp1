package br.unitins.topicos1.lgc.Telefone.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.Telefone.service.TelefoneService;
import jakarta.annotation.security.RolesAllowed;
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

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @POST
    @Transactional
    @RolesAllowed({"Administrador", "Usuario"})
    public Response create(@Valid TelefoneDTO dto) {
        LOG.info("INICIANDO METODO create");
        TelefoneDTOResponse response = service.create(dto);
        LOG.info("TELEFONE CRIADO COM SUCESSO. ID= "+ response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Administrador", "Usuario"})
    public Response update(@PathParam("id") Long id, @Valid TelefoneDTO dto) {
        LOG.info("INICIANDO METODO update PARA TELEFONE: " + id);
        TelefoneDTOResponse response = service.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Administrador", "Usuario"})
    public Response delete(@PathParam("id") Long id) {
        LOG.warn("INCIANDO METODO delete PARA TELEFONE: "+ id);
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO OS DETALHES DO TELEFONE: " + id + " POR ID");
        return Response.ok(service.findById(id)).build();
    }
    
    @GET
    @RolesAllowed({"Administrador"})
    public Response buscarTodos() {
        LOG.info("LISTANDO TODOS OS TELEFONES [ ADM ACESS ]");
        List<TelefoneDTOResponse> lista = service.findAll(); 
        LOG.info("TOTAL DE TELEFONES RETORNADOS: "+ lista.size());
        return Response.ok(lista).build();
    }
    
    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findByUsuario(@PathParam("idUsuario") Long idUsuario) {
        LOG.info("BUSCANDO TELEFONE POR USUARIO: '" + idUsuario + "'");
        List<TelefoneDTOResponse> lista = service.findByUsuario(idUsuario); 
        return Response.ok(lista).build();
    }
}