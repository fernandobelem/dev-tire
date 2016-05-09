/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Pneu;

/**
 *
 * @author fobm
 */
public class PneuTableModel extends AbstractTableModel {

    private ArrayList<Pneu> list = new ArrayList<Pneu>();

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pneu p = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getCodigo();
            case 1:
                return p.getPosicao();
            case 2:
                return p.getEixo();
            case 3:
                return p.getEstado();
        }
        return null;
    }
    
    public Pneu getPneuAtRow(int rowIndex){
        return list.get(rowIndex);
    }

    public List<Pneu> addPneu(Pneu p) {
        list.add(p);
        this.fireTableDataChanged();
        return list;
    }

    public List<Pneu> removeRow(String codigo) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getCodigo().equals(codigo)) {
                list.remove(i);
            }
        }
        this.fireTableDataChanged();
        return list;
    }

    public List<Pneu> eraseTable() {
        list = new ArrayList<Pneu>();
        this.fireTableDataChanged();
        return list;
    }

}
