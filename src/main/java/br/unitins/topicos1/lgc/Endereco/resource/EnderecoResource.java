package br.unitins.topicos1.lgc.Endereco.resource;

import org.jboss.logging.Logger;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Endereco.service.EnderecoService;
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

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    // --- GESTÃO PESSOAL (Cliente gerencia seus dados) ---

    @POST
    @Transactional
    @RolesAllowed({"Usuario", "Administrador"}) // Cliente adiciona endereço
    public Response incluir(EnderecoDTO dto) {
        LOG.info("INICIANDO METODO create");
        // O Service deve validar se o ID do usuário no DTO é o mesmo do token
        EnderecoDTOResponse retorno = service.create(dto);
        LOG.info("ENDEDRECO CRIADO COM SUCESSO. ID= "+ retorno.id());
        return Response.status(Status.CREATED).entity(retorno).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Usuario", "Administrador"}) // Cliente corrige endereço
    public Response alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        LOG.info("INICIANDO METODO update PARA ENDERECO: " + id);
        // O Service deve validar se o endereço pertence ao usuário do token
        EnderecoDTOResponse retorno = service.update(id, dto);
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Usuario", "Administrador"}) // Cliente remove endereço antigo
    public Response apagar(@PathParam("id") Long id) {
        LOG.warn("INICIANDO METTODO delete PARA ENDERECO: " +id);
        // O Service deve validar se o endereço pertence ao usuário do token
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Usuario", "Administrador"}) // Cliente vê detalhes para editar
    public Response findById(@PathParam("id") Long id) {
        LOG.info("BUSCANDO OS DETALHES DO ENDERECO: " + id + " POR ID");
        return Response.ok(service.findById(id)).build();
    }

    // --- GESTÃO ADMINISTRATIVA & CONSULTAS ESPECÍFICAS ---

    @GET
    @RolesAllowed({"Administrador"}) // Lista completa é restrita
    public Response buscarTodos() {
        LOG.info("LISTANDO TODOS OS ENDERECOS [ ADM ACESS ]");
        List<EnderecoDTOResponse> lista = service.findAll();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/cep/{cep}")
    @RolesAllowed({"Administrador"}) // Busca técnica, geralmente uso interno
    public Response buscarPorCep(@PathParam("cep") String cep) {
        LOG.info("BUSCANDO ENDERECO POR CEP: " +cep);
        List<EnderecoDTOResponse> lista = service.findByCep(cep);
        LOG.info("TOTAL DE ENDERECOS RETORNADOS: "+lista.size());
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/rua/{rua}")
    @RolesAllowed({"Administrador"}) // Busca técnica
    public Response buscarPorRua(@PathParam("rua") String rua) {
        LOG.info("BUSCANDO ENDERECO POR RUA: " + rua);
        List<EnderecoDTOResponse> lista = service.findByRua(rua);
        LOG.info("TOTAL DE ENDERECOS RETORNADOS: "+lista.size());
        return Response.ok(lista).build();
    }
    
    // SUGESTÃO: Endpoint essencial para o front-end "Meus Endereços"
    /*
    @GET
    @Path("/usuario/{idUsuario}")
    @RolesAllowed({"Usuario", "Administrador"})
    public Response buscarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        // Validar token vs idUsuario
        return Response.ok(service.findByUsuario(idUsuario)).build();
    }
    */
}