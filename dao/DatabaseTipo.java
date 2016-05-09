/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.TipoVeiculo;

/**
 *
 * @author fobm
 */
public class DatabaseTipo {

    public static void main(String args[]) {

    }

    public static TipoVeiculo selecteTipo(int id) {
        Connection c = null;
        TipoVeiculo t = new TipoVeiculo();
        try {
            c = Database.getConnection();
            Statement st = c.createStatement();
            String query = "SELECT * FROM TIPO WHERE ID = " + id + ";";

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                t.setId(rs.getInt(1));
                t.setNome(rs.getString(2));
                t.setNumeroEixos(rs.getInt(3));
                t.setRodasPorEixo(rs.getInt(4));
                t.setImg(rs.getBytes(5));
            }
            c.close();

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        return t;
    }

    public static List<TipoVeiculo> selectFullTipo() {
        Connection c = null;
        List<TipoVeiculo> listTipo = null;
        try {
            c = Database.getConnection();
            Statement st = c.createStatement();
            String query = "SELECT * FROM TIPO;";

            listTipo = new ArrayList<TipoVeiculo>();
            listTipo.add(new TipoVeiculo("Selecione"));

            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                TipoVeiculo t = new TipoVeiculo();
                t.setId(rs.getInt(1));
                t.setNome(rs.getString(2));
                t.setNumeroEixos(rs.getInt(3));
                t.setRodasPorEixo(rs.getInt(4));

//                Blob b = rs.getBlob("IMG");
//                t.setImg(b.getBytes(1L, (int)b.length()));
                t.setImg(rs.getBytes(5));
                listTipo.add(t);
            }
            c.close();

        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        return listTipo;
    }

    public static byte[] selectTipoImage(String nome) {
        byte[] img = null;

        try {
            String sql = "SELECT IMG FROM TIPO WHERE NOME = '" + nome + "';";
            Statement st = Database.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                img = rs.getBytes(1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseTipo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;

    }

    public static byte[] selectTipoImage(int id) {
        byte[] img = null;

        try {
            String sql = "SELECT IMG FROM TIPO WHERE id = '" + id + "';";
            Statement st = Database.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                img = rs.getBytes(1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseTipo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;

    }

    private static int selectNextId(Connection c) throws SQLException {
        Statement st = c.createStatement();
        return st.executeQuery("SELECT MAX(ID)+1 FROM TIPO").getInt(1);
    }

    public static void insertNewTipo(TipoVeiculo tipo) {
        Connection c = null;
        try {
            c = Database.getConnection();
            String sql = "INSERT INTO TIPO (ID, NOME, QUANTIDADE_EIXO, QUANTIDADE_PNEUS_EIXO, IMG) VALUES (?,?,?,?,?)";
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, selectNextId(c));
            st.setString(2, tipo.getNome());
            st.setInt(3, tipo.getNumeroEixos());
            st.setInt(4, tipo.getRodasPorEixo());
            st.setBytes(5, tipo.getImg());

            st.executeUpdate();

            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void updateTipo(TipoVeiculo tipo) {
        Connection c = null;
        try {
            c = Database.getConnection();
            Statement st = c.createStatement();
            String sql = "UPDATE TIPO SET "
                    + "NOME = '" + tipo.getNome() + "',"
                    + "QUANTIDADE_EIXO = " + tipo.getNumeroEixos() + ","
                    + "QUANTIDADE_PNEUS_EIXO = " + tipo.getRodasPorEixo()
                    + "IMG = " + tipo.getImg()
                    + "WHERE ID = " + tipo.getId() + ";";
            st.executeUpdate(sql);

            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void deleteTipo(int id) {
        Connection c = null;
        try {
            c = Database.getConnection();
            Statement st = c.createStatement();
            String sql = "DELETE FROM TIPO WHERE ID = " + id + ";";
            st.execute(sql);
            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
