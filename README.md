## API REST para Gestão de Fretes

### Tecnologias utilizadas:

- Java 21
- Spring Boot 3.2.3
- MongoDB
- QueryDSL
- Docker

### Swagger
Para acessar a documentação da aplicação pelo swagger: http://localhost:8080/swagger-ui/index.html#/

### Para iniciar a aplicação

Caso for executar a aplicação por alguma IDE e não pelo Docker, ajustar o host do MongoDB no arquivo `application.yml`.
1. Compilação
- Via maven:

`mvn clean package -DskipTests`

- Via maven wrapper:

`./mvnw clean package -DskipTests` (Linux/MacOS)

`mvnw.cmd clean package -DskipTests` (Windows)

2. Execução (Necessário Docker)

`docker-compose up -d --build`

3. Verificar logs da aplicação

`docker exec -it <container-id> /bin/sh` (Linux/MacOS/Git Bash)

4. Parar a aplicação

`docker compose down`

