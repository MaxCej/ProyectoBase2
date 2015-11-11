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

    //constructor de clase
    public BaseDeDatos(String nombreBase) {
        this.nombreBase = nombreBase;
        this.tablas = new LinkedList();
        this.difTablas = new LinkedList();
        this.procedimientos = new LinkedList();
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
        this.procedimientos.addLast(p);
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
                mismoNombre = this.getTablas().get(i).getNombre().equals(bd2.getTablas().get(j).getNombre());
                //si la i-esima tabla de la 1er bd tiene el mismo nombre que 
                //la j-esima tabla de la 2da bd, comparamos las tablas
                if (mismoNombre) {
                    //comparamos sus tablas
                    aux = this.getTablas().get(i).compararTablas(bd2.getTablas().get(j));
                    //comparamos los triggers de las tablas
                    aux2= this.getTablas().get(i).comparadorTriggers(bd2.getTablas().get(j));
         
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
    
    /**
     * @return the dif
     */
    public LinkedList<Integer> getDifTablas() {
        return difTablas;
    }

}
