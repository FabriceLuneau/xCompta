package fr.xcompta.core.etat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Etat extends HashMap<String, Cellule> {
	String name = "Pas de nom";
	private int largeur = 0;
	private static int Largeur_max = 676;

	private int hauteur = 0;

	private boolean editable = true;
	
	private HashMap<String, String> colonnes = new HashMap<>();

	public Etat() {
		super();
	}

	public Etat(int x, int y) {
		super();
		
		this.largeur = x;
		this.hauteur = y;
	}

	public int getLargeur() {
		return largeur;
	}
	
	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}

	public int getHauteur() {
		return hauteur;
	}
	
	public void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean  editable) {
		this.editable = editable;
	}

//	@Override
//	public boolean isEmpty() {
//		return super.isEmpty();
//	}
	
	public void setColonne(int x, String nom) {
	colonnes.put(Base26Convertisseur.nombreVersTable26(x), nom);
	}
	
	public void setColonne(String x, String nom) {
		colonnes.put(x, nom);
	}
	
	public String getColonne(int x) {
		return colonnes.get(Base26Convertisseur.nombreVersTable26(x));
		}
		
		public String getColonne(String x, String nom) {
			return colonnes.get(x);
		}

	public Cellule get(String clef) {
//		if (containsKey(clef)) {
			return super.get(clef);
//		} else {
//			return null;
//		}
	}
		
		public Cellule get(int x, int y ) {
			return get(Clef.calculerClef(x, y));
		}

	public boolean containsClef(String clef) {
		return super.containsKey(clef);
	}
	
	public boolean containsClef(int x, int y) {
	return containsClef(Clef.calculerClef(x, y));
	}

	public Cellule put(int x, int y, String valeur) {
		if(x > largeur) {
			largeur = x;
		}
		
		if(y > hauteur) {
			hauteur = y;
		}
		
		return super.put(Clef.calculerClef(x, y).toString(), new Cellule(valeur));
	}

//	public Cellule put(int x, int y, Cellule cellule) {
//		return super.put(new Clef(x, y), cellule);
//	}

//		@Override
//		public void putAll(Map<? extends Clef, ? extends Cellule> m) {
//			super.putAll(m);
//		}

//	public Cellule remove(Clef clef) {
//		return super.remove(clef);
//	}
	
	public Cellule remove(int x, int y) {
		return remove(new Clef(x, y));
	}
	
	

//	@Override
//	public void clear() {
//		super.clear();
//	}

//	@Override
//	public boolean containsValue(Object value) {
//		return super.containsValue(value);
//	}

//	@Override
//	public Set<Clef> keySet() {
//		return super.keySet();
//	}

//	@Override
//	public Collection<Cellule> values() {
//		return super.values();
//	}

//	@Override
//	public Set<Entry<Clef, Cellule>> entrySet() {
//		return super.entrySet();
//	}

//	@Override
//	public Cellule getOrDefault(Object key, Cellule defaultValue) {
//		return super.getOrDefault(key, defaultValue);
//	}

//	@Override
//	public Cellule putIfAbsent(Clef key, Cellule value) {
//		return super.putIfAbsent(key, value);
//	}

//	@Override
//	public boolean remove(Object key, Object value) {
//		 TODO Auto-generated method stub
//		return super.remove(key, value);
//	}

//	@Override
//	public boolean replace(Clef key, Cellule oldValue, Cellule newValue) {
//		return super.replace(key, oldValue, newValue);
//	}

//	@Override
//	public Cellule replace(Clef key, Cellule value) {
//		return super.replace(key, value);
//	}

//	@Override
//	public Cellule computeIfAbsent(Clef key, Function<? super Clef, ? extends Cellule> mappingFunction) {
//		 TODO Auto-generated method stub
//		return super.computeIfAbsent(key, mappingFunction);
//	}

//	@Override
//	public Cellule computeIfPresent(Clef key,
//			BiFunction<? super Clef, ? super Cellule, ? extends Cellule> remappingFunction) {
//		 TODO Auto-generated method stub
//		return super.computeIfPresent(key, remappingFunction);
//	}

//	@Override
//	public Cellule compute(Clef key, BiFunction<? super Clef, ? super Cellule, ? extends Cellule> remappingFunction) {
//		 TODO Auto-generated method stub
//		return super.compute(key, remappingFunction);
//	}

//	@Override
//	public Cellule merge(Clef key, Cellule value,
//			BiFunction<? super Cellule, ? super Cellule, ? extends Cellule> remappingFunction) {
//		 TODO Auto-generated method stub
//		return super.merge(key, value, remappingFunction);
//	}

//	@Override
//	public void forEach(BiConsumer<? super Clef, ? super Cellule> action) {
//		super.forEach(action);
//	}

//	@Override
//	public void replaceAll(BiFunction<? super Clef, ? super Cellule, ? extends Cellule> function) {
//		super.replaceAll(function);
//	}

//	@Override
//	public Object clone() {
//		return super.clone();
//	}

public void setName(String name) {
	this.name = name;
}

public String getName() {
	return name;
}

@Override
public String toString() {
	String texte = "Nom de l'état " + name + "\n"; 
	
	for(Entry entry : entrySet()){
		texte += entry;
}
	return texte;
}
}

