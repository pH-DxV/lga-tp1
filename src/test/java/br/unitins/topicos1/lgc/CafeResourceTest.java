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
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CafeResourceTest {

    private static Long idCafe;
    private static Long idMarca;
    private static Long idCategoria;
    
    private final Long ID_TORRA_MEDIA = 3L;
    private final Long ID_TRATAMENTO_NATURAL = 1L;

    @BeforeEach
    public void setup() {
        if (idMarca == null) {
            MarcaDTO dto = new MarcaDTO("Marca Pedido Teste", null);
            idMarca = given().contentType(ContentType.JSON).body(dto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();
        }
        if (idCategoria == null) {
            CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO("Categoria Pedido Teste", null);
            idCategoria = given().contentType(ContentType.JSON).body(dto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testCreate() {
        CafeDTO dto = new CafeDTO(
            "Café Especial Teste", // Nome (Novo)
            "Descrição do café teste.", // Descrição (Novo)
            idMarca, 
            idCategoria, 
            ID_TORRA_MEDIA, 
            ID_TRATAMENTO_NATURAL, 
            Set.of(NotaSensorial.MEL, NotaSensorial.FRUTAS_VERMELHAS), 
            88, 
            55.00, 
            250.0, 
            100 
        );

        CafeDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/cafes")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Café Especial Teste"))
            .body("descricao", is("Descrição do café teste.")) // Verificando o novo campo
            .body("pontuacaoSCA", is(88))
            .extract().as(CafeDTOResponse.class);

        idCafe = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        CafeDTO dto = new CafeDTO(
            "Café Especial (Atualizado)", // Nome mudou
            "Descrição atualizada.", // Descrição mudou
            idMarca, 
            idCategoria, 
            ID_TORRA_MEDIA, 
            ID_TRATAMENTO_NATURAL, 
            Set.of(NotaSensorial.MEL), 
            90, 
            60.00, 
            250.0,
            50 
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/cafes/" + idCafe)
        .then()
            .statusCode(200)
            .body("id", is(idCafe.intValue()))
            .body("nome", is("Café Especial (Atualizado)"))
            .body("descricao", is("Descrição atualizada."))
            .body("pontuacaoSCA", is(90));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/cafes")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/cafes/" + idCafe)
        .then()
            .statusCode(200)
            .body("id", is(idCafe.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/cafes/search/nome/Especial")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testFindByPontuacao() {
        given()
        .when()
            .get("/cafes/search/pontuacao?min=89&max=91") 
        .then()
            .statusCode(200);
    }

    @Test
    @Order(7)
    public void testDelete() {
        given()
        .when()
            .delete("/cafes/" + idCafe)
        .then()
            .statusCode(204);
    }
}