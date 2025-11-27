package br.unitins.topicos1.lgc;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarcaResourceTest {

    private static Long idMarca;

    @Test
    @Order(1)
    public void testCreate() {
        MarcaDTO dto = new MarcaDTO("Marca Teste", "Descricao Teste");

        MarcaDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/marcas")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Marca Teste"))
            .extract().as(MarcaDTOResponse.class);

        idMarca = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        MarcaDTO dto = new MarcaDTO("Marca Teste (Atualizada)", "Descricao Atualizada");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/marcas/" + idMarca)
        .then()
            .statusCode(200)
            .body("id", is(idMarca.intValue()))
            .body("nome", is("Marca Teste (Atualizada)"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/marcas")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/marcas/" + idMarca)
        .then()
            .statusCode(200)
            .body("id", is(idMarca.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/marcas/search/Marca Teste") 
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/marcas/" + idMarca)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(7)
    public void testFindByIdAfterDelete() {
        given()
        .when()
            .get("/marcas/" + idMarca)
        .then()
            .statusCode(404);
    }

    // --- NOVOS TESTES DE TRATAMENTO DE ERRO ---

    @Test
    @Order(8)
    public void testCreateInvalidMarca() {
        // DTO inválido (Nome nulo) para testar o ValidationExceptionMapper
        MarcaDTO dto = new MarcaDTO(null, "Descrição válida");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/marcas")
        .then()
            .statusCode(400) // Esperamos Bad Request
            // Verificamos se o JSON de erro (Problem Details) está correto
            .body("title", is("Erro de validação")) 
            .body("errors[0].message", is("O nome não pode ser nulo ou vazio.")); 
    }

    @Test
    @Order(9)
    public void testFindByIdNotFound() {
        // ID que com certeza não existe
        Long idInexistente = 999999L;

        given()
        .when()
            .get("/marcas/" + idInexistente)
        .then()
            .statusCode(404); // Esperamos Not Found
    }
}