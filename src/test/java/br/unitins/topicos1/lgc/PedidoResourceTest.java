package br.unitins.topicos1.lgc;

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
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

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

@BeforeEach
    public void setup() {
        // 1. Cria Usuário (se não existir)
        if (idUsuario == null) {
            // Usa um CPF aleatório ou fixo que não conflite
            UsuarioDTO dto = new UsuarioDTO("Cliente Pedido", "cliente_pedido", "123456", "12345678999", 2, null, 70.0);
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios")
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }

        // 2. Cria Endereço vinculado ao Usuário
        if (idEndereco == null) {
            EnderecoDTO dto = new EnderecoDTO("77000000", "Rua da Entrega", "Casa 1", idUsuario);
            idEndereco = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/endereco")
                .then().statusCode(201).extract().as(EnderecoDTOResponse.class).id();
        }

        // 3. Cria Café (precisa de Marca e Categoria antes)
        if (idCafe == null) {
            // TRUQUE: Usamos o tempo atual para gerar um nome único e evitar erro 500
            long uniqueId = System.currentTimeMillis();
            
            MarcaDTO marcaDto = new MarcaDTO("Marca Pedido " + uniqueId, null);
            Long idMarca = given().contentType(ContentType.JSON).body(marcaDto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();

            CategoriaDoCafeDTO catDto = new CategoriaDoCafeDTO("Cat Pedido " + uniqueId, null);
            Long idCategoria = given().contentType(ContentType.JSON).body(catDto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();

            // ATUALIZADO: CafeDTO com todos os campos novos (nome, descricao)
            CafeDTO cafeDto = new CafeDTO(
                "Café para Venda " + uniqueId, // nome (obrigatório)
                "Descrição do café teste",     // descricao (novo)
                idMarca, 
                idCategoria, 
                3L, // ID Nivel Torra (MEDIA)
                1L, // ID Tratamento (NATURAL)
                Set.of(NotaSensorial.MEL), 
                85, // Pontuação
                50.00, // Preço
                500.0, // Peso
                100 // Estoque
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
        // Vamos comprar 2 unidades do café (Total esperado: 100.00)
        ItemPedidoDTO item = new ItemPedidoDTO(2, idCafe);
        
        PedidoDTO dto = new PedidoDTO(
            idUsuario, 
            idEndereco, 
            List.of(item) // Lista de itens
        );

        PedidoDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pedidos")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("totalPedido", is(100.00f)) // Verifica se calculou certo (50 * 2)
            .body("itens.size()", is(1))      // Verifica se tem 1 item na lista
            .extract().as(PedidoDTOResponse.class);

        idPedido = response.id();
    }

    @Test
    @Order(2)
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
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/pedidos")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindByUsuario() {
        given()
        .when()
            .get("/pedidos/usuario/" + idUsuario)
        .then()
            .statusCode(200)
            // Verifica se retornou pelo menos um pedido na lista
            .body("size()", org.hamcrest.Matchers.greaterThanOrEqualTo(1)); 
    }
}
