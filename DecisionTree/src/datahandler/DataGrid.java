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
    public HashMap<String, ArrayList<Integer>> dataSet;
    public HashMap<String, Integer> giniSet;
    
    public DataGrid(){
        dataSet = new HashMap<String, ArrayList<Integer>>();
        giniSet = new HashMap<String, Integer>();
    }
    
    public void cargarDatos(String fileName) throws IOException{
        String line;
        try(BufferedReader reader= new BufferedReader(new FileReader("src/"+fileName+".csv"))){
            line = reader.readLine();
            String[] header = line.split(",");
            
            for(String str: header){
                ArrayList<Integer> arrInt = new ArrayList<Integer>();
                this.dataSet.put(str, arrInt);
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
}