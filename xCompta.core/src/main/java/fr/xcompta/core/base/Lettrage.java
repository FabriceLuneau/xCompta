package fr.xcompta.core.base;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
* @author Fabrice Luneau
* Modelise le lettrage des compte s'applique au mouvements qui se
* referent a un compte lettrables 
* 
* remplacera l'ancienne implementation,
*qui mettait un id dans chaques mouvement, cela permmettra de
*         consommer moins d'espace en base -un delettrage correspond � la         destruction d'un lettrage - un lettrage se fait � une date donnees -
*         un mouvement ne peut etre lettre qu'une seule fois - Un lettrage
*         concerne au moins deux mouvements -la somme des montants des
*         mouvements est eqquilibree et donc "egal a 0 -un * lettrage concerne
*         un et un seul compte, tous les mouvements doivent se referer au meme
*         compte
* 
*/
@Entity
public class Lettrage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id = 0;
	@Basic
	@NotNull
	private LocalDateTime date;
	@OneToMany
	private List<Mouvement> mouvements = new ArrayList<>();

	/**
	 * Constructeur vide pour JPA/Hibernate
	 */
	public Lettrage() {
		id = 0;
		date = LocalDateTime.now();
	}

	// Implementer d'autres constructeurs

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Lettrage : L'ID en argument est invalide");
		}

		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		if (date == null) {
			throw new NullPointerException("Lettrage : L'objet Date en argument est null)");
		}

		this.date = date;
	}

	public List<Mouvement> getMouvements() {
		return mouvements;
	}

	public void setMouvements(List<Mouvement> mouvements) {
		if (mouvements == null) {
			throw new NullPointerException("Lettrage : M'objet List en argument est NULL");
		}
		this.mouvements = mouvements;
	}

	public void addMouvement(Mouvement mouvement) {
		if (mouvement == null) {
			throw new NullPointerException("Lettrage : l'objet Mouvement en argument est NULL");
		}

		mouvements.add(mouvement);
	}

	public void removeMouvement(Mouvement mouvement) {
		if (mouvement == null) {
			throw new NullPointerException("Lettrage : l'objet Mouvement en argument est NULL");
		}

		mouvements.remove(mouvement);
	}

	public boolean estEquilibre() {
		float solde = 0;

		for (Mouvement mouvement : mouvements) {
			solde = +mouvement.getMontant();
		}

		return (solde == 0);
	}

}
