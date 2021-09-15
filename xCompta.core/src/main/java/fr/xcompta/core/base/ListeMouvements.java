package fr.xcompta.core.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.ManyToOne;

//@Entity
public class ListeMouvements implements Iterable<ListeMouvements>, Serializable {
	private static final long serialVersionUID = 1L;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
//	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
	private List<Mouvement> mouvements ;
//	@ManyToOne
	private Ecriture ecriture = null;
	
	
	public ListeMouvements() {
		mouvements = new ArrayList<Mouvement>();
	}
	
	public ListeMouvements(List<Mouvement> listeMouvements) {
		this.mouvements = listeMouvements;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setEcriture(Ecriture ecriture) {
		this.ecriture = ecriture;
	}

	public Ecriture getEcriture() {
		return ecriture;
	}
	
	public float getSolde() {
		//Todo remplacer par un stream
		float solde = 0;
		
		for(Mouvement mouvement : mouvements) {
			solde = solde + mouvement.getMontant();
		}
		return solde;
	}
	
	public boolean estEquilibree() {
		return getSolde() == 0;
	}
	
	public boolean estDebiteur() {
		return getSolde() > 0;
	}

	public boolean estCrediteur() {
		return getSolde() < 0;
	}

	@Override
	public Iterator<ListeMouvements> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}

//Todo
//Remplacera à terme le champs mouvements dans ecriture
//implementer plus tard les anontation hibernate.jpA
//impreleneter iterator
// ou faire dériver la classe d'ArrayListe
//lier à Ecriture et MMouvement
//ajouter les méthodesutilitaire
//
//ajouter 
//ajouter à, 
//remove
//...
