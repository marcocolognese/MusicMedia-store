package Progetto_Colognese_Rossini;

import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Accedi extends JFrame {
	private static final String titolo = "Accedi";
	private static final JPanel ovest = new JPanel(new GridLayout(2, 1));
	private static final JPanel est = new JPanel(new GridLayout(2, 1));
	private static final JLabel nomeUtenteLabel = new JLabel(" Nome utente: ");
	private static final JTextField nomeUtenteText = new JTextField(17);
	private static final JLabel passwordLabel = new JLabel(" Password: ");
	private static final JPasswordField passwordText = new JPasswordField(17);
	private static final JPanel sud = new JPanel();
	private static final JButton accediButton = new JButton("Accedi");
	private AccediListener accediListener;

	public Accedi(HomePage homePage) {
		super(titolo);
		accediListener = new AccediListener(this, homePage);
		ovest.add(nomeUtenteLabel);
		est.add(nomeUtenteText);
		ovest.add(passwordLabel);
		est.add(passwordText);
		sud.add(accediButton);

		// Container Pricipale
		Container frmContentPane = this.getContentPane();
		frmContentPane.add(ovest, BorderLayout.WEST);
		frmContentPane.add(est, BorderLayout.EAST);
		frmContentPane.add(sud, BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);

		accediButton.addActionListener(accediListener);
		accediButton.setActionCommand(accediListener.ACCEDI);
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

	public static JPanel getSud() {
		return sud;
	}

	public static JButton getAccedibutton() {
		return accediButton;
	}

}