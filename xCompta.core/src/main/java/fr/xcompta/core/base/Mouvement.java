package fr.xcompta.core.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mouvement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private float montant;
	private int idLettrage = 0;
	@ManyToOne
	private Ecriture ecriture;
	@ManyToOne
	private Compte compte = null;

	public Mouvement() {
		// Constructeur vide pour JPA/Hibernate
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	public void setIdLettrage(int idLettrage) {
		this.idLettrage = idLettrage;
	}

	public int getIdLettrage() {
		return idLettrage;
	}

	public void setEcriture(Ecriture ecriture) {
		this.ecriture = ecriture;
	}

	public Ecriture getEcriture() {
		return ecriture;
	}

	public void setCompte(Compte compte) {
		this.compte = compte;
	}

	public Compte getCompte() {
		return compte;
	}

	public Mouvement getCopy() {

		// Todo remplacer par clone( ou faire un wraper de clone
		// et Ecriture ? qui set l'ecriture parent
		Mouvement mouvement = new Mouvement();

		mouvement.setId(0);
		mouvement.setCompte(compte);
		mouvement.setMontant(montant);

		return mouvement;
	}

	public boolean estAZero() {
		return (montant == 0);
	}

	public boolean estDebiteur() {
		return montant > 0;
	}

	public boolean estCrediteur() {
		return montant < 0;
	}
	
 	@Override
	public String toString() {
		return compte + "-" + montant + "-" + idLettrage;
	}
}
//ajouter annotations hibernate
//bidirectionnelle avec Ecriture
