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
import java.util.Map.Entry;

/**
 *
 * @author erick
 */
public class DataGrid {
    private final HashMap<String, ArrayList<Integer>> dataSet;
    private final HashMap<String, Double> giniSet;
    
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
    
    public Entry<String, Double> get_min_gini(){
        //actualizar_gini(target);
        Entry<String, Double> min = null;
        for(Entry<String, Double> entry: this.giniSet.entrySet()){
            if(min==null||min.getValue()>entry.getValue()){
                min = entry;
            }
        }
        //System.out.println(min.getKey()+" "+min.getValue());
        return min;
        
    }
    
    public DataGrid[] segmentarDatos(String target){
        actualizar_gini(target);
        
        //encontrar atributo con menor gini
        Entry<String, Double> min = get_min_gini();
        
        //crear datosPositivos y datosNegativos
        DataGrid datosPositivos = new DataGrid();
        DataGrid datosNegativos = new DataGrid();
        
        //agregar los atributos a datosP y datosN con valores(new arrayList)
        for(Entry<String, ArrayList<Integer>> entry: this.dataSet.entrySet()){
            if(!min.getKey().equals(entry.getKey())){
                datosPositivos.getDataSet().put(entry.getKey(), new ArrayList<Integer>());
                datosPositivos.getGiniSet().put(entry.getKey(), 0.0);
                
                datosNegativos.getDataSet().put(entry.getKey(), new ArrayList<Integer>());
                datosNegativos.getGiniSet().put(entry.getKey(), 0.0);
            }
        }
        
        //llenar los atributos a datosP y datosN
        for(int i=0;i<this.dataSet.get(min.getKey()).size();i++){
            //for que recorre los otros atributos
            for(Entry<String, ArrayList<Integer>> entry: this.dataSet.entrySet()){
                if(!min.getKey().equals(entry.getKey())){
                    if(this.dataSet.get(min.getKey()).get(i) == 1){
                        datosPositivos.getDataSet().get(entry.getKey()).add(this.dataSet.get(entry.getKey()).get(i));
                    }else{
                        datosNegativos.getDataSet().get(entry.getKey()).add(this.dataSet.get(entry.getKey()).get(i));
                    }
                }
            }
        }
        
        DataGrid[] dataSegmentada = {datosPositivos, datosNegativos};
        return dataSegmentada;
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
 
    public int contarPositivos (String target){ 
        ArrayList<Integer> datos=dataSet.get(target);
        int contador=0;
        for (int d:datos)contador+=d;
        return contador;
    }
    
    public int contarNegativos(String target){
        ArrayList<Integer> datos=dataSet.get(target);
        int contadorPositivos=contarPositivos(target);
        return datos.size()-contadorPositivos;
    }
    public HashMap<String, ArrayList<Integer>> getDataSet() {
        return dataSet;
    }

    public HashMap<String, Double> getGiniSet() {
        return giniSet;
    }
    
    
    
}
