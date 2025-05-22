/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import connection.Conexao;
import controller.CurtidaController;
import controller.MusicaController;
import controller.HistoricoController;
import model.Artista;
import model.Usuario;
import model.Historico;
import dao.ArtistaDAO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author ivald
 * Esse painel é o painel onde o principal foco é buscar as musicas 
 * ou artistas registrados no banco de dados
 */

import javax.swing.table.DefaultTableModel;
import model.Musica;
public class PainelBuscar extends javax.swing.JPanel {
    private Usuario usuarioLogado;
    private HistoricoController historicoController; 
    
    private void testarRegistroHistorico() {
    if (usuarioLogado == null) {
        JOptionPane.showMessageDialog(this, "Nenhum usuário logado para teste");
        return;
    }

    // Teste 1: Registrar busca
    new HistoricoController().registrarBusca(usuarioLogado.getId(), "teste busca");
    
    // Teste 2: Registrar ação genérica
    new HistoricoController().registrarAcao(usuarioLogado.getId(), "música teste", "play");
    
    JOptionPane.showMessageDialog(this, "Ações de teste registradas!\nVerifique no banco de dados.");
}

    // Construtor que recebe o usuário logado
        public PainelBuscar(Usuario usuarioLogado) {
            this.usuarioLogado = usuarioLogado;
            this.historicoController = new HistoricoController();
            initComponents();
    tabelaResultados.getSelectionModel().addListSelectionListener(e -> {
        int selectedRow = tabelaResultados.getSelectedRow();
        btnCurtir.setEnabled(selectedRow >= 0);
        btnDescurtir.setEnabled(selectedRow >= 0);
    });
        }

    // Construtor padrão
        public PainelBuscar() {
            initComponents();
            // No método initComponents(), atualize o modelo da tabela:
            tabelaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "ID", "Nome", "Artista", "Gênero", "Duração", "Álbum"
            }
                    
                    
     ) {
    boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
});

// Esconda a coluna ID (opcional)
tabelaResultados.removeColumn(tabelaResultados.getColumnModel().getColumn(0));
        }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBuscar = new javax.swing.JPanel();
        jLabelBuscar = new javax.swing.JLabel();
        jTextFieldBuscar = new javax.swing.JTextField();
        comboFiltro = new javax.swing.JComboBox<>();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaResultados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnCurtir = new javax.swing.JButton();
        btnDescurtir = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(700, 600));

        jPanelBuscar.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBuscar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelBuscar.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        jLabelBuscar.setText("Buscar:");

        comboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Artista", "Genero", " " }));
        comboFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboFiltroActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBuscarLayout = new javax.swing.GroupLayout(jPanelBuscar);
        jPanelBuscar.setLayout(jPanelBuscarLayout);
        jPanelBuscarLayout.setHorizontalGroup(
            jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBuscarLayout.createSequentialGroup()
                .addGroup(jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBuscarLayout.createSequentialGroup()
                        .addComponent(jLabelBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBuscarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBuscar)))
                .addContainerGap())
        );
        jPanelBuscarLayout.setVerticalGroup(
            jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBuscarLayout.createSequentialGroup()
                .addGroup(jPanelBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBuscar)
                    .addComponent(jTextFieldBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(btnBuscar)
                .addContainerGap())
        );

        jScrollPane2.setToolTipText("\n");
        jScrollPane2.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());

        tabelaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "Música", "Artista", "Gênero"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelaResultados);

        btnCurtir.setText("Curtir");
        btnCurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCurtirActionPerformed(evt);
            }
        });

        btnDescurtir.setText("Descurtir");
        btnDescurtir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescurtirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCurtir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDescurtir)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDescurtir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(248, 248, 248)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboFiltroActionPerformed

    private void btnCurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCurtirActionPerformed
        if (usuarioLogado == null || usuarioLogado.getId() <= 0) {
        JOptionPane.showMessageDialog(this, "Nenhum usuário válido logado!", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
        int selectedRow = tabelaResultados.getSelectedRow();
    if (selectedRow >= 0) {
        int musicaId = (int) tabelaResultados.getValueAt(selectedRow, 0);
        String musicaNome = (String) tabelaResultados.getValueAt(selectedRow, 1); // Pega o nome da música
        
        try {
            CurtidaController curtidaController = new CurtidaController();
            curtidaController.curtirMusica(usuarioLogado.getId(), musicaId);
            
            // REGISTRA A CURTIDA NO HISTÓRICO
            historicoController.registrarAcao(
                usuarioLogado.getId(), 
                musicaNome, 
                "curtida"
            );
            
            JOptionPane.showMessageDialog(this, "Música curtida com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao curtir música: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione uma música para curtir.");
    }
    }//GEN-LAST:event_btnCurtirActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
    btnBuscar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            buscarMusicas();
        }
    });
}

// Método da classe
private void buscarMusicas() {
    String termo = jTextFieldBuscar.getText().trim();
    String filtro = (String) comboFiltro.getSelectedItem();
    
    // REGISTRA A BUSCA NO HISTÓRICO (adicione esta parte)
    if (usuarioLogado != null && !termo.isEmpty()) {
        historicoController.registrarBusca(usuarioLogado.getId(), termo);
    }

    DefaultTableModel model = (DefaultTableModel) tabelaResultados.getModel();
    model.setRowCount(0); // Limpa a tabela

    try {
        MusicaController controller = new MusicaController();
        List<Musica> resultados;

        switch(filtro) {
            case "Nome":
                resultados = controller.buscarMusicas(termo, "nome");
                break;
            case "Artista":
                resultados = controller.buscarMusicas(termo, "artista");
                break;
            case "Gênero":
                resultados = controller.buscarMusicas(termo, "genero");
                break;
            default:
                resultados = controller.listarTodas();
        }

        Connection conn = Conexao.getConexao();
        ArtistaDAO artistaDAO = new ArtistaDAO(conn);

        for (Musica musica : resultados) {
            Artista artista = artistaDAO.buscarPorId(musica.getArtistaid());
            model.addRow(new Object[]{
                musica.getId(),
                musica.getTitulo(),
                artista != null ? artista.getNome() : "Desconhecido",
                musica.getGenero(),
                musica.getDuracaoSegundos()
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao carregar os artistas: " + e.getMessage());
    }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnDescurtirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescurtirActionPerformed
    int selectedRow = tabelaResultados.getSelectedRow();
    if (selectedRow >= 0) {
        int musicaId = (int) tabelaResultados.getValueAt(selectedRow, 0);
        String musicaNome = (String) tabelaResultados.getValueAt(selectedRow, 1);
        
        try {
            CurtidaController curtidaController = new CurtidaController();
            curtidaController.descurtirMusica(usuarioLogado.getId(), musicaId);
            
            // REGISTRA A DESCURTIDA NO HISTÓRICO (nova parte)
            historicoController.registrarAcao(
                usuarioLogado.getId(), 
                musicaNome, 
                "descurtida"
            );
            
            JOptionPane.showMessageDialog(this, "Música descurtida com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao descurtir música: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "Selecione uma música para descurtir.");
    }
    }//GEN-LAST:event_btnDescurtirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCurtir;
    private javax.swing.JButton btnDescurtir;
    private javax.swing.JComboBox<String> comboFiltro;
    private javax.swing.JLabel jLabelBuscar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelBuscar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldBuscar;
    private javax.swing.JTable tabelaResultados;
    // End of variables declaration//GEN-END:variables
}
