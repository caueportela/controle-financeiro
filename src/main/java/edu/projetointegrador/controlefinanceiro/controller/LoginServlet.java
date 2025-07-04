package edu.projetointegrador.controlefinanceiro.controller;

import edu.projetointegrador.controlefinanceiro.connector.PostgresConnector;
import edu.projetointegrador.controlefinanceiro.dao.UsuarioDAO;
import edu.projetointegrador.controlefinanceiro.model.Usuario;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static class LoginRequest {
        private String email;
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("Servlet está funcionando!");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        try (BufferedReader reader = request.getReader()) {
            LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);

            String email = loginRequest.getEmail();
            String senha = loginRequest.getSenha();

            PostgresConnector connector = PostgresConnector.getInstance();
            UsuarioDAO dao = new UsuarioDAO(connector);

            Usuario usuario = dao.findByEmail(email);
            boolean autenticado = usuario != null && usuario.getSenha().equals(senha);

            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("sucesso", autenticado);

            if (autenticado) {
                HttpSession session = request.getSession(true);
                session.setAttribute("usuarioId", usuario.getId()); // ESSENCIAL para controlar sessão e autorizar acessos
                session.setAttribute("usuarioLogado", email);
            }

            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            response.setStatus(500);

            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("sucesso", false);
            errorResponse.addProperty("erro", "Erro interno: " + e.getMessage());

            response.getWriter().write(gson.toJson(errorResponse));
        }
    }
}
