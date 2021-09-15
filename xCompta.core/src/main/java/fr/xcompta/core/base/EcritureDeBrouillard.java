package fr.xcompta.core.base;

import javax.persistence.Entity;

/**
 * @author Fabrice LUNEAU
 * 
 *         La classe écriture de brouillard dérive de la classe Ecriture
 * 
 *         Une écriture de brouillard peut être supprimer et modifier Elle peut
 *         être validée, elle est alors transformé en écriture validée, on
 *         conserve son numéro de préenregistrement, pour pouvoir s'y référer,
 *         en plus de son numéro d'enregistrement
 * 
 */
@Entity
public class EcritureDeBrouillard extends Ecriture {
	private static final long serialVersionUID = 1L;

	public EcritureDeBrouillard() {
		// Constructeur vide pour JPA/Hibernate
	}
	
	

}
