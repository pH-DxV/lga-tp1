// package br.unitins.topicos1.lgc;

// import io.quarkus.test.junit.QuarkusTest;


// import org.junit.jupiter.api.Test;
// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.Matchers.anyOf;

// // import io.restassured.http.ContentType;

// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
// // import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
// // import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
// // import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
// // import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;




// // import java.time.LocalDate;
// // import java.util.List;
// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class UsuarioResourceTest {

// //     private static Long idUsuarioAdmin;
// //     private static String tokenAdmin;

// //     private final String loginAdmin = "admin.teste";
// //     private final String senhaAdmin = "senhaAdmin123";
// //     private final LocalDate dataNasc = LocalDate.of(1985, 10, 10);

// //     private String loginComoAdminPadrao() {
// //         AuthDTO authDto = new AuthDTO("raphael", "123456");

// //         return given()
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
// //     public void testCreateAdmin() {

// //         String tokenInicial = loginComoAdminPadrao();

// //         TelefoneDTO telefone = new TelefoneDTO("63", "999998888");
// //         EnderecoDTO endereco = new EnderecoDTO(
// //             "77000000",
// //             "Rua Admin",
// //             "100",
// //             "Sala 1",
// //             "Centro",
// //             1L
// //         );

// //         UsuarioDTO dto = new UsuarioDTO(
// //             "Novo Admin Teste",
// //             loginAdmin,
// //             senhaAdmin,
// //             "00011122299",
// //             1,
// //             dataNasc,
// //             List.of(telefone),
// //             List.of(endereco)
// //         );

// //         UsuarioDTOResponse response = given()
// //             .header("Authorization", "Bearer " + tokenInicial)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/usuarios/admin")
// //         .then()
// //             .statusCode(201)
// //             .extract()
// //             .as(UsuarioDTOResponse.class);

// //         idUsuarioAdmin = response.id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testLoginNovoAdmin() {

// //         AuthDTO authDto = new AuthDTO(loginAdmin, senhaAdmin);

// //         tokenAdmin = given()
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
// //     @Order(3)
// //     public void testUpdateAdmin() {

// //         TelefoneDTO telefone = new TelefoneDTO("62", "988887777");
// //         EnderecoDTO endereco = new EnderecoDTO(
// //             "77000111",
// //             "Rua Atualizada",
// //             "200",
// //             "Bloco B",
// //             "Centro",
// //             1L
// //         );

// //         UsuarioDTO dto = new UsuarioDTO(
// //             "Admin Atualizado",
// //             loginAdmin,
// //             senhaAdmin,
// //             "00011122299",
// //             1,
// //             dataNasc,
// //             List.of(telefone),
// //             List.of(endereco)
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/usuarios/" + idUsuarioAdmin)
// //         .then()
// //             .statusCode(200)
// //             .body("nome", is("Admin Atualizado"));
// //     }

// //     @Test
// //     @Order(4)
// //     public void testFindAll() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/usuarios")
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(5)
// //     public void testFindById() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/usuarios/" + idUsuarioAdmin)
// //         .then()
// //             .statusCode(200)
// //             .body("id", is(idUsuarioAdmin.intValue()));
// //     }

// //     @Test
// //     @Order(6)
// //     public void testCreateUsuarioInvalido() {

// //         UsuarioDTO dto = new UsuarioDTO(
// //             null,
// //             null,
// //             "senha",
// //             "cpf_invalido",
// //             2,
// //             null,
// //             List.of(),
// //             List.of()
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/usuarios/admin")
// //         .then()
// //             .statusCode(400);
// //     }

// //     @Test
// //     @Order(7)
// //     public void testAcessoSemToken() {
// //         given()
// //         .when()
// //             .get("/usuarios")
// //         .then()
// //             .statusCode(401);
// //     }

// //     @Test
// //     @Order(8)
// //     public void testDelete() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .delete("/usuarios/" + idUsuarioAdmin)
// //         .then()
// //             .statusCode(204);
// //     }

// //     @Test
// //     @Order(9)
// //     public void testLoginAposDelete() {

// //         AuthDTO authDto = new AuthDTO(loginAdmin, senhaAdmin);

// //         given()
// //             .contentType(ContentType.JSON)
// //             .body(authDto)
// //         .when()
// //             .post("/auth/login")
// //         .then()
// //             .statusCode(anyOf(is(401), is(404), is(204)));
// //     }
// // }


// @QuarkusTest
// public class UsuarioResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/usuarios")
//             .then().statusCode(anyOf(is(200), is(401)));
//     }
// }