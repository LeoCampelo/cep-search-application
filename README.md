# cep-search-application

Aplicação em Java com Spring Boot para consulta de CEPs em uma API externa (atualmente mockada com WireMock), gravação de logs no banco de dados e aplicação dos princípios SOLID. Desenvolvido como parte de um desafio com foco em boas práticas, arquitetura limpa e uso de Docker e AWS.


## Desafio

Essa app foi feita para atender um desafio técnico seguindo requisitos pré-definidos, o tema do desafio é livre, use a criatividade e desenvolva uma aplicação que atenda os critérios abaixo:
- Faça um desenho de solução para explicação da sua aplicação;
- Sua aplicação deverá prover a capacidade de realizar as operações de busca de cep em uma api externa (de preferência para fazer a api mocada com Wiremock, Mockoon ou similar);
- Os logs das consultas precisam ser gravados em base de dados, com o horário da consulta e os dados que retornaram da api.
- Sua aplicação deverá utilizar os conceitos básicos de SOLID;
- Seu repositório deverá ser exposto publicamente no Git.

Tecnologias obrigatórias:
- Java versão 11 ou superior.
- Banco de dados relacional ou não relacional

Será um diferencial:
- Utilização de Docker para criação do banco de dados e mocks
- AWS

## Tecnologias Utilizadas

Seguindo a proposta do desafio, esse projeto apresenta as seguintes tecnologias:
- Java 21 LTS
- Spring Boot 3.4.8
- Postgres SQL
- WireMock
- Docker
- AWS

Além de algumas configurações adicionais como:
- Lombok
- Auth0
- WebFlux
- Pacotes do Spring (Security, Tests e Data JPA)

## Arquitetura

A arquitetura utilizada foi a clássica MVC (Model-View-Controller) por razões práticas, estruturais e de manutenibilidade. A baixa complexidade da proposta do projeto, junto à praticidade de aplicar tal arquitetura nesse contexto foram motivos que levaram à escolha dessa, a boa separação de estruturas, testabilidade e possibilidade de evolução e melhorias se tornam bons prós para aplicar o MVC.

Porém também é importante entender as limitações e contras que podem aparecer ao utilizar essa arquitetura, como:
- Maior acoplamento, o que pode dificultar a manutenção sem um bom entendimento da estrutura do projeto.
- Dificuldade em substituir partes do sistema, pois em uma arquitetura monolítica é comum vermos casos onde o alto acoplamento leva a consequências indesejadas ao alterar trechos de código com alto impacto em outras funcionalidades.
- Escalabilidade comprometida, visto que se trata de um sistema Monolito, a escalabilidade pode se tornar um recurso caro e ineficiente, o que reduz a resiliência da aplicação.

O diagrama do funcionamento da aplicação:
![Diagrama da arquitetura](docs/DiagramaCEPSearch.png)

Atualmente tanto a aplicação quanto o container Docker com o WireMock estão sendo executados na mesma instância do EC2 (idealmente o `service` se comunica com a API externa, porém, para os requisitos do desafio, a API externa está representada dessa forma).

## Padrão SOLID  

O projeto segue o padrão de design SOLID nos seguintes quesitos:
- S (Princípio da Responsabilidade Única): Com a divisão clara de responsabilidades e funções em cada módulo:
    - `controller`: Responsável por requisições HTTP
    - `service`: Responsável pela lógica de negócio e validações
    - `repository`: Responsável pela camada de interação de banco de dados
    - `security`: Responsável pela camada de segurança e autenticação
- O (Princípio do Aberto/Fechado): A lógica de serviço, por exemplo, pode representar:
    - Aberto para: Utilização de outras APIs externas sem alteração no código e na lógica
- L (Princípio da Substituição de Liskov): Não foi aplicado; não há classes que implementam interfaces nesse projeto
- I (Princípio da Segregação de Interfaces): No módulo `repository` temos a extensão de `JPARepository`, recebendo assim diversos métodos e funcionalidades sem sobrecarregar a classe mãe. Outro exemplo é `UserDetailData` que implementa com foco os métodos necessários de `UserDetail`
- D (Princípio da Inversão de Dependência): Com a utilização de `@Autowired` para injeção de Beans, assim dependendo de abstrações de outras classes ou interfaces e não de implementações diretas na classe

## Padrão de Commits

Foi utilizado o seguinte padrão para branches e commits, facilitando possíveis code reviews se necessário, ambos em inglês

Nomeação de branches: `<tipo>/<descricao-da-tarefa>` e mensagem de commit: `<tipo>: <descrição do que foi feito>` 

Sendo os tipos:
- `feat`: Uma nova funcionalidade
- `fix`: Uma correção de bug
- `docs`: Manutenção na documentação

## Endpoints

Com a implementação de autorização via token, é necessário realizar cadastro/login antes de utilizar as chamadas para busca de CEP e histórico de logs.
Como a aplicação está hospedada em nuvem, é possível realizar chamadas seguindo o domínio da instância EC2 ou executando localmente na porta 8081:  
  
Local: `http://localhost:8081`  
AWS: `http://3.208.13.113:8081`

### Registro
| Método | URL | Descrição |
| :--- | :--- | :--- |
| POST | /api/user/create | Cria um novo usuário e retorna o usuário registrado com a senha encriptada |

Necessário o corpo em JSON:  
```
{
    "login" : "user",
    "password" : "password"
}
```

Exemplo de retorno:  
```
{
    "id" : "1",
    "login" : "user",
    "password" : "$2a$10$RD8Y7djOOataYs.s8tqGV.fuYEeUyOzlpuIOS1TF7VpmiMgkbvywG"
}
```
Token com expiração de 10 minutos.  

### Login  
| Método | URL | Descrição |
| :--- | :--- | :--- |
| POST | /login | Registra um novo usuário e retorna o token de autenticação |  

Necessário corpo em JSON:

```
{
    "login" : "user",
    "password" : "password"
}
```

Exemplo de retorno:
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNzU0MjQxMTAyfQ.gOiuhxu4AQeo8cL1aPvG55mwG_nL8Tf77EvsFISgFXztzy877Ml__N1_AY9NXJbIq_iTS8gTzcKO1N-Qj6eI5w
```
  
### Busca por CEP
| Método | URL | Descrição |
| :--- | :--- | :--- |
| GET | /api/cep/{cep} | Realiza a busca pelo CEP inserido na URL e retorna informações relacionadas |

É necessário também adicionar `header` com token de autorização:
| Header | Value |
| :--- | :--- | 
| Authorization | Bearer {token} | 

Como as chamadas estão mockadas, temos apenas quatro casos de uso para esse Endpoint:

| CEP | Descrição |
| :--- | :--- | 
| 01001000 | CEP referente a endereço em São Paulo | 
| 30140010 | CEP referente a endereço em Minas Gerais | 
| 99999999 | CEP inválido, retorno com erro | 
| Qualquer outra combinação | Caso para simular erro interno do servidor |

Exemplo de chamada com sucesso:
```
http://localhost:8081/api/cep/01001000
```  
Retorno:
![CepSP](docs/CepSPExample.png)  
Exemplo de chamada com erro:
```
http://localhost:8081/api/cep/99999999
```   
Retorno:
![CEPNotFound](docs/CepNotFoundExample.png) 

Exemplo de chamada com erro interno do servidor:
```
http://localhost:8081/api/cep/12345678
```   
Retorno:
![CEPServerError](docs/CepServerError.png) 

### Histórico de Logs
| Método | URL | Descrição |
| :--- | :--- | :--- |
| GET | /api/cep/log?page={page_number}&size={page_size} | Retorna os logs paginados por número da página e quantidade de registros por página |
| GET | /api/cep/log/by-date?start={date&time_start}&end={date&time_end}&page={page_number}&size={page_size} | Retorna os logs entre o intervalo de data e hora fornecidos com a opção de paginação também |

O padrão é, independente da URL, vir na página 0 (primeira) com 10 registros por página

É necessário também adicionar `header` com token de autorização:
| Header | Value |
| :--- | :--- | 
| Authorization | Bearer {token} | 

Exemplo de chamada com paginação:
```
http://localhost:8081/api/cep/log?page=0&size=10
```  
Retorno:
![LogHistoryPageable](docs/LogHistoryPageable.png) 

Exemplo de chamada com paginação e intervalo de datas:
```
http://localhost:8081/api/cep/log/by-date?start=2025-08-02T10:00:00&end=2025-08-31T10:00:00&page=0&size=10
```  
Retorno:
![LogHistoryByDate](docs/LogHistoryByDate.png) 

## Collection

A ferramenta utilizada para testar as requisições e respostas nesse projeto foi o Postman, as collections utilizadas estão disponibilizadas em /docs/collections na raiz do projeto.

## Possíveis Melhorias

Dado o tempo curto para a realização do projeto, várias features interessantes ainda não foram implementadas, porém estarão listadas para possíveis melhorias no projeto ao longo do tempo.

### View e Respostas as Requisições

A aplicação é puramente back-end e não possui implementação da camada View com uma interface para o usuário utilizar a aplicação com maior facilidade, além de um tratamento de respostas das URLs mais detalhado. Temos então:
- Possível interface para utilização da API
- Melhorar as respostas retornadas das requisições HTTP

### Segurança 

Apesar da implementação de token de segurança nas URLs de pesquisa, ainda se faz necessário:
- Validações de entrada para evitar dados mal formatados
- Remover as senhas inseridas diretamente no código
- Melhorar o tratamento de exceções

### Testes

No momento, apenas o módulo de serviço (responsável pelas regras de negócio e processamento) possui testes unitários, tendo isso em vista:
- Melhorar cobertura de testes, com mais cenários e abrangendo o módulo controller
- Testes de integração para a camada Model (módulos model e repository)

### Organização e Qualidade

O projeto, no momento, não conta com ambientes de desenvolvimento nem esteiras para deploy com CI/CD, logo:
- Implementação de ambientes (desenvolvimento, homologação e produção)
- Implementação de esteira no GitHub para deploy nos ambientes divididos em branches
- Aplicação de CI/CD para garantir a integridade dos ambientes
- Aplicação de Sonar para code smells e cobertura de testes

### AWS

Apesar do projeto estar funcionando em ambiente cloud, alguns pontos podem ser melhorados:
- Melhoria da estrutura de arquitetura de cloud
- Criação de repositório de infraestrutura em Terraform para provisionamento e maior flexibilidade/manutenção de serviços na AWS
- Utilizar serviço Secret Manager para tráfego de senhas
- Criar hierarquia de usuários na plataforma
- Registro de novo domínio no Route 53 para utilização dos endpoints via URL mais acessível
