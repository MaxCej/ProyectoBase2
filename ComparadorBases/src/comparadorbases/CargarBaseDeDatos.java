/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.sql.Connection;

/**
 *
 * @author federf
 */
public class CargarBaseDeDatos {

    
    public static void CargarBaseDeDatos(Connection c, BaseDeDatos bd) {    
        CargarTablas(c, bd);
        CargarProcedimientos(c, bd);
        CargarTriggers(c, bd);
    }

    private static void CargarTablas(Connection c, BaseDeDatos bd) {
        
    }

    private static void CargarProcedimientos(Connection c, BaseDeDatos bd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void CargarTriggers(Connection c, BaseDeDatos bd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
