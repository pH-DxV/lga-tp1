package br.unitins.topicos1.lgc.Telefone.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Telefone.model.Telefone;
import br.unitins.topicos1.lgc.Telefone.service.TelefoneService;
import jakarta.inject.Inject;
// import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    
    @Inject
    TelefoneService service;

    @GET
    public List<Telefone> buscarTodos() {
    return service.findAll();
    }
}
