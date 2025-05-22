/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import connection.Conexao;
import controller.HistoricoController;
import controller.CurtidaController;
import dao.ArtistaDAO;
import dao.HistoricoDAO;
import model.Musica;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import model.Historico;
/**
 *
 * @author ivald
 * Aqui é o painel onde aparece tudo ja feito pelo
 * usuario, a pesquisa e a ação tomada no buscar, seja curtir ou descurtir.
 */

public class PainelHistorico extends javax.swing.JPanel {
    private HistoricoController historicoController;
    private ArtistaDAO artistaDAO;
    private CurtidaController curtidaController;
    private final DefaultTableModel modeloTabela;
    private final Usuario usuarioLogado;
    
    

 public PainelHistorico(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.curtidaController = new CurtidaController(); // Instância correta
        initComponents();
        this.modeloTabela = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Termo Buscado", "Data/Hora", "Ação"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaCurtidas.setModel(new DefaultTableModel(
            new Object[][]{}, 
            new String[]{"Música", "Ação", "Gênero"}  // Colunas da tabela
            ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;  // Torna a tabela não editável
        }
    });
        tabelaHistorico.setModel(modeloTabela);
        carregarMusicasCurtidas();
        carregarDados();
    }


    private void inicializarComponentesCustomizados() {
    // Configuração da tabela de histórico
    tabelaHistorico = new JTable();
    String[] colunas = {"Termo Buscado", "Ação", "Data/Hora"};
    DefaultTableModel model = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Torna todas as células não editáveis
        }
    };
        tabelaHistorico.setModel(model);
        
        }

    private void carregarDados() {
        if (usuarioLogado == null) return;
        
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                HistoricoDAO dao = new HistoricoDAO();
                List<Historico> historicos = dao.listarUltimosTermosBuscados(usuarioLogado.getId());
                
                SwingUtilities.invokeLater(() -> {
                    modeloTabela.setRowCount(0);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                    
                    for (Historico h : historicos) {
                        modeloTabela.addRow(new Object[]{
                            h.getId(),
                            h.getNomeMusica(),
                            h.getHorario().format(formatter),
                            h.getAcao()
                        });
                    }
                });
                return null;
            }
            
            @Override
            protected void done() {
                tabelaHistorico.revalidate();
                tabelaHistorico.repaint();
            }
        }.execute();
    }



    private void carregarUltimasBuscas() {
        try {
            List<Historico> historicos = historicoController.listarUltimosTermosBuscados(usuarioLogado.getId());
        
            DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
            model.setRowCount(0); // Limpa a tabela
        
        for (Historico historico : historicos) {
            model.addRow(new Object[]{
                historico.getNomeMusica(), // Ou getNomeMusica() dependendo da sua classe
                historico.getAcao(),
                historico.getHorario().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            });
        }
        
        // Atualiza a visualização
        tabelaHistorico.revalidate();
        tabelaHistorico.repaint();
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Erro ao carregar histórico: " + e.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
    }
}


    private void carregarMusicasBuscadas() {
        try {
            List<Musica> musicas = historicoController.listarMusicasBuscadasRecentemente(usuarioLogado.getId());
            DefaultTableModel model = (DefaultTableModel) tabelaHistorico.getModel();
            
            SwingUtilities.invokeLater(() -> {
                model.setRowCount(0); // Limpa a tabela
                
               for (Musica musica : musicas) {
        String artista = artistaDAO.buscarPorId(musica.getArtistaid()).getNome();
        model.addRow(new Object[]{
            musica.getTitulo(),
            artista,
            musica.getGenero(),
            musica.getDataLancamento()
        });
    }
});
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> 
                JOptionPane.showMessageDialog(PainelHistorico.this, 
                    "Erro ao carregar músicas: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE));
        }
    }
    
    private void carregarMusicasCurtidas() {
        if (usuarioLogado == null) return;
            var curtidas = curtidaController.listarPorAcao(usuarioLogado.getId(), true);
            var descurtidas = curtidaController.listarPorAcao(usuarioLogado.getId(), false);

        DefaultTableModel model = (DefaultTableModel) tabelaCurtidas.getModel();
        model.setRowCount(0); // Limpa a tabela

        // Adiciona as curtidas
        for (var musica : curtidas) {
            model.addRow(new Object[]{
                musica.getTitulo(),
                "Curtiu",
                musica.getGenero(),
                "Data" // Substitua por data real se necessário
            });
        }

        // Adiciona as descurtidas
        for (var musica : descurtidas) {
            model.addRow(new Object[]{
                musica.getTitulo(),
                "Descurtiu",
                musica.getGenero(),
                "Data" // Substitua por data real se necessário
            });
        }
    tabelaCurtidas.revalidate();
    tabelaCurtidas.repaint();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabelUltimasBuscas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaHistorico = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCurtidas = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(600, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Histórico de Atividades");

        jLabelUltimasBuscas.setText("Últimas 10 Ações");

        jLabel2.setText("Musícas curtidas/descurtidas");

        tabelaHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Música", "Artista", "Gênero", "Curtida/Descurtida"
            }
        ));
        tabelaHistorico.setPreferredSize(new java.awt.Dimension(200, 100));
        jScrollPane4.setViewportView(tabelaHistorico);

        tabelaCurtidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id do Usuário", "Id da Música", "Ação"
            }
        ));
        tabelaCurtidas.setMinimumSize(new java.awt.Dimension(30, 100));
        tabelaCurtidas.setPreferredSize(new java.awt.Dimension(200, 100));
        jScrollPane2.setViewportView(tabelaCurtidas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelUltimasBuscas))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabelUltimasBuscas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelUltimasBuscas;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tabelaCurtidas;
    private javax.swing.JTable tabelaHistorico;
    // End of variables declaration//GEN-END:variables
}
