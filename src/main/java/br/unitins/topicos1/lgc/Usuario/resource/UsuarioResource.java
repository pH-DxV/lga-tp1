package br.unitins.topicos1.lgc.Usuario.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import br.unitins.topicos1.lgc.Usuario.service.UsuarioService;
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

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed("Administrador")
    public Response buscarTodos() { // Corrigido
        LOG.info("LISTANDO TODOS OS USUARIOS [ ADM ACESS ]");
        List<UsuarioDTOResponse> lista = service.findAll();
        LOG.info("TOTAL DE USUARIOS RETORNADOS: "+lista.size());
        return Response.ok(lista).build();
    }

    // Adicionado o findById (essencial)
    @GET
    @Path("/{id}")
    @RolesAllowed("Administrador")
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO OS DETALHES DO USUARIO: " + id + " POR ID [ ADM ACESS ]");
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/find/{nome}")
    @RolesAllowed("Administrador")
    public Response buscarPorNome(@PathParam("nome") String nome) { // Corrigido
        LOG.info("BUSCANDO POR NOME: '"+ nome + "' [ ADM ACESS ]");
        List<UsuarioDTOResponse> lista = service.findByNome(nome);
        return Response.ok(lista).build();
    }

    @POST
    @Transactional // Adicionado
    @RolesAllowed("Administrador")
    public Response incluir(UsuarioDTO dto) { // Corrigido
        LOG.info("INICIANDO METODO create [ ADM ACESS ]");
        UsuarioDTOResponse retorno = service.create(dto);
        return Response.status(Status.CREATED).entity(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed("Administrador")
    public Response alterar(@PathParam("id") Long id, UsuarioDTO dto) { // Corrigido
        LOG.info("INICIANDO METODO update PARA USUARIO: " + id + "[ ADM ACESS ]");
        UsuarioDTOResponse retorno = service.update(id, dto);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed("Administrador")
    public Response apagar(@PathParam("id") Long id) { // Corrigido
        LOG.warn("INICIANDO METODO delete PARA USUARIO: "+ id);
        service.delete(id);
        return Response.noContent().build();
    }
}