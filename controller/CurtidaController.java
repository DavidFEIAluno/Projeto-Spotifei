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

    /**
     * Registra a ação de curtir ou descurtir uma música.
     * @param usuarioId ID do usuário
     * @param musicaId ID da música
     * @param acao true para curtir, false para descurtir
     */
    public void registrarCurtida(int usuarioId, int musicaId, boolean acao) {
        curtidaDAO.registrarAcao(usuarioId, musicaId, acao);
    }

    /**
     * Lista as músicas curtidas por um usuário.
     * @param usuarioId ID do usuário
     * @return Lista de músicas curtidas
     */
    public List<Musica> listarMusicasCurtidas(int usuarioId) {
        return curtidaDAO.listarPorAcao(usuarioId, true);
    }

    /**
     * Lista as músicas descurtidas por um usuário.
     * @param usuarioId ID do usuário
     * @return Lista de músicas descurtidas
     */
    public List<Musica> listarMusicasDescurtidas(int usuarioId) {
        return curtidaDAO.listarPorAcao(usuarioId, false);
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


