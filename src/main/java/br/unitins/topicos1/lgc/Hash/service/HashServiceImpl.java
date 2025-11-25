package br.unitins.topicos1.lgc.Hash.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HashServiceImpl implements HashService {

    // Personalizei o SALT para o seu projeto. 
    // Isso garante que o hash de "123" aqui seja diferente do projeto do professor.
    private String salt = "#lgc-api-cafe-v1#"; 
    
    private Integer iterationCount = 405; // Levemente alterado para segurança
    private Integer keyLength = 512;

    @Override
    public String getHashSenha(String senha) {
        try {
            byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                    .generateSecret(new PBEKeySpec(
                            senha.toCharArray(),
                            salt.getBytes(),
                            iterationCount,
                            keyLength))
                    .getEncoded();

            return Base64.getEncoder().encodeToString(result);

        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar o hash da senha");
        }
    }

    // Este método main serve apenas para você testar e gerar hashes manualmente se precisar
    public static void main(String[] args) {
        HashService hash = new HashServiceImpl();
        System.out.println(hash.getHashSenha("123456"));
    }
}