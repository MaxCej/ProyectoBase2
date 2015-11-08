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
    LinkedList<Integer> difColumna;

    /*
     Lista de TODOS los nombres de triggers de la tabla
     */
    LinkedList<Trigger> triggers;

    //Lista de triggers en que difieren las tablas comparadas
    /*
     * 0 = mismo nombre, identicos
     * 1 = mismo nombre, distintos
     * 2 = trigger inexistente en la otra base de datos
     */
    LinkedList<Integer> difTriggers;

    //Constructor de clase
    public Tabla(String nombre) {
        this.nombre = nombre;
        this.Columnas = new LinkedList();
        this.difColumna = new LinkedList();
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
        this.difColumna.addLast(2);
    }

    /*
     * cada vez que se agrega un trigger, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * triggers
     */
    public void agregarTrigger(Trigger t) {
        this.triggers.addLast(t);
        this.difTriggers.addLast(2);
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
        if (this.Columnas.size() != other.Columnas.size()) {
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
                            this.difColumna.set(i, 0);
                            other.difColumna.set(j, 0);
                        } else {
                            //seteamos en 1 si se llaman igual y son diferentes
                            this.difColumna.set(i, 1);
                            other.difColumna.set(j, 1);
                            res = false;
                        }
                        break;
                    }
                }
                //Si llego al final del ciclo, la i-esima columna de la 1era tabla
                //no esta en la segunda
                if (j == other.Columnas.size() - 1) {
                    res = false;
                }
            }
        }
        return res && this.comparadorTriggers(other);
    }

    /*
     * Dadas dos tablas devuelve si difieren en sus triggers
     *  y a la vez guarda una lista con sus diferencias
     */
    public boolean comparadorTriggers(Tabla other) {
        boolean res = true;
        if (this.triggers.size() > other.triggers.size()) {
            res = false;
        }

        for (int i = 0; i < this.triggers.size(); i++) {
            for (int j = 0; j < other.triggers.size(); j++) {
                System.out.println("compara: " + this.triggers.get(i).nombre + " con " + other.triggers.get(j).nombre);
                //si los triggers tienen el mismo nombre
                if (this.triggers.get(i).nombre.equals(other.triggers.get(j).nombre)) {
                    System.out.println("mismo nombre");
                    //comparamos su estructura
                    boolean resParcial = (this.triggers.get(i).equals(other.triggers.get(j)));
                    System.out.println("resparcial: " + resParcial);
                    if (resParcial) {
                        //tienen el mismo nombre y estructura
                        this.difTriggers.set(i, 0);
                        other.difTriggers.set(j, 0);
                    } else {
                        //tienen el mismo nombre pero difieren en su estructura
                        this.difTriggers.set(i, 1);
                        other.difTriggers.set(j, 1);
                        res = false;
                    }
                    break;
                }

                if (j == other.triggers.size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

}
