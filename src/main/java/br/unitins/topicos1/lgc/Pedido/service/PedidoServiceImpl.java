package br.unitins.topicos1.lgc.Pedido.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.unitins.topicos1.lgc.Cafe.model.Cafe;
import br.unitins.topicos1.lgc.Cafe.repository.CafeRepository;
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

    @Override
    @Transactional
    public PedidoDTOResponse create(PedidoDTO dto) {
        // 1. Validações Iniciais
        Usuario usuario = usuarioRepository.findById(dto.idUsuario());
        if (usuario == null) throw new NotFoundException("Usuário não encontrado.");

        Endereco endereco = enderecoRepository.findById(dto.idEnderecoEntrega());
        if (endereco == null) throw new NotFoundException("Endereço não encontrado.");

        // 2. Criação do Pedido (Cabeçalho)
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEnderecoEntrega(endereco);
        pedido.setDataHora(LocalDateTime.now());
        
        // 3. Processamento dos Itens
        List<ItemPedido> itens = new ArrayList<>();
        Double total = 0.0;

        if (dto.itens() != null && !dto.itens().isEmpty()) {
            for (ItemPedidoDTO itemDto : dto.itens()) {
                
                Cafe cafe = cafeRepository.findById(itemDto.idCafe());
                if (cafe == null) {
                    throw new NotFoundException("Café não encontrado (ID: " + itemDto.idCafe() + ")");
                }

                // Cria o Item
                ItemPedido item = new ItemPedido();
                item.setQuantidade(itemDto.quantidade());
                item.setPrecoUnitario(cafe.getPreco()); // Pega o preço do Café
                item.setCafe(cafe);
                item.setPedido(pedido); // Vincula ao Pedido Pai
                
                itens.add(item);
                
                // Cálculo do Subtotal (Preço * Quantidade)
                total += (item.getPrecoUnitario() * item.getQuantidade());
            }
        } else {
            // Opcional: Lançar erro se tentar criar pedido sem itens
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        // 4. Atualização Final do Pedido
        pedido.setItens(itens);       // Vincula a lista
        pedido.setTotalPedido(total); // Salva o total calculado

        // 5. Persistência (Cascata salva os itens)
        repository.persist(pedido);
        
        return PedidoDTOResponse.valueOf(pedido);
    }

    @Override
    public PedidoDTOResponse findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
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
        return repository.findByUsuario(idUsuario).stream()
                .map(PedidoDTOResponse::valueOf)
                .collect(Collectors.toList());
    }
}