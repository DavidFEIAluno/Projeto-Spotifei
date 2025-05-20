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

public class PlaylistMusicaDAO {

    public boolean adicionarMusicaPlaylist(int playlistId, int musicaId, int ordem) {
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

    public boolean removerMusicaPlaylist(int playlistId, int musicaId) {
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

    public List<Musica> listarMusicasPlaylist(int playlistId) {
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
                
                musica.setArtistanome(rs.getString("artista_nome"));
                musica.setOrdemNaPlaylist(rs.getInt("ordem"));
                
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas da playlist: " + e.getMessage());
        }
        return musicas;
    }

    public boolean verificarMusicaNaPlaylist(int playlistId, int musicaId) {
        String sql = "SELECT 1 FROM playlist_musicas WHERE playlist_id = ? AND musica_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar música na playlist: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarOrdemMusica(int playlistId, int musicaId, int novaOrdem) {
        String sql = "UPDATE playlist_musicas SET ordem = ? WHERE playlist_id = ? AND musica_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, novaOrdem);
            stmt.setInt(2, playlistId);
            stmt.setInt(3, musicaId);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar ordem da música: " + e.getMessage());
            return false;
        }
    }

    public int contarMusicasNaPlaylist(int playlistId) {
        String sql = "SELECT COUNT(*) AS total FROM playlist_musicas WHERE playlist_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar músicas na playlist: " + e.getMessage());
        }
        return 0;
    }
}
