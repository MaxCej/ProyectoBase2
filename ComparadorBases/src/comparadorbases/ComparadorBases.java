/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.util.LinkedList;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

/**
 *
 * @author fede
 */
public class ComparadorBases {

    /**
     * @param args the command line
     * arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) { //throws SQLException{
        /*
         String base1, base2;
         try{
         Connection connection1;
         Connection connection2;
         base1="comparador1"; //setear nombres de las bases de datos
         base2="comparador2";
         String driver = "org.postgresql.Driver";
         String url1 = "jdbc:postgresql://localhost:5433/"+base1;
         String url2 = "jdbc:postgresql://localhost:5433/"+base2;
         String user="postgres";
         String pass="root";
         Class.forName(driver);
         connection1=DriverManager.getConnection(url1,user,pass);
         connection2=DriverManager.getConnection(url2,user,pass);
         //Opciones(connection);
         } catch(Exception sqle) {
         sqle.printStackTrace();
         System.err.println("Error connecting: " + sqle);
         }
         }
         */

        boolean res;
        Columna c1 = new Columna("pepito", 0, "varchar", 40, true, false);
        Columna c2 = new Columna("pepito", 0, "varchar", 40, true, false);
        Tabla t1 = new Tabla("juan");
        t1.agregarColumna(c1);
        Tabla t2 = new Tabla("juan");
        t2.agregarColumna(c2);
        res = t1.compararTablas(t2);
        System.out.println(res);
        for (int i = 0; i < t1.dif.size(); i++) {
            System.out.println(t1.Columnas.get(i).nombre);
            System.out.println(t1.dif.get(i));
            System.out.println("---------------------");
            System.out.println(t2.Columnas.get(i).nombre);
            System.out.println(t2.dif.get(i));
            System.out.println("---------------------");
        }

        //TEST DE COLUMNAS
        /*
         res = c1.equals(c2);
         System.out.println(res);
         System.out.println("-------------------------");
         if (c1.getDif()!=null){
         for (int i = 0; i < c1.dif.length; i++) {
         System.out.println(c1.dif[i]);
         }
         System.out.println("-------------------------");
         for (int i = 0; i < c2.dif.length; i++) {
         System.out.println(c2.dif[i]);
         }
         }else{
         if(!res){
         System.out.println("tienen distinto nombre. puto en el ocho");
         }else{
         System.out.println("son iguales. puto en el ocho");
         }
             
         }
         */
    }
}
