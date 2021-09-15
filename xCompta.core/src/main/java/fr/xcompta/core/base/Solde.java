package fr.xcompta.core.base;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Fabrice LUNEAU
 * 
 *         Un solde regroupe et totalise les mouvement d'un compte pour une
 *         journee n'y a qun solde pour un compte et pour une journee Si la
 *         *         journeen'a pas ete mouvmente i n'y a pas de solde
 * 
 *         Il recapitule pour la journee-le total des debits -le total des*         credits -Le solde
 * 
 *         Et le cumul avec le solde anterieur
 * 
 *         ne concernent donc que des �citures validees
 * 
 *         Revise : e le 29/04/2018
 */
@Entity
public class Solde implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

//	@EmbeddedId
	@Basic
	private LocalDate date;
//	@EmbeddedId
	@ManyToOne
	private Compte compte;
	private float debit;
	private float credit;
	private float solde;
	private float cumuleDebit;
	private float cumuleCredit;
	private float soldeCumule;

	/**
	 * Constructeur pour JPA/Hibernate
	 */
	public Solde() {}

	public Solde(LocalDate date, Compte compte, float debit, float credit, float solde, float cumuleDebit,
			float cumuleCredit, float soldeCumule) {
		setDate(date);
		setCompte(compte);
		setDebit(debit);
		setCredit(credit);
		setSolde(solde);
		setCumuleDebit(cumuleDebit);
		setCumuleCredit(cumuleCredit);
		setSoldeCumule(soldeCumule);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId() {
	this.id = id;	
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		if (date == null) {
			throw new NullPointerException(getClass() + " : 'objet DATE en argument est NULL");
		}

		this.date = date;
	}

	public Compte getCompte() {
		return compte;
	}

	public void setCompte(Compte compte) {
		if (compte == null) {
			throw new NullPointerException(getClass() + " :L'objet COMPTE en argument est NULL");
		}

		this.compte = compte;
	}

	public float getDebit() {
		return debit;
	}

	public void setDebit(float debit) {
		controleMontant(debit);

		this.debit = debit;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		controleMontant(credit);

		this.credit = credit;
	}

	public float getSolde() {
		return solde;
	}

	public void setSolde(float solde) {
		controleMontant(solde);

		this.solde = solde;
	}

	public float getCumuleDebit() {
		return cumuleDebit;
	}

	public void setCumuleDebit(float cumuleDebit) {
		controleMontant(cumuleDebit);

		this.cumuleDebit = cumuleDebit;
	}

	public float getCumuleCredit() {
		return cumuleCredit;
	}

	public void setCumuleCredit(float cumuleCredit) {
		controleMontant(cumuleCredit);

		this.cumuleCredit = cumuleCredit;
	}

	public float getSoldeCumule() {
		return soldeCumule;
	}

	public void setSoldeCumule(float cumuleSolde) {
		controleMontant(cumuleSolde);

		this.soldeCumule = cumuleSolde;
	}

	private void controleMontant(float montant) {
		if (montant < 0) {
			throw new IllegalArgumentException(getClass() + " : Le montant dot �tre sup�rieur � 0");
		}

	}

	@Override
	public String toString() {
		return date + " " + compte + " " + solde;
	}
	}

// Un seul solde par compte et par journee
//Todo  Controler vis a vis de la date de cloture, pas de solde apres la datede cloture
	//Todo supprimer solde et solde cumules c'est une donnee calculee
