package Progetto_Colognese_Rossini;

import java.awt.*;
import javax.swing.*;

public class AggiungiProdotto extends JFrame {
	private static final String titolo = "Aggiungi Prodotto";
	private static final JPanel ovest = new JPanel(new GridLayout(12, 1));
	private static final JPanel est = new JPanel(new GridLayout(12, 1));
	private static final JLabel codiceLabel = new JLabel(" Codice univoco*: ");
	private static final JTextField codiceText = new JTextField(20);
	private static final JLabel titoloLabel = new JLabel(" Titolo*: ");
	private static final JTextField titoloText = new JTextField(20);
	private static final JLabel tipoLabel = new JLabel(" Modalità di consegna: ");
	private static final JComboBox<String> tipoComboBox = new JComboBox<String>(new String[] { "CD", "DVD" });
	private static final JLabel titoliPezziLabel = new JLabel(" Titoli dei pezzi contenuti*: ");
	private static final JTextField titoliPezziText = new JTextField(20);
	private static final JLabel fotoLabel = new JLabel(" Foto della copertina: ");
	private static final JTextField fotoText = new JTextField(17);
	private static final JLabel prezzoLabel = new JLabel(" Prezzo*: ");
	private static final JTextField prezzoText = new JTextField(17);
	private static final JLabel titolareLabel = new JLabel(" Musicista/Band titolare*: ");
	private static final JTextField titolareText = new JTextField(17);
	private static final JLabel descrizioneLabel = new JLabel(" Descrizione*: ");
	private static final JTextField descrizioneText = new JTextField(20);
	private static final JLabel genereLabel = new JLabel(" Genere*: ");
	private static final JTextField genereText = new JTextField(20);
	private static final JLabel musicistiLabel = new JLabel(" Musicisti partecipanti*: ");
	private static final JTextField musicistiText = new JTextField(20);
	private static final JLabel strumentiLabel = new JLabel(" Strumenti utilizzati*: ");
	private static final JTextField strumentiText = new JTextField(20);
	private static final JLabel quantitaLabel = new JLabel(" Quantità: ");
	private static final JPanel quantita = new JPanel(new SpringLayout());
	private static final JSpinner quantitaSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
	private static final JPanel sud = new JPanel();
	private static final JButton aggiungiButton = new JButton("Aggiungi prodotto");

	private static AggiungiProdottoListener aggiungiProdottoListener;

	public AggiungiProdotto(HomePage homePage, GestioneNegozio gestioneNegozio) {
		super(titolo);

		aggiungiProdottoListener = new AggiungiProdottoListener(this, homePage, gestioneNegozio);

		ovest.add(codiceLabel);
		est.add(codiceText);
		ovest.add(titoloLabel);
		est.add(titoloText);
		ovest.add(titoliPezziLabel);
		est.add(titoliPezziText);
		ovest.add(tipoLabel);
		est.add(tipoComboBox);
		ovest.add(fotoLabel);
		est.add(fotoText);
		ovest.add(prezzoLabel);
		est.add(prezzoText);
		ovest.add(titolareLabel);
		est.add(titolareText);
		ovest.add(descrizioneLabel);
		est.add(descrizioneText);
		ovest.add(genereLabel);
		est.add(genereText);
		ovest.add(musicistiLabel);
		est.add(musicistiText);
		ovest.add(strumentiLabel);
		est.add(strumentiText);
		ovest.add(quantitaLabel);
		quantita.add(quantitaSpinner);
		est.add(quantita);
		sud.add(aggiungiButton);

		aggiungiButton.addActionListener(aggiungiProdottoListener);
		aggiungiButton.setActionCommand(aggiungiProdottoListener.AGGIUNGI);

		// Container Pricipale
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(ovest, BorderLayout.WEST);
		frmContentPane.add(est, BorderLayout.EAST);
		frmContentPane.add(sud, BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public static String getTitolo() {
		return titolo;
	}

	public static JPanel getOvest() {
		return ovest;
	}

	public static JPanel getEst() {
		return est;
	}

	public static JLabel getCodicelabel() {
		return codiceLabel;
	}

	public static JTextField getCodicetext() {
		return codiceText;
	}

	public static JLabel getTitololabel() {
		return titoloLabel;
	}

	public static JTextField getTitolotext() {
		return titoloText;
	}

	public static JLabel getTipolabel() {
		return tipoLabel;
	}

	public static JComboBox<String> getTipocombobox() {
		return tipoComboBox;
	}

	public static JLabel getTitolipezzilabel() {
		return titoliPezziLabel;
	}

	public static JTextField getTitolipezzitext() {
		return titoliPezziText;
	}

	public static JLabel getFotolabel() {
		return fotoLabel;
	}

	public static JTextField getFototext() {
		return fotoText;
	}

	public static JLabel getPrezzolabel() {
		return prezzoLabel;
	}

	public static JTextField getPrezzotext() {
		return prezzoText;
	}

	public static JLabel getTitolarelabel() {
		return titolareLabel;
	}

	public static JTextField getTitolaretext() {
		return titolareText;
	}

	public static JLabel getDescrizionelabel() {
		return descrizioneLabel;
	}

	public static JTextField getDescrizionetext() {
		return descrizioneText;
	}

	public static JLabel getGenerelabel() {
		return genereLabel;
	}

	public static JTextField getGeneretext() {
		return genereText;
	}

	public static JLabel getMusicistilabel() {
		return musicistiLabel;
	}

	public static JTextField getMusicistitext() {
		return musicistiText;
	}

	public static JLabel getStrumentilabel() {
		return strumentiLabel;
	}

	public static JTextField getStrumentitext() {
		return strumentiText;
	}

	public static JLabel getQuantitalabel() {
		return quantitaLabel;
	}

	public static JPanel getQuantita() {
		return quantita;
	}

	public static JSpinner getQuantitaspinner() {
		return quantitaSpinner;
	}

	public static JPanel getSud() {
		return sud;
	}

	public static JButton getAggiungibutton() {
		return aggiungiButton;
	}

	public static AggiungiProdottoListener getAggiungiProdottoListener() {
		return aggiungiProdottoListener;
	}
}