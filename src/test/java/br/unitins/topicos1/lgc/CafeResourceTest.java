package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CafeResourceTest {

    private static Long idCafe;
    private static Long idMarca;
    private static Long idCategoria;
    
    @BeforeEach
    public void setup() {
        if (idMarca == null) {
            MarcaDTO dto = new MarcaDTO("Marca Cafe Teste", null);
            idMarca = given().contentType(ContentType.JSON).body(dto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();
        }
        if (idCategoria == null) {
            CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO("Cat Cafe Teste", null);
            idCategoria = given().contentType(ContentType.JSON).body(dto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testCreate() {
        CafeDTO dto = new CafeDTO(
            "Café Teste", "Descricao", idMarca, idCategoria, 
            3L, 1L, Set.of(NotaSensorial.MEL), 
            88, 50.00, 250.0, 100
        );

        CafeDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/cafes")
        .then()
            .statusCode(201)
            .extract().as(CafeDTOResponse.class);

        idCafe = response.id();
    }
    
    // ... (Pode adicionar update e delete se quiser, mas o create já valida o principal) ...
    
    @Test
    @Order(2)
    public void testFindAll() {
        given().when().get("/cafes").then().statusCode(200);
    }
}