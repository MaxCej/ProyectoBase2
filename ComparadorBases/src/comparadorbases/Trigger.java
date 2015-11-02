/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.util.Objects;

/**
 *
 * @author federf
 */
public class Trigger {

    String nombre;
    //0=before
    //1=after
    int disparo;
    //0=insert
    //1=update
    //2=delete
    int condicion;
    //tabla en que se trabaja
    String tabla;
    //campo que indica que un trigger es identico a otro 
    boolean presente;

    public Trigger(String nombre, int cond, int disp, String tabla) {
        this.nombre = nombre;
        this.condicion = cond;
        this.disparo = disp;
        this.tabla = tabla;
        this.presente = false;
    }

    //Precond: los triggers tienen el mismo nombre
    //metodo que dados dos triggers devuelve true si son totalmente iguales
    // y los marca para evitar ser evaluados de nuevo
    public boolean equals(Trigger other) {
        if (other == null) {
            return false;
        }
        
        if (this.disparo != other.disparo) {
            return false;
        }
        if (this.condicion != other.condicion) {
            return false;
        }
        if (!Objects.equals(this.tabla, other.tabla)) {
            return false;
        }
        this.presente = true;
        other.presente = true;
        return true;
    }

}
