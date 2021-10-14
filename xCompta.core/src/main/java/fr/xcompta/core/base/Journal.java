package fr.xcompta.core.base;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
	 * @author Fabrice Luneau
 * 
 *         Les journaux comptable, permettent de classer les ecritures par theme
 *
 *         Ils sont caraccerises par : - un code pour faciliter la saisie -un
 *         libell -ils nepeuvent pas etre supprimes une fois utilises
 * 
 *         un journal regroupe les ecritures, mais il n'ya pas besoin
 *         d'association de journal vers ecriture
 */
@Entity
public class Journal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank
	@Length
	(min = 2, max = 5)
	protected String code = "";
//	@NotNull
//	@NotBlank
	@Length(min = 5, max = 50)
	private String libelle = "";

	
	/**
	 * Constructeur vide pour Hibernate
	 */
	public Journal() {
		//Constructeur vide pour JPA/Hibernate
	}

	/**
	 * @param code
	 * @param libelle
	 * 
	 *                Constructeur de Journal avec un Code et un libell�
	 */
	public Journal(String code, String libelle) {
		setCode(code);
		setLibelle(libelle);
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}
	
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
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
		Journal other = (Journal) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return code + " " + libelle;
	}
}
