package edu.projetointegrador.controlefinanceiro.controller;

import com.google.gson.Gson;
import edu.projetointegrador.controlefinanceiro.dao.TransacaoDAO;
import edu.projetointegrador.controlefinanceiro.connector.PostgresConnector;
import edu.projetointegrador.controlefinanceiro.model.Transacao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/transacoes")
public class TransacaoServlet extends HttpServlet {

    private TransacaoDAO transacaoDAO;
    private Gson gson = new Gson();

    @Override
    public void init() {
        transacaoDAO = new TransacaoDAO(PostgresConnector.getInstance());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuarioId") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(Map.of("erro", "Usuário não autenticado")));
            return;
        }

        int usuarioId = (int) session.getAttribute("usuarioId");

        try (BufferedReader reader = req.getReader()) {
            Transacao transacao = gson.fromJson(reader, Transacao.class);
            transacao.setUsuarioId(usuarioId);

            transacaoDAO.salvar(transacao);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(gson.toJson(Map.of("mensagem", "Transação salva com sucesso!")));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(Map.of("erro", "Erro ao salvar transação: " + e.getMessage())));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuarioId") == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write(gson.toJson(Map.of("erro", "Usuário não autenticado")));
            return;
        }

        long usuarioId = (long) session.getAttribute("usuarioId");

        try {
            List<Transacao> transacoes = transacaoDAO.listarPorUsuario(usuarioId);
            String json = gson.toJson(transacoes);
            resp.getWriter().write(json);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(gson.toJson(Map.of("erro", "Erro ao buscar transações: " + e.getMessage())));
        }
    }
}
