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
    String nombreBase;
    //lista de tablas de la BD
    LinkedList<Tabla> tablas;
    /*LinkedList de enteros que indican las diferencias entre tablas con un mismo nombre
     * Comparacion de columnas de tablas, 
     * 0 = columnas del mismo nombre, identicas
     * 1 = columnas del mismo nombre, distintas
     * 2 = la columna no existe en la otra tabla
     */
    LinkedList<Integer> dif;
    /*
     Lista de TODOS los nombres de triggers de la base de datos
     */
    LinkedList<String> triggers;
    //Lista de triggers UNICOS de la DB
    LinkedList<String> difTriggers;

    //constructor de clase
    public BaseDeDatos(String nombreBase) {
        this.nombreBase = nombreBase;
        this.tablas = new LinkedList();
        this.dif = new LinkedList();
        this.triggers = new LinkedList();
        this.difTriggers = new LinkedList();
    }

    /*
     * cada vez que se agrega una tabla, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * bases de datos, en cuanto a tablas
     */
    public void agregarTabla(Tabla t) {
        this.tablas.addLast(t);
        this.dif.addLast(2);
    }

    public void agregarTrigger(String t) {
        this.triggers.addLast(t);
    }

    public boolean equals(BaseDeDatos bd2) {
        int i, j;
        boolean aux;
        boolean res = true;
        boolean mismoNombre;

        if (this.tablas.size() != bd2.tablas.size()) {
            res = false;
        }

        for (i = 0; i < this.tablas.size(); i++) {
            for (j = 0; j < bd2.tablas.size(); j++) {
                mismoNombre = this.tablas.get(i).nombre.equals(bd2.tablas.get(j).nombre);
                //si la i-esima tabla de la 1er bd tiene el mismo nombre que 
                //la j-esima tabla de la 2da bd, comparamos las tablas
                if (mismoNombre) {
                    aux = this.tablas.get(i).compararTablas(bd2.tablas.get(j));
                    //seteamos en 0 si se llaman igual y son identicas
                    if (aux) {
                        this.dif.set(i, 0);
                        bd2.dif.set(j, 0);
                    } else {
                        //seteamos en 1 si se llaman igual y son diferentes
                        this.dif.set(i, 1);
                        bd2.dif.set(j, 1);
                        res = false;
                    }
                    break;
                }
                //Si llego al final del ciclo, la i-esima tabla de la 1era base
                //no esta en la segunda
                if (j == bd2.tablas.size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    /*Metodo que dadas dos bases de datos retorna true si
     la lista de triggers de la 1er base esta incluida
     en la lista de triggers de la 2da y en caso de haber
     triggers de la 1er BD que no esten en la 2da, se guardan en una lista
     dentro de la 1er BD
     */
    public boolean esSubconjuntoTriggers(BaseDeDatos other) {
        boolean res = true;
        if (this.triggers.size() > other.triggers.size()) {
            res = false;
        }

        for (int i = 0; i < this.triggers.size(); i++) {
            for (int j = 0; j < other.triggers.size(); j++) {
                System.out.println("compara: "+this.triggers.get(i)+" con "+other.triggers.get(j));
                boolean resParcial = (this.triggers.get(i).equals(other.triggers.get(j)));
                System.out.println("resparcial: "+resParcial);
                if (resParcial) {
                    break;
                }
                if (j == other.triggers.size() - 1) {
                    this.difTriggers.add(this.triggers.get(i));
                    res = false;
                }
            }
            //resTotal=resTotal && resParcial;
            //if(){
            //    res=false;
            //}
        }
        return res;
    }

}
