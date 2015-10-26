/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package comparadorbases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    public static void main(String[] args) throws SQLException{
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
    
}
