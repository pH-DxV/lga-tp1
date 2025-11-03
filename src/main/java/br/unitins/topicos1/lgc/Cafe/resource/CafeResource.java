package br.unitins.topicos1.lgc.Cafe.resource;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.service.CafeService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/cafes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CafeResource {

    @Inject
    CafeService cafeService;

    @POST
    public Response create(@Valid CafeDTO dto) {
        return Response.status(Status.CREATED).entity(cafeService.create(dto)).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CafeDTO dto) {
        return Response.ok(cafeService.update(id, dto)).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        cafeService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response findAll() {
        return Response.ok(cafeService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(cafeService.findById(id)).build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(cafeService.findByNome(nome)).build();
    }
    
    @GET
    @Path("/search/pontuacao")
    public Response findByPontuacao(
            @QueryParam("min") Integer minSCA,
            @QueryParam("max") Integer maxSCA) {
        
        if (minSCA == null && maxSCA == null) {
            return Response.status(Status.BAD_REQUEST)
                           .entity("Informe ao menos um valor (min ou max) para a pontuação.")
                           .build();
        }
        return Response.ok(cafeService.findByPontuacao(minSCA, maxSCA)).build();
    }
}
