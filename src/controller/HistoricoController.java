/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author ivald
 * Reponsavel para as requisicoes do usuario em tudo que envolve o historico
 */
import connection.Conexao;
import dao.HistoricoDAO;
import model.Historico;
import model.Musica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dao.HistoricoDAO;
import java.time.LocalDateTime;

public class HistoricoController {
    private HistoricoDAO historicoDAO = new HistoricoDAO();

    public void salvarHistorico(String nomeMusica, String acao, int idUsuario) {
        Historico h = new Historico();
        h.setNomeMusica(nomeMusica);
        h.setHorario(LocalDateTime.now());
        h.setAcao(acao);
        h.setIdUsuario(idUsuario);

        System.out.println("[LOG] Chamando salvarHistorico com dados: " + nomeMusica + ", " + acao);
        historicoDAO.adicionarHistorico(h);
    }

    public void registrarBusca(int usuarioId, String termo) {
        // Usando o método existente que só precisa de usuarioId e termo
        historicoDAO.registrarBusca(usuarioId, termo);
    }
    
    public void registrarAcao(int usuarioId, String termo, String acao) {
        // Criando um objeto Historico completo
        Historico registro = new Historico();
        registro.setIdUsuario(usuarioId);
        registro.setNomeMusica(termo);
        registro.setAcao(acao);
        registro.setHorario(LocalDateTime.now());
        
        historicoDAO.adicionarHistorico(registro);
    }

 public List<Historico> listarUltimosTermosBuscados(int usuarioId) {
    String sql = "SELECT termo_busca, acao, data_hora FROM historico_buscas " +
                 "WHERE usuario_id = ? ORDER BY data_hora DESC LIMIT 10";

        List<Historico> historicos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Historico historico = new Historico();
                historico.setIdUsuario(rs.getInt("usuario_id"));
                historico.setNomeMusica(rs.getString("termo_busca"));
                historico.setHorario(rs.getTimestamp("data_hora").toLocalDateTime());
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
