<h1 align="center"> Task List API </h1>

<p align="center">
<img src="https://img.shields.io/static/v1?label=GitHub Profile:&message=@Mr-Sena&color=8257E5&labelColor=000000" alt="@Mr-Sena">
<img src="https://img.shields.io/static/v1?label=Tipo&message=Desafio&color=8257E5&labelColor=000000" alt="Desafio">
</p>


A API permite gerenciar atividade(s) para uma determinada tarefa; através de um sistema CRUD que é exposto para o BackEnd. O objetivo é propor realizar a entrega para o [desafio](https://github.com/simplify-tec/desafio-junior-backend-simplify).

## Tecnologias:
- Spring Boot;
- SpringDoc OpenAPI;
- Spring Data JPA, (Jakarta API).
- Spring Web;
- MySQL Database;
- Lombok;
- Banco em memória H2;
- Docker container.


## Práticas importantes: 
 - Filtro para verificar a identidade de usuário.
 - API REST
 - Injeção de Dependências
 - Tratamento de resposta de erro; 
 - Geração automática do Swagger com a OpenAPI 3.
 - Test as a Componente (TaaC).
               
               
## Como executar: 
 - Baixar e instalar o Docker.
 - Acessar o diretório raiz do repositório - [task-simplify-api].
 - Enviar o comando: 
```
docker compose up
```
<br>

 - Encerrar o aplicativo:
```
docker compose down
```

<BR>
<BR>

## API Endpoints

- Criar Tarefa
```
http POST :8080/tasks


Body (json):  
{
    "nome": "Buy Store", 
    "descricao": "Compras para o mercado.",
    "prioridade": "2",
    "realizado": false
}
```

- Lista de tarefas:
```
http GET :8080/tasks
```

- Atualizar task
```
http PUT :8080/tasks/1

Body (json):
{
    "nome": "Wear store",
    "descricao": "Roupas preparadas para os momentos decisívos.",
    "realizado": false,
    "prioridade": 2
}
```

- Excluir task:
```
http DELETE :8080/tasks/2
```

<br>

- Lista de usuários:
```
http GET :8080/users
```
- Cadastrar usuário:
```
http POST :8080/users


Body (json):
{
    "username": "7oao",
    "password": "client-pwd",
    "name": "Joao"
}
```

- Verificar cadastro de usuário:
```
http POST :8080/auth


Authorization - Basic Auth
Username: <username>
Password:<password>
```

<br>

 - Doc OpenAPI: 
```
http GET :8080/swagger-ui.html
```

 - URL - Acesso interno:
```
http://localhost:8080/swagger-ui.html
```
