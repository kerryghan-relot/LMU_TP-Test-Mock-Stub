package com.soprasteria.gestion;

import com.soprasteria.gestion.control.AnneeController;
import com.soprasteria.paiement.IPaiement;
import com.soprasteria.paiement.RouteurPaiement;
import com.soprasteria.panier.model.Panier;

public class Commande {
	private static final String REF_COMMERCANT = "ENSIM_COMMERCE";
	private static final char EXPIRATION_SEPARATOR = '/';

	private Client client;
	private Panier panier;
	private IPaiement routeurPaiement;
	private boolean estControleAnnee;
	private final AnneeController controleurAnnee;

	public Commande(Client client, Panier panier, IPaiement routeurPaiement) {
		this(client, panier, routeurPaiement, null);
	}
	
	public Commande(Client client, Panier panier, IPaiement routeurPaiement, AnneeController anneeController) {
		this.client = client;
		this.panier = panier;
		this.routeurPaiement = routeurPaiement;
		this.estControleAnnee = anneeController != null;
		this.controleurAnnee = anneeController;
	}

	public boolean validerPaiement(String pan, String dateExpiration, String cvv2) {
		int separator = dateExpiration.indexOf(EXPIRATION_SEPARATOR);

		// Hmm, buggy :)
		String moisExpiration = dateExpiration.substring(0, separator);
		String anneeExpiration = dateExpiration.substring(separator + 1);
		
		if(estControleAnnee && controleurAnnee != null) {
			controleurAnnee.controler(anneeExpiration);
		}

		return routeurPaiement.transaction(REF_COMMERCANT, pan, moisExpiration, anneeExpiration, cvv2,
				panier.prixTTCPanier());
	}

	public void setClient(Client client) {
		this.client = client;
		System.out.println("client");
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public void setRouteurPaiement(RouteurPaiement routeurPaiement) {
		this.routeurPaiement = routeurPaiement;
	}

	public void activerControleAnnee(boolean estControleRefCommercant) {
		this.estControleAnnee = estControleRefCommercant;
	}
	
	public boolean estControleAnnee() {
		return estControleAnnee;
	}
}
