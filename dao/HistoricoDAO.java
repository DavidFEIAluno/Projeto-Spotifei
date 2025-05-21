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
import model.Historico;

public class HistoricoDAO {

    public void registrarBusca(int usuarioId, String termoBusca) {
    String sql = "INSERT INTO historico_buscas (usuario_id, termo_busca) VALUES (?, ?)";
    
    System.out.println("Executando SQL: " + sql);
    System.out.println("Parametros: usuario_id = " + usuarioId + ", termo_busca = " + termoBusca);

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, usuarioId);
        stmt.setString(2, termoBusca);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Busca registrada com sucesso!");
        } else {
            System.out.println("Nenhuma linha inserida.");
        }
    } catch (SQLException e) {
        System.err.println("Erro ao registrar busca no histórico: " + e.getMessage());
    }
}


public List<Historico> listarUltimosTermosBuscados(int usuarioId) {
    String sql = """
        SELECT id, usuario_id, termo_busca, data_hora 
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
            historico.setUsuarioId(rs.getInt("usuario_id"));
            historico.setTermoBusca(rs.getString("termo_busca"));
            historico.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
            historicos.add(historico);
        }
    } catch (SQLException e) {
        System.err.println("Erro ao recuperar histórico de buscas: " + e.getMessage());
    }
    return historicos;
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

