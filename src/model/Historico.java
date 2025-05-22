/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author ivald
 * Aqui é onde fica os get e set do Historico, armazenando as ações de buscar
 */

public class Historico {
    private int id;
    private String nomeMusica;
    private LocalDateTime horario;
    private String acao;
    private int idUsuario;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeMusica() { return nomeMusica; }
    public void setNomeMusica(String nomeMusica) { this.nomeMusica = nomeMusica; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}


