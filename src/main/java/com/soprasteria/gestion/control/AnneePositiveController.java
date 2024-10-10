package com.soprasteria.gestion.control;

/**
 * Contrôleur vérifiant qu'une année est positive.
 */
public class AnneePositiveController implements AnneeController {

	@Override
	public void controler(String annee) throws AnneeException {
		try {
			if(annee == null || Integer.parseInt(annee) < 0)
				throw new AnneeException(annee);
		} catch(NumberFormatException e) {
			throw new AnneeException(annee, e);
		}
	}

}
