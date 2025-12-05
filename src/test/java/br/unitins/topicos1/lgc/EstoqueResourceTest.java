package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstoqueResourceTest {

    private static Long idCafe;
    private static Long idUsuario;
    private static Long idEndereco;
    
    // Variáveis auxiliares
    private static Long idMarca;
    private static Long idCategoria;

    @BeforeEach
    public void setup() {
        // 1. Prepara dependências do Café (Marca/Categoria)
        if (idMarca == null) {
            MarcaDTO marcaDto = new MarcaDTO("Marca Estoque Teste", null);
            idMarca = given().contentType(ContentType.JSON).body(marcaDto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();
            
            CategoriaDoCafeDTO catDto = new CategoriaDoCafeDTO("Cat Estoque Teste", null);
            idCategoria = given().contentType(ContentType.JSON).body(catDto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();
        }

        // 2. Prepara dependências do Pedido (Usuario/Endereco)
        if (idUsuario == null) {
            // UsuarioDTO atualizado (sem peso)
            UsuarioDTO userDto = new UsuarioDTO("Cliente Estoque", "cli_estoque", "123", "88888888899", 2, null);
            idUsuario = given().contentType(ContentType.JSON).body(userDto).when().post("/usuarios").then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
            
            EnderecoDTO endDto = new EnderecoDTO("77000000", "Rua E", "1", "Qd 1", "Centro", 1L, idUsuario);
            idEndereco = given().contentType(ContentType.JSON).body(endDto).when().post("/endereco").then().statusCode(201).extract().as(EnderecoDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testIniciaEstoqueNoCadastro() {
        // Cria um café com estoque inicial de 100
        CafeDTO dto = new CafeDTO(
            "Café Teste Estoque", 
            "Teste de controle de estoque", 
            idMarca, 
            idCategoria, 
            1L, // Torra Clara
            1L, // Tratamento Natural
            Set.of(NotaSensorial.MEL), 
            85, 
            40.00, 
            500.0, 
            100 // <--- ESTOQUE INICIAL
        );

        CafeDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/cafes")
        .then()
            .statusCode(201)
            .body("estoque", is(100)) // Verifica se retornou o estoque correto
            .extract().as(CafeDTOResponse.class);

        idCafe = response.id();
    }

    @Test
    @Order(2)
    public void testBaixaEstoqueAoCriarPedido() {
        // Vamos comprar 10 unidades do café criado acima (100 - 10 = 90)
        ItemPedidoDTO item = new ItemPedidoDTO(10, idCafe);
        
        PedidoDTO pedidoDto = new PedidoDTO(
            idUsuario, 
            idEndereco, 
            List.of(item)
        );

        // 1. Faz o pedido
        given()
            .contentType(ContentType.JSON)
            .body(pedidoDto)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201);

        // 2. Verifica se o estoque do café baixou para 90
        // O endpoint GET /cafes/{id} busca o saldo atualizado no EstoqueService
        given()
        .when()
            .get("/cafes/" + idCafe)
        .then()
            .statusCode(200)
            .body("estoque", is(90));
    }

    @Test
    @Order(3)
    public void testErroEstoqueInsuficiente() {
        // Tenta comprar 1000 unidades (só tem 90 agora)
        ItemPedidoDTO item = new ItemPedidoDTO(1000, idCafe);
        
        PedidoDTO pedidoDto = new PedidoDTO(
            idUsuario, 
            idEndereco, 
            List.of(item)
        );

        given()
            .contentType(ContentType.JSON)
            .body(pedidoDto)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(400); // Espera Bad Request (Estoque Insuficiente)
    }
}