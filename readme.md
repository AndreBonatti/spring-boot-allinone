# Leia me primeiro

## Projeto conceito

Utilizado apenas para projeto de estudo 


### 1ª Etapa - Utilizado no projeto conceito de programação em camada, com as tecnologias:  

* IDE Intellij, com plugin SonarLint para verificação do código
* Framework Spring boot
* JPA (Hibernate/ORM)
* lombok e Sl4j 
* Example e pageable (pesquisa genéricas e paginadas)
* Interceptor (Rest / Handlerexception)
* ModelMapper e ObjectMapper (serialização e deserialização de objetos)
* Testes unitários e cobertura(Mockito)
* Swagger (documentação)

## 2ª Etapa - Para medida de conhecimento

* criado processo assíncrono "simples" com ThreadPoolTaskExecutor e anotação @Async controlado pela aplicação
* criado processo assíncrono "robusto" com kafka, producer e consumer com tratamentos de exceção na camada factory listener e fila de DLT

### Serviços a externos:

* Teste de integração com postman (evidencias com collections)
* Banco de dados postgres
* Docker para criação de imagem


# inciar projeto

comando para inciar
```
.\mvnw spring-boot:run
```

acessando documentação
> http://localhost:8080/app/swagger-ui.html


Build projeto:
* Faz-se a leitura do pom.xml que cria os passos necessários do projeto, entre eles o teste de cobertura e o artefato jar.

```
Build completo:
.\mvnw clean install

Sem testes unitários:
.\mvnw clean install -DskipTests
```
>target/jacoco-report/index.html

Criação da imagem docker:

```
docker-compose build --no-cache

docker-compose up --force-recreate
```


# References 

## kakfa
https://marco.dev/spring-boot-kafka-tutorial

https://github.com/provectus/kafka-ui/blob/master/documentation/compose/DOCKER_COMPOSE.md

https://github.com/expertos-tech/dio-tutorial-kafka

https://blog.jdriven.com/2022/01/kafka-dead-letter-publishing/

https://evgeniy-khyst.com/spring-kafka-non-blocking-retries-and-dlt/

## Redis and Postgres
https://medium.com/echohub/spring-boot-redis-postgresql-caching-58ca352280a3

https://medium.com/editora-globo/configurando-redis-com-dados-de-conex%C3%A3o-recebidas-do-provedor-com-spring-boot-de4de9469688

## ControllerAdvice
https://www.baeldung.com/global-error-handler-in-a-spring-rest-api

## Swagger 
https://www.baeldung.com/swagger-set-example-description

## Pagination
https://www.javainuse.com/spring/SpringBootUsingPagination
