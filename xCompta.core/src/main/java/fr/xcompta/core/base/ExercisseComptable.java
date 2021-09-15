package fr.xcompta.core.base;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Fabrice LUNEAU
 * 
 *         Un exercisse comptable est une p�riode de r�f�rence d'un an, dans
 *         laquelle on enregistre des op�rations comptables Il peut �tre ouvert
 *         ou ferm�, cl�tur�. une fois ferm� il ne peut plus �tre modifi�, et on
 *         ne peut pas ajouter ou supprimer d'op�rations Il a une date de d�but
 *         et de fin La date de fin est �gale � la date de d�but plus 1 an moins
 *         un jour
 *
 *         Il peut y avoir deux exercisses ouverts au maximum en m�me temps pour
 *         une soci�t� Il est continu et fait un an Il ne correspond pas
 *         obligatoirement � l'ann�e civile Les exercisses ne peuvent pas se
 *         chevaucher A terme ils doivent �tre cl�tur�s
 *
 *         R�vis� le 04/04/2019
 */
@Entity
public class ExercisseComptable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic
	protected LocalDate debut;
	protected boolean ouvert = true;

	/**
	 * Constructeur vide pour JPA/Hibernate
	 */
	public ExercisseComptable() {
	}

	public ExercisseComptable(LocalDate debut) {
		setDebut(debut);
	}

	public ExercisseComptable(LocalDate debut, boolean ouvert) {
		setDebut(debut);
		setOuvert(ouvert);
	}

	public LocalDate getDebut() {
		return debut;
	}

	public void setDebut(LocalDate debut) {
		if (debut == null) {
			throw new NullPointerException(getClass() + " : la date de d�but en argument est NULL");
		}

		this.debut = debut;
	}

	public LocalDate getFin() {
		return debut.plusYears(1).minusDays(1);
	}

	public boolean estOuvert() {
		return ouvert;
	}

	public void setOuvert(boolean ouvert) {
		this.ouvert = ouvert;
	}

}
