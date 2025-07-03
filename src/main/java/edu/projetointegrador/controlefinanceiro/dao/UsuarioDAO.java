package edu.projetointegrador.controlefinanceiro.dao;

import edu.projetointegrador.controlefinanceiro.connector.PostgresConnector;
import edu.projetointegrador.controlefinanceiro.model.Usuario;

import java.sql.*;

public class UsuarioDAO {
    private PostgresConnector connector;

    public UsuarioDAO(PostgresConnector connector) {
        this.connector = connector;
    }

    // CREATE - registrar novo usuário
    public void create(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha_hash) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connector.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();
        }
    }

    // READ - buscar usuário pelo email (para login)
    public Usuario findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (PreparedStatement stmt = connector.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha_hash"));
                    return usuario;
                }
            }
        }
        return null;
    }

    // AUTENTICAR
    public boolean autenticar(String email, String senha) throws SQLException {
        Usuario usuario = findByEmail(email);
        if (usuario != null) {
            return usuario.getSenha().equals(senha);
        }
        return false;
    }

    // DELETE - deletar usuário pelo ID (opcional)
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement stmt = connector.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
