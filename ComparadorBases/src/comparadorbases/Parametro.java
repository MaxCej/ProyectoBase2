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

    //campo que indica si un parametro fue evaluado (comparado con otro)
    boolean presente;

    //arreglo que marca en que difieren dos parametros
    //pos 0=tipo
    //pos 1=tipo de pasaje
    //true significa que son iguales en dicho campo 
    boolean[] difParam;

    //Constructor de clase
    public Parametro(String tipo, int tipoPasaje) {
        this.tipo = tipo;
        this.tipoPasaje = tipoPasaje;
        this.presente = false;
        this.difParam = new boolean[2];
    }

    public boolean equals(Parametro other) {
        //si ambos parametros son iguales, se los marca para evitar su evaluacion
        this.presente = true;
        other.presente = true;
        boolean res = true;
        boolean[] difer = {true, true};
        if (!this.tipo.equals(other.tipo)) {
            difer[0] = false;
            res = false;
        }
        if (this.getTipoPasaje() != other.getTipoPasaje()) {
            difer[1] = false;
            res = false;
        }

        this.difParam = difer;
        other.difParam = difer;
        return res;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTipoPasaje() {
        return tipoPasaje;
    }

    public boolean[] getDif() {
        return difParam;
    }

    public boolean isPresente() {
        return presente;
    }

    //retorna true si el parametro es totalmente igual a otro con que se comparo
    private boolean recorridoDif() {
        boolean res = true;
        for (boolean b : this.getDif()) {
            res = res && b;
        }
        return res;
    }

    public void mostrarDiferenciasParametro(int index) {
        //recorremos el arreglo dif que contiene las diferencias entre parametros ubicados en la misma posicion
        //siempre que se haya encontrado alguna diferencia entre ellos
        if (this.recorridoDif()) {
            if(index==0){
                System.out.println("        los valores de retorno son identicos en ambos procedimientos de las bases de datos");
            }else{
                System.out.println("        los parametros ubicados en la posicion " + index + " son identicos en ambos procedimientos de las bases de datos");
            }
            
        } else {
            if(index==0){
                System.out.println("        los valores de retorno difieren ");
            }else{
                System.out.println("        los parametros ubicados en la posicion " + index + " difieren ");
            }
            
            for (int i = 0; i < this.getDif().length; i++) {
                //si el arreglo en la posicion corriente presenta una diferencia (es false)
                if (!this.getDif()[i]) {
                    switch (i) {
                        case 0:
                            System.out.println("            en tipo: " + this.getTipo());
                            break;
                        case 1:
                            System.out.println("            en tipo de pasaje: " + this.getTipoPasaje());
                            break;
                    }
                }
            }
        }
    }
}
