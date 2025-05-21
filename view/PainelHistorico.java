/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import connection.Conexao;
import controller.HistoricoController;
import dao.ArtistaDAO;
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
 */

public class PainelHistorico extends javax.swing.JPanel {
    private Usuario usuarioLogado;
    private HistoricoController historicoController;
    private ArtistaDAO artistaDAO;

 public PainelHistorico(Usuario usuarioLogado) {
    this.usuarioLogado = usuarioLogado;
    try {
        Connection conn = Conexao.getConexao(); // Obtenha a conexão
        this.historicoController = new HistoricoController();
        this.artistaDAO = new ArtistaDAO(conn); // Passe a conexão
        
        initComponents();
        inicializarComponentesCustomizados();
        carregarDados();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, 
            "Erro ao conectar ao banco de dados: " + e.getMessage(),
            "Erro", JOptionPane.ERROR_MESSAGE);
        throw new RuntimeException("Falha ao inicializar PainelHistorico", e);
    }
}

    private void inicializarComponentesCustomizados() {
        // Inicializa a lista se estiver nula (segurança)
        if (listaUltimasBuscas == null) {
            listaUltimasBuscas = new JList<>();
            jScrollPane1.setViewportView(listaUltimasBuscas);
        }
        
        // Inicializa a tabela se estiver nula (segurança)
        if (tabelaMusicasBuscadas == null) {
            tabelaMusicasBuscadas = new JTable();
            jScrollPane2.setViewportView(tabelaMusicasBuscadas);
        }
        
        // Configura modelo da tabela
        tabelaMusicasBuscadas.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Música", "Artista", "Gênero", "Data"}
        ));
    }

    private void carregarDados() {
        if (usuarioLogado == null || usuarioLogado.getId() <= 0) {
            JOptionPane.showMessageDialog(this, "Usuário não autenticado", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                carregarUltimasBuscas();
                carregarMusicasBuscadas();
                return null;
            }
            
            @Override
            protected void done() {
                // Atualiza a interface após carregar
                repaint();
                revalidate();
            }
        };
        worker.execute();
    }

    private void carregarUltimasBuscas() {
    try {
        // Agora a lista de historicos é do tipo List<Historico>
        List<Historico> historicos = historicoController.listarUltimosTermosBuscados(usuarioLogado.getId());
        SwingUtilities.invokeLater(() -> {
            DefaultListModel<String> model = new DefaultListModel<>();
            for (Historico historico : historicos) {
                // Formatando a data e o termo de busca
                String entrada = String.format("%s - %s", 
                    historico.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    historico.getTermoBusca());
                model.addElement(entrada);
            }
            listaUltimasBuscas.setModel(model);
        });
    } catch (Exception e) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(PainelHistorico.this, 
                "Erro ao carregar buscas: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE));
    }
}


    private void carregarMusicasBuscadas() {
        try {
            List<Musica> musicas = historicoController.listarMusicasBuscadasRecentemente(usuarioLogado.getId());
            DefaultTableModel model = (DefaultTableModel) tabelaMusicasBuscadas.getModel();
            
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



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCurtidas = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaUltimasBuscas = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelaMusicasBuscadas = new javax.swing.JTable();
        jLabelUltimasBuscas = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(600, 800));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Histórico de Atividades");

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tabelaCurtidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaCurtidas.setPreferredSize(new java.awt.Dimension(200, 80));
        jScrollPane2.setViewportView(tabelaCurtidas);

        jSplitPane1.setRightComponent(jScrollPane2);

        jScrollPane1.setAutoscrolls(true);

        jScrollPane1.setViewportView(listaUltimasBuscas);

        jSplitPane1.setTopComponent(jScrollPane1);

        tabelaMusicasBuscadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Música", "Artista", "Gênero"
            }
        ));
        jScrollPane4.setViewportView(tabelaMusicasBuscadas);

        jSplitPane1.setRightComponent(jScrollPane4);

        jLabelUltimasBuscas.setText("Últimas 10 Buscas");

        jLabel2.setText("Musícas curtidas/descurtidas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelUltimasBuscas))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addComponent(jLabelUltimasBuscas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelUltimasBuscas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<String> listaUltimasBuscas;
    private javax.swing.JTable tabelaCurtidas;
    private javax.swing.JTable tabelaMusicasBuscadas;
    // End of variables declaration//GEN-END:variables
}
