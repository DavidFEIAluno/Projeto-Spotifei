/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ivald
 * Aqui ficam dados e toda a transportacao das regras Get e set e dos construtores. 
 * fundamental para o funcionamento das playlists, armazenando e fazendo com que 
 * seja possivel a criação delas
 */


public class Playlist {
    private int id;
    private int usuarioId;
    private String nome;
    private LocalDateTime dataCriacao;
    private List<Musica> musicas;
    
    /** Construtores*/
    public Playlist() {}
    
    public Playlist(int usuarioId, String nome) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    
    public List<Musica> getMusicas() { return musicas; }
    public void setMusicas(List<Musica> musicas) { this.musicas = musicas; }
}
