
## 🌳 Estrutura de Branches

- `main`: código estável e pronto para produção (🚫 ninguém comita diretamente aqui)
- `develop`: onde as funcionalidades são integradas após revisão
- `feature/nome-da-tarefa`: onde cada pessoa trabalha em uma funcionalidade específica

---

## 🚀 Começando uma nova tarefa

```bash
# Atualize sua branch develop local
git checkout develop
git pull origin develop

# Crie sua branch de tarefa a partir de develop
git checkout -b feature/nome-da-tarefa

# Exemplo:
# git checkout -b feature/cadastro-usuario
```

---

## 💻 Trabalhando na sua branch

```bash
# Após fazer mudanças no código
git add .
git commit -m "Implementa [descrição da tarefa]"

# Envie sua branch para o GitHub
git push origin feature/nome-da-tarefa
```

---

## 🔁 Criando um Pull Request (PR)

1. Vá até o repositório no GitHub.
2. Clique em **"Compare & pull request"** ou vá na aba **Pull Requests**.
3. Selecione:
    - **Base**: `develop`
    - **Compare**: `feature/nome-da-tarefa`
4. Descreva o que foi feito e envie para revisão.
5. Aguarde aprovação e merge.

---

## 🔄 Mantendo sua branch atualizada

Se alguém já tiver dado merge em `develop`, atualize a sua:

```bash
# Atualize develop
git checkout develop
git pull origin develop

# Volte para sua feature branch
git checkout feature/nome-da-tarefa

# Mescle as mudanças da develop na sua branch
git merge develop

# Resolva conflitos, se houver, e continue trabalhando normalmente
```

---

## ✅ Finalizando

Quando a branch `develop` estiver com várias funcionalidades testadas e estável, um responsável faz o merge dela para `main` via Pull Request:

```bash
git checkout main
git pull origin main
git merge develop
git push origin main
```

---

## 📌 Regras importantes

- 🔒 **Nunca comitar direto em `main` ou `develop`**
- ✅ **Sempre trabalhe em branches `feature/*`**
- 🔄 **Atualize sua branch com `develop` com frequência**
- 🧪 **Teste antes de pedir merge**
- 🧠 **Nomeie bem seus commits e branches**

---

👥 Time colaborando com responsabilidade = projeto saudável 🚀


## 📖 Documentação Swagger
`http://localhost:8080/swagger-ui.html`

## BANCO H2
`http://localhost:8080/h2-console/`
