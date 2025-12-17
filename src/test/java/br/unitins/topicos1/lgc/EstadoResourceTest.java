package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;


// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;

// import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
// import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;

// @QuarkusTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class EstadoResourceTest extends AuthTestBase {

//     private static Long idEstado;
//     private static String tokenAdmin;

//     @BeforeAll
//     static void setup() {
//         tokenAdmin = loginAdmin();
//     }

//     @Test
//     @Order(1)
//     public void testCreate() {

//         EstadoDTO dto = new EstadoDTO("Tocantins", "TO", 3L);

//         EstadoDTOResponse response = given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//             .contentType(ContentType.JSON)
//             .body(dto)
//         .when()
//             .post("/estados")
//         .then()
//             .statusCode(201)
//             .extract()
//             .as(EstadoDTOResponse.class);

//         idEstado = response.id();
//     }

//     @Test
//     @Order(2)
//     public void testUpdate() {

//         EstadoDTO dto = new EstadoDTO("Goiás", "GO", 1L);

//         given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//             .contentType(ContentType.JSON)
//             .body(dto)
//         .when()
//             .put("/estados/" + idEstado)
//         .then()
//             .statusCode(200)
//             .body("id", is(idEstado.intValue()))
//             .body("nome", is("Goiás"));
//     }

//     @Test
//     @Order(3)
//     public void testFindAll() {

//         given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//         .when()
//             .get("/estados")
//         .then()
//             .statusCode(200);
//     }

//     @Test
//     @Order(4)
//     public void testFindById() {

//         given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//         .when()
//             .get("/estados/" + idEstado)
//         .then()
//             .statusCode(200);
//     }

//     @Test
//     @Order(5)
//     public void testFindByNome() {

//         given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//         .when()
//             .get("/estados/find/Goiás")
//         .then()
//             .statusCode(200);
//     }

//     @Test
//     @Order(6)
//     public void testDelete() {

//         given()
//             .header("Authorization", "Bearer " + tokenAdmin)
//         .when()
//             .delete("/estados/" + idEstado)
//         .then()
//             .statusCode(204);
//     }
// }

@QuarkusTest
public class EstadoResourceTest {

    @Test
    public void testUp() {
        given().when().get("/estados")
            .then().statusCode(200);
    }
}