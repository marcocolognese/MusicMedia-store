package Progetto_Colognese_Rossini;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Formatter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CarrelloPage extends JFrame {
	private static final String titolo = "Carrello";
	private static final int larghezza = 1100, altezza = 700;

	// Pannello Nord
	private static final JLabel titoloLabel = new JLabel("Carrello");
	private static final JPanel nordPnl = new JPanel();
	// Pannello Centrale
	private static final JPanel centroPnl = new JPanel(new BorderLayout());
	private static JPanel sinistra = new JPanel(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
	private static JPanel centro = new JPanel(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
	private static JPanel destra = new JPanel(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));

	private static int[] idProdotto = new int[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JPanel[] destra1 = new JPanel[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JLabel[] immagine = new JLabel[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JLabel[] info = new JLabel[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JButton[] dettagli = new JButton[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JLabel[] quantitaLabel = new JLabel[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JSpinner[] quantita = new JSpinner[HomePage.getCatalogo().getTotProdottiCatalogo()];
	private static JButton[] modifica = new JButton[HomePage.getCatalogo().getTotProdottiCatalogo()];

	// Pannello Sud
	private static final JButton svuotaButton = new JButton("Svuota carrello");
	private static final JButton confermaButton = new JButton("Conferma ordine");
	private BigDecimal prezzoTot = new BigDecimal(0);
	private static JLabel totaleLabel = new JLabel("");
	private static final JPanel sudPnl = new JPanel();
	int i;

	private static CarrelloPageListener carrelloPageListener;

	public CarrelloPage(HomePage homePage) throws SQLException {
		super(titolo);
		this.setVisible(true);
		carrelloPageListener = new CarrelloPageListener(this, homePage);

		// Pannello Nord
		titoloLabel.setFont(new Font("Serif", Font.BOLD, 45));
		nordPnl.add(titoloLabel);
		nordPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nordPnl.setBackground(Color.lightGray);
		// Pannello Centrale
		ResultSet rs = HomePage.getCarrello().getProdotti();
		for (i = 0; i < HomePage.getCarrello().getTotProdottiCarrello(); i++) {
			rs.next();
			idProdotto[i] = rs.getInt(1);
			quantitaLabel[i] = new JLabel("Quantità: ");
			quantita[i] = new JSpinner(new SpinnerNumberModel(rs.getInt(6), 0, 99999, 1));
			modifica[i] = new JButton("Modifica quantità");
			destra1[i] = new JPanel(new GridBagLayout());
			immagine[i] = new JLabel();
			immagine[i].setIcon(new ImageIcon(
					new ImageIcon(rs.getString(5)).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH)));
			BigDecimal prezzo = rs.getBigDecimal(3);
			prezzo.multiply(new BigDecimal(rs.getInt(6)));
			prezzoTot = prezzo.add(prezzoTot);
			if (HomePage.getCatalogo().getCatalogoScontato(HomePage.getCliente().getNomeUtente()))
				prezzo = prezzo.multiply(new BigDecimal(0.8));
			info[i] = new JLabel("<HTML>&nbsp;TITOLO: " + rs.getString(2) + "<BR>&nbsp;ARTISTA: " + rs.getString(4)
					+ " <BR>&nbsp;PREZZO: " + prezzo.setScale(2, BigDecimal.ROUND_UP) + "€</HTML>");
			;
			dettagli[i] = new JButton("Dettagli del prodotto");
			sinistra.add(immagine[i], BorderLayout.WEST);
			centro.add(info[i], BorderLayout.CENTER);
			destra1[i].add(quantitaLabel[i]);
			destra1[i].add(quantita[i]);
			destra1[i].add(modifica[i]);
			destra1[i].add(dettagli[i]);
			destra.add(destra1[i], BorderLayout.EAST);

			modifica[i].addActionListener(carrelloPageListener);
			modifica[i].setActionCommand(carrelloPageListener.MODIFICA);
			dettagli[i].addActionListener(carrelloPageListener);
			dettagli[i].setActionCommand(carrelloPageListener.DETTAGLI);
		}
		centroPnl.add(sinistra, BorderLayout.WEST);
		centroPnl.add(centro, BorderLayout.CENTER);
		centroPnl.add(destra, BorderLayout.EAST);
		// Pannello Sud
		totaleLabel = new JLabel("  Totale: " + prezzoTot.setScale(2, BigDecimal.ROUND_UP)
				+ "€                                                                                                                                                                     ");
		totaleLabel.setFont(new Font("Serif", Font.BOLD, 17));
		sudPnl.add(totaleLabel);
		sudPnl.add(svuotaButton);
		sudPnl.add(confermaButton);
		sudPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		svuotaButton.addActionListener(carrelloPageListener);
		svuotaButton.setActionCommand(carrelloPageListener.SVUOTA);
		confermaButton.addActionListener(carrelloPageListener);
		confermaButton.setActionCommand(carrelloPageListener.CONFERMA);

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

	public static void setSinistra(JPanel sinistra) {
		CarrelloPage.sinistra = sinistra;
	}

	public static JPanel getCentro() {
		return centro;
	}

	public static JPanel getDestra() {
		return destra;
	}

	public static int[] getIdprodotto() {
		return idProdotto;
	}

	public static JPanel[] getDestra1() {
		return destra1;
	}

	public static JLabel[] getImmagine() {
		return immagine;
	}

	public static JLabel[] getInfo() {
		return info;
	}

	public static JButton[] getDettagli() {
		return dettagli;
	}

	public static JLabel[] getQuantitalabel() {
		return quantitaLabel;
	}

	public static JSpinner[] getQuantita() {
		return quantita;
	}

	public static JButton getSvuotabutton() {
		return svuotaButton;
	}

	public static JButton getConfermabutton() {
		return confermaButton;
	}

	public BigDecimal getPrezzoTot() {
		return prezzoTot;
	}

	public static JLabel getTotaleLabel() {
		return totaleLabel;
	}

	public static JPanel getSudpnl() {
		return sudPnl;
	}

	public int getI() {
		return i;
	}

	public static CarrelloPageListener getCarrelloPageListener() {
		return carrelloPageListener;
	}

	public static JButton[] getModifica() {
		return modifica;
	}

	public static void setCentro(JPanel centro) {
		CarrelloPage.centro = centro;
	}

	public static void setDestra(JPanel destra) {
		CarrelloPage.destra = destra;
	}

	public static void setIdProdotto(int[] idProdotto) {
		CarrelloPage.idProdotto = idProdotto;
	}

	public static void setDestra1(JPanel[] destra1) {
		CarrelloPage.destra1 = destra1;
	}

	public static void setImmagine(JLabel[] immagine) {
		CarrelloPage.immagine = immagine;
	}

	public static void setInfo(JLabel[] info) {
		CarrelloPage.info = info;
	}

	public static void setDettagli(JButton[] dettagli) {
		CarrelloPage.dettagli = dettagli;
	}

	public static void setQuantitaLabel(JLabel[] quantitaLabel) {
		CarrelloPage.quantitaLabel = quantitaLabel;
	}

	public static void setQuantita(JSpinner[] quantita) {
		CarrelloPage.quantita = quantita;
	}

	public static void setModifica(JButton[] modifica) {
		CarrelloPage.modifica = modifica;
	}

	public void setPrezzoTot(BigDecimal prezzoTot) {
		this.prezzoTot = prezzoTot;
	}

	public static void setTotaleLabel(JLabel totaleLabel) {
		CarrelloPage.totaleLabel = totaleLabel;
	}

	public void setI(int i) {
		this.i = i;
	}

	public void setCarrelloPageListener(CarrelloPageListener carrelloPageListener) {
		this.carrelloPageListener = carrelloPageListener;
	}
}