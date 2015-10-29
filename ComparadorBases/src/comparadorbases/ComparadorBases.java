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

//        boolean res;
//        Columna c1 = new Columna("c1", 0, "varchar", 40, true, false);
//        Columna c2 = new Columna("c1", 0, "varchar", 40, true, false);
//        Columna c3 = new Columna("c2", 0, "varchar", 40, true, false);
//        Columna c4 = new Columna("c2", 0, "varchar", 40, true, false);
//        Columna c5 = new Columna("c4", 0, "varchar", 40, true, false);
//        Columna c6 = new Columna("c4", 0, "varchar", 40, true, false);
//        Columna c7 = new Columna("c4", 1, "varchar", 40, true, false);
//        Tabla t1 = new Tabla("juan");
//        t1.agregarColumna(c1);
//        t1.agregarColumna(c3);
//        t1.agregarColumna(c5);
//        Tabla t2 = new Tabla("pepe");
//        t2.agregarColumna(c2);
//        t2.agregarColumna(c4);
//        t2.agregarColumna(c6);
//        Tabla t3 = new Tabla("juan");
//        t3.agregarColumna(c1);
//        t3.agregarColumna(c3);
//        t3.agregarColumna(c5);
//        Tabla t4 = new Tabla("pepe");
//        t4.agregarColumna(c2);
//        t4.agregarColumna(c4);
//        t4.agregarColumna(c7);
//        //res = t1.compararTablas(t2);
//
//        BaseDeDatos bd1 = new BaseDeDatos("base1");
//        bd1.agregarTabla(t1);
//        bd1.agregarTabla(t2);
//        BaseDeDatos bd2 = new BaseDeDatos("base2");
//        bd2.agregarTabla(t3);
//        bd2.agregarTabla(t4);
//        res = bd1.equals(bd2);
//
//        System.out.println("bases iguales?: " + res + "\n-------------------------");
//        for (int i = 0; i < bd1.tablas.size(); i++) {
//            System.out.println(bd1.tablas.get(i).nombre);
//            System.out.println(bd1.dif.get(i));
//        }
//        System.out.println("---------------------");
//
//        for (int i = 0; i < bd2.tablas.size(); i++) {
//            System.out.println(bd2.tablas.get(i).nombre);
//            System.out.println(bd2.dif.get(i));
//        }
//        System.out.println("---------------------");
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
        /* PRUEBA COMPARADOR TRIGGERS
         BaseDeDatos bd1 = new BaseDeDatos("base1");
         bd1.agregarTrigger("a");
         bd1.agregarTrigger("b");
         bd1.agregarTrigger("c");
         bd1.agregarTrigger("d");
         bd1.agregarTrigger("e");
         bd1.agregarTrigger("f");
         BaseDeDatos bd2 = new BaseDeDatos("base2");
         bd2.agregarTrigger("a");
         bd2.agregarTrigger("b");
         bd2.agregarTrigger("c");
         bd2.agregarTrigger("d");
         boolean res = bd2.esSubconjuntoTriggers(bd1);
         System.out.println("");
         System.out.println("es subconjunto?: " + res + "\n-------------------------");
         if (bd1.difTriggers == null) {
         System.out.println("son iguales");
         } else {
         for (int i = 0; i < bd1.difTriggers.size(); i++) {
         System.out.println(bd1.difTriggers.get(i));
         }
         System.out.println("---------------------");
         }*/
    }
}
