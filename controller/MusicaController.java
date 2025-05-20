/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.MusicaDAO;
import model.Musica;
import connection.Conexao;

import java.sql.Connection;
import java.util.List;

public class MusicaController {
    private MusicaDAO musicaDAO;

    public MusicaController() {
        Connection conn = Conexao.getConexao();
        musicaDAO = new MusicaDAO(conn);
    }

    public void adicionarMusica(String nome, String artista, String genero) {
        Musica m = new Musica(nome, artista, genero);
        musicaDAO.inserirMusica(m);
    }

    public List<Musica> buscarMusicas(String termo, String filtro) {
        return musicaDAO.buscarPorFiltro(termo, filtro);
    }

    public List<Musica> listarTodas() {
        return musicaDAO.listarTodas();
    }

    public Musica buscarPorId(int id) {
        return musicaDAO.buscarPorId(id);
    }
}

