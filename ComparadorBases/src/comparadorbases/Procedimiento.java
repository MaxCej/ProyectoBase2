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
public class Procedimiento {

    //nombre del procedimiento
    private String nombre;

    //lista de parametros del procedimiento
    private LinkedList<Parametro> param;

    //lista de diferencias con procedimiento del mismo nombre, en cuanto a parametros
    //0= igualdad de tipo y tipo de pasaje
    //1= difieren en algun campo
    //2= parametro unico (excede en posicion a la cantidad de parametros del otro procedimiento)
    private LinkedList<Integer> dif;

    //campo que indica si un procedimiento esta presente,se llama igual a otro procedimeinto, en la otra DB
    boolean presente;

    //constructor de clase
    public Procedimiento(String nombreProc) {
        this.nombre = nombreProc;
        this.param = new LinkedList();
        this.dif = new LinkedList();
        this.presente = false;
    }

    public void agregarParametro(Parametro p) {
        this.getParam().addLast(p);
        this.getDif().addLast(2);
    }

    /*
     * Precond:los procedimientos tienen el mismo nombre
     * dados dos procedimientos devuelve si difieren en cuanto a sus parametros
     * y a la vez guarda una lista con sus diferencias
     */
    public boolean compararProcedimientos(Procedimiento other) {
        int i;
        boolean res;

        if (other == null) {
            return false;
        }
        //seteamos presente en true
        //en ambos procedimientos
        this.presente = true;
        other.presente = true;
        if (this.getParam().size() == other.getParam().size()) {
            res = compararParametros(this, other);
        } else {
            if (this.getParam().size() < other.getParam().size()) {
                compararParametros(this, other);
            } else {
                compararParametros(other, this);
            }
            res = false;
        }
        //si un procedimiento tiene mas parametros que otro, son distintos
        return res;
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<Parametro> getParam() {
        return param;
    }

    public LinkedList<Integer> getDif() {
        return dif;
    }

    private static boolean compararParametros(Procedimiento p1, Procedimiento p2) {
        int i;
        boolean aux = true;
        //resultado final
        boolean res = true;
        for (i = 0; i < p1.getParam().size(); i++) {
            aux = p1.getParam().get(i).equals(p2.getParam().get(i));
            /* si se llaman igual (present == true) 
             * guardamos el resultado de la comparacion entre ambos
             */
            res = res && aux;
            if (aux) {
                //seteamos en 0 si ambos parametros son identicos
                p1.getDif().set(i, 0);
                p2.getDif().set(i, 0);
            } else {
                p1.getDif().set(i, 1);
                p2.getDif().set(i, 1);
            }
        }
        return res;
    }

    public void mostrarDiferenciasProcedimientos() {
        if (!this.presente) {
            System.out.println("    el procedimiento " + this.getNombre() + " es unico de la base de datos");
        } else {
            System.out.println("    el procedimiento " + this.getNombre() + " comparte nombre con otro procedimiento de la otra base de datos");
            //recorremos la lista dif que contiene las diferencias de columnas entre tablas del mismo nombre
            for (int i = 0; i < this.getDif().size(); i++) {
                //si el i-esimo del procedimiento es igual al i-esimo parametro del procedimiento
                //que tiene el mismo nombre pero en la otra base de datos
                Parametro paramActual = this.getParam().get(i);
                if (paramActual.isPresente()) {
                    paramActual.mostrarDiferenciasParametro(i);
                } else {
                    System.out.println("        el parametro ubicado en la posicion " + (i) + " es unico del procedimiento " + this.getNombre());
                }
            }

        }
    }
}
