// package br.unitins.topicos1.lgc;

// import org.junit.jupiter.api.Test;

// import io.quarkus.test.junit.QuarkusTest;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.Matchers.anyOf;

// // import io.quarkus.test.junit.QuarkusTest;
// // import io.restassured.http.ContentType;
// // import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
// // import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;


// // import org.junit.jupiter.api.*;


// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class CategoriaDoCafeResourceTest extends AuthTestBase {

// //     private static String tokenAdmin;
// //     private static Long idCategoria;

// //     @BeforeAll
// //     static void setup() {
// //         tokenAdmin = loginAdmin();
// //     }

// //     @Test
// //     @Order(1)
// //     public void testCreate() {
// //         CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO(
// //             "Categoria Teste",
// //             null
// //         );

// //         idCategoria = given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/categorias")
// //         .then()
// //             .statusCode(201)
// //             .extract()
// //             .as(CategoriaDoCafeDTOResponse.class)
// //             .id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testUpdate() {
// //         String nomeAtualizado = "Categoria Atualizada " + System.currentTimeMillis();

// //         CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO(
// //             nomeAtualizado,
// //             "Descrição atualizada"
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/categorias/" + idCategoria)
// //         .then()
// //             .statusCode(200)
// //             .body("id", is(idCategoria.intValue()))
// //             .body("nome", is(nomeAtualizado))
// //             .body("descricao", is("Descrição atualizada"));
// //     }

// //     @Test
// //     @Order(3)
// //     public void testFindAll() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/categorias")
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(4)
// //     public void testFindById() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/categorias/" + idCategoria)
// //         .then()
// //             .statusCode(200)
// //             .body("id", is(idCategoria.intValue()));
// //     }

// //     @Test
// //     @Order(5)
// //     public void testFindByNome() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/categorias/search/Categoria")
// //         .then()
// //             .statusCode(200);
// //     }

// //     @Test
// //     @Order(6)
// //     public void testDelete() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .delete("/categorias/" + idCategoria)
// //         .then()
// //             .statusCode(204);
// //     }

// //     @Test
// //     @Order(7)
// //     public void testFindByIdAfterDelete() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .get("/categorias/" + idCategoria)
// //         .then()
// //             .statusCode(404);
// //     }

// //     // ---------- TESTES DE ERRO ----------

// //     @Test
// //     @Order(8)
// //     public void testCreateInvalidCategoria() {
// //         CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO(
// //             null,
// //             "Descrição válida"
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/categorias")
// //         .then()
// //             .statusCode(400);
// //     }

// //     @Test
// //     @Order(9)
// //     public void testUpdateNotFound() {
// //         CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO(
// //             "Categoria Fantasma",
// //             "Descrição"
// //         );

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/categorias/999999")
// //         .then()
// //             .statusCode(404);
// //     }

// //     @Test
// //     @Order(10)
// //     public void testDeleteNotFound() {
// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .delete("/categorias/999999")
// //         .then()
// //             .statusCode(404);
// //     }
// // }

// @QuarkusTest
// public class CategoriaDoCafeResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/categorias")
//             .then().statusCode(anyOf(is(200), is(401)));
//     }
// }