/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.HistoricoDAO;
import model.Musica;

import java.util.List;

public class HistoricoController {
    private HistoricoDAO historicoDAO;

    public HistoricoController() {
        this.historicoDAO = new HistoricoDAO();
    }

    public void registrarBusca(int usuarioId, int musicaId) {
        historicoDAO.registrarBusca(usuarioId, musicaId);
    }

    public void registrarCurtida(int usuarioId, int musicaId, boolean curtida) {
        historicoDAO.registrarCurtida(usuarioId, musicaId, curtida);
    }

    public List<Musica> listarUltimasBuscas(int usuarioId) {
        return historicoDAO.listarUltimasBuscas(usuarioId);
    }

    public List<Musica> listarCurtidas(int usuarioId) {
        return historicoDAO.listarCurtidas(usuarioId, true);
    }

    public List<Musica> listarDescurtidas(int usuarioId) {
        return historicoDAO.listarCurtidas(usuarioId, false);
    }
}

