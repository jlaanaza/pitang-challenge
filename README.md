# pitang-challenge

ESTÓRIAS DE USUÁRIO

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

Estória de Usuário 6: Autenticação Requerida para Gerenciamento de Carros e Usuários

Como um usuário autenticado, desejo que todas as operações de gerenciamento de carros e usuários (listar, cadastrar, buscar, remover e atualizar) exijam autenticação via token JWT.
Critérios de Aceitação:
Todas as rotas relacionadas a carros e usuários (exceto /api/signin) devem exigir que o token de acesso JWT seja enviado no cabeçalho Authorization.
Em caso de token não enviado ou token expirado, deve retornar um erro com a mensagem "Unauthorized" ou "Unauthorized - invalid session".
