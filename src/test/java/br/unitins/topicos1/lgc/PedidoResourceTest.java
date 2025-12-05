package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PedidoResourceTest {

    private static Long idPedido;
    private static Long idUsuario;
    private static Long idEndereco;
    private static Long idCafe;
    
    // Variáveis auxiliares para criar dependências
    private static Long idMarca;
    private static Long idCategoria;
    private static Long idMunicipio; // Assumindo que precise de um município para o endereço

    @BeforeEach
    public void setup() {
        // 1. Cria Usuário (Cliente para fazer o pedido)
        if (idUsuario == null) {
            UsuarioDTO dto = new UsuarioDTO(
                "Cliente Pedido Teste", 
                "cliente_pedido", 
                "123456", 
                "99988877766", 
                2, // Perfil USER 
                null
            );
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios") // Ou /clientes
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }

        // 2. Cria Endereço vinculado ao Usuário
        if (idEndereco == null) {
            // Se precisar de município, crie aqui ou use um ID fixo se tiver import.sql
            // Vou assumir ID 1 para município e estado para simplificar o teste de integração
            Long idMunicipioTeste = 1L; 
            
            EnderecoDTO dto = new EnderecoDTO(
                "77000000", 
                "Rua da Entrega", 
                "10", 
                "Casa", 
                "Centro", 
                idMunicipioTeste, 
                idUsuario
            );
            
            idEndereco = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/endereco")
                .then().statusCode(201).extract().as(EnderecoDTOResponse.class).id();
        }

        // 3. Cria Café (Produto)
        // Precisa de Marca e Categoria antes
        if (idCafe == null) {
            // Nomes únicos para evitar erro 500 de duplicidade
            long time = System.currentTimeMillis();
            
            if (idMarca == null) {
                MarcaDTO marcaDto = new MarcaDTO("Marca Pedido " + time, null);
                idMarca = given().contentType(ContentType.JSON).body(marcaDto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();
            }
            
            if (idCategoria == null) {
                CategoriaDoCafeDTO catDto = new CategoriaDoCafeDTO("Cat Pedido " + time, null);
                idCategoria = given().contentType(ContentType.JSON).body(catDto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();
            }

            CafeDTO cafeDto = new CafeDTO(
                "Café Venda " + time, 
                "Café teste para pedido",
                idMarca, 
                idCategoria, 
                3L, // Torra Média
                1L, // Tratamento Natural
                Set.of(NotaSensorial.MEL), 
                85, 
                50.00, // Preço: R$ 50,00
                500.0, 
                100 // Estoque Inicial: 100
            );
            
            idCafe = given()
                .contentType(ContentType.JSON)
                .body(cafeDto)
                .when().post("/cafes")
                .then().statusCode(201).extract().as(CafeDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testCreate() {
        // Compra 2 unidades do café (Total esperado em produtos: 100.00)
        // O frete será calculado automaticamente pelo Service
        ItemPedidoDTO item = new ItemPedidoDTO(2, idCafe);
        
        PedidoDTO dto = new PedidoDTO(
            idUsuario, 
            idEndereco, 
            List.of(item)
        );

        PedidoDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("itens.size()", is(1))
            .body("status", is("AGUARDANDO_PAGAMENTO"))
            // Não validamos o total exato aqui porque depende do valor do frete
            // que é calculado dinamicamente no Service.
            .extract().as(PedidoDTOResponse.class);

        idPedido = response.id();
    }
    
    @Test
    @Order(2)
    public void testBaixaEstoque() {
        // Verifica se o estoque do café baixou de 100 para 98
        // O endpoint GET /cafes/{id} agora consulta o EstoqueService
        given()
        .when()
            .get("/cafes/" + idCafe)
        .then()
            .statusCode(200)
            .body("estoque", is(98));
    }

    @Test
    @Order(3)
    public void testFindById() {
        given()
        .when()
            .get("/pedidos/" + idPedido)
        .then()
            .statusCode(200)
            .body("id", is(idPedido.intValue()))
            .body("usuario.id", is(idUsuario.intValue()));
    }

    @Test
    @Order(4)
    public void testFindByUsuario() {
        given()
        .when()
            .get("/pedidos/usuario/" + idUsuario)
        .then()
            .statusCode(200)
            // Deve haver pelo menos 1 pedido na lista
            .body("size()", org.hamcrest.Matchers.greaterThanOrEqualTo(1)); 
    }
    
    @Test
    @Order(5)
    public void testErroEstoqueInsuficiente() {
        // Tenta comprar 1000 unidades (só tem 98 agora)
        ItemPedidoDTO item = new ItemPedidoDTO(1000, idCafe);
        
        PedidoDTO dto = new PedidoDTO(idUsuario, idEndereco, List.of(item));

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(400); // Bad Request (Estoque insuficiente)
    }
}