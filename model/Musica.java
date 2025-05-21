/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

public class Musica {
    private int artistaid;
    private int id;
    private String titulo;  
    private String artistanome;
    private String genero;
    private int duracaoSegundos;
    private LocalDate dataLancamento;
    private int contadorCurtidas;

    // Construtores
    public Musica() {}
    
    private int ordemNaPlaylist;
    
    public int getOrdemNaPlaylist() {
        return ordemNaPlaylist;
    }
    
    public void setOrdemNaPlaylist(int ordemNaPlaylist) {
        this.ordemNaPlaylist = ordemNaPlaylist;
    }

public Musica(String titulo, int artistaId, String artistaNome, String genero, 
             int duracaoSegundos, LocalDate dataLancamento) {
    this.titulo = titulo;
    this.artistaid = artistaId;
    this.artistanome = artistaNome;
    this.genero = genero;
    this.duracaoSegundos = duracaoSegundos;
    this.dataLancamento = dataLancamento;
    this.contadorCurtidas = 0;
}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getArtistaid() {
        return artistaid;
    }

    public void setArtistaid(int artistaid) {
        this.artistaid = artistaid;
    }

    public String getArtistanome() {
        return artistanome;
    }

    public void setArtistanome(String artistanome) {
        this.artistanome = artistanome;
    } 
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracaoSegundos() { return duracaoSegundos; }
    public void setDuracaoSegundos(int duracaoSegundos) { 
        this.duracaoSegundos = duracaoSegundos; 
    }

    public LocalDate getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(LocalDate dataLancamento) { 
        this.dataLancamento = dataLancamento; 
    }

    public int getContadorCurtidas() { return contadorCurtidas; }
    public void setContadorCurtidas(int contadorCurtidas) { 
        this.contadorCurtidas = contadorCurtidas; 
    }

    public String getDuracaoFormatada() {
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }
}

