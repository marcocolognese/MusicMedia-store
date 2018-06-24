package Progetto_Colognese_Rossini;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ordine extends JFrame {
	private static final String titolo = "Conferma ordine";

	private static final JPanel ovest = new JPanel(new GridLayout(2, 1));
	private static final JPanel est = new JPanel(new GridLayout(2, 1));
	private static final JLabel pagamentoLabel = new JLabel(" Modalità di pagamento: ");
	private static final JComboBox<String> pagamentoComboBox = new JComboBox<String>(
			new String[] { "--- Seleziona ---", "Bonifico", "Carta di credito", "PayPal" });
	private static final JLabel consegnaLabel = new JLabel(" Modalità di consegna: ");
	private static final JComboBox<String> consegnaComboBox = new JComboBox<String>(
			new String[] { "--- Seleziona ---", "Corriere", "Posta" });
	// PayPal
	private static final JLabel emailPPLabel = new JLabel(" Email: ");
	private static final JTextField emailPPText = new JTextField(20);
	private static final JLabel passwordPPLabel = new JLabel(" Password PayPal: ");
	private static final JPasswordField passwordPPText = new JPasswordField(20);
	// Carta di credito
	private static final JLabel tipoCCLabel = new JLabel(" Tipo: ");
	private static final JComboBox<String> tipoCCComboBox = new JComboBox<String>(
			new String[] { "--- Seleziona ---", "MasterCard", "Visa", "Postepay" });
	private static final JLabel numeroCCLabel = new JLabel(" Numero di carta: ");
	private static final JTextField numeroCCText = new JTextField(16);
	private static final JLabel cvvCCLabel = new JLabel(" CVV: ");
	private static final JTextField cvvCCText = new JTextField(3);
	private static final JLabel scadenzaCCLabel = new JLabel(" Data di scadenza: ");
	private static final JComboBox<String> scadenzaMeseCCComboBox = new JComboBox<String>(
			new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" });
	private static final JComboBox<String> scadenzaAnnoCCComboBox = new JComboBox<String>(
			new String[] { "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027" });
	private static final JPanel dataScadenza = new JPanel(new GridLayout(1, 2));

	private static final JPanel sud = new JPanel();
	private static final JButton confermaButton = new JButton("Conferma");

	private static OrdineListener ordineListener;

	public Ordine(HomePage homePage) {
		super(titolo);

		ordineListener = new OrdineListener(this, homePage);

		ovest.add(pagamentoLabel);
		est.add(pagamentoComboBox);
		ovest.add(consegnaLabel);
		est.add(consegnaComboBox);
		sud.add(confermaButton);
		pagamentoComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				switch (e.getItem().toString()) {
				case "--- Seleziona ---":
				case "Bonifico":
					ovest.remove(emailPPLabel);
					est.remove(emailPPText);
					ovest.remove(passwordPPLabel);
					est.remove(passwordPPText);
					ovest.remove(tipoCCLabel);
					est.remove(tipoCCComboBox);
					ovest.remove(numeroCCLabel);
					est.remove(numeroCCText);
					ovest.remove(cvvCCLabel);
					est.remove(cvvCCText);
					ovest.remove(scadenzaCCLabel);
					est.remove(dataScadenza);
					est.setLayout(new GridLayout(2, 1));
					ovest.setLayout(new GridLayout(2, 1));
					pack();
					break;
				case "PayPal":
					ovest.add(emailPPLabel);
					est.add(emailPPText);
					ovest.add(passwordPPLabel);
					est.add(passwordPPText);
					ovest.remove(tipoCCLabel);
					est.remove(tipoCCComboBox);
					ovest.remove(numeroCCLabel);
					est.remove(numeroCCText);
					ovest.remove(cvvCCLabel);
					est.remove(cvvCCText);
					ovest.remove(scadenzaCCLabel);
					est.remove(dataScadenza);
					est.setLayout(new GridLayout(4, 1));
					ovest.setLayout(new GridLayout(4, 1));
					pack();
					break;
				case "Carta di credito":
					ovest.remove(emailPPLabel);
					est.remove(emailPPText);
					ovest.remove(passwordPPLabel);
					est.remove(passwordPPText);
					ovest.add(tipoCCLabel);
					est.add(tipoCCComboBox);
					ovest.add(numeroCCLabel);
					est.add(numeroCCText);
					ovest.add(cvvCCLabel);
					est.add(cvvCCText);
					ovest.add(scadenzaCCLabel);
					dataScadenza.add(scadenzaMeseCCComboBox);
					dataScadenza.add(scadenzaAnnoCCComboBox);
					est.add(dataScadenza);
					ovest.setLayout(new GridLayout(6, 1));
					est.setLayout(new GridLayout(6, 1));
					pack();
					break;
				}
			}
		});

		confermaButton.addActionListener(ordineListener);
		confermaButton.setActionCommand(ordineListener.CONFERMA);

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

	public static JLabel getPagamentolabel() {
		return pagamentoLabel;
	}

	public static JComboBox<String> getPagamentocombobox() {
		return pagamentoComboBox;
	}

	public static JLabel getConsegnalabel() {
		return consegnaLabel;
	}

	public static JComboBox<String> getConsegnacombobox() {
		return consegnaComboBox;
	}

	public static JLabel getEmailpplabel() {
		return emailPPLabel;
	}

	public static JTextField getEmailpptext() {
		return emailPPText;
	}

	public static JLabel getPasswordpplabel() {
		return passwordPPLabel;
	}

	public static JPasswordField getPasswordpptext() {
		return passwordPPText;
	}

	public static JLabel getTipocclabel() {
		return tipoCCLabel;
	}

	public static JComboBox<String> getTipocccombobox() {
		return tipoCCComboBox;
	}

	public static JLabel getNumerocclabel() {
		return numeroCCLabel;
	}

	public static JTextField getNumerocctext() {
		return numeroCCText;
	}

	public static JLabel getCvvcclabel() {
		return cvvCCLabel;
	}

	public static JTextField getCvvcctext() {
		return cvvCCText;
	}

	public static JLabel getScadenzacclabel() {
		return scadenzaCCLabel;
	}

	public static JComboBox<String> getScadenzamesecccombobox() {
		return scadenzaMeseCCComboBox;
	}

	public static JComboBox<String> getScadenzaannocccombobox() {
		return scadenzaAnnoCCComboBox;
	}

	public static JPanel getDatascadenza() {
		return dataScadenza;
	}

	public static JPanel getSud() {
		return sud;
	}

	public static JButton getConfermabutton() {
		return confermaButton;
	}

	public static OrdineListener getOrdineListener() {
		return ordineListener;
	}
}
