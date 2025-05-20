/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Musica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    public void registrarBusca(int usuarioId, String termoBusca) {
        String sql = "INSERT INTO historico_buscas (usuario_id, termo_busca) VALUES (?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, termoBusca);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar busca no histórico: " + e.getMessage());
            // Ou lançar uma exceção customizada
            // throw new DAOException("Falha ao registrar busca", e);
        }
    }

    public List<String> listarUltimosTermosBuscados(int usuarioId) {
        String sql = """
            SELECT termo_busca 
            FROM historico_buscas 
            WHERE usuario_id = ? 
            ORDER BY data_hora DESC 
            LIMIT 10
            """;

        List<String> termos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                termos.add(rs.getString("termo_busca"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao recuperar histórico de buscas: " + e.getMessage());
        }
        return termos;
    }

    public List<Musica> listarMusicasBuscadasRecentemente(int usuarioId) {
        String sql = """
            SELECT DISTINCT m.id, m.titulo, a.nome as artista, m.genero
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

