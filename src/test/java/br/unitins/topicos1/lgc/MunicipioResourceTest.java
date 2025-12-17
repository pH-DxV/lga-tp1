// package br.unitins.topicos1.lgc;

// import io.quarkus.test.junit.QuarkusTest;
// // import io.restassured.http.ContentType;

// // import org.junit.jupiter.api.BeforeAll;
// // import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// // import org.junit.jupiter.api.MethodOrderer;
// // import org.junit.jupiter.api.Order;
// // import org.junit.jupiter.api.TestMethodOrder;

// // import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
// // import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
// // import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTO;
// // import br.unitins.topicos1.lgc.Municipio.dto.MunicipioDTOResponse;

// import static io.restassured.RestAssured.given;
// // import static org.hamcrest.CoreMatchers.is;
// // import static org.hamcrest.CoreMatchers.notNullValue;


// // @QuarkusTest
// // @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// // public class MunicipioResourceTest extends AuthTestBase {

// //     private static Long idMunicipio;
// //     private static Long idEstado;
// //     private static String tokenAdmin;

// //     @BeforeAll
// //     static void setupAuth() {
// //         tokenAdmin = loginAdmin();
// //     }

// //     @BeforeEach
// //     public void setupEstado() {

// //         if (idEstado == null) {
// //             // Regiao.NORTE (ID 3)
// //             EstadoDTO dto = new EstadoDTO("Pará", "PA", 3L);

// //             idEstado = given()
// //                 .header("Authorization", "Bearer " + tokenAdmin)
// //                 .contentType(ContentType.JSON)
// //                 .body(dto)
// //             .when()
// //                 .post("/estados")
// //             .then()
// //                 .statusCode(201)
// //                 .extract()
// //                 .as(EstadoDTOResponse.class)
// //                 .id();
// //         }
// //     }

// //     @Test
// //     @Order(1)
// //     public void testCreate() {

// //         MunicipioDTO dto = new MunicipioDTO("Belém", idEstado);

// //         MunicipioDTOResponse response = given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .post("/municipios")
// //         .then()
// //             .statusCode(201)
// //             .body("id", notNullValue())
// //             .body("nome", is("Belém"))
// //             .extract()
// //             .as(MunicipioDTOResponse.class);

// //         idMunicipio = response.id();
// //     }

// //     @Test
// //     @Order(2)
// //     public void testUpdate() {

// //         MunicipioDTO dto = new MunicipioDTO("Santarém", idEstado);

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //             .contentType(ContentType.JSON)
// //             .body(dto)
// //         .when()
// //             .put("/municipios/" + idMunicipio)
// //         .then()
// //             .statusCode(200)
// //             .body("id", is(idMunicipio.intValue()))
// //             .body("nome", is("Santarém"));
// //     }

// //     @Test
// //     @Order(3)
// //     public void testDelete() {

// //         given()
// //             .header("Authorization", "Bearer " + tokenAdmin)
// //         .when()
// //             .delete("/municipios/" + idMunicipio)
// //         .then()
// //             .statusCode(204);
// //     }
// // }


// @QuarkusTest
// public class MunicipioResourceTest {

//     @Test
//     public void testUp() {
//         given().when().get("/municipios")
//             .then().statusCode(200);
//     }
// }
