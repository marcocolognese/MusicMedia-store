package Progetto_Colognese_Rossini;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CarrelloPageListener implements ActionListener {

	private static CarrelloPage carrelloPage;
	private static HomePage homePage;
	public final String DETTAGLI = "Dettagli";
	public final String MODIFICA = "Modifica";
	public final String SVUOTA = "Svuota";
	public final String CONFERMA = "Conferma";

	CarrelloPageListener(CarrelloPage carrelloPage, HomePage homePage) {
		this.carrelloPage = carrelloPage;
		this.homePage = homePage;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case DETTAGLI:
			try {
				dettagli(e);
			} catch (SQLException e1) {
			}
			break;
		case MODIFICA:
			try {
				modifica(e);
			} catch (SQLException e1) {
			}
			break;
		case SVUOTA:
			try {
				svuota();
			} catch (SQLException e1) {
			}
			break;
		case CONFERMA:
			conferma();
			break;
		default:
			break;
		}
	}

	private void modifica(ActionEvent event) throws SQLException {
		int i = 0;
		boolean nonTrovato = true;
		Disk disco = new Disk();
		while (nonTrovato) {
			if (event.getSource() == CarrelloPage.getModifica()[i]) {
				ResultSet rs = disco.getInfo((int) CarrelloPage.getIdprodotto()[i]);
				if (rs.getInt(13) + homePage.getCarrello()
						.getQuantitàProdotto(rs.getInt(1)) >= (int) CarrelloPage.getQuantita()[i].getValue())
					HomePage.getCarrello().modificaQuantitàProdotto((int) CarrelloPage.getIdprodotto()[i],
							(int) CarrelloPage.getQuantita()[i].getValue());
				else {
					JOptionPane.showMessageDialog(null,
							"Sono diponibili solo altri " + rs.getInt(13)
									+ " prodotti di questo tipo e sono stati aggiunti al carrello.\n",
							"Attenzione!", JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					CarrelloPage.getQuantita()[i].setValue(rs.getInt(13)
							+ HomePage.getCarrello().getQuantitàProdotto((int) CarrelloPage.getIdprodotto()[i]));
					HomePage.getCarrello().modificaQuantitàProdotto((int) CarrelloPage.getIdprodotto()[i], rs.getInt(13)
							+ HomePage.getCarrello().getQuantitàProdotto((int) CarrelloPage.getIdprodotto()[i]));
					carrelloPage.pack();
					carrelloPage.setSize(carrelloPage.getLarghezza(), carrelloPage.getAltezza());
					carrelloPage.setLocationRelativeTo(null);
				}
				if ((int) CarrelloPage.getQuantita()[i].getValue() == 0) {
					carrelloPage.getSinistra().remove(carrelloPage.getImmagine()[i]);
					carrelloPage.getCentro().remove(carrelloPage.getInfo()[i]);
					carrelloPage.getDestra().remove(carrelloPage.getDestra1()[i]);
				}
				if (homePage.getCarrello().getTotProdottiCarrello() == 0)
					carrelloPage.getConfermabutton().setEnabled(false);

				ResultSet rs1 = HomePage.getCarrello().getProdotti();
				BigDecimal prezzoTot = new BigDecimal(0);
				while (rs1.next()) {
					BigDecimal prezzo = rs1.getBigDecimal(3);
					for (int j = 0; j < rs1.getInt(6) - 1; j++)
						prezzo = prezzo.add(rs1.getBigDecimal(3));
					prezzoTot = prezzo.add(prezzoTot);
					if (HomePage.getCatalogo().getCatalogoScontato(HomePage.getCliente().getNomeUtente()))
						prezzo = prezzo.multiply(new BigDecimal(0.8));
				}
				if (homePage.getCatalogo().getCatalogoScontato(homePage.getCliente().getNomeUtente()))
					CarrelloPage.getTotaleLabel().setText("  Totale: " + prezzoTot.setScale(2, BigDecimal.ROUND_UP)
							+ "€   Spedizione: 0.00€                                                                                                                          ");
				else
					CarrelloPage.getTotaleLabel().setText("  Totale: " + prezzoTot.setScale(2, BigDecimal.ROUND_UP)
							+ "€   Spedizione: 5.00€                                                                                                                          ");

				carrelloPage.pack();
				carrelloPage.setSize(CarrelloPage.getLarghezza(), CarrelloPage.getAltezza());
				carrelloPage.setLocationRelativeTo(null);

				int temp = 0;
				for (int a = 0; a < homePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); a++)
					if (homePage.getCarrelloPage().getIdprodotto()[i] == homePage.getGestioneNegozio()
							.getIdprodotto()[a])
						temp = a;

				Disk disco1 = new Disk();
				homePage.getGestioneNegozio().getQuantita()[temp]
						.setValue(disco1.getScorte(homePage.getCarrelloPage().getIdprodotto()[i]));// (int)homePage.getGestioneNegozio().getQuantita()[temp].getValue()-);

				homePage.getGestioneNegozio().pack();
				homePage.getGestioneNegozio().setSize(homePage.getGestioneNegozio().getLarghezza(),
						homePage.getGestioneNegozio().getAltezza());
				homePage.getGestioneNegozio().setLocationRelativeTo(null);

				nonTrovato = false;
			}
			i++;
		}
	}

	private void dettagli(ActionEvent event) throws SQLException {
		int i = 0;
		boolean nonTrovato = true;
		while (nonTrovato) {
			if (event.getSource() == carrelloPage.getDettagli()[i]) {
				nonTrovato = false;
				homePage.getProdottoPage().setId(carrelloPage.getIdprodotto()[i]);
				Disk disk = new Disk();
				ResultSet rs = disk.getInfo(carrelloPage.getIdprodotto()[i]);
				homePage.getProdottoPage().getImmagine().setIcon(new ImageIcon(
						new ImageIcon(rs.getString(4)).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
				BigDecimal prezzo = rs.getBigDecimal(5);
				if (HomePage.getCatalogo().getCatalogoScontato(homePage.getCliente().getNomeUtente()))
					prezzo = prezzo.multiply(new BigDecimal(0.8));
				homePage.getProdottoPage().getInfo()
						.setText("<HTML>&nbsp;<B>TITOLO:</B> " + rs.getString(2) + "<BR>&nbsp;<B>TRACCE:</B> "
								+ rs.getString(3) + "<BR>&nbsp;<B>PREZZO:</B> "
								+ prezzo.setScale(2, BigDecimal.ROUND_UP) + "€<BR>&nbsp;<B>TITOLARE:</B> "
								+ rs.getString(7) + "<BR>&nbsp;<B>DESCRIZIONE:</B> " + rs.getString(8)
								+ "<BR>&nbsp;<B>GENERE:</B> " + rs.getString(9) + "<BR>&nbsp;<B>TIPO:</B> "
								+ rs.getString(10) + "<BR>&nbsp;<B>MUSICISTI PARTECIPANTI:</B> " + rs.getString(11)
								+ "<BR>&nbsp;<B>STRUMENTI:</B> " + rs.getString(12));
				homePage.getProdottoPage().setVisible(true);
				ProdottoPage.getSudpnl().setVisible(false);
			}
			i++;
		}
	}

	private void svuota() throws SQLException {

		HomePage.getCarrello().svuota();

		for (int a = 0; a < homePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); a++) {
			Disk disco1 = new Disk();
			homePage.getGestioneNegozio().getQuantita()[a]
					.setValue(disco1.getScorte(homePage.getGestioneNegozio().getIdprodotto()[a]));
		}

		homePage.getGestioneNegozio().pack();
		homePage.getGestioneNegozio().setSize(homePage.getGestioneNegozio().getLarghezza(),
				homePage.getGestioneNegozio().getAltezza());
		homePage.getGestioneNegozio().setLocationRelativeTo(null);

		CarrelloPage.getConfermabutton().setEnabled(false);

		CarrelloPage.getSinistra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getCentro().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getDestra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getSinistra().removeAll();
		CarrelloPage.getCentro().removeAll();
		CarrelloPage.getDestra().removeAll();

		if (homePage.getCatalogo().getCatalogoScontato(homePage.getCliente().getNomeUtente()))
			CarrelloPage.getTotaleLabel().setText(
					"  Totale: 0.00€   Spedizione: 0.00€                                                                                                                          ");
		else
			CarrelloPage.getTotaleLabel().setText(
					"  Totale: 0.00€   Spedizione: 5.00€                                                                                                                          ");

		carrelloPage.pack();
		carrelloPage.setSize(CarrelloPage.getLarghezza(), CarrelloPage.getAltezza());
		carrelloPage.setLocationRelativeTo(null);

	}

	private void conferma() {
		homePage.getOrdine().setVisible(true);
	}
}
