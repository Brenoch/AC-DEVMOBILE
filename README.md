# Projeto CRUD de Produtos (Android + Spring Boot)

Este repositório contém uma solução completa de CRUD (Create, Read, Update, Delete) composta por um aplicativo móvel Android nativo e uma API backend em Spring Boot.

## 🚀 Estrutura do Projeto

O repositório está dividido em duas partes principais:
1.  **app/**: Aplicativo Android desenvolvido em Kotlin.
2.  **api-spring/**: API REST desenvolvida em Java com Spring Boot e banco de dados H2 (em memória).

---

## 🛠️ Tecnologias Utilizadas

### Android (Mobile)
- **Linguagem:** Kotlin
- **SDK:** Compile SDK 35 / Min SDK 24
- **Network:** Retrofit 2.9.0 & Gson
- **UI:** RecyclerView, Material Design, XML Layouts
- **Arquitetura:** MVC (Model-View-Controller) simples

### Spring Boot (Backend)
- **Linguagem:** Java 17
- **Framework:** Spring Boot 3.2.2
- **Persistência:** Spring Data JPA
- **Banco de Dados:** H2 Database (In-memory)
- **Dependências:** Spring Web, Spring Data JPA, H2 Runtime

---

## 💻 Como Executar

### 1. Rodando a API (Backend)
1.  Navegue até a pasta `api-spring`.
2.  Certifique-se de ter o JDK 17 instalado.
3.  Execute via terminal:
    ```bash
    mvnw spring-boot:run
    ```
    Ou abra a pasta no IntelliJ IDEA, aguarde a importação do Maven e execute a classe `ProdutoApplication.java`.
4.  A API estará disponível em: `http://localhost:8080/produtos`

### 2. Rodando o Aplicativo (Android)
1.  Abra a pasta raiz no Android Studio.
2.  **Se usar o Emulador:** A URL já está configurada como `http://10.0.2.2:8080/`.
3.  **Se usar Celular Real:** 
    - Conecte via USB.
    - No terminal, execute: `adb reverse tcp:8080 tcp:8080`.
    - Certifique-se que o `RetrofitClient.kt` aponta para `http://127.0.0.1:8080/`.
4.  Clique em **Run**.

---

## 📸 Demonstração

### Aplicativo Android
O app permite inserir o nome do produto, salvar, atualizar dados existentes via ID, deletar e listar sob demanda.

![App Screenshot](screenshots/app_screenshot.png)

### API Backend (Spring Boot)
A API gerencia a persistência dos dados e fornece os endpoints REST para o aplicativo.

![API Screenshot](screenshots/api_screenshot.png)

### Resposta JSON (Exemplo)
Exemplo de resposta em `GET /produtos`:
```json
[
  { "id": 2, "nome": "salada" },
  { "id": 3, "nome": "pizza" },
  { "id": 4, "nome": "hamburguer" },
  { "id": 5, "nome": "caviar" }
]
```

---

## 🔗 Endpoints da API
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/produtos` | Lista todos os produtos |
| `POST` | `/produtos` | Cria um novo produto |
| `PUT` | `/produtos/{id}` | Atualiza um produto existente |
| `DELETE` | `/produtos/{id}` | Remove um produto do banco |


