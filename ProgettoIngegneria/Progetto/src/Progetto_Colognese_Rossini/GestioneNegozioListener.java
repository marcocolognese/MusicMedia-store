package Progetto_Colognese_Rossini;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestioneNegozioListener implements ActionListener {

	private static GestioneNegozio gestioneNegozio;
	private static HomePage homePage;
	public final String MUSICISTA = "Musicista";
	public final String PRODOTTO = "Prodotto";
	public final String CONFERMA = "Conferma";

	GestioneNegozioListener(GestioneNegozio gestioneNegozio, HomePage homePage) {
		this.gestioneNegozio = gestioneNegozio;
		this.homePage = homePage;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case MUSICISTA:
			Musicista();
			break;
		case PRODOTTO:
			prodotto();
			break;
		case CONFERMA:
			try {
				conferma();
			} catch (SQLException e1) {
			}
			break;
		default:
			break;
		}
	}

	private void Musicista() {
		homePage.getAggiungiMusicista().setVisible(true);
	}

	private void prodotto() {
		homePage.getAggiungiProdotto().setVisible(true);
	}

	private void conferma() throws SQLException {
		ResultSet rs = HomePage.getPersonaleAutorizzato().getCatalogo();
		for (int i = 0; i < HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); i++) {
			rs.next();
			homePage.getPersonaleAutorizzato().modificaScorteProdotto(rs.getInt(1),
					Integer.valueOf(GestioneNegozio.getQuantita()[i].getValue().toString()));
		}
	}
}
