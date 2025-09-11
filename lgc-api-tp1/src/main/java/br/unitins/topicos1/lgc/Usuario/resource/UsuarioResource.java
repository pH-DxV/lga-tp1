package br.unitins.topicos1.lgc.Usuario.resource;

import java.util.List;

import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioRepository repository;

    @GET
    public List<Usuario> buscarTodos() {

        return repository.listAll();
        
    }
    
    @GET
    @Path("/find/{nome}")
    public List<Usuario> buscarPorNome(String nome){

        return repository.findByNome(nome);

    }

    @POST
    @Transactional
    public Usuario incluir (Usuario usuario){

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuario.getNome());

        repository.persist(novoUsuario);

        return novoUsuario;

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void alterar (Long id, Usuario usuario){

        Usuario edicaoUsuario = repository.findById(id);
        edicaoUsuario.setNome(usuario.getNome());
        
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void apagar(Long id){

        repository.deleteById(id);
        
    }

}
