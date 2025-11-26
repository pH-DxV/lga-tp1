package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioResourceTest {

    private static Long idUsuario;

    @Test
    @Order(1)
    public void testCreate() {
        // ATUALIZADO: Agora passamos login, senha e idPerfil (1=Admin, 2=User)
        UsuarioDTO dto = new UsuarioDTO(
            "Usuario Teste", 
            "usuario_teste", // login
            "123456",        // senha
            "11122233344", 
            2,               // idPerfil (2 = User)
            null,            // dataNascimento
            75.5             // peso
        );

        UsuarioDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/usuarios")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Usuario Teste"))
            .body("login", is("usuario_teste")) // Verifica se o login voltou
            .body("cpf", is("11122233344"))
            .extract().as(UsuarioDTOResponse.class);

        idUsuario = response.id();
    }

    @Test
    @Order(2) // Pode rodar logo após criar o usuário comum
    public void testCreateAdmin() {
        UsuarioDTO dto = new UsuarioDTO(
            "Admin Teste", 
            "admin_teste", 
            "123456", 
            "00011122233", // CPF diferente para não dar conflito
            1,             // <--- idPerfil 1 (ADMINISTRADOR)
            null, 
            80.0
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/usuarios")
        .then()
            .statusCode(201)
            .body("nome", is("Admin Teste"))
            // Verifica se dentro da lista de perfis tem o objeto com label "Administrador"
            // Como o Perfil é serializado como objeto JSON, buscamos pelo campo label
            .body("perfis[0].label", is("Administrador")); 
    }
    
    @Test
    @Order(2)
    public void testUpdate() {
        // ATUALIZADO: DTO completo para update
        UsuarioDTO dto = new UsuarioDTO(
            "Usuario Teste Atualizado", 
            "usuario_teste", // Mantém o login
            "123456",        // Mantém a senha
            "55566677788",   // Novo CPF
            2, 
            null, 
            80.0
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/usuarios/" + idUsuario)
        .then()
            .statusCode(200)
            .body("id", is(idUsuario.intValue()))
            .body("nome", is("Usuario Teste Atualizado"))
            .body("cpf", is("55566677788"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/usuarios")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/usuarios/" + idUsuario)
        .then()
            .statusCode(200)
            .body("id", is(idUsuario.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/usuarios/find/Usuario Teste")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/usuarios/" + idUsuario)
        .then()
            .statusCode(204);
    }
}