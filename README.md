Famify

O Famify é uma aplicação desenvolvida para facilitar a identificação de usuários com interesses em jogos semelhantes, utilizando informações das bibliotecas de jogos da plataforma Steam.
A aplicação permite cadastrar usuários, importar automaticamente suas bibliotecas por meio da API da Steam e gerenciar listas de desejos, nas quais cada usuário pode adicionar os jogos que gostaria de possuir.
Com essas informações, o Famify utiliza um algoritmo de compatibilidade para identificar outros usuários que possuem os jogos desejados, apresentando recomendações ordenadas pelo percentual de compatibilidade.
O objetivo é auxiliar na descoberta de possíveis integrantes para grupos de compartilhamento de jogos, respeitando as regras da plataforma Steam.

As principais funcionalidades são:
Gerenciamento de usuários: cadastro e consulta de usuários, com verificação de nomes e Steam IDs duplicados.
Integração com a Steam: comunicação com a API oficial da Steam para consultar os jogos pertencentes a cada usuário.
Gerenciamento de bibliotecas: criação de bibliotecas vinculadas aos usuários e armazenamento dos jogos importados.
Gerenciamento de listas de desejos: criação automática de listas vazias, permitindo adicionar e remover jogos manualmente.
Validação de jogos: prevenção de jogos duplicados nas listas de desejos e bloqueio da inclusão de jogos que o usuário já possui.
Algoritmo de compatibilidade: comparação entre os jogos desejados por um usuário e as bibliotecas dos demais usuários cadastrados.
Sistema de recomendações: classificação dos usuários de acordo com a porcentagem de jogos desejados encontrados em suas bibliotecas.

Tecnologias Utilizadas:
Java 21
SpringBoot
Spring Data JPA
MySql
SteamWeb API
Postman

Para a utilização do sistema é preciso:
Clonar o repositório e configurar o banco de dados,
Possuir uma chave de API da steam e defini-la no codespace secrets,
Iniciar a aplicação e testar os EndPoints

Feito Por:
Felipe B. Bertoldo,
Felipe M. Torini,
Guilherme dos Santos,
Luiz Fernando J. Trovo,
Mauricio G. Constante Neto,
Nicolly M. de Sene
