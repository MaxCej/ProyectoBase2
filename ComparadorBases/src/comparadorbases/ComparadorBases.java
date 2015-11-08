/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadorbases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

/**
 *
 * @author fede
 */
public class ComparadorBases {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) { //throws SQLException{
        String base1, base2;
        try {
            //conn1
            Connection conn1;
            base1 = "root"; //setear nombres de las bases de datos
            String url1 = "jdbc:postgresql://localhost:5432/" + base1;

            //conn2
            Connection conn2;
            base2 = "postgres";
            String url2 = "jdbc:postgresql://localhost:5432/" + base2;

            String user = "root";
            String pass = "root";
            String driver = "org.postgresql.Driver";
            Class.forName(driver);

            conn1 = DriverManager.getConnection(url1, user, pass);
            //conn2 = DriverManager.getConnection(url2, user, pass);

            DatabaseMetaData meta = conn1.getMetaData();

            ResultSet rs = meta.getCatalogs();
            ResultSet rs2 = meta.getSchemas();

            while (rs2.next()) {
                String schema = rs2.getString(1);  //"TABLE_CATALOG"
                System.out.println("schema: " + schema);
            }
            while (rs.next()) {
                String catalog = rs.getString(1);  //"TABLE_CATALOG"
                System.out.println("catalog: " + catalog);
            }
            //Opciones(connection);
        } catch (Exception sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }

    }

    public static boolean ObtenerDatos(BaseDeDatos db, Connection c, String schema) {
        try {
            /*metaDato*/
            DatabaseMetaData metaData = c.getMetaData();

            /*resultSets */
            ResultSet resultSetTabla, resultSetColumna, resultSetClavePrimaria, resultSetClaveForanea, resultSetProcedimiento, resultSetParametro, resultSetTrigger, resultSetClaveUnica;

            /*obtengo las tablas*/
            String[] tipo = {"TABLE"};
            resultSetTabla = metaData.getTables(null, schema, null, tipo);

            // obtener las tablas de la base de datos
            while (resultSetTabla.next()) {
                Tabla tabla = new Tabla(resultSetTabla.getString(3));
                /*obtengo las columnas*/
                resultSetColumna = metaData.getColumns(null, schema, resultSetTabla.getString(3), "%");

                while (resultSetColumna.next()) {
                    Integer clave = 0;
                    boolean esUnico = false;
                    boolean tieneIndice = false;

                    // obtengo si es clave primaria
                    resultSetClavePrimaria = metaData.getPrimaryKeys(null, schema, resultSetTabla.getString(3));
                    boolean pk = false;
                    while (resultSetClavePrimaria.next() && !pk) {
                        pk = resultSetClavePrimaria.getString(4).equals(resultSetColumna.getString(4));
                    }

                    // si es primaria
                    if (pk) {
                        clave = 1;
                        esUnico = true;

                    } else {
                        //obtengo claves unicas
                        resultSetClaveUnica = metaData.getIndexInfo(null, schema, resultSetTabla.getString(3), true, true);
                        resultSetClaveUnica.next();
                        while (resultSetClaveUnica.next() && !esUnico) {
                            esUnico = resultSetClaveUnica.getString(9).equals(resultSetColumna.getString(4));
                        }

                        //obtener clave foranea
                        resultSetClaveForanea = metaData.getImportedKeys(null, schema, resultSetTabla.getString(3));
                        while (resultSetClaveForanea.next() && (clave != 2)) {
                            if (resultSetClaveForanea.getString(8).equals(resultSetColumna.getString(4))) {
                                clave = 2;
                            }
                        }

                    }
                    //agregar una columna nueva a la estructura de tabla
                    Columna columna = new Columna(resultSetColumna.getString(4), clave, resultSetColumna.getString(6), resultSetColumna.getInt(7), esUnico, tieneIndice);
                    tabla.agregarColumna(columna);
                }

                //
                // FALTA OBTENER INFORMACION DE TRIGGERS
                //
                //
                /*obtener triggers*/
                Statement statement = c.createStatement();
                resultSetTrigger = statement.executeQuery(
                        "SELECT trigger_name, event_object_table,action_timing,event_manipulation\n"
                        + " FROM information_schema.triggers\n"
                        + " WHERE trigger_schema NOT IN\n"
                        + " ('pg_catalog', 'information_schema') and event_object_table='"
                        + resultSetTabla.getString(3) + "'");

                while (resultSetTrigger.next()) {
                    Trigger trigger = new Trigger(resultSetTrigger.getString(1), resultSetTrigger.getString(3), resultSetTrigger.getString(4));
                    tabla.agregarTrigger(trigger);
                }
                db.agregarTabla(tabla);
            }

            // obtenemos los procedimientos
            resultSetProcedimiento = metaData.getProcedures(null, schema, "%");
            while (resultSetProcedimiento.next()) {
                Procedimiento procedure = new Procedimiento(resultSetProcedimiento.getString(3));
                resultSetParametro = metaData.getProcedureColumns(null, schema, resultSetProcedimiento.getString(3), "%");
                while (resultSetParametro.next()) {
                    Parametro parametro = new Parametro(resultSetParametro.getString(7), resultSetParametro.getInt(5));
                    procedure.agregarParametro(parametro);
                }
                db.agregarProcedimiento(procedure);// TODO METODO
            }

        } catch (SQLException sqle) {
            System.out.println("Ocurrio un error SQL2");
        }
        return true;
    }
}
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
    // PRUEBA COMPARADOR TRIGGERS
//        BaseDeDatos bd1 = new BaseDeDatos("base1");
//        Trigger t1 = new Trigger("t1", 0, 0, "tabla1");
//        Trigger t2 = new Trigger("t1", 0, 0, "tabla1");
//        Trigger t3 = new Trigger("t2", 1, 0, "tabla1");
//        bd1.agregarTrigger(t1);
//        BaseDeDatos bd2 = new BaseDeDatos("base2");
//        bd2.agregarTrigger(t2);
//        bd2.agregarTrigger(t3);
//        boolean res = bd2.comparadorTriggers(bd1);
//        System.out.println("");
//        System.out.println("mismos triggers?: " + res + "\n-------------------------");
//        if (bd1.difTriggers != null) {
//            for (int i = 0; i < bd1.difTriggers.size(); i++) {
//                System.out.println(bd1.difTriggers.get(i));
//            }
//            System.out.println("-------------------------");
//            for (int i = 0; i < bd2.difTriggers.size(); i++) {
//                System.out.println(bd2.difTriggers.get(i));
//            }
//            System.out.println("---------------------");
//
//        }
//        System.out.println("---------------------");


/* PRUEBA PROCEDIMIENTOS
 BaseDeDatos bd1 = new BaseDeDatos("base1");
 BaseDeDatos bd2 = new BaseDeDatos("base2");

 Procedimiento p1 = new Procedimiento("nombre1");
 Parametro param1 = new Parametro("param1", 1);
 Parametro param4 = new Parametro("param3", 1);
 Parametro param5 = new Parametro("param5", 1);
 p1.agregarParametro(param1);
 p1.agregarParametro(param4);

 Procedimiento p2 = new Procedimiento("nombre1");
 Parametro param2 = new Parametro("param1", 1);
 Parametro param3 = new Parametro("param2", 1);
 p2.agregarParametro(param5);
 p2.agregarParametro(param2);
 p2.agregarParametro(param3);

 boolean res = p1.compararProcedimientos(p2);
 System.out.println(res);
 System.out.println("");
 System.out.println("parametros iguales?: " + res + "\n-------------------------");
 if (p1.dif != null) {
 for (int i = 0; i < p1.dif.size(); i++) {
 System.out.println(p1.dif.get(i));
 }
 System.out.println("-------------------------");
 for (int i = 0; i < p2.dif.size(); i++) {
 System.out.println(p2.dif.get(i));
 }
 System.out.println("---------------------");

 }
 */
