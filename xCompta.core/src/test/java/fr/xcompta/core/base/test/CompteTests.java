package fr.xcompta.core.base.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import fr.xcompta.core.base.Compte;

class CompteTests {
	@Test
	public void constructeurVide() {
		//teste que le constructeur retourne un objet
		Compte compte = new Compte();
	assertNotNull(compte);	
	}
	
	@Test
	public void valeurParDefautNumero() {
		Compte compte = new Compte();
			assertNull(compte.getNumero());
		}
	
	@Test
	public void valeurParDefautLibelle() {
		Compte compte = new Compte();
assertEquals("", compte.getLibelle());		
	}
			
			
	@Test
	public void valeurParDefautUtilisable() {
		Compte compte = new Compte();
		assertEquals(true, compte);
	}
	
	@Test
	public void getter2() {
		Compte compte = new Compte("0601", "test");
		assertEquals("601", compte.getNumero());
	}
	
	@Test
	public void getter3() {
		Compte compte = new Compte("6010", "test");
		assertEquals("601", compte.getNumero());
	}
	
	@Test
	public void getter4() {
Compte 		compte = new Compte("06010", "test");
		assertEquals("601", compte.getNumero());
	}
}
