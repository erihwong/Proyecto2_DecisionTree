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
import java.util.Collections;
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
    
    public DataGrid segmentarDatos(String target){
        actualizar_gini(target);
        double min = Collections.min(this.giniSet.values());
        String minAtr = "";
        for(String str: this.giniSet.keySet()){
            if(this.giniSet.get(str) == min) minAtr = str;
        }
        System.out.println(minAtr);
        return null;
    }
    
    public void actualizar_gini(String target){
        for (String str:dataSet.keySet()){
            giniSet.put(str, calcular_gini(str,target));
        }
    }
    
    private double calcular_gini(String columna,String target){
        ArrayList<Integer> datosCol = dataSet.get(columna);
        ArrayList<Integer> datosTarget = dataSet.get(target);
        
        final double totales=datosCol.size();
        
        //gini hoja
        if (columna.compareTo(target)==0){
            double contador_si=0;
            
            for(Integer integ: datosCol){
                if (integ == 1) contador_si+=1;
            }
            
            double contador_no = totales - contador_si;
            return 1-Math.pow(contador_si/totales,2)-Math.pow(contador_no/totales,2);
        }
        
        //gini nodo
        double contador_si_si=0;
        double contador_si_no=0;
        double contador_no_si=0;
        double contador_no_no=0;
        
        for (int i=0;i<datosCol.size();i++){
            if (datosCol.get(i)==1 && datosTarget.get(i)==1)contador_si_si++;
            else if (datosCol.get(i)==1 && datosTarget.get(i)==0)contador_si_no++;
            else if (datosCol.get(i)==0 && datosTarget.get(i)==1)contador_no_si++;
            else contador_no_no++;
        }
        
        final double totales_si=contador_si_si+contador_si_no;
        final double totales_no=contador_no_si+contador_no_no;

        double gini_izq=1-Math.pow(contador_si_si/totales_si,2)-Math.pow(contador_si_no/totales_si,2);
        double gini_der=1-Math.pow(contador_no_si/totales_no,2)-Math.pow(contador_no_no/totales_no,2);
        return gini_izq*(totales_si/totales)+gini_der*(totales_no/totales);
        
    }
 
    public HashMap<String, ArrayList<Integer>> getDataSet() {
        return dataSet;
    }

    public HashMap<String, Double> getGiniSet() {
        return giniSet;
    }
    
    
    
}
