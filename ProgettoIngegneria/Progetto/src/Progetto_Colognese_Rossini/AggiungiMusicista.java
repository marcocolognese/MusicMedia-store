package Progetto_Colognese_Rossini;

import java.awt.*;
import javax.swing.*;

public class AggiungiMusicista extends JFrame {
	private static final String titolo = "Aggiungi Musicista";
	private static final JPanel ovest = new JPanel(new GridLayout(4, 1));
	private static final JPanel est = new JPanel(new GridLayout(4, 1));
	private static final JLabel nomeLabel = new JLabel(" Nome d'arte*: ");
	private static final JTextField nomeText = new JTextField(20);
	private static final JLabel genereLabel = new JLabel(" Genere principale*: ");
	private static final JTextField genereText = new JTextField(20);
	private static final JLabel annoLabel = new JLabel(" Anno di nascita: ");
	private static final JTextField annoText = new JTextField(20);
	private static final JLabel strumentiLabel = new JLabel(" Strumenti suonati*: ");
	private static final JTextField strumentiText = new JTextField(20);
	private static final JPanel sud = new JPanel();
	private static final JButton aggiungiButton = new JButton("Aggiungi Musicista");

	private static AggiungiMusicistaListener aggiungiMusicistaListener;

	public AggiungiMusicista(HomePage homePage) {
		super(titolo);
		aggiungiMusicistaListener = new AggiungiMusicistaListener(this, homePage);

		ovest.add(nomeLabel);
		est.add(nomeText);
		ovest.add(genereLabel);
		est.add(genereText);
		ovest.add(annoLabel);
		est.add(annoText);
		ovest.add(strumentiLabel);
		est.add(strumentiText);
		sud.add(aggiungiButton);

		aggiungiButton.addActionListener(aggiungiMusicistaListener);
		aggiungiButton.setActionCommand(aggiungiMusicistaListener.AGGIUNGI);

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

	public static JLabel getNomelabel() {
		return nomeLabel;
	}

	public static JTextField getNometext() {
		return nomeText;
	}

	public static JLabel getGenerelabel() {
		return genereLabel;
	}

	public static JTextField getGeneretext() {
		return genereText;
	}

	public static JLabel getAnnolabel() {
		return annoLabel;
	}

	public static JTextField getAnnotext() {
		return annoText;
	}

	public static JLabel getStrumentilabel() {
		return strumentiLabel;
	}

	public static JTextField getStrumentitext() {
		return strumentiText;
	}

	public static JPanel getSud() {
		return sud;
	}

	public static JButton getAggiungibutton() {
		return aggiungiButton;
	}

	public static AggiungiMusicistaListener getAggiungiMusicistaListener() {
		return aggiungiMusicistaListener;
	}
}