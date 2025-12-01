package br.unitins.topicos1.lgc.Pedido.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.Cafe.service.CafeService;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.repository.EnderecoRepository;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import br.unitins.topicos1.lgc.ItemPedido.model.ItemPedido;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Pedido.repository.PedidoRepository;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    CafeRepository cafeRepository;

    @Inject
    CafeService cafeService;

    @Inject
    JsonWebToken jwt; // INJETADO: Para pegar o login do usuário logado

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        // 1. Validações Iniciais
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        // DICA DE SEGURANÇA ADICIONAL (OPCIONAL):
        // Valida se o dto.idUsuario() corresponde ao usuário do token aqui também,
        // para impedir que eu crie um pedido no nome de outra pessoa.
        String loginLogado = jwt.getSubject();
        boolean isAdmin = jwt.getGroups().contains("Administrador");
        
        if (!usuario.getLogin().equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para criar pedidos para outro usuário.");
        }

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        
        List<ItemPedido> itens = new ArrayList<>();
        Double total = 0.0;

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");

                cafeService.baixarEstoque(cafe.getId(), itemDto.quantidade());

                ItemPedido item = new ItemPedido();
                item.setQuantidade(itemDto.quantidade());
                item.setPrecoUnitario(cafe.getPreco());
                item.setCafe(cafe);
                item.setPedido(pedido);
                
                itens.add(item);
                total += (item.getPrecoUnitario() * item.getQuantidade());
            }
        } else {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        pedido.setItens(itens);
        pedido.setTotalPedido(total);

        repository.persist(pedido);
        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public PedidoDTOResponse findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        
        // --- VALIDAÇÃO DE SEGURANÇA ---
        String loginLogado = jwt.getSubject(); // Pega o login do token
        String loginDonoPedido = pedido.getUsuario().getLogin();
        
        // Verifica se o usuário tem perfil de Administrador
        boolean isAdmin = jwt.getGroups().contains("Administrador");
        
        // Se não for o dono E não for admin, bloqueia
        if (!loginDonoPedido.equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para acessar este pedido.");
        }
        // ------------------------------

        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public List<PedidoDTOResponse> findAll() {
        // Este método geralmente só é chamado por Admin (controlado no Resource),
        // mas se quiser garantir:
        if (!jwt.getGroups().contains("Administrador"))
            throw new ForbiddenException();
        
        return repository.listAll().stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDTOResponse> findByUsuario(Long idUsuario) {
        // Primeiro, busca o usuário dono do ID solicitado para pegar o login dele
        Usuario usuarioSolicitado = usuarioRepository.findById(idUsuario);
        if (usuarioSolicitado == null) throw new NotFoundException("Usuário não encontrado.");

        // --- VALIDAÇÃO DE SEGURANÇA ---
        String loginLogado = jwt.getSubject();
        String loginSolicitado = usuarioSolicitado.getLogin();
        
        boolean isAdmin = jwt.getGroups().contains("Administrador");
        
        // Se eu estou tentando ver pedidos de outra pessoa e não sou admin, bloqueia
        if (!loginSolicitado.equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para ver os pedidos deste usuário.");
        }
        // ------------------------------

        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}