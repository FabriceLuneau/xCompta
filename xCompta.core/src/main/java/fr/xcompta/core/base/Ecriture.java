package fr.xcompta.core.base;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Fabrice Luneau
 * 
 *         Classe mere abstraite de - EcritureDeBrouillard -EcritureValide
 * 
 *         Implemente les * comportements communs aux deux classes filles
 *
 *         - Le numero dede pre-enregistrement doit etre recopie dans l'ecriture
 *         validee au moment de la validation - les numeros des ecritures
 *         validees doivent se suivre pour garantir qu'il n'y a pas eu de
 *         suppression et donc de manipulations le numero d'enregistrement est
 *         renseigne la validation toutes ecritures de brouillard doit etre
 *         validee ou supprimee a terme - Les ecritures de brouillard qui ne
 *         sont pas validees sont supprimees
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Ecriture implements Serializable, Iterable<Mouvement> {
	protected static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected int id = 0;
	protected int numeroPreEnregistrement = 0;
	protected String libelle = "";
	@Basic
	protected LocalDate date = LocalDate.now();
	@ManyToOne
	protected Journal journal = null;
	@OneToMany(mappedBy = "ecriture", cascade = CascadeType.ALL)
	protected List<Mouvement> mouvements = new ArrayList<Mouvement>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException(getClass() + " : l'Id ne peut pas etre negatif ou a zero");
		}

		this.id = id;
	}

	public int getNumeroPreEnregistrement() {
		return numeroPreEnregistrement;
	}

	public void setNumeroPreEnregistrement(int numeroPreEnregistrement) {
		if (numeroPreEnregistrement <= 0) {
			throw new IllegalArgumentException(
					getClass() + " : Le numero de preenregistrement ne peut pas etre n�gatif ou a zero");
		}

		this.numeroPreEnregistrement = numeroPreEnregistrement;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		if (libelle == null) {
			throw new NullPointerException(getClass() + " : L'objet String pour le libelle est NULL");
		}

		libelle = libelle.trim();

		if (libelle.isEmpty()) {
			throw new IllegalArgumentException(getClass() + " :L'objet String pour le libelle est vide ");
		}

		this.libelle = libelle;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		if (date == null) {
			throw new NullPointerException(getClass() + " : L'objet Date en argument est null");
		}

		this.date = date;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		if (journal == null) {
			throw new NullPointerException(getClass() + " : L'objet Journal est NULL");
		}

		this.journal = journal;
	}

	public List<Mouvement> getMouvements() {
		return mouvements;
	}

	public void setMouvements(List<Mouvement> mouvements) {
		if (mouvements == null) {
			throw new NullPointerException(getClass() + " : 'objet List est NULL");
		}

		this.mouvements = mouvements;
	}

	/**
	 * @param mouvement
	 * 
	 *                  Wrapper d'ajouterMouvement, pour ajouter un mouvement au
	 *                  debut a l'index 0
	 */
	public void ajouterMouvementAuDebut(Mouvement mouvement) {
		ajouterMouvementA(0, mouvement);
	}

	/**
	 * @param mouvement
	 * 
	 *                  Ajoute le Moubement a la fin
	 */
	public void ajouterMouvementALaFin(Mouvement mouvement) {
		if (this.mouvements == null)
			this.mouvements = new ArrayList<>();

		mouvement.setEcriture(this);
		this.mouvements.add(mouvement);
	}

	/**
	 * @param index
	 * @param mouvement
	 *
	 *                  Ajoute le mouvement a l'index
	 */
	public void ajouterMouvementA(int index, Mouvement mouvement) {
		if (this.mouvements == null) {
			this.mouvements = new ArrayList<>();
		}

		mouvement.setEcriture(this);
		this.mouvements.add(index, mouvement);
	}

	/**
	 * @param index
	 * 
	 *              Supprime le Mouvement a l'indexe
	 */
	public void supprimerMouvement(int index) {
		if (this.mouvements == null) {
			throw new NullPointerException(
					getClass() + "Ecriture : Suppression impossible la liste des mouvements est NULL");
		}

		if (index > (size() - 1)) {
			throw new ArrayIndexOutOfBoundsException(getClass() + " : l'index demande est superieur au maximum");
		}

		if (mouvements.get(index) == null) {
			throw new NullPointerException(getClass() + " : le mouvemenent demande est null");
		}

		mouvements.remove(index);
	}

	/**
	 * @return
	 * 
	 *         Retourne la taille de l'ecriture, soit le nombre de mouvements pour
	 *         la collection mouvements
	 */
	public int size() {
		return mouvements.size();
	}

	/**
	 * @return
	 * 
	 *         Calcule le total des debit pour l'ecriture
	 */
	public float getTotalDebit() {
		if (mouvements == null || mouvements.isEmpty()) {
			return 0;
		}

		float totalDebit = 0;

		for (Mouvement mouvement : mouvements) {
			if (mouvement.getMontant() > 0) {
				totalDebit += mouvement.getMontant();
			}
		}

		return totalDebit;
	}

	/**
	 * @return
	 * 
	 *         Calcule le total des credits pour l'ecriture
	 */
	public float getTotalCredit() {
		if (mouvements == null || mouvements.isEmpty()) {
			return 0;
		}

		float totalCredit = 0;

		for (Mouvement mouvement : mouvements) {
			if (mouvement.getMontant() < 0) {
				totalCredit += mouvement.getMontant();
			}
		}

		return totalCredit;
	}

	/**
	 * @return
	 * 
	 *         Retourne vrai si le total des debit et credit sont egaux, sinon faux
	 */
	public boolean estEquilibree() {
		return (getSolde() == 0);
	}

	/**
	 * @return
	 * 
	 *         Retourne une copie inversee de l'ecriture en argument
	 */
	public EcritureDeBrouillard contrepasser() {
		EcritureDeBrouillard ecritureDestination = new EcritureDeBrouillard();

		ecritureDestination.date = LocalDate.now();

		ecritureDestination.setJournal(this.journal);
		ecritureDestination.setLibelle(this.libelle);

		for (Mouvement mouvementSource : mouvements) {
			Mouvement mouvementDestination = mouvementSource.getCopy();

			mouvementDestination.setId(0);
			mouvementDestination.setEcriture(ecritureDestination);
			mouvementDestination.setMontant(mouvementDestination.getMontant() * -1);

			ecritureDestination.ajouterMouvementALaFin(mouvementDestination);
		}

		return ecritureDestination;
	}

	/**
	 * @return
	 * 
	 *         Retourne le solde de l'ecriture
	 */
	public float getSolde() {
		if (mouvements == null || mouvements.isEmpty()) {
			return 0;
		}

		float solde = 0;

		for (Mouvement mouvement : mouvements) {
			solde += mouvement.getMontant();
		}

		return solde;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 * 
	 * Retourne un Iterator pour parcourrir les mouvements de l'ecriture.
	 */
	@Override
	public Iterator<Mouvement> iterator() {
		return mouvements.iterator();
	}

	@Override
	public String toString() {
		StringBuilder ecriture = new StringBuilder();

		ecriture.append("-" + journal + "\n");

		for (int i = 0; i < mouvements.size(); i++)
			ecriture.append((mouvements.get(i)).toString() + "\n");

		return ecriture.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((journal == null) ? 0 : journal.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((mouvements == null) ? 0 : mouvements.hashCode());
		result = prime * result + numeroPreEnregistrement;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ecriture other = (Ecriture) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (journal == null) {
			if (other.journal != null)
				return false;
		} else if (!journal.equals(other.journal))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (mouvements == null) {
			if (other.mouvements != null)
				return false;
		} else if (!mouvements.equals(other.mouvements))
			return false;
		if (numeroPreEnregistrement != other.numeroPreEnregistrement)
			return false;
		return true;
	}
}
//todo les methodes getTotalCredit getTotalDebit et getSolde sont quasi identique il faudrait les fusioner en un code