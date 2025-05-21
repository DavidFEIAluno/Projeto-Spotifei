/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.Conexao;
import dao.ArtistaDAO;
import dao.MusicaDAO;
import model.Artista;
import model.Musica;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author ivald
 */

public class ArtistaController {
    private ArtistaDAO artistaDAO;
    private MusicaDAO musicaDAO;

    
    public ArtistaController() {
        try {
            Connection conn = Conexao.getConexao();
            
            this.artistaDAO = new ArtistaDAO(conn);
            this.musicaDAO = new MusicaDAO(conn);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    
    public List<Artista> buscarArtistasPorNome(String nome) {
        return artistaDAO.buscarPorNome(nome);
    }
 
    public List<Artista> buscarArtistasPorGenero(String genero) {
        return artistaDAO.buscarPorGenero(genero);
    }
    
    public List<Artista> listarTodosArtistas() {
        return artistaDAO.listarTodos();
    }
    
    public Artista obterArtistaPorId(int id) {
        return artistaDAO.buscarPorId(id);
    }

}