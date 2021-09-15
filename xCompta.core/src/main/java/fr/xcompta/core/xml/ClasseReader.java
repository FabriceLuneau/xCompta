package fr.xcompta.core.xml;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;

import fr.xcompta.core.base.Classe;

public class ClasseReader {
	public static void main(final String argv[]) {

	try(XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("datas/ClasseDeComptes.xml")))) {
//	decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("datas/classes.xml")));
	 
//	ArrayList<Classe> classes = (Classe) decoder.readObject();
		
		Classe classe;
		ArrayList<Classe> classes;
		
classes = (ArrayList<Classe>) decoder.readObject();
//		Object objet = (Classe) decoder.readObject();
//		System.out.println(objet);
		
//	 System.out.println(classes);
	 } catch (final Exception e) {
	 e.printStackTrace();
	 } finally {
	 }
}
}
