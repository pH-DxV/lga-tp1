-- ============================================================================
-- ESTADOS (Regiao: 1=Centro-Oeste, 2=Nordeste, 3=Norte, 4=Sudeste, 5=Sul)
-- ============================================================================
INSERT INTO estado (nome, sigla, regiao) VALUES ('Tocantins', 'TO', 3); -- ID 1
INSERT INTO estado (nome, sigla, regiao) VALUES ('Goiás', 'GO', 1);     -- ID 2
INSERT INTO estado (nome, sigla, regiao) VALUES ('Minas Gerais', 'MG', 4); -- ID 3
INSERT INTO estado (nome, sigla, regiao) VALUES ('São Paulo', 'SP', 4);    -- ID 4

-- ============================================================================
-- MUNICIPIOS
-- ============================================================================
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 1);    -- ID 1
INSERT INTO municipio (nome, id_estado) VALUES ('Paraíso', 1);   -- ID 2
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 2);   -- ID 3
INSERT INTO municipio (nome, id_estado) VALUES ('Varginha', 3);  -- ID 4 (Terra do café!)

-- ============================================================================
-- USUÁRIOS
-- Perfil é salvo na tabela auxiliar usuario_perfil (1=Admin, 2=User)
-- ============================================================================

-- Admin (Senha 123456)
INSERT INTO usuario (nome, cpf, login, senha, dataNascimento, peso) 
VALUES ('Raphael Admin', '11111111111', 'raphael', 'KUtBD9kIl87mEJ9A9ykmmWdNdO5AARI95nCklB5rpjrGkb7LVoqBwrpHiYiaMh+yyBfnfYR+G1gJecdm8A85rw==', '2000-01-01', 75.0);

-- Cliente Comum (Senha 123456)
INSERT INTO usuario (nome, cpf, login, senha, dataNascimento, peso) 
VALUES ('João Cliente', '22222222222', 'joao', 'KUtBD9kIl87mEJ9A9ykmmWdNdO5AARI95nCklB5rpjrGkb7LVoqBwrpHiYiaMh+yyBfnfYR+G1gJecdm8A85rw==', '1995-05-20', 80.0);

-- Definindo os perfis (Tabela auxiliar gerada pelo @ElementCollection)
-- Usuario 1 é ADMIN (1)
INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES (1, 1);
-- Usuario 2 é USER (2)
INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES (2, 2);

-- ============================================================================
-- TELEFONES E ENDEREÇOS
-- ============================================================================
INSERT INTO telefone (ddd, numero, id_usuario) VALUES ('63', '999991234', 1);
INSERT INTO endereco (cep, rua, complemento, id_usuario) VALUES ('77000000', 'Rua dos Cafés', 'Qd 10 Lt 1', 1);

-- ============================================================================
-- DADOS DE PRODUTO (Marca e Categoria)
-- ============================================================================
INSERT INTO marca (nome, descricao) VALUES ('3 Corações', 'Marca tradicional brasileira.'); -- ID 1
INSERT INTO marca (nome, descricao) VALUES ('Orfeu', 'Cafés especiais de alta qualidade.'); -- ID 2
INSERT INTO marca (nome, descricao) VALUES ('Starbucks', 'Marca internacional.'); -- ID 3

INSERT INTO categoriadocafe (nome, descricao) VALUES ('Tradicional', 'Para o dia a dia.'); -- ID 1
INSERT INTO categoriadocafe (nome, descricao) VALUES ('Especial', 'Acima de 80 pontos SCA.'); -- ID 2
INSERT INTO categoriadocafe (nome, descricao) VALUES ('Gourmet', 'Alta qualidade sem defeitos.'); -- ID 3

-- ============================================================================
-- CAFÉS (PRODUTOS)
-- NivelTorra: 1=Clara, 2=Media-Clara, 3=Media, 4=Media-Escura, 5=Escura
-- Tratamento: 1=Natural, 2=Lavado, 3=Honey...
-- ============================================================================

-- Café 1: Orfeu Clássico
INSERT INTO cafe (nome, descricao, pontuacao_sca, preco, peso, estoque, id_marca, id_categoria_cafe, nivelDeTorra, tratamento)
VALUES ('Orfeu Clássico', 'Equilibrado e aveludado.', 84, 45.90, 250.0, 100, 2, 2, 3, 1);

-- Café 2: 3 Corações Mogiana
INSERT INTO cafe (nome, descricao, pontuacao_sca, preco, peso, estoque, id_marca, id_categoria_cafe, nivelDeTorra, tratamento)
VALUES ('Mogiana Paulista', 'Doçura intensa e notas de amêndoas.', 82, 25.50, 250.0, 200, 1, 3, 4, 1);

-- ============================================================================
-- NOTAS SENSORIAIS DOS CAFÉS
-- Tabela auxiliar gerada pelo @ElementCollection
-- ============================================================================

-- Notas do Café 1 (Orfeu): Caramelo e Frutas Secas (Exemplo)
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'CARAMELO');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'CASTANHA_DO_PARA');

-- Notas do Café 2 (Mogiana): Chocolate e Nozes
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'CHOCOLATE_AO_LEITE');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'AMENDOAS');