// package br.unitins.topicos1.lgc;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.junit.jupiter.api.MethodOrderer;
// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;

// import static io.restassured.RestAssured.given;

// @QuarkusTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class PagamentoResourceTest {

//     private static final String FAKE_TOKEN = "Bearer tokenFake";

//     @Test
//     @Order(1)
//     public void testCreate() {
//         given()
//             .header("Authorization", FAKE_TOKEN)
//             .contentType(ContentType.JSON)
//         .when()
//             .post("/pagamentos")
//         .then()
//             .statusCode(201);
//     }

//     @Test
//     @Order(2)
//     public void testFindById() {
//         given()
//             .header("Authorization", FAKE_TOKEN)
//         .when()
//             .get("/pagamentos/1")
//         .then()
//             .statusCode(200);
//     }
// }
