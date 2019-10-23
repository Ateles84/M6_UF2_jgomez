package pt01_jgomez;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Principal {

	public static void main(String[] args) {
//		crearBD();
//		crearTaules();

//		insertarFaccions(1, "Caballers", "Putos amos de la vida");
//		insertarFaccions(2, "Vikings", "Putos amos de la vida en el norte");
//		insertarFaccions(3, "Samurais", "Putos amos de la vida al estilo japo");

//		insertarPersonatges(1, "Guardia", 130, 120, 1);
//		insertarPersonatges(2, "Conqueror", 150, 90, 1);
//		insertarPersonatges(3, "Peacekeeper", 90, 150, 1);
//		
//		insertarPersonatges(4, "Warlord", 140, 120, 2);
//		insertarPersonatges(5, "Raider", 140, 130, 2);
//
//		insertarPersonatges(6, "Shugoki", 140, 120, 3);
//		insertarPersonatges(7, "Kensei", 125, 120, 3);

//		mostrarPersonatges();
		
//		mostrarCaballers();
		
		mostrarMaxAtkSamurai();
	}

	static void crearBD() {

		String url = "jdbc:sqlite:D:\\SQLite\\forHonor";

		try (Connection conn = DriverManager.getConnection(url)) {
			if (conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("Estas utilitzant el driver: " + meta.getDriverName());
				System.out.println("La base de dades forHonor ha sigut creada");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void crearTaules() {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "CREATE TABLE IF NOT EXISTS faccion (faccion_id integer PRIMARY KEY, nom_faccion varchar2(30), lore varchar2(200));";
		String sentenciaSQL2 = "CREATE TABLE IF NOT EXISTS personatges (personaje_id integer PRIMARY KEY, nom_personatge varchar2(15), atac integer, "
				+ "defensa integer, faccion_id integer, FOREIGN KEY (faccion_id) REFERENCES faccion(faccion_id));";

		try (Connection conn = DriverManager.getConnection(bd); Statement stmt = conn.createStatement()) {
			stmt.execute(sentenciaSQL);
			stmt.execute(sentenciaSQL2);
			System.out.println("Taules creada correctament");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void insertarFaccions(int fid, String nomFacc, String lore) {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "INSERT INTO faccion VALUES (?,?,?);";

		try (Connection conn = DriverManager.getConnection(bd);
				PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {
			pstmt.setInt(1, fid);
			pstmt.setString(2, nomFacc);
			pstmt.setString(3, lore);
			pstmt.executeUpdate();
			System.out.println("Dades insertades correctament");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void insertarPersonatges(int pid, String nomPj, int atk, int def, int facc) {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "INSERT INTO personatges VALUES (?,?,?,?,?);";

		try (Connection conn = DriverManager.getConnection(bd);
				PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {
			pstmt.setInt(1, pid);
			pstmt.setString(2, nomPj);
			pstmt.setInt(3, atk);
			pstmt.setInt(4, def);
			pstmt.setInt(5, facc);
			pstmt.executeUpdate();
			System.out.println("Dades insertades correctament");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void mostrarPersonatges() {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "SELECT * FROM personatges;";

		try (Connection conn = DriverManager.getConnection(bd);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sentenciaSQL)) {

			while (rs.next()) {
				System.out.println("[Personatge_ID: " + rs.getInt(1) + " | Nom: " + rs.getString(2) + " | Atac: "
						+ rs.getInt(3) + " | Defensa: " + rs.getInt(4) + " | Faccio_ID: " + rs.getInt(5) + "]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	static void mostrarCaballers() {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "SELECT * FROM personatges WHERE faccion_id = 1;";

		try (Connection conn = DriverManager.getConnection(bd);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sentenciaSQL)) {

			while (rs.next()) {
				System.out.println("[Personatge_ID: " + rs.getInt(1) + " | Nom: " + rs.getString(2) + " | Atac: "
						+ rs.getInt(3) + " | Defensa: " + rs.getInt(4) + " | Faccio_ID: " + rs.getInt(5) + "]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	static void mostrarMaxAtkSamurai() {
		String bd = "jdbc:sqlite:D:\\SQLite\\forHonor";

		String sentenciaSQL = "SELECT * FROM personatges WHERE faccion_id = 3 AND atac = (SELECT max(atac) FROM personatges WHERE faccion_id = 3);";

		try (Connection conn = DriverManager.getConnection(bd);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sentenciaSQL)) {
				
			while (rs.next()) {
				System.out.println("[Personatge_ID: " + rs.getInt(1) + " | Nom: " + rs.getString(2) + " | Atac: "
						+ rs.getInt(3) + " | Defensa: " + rs.getInt(4) + " | Faccio_ID: " + rs.getInt(5) + "]");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
