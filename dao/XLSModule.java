/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import model.Veiculo;

/**
 *
 * @author fobm
 */
public class XLSModule {
     public static void main(String[] args) throws BiffException, IOException, WriteException {
         
        Workbook workbook = Workbook.getWorkbook(new File("c:\\gp\\oleo_2016.xls"));
        Sheet sheet = workbook.getSheet(0);
       
        
         for (int i = 1; i < sheet.getRows(); i++) {
             Veiculo v = new Veiculo();
             v.setPlaca(sheet.getCell(0, i).getContents());
             v.setAno(sheet.getCell(1, i).getContents());
             v.setModelo(sheet.getCell(2, i).getContents());
       
             if(sheet.getCell(3,i) != null){
                String kmString = sheet.getCell(3,1).getContents();
                double km = Double.parseDouble(kmString.replace(",","."));
                v.setKm(km);
             }
             
             v.setMarca(sheet.getCell(4, i).getContents());
             
             Database.insertNewVeiculo(v);
         }
        workbook.close();
    }
}
