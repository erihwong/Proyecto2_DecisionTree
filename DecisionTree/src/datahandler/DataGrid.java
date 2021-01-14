/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datahandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author erick
 */
public class DataGrid {
    private HashMap<String, ArrayList<Integer>> dataSet;
    private HashMap<String, Double> giniSet;
    
    public DataGrid(){
        dataSet = new HashMap<String, ArrayList<Integer>>();
        giniSet = new HashMap<String, Double>();
    } 
    
    public void cargarDatos(String fileName) throws IOException{
        String line;
        try(BufferedReader reader= new BufferedReader(new FileReader("src/"+fileName+".csv"))){
            line = reader.readLine();
            String[] header = line.split(",");
            
            for(String str: header){
                ArrayList<Integer> arrInt = new ArrayList<Integer>();
                this.dataSet.put(str, arrInt);
                this.giniSet.put(str, 0.0);
            }
            
            while((line = reader.readLine()) != null){
                String[] row = line.split(",");
                int cont=0;
                for(String str: header){
                    this.dataSet.get(str).add(Integer.parseInt(row[cont]));
                    cont++;
                }
                
            }
        }
        
    }

    private double calcular_gini(String columna,String target){
        //calcular gini
        return 0.0;
    }
    public void actualizar_gini(String target){
        for (String str:dataSet.keySet()){
            giniSet.put(str, calcular_gini(str,target));
        }
        
        
    }
    
    public HashMap<String, ArrayList<Integer>> getDataSet() {
        return dataSet;
    }

    public HashMap<String, Double> getGiniSet() {
        return giniSet;
    }
    
    
    
}
