package Progetto_Colognese_Rossini;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Vendita {

	Connection conn;
	PreparedStatement ps;

	public Vendita() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ingegneria", "Ross", "");
		} catch (Exception e) {
		}
	}

	public Boolean aggiungiVendita(String cliente, String prodotti, String ip, BigDecimal prezzoTot,
			String modalitàPagamento, String modalitàConsegna) {
		try {
			ps = conn
					.prepareStatement("SELECT * FROM Vendita WHERE cliente = ? AND dataOraAcquisto = now()::Timestamp");
			ps.setString(1, cliente);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				ps = conn.prepareStatement(
						"INSERT INTO Vendita (cliente, prodotti, ip, prezzoTot, dataOraAcquisto, modalitàPagamento, modalitàConsegna) VALUES(?, ?, ?, ?, now()::Timestamp, ?, ?)");
				ps.setString(1, cliente);
				ps.setString(2, prodotti);
				ps.setString(3, ip);
				ps.setBigDecimal(4, prezzoTot);
				ps.setString(5, modalitàPagamento);
				ps.setString(6, modalitàConsegna);
				ps.executeUpdate();
				return true;
			} else
				return false;
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet getVenditeCliente(String cliente) {
		try {
			ps = conn.prepareStatement("SELECT * FROM Vendita WHERE cliente = ?");
			ps.setString(1, cliente);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
}
