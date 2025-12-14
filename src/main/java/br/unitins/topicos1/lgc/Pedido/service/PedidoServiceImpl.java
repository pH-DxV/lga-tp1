package br.unitins.topicos1.lgc.Pedido.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.unitins.topicos1.lgc.Security.service.SecurityService;
import br.unitins.topicos1.lgc.Usuario.model.Usuario;
import br.unitins.topicos1.lgc.Usuario.repository.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
    FreteService freteService;
    
    @Inject
    SecurityService securityService; // Injeção da segurança centralizada

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        // 1. Busca o usuário
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        // 2. Validação de Segurança (Centralizada)
        // Garante que o usuário logado é o mesmo do pedido (ou é Admin)
        securityService.validarPermissao(usuario);

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        
        // Cálculo do Frete
        Double valorFrete = freteService.calcularFrete(endereco);
        pedido.setValorFrete(valorFrete);

        List<ItemPedido> itens = new ArrayList<>();
        Double totalItens = 0.0; 

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
        pedido.setTotalPedido(totalItens + valorFrete); // Total = Produtos + Frete

        repository.persist(pedido);
        
        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public PedidoDTOResponse findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        
        // Validação de Segurança
        // Garante que só o dono do pedido (ou Admin) pode vê-lo
        securityService.validarPermissao(pedido.getUsuario());

        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public List<PedidoDTOResponse> findAll() {
        // Geralmente restrito a Admin no Resource
        return repository.listAll().stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDTOResponse> findByUsuario(Long idUsuario) {
        Usuario usuarioSolicitado = usuarioRepository.findById(idUsuario);
        if (usuarioSolicitado == null) throw new NotFoundException("Usuário não encontrado.");

        // Validação de Segurança
        // Garante que eu só vejo a MINHA lista de pedidos (ou sou Admin)
        securityService.validarPermissao(usuarioSolicitado);

        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}