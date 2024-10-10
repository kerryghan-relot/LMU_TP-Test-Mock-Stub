package com.soprasteria.gestion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.soprasteria.gestion.control.AnneeController;
import com.soprasteria.paiement.IPaiement;
import com.soprasteria.paiement.exception.PaiementException;
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
	@Mock
	private Client client;
	@Mock
	private Panier panier;
	@Mock
	private IPaiement routeurPaiement;
	@Mock
	private AnneeController anneeController;

	// TODO : Injecter les mocks créés dans commande avec une annotation
	@InjectMocks
	private Commande commande;


	@Test
	public void testValiderPaiement() {
		// TODO : configurer le mock de RouteurPaiement afin que le test passe
		// l'assertion
		when(routeurPaiement.transaction(anyString(),
										anyString(),
										anyString(),
										anyString(),
										anyString(),
										anyDouble())).thenReturn(true);

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// vérification système testé
		assertThat(resultat).isTrue();
	}
	
	@Test
	public void testValiderPaiementAnneeInvalide() {
		// TODO : configurer les mocks afin que le test passe l'assertion, l'exeption doit être levée par AnneeController
		doThrow(new AnneeException("exception du bouchon")).when(anneeController).controler(anyString());
		// Par défaut mockito ne peut pas bouchonner des méthodes void car
		// il part du principe qu'une fonction void ne fait rien.
		// Pour ce faire, on doit lui dire explicitement de renvoyer une erreur (qu'on définit nous même).

		// execution
		Exception e = assertThrows(AnneeException.class, () -> commande.validerPaiement("4444555551666666", "01/2017", "345"));;
		assertEquals("Annee incorrecte : exception du bouchon", e.getMessage());
	}

}
