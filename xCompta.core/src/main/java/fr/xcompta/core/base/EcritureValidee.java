package fr.xcompta.core.base;

import javax.persistence.Entity;

/**
 * @author Fabrice LUNEAU
 * 
 *         Modelise une ecriture validee Une ecriture validee ne peut pas etre
 *         supprimee Pour l'annuler il faut la contrepasser
 **/
@Entity
public class EcritureValidee extends Ecriture {
	private static final long serialVersionUID = 1L;
	
	protected int numeroEnregistrement;

	/**
	 * Constructeur vide pour JPA/Hibernate
	 */
	public EcritureValidee() {
		//Constructeur vide pour JPA/Hibernate
	}

	public int getNumeroEnregistrement() {
		return numeroEnregistrement;
	}

	public void setNumeroEnregistrement(int numeroEnregistrement) {
		if (numeroEnregistrement <= 0) {
			throw new IllegalArgumentException(
					getClass() + " : Le numero d'enregistrement ne peut pas etre negatif ou a zero");
		}

		this.numeroEnregistrement = numeroEnregistrement;
	}
}
