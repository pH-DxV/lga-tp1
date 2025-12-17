// package br.unitins.topicos1.lgc;

// import io.quarkus.test.junit.QuarkusTest;
// import static org.hamcrest.Matchers.is;
// import static org.hamcrest.Matchers.anyOf;
// import static io.restassured.RestAssured.given;
// import org.junit.jupiter.api.Test;


// // import io.restassured.http.ContentType;

// // import org.junit.jupiter.api.TestMethodOrder;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;

// // import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
// // import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;


// // import static org.hamcrest.CoreMatchers.notNullValue;


// // import java.time.LocalDate;
// // import java.util.List;

// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class AuthResourceTest {

// //     private final String loginTeste = "login_auth_test";
// //     private final String senhaTeste = "senha123";

// //     @Test
// //     @Order(1)
// //     public void testLoginSucesso() {

// //         TelefoneDTO telefone = new TelefoneDTO("63", "999887766");
// //         EnderecoDTO endereco = new EnderecoDTO(
// //             "77000000",
// //             "Rua Auth",
// //             "10",
// //             "Casa",
// //             "Centro",
// //             1L
// //         );

// //         ClienteDTO clienteDto = new ClienteDTO(
// //             "Usuario Auth Teste",
// //             loginTeste,
// //             senhaTeste,
// //             "00099988877",
// //             LocalDate.of(1995, 5, 20),
// //             List.of(telefone),
// //             List.of(endereco)
// //         );

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(clienteDto)
// //         .when()
// //             .post("/clientes")
// //         .then()
// //             .statusCode(201);

// //         AuthDTO authDto = new AuthDTO(loginTeste, senhaTeste);

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(authDto)
// //         .when()
// //             .post("/auth/login")
// //         .then()
// //             .statusCode(200)
// //             .header("Authorization", notNullValue());
// //     }

// //     @Test
// //     @Order(2)
// //     public void testLoginSenhaIncorreta() {

// //         AuthDTO authDto = new AuthDTO(loginTeste, "senha_errada");

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(authDto)
// //         .when()
// //             .post("/auth/login")
// //         .then()
// //             .statusCode(204); // ou 401 conforme implementação
// //     }

// //     @Test
// //     @Order(3)
// //     public void testLoginUsuarioInexistente() {

// //         AuthDTO authDto = new AuthDTO("usuario_inexistente", "senha123");

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(authDto)
// //         .when()
// //             .post("/auth/login")
// //         .then()
// //             .statusCode(204); // ou 401 / 404
// //     }
// // }

// @QuarkusTest
// public class AuthResourceTest {

//     @Test
//     public void testAuthUp() {
//         given()
//         .when()
//             .get("/auth")
//         .then()
//             .statusCode(anyOf(is(200), is(404)));
//     }
// }