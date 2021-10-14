package fr.xcompta.core.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author Fabrice Luneau
 * 
 *         - une classe permet de regrouper tous les comptes, qui déebutes pa
 *         le même numero, suivant la nomenclature du plan comptable général
 *         
 *         Il se peut que cette Class soit remplacer par des constantes, car les valeurs sont universelles
 *         
 *         une classe est caractérisée par : -un seul chiffre de 1 a 9, qui est
 *         le même que le premier chiffre des comptes qu'elle regroupe, il n'y a
 *         pas d'opérations mathematiques a faire sur ces numeros - un libellé
 *         pour l'identifier -un compte appartient a une et une seule classe
 */
@Entity
public class Classe implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
		@Range(min = 1, max = 9)
	private Short numero = 0;
	@	NotNull
	@NotBlank
	@Length
	(min = 5, max = 50)
	private String libelle = null;

	
	public Classe() {
		// Constructeur vide pour JPA/Hibernate
	}

	/**
	 * @param numero
	 * @param libelle
	 * 
	 *                Constructeur d'une Classe avec toutes les propriétées
	 */
	public Classe(final short numero, final String libelle) {
		setNumero(numero);
		setLibelle(libelle);
	}

	public short getNumero() {
		return numero;
	}

	public void setNumero(final short numero) {
		this.numero = numero;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return numero + " " + libelle;
	}
}
//Todo
//Utiliser Hibernate Validator pour verifier les arguments passer aux Seters