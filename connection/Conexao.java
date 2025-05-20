package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {
    private static final Logger logger = Logger.getLogger(Conexao.class.getName());
    
    // Configurações de conexão (idealmente viriam de um arquivo de configuração)
    private static final String URL = "jdbc:postgresql://localhost:5433/spotifei";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "david";
    
    // Bloco estático para registro do driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
            logger.log(Level.INFO, "Driver PostgreSQL registrado com sucesso");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Driver PostgreSQL não encontrado! Adicione o JDBC ao classpath.", e);
            throw new ExceptionInInitializerError("Driver PostgreSQL não encontrado");
        }
    }

    // Padrão Singleton para gerenciar a conexão
    private static Connection conexao;
    
    public static Connection getConexao() throws SQLException {
        if (conexao == null || conexao.isClosed()) {
            try {
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                conexao.setAutoCommit(false); // Importante para controle transacional
                logger.log(Level.INFO, "Conexão estabelecida com sucesso");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Falha ao estabelecer conexão com o banco de dados", e);
                throw e;
            }
        }
        return conexao;
    }

    public static void fecharConexao() {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    conexao.close();
                    logger.log(Level.INFO, "Conexão fechada com sucesso");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Erro ao fechar conexão", e);
            } finally {
                conexao = null;
            }
        }
    }
    
    // Métodos para controle transacional
    public static void commit() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.commit();
            logger.log(Level.FINE, "Commit realizado");
        }
    }
    
    public static void rollback() {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    conexao.rollback();
                    logger.log(Level.FINE, "Rollback realizado");
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Erro ao realizar rollback", e);
            }
        }
    }
}

