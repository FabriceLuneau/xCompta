package fr.xcompta.core.xcontext;

import java.util.Date;
import java.util.List;

import fr.xcompta.core.base.Classe;
import fr.xcompta.core.base.Compte;
import fr.xcompta.core.base.Ecriture;
import fr.xcompta.core.base.EcritureDeBrouillard;
import fr.xcompta.core.base.EcritureValidee;
import fr.xcompta.core.base.Journal;
import fr.xcompta.core.base.Lettrage;
import fr.xcompta.core.base.Mouvement;
import fr.xcompta.core.base.Solde;
import fr.xcompta.core.base.exception.ValidationEcritureException;
import fr.xcompta.core.etat.Etat;
import fr.xcompta.core.xcontext.dao.exception.XComptaMiseAJourException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetExisteDejaException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetIntrouvableException;
import fr.xcompta.core.xcontext.dao.exception.XComptaObjetVerouilleException;
import fr.xcompta.core.xcontext.dao.exception.XComptaSauvegardeException;
import fr.xcompta.core.xcontext.exception.XComptaConfigurationException;

public interface XContextInterface extends AutoCloseable {
	 public abstract void open() throws XComptaConfigurationException;
	 @Override
	 public void close();
	 
	 public String getName();
	 

	// *****Classe
	public abstract Classe getClasse(short numero) throws XComptaObjetIntrouvableException;

	public abstract List<Classe> getClasses();

	public abstract void sauverClasse(Classe classe) throws XComptaObjetExisteDejaException, XComptaSauvegardeException;

	public abstract void supprimerClasse(Classe classe) throws XComptaObjetVerouilleException;

	public abstract void mettreAJourClasse(Classe classe) throws XComptaMiseAJourException;

	public abstract boolean classeExiste(Short numero);

	// *****Compte
	public abstract Compte getCompte(String numero) throws XComptaObjetIntrouvableException;

	public abstract List<Compte> getComptes();

	public abstract void sauverCompte(Compte compte) throws XComptaObjetExisteDejaException, XComptaSauvegardeException;
	
	public abstract Etat getEtatPlanComptable(); 

	public abstract void supprimerCompte(Compte compte) throws XComptaObjetVerouilleException;

	public abstract void mettreAJourCompte(Compte compte);

	public abstract boolean compteExiste(String numero);

	// *****Journal
	public abstract Journal getJournal(String code) throws XComptaObjetIntrouvableException;

	public abstract List<Journal> getJournaux();

	public abstract void sauverJournal(Journal journal) throws XComptaObjetExisteDejaException, XComptaSauvegardeException;

	public abstract void supprimerJournal(Journal journal) throws XComptaObjetVerouilleException;

	public abstract void mettreAJourJournal(Journal journal);

	public abstract boolean journalExiste(String code);

	public abstract Ecriture getEcriture(int id) throws XComptaObjetIntrouvableException;

	public abstract List<Ecriture> getEcritures();

	public abstract Integer sauverEcriture(Ecriture ecriture);

	public abstract void mettreAJourEcriture(Ecriture ecriture)
			throws XComptaObjetVerouilleException, XComptaObjetIntrouvableException;

	public abstract boolean ecritureExiste(int id);

	// Solde
	public abstract Solde getSolde(int id) throws XComptaObjetIntrouvableException;

	public abstract List<Solde> getSoldes();

	public abstract void sauverSolde(Solde solde);

	public abstract void supprimerSolde(Solde solde) throws XComptaObjetVerouilleException;

	public abstract void mettreAJourSolde(Solde solde);

	public abstract boolean soldeExiste(int id);

	// *****Lettrage
	public abstract Lettrage getLettrage(int id) throws XComptaObjetIntrouvableException;

	public abstract List<Lettrage> getLettrage();

	public abstract void sauverLettrage(Lettrage lettrage);

	public abstract void supprimerLettrage(Lettrage lettrage);

	public abstract void mettreAJourLettrage(Lettrage lettrage);

	public abstract boolean lettrageExiste(int id);

	// *****Extrait Journal
	public abstract List<Mouvement> getExtraitJournal(Journal journal, boolean brouillard, Date debut, Date fin);

	public abstract EcritureValidee validerEcriture(EcritureDeBrouillard ecriture) throws ValidationEcritureException;

	public abstract void getExtraitBrouillard(Journal journal, Date debut, Date fin);

	public abstract List<Mouvement> getLettrage(Compte compte);

	public abstract List<Mouvement> getDeLettrage(Compte compte);

	public abstract void lettrer(List<Mouvement> mouvements);

	public abstract void deLettrer(List<Mouvement> mouvements);
	
	//Etat
}