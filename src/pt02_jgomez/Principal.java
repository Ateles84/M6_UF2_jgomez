package pt02_jgomez;

import java.awt.Canvas;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Principal {
	static Scanner sc;

	public static void main(String[] args) {		//Eii rafa se que esta commiteado tarde, perdoname no he podido hacerlo antes :(
		boolean power = true;
		sc = new Scanner(System.in);

		while (power) {
			System.out.println(
					"--------- BENVINGUT AL VIDEOCLUB JGOMEZ --------- \r1. Mostrar socis \r2. Afegir soci \r3. Modificar quota soci \r4. Eliminar soci \r5. Sortir");
			int aux = sc.nextInt();

			switch (aux) {
			case 1:
				mostrarSocis();
				break;
			case 2:
				System.out.println("Introdueix el nom del soci: ");
				String z = sc.next();
				crearSoci(z);
				break;
			case 3:
				System.out.println("Introdueix el cod_soc: ");
				int x = sc.nextInt();
				cuotaSoci(x);
				break;
			case 4:
				System.out.println("Introdueix cod_soc: ");
				x = sc.nextInt();
				eliminarSoci(x);
				break;
			case 5:
				System.out.println("c ya! c:");
				power = false;
				break;
			default:
				System.out.println("Introdueix un valor valid!");
			}

		}
	}

	static void crearSoci(String nomSoci) {
		String bd = "jdbc:mysql://localhost/m6_uf2_pt02";

		String sentenciaSQL = "INSERT INTO socio (nombre) VALUES(?);";

		try (Connection conn = DriverManager.getConnection(bd, "root", "");
				PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {
			pstmt.setString(1, nomSoci);
			pstmt.executeUpdate();
			System.out.println("Soci: " + nomSoci + " introduit");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("crearSoci -- Error: " + e.getMessage());
		}

	}

	static void cuotaSoci(int codSoci) {
		String bd = "jdbc:mysql://localhost/m6_uf2_pt02";

		String sentenciaSQL = "SELECT cod_soc, nombre, cuota FROM socio WHERE cod_soc = " + codSoci;

		sc = new Scanner(System.in);

		try (Connection conn = DriverManager.getConnection(bd, "root", "");
				PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {

			ResultSet rs = pstmt.executeQuery(sentenciaSQL);

			if (rs.next()) {
				System.out.println("Cod_soc: " + rs.getString(1) + " | Nom: " + rs.getString(2) + " | Quota: "
						+ rs.getInt(3) + "\rVols modificar la quota? [Si] | [No]");
				String saux = sc.next().toLowerCase();

				switch (saux) {
				case "si":
					System.out.println("Introdueix la nova quota");
					int iaux = sc.nextInt();
					modificarCuota(iaux, codSoci);
					break;
				case "no":
					System.out.println("rip");
				}

			} else {
				System.out.println("No s'ha trobat l'usuari");
			}

//			System.out.println("Everything okkk"); xivato

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("cuotaCanv -- Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	static void modificarCuota(int x, int codSoci) {
		String bd = "jdbc:mysql://localhost/m6_uf2_pt02";

		String sentenciaSQL = "UPDATE socio SET cuota= ? WHERE cod_soc = ? ";

		try (Connection conn = DriverManager.getConnection(bd, "root", "");
				PreparedStatement pstmt = conn.prepareStatement(sentenciaSQL)) {
			pstmt.setInt(1, x);
			pstmt.setInt(2, codSoci);

			if (pstmt.executeUpdate() == 1)
				System.out.println("Introduccio feta");
			else
				System.out.println("That's lifeee");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("ModificarCuota -- Error: " + e.getMessage());
		}
	}

	static void eliminarSoci(int codSoci) {
		String bd = "jdbc:mysql://localhost/m6_uf2_pt02";

		String sentenciaSQL = "DELETE FROM socio WHERE cod_soc = " + codSoci;

		try (Connection conn = DriverManager.getConnection(bd, "root", "")) {

			conn.createStatement().execute(sentenciaSQL);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}
	}

	static void mostrarSocis() {
		String bd = "jdbc:mysql://localhost/m6_uf2_pt02";

		String sentenciaSQL = "SELECT cod_soc, nombre, apellidos FROM socio";

		try (Connection conn = DriverManager.getConnection(bd, "root", "")) {

			ResultSet rs = conn.createStatement().executeQuery(sentenciaSQL);

			while (rs.next()) {
				System.out.println(
						"Cod_soc: " + rs.getString(1) + " | Nom: " + rs.getString(2) + " | Cognom: " + rs.getString(3));
			}

//			System.out.println("Everything okkk");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: " + e.getMessage());
		}
	}
}
