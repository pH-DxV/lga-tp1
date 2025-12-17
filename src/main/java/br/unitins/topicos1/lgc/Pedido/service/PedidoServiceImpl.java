package br.unitins.topicos1.lgc.Pedido.service;

import java.math.BigDecimal;
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
import br.unitins.topicos1.lgc.Frete.service.FreteService;
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
import jakarta.ws.rs.ForbiddenException; // Usado pelo SecurityService
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
    
    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        
        // 1. Identificação pelo Token (Substituindo o uso de dto.idUsuario())
        String loginLogado = jwt.getName(); // Pega o login (subject) do token
        
        // Busca o usuário pelo login (que é único)
        Usuario usuario = usuarioRepository.findByLogin(loginLogado);
        if (usuario == null) throw new NotFoundException("Usuário logado não encontrado no banco.");

        // Validação de Segurança (Embora já tenhamos pego do token, o securityService reforça)
        securityService.validarPermissao(usuario);

        // 2. Busca e Validação do Endereço
        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        // Validação Extra: O endereço pertence ao usuário?
        if (!endereco.getUsuario().getId().equals(usuario.getId())) {
             throw new ForbiddenException("O endereço informado não pertence ao usuário logado.");
        }

        // 3. Criação do Cabeçalho do Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        
        // Cálculo do Frete
        BigDecimal valorFrete = freteService.calcularFrete(endereco);
        pedido.setValorFrete(valorFrete);
        
        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal totalItens = BigDecimal.ZERO; 

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");

                // Baixa de estoque
                estoqueService.baixarEstoque(cafe.getId(), itemDto.quantidade());

                ItemPedido item = new ItemPedido();
                item.setQuantidade(itemDto.quantidade());
                item.setPrecoUnitario(cafe.getPreco()); 
                item.setCafe(cafe);
                item.setPedido(pedido);
                
                itens.add(item);
                
                BigDecimal valorItem = cafe.getPreco().multiply(new BigDecimal(item.getQuantidade()));
                totalItens = totalItens.add(valorItem);
            }
        } else {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        pedido.setItens(itens);
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