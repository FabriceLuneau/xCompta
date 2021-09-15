package fr.xcompta.core.base;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compte implements Serializable	 {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String numero;
	private String libelle;
	private boolean utilisable = true;
//	@ManyToOne
//	private Classe classe;
	
	
	public Compte() {
		//Constructeur vide pour hibernate
	}
	
	public Compte(String numero, String libelle) {
		setNumero(numero);
		setLibelle(libelle);
		setUtilisable(true);
	}
	
	public Compte(String numero, String libelle, boolean utilisable) {
		this.numero = numero;
		setNumero(numero);
		setLibelle(libelle);
		setUtilisable(utilisable);
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isUtilisable() {
		return utilisable;
	}

	public void setUtilisable(boolean utilisable) {
		this.utilisable = utilisable;
	}

	@Override
	public String toString() {
		return numero + "-" + libelle;
	}

	public short getClasse() {
		return Short.parseShort(numero.substring(0, 1));
	}
	
//	public void setClasse(Classe classe) {
//		this.classe = classe;
//	}

	public static String trimNumero(String numero) {
		if(numero.isEmpty()) {
		return numero;
		}
				
		//Supprime les zéros en début de chaînes
			while(numero.charAt(0) =='0') {
				numero = numero.substring(1) ;
	}
			
			//Supprime les zéros en fain de chaînes
			while((numero.charAt(numero.length()-1)) == '0')
				numero = numero.substring(0, (numero.length()-1));
		
			while((numero.charAt(0)) == '0')
				numero = numero.substring(1, (numero.length()));
			return numero;
		}
	
	public String getNumeroTrimed() {
		return trimNumero(numero);
	}

	public static String formatNumero(String numero, int nbDecimal) {
		numero = numero.trim();
		numero = numero.toUpperCase();
	
		if(numero.matches("401[A-Za-z]+"))
			return numero;
	
		if(numero.matches("411[A-Za-z]+"))
			return numero;
	
		if(numero.matches("404[A-Za-z]+"))
			return numero;
	
		if(numero.matches("[1-9]\\d{1," + (nbDecimal-1) +"}")) {
			if(numero.length() < nbDecimal ) {
				int j = nbDecimal-numero.length(); 
					for(int i=0;i<j;i++)	//Trouver une meilleur solution
						numero = numero.concat("0");
			} else
				numero = numero.substring(0, nbDecimal);
			return numero;
		}
		return null;
	}
	
	public String numeroFormated(int nbDecimal) {
		return formatNumero(numero, nbDecimal);
	}
}
