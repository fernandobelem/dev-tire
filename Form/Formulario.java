package Form;

import graphics.ImagePanel;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

import model.Pneu;
import model.TipoVeiculo;
import model.Veiculo;
import tablemodel.PneuTableModel;
import dao.Database;
import dao.DatabaseTipo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fobm
 */
public class Formulario extends javax.swing.JPanel {

    PneuTableModel tModel = new PneuTableModel();

    List<Pneu> listPneu = new ArrayList<Pneu>();
    List<Pneu> listExcludedPneu = new ArrayList<Pneu>();
    List<Pneu> listIncludedPneuDuringEdit = new ArrayList<Pneu>();
    List<TipoVeiculo> listTipo = new ArrayList<TipoVeiculo>();

    final static String EMPTY_STRING = "";
    final static String ONLY_NUMBERS = "^[0-9\\,.]*$";

    public boolean isEdicao = false;

    TableColumn eixo;
    JPopupMenu menuExcluirPneu;

    public int veicIdFromConsulta;

    /**
     * Creates new form Formulario
     */
    public Formulario() {
        initComponents();
        initComponentsCustom();
        setVisible(true);
        setHabilitarEdicaoVisible(false);
    }

    private void initComponentsCustom() {
        configureJTablePneu();
        loadListaTipo();
        configureComboTipo();
        loadFormattedTextFieldPlaca();
        jLabel5.setVisible(false);
    }

    public void loadIconComponent() {
        String path = "/resources/remove.png";
        ImagePanel imgPanel = new ImagePanel();
        Icon icon = new ImageIcon(imgPanel.loadImgBytes(path), "Excluir Veículo");
        jLabel5.setText("");
        jLabel5.setIcon(icon);
        jLabel5.setVisible(true);
    }

    public void setHabilitarEdicaoVisible(boolean b) {
        this.checkBoxHabilitarEdicao.setVisible(b);
    }

    private void loadFormattedTextFieldPlaca() {
        try {
            MaskFormatter placaMask = new MaskFormatter("AAA-AAAA");
            placaMask.install(tfPlaca);
        } catch (ParseException ex) {
            Logger.getLogger(Formulario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void configureJTablePneu() {

        pneuTableList.setModel(tModel);

        TableColumn statusColumn = pneuTableList.getColumnModel().getColumn(0);
        statusColumn.setHeaderValue("Cod. Pneu");

        TableColumn posicao = pneuTableList.getColumnModel().getColumn(1);
        posicao.setHeaderValue("Posição");

        this.eixo = pneuTableList.getColumnModel().getColumn(2);
        eixo.setHeaderValue("Eixo");

        TableColumn estado = pneuTableList.getColumnModel().getColumn(3);
        estado.setHeaderValue("Estado");

        pneuTableList.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    Point point = e.getPoint();
                    int currentRow = pneuTableList.rowAtPoint(point);
                    pneuTableList.setRowSelectionInterval(currentRow, currentRow);
                    if (checkBoxHabilitarEdicao.isSelected()) {
                        getPneuPopupMenu().show(e.getComponent(), e.getX(), e.getY());
                    }

                    if (!isEdicao) {
                        getPneuPopupMenu().show(e.getComponent(), e.getX(), e.getY());

                    }
                }
            }
        });
    }

    private JPopupMenu getPneuPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemRemove = new JMenuItem("Remover pneu selecionado");
        menuItemRemove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removeCurrentRow();
            }
        });

        popupMenu.add(menuItemRemove);
        return popupMenu;
    }

    private void removeCurrentRow() {
        int selectedRow = pneuTableList.getSelectedRow();
        listExcludedPneu.add(tModel.getPneuAtRow(selectedRow));
        listPneu = tModel.removeRow((String) tModel.getValueAt(selectedRow, 0));
        if (isEdicao) {
            for (int i = 0; i < listIncludedPneuDuringEdit.size(); i++) {
                for (int j = 0; j < listExcludedPneu.size(); j++) {
                    if (listExcludedPneu.get(j).getCodigo().equals(listIncludedPneuDuringEdit.get(i).getCodigo())) {
                        listIncludedPneuDuringEdit.remove(i);
                    }
                }
            }
        }
        setComboTipoAccordingToListPneu();
    }

    private void setComboTipoAccordingToListPneu() {
        if (!listPneu.isEmpty()) {
            comboTipo.setEnabled(false);
        } else {
            comboTipo.setEnabled(true);
        }
    }

    private void configureComboTipo() {

        comboTipo.setModel(new DefaultComboBoxModel(listTipo.toArray()));

        comboTipo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof TipoVeiculo) {
                    TipoVeiculo tipo = (TipoVeiculo) value;
                    setText(tipo.getNome());
                }
                return this;
            }
        });

    }

    private void loadListaTipo() {
        listTipo = DatabaseTipo.selectFullTipo();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panelVeiculo = new javax.swing.JPanel();
        labelPlaca = new javax.swing.JLabel();
        labelModelo = new javax.swing.JLabel();
        tfModelo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfKm = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfMarca = new javax.swing.JTextField();
        tfPlaca = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        tfPeso = new javax.swing.JTextField();
        labelTipo = new javax.swing.JLabel();
        comboTipo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        tfAno = new javax.swing.JTextField();
        panelPneu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pneuTableList = new javax.swing.JTable();
        incluirPneu = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        panelImagemVeiculo = new javax.swing.JPanel();
        btIncluirImagem = new javax.swing.JButton();
        imagePanel1 = new graphics.ImagePanel();
        checkBoxHabilitarEdicao = new javax.swing.JCheckBox();
        btCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Veículo"));

        panelVeiculo.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados principais"));

        labelPlaca.setText("Placa:");

        labelModelo.setText("Modelo:");

        jLabel1.setText("Km:");

        tfKm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfKmActionPerformed(evt);
            }
        });

        jLabel2.setText("Marca:");

        tfPlaca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPlacaActionPerformed(evt);
            }
        });

        jLabel3.setText("Peso:");

        labelTipo.setText("Tipo:");

        comboTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTipoItemStateChanged(evt);
            }
        });
        comboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoActionPerformed(evt);
            }
        });

        jLabel4.setText("Ano:");

        javax.swing.GroupLayout panelVeiculoLayout = new javax.swing.GroupLayout(panelVeiculo);
        panelVeiculo.setLayout(panelVeiculoLayout);
        panelVeiculoLayout.setHorizontalGroup(
            panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVeiculoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                        .addComponent(labelTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                        .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelVeiculoLayout.createSequentialGroup()
                                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelModelo)
                                    .addComponent(jLabel2)
                                    .addComponent(labelPlaca))
                                .addGap(19, 19, 19)
                                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfModelo)
                                    .addComponent(tfMarca)
                                    .addComponent(tfPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelVeiculoLayout.createSequentialGroup()
                                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel4))
                                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(tfAno))
                                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfKm)
                                            .addComponent(tfPeso))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelVeiculoLayout.setVerticalGroup(
            panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVeiculoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelPlaca)
                    .addComponent(tfPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                        .addComponent(tfAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelVeiculoLayout.createSequentialGroup()
                        .addGap(0, 1, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(14, 14, 14)
                        .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfKm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTipo))
                .addGap(22, 22, 22))
        );

        panelPneu.setBorder(javax.swing.BorderFactory.createTitledBorder("Painel de Pneus"));

        pneuTableList.setBorder(new javax.swing.border.MatteBorder(null));
        pneuTableList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        pneuTableList.setToolTipText("");
        jScrollPane1.setViewportView(pneuTableList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        incluirPneu.setText("Incluir novo pneu");
        incluirPneu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                incluirPneuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPneuLayout = new javax.swing.GroupLayout(panelPneu);
        panelPneu.setLayout(panelPneuLayout);
        panelPneuLayout.setHorizontalGroup(
            panelPneuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPneuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPneuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPneuLayout.createSequentialGroup()
                        .addGap(0, 249, Short.MAX_VALUE)
                        .addComponent(incluirPneu)))
                .addContainerGap())
        );
        panelPneuLayout.setVerticalGroup(
            panelPneuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPneuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(incluirPneu)
                .addGap(19, 19, 19))
        );

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        panelImagemVeiculo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Imagem"), "Imagem do veículo\n"));

        btIncluirImagem.setText("Incluir imagem");
        btIncluirImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIncluirImagemActionPerformed(evt);
            }
        });

        imagePanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        javax.swing.GroupLayout imagePanel1Layout = new javax.swing.GroupLayout(imagePanel1);
        imagePanel1.setLayout(imagePanel1Layout);
        imagePanel1Layout.setHorizontalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 247, Short.MAX_VALUE)
        );
        imagePanel1Layout.setVerticalGroup(
            imagePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 199, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelImagemVeiculoLayout = new javax.swing.GroupLayout(panelImagemVeiculo);
        panelImagemVeiculo.setLayout(panelImagemVeiculoLayout);
        panelImagemVeiculoLayout.setHorizontalGroup(
            panelImagemVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagemVeiculoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelImagemVeiculoLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btIncluirImagem))
        );
        panelImagemVeiculoLayout.setVerticalGroup(
            panelImagemVeiculoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelImagemVeiculoLayout.createSequentialGroup()
                .addComponent(imagePanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btIncluirImagem))
        );

        checkBoxHabilitarEdicao.setText("Habiltar edição");
        checkBoxHabilitarEdicao.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkBoxHabilitarEdicaoItemStateChanged(evt);
            }
        });

        btCancel.setText("Cancelar");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jLabel5.setText("icon");
        jLabel5.setToolTipText("Excluir Veículo");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelImagemVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPneu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(checkBoxHabilitarEdicao))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(checkBoxHabilitarEdicao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelVeiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelImagemVeiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar)
                            .addComponent(btCancel))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelPneu, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(jLabel5))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboTipoActionPerformed

    private void incluirPneuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_incluirPneuActionPerformed
        if (comboTipo.getSelectedIndex() != 0) {
            PneuForm p = new PneuForm(this);
            p.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um tipo antes de criar novos pneus !");
        }

    }//GEN-LAST:event_incluirPneuActionPerformed

    private void comboTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (evt.getItem() instanceof TipoVeiculo) {
                TipoVeiculo t = (TipoVeiculo) evt.getItem();
                tipoSelecionado = t.getId();

                if (t.getNome().equals("Veículo Passeio")) {
                    eixo.setHeaderValue("Orientação Pneu");
                    repaint();
                } else {
                    eixo.setHeaderValue("Eixo");
                    repaint();
                }

            }
        }
    }//GEN-LAST:event_comboTipoItemStateChanged

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (isValidData()) {
            Veiculo v = new Veiculo();
            v.setPlaca(tfPlaca.getText().replace("-", ""));
            v.setModelo(tfModelo.getText());
            v.setIdTipo(((TipoVeiculo) comboTipo.getSelectedItem()).getId());
            v.setPneus(listPneu);
            v.setImg(imagePanel1.extractBytes());
            v.setMarca(tfMarca.getText());
            v.setPeso(tfPeso.getText());
            v.setAno(tfAno.getText());

            if (!tfKm.getText().isEmpty()) {
                if (tfKm.getText().matches(ONLY_NUMBERS)) {
                    v.setKm(Double.valueOf(tfKm.getText()));
                }
            }
            if (isEdicao) {
                v.setId(veicIdFromConsulta);
                v.setExcludedPneus(listExcludedPneu);
                v.setPneus(listIncludedPneuDuringEdit);
                Database.updateVeiculo(v);
                JOptionPane.showMessageDialog(this, "Registro editado com sucesso!");

            } else {
                Database.insertNewVeiculo(v);
                JOptionPane.showMessageDialog(this, "Registro inserido com sucesso!");
            }

            eraseForm();
            callConsulta();
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void callConsulta() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll();
        topFrame.getContentPane().add(new Consulta());
        topFrame.pack();
        topFrame.setLocationRelativeTo(null);
    }

    private void eraseForm() {
        tfPlaca.setText(EMPTY_STRING);
        tfModelo.setText(EMPTY_STRING);
        comboTipo.setSelectedIndex(0);
        tfMarca.setText(EMPTY_STRING);
        tfKm.setText(EMPTY_STRING);
        tfAno.setText(EMPTY_STRING);
        tfPeso.setText(EMPTY_STRING);
        imagePanel1.erasePaint();
        listPneu = tModel.eraseTable();
    }

    private boolean isValidData() {
        if (tfPlaca.getText().replace("-", "").trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O preenchimento do campo 'Placa' é obrigatório !");
            return false;
        }

        if (listPneu.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O preenchimento da lista de pneus é obrigatório !");
            return false;
        }

        if (!tfKm.getText().isEmpty()) {
            if (!tfKm.getText().matches(ONLY_NUMBERS)) {
                JOptionPane.showMessageDialog(this, "O campo 'KM' deve ser preenchido apenas com valores numéricos !");
                return false;
            }
        }

        return true;
    }

    private void tfKmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfKmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfKmActionPerformed

    private void tfPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPlacaActionPerformed

    private void btIncluirImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIncluirImagemActionPerformed
        JFileChooser c = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Apenas imagem", "jpg", "png", "jpge");
        c.setFileFilter(filter);
        c.setDialogTitle("Selecione imagem");
        c.showOpenDialog(null);

        if (c.getSelectedFile() != null) {
            imagePanel1.repaintPanel(c.getSelectedFile());
        }
    }//GEN-LAST:event_btIncluirImagemActionPerformed

    private void checkBoxHabilitarEdicaoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkBoxHabilitarEdicaoItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            setComponentsEditable(true);
        } else {
            setComponentsEditable(false);
        };
    }//GEN-LAST:event_checkBoxHabilitarEdicaoItemStateChanged

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        int i = JOptionPane.showConfirmDialog((Component) null, "Deseja excluir este Veículo ?",
                "Exclusão de Veículo", JOptionPane.OK_CANCEL_OPTION);
        if (i == 0) {
            Database.deleteVeiculo(veicIdFromConsulta);
            eraseForm();
            callConsulta();
        }

    }//GEN-LAST:event_jLabel5MouseClicked

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        eraseForm();
        callConsulta();
    }//GEN-LAST:event_btCancelActionPerformed

    public void setComponentsEditable(boolean b) {
        for (Component c : getAllComponents()) {
            if (c instanceof JTextField || c instanceof JComboBox || c instanceof JButton) {
                c.setEnabled(b);
            }
        }
        setComboTipoAccordingToListPneu();
    }

    private List<Component> getAllComponents() {
        List<Component> l = new ArrayList<Component>();

        for (Component c : panelImagemVeiculo.getComponents()) {
            l.add(c);
        }
        for (Component c : panelPneu.getComponents()) {
            l.add(c);
        }
        for (Component c : panelVeiculo.getComponents()) {
            l.add(c);
        }
        for (Component c : this.getComponents()) {
            l.add(c);
        }

        return l;
    }

    public void insertPneuInTable(Pneu p) {
        listPneu = tModel.addPneu(p);
        if (isEdicao) {
            listIncludedPneuDuringEdit.add(p);
        }
    }

    private int tipoSelecionado = 0;
    private AxisPanel axisPanel1 = new AxisPanel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btIncluirImagem;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox checkBoxHabilitarEdicao;
    private javax.swing.JComboBox comboTipo;
    private graphics.ImagePanel imagePanel1;
    private javax.swing.JButton incluirPneu;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelModelo;
    private javax.swing.JLabel labelPlaca;
    private javax.swing.JLabel labelTipo;
    private javax.swing.JPanel panelImagemVeiculo;
    private javax.swing.JPanel panelPneu;
    private javax.swing.JPanel panelVeiculo;
    private javax.swing.JTable pneuTableList;
    private javax.swing.JTextField tfAno;
    private javax.swing.JTextField tfKm;
    private javax.swing.JTextField tfMarca;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfPeso;
    private javax.swing.JFormattedTextField tfPlaca;
    // End of variables declaration//GEN-END:variables

    private void modificarDesenhoEixosPorTipoVeiculo(int tipo, AxisPanel ap) {
        if (tipo == 0) {
            ap.erasePanelForNewSelection();
        }

        if (tipo == 1) {
            ap.createNormalCar();
        }

        if (tipo == 2) {
            ap.createForCarreta1Axis1Set();
        }

        if (tipo == 3) {
            ap.createForCarreta1Axis2Set();
        }

        if (tipo == 4) {
            ap.createForCarreta2Axis1Set();
        }

        if (tipo == 5) {
            ap.createForCarreta2Axis2Set();
        }
    }

    public List<Pneu> getListPneu() {
        if (listPneu == null) {
            listPneu = new ArrayList<Pneu>();
        }
        return listPneu;
    }

    public PneuTableModel gettModel() {
        return tModel;
    }

    public void settModel(PneuTableModel tModel) {
        this.tModel = tModel;
    }

    public int getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(int tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

    public JComboBox getComboTipo() {
        return comboTipo;
    }

    public void setComboTipo(JComboBox comboTipo) {
        this.comboTipo = comboTipo;
    }

    public ImagePanel getImagePanel1() {
        return imagePanel1;
    }

    public void setImagePanel1(ImagePanel imagePanel1) {
        this.imagePanel1 = imagePanel1;
    }

    public JTable getPneuTableList() {
        return pneuTableList;
    }

    public void setPneuTableList(JTable pneuTableList) {
        this.pneuTableList = pneuTableList;
    }

    public JTextField getTfKm() {
        return tfKm;
    }

    public void setTfKm(JTextField tfKm) {
        this.tfKm = tfKm;
    }

    public JTextField getTfMarca() {
        return tfMarca;
    }

    public void setTfMarca(JTextField tfMarca) {
        this.tfMarca = tfMarca;
    }

    public JTextField getTfModelo() {
        return tfModelo;
    }

    public void setTfModelo(JTextField tfModelo) {
        this.tfModelo = tfModelo;
    }

    public JTextField getTfPeso() {
        return tfPeso;
    }

    public void setTfPeso(JTextField tfPeso) {
        this.tfPeso = tfPeso;
    }

    public JFormattedTextField getTfPlaca() {
        return tfPlaca;
    }

    public void setTfPlaca(JFormattedTextField tfPlaca) {
        this.tfPlaca = tfPlaca;
    }

    public JTextField getTfAno() {
        return tfAno;
    }

    public void setTfAno(JTextField tfAno) {
        this.tfAno = tfAno;
    }

}
