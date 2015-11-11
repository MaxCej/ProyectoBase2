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

    //
    boolean dif[];

    public Trigger(String nombre, String cond, String disp) {
        this.nombre = nombre;
        this.condicion = cond;
        this.disparo = disp;
        this.presente = false;
        this.dif = new boolean[3];
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

    public boolean[] getDif() {
        return dif;
    }

    public boolean isPresente() {
        return presente;
    }

    //Precond: los triggers tienen el mismo nombre
    //metodo que dados dos triggers devuelve true si son totalmente iguales
    // y los marca para evitar ser evaluados de nuevo
    public boolean equals(Trigger other) {
        boolean difer[] = {true, true, true};
        boolean res = true;
        if (other == null) {
            return false;
        } else {
            if (this.nombre.equals(other.nombre)) {
                this.presente = true;
                other.presente = true;
                if (!this.disparo.equals(other.disparo)) {
                    difer[1] = false;
                    res = false;
                }
                if (!this.condicion.equals(other.condicion)) {
                    difer[2] = false;
                    res = false;
                }
                //actualizamos los arreglos dif de ambas tablas
                this.dif = difer;
                other.dif = difer;
                return res;
            }else{
                return false;
            }

        }

    }

    //retorna true si la columna es totalmente igual a otra
    private boolean recorridoDif() {
        boolean res = true;
        for (boolean b : this.getDif()) {
            res = res && b;
        }
        return res;
    }

    public void mostrarDiferenciasTrigger() {
        //recorremos el arreglo dif que contiene las diferencias entre columnas del mismo nombre
        //siempre que se haya encontrado alguna diferencia entre ellas
        if (this.recorridoDif()) {
            System.out.println("        los triggers de nombre " + this.getNombre() + " son identicos en ambas tablas de las bases de datos");
        } else {
            System.out.println("        los triggers de nombre " + this.getNombre() + " difieren ");
            for (int i = 0; i < this.dif.length; i++) {
                //si el arreglo en la posicion corriente presenta una diferencia (es false)
                if (!this.dif[i]) {
                    switch (i) {
                        case 0:
                            System.out.println("            en nombre: " + this.getNombre());
                            break;
                        case 1:
                            System.out.println("            en condicion : " + this.getCondicion());
                            break;
                        case 2:
                            System.out.println("            en condicion de disparo: " + this.getDisparo());
                            break;
                    }
                }
            }
        }
    }
}
