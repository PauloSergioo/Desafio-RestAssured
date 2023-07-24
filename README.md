# DESAFIO: DSMovie Rest Assured - Backend

# Sobre o projeto

### Premissas

- É um sistema que possui um modelo de domínio relativamente simples,
porém abrangente, ou seja, que explore vários tipos de relacionamentos entre as
entidades de negócio (muitos-para-um, muitos-para-muitos, etc.).
- O sistema possibilita a aplicação de vários conhecimentos importantes das
disciplinas de fundamentos.
- O sistema contem as principais funcionalidades que se espera de um
profissional iniciante deve saber construir.


## 

### Visão geral do sistema

Este é um projeto de filmes e avaliações de filmes. 
A visualização dos dados dos filmes é pública (não necessita login), porém as alterações de filmes (inserir, atualizar, deletar)
são permitidas apenas para usuários ADMIN. As avaliações de filmes podem ser registradas por qualquer usuário logado CLIENT ou ADMIN.
A entidade Score armazena uma nota de 0 a 5 (score) que cada usuário deu a cada filme. 
Sempre que um usuário registra uma nota, o sistema calcula a média das notas de todos usuários, 
e armazena essa nota média (score) na entidade Movie, juntamente com a contagem de votos (count).

##

### Respeitando o seguinte Modelo Conceitual:

![image](https://github.com/PauloSergioo/Desafio-RestAssured/assets/88008441/9e8eceec-d3e3-4352-9e5c-284bc056894d)


### Testes Realizados:

MovieContollerRA:

- findAllShouldReturnOkWhenMovieNoArgumentsGiven
- findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty
- findByIdShouldReturnMovieWhenIdExists
- findByIdShouldReturnNotFoundWhenIdDoesNotExist
- insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle
- insertShouldReturnForbiddenWhenClientLogged
- insertShouldReturnUnauthorizedWhenInvalidToken

ScoreContollerRA:

- saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist
- saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId
- saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero


# Educador

[DevSuperior - Escola de programação](https://devsuperior.com.br/)

[![DevSuperior no Instagram](https://raw.githubusercontent.com/devsuperior/bds-assets/main/ds/ig-icon.png)](https://instagram.com/devsuperior.ig) ![DevSuperior no Youtube](https://raw.githubusercontent.com/devsuperior/bds-assets/main/ds/yt-icon.png)
