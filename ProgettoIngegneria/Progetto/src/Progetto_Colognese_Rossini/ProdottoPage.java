package Progetto_Colognese_Rossini;

import java.awt.*;
import javax.swing.*;

public class ProdottoPage extends JFrame {
	private static final String titolo = "Prodotto";
	private static final int larghezza = 850, altezza = 650;

	// Pannello Nord
	private static final JLabel titoloLabel = new JLabel("Prodotto");
	private static final JPanel nordPnl = new JPanel();
	// Pannello Centrale
	private static final JPanel centroPnl = new JPanel(new BorderLayout());
	private static final JPanel nord = new JPanel(new GridLayout(1, 1));
	private static final JPanel centro = new JPanel(new GridLayout(2, 1));
	private static final JPanel nord1 = new JPanel(new GridBagLayout());
	private static final JLabel immagine = new JLabel();
	private static final JLabel info = new JLabel();
	// Pannello Sud
	private static final JLabel quantitaLabel = new JLabel("Quantità: ");
	private static final JSpinner quantitaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99999, 1));
	private static final JButton aggiungiButton = new JButton("Aggiungi al carrello");
	private static final JPanel sudPnl = new JPanel();

	private static int id = 0;
	private ProdottoPageListener prodottoPageListener;

	public ProdottoPage(int id, HomePage homePage) {
		super(titolo);
		this.id = id;
		this.setVisible(true);

		// Pannello Nord
		titoloLabel.setFont(new Font("Serif", Font.BOLD, 45));
		nordPnl.add(titoloLabel);
		nordPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nordPnl.setBackground(Color.lightGray);
		// Pannello Centrale
		nord1.add(immagine);
		nord.add(nord1);
		centro.add(nord);
		info.setFont(new Font("Serif", Font.PLAIN, 17));
		centro.add(info);
		centroPnl.add(centro);
		// Pannello Sud
		quantitaLabel.setFont(new Font("Serif", Font.PLAIN, 17));
		quantitaSpinner.setValue(1);
		quantitaSpinner.setEnabled(false);
		aggiungiButton.setEnabled(false);
		sudPnl.add(quantitaLabel);
		sudPnl.add(quantitaSpinner);
		sudPnl.add(aggiungiButton);
		sudPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Container Pricipale
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(nordPnl, BorderLayout.NORTH);
		frmContentPane.add(centroPnl, BorderLayout.CENTER);
		frmContentPane.add(sudPnl, BorderLayout.SOUTH);

		// Impostazioni di visualizzazione
		this.pack();
		this.setSize(larghezza, altezza);
		this.setLocationRelativeTo(null);

		prodottoPageListener = new ProdottoPageListener(this, homePage);
		aggiungiButton.addActionListener(prodottoPageListener);
		aggiungiButton.setActionCommand(prodottoPageListener.AGGIUNGI);
	}

	public static String getTitolo() {
		return titolo;
	}

	public static int getLarghezza() {
		return larghezza;
	}

	public static int getAltezza() {
		return altezza;
	}

	public static JLabel getTitololabel() {
		return titoloLabel;
	}

	public static JPanel getNordpnl() {
		return nordPnl;
	}

	public static JPanel getCentropnl() {
		return centroPnl;
	}

	public static JPanel getNord() {
		return nord;
	}

	public static JPanel getCentro() {
		return centro;
	}

	public static JPanel getNord1() {
		return nord1;
	}

	public static JLabel getImmagine() {
		return immagine;
	}

	public static JLabel getInfo() {
		return info;
	}

	public static JLabel getQuantitalabel() {
		return quantitaLabel;
	}

	public static JSpinner getQuantitaspinner() {
		return quantitaSpinner;
	}

	public static JButton getAggiungibutton() {
		return aggiungiButton;
	}

	public static JPanel getSudpnl() {
		return sudPnl;
	}

	public static int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}