package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

import br.unitins.topicos1.lgc.Auth.dto.AuthDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthResourceTest {

    @Test
    @Order(1)
    public void testLoginSucesso() {
        // 1. Cria um usuário para testar o login
        // CORREÇÃO: Construtor atualizado (sem o campo peso no final)
        UsuarioDTO usuarioDto = new UsuarioDTO(
            "Usuario Login Teste", 
            "login_teste", 
            "123456", 
            "00099988877", 
            2,    // idPerfil (User)
            null  // dataNascimento
            // PESO REMOVIDO DAQUI
        );
        
        // Cadastra o usuário primeiro
        given()
            .contentType(ContentType.JSON)
            .body(usuarioDto)
            .when().post("/usuarios") // Ou /clientes, dependendo da sua rota pública
            .then().statusCode(201);

        // 2. Tenta fazer login com esse usuário
        AuthDTO authDto = new AuthDTO("login_teste", "123456");

        given()
            .contentType(ContentType.JSON)
            .body(authDto)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .header("Authorization", notNullValue()); // Verifica se o token veio
    }

    @Test
    @Order(2)
    public void testLoginFalha() {
        AuthDTO authDto = new AuthDTO("login_teste", "senha_errada");

        given()
            .contentType(ContentType.JSON)
            .body(authDto)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(204); // No Content (ou 401, conforme sua implementação)
    }
}