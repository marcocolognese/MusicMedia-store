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

public class AggiungiProdottoListener implements ActionListener {
	private static AggiungiProdotto aggiungiProdotto;
	private static HomePage homePage;
	private static GestioneNegozio gestioneNegozio;
	public final String AGGIUNGI = "aggiungi";

	AggiungiProdottoListener(AggiungiProdotto aggiungiProdotto, HomePage homePage, GestioneNegozio gestioneNegozio) {
		this.aggiungiProdotto = aggiungiProdotto;
		this.homePage = homePage;
		this.gestioneNegozio = gestioneNegozio;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case AGGIUNGI:
			try {
				Aggiungi();
			} catch (SQLException e1) {
			}
			break;
		default:
			break;
		}
	}

	private void Aggiungi() throws SQLException {
		if (aggiungiProdotto.getCodicetext().getText().trim().matches("[0-9]+")
				&& aggiungiProdotto.getTitolotext().getText().trim().length() != 0
				&& aggiungiProdotto.getTitolipezzitext().getText().trim().length() != 0
				&& aggiungiProdotto.getPrezzotext().getText().trim().length() != 0
				&& aggiungiProdotto.getTitolaretext().getText().trim().length() != 0
				&& aggiungiProdotto.getDescrizionetext().getText().trim().length() != 0
				&& aggiungiProdotto.getGeneretext().getText().trim().length() != 0
				&& aggiungiProdotto.getMusicistitext().getText().trim().length() != 0
				&& aggiungiProdotto.getStrumentitext().getText().trim().length() != 0) {
			if (aggiungiProdotto.getFototext().getText().trim().length() != 0) {
				if (aggiungiProdotto.getPrezzotext().getText().trim().matches("[0-9]+")
						|| aggiungiProdotto.getPrezzotext().getText().trim().matches("[0-9]+[,.]{1}[0-9]{2}")) {
					int flag = HomePage.getPersonaleAutorizzato().aggiungiProdotto(
							Integer.valueOf(aggiungiProdotto.getCodicetext().getText().trim()),
							aggiungiProdotto.getTitolotext().getText().trim(),
							aggiungiProdotto.getTitolipezzitext().getText().trim(),
							aggiungiProdotto.getFototext().getText().trim(),
							BigDecimal.valueOf(Double
									.valueOf(aggiungiProdotto.getPrezzotext().getText().trim().replace(',', '.'))),
							aggiungiProdotto.getTitolaretext().getText().trim(),
							aggiungiProdotto.getDescrizionetext().getText().trim(),
							aggiungiProdotto.getGeneretext().getText().trim(),
							aggiungiProdotto.getTipocombobox().getSelectedItem().toString(),
							aggiungiProdotto.getMusicistitext().getText().trim(),
							aggiungiProdotto.getStrumentitext().getText().trim(),
							Integer.valueOf(aggiungiProdotto.getQuantitaspinner().getValue().toString()));
					if (flag == 2) {
						JOptionPane.showMessageDialog(null,
								"Il musicista inserito non è presente tra quelli del sistema.", "Attenzione!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
						return;
					}
					if (flag == 3) {
						JOptionPane.showMessageDialog(null, "Il codice deve essere univoco.", "Attenzione!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Inserire un prezzo corretto.", "Attenzione!",
							JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
			} else {
				if (aggiungiProdotto.getPrezzotext().getText().trim().matches("[0-9]+")
						|| aggiungiProdotto.getPrezzotext().getText().trim().matches("[0-9]+[,.]{1}[0-9]{2}")) {
					int flag = HomePage.getPersonaleAutorizzato().aggiungiProdotto(
							Integer.valueOf(aggiungiProdotto.getCodicetext().getText().trim()),
							aggiungiProdotto.getTitolotext().getText().trim(),
							aggiungiProdotto.getTitolipezzitext().getText().trim(),
							BigDecimal.valueOf(Double
									.valueOf(aggiungiProdotto.getPrezzotext().getText().trim().replace(',', '.'))),
							aggiungiProdotto.getTitolaretext().getText().trim(),
							aggiungiProdotto.getDescrizionetext().getText().trim(),
							aggiungiProdotto.getGeneretext().getText().trim(),
							aggiungiProdotto.getTipocombobox().getSelectedItem().toString(),
							aggiungiProdotto.getMusicistitext().getText().trim(),
							aggiungiProdotto.getStrumentitext().getText().trim(),
							Integer.valueOf(aggiungiProdotto.getQuantitaspinner().getValue().toString()));
					if (flag == 2) {
						JOptionPane.showMessageDialog(null,
								"Il musicista inserito non è presente tra quelli del sistema.", "Attenzione!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
						return;
					}
					if (flag == 3) {
						JOptionPane.showMessageDialog(null, "Il codice deve essere univoco.", "Attenzione!",
								JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Inserire un prezzo corretto.", "Attenzione!",
							JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
					return;
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Completare tutti i campi correttamente.", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
			return;
		}

		GestioneNegozio.setSinistra(new JPanel(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1)));
		GestioneNegozio.setCentro(new JPanel(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1)));
		GestioneNegozio.setDestra(new JPanel(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1)));
		GestioneNegozio.setDestra1(new JPanel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		GestioneNegozio.setImmagine(new JLabel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		GestioneNegozio.setIdProdotto(new int[homePage.getCatalogo().getTotProdottiCatalogo()]);
		GestioneNegozio.setInfo(new JLabel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		GestioneNegozio.setQuantitaLabel(new JLabel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		GestioneNegozio.setQuantita(new JSpinner[homePage.getCatalogo().getTotProdottiCatalogo()]);

		HomePage.setIdProdotto(new int[homePage.getCatalogo().getTotProdottiCatalogo()]);
		HomePage.setDestra1(new JPanel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		HomePage.setImmagine(new JLabel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		HomePage.setInfo(new JLabel[homePage.getCatalogo().getTotProdottiCatalogo()]);
		HomePage.setAggiungi(new JButton[homePage.getCatalogo().getTotProdottiCatalogo()]);
		HomePage.setDettagli(new JButton[homePage.getCatalogo().getTotProdottiCatalogo()]);

		Disk disco = new Disk();
		ResultSet rs = homePage.getCatalogo().getCatalogo();
		int i = 0;
		while (rs.next()) {
			GestioneNegozio.getIdprodotto()[i] = rs.getInt(1);
			GestioneNegozio.getDestra1()[i] = new JPanel(new GridBagLayout());
			GestioneNegozio.getImmagine()[i] = new JLabel();
			GestioneNegozio.getImmagine()[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			GestioneNegozio.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: "
					+ rs.getString(4) + " <BR>&nbsp;PREZZO: " + rs.getBigDecimal(3) + "€</HTML>");
			GestioneNegozio.getQuantitalabel()[i] = new JLabel("Quantità disponibile: ");
			GestioneNegozio.getQuantita()[i] = new JSpinner(
					new SpinnerNumberModel(disco.getScorte(rs.getInt(1)), 0, 99999, 1));
			GestioneNegozio.getSinistra().add(GestioneNegozio.getImmagine()[i], BorderLayout.WEST);
			GestioneNegozio.getCentro().add(GestioneNegozio.getInfo()[i], BorderLayout.CENTER);
			GestioneNegozio.getDestra1()[i].add(GestioneNegozio.getQuantitalabel()[i]);
			GestioneNegozio.getDestra1()[i].add(GestioneNegozio.getQuantita()[i]);
			GestioneNegozio.getDestra().add(GestioneNegozio.getDestra1()[i], BorderLayout.EAST);
			i++;
		}
		GestioneNegozio.getCentropnl().add(GestioneNegozio.getSinistra(), BorderLayout.WEST);
		GestioneNegozio.getCentropnl().add(GestioneNegozio.getCentro(), BorderLayout.CENTER);
		GestioneNegozio.getCentropnl().add(GestioneNegozio.getDestra(), BorderLayout.EAST);
		gestioneNegozio.pack();
		gestioneNegozio.setSize(GestioneNegozio.getLarghezza(), GestioneNegozio.getAltezza());
		gestioneNegozio.setLocationRelativeTo(null);

		Catalogo catalogo = Catalogo.getInstance();
		ResultSet rs2 = catalogo.getCatalogo();
		i = 0;
		HomePage.getSinistra().removeAll();
		HomePage.getCentro().removeAll();
		HomePage.getDestra().removeAll();
		HomePage.getSinistra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getCentro().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		HomePage.getDestra().setLayout(new GridLayout(HomePage.getCatalogo().getTotProdottiCatalogo(), 1));
		while (rs2.next()) {
			HomePage.getDestra1()[i] = new JPanel(new GridBagLayout());
			HomePage.getImmagine()[i] = new JLabel();
			HomePage.getImmagine()[i].setIcon(new ImageIcon(
					new ImageIcon(rs2.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			HomePage.getInfo()[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs2.getString(2) + "<BR>&nbsp;ARTISTA: "
					+ rs2.getString(4) + "<BR>&nbsp;PREZZO: " + rs2.getBigDecimal(3) + "€</HTML>");
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

			i++;
		}
		homePage.pack();
		homePage.setSize(HomePage.getLarghezza(), HomePage.getAltezza());
		homePage.setLocationRelativeTo(null);

		aggiungiProdotto.dispose();
	}
}