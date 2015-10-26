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

    //Constructor de clase
    public Tabla(String nombre) {
        this.nombre = nombre;
        this.Columnas = new LinkedList<Columna>();
        this.presente=false;
    }

    public LinkedList<Columna> getColumnas() {
        return Columnas;
    }

    public void agregarColumna(Columna c) {
        Columnas.addLast(c);
    }   

    
    /*
     * Precond: las tablas tienen el mismo nombre
     * dadas dos tablas devuelve una estructura que contiene
     * los elementos en que difieren
     */
    public Object compararTablas(Tabla other) {
        int i, j;
        boolean res[];
        if (other == null) {
            return false;
        }
        if(!this.nombre.equals(other.nombre)){
            return false;
        }

        i = 0;
        j = 0;
        while (i < this.Columnas.size()) {
            while (j < other.Columnas.size()) {
                //si la columna de la 2da tabla no estaba presente en la 1era
                if (!other.Columnas.get(j).presente) {

                   /*res=this.Columnas.get(i).equals(other.Columnas.get(j));
                   if(res!=null){
                       
                   }else{
                       //EXCEPTION TODO
                   }*/
                }
                j++;
            }
            i++;
        }

        return true;
    }

}
