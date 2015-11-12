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
            base1 = "ejbPract1"; //setear nombres de las bases de datos
            String url1 = "jdbc:postgresql://localhost:5432/" + base1;

            //conn2
            Connection conn2;
            base2 = "ejcPract1";
            String url2 = "jdbc:postgresql://localhost:5432/" + base2;

            String user = "postgres";
            String pass = "root";
            String driver = "org.postgresql.Driver";
            Class.forName(driver);

            conn1 = DriverManager.getConnection(url1, user, pass);
            conn2 = DriverManager.getConnection(url2, user, pass);

            BaseDeDatos db1 = new BaseDeDatos(base1);
            ObtenerDatos(db1, conn1, "public");

            BaseDeDatos db2 = new BaseDeDatos(base2);
            ObtenerDatos(db2, conn2, "public");

            System.out.println("Son iguales las 2 bases?: " + db1.equals(db2));
            System.out.println("\n--------------------------------------------\n");
            //MostrarEstructura(db1);
            //MostrarEstructura(db2);
            System.out.println("diferencias "+base1+" con respecto a "+base2+" : ");
            mostrarDiferenciasBases(db1);
            System.out.println("\n--------------------------------------------\n");
            System.out.println("diferencias "+base2+" con respecto a "+base1+" : ");
            mostrarDiferenciasBases(db2);
            //Opciones(connection);
            
        } catch (Exception sqle) {
            sqle.printStackTrace();
            System.err.println("Error connecting: " + sqle);
        }

    }

    public static void ObtenerDatos(BaseDeDatos db, Connection c, String schema) {
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
    }

    public static void MostrarEstructura(BaseDeDatos db) {
        System.out.println("ESTRUCTURA DE BASES DE DATOS: \n\n");
        System.out.println("Nombre de BD: " + db.getNombreBase() + "\n");
        for (Tabla t : db.getTablas()) {
            System.out.println("---------------------------------------");
            System.out.println("Nombre de tabla: " + t.getNombre());
            System.out.println("Columnas: ");
            for (Columna c : t.getColumnas()) {
                System.out.println("nombre col: " + c.getNombre() + ", tamaño: " + c.getTamaño() + ", tipo: " + c.getTipo() + ",tipo clave: " + c.getTipoClave());
                boolean pres = c.isPresente();
                System.out.println("Esta presente en la otra tabla?: " + pres);

                boolean dif = c.getDif() != null;

                System.out.println("dif nombre: " + c.getDif()[0]);
                System.out.println("dif tipoClave: " + c.getDif()[1]);
                System.out.println("dif tipo: " + c.getDif()[2]);
                System.out.println("dif tamaño: " + c.getDif()[3]);
                System.out.println("dif esUnica: " + c.getDif()[4]);
                System.out.println("dif esIndice: " + c.getDif()[5]);

                System.out.println("");
            }
            if (!t.getTriggers().isEmpty()) {
                System.out.println("Triggers: ");
                for (int i = 0; i < t.getTriggers().size(); i++) {
                    Trigger tg = t.getTriggers().get(i);
                    System.out.println(tg.getNombre() + ", " + tg.getCondicion() + ", " + tg.getDisparo());
                    int diftg = t.getDifTriggers().get(i);
                    System.out.println("presente: " + diftg);
                }
                System.out.println("------------------------------------------- ");
            }

        }
        if (!db.getProcedimientos().isEmpty()) {
            System.out.println("Procedimientos\n");
            for (Procedimiento p : db.getProcedimientos()) {
                System.out.println("Nombre Procedimiento:" + p.getNombre() + "\n");
                for (Parametro pa : p.getParam()) {
                    System.out.println("pasaje: " + pa.getTipoPasaje() + ", tipo: " + pa.getTipo());
                }
                System.out.println("------------------------------------------- ");
                System.out.println("");
            }
            System.out.println("------------------------------------------- ");
        }

    }

    public static void mostrarDiferenciasBases(BaseDeDatos db) {
        for (Tabla t : db.getTablas()) {
            t.mostrarDiferenciasTabla();
        }
        for (Procedimiento p: db.getProcedimientos()){
            p.mostrarDiferenciasProcedimientos();
        }
    }
}
