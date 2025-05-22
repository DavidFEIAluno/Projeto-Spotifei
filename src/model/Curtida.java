/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author ivald
 * aqui fica o get e set, e os construtores das curtidas, onde armazenam ao banco de dados.
 */
public class Curtida {
    private int usuarioId;
    private int musicaId;
    private boolean acao;
    private LocalDateTime dataHora;
    
    // Campos temporários para exibição
    private String musicaTitulo;
    private String artistaNome;

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getMusicaId() {
        return musicaId;
    }

    public void setMusicaId(int musicaId) {
        this.musicaId = musicaId;
    }

    public boolean isAcao() {
        return acao;
    }

    public void setAcao(boolean acao) {
        this.acao = acao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    
    
    public String getMusicaTitulo() {
        return musicaTitulo;
    }

    public void setMusicaTitulo(String musicaTitulo) {
        this.musicaTitulo = musicaTitulo;
    }

    public String getArtistaNome() {
        return artistaNome;
    }

    public void setArtistaNome(String artistaNome) {
        this.artistaNome = artistaNome;
    }
    
    // Método auxiliar para exibição
    public String getAcaoFormatada() {
        return acao ? "Curtiu" : "Descurtiu";
    }
}