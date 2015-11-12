/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.util.LinkedList;

/**
 *
 * @author fede
 */
public class BaseDeDatos {

    //nombre de la BD
    private String nombreBase;

    //lista de tablas de la BD
    private LinkedList<Tabla> tablas;

    /*LinkedList de enteros que indican las diferencias entre tablas con un mismo nombre
     * Comparacion de columnas de tablas, 
     * 0 = columnas del mismo nombre, identicas
     * 1 = columnas del mismo nombre, distintas
     * 2 = la columna no existe en la otra tabla
     */
    private LinkedList<Integer> difTablas;
    
    private LinkedList<Procedimiento> procedimientos;

    /*LinkedList de enteros que indican las diferencias entre procedimientos con un mismo nombre
     * Comparacion de columnas de tablas, 
     * 0 = procedimientos del mismo nombre, identicos
     * 1 = procedimientos del mismo nombre, distintos
     * 2 = procedimiento no existe en la otra base
     */
    private LinkedList<Integer> difProcedimientos;

    //constructor de clase
    public BaseDeDatos(String nombreBase) {
        this.nombreBase = nombreBase;
        this.tablas = new LinkedList();
        this.difTablas = new LinkedList();
        this.procedimientos = new LinkedList();
        this.difProcedimientos=new LinkedList();
    }

    
    /*
     * cada vez que se agrega una tabla, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * bases de datos, en cuanto a tablas
     */
    public void agregarTabla(Tabla t) {
        this.getTablas().addLast(t);
        this.getDifTablas().addLast(2);
    }

    public void agregarProcedimiento(Procedimiento p) {
        this.getProcedimientos().addLast(p);
        this.getDifProcedimientos().addLast(2);
    }
    
    

    public boolean equals(BaseDeDatos bd2) {
        int i, j;
        boolean aux, aux2;
        boolean res = true;
        boolean mismoNombre;

        if (this.getTablas().size() != bd2.getTablas().size()) {
            res = false;
        }

        for (i = 0; i < this.getTablas().size(); i++) {
            for (j = 0; j < bd2.getTablas().size(); j++) {
                Tabla t1=this.getTablas().get(i);
                Tabla t2=bd2.getTablas().get(j);
                mismoNombre = t1.getNombre().equals(t2.getNombre());
                //si la i-esima tabla de la 1er bd tiene el mismo nombre que 
                //la j-esima tabla de la 2da bd, comparamos las tablas
                if (mismoNombre) {
                    //comparamos sus tablas
                    aux = t1.compararTablas(t2);
                    //comparamos los triggers de las tablas
                    aux2= t1.comparadorTriggers(t2);
         
                    //seteamos en 0 si se llaman igual y son identicas
                    if (aux && aux2) {
                        this.getDifTablas().set(i, 0);
                        bd2.getDifTablas().set(j, 0);
                    } else {
                        //seteamos en 1 si se llaman igual y son diferentes
                        this.getDifTablas().set(i, 1);
                        bd2.getDifTablas().set(j, 1);
                        res = false;
                    }
                    break;
                }
                //Si llego al final del ciclo, la i-esima tabla de la 1era base
                //no esta en la segunda
                if (j == bd2.getTablas().size() - 1) {
                    res = false;
                }
            }
        }
        
        if(this.getProcedimientos().size()!=bd2.getProcedimientos().size()){
            res=false;
        }
        
        for(i=0; i<this.getProcedimientos().size(); i++){
            for(j=0; j<bd2.getProcedimientos().size(); j++){
                mismoNombre=this.getProcedimientos().get(i).getNombre().equals(bd2.getProcedimientos().get(j).getNombre());
                if(mismoNombre){
                    Procedimiento p1=this.getProcedimientos().get(i);
                    Procedimiento p2=bd2.getProcedimientos().get(j);
                    aux=p1.compararProcedimientos(p2);
                    if(aux){
                        this.difProcedimientos.set(i,0);
                        bd2.difProcedimientos.set(j,0);
                    }else{
                        this.difProcedimientos.set(i,1);
                        bd2.difProcedimientos.set(j,1);
                    }
                    break;
                }
                if (j == bd2.getProcedimientos().size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    /**
     * @return the nombreBase
     */
    public String getNombreBase() {
        return nombreBase;
    }

    /**
     * @return the tablas
     */
    public LinkedList<Tabla> getTablas() {
        return tablas;
    }

    public LinkedList<Procedimiento> getProcedimientos() {
        return procedimientos;
    }
    

    public LinkedList<Integer> getDifTablas() {
        return difTablas;
    }
    
    public LinkedList<Integer> getDifProcedimientos() {
        return difProcedimientos;
    }

}
