/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

/**
 *
 * @author federf
 */
public class Indice {

    String nombre;
    String campoTabla;
    boolean esUnico;
    //arreglo de boolean que indica en que difieren dos indices
    boolean[] dif;
    //este campo indica que se un indice fue comparado con otro
    boolean presente;

    public Indice(String nombre, String ct, boolean unico) {
        this.nombre = nombre;
        this.campoTabla = ct;
        this.esUnico = unico;
        this.dif = new boolean[2];
        this.presente = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCampoTabla() {
        return campoTabla;
    }

    public boolean getUnico() {
        return esUnico;
    }

    public boolean[] getDif() {
        return dif;
    }

    public boolean isPresente() {
        return presente;
    }
    /*
     * PRECOND: los indices tienen el mismo nombre
     */

    public boolean equals(Indice other) {
        boolean res = true;
        boolean[] difer = {true, true};
        if (this.nombre.equals(other.nombre)) {
            //si tienen el mismo nombre seteamos presente en true en ambos indices
            this.presente = true;
            other.presente = true;

            if (!this.campoTabla.equals(other.campoTabla)) {
                res = false;
                difer[0] = false;
            }
            if (this.esUnico != other.esUnico) {
                res = false;
                difer[1] = false;
            }
            this.dif = difer;
            other.dif = difer;
        }
        return res;
    }

    //retorna true si el indice fue totalmente igual a otro con que se comparo
    private boolean recorridoDif() {
        boolean res = true;
        for (boolean b : this.getDif()) {
            res = res && b;
        }
        return res;
    }

    public void mostrarDiferenciasIndice() {
        //recorremos el arreglo dif que contiene las diferencias entre columnas del mismo nombre
        //siempre que se haya encontrado alguna diferencia entre ellas
        if (this.recorridoDif()) {
            System.out.println("        los indices de nombre " + this.getNombre() + " son identicos en ambas tablas de las bases de datos");
        } else {
            System.out.println("        los indices de nombre " + this.getNombre() + " difieren ");
            for (int i = 0; i < this.dif.length; i++) {
                //si el arreglo en la posicion corriente presenta una diferencia (es false)
                if (!this.dif[i]) {
                    switch (i) {
                        case 0:
                            System.out.println("            en campo de tabla sobre el cual se establecen: " + this.getCampoTabla());
                            break;
                        case 1:
                            System.out.println("            en si es unico o no: " + this.getUnico());
                            break;
                    }
                }
            }
        }
    }
}
