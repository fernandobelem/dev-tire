/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Form.Consulta;
import Form.Formulario;
import Form.TipoVeiculoForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author fobm
 */
public class Main extends JFrame {

    private JMenu menuArquivo = new JMenu();
    private JMenuBar menuBarPrincipal = new JMenuBar();
    private JMenuItem menuItemConsultar = new JMenuItem();
    private JMenuItem menuItemPneu = new JMenuItem();
    private JMenuItem menuItemSair = new JMenuItem();
    private JMenuItem menuItemTipoVeiculo = new JMenuItem();
    private JMenuItem menuItemVeiculo = new JMenuItem();
    private JMenu menuSobre = new JMenu();
    private JMenu subMenuIncluir = new JMenu();
    private JMenuItem subMenuSobre = new JMenuItem();

    public Main() {
        super("Gerenciador de Veículos");
        try {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(758, 486);
            setResizable(false);
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            initMenus();
            
            
            getContentPane().add(new Consulta());
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void main(String[] args) {
        new Main();      
    }

    private void initMenus() {
        setJMenuBar(menuBarPrincipal);
        menuBarPrincipal.setBorder(BorderFactory.createEtchedBorder());
        menuArquivo.setText("Arquivo");

        menuItemConsultar.setText("Nova consulta");
        menuItemConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemConsultarActionPerformed(evt);
            }
        });
        menuArquivo.add(menuItemConsultar);

        subMenuIncluir.setText("Incluir novo");

        menuItemVeiculo.setText("Veículo");
        menuItemVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemVeiculoActionPerformed(evt);
            }
        });
        subMenuIncluir.add(menuItemVeiculo);

        menuItemTipoVeiculo.setText("Tipo de veículo");
        menuItemTipoVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemTipoVeiculoActionPerformed(evt);
            }
        });
        subMenuIncluir.add(menuItemTipoVeiculo);

        menuItemPneu.setText("Pneu");

        menuArquivo.add(subMenuIncluir);

        menuItemSair.setText("Sair");
        menuItemSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSairActionPerformed(evt);
            }
        });
        menuArquivo.add(menuItemSair);

        menuBarPrincipal.add(menuArquivo);

        menuSobre.setText("Sobre");
        subMenuSobre.setText("Sobre este aplicativo");
        menuSobre.add(subMenuSobre);
        subMenuSobre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JOptionPane.showMessageDialog(null, "Software desenvolveido por Fernando Belém. \n"
                        + "Contato: fernando.abelem@gmail.com \n");
            }
        });

        menuBarPrincipal.add(menuSobre);
    }

    private void menuItemSairActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);  
    }

    private void menuItemConsultarActionPerformed(java.awt.event.ActionEvent evt) {
        getContentPane().removeAll();
        getContentPane().add(new Consulta());
        pack();
    }

    private void menuItemTipoVeiculoActionPerformed(java.awt.event.ActionEvent evt) {
        getContentPane().removeAll();
        getContentPane().add(new TipoVeiculoForm());
        pack();
    }

    private void menuItemVeiculoActionPerformed(java.awt.event.ActionEvent evt) {
        getContentPane().removeAll();
        getContentPane().add(new Formulario());
        pack();
    }
}
