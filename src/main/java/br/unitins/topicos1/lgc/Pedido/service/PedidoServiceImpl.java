package br.unitins.topicos1.lgc.Pedido.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

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
    
    // Logger para debug
    private static final Logger LOG = Logger.getLogger(PedidoServiceImpl.class);

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        LOG.info("Iniciando criação de pedido...");

        // Debug: Imprime todas as claims do token para ver o que está chegando
        LOG.info("Claims do Token: " + jwt.getClaimNames());

        // 1. Identificação pelo Token
        Object claimId = jwt.getClaim("id");
        
        if (claimId == null) {
             LOG.error("ERRO: Claim 'id' não encontrado no token. Claims disponíveis: " + jwt.getClaimNames());
             throw new ForbiddenException("Usuário não identificado no token (Claim 'id' ausente). Por favor, faça login novamente.");
        }
        
        Long idUsuarioLogado;
        try {
            idUsuarioLogado = Long.parseLong(claimId.toString());
        } catch (NumberFormatException e) {
            LOG.error("ERRO: Claim 'id' não é um número válido: " + claimId);
            throw new ForbiddenException("Erro ao processar identificação do usuário.");
        }

        LOG.info("Usuário identificado pelo token. ID: " + idUsuarioLogado);

        // Busca o usuário
        Usuario usuario = usuarioRepository.findById(idUsuarioLogado);
        if (usuario == null) {
            LOG.error("ERRO: Usuário com ID " + idUsuarioLogado + " não encontrado no banco.");
            throw new NotFoundException("Usuário não encontrado.");
        }
        LOG.info("Usuário encontrado no banco: " + usuario.getNome());

        // Validação de Segurança
        securityService.validarPermissao(usuario);

        // 2. Busca e Validação do Endereço
        LOG.info("Buscando endereço ID: " + dto.idEnderecoEntrega());
        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) {
            LOG.error("ERRO: Endereço com ID " + dto.idEnderecoEntrega() + " não encontrado.");
            throw new NotFoundException("Endereço não encontrado.");
        }
        LOG.info("Endereço encontrado.");
        
        // Regra extra
        if (!endereco.getUsuario().getId().equals(usuario.getId())) {
             LOG.error("ERRO: Endereço pertence ao usuário " + endereco.getUsuario().getId() + ", mas quem pede é " + usuario.getId());
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
        LOG.info("Frete calculado: " + valorFrete);
        
        List<ItemPedido> itens = new ArrayList<>();
        BigDecimal totalItens = BigDecimal.ZERO; 

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                LOG.info("Processando item. Café ID: " + itemDto.idCafe());
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) {
                    LOG.error("ERRO: Café com ID " + itemDto.idCafe() + " não encontrado.");
                    throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");
                }

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
        LOG.info("Pedido salvo com sucesso. ID: " + pedido.getId());
        
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

        securityService.validarPermissao(usuarioSolicitado);

        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}