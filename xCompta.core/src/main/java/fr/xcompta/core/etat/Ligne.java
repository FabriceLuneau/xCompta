package fr.xcompta.core.etat;

public class Ligne {
	private int index;
	private String alias;
	private float hauteur;

	
	public Ligne(int index) {
		this.index = index;
	}
	
	public Ligne(int index, String alias) {
this.index = index;
	this.alias = alias;
}
}

