package Progetto_Colognese_Rossini;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Catalogo {

	Connection conn;
	PreparedStatement ps;
	private static Catalogo instance = null;

	private Catalogo() {
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ingegneria", "Ross", "");
		} catch (Exception e) {
		}
	}

	public static Catalogo getInstance() {
		if (instance == null)
			instance = new Catalogo();
		return instance;
	}

	public int getTotProdottiCatalogo() {
		try {
			ps = conn.prepareStatement("SELECT COUNT(*) FROM Disk WHERE totMagazzino > ?");
			ps.setInt(1, 0);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			return 0;
		}
	}

	public ResultSet getCatalogo() {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE totMagazzino > ? ORDER BY titolo ASC");
			ps.setInt(1, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public List<ResultSet> getCatalogoPersonale(String cliente) {
		List<ResultSet> rs = new ArrayList<>();
		ResultSet rs1;

		Set<Integer> products = new HashSet<>();
		List<String> generi = new ArrayList<>();
		int ricorrenze = 0;

		String genereMax = "";
		int max = 0;

		try {
			ps = conn.prepareStatement("SELECT prodotti FROM Vendita V WHERE V.cliente = ?");
			ps.setString(1, cliente);
			rs1 = ps.executeQuery(); // lista di vendite con tutti i prodotti
			if (rs1.next()) {
				do {
					for (String s : rs1.getString(1).split(","))
						products.add(Integer.parseInt(s.trim()));

				} while (rs1.next());

				for (Integer s : products) {
					ps = conn.prepareStatement("SELECT genere FROM Disk WHERE codice = ?");
					ps.setInt(1, s);
					rs1 = ps.executeQuery();
					if (rs1.next())
						generi.add(rs1.getString(1));
				}

				for (String s : generi) {
					for (int i = 0; i < generi.size(); i++)
						if (generi.get(i).equals(s))
							ricorrenze++;
					if (ricorrenze > max) {
						max = ricorrenze;
						genereMax = s;
					}
				}

				ps = conn.prepareStatement(
						"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE totMagazzino > ? AND genere = ?");
				ps.setInt(1, 0);
				ps.setString(2, genereMax);
				rs.add(ps.executeQuery());

				ps = conn.prepareStatement(
						"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE totMagazzino > ? AND genere <> ?");
				ps.setInt(1, 0);
				ps.setString(2, genereMax);
				rs.add(ps.executeQuery());
				return rs;
			} else {
				rs.add(this.getCatalogo());
				return rs;
			}
		} catch (SQLException e) {
			return null;
		}
	}

	public Boolean getCatalogoScontato(String cliente) {
		try {
			ps = conn.prepareStatement(
					"SELECT COUNT(*) FROM Vendita WHERE cliente = ? AND prezzoTot >= 250 HAVING COUNT(*)>2");
			ps.setString(1, cliente);
			if (ps.executeQuery().next())
				return true;
			return false;
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet filtraPerGenere(String genere) {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE genere ILIKE ? AND totMagazzino > ?");
			ps.setString(1, "%" + genere + "%");
			ps.setInt(2, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet filtraPerTitolare(String titolare) {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE titolare ILIKE ? AND totMagazzino > ?");
			ps.setString(1, "%" + titolare + "%");
			ps.setInt(2, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet filtraPerMusicistaPartecipante(String musicista) {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE musicisti ILIKE ? AND totMagazzino > ?");
			ps.setString(1, "%" + musicista + "%");
			ps.setInt(2, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet filtraPerPrezzoCrescente(BigDecimal max) {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE prezzo <= ? AND totMagazzino > ? ORDER BY prezzo ASC");
			ps.setBigDecimal(1, max);
			ps.setInt(2, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}

	public ResultSet filtraPerPrezzoDecrescente(BigDecimal max) {
		try {
			ps = conn.prepareStatement(
					"SELECT codice, titolo, prezzo, titolare, copertina FROM Disk WHERE prezzo <= ? AND totMagazzino > ? ORDER BY prezzo DESC");
			ps.setBigDecimal(1, max);
			ps.setInt(2, 0);
			return ps.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
}
