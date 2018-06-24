package Progetto_Colognese_Rossini;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.w3c.dom.CDATASection;

public class Carrello {

	private String id;
	Connection conn;
	PreparedStatement ps;

	public Carrello(String idCliente) {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ingegneria", "Ross", "");
			this.id = idCliente;
		} catch (Exception e) {
		}
	}

	public int getTotProdottiCarrello() {
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) AS ct FROM inCarrello WHERE cliente = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt("ct");
		} catch (SQLException e) {
			return 0;
		}
	}

	public int getQuantitàProdotto(int codice) {
		try {
			ps = conn.prepareStatement("SELECT quantità FROM inCarrello WHERE disk = ?");
			ps.setInt(1, codice);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1);
			return 0;
		} catch (SQLException e) {
			return 0;
		}
	}

	public ResultSet getProdotti() {
		try {
			ps = conn.prepareStatement(
					"SELECT D.codice, D.titolo, D.prezzo, D.titolare, D.copertina, C.quantità FROM Disk D JOIN inCarrello C ON D.codice = C.disk WHERE C.cliente = ?");
			ps.setString(1, id);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public void svuota() throws SQLException {
		try {
			ps = conn.prepareStatement("SELECT disk, quantità FROM inCarrello WHERE cliente = ?");
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino + ? WHERE codice = ?");
				ps.setInt(1, rs.getInt(2));
				ps.setInt(2, rs.getInt(1));
				ps.executeUpdate();
			}

			ps = conn.prepareStatement("DELETE FROM inCarrello WHERE cliente = ?");
			ps.setString(1, id);
			ps.executeUpdate();
			PersonaleAutorizzato.setModificato(true);

		} catch (SQLException e) {
		}
	}

	public void aggiungiProdotto(int prodotto, int quantità) {
		try {
			ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino - ? WHERE codice = ?");
			ps.setInt(1, quantità);
			ps.setInt(2, prodotto);
			ps.executeUpdate();

			ps = conn.prepareStatement("SELECT * FROM inCarrello WHERE disk = ? AND cliente = ?");
			ps.setInt(1, prodotto);
			ps.setString(2, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ps = conn.prepareStatement(
						"UPDATE inCarrello SET quantità = quantità + ? WHERE disk = ? AND cliente = ?");
				ps.setInt(1, quantità);
				ps.setInt(2, prodotto);
				ps.setString(3, id);
				ps.executeUpdate();
			} else {
				ps = conn.prepareStatement("INSERT INTO inCarrello (cliente, disk, quantità) VALUES(?, ?, ?)");
				ps.setString(1, id);
				ps.setInt(2, prodotto);
				ps.setInt(3, quantità);
				ps.executeUpdate();
			}
			PersonaleAutorizzato.setModificato(true);
		} catch (SQLException e) {
		}
	}

	public void rimuoviProdotto(int prodotto, int quantità) {
		try {
			ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino + ? WHERE codice = ?");
			ps.setInt(1, quantità);
			ps.setInt(2, prodotto);
			ps.executeUpdate();

			ps = conn.prepareStatement("DELETE FROM inCarrello WHERE cliente = ? AND disk = ?");
			ps.setString(1, id);
			ps.setInt(2, prodotto);
			ps.executeUpdate();
			PersonaleAutorizzato.setModificato(true);
		} catch (SQLException e) {
		}
	}

	public void incrementaQuantità(int prodotto) {
		try {
			ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino - 1 WHERE codice = ?");
			ps.setInt(1, prodotto);
			ps.executeUpdate();

			ps = conn.prepareStatement("UPDATE inCarrello SET quantità = quantità + 1 WHERE disk = ? AND cliente = ?");
			ps.setInt(1, prodotto);
			ps.setString(2, id);
			ps.executeUpdate();
			PersonaleAutorizzato.setModificato(true);
		} catch (SQLException e) {
		}
	}

	public void decrementaQuantità(int prodotto) {
		try {
			ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino + 1 WHERE codice = ?");
			ps.setInt(1, prodotto);
			ps.executeUpdate();

			ps = conn.prepareStatement("UPDATE inCarrello SET quantità = quantità - 1 WHERE disk = ? AND cliente = ?");
			ps.setInt(1, prodotto);
			ps.setString(2, id);
			ps.executeUpdate();
			PersonaleAutorizzato.setModificato(true);
		} catch (SQLException e) {
		}
	}

	public Boolean modificaQuantitàProdotto(int codice, int nuovaQuantità) {
		int quantità = 0;
		try {
			ps = conn.prepareStatement("SELECT quantità FROM inCarrello WHERE disk = ?");
			ps.setInt(1, codice);
			ResultSet rs = ps.executeQuery();
			if (!rs.next())
				return false;

			quantità = rs.getInt(1);
			if (nuovaQuantità == 0) {
				rimuoviProdotto(codice, quantità);
				return true;
			}
			ps = conn.prepareStatement("UPDATE Disk SET totMagazzino = totMagazzino + ? - ? WHERE codice = ?");
			ps.setInt(1, quantità);
			ps.setInt(2, nuovaQuantità);
			ps.setInt(3, codice);
			ps.executeUpdate();

			ps = conn.prepareStatement("UPDATE inCarrello SET quantità = ? WHERE disk = ?");
			ps.setInt(1, nuovaQuantità);
			ps.setInt(2, codice);
			ps.executeUpdate();
			PersonaleAutorizzato.setModificato(true);
			return true;
		} catch (SQLException e) {
			return null;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
