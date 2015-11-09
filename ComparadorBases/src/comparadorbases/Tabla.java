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
    private String nombre;

    //lista de columnas de la tabla
    private LinkedList<Columna> Columnas;

    //campo que indica si una tabla esta presente,se llama igual a otra tabla, en la otra DB
    boolean presente;

    /*LinkedList de enteros que indican las diferencias entre tablas con un mismo nombre
     * Comparacion de columnas de tablas, 
     * 0 = columnas del mismo nombre, identicas
     * 1 = columnas del mismo nombre, distintas
     * 2 = la columna no existe en la otra tabla
     */
    private LinkedList<Integer> difColumna;

    /*
     Lista de TODOS los nombres de triggers de la tabla
     */
    private LinkedList<Trigger> triggers;

    //Lista de triggers en que difieren las tablas comparadas
    /*
     * 0 = mismo nombre, identicos
     * 1 = mismo nombre, distintos
     * 2 = trigger inexistente en la otra base de datos
     */
    private LinkedList<Integer> difTriggers;

    //Constructor de clase
    public Tabla(String nombre) {
        this.nombre = nombre;
        this.Columnas = new LinkedList();
        this.difColumna = new LinkedList();
        this.triggers = new LinkedList();
        this.difTriggers = new LinkedList();
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
        this.getColumnas().addLast(c);
        this.getDifColumna().addLast(2);
    }

    /*
     * cada vez que se agrega un trigger, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * triggers
     */
    public void agregarTrigger(Trigger t) {
        this.getTriggers().addLast(t);
        this.getDifTriggers().addLast(2);
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
        if (this.getColumnas().size() != other.getColumnas().size()) {
            res = false;
        }
        for (i = 0; i < this.getColumnas().size(); i++) {
            for (j = 0; j < other.getColumnas().size(); j++) {
                //si la columna de la 2da tabla no estaba presente en la 1era
                if (!other.Columnas.get(j).presente) {
                    aux = this.getColumnas().get(i).equals(other.getColumnas().get(j));
                    /* si se llaman igual (present == true) 
                     * guardamos el resultado de la comparacion entre ambas
                     */
                    if (this.getColumnas().get(i).presente) {
                        //seteamos en 0 si se llaman igual y son identicas
                        if (aux) {
                            this.getDifColumna().set(i, 0);
                            other.getDifColumna().set(j, 0);
                        } else {
                            //seteamos en 1 si se llaman igual y son diferentes
                            this.getDifColumna().set(i, 1);
                            other.getDifColumna().set(j, 1);
                            res = false;
                        }
                        break;
                    }
                }
                //Si llego al final del ciclo, la i-esima columna de la 1era tabla
                //no esta en la segunda
                if (j == other.getColumnas().size() - 1) {
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
        if (this.getTriggers().size() > other.getTriggers().size()) {
            res = false;
        }

        for (int i = 0; i < this.getTriggers().size(); i++) {
            for (int j = 0; j < other.getTriggers().size(); j++) {
                //si los triggers tienen el mismo nombre
                if (this.getTriggers().get(i).nombre.equals(other.getTriggers().get(j).nombre)) {
                   
                    //comparamos su estructura
                    boolean resParcial = (this.getTriggers().get(i).equals(other.getTriggers().get(j)));
                   
                    if (resParcial) {
                        //tienen el mismo nombre y estructura
                        this.getDifTriggers().set(i, 0);
                        other.getDifTriggers().set(j, 0);
                    } else {
                        //tienen el mismo nombre pero difieren en su estructura
                        this.getDifTriggers().set(i, 1);
                        other.getDifTriggers().set(j, 1);
                        res = false;
                    }
                    break;
                }

                if (j == other.getTriggers().size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the difColumna
     */
    public LinkedList<Integer> getDifColumna() {
        return difColumna;
    }

    /**
     * @return the triggers
     */
    public LinkedList<Trigger> getTriggers() {
        return triggers;
    }

    /**
     * @return the difTriggers
     */
    public LinkedList<Integer> getDifTriggers() {
        return difTriggers;
    }

}
