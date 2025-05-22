/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Musica;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Historico;

/**
 *
 * @author ivald
 * Acesso ao banco de dados do Historico e devolve 
 * a informação para os controllers que requisitam pelo o historico, é fundamental para o PainelHistorico 
 * e obviamente o armazenamento de dados
 */

public class HistoricoDAO {
public void adicionarHistorico(Historico historico) {
    Connection conn = null;
    try {
        conn = Conexao.getConexao();
        conn.setAutoCommit(false); // Desativa o autocommit
        
        String sql = "INSERT INTO historico_buscas (usuario_id, termo_busca, data_hora, acao) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, historico.getIdUsuario());
            stmt.setString(2, historico.getNomeMusica());
            stmt.setTimestamp(3, Timestamp.valueOf(historico.getHorario()));
            stmt.setString(4, historico.getAcao());
            
            stmt.executeUpdate();
            conn.commit(); // Confirma a transação
            System.out.println("[LOG] Histórico salvo e commitado com sucesso.");
        }
    } catch (Exception e) {
        try {
            if (conn != null) {
                conn.rollback(); // Desfaz em caso de erro
            }
            System.err.println("[ERRO] Falha ao salvar histórico: " + e.getMessage());
        } catch (SQLException ex) {
            System.err.println("[ERRO] Falha no rollback: " + ex.getMessage());
        }
    } finally {
        try {
            if (conn != null) {
                conn.setAutoCommit(true); // Restaura o autocommit
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("[ERRO] Ao fechar conexão: " + e.getMessage());
        }
    }
}


public List<Historico> listarUltimosTermosBuscados(int usuarioId) {
    String sql = """
        SELECT id, usuario_id, termo_busca, data_hora, acao 
        FROM historico_buscas 
        WHERE usuario_id = ? 
        ORDER BY data_hora DESC 
        LIMIT 10
        """;

    List<Historico> historicos = new ArrayList<>();
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Historico historico = new Historico();
            historico.setId(rs.getInt("id"));
            historico.setIdUsuario(rs.getInt("usuario_id"));
            historico.setNomeMusica(rs.getString("termo_busca"));
            historico.setHorario(rs.getTimestamp("data_hora").toLocalDateTime());
            historico.setAcao(rs.getString("acao"));
            historicos.add(historico);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao recuperar histórico de buscas: " + e.getMessage());
        e.printStackTrace();
    }
    return historicos;
}

public void registrarBusca(int usuarioId, String termoBusca) {
    String sql = "INSERT INTO historico_buscas (usuario_id, termo_busca, data_hora, acao) VALUES (?, ?, ?, ?)";

    try (Connection conn = Conexao.getConexao(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId); // Certifique-se que é setInt() e não setString()
        stmt.setString(2, termoBusca);
        stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
        stmt.setString(4, "busca");
        stmt.executeUpdate();
    } catch (Exception e) {
        System.err.println("[ERRO] Falha ao registrar busca: " + e.getMessage());
    }
}




        public List<Musica> listarMusicasBuscadasRecentemente(int usuarioId) {
            String sql = """
            SELECT DISTINCT m.id, m.titulo, a.nome as artista, m.genero, m.artista_id, hb.data_hora
            FROM historico_buscas hb
            JOIN musicas m ON m.titulo ILIKE '%' || hb.termo_busca || '%'
            JOIN artistas a ON m.artista_id = a.id
            WHERE hb.usuario_id = ?
            ORDER BY hb.data_hora DESC
            LIMIT 10
            """;

        List<Musica> musicas = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Musica musica = new Musica();
                musica.setId(rs.getInt("id"));
                musica.setTitulo(rs.getString("titulo"));
                musica.setArtistaid(rs.getInt("artista_id"));
                musica.setGenero(rs.getString("genero"));
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas buscadas: " + e.getMessage());
        }
        return musicas;
    }
}

