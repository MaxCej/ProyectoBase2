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
public class Parametro {

    private String tipo;
    
    /*
     0=in
     1=out
     2=in/out
     */
    private int tipoPasaje;
    
    //campo que indica si un parametro es identico a otro evaluado, para evitar repetir su evaluacion.
    boolean presente;

    //Constructor de clase
    public Parametro(String tipo, int tipoPasaje) {
        this.tipo = tipo;
        this.tipoPasaje = tipoPasaje;
        this.presente = false;
    }

    public boolean equals(Parametro p) {
        if (p == null) {
            return false;
        }

        if (!this.tipo.equals(p.tipo)) {
            return false;
        }
        if (this.getTipoPasaje() != p.getTipoPasaje()) {
            return false;
        }
        //si ambos parametros son iguales, se los marca para evitar su evaluacion
        this.presente = true;
        p.presente = true;
        return true;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the tipoPasaje
     */
    public int getTipoPasaje() {
        return tipoPasaje;
    }

}
