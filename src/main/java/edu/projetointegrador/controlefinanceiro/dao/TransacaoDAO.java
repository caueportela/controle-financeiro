package edu.projetointegrador.controlefinanceiro.dao;

import edu.projetointegrador.controlefinanceiro.connector.PostgresConnector;
import edu.projetointegrador.controlefinanceiro.model.Transacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {
    private PostgresConnector connector;

    public TransacaoDAO(PostgresConnector connector) {
        this.connector = connector;
    }

    public void salvar(Transacao transacao) throws SQLException {
        String sql = "INSERT INTO transacao (descricao, valor, categoria, id_usuario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connector.prepareStatement(sql)) {
            stmt.setString(1, transacao.getDescricao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.getCategoria());
            stmt.setLong(4, transacao.getUsuarioId());
            stmt.executeUpdate();
        }
    }

    public List<Transacao> listarPorUsuario(long usuarioId) throws SQLException {
        String sql = "SELECT descricao, valor, categoria FROM transacao WHERE id_usuario = ?";
        List<Transacao> transacoes = new ArrayList<>();

        try (PreparedStatement stmt = connector.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transacao t = new Transacao();
                    t.setDescricao(rs.getString("descricao"));
                    t.setValor(rs.getDouble("valor"));
                    t.setCategoria(rs.getString("categoria"));
                    t.setUsuarioId(usuarioId);
                    transacoes.add(t);
                }
            }
        }
        return transacoes;
    }
}
