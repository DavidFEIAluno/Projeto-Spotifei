/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Playlist;
import model.Musica;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ivald
 * Acesso ao banco de dados dos Artistas e devolve 
 * a informação para os controllers que requisitam pelas playlists
 * fundamental para o funcionamento da TelaPlaylist
 */

public class PlaylistDAO {
    private Connection conn;
    
    public PlaylistDAO(Connection conn) {
        this.conn = conn;
    }
    
    // CRUD Básico
    public int criarPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlists (usuario_id, nome, data_criacao) VALUES (?, ?, current_timestamp)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, playlist.getUsuarioId());
            stmt.setString(2, playlist.getNome());
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                conn.commit(); // commit manual
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                    return rs.getInt(1); // retorna o id gerado
                    }
                }
            } else {
                conn.rollback();
            }
        } catch (SQLException e) {
            try {
                conn.rollback(); // rollback em caso de erro
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return -1;
    }


       public boolean atualizarOrdemMusica(int playlistId, int musicaId, int novaOrdem) throws SQLException {
    String sql = "UPDATE playlist_musicas SET ordem = ? WHERE playlist_id = ? AND musica_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, novaOrdem);
        stmt.setInt(2, playlistId);
        stmt.setInt(3, musicaId);
        return stmt.executeUpdate() > 0;
    }
}
    
    public boolean atualizarPlaylist(Playlist playlist) throws SQLException {
        String sql = "UPDATE playlists SET nome = ? WHERE id = ? AND usuario_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playlist.getNome());
            stmt.setInt(2, playlist.getId());
            stmt.setInt(3, playlist.getUsuarioId());
            
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean excluirPlaylist(int playlistId, int usuarioId) throws SQLException {
    // Iniciar a transação manual
    conn.setAutoCommit(false);

    try {
        
        String sqlMusicas = "DELETE FROM playlist_musicas WHERE playlist_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlMusicas)) {
            stmt.setInt(1, playlistId);
            stmt.executeUpdate();
        }
        String sqlPlaylist = "DELETE FROM playlists WHERE id = ? AND usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlPlaylist)) {
                stmt.setInt(1, playlistId);
                stmt.setInt(2, usuarioId);
                int rowsAffected = stmt.executeUpdate();
            
                // Se a playlist foi excluída, faz o commit e retorna true
                if (rowsAffected > 0) {
                    conn.commit();  // Confirma a transação
                    return true;
                } else {
                    conn.rollback();  // Caso a playlist não tenha sido excluída, faz o rollback
                    return false;
                }
            }
        } catch (SQLException e) {
            // Em caso de erro, faz o rollback
            conn.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Sempre retorna o auto-commit ao normal
            conn.setAutoCommit(true);
        }
    }

    
    // Gerenciamento de Músicas
    public boolean adicionarMusica(int playlistId, int musicaId, int ordem) throws SQLException {
        String sql = "INSERT INTO playlist_musicas (playlist_id, musica_id, ordem) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            stmt.setInt(3, ordem);
            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean removerMusica(int playlistId, int musicaId) throws SQLException {
        String sql = "DELETE FROM playlist_musicas WHERE playlist_id = ? AND musica_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            return stmt.executeUpdate() > 0;
        }
    }
    
    // Consultas
    public List<Playlist> listarPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM playlists WHERE usuario_id = ? ORDER BY data_criacao DESC";
        List<Playlist> playlists = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Playlist p = new Playlist();
                p.setId(rs.getInt("id"));
                p.setUsuarioId(usuarioId);
                p.setNome(rs.getString("nome"));
                p.setDataCriacao(rs.getTimestamp("data_criacao").toLocalDateTime());
                playlists.add(p);
            }
        }
        return playlists;
    }
    
    public List<Musica> listarMusicasDaPlaylist(int playlistId) throws SQLException {
        String sql = """
            SELECT m.* FROM playlist_musicas pm
            JOIN musicas m ON pm.musica_id = m.id
            WHERE pm.playlist_id = ?
            ORDER BY pm.ordem
            """;
            
        List<Musica> musicas = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlistId);
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
}

