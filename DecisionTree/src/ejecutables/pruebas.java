/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecutables;

import datahandler.ArbolDecision;
import datahandler.DataGrid;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erick
 */
public class pruebas {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
            // TODO code application logic here
            /*DataGrid pacientesGrid = new DataGrid();
            
            try{
            pacientesGrid.cargarDatos("datos_booleanos");
            }catch(IOException notFound){
            System.out.println("Error: File not Found.");
            }
            pacientesGrid.actualizar_gini("DEATH_EVENT");
            pacientesGrid.getGiniSet().entrySet().forEach((me) -> {
            System.out.println("KEY: "+me.getKey()+" VALUE: "+me.getValue());
            });*/
            ArbolDecision tree= new ArbolDecision("datos_booleanos");
            //tree.printTree();
            System.out.println(tree.run_test());
        
        
        
    }
}
