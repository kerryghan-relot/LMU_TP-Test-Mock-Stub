package com.soprasteria.gestion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprasteria.paiement.exception.PaiementException;
import com.soprasteria.panier.model.Article;
import com.soprasteria.panier.model.Panier;
import com.soprasteria.panier.model.exceptions.MontantTropEleveException;
import com.soprasteria.panier.model.exceptions.QuantiteArticleTropGrandeException;
import com.soprasteria.panier.model.exceptions.TropDeReferencesException;

@ExtendWith(MockitoExtension.class)
public class CommandeAvecMockTest {

	private static Client client;
	private static Panier pan;
	private static Article art1;

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

		// preparation
		Commande commande = new Commande(/* TODO : params à renseigner */null, null, null);

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// verification mock
		// TODO : vérifier l'invocation de la méthode transaction sur le mock
		// avec les paramètres pan=4444555551666666, moisExpiration=01,
		// anneeExpiration=2017, cvv2=345 et le montant attendu selon le panier
		// passé à la commande

		// vérification système testé
		assertThat(resultat).isTrue();
	}

	@Test
	public void testValiderPaiementMockAnnotation() {
		// TODO : configurer le mock par annotation (à définir) de IPaiement
		// afin que le test passe l'assertion

		// preparation
		Commande commande = new Commande(/* params à renseigner */null, null, null);

		// execution
		boolean resultat = commande.validerPaiement("4444555551666666", "01/2017", "345");

		// verification mock
		// TODO : vérifier l'invocation de la méthode transaction sur le mock
		// avec les paramètres pan=4444555551666666, moisExpiration=01,
		// anneeExpiration=2017, cvv2=345 et le montant attendu selon le panier
		// passé à la commande

		// vérification système testé
		assertThat(resultat).isTrue();
	}

	@Test
	public void testValiderPaiementMockException() {
		// TODO : configurer le mock par annotation (à définir) de IPaiement
		// afin que le test passe l'assertion quel que soit les paramètres
		// d'appel de la dépendance

		// preparation
		Commande commande = new Commande(/* params à renseigner */null, null, null);
		
		// verification
		assertThrows(PaiementException.class, () -> {			
			// execution
			commande.validerPaiement("4444555551666666", "01/2017", "345");
		});

	}

}
