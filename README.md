# Sistema de Usuários de Carros

# Estórias de Usuário

Estória de Usuário 1: Montar arquitetura do sistema

Como gerente do projeto , desejo montar a arquitetura inicial do sistema, para atender os requisitos mínimos da aplicação
Critérios de Aceitação: 
Deve ter um servidor embutido na aplicação;
Deve ter um processo de build via Maven;
Deve ter um banco de dados em H2 e persistência com JPA/Hibernate;
Deve utilizar o Framework Spring;
Deve Utilizar no mínimo Java 8;
Deve Utilizar testes unitários.

Estória de Usuário 2: Autenticação de Usuário

Como um usuário, desejo poder autenticar-me na aplicação para acessar recursos protegidos.
Critérios de Aceitação:
A rota /api/signin deve aceitar um objeto com os campos login e password.
Deve retornar um token de acesso JWT válido com as informações do usuário logado.
Em caso de login inexistente ou senha inválida, deve retornar um erro com a mensagem "Invalid login or password".

Estória de Usuário 3: Gerenciamento de Usuários

Como um administrador, desejo listar todos os usuários, cadastrar novos usuários, buscar um usuário pelo ID, remover um usuário pelo ID e atualizar um usuário pelo ID.
Critérios de Aceitação:
Deve haver rotas /api/users para listar, cadastrar e buscar usuários.
Deve haver rotas /api/users/{id} para remover e atualizar um usuário pelo ID.
O sistema deve verificar se o e-mail e o login do usuário já existem e retornar mensagens de erro adequadas ("Email already exists" ou "Login already exists").
Deve haver validação de campos inválidos e campos não preenchidos, retornando mensagens de erro apropriadas ("Invalid fields" ou "Missing fields").

Estória de Usuário 4: Gerenciamento de Carros

Como um usuário, desejo listar todos os carros associados à minha conta, cadastrar novos carros, buscar um carro pelo ID, remover um carro pelo ID e atualizar um carro pelo ID.
Critérios de Aceitação:
Deve haver rotas /api/cars para listar, cadastrar e buscar carros.
Deve haver rotas /api/cars/{id} para remover e atualizar um carro pelo ID.
O sistema deve verificar se a placa do carro já existe e retornar uma mensagem de erro adequada ("License plate already exists").
Deve haver validação de campos inválidos e campos não preenchidos, retornando mensagens de erro apropriadas ("Invalid fields" ou "Missing fields").

Estória de Usuário 5: Informações do Usuário Logado

Como um usuário autenticado, desejo poder acessar informações sobre minha própria conta.
Critérios de Aceitação:
Deve haver uma rota /api/me que retorne informações do usuário logado (firstName, lastName, email, birthday, login, phone, cars).
Além das informações do usuário, deve incluir informações como createdAt (data de criação do usuário) e lastLogin (data da última vez que o usuário realizou login).

Estória de Usuário 6: Autenticação Requerida para Gerenciamento de Carros

Como um usuário autenticado, desejo que todas as operações de gerenciamento de carros (listar, cadastrar, buscar, remover e atualizar) exijam autenticação via token JWT.
Critérios de Aceitação:
Todas as rotas relacionadas a carros devem exigir que o token de acesso JWT seja enviado no cabeçalho Authorization.
Em caso de token não enviado deve retornar um erro com a mensagem "Unauthorized".
Em caso de token expirado, deve retornar um erro com a mensagem "Unauthorized - invalid session".

Estória de Usuário 7: Desenvolver front-end funcional

Como um usuário, desejo ter acesso gráfico ao sistema de forma funcional, para poder operar a aplicação.
Critérios de Aceitação:
Ter as rotas do backend sendo acessadas via front-end utilizando angular.

# Dependências
Para rodar o projeto é preciso ter as seguintes dependência `Maven 3.8.6`, `Node V16.18.0`, `npm 8.19.2`, `Java 11`, `Angular 15.2.9`, instaladas na máquina.

# Build & Tests
Para buildar o projeto rode o comando `mvn clean install`. Esse comando irá, rodar os testes e gerar o arquivo .jar e os arquivos estáticos do Angular.

# Run 
Para rodar o projeto utilize o comando maven `mvn spring-boot:run`. Este comando irá rodar a aplicação na porta 8080. 

Para acessar a aplicação locamente utilize a rota [Localhost](http://localhost:8080).


# SOLUÇÃO
Tecnologias Utilizadas:

Linguagem de Programação: Java 11

Framework: Spring Boot

Autenticação: JWT (JSON Web Tokens)

Banco de Dados: H2

Persistência: JPA

Front-end Mínimo: Angular 15

Build: Maven

Servidor: Tomcat Embutido, graças ao spring-boot

Descrição da Solução:

Foi utilizado o Maven para realizar os builds da aplicação , onde ficam armazenadas todas as bibliotecas que serão utilizadas no sistema no arquivo pom.xml.

A versão utilizada do Java foi a 11, o qual traz várias vantagens, incluindo melhorias de desempenho, segurança, suporte técnico, recursos de linguagem e bibliotecas em relação a suas versões anteriores, também existindo uma boa base de usuários auxiliando na resolução de possíveis problemas que possam existir na implementação.

Para montar a aplicação back-end , se foi incluído no pom.xml o framework spring-boot na versão 2.7.6, com esse framework é possível abstrair, várias das complexidades que o Spring puro costuma ter para subida de uma aplicação, uma das vantagens utilizados no sistema foi que graças ao spring-boot ja ter embutido um servidor tomcat por padrão, não necessitando se precoupar com essa configuração na aplicação. 

Para realização do processo de autenticação via JWT foi utilizado o pacote spring-boot-starter-security, juntamente com os artefatos jjwt-api, jjwt-impl e jjwt-jackson , o objetivo dessas bibliotecas é dar facilidade na criação de pacotes de segurança e geração de tokens jwt. O JWT gerado foi utilizado algoitmo de criptografia HS512 , juntamnete com uma chave privada e com um tempo de expiração do token de 30 minutos. Os valores de chave privada + token de expiração são configuráveis via variável de ambiente utilizando o arquivo application.properties. Para realizar a criptografia senha está sendo utilizado o padrão BCrypt advindo da própria biblioteca do spring-security.

O banco utilizado foi o H2 sendo importado também no arquivo pom.xml como.h2database , nele foi instaciado o banco de dados por meio dos arquivos schema.sql que contém um script de criação do banco e o arquvivo data.sql que já inclui dados no banco previamente criado.

Para comunicação com o banco está sendo utilizado o JPA , com sua configuração sendo simplificada pelo artefado spring-boot-starter-data-jpa do spring-boot , nele é possível configurar como os modelos criados no sistema vão se comunicar com o banco de dados da aplicação.

Para realização dos testes foi utilizado o framework JUnit , a importação das bibliotecas necessárias para realização dos testes , foi feita utilizando o arquivo pom.xml + sprin-boot , com o nome do artefato spring-boot-starter-test, essa biblioteca já traz as configurações básicas para utilização das funcionalidades do JUnit.

Foi utilizado no front-end o Angular 15, por se tratar de uma das versões mais novas existentes, e de fácil implementação e comunicação com sistema rest criado no back-end.

A aplicação está utilizando de um plugin denomiado "frontend-maven-plugin" para gerar um build em conjunto de ambas as aplicações front-end + back-end, esse plugin só necessita de que seja realizado primeiro o build da aplicação e depois ser realizado o processo de subir a aplicação, quando os dois são rodados o front e o back já estão integrados e prontos para serem utilizados, dessa forma facilitar o deploy do serviço para os ambientes produtivos.

Por fim para realização do deploy foi utilizada a ferramente Heroku , a qual tem várias linguagens disponíveis para realização de deploys, e é extremamente fácila para subir a aplicação  sem complexidade, nele foi apenas integrado este github e solicitado a branch de deploy da aplicação. Com isso a aplicação já estava online para uso externo.


