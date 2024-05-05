
# Aplicação BackEnd com Java, Spring Boot e Spring Security
## Descrição
Esta é uma aplicação Java desenvolvida com Spring Boot e Spring Security para gerenciamento de imagens. A aplicação fornece endpoints para postar imagens, buscar imagens por diferentes critérios (tags e nome), criar usuários e autenticar usuários.

# Funcionalidades
- Postar Imagem: Endpoint para enviar e armazenar uma imagem no servidor.
- Buscar Imagem: Endpoint para recuperar uma imagem específica com base no seu ID.
- Buscar Imagem por Tags e Nome: Endpoint para pesquisar imagens com base em tags e/ou nome.
- Criar Usuário: Endpoint para criar um novo usuário na aplicação.
- Autenticar Usuário: Endpoint para autenticar um usuário e gerar um token de acesso.
# Tecnologias Utilizadas
- Spring Boot: Framework para criação de aplicativos Java baseados em Spring.
- Spring Security: Framework de autenticação e autorização para aplicativos Spring.
- Outras Dependências do Spring: Spring Data JPA para persistência de dados, Spring Web para criação de APIs RESTful, entre outros.
- Banco de Dados: Neste projeto, o banco de dados PostgreSQL foi configurado utilizando o Docker para facilitar o ambiente de desenvolvimento e realizar 

# Deploy da Aplicação com Docker
Neste projeto, o Docker foi utilizado para realizar o deploy da aplicação, e redes foram utilizadas para conectar os contêineres. Abaixo está uma visão geral dos componentes Docker utilizados:

# Componentes Docker Utilizados:
## Contêiner da Aplicação Spring Boot:
Um contêiner Docker foi criado para hospedar a aplicação Spring Boot.
Este contêiner executa a aplicação e expõe as portas necessárias para a comunicação com outros contêineres ou clientes externos.
## Contêiner do Banco de Dados PostgreSQL:
Um contêiner Docker foi configurado para hospedar o banco de dados PostgreSQL.
Este contêiner armazena os dados da aplicação e é acessado pela aplicação Spring Boot para operações de leitura e escrita.
## Rede Docker para Conexão dos Contêineres:
Uma rede Docker foi criada para conectar os contêineres da aplicação e do banco de dados.
# Esta rede permite a comunicação segura e eficiente entre a aplicação Spring Boot e o banco de dados PostgreSQL.
Notas Adicionais:
O uso do Docker simplifica o processo de deploy da aplicação, garantindo um ambiente consistente e isolado para a execução dos componentes.
As redes Docker permitem que os contêineres se comuniquem entre si de forma eficiente, mantendo a segurança e isolamento necessários.
