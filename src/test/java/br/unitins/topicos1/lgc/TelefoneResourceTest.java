package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTO;
import br.unitins.topicos1.lgc.Telefone.dto.TelefoneDTOResponse;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TelefoneResourceTest {

    private static Long idTelefone;
    private static Long idUsuario;

    // Roda antes do 'testCreate' para garantir que temos um usuário
    @BeforeEach
    public void setup() {
        // Cria um usuário para vincular
        if (idUsuario == null) {
            UsuarioDTO dto = new UsuarioDTO(
                "Usuario Teste Vinculo", 
                "user_vinculo", // login
                "123456",       // senha
                "99988877700",  // cpf
                2,              // perfil (USER)
                null
            );
            
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios")
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }
        
        // (No caso do EnderecoResourceTest, mantenha a lógica de criar o Estado/Municipio aqui também se houver)
    }

    @Test
    @Order(1)
    public void testCreate() {
        TelefoneDTO dto = new TelefoneDTO("63", "988776655", idUsuario);

        TelefoneDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/telefones")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("ddd", is("63"))
            .extract().as(TelefoneDTOResponse.class);

        idTelefone = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        TelefoneDTO dto = new TelefoneDTO("62", "911223344", idUsuario);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/telefones/" + idTelefone)
        .then()
            .statusCode(200)
            .body("id", is(idTelefone.intValue()))
            .body("ddd", is("62"));
    }
    
    @Test
    @Order(3)
    public void testFindByUsuario() {
        given()
        .when()
            .get("/telefones/usuario/" + idUsuario)
        .then()
            .statusCode(200);
    }
    
    @Test
    @Order(4)
    public void testDelete() {
        given()
        .when()
            .delete("/telefones/" + idTelefone)
        .then()
            .statusCode(204);
    }
}