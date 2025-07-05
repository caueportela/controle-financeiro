# 💸 Controle Financeiro Pessoal

Sistema web feito para controlar as despesas mensais de uma pessoa, desenvolvido como parte do Projeto Integrador III-B do curso de Análise e Desenvolvimento de Sistemas.

## 🧩 Tecnologias Utilizadas

- Java 17
- Servlets (Jakarta EE)
- HTML5, CSS3 e JavaScript (vanilla)
- PostgreSQL
- Apache Tomcat
- JDBC
- Gson
- MVC

## 🎯 Funcionalidades

- ✅ Autenticação de usuário (login)
- ✅ Registro de despesas com descrição, valor e categoria
- ✅ Exibição do saldo atualizado
- ✅ Tabela com histórico de transações
- ✅ Botão para zerar todas as despesas
- ✅ Integração front-end e back-end via API REST (fetch)

## 🗃️ Estrutura do Projeto
controle-financeiro/
├── edu.projetointegrador.controlefinanceiro
│ ├── controller/
│ ├── dao/
│ ├── model/
│ └── connector/ 
├── webapp/
└── frontend/
│ ├── index.html
│ ├── login.html
│ ├── style.css
│ └── script.js
└── WEB-INF/
└── web.xml 

##  Deploy com Tomcat

1. Compile o projeto e gere o arquivo `.war`
2. Coloque o `.war` dentro da pasta `webapps/` do Tomcat
3. Inicie o servidor
4. Acesse: http://localhost:8080/controle-financeiro/login.html
