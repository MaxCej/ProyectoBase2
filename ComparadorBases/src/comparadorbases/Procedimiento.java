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
    String nombre;
    //lista de parametros del procedimiento
    LinkedList<Parametro> param;
    //lista de diferencias con procedimiento del mismo nombre, en cuanto a parametros
    //0= igualdad de nombre, igualdad de tipo 
    //1= igualdad de nombre,difieren en algo
    //2= unico del procedimiento
    LinkedList<Integer> dif;
    //campo que indica si un procedimiento esta presente,se llama igual a otro procedimeinto, en la otra DB
    boolean presente;

    //constructor de clase
    public Procedimiento(String nombreProc) {
        this.nombre = nombreProc;
        this.param = new LinkedList();
        this.dif = new LinkedList();
        this.presente=false;
    }

    public void agregarParametro(Parametro p) {
        this.param.addLast(p);
        this.dif.addLast(2);
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
        if(this.param.size()!=other.param.size()){
            res = false;
        }
        for (i = 0; i < this.param.size(); i++) {
            for (j = 0; j < other.param.size(); j++) {
                //si el parametro del 2do procedimiento no estaba presente en el 1ero
                if (!other.param.get(j).presente) {
                    aux = this.param.get(i).equals(other.param.get(j));
                    /* si se llaman igual (present == true) 
                     * guardamos el resultado de la comparacion entre ambos
                     */
                    if (this.param.get(i).presente) {
                        //seteamos en 0 si se llaman igual y son identicos
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
                //Si llego al final del ciclo, el i-esimo parametro del 1er procedimiento
                //no esta en el 2do
                if (j==other.param.size()-1){
                    res = false;
                }
            }
        }
        return res;
    }
}
