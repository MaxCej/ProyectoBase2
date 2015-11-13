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
     Lista de TODOS los triggers de la tabla
     */
    private LinkedList<Trigger> triggers;

    //Lista de triggers en que difieren las tablas comparadas
    /*
     * 0 = mismo nombre, identicos
     * 1 = mismo nombre, distintos
     * 2 = trigger inexistente en la otra base de datos
     */
    private LinkedList<Integer> difTriggers;

    /*
     Lista de TODOS los indices de la tabla
     */
    private LinkedList<Indice> indices;

    //Lista de indices en que difieren las tablas comparadas
    /*
     * 0 = mismo nombre, identicos
     * 1 = mismo nombre, distintos
     * 2 = indice inexistente en la otra base de datos
     */
    private LinkedList<Integer> difIndices;

    //Constructor de clase
    public Tabla(String nombre) {
        this.nombre = nombre;
        this.Columnas = new LinkedList();
        this.difColumna = new LinkedList();
        this.triggers = new LinkedList();
        this.difTriggers = new LinkedList();
        this.indices = new LinkedList();
        this.difIndices = new LinkedList();
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
     * cada vez que se agrega un indice, se agrega
     * un integer "2" en la lista que indica diferencias entre
     * indices
     */
    public void agregarIndice(Indice i) {
        this.getIndices().addLast(i);
        this.getDifIndices().addLast(2);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isPresente() {
        return presente;
    }

    public LinkedList<Integer> getDifColumna() {
        return difColumna;
    }

    public LinkedList<Trigger> getTriggers() {
        return triggers;
    }

    public LinkedList<Integer> getDifTriggers() {
        return difTriggers;
    }

    public LinkedList<Indice> getIndices() {
        return indices;
    }

    public LinkedList<Integer> getDifIndices() {
        return difIndices;
    }

    /*
     * Precond: las tablas tienen el mismo nombre
     * dadas dos tablas devuelve si difieren en la estructura de sus columnas
     * y a la vez guarda una lista con sus diferencias
     */
    public boolean compararColumnas(Tabla other) {
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
                Columna c1 = this.getColumnas().get(i);
                Columna c2 = other.getColumnas().get(j);
                //si la columna de la 2da tabla no estaba presente en la 1era
                if (!c2.isPresente()) {
                    aux = c1.equals(c2);
                    /* si se llaman igual (present == true) 
                     * guardamos el resultado de la comparacion entre ambas
                     */
                    if (c1.isPresente()) {
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
        return res;
    }

    /*
     * Dadas dos tablas devuelve si difieren en sus triggers
     *  y a la vez guarda una lista con sus diferencias
     */
    public boolean compararTriggers(Tabla other) {
        boolean res = true;
        boolean aux;
        if (this.getTriggers().size() > other.getTriggers().size()) {
            res = false;
        }

        for (int i = 0; i < this.getTriggers().size(); i++) {
            for (int j = 0; j < other.getTriggers().size(); j++) {
                Trigger t1 = this.getTriggers().get(i);
                Trigger t2 = other.getTriggers().get(j);
                //si los triggers tienen el mismo nombre
                if (t1.getNombre().equals(t2.getNombre())) {
                    //si el trigger de la 2da tabla no fue evaluado aun
                    if (!t2.isPresente()) {
                        //comparamos la estructura de ambos trigger
                        aux = (t1.equals(t2));
                        //si se marco como presente al primero
                        if (t1.isPresente()) {
                            if (aux) {
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

                    }

                }

                if (j == other.getTriggers().size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    /*
     * metodo que dadas dos tablas devuelve true si todos los indices de
     * ambas tablas son identicos (en cuanto a nombre y estructura)
     * y falso en caso de encontrar alguna diferencia. Ademas registra 
     * las diferencias encontradas durante la comparacion
     */
    public boolean compararIndices(Tabla other) {
        boolean res = true;
        boolean aux;
        if (this.getIndices().size() > other.getIndices().size()) {
            res = false;
        }

        for (int i = 0; i < this.getIndices().size(); i++) {
            for (int j = 0; j < other.getIndices().size(); j++) {
                Indice i1 = this.getIndices().get(i);
                Indice i2 = other.getIndices().get(j);
                //si los indices tienen el mismo nombre
                if (i1.getNombre().equals(i2.getNombre())) {
                    //si el indice de la 2da tabla no fue evaluado aun
                    if (!i2.isPresente()) {
                        //comparamos la estructura de ambos indices
                        aux = (i1.equals(i2));
                        //si se marco como presente al primero
                        if (i1.isPresente()) {
                            if (aux) {
                                //tienen el mismo nombre y estructura
                                this.getDifIndices().set(i, 0);
                                other.getDifIndices().set(j, 0);
                            } else {
                                //tienen el mismo nombre pero difieren en su estructura
                                this.getDifIndices().set(i, 1);
                                other.getDifIndices().set(j, 1);
                                res = false;
                            }
                            break;
                        }

                    }

                }

                if (j == other.getIndices().size() - 1) {
                    res = false;
                }
            }
        }
        return res;
    }

    /*
     * metodo que muestra por pantalla informacion sobre las diferencias entre las tablas de dos BD
     */
    public void mostrarDiferenciasTabla() {
        if (!this.isPresente()) {
            System.out.println("    la tabla " + this.getNombre() + " es unica de la base de datos");
        } else {
            System.out.println("    la tabla " + this.getNombre() + " comparte nombre con otra tabla de la otra base de datos");
            //recorremos la lista dif que contiene las diferencias de columnas entre tablas del mismo nombre
            for (int i = 0; i < this.difColumna.size(); i++) {
                //si la i-esima columna comparte nombre con otra columna en la otra base de datos
                Columna colActual = this.getColumnas().get(i);
                if (colActual.isPresente()) {
                    colActual.mostrarDiferenciasColumna();
                } else {
                    System.out.println("        la columna " + colActual.getNombre() + " es unica de la tabla " + this.getNombre());
                }
            }
            if (this.getTriggers().size() != 0) {
                for (int i = 0; i < this.getDifTriggers().size(); i++) {
                    Trigger triggerActual = this.getTriggers().get(i);
                    //si el trigger actual tiene el mismo nombre que un trigger en la otra BD
                    if (triggerActual.isPresente()) {
                        triggerActual.mostrarDiferenciasTrigger();
                    } else {
                        System.out.println("        el trigger " + triggerActual.getNombre() + " es unico de la tabla " + this.getNombre());
                    }
                }
            }

            if (this.getIndices().size() != 0) {
                for (int i = 0; i < this.getDifIndices().size(); i++) {
                    Indice indActual = this.getIndices().get(i);
                    //si el indice actual tiene el mismo nombre que un indice en la otra BD
                    if (indActual.isPresente()) {
                        indActual.mostrarDiferenciasIndice();
                    } else {
                        System.out.println("        el indice " + indActual.getNombre() + " es unico de la tabla " + this.getNombre());
                    }
                }
            }
        }

    }
}
