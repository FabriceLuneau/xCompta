package fr.xcompta.core.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compte implements Serializable	 {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String numero = null;
	private String libelle = "";
	private boolean utilisable = true;
//	private boolean lettrable = false;
	
	
	public Compte() {
		//Constructeur vide pour hibernate
	}
	
	public Compte(String numero, String libelle) {
		setNumero(numero);
		setLibelle(libelle);
		setUtilisable(true);
	}
	
	public Compte(final String numero , final String libelle, boolean utilisable) {
		this.numero = numero;
		setNumero(numero);
		setLibelle(libelle);
		setUtilisable(utilisable);
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(final String numero) {
		this.numero = trimNumero(numero);
	}

	public boolean isUtilisable() {
		return utilisable;
	}

	public void setUtilisable(final boolean utilisable) {
		this.utilisable = utilisable;
	}

	@Override
	public String toString() {
		return numero + "-" + libelle;
	}

	public short getNumeroClasse() {
		return Short.parseShort(numero.substring(0, 1));
	}
	
	public static String trimNumero(String numeroToTrim) {
		if(numeroToTrim.isEmpty()) {
		return numeroToTrim;
		}
				
		//Supprime les zéros en début de chaînes
			while(numeroToTrim.charAt(0) =='0') {
				numeroToTrim = numeroToTrim.substring(1) ;
	}
			
			//Supprime les zéros en fain de chaînes
			while((numeroToTrim.charAt(numeroToTrim.length()-1)) == '0')
				numeroToTrim = numeroToTrim.substring(0, (numeroToTrim.length()-1));
		
			while((numeroToTrim.charAt(0)) == '0')
				numeroToTrim = numeroToTrim.substring(1, (numeroToTrim.length()));
			return numeroToTrim;
		}

	public static String formatNumero(String numeroToFormat, int nbDecimal) {
		numeroToFormat = numeroToFormat.trim();
		numeroToFormat = numeroToFormat.toUpperCase();
	
		if(numeroToFormat.matches("401[A-Za-z]+"))
			return numeroToFormat;
	
		if(numeroToFormat.matches("411[A-Za-z]+"))
			return numeroToFormat;
	
		if(numeroToFormat.matches("404[A-Za-z]+"))
			return numeroToFormat;
	
		if(numeroToFormat.matches("[1-9]\\d{1," + (nbDecimal-1) +"}")) {
			if(numeroToFormat.length() < nbDecimal ) {
				int j = nbDecimal-numeroToFormat.length(); 
					for(int i=0;i<j;i++)	//Trouver une meilleur solution
						numeroToFormat = numeroToFormat.concat("0");
			} else
				numeroToFormat = numeroToFormat.substring(0, nbDecimal);
			return numeroToFormat;
		}
		return null;
	}
	
	public String getNumeroFormated(int nbDecimal) {
		return formatNumero(numero, nbDecimal);
	}
}
