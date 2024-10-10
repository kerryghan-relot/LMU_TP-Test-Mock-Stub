package com.soprasteria.gestion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.AdditionalMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.soprasteria.paiement.IPaiement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprasteria.paiement.exception.PaiementException;
import com.soprasteria.panier.model.Article;
import com.soprasteria.panier.model.Panier;
import com.soprasteria.panier.model.exceptions.MontantTropEleveException;
import com.soprasteria.panier.model.exceptions.QuantiteArticleTropGrandeException;
import com.soprasteria.panier.model.exceptions.TropDeReferencesException;

import java.awt.image.ImageProducer;

@ExtendWith(MockitoExtension.class)
public class CommandeAvecMockTest {

	private static Client client;
	private static Panier pan;
	private static Article art1;
	@Mock
	private IPaiement routeurPaiementAnnotation;
	@Mock
	private IPaiement routeurPaiementAnnotationArgumentMatchers;

	@BeforeAll
	public static void init()
			throws TropDeReferencesException, QuantiteArticleTropGrandeException, MontantTropEleveException {
		// Panier
		pan = new Panier();
		art1 = new Article(100.00, "REF001", "LIBELLE01", 9.99);
		pan.ajouterArticle(art1, 5);
		client = new Client();
	}

	@Test
	public void testValiderPaiementMockInstance() {
		// TODO : instancier un mock de IPaiement et le configurer
		// afin que le test passe l'assertion
		IPaiement routeurPaiement = Mockito.mock(IPaiement.class);
		when(routeurPaiement.transaction("ENSIM_COMMERCE",
										"4444555551666666",
										"01",
										"2017",
										"345",
										5*(100+9.99))).thenReturn(true);
		// J'ai laissé les valeurs en dur puisque c'est sur celles-ci que l'on va faire nos tests.
		// J'aurais aussi pu utiliser `eq(<maChaineDeCaracteres>)`, mais on va déjà utiliser les
		// ArgumentMatchers plus loin dans le TP.

		// preparation
		Commande commande = new Commande(client, pan, routeurPaiement);

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// verification mock
		// TODO : vérifier l'invocation de la méthode transaction sur le mock
		// avec les paramètres pan=4444555551666666, moisExpiration=01,
		// anneeExpiration=2017, cvv2=345 et le montant attendu selon le panier
		// passé à la commande
		verify(routeurPaiement).transaction("ENSIM_COMMERCE", "4444555551666666", "01", "2017", "345", 5*(100+9.99));

		// vérification système testé
		assertThat(resultat).isTrue();
	}

	@Test
	public void testValiderPaiementMockAnnotation() {
		// TODO : configurer le mock par annotation (à définir) de IPaiement
		// afin que le test passe l'assertion
		// Le bouchon est déclarer plus haut
		when(routeurPaiementAnnotation.transaction("ENSIM_COMMERCE",
													"4444555551666666",
													"01",
													"2017",
													"345",
													5*(100+9.99))).thenReturn(true);

		// preparation
		Commande commande = new Commande(client, pan, routeurPaiementAnnotation);

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// verification mock
		// TODO : vérifier l'invocation de la méthode transaction sur le mock
		// avec les paramètres pan=4444555551666666, moisExpiration=01,
		// anneeExpiration=2017, cvv2=345 et le montant attendu selon le panier
		// passé à la commande
		verify(routeurPaiementAnnotation).transaction("ENSIM_COMMERCE", "4444555551666666", "01", "2017", "345", 5*(100+9.99));

		// vérification système testé
		assertThat(resultat).isTrue();
	}

	@Test
	public void testValiderPaiementMockException() {
		// TODO : configurer le mock par annotation (à définir) de IPaiement
		// afin que le test passe l'assertion quel que soit les paramètres
		// d'appel de la dépendance
		when(routeurPaiementAnnotationArgumentMatchers.transaction(anyString(),
												anyString(),
												anyString(),
												anyString(),
												anyString(),
												eq(5*(100+9.99), 0.001))).thenThrow(PaiementException.class);

		// preparation
		Commande commande = new Commande(client, pan, routeurPaiementAnnotationArgumentMatchers);

		// verification
		assertThrows(PaiementException.class, () -> {
			// execution
			commande.validerPaiement("4444555551666666", "01/2017", "345");
		});
		verify(routeurPaiementAnnotationArgumentMatchers).transaction("ENSIM_COMMERCE", "4444555551666666", "01", "2017", "345", 5*(100+9.99));


	}

}
