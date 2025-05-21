/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.Conexao;
import dao.HistoricoDAO;
import model.Historico;
import controller.HistoricoController;
import model.Musica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoController {
    private HistoricoDAO historicoDAO;

    public HistoricoController() {
        this.historicoDAO = new HistoricoDAO();
    }

    public void registrarBusca(int usuarioId, String termoBusca) {
    System.out.println("Registrando busca para o usuário " + usuarioId + " com o termo: " + termoBusca);
    historicoDAO.registrarBusca(usuarioId, termoBusca);
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
        return historicoDAO.listarMusicasBuscadasRecentemente(usuarioId);
    }
}
