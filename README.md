# 🧪 Portal LACEN - API de Envio de Amostras

Projeto desenvolvido pelo **Squad 2** para a Fundação de Saúde Parreiras Horta (FSPH).  
Esta API gerencia o cadastro, envio e controle de amostras biológicas enviadas pelas prefeituras para o LACEN/SE.

Este projeto é uma API RESTful desenvolvida com **Spring Boot**, voltada para o gerenciamento de **lotes**, **amostras** e **lâminas** em um fluxo laboratorial. O sistema oferece funcionalidades completas, controle de acesso com JWT, verificação de integridade, upload e download de laudos, além de respeitar os princípios da **LGPD**.

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)** – autenticação com token
- **JPA / Hibernate**
- **H2 Database** - Banco de teste em memória
- **MySQL** – banco de dados
- **Swagger UI** – com interface personalizada para documentação
- **Lombok**
- **MapStruct**

---

## 🔐 Autenticação

A autenticação é feita por meio de **token JWT**. Após o login, o token deve ser utilizado nos headers (`Authorization: Bearer <token>`) para acessar endpoints protegidos.

---

## 📦 Funcionalidades Principais

### 🔹 Lotes
- Criar um novo lote
- Lote pode conter **apenas amostras** ou **apenas lâminas**
- Buscar lote por protocolo
- Buscar todos os lotes
- Editar lote (adicionar/remover amostras ou lâminas)
- Atualizar status do lote
- Excluir lote
- Gerar relatório do lote (PDF)

### 🔹 Amostras
- Criar amostras de diferentes tipos (Escorpião, Flebotomíneo, Triatomíneo, etc.)
- Associar e remover amostras de um lote
- Enviar laudo PDF
- Fazer download do laudo
- Atualizar status da amostra
- Verificação de integridade
- Geração automática de protocolo

### 🔹 Lâminas
- Criar lâminas
- Associar e remover lâminas de um lote
- Enviar laudo PDF
- Fazer download do laudo
- Atualizar status da lâmina
- Verificação de integridade
- Geração automática de protocolo

---

## 🔒 Conformidade com a LGPD

O sistema foi projetado seguindo os princípios da **Lei Geral de Proteção de Dados (LGPD)**:

- Dados pessoais sensíveis são armazenados de forma segura
- Somente usuários autenticados podem acessar dados protegidos
- Logs e rastreamento de ações importantes

---

## 📄 Documentação da API

A documentação completa está disponível via Swagger:

> Acesse: `http://localhost:8080/swagger-ui/index.html`

A interface Swagger foi **personalizada** para melhor navegação e visualização dos recursos.

---

## Endpoints Documentados

Link do documento contendo todos os enpoints para utilizar a API:

> Acesse: `https://docs.google.com/document/d/1ttcpvTfUjaORzU-16qTqLAmNPquedhVSLa5vs_SzVDk/edit?tab=t.0`


---

## ▶️ Como executar

### Pré-requisitos:
- Java 17+
- Maven
- MySQL

### Passos:
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
./mvnw spring-boot:run
