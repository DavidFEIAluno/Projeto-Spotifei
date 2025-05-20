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

public class CurtidaDAO {

    public void registrarAcao(int usuarioId, int musicaId, boolean acao) {
        String sql = """
            INSERT INTO curtidas (usuario_id, musica_id, acao) 
            VALUES (?, ?, ?)
            ON CONFLICT (usuario_id, musica_id) 
            DO UPDATE SET acao = EXCLUDED.acao, data_hora = CURRENT_TIMESTAMP
            """;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, musicaId);
            stmt.setBoolean(3, acao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao registrar curtida: " + e.getMessage());
        }
    }

    public List<Musica> listarPorAcao(int usuarioId, boolean acao) {
        String sql = """
            SELECT m.id, m.titulo, a.nome as artista, m.genero
            FROM curtidas c
            JOIN musicas m ON c.musica_id = m.id
            JOIN artistas a ON m.artista_id = a.id
            WHERE c.usuario_id = ? AND c.acao = ?
            ORDER BY c.data_hora DESC
            """;

        List<Musica> musicas = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas curtidas: " + e.getMessage());
        }
        return musicas;
    }
}
