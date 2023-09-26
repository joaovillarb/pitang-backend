# User Car Manager - Estrutura do Projeto

## Visão Geral

O projeto **User Car Manager** é uma API RESTful desenvolvido para empresa Pitang com o objetivo de gerenciar
informações de usuários e seus carros.
O projeto segue a arquitetura Clean Architecture, proporcionando uma organização modular e escalável.

A estrutura do projeto é dividida em quatro principais pacotes:

1. **Core:** Este pacote é o coração do projeto e contém as regras de negócios da aplicação. É importante notar que o
   pacote Core não deve conter nenhuma anotação de framework.
2. **Entrypoint:** O pacote Entrypoint serve como a entrada de dados na aplicação. Aqui, encontram-se os controladores,
   interfaces de API e pontos de entrada para interações com o sistema. É a camada responsável por receber e transmitir
   solicitações e respostas.
3. **Infrastructure:** Neste pacote, são gerenciadas todas as configurações e infraestrutura necessárias para o
   funcionamento da aplicação. Além disso, é onde ocorre a criação dos "beans" ou instâncias de classes necessárias para
   o projeto.
4. **Dataprovider:** O pacote Dataprovider abriga os provedores de dados, responsáveis por interagir com as fontes de
   dados externas, como bancos de dados, serviços web ou outros sistemas de informação.

## Instalação e Execução

Para executar o projeto, siga os seguintes passos:

1. Clone o repositório:
   `git clone https://github.com/joaovillarb/user-car-manager.git`
2. Navegue até a pasta do projeto baixado.
3. Build e Execução do Backend (Spring Boot com Java 17)
   _- Certifique-se de que você tem o Java 17 e o Maven instalados em seu sistema. Em seguida, siga estas etapas:_
4. Execute o build com o Maven:
   `mvn clean install`
5. Após o build ser concluído com sucesso, execute o projeto com o Maven:
   `mvn spring-boot:run`

## Configuração da Porta do Frontend

Se você alterou a porta do frontend para algo diferente de 4200, é necessário configurar a variável de ambiente
`FRONTEND_URL` para permissão no `CORS`. Certifique-se de definir essa variável de ambiente conforme necessário.

## Testes Unitários

O projeto possui testes unitários para garantir a qualidade do código.

## CI/CD

O projeto está configurado para Integração Contínua (CI) e Entrega Contínua (CD). A pipeline inclui o build da imagem e
o envio para o Amazon Elastic Container Registry (ECR), com instâncias criadas a partir do Amazon Elastic Container
Service (ECS) e um load balancer da AWS para distribuir o tráfego.
Além da action no github garantindo o build do projeto.

## Swagger e Health Check

- Rota do Swagger: http://api-1311701201.us-east-2.elb.amazonaws.com/api/swagger-ui/index.html#/
- Health Check: http://api-1311701201.us-east-2.elb.amazonaws.com/api/actuator/health

## Board do Projeto

[Link para o board do projeto no github](https://github.com/users/joaovillarb/projects/4).

## Estórias de Usuário

Aqui estão as URLs do GitHub contendo as estórias de usuário do projeto:
- [CRUD Usuários](https://github.com/joaovillarb/user-car-manager/issues/1)
- [CRUD carros](https://github.com/joaovillarb/user-car-manager/issues/2)
- [Configuração e disponibilidade do sonar](https://github.com/joaovillarb/user-car-manager/issues/6)
- [Spring security e autenticação via jwt](https://github.com/joaovillarb/user-car-manager/issues/7)
- [Remoção logica das entidades](https://github.com/joaovillarb/user-car-manager/issues/10)
- [Configuração e disponibilidade do jenkins](https://github.com/joaovillarb/user-car-manager/issues/12)
- [Deploy da aplicação na aws](https://github.com/joaovillarb/user-car-manager/issues/13)


## Solução

O projeto foi desenvolvido em Spring Boot com Java 17 devido à robustez e desempenho da plataforma Java.
A arquitetura Clean Architecture foi escolhida para manter o código bem organizado, facilitando a manutenção e a
escalabilidade.
Testes Unitários com JUnit que garantem a qualidade do código, identificam erros precocemente e garantem confiança nas
alterações do código.

## Sugestões para futuras melhorias:

- Criação de mais validações dos campos

## Pipeline e Sonar

- Jenkins: http://54.157.30.248:8080 (Usuário: user, Senha: user)
- Sonar: http://54.173.146.101:9000 (Usuário: user, Senha: user)
- Postman
  collection: [Postman](https://www.postman.com/joao-villar/workspace/user-car-manager/collection/10612055-7644ca65-6193-405a-96e5-f4d32973b61d?action=share&creator=10612055&active-environment=10612055-173be168-0766-4f56-b257-4b605892fde0)

## Infraestrutura

- Jenkins hospedado na AWS Lightsail.
- Sonar hospedado na AWS Lightsail.
- Imagem do backend armazenada no Amazon Elastic Container Registry (ECR).
- Executado nas instâncias do Amazon Elastic Container Service (ECS) e Amazon Elastic Compute Cloud (EC2).

# Url do projeto

http://api-1311701201.us-east-2.elb.amazonaws.com/api