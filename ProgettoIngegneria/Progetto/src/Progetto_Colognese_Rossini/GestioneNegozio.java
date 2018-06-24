package Progetto_Colognese_Rossini;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class GestioneNegozio extends JFrame {
	private static final String titolo = "Gestione Negozio";
	private static final int larghezza = 1100, altezza = 700;

	// Pannello Nord
	private static final JLabel titoloLabel = new JLabel("Gestione Negozio");
	private static final JPanel nordPnl = new JPanel();
	// Pannello Centrale
	private static final JPanel centroPnl = new JPanel(new BorderLayout());
	private static JPanel sinistra = new JPanel(
			new GridLayout(HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo(), 1));
	private static JPanel centro = new JPanel(
			new GridLayout(HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo(), 1));
	private static JPanel destra = new JPanel(
			new GridLayout(HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo(), 1));
	private static JPanel[] destra1 = new JPanel[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	private static JLabel[] immagine = new JLabel[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	private static int[] idProdotto = new int[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	private static JLabel[] info = new JLabel[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	private static JLabel[] quantitaLabel = new JLabel[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	private static JSpinner[] quantita = new JSpinner[HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo()];
	// Pannello Sud
	private static final JButton aggiungiMusicistaButton = new JButton("Aggiungi musicista");
	private static final JButton aggiungiProdottoButton = new JButton("Aggiungi prodotto");
	private static final JButton confermaButton = new JButton("Conferma modifiche");
	private static final JPanel sudPnl = new JPanel();

	private static GestioneNegozioListener gestioneNegozioListener;

	public GestioneNegozio(HomePage homePage) throws SQLException {
		super(titolo);
		this.setVisible(true);
		gestioneNegozioListener = new GestioneNegozioListener(this, homePage);

		// Pannello Nord
		titoloLabel.setFont(new Font("Serif", Font.BOLD, 45));
		nordPnl.add(titoloLabel);
		nordPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nordPnl.setBackground(Color.lightGray);
		// Pannello Centrale
		Disk disco = new Disk();
		ResultSet rs = HomePage.getPersonaleAutorizzato().getCatalogo();
		for (int i = 0; i < HomePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); i++) {
			rs.next();
			idProdotto[i] = rs.getInt(1);
			destra1[i] = new JPanel(new GridBagLayout());
			immagine[i] = new JLabel();
			immagine[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(6)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			info[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: " + rs.getString(4)
					+ " <BR>&nbsp;PREZZO: " + rs.getBigDecimal(3) + "€</HTML>");
			quantitaLabel[i] = new JLabel("Quantità disponibile: ");
			quantita[i] = new JSpinner(new SpinnerNumberModel(disco.getScorte(rs.getInt(1)), 0, 99999, 1));
			sinistra.add(immagine[i], BorderLayout.WEST);
			centro.add(info[i], BorderLayout.CENTER);
			destra1[i].add(quantitaLabel[i]);
			destra1[i].add(quantita[i]);
			destra.add(destra1[i], BorderLayout.EAST);
		}
		centroPnl.add(sinistra, BorderLayout.WEST);
		centroPnl.add(centro, BorderLayout.CENTER);
		centroPnl.add(destra, BorderLayout.EAST);

		// Pannello Sud
		sudPnl.add(aggiungiMusicistaButton);
		sudPnl.add(aggiungiProdottoButton);
		sudPnl.add(confermaButton);
		sudPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		aggiungiMusicistaButton.addActionListener(gestioneNegozioListener);
		aggiungiMusicistaButton.setActionCommand(gestioneNegozioListener.MUSICISTA);
		aggiungiProdottoButton.addActionListener(gestioneNegozioListener);
		aggiungiProdottoButton.setActionCommand(gestioneNegozioListener.PRODOTTO);
		confermaButton.addActionListener(gestioneNegozioListener);
		confermaButton.setActionCommand(gestioneNegozioListener.CONFERMA);

		// Scroll Bar
		JScrollPane scrollPane = new JScrollPane(centroPnl);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

		// Container Pricipale
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(nordPnl, BorderLayout.NORTH);
		frmContentPane.add(scrollPane, BorderLayout.CENTER);
		frmContentPane.add(sudPnl, BorderLayout.SOUTH);

		// Impostazioni di visualizzazione
		this.pack();
		this.setSize(larghezza, altezza);
		this.setLocationRelativeTo(null);
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

	public static JPanel getSinistra() {
		return sinistra;
	}

	public static JPanel getCentro() {
		return centro;
	}

	public static JPanel getDestra() {
		return destra;
	}

	public static JPanel[] getDestra1() {
		return destra1;
	}

	public static JLabel[] getImmagine() {
		return immagine;
	}

	public static int[] getIdprodotto() {
		return idProdotto;
	}

	public static JLabel[] getInfo() {
		return info;
	}

	public static JLabel[] getQuantitalabel() {
		return quantitaLabel;
	}

	public static JSpinner[] getQuantita() {
		return quantita;
	}

	public static JButton getAggiungimusicistabutton() {
		return aggiungiMusicistaButton;
	}

	public static JButton getAggiungiprodottobutton() {
		return aggiungiProdottoButton;
	}

	public static JButton getConfermabutton() {
		return confermaButton;
	}

	public static JPanel getSudpnl() {
		return sudPnl;
	}

	public static GestioneNegozioListener getGestioneNegozioListener() {
		return gestioneNegozioListener;
	}

	public static void setSinistra(JPanel sinistra) {
		GestioneNegozio.sinistra = sinistra;
	}

	public static void setCentro(JPanel centro) {
		GestioneNegozio.centro = centro;
	}

	public static void setDestra(JPanel destra) {
		GestioneNegozio.destra = destra;
	}

	public static void setDestra1(JPanel[] destra1) {
		GestioneNegozio.destra1 = destra1;
	}

	public static void setImmagine(JLabel[] immagine) {
		GestioneNegozio.immagine = immagine;
	}

	public static void setIdProdotto(int[] idProdotto) {
		GestioneNegozio.idProdotto = idProdotto;
	}

	public static void setInfo(JLabel[] info) {
		GestioneNegozio.info = info;
	}

	public static void setQuantitaLabel(JLabel[] quantitaLabel) {
		GestioneNegozio.quantitaLabel = quantitaLabel;
	}

	public static void setQuantita(JSpinner[] quantita) {
		GestioneNegozio.quantita = quantita;
	}

	public static void setGestioneNegozioListener(GestioneNegozioListener gestioneNegozioListener) {
		GestioneNegozio.gestioneNegozioListener = gestioneNegozioListener;
	}
}