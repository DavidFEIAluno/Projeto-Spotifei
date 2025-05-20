/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PlaylistDAO;
import model.Musica;
import model.Playlist;
import connection.Conexao;

import java.sql.Connection;
import java.util.List;

public class PlaylistController {
    private PlaylistDAO playlistDAO;

    public PlaylistController() {
        Connection conn = Conexao.getConexao();
        playlistDAO = new PlaylistDAO(conn);
    }

    public void criarPlaylist(String nome, int usuarioId) {
        Playlist p = new Playlist(nome, usuarioId);
        playlistDAO.criarPlaylist(p);
    }

    public List<Playlist> listarPlaylistsDoUsuario(int usuarioId) {
        return playlistDAO.listarPlaylistsPorUsuario(usuarioId);
    }

    public void adicionarMusica(int playlistId, int musicaId) {
        playlistDAO.adicionarMusica(playlistId, musicaId);
    }

    public void removerMusica(int playlistId, int musicaId) {
        playlistDAO.removerMusica(playlistId, musicaId);
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        return playlistDAO.listarMusicasDaPlaylist(playlistId);
    }

    public void excluirPlaylist(int playlistId) {
        playlistDAO.excluirPlaylist(playlistId);
    }
}

