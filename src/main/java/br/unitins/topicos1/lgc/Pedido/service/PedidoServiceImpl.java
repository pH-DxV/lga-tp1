package br.unitins.topicos1.lgc.Pedido.service;

import java.math.BigDecimal;
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
    SecurityService securityService;

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        // 1. Validações Iniciais
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        // Validação de Segurança
        securityService.validarPermissao(usuario);

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        // 2. Criação do Cabeçalho
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        
        // --- CÁLCULO DO FRETE (BIGDECIMAL) ---
        // Assumindo que o FreteService já retorna BigDecimal. 
        // Se retornar Double, use: BigDecimal.valueOf(freteService.calcularFrete(endereco))
        BigDecimal valorFrete = BigDecimal.valueOf(freteService.calcularFrete(endereco));
        pedido.setValorFrete(valorFrete);
        // -------------------------------------
        
        List<ItemPedido> itens = new ArrayList<>();
        
        // Inicializa o total com ZERO (BigDecimal)
        BigDecimal totalItens = BigDecimal.ZERO; 

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");

                // Baixa de estoque
                estoqueService.baixarEstoque(cafe.getId(), itemDto.quantidade());

                ItemPedido item = new ItemPedido();
                item.setQuantidade(itemDto.quantidade());
                
                // O preço do café agora é BigDecimal
                item.setPrecoUnitario(cafe.getPreco()); 
                item.setCafe(cafe);
                item.setPedido(pedido);
                
                itens.add(item);
                
                // --- CÁLCULO MATEMÁTICO COM BIGDECIMAL ---
                // total += preco * quantidade
                BigDecimal valorItem = cafe.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade()));
                totalItens = totalItens.add(valorItem);
                // -----------------------------------------
            }
        } else {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        pedido.setItens(itens);
        
        // Soma final: Total Itens + Frete
        pedido.setTotalPedido(totalItens.add(valorFrete));

        repository.persist(pedido);
        
        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public PedidoDTOResponse findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        
        // Validação de Segurança
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
        securityService.validarPermissao(usuarioSolicitado);

        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}