package br.unitins.topicos1.lgc.Marca.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.service.MarcaService;
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

@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {

    @Inject
    MarcaService marcaService;

    private static final Logger LOG = Logger.getLogger(MarcaResource.class);

    @POST
    @RolesAllowed({"Administrador"})
    public Response create(@Valid MarcaDTO dto) {
        LOG.info("INICIANDO METODO create [ ADM ACESS ]");
        return Response.status(Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Administrador"})
    public Response update(@PathParam("id") Long id, @Valid MarcaDTO dto) {
        LOG.info("INICIANDO METODO update [ ADM ACESS ]");
        return Response.ok(marcaService.update(id, dto)).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({"Administrador"})
    public Response delete(@PathParam("id") Long id) {
        LOG.warn("INICIANDO METODO delete PARA MARCA: "+ id + "[ ADM ACESS ]");
        marcaService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @PermitAll
    public Response findAll() {
        LOG.info("LISTANDO TODAS AS MARCAS");
        return Response.ok(marcaService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO DETALHES DA MARCA: " + id + " POR ID");
        return Response.ok(marcaService.findById(id)).build();
    }

    @GET
    @Path("/search/{nome}")
    @PermitAll
    public Response findByNome(@PathParam("nome") String nome) {
        LOG.info("BUSCANDO POR NOME: '" + nome + "'");
        return Response.ok(marcaService.findByNome(nome)).build();
    }
}
