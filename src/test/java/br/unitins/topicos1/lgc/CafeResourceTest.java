// package br.unitins.topicos1.lgc;

// import io.quarkus.test.junit.QuarkusTest;
// import io.restassured.http.ContentType;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;

// import java.math.BigDecimal;
// import java.util.Set;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.TestMethodOrder;

// import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
// import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
// import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
// import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
// import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
// import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;

// import static io.restassured.RestAssured.given;
// import static org.hamcrest.CoreMatchers.is;
// import static org.hamcrest.CoreMatchers.notNullValue;


// @QuarkusTest
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class CafeResourceTest extends AuthTestBase {

//     private static Long idCafe;
//     private static Long idMarca;
//     private static Long idCategoria;
//     private static String tokenAdmin;

//     @BeforeAll
//     static void setup() {
//         tokenAdmin = loginAdmin();

//         if (idMarca == null) {
//             MarcaDTO dto = new MarcaDTO("Marca Cafe Teste", null);
//             idMarca = given()
//                     .header("Authorization", "Bearer " + tokenAdmin)
//                     .contentType(ContentType.JSON)
//                     .body(dto)
//                     .when()
//                     .post("/marcas")
//                     .then()
//                     .statusCode(201)
//                     .extract()
//                     .as(MarcaDTOResponse.class)
//                     .id();
//         }

//         if (idCategoria == null) {
//             CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO("Categoria Cafe Teste", null);
//             idCategoria = given()
//                     .header("Authorization", "Bearer " + tokenAdmin)
//                     .contentType(ContentType.JSON)
//                     .body(dto)
//                     .when()
//                     .post("/categorias")
//                     .then()
//                     .statusCode(201)
//                     .extract()
//                     .as(CategoriaDoCafeDTOResponse.class)
//                     .id();
//         }
//     }

//     @Test
//     @Order(1)
//     public void testCreate() {
//         CafeDTO dto = new CafeDTO(
//                 "Café Especial Teste",
//                 "Descrição do café teste.",
//                 idMarca,
//                 idCategoria,
//                 3L,
//                 1L,
//                 Set.of(),
//                 88,
//                 new BigDecimal("55.00"),
//                 250.0,
//                 100
//         );

//         CafeDTOResponse response = given()
//                 .header("Authorization", "Bearer " + tokenAdmin)
//                 .contentType(ContentType.JSON)
//                 .body(dto)
//                 .when()
//                 .post("/cafes")
//                 .then()
//                 .statusCode(201)
//                 .body("id", notNullValue())
//                 .body("nome", is("Café Especial Teste"))
//                 .extract()
//                 .as(CafeDTOResponse.class);

//         idCafe = response.id();
//     }

//     @Test
//     @Order(2)
//     public void testFindAll() {
//         given()
//         .when()
//             .get("/cafes")
//         .then()
//             .statusCode(200);
//     }
// }