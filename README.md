# LGC — E‑commerce de Grãos de Café (API)

Uma API RESTful desenvolvida em Java com Quarkus, pensada para vender grãos de café online. Projeto acadêmico com estrutura preparada para uso real: catálogo detalhado, controle de estoque, gestão de marcas e categorias, cadastro de clientes com múltiplos endereços e processamento de pedidos com entrega.

Descrição curta (pitch)
----------------------
LGC é a base técnica ideal para torrefações e lojistas que querem vender cafés especiais na web. Catálogo rico em metadados sensoriais (pontuação SCA, tratamento, nível de torra), ferramentas de estoque e um fluxo de pedidos pensado para entrega ao cliente final — tudo exposto por uma API limpa e fácil de integrar.

Público‑alvo
-----------
- Pequenas e médias torrefações
- Lojistas online de cafés especiais
- Desenvolvedores que buscam um backend leve e extensível
- Docentes e alunos interessados em projetos práticos com Quarkus

Principais vantagens e diferenciais
----------------------------------
- Catálogo detalhado: cadastre tipos de café, categorias, níveis de torra, tratamentos e notas sensoriais (SCA).
- Gestão comercial: marcas, categorias e regras para evitar exclusão de itens em uso.
- Controle de estoque: verificação automática e baixa de estoque ao processar pedidos.
- Experiência do cliente: usuários podem cadastrar múltiplos endereços para entrega.
- Segurança básica: autenticação via JWT e controle de acesso por roles (Administrador / Usuário).
- Arquitetura modular (resources → services → repositories), facilitando integração e evolução.

Funcionalidades principais (visão resumida)
-------------------------------------------
- Autenticação: /auth/login (JWT)
- Cadastro e gerenciamento de usuários e clientes
- Cadastro de endereços (vários por usuário) e telefones
- Catálogo de cafés com filtros (nome, pontuação SCA, etc.)
- Cadastro de marcas e categorias
- Controle de estoque e operação de pedidos com endereço de entrega

Como subir a aplicação localmente (modo dev)
-------------------------------------------
1. Clone o repositório:
   ```bash
   git clone https://github.com/pH-DxV/lga-tp1.git
   cd lga-tp1
   ```
2. Execute a aplicação em modo de desenvolvimento (live reload):
   ```bash
   ./mvnw quarkus:dev
   ```
3. Acesse a aplicação:
   - Interface principal: http://localhost:8080
   - Interface interativa de documentação (Swagger UI): http://localhost:8080/q/swagger-ui

Abra o Swagger UI para explorar os endpoints, testar rotas e visualizar contratos de request/response.

Exemplo rápido (login e listagem)
--------------------------------
Obter token:
```bash
curl -i -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"usuario@example.com","senha":"senha"}'
```
Consumir endpoint protegido:
```bash
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/cafes
```

Como o sistema organiza a venda de cafés (funcional)
---------------------------------------------------
- Produtos — cada Café possui atributos do produto (nome, descrição), metadados sensoriais (pontuação SCA), nível de torra, tratamento, categoria e marca.
- Categorias & Marcas — permitem organizar o catálogo; há validações que impedem remoção de uma marca/categoria que possua cafés associados.
- Estoque — cada produto tem controle de estoque; ao processar um pedido, o sistema verifica disponibilidade e decrementa a quantidade.
- Pedidos — associam itens, quantidades e um endereço de entrega (o cliente pode ter vários endereços salvos).
- Endereços — múltiplos endereços por usuário, para permitir entregas em residências, comércios ou pontos alternativos.
- Perfil do usuário — roles (Administrador / Usuário) determinam permissões para criação, alteração e exclusão de recursos.

Para leitores técnicos — visão da implementação
----------------------------------------------
- Framework: Quarkus (extensões REST, Hibernate ORM/Panache, SmallRye JWT/OpenAPI quando habilitado).
- Organização: camada Resource (endpoints) → Service (regras de negócio) → Repository (Panache/Hibernate).
- Segurança:
  - JWT para autenticação; @RolesAllowed e SecurityService fazem validações de acesso e de dono de recurso.
  - Senhas devem ser tratadas por HashService (uso de algoritmo forte recomendado).
- Persistência: PanacheRepository facilita consultas e mapeamento das entidades.
- Validação: Jakarta Validation (@Valid) usada em DTOs para garantir dados consistentes.
- Observações: logs configurados de forma a não expor dados sensíveis; DTOs evitam mass‑assignment.

Próximos passos / Sugestões de evolução
--------------------------------------
(Aqui listamos separadamente apenas ideias de evolução — a descrição principal do README descreve a versão atual do projeto.)
- Habilitar documentação OpenAPI/Swagger em ambiente de produção (se ainda não habilitado).
- Adicionar testes automatizados (unitários e de integração).
- Versionamento do esquema do banco com Flyway ou similar.
- Pipeline de CI para build e testes automatizados.

Licença e contato
-----------------
- Inclua aqui a licença do projeto (por exemplo: MIT ou Apache‑2.0).
- Para dúvidas ou colaborações, abra uma issue no repositório.

Obrigado por conferir o LGC — uma API pensada para transformar o catálogo e a venda de cafés especiais em uma experiência simples e profissional.
