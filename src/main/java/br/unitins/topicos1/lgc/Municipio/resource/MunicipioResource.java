package br.unitins.topicos1.lgc.Municipio.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;
import br.unitins.topicos1.lgc.Municipio.service.MunicipioService;
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

@Path("/municipios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioService service;

    private static final Logger LOG = Logger.getLogger(MunicipioResource.class);

    @GET
    @RolesAllowed({"Administrador", "Usuario"})
    public Response buscarTodos() {
        LOG.info("LISTANDO TODOS OS MUNICIPIOS");
        List<MunicipioDTOResponse> lista = service.findAll();
        LOG.info("TOTAL DE MUNICIPIOS RETORNADOS: "+ lista.size());
        return Response.ok(lista).build();
    }

    // Adicionado o findById (essencial)
    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO OS DETALHES DO MUNICIPIO: " + id + " POR ID");
        return Response.ok(service.findById(id)).build();
    }

    @GET
    @Path("/find/{nome}")
    @RolesAllowed({"Administrador", "Usuario"})
    public Response buscarPorNome(@PathParam("nome") String nome) { // Corrigido
        LOG.info("BUSCANDO POR NOME: '"+ nome + "'");
        List<MunicipioDTOResponse> lista = service.findByNome(nome);
        return Response.ok(lista).build();
    }

    @POST
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response incluir(MunicipioDTO dto) {
        LOG.info("INICIANDO METODO create");
        MunicipioDTOResponse retorno = service.create(dto);
        return Response.status(Status.CREATED).entity(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response alterar(@PathParam("id") Long id, MunicipioDTO dto) { // Corrigido
        LOG.info("INICIANDO METODO update PARA MUNICIPIO: "+ id);
        MunicipioDTOResponse retorno = service.update(id, dto);
        return Response.ok(retorno).build(); // Retorna o objeto atualizado
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Adicionado
    @RolesAllowed({"Administrador"})
    public Response apagar(@PathParam("id") Long id) { // Corrigido
        LOG.warn("INICIANDO METODO delete PARA MUNICIPIO: " + id);
        service.delete(id);
        return Response.noContent().build();
    }
}