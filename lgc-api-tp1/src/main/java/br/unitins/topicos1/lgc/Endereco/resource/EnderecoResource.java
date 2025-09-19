package br.unitins.topicos1.lgc.Endereco.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.service.EnderecoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    

    @GET
    @Path("/find/cep/{cep}")
    public List<Endereco> buscarPorCep(@PathParam("cep") String cep) {
        return service.findByCep(cep);
    }
    @GET
    @Path("/find/rua/{rua}")
    public List<Endereco> buscarPorRua(@PathParam("rua") String rua) {
        return service.findByRua(rua);
    }
    @GET
    public List<Endereco> buscarTodos() {
        return service.findAll();
    }



    @POST
    public Endereco incluir(EnderecoDTO dto) {
        return service.create(dto);
    }
    @PUT
    @Path("/{id}")
    public void alterar(@PathParam("id") Long id, EnderecoDTO dto) {
        service.update(id, dto);
    }
    @DELETE
    @Path("/{id}")
    public void apagar(@PathParam("id") Long id) {
        service.delete(id);
    }
    
}
