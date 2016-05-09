/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Veiculo;

/**
 *
 * @author fobm
 */
public class VeiculoTableModel extends AbstractTableModel{
    
    private ArrayList<Veiculo> list = new ArrayList<Veiculo>();

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo v = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return v.getPlaca();
            case 1:
                return v.getMarca();
            case 2:
                return v.getModelo();
            case 3:
                return v.getKm();
            case 4: return v.getPeso();
              
        }
        return null;
    }
    
    public Veiculo getVeiculoAt(int row){
        Veiculo v = list.get(row);
        return v;
    }

    public List<Veiculo> addVeiculo(Veiculo v) {
        list.add(v);
        this.fireTableDataChanged();
        return list;
    }

    public List<Veiculo> removeRow(String placa) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getPlaca().equals(placa)) {
                list.remove(i);
            }
        }
        this.fireTableDataChanged();
        return list;
    }

    public List<Veiculo> eraseTable() {
        list = new ArrayList<Veiculo>();
        this.fireTableDataChanged();
        return list;
    }
    
}
