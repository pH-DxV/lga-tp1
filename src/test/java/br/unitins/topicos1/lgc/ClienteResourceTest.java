// package br.unitins.topicos1.lgc;

// import io.quarkus.test.junit.QuarkusTest;
// import io.quarkus.test.security.TestSecurity;
// import io.restassured.http.ContentType;
// import org.junit.jupiter.api.Test;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;

// @QuarkusTest
// public class ClienteResourceTest {

//     @Test
//     @TestSecurity(user = "admin", roles = {"ADMIN"})
//     public void testCreate() {
//         given()
//             .contentType(ContentType.JSON)
//         .when()
//             .post("/clientes")
//         .then()
//             .statusCode(201);
//     }

//     @Test
//     @TestSecurity(user = "admin", roles = {"ADMIN"})
//     public void testFindById() {
//         given()
//         .when()
//             .get("/clientes/1")
//         .then()
//             .statusCode(200);
//     }

//     @Test
//     @TestSecurity(user = "admin", roles = {"ADMIN"})
//     public void testUpdate() {
//         given()
//             .contentType(ContentType.JSON)
//         .when()
//             .put("/clientes/1")
//         .then()
//             .statusCode(200);
//     }

//     @Test
//     @TestSecurity(user = "admin", roles = {"ADMIN"})
//     public void testDelete() {
//         given()
//         .when()
//             .delete("/clientes/1")
//         .then()
//             .statusCode(204);
//     }
// }
