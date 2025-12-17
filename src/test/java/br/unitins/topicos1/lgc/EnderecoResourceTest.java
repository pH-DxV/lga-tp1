// package br.unitins.topicos1.lgc;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.Matchers.anyOf;

// import org.junit.jupiter.api.Test;

// import io.quarkus.test.junit.QuarkusTest;

// // import java.time.LocalDate;
// // import java.util.List;

// // import org.junit.jupiter.api.BeforeAll;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
// // import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
// // import io.quarkus.test.junit.QuarkusTest;
// // import io.restassured.http.ContentType;


// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class EnderecoResourceTest {

// //     private static Long idEndereco;
// //     private static String token;

// //     @BeforeAll
// //     static void setup() {

// //         TelefoneDTO telefone =
// //             new TelefoneDTO("63", "999887766");

// //         EnderecoDTO endereco =
// //             new EnderecoDTO(
// //                 "77000000",
// //                 "Rua Inicial",
// //                 "1",
// //                 "Quadra 1",
// //                 "Centro",
// //                 1L
// //             );

// //         ClienteDTO cliente = new ClienteDTO(
// //             "Usuario Teste Endereco",
// //             "user_endereco",
// //             "123456",
// //             "11188899900",
// //             LocalDate.of(1995, 5, 20),
// //             List.of(telefone),
// //             List.of(endereco)
// //         );

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(cliente)
// //         .when()
// //             .post("/clientes")
// //         .then()
// //             .statusCode(201);

// //         AuthDTO authDto = new AuthDTO("user_endereco", "123456");

// //         token = given()
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
// //     public void testCreate() {

// //         EnderecoDTO dto = new EnderecoDTO(
// //             "77000000",
// //             "Rua das Flores",
// //             "10",
// //             "Quadra 10",
// //             "Centro",
// //             1L
// //         );

// //         idEndereco = given()
// //             .header("Authorization", "Bearer " + token)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/endereco")
// //         .then()
// //             .statusCode(201)
// //             .extract()
// //             .as(EnderecoDTOResponse.class)
// //             .id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testUpdate() {

// //         EnderecoDTO dto = new EnderecoDTO(
// //             "77000123",
// //             "Rua Nova",
// //             "20",
// //             "Qd 2",
// //             "Bairro Novo",
// //             1L
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/endereco/" + idEndereco)
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(3)
// //     public void testFindById() {

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //         .when()
// //             .get("/endereco/" + idEndereco)
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(4)
// //     public void testDelete() {

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //         .when()
// //             .delete("/endereco/" + idEndereco)
// //         .then()
// //             .statusCode(204);
// //     }
// // }

// @QuarkusTest
// public class EnderecoResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/enderecos")
//             .then().statusCode(anyOf(is(200), is(401)));
//     }
// }