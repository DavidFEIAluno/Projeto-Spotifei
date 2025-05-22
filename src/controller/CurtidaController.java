/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CurtidaDAO;
import model.Musica;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import connection.Conexao;


/**
 *
 * @author ivald
 * Codigo bem simples, focado em fazer 
 * funcionar o registro das curtidas e passar do CurtidasDAO 
 * para o paineBuscar, onde é possivel curtir uma musica
 */

public class CurtidaController {
    private CurtidaDAO curtidaDAO;
    private Connection conn;

   public CurtidaController() {
        try {
            this.conn = Conexao.getConexao();
            this.conn.setAutoCommit(false); // Desativa auto-commit
            this.curtidaDAO = new CurtidaDAO(conn);
        } catch (SQLException e) {
            System.err.println("Erro ao criar CurtidaController: " + e.getMessage());
            throw new RuntimeException("Falha na inicialização", e);
        }
    }
   
   public List<Musica> listarPorAcao(int usuarioId, boolean acao) {
        return curtidaDAO.listarPorAcaoComData(usuarioId, acao);
    }
    
    // pega todas as ações formatadas
    public List<Object[]> listarTodasAcoesFormatadas(int usuarioId) {
        return curtidaDAO.listarTodasAcoesFormatadas(usuarioId);
    }
   
    public void registrarCurtida(int usuarioId, int musicaId, boolean acao) {
    boolean sucesso = curtidaDAO.registrarAcao(usuarioId, musicaId, acao);
    if (sucesso) {
        System.out.println("Curtida registrada com sucesso para a música ID " + musicaId);
    } else {
        System.out.println("Falha ao registrar curtida para a música ID " + musicaId);
    }
}


    public List<Musica> listarMusicasCurtidas(int usuarioId) {
        return curtidaDAO.listarPorAcaoComData(usuarioId, true);
    }

 
    public List<Musica> listarMusicasDescurtidas(int usuarioId) {
        return curtidaDAO.listarPorAcaoComData(usuarioId, false);
    }
    
    public boolean curtirMusica(int usuarioId, int musicaId) {
        try {
            boolean sucesso = curtidaDAO.registrarAcao(usuarioId, musicaId, true);
            if (sucesso) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro no rollback: " + ex.getMessage());
            }
            System.err.println("Erro ao curtir música: " + e.getMessage());
            return false;
        }
    }

 public boolean descurtirMusica(int usuarioId, int musicaId) {
        try {
            boolean sucesso = curtidaDAO.registrarAcao(usuarioId, musicaId, false);
            if (sucesso) {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro no rollback: " + ex.getMessage());
            }
            System.err.println("Erro ao descurtir música: " + e.getMessage());
            return false;
        }
    }

}


