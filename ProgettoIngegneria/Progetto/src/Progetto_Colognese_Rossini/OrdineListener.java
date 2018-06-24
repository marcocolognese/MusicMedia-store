package Progetto_Colognese_Rossini;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.*;

public class OrdineListener implements ActionListener {

	private static Ordine ordine;
	private static HomePage homePage;
	public final String CONFERMA = "Conferma";

	OrdineListener(Ordine ordine, HomePage homePage) {
		this.ordine = ordine;
		this.homePage = homePage;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case CONFERMA:
			try {
				conferma();
			} catch (SQLException e1) {
			} catch (UnknownHostException e1) {
			}
			break;
		default:
			break;
		}
	}

	private void conferma() throws SQLException, UnknownHostException {
		if (Ordine.getConsegnacombobox().getSelectedItem().equals("--- Seleziona ---")) {
			JOptionPane.showMessageDialog(null, "Selezionare il tipo di consegna.", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
			return;
		}
		if (Ordine.getPagamentocombobox().getSelectedItem().equals("--- Seleziona ---"))
			JOptionPane.showMessageDialog(null, "Selezionare il tipo di pagamento.", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
		else if (Ordine.getPagamentocombobox().getSelectedItem().equals("PayPal")) {
			if (Ordine.getEmailpptext().getText().trim().equals("")
					|| Ordine.getPasswordpptext().getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Completare tutti i campi.", "Attenzione!",
						JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
				return;
			}
		} else if (Ordine.getPagamentocombobox().getSelectedItem().equals("Carta di credito")) {
			if (Ordine.getTipocccombobox().getSelectedItem().equals("--- Seleziona ---")
					|| Ordine.getNumerocctext().getText().trim().length() < 13
					|| !Ordine.getCvvcctext().getText().trim().matches("[0-9]{3}")) {
				JOptionPane.showMessageDialog(null, "Completare tutti i campi in modo corretto.", "Attenzione!",
						JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
				return;
			}
		}
		String prodotti = "";
		BigDecimal prezzoTot = new BigDecimal(0);
		ResultSet rs = HomePage.getCarrello().getProdotti();
		while (rs.next()) {
			// prodotti
			if (prodotti.equals(""))
				prodotti = String.valueOf(rs.getInt(1));
			else
				prodotti = prodotti + ", " + rs.getInt(1);
			// prezzo
			BigDecimal prezzo = rs.getBigDecimal(3);
			for (int j = 0; j < rs.getInt(6) - 1; j++)
				prezzo = prezzo.add(rs.getBigDecimal(3));
			prezzoTot = prezzo.add(prezzoTot);
			if (HomePage.getCatalogo().getCatalogoScontato(HomePage.getCliente().getNomeUtente()))
				prezzo = prezzo.multiply(new BigDecimal(0.8));
		}
		if (Ordine.getPagamentocombobox().getSelectedItem().equals("Bonifico"))
			JOptionPane.showMessageDialog(null, "Bonifico ricevuto.", "Ricevuta bonifico",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(""));
		HomePage.getVendita().aggiungiVendita(HomePage.getCliente().getNomeUtente(), prodotti,
				InetAddress.getLocalHost().getHostAddress().toString(), prezzoTot,
				Ordine.getPagamentocombobox().getSelectedItem().toString(),
				Ordine.getConsegnacombobox().getSelectedItem().toString());

		HomePage.getCarrello().svuota();
		CarrelloPage.getConfermabutton().setEnabled(false);
		CarrelloPage.getSinistra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getCentro().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getDestra().setLayout(new GridLayout(HomePage.getCarrello().getTotProdottiCarrello(), 1));
		CarrelloPage.getSinistra().removeAll();
		CarrelloPage.getCentro().removeAll();
		CarrelloPage.getDestra().removeAll();

		if (HomePage.getCatalogo().getCatalogoScontato(HomePage.getCliente().getNomeUtente()))
			CarrelloPage.getTotaleLabel().setText(
					"  Totale: 0.00€   Spedizione: 0.00€                                                                                                                          ");
		else
			CarrelloPage.getTotaleLabel().setText(
					"  Totale: 0.00€   Spedizione: 5.00€                                                                                                                          ");

		homePage.getOrdine().setVisible(false);
		ordine.dispose();

	}
}