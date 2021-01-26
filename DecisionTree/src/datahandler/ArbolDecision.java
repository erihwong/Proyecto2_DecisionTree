/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datahandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jorge1504
 */
public class ArbolDecision{
    private class Node {
        private DataGrid datosPacientes;
        private String atributoDominante;
        private String decision;
        private Node left, right, parent;
        public Node(){
            parent=null;
            right=null;
            left=null;
            decision="";
            atributoDominante="";
            datosPacientes=null;
        }
        public Node(DataGrid datos){
            parent=null;
            right=null;
            left=null;
            datosPacientes=datos;
        }
        public Node(DataGrid datos, Node parent){
            this.parent=parent;
            datosPacientes=datos;
            right=null;
            left=null;
        }
        
      
        public void segmentar(Node parent,int level){
            //segmentar los datos del nnodo padres
            DataGrid[] divisiones= parent.datosPacientes.segmentarDatos("DEATH_EVENT");
            //actualizar gini de  divisiones
            divisiones[0].actualizar_gini("DEATH_EVENT");
            divisiones[1].actualizar_gini("DEATH_EVENT");
            
            //crear los hijo con los dataGrid pertinentes y la conexion a parent
            Node trueNode= new Node(divisiones[0],parent);
            Node falseNode= new Node(divisiones[1],parent);
            //set atributo dominante
            trueNode.atributoDominante=divisiones[0].get_min_gini().getKey();
            falseNode.atributoDominante=divisiones[1].get_min_gini().getKey();
            //System.out.println("division left: "+trueNode.atributoDominante+" right: "+falseNode.atributoDominante/*+" level: "+level+" "*/+parent.datosPacientes.getDataSet().keySet().size());
            //configurar hijo derecho e izquierdo del padre
            parent.left=trueNode;
            parent.right=falseNode;
            //si no son nodo hoja seguir segmentanndo
            if (!trueNode.atributoDominante.equals("DEATH_EVENT"))segmentar(trueNode,level+1);
            else {//si esto se da entonces es un nodo de desicion y se debe definir el artibuto decision
                double porcentajePositivo=(double)trueNode.datosPacientes.contarPositivos("DEATH_EVENT")/trueNode.datosPacientes.getDataSet().size();
                if (porcentajePositivo>=0.5)trueNode.decision="SI";
                else trueNode.decision="NO";
                
            }
            if (!falseNode.atributoDominante.equals("DEATH_EVENT"))segmentar(falseNode,level+1);
            
            else {//si esto se da entonces es un nodo de desicion y se debe definir el artibuto decision
                double porcentajePositivo=(double)falseNode.datosPacientes.contarPositivos("DEATH_EVENT")/falseNode.datosPacientes.getDataSet().size();
                if (porcentajePositivo>=0.5)falseNode.decision="SI";
                else falseNode.decision="NO";
                
            }
            
        }
        public boolean esHoja(){
            return right==null && left==null;
        }
    }
    private Node root;
    public ArbolDecision(String pathToFile) throws IOException{
        DataGrid dg= new DataGrid();
        dg.cargarDatos(pathToFile);
        dg.actualizar_gini("DEATH_EVENT");
        root= new Node(dg);
        root.atributoDominante=dg.get_min_gini().getKey();
        //System.out.println("dom");
        //System.out.println(root.atributoDominante);
        root.segmentar(root,0);
    }
    
    private Map<String,Integer> row_to_hashmap(int row){
        //System.out.println(row);
        Map <String, Integer> fila = new HashMap<>();
        DataGrid dg=root.datosPacientes;
        if (row<0 ||row>=dg.getDataSet().get("DEATH_EVENT").size())return null;
        for (String key:dg.getDataSet().keySet()){
            fila.put(key,dg.getDataSet().get(key).get(row));
        }
        return fila;
        
    }
    private boolean testRow(int row){
        Map <String, Integer> fila_map =row_to_hashmap(row);
        //System.out.println("\n\n");
        return test_hash_map(root,fila_map);
        
    }
    private boolean test_hash_map(Node n,Map <String, Integer> fila){
        if (n.atributoDominante.equals("DEATH_EVENT")){//comparar que la comparcion se correcta
            String decision=n.decision;
            int decision_int=0;
            if(decision.equals("SI"))decision_int=1;
            if(fila.get("DEATH_EVENT")==decision_int)return true;
            return false;
        }
        //System.out.println(fila);
        int decision=fila.get(n.atributoDominante);
        if (decision==1)return test_hash_map(n.left,fila);
        return test_hash_map(n.right,fila);
        
    }
    public double run_test(){
        int contador=0;
        int filas=root.datosPacientes.getDataSet().get("DEATH_EVENT").size();
        for (int i=0;i<filas;i++){//loop de 0 hasta el numero de filas en el dataset
            if(testRow(i))contador++;
        }
        return (double)contador /filas;
    }
    
    /*
    public void print(){
        print(root);
    }*/
    public void printTree(){
        printTree(root);
        
    }
    private void printTree (Node n){
        System.out.print(n.atributoDominante+", ");
        if(n.esHoja()){
            return;
        }
        printTree(n.left);
        printTree(n.right);
        
        
    }
    
    
        
        
        
        
        
}
    

