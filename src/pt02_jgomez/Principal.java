package pt02_jgomez;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Principal {
	static Connection conn = null; 
	Statement stmnt;
	Scanner sc;

	public static void main(String[] args) {
		CreaConn();
		
		
	}
	
	static void CreaConn() {
		

		String url = "jdbc:mysql://localhost/m6_uf2_pt02";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,"root","");
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("Estas utilitzant el driver: " + meta.getDriverName()+"\rConexio feta!");
				
			}

		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error: "+e.getMessage());
			System.exit(0);
			
		}
		
	}
	
	void crearSoci(String nomSoci, String cognom, String dir, String tlf, String pob, String cp, String provincia, String pais, int edad, java.sql.Date data, int cuota) {
		if (conn != null) {
			try {
				stmnt = conn.createStatement();
				
				stmnt.execute("");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
