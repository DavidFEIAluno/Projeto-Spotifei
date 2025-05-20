/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Musica;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAO {
    
    public boolean inserirMusica(Musica musica) {
        String sql = "INSERT INTO musicas (titulo, artista_id, genero, duracao, data_lancamento) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, musica.getTitulo());
            stmt.setInt(2, musica.getArtistaid());
            stmt.setString(3, musica.getGenero());
            stmt.setInt(4, musica.getDuracaoSegundos());
            stmt.setDate(5, musica.getDataLancamento() != null ? 
                         Date.valueOf(musica.getDataLancamento()) : null);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        musica.setId(generatedKeys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir música: " + e.getMessage());
        }
        return false;
    }

    public List<Musica> buscarPorFiltro(String termo, String tipoFiltro) {
        List<Musica> musicas = new ArrayList<>();
        String sql = "";
        
        switch(tipoFiltro.toLowerCase()) {
            case "nome":
                sql = """
                    SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                           a.nome AS artista_nome, a.id AS artista_id
                    FROM musicas m
                    JOIN artistas a ON m.artista_id = a.id
                    WHERE m.titulo ILIKE ?
                    """;
                break;
            case "artista":
                sql = """
                    SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                           a.nome AS artista_nome, a.id AS artista_id
                    FROM musicas m
                    JOIN artistas a ON m.artista_id = a.id
                    WHERE a.nome ILIKE ?
                    """;
                break;
            case "genero":
                sql = """
                    SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                           a.nome AS artista_nome, a.id AS artista_id
                    FROM musicas m
                    JOIN artistas a ON m.artista_id = a.id
                    WHERE m.genero ILIKE ?
                    """;
                break;
            default:
                return musicas;
        }
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + termo + "%");
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
                musica.setArtistanome(rs.getString("artista_nome"));
                
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar músicas por filtro: " + e.getMessage());
        }
        return musicas;
    }

    public Musica buscarPorId(int id) {
        String sql = """
            SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                   a.nome AS artista_nome, a.id AS artista_id
            FROM musicas m
            JOIN artistas a ON m.artista_id = a.id
            WHERE m.id = ?
            """;
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
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
                musica.setArtistanome(rs.getString("artista_nome"));
                
                return musica;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar música por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Musica> listarTodas() {
        List<Musica> musicas = new ArrayList<>();
        String sql = """
            SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                   a.nome AS artista_nome, a.id AS artista_id
            FROM musicas m
            JOIN artistas a ON m.artista_id = a.id
            ORDER BY m.titulo
            """;
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
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
                musica.setArtistanome(rs.getString("artista_nome"));
                
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todas as músicas: " + e.getMessage());
        }
        return musicas;
    }

    public List<Musica> listarCurtidasPorUsuario(int usuarioId, boolean acao) {
        List<Musica> musicas = new ArrayList<>();
        String sql = """
            SELECT m.id, m.titulo, m.genero, m.duracao, m.data_lancamento, 
                   a.nome AS artista_nome, a.id AS artista_id
            FROM curtidas c
            JOIN musicas m ON c.musica_id = m.id
            JOIN artistas a ON m.artista_id = a.id
            WHERE c.usuario_id = ? AND c.acao = ?
            ORDER BY c.data_hora DESC
            """;
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, usuarioId);
            stmt.setBoolean(2, acao);
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
                musica.setArtistanome(rs.getString("artista_nome"));
                
                musicas.add(musica);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas curtidas: " + e.getMessage());
        }
        return musicas;
    }
}