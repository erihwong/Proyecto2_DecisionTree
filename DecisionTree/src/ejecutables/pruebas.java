/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutables;

import datahandler.DataGrid;
import java.io.IOException;

/**
 *
 * @author erick
 */
public class pruebas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DataGrid pacientesGrid = new DataGrid();
        
        try{
            pacientesGrid.cargarDatos("datos_booleanos");
        }catch(IOException notFound){
            System.out.println("Error: File not Found.");
        }
        
        pacientesGrid.getDataSet().entrySet().forEach((me) -> {
            System.out.println("KEY: "+me.getKey()+" VALUE: "+me.getValue());
        });
        
        
    }
}
