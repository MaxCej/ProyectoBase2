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
public class BaseDeDatos {
    //nombre de la BD
    String nombreBase;
    //lista de tablas de la BD
    LinkedList<Tabla> tablas;

    //constructor de clase
    public BaseDeDatos(String nombreBase) {
        this.nombreBase = nombreBase;
        this.tablas=new LinkedList();
    }
    
    
}
