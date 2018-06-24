package Progetto_Colognese_Rossini;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import javax.swing.*;

public class RegistratiListener implements ActionListener {

	private static Registrati registrati;
	private static HomePage homePage;
	public final String REGISTRATI = "Registrati";

	RegistratiListener(Registrati registrati, HomePage homePage) {
		this.registrati = registrati;
		this.homePage = homePage;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case REGISTRATI:
			try {
				registrati();
			} catch (SQLException e2) {
			}
			break;
		default:
			break;
		}
	}

	private void registrati() throws SQLException {
		if (Registrati.getNomeutentetext().getText().trim().length() != 0
				&& Registrati.getPasswordtext().getText().trim().length() != 0
				&& Registrati.getNometext().getText().trim().length() != 0
				&& Registrati.getCognometext().getText().trim().length() != 0
				&& Registrati.getCodfistext().getText().trim().length() != 0
				&& Registrati.getCittatext().getText().trim().length() != 0
				&& Registrati.getTelefonotext().getText().trim().length() != 0) {
			if (Registrati.getCellularetext().getText().trim().length() != 0) {
				if (!HomePage.getCliente().signUp(Registrati.getCodfistext().getText().trim(),
						Registrati.getNomeutentetext().getText().trim(), Registrati.getPasswordtext().getText().trim(),
						Registrati.getNometext().getText().trim(), Registrati.getCognometext().getText().trim(),
						Registrati.getCittatext().getText().trim(), Registrati.getTelefonotext().getText().trim(),
						Registrati.getCellularetext().getText().trim())) {
					JOptionPane.showMessageDialog(null, "Il nome utente scelto non è disponibile", "Attenzione!",
							JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
			} else {
				if (!HomePage.getCliente().signUp(Registrati.getCodfistext().getText().trim(),
						Registrati.getNomeutentetext().getText().trim(), Registrati.getPasswordtext().getText().trim(),
						Registrati.getNometext().getText().trim(), Registrati.getCognometext().getText().trim(),
						Registrati.getCittatext().getText().trim(), Registrati.getTelefonotext().getText().trim())) {
					JOptionPane.showMessageDialog(null, "Il nome utente scelto non è disponibile", "Attenzione!",
							JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Alcuni dati inseriti non sono validi", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
			return;
		}

		Catalogo catalogo = Catalogo.getInstance();
		for (int i = 0; i < catalogo.getTotProdottiCatalogo(); i++)
			HomePage.getAggiungi()[i].setEnabled(true);
		HomePage.getCarrellobutton().setEnabled(true);
		HomePage.getCiaolabel().setText("  Ciao, " + HomePage.getCliente().getNomeUtente() + "!");
		ProdottoPage.getQuantitaspinner().setEnabled(true);
		ProdottoPage.getAggiungibutton().setEnabled(true);
		homePage.getProdottoPage().getAggiungibutton().setEnabled(true);
		homePage.getProdottoPage().getQuantitaspinner().setEnabled(true);

		int i = 0;
		HomePage.getSinistra().removeAll();
		HomePage.getCentro().removeAll();
		HomePage.getDestra().removeAll();
		List<ResultSet> resultSet = catalogo.getCatalogoPersonale(HomePage.getCliente().getNomeUtente());
		i = 0;
		for (ResultSet rs : resultSet) {
			while (rs.next()) {
				HomePage.getDestra1()[i] = new JPanel(new GridBagLayout());
				HomePage.getImmagine()[i] = new JLabel();
				HomePage.getImmagine()[i].setIcon(new ImageIcon(
						new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
				BigDecimal prezzo = rs.getBigDecimal(3);
				if (catalogo.getCatalogoScontato(homePage.getCliente().getNomeUtente()))
					prezzo = prezzo.multiply(new BigDecimal(0.8));
				HomePage.getInfo()[i] = new JLabel(
						"<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: " + rs.getString(4)
								+ "<BR>&nbsp;PREZZO: " + prezzo.setScale(2, BigDecimal.ROUND_UP) + "€</HTML>");
				HomePage.getAggiungi()[i] = new JButton("Aggiungi al carrello");
				HomePage.getDettagli()[i] = new JButton("Dettagli del prodotto");
				HomePage.getDestra1()[i].removeAll();
				HomePage.getSinistra().add(HomePage.getImmagine()[i], BorderLayout.WEST);
				HomePage.getCentro().add(HomePage.getInfo()[i], BorderLayout.CENTER);
				HomePage.getAggiungi()[i].setEnabled(true);
				HomePage.getDestra1()[i].add(HomePage.getAggiungi()[i]);
				HomePage.getDestra1()[i].add(HomePage.getDettagli()[i]);
				HomePage.getDestra().add(HomePage.getDestra1()[i], BorderLayout.EAST);

				HomePage.getDettagli()[i].addActionListener(homePage.getHomePageListener());
				HomePage.getDettagli()[i].setActionCommand(homePage.getHomePageListener().DETTAGLI);
				HomePage.getAggiungi()[i].addActionListener(homePage.getHomePageListener());
				HomePage.getAggiungi()[i].setActionCommand(homePage.getHomePageListener().AGGIUNGI);
				i++;
			}
		}
		HomePage.getSinistra().setLayout(new GridLayout(i, 1));
		HomePage.getCentro().setLayout(new GridLayout(i, 1));
		HomePage.getDestra().setLayout(new GridLayout(i, 1));
		HomePage.getLogpanel1().remove(HomePage.getAccedibutton());
		HomePage.getLogpanel1().remove(HomePage.getRegistratibutton());
		HomePage.getLogpanel1().add(HomePage.getEscibutton());
		registrati.dispose();
		homePage.pack();
		homePage.setSize(HomePage.getLarghezza(), HomePage.getAltezza());
		homePage.setLocationRelativeTo(null);
	}

}