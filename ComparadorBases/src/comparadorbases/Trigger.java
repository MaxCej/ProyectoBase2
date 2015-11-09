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

    //campo que indica el nombre del trigger
    String nombre;

    //campo que indica el tiempo del disparo
    String disparo;

    //campo que indica la condicion
    String condicion;

    //campo que indica que un trigger es identico a otro 
    boolean presente;

    public Trigger(String nombre, String cond, String disp) {
        this.nombre = nombre;
        this.condicion = cond;
        this.disparo = disp;
        this.presente = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDisparo() {
        return disparo;
    }

    public String getCondicion() {
        return condicion;
    }

    //Precond: los triggers tienen el mismo nombre
    //metodo que dados dos triggers devuelve true si son totalmente iguales
    // y los marca para evitar ser evaluados de nuevo
    public boolean equals(Trigger other) {
        if (other == null) {
            return false;
        }
        if (!Objects.equals(this.disparo, other.disparo)) {
            return false;
        }
        if (!Objects.equals(this.condicion, other.condicion)) {
            return false;
        }
        this.presente = true;
        other.presente = true;
        return true;
    }

}
