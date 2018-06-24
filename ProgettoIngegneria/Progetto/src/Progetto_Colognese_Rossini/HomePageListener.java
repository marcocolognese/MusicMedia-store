package Progetto_Colognese_Rossini;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;

public class HomePageListener implements ActionListener {

	private static HomePage homePage;
	public final String ACCEDI = "Accedi";
	public final String REGISTRATI = "Registrati";
	public final String CARRELLO = "Carrello";
	public final String CERCA = "Cerca";
	public final String AGGIORNA = "Aggiorna";
	public final String DETTAGLI = "Dettagli";
	public final String AGGIUNGI = "Aggiungi";
	public final String ESCI = "ESCI";

	HomePageListener(HomePage frame) {
		homePage = frame;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case ACCEDI:
			accedi();
			break;
		case REGISTRATI:
			registrati();
			break;
		case CARRELLO:
			try {
				carrello();
			} catch (SQLException e4) {
			}
			break;
		case CERCA:
			try {
				cerca();
			} catch (SQLException e1) {
			}
			break;
		case AGGIORNA:
			try {
				aggiorna();
			} catch (SQLException e1) {
			}
			break;
		case DETTAGLI:
			try {
				dettagli(e);
			} catch (SQLException e3) {
			}
			break;
		case AGGIUNGI:
			try {
				aggiungi(e);
			} catch (SQLException e2) {
			}
			break;
		case ESCI:
			try {
				esci();
			} catch (SQLException e1) {
			}
			break;
		default:
			break;
		}
	}

	private void accedi() {
		homePage.getAccedi().setVisible(true);
	}

	private void registrati() {
		homePage.getRegistrati().setVisible(true);
	}

	private void carrello() throws SQLException {
		homePage.setCarrello(homePage.getCliente().getNomeUtente());

		CarrelloPage.getSinistra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getCentro().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getDestra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));

		CarrelloPage.getSinistra().removeAll();
		CarrelloPage.getCentro().removeAll();
		CarrelloPage.getDestra().removeAll();
		ResultSet rs = HomePage.getCarrello().getProdotti();
		int i;
		BigDecimal prezzoTot = new BigDecimal(0);
		for (i = 0; i < HomePage.getCarrello().getTotProdottiCarrello(); i++) {
			rs.next();
			CarrelloPage.getIdprodotto()[i] = rs.getInt(1);
			CarrelloPage.getQuantitalabel()[i] = new JLabel("Quantità: ");
			CarrelloPage.getQuantita()[i] = new JSpinner(new SpinnerNumberModel(rs.getInt(6), 0, 99999, 1));
			CarrelloPage.getModifica()[i] = new JButton("Modifica quantità");
			CarrelloPage.getDestra1()[i] = new JPanel(new GridBagLayout());
			CarrelloPage.getImmagine()[i] = new JLabel();
			CarrelloPage.getImmagine()[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			BigDecimal prezzo = rs.getBigDecimal(3);
			for (int j = 0; j < rs.getInt(6) - 1; j++)
				prezzo = prezzo.add(rs.getBigDecimal(3));
			prezzoTot = prezzo.add(prezzoTot);
			if (HomePage.getCatalogo().getCatalogoScontato(HomePage.getCliente().getNomeUtente()))
				prezzo = prezzo.multiply(new BigDecimal(0.8));
			CarrelloPage.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: "
					+ rs.getString(4) + " <BR>&nbsp;PREZZO: " + rs.getBigDecimal(3).setScale(2, BigDecimal.ROUND_UP)
					+ "€</HTML>");
			;
			CarrelloPage.getDettagli()[i] = new JButton("Dettagli del prodotto");
			CarrelloPage.getSinistra().add(CarrelloPage.getImmagine()[i], BorderLayout.WEST);
			CarrelloPage.getCentro().add(CarrelloPage.getInfo()[i], BorderLayout.CENTER);
			CarrelloPage.getDestra1()[i].add(CarrelloPage.getQuantitalabel()[i]);
			CarrelloPage.getDestra1()[i].add(CarrelloPage.getQuantita()[i]);
			CarrelloPage.getDestra1()[i].add(CarrelloPage.getModifica()[i]);
			CarrelloPage.getDestra1()[i].add(CarrelloPage.getDettagli()[i]);
			CarrelloPage.getDestra().add(CarrelloPage.getDestra1()[i], BorderLayout.EAST);

			CarrelloPage.getModifica()[i].addActionListener(CarrelloPage.getCarrelloPageListener());
			CarrelloPage.getModifica()[i].setActionCommand(CarrelloPage.getCarrelloPageListener().MODIFICA);
			CarrelloPage.getDettagli()[i].addActionListener(CarrelloPage.getCarrelloPageListener());
			CarrelloPage.getDettagli()[i].setActionCommand(CarrelloPage.getCarrelloPageListener().DETTAGLI);
		}
		CarrelloPage.getCentropnl().add(CarrelloPage.getSinistra(), BorderLayout.WEST);
		CarrelloPage.getCentropnl().add(CarrelloPage.getCentro(), BorderLayout.CENTER);
		CarrelloPage.getCentropnl().add(CarrelloPage.getDestra(), BorderLayout.EAST);
		// Pannello Sud
		if (homePage.getCatalogo().getCatalogoScontato(homePage.getCliente().getNomeUtente()))
			CarrelloPage.getTotaleLabel().setText("  Totale: " + prezzoTot.setScale(2, BigDecimal.ROUND_UP)
					+ "€   Spedizione: 0.00€                                                                                                                          ");
		else
			CarrelloPage.getTotaleLabel().setText("  Totale: " + prezzoTot.setScale(2, BigDecimal.ROUND_UP)
					+ "€   Spedizione: 5.00€                                                                                                                          ");
		if (i == 0)
			CarrelloPage.getConfermabutton().setEnabled(false);
		else
			CarrelloPage.getConfermabutton().setEnabled(true);

		homePage.getCarrelloPage().setVisible(true);
	}

	private void cerca() throws SQLException {
		if (HomePage.getFiltrapercombobox().getSelectedItem().equals("        --- Seleziona ---")
				|| HomePage.getFiltratextfield().getText().trim().equals("")
				|| HomePage.getFiltratextfield().getText().equals("Cerca per..."))
			JOptionPane.showMessageDialog(null, "Alcuni parametri per la ricerca sono vuoti.", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
		else {
			Catalogo catalogo = Catalogo.getInstance();
			ResultSet rs = catalogo.getCatalogo();
			switch (HomePage.getFiltrapercombobox().getSelectedItem().toString()) {
			case "Genere":
				rs = catalogo.filtraPerGenere(HomePage.getFiltratextfield().getText().trim());
				break;
			case "Artista":
				rs = catalogo.filtraPerTitolare(HomePage.getFiltratextfield().getText().trim());
				break;
			case "Partecipante":
				rs = catalogo.filtraPerMusicistaPartecipante(HomePage.getFiltratextfield().getText().trim());
				break;
			case "Prezzo crescente":
				if (HomePage.getFiltratextfield().getText().trim().matches("[0-9]+[,.]{0,1}[0-9]*"))
					rs = catalogo.filtraPerPrezzoCrescente(
							new BigDecimal(HomePage.getFiltratextfield().getText().replace(',', '.')));
				else {
					JOptionPane.showMessageDialog(null, "Prezzo inserito non valido, inserire un numero.",
							"Attenzione!", JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
				break;
			case "Prezzo decrescente":
				if (HomePage.getFiltratextfield().getText().trim().matches("[0-9]+[,.]{0,1}[0-9]*"))
					rs = catalogo.filtraPerPrezzoDecrescente(
							new BigDecimal(HomePage.getFiltratextfield().getText().replace(',', '.')));
				else {
					JOptionPane.showMessageDialog(null, "Prezzo inserito non valido, inserire un numero.",
							"Attenzione!", JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
				break;
			}
			int i = 0;
			HomePage.getSinistra().removeAll();
			HomePage.getCentro().removeAll();
			HomePage.getDestra().removeAll();
			while (rs.next()) {
				HomePage.getIdProdotto()[i] = rs.getInt(1);
				HomePage.getDestra1()[i] = new JPanel(new GridBagLayout());
				HomePage.getImmagine()[i] = new JLabel();
				HomePage.getImmagine()[i].setIcon(new ImageIcon(
						new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
				HomePage.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: "
						+ rs.getString(4) + "<BR>&nbsp;PREZZO: " + rs.getBigDecimal(3) + "€</HTML>");
				HomePage.getAggiungi()[i] = new JButton("Aggiungi al carrello");
				HomePage.getDettagli()[i] = new JButton("Dettagli del prodotto");
				HomePage.getDestra1()[i].removeAll();
				HomePage.getAggiungi()[i].setEnabled(false);
				HomePage.getSinistra().add(HomePage.getImmagine()[i], BorderLayout.WEST);
				HomePage.getCentro().add(HomePage.getInfo()[i], BorderLayout.CENTER);
				HomePage.getDestra1()[i].add(HomePage.getAggiungi()[i]);
				HomePage.getDestra1()[i].add(HomePage.getDettagli()[i]);
				HomePage.getDestra().add(HomePage.getDestra1()[i], BorderLayout.EAST);

				HomePage.getDettagli()[i].addActionListener(this);
				HomePage.getDettagli()[i].setActionCommand(this.DETTAGLI);
				HomePage.getAggiungi()[i].addActionListener(this);
				HomePage.getAggiungi()[i].setActionCommand(this.AGGIUNGI);
				i++;
			}
			HomePage.getSinistra().setLayout(new GridLayout(i, 1));
			HomePage.getCentro().setLayout(new GridLayout(i, 1));
			HomePage.getDestra().setLayout(new GridLayout(i, 1));
			homePage.pack();
			homePage.setSize(HomePage.getLarghezza(), HomePage.getAltezza());
			homePage.setLocationRelativeTo(null);
		}
	}

	private void aggiorna() throws SQLException {
		Catalogo catalogo = Catalogo.getInstance();
		ResultSet rs = catalogo.getCatalogo();
		int i = 0;

		HomePage.setIdProdotto(new int[catalogo.getTotProdottiCatalogo()]);
		HomePage.setDestra1(new JPanel[catalogo.getTotProdottiCatalogo()]);
		HomePage.setImmagine(new JLabel[catalogo.getTotProdottiCatalogo()]);
		HomePage.setInfo(new JLabel[catalogo.getTotProdottiCatalogo()]);
		HomePage.setAggiungi(new JButton[catalogo.getTotProdottiCatalogo()]);
		HomePage.setDettagli(new JButton[catalogo.getTotProdottiCatalogo()]);

		HomePage.getSinistra().removeAll();
		HomePage.getCentro().removeAll();
		HomePage.getDestra().removeAll();
		HomePage.getSinistra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getCentro().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getDestra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		while (rs.next()) {
			HomePage.getIdprodotto()[i] = rs.getInt(1);
			HomePage.getDestra1()[i] = new JPanel(new GridBagLayout());
			HomePage.getImmagine()[i] = new JLabel();
			HomePage.getImmagine()[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			HomePage.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: "
					+ rs.getString(4) + "<BR>&nbsp;PREZZO: " + rs.getBigDecimal(3) + "€</HTML>");
			HomePage.getAggiungi()[i] = new JButton("Aggiungi al carrello");
			HomePage.getDettagli()[i] = new JButton("Dettagli del prodotto");
			HomePage.getDestra1()[i].removeAll();
			HomePage.getSinistra().add(HomePage.getImmagine()[i], BorderLayout.WEST);
			HomePage.getCentro().add(HomePage.getInfo()[i], BorderLayout.CENTER);
			if (HomePage.getCliente().getNomeUtente().length() == 0)
				HomePage.getAggiungi()[i].setEnabled(false);
			else
				HomePage.getAggiungi()[i].setEnabled(true);
			HomePage.getDestra1()[i].add(HomePage.getAggiungi()[i]);
			HomePage.getDestra1()[i].add(HomePage.getDettagli()[i]);
			HomePage.getDestra().add(HomePage.getDestra1()[i], BorderLayout.EAST);

			HomePage.getDettagli()[i].addActionListener(this);
			HomePage.getDettagli()[i].setActionCommand(this.DETTAGLI);
			HomePage.getAggiungi()[i].addActionListener(this);
			HomePage.getAggiungi()[i].setActionCommand(this.AGGIUNGI);
			i++;
		}
		homePage.pack();
		homePage.setSize(HomePage.getLarghezza(), HomePage.getAltezza());
		homePage.setLocationRelativeTo(null);
	}

	private void dettagli(ActionEvent event) throws SQLException {
		int i = 0;
		boolean nonTrovato = true;
		while (nonTrovato) {
			if (event.getSource() == HomePage.getDettagli()[i]) {
				nonTrovato = false;
				homePage.getProdottoPage().setId(homePage.getIdprodotto()[i]);
				Disk disk = new Disk();
				ResultSet rs = disk.getInfo(homePage.getIdprodotto()[i]);
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
				ProdottoPage.getSudpnl().setVisible(true);
			}
			i++;
		}
	}

	private void aggiungi(ActionEvent event) throws SQLException {
		int i = 0;
		boolean nonTrovato = true;
		Disk disco = new Disk();
		while (nonTrovato) {
			if (event.getSource() == HomePage.getAggiungi()[i]) {
				ResultSet rs = disco.getInfo((int) HomePage.getIdprodotto()[i]);
				if (rs.getInt(13) > 0) {
					HomePage.getCarrello().aggiungiProdotto((int) HomePage.getIdprodotto()[i], 1);
					int temp = 0;
					for (int a = 0; a < homePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); a++)
						if (homePage.getIdProdotto()[i] == homePage.getGestioneNegozio().getIdprodotto()[a])
							temp = a;

					homePage.getGestioneNegozio().getQuantita()[temp]
							.setValue((int) homePage.getGestioneNegozio().getQuantita()[temp].getValue() - 1);

					homePage.getGestioneNegozio().pack();
					homePage.getGestioneNegozio().setSize(homePage.getGestioneNegozio().getLarghezza(),
							homePage.getGestioneNegozio().getAltezza());
					homePage.getGestioneNegozio().setLocationRelativeTo(null);
				} else
					JOptionPane.showMessageDialog(null, "Sono diponibili solo " + rs.getInt(13) + " prodotti.",
							"Attenzione!", JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
				nonTrovato = false;
			}
			i++;
		}
	}

	private void esci() throws SQLException {
		HomePage.getCiaolabel().setText("  Ciao, accedi o registrati!");

		HomePage.getLogpanel1().remove(HomePage.getEscibutton());
		HomePage.getLogpanel1().add(HomePage.getAccedibutton());
		HomePage.getLogpanel1().add(HomePage.getRegistratibutton());
		HomePage.getCarrellobutton().setEnabled(false);
		ResultSet rs = HomePage.getCatalogo().getCatalogo();
		homePage.getProdottoPage().getAggiungibutton().setEnabled(false);
		homePage.getProdottoPage().getQuantitaspinner().setEnabled(false);
		homePage.getCliente().setNomeUtente("");

		homePage.getSinistra().removeAll();
		homePage.getCentro().removeAll();
		homePage.getDestra().removeAll();

		HomePage.getSinistra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getCentro().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getDestra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		for (int i = 0; i < HomePage.getCatalogo().getTotProdottiCatalogo(); i++) {
			rs.next();
			HomePage.getIdprodotto()[i] = rs.getInt(1);
			HomePage.getDestra1()[i] = new JPanel(new GridBagLayout());
			HomePage.getImmagine()[i] = new JLabel();
			HomePage.getImmagine()[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			HomePage.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: "
					+ rs.getString(4) + " <BR>&nbsp;PREZZO: " + rs.getBigDecimal(3) + "€</HTML>");
			;
			HomePage.getAggiungi()[i] = new JButton("Aggiungi al carrello");
			HomePage.getDettagli()[i] = new JButton("Dettagli del prodotto");
			HomePage.getSinistra().add(HomePage.getImmagine()[i], BorderLayout.WEST);
			HomePage.getCentro().add(HomePage.getInfo()[i], BorderLayout.CENTER);
			HomePage.getAggiungi()[i].setEnabled(false);
			HomePage.getDestra1()[i].removeAll();
			HomePage.getDestra1()[i].add(HomePage.getAggiungi()[i]);
			HomePage.getDestra1()[i].add(HomePage.getDettagli()[i]);
			HomePage.getDestra().add(HomePage.getDestra1()[i], BorderLayout.EAST);

			HomePage.getAggiungi()[i].addActionListener(homePage.getHomePageListener());
			HomePage.getAggiungi()[i].setActionCommand(homePage.getHomePageListener().AGGIUNGI);
			HomePage.getDettagli()[i].addActionListener(homePage.getHomePageListener());
			HomePage.getDettagli()[i].setActionCommand(homePage.getHomePageListener().DETTAGLI);
		}
	}
}