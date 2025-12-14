package br.unitins.topicos1.lgc.Estado.resource;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
import br.unitins.topicos1.lgc.Estado.service.EstadoService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {
    
    @Inject
    EstadoService service;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    @PermitAll
    public Response buscarTodos() {
       LOG.info("LISTATNDO TODOS OS ESTADOS");
        return Response.ok(service.findAll()).build();
    }

    @GET
    @Path("/find/{nome}")
    @PermitAll
    public Response buscarPorNome(@PathParam("nome") String nome) { // Corrigido
        LOG.info("BUSCANDO ESTADO POR NOME: "+nome);
        // Agora o service.findByNome() retorna List<EstadoDTOResponse>
        return Response.ok(service.findByNome(nome)).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO ESTADO POR ID: " + id);
        return Response.ok(service.findById(id)).build();
    }

    @POST
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response incluir(EstadoDTO dto) {
        LOG.info("INICIANDO METODO create [ ADM ACESS ]");
        EstadoDTOResponse response = service.create(dto);
        LOG.info("ESTADO CRIADO COM SUCESSO. ID= "+response.id());
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response alterar(@PathParam("id") Long id, EstadoDTO dto) { // Corrigido
        LOG.info("INICIANDO METODO update PARA O ESTADO: " + id + " [ ADM ACESS ]");
        // Corrigido para retornar o objeto atualizado (200 OK)
        EstadoDTOResponse response = service.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response apagar(@PathParam("id") Long id) { // Corrigido
        LOG.warn("INICIANDO METODO delete PARA ESTADO: " + id + " [ ADM ACESS ]");
        service.delete(id);
        return Response.noContent().build();
    }
}