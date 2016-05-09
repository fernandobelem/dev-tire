package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Pneu;
import model.Veiculo;

public class Database {

    public static void main(String args[]) throws Exception {
        getConnection();

    }

    public static List<Veiculo> selectFullVeiculos() {
        List<Veiculo> list = new ArrayList<Veiculo>();
        Connection c = null;

        try {
            c = getConnection();
            Statement st = c.createStatement();

            String queryVeiculo = "SELECT * FROM VEICULO;";

            ResultSet rs = st.executeQuery(queryVeiculo);
            while (rs.next()) {

                Veiculo v = new Veiculo();
                v.setId(rs.getInt(1));
                v.setPlaca(rs.getString(2));
                v.setModelo(rs.getString(3));
                v.setIdTipo(rs.getInt(4));
                v.setKm(rs.getDouble(5));
                v.setImg(rs.getBytes(6));
                v.setMarca(rs.getString(7));
                v.setPeso(rs.getString(8));
                v.setAno(rs.getString(9));

                String queryPneu = "SELECT * FROM PNEU WHERE ID_VEICULO = " + v.getId() + ";";
                Statement st2 = c.createStatement();
                ResultSet rs2 = st2.executeQuery(queryPneu);
                while (rs2.next()) {
                    Pneu p = new Pneu();
                    p.setId(rs2.getInt(1));
                    p.setIdVeiculo(rs2.getInt(2));
                    p.setCodigo(rs2.getString(3));
                    p.setPosicao(rs2.getString(4));
                    p.setEstado(rs2.getString(5));
                    p.setEixo(rs2.getString(6));
                    v.getPneus().add(p);
                }

                list.add(v);
            }

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    public static List<Pneu> selectAllPneuByCod(String cod) {
        List<Pneu> list = new ArrayList<Pneu>();

        Connection c = null;

        try {
            c = getConnection();
            Statement st = c.createStatement();

            String query = "SELECT * FROM PNEU WHERE CODIGO = '" + cod + "';";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Pneu p = new Pneu();
                p.setId(rs.getInt(1));
                p.setIdVeiculo(rs.getInt(2));
                p.setCodigo(rs.getString(3));
                p.setPosicao(rs.getString(4));
                p.setEstado(rs.getString(5));
                p.setEixo(rs.getString(6));
                list.add(p);
            }

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static List<Pneu> selectAllPneuByIdVeic(int id) {
        List<Pneu> list = new ArrayList<Pneu>();

        Connection c = null;

        try {
            c = getConnection();
            Statement st = c.createStatement();

            String query = "SELECT * FROM PNEU WHERE ID_VEICULO = '" + id + "';";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Pneu p = new Pneu();
                p.setId(rs.getInt(1));
                p.setIdVeiculo(rs.getInt(2));
                p.setCodigo(rs.getString(3));
                p.setPosicao(rs.getString(4));
                p.setEstado(rs.getString(5));
                p.setEixo(rs.getString(6));
                list.add(p);
            }

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static Veiculo selectVeiculo(String placa, String cod) {

        Veiculo v = new Veiculo();

        Connection c = null;

        try {
            c = getConnection();
            Statement st = c.createStatement();

            String queryVeiculo = null;
            if (!placa.isEmpty()) {
                queryVeiculo = "SELECT * FROM VEICULO WHERE PLACA = '" + placa.toUpperCase() + "';";
            } else {
                queryVeiculo = "SELECT * FROM VEICULO WHERE ID = (SELECT ID_VEICULO FROM PNEU WHERE CODIGO = '" + cod.toUpperCase() + "');";
            }

            ResultSet rs = st.executeQuery(queryVeiculo);
            while (rs.next()) {
                v.setId(rs.getInt(1));
                v.setPlaca(rs.getString(2));
                v.setModelo(rs.getString(3));
                v.setIdTipo(rs.getInt(4));
                v.setKm(rs.getDouble(5));
                v.setImg(rs.getBytes(6));
                v.setMarca(rs.getString(7));
                v.setPeso(rs.getString(8));
                v.setAno(rs.getString(9));
            }

            String queryPneu = "SELECT * FROM PNEU WHERE ID_VEICULO = " + v.getId() + ";";

            rs = st.executeQuery(queryPneu);
            while (rs.next()) {
                Pneu p = new Pneu();
                p.setId(rs.getInt(1));
                p.setIdVeiculo(rs.getInt(2));
                p.setCodigo(rs.getString(3));
                p.setPosicao(rs.getString(4));
                p.setEstado(rs.getString(5));
                p.setEixo(rs.getString(6));
                v.getPneus().add(p);
            }

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return v;

    }

    public static void insertNewVeiculo(Veiculo v) {
        Connection c = null;
        try {
            c = getConnection();

            //VEICULO
            v.setId(getNextVeiculoId(c));

            String sql = "INSERT INTO VEICULO (ID, PLACA, MODELO, ID_TIPO, KM, IMAGEM, MARCA, PESO, ANO) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, v.getId());
            st.setString(2, v.getPlaca().trim().toUpperCase());
            st.setString(3, v.getModelo());
            st.setInt(4, v.getIdTipo());
            st.setDouble(5, v.getKm());
            st.setBytes(6, v.getImg());
            st.setString(7, v.getMarca());
            st.setString(8, v.getPeso());
            st.setString(9, v.getAno());
            st.executeUpdate();

            //PNEUS
            for (Pneu p : v.getPneus()) {
                insertPneu(p, v.getId(), c);
            }

            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void insertPneu(Pneu p, int veicId, Connection c) {
        try {
            String pneuSql = "INSERT INTO PNEU (ID, ID_VEICULO, CODIGO, POSICAO, ESTADO, EIXO) VALUES (?,?,?,?,?,?)";
            PreparedStatement st = c.prepareStatement(pneuSql);
            st.setInt(1, getNextPneuId(c));
            st.setInt(2, veicId);
            st.setString(3, p.getCodigo().toUpperCase());
            st.setString(4, p.getPosicao());
            st.setString(5, p.getEstado());
            st.setString(6, p.getEixo());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int getNextVeiculoId(Connection c) throws SQLException {

        Statement st = c.createStatement();

        return st.executeQuery("SELECT MAX(ID)+1 FROM VEICULO").getInt(1);

    }

    private static int getNextPneuId(Connection c) throws SQLException {

        Statement st = c.createStatement();

        return st.executeQuery("SELECT MAX(ID)+1 FROM PNEU").getInt(1);

    }

    public static void updateVeiculo(Veiculo v) {
        Connection c = null;
        try {
            c = getConnection();
            Statement st = c.createStatement();
            String sql = "UPDATE VEICULO SET PLACA = ?, MODELO = ?, ID_TIPO = ?, KM = ?, IMAGEM = ?, MARCA = ?, ANO = ?, PESO = ? WHERE ID = " + v.getId();

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getModelo());
            ps.setInt(3, v.getIdTipo());
            ps.setDouble(4, v.getKm());
            ps.setBytes(5, v.getImg());
            ps.setString(6, v.getMarca());
            ps.setString(7, v.getAno());
            ps.setString(8, v.getPeso());

            ps.executeUpdate();

            //excluded
            System.out.println("v.getExcludedPneus().size() = " + v.getExcludedPneus().size());
            for (Pneu p : v.getExcludedPneus()) {
                System.out.println("Deleted: " + p.toString());
                st.execute("DELETE FROM PNEU WHERE ID = " + p.getId());
            }

            System.out.println(v.getPneus().size());
            for (Pneu p : v.getPneus()) {
                insertPneu(p, v.getId(), c);
            }

            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static Map<String, Pneu> selectAllPneuByVeicId(int id) {
        Connection c = null;

        Map<String, Pneu> map = new HashMap<String, Pneu>();
        try {
            c = getConnection();
            Statement st = c.createStatement();

            String query = "SELECT * FROM PNEU WHERE ID = '" + id + "';";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Pneu p = new Pneu();
                p.setId(rs.getInt(1));
                p.setIdVeiculo(rs.getInt(2));
                p.setCodigo(rs.getString(3));
                p.setPosicao(rs.getString(4));
                p.setEstado(rs.getString(5));
                p.setEixo(rs.getString(6));

                map.put(p.getCodigo(), p);
            }

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    private static boolean existsPneuId(int id) {

        Connection c = null;
        int queryId = -1;
        try {
            c = getConnection();
            Statement st = c.createStatement();
            Pneu p = new Pneu();
            String query = "SELECT * FROM PNEU WHERE ID = '" + id + "';";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                p.setId(rs.getInt(1));
            }

            queryId = p.getId();

            c.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return queryId != -1;
    }

    public static void deleteVeiculo(int id) {
        Connection c = null;
        try {
            c = getConnection();
            Statement st = c.createStatement();
            String sqlVeiculo = "DELETE FROM VEICULO WHERE ID = " + id + ";";
            st.execute(sqlVeiculo);

            String sqlPneu = "DELETE FROM PNEU WHERE ID_VEICULO = " + id + ";";
            st.execute(sqlPneu);
            c.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Database.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection c;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:C:\\GP\\gpbase.sqlite");
//        c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\fobm\\Desktop\\Documentos\\TIRE\\GP\\gpbase.sqlite");
//        System.out.println("Conn database successfully");
        return c;
    }

}
