/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.PlaylistDAO;
import java.time.LocalDateTime;
import model.Musica;
import model.Playlist;
import java.util.List;

public class PlaylistController {
    private final PlaylistDAO playlistDAO;

    public PlaylistController() {
        this.playlistDAO = new PlaylistDAO();
    }

public boolean criarPlaylist(String nome, int usuarioId) {
    if (nome == null || nome.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome da playlist não pode ser vazio");
    }
    if (usuarioId <= 0) {
        throw new IllegalArgumentException("ID do usuário inválido");
    }

    Playlist playlist = new Playlist();
    playlist.setNome(nome.trim());
    playlist.setUsuarioId(usuarioId);
    playlist.setDataCriacao(LocalDateTime.now());
    
    return playlistDAO.criarPlaylist(playlist);
}

    public boolean adicionarMusica(int playlistId, int musicaId, int ordem) {
        if (playlistId <= 0 || musicaId <= 0) {
            throw new IllegalArgumentException("IDs inválidos");
        }
        if (ordem < 0) {
            throw new IllegalArgumentException("Ordem inválida");
        }
        return playlistDAO.adicionarMusica(playlistId, musicaId, ordem);
    }

    public boolean removerMusica(int playlistId, int musicaId) {
        if (playlistId <= 0 || musicaId <= 0) {
            throw new IllegalArgumentException("IDs inválidos");
        }
        return playlistDAO.removerMusica(playlistId, musicaId);
    }

    public List<Playlist> listarPlaylistsDoUsuario(int usuarioId) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("ID do usuário inválido");
        }
        return playlistDAO.listarPlaylistsPorUsuario(usuarioId);
    }

    public List<Musica> listarMusicasDaPlaylist(int playlistId) {
        if (playlistId <= 0) {
            throw new IllegalArgumentException("ID da playlist inválido");
        }
        return playlistDAO.listarMusicasDaPlaylist(playlistId);
    }

    public boolean excluirPlaylist(int playlistId) {
        if (playlistId <= 0) {
            throw new IllegalArgumentException("ID da playlist inválido");
        }
        return playlistDAO.excluirPlaylist(playlistId);
    }

    // Métodos adicionais que podem ser úteis
    public boolean playlistPertenceAoUsuario(int playlistId, int usuarioId) {
        if (playlistId <= 0 || usuarioId <= 0) {
            return false;
        }
        List<Playlist> playlists = playlistDAO.listarPlaylistsPorUsuario(usuarioId);
        return playlists.stream().anyMatch(p -> p.getId() == playlistId);
    }

    public boolean atualizarOrdemMusica(int playlistId, int musicaId, int novaOrdem) {
        if (playlistId <= 0 || musicaId <= 0 || novaOrdem < 0) {
            return false;
        }
        
        // Primeiro remove a música da posição atual
        if (!playlistDAO.removerMusica(playlistId, musicaId)) {
            return false;
        }
        
        // Depois adiciona na nova posição
        return playlistDAO.adicionarMusica(playlistId, musicaId, novaOrdem);
    }
}

