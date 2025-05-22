/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import connection.Conexao;
import dao.PlaylistDAO;
import model.Playlist;
import model.Musica;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistController {
    private PlaylistDAO playlistDAO;

    public PlaylistController() {
        try {
            this.playlistDAO = new PlaylistDAO(new Conexao().getConexao());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public List<Playlist> getPlaylistsPorUsuario(int usuarioId) {
        List<Playlist> playlists = new ArrayList<>();
        try {
            playlists = playlistDAO.listarPorUsuario(usuarioId);  // Chama o DAO para listar as playlists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }




    
    // Métodos de Playlist
    public boolean criarPlaylist(int usuarioId, String nome) {
    Playlist playlist = new Playlist(usuarioId, nome);
    return playlistDAO.criarPlaylist(playlist) > 0;
}

    
    public boolean atualizarPlaylist(int playlistId, int usuarioId, String novoNome) {
    try {
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setUsuarioId(usuarioId);
        playlist.setNome(novoNome);
        return playlistDAO.atualizarPlaylist(playlist);
       } catch (SQLException e) {
        System.err.println("Erro ao atualizar playlist: " + e.getMessage());
        return false;
    }
}

    
    public boolean excluirPlaylist(int playlistId, int usuarioId) {
        try {
            return playlistDAO.excluirPlaylist(playlistId, usuarioId);
        } catch (SQLException e) {
            System.err.println("Erro ao excluir playlist: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos de Músicas na Playlist
    public boolean adicionarMusica(int playlistId, int musicaId, int ordem) {
        try {
            return playlistDAO.adicionarMusica(playlistId, musicaId, ordem);
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar música: " + e.getMessage());
            return false;
        }
    }
    
    public boolean removerMusica(int playlistId, int musicaId) {
        try {
            return playlistDAO.removerMusica(playlistId, musicaId);
        } catch (SQLException e) {
            System.err.println("Erro ao remover música: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos de Consulta
    public List<Playlist> listarPlaylistsUsuario(int usuarioId) {
        try {
            List<Playlist> playlists = playlistDAO.listarPorUsuario(usuarioId);
            // Carrega as músicas para cada playlist
            for (Playlist playlist : playlists) {
                playlist.setMusicas(playlistDAO.listarMusicasDaPlaylist(playlist.getId()));
            }
            return playlists;
        } catch (SQLException e) {
            System.err.println("Erro ao listar playlists: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        try {
            return playlistDAO.listarMusicasDaPlaylist(playlistId);
        } catch (SQLException e) {
            System.err.println("Erro ao listar músicas: " + e.getMessage());
            return List.of();
        }
    }
}
