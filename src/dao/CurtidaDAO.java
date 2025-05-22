/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Curtida;
import connection.Conexao;
import model.Musica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivald
 * Acesso ao banco de dados das Curtidas e devolve 
 * a informação para os controllers que requisitam pelo por essas curtidas, como o HistoricoController, UsuarioController 
 * e o CurtidaController, onde utilizam para buscar as musicas que foram curtidas pelo usuario
 */

public class CurtidaDAO {
    private Connection conn;
    
    public CurtidaDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Object[]> listarTodasAcoesFormatadas(int usuarioId) {
    String sql = """
        SELECT m.titulo, a.nome, 
               CASE WHEN c.acao THEN 'Curtiu' ELSE 'Descurtiu' END as acao, 
               c.data_hora
        FROM curtidas c
        JOIN musicas m ON c.musica_id = m.id
        JOIN artistas a ON m.artista_id = a.id
        WHERE c.usuario_id = ?
        ORDER BY c.data_hora DESC
        """;

    List<Object[]> resultados = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Object[] linha = new Object[4];
            linha[0] = rs.getString("titulo");      // Música
            linha[1] = rs.getString("nome");        // Artista
            linha[2] = rs.getString("acao");        // Ação
            linha[3] = rs.getTimestamp("data_hora");// Data
            resultados.add(linha);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar ações formatadas: " + e.getMessage());
    }
    return resultados;
}
    public List<Musica> listarPorAcao(int usuarioId, boolean acao) throws SQLException {
    String sql = """
        SELECT m.* 
        FROM curtidas c
        JOIN musicas m ON c.musica_id = m.id
        WHERE c.usuario_id = ? AND c.acao = ?
        ORDER BY c.data_hora DESC
        """;

    List<Musica> musicas = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        stmt.setBoolean(2, acao);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Musica musica = new Musica();
            musica.setId(rs.getInt("id"));
            musica.setTitulo(rs.getString("titulo"));
            musica.setArtistaid(rs.getInt("artista_id"));
            musica.setGenero(rs.getString("genero"));
            musicas.add(musica);
        }
    }
    return musicas;
    }
        public boolean registrarAcao(int usuarioId, int musicaId, boolean acao) {
        String sql = """
            INSERT INTO curtidas (usuario_id, musica_id, acao) 
            VALUES (?, ?, ?)
            ON CONFLICT (usuario_id, musica_id) 
            DO UPDATE SET acao = EXCLUDED.acao, data_hora = CURRENT_TIMESTAMP
            """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.setBoolean(3, acao);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao registrar curtida: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro no rollback: " + ex.getMessage());
            }
            return false;
        }
    }

  public List<Musica> listarPorAcaoComData(int usuarioId, boolean acao) {
    String sql = """
        SELECT m.id, m.titulo, m.artista_id, m.genero, c.data_hora
        FROM curtidas c
        JOIN musicas m ON c.musica_id = m.id
        WHERE c.usuario_id = ? AND c.acao = ?
        ORDER BY c.data_hora DESC
        """;

    List<Musica> musicas = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        stmt.setBoolean(2, acao);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Musica musica = new Musica();
            musica.setId(rs.getInt("id"));
            musica.setTitulo(rs.getString("titulo"));
            musica.setArtistaid(rs.getInt("artista_id"));
            musica.setGenero(rs.getString("genero"));
            musica.setDataLancamento(rs.getTimestamp("data_hora").toLocalDateTime().toLocalDate());
            musicas.add(musica);
            System.out.println("Musica adicionada: " + musica.getTitulo()); // Log para depuração
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar músicas curtidas: " + e.getMessage());
    }
    return musicas;
}
  public List<Curtida> listarCurtidasFormatadas(int usuarioId) {
    String sql = """
        SELECT c.usuario_id, c.musica_id, c.acao, c.data_hora,
               m.titulo as musica_titulo, a.nome as artista_nome
        FROM curtidas c
        JOIN musicas m ON c.musica_id = m.id
        JOIN artistas a ON m.artista_id = a.id
        WHERE c.usuario_id = ?
        ORDER BY c.data_hora DESC
        LIMIT 20
        """;

    List<Curtida> curtidas = new ArrayList<>();
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Curtida curtida = new Curtida();
            curtida.setUsuarioId(rs.getInt("usuario_id"));
            curtida.setMusicaId(rs.getInt("musica_id"));
            curtida.setAcao(rs.getBoolean("acao"));
            curtida.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
            
            // Adiciona informações extras diretamente no objeto
            curtida.setMusicaTitulo(rs.getString("musica_titulo"));
            curtida.setArtistaNome(rs.getString("artista_nome"));
            
            curtidas.add(curtida);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao listar curtidas formatadas: " + e.getMessage());
        e.printStackTrace();
    }
    return curtidas;
    
    
}
}