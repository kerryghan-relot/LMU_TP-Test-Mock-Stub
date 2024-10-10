package com.soprasteria.gestion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprasteria.gestion.control.AnneeException;
import com.soprasteria.paiement.RouteurPaiement;
import com.soprasteria.panier.model.Panier;

@ExtendWith(MockitoExtension.class)
public class CommandeAvecInjectMocksTest {

	// TODO : Créer un mock par annotation pour Client, Panier,
	// IPaiement et AnneeController.

	// TODO : Injecter les mocks créés dans commande avec une annotation
	private Commande commande;


	@Test
	public void testValiderPaiement() {
		// TODO : configurer le mock de RouteurPaiement afin que le test passe
		// l'assertion

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// vérification système testé
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void testValiderPaiementAnneeInvalide() {
		// TODO : configurer les mocks afin que le test passe l'assertion, l'exeption doit être levée par AnneeController

		// execution
		Exception e = assertThrows(AnneeException.class, () -> commande.validerPaiement("4444555551666666", "01/2017", "345"));;
		assertEquals("Annee incorrecte : exception du bouchon", e.getMessage());
	}

}
