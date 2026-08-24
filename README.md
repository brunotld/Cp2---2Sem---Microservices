# 🎬 Cinema API - Check Point 1

API RESTful para gerenciamento de filmes e salas de cinema, desenvolvida com
Spring Boot, Spring Data JPA e MySQL, empacotada com Docker.

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL 8
- Swagger / OpenAPI (springdoc)
- Docker

---

## ⚙️ Profiles

A aplicação possui dois profiles de execução:

| Profile   | Uso                          | Banco / Tabelas                                  |
|-----------|-------------------------------|---------------------------------------------------|
| `default` | Desenvolvimento local          | Criados automaticamente pelo Hibernate (`update`) |
| `prd`     | Produção                       | **Não** são criados automaticamente (`ddl-auto=none`). Devem existir previamente — ver `database/schema.sql`. |

O profile é definido pela variável de ambiente `SPRING_PROFILES_ACTIVE`.

---

## 🔐 Variáveis de ambiente

| Variável                | Descrição                          | Exemplo       |
|--------------------------|-------------------------------------|---------------|
| `SPRING_PROFILES_ACTIVE` | Profile ativo (`default` ou `prd`) | `prd`         |
| `DB_SERVER_URL`          | Host do banco de dados             | `localhost`   |
| `DB_SERVER_PORT`         | Porta do banco de dados            | `3306`        |
| `DB_SCHEMA`              | Nome do schema                     | `cinema_db`   |
| `DB_USER`                | Usuário do banco de dados          | `root`        |
| `DB_PWD`                 | Senha do banco de dados            | `root`        |

> No profile `default`, `DB_SERVER_URL`, `DB_SERVER_PORT`, `DB_SCHEMA`, `DB_USER`
> e `DB_PWD` têm valores padrão (`localhost`, `3306`, `cinema_db`, `root`, `root`)
> e podem ser omitidos. No profile `prd` **todas** são obrigatórias.

---

## ▶️ Executando localmente (sem Docker)

### 1. Suba um MySQL local

```bash
docker run -d \
  --name mysql-cinema \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=cinema_db \
  -p 3306:3306 \
  mysql:8.0
```

### 2. Rode a aplicação (profile default)

```bash
./mvnw spring-boot:run
```

Acesse em: `http://localhost:8080`

---

## 🐳 Executando com Docker

### 1. Build da imagem

Na raiz do projeto:

```bash
docker build -t cinema-api:1.0 .
```

### 2. Rodar com o profile `default` (desenvolvimento)

```bash
docker run -d \
  --name cinema-api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=default \
  -e DB_SERVER_URL=host.docker.internal \
  -e DB_SERVER_PORT=3306 \
  -e DB_SCHEMA=cinema_db \
  -e DB_USER=root \
  -e DB_PWD=root \
  cinema-api:1.0
```

> `host.docker.internal` permite que o container acesse um banco rodando na
> máquina host. Em Linux, pode ser necessário adicionar
> `--add-host=host.docker.internal:host-gateway` ao comando `docker run`.

### 3. Rodar com o profile `prd` (produção)

Antes de iniciar, crie o banco e as tabelas (elas **não** são criadas
automaticamente nesse profile) usando o script `database/schema.sql`:

```bash
mysql -h <host> -P <porta> -u <usuario> -p < database/schema.sql
```

Depois, inicie o container:

```bash
docker run -d \
  --name cinema-api-prd \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_SERVER_URL=<host_do_banco> \
  -e DB_SERVER_PORT=3306 \
  -e DB_SCHEMA=cinema_db \
  -e DB_USER=<usuario> \
  -e DB_PWD=<senha> \
  cinema-api:1.0
```

---

## 📥 Executando a imagem publicada no Docker Hub

### 1. Baixar a imagem

```bash
docker pull SEU_USUARIO_DOCKERHUB/cinema-api:1.0
```

### 2. Executar o container

```bash
docker run -d \
  --name cinema-api \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prd \
  -e DB_SERVER_URL=<host_do_banco> \
  -e DB_SERVER_PORT=3306 \
  -e DB_SCHEMA=cinema_db \
  -e DB_USER=<usuario> \
  -e DB_PWD=<senha> \
  SEU_USUARIO_DOCKERHUB/cinema-api:1.0
```

A aplicação ficará disponível em `http://localhost:8080`.

---

## 📖 Swagger / OpenAPI

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## 📋 Endpoints

### Filmes

| Método | Rota          | Descrição      |
|--------|---------------|----------------|
| GET    | /filmes       | Lista todos    |
| GET    | /filmes/{id}  | Busca por ID   |
| POST   | /filmes       | Cria filme     |
| PUT    | /filmes/{id}  | Atualiza filme |
| DELETE | /filmes/{id}  | Remove filme   |

### Salas

| Método | Rota         | Descrição     |
|--------|--------------|---------------|
| GET    | /salas       | Lista todas   |
| GET    | /salas/{id}  | Busca por ID  |
| POST   | /salas       | Cria sala     |
| PUT    | /salas/{id}  | Atualiza sala |
| DELETE | /salas/{id}  | Remove sala   |

---

## 📦 Docker — comandos úteis

```bash
# Listar containers em execução
docker ps

# Ver logs
docker logs -f cinema-api

# Parar o container
docker stop cinema-api

# Remover o container
docker rm cinema-api

# Remover a imagem
docker rmi cinema-api:1.0
```

---

## 🔒 Segurança

Não versione credenciais reais no repositório. Utilize variáveis de ambiente
(como mostrado acima) para fornecer usuário, senha e dados de conexão do
banco em tempo de execução.
