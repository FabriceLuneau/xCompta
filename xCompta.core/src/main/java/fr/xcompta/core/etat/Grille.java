package fr.xcompta.core.etat;

import java.util.HashMap;
import java.util.Set;

public class Grille {
	private HashMap<String, Cellule> grille;
	private Set<Colonne> colonnes;
	private Set<Ligne> ligne;

	private int nombreDeColonnes;
	private int nombreDeLignes;

	public static String nombreVersChaine(int number) {
			int modulo;
			int  result = number;
			String value = "";
			
			while(number > 26)  {
			result = number  /26;
			modulo =  number % 26;
			
				value = charPour(modulo) + value;
				
			}
			
			value = charPour(number) + value;
			
		return value;
		}
		
		public static int chaineVersNombre(String chaine) {
	int value = 0;
	
	
	if(chaine.length() == 1) {
		value  = valeurDuChar(chaine.charAt(0));
	}
	
	if(chaine.length() >1) {
		chaine = Grille.inverserChaine(chaine);
				
		for(int i=0;i<chaine.length();i++) {
		value += valeurDuChar(chaine.charAt(i)) *Math.pow(26, i);
		//System.out.println("Char " + chaine.charAt(i) + 
				//"  rang " + charValue(chaine.charAt(i)) + 
				//"multipli " + Math.pow(26, i));
	}
	}
	
			return value;
		}
			
		public static int valeurDuChar(char d) {
			int i = 0;
			
			for (char c = 'a'; c <= 'z'; c++) {
				if(c == d){
					return i+1;
				}
				i++;
			}
		return 99;
			}
			
		public static char charPour(int number) {   
			int i=0;
			
			for (char c = 'a'; c <= 'z'; c++) {
				if(i == number) {
					return c;
				}
				i++;
			}
			return ' ';
		}
			
			private static String inverserChaine(String chaine) {
				String chaineInversee = "";
				
				for(int i = chaine.length() - 1;i >= 0; i-- ) {
					chaineInversee = chaineInversee + chaine.charAt(i);
				}
				
				return chaineInversee;
}

public static void main(String [] args) {
	int numero= 0;
	
	numero = valeurDuChar('A');
	System.out.println(numero);
}
}
