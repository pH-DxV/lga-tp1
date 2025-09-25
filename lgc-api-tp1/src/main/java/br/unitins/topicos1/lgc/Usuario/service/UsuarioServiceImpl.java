package br.unitins.topicos1.lgc.Usuario.service;

import java.util.List;

import br.unitins.topicos1.lgc.Municipio.model.Municipio;
import br.unitins.topicos1.lgc.Municipio.repository.MunicipioRepository;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;
    @Inject
    MunicipioRepository repositoryMunicipio;



    @Override
    public List<Usuario> findAll() {
        return repository.listAll();
    }
    @Override
    public List<Usuario> findByNome(String nome) {
        return repository.findByNome(nome);
    }
    @Override
    public Usuario findById(Long id) {
        return repository.findById(id);
    }



    @Override
    @Transactional
    public Usuario create(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setPeso(dto.peso());

        Municipio naturalidade = repositoryMunicipio.findById(dto.idMunicipioNaturalidade());
        usuario.setNaturalidade(naturalidade);

        repository.persist(usuario);
        return usuario;
    }
    @Override
    @Transactional
    public void update(Long id, UsuarioDTO dto) {
        Usuario usuario = repository.findById(id);
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setPeso(dto.peso());

        Municipio naturalidade = repositoryMunicipio.findById(dto.idMunicipioNaturalidade());
        usuario.setNaturalidade(naturalidade);
    }
    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }


    
}
