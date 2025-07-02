package edu.projetointegrador.controlefinanceiro.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PostgresConnector {
    private Connection conn = null;
    private static PostgresConnector instance = null;

    private PostgresConnector() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/controlador_financeiro", "postgres", "250682");
    }

    // Metodo getInstance, que vai ser usado na aplicação.
    public static PostgresConnector getInstance() {
        if (instance == null) {
            try {
                instance = new PostgresConnector();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    public static void close() {
        if (instance != null) {
            try {
                instance.conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // retorna o preparedStatement, que é um metodo para consultas sql, evita sql injection e é mais rápido para consultas que são executadas várias vezes com parâmetros diferentes.
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.conn.prepareStatement(sql);
    }

}
