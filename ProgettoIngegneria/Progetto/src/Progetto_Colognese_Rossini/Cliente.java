package Progetto_Colognese_Rossini;

import java.sql.*;

public class Cliente {

	private static String id = "";
	Connection conn;
	PreparedStatement ps;

	public Cliente() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ingegneria", "Ross", "");
		} catch (Exception e) {
		}
	}

	public int logIn(String nome, String password) {
		try {
			ps = conn.prepareStatement("SELECT password, personaleAutorizzato FROM Cliente WHERE nomeUtente = ?");
			ps.setString(1, nome);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				if (rs.getString("password").equals(password)) {
					if (rs.getBoolean("personaleAutorizzato") == false) {
						id = nome;
						return 1;
					} else
						return 2;
				}
			return 3;
		} catch (SQLException e) {
			return 4;
		}
	}

	public Boolean signUp(String codiceFiscale, String nomeUt, String password, String nome, String cognome,
			String città, String telefono) {
		try {
			ps = conn.prepareStatement("SELECT * FROM Cliente WHERE nomeUtente = ?");
			ps.setString(1, nomeUt);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				id = nomeUt;
				ps = conn.prepareStatement(
						"INSERT INTO Cliente (codiceFiscale, nomeUtente, password, nome, cognome, città, telefono, personaleAutorizzato) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, codiceFiscale);
				ps.setString(2, nomeUt);
				ps.setString(3, password);
				ps.setString(4, nome);
				ps.setString(5, cognome);
				ps.setString(6, città);
				ps.setString(7, telefono);
				ps.setBoolean(8, false);
				ps.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	public Boolean signUp(String codiceFiscale, String nomeUt, String password, String nome, String cognome,
			String città, String telefono, String cellulare) {
		try {
			ps = conn.prepareStatement("SELECT * FROM Cliente WHERE nomeUtente = ?");
			ps.setString(1, nomeUt);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				id = nomeUt;
				ps = conn.prepareStatement(
						"INSERT INTO Cliente (codiceFiscale, nomeUtente, password, nome, cognome, città, telefono, cellulare, personaleAutorizzato) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				ps.setString(1, codiceFiscale);
				ps.setString(2, nomeUt);
				ps.setString(3, password);
				ps.setString(4, nome);
				ps.setString(5, cognome);
				ps.setString(6, città);
				ps.setString(7, telefono);
				ps.setString(8, cellulare);
				ps.setBoolean(9, false);
				ps.executeUpdate();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	public String getNomeUtente() {
		return id;
	}

	public void setNomeUtente(String id) {
		this.id = id;
	}

	public void logOut() {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
}
