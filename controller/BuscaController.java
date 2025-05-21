/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.MusicaDAO;
import dao.ArtistaDAO;
import connection.Conexao;
import model.Musica;
import model.Artista;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BuscaController {
    private MusicaDAO musicaDAO;
    private ArtistaDAO artistaDAO;

    public BuscaController() {
        try {
            Connection conn = Conexao.getConexao();
            this.musicaDAO = new MusicaDAO(conn);
            this.artistaDAO = new ArtistaDAO(conn);
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Busca músicas
    public List<Musica> buscarMusicas(String termo) {
        return musicaDAO.buscarPorFiltro(termo, "nome");
    }

    public List<Musica> buscarMusicasPorGenero(String genero) {
        return musicaDAO.buscarPorFiltro(genero, "genero");
    }

    // Busca música por ID
    public Musica buscarMusicaPorId(int id) {
        return musicaDAO.buscarPorId(id);
    }

    public List<Artista> buscarPorTermo(String termo) {
    List<Artista> artistas = new ArrayList<>();
    String sql = "SELECT * FROM artistas WHERE nome ILIKE ? OR genero ILIKE ? ORDER BY nome";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        String likeTermo = "%" + termo + "%";
        stmt.setString(1, likeTermo);
        stmt.setString(2, likeTermo);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            artistas.add(new Artista(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("genero")
            ));
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar artistas por termo: " + e.getMessage());
    }

    return artistas;
}

}


