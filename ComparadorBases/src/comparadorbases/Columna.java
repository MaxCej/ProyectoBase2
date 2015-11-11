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
    private String nombre;

    //tipo de la columna
    private String tipo;

    //tamaño de la columna
    private int tamaño;

    //int que indica si es clave primaria, secundaria/foranea o nada (0=nada, 1=primaria, 2=secundaria)
    private int tipoClave;

    //campo booleano que indica si la columna es unica o no
    private boolean esUnica;

    //campo booleano que indica si la columna tiene o no un indice
    private boolean esIndice;

    //campo que indica si hay una columna con el mismo nombre en la otra BD
    boolean presente;

    //arreglo de boolean indica las diferencias con respecto a otra columna del mismo nombre
    public boolean dif[];

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
        this.dif = new boolean[6];
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

    public boolean[] getDif() {
        return dif;
    }

    /* metodo que dadas dos columnas devuelve
     * un boolean que indica si difieren las columnas
     * y si difieren, guarda un arreglo de bool con sus diferencias
     */
    public boolean equals(Columna other) {

        //campo que indica si se encontro alguna diferencia
        boolean aux = true;
        boolean difer[] = {true, true, true, true, true, true};

        if (other == null) {
            return false;//exception TODO........
        } else {
            //si el nombre es igual, se compara ambas columnas
            if (this.getNombre().equals(other.getNombre())) {
                this.presente = true;
                other.presente = true;
                //compara tipo de clave, tipo, tamaño, si son indices o no y si son campos unicos o no
                if (this.getTipoClave() != other.getTipoClave()) {
                    difer[1] = false;
                    aux = false;
                }
                if (!this.tipo.equals(other.tipo)) {
                    difer[2] = false;
                    aux = false;
                }
                if (this.getTamaño() != other.getTamaño()) {
                    difer[3] = false;
                    aux = false;
                }
                if (this.esUnica() != (other.esUnica())) {
                    difer[4] = false;
                    aux = false;
                }
                if (this.esIndice() != other.esIndice()) {
                    difer[5] = false;
                    aux = false;
                }
                //actualizamos el arreglo dif
                this.dif = difer;
                other.dif = difer;
                //retornamos el valor de aux, que en caso de haber diferencia es false, sino, es true
                return aux;

            } else {
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

    public boolean esUnica() {
        return esUnica;
    }

    public boolean esIndice() {
        return esIndice;
    }

    public void mostrarDiferenciasColumna() {
        //recorremos el arreglo dif que contiene las diferencias entre columnas del mismo nombre
        //siempre que se haya encontrado alguna diferencia entre ellas
        if (this.recorridoDif()) {
            System.out.println("        las columnas de nombre " + this.getNombre() + " son identicas en ambas tablas de las bases de datos");
        } else {
            System.out.println("        las columnas de nombre " + this.getNombre() + " difieren ");
            for (int i = 0; i < this.dif.length; i++) {
                //si el arreglo en la posicion corriente presenta una diferencia (es false)
                if (!this.dif[i]) {
                    switch (i) {
                        case 0:
                            System.out.println("            en nombre: " + this.getNombre());
                            break;
                        case 1:
                            System.out.println("            en tipo de clave: " + this.getTipoClave());
                            break;
                        case 2:
                            System.out.println("            en tipo de campo: " + this.getTipo());
                            break;
                        case 3:
                            System.out.println("            en tamaño: " + this.getTamaño());
                            break;
                        case 4:
                            System.out.println("            en si el campo es unico o no: " + this.esUnica());
                            break;
                        case 5:
                            System.out.println("            en si el campo es indice o no: " + this.esIndice());
                            break;
                    }
                }
            }
        }

    }
}
