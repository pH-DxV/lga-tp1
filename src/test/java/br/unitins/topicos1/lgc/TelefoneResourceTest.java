// package br.unitins.topicos1.lgc;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.Matchers.anyOf;

// import io.quarkus.test.junit.QuarkusTest;
// // import io.restassured.http.ContentType;
// // import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
// // import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;


// // import static org.hamcrest.CoreMatchers.notNullValue;

// // import java.time.LocalDate;
// // import java.util.List;


// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class TelefoneResourceTest extends AuthTestBase {

// //     private static Long idTelefone;
// //     private static Long idUsuario;
// //     private static String token;

// //     @BeforeEach
// //     public void setup() {

// //         if (idUsuario == null) {

// //             TelefoneDTO telefoneInicial =
// //                 new TelefoneDTO("63", "999887766");

// //             EnderecoDTO enderecoInicial =
// //                 new EnderecoDTO(
// //                     "Rua Teste",
// //                     "123",
// //                     "Centro",
// //                     "Palmas",
// //                     "TO",
// //                     77000000L
// //                 );

// //             ClienteDTO dto = new ClienteDTO(
// //                 "Usuario Teste Telefone",
// //                 "user_telefone",
// //                 "123456",
// //                 "99988877766",
// //                 LocalDate.of(1990, 1, 1),
// //                 List.of(telefoneInicial),
// //                 List.of(enderecoInicial)
// //             );

// //             idUsuario = given()
// //                 .contentType(ContentType.JSON)
// //                 .body(dto)
// //             .when()
// //                 .post("/clientes")
// //             .then()
// //                 .statusCode(201)
// //                 .extract()
// //                 .as(ClienteDTOResponse.class)
// //                 .id();

// //             // 👉 login centralizado
// //             token = login("user_telefone", "123456");
// //         }
// //     }

// //     @Test
// //     @Order(1)
// //     public void testCreate() {

// //         TelefoneDTO dto = new TelefoneDTO("63", "988776655");

// //         TelefoneDTOResponse response = given()
// //             .header("Authorization", "Bearer " + token)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/telefones")
// //         .then()
// //             .statusCode(201)
// //             .body("id", notNullValue())
// //             .body("ddd", is("63"))
// //             .extract()
// //             .as(TelefoneDTOResponse.class);

// //         idTelefone = response.id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testUpdate() {

// //         TelefoneDTO dto = new TelefoneDTO("62", "911223344");

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/telefones/" + idTelefone)
// //         .then()
// //             .statusCode(200)
// //             .body("id", is(idTelefone.intValue()))
// //             .body("ddd", is("62"));
// //     }

// //     @Test
// //     @Order(3)
// //     public void testFindByUsuario() {

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //         .when()
// //             .get("/telefones/usuario/" + idUsuario)
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(4)
// //     public void testDelete() {

// //         given()
// //             .header("Authorization", "Bearer " + token)
// //         .when()
// //             .delete("/telefones/" + idTelefone)
// //         .then()
// //             .statusCode(204);
// //     }
// // }


// @QuarkusTest
// public class TelefoneResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/telefones")
//             .then().statusCode(anyOf(is(200), is(401)));
//     }
// }
