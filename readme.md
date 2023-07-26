<h1 align="center">MANGA-API</h1>

## Stacks:

- Java
- Maven
- Spring Boot
- Spring MVC
- Spring Data JPA
- SpringDoc OpenAPI 3
- MySQL
- H2
- JUnit 5
- Mockito
- AssertJ

## Como Executar:

### !!! requer mysql server instalado na máquina !!!

- Clonar o repositório git
- O projeto requer um banco de dados Mysql, então é necessário criar uma base de dados com os seguintes comandos e credenciais:

```
mysql CREATE USER 'user'@'%' IDENTIFIED BY '123456';

mysql GRANT ALL PRIVILEGES ON *.* TO 'user'@'%' WITH GRANT OPTION;

exit

mysql -u user -p

CREATE DATABASE mangaapi;

exit
```

- Contruir o projeto:

```
mvn clean package
```

- Executar a aplicação:

```
java -jar target/manga-api-0.0.1-SNAPSHOT.jar
```

A api poderá ser acessada em [localhost:8080](http://localhost:8080)

O Swagger poderá ser vizualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
