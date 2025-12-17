// package br.unitins.topicos1.lgc;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.Matchers.anyOf;

// import org.junit.jupiter.api.Test;

// import io.quarkus.test.junit.QuarkusTest;


// // import java.math.BigDecimal;
// // import java.util.List;
// // import java.util.Set;

// // import org.junit.jupiter.api.BeforeAll;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
// // import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
// // import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
// // import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
// // import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
// // import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
// // import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
// // import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
// // import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
// // import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
// // import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
// // import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
// // import io.quarkus.test.junit.QuarkusTest;
// // import io.restassured.http.ContentType;


// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class EstoqueResourceTest extends AuthTestBase {

// //     private static String tokenAdmin;
// //     private static String tokenCliente;

// //     private static Long idCafe;
// //     private static Long idEndereco;
// //     private static Long idMarca;
// //     private static Long idCategoria;

// //     @BeforeAll
// //     static void setup() {

// //         tokenAdmin = loginAdmin();

// //         // ---------- Marca ----------
// //         MarcaDTO marcaDto = new MarcaDTO("Marca Estoque Teste", null);
// //         idMarca = given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(marcaDto)
// //         .when()
// //             .post("/marcas")
// //         .then()
// //             .statusCode(201)
// //             .extract().as(MarcaDTOResponse.class).id();

// //         // ---------- Categoria ----------
// //         CategoriaDoCafeDTO catDto =
// //             new CategoriaDoCafeDTO("Cat Estoque Teste", null);

// //         idCategoria = given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(catDto)
// //         .when()
// //             .post("/categorias")
// //         .then()
// //             .statusCode(201)
// //             .extract().as(CategoriaDoCafeDTOResponse.class).id();

// //         // ---------- Cliente ----------
// //         TelefoneDTO tel = new TelefoneDTO("63", "999999999");

// //         EnderecoDTO enderecoDto = new EnderecoDTO(
// //             "77000000",
// //             "Rua E",
// //             "1",
// //             "Qd 1",
// //             "Centro",
// //             1L
// //         );

// //         UsuarioDTO userDto = new UsuarioDTO(
// //             "Cliente Estoque",
// //             "cli_estoque",
// //             "123456",
// //             "88888888899",
// //             2,
// //             null,
// //             List.of(tel),
// //             List.of(enderecoDto)
// //         );

// //         UsuarioDTOResponse userResp = given()
// //             .contentType(ContentType.JSON)
// //             .body(userDto)
// //         .when()
// //             .post("/usuarios")
// //         .then()
// //             .statusCode(201)
// //             .extract().as(UsuarioDTOResponse.class);

// //         idEndereco = userResp.enderecos().get(0).id();

// //         // ---------- Login Cliente ----------
// //         AuthDTO authDto = new AuthDTO("cli_estoque", "123456");

// //         tokenCliente = given()
// //             .contentType(ContentType.JSON)
// //             .body(authDto)
// //         .when()
// //             .post("/auth/login")
// //         .then()
// //             .statusCode(200)
// //             .extract()
// //             .header("Authorization");
// //     }

// //     @Test
// //     @Order(1)
// //     public void testIniciaEstoqueNoCadastro() {

// //         CafeDTO dto = new CafeDTO(
// //             "Café Teste Estoque",
// //             "Teste de controle de estoque",
// //             idMarca,
// //             idCategoria,
// //             1L,
// //             1L,
// //             Set.of(NotaSensorial.MEL),
// //             85,
// //             new BigDecimal("40.00"),
// //             500.0,
// //             100
// //         );

// //         CafeDTOResponse response = given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/cafes")
// //         .then()
// //             .statusCode(201)
// //             .body("estoque", is(100))
// //             .extract().as(CafeDTOResponse.class);

// //         idCafe = response.id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testBaixaEstoqueAoCriarPedido() {

// //         ItemPedidoDTO item = new ItemPedidoDTO(10, idCafe);

// //         PedidoDTO pedidoDto = new PedidoDTO(
// //             idEndereco,
// //             List.of(item)
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenCliente)
// //             .contentType(ContentType.JSON)
// //             .body(pedidoDto)
// //         .when()
// //             .post("/pedidos")
// //         .then()
// //             .statusCode(201);

// //         given()
// //         .when()
// //             .get("/cafes/" + idCafe)
// //         .then()
// //             .statusCode(200)
// //             .body("estoque", is(90));
// //     }

// //     @Test
// //     @Order(3)
// //     public void testErroEstoqueInsuficiente() {

// //         ItemPedidoDTO item = new ItemPedidoDTO(1000, idCafe);

// //         PedidoDTO pedidoDto = new PedidoDTO(
// //             idEndereco,
// //             List.of(item)
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenCliente)
// //             .contentType(ContentType.JSON)
// //             .body(pedidoDto)
// //         .when()
// //             .post("/pedidos")
// //         .then()
// //             .statusCode(400);
// //     }
// // }


// @QuarkusTest
// public class EstoqueResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/estoque")
//             .then().statusCode(anyOf(is(200), is(404)));
//     }
// }
