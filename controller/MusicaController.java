/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.MusicaDAO;
import dao.ArtistaDAO;
import model.Musica;
import model.Artista;
import connection.Conexao;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

public class MusicaController {
    private MusicaDAO musicaDAO;
    private ArtistaDAO artistaDAO;

    public MusicaController() {
    try {
        Connection conn = Conexao.getConexao();
        this.musicaDAO = new MusicaDAO(conn);
        this.artistaDAO = new ArtistaDAO(conn);
    } catch (SQLException e) {
        System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
    }
}


    /**
     * Adiciona uma nova música ao banco de dados.
     * Se o artista não existir, ele será criado.
     */
    public boolean adicionarMusica(String titulo, String nomeArtista, String genero, int duracaoSegundos, String dataLancamento) {
        try {
            // Verifica se o artista já existe, senão insere
            Artista artista = artistaDAO.buscarPorNomeExato(nomeArtista);
            if (artista == null) {
                artista = new Artista();
                artista.setNome(nomeArtista);
                artista.setGenero(genero); // ou null, se não quiser associar aqui
                int artistaId = artistaDAO.inserirArtista(artista);
                artista.setId(artistaId);
            }

            // Cria a música e insere
            Musica musica = new Musica();
            musica.setTitulo(titulo);
            musica.setGenero(genero);
            musica.setDuracaoSegundos(duracaoSegundos);
            musica.setArtistaid(artista.getId());

            return musicaDAO.inserirMusica(musica);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca músicas com base no termo e filtro: "nome", "artista" ou "genero"
     */
    public List<Musica> buscarMusicas(String termo, String filtro) {
        return musicaDAO.buscarPorFiltro(termo, filtro);
    }

    /**
     * Lista todas as músicas disponíveis no sistema.
     */
    public List<Musica> listarTodas() {
        return musicaDAO.listarTodas();
    }

    /**
     * Busca uma música pelo seu ID.
     */
    public Musica buscarPorId(int id) {
        return musicaDAO.buscarPorId(id);
    }
}


