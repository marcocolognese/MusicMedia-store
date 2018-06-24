package Progetto_Colognese_Rossini;

import java.awt.*;
import javax.swing.*;

public class Registrati extends JFrame {
	private static final String titolo = "Registrati";
	private static final JPanel ovest = new JPanel(new GridLayout(8, 1));
	private static final JPanel est = new JPanel(new GridLayout(8, 1));
	private static final JLabel nomeUtenteLabel = new JLabel(" Nome utente*: ");
	private static final JTextField nomeUtenteText = new JTextField(17);
	private static final JLabel passwordLabel = new JLabel(" Password*: ");
	private static final JPasswordField passwordText = new JPasswordField(17);
	private static final JLabel nomeLabel = new JLabel(" Nome*: ");
	private static final JTextField nomeText = new JTextField(17);
	private static final JLabel cognomeLabel = new JLabel(" Cognome*: ");
	private static final JTextField cognomeText = new JTextField(17);
	private static final JLabel codFisLabel = new JLabel(" Codice fiscale*: ");
	private static final JTextField codFisText = new JTextField(17);
	private static final JLabel cittaLabel = new JLabel(" Città di residenza*: ");
	private static final JTextField cittaText = new JTextField(17);
	private static final JLabel telefonoLabel = new JLabel(" Numero di telefono*: ");
	private static final JTextField telefonoText = new JTextField(17);
	private static final JLabel cellulareLabel = new JLabel(" Numero di cellulare: ");
	private static final JTextField cellulareText = new JTextField(17);
	private static final JPanel sud = new JPanel();
	private static final JButton registratiButton = new JButton("Registrati");
	private RegistratiListener registratiListener;

	public Registrati(HomePage homePage) {
		super(titolo);
		ovest.add(nomeUtenteLabel);
		est.add(nomeUtenteText);
		ovest.add(passwordLabel);
		est.add(passwordText);
		ovest.add(nomeLabel);
		est.add(nomeText);
		ovest.add(cognomeLabel);
		est.add(cognomeText);
		ovest.add(codFisLabel);
		est.add(codFisText);
		ovest.add(cittaLabel);
		est.add(cittaText);
		ovest.add(telefonoLabel);
		est.add(telefonoText);
		ovest.add(cellulareLabel);
		est.add(cellulareText);
		sud.add(registratiButton);

		// Container Pricipale
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(ovest, BorderLayout.WEST);
		frmContentPane.add(est, BorderLayout.EAST);
		frmContentPane.add(sud, BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

		registratiListener = new RegistratiListener(this, homePage);
		registratiButton.addActionListener(registratiListener);
		registratiButton.setActionCommand(registratiListener.REGISTRATI);
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

	public static JLabel getNomeutentelabel() {
		return nomeUtenteLabel;
	}

	public static JTextField getNomeutentetext() {
		return nomeUtenteText;
	}

	public static JLabel getPasswordlabel() {
		return passwordLabel;
	}

	public static JPasswordField getPasswordtext() {
		return passwordText;
	}

	public static JLabel getNomelabel() {
		return nomeLabel;
	}

	public static JTextField getNometext() {
		return nomeText;
	}

	public static JLabel getCognomelabel() {
		return cognomeLabel;
	}

	public static JTextField getCognometext() {
		return cognomeText;
	}

	public static JLabel getCodfislabel() {
		return codFisLabel;
	}

	public static JTextField getCodfistext() {
		return codFisText;
	}

	public static JLabel getCittalabel() {
		return cittaLabel;
	}

	public static JTextField getCittatext() {
		return cittaText;
	}

	public static JLabel getTelefonolabel() {
		return telefonoLabel;
	}

	public static JTextField getTelefonotext() {
		return telefonoText;
	}

	public static JLabel getCellularelabel() {
		return cellulareLabel;
	}

	public static JTextField getCellularetext() {
		return cellulareText;
	}

	public static JPanel getSud() {
		return sud;
	}

	public static JButton getAccedibutton() {
		return registratiButton;
	}
}