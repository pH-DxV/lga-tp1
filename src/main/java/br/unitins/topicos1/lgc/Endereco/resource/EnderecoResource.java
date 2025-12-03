package br.unitins.topicos1.lgc.Endereco.resource;

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

    @GET
    @Path("/find/cep/{cep}")
    @RolesAllowed({"Administrador"})
    public Response buscarPorCep(@PathParam("cep") String cep) {
        List<EnderecoDTOResponse> lista = service.findByCep(cep);
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/rua/{rua}")
    @RolesAllowed({"Administrador"})
    public Response buscarPorRua(@PathParam("rua") String rua) {
        List<EnderecoDTOResponse> lista = service.findByRua(rua);
        return Response.ok(lista).build();
    }

    @GET
    @RolesAllowed({"Administrador"})
    public Response buscarTodos() {
        List<EnderecoDTOResponse> lista = service.findAll();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional // Boa prática
    @RolesAllowed({"Administrador", "Usuario"})
    public Response incluir(EnderecoDTO dto) {
        EnderecoDTOResponse retorno = service.create(dto);
        // Retorna 201 Created com o objeto criado
        return Response.status(Status.CREATED).entity(retorno).build(); 
    }

    @PUT
    @Path("/{id}")
    @Transactional // Obrigatório
    @RolesAllowed({"Administrador", "Usuario"})
    public Response alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        EnderecoDTOResponse retorno = service.update(id, dto);
        // Retorna 200 OK com o objeto atualizado
        return Response.ok(retorno).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional // Obrigatório
    @RolesAllowed({"Administrador", "Usuario"})
    public Response apagar(@PathParam("id") Long id) {
        service.delete(id);
        // Retorna 204 No Content (sucesso sem corpo)
        return Response.noContent().build();
    }
}
