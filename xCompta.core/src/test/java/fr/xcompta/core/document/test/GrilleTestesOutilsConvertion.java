package fr.xcompta.core.document.test;

import org.junit.Test;

import fr.xcompta.core.etat.Grille;
import junit.framework.Assert;

public class GrilleTestesOutilsConvertion {
	@Test
	public void TesteFonctionValeurLettre() {
		int valeur;
		
		//Minuscules
		valeur = Grille.valeurDuChar('e'); 
		Assert.assertEquals(1, valeur);
		valeur = Grille.valeurDuChar('z');
		Assert.assertEquals(26, valeur);
		
		//Majuscules
		valeur = Grille.valeurDuChar('A');
		Assert.assertEquals(1, valeur);
		
		valeur = Grille.valeurDuChar('Z');
		Assert.assertEquals(26, valeur);
		
		
	}
	
	@Test	
	public void testFonctionvaleurChar() {
		char caractere = ' ';
		
		Grille.charPour(1);
		Assert.assertEquals('a', caractere);
		
		Grille.charPour(26);
		Assert.assertEquals('z', caractere);
	}
	
	@Test
	public void testeChaineVersNombre() {
		int numero = 0;

		numero = Grille.chaineVersNombre("a");
		Assert.assertEquals(1, numero);

		numero = Grille.chaineVersNombre("z");
		Assert.assertEquals(26, numero);
		
		numero = Grille.chaineVersNombre("aa");
		Assert.assertEquals(76, numero);
		
		numero = Grille.chaineVersNombre("ba");
		Assert.assertEquals(31, numero);
	}
	/*

	public void testDeuxLettre() {
		int numero = 0;

		numero = Grille.stringToNumber("aa");
		Assert.assertEquals(26, numero);

		numero = Grille.stringToNumber("zb");
		Assert.assertEquals(25 *26 + 2, numero);

		numero = Grille.stringToNumber("zz");
		Assert.assertEquals(25 * 26 + 25, numero);
	}

	@Test
	public void testTroisLettre() {
		int numero;

		numero = Grille.stringToNumber("bbc");
		//Assert.assertEquals(2*26*26 + 2*26 + 3, numero);

	}
	*/

	@Test
	public void TestValeurs() {
		for(int i=0;i<1000;i++) {
			String chaine = Grille.nombreVersChaine(i);
			int nombre = Grille.chaineVersNombre(chaine);   
					
			System.out.println(i + " => " + chaine + " - " + nombre);
			//Assert.assertEquals(i, nombre);
		}
	}

}