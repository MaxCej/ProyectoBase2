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
    //0= igualdad de nombre, igualdad de tipo 
    //1= unico del procedimiento
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
        this.getDif().addLast(1);
    }

    /*
     * Precond:los procedimientos tienen el mismo nombre
     * dados dos procedimientos devuelve si difieren en cuanto a sus parametros
     * y a la vez guarda una lista con sus diferencias
     */
    public boolean compararProcedimientos(Procedimiento other) {
        int i, j;
        boolean aux;
        boolean res = true;

        if (other == null) {
            return false;
        }
        if (!this.nombre.equals(other.nombre)) {
            return false;
        }
        //si los procedimientos se llaman igual, seteamos presente en true
        //en ambos procedimientos
        this.presente = true;
        other.presente = true;
        //si un procedimiento tiene mas parametros que otro, son distintos
        if (this.getParam().size() != other.getParam().size()) {
            res = false;
        }
        for (i = 0; i < this.getParam().size(); i++) {
            for (j = 0; j < other.getParam().size(); j++) {
                //si el parametro del 2do procedimiento no estaba presente en el 1ero
                if (!other.param.get(j).presente) {
                    System.out.println("compara: " + this.getParam().get(i).getTipo() + ", " + this.getParam().get(i).getTipoPasaje());
                    System.out.println("Con : " + other.getParam().get(j).getTipo() + ", " + other.getParam().get(j).getTipoPasaje());
                    aux = this.getParam().get(i).equals(other.getParam().get(j));
                    System.out.println("res: " + aux);
                    /* si se llaman igual (present == true) 
                     * guardamos el resultado de la comparacion entre ambos
                     */
                    if (this.getParam().get(i).presente) {
                        //seteamos en 0 si ambos parametros son identicos

                        System.out.println("entro if(0)");
                        this.getDif().set(i, 0);
                        other.getDif().set(j, 0);

                        break;
                    }
                }
                //Si llego al final del ciclo, el i-esimo parametro del 1er procedimiento
                //no esta en el 2do
                if (j == other.getParam().size() - 1) {
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
     * @return the param
     */
    public LinkedList<Parametro> getParam() {
        return param;
    }

    /**
     * @return the dif
     */
    public LinkedList<Integer> getDif() {
        return dif;
    }
}
