package com.soprasteria.gestion.control;

public interface AnneeController {
	/**
	 * Contrôle une année sous forme de chaine de caractères
	 * @param annee chaine de caractères représentant une année
	 * @throws AnneeException si la chaine de caractère ne représente pas une année valide
	 */
	void controler(String annee) throws AnneeException;
}
