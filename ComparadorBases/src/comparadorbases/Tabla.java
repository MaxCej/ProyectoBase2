/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author fede
 */
public class Tabla {

    //nombre de la tabla
    String nombre;
    //lista de columnas de la tabla
    LinkedList<Columna> Columnas;
    //campo que indica si una tabla esta presente,se llama igual a otra tabla, en la otra DB
    boolean presente;
    /*LinkedList de enteros que indican las diferencias entre tablas con un mismo nombre
     * Comparacion de columnas de tablas, 
     * 0 = columnas del mismo nombre, identicas
     * 1 = columnas del mismo nombre, distintas
     * 2 = la columna no existe en la otra tabla
     */
    LinkedList<Integer> dif;

    //Constructor de clase
    public Tabla(String nombre) {
        this.nombre = nombre;
        this.Columnas = new LinkedList();
        this.dif = new LinkedList();
        this.presente = false;
    }

    public LinkedList<Columna> getColumnas() {
        return this.Columnas;
    }

    /*
     * cada vez que se agrega una columna, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * columnas
     */
    public void agregarColumna(Columna c) {
        this.Columnas.addLast(c);
        this.dif.addLast(2);
    }

    /*
     * Precond: las tablas tienen el mismo nombre
     * dadas dos tablas devuelve si difieren las tablas
     * y a la vez guarda una lista con sus diferencias
     */
    public boolean compararTablas(Tabla other) {
        int i, j;
        boolean aux;
        boolean res = true;

        if (other == null) {
            return false;
        }
        if (!this.nombre.equals(other.nombre)) {
            return false;
        }
        //si las tablas se llaman igual, seteamos presente en true
        //en ambas tablas
        this.presente = true;
        other.presente = true;
        //si una tablas tiene mas columas que la otra, son distintas
        if(this.Columnas.size()!=other.Columnas.size()){
            res = false;
        }
        for (i = 0; i < this.Columnas.size(); i++) {
            for (j = 0; j < other.Columnas.size(); j++) {
                //si la columna de la 2da tabla no estaba presente en la 1era
                if (!other.Columnas.get(j).presente) {
                    aux = this.Columnas.get(i).equals(other.Columnas.get(j));
                    /* si se llaman igual (present == true) 
                     * guardamos el resultado de la comparacion entre ambas
                     */
                    if (this.Columnas.get(i).presente) {
                        //seteamos en 0 si se llaman igual y son identicas
                        if (aux) {
                            this.dif.set(i, 0);
                            other.dif.set(j, 0);
                        } else {
                            //seteamos en 1 si se llaman igual y son diferentes
                            this.dif.set(i, 1);
                            other.dif.set(j, 1);
                            res = false;
                        }
                        break;
                    }
                }
                //Si llego al final del ciclo, la i-esima columna de la 1era tabla
                //no esta en la segunda
                if (j==other.Columnas.size()-1){
                    res = false;
                }
            }
        }
        return res;
    }

}
