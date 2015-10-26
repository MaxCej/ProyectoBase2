/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.util.Objects;

/**
 *
 * @author fede
 */
public class Columna {

    //nombre de la columna
    String nombre;
    //tipo de la columna
    String tipo;
    //tamaño de la columna
    int tamaño;
    //int que indica si es clave primaria, secundaria/foranea o nada (0=nada, 1=primaria, 2=secundaria)
    int tipoClave;
    //campo booleano que indica si la columna es unica o no
    boolean esUnica;
    //campo booleano que indica si la columna tiene o no un indice
    boolean esIndice;
    //campo que indica si hay una columna con el mismo nombre en la otra BD
    boolean presente;
    //arreglo de boolean indica las diferencias con respecto a otra columna del mismo nombre
    boolean dif[];

    /*
     * Constructor de clase
     */
    public Columna(String nombre, int tipoClave, String tipo, int tamaño, boolean esUnica, boolean esIndice) {
        this.nombre = nombre;
        this.tipoClave = tipoClave;
        this.tipo = tipo;
        this.tamaño = tamaño;
        this.esUnica = esUnica;
        this.esIndice = esIndice;
        this.presente = false;
        dif = null;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTipoClave() {
        return tipoClave;
    }

    public void setTipoClave(int tipoClave) {
        this.tipoClave = tipoClave;
    }

    public boolean isPresente() {
        return presente;
    }

    /* metodo que dadas dos columnas devuelve
     *  un arreglo de boolean que indica en que difiere la 2da de la 1era
     */
    public void equals(Columna other) {

        //campo que indica si se encontro alguna diferencia
        boolean aux=true;
        boolean difer[] = {true, true, true, true, true, true};

        if (other == null) {
            //exception TODO........
        } else {
            //si el nombre es igual, se compara ambas columnas
            if (this.nombre.equals(other.nombre)) {
                
                this.presente = true;
                other.presente = true;
                //compara tipo de clave, tipo, tamaño, si son indices o no y si son campos unicos o no
                if (this.tipoClave != other.tipoClave) {
                    difer[1] = false;
                    aux=false;
                }
                if (!this.tipo.equals(other.tipo)) {
                    difer[2] = false;
                    aux=false;
                }
                if (this.tamaño != other.tamaño) {
                    difer[3] = false;
                    aux=false;
                }
                if (this.esUnica != (other.esUnica)) {
                    difer[4] = false;
                    aux=false;
                }
                if (this.esIndice != other.esIndice) {
                    difer[5] = false;
                    aux=false;
                }
                //en caso de haberse encontrado alguna diferencia se guarda difer en ambas columnas
                if(!aux){
                    this.dif = difer;
                    other.dif = difer;
                }
                //caso contrario, no se hace nada y ambos dif (de ambas columnas) quedan en null
                
            }
        }

    }

}
