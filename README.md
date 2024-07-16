# Spring CRUD Application for Servants and Specializations

Este projeto é uma aplicação Spring simples que faz o CRUD completo de servidores e especializações.

## Instruções para Executar o Projeto

### 1. Criar e Rodar os Containers

Execute os containers do Docker Compose. Certifique-se de que o Docker e o Docker Compose estejam instalados no seu sistema.
docker-compose up -d
### 2. Configurações do Banco de Dados e Redis
Certifique-se de ajustar as configurações do banco de dados PostgreSQL e Redis no arquivo application.properties antes de iniciar a aplicação.

### 3. Iniciar o Projeto
Para iniciar o projeto Spring, utilize o seguinte comando:

bash
Copiar código
./mvnw spring-boot:run
### 4. Acessar a Documentação
Após iniciar a aplicação, acesse a documentação Swagger para explorar e testar as APIs disponíveis:

bash
Copiar código
http://localhost:8080/swagger-ui/index.html#/
### 5. Autenticação
Para utilizar as APIs protegidas, registre um usuário através da rota /auth/register para obter um Bearer Token.

Funcionalidades
CRUD de Servidores
Cadastro, leitura, atualização e exclusão de dados de servidores.
CRUD de Especializações
Cadastro, leitura, atualização e exclusão de dados de especializações.
Cache com Redis
Utilização do Redis para caching em operações de leitura (GET). Configurações específicas do Redis estão definidas para este projeto.
Testes
A aplicação possui testes automatizados para garantir a integridade e funcionalidade das operações.
Aprovações e Rejeições
Ao criar um servidor ou especialização, é possível gerar emails de aprovação ou rejeição.
Configurações de Email
Para habilitar o envio de emails ao aprovar ou rejeitar servidores e especializações, configure as seguintes propriedades em application.properties:

properties
Copiar código
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_de_app
A senha de aplicação pode ser gerada na seção "Senhas de app" da sua conta Google.
