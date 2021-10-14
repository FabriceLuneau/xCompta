package fr.xcompta.core.etat;

public class Colonne {
	private Integer index;
	private String alias = null;
	private float largeur;
	
	
	public Colonne(int index) {
		this.index = index;
	}
	
	public Colonne(int index, String alias) {
		this.index= index;
		this.alias = alias;
	}
}
