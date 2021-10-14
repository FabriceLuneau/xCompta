package fr.xcompta.core.etat;

public class Cellule {
	private String valeur = "";
	private boolean editable = true;

	
	public Cellule() {}

public Cellule(String valeur) {
	this.valeur = valeur;
}

public String getValeur() {
	return valeur;
}

public void setValeur(String valeur) {
	this.valeur = valeur;
}

public boolean isEditable() {
	 return editable;
}
	
	public void setEditable() {
		this.editable = editable;
}
	
	@Override
	public String toString() {
		return valeur;
	}
}
