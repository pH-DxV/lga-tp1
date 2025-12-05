package br.unitins.topicos1.lgc.Pedido.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
import br.unitins.topicos1.lgc.Endereco.model.Endereco;
import br.unitins.topicos1.lgc.Endereco.repository.EnderecoRepository;
import br.unitins.topicos1.lgc.Estoque.service.EstoqueService; 
import br.unitins.topicos1.lgc.Frete.service.FreteService; // Importe o FreteService
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import br.unitins.topicos1.lgc.ItemPedido.model.ItemPedido;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Pedido.model.Pedido;
import br.unitins.topicos1.lgc.Pedido.model.PedidoStatus;
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
    EstoqueService estoqueService;
    
    @Inject
    FreteService freteService; // INJETE O SERVIÇO AQUI
    
    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        // 1. Validações Iniciais
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        String loginLogado = jwt.getSubject();
        boolean isAdmin = jwt.getGroups() != null && jwt.getGroups().contains("Administrador");
        
        if (loginLogado != null && !usuario.getLogin().equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para criar pedidos para outro usuário.");
        }

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        
        // --- CÁLCULO DO FRETE ---
        Double valorFrete = freteService.calcularFrete(endereco);
        pedido.setValorFrete(valorFrete);
        // ------------------------

        List<ItemPedido> itens = new ArrayList<>();
        Double totalItens = 0.0; // Soma só dos produtos

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");

                estoqueService.baixarEstoque(cafe.getId(), itemDto.quantidade());

                ItemPedido item = new ItemPedido();
                item.setQuantidade(itemDto.quantidade());
                item.setPrecoUnitario(cafe.getPreco());
                item.setCafe(cafe);
                item.setPedido(pedido);
                
                itens.add(item);
                totalItens += (item.getPrecoUnitario() * item.getQuantidade());
            }
        } else {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        pedido.setItens(itens);
        
        // --- TOTAL FINAL = PRODUTOS + FRETE ---
        pedido.setTotalPedido(totalItens + valorFrete);
        // --------------------------------------

        repository.persist(pedido);
        
        return PedidoDTOResponse.valueOf(pedido);
    }

    // ... (Os outros métodos findById, findAll, etc. continuam iguais) ...
    @Override
    public PedidoDTOResponse findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        
        String loginLogado = jwt.getSubject();
        String loginDonoPedido = pedido.getUsuario().getLogin();
        boolean isAdmin = jwt.getGroups() != null && jwt.getGroups().contains("Administrador");
        
        if (loginLogado != null && !loginDonoPedido.equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para acessar este pedido.");
        }

        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public List<PedidoDTOResponse> findAll() {
        return repository.listAll().stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDTOResponse> findByUsuario(Long idUsuario) {
        Usuario usuarioSolicitado = usuarioRepository.findById(idUsuario);
        if (usuarioSolicitado == null) throw new NotFoundException("Usuário não encontrado.");

        String loginLogado = jwt.getSubject();
        String loginSolicitado = usuarioSolicitado.getLogin();
        boolean isAdmin = jwt.getGroups() != null && jwt.getGroups().contains("Administrador");
        
        if (loginLogado != null && !loginSolicitado.equals(loginLogado) && !isAdmin) {
             throw new ForbiddenException("Você não tem permissão para ver os pedidos deste usuário.");
        }

        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}