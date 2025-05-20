/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int id;
    private String nome;
    private int usuarioId;
    private LocalDateTime dataCriacao;
    private List<Musica> musicas = new ArrayList<>();

    // Construtores
    public Playlist() {}

    public Playlist(String nome, int usuarioId, boolean publica) {
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.dataCriacao = LocalDateTime.now();
    }

    public Playlist(int id, String nome, int usuarioId, LocalDateTime dataCriacao, boolean publica) {
        this.id = id;
        this.nome = nome;
        this.usuarioId = usuarioId;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { 
        this.dataCriacao = dataCriacao; 
    }

    public List<Musica> getMusicas() { return new ArrayList<>(musicas); }
    public void setMusicas(List<Musica> musicas) { 
        this.musicas = new ArrayList<>(musicas); 
    }

    // Métodos auxiliares
    public void adicionarMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
        }
    }

    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }

    public int getQuantidadeMusicas() {
        return musicas.size();
    }

    public int getDuracaoTotalSegundos() {
        return musicas.stream()
                     .mapToInt(Musica::getDuracaoSegundos)
                     .sum();
    }

    public String getDuracaoTotalFormatada() {
        int totalSegundos = getDuracaoTotalSegundos();
        int horas = totalSegundos / 3600;
        int minutos = (totalSegundos % 3600) / 60;
        int segundos = totalSegundos % 60;
        
        return horas > 0 
            ? String.format("%d:%02d:%02d", horas, minutos, segundos)
            : String.format("%d:%02d", minutos, segundos);
    }
}
