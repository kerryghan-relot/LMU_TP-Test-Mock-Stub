package com.soprasteria.gestion.control;

public class AnneeException extends RuntimeException {
	public AnneeException(String annee) {
		this(annee, null);
	}

	public AnneeException(String annee, Throwable e) {
		super("Annee incorrecte : " + annee, e);
	}
}
