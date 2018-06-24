package Progetto_Colognese_Rossini;

import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import javax.swing.*;

public class ProdottoPageListener implements ActionListener {

	private static ProdottoPage prodottoPage;
	private static HomePage homePage;
	public final String AGGIUNGI = "Aggiungi";

	ProdottoPageListener(ProdottoPage prodottoPage, HomePage homePage) {
		this.prodottoPage = prodottoPage;
		this.homePage = homePage;
	}

	public void actionPerformed(ActionEvent e) {
		String com = e.getActionCommand();
		switch (com) {
		case AGGIUNGI:
			aggiungi();
			break;
		default:
			break;
		}
	}

	private void aggiungi() {
		Disk disk = new Disk();
		if ((int) ProdottoPage.getQuantitaspinner().getValue() > 0) {
			if (disk.getScorte(ProdottoPage.getId()) >= (int) ProdottoPage.getQuantitaspinner().getValue()) {
				HomePage.getCarrello().aggiungiProdotto(ProdottoPage.getId(),
						(int) ProdottoPage.getQuantitaspinner().getValue());
				prodottoPage.dispose();
			} else
				JOptionPane.showMessageDialog(null,
						"Sono disponibili solamente " + disk.getScorte(ProdottoPage.getId())
								+ " dischi per questo prodotto.",
						"Attenzione!", JOptionPane.ERROR_MESSAGE, new ImageIcon(""));
		} else
			JOptionPane.showMessageDialog(null,
					"La quantità scelta è zero.\nNessun prodotto è stato aggiunto al carrello.", "Attenzione!",
					JOptionPane.ERROR_MESSAGE, new ImageIcon(""));

		for (int a = 0; a < homePage.getPersonaleAutorizzato().getTotProdottiCatalogo(); a++) {
			Disk disco1 = new Disk();
			homePage.getGestioneNegozio().getQuantita()[a]
					.setValue(disco1.getScorte(homePage.getGestioneNegozio().getIdprodotto()[a]));
		}

		homePage.getGestioneNegozio().pack();
		homePage.getGestioneNegozio().setSize(homePage.getGestioneNegozio().getLarghezza(),
				homePage.getGestioneNegozio().getAltezza());
		homePage.getGestioneNegozio().setLocationRelativeTo(null);
	}
}
