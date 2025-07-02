package edu.projetointegrador.controlefinanceiro.controller;

import edu.projetointegrador.controlefinanceiro.connector.PostgresConnector;
import edu.projetointegrador.controlefinanceiro.dao.UsuarioDAO;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("Servlet está funcionando!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder sb = new StringBuilder();

        // Lê o JSON do corpo da requisição com try-with-resources para fechar o reader automaticamente
        try (BufferedReader reader = request.getReader()) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                sb.append(linha);
            }
        }

        JSONObject resposta = new JSONObject();
        response.setContentType("application/json");

        try {
            JSONObject json = new JSONObject(sb.toString());
            String email = json.getString("email");
            String senha = json.getString("senha");

            PostgresConnector connector = PostgresConnector.getInstance();
            UsuarioDAO dao = new UsuarioDAO(connector);

            boolean autenticado = dao.autenticar(email, senha);
            resposta.put("sucesso", autenticado);

        } catch (Exception e) {
            response.setStatus(500);
            resposta.put("erro", "Erro interno: " + e.getMessage());
        }

        response.getWriter().write(resposta.toString());
    }
}
