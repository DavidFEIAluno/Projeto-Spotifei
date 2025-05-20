/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Musica;
import model.Playlist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    
    public boolean criarPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlists (usuario_id, nome) VALUES (?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, playlist.getUsuarioId());
            stmt.setString(2, playlist.getNome());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        playlist.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar playlist: " + e.getMessage());
        }
        return false;
    }

    public boolean adicionarMusica(int playlistId, int musicaId, int ordem) {
        String sql = "INSERT INTO playlist_musicas (playlist_id, musica_id, ordem) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            stmt.setInt(3, ordem);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar música à playlist: " + e.getMessage());
            return false;
        }
    }

    public boolean removerMusica(int playlistId, int musicaId) {
        String sql = "DELETE FROM playlist_musicas WHERE playlist_id = ? AND musica_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover música da playlist: " + e.getMessage());
            return false;
        }
    }

    public List<Playlist> listarPlaylistsPorUsuario(int usuarioId) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT id, nome, usuario_id, data_criacao FROM playlists WHERE usuario_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setId(rs.getInt("id"));
                playlist.setNome(rs.getString("nome"));
                playlist.setUsuarioId(rs.getInt("usuario_id"));
                Timestamp timestamp = rs.getTimestamp("data_criacao");
            if (timestamp != null) {
                playlist.setDataCriacao(timestamp.toLocalDateTime());
            }
                
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar playlists do usuário: " + e.getMessage());
        }
        return playlists;
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        List<Musica> musicas = new ArrayList<>();
        String sql = """
            SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                   a.nome AS artista_nome, pm.ordem
            FROM playlist_musicas pm
            JOIN musicas m ON pm.musica_id = m.id
            JOIN artistas a ON m.artista_id = a.id
            WHERE pm.playlist_id = ?
            ORDER BY pm.ordem
            """;
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Musica musica = new Musica();
                musica.setId(rs.getInt("id"));
                musica.setTitulo(rs.getString("titulo"));
                musica.setGenero(rs.getString("genero"));
                musica.setDuracaoSegundos(rs.getInt("duracao"));
                Date data = rs.getDate("data_lancamento");
            if (data != null) {
                musica.setDataLancamento(data.toLocalDate());
            }
                musica.setArtistaid(rs.getInt("artista_id"));
                
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas da playlist: " + e.getMessage());
        }
        return musicas;
    }

    public boolean excluirPlaylist(int playlistId) {
        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false); // Inicia transação
            
            // Primeiro remove as músicas da playlist
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM playlist_musicas WHERE playlist_id = ?")) {
                stmt.setInt(1, playlistId);
                stmt.executeUpdate();
            }
            
            // Depois remove a playlist
            try (PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM playlists WHERE id = ?")) {
                stmt.setInt(1, playlistId);
                int affectedRows = stmt.executeUpdate();
                
                if (affectedRows > 0) {
                    conn.commit();
                    return true;
                }
            }
            
            conn.rollback();
            return false;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Erro ao fazer rollback: " + ex.getMessage());
                }
            }
            System.err.println("Erro ao excluir playlist: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }
}

