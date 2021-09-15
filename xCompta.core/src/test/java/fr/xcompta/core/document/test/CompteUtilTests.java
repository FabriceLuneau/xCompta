package fr.xcompta.core.document.test;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.xcompta.core.base.Compte;
import junit.framework.Assert;

public class CompteUtilTests {

	@Test
	public void TestTrimDroite() {
	String numero = "607000";
	
	numero = Compte.trimNumero(numero);
	
	Assert.assertEquals(numero, "607");
	}

	@Test
	public void TestTrimGauche() {
	String numero = "00607";
	
	numero = Compte.trimNumero(numero);
	
	Assert.assertEquals(numero, "607");
	}

	@Test
	public void TestTrimRienAEnlever() {
	String numero = "607";
	
	numero = Compte.trimNumero(numero);

	Assert.assertEquals(numero, "607");
	}

	@Test
	public void TestTrimGaucheEtDroite() {
	String numero = "00607000";
	
	numero = Compte.trimNumero(numero);
	
	Assert.assertEquals(numero, "607");
	}
	
	@Test
	public void testFormatNumero() {
		String numero = "607";
		
		numero = Compte.formatNumero(numero, 6);
		
		Assert.assertEquals(numero,  "607000");;
	}
}
